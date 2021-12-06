/*
Package eth - Message dispatcher for Ethereum Clients.
*/
package eth

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

// import (
// 	"github.com/ConsenSys/gpact/messaging/relayer/internal/crypto"
// )

// KeyManager holds one or more Ethereum keys, or manages access to the keys.
// TODO: For the moment, just use one key pair.
type KeyManager struct {
	//	keyPair *crypto.KeyPairSecp256k1
}

// NewKeyManager creates a new key manager.
func NewKeyManager() (*KeyManager, error) {
	var k = KeyManager{}

	return &k, nil
}

// AddKey adds a key that can be used by the key manager.
// func (k *KeyManager) AddKey(keyPair *crypto.KeyPairSecp256k1) {
// 	k.keyPair = keyPair
// }
