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
)

// SampleReqV1 represents a sample request version 1 in the system.
type SampleReqV1 struct {
	Message

	text   string
	number int
	data   []byte
}

// sampleReqV1Json is used for serialization.
type sampleReqV1Json struct {
	Text   string `json:"text"`
	Number int    `json:"number"`
	Data   string `json:"data"`
}

// NewSampleReqV1 creates a new sample request version 1.
func NewSampleReqV1(text string, number int, data []byte) Message {
	// As long as there is no cyclic data structure, json marshal should not give error.
	payload, _ := json.Marshal(sampleReqV1Json{
		Text:   text,
		Number: number,
		// Note, if using json.Marshal to perform serialization, avoid using []byte, here, we convert to string
		Data: hex.EncodeToString(data),
	})
	raw := NewRawMessage(Version1, SampleReqType, payload)
	return &SampleReqV1{
		Message: raw,
		text:    text,
		number:  number,
		data:    data,
	}
}

// Decoder decodes a raw message into this type of message.
func (msg SampleReqV1) Decoder(raw Message) (Message, error) {
	// Deserialization
	msgJson := &sampleReqV1Json{}
	err := json.Unmarshal(raw.Payload(), msgJson)
	if err != nil {
		return nil, err
	}
	// Further decode fields if required.
	data, err := hex.DecodeString(msgJson.Data)
	if err != nil {
		return nil, err
	}
	return &SampleReqV1{
		Message: raw,
		text:    msgJson.Text,
		number:  msgJson.Number,
		data:    data,
	}, nil
}

// Text obtains the text of this sample request.
func (msg *SampleReqV1) Text() string {
	return msg.text
}

// Number obtains the number of this sample request.
func (msg *SampleReqV1) Number() int {
	return msg.number
}

// Data obtains the data of this sample request.
func (msg *SampleReqV1) Data() []byte {
	return msg.data
}
