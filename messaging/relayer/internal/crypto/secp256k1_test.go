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
	"math/rand"
	"testing"
	"time"

	"github.com/stretchr/testify/assert"
)

func TestSecP256K1GenerateKey(t *testing.T) {
	rand.Seed(time.Now().UnixNano())

	sk, err := secp256k1GenerateKey()
	assert.NoError(t, err)

	assert.Equal(t, len(sk), 32)

	msg := make([]byte, 32)
	for i := 0; i < len(msg); i++ {
		msg[i] = byte(i)
	}

	digest, err := secp256k1Sign(sk, msg)
	assert.NoError(t, err)
	assert.Equal(t, len(digest), 65)
	pk := secp256k1PublicKey(sk)

	// valid signature
	assert.True(t, secp256k1Verify(pk, msg, digest))

	// invalid signature - different message (too short)
	assert.False(t, secp256k1Verify(pk, msg[3:], digest))

	// invalid signature - different message
	msg2 := make([]byte, 32)
	copy(msg2, msg)
	rand.Shuffle(len(msg2), func(i, j int) { msg2[i], msg2[j] = msg2[j], msg2[i] })
	assert.False(t, secp256k1Verify(pk, msg2, digest))

	// invalid signature - different digest
	digest2 := make([]byte, 65)
	copy(digest2, digest)
	rand.Shuffle(len(digest2), func(i, j int) { digest2[i], digest2[j] = digest2[j], digest2[i] })
	assert.False(t, secp256k1Verify(pk, msg, digest2))

	// invalid signature - digest too short
	assert.False(t, secp256k1Verify(pk, msg, digest[3:]))
	assert.False(t, secp256k1Verify(pk, msg, digest[:29]))

	// invalid signature - digest too long
	digest3 := make([]byte, 70)
	copy(digest3, digest)
	assert.False(t, secp256k1Verify(pk, msg, digest3))

	recovered, err := secp256k1EcRecover(msg, digest)
	assert.NoError(t, err)
	assert.Equal(t, recovered, secp256k1PublicKey(sk))
}
