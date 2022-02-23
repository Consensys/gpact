package observer

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
	"encoding/hex"
	"encoding/json"
	"errors"
	"fmt"
	"time"

	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/ethereum/go-ethereum/core/types"

	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
)

type EventTransformer interface {
	// ToMessage converts a given event to a relayer message
	ToMessage(event interface{}) (*v1.Message, error)
}

// SFCEventTransformer converts events from a simple-function-call bridge contract to relayer messages
type SFCEventTransformer struct {
	Source     string
	SourceAddr string
}

// ToMessage converts a 'CrossCall' event emited from a simple-function-call bridge contract to relayer message
// It returns an error if the event contains an invalid timestamp or destination information
// It panics if the event is not of an sfc.SfcCrossCall type
func (t *SFCEventTransformer) ToMessage(event interface{}) (*v1.Message, error) {
	sfcEvent := event.(*functioncall.SfcCrossCall)

	if err := t.validate(sfcEvent); err != nil {
		return nil, err
	}

	source := v1.ApplicationAddress{NetworkID: t.Source, ContractAddress: t.SourceAddr}
	destination := v1.ApplicationAddress{NetworkID: sfcEvent.DestBcId.String(), ContractAddress: sfcEvent.DestContract.String()}

	data, err := json.Marshal(sfcEvent.Raw)
	if err != nil {
		return nil, err
	}

	message := v1.Message{
		ID:          t.getIDForEvent(sfcEvent.Raw),
		Timestamp:   sfcEvent.Timestamp.Int64(),
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: destination,
		Source:      source,
		Proofs:      []v1.Proof{},
		Payload:     hex.EncodeToString(data),
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

// getIDForEvent generates a deterministic ID for an event of the format {network_id}/{contract_address}/{block_number}/{tx_index}/{log_index}
func (t *SFCEventTransformer) getIDForEvent(event types.Log) string {
	return fmt.Sprintf("%s-%s-%d-%d-%d", t.Source, t.SourceAddr, event.BlockNumber, event.TxIndex, event.Index)
}

func NewSFCEventTransformer(sourceNetwork string, sourceAddr string) *SFCEventTransformer {
	return &SFCEventTransformer{sourceNetwork, sourceAddr}
}

type GPACTEventTransformer struct {
	Source     string
	SourceAddr string
}

func (t *GPACTEventTransformer) ToMessage(event interface{}) (*v1.Message, error) {
	var raw types.Log
	startEvent, ok := event.(*functioncall.GpactStart)
	if ok {
		raw = startEvent.Raw
	} else {
		segmentEvent, ok := event.(*functioncall.GpactSegment)
		if ok {
			raw = segmentEvent.Raw
		} else {
			rootEvent, ok := event.(*functioncall.GpactRoot)
			if ok {
				raw = rootEvent.Raw
			} else {
				return nil, fmt.Errorf("event not supported")
			}
		}
	}

	source := v1.ApplicationAddress{NetworkID: t.Source, ContractAddress: t.SourceAddr}
	destination := v1.ApplicationAddress{NetworkID: "0", ContractAddress: ""}

	data, err := json.Marshal(raw)
	if err != nil {
		return nil, err
	}

	message := v1.Message{
		ID:          t.getIDForEvent(raw),
		Timestamp:   time.Now().Unix(), //Event does not contain timestamp
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: destination,
		Source:      source,
		Proofs:      []v1.Proof{},
		Payload:     hex.EncodeToString(data),
	}

	return &message, nil
}

// getIDForEvent generates a deterministic ID for an event of the format {network_id}/{contract_address}/{block_number}/{tx_index}/{log_index}
func (t *GPACTEventTransformer) getIDForEvent(event types.Log) string {
	return fmt.Sprintf("%s-%s-%d-%d-%d", t.Source, t.SourceAddr, event.BlockNumber, event.TxIndex, event.Index)
}

func NewGPACTEventTransformer(sourceNetwork string, sourceAddr string) *GPACTEventTransformer {
	return &GPACTEventTransformer{sourceNetwork, sourceAddr}
}
