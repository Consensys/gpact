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

// GeneratePublicRandomBytes generates zero or more random numbers
func GeneratePublicRandomBytes(b []byte) {
	GetPublicPRNG().ReadBytes(b)
}

// Single instance of the gateway
var publicPRNG Random
var doOnce sync.Once

// GetPublicPRNG returns the PRNG that should be used for generating random values
// that will become public.
func GetPublicPRNG() Random {
	doOnce.Do(func() {
		publicPRNG = NewPRNG([]byte("public"))
		prngPool = append(prngPool, publicPRNG)
	})
	return publicPRNG
}
