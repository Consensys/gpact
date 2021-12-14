package eth

import (
	"math/big"
	"testing"

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
	handler.On("Handle", mock.AnythingOfType("*sfc.SfcCrossCall")).Once().Return(nil)

	watcher := NewSFCCrossCallWatcher(auth.Context, handler, contract)
	go watcher.Watch()

	_, err := contract.SfcTransactor.CrossBlockchainCall(auth, big.NewInt(100), auth.From, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}

	simBackend.Commit()
	handler.AssertExpectations(t)
}
