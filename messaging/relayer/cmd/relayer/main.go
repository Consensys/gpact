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
	"strconv"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/adminserver"
	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/crypto"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	_ "github.com/joho/godotenv/autoload"
)

// TODO, Need a separate package to put all core components.
var s *mqserver.MQServer
var api adminserver.AdminServer
var key []byte

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
	err = s.Start()
	if err != nil {
		panic(err)
	}
	logging.Info("Relayer started.")

	// Generate a random key
	key, err = crypto.Secp256k1GenerateKey()
	if err != nil {
		panic(err)
	}

	// For now just have one single handler for setting up relayer key with type 1.
	apiHandlers := make(map[byte]func(req []byte) ([]byte, error))
	apiHandlers[1] = func(req []byte) ([]byte, error) {
		key = req
		return []byte{0}, nil
	}

	for {
	}
}

// simpleHandler only does a message forward with some slightly message change.
func simpleHandler(req messages.Message) {
	// Received request from observer
	msg := req.(*v1.Message)
	logging.Info("Process message with ID: %v", msg.ID)

	// TODO, For now, just one single relayer. Sign & Send to dispatcher.
	data, err := hex.DecodeString(msg.Payload)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	raw := types.Log{}
	err = json.Unmarshal(data, &raw)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	srcID, err := strconv.Atoi(msg.Source.NetworkID)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	sfcAddr := common.HexToAddress(msg.Source.ContractAddress)

	toSign := make([]byte, 32)
	toSign[31] = byte(srcID)
	toSign = append(toSign, sfcAddr.Bytes()...)
	toSign = append(toSign, []byte{89, 247, 54, 220, 94, 21, 196, 177, 37, 38, 72, 117, 2, 100, 84, 3, 176, 164, 49, 109, 130, 235, 167, 233, 236, 220, 42, 5, 12, 16, 173, 39}...)
	toSign = append(toSign, raw.Data...)

	signature, err := crypto.Secp256k1Sign(key, toSign)
	if err != nil {
		panic(err)
	}
	signature[len(signature)-1] += 27

	// Add proof
	msg.Proofs = append(msg.Proofs, v1.Proof{
		ProofType: "secp256k1",
		Created:   time.Now().UnixMilli(), // Milli seconds?
		Proof:     hex.EncodeToString(signature),
	})

	s.Request(msg.Version, msg.MsgType, msg)
}
