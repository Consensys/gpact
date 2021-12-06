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
	"sync"
)

// This file contains the Pseudo Random Number Generator (PRNG) to be used for generating
// public values.

// GeneratePrivateRandomBytes generates zero or more random numbers using the Private
// PRNG instance. These bytes should never be made public. Users of this function
// should consider creating a separate PRNG using GetNewPrivatePRNG if security
// domain separation is required.
func GeneratePrivateRandomBytes(b []byte) {
	GetPrivatePRNG().ReadBytes(b)
}

// Single instance of the gateway
var privatePRNG Random
var doOnce1 sync.Once

// GetPrivatePRNG returns the PRNG that should be used for generating random values
// that will stay private.
func GetPrivatePRNG() Random {
	doOnce1.Do(func() {
		privatePRNG = NewPRNG([]byte("private"))
		prngPool = append(prngPool, privatePRNG)
	})
	return privatePRNG
}

// NewPrivatePRNG returns the PRNG that should be used for generating random values
// that will stay private. Creating a separate PRNG for each set of usages helps to
// guarentee security domain separation.
func NewPrivatePRNG(securityDomain []byte) Random {
	aPRNG := NewPRNG(securityDomain)
	prngPool = append(prngPool, aPRNG)
	return aPRNG
}
