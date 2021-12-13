package eth

import (
	"log"
	"math/big"
	"testing"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core"
	"github.com/ethereum/go-ethereum/crypto"
)

func simulatedBackend() (*backends.SimulatedBackend, *bind.TransactOpts) {
	key, err := crypto.GenerateKey()
	if err != nil {
		log.Fatal(err)
	}

	auth, _ := bind.NewKeyedTransactorWithChainID(key, big.NewInt(1337))

	address := auth.From
	genesisAlloc := map[common.Address]core.GenesisAccount{
		address: {
			Balance: big.NewInt(10000000000000000),
		},
	}

	blockGasLimit := uint64(10000000)
	return backends.NewSimulatedBackend(genesisAlloc, blockGasLimit), auth
}

func deploy(t *testing.T, simBackend *backends.SimulatedBackend, auth *bind.TransactOpts) *sfc.Sfc {
	_, _, contract, err := sfc.DeploySfc(auth, simBackend, big.NewInt(10), big.NewInt(10))

	if err != nil {
		failNow(t, "failed to deploy contract: %v", err)
	}

	simBackend.Commit()

	return contract
}
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
	simBackend, auth := simulatedBackend()
	contract := deploy(t, simBackend, auth)
	observer, err := NewSFCObserver("sim-net-001", contract, createQueue(t))

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

func failNow(t *testing.T, message string, args ...interface{}) {
	t.Errorf(message, args...)
	t.FailNow()
}
