package eth

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
	"encoding/hex"
	"errors"
	"fmt"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
)

type EventTransformer interface {
	// ToMessage converts a given event to a relayer message
	ToMessage(event interface{}) (*v1.Message, error)
}

// SFCEventTransformer converts events from a simple-function-call bridge contract to relayer messages
type SFCEventTransformer struct {
	Source string
}

// ToMessage converts a 'CrossCall' event emited from a simple-function-call bridge contract to relayer message
// It returns an error if the event contains an invalid timestamp or destination information
// It panics if the event is not of an sfc.SfcCrossCall type
func (t *SFCEventTransformer) ToMessage(event interface{}) (*v1.Message, error) {
	sfcEvent := event.(*functioncall.SfcCrossCall)

	if err := t.validate(sfcEvent); err != nil {
		return nil, err
	}

	source := v1.ApplicationAddress{NetworkID: t.Source, ContractAddress: sfcEvent.DestContract.String()}
	destination := v1.ApplicationAddress{NetworkID: sfcEvent.DestBcId.String(), ContractAddress: sfcEvent.DestContract.String()}

	message := v1.Message{
		ID:          hex.EncodeToString(randomBytes(16)), // TODO: replace with a proper message id scheme
		Timestamp:   sfcEvent.Timestamp.Int64(),
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: destination,
		Source:      source,
		Payload:     toBase64String(sfcEvent.DestFunctionCall),
	}

	return &message, nil
}

func (t *SFCEventTransformer) validate(event *functioncall.SfcCrossCall) error {
	if event.DestBcId == nil {
		return errors.New("destination network id cannot be empty")
	}

	if event.Timestamp == nil || event.Timestamp.Int64() <= 0 {
		return fmt.Errorf("invalid timestamp '%s'", event.Timestamp)
	}

	return nil
}

func NewSFCEventTransformer(sourceNetwork string) *SFCEventTransformer {
	return &SFCEventTransformer{sourceNetwork}
}
