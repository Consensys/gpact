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

// Function sigs.
var sfcFuncSig = [32]byte{89, 247, 54, 220, 94, 21, 196, 177, 37, 38, 72, 117, 2, 100, 84, 3, 176, 164, 49, 109, 130, 235, 167, 233, 236, 220, 42, 5, 12, 16, 173, 39}
var startFuncSig = [32]byte{0x77, 0xda, 0xb6, 0x11, 0xad, 0x9a, 0x24, 0xb7, 0x63, 0xe2, 0x74, 0x2f, 0x57, 0x74, 0x9a, 0x02, 0x27, 0x39, 0x3e, 0x0d, 0xa7, 0x62, 0x12, 0xd7, 0x4f, 0xce, 0xb3, 0x26, 0xb0, 0x66, 0x14, 0x24}
var segmentFuncSig = [32]byte{0xb0, 0x15, 0x57, 0xf1, 0xf6, 0x34, 0xb7, 0xc5, 0x07, 0x2a, 0xb5, 0xe3, 0x6d, 0x07, 0xa2, 0x35, 0x5e, 0xf8, 0x19, 0xfa, 0xca, 0x5a, 0x3d, 0x32, 0x14, 0x30, 0xd7, 0x19, 0x87, 0x15, 0x5b, 0x8f}
var rootFuncSig = [32]byte{0xe6, 0x76, 0x3d, 0xd9, 0x9b, 0xf8, 0x94, 0xd7, 0x2f, 0x34, 0x99, 0xdd, 0x57, 0x2a, 0xa4, 0x28, 0x76, 0xea, 0xe7, 0xae, 0x02, 0x8c, 0x32, 0xff, 0xf2, 0x16, 0x54, 0xe1, 0xbb, 0xc4, 0xc8, 0x07}

const MessageIDPattern = "%s-%s-%d-%d-%d"

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
	data = append(sfcFuncSig[:], data...)

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
	return fmt.Sprintf(MessageIDPattern, t.Source, t.SourceAddr, event.BlockNumber, event.TxIndex, event.Index)
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
	var funcSig []byte
	if ok {
		raw = startEvent.Raw
		funcSig = startFuncSig[:]
	} else {
		segmentEvent, ok := event.(*functioncall.GpactSegment)
		if ok {
			raw = segmentEvent.Raw
			funcSig = segmentFuncSig[:]
		} else {
			rootEvent, ok := event.(*functioncall.GpactRoot)
			if ok {
				raw = rootEvent.Raw
				funcSig = rootFuncSig[:]
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
	data = append(funcSig, data...)

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
