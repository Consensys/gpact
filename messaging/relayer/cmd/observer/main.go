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
	"crypto/rand"
	"encoding/hex"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	_ "github.com/joho/godotenv/autoload"
)

// TODO, Need a separate package to put all core components.
var s *mqserver.MQServer

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
	s.Start()
	logging.Info("Observer started.")
	for {
		// Send a random message every 3 seconds.
		go sendRandomMessage()
		time.Sleep(3 * time.Second)
	}
}

// sendRandomMessage creates and send a random message.
func sendRandomMessage() {
	msg := &v1.Message{
		ID:        hex.EncodeToString(randomBytes(16)),
		Timestamp: time.Now().UnixMilli(),
		Destination: v1.ApplicationAddress{
			NetworkID:       hex.EncodeToString(randomBytes(2)),
			ContractAddress: hex.EncodeToString(randomBytes(16)),
		},
		Source: v1.ApplicationAddress{
			NetworkID:       hex.EncodeToString(randomBytes(2)),
			ContractAddress: hex.EncodeToString(randomBytes(16)),
		},
		Proofs: []v1.Proof{
			{
				ProofType: hex.EncodeToString(randomBytes(4)),
				Created:   time.Now().UnixMilli(),
				Proof:     hex.EncodeToString(randomBytes(32)),
			},
		},
		Payload: hex.EncodeToString(randomBytes(16)),
	}
	logging.Info("Send message with ID: %v", msg.ID)
	s.Request(v1.Version, v1.MessageType, msg)
}

func randomBytes(n int) []byte {
	res := make([]byte, n)
	rand.Read(res)
	return res
}
