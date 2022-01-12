package observer

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
	"encoding/json"
	"math/big"
	"testing"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/ethereum/go-ethereum/common"
	"github.com/stretchr/testify/assert"
)

var fixValidEvent = functioncall.SfcCrossCall{
	DestBcId:         big.NewInt(1),
	DestContract:     common.HexToAddress("0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4"),
	Timestamp:        big.NewInt(1639527190),
	DestFunctionCall: randomBytes(10),
}
var transformer = NewSFCEventTransformer("network-001", "0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4")

func TestSFCTransformer(t *testing.T) {
	message, err := transformer.ToMessage(&fixValidEvent)
	assert.Nil(t, err)

	data, err := json.Marshal(fixValidEvent.Raw)
	assert.Nil(t, err)

	assert.Equal(t, fixValidEvent.DestBcId.String(), message.Destination.NetworkID)
	assert.Equal(t, fixValidEvent.DestContract.String(), message.Destination.ContractAddress)
	assert.Equal(t, fixValidEvent.Timestamp, big.NewInt(message.Timestamp))
	assert.Equal(t, hex.EncodeToString(data), message.Payload)
}
func TestSFCTransformerFailsOnInvalidEventType(t *testing.T) {
	assert.Panics(t, func() { transformer.ToMessage("invalid event") })
}
func TestSFCTransformerFailsOnInvalidTimestamp(t *testing.T) {
	invalidTimestamp := fixValidEvent
	invalidTimestamp.Timestamp = big.NewInt(-1)

	_, err := transformer.ToMessage(&invalidTimestamp)
	assert.NotNil(t, err)
	assert.Regexp(t, "invalid timestamp", err.Error())

}

func TestSFCTransformerFailsOnInvalidDestination(t *testing.T) {
	invalidDesBcId := fixValidEvent
	invalidDesBcId.DestBcId = nil

	_, err := transformer.ToMessage(&invalidDesBcId)
	assert.NotNil(t, err)
	assert.Regexp(t, "destination network id", err.Error())
}

// TODO: verify encoded payload
