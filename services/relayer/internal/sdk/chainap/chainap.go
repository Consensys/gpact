package chainap

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

	"github.com/ethereum/go-ethereum/ethclient"
)

// ChainAPManager manages the access to chain.
type ChainAPManager interface {
	// RegisterChainAP registers an access of chain to given chainID.
	RegisterChainAP(chainID *big.Int, chainAP *ethclient.Client)

	// ChainAP obtains the access of chain for given chainID.
	ChainAP(chainID *big.Int) (*ethclient.Client, error)
}
