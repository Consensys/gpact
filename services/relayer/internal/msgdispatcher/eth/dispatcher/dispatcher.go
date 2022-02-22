package dispatcher

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

	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
)

// Dispatcher is the interface to a componenet to dispatch requests from threads.
type Dispatcher interface {
	// Start starts the Dispatcher's routine.
	Start() error

	// Stop stops the dispatcher.
	Stop()

	// AddToQueue adds a message to the queue for process.
	AddToQueue(link string, auth *bind.TransactOpts, id string, destID *big.Int, esAddr common.Address, srcID *big.Int, srcAddr common.Address, rawData []byte, signature []byte)
}
