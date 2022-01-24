package signer

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
	"crypto/ecdsa"
	"fmt"
	"math/big"

	"github.com/consensys/gpact/messaging/relayer/internal/crypto"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/crypto/secp256k1"
)

// Signer is an interface for a message signer.
type Signer interface {
	// Start starts the signer's routine.
	Start() error

	// Stop safely stops the signer.
	Stop()

	// SetKey sets a signing key for given chain and a given contract addr.
	SetKey(chainID *big.Int, contractAddr common.Address, keyType byte, key []byte) error

	// GetAddr gets the associated address for a signing key linked with given chain and given contract addr.
	GetAddr(chainID *big.Int, contractAddr common.Address) (byte, common.Address, error)

	// Sign signs given message with internal stored key correspdoing to given chainID and given contract addr.
	Sign(chainID *big.Int, contractAddr common.Address, msg []byte) (byte, []byte, error)
}

const (
	SECP256K1_KEY_TYPE      = byte(1)
	SECP256K1_KEY_TYPE_REPR = "secp256k1"
)

var supportedKeyTypes map[byte]string

func init() {
	supportedKeyTypes = make(map[byte]string)
	supportedKeyTypes[SECP256K1_KEY_TYPE] = SECP256K1_KEY_TYPE_REPR
}

// TypeToString gets the correspoding string repr of key type.
func TypeToString(keyType byte) string {
	return supportedKeyTypes[keyType]
}

// keyToAddr converts key to address.
func keyToAddr(keyType byte, key []byte) (common.Address, error) {
	_, ok := supportedKeyTypes[keyType]
	if !ok {
		return common.Address{}, fmt.Errorf("key type %v not supported", keyType)
	}
	switch keyType {
	case SECP256K1_KEY_TYPE:
		x, y := secp256k1.S256().ScalarBaseMult(key)
		prv := &ecdsa.PrivateKey{}
		prv.D = big.NewInt(0).SetBytes(key)
		prv.PublicKey = ecdsa.PublicKey{
			X:     x,
			Y:     y,
			Curve: secp256k1.S256(),
		}
		auth := bind.NewKeyedTransactor(prv)
		return auth.From, nil
	}
	return common.Address{}, fmt.Errorf("key type %v does not have a decrypter", keyType)
}

// sign signs a message with given key.
func sign(keyType byte, key []byte, msg []byte) ([]byte, error) {
	_, ok := supportedKeyTypes[keyType]
	if !ok {
		return nil, fmt.Errorf("key type %v not supported", keyType)
	}
	switch keyType {
	case SECP256K1_KEY_TYPE:
		return crypto.Secp256k1Sign(key, msg)
	}
	return nil, fmt.Errorf("key type %v does not have a signer", keyType)
}
