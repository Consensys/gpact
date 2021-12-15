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
	"bytes"
	"crypto/ecdsa"
	"crypto/elliptic"
	"io"
	secp256k1 "github.com/ethereum/go-ethereum/crypto/secp256k1"
)

// This file is very similar to https://github.com/filecoin-project/go-crypto/blob/master/crypto.go
// with the following exceptions:
// All variables have been prefixed with secp256k1 to namespace them
// The PRNG passed into generate key pair is the shared private PRNG.

// Having all usages of SecP256K1 go through this file means that if
// the underlying library needs to be changed, it will only need to be changed here.

// PrivateKeyBytes is the size of a serialized private key.
const secp256k1PrivateKeyBytes = 32

// PublicKeyBytes is the size of a serialized public key.
const secp256k1PublicKeyBytes = 65

// PublicKey returns the public key for this private key.
func secp256k1PublicKey(sk []byte) []byte {
	x, y := secp256k1.S256().ScalarBaseMult(sk)
	return elliptic.Marshal(secp256k1.S256(), x, y)
}

// Sign signs the given message, which must be 32 bytes long.
func secp256k1Sign(sk, msg []byte) ([]byte, error) {
	return secp256k1.Sign(msg, sk)
}

// Equals compares two private key for equality and returns true if they are the same.
func secp256k1Equals(sk, other []byte) bool {
	return bytes.Equal(sk, other)
}

// Verify checks the given signature and returns true if it is valid.
func secp256k1Verify(pk, msg, signature []byte) bool {
	if len(signature) == 65 {
		// Drop the V (1byte) in [R | S | V] style signatures.
		// The V (1byte) is the recovery bit and is not apart of the signature verification.
		return secp256k1.VerifySignature(pk[:], msg, signature[:len(signature)-1])
	}

	return secp256k1.VerifySignature(pk[:], msg, signature)
}

// GenerateKeyFromSeed generates a new key from the given reader.
func secp256k1GenerateKeyFromSeed(seed io.Reader) ([]byte, error) {
	key, err := ecdsa.GenerateKey(secp256k1.S256(), seed)
	if err != nil {
		return nil, err
	}

	privkey := make([]byte, secp256k1PrivateKeyBytes)
	blob := key.D.Bytes()

	// the length is guaranteed to be fixed, given the serialization rules for secp2561k curve points.
	copy(privkey[secp256k1PrivateKeyBytes-len(blob):], blob)

	return privkey, nil
}

// GenerateKey creates a new key using the private PRNG
func secp256k1GenerateKey() ([]byte, error) {
	prng := GetPrivatePRNG()
	return secp256k1GenerateKeyFromSeed(prng.GetReader())
}

// EcRecover recovers the public key from a message, signature pair.
func secp256k1EcRecover(msg, signature []byte) ([]byte, error) {
	return secp256k1.RecoverPubkey(msg, signature)
}
