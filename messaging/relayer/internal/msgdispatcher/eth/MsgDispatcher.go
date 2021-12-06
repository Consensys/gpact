/*
Package eth - Message dispatcher for Ethereum Clients.
*/
package eth

import (
	log "github.com/consensys/gpact/messaging/relayer/internal/logging"
)

/*
 * Copyright 2021 ConsenSys Software Inc.
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

// MsgDispatcher holds the context for submitting transactions
// to an Ethereum Client.
type MsgDispatcher struct {
	endpoint   string     // URL without protocol specifier of Ethereum client.
	http       bool       // HTTP or WS
	apiAuthKey string     // Authentication key to access the Ethereum API.
	keyManager KeyManager // Holds all keys for this dispatcher.
}

// MsgDispatcherConfig holds variables needed to configure the message
// dispatcher component.
type MsgDispatcherConfig struct {
	endpoint   string // URL without protocol specifier of Ethereum client.
	http       bool   // HTTP or WS
	apiAuthKey string // Authentication key to access the Ethereum API.
	keyManager KeyManager
}

// NewMsgDispatcher creates a new message dispatcher instance.
func NewMsgDispatcher(c *MsgDispatcherConfig) (*MsgDispatcher, error) {
	var m = MsgDispatcher{}
	m.endpoint = c.endpoint
	m.http = c.http
	m.apiAuthKey = c.apiAuthKey
	m.keyManager = c.keyManager

	log.Info("Message Dispatcher (Eth) for %v", c.endpoint)

	return &m, nil
}

// Connect attempts to use the configuration to connect to the end point.
func Connect() {
	// TODO
}

//
func SubmitTransaction() {
	// TODO: Params: gas limit, min gas, max gas, tip, gas price,
	// TODO If using HTTP, establish a new connection. If using WS, use existing connection.
	//
}
