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
	"hash"

	"golang.org/x/crypto/sha3"
)

// Keccak256 calculates the keccak256 hash of given bytes
func Keccak256(data ...[]byte) []byte {
	digestImpl := sha3.NewLegacyKeccak256()
	for _, b := range data {
		digestImpl.Write(b)
	}
	return digestImpl.Sum(nil)
}

// The PRNG to be used as part of the PRF in the PRNG. Any message digest algorithm that
// has a security strength stronger than the PRNG can be used.
func getPRNGHasher() hash.Hash {
	return sha3.NewLegacyKeccak256()
}

// The size of the message digest hash in bytes.
func getPRNGHasherDigestSize() int {
	return 32
}
