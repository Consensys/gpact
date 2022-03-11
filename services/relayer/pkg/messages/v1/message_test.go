package v1

/*
 * Copyright 2022 ConsenSys Software Inc
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

	"github.com/consensys/gpact/services/relayer/internal/messages"
	"github.com/stretchr/testify/assert"
)

func TestMessageRoundTrip(t *testing.T) {
	msg := &Message{
		ID:        "test_id",
		Timestamp: 100,
		Destination: ApplicationAddress{
			NetworkID:       "test_dest_net_id",
			ContractAddress: "test_dest_contract_addr",
		},
		Source: ApplicationAddress{
			NetworkID:       "test_src_net_id",
			ContractAddress: "test_src_contract_addr",
		},
		Proofs: []Proof{
			{
				ProofType: "test_proof_type_1",
				Created:   101,
				Proof:     "test_proof_1",
			},
			{
				ProofType: "test_proof_type_2",
				Created:   102,
				Proof:     "test_proof_2",
			},
		},
		Payload: "test_payload",
	}
	data := msg.ToBytes()
	msg2, err := messages.DecodeMessage(Version, MessageType, data)
	assert.Empty(t, err)
	assert.Equal(t, msg, msg2)
}
