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
	"github.com/consensys/gpact/messaging/relayer/internal/msgrelayer/eth/api"
	"github.com/consensys/gpact/messaging/relayer/internal/msgrelayer/eth/mq"
	"github.com/consensys/gpact/messaging/relayer/internal/msgrelayer/eth/node"
	"github.com/consensys/gpact/messaging/relayer/internal/msgrelayer/eth/signer"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
	_ "github.com/joho/godotenv/autoload"
)

// main entrypoint of relayer.
func main() {
	// Load config
	conf := config.NewConfig()
	node := node.GetSingleInstance()

	// Start the MQ server
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
	node.MQ = mq
	defer mq.Stop()
	// Start the Signer
	signer := signer.NewSignerImplV1(conf.SignerDSPath)
	err = signer.Start()
	if err != nil {
		panic(err)
	}
	node.Signer = signer
	defer signer.Stop()
	// Start the RPC Server
	rpc := rpc.NewServerImplV1(conf.APIPort).
		AddHandler(api.SetKeyReqType, api.HandleSetKey).
		AddHandler(api.GetAddrReqType, api.HandleGetAddr)
	err = rpc.Start()
	if err != nil {
		panic(err)
	}
	node.RPC = rpc
	defer rpc.Stop()
	logging.Info("Relayer started.")

	c := make(chan os.Signal)
	signal.Notify(c, os.Interrupt)
	<-c
}
