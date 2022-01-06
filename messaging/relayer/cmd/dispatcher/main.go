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
	"context"
	"crypto/ecdsa"
	"encoding/hex"
	"encoding/json"
	"math/big"
	"strconv"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/adminserver"
	"github.com/consensys/gpact/messaging/relayer/internal/config"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/messaging"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/crypto/secp256k1"
	"github.com/ethereum/go-ethereum/ethclient"
	_ "github.com/joho/godotenv/autoload"
)

// TODO, Need a separate package to put all core components.
var s *mqserver.MQServer
var api adminserver.AdminServer
var chainInfos map[byte]chainInfo

type chainInfo struct {
	auth   *bind.TransactOpts
	chain  string
	esAddr string
}

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
	logging.Info("Dispatcher started.")

	// For now just have one single handler for dispatching chain with type 1.
	chainInfos = make(map[byte]chainInfo)
	apiHandlers := make(map[byte]func(req []byte) ([]byte, error))
	apiHandlers[1] = func(req []byte) ([]byte, error) {
		request := Request{}
		err = json.Unmarshal(req, &request)
		if err != nil {
			return nil, err
		}
		key, err := hex.DecodeString(request.Key)
		if err != nil {
			return nil, err
		}

		x, y := secp256k1.S256().ScalarBaseMult(key)
		prv := &ecdsa.PrivateKey{}
		prv.D = big.NewInt(0).SetBytes(key)
		prv.PublicKey = ecdsa.PublicKey{
			X:     x,
			Y:     y,
			Curve: secp256k1.S256(),
		}
		auth := bind.NewKeyedTransactor(prv)
		auth.Nonce = big.NewInt(int64(request.Nonce))
		auth.Value = big.NewInt(0)      // in wei
		auth.GasLimit = uint64(3000000) // in units
		auth.GasPrice = big.NewInt(0)

		chainInfos[request.BcID] = chainInfo{
			auth:   auth,
			chain:  request.Chain,
			esAddr: request.EsAddr,
		}
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

// simpleHandler only does a message forward with some slightly message change.
func simpleHandler(req messages.Message) {
	// Received request from observer
	msg := req.(*v1.Message)
	logging.Info("Process message with ID: %v", msg.ID)

	destID, err := strconv.Atoi(msg.Destination.NetworkID)
	if err != nil {
		logging.Error(err.Error())
		return
	}

	info, ok := chainInfos[byte(destID)]
	if !ok {
		logging.Error("chain id %v not supported", destID)
		return
	}

	chain, err := ethclient.Dial(info.chain)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	defer chain.Close()

	srcID, err := strconv.Atoi(msg.Source.NetworkID)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	sfcAddr := common.HexToAddress(msg.Source.ContractAddress)
	esAddr := common.HexToAddress(info.esAddr)

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
	tx, err := verifier.RelayEvent(info.auth, big.NewInt(int64(srcID)), sfcAddr, raw.Data, signature)
	if err != nil {
		logging.Error(err.Error())
		return
	}
	waitForReceipt(chain, tx)
	info.auth.Nonce = big.NewInt(0).Add(info.auth.Nonce, big.NewInt(1))
}

// TODO: Create a package to place all admin APIs.
type Request struct {
	BcID   byte   `json:"bc_id"`
	Key    string `json:"key"`
	Nonce  int    `json:"nonce"`
	Chain  string `json:"chain"`
	EsAddr string `json:"es_addr"`
}

func waitForReceipt(conn *ethclient.Client, tx *types.Transaction) error {
	for {
		rept, err := conn.TransactionReceipt(context.Background(), tx.Hash())
		if err == nil {
			if rept.Status != types.ReceiptStatusSuccessful {
				logging.Error("Transaction failed... ", rept)
				return nil
			} else {
			}
			break
		}
		time.Sleep(1 * time.Second)
	}
	return nil
}
