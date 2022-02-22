package simulator

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

	"github.com/consensys/gpact/services/relayer/internal/sdk/chainap"
	"github.com/consensys/gpact/services/relayer/internal/sdk/treenode"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/common"
)

// CrosschainCall represents a crosschain function call.
type CrosschainCall struct {
	ChainID      *big.Int
	ContractType string
	ContractAddr common.Address
	Method       string
	Params       []interface{}
}

// Link is the logic link of a crosschain calls. For a particular call, it is used to obtain all the subsequent crosschain calls.
// It takes a resource, chain id, contract address, parameters as arguments.
// It returns a list of chainIDs, contract types, contract addrs, methods, parameters lists of the subsequent calls.
type Link func(resource Resource, chainID *big.Int, contractAddr common.Address, params ...interface{}) ([]CrosschainCall, error)

// Resource is the resource that a link can use.
// TODO: For now, it contains only the chain ap manager, but it can contain more things in the future.
// For example, certain solidity global variables/functions  (msg.sender, msg.data...).
type Resource struct {
	ChainAPMgr chainap.ChainAPManager
}

// Simulator is used to simulate a crosschain function call and generate the call execution tree.
type Simulator interface {
	// RegisterABI registers a contract abi.
	RegisterABI(contractType string, contractABI *abi.ABI)

	// RegisterCallLink registers a call link.
	RegisterCallLink(contractType string, method string, link Link)

	// Simulate simulates a crosschain function call, builds a call execution tree and returns the root of the tree.
	Simulate(chainID *big.Int, contractType string, contractAddr common.Address, method string, params ...interface{}) (*treenode.TreeNode, error)
}
