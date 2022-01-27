package observer

/*
 * Copyright 2021 ConsenSys Software Inc.
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
	"math/big"
	"testing"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core"
	"github.com/ethereum/go-ethereum/crypto"
	"github.com/stretchr/testify/mock"
)

type MockMQ struct {
	mock.Mock
	LastMessage messages.Message
}

func (mk *MockMQ) Request(version string, msgType string, msg messages.Message) {
	mk.LastMessage = msg
	mk.Called(version, msgType, msg)
}

func (mk *MockMQ) Start() error {
	return nil
}

func (mk *MockMQ) Stop() {}

func simulatedBackend(t *testing.T) (*backends.SimulatedBackend, *bind.TransactOpts) {
	key, err := crypto.GenerateKey()
	if err != nil {
		failNow(t, "failed to generate key: %v", err)
	}

	auth, _ := bind.NewKeyedTransactorWithChainID(key, big.NewInt(1337))

	address := auth.From
	genesisAlloc := map[common.Address]core.GenesisAccount{
		address: {
			Balance: big.NewInt(10000000000000000),
		},
	}
	blockGasLimit := uint64(10000000)
	return backends.NewSimulatedBackend(genesisAlloc, blockGasLimit), auth
}

func deployContract(t *testing.T, simBackend *backends.SimulatedBackend, auth *bind.TransactOpts) *functioncall.Sfc {
	_, _, contract, err := functioncall.DeploySfc(auth, simBackend, big.NewInt(10), big.NewInt(10))

	if err != nil {
		failNow(t, "failed to deploy contract: %v", err)
	}

	simBackend.Commit()
	return contract
}

func failNow(t *testing.T, message string, args ...interface{}) {
	t.Errorf(message, args...)
	t.FailNow()
}
