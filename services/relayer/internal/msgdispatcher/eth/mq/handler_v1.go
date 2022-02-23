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
	"bytes"
	"encoding/hex"
	"encoding/json"
	"fmt"
	"math/big"
	"net/http"
	"strconv"

	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"

	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/messages"
	"github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/node"
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
	empty := common.Address{}
	if destAddr != empty {
		logging.Info("Received message for bridging from contract %v on chain %v to contract %v on chain %v", srcAddr.String(), srcID, destAddr.String(), destID)

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

		link, err := instance.Transactor.GetChainAP(big.NewInt(int64(destID)))
		if err != nil {
			logging.Error(err.Error())
			return
		}
		auth, err := instance.Transactor.GetAuth(big.NewInt(int64(destID)))
		if err != nil {
			logging.Error(err.Error())
			return
		}
		esAddr, err := instance.Verifier.GetVerifierAddr(big.NewInt(int64(destID)), destAddr)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		logging.Info("Obtain event store address: %v", esAddr.String())

		logging.Info("Adding message %v to queue for process...", msg.ID)
		instance.Dispatcher.AddToQueue(link, auth, msg.ID, big.NewInt(int64(destID)), esAddr, big.NewInt(int64(srcID)), srcAddr, raw.Data, signature)
		logging.Info("Message %v is added to queue.", msg.ID)
	} else {
		logging.Info("Received message for bridging from contract %v on chain %v to message store on %v", srcAddr.String(), srcID, instance.MessageStoreAddr)

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
		// Put message
		client := &http.Client{}
		req, err := http.NewRequest("PUT", fmt.Sprintf("http://%v/messages/", instance.MessageStoreAddr), bytes.NewReader(msg.ToBytes()))
		if err != nil {
			logging.Error(err.Error())
			return
		}
		resp, err := client.Do(req)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		if !(resp.StatusCode == http.StatusOK || resp.StatusCode == http.StatusCreated) {
			logging.Error("Error creating new message: %v", resp.Status)
			return
		}
		logging.Info("Message %v is pushed to message store.", msg.ID)
	}
}

// initV1 inits the handler.
func initV1() {
	_, ok := Handlers[v1.Version]
	if !ok {
		Handlers[v1.Version] = make(map[string]func(msg messages.Message))
	}
	Handlers[v1.Version][v1.MessageType] = handleV1
}
