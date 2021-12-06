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
	"github.com/ipfs/go-cid"
)

// Message represents a message in the system.
// There are three types of messages in the system:
// 	1. Messages from message observer to relayer.
// 	2. Messages between relayers.
// 	3. Messages from relayer to message dispatcher.
// Every message has -
// 	ID: The unique id of the message, represented using the content ID.
//	Timestamp: The time at which this message is created.
//	Version: The version of the message.
//	MsgType: The exact type of the message.
//	Payload: The payload of the message, that can be further decoded according to message type.
type Message interface {
	// ID is used to obtain the ID of the message.
	ID() cid.Cid

	// Timestamp is used to obtain the timestamp of the message.
	Timestamp() int64

	// Version is used to obtain the version of the message.
	Version() string

	// Type is used to obtain the message type.
	Type() int64

	// Payload is used to obtain the message payload.
	Payload() []byte

	// Encode is used to encode the message.
	Encode() []byte

	// Decode is used to decode the message.
	Decode() (Message, error)
}
