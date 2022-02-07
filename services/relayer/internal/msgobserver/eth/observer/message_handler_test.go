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
	"fmt"
	"testing"
	"time"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
)

func TestMessageEnqueueHandler(t *testing.T) {
	fixMsg := v1.Message{
		ID:          "msg-0001",
		Timestamp:   2384923489234,
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: v1.ApplicationAddress{NetworkID: "network-001"},
		Source:      v1.ApplicationAddress{NetworkID: "network-002"},
	}

	mockMQ := new(MockMQ)

	mockMQ.On("Request", fixMsg.Version, fixMsg.MsgType, &fixMsg).Once().Return(nil)
	handler := NewMessageEnqueueHandler(mockMQ, DefaultRetryOptions)
	handler.Handle(&fixMsg)

	time.Sleep(time.Second)
	mockMQ.AssertExpectations(t)
}
func TestMessageEnqueueHandler_RetryOnFailure(t *testing.T) {
	fixMsg := v1.Message{
		ID:          "msg-0001",
		Timestamp:   2384923489234,
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: v1.ApplicationAddress{NetworkID: "network-001"},
		Source:      v1.ApplicationAddress{NetworkID: "network-002"},
	}

	mockMQ := new(MockMQ)

	retryOpts := FailureRetryOpts{RetryDelay: 100 * time.Millisecond, RetryAttempts: 3}
	mockMQ.On("Request", fixMsg.Version, fixMsg.MsgType, &fixMsg).
		Times(int(retryOpts.RetryAttempts)).Return(fmt.Errorf("transient failure when calling handler"))
	handler := NewMessageEnqueueHandler(mockMQ, retryOpts)
	handler.Handle(&fixMsg)

	time.Sleep(2 * time.Second)
	mockMQ.AssertExpectations(t)
}
