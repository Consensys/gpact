package node

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
	"sync"

	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/transactor"
	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/verifier"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
)

// Node represents the node core.
type Node struct {
	MQ *mqserver.MQServer

	RPC rpc.Server

	Transactor transactor.Transactor

	Verifier verifier.Verifier
}

// Single instance of the gateway
var instance *Node
var doOnce sync.Once

// GetSingleInstance returns the single instance of the node
func GetSingleInstance() *Node {
	doOnce.Do(func() {
		instance = &Node{}
	})
	return instance
}
