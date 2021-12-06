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
	"fmt"
	"time"

	"github.com/ipfs/go-cid"
	"github.com/multiformats/go-multihash"
)

// RawMessage represents an undecoded message.
type RawMessage struct {
	id        cid.Cid
	timestamp int64
	version   string
	msgType   int64
	payload   []byte
}

// rawMessageJson is used for serialization.
type rawMessageJson struct {
	ID        string `json:"id"`
	Timestamp int64  `json:"timestamp"`
	Version   string `json:"version"`
	MsgType   int64  `json:"msg_type"`
	Payload   string `json:"payload"`
}

// NewRawMessage creates a raw message.
func NewRawMessage(version string, msgType int64, payload []byte) Message {
	now := time.Now().Unix()
	// Generate unique id
	data, _ := json.Marshal(rawMessageJson{
		Timestamp: now,
		Version:   version,
		MsgType:   msgType,
		Payload:   hex.EncodeToString(payload),
	})
	pref := cid.Prefix{
		Version:  1,
		Codec:    cid.DagProtobuf,
		MhType:   multihash.SHA2_256,
		MhLength: -1,
	}
	id, _ := pref.Sum(data)
	return &RawMessage{
		id:        id,
		timestamp: now,
		version:   version,
		msgType:   msgType,
		payload:   payload,
	}
}

// NewRawMessageFromBytes creates a raw message from bytes.
func NewRawMessageFromBytes(data []byte) (Message, error) {
	msgJson := rawMessageJson{}
	err := json.Unmarshal(data, &msgJson)
	if err != nil {
		return nil, err
	}
	payload, err := hex.DecodeString(msgJson.Payload)
	if err != nil {
		return nil, err
	}
	// Check if ID matches.
	idStr := msgJson.ID
	msgJson.ID = ""
	data, _ = json.Marshal(msgJson)
	pref := cid.Prefix{
		Version:  1,
		Codec:    cid.DagProtobuf,
		MhType:   multihash.SHA2_256,
		MhLength: -1,
	}
	id, _ := pref.Sum(data)
	if id.String() != idStr {
		return nil, fmt.Errorf("message id mismatch, expect %v got %v", id.String(), idStr)
	}
	return &RawMessage{
		id:        id,
		timestamp: msgJson.Timestamp,
		version:   msgJson.Version,
		msgType:   msgJson.MsgType,
		payload:   payload,
	}, nil
}

// ID is used to obtain the ID of the message.
func (msg *RawMessage) ID() cid.Cid {
	return msg.id
}

// Timestamp is used to obtain the timestamp of the message.
func (msg *RawMessage) Timestamp() int64 {
	return msg.timestamp
}

// Version is used to obtain the version of the message.
func (msg *RawMessage) Version() string {
	return msg.version
}

// Type is used to obtain the message type.
func (msg *RawMessage) Type() int64 {
	return msg.msgType
}

// Payload is used to obtain the message payload.
func (msg *RawMessage) Payload() []byte {
	return msg.payload
}

// Encode is used to encode the message.
func (msg *RawMessage) Encode() []byte {
	data, _ := json.Marshal(rawMessageJson{
		ID:        msg.id.String(),
		Timestamp: msg.timestamp,
		Version:   msg.version,
		MsgType:   msg.msgType,
		Payload:   hex.EncodeToString(msg.payload),
	})
	return data
}

// Decode is used to decode the message.
func (msg *RawMessage) Decode() (Message, error) {
	decoders, ok := registeredDecoder[msg.version]
	if !ok {
		return nil, fmt.Errorf("message version %v is not supported", msg.version)
	}
	decoder, ok := decoders[msg.msgType]
	if !ok {
		return nil, fmt.Errorf("message type %v is not supported", msg.msgType)
	}
	return decoder(msg)
}
