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
	fixSourceID := big.NewInt(1)

	cases := map[string]struct {
		contractType  string
		watcherType   WatcherType
		confirmations int
		destAddress   string
		destId        *big.Int
	}{
		"Start-Realtime-SFC-Observer": {"sfc", RealtimeWatcher, 1,
			"0x8E215D06Ea7EC1Fdb4fC5fD21768F4B34eE92EF4", big.NewInt(2)},
		"Start-Finalised-SFC-Observer": {"sfc", FinalisedWatcher, 4,
			"0x8E215D06Ea7EC1Fdb4fC5fD21768F4B34eE92EF4", big.NewInt(2)},
		"Start-Realtime-GPACT-Observer": {"gpact", RealtimeWatcher, 1,
			"", big.NewInt(0)},
		"Start-Finalised-GPACT-Observer": {"gpact", FinalisedWatcher, 4,
			"", big.NewInt(0)},
	}
	dsPath, cleanupFn := newDSPath(t)
	defer cleanupFn()

	for name, testCase := range cases {
		logging.Info("Testing scenario: '%s'", name)
		simBackend, auth := simulatedBackend(t)
		var contract interface{}
		var fixSourceAddress common.Address
		fixSourceAddress, contract = deployContract(t, testCase.contractType, simBackend, auth)

		mockMQ := new(MockMQ)

		multiObserver, err := NewMultiSourceObserver(dsPath, mockMQ, factoryGenerator(simBackend))
		assert.Nil(t, err, "unexpected error creating multisource observer: %v", err)

		err = multiObserver.StartObservation(fixSourceID, "", testCase.contractType, fixSourceAddress, testCase.watcherType)
		assert.Nil(t, err, "could not start observation, error: %v", err)

		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Once().Return(nil)

		// perform crosschain calls and simulate the specified number of confirmations
		transact(t, testCase.contractType, contract, auth, testCase.destId, common.HexToAddress(testCase.destAddress),
			testCase.confirmations,
			simBackend)

		// verify that the observer detected and processed the contract event
		mockMQ.AssertExpectations(t)
		sentMsg := mockMQ.LastMessage.(*v1.Message)
		assert.Equal(t, testCase.destAddress, sentMsg.Destination.ContractAddress)
		assert.Equal(t, testCase.destId.String(), sentMsg.Destination.NetworkID)
		assert.Equal(t, fixSourceID.String(), sentMsg.Source.NetworkID)

		// ensure the observer is successfully stopped, by checking that subsequent crosschain calls are not detected
		multiObserver.Stop()
		assert.False(t, multiObserver.IsRunning(), "multiobserver stop failed")
		mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Times(0).Return(nil)
		transact(t, testCase.contractType, contract, auth, testCase.destId, common.HexToAddress(testCase.destAddress),
			testCase.confirmations,
			simBackend)
		mockMQ.AssertExpectations(t)
	}
}

func transact(t *testing.T, contractType string, contract interface{}, auth *bind.TransactOpts, destId *big.Int,
	address common.Address, confirmations int, backend *backends.SimulatedBackend) {
	if contractType == "gpact" {
		transactGPACT(t, contract, auth, confirmations, backend)
	} else {
		transactSFC(t, contract, auth, destId, address, confirmations, backend)
	}
}

func deployContract(t *testing.T, contractType string, backend *backends.SimulatedBackend, auth *bind.TransactOpts) (common.Address, interface{}) {
	if contractType == "gpact" {
		return deployGPACTContract(t, backend, auth)
	} else {
		return deploySFCContract(t, backend, auth)
	}
}

func transactSFC(t *testing.T, contract interface{}, auth *bind.TransactOpts, fixDestID *big.Int,
	fixDesAddress common.Address, confirmations int, simBackend *backends.SimulatedBackend) {
	c := contract.(*functioncall.Sfc)
	_, err := c.SfcTransactor.CrossBlockchainCall(auth, fixDestID, fixDesAddress, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
	for i := 0; i < confirmations; i++ {
		simBackend.Commit()
	}
	time.Sleep(3 * time.Second)
}

func transactGPACT(t *testing.T, contract interface{}, auth *bind.TransactOpts, confirmations int,
	simBackend *backends.SimulatedBackend) {
	c := contract.(*functioncall.Gpact)
	_, err := c.GpactTransactor.Start(auth, big.NewInt(rand.Int63()), big.NewInt(10), []byte("call graph payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
	for i := 0; i < confirmations; i++ {
		simBackend.Commit()
	}
	time.Sleep(3 * time.Second)
}

func factoryGenerator(backend *backends.SimulatedBackend) func(url string) (Backend, error) {
	return func(url string) (Backend, error) {
		return backend, nil
	}
}
