package observer

import (
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ethereum/go-ethereum/common"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"math/big"
	"math/rand"
	"testing"
	"time"
)

func TestMultiSourceObserver_SFCObservation(t *testing.T) {
	fixDesAddress := common.HexToAddress("0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4")
	fixDestID := big.NewInt(2)
	fixSourceID := big.NewInt(1)

	cases := map[string]struct {
		contractType  string
		watcherType   WatcherType
		confirmations int
	}{
		"Start-Realtime-SFC-Observer":  {"sfc", RealtimeWatcher, 1},
		"Start-Finalised-SFC-Observer": {"sfc", FinalisedWatcher, 4},
	}
	dsPath, cleanupFn := newDSPath(t)
	defer cleanupFn()

	for k, v := range cases {
		logging.Info("Started testing scenario: '%s'", k)
		simBackend, auth := simulatedBackend(t)
		fixSourceAddress, contract := deploySFCContract(t, simBackend, auth)
		mockMQ := new(MockMQ)

		multiObserver, err := NewMultiSourceObserver(dsPath, mockMQ, factoryGenerator(simBackend))
		assert.Nil(t, err, "unexpected error creating multisource observer: %v", err)

		err = multiObserver.StartObservation(fixSourceID, "", v.contractType, fixSourceAddress, v.watcherType)
		assert.Nil(t, err, "could not start observation, error: %v", err)

		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Once().Return(nil)

		// perform crosschain calls and ensure sufficient number of confirmations are performed for the event to be
		// processed by the observer
		transactSFC(t, contract, auth, fixDestID, fixDesAddress, v.confirmations, simBackend)

		// verify that the observer detected and processed the contract event
		mockMQ.AssertExpectations(t)
		sentMsg := mockMQ.LastMessage.(*v1.Message)
		assert.Equal(t, fixDesAddress.String(), sentMsg.Destination.ContractAddress)
		assert.Equal(t, fixDestID.String(), sentMsg.Destination.NetworkID)
		assert.Equal(t, fixSourceID.String(), sentMsg.Source.NetworkID)

		// ensure the observer is successfully stopped, by checking that subsequent crosschain calls are not detected
		multiObserver.Stop()
		assert.False(t, multiObserver.IsRunning(), "multiobserver stop failed")
		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Times(0).Return(nil)
		transactSFC(t, contract, auth, fixDestID, fixDesAddress, v.confirmations, simBackend)
		mockMQ.AssertExpectations(t)

		logging.Info("Finished testing scenario: '%s'", k)
	}
}

func TestMultiSourceObserver_GPACTObservation(t *testing.T) {
	fixSourceID := big.NewInt(1)

	cases := map[string]struct {
		contractType  string
		watcherType   WatcherType
		confirmations int
	}{
		"Start-Realtime-GPACT-Observer":  {"gpact", RealtimeWatcher, 1},
		"Start-Finalised-GPACT-Observer": {"gpact", FinalisedWatcher, 4},
	}
	dsPath, cleanupFn := newDSPath(t)
	defer cleanupFn()

	for k, v := range cases {
		logging.Info("Started testing scenario: '%s'", k)
		simBackend, auth := simulatedBackend(t)
		fixSourceAddress, contract := deployGPACTContract(t, simBackend, auth)
		mockMQ := new(MockMQ)

		multiObserver, err := NewMultiSourceObserver(dsPath, mockMQ, factoryGenerator(simBackend))
		assert.Nil(t, err, "unexpected error creating multisource observer: %v", err)

		err = multiObserver.StartObservation(fixSourceID, "", v.contractType, fixSourceAddress, v.watcherType)
		assert.Nil(t, err, "could not start observation, error: %v", err)

		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Once().Return(nil)

		// perform crosschain calls and ensure sufficient number of confirmations are performed for the event to be
		// processed by the observer
		transactGPACT(t, contract, auth, v.confirmations, simBackend)

		// verify that the observer detected and processed the contract event
		mockMQ.AssertExpectations(t)
		sentMsg := mockMQ.LastMessage.(*v1.Message)
		assert.Equal(t, fixSourceID.String(), sentMsg.Source.NetworkID)

		// ensure the observer is successfully stopped, by checking that subsequent crosschain calls are not detected
		multiObserver.Stop()
		assert.False(t, multiObserver.IsRunning(), "multiobserver stop failed")
		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Times(0).Return(nil)

		transactGPACT(t, contract, auth, v.confirmations, simBackend)
		mockMQ.AssertExpectations(t)

		logging.Info("Finished testing scenario: '%s'", k)
	}
}

func transactSFC(t *testing.T, contract *functioncall.Sfc, auth *bind.TransactOpts, fixDestID *big.Int,
	fixDesAddress common.Address, confirmations int, simBackend *backends.SimulatedBackend) {
	_, err := contract.SfcTransactor.CrossBlockchainCall(auth, fixDestID, fixDesAddress, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
	for i := 0; i < confirmations; i++ {
		simBackend.Commit()
	}
	time.Sleep(3 * time.Second)
}

func transactGPACT(t *testing.T, contract *functioncall.Gpact, auth *bind.TransactOpts, confirmations int, simBackend *backends.SimulatedBackend) {
	_, err := contract.GpactTransactor.Start(auth, big.NewInt(rand.Int63()), big.NewInt(10), []byte("call graph payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
	for i := 0; i < confirmations; i++ {
		simBackend.Commit()
	}
	time.Sleep(4 * time.Second)
}

func factoryGenerator(backend *backends.SimulatedBackend) func(url string) (Backend, error) {
	return func(url string) (Backend, error) {
		return backend, nil
	}
}
