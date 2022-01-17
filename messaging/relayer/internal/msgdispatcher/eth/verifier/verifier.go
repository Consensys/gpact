package verifier

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

	"github.com/ethereum/go-ethereum/common"
)

// Verifier is an interface for the verifier in the system.
type Verifier interface {
	// Start starts the verifier's routine
	Start() error

	// Stop stops the verifier.
	Stop()

	// SetVerifierAddr sets a verifier address with associated chain id and contract address.
	SetVerifierAddr(chainID *big.Int, contractAddr common.Address, esAddr common.Address) error

	// GetVerifierAddr gets a verifier address for given associated chain id and contract address.
	GetVerifierAddr(chainID *big.Int, contractAddr common.Address) (common.Address, error)
}
