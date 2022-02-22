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
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"math/big"
	"testing"
	"time"
)

type MockEventHandler struct {
	mock.Mock
}

func (m *MockEventHandler) Handle(event interface{}) {
	m.Called(event)
}

var fixWatcherProgressDsOpts = WatcherProgressDsOpts{
	FailureRetryOpts: FailureRetryOpts{
		RetryAttempts: 3,
		RetryDelay:    500 * time.Millisecond,
	},
}
var fixEventHandleRetryOpts = DefaultRetryOptions

func TestSFCCrossCallRealtimeEventWatcher(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)

	opts := EventWatcherOpts{
		Context:      auth.Context,
		EventHandler: handler,
	}
	watcher, err := NewSFCCrossCallRealtimeEventWatcher(opts, handler, contract)
	assert.Nil(t, err, "failed to create a realtime event watcher")
	go watcher.Watch()
	defer watcher.StopWatcher()

	makeCrossContractCallTx(t, contract, auth)

	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
}

func TestSFCCrossCallRealtimeEventWatcher_RemovedEvent(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)

	removedHandler := new(MockEventHandler)
	removedHandler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)

	opts := EventWatcherOpts{
		Context:      auth.Context,
		EventHandler: handler,
	}
	watcher, err := NewSFCCrossCallRealtimeEventWatcher(opts, removedHandler, contract)
	assert.Nil(t, err, "failed to create a realtime event watcher")
	go watcher.Watch()
	defer watcher.StopWatcher()

	b1 := simBackend.Blockchain().CurrentBlock().Hash()

	makeCrossContractCallTx(t, contract, auth)
	commit(simBackend)

	// create a fork of the chain that excludes the event. turn this chain into the longer chain.
	// this will cause the event log to be re-sent with a 'removed' flag set
	err = simBackend.Fork(auth.Context, b1)
	assert.Nil(t, err, "could not simulate forking blockchain")
	mineConfirmingBlocks(1, simBackend)
	commitAndSleep(simBackend)

	handler.AssertExpectations(t)
	removedHandler.AssertExpectations(t)
}

func TestSFCCrossCallFinalisedEventWatcher_FailsIfConfirmationTooLow(t *testing.T) {
	handler := new(MockEventHandler)
	opts := EventWatcherOpts{EventHandler: handler}
	_, err := NewSFCCrossCallFinalisedEventWatcher(opts, fixWatcherProgressDsOpts, fixEventHandleRetryOpts, 0, nil, nil)
	assert.NotNil(t, err)
}

func TestSFCCrossCallFinalisedEventWatcher_FailsIfEventHandlerNil(t *testing.T) {
	opts := EventWatcherOpts{EventHandler: nil}
	_, err := NewSFCCrossCallFinalisedEventWatcher(opts, fixWatcherProgressDsOpts, fixEventHandleRetryOpts, 2, nil, nil)
	assert.NotNil(t, err)
}

// tests the watcher behaviour under different confirmation number settings
func TestSFCCrossCallFinalisedEventWatcher(t *testing.T) {
	cases := map[string]struct{ confirmations, start, lastFinalised uint64 }{
		"1 Confirmation":  {1, 2, 2},
		"2 Confirmations": {2, 1, 2},
		"6 Confirmations": {6, 1, 2},
	}
	ds, dsClose := newDS(t)
	defer dsClose()
	progOpts := createProgressOpts(ds)

	for k, v := range cases {
		logging.Info("testing scenario: %s", k)
		handler := new(MockEventHandler)

		simBackend, auth := simulatedBackend(t)
		contract := deployContract(t, simBackend, auth)

		opts := EventWatcherOpts{
			Start:        v.start,
			Context:      auth.Context,
			EventHandler: handler,
		}

		progOpts.dsProgKey = datastore.NewKey(k)
		watcher, e := NewSFCCrossCallFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, v.confirmations, contract, simBackend)
		assert.Nil(t, e)
		go watcher.Watch()

		makeCrossContractCallTx(t, contract, auth)

		mineConfirmingBlocks(v.confirmations-1, simBackend)
		handler.AssertNotCalled(t, "Handle", mock.AnythingOfType("*functioncall.SfcCrossCall"))

		handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)
		commitAndSleep(simBackend)
		handler.AssertExpectations(t)
		watcher.StopWatcher()

		// Test that the progress of the watcher is persisted correctly
		progress, err := watcher.GetSavedProgress()
		assert.Nil(t, err)
		assert.Equal(t, v.lastFinalised, progress)
	}
}

// tests scenarios where events in multiple blocks have been finalised but not yet been processed
func TestSFCCrossCallFinalisedEventWatcher_MultipleBlocksFinalised(t *testing.T) {
	cases := map[string]struct {
		confirmations, start, lastFinalised uint64
		ccEventsToCommit, expectedFinalised int
	}{
		"Multi-Block-Event-Finalisation-with-1-Confirmation":  {1, 0, 6, 4, 4},
		"Multi-Block-Event-Finalisation-with-2-Confirmations": {2, 0, 5, 4, 3},
	}
	ds, dsClose := newDS(t)
	defer dsClose()
	progOpts := createProgressOpts(ds)

	for k, v := range cases {
		logging.Info("testing scenario: %s", k)

		simBackend, auth := simulatedBackend(t)
		handler := new(MockEventHandler)
		contract := deployContract(t, simBackend, auth)

		// cross-chain calls before watch instance is started
		for i := 0; i < v.ccEventsToCommit; i++ {
			commit(simBackend)
			makeCrossContractCallTx(t, contract, auth)
		}
		opts := EventWatcherOpts{
			Start:        v.start,
			Context:      auth.Context,
			EventHandler: handler,
		}
		progOpts.dsProgKey = datastore.NewKey(k)
		watcher, e := NewSFCCrossCallFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, v.confirmations, contract, simBackend)
		assert.Nil(t, e)
		go watcher.Watch()

		handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Times(v.expectedFinalised).Return(nil)
		commitAndSleep(simBackend)
		handler.AssertExpectations(t)
		watcher.StopWatcher()

		// Test that the progress of the watcher is persisted correctly
		progress, err := watcher.GetSavedProgress()
		assert.Nil(t, err)
		assert.Equal(t, v.lastFinalised, progress)
	}
}

func TestSFCCrossCallFinalisedEventWatcher_ProgressPersisted(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)
	fixConfirms := uint64(2)
	fixLastFinalised := uint64(2)

	handler := new(MockEventHandler)

	opts := EventWatcherOpts{
		Start:        0,
		Context:      auth.Context,
		EventHandler: handler,
	}

	ds, dsClose := newDS(t)
	defer dsClose()
	progOpts := createProgressOpts(ds)

	watcher, e := NewSFCCrossCallFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, fixConfirms, contract, simBackend)
	assert.Nil(t, e)

	go watcher.Watch()
	makeCrossContractCallTx(t, contract, auth)
	mineConfirmingBlocks(fixConfirms-1, simBackend)
	handler.On("Handle", mock.AnythingOfType("*functioncall.SfcCrossCall")).Once().Return(nil)
	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
	watcher.StopWatcher()

	// Test that the progress of the watcher is persisted correctly
	progress, err := watcher.GetSavedProgress()
	assert.Nil(t, err)
	assert.Equal(t, fixLastFinalised, progress)

	// Test that the saved progress is used when restarting a new watcher
	newWatcher, e := NewSFCCrossCallFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, fixConfirms, contract, simBackend)
	go newWatcher.Watch()
	time.Sleep(1 * time.Second)
	assert.Equal(t, progress+1, newWatcher.GetNextBlockToProcess())
	newWatcher.StopWatcher()
}

/*
Scenario: event is included in a block (b2') that is not part of the canonical chain.
Given: number of confirmation = 2
Expectation: the event is not processed at height b3
	b1 <-- b2 <-- b3 <-- b4
	  \_ b2'(ev)
*/
func TestSFCCrossCallFinalisedEventWatcher_Reorg(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	handler := new(MockEventHandler)
	contract := deployContract(t, simBackend, auth)
	opts := EventWatcherOpts{
		Start:        2,
		Context:      auth.Context,
		EventHandler: handler,
	}

	ds, dsClose := newDS(t)
	defer dsClose()
	progOpts := createProgressOpts(ds)

	watcher, e := NewSFCCrossCallFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, 2, contract, simBackend)
	assert.Nil(t, e)
	go watcher.Watch()
	defer watcher.StopWatcher()

	b1 := simBackend.Blockchain().CurrentBlock().Hash()
	makeCrossContractCallTx(t, contract, auth)
	commit(simBackend)

	err := simBackend.Fork(auth.Context, b1)
	assert.Nil(t, err, "could not simulate forking blockchain")
	mineConfirmingBlocks(2, simBackend)
	time.Sleep(2 * time.Second)

	// handler would panic if it had been called, so this line is a redundant check for clarity
	handler.AssertNotCalled(t, "Handle", mock.AnythingOfType("*functioncall.SfcCrossCall"))
}

func mineConfirmingBlocks(confirms uint64, simBackend *backends.SimulatedBackend) {
	for i := uint64(0); i < confirms; i++ {
		commit(simBackend)
	}
}

func makeCrossContractCallTx(t *testing.T, contract *functioncall.Sfc, auth *bind.TransactOpts) {
	_, err := contract.SfcTransactor.CrossBlockchainCall(auth, big.NewInt(100), auth.From, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
}

func commit(backend *backends.SimulatedBackend) {
	backend.Commit()
}

func commitAndSleep(backend *backends.SimulatedBackend) {
	commit(backend)
	time.Sleep(2 * time.Second)
}

func createProgressOpts(ds *badgerds.Datastore) WatcherProgressDsOpts {
	progOpts := fixWatcherProgressDsOpts
	progOpts.ds = ds
	progOpts.dsProgKey = datastore.NewKey("reorg_test")
	return progOpts
}
