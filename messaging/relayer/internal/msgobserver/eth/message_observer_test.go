package eth

import (
	"math/big"
	"testing"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

// FIXME: just mock the queue
func createQueue(t *testing.T) *mqserver.MQServer {
	var err error
	s, err := mqserver.NewMQServer(
		"amqp://guest:guest@localhost:5672/",
		"channel_message",
		"amqp://guest:guest@localhost:5672/",
		"channel_event", nil)
	if err != nil {
		failNow(t, "error creating queue: %v", err)
	}
	return s
}
func TestSFCBridgeObserver(t *testing.T) {
	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)
	observer, err := NewSFCBridgeObserver("sim-net-001", contract, createQueue(t))

	if err != nil {
		failNow(t, "could not create an observer instance: %v", err)
	}

	go observer.Start()

	_, err = contract.SfcTransactor.CrossBlockchainCall(auth, big.NewInt(100), auth.From, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}

	simBackend.Commit()
	//FIXME:
	time.Sleep(3 * time.Second)
	//TODO: validation of messages in queue
}
