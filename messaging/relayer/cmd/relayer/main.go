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
	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	_ "github.com/joho/godotenv/autoload"
)

// TODO, Need a separate package to put all core components.
var s *mqserver.MQServer

func main() {
	// Load config
	conf := config.NewConfig()

	// Create handlers.
	handlers := make(map[string]map[string]func(msg messages.Message))
	handlers[v1.Version] = make(map[string]func(msg messages.Message))
	handlers[v1.Version][v1.MessageType] = simpleHandler

	// Start the server
	var err error
	s, err = mqserver.NewMQServer(
		conf.InboundMQAddr,
		conf.InboundChName,
		conf.OutboundMQAddr,
		conf.OutboundChName,
		handlers,
	)
	if err != nil {
		panic(err)
	}
	s.Start()
	logging.Info("Relayer started.")
	for {
	}
}

// simpleHandler only does a message forward with some slightly message change.
func simpleHandler(req messages.Message) {
	// Received request from observer
	msg := req.(*v1.Message)
	logging.Info("Process message with ID: %v", msg.ID)
	// Do a small modification
	msg.Payload = "processed:" + msg.Payload
	// Send it to dispatcher
	s.Request(msg.Version, msg.MsgType, msg)
}
