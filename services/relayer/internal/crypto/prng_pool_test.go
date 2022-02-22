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
)

func TestPRNGEntropyKickEmptyPool(t *testing.T) {
	PRNGEntropyKick()
}

func TestPRNGEntropyKickOnlyPublic(t *testing.T) {
	GetPublicPRNG()
	PRNGEntropyKick()
}

func TestPRNGEntropyKickOnlyPrivate(t *testing.T) {
	GetPrivatePRNG()
	PRNGEntropyKick()
}

func TestPRNGEntropyKickOnlySeparate(t *testing.T) {
	securityDomain := []byte("client")
	NewPrivatePRNG(securityDomain)
	PRNGEntropyKick()
}

func TestPRNGEntropyKickTwo(t *testing.T) {
	GetPublicPRNG()
	GetPrivatePRNG()
	PRNGEntropyKick()
}

func TestPRNGEntropyKickThree(t *testing.T) {
	GetPublicPRNG()
	GetPrivatePRNG()
	securityDomain := []byte("client")
	NewPrivatePRNG(securityDomain)
	PRNGEntropyKick()
}

func TestPRNGEntropyKickFour(t *testing.T) {
	GetPublicPRNG()
	GetPrivatePRNG()
	securityDomain := []byte("client")
	NewPrivatePRNG(securityDomain)
	securityDomain2 := []byte("secrets")
	NewPrivatePRNG(securityDomain2)
	PRNGEntropyKick()
}

func TestPRNGEntropyKickMultiple(t *testing.T) {
	GetPublicPRNG()
	GetPrivatePRNG()
	PRNGEntropyKick()
	PRNGEntropyKick()
	PRNGEntropyKick()
	PRNGEntropyKick()
}
