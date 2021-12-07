package messages

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

import "fmt"

// Message is an interface for all the intra relayer messages in the system.
// Every message must implement -
//	ToBytes: Serialization function.
//	FromBytes: De-serialization function.
type Message interface {
	// ToBytes is used for serialization.
	ToBytes() []byte

	// FromBytes is used for de-serialization.
	FromBytes(data []byte) (Message, error)
}

// Global variable storing all message decoders.
var registeredDecoders map[string]map[string]func([]byte) (Message, error)

// init is used to initialise the package.
func init() {
	registeredDecoders = make(map[string]map[string]func([]byte) (Message, error))
	// Register supported message versions
	registeredDecoders[Version1] = make(map[string]func([]byte) (Message, error))
	// Register supported message decoders
	registeredDecoders[Version1][MessageV1Type] = (&MessageV1{}).FromBytes
}

// DecodeMessage is used to decode message based on version and message type.
func DecodeMessage(version string, msgType string, data []byte) (Message, error) {
	decoders, ok := registeredDecoders[version]
	if !ok {
		return nil, fmt.Errorf("message version %v is not supported", version)
	}
	decoder, ok := decoders[msgType]
	if !ok {
		return nil, fmt.Errorf("message type %v is not supported", msgType)
	}
	return decoder(data)
}
