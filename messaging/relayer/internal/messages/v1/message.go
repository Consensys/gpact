package v1

/*
 * Copyright 2021 ConsenSys Software Inc
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
	"encoding/json"
	"fmt"

	"github.com/consensys/gpact/messaging/relayer/internal/messages"
)

const (
	Version     = "0.0.1"
	MessageType = "message_version_0_0_1"
)

// Message implements message interface.
type Message struct {
	// ID of the message
	ID string `json:"id"`

	// Timestamp of the message
	Timestamp int64 `json:"timestamp"`

	// Message type
	MsgType string `json:"msg_type"`

	// Message version
	Version string `json:"version"`

	// Destination info
	Destination ApplicationAddressV1 `json:"destination"`

	// Source info
	Source ApplicationAddressV1 `json:"source"`

	// All the proofs
	Proofs []ProofV1 `json:"proofs"`

	// The payload
	Payload string `json:"payload"`
}

// ToBytes is used for serialization.
func (msg *Message) ToBytes() []byte {
	// Enforce the msg type and version to be the correct.
	msg.MsgType = MessageType
	msg.Version = Version
	data, err := json.Marshal(msg)
	if err != nil {
		// It shouldn't have error.
		panic(err)
	}
	return data
}

// FromBytes is used for de-serialization.
func (msg *Message) FromBytes(data []byte) (messages.Message, error) {
	maybeMsg := Message{}
	err := json.Unmarshal(data, &maybeMsg)
	if err != nil {
		return nil, err
	}
	if maybeMsg.MsgType != MessageType {
		return nil, fmt.Errorf("message type mismatch, expect %v got %v", MessageType, maybeMsg.MsgType)
	}
	if maybeMsg.Version != Version {
		return nil, fmt.Errorf("message version mismatch, expect %v got %v", Version, maybeMsg.Version)
	}
	return &maybeMsg, nil
}

// ApplicationAddressV1 is the version 1 application address info.
type ApplicationAddressV1 struct {
	NetworkID       string `json:"network_id"`
	ContractAddress string `json:"contract_address"`
}

// ProofV1 is the version 1 proof struct.
type ProofV1 struct {
	ProofType string `json:"proof_type"`
	Created   int64  `json:"created"`
	Proof     string `json:"proof"`
}

// init is used to register the decoder.
func init() {
	messages.RegisterDecoder(Version, MessageType, (&Message{}).FromBytes)
}
