package observer

import (
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"math/big"
	"testing"
)

func TestGPACTRealtimeEventWatcher(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployGPACTContract(t, simBackend, auth)

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

	makeGpactStartCall(t, contract, auth)
	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
}

func TestGPACTRealtimeEventWatcher_RemovedEvent(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployGPACTContract(t, simBackend, auth)

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

	makeGpactStartCall(t, contract, auth)
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

func makeGpactStartCall(t *testing.T, contract *functioncall.Gpact, auth *bind.TransactOpts) {
	_, err := contract.GpactTransactor.Start(auth, big.NewInt(100), big.NewInt(10), []byte("call graph payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
}
