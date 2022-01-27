package transactor

import (
	"math/big"
	"os"
	"testing"

	"github.com/stretchr/testify/assert"
)

const (
	testDS = "./test"
)

func TestMain(m *testing.M) {
	os.RemoveAll(testDS)
	os.Mkdir(testDS, os.ModePerm)
	defer os.RemoveAll(testDS)
	m.Run()
}

func TestNewTransactorImplV1(t *testing.T) {
	transactor := NewTransactorImplV1(testDS)
	err := transactor.Start()
	assert.Empty(t, err)
	defer transactor.Stop()
}

func TestSetTransactionOpts(t *testing.T) {
	transactor := NewTransactorImplV1(testDS)
	err := transactor.Start()
	assert.Empty(t, err)
	defer transactor.Stop()

	err = transactor.SetTransactionOpts(big.NewInt(1), "test-chain", []byte{1, 2, 3})
	assert.Empty(t, err)
}

func TestGetChainAP(t *testing.T) {
	transactor := NewTransactorImplV1(testDS)
	err := transactor.Start()
	assert.Empty(t, err)
	defer transactor.Stop()

	_, err = transactor.GetChainAP(big.NewInt(2))
	assert.NotEmpty(t, err)

	chainAP, err := transactor.GetChainAP(big.NewInt(1))
	assert.Empty(t, err)
	assert.Equal(t, "test-chain", chainAP)
}

func TestGetAuth(t *testing.T) {
	transactor := NewTransactorImplV1(testDS)
	err := transactor.Start()
	assert.Empty(t, err)
	defer transactor.Stop()

	_, err = transactor.GetAuth(big.NewInt(2))
	assert.NotEmpty(t, err)

	_, err = transactor.GetAuth(big.NewInt(1))
	assert.Empty(t, err)
}
