package mq

/*
 * Copyright 2022 ConsenSys Software Inc
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
	"math/big"
	"strconv"
	"time"

	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"

	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/messages"
	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/signer"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
)

// handleV1 handles message v1 with type v1.
func handleV1(req messages.Message) {
	instance := node.GetSingleInstance()

	msg, ok := req.(*v1.Message)
	if !ok {
		logging.Error("fail to decode message")
		return
	}
	logging.Info("Process message with ID: %v", msg.ID)

	data, err := hex.DecodeString(msg.Payload)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	funcSig := data[:32]
	raw := types.Log{}
	err = json.Unmarshal(data[32:], &raw)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	srcID, err := strconv.Atoi(msg.Source.NetworkID)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	srcAddr := common.HexToAddress(msg.Source.ContractAddress)

	// If this message should be routed to the message store, remove the destination attribute
	// TODO:
	// the current implementation of the dispatcher determines what should go to the message store based on
	// whether the destination field is empty. This is brittle approach. The code below will be updated,
	// once the dispatcher is better refactored
	if instance.RelayRoutes.ShouldRouteToStore(&msg.Source) {
		logging.Info("Routing Message %v to Message Store", msg.ID)
		msg.Destination = v1.ApplicationAddress{}
	} else {
		logging.Info("NOT routing Message %v to Message Store", msg.ID)
	}

	//destID, err := strconv.Atoi(msg.Destination.NetworkID)
	//if err != nil {
	//	logging.Error(err.Error())
	//	return
	//}
	//destAddr := common.HexToAddress(msg.Destination.ContractAddress)

	toSign := make([]byte, 32)
	toSign[31] = byte(srcID)
	toSign = append(toSign, srcAddr.Bytes()...)
	toSign = append(toSign, funcSig...)
	toSign = append(toSign, raw.Data...)
	logging.Info("Generated data to be signed: %v", hex.EncodeToString(toSign))

	//Old: _, addr, err := instance.Signer.GetAddr(big.NewInt(int64(destID)), destAddr)
	//	_, addr, err := instance.Signer.GetAddr(big.NewInt(int64(srcID)), srcAddr)
	_, addr, err := instance.Signer.GetAddr(big.NewInt(0), common.BigToAddress(big.NewInt(0)))
	if err != nil {
		logging.Error("Signer not found for: Chain: %v, Address: %v, Error: %v", 0, 0, err.Error())
		//		logging.Error("Signer not found for: Chain: %v, Address: %v, Error: %v", destID, destAddr, err.Error())
		return
	}
	//	sigType, signature, err := instance.Signer.Sign(big.NewInt(int64(destID)), destAddr, toSign)
	//	sigType, signature, err := instance.Signer.Sign(big.NewInt(int64(srcID)), srcAddr, toSign)
	sigType, signature, err := instance.Signer.Sign(big.NewInt(0), common.BigToAddress(big.NewInt(0)), toSign)
	if err != nil {
		logging.Error("Signer error signing: %v", err.Error())
		return
	}
	logging.Info("Signature generated with type %v: %v", signer.TypeToString(sigType), hex.EncodeToString(signature))

	signature[len(signature)-1] += 27
	// TODO, Add relayer communication to order signatures from different relayers.
	signature = append([]byte{0, 0, 0, 1}, append(addr.Bytes(), signature...)...)

	// Add proof
	msg.Proofs = append(msg.Proofs, v1.Proof{
		ProofType: signer.TypeToString(sigType),
		Created:   time.Now().UnixMilli(),
		Proof:     hex.EncodeToString(signature),
	})

	logging.Info("Message Destination: %v", msg.Destination.ContractAddress)
	// Pass message to MQ.
	go func() {
		err := instance.MQ.Request(msg.Version, msg.MsgType, msg)
		if err != nil {
			logging.Error("error occurred submitting message to queue: %v", err)
		}
	}()
}

// initV1 inits the handler.
func initV1() {
	_, ok := Handlers[v1.Version]
	if !ok {
		Handlers[v1.Version] = make(map[string]func(msg messages.Message))
	}
	Handlers[v1.Version][v1.MessageType] = handleV1
}
