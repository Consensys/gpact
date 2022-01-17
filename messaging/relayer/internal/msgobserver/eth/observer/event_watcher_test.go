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
	"math/big"
	"testing"
	"time"

	"github.com/stretchr/testify/mock"
)

type MockEventHandler struct {
	mock.Mock
}

func (m *MockEventHandler) Handle(event interface{}) error {
	args := m.Called(event)
	return args.Error(0)
}

func TestSFCCrossCallWatcher(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)

	watcher := NewSFCCrossCallWatcher(auth.Context, handler, contract, make(chan bool))
	go watcher.Watch()

	_, err := contract.SfcTransactor.CrossBlockchainCall(auth, big.NewInt(100), auth.From, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}

	simBackend.Commit()
	time.Sleep(2 * time.Second)
	handler.AssertExpectations(t)
}
