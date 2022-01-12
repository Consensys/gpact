package mq

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
	"context"
	"encoding/hex"
	"encoding/json"
	"math/big"
	"strconv"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/messaging"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/node"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
)

// handleV1 handles message v1 with type v1.
func handleV1(req messages.Message) {
	node := node.GetSingleInstance()

	msg, ok := req.(*v1.Message)
	if !ok {
		logging.Error("fail to decode message")
		return
	}
	logging.Info("Process message with ID: %v", msg.ID)

	destID, err := strconv.Atoi(msg.Destination.NetworkID)
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
	destAddr := common.HexToAddress(msg.Destination.ContractAddress)
	logging.Info("Received message for bridging from contract %v on chain %v to contract %v on chain %v", srcAddr.String(), srcID, destAddr.String(), destID)

	link, err := node.Transactor.GetChainAP(big.NewInt(int64(destID)))
	if err != nil {
		logging.Error(err.Error())
		return
	}
	auth, err := node.Transactor.GetAuth(big.NewInt(int64(destID)))
	if err != nil {
		logging.Error(err.Error())
		return
	}
	esAddr, err := node.Verifier.GetVerifierAddr(big.NewInt(int64(destID)), destAddr)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	logging.Info("Obtain event store address: %v", esAddr.String())

	var chain *ethclient.Client
	// Retry 3 times, 3 seconds apart.
	for i := 0; i < 3; i++ {
		chain, err = ethclient.Dial(link)
		if err == nil {
			break
		}
		logging.Error("Error in connecting to chain, retry...: %v", err.Error())
		time.Sleep(3 * time.Second)
	}
	if err != nil {
		logging.Error("Error in connecting to chain, stop retry: %v", err.Error())
		return
	}
	logging.Info("Connected to chain %v", destID)

	// Load verifier
	verifier, err := messaging.NewSignedEventStore(esAddr, chain)
	if err != nil {
		logging.Error(err.Error())
		return
	}

	// Get proof
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
	if len(msg.Proofs) == 0 {
		logging.Error("Empty proofs received.")
		return
	}
	signature, err := hex.DecodeString(msg.Proofs[0].Proof)
	if err != nil {
		logging.Error(err.Error())
		return
	}

	// Dispatching
	logging.Info("Start dispatching...")
	// TODO: Need to estimate the gas involved in the call.
	auth.GasLimit = uint64(3000000)
	auth.GasPrice, err = chain.SuggestGasPrice(context.Background())
	if err != nil {
		logging.Error(err.Error())
		return
	}
	nonce, err := chain.NonceAt(context.Background(), auth.From, nil)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	auth.Nonce = big.NewInt(int64(nonce))

	// Try 5 times, 3 seconds apart.
	for i := 0; i < 5; i++ {
		var tx *types.Transaction
		tx, err = verifier.RelayEvent(auth, big.NewInt(int64(srcID)), srcAddr, raw.Data, signature)
		if err == nil {
			logging.Info("Transaction submitted with hash: %v", tx.Hash().String())
			return
		}
		logging.Error("Error submitting transaction, Retry...: %v", err.Error())
		time.Sleep(3 * time.Second)
	}
	logging.Error("Error submitting transaction, stop retry: %v", err.Error())
}

// initV1 inits the handler.
func initV1() {
	_, ok := Handlers[v1.Version]
	if !ok {
		Handlers[v1.Version] = make(map[string]func(msg messages.Message))
	}
	Handlers[v1.Version][v1.MessageType] = handleV1
}
