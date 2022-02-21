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
	"fmt"
	"math/big"

	"github.com/consensys/gpact/messaging/relayer/internal/sdk/chainap"
	"github.com/consensys/gpact/messaging/relayer/internal/sdk/treenode"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/common"
)

// SimulatorImplV1 implements the Simulator.
type SimulatorImplV1 struct {
	cmgr  chainap.ChainAPManager
	abis  map[string]*abi.ABI
	links map[string]map[string]Link
}

// NewSimulatorImplV1 creates a new simulator impl v1.
func NewSimulatorImplV1(cmgr chainap.ChainAPManager) *SimulatorImplV1 {
	return &SimulatorImplV1{
		cmgr:  cmgr,
		abis:  make(map[string]*abi.ABI),
		links: make(map[string]map[string]Link),
	}
}

// RegisterABI registers a contract abi.
func (sim *SimulatorImplV1) RegisterABI(contractType string, contractABI *abi.ABI) {
	sim.abis[contractType] = contractABI
}

// RegisterCallLink registers a call link.
func (sim *SimulatorImplV1) RegisterCallLink(contractType string, method string, link Link) {
	_, ok := sim.links[contractType]
	if !ok {
		sim.links[contractType] = make(map[string]Link)
	}
	sim.links[contractType][method] = link
}

// Simulate simulates a crosschain function call, builds a call execution tree and returns the root of the tree.
func (sim *SimulatorImplV1) Simulate(chainID *big.Int, contractType string, contractAddr common.Address, method string, params ...interface{}) (*treenode.TreeNode, error) {
	_, ok := sim.links[contractType]
	if !ok {
		return nil, nil
	}
	link, ok := sim.links[contractType][method]
	if !ok {
		return nil, nil
	}
	contractABI, ok := sim.abis[contractType]
	if !ok {
		return nil, fmt.Errorf("unsupported contract type")
	}
	callData, err := contractABI.Pack(method, params...)
	if err != nil {
		return nil, err
	}
	head, err := treenode.NewTreeNode(chainID, contractAddr, callData)
	if err != nil {
		return nil, err
	}
	crosschainCalls, err := link(Resource{sim.cmgr}, chainID, contractAddr, params...)
	if err != nil {
		return nil, err
	}
	for i := 0; i < len(crosschainCalls); i++ {
		subChainID := crosschainCalls[i].ChainID
		subContractType := crosschainCalls[i].ContractType
		subContractAddr := crosschainCalls[i].ContractAddr
		subMethod := crosschainCalls[i].Method
		subParams := crosschainCalls[i].Params
		child, err := sim.Simulate(subChainID, subContractType, subContractAddr, subMethod, subParams...)
		if err != nil {
			return nil, err
		}
		if child == nil {
			subABI, ok := sim.abis[subContractType]
			if !ok {
				return nil, fmt.Errorf("unsupported contract type")
			}
			subCallData, err := subABI.Pack(subMethod, subParams...)
			if err != nil {
				return nil, err
			}
			child, err = treenode.NewTreeNode(subChainID, subContractAddr, subCallData)
			if err != nil {
				return nil, err
			}
		}
		head.AddChild(child)
	}
	return head, nil
}
