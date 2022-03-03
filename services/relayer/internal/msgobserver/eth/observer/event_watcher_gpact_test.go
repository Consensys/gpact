package observer

import (
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"math/big"
	"testing"
)

func TestGpactRealtimeEventWatcher(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployGpactContract(t, simBackend, auth)

	handler := new(MockEventHandler)
	handler.On("Handle", mock.AnythingOfType("*functioncall.GpactStart")).Once().Return(nil)

	opts := EventWatcherOpts{
		Context:      auth.Context,
		EventHandler: handler,
	}
	watcher, err := NewGPACTRealtimeEventWatcher(opts, contract)
	assert.Nil(t, err, "failed to create a realtime event watcher")
	go watcher.Watch()
	defer watcher.StopWatcher()

	makeGpactStartCall(t, contract, auth)
	commitAndSleep(simBackend)
	handler.AssertExpectations(t)
}

func makeGpactStartCall(t *testing.T, contract *functioncall.Gpact, auth *bind.TransactOpts) {
	_, err := contract.GpactTransactor.Start(auth, big.NewInt(100), big.NewInt(10), []byte("call graph payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
}
