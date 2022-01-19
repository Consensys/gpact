package main

/*
 * Copyright 2021 ConsenSys Software Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import (
	"os"
	"os/signal"

	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/api"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/dispatcher"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/mq"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/node"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/transactor"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/verifier"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
	_ "github.com/joho/godotenv/autoload"
)

// main entrypoint of dispatcher.
func main() {
	// Load config
	conf := config.NewConfig()
	instance := node.GetSingleInstance()

	// Start the server
	var err error
	mq, err := mqserver.NewMQServer(
		conf.InboundMQAddr,
		conf.InboundChName,
		conf.OutboundMQAddr,
		conf.OutboundChName,
		mq.Handlers,
	)
	if err != nil {
		panic(err)
	}
	err = mq.Start()
	if err != nil {
		panic(err)
	}
	instance.MQ = mq
	defer mq.Stop()
	// Start the Transactor
	transactor := transactor.NewTransactorImplV1(conf.TransactorDSPath)
	err = transactor.Start()
	if err != nil {
		panic(err)
	}
	instance.Transactor = transactor
	defer transactor.Stop()
	// Start the Verifier
	verifier := verifier.NewVerifierImplV1(conf.VerifierDSPath)
	err = verifier.Start()
	if err != nil {
		panic(err)
	}
	instance.Verifier = verifier
	defer verifier.Stop()
	// Start the Dispatcher.
	dispatcher := dispatcher.NewDispatcherImplV1()
	err = dispatcher.Start()
	if err != nil {
		panic(err)
	}
	instance.Dispatcher = dispatcher
	defer dispatcher.Stop()
	// Start the RPC Server
	rpc := rpc.NewServerImplV1(conf.APIPort).
		AddHandler(api.SetTransactionOptsReqType, api.HandleSetTransactionOpts).
		AddHandler(api.GetChainAPReqType, api.HandleGetChainAP).
		AddHandler(api.GetAuthAddrReqType, api.HandleGetAuthAddr).
		AddHandler(api.SetVerifierAddrReqType, api.HandleSetVerifierAddr).
		AddHandler(api.GetVerifierAddrReqType, api.HandleGetVerifierAddr)
	err = rpc.Start()
	if err != nil {
		panic(err)
	}
	instance.RPC = rpc
	defer rpc.Stop()
	logging.Info("Dispatcher started.")

	c := make(chan os.Signal)
	signal.Notify(c, os.Interrupt)
	<-c
}
