package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core"
	"github.com/ethereum/go-ethereum/crypto"
	"math/big"
	"testing"
)

func simulatedBackend(t *testing.T) (*backends.SimulatedBackend, *bind.TransactOpts) {
	key, err := crypto.GenerateKey()
	if err != nil {
		failNow(t, "failed to generate key: %v", err)
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

func deployContract(t *testing.T, simBackend *backends.SimulatedBackend, auth *bind.TransactOpts) *sfc.Sfc {
	_, _, contract, err := sfc.DeploySfc(auth, simBackend, big.NewInt(10), big.NewInt(10))

	if err != nil {
		failNow(t, "failed to deploy contract: %v", err)
	}

	simBackend.Commit()
	return contract
}

func failNow(t *testing.T, message string, args ...interface{}) {
	t.Errorf(message, args...)
	t.FailNow()
}
