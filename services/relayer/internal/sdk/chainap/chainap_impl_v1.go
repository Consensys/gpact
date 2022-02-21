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
	"fmt"
	"math/big"

	"github.com/ethereum/go-ethereum/ethclient"
)

// ChainAPManagerImplV1 implements ChainAPManager.
type ChainAPManagerImplV1 struct {
	chainAPs map[string]*ethclient.Client
}

// NewChainAPManagerImplV1 creates a new chain ap manager impl v1.
func NewChainAPManagerImplV1() *ChainAPManagerImplV1 {
	return &ChainAPManagerImplV1{
		chainAPs: make(map[string]*ethclient.Client),
	}
}

// RegisterChainAP registers an access of chain to given chainID.
func (mgr *ChainAPManagerImplV1) RegisterChainAP(chainID *big.Int, chainAP *ethclient.Client) {
	mgr.chainAPs[chainID.String()] = chainAP
}

// ChainAP obtains the access of chain for given chainID.
func (mgr *ChainAPManagerImplV1) ChainAP(chainID *big.Int) (*ethclient.Client, error) {
	ap, ok := mgr.chainAPs[chainID.String()]
	if !ok {
		return nil, fmt.Errorf("chain id %v is not suppoorted.", chainID.String())
	}
	return ap, nil
}
