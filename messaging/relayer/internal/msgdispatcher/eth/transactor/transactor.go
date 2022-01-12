package transactor

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
	"math/big"

	"github.com/ethereum/go-ethereum/accounts/abi/bind"
)

// Transactor is an interface for a chain transactor.
type Transactor interface {
	// Start starts the transactor's routine.
	Start() error

	// Stop safely stops the transactor.
	Stop()

	// SetTransactionOpts sets a key to given chain and chain AP.
	SetTransactionOpts(chainID *big.Int, chainAP string, key []byte) error

	// GetChainAP gets a chain ap associated given chain id.
	GetChainAP(chainID *big.Int) (string, error)

	// GetAuth gets the auth for given chain id.
	GetAuth(chainID *big.Int) (*bind.TransactOpts, error)
}
