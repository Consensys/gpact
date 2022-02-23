package crypto

/*
 * Copyright 2022 ConsenSys Software Inc.
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
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestGetPrivatePRNG(t *testing.T) {
	b := make([]byte, 32)
	zeroBytes := make([]byte, 32)
	rand := GetPrivatePRNG()
	rand.ReadBytes(b)
	assert.NotEqual(t, b, zeroBytes, "Random bytes all zero")
}

func TestGeneratePrivateRandomBytes(t *testing.T) {
	b := make([]byte, 32)
	zeroBytes := make([]byte, 32)
	GeneratePrivateRandomBytes(b)
	assert.NotEqual(t, b, zeroBytes, "Random bytes all zero")
}

func TestNewPrivatePRNG(t *testing.T) {
	b := make([]byte, 32)
	zeroBytes := make([]byte, 32)
	securityDomain := []byte("fc-retrieval-client-keys")
	rand := NewPrivatePRNG(securityDomain)
	rand.ReadBytes(b)
	assert.NotEqual(t, b, zeroBytes, "Random bytes all zero")
}
