package crypto

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

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestGetPublicPRNG(t *testing.T) {
	b := make([]byte, 32)
	zeroBytes := make([]byte, 32)
	pub := GetPublicPRNG()
	pub.ReadBytes(b)
	assert.NotEqual(t, b, zeroBytes, "Random bytes all zero")
}

func TestGeneratePublicRandomBytes(t *testing.T) {
	b := make([]byte, 32)
	zeroBytes := make([]byte, 32)
	GeneratePublicRandomBytes(b)
	assert.NotEqual(t, b, zeroBytes, "Random bytes all zero")
}
