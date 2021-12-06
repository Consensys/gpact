package messages

/*
 * Copyright 2019 ConsenSys Software Inc
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
	"encoding/hex"
	"encoding/json"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestRawMessage(t *testing.T) {
	testVersion := "0.0.1"
	testType := int64(1)
	testPayload := []byte{1, 2, 3, 4}

	raw := NewRawMessage(testVersion, testType, testPayload)
	assert.NotEmpty(t, raw.ID())
	assert.NotEmpty(t, raw.Timestamp())
	assert.Equal(t, testVersion, raw.Version())
	assert.Equal(t, testType, raw.Type())
	assert.Equal(t, testPayload, raw.Payload())

	// Test round trip
	data := raw.Encode()
	raw2, err := NewRawMessageFromBytes(data)
	assert.Empty(t, err)
	assert.Equal(t, raw.ID(), raw2.ID())

	// Test failure
	_, err = NewRawMessageFromBytes([]byte{1, 2, 3, 4})
	assert.NotEmpty(t, err)

	data, _ = json.Marshal(rawMessageJson{
		ID:        raw.ID().String(),
		Timestamp: raw.Timestamp(),
		Version:   raw.Version(),
		MsgType:   raw.Type(),
		Payload:   "zz",
	})
	_, err = NewRawMessageFromBytes(data)
	assert.NotEmpty(t, err)

	data, _ = json.Marshal(rawMessageJson{
		ID:        raw.ID().String(),
		Timestamp: raw.Timestamp(),
		Version:   raw.Version(),
		MsgType:   raw.Type() + 1,
		Payload:   hex.EncodeToString(raw.Payload()),
	})
	_, err = NewRawMessageFromBytes(data)
	assert.NotEmpty(t, err)
}
