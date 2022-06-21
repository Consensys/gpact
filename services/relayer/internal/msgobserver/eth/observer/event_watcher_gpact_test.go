package observer

import (
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ipfs/go-datastore"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"math/big"
	"math/rand"
	"testing"
	"time"
)

func TestGPACTRealtimeEventWatcher(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	_, contract := deployGPACTContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)

	opts := EventWatcherOpts{
		Context:      auth.Context,
		EventHandler: handler,
	}
	watcher, err := NewGPACTRealtimeEventWatcher(opts, handler, contract)
	assert.Nil(t, err, "failed to create a realtime event watcher")
	go watcher.Watch()
	defer watcher.StopWatcher()

	makeGPACTStartCall(t, contract, auth)
	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
	// TODO: test scenarios involving segment and root events
}

func TestGPACTRealtimeEventWatcher_RemovedEvent(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	_, contract := deployGPACTContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)

	removedHandler := new(MockEventHandler)
	removedHandler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)

	opts := EventWatcherOpts{
		Context:      auth.Context,
		EventHandler: handler,
	}
	watcher, err := NewGPACTRealtimeEventWatcher(opts, removedHandler, contract)
	assert.Nil(t, err, "failed to create a realtime event watcher")
	go watcher.Watch()
	defer watcher.StopWatcher()

	b1 := simBackend.Blockchain().CurrentBlock().Hash()

	makeGPACTStartCall(t, contract, auth)
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

func TestGPACTFinalisedEventWatcher_FailsIfConfirmationTooLow(t *testing.T) {
	handler := new(MockEventHandler)
	opts := EventWatcherOpts{EventHandler: handler}
	_, err := NewGPACTFinalisedEventWatcher(opts, fixWatcherProgressDsOpts, fixEventHandleRetryOpts, 0, nil, nil)
	assert.NotNil(t, err)
}

func TestGPACTFinalisedEventWatcher_FailsIfEventHandlerNil(t *testing.T) {
	opts := EventWatcherOpts{EventHandler: nil}
	_, err := NewGPACTFinalisedEventWatcher(opts, fixWatcherProgressDsOpts, fixEventHandleRetryOpts, 2, nil, nil)
	assert.NotNil(t, err)
}

// tests the watcher behaviour under different confirmation number settings
func TestGPACTFinalisedEventWatcher(t *testing.T) {
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
		_, contract := deployGPACTContract(t, simBackend, auth)

		opts := EventWatcherOpts{
			Start:        v.start,
			Context:      auth.Context,
			EventHandler: handler,
		}

		progOpts.dsProgKey = datastore.NewKey(k)
		watcher, e := NewGPACTFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, v.confirmations, contract,
			simBackend)
		assert.Nil(t, e)
		go watcher.Watch()

		makeGPACTStartCall(t, contract, auth)

		mineConfirmingBlocks(v.confirmations-1, simBackend)
		handler.AssertNotCalled(t, "Handle", mock.AnythingOfType("*functioncall.GpactStart"))

		handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)
		commitAndSleep(simBackend)
		handler.AssertExpectations(t)
		watcher.StopWatcher()

		// Test that the progress of the watcher is persisted correctly
		progress, err := watcher.GetSavedProgress()
		assert.Nil(t, err)
		assert.Equal(t, v.lastFinalised, progress)
	}
	// TODO: test scenarios involving segment and root events
}

// tests scenarios where events in multiple blocks have been finalised but not yet been processed
func TestGPACTFinalisedEventWatcher_MultipleBlocksFinalised(t *testing.T) {
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
		_, contract := deployGPACTContract(t, simBackend, auth)

		// cross-chain calls before watch instance is started
		for i := 0; i < v.ccEventsToCommit; i++ {
			commit(simBackend)
			makeGPACTStartCall(t, contract, auth)
		}
		opts := EventWatcherOpts{
			Start:        v.start,
			Context:      auth.Context,
			EventHandler: handler,
		}
		progOpts.dsProgKey = datastore.NewKey(k)
		watcher, e := NewGPACTFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, v.confirmations, contract, simBackend)
		assert.Nil(t, e)
		go watcher.Watch()

		handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Times(v.expectedFinalised).Return(nil)
		commitAndSleep(simBackend)
		handler.AssertExpectations(t)
		watcher.StopWatcher()

		// Test that the progress of the watcher is persisted correctly
		progress, err := watcher.GetSavedProgress()
		assert.Nil(t, err)
		assert.Equal(t, v.lastFinalised, progress)
	}
}

func TestGPACTFinalisedEventWatcher_ProgressPersisted(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	_, contract := deployGPACTContract(t, simBackend, auth)
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

	watcher, e := NewGPACTFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, fixConfirms, contract, simBackend)
	assert.Nil(t, e)

	go watcher.Watch()
	makeGPACTStartCall(t, contract, auth)
	mineConfirmingBlocks(fixConfirms-1, simBackend)
	handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)
	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
	watcher.StopWatcher()

	// Test that the progress of the watcher is persisted correctly
	progress, err := watcher.GetSavedProgress()
	assert.Nil(t, err)
	assert.Equal(t, fixLastFinalised, progress)

	// Test that the saved progress is used when restarting a new watcher
	newWatcher, e := NewGPACTFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, fixConfirms, contract, simBackend)
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
func TestGPACTFinalisedEventWatcher_Reorg(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	handler := new(MockEventHandler)
	_, contract := deployGPACTContract(t, simBackend, auth)
	opts := EventWatcherOpts{
		Start:        2,
		Context:      auth.Context,
		EventHandler: handler,
	}

	ds, dsClose := newDS(t)
	defer dsClose()
	progOpts := createProgressOpts(ds)

	watcher, e := NewGPACTFinalisedEventWatcher(opts, progOpts, fixEventHandleRetryOpts, 2, contract, simBackend)
	assert.Nil(t, e)
	go watcher.Watch()
	defer watcher.StopWatcher()

	b1 := simBackend.Blockchain().CurrentBlock().Hash()
	makeGPACTStartCall(t, contract, auth)
	commit(simBackend)

	err := simBackend.Fork(auth.Context, b1)
	assert.Nil(t, err, "could not simulate forking blockchain")
	mineConfirmingBlocks(2, simBackend)
	time.Sleep(2 * time.Second)

	// handler would panic if it had been called, so this line is a redundant check for clarity
	handler.AssertNotCalled(t, "Handle", mock.AnythingOfType("*functioncall.GpactStart"))
}

func makeGPACTStartCall(t *testing.T, contract *functioncall.Gpact, auth *bind.TransactOpts) {
	_, err := contract.GpactTransactor.Start(auth, big.NewInt(rand.Int63()), big.NewInt(10),
		[]byte("call graph payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
}
