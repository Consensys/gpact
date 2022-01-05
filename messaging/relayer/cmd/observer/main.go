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
	"encoding/hex"
	"encoding/json"

	"github.com/consensys/gpact/messaging/relayer/internal/adminserver"
	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	_ "github.com/joho/godotenv/autoload"
)

// TODO, Need a separate package to put all core components.
var s mqserver.MessageQueue
var api adminserver.AdminServer

func main() {
	// Load config
	conf := config.NewConfig()

	// Start the server
	var err error
	s, err = mqserver.NewMQServer(
		conf.InboundMQAddr,
		conf.InboundChName,
		conf.OutboundMQAddr,
		conf.OutboundChName,
		nil,
	)
	if err != nil {
		panic(err)
	}
	err = s.Start()
	if err != nil {
		panic(err)
	}
	logging.Info("Observer started.")

	// For now just have one single handler for observing chain with type 1.
	apiHandlers := make(map[byte]func(req []byte) ([]byte, error))
	apiHandlers[1] = func(req []byte) ([]byte, error) {
		request := Request{}
		err = json.Unmarshal(req, &request)
		if err != nil {
			return nil, err
		}
		go func() {
			// TODO: User msgobserver.
			chain, err := ethclient.Dial(request.Chain)
			if err != nil {
				logging.Error(err.Error())
				return
			}
			defer chain.Close()

			sfc, err := functioncall.NewSfc(common.HexToAddress(request.SFCAddr), chain)
			if err != nil {
				logging.Error(err.Error())
				return
			}
			sink := make(chan *functioncall.SfcCrossCall)
			event, err := sfc.WatchCrossCall(&bind.WatchOpts{Start: nil, Context: nil}, sink)
			if err != nil {
				logging.Error(err.Error())
				return
			}
			for {
				select {
				case log := <-event.Err():
					logging.Error(log.Error())
					return
				case call := <-sink:
					// Pack & Send to message queue.
					logging.Info("Event observed: %v.", call)
					data, err := json.Marshal(call.Raw)
					if err != nil {
						logging.Error(err.Error())
						return
					}
					msg := &v1.Message{
						ID:        "TBD", //TODO
						Timestamp: call.Timestamp.Int64(),
						MsgType:   v1.MessageType,
						Version:   v1.Version,
						Destination: v1.ApplicationAddress{
							NetworkID:       call.DestBcId.String(),
							ContractAddress: call.DestContract.String(),
						},
						Source: v1.ApplicationAddress{
							NetworkID:       request.BcID,
							ContractAddress: request.SFCAddr,
						},
						Proofs:  []v1.Proof{},
						Payload: hex.EncodeToString(data),
					}
					s.Request(v1.Version, v1.MessageType, msg)
					logging.Info("Event processed.")
				}
			}
		}()
		return []byte{0}, nil
	}
	api = adminserver.NewAdminServerImpl(conf.APIPort, apiHandlers)
	err = api.Start()
	if err != nil {
		panic(err)
	}
	for {
	}
}

// TODO: Create a package to place all admin APIs.
type Request struct {
	BcID    string `json:"bc_id"`
	Chain   string `json:"chain"`
	SFCAddr string `json:"sfc_addr"`
}
