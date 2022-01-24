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
	"encoding/hex"
	"math/big"
	"os"
	"testing"

	"github.com/ethereum/go-ethereum/common"
	"github.com/stretchr/testify/assert"
)

const (
	testDS            = "./test"
	testKey           = "45ded31614da011e7d905a8afa7d78f464cedbe31c8699b23e0a2a1906c29a7c"
	testContractAddr1 = "0x15c941024D2a7B9b6603459d0B74aA7B4f0C6bDd"
	testContractAddr2 = "0x15c941024D2a7B9b6603459d0B74aA7B4f0C6bDe"
)

func TestMain(m *testing.M) {
	os.RemoveAll(testDS)
	os.Mkdir(testDS, os.ModePerm)
	defer os.RemoveAll(testDS)
	m.Run()
}

func TestNewSignerImplV1(t *testing.T) {
	signer := NewSignerImplV1(testDS)
	err := signer.Start()
	assert.Empty(t, err)
	defer signer.Stop()
}

func TestSetKey(t *testing.T) {
	signer := NewSignerImplV1(testDS)
	err := signer.Start()
	assert.Empty(t, err)
	defer signer.Stop()

	key, _ := hex.DecodeString(testKey)
	err = signer.SetKey(big.NewInt(1), common.HexToAddress(testContractAddr1), SECP256K1_KEY_TYPE+1, key)
	assert.NotEmpty(t, err)

	err = signer.SetKey(big.NewInt(1), common.HexToAddress(testContractAddr1), SECP256K1_KEY_TYPE, key)
	assert.Empty(t, err)
}

func TestGetAddr(t *testing.T) {
	signer := NewSignerImplV1(testDS)
	err := signer.Start()
	assert.Empty(t, err)
	defer signer.Stop()

	_, _, err = signer.GetAddr(big.NewInt(2), common.HexToAddress(testContractAddr2))
	assert.NotEmpty(t, err)

	_, _, err = signer.GetAddr(big.NewInt(1), common.HexToAddress(testContractAddr2))
	assert.NotEmpty(t, err)

	_, _, err = signer.GetAddr(big.NewInt(2), common.HexToAddress(testContractAddr1))
	assert.NotEmpty(t, err)

	keyType, addr, err := signer.GetAddr(big.NewInt(1), common.HexToAddress(testContractAddr1))
	assert.Empty(t, err)
	assert.Equal(t, SECP256K1_KEY_TYPE, keyType)
	assert.Equal(t, "0xF0421797664F4132676Acb857c9fdadaD6AB4dAD", addr.String())
}

func TestSign(t *testing.T) {
	signer := NewSignerImplV1(testDS)
	err := signer.Start()
	assert.Empty(t, err)
	defer signer.Stop()

	_, _, err = signer.Sign(big.NewInt(2), common.HexToAddress(testContractAddr2), []byte{1})
	assert.NotEmpty(t, err)

	_, _, err = signer.Sign(big.NewInt(1), common.HexToAddress(testContractAddr2), []byte{1})
	assert.NotEmpty(t, err)

	_, _, err = signer.Sign(big.NewInt(2), common.HexToAddress(testContractAddr1), []byte{1})
	assert.NotEmpty(t, err)

	keyType, sig, err := signer.Sign(big.NewInt(1), common.HexToAddress(testContractAddr1), []byte{1})
	assert.Empty(t, err)
	assert.Equal(t, SECP256K1_KEY_TYPE, keyType)
	assert.Equal(t, "983686ec1cd3a88f33278a74a12dccf4d7ba33ad3e8e42f858656fbcb07e9051303998d6cb84a4051646c441eb4b2cc4bbaf29f5afdeb3afeb4bf3900ca8615e00", hex.EncodeToString(sig))
}
