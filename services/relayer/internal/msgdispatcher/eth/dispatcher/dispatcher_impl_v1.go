package dispatcher

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
	"context"
	"math/big"
	"time"

	"github.com/consensys/gpact/services/relayer/internal/contracts/messaging"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
)

const (
	queueSize = 10
)

// DispatcherImplV1 implements dispatcher interface.
type DispatcherImplV1 struct {
	end chan bool

	queue chan Item
}

// Item represents an item in the queue.
type Item struct {
	link      string
	auth      *bind.TransactOpts
	id        string
	destID    *big.Int
	esAddr    common.Address
	srcID     *big.Int
	srcAddr   common.Address
	rawData   []byte
	signature []byte
}

// NewDispatcherImplV1 creates a new dispatcher.
func NewDispatcherImplV1() Dispatcher {
	return &DispatcherImplV1{
		end:   make(chan bool),
		queue: make(chan Item, queueSize),
	}
}

// Start starts the Dispatcher's routine.
func (d *DispatcherImplV1) Start() error {
	go d.processRoutine()
	return nil
}

// Stop stops the dispatcher.
func (d *DispatcherImplV1) Stop() {
	d.end <- true
}

// AddToQueue adds a message to the queue for process.
func (d *DispatcherImplV1) AddToQueue(link string, auth *bind.TransactOpts, id string, destID *big.Int, esAddr common.Address, srcID *big.Int, srcAddr common.Address, rawData []byte, signature []byte) {
	d.queue <- Item{
		link:      link,
		auth:      auth,
		id:        id,
		destID:    destID,
		esAddr:    esAddr,
		srcID:     srcID,
		srcAddr:   srcAddr,
		rawData:   rawData,
		signature: signature,
	}
}

// processRoutine processes message coming from the queue.
func (d *DispatcherImplV1) processRoutine() {
	for {
		select {
		case <-d.end:
			return
		case item := <-d.queue:
			logging.Info("Received message with id %v from queue", item.id)

			var err error
			var chain *ethclient.Client
			// Retry 3 times, 3 seconds apart.
			for i := 0; i < 3; i++ {
				chain, err = ethclient.Dial(item.link)
				if err == nil {
					break
				}
				logging.Error("Error in connecting to chain, retry...: %v", err.Error())
				time.Sleep(3 * time.Second)
			}
			if err != nil {
				logging.Error("Error in connecting to chain, stop retry: %v", err.Error())
				continue
			}
			logging.Info("Connected to chain %v", item.destID)
			dispatch(chain, item.auth, item.esAddr, item.srcID, item.srcAddr, item.rawData, item.signature)
			chain.Close()
		}
	}
}

// dispatch dispatches the actual message to the chain.
func dispatch(chain *ethclient.Client, auth *bind.TransactOpts, esAddr common.Address, srcID *big.Int, srcAddr common.Address, rawData []byte, signature []byte) {
	// Load verifier
	verifier, err := messaging.NewSignedEventStore(esAddr, chain)
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

	// Try 5 times, 5 seconds apart.
	for i := 0; i < 5; i++ {
		var tx *types.Transaction
		tx, err = verifier.RelayEvent(auth, srcID, srcAddr, rawData, signature)
		if err == nil {
			logging.Info("Transaction submitted with hash: %v", tx.Hash().String())
			return
		}
		logging.Error("Error submitting transaction, Retry...: %v", err.Error())
		time.Sleep(5 * time.Second)
		auth.GasPrice, err = chain.SuggestGasPrice(context.Background())
		if err != nil {
			logging.Error(err.Error())
			return
		}
	}
	logging.Error("Error submitting transaction, stop retry")
}
