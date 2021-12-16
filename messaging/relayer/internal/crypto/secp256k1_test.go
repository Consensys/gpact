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

	sk, err := Secp256k1GenerateKey()
	assert.NoError(t, err)

	assert.Equal(t, len(sk), 32)

	digest := make([]byte, 32)
	for i := 0; i < len(digest); i++ {
		digest[i] = byte(i)
	}

	signature, err := Secp256k1SignDigest(sk, digest)
	assert.NoError(t, err)
	assert.Equal(t, len(signature), 65)
	pk := Secp256k1PublicKey(sk)

	// valid signature
	assert.True(t, Secp256k1VerifyDigest(pk, digest, signature))

	// invalid signature - different message (too short)
	assert.False(t, Secp256k1VerifyDigest(pk, digest[3:], signature))

	// invalid signature - different message
	msg2 := make([]byte, 32)
	copy(msg2, digest)
	rand.Shuffle(len(msg2), func(i, j int) { msg2[i], msg2[j] = msg2[j], msg2[i] })
	assert.False(t, Secp256k1VerifyDigest(pk, msg2, signature))

	// invalid signature - different digest
	signature2 := make([]byte, 65)
	copy(signature2, signature)
	rand.Shuffle(len(signature2), func(i, j int) { signature2[i], signature2[j] = signature2[j], signature2[i] })
	assert.False(t, Secp256k1VerifyDigest(pk, digest, signature2))

	// invalid signature - signature too long
	signature3 := make([]byte, 70)
	copy(signature3, signature)
	assert.False(t, Secp256k1VerifyDigest(pk, digest, signature3))

	// Check that public key can be recovered from the signature.
	recovered, err := Secp256k1EcRecover(digest, signature)
	assert.NoError(t, err)
	assert.Equal(t, recovered, Secp256k1PublicKey(sk))
}
