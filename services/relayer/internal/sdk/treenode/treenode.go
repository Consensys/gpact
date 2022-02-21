package treenode

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
	"encoding/binary"
	"fmt"
	"math/big"

	"github.com/ethereum/go-ethereum/common"
)

const (
	NUM_FUNCS_CALLED_SIZE = 1
	OFFSET_SIZE           = 4
	BLOCKCHAIN_ID_SIZE    = 32
	ADDRESS_SIZE          = 20
	DATA_LEN_SIZE_SIZE    = 2
	LEAF_FUNCTION         = 0
	MAX_CALL_EX_TREE_SIZE = 1000000
	MAX_CALLED_FUNCTIONS  = 255
)

// TreeNode is a node in the execution tree.
// It represents a function execution at a specific contract in a specific chain.
type TreeNode struct {
	// chainID is the ID of the blockchain where the execution happens.
	chainID *big.Int

	// contractAddr is the address of the contract where this execution happens.
	contractAddr common.Address

	// callData is an abi Pack of (function selector) and parameters.
	callData []byte

	// children is a list of executions this execution depends on.
	children []*TreeNode
}

// NewTreeNode creates a new tree node with empty children.
func NewTreeNode(chainID *big.Int, contractAddr common.Address, callData []byte) (*TreeNode, error) {
	if len(callData) > MAX_CALL_EX_TREE_SIZE {
		return nil, fmt.Errorf("calldata exceed max size")
	}
	return &TreeNode{
		chainID:      chainID,
		contractAddr: contractAddr,
		callData:     callData,
		children:     make([]*TreeNode, 0),
	}, nil
}

// ChainID gets the chain id of the tree node.
func (n *TreeNode) ChainID() *big.Int {
	return n.chainID
}

// ContractAddr gets the contract addr of the tree node.
func (n *TreeNode) ContractAddr() common.Address {
	return n.contractAddr
}

// CallData gets the call data of the tree node.
func (n *TreeNode) CallData() []byte {
	return n.callData
}

// Children gets a list of children nodes.
func (n *TreeNode) Children() []*TreeNode {
	return n.children
}

// AddChild adds a child to this node.
// Error occurs if the child has same chain id as this node.
// TODO:
// 		Do we need to check if the whole tree does not have two executions having access to the same lockable contract?
// 		We can have Lock & RLock in the lockable storage and so only multiple reads if permitted.
func (n *TreeNode) AddChild(child *TreeNode) error {
	if child.chainID.Cmp(n.chainID) == 0 {
		return fmt.Errorf("child has the same chain id as parent.")
	} else if len(n.children) == MAX_CALLED_FUNCTIONS {
		return fmt.Errorf("max children reached.")
	}
	n.children = append(n.children, child)
	return nil
}

// Encode encodes this tree node to bytes.
func (n *TreeNode) Encode() []byte {
	data := make([]byte, 0)
	numFunctions := len(n.children)
	data = append(data, byte(numFunctions))
	// Encoded data of this node.
	encoded := make([]byte, BLOCKCHAIN_ID_SIZE)
	n.chainID.FillBytes(encoded)
	encoded = append(encoded, n.contractAddr.Bytes()...)
	tmp := make([]byte, DATA_LEN_SIZE_SIZE)
	binary.BigEndian.PutUint16(tmp, uint16(len(n.callData)))
	encoded = append(encoded, tmp...)
	encoded = append(encoded, n.callData...)
	if data[0] == 0 {
		// Leaf node.
		data = append(data, encoded...)
	} else {
		// Non-leaf node.
		encodedChildren := make([][]byte, 0)
		encodedChildren = append(encodedChildren, encoded)
		for _, child := range n.children {
			encodedChildren = append(encodedChildren, child.Encode())
		}
		// Add offsets
		offset := (numFunctions+1)*OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE
		for _, encodedChild := range encodedChildren {
			tmp = make([]byte, OFFSET_SIZE)
			binary.BigEndian.PutUint32(tmp, uint32(offset))
			data = append(data, tmp...)
			offset += len(encodedChild)
		}
		for _, encodedChild := range encodedChildren {
			data = append(data, encodedChild...)
		}
	}
	return data
}
