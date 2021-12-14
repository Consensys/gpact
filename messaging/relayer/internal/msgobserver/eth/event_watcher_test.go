package eth

import (
	"math/big"
	"testing"
)

type MockHandler struct {
	CalledTimes uint16
}

func (m *MockHandler) Handle(event interface{}) error {
	m.CalledTimes += 1
	return nil
}

func TestSFCCrossCallListener(t *testing.T) {
	// setup simulated backend
	// check that handler receives events
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)

	handler := &MockHandler{}
	watcher := NewSFCCrossCallWatcher(auth.Context, handler, contract)
	watcher.Watch()

	_, err := contract.SfcTransactor.CrossBlockchainCall(auth, big.NewInt(100), auth.From, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}
}
