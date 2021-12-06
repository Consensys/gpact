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
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestSampleReqV1(t *testing.T) {
	testText := "test-text"
	testNumber := 10
	testData := []byte{1, 2, 3, 4}

	raw := NewSampleReqV1(testText, testNumber, testData)
	assert.NotEmpty(t, raw.ID())
	assert.NotEmpty(t, raw.Timestamp())
	assert.Equal(t, Version1, raw.Version())
	assert.Equal(t, SampleReqType, raw.Type())
	assert.NotEmpty(t, raw.Payload())
	msg, ok := raw.(*SampleReqV1)
	assert.True(t, ok)
	assert.Equal(t, testText, msg.Text())
	assert.Equal(t, testNumber, msg.Number())
	assert.Equal(t, testData, msg.Data())

	// Test round trip
	data := raw.Encode()
	raw2, err := NewRawMessageFromBytes(data)
	assert.Empty(t, err)
	decoded, err := raw2.Decode()
	assert.Empty(t, err)
	msg2, ok := decoded.(*SampleReqV1)
	assert.True(t, ok)
	assert.Equal(t, testText, msg2.Text())
	assert.Equal(t, testNumber, msg2.Number())
	assert.Equal(t, testData, msg2.Data())
}
