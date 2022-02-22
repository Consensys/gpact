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
	"bytes"
	"crypto/ecdsa"
	"crypto/elliptic"
	"io"

	//	secp256k1 "github.com/ipsn/go-secp256k1"
	secp256k1 "github.com/ethereum/go-ethereum/crypto/secp256k1"
)

// This file is very similar to https://github.com/filecoin-project/go-crypto/blob/master/crypto.go
// with the following exceptions:
// All variables have been prefixed with secp256k1 to namespace them
// The PRNG passed into generate key pair is the shared private PRNG.

// Having all usages of SecP256K1 go through this file means that if
// the underlying library needs to be changed, it will only need to be changed here.

// Secp256k1PrivateKeyBytes is the size of a serialized private key.
const Secp256k1PrivateKeyBytes = 32

// Secp256k1PublicKeyBytes is the size of a serialized public key.
const Secp256k1PublicKeyBytes = 65

// Secp256k1PublicKey returns the public key for this private key.
func Secp256k1PublicKey(sk []byte) []byte {
	x, y := secp256k1.S256().ScalarBaseMult(sk)
	return elliptic.Marshal(secp256k1.S256(), x, y)
}

// Secp256k1Sign signs some data and returns the signature.
func Secp256k1Sign(sk, toBeSigned []byte) ([]byte, error) {
	digest := Keccak256(toBeSigned)
	return Secp256k1SignDigest(sk, digest)
}

// Secp256k1SignDigest signs the given message, which must be 32 bytes long.
func Secp256k1SignDigest(sk, msg []byte) ([]byte, error) {
	return secp256k1.Sign(msg, sk)
}

// Secp256k1Equals compares two private key for equality and returns true if they are the same.
func Secp256k1Equals(sk, other []byte) bool {
	return bytes.Equal(sk, other)
}

// Secp256k1Verify checks the given signature and returns true if it is valid.
func Secp256k1Verify(pk, toBeVerified, signature []byte) bool {
	digest := Keccak256(toBeVerified)
	return Secp256k1VerifyDigest(pk, digest, signature)
}

// Secp256k1VerifyDigest checks the given signature and returns true if it is valid.
func Secp256k1VerifyDigest(pk, digest, signature []byte) bool {
	if len(signature) == 65 {
		// Drop the V (1byte) in [R | S | V] style signatures.
		// The V (1byte) is the recovery bit and is not apart of the signature verification.
		return secp256k1.VerifySignature(pk[:], digest, signature[:len(signature)-1])
	}

	return secp256k1.VerifySignature(pk[:], digest, signature)
}

// Secp256k1GenerateKeyFromSeed generates a new key from the given reader.
func Secp256k1GenerateKeyFromSeed(seed io.Reader) ([]byte, error) {
	key, err := ecdsa.GenerateKey(secp256k1.S256(), seed)
	if err != nil {
		return nil, err
	}

	privkey := make([]byte, Secp256k1PrivateKeyBytes)
	blob := key.D.Bytes()

	// the length is guaranteed to be fixed, given the serialization rules for secp2561k curve points.
	copy(privkey[Secp256k1PrivateKeyBytes-len(blob):], blob)

	return privkey, nil
}

// Secp256k1GenerateKey creates a new key using the private PRNG
func Secp256k1GenerateKey() ([]byte, error) {
	prng := GetPrivatePRNG()
	return Secp256k1GenerateKeyFromSeed(prng.GetReader())
}

// Secp256k1EcRecover recovers the public key from a message, signature pair.
func Secp256k1EcRecover(msg, signature []byte) ([]byte, error) {
	return secp256k1.RecoverPubkey(msg, signature)
}
