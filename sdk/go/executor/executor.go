package executor

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
	"math/big"

	"github.com/ConsenSys/gpact/sdk/go/treenode"
	"github.com/ethereum/go-ethereum/common"
)

// Executor is used to execute crosschain transactions.
type Executor interface {
	// RegisterGPACT registers a gpact contract to a given chain id.
	RegisterGPACT(chainID *big.Int, gpactAddr common.Address)

	// CrosschainCall executes a crosschain transaction from the root node of an execution tree.
	CrosschainCall(root *treenode.TreeNode) ([]interface{}, error)
}
