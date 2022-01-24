package verifier

import (
	"math/big"
	"os"
	"testing"

	"github.com/ethereum/go-ethereum/common"
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

func TestNewVerifierImplV1(t *testing.T) {
	verifier := NewVerifierImplV1(testDS)
	err := verifier.Start()
	assert.Empty(t, err)
	defer verifier.Stop()
}

func TestSetVerifierAddr(t *testing.T) {
	verifier := NewVerifierImplV1(testDS)
	err := verifier.Start()
	assert.Empty(t, err)
	defer verifier.Stop()

	err = verifier.SetVerifierAddr(big.NewInt(1), common.BytesToAddress([]byte{1}), common.BytesToAddress([]byte{2}))
	assert.Empty(t, err)
}

func TestGetVerifierAddr(t *testing.T) {
	verifier := NewVerifierImplV1(testDS)
	err := verifier.Start()
	assert.Empty(t, err)
	defer verifier.Stop()

	_, err = verifier.GetVerifierAddr(big.NewInt(2), common.BytesToAddress([]byte{2}))
	assert.NotEmpty(t, err)

	_, err = verifier.GetVerifierAddr(big.NewInt(1), common.BytesToAddress([]byte{2}))
	assert.NotEmpty(t, err)

	_, err = verifier.GetVerifierAddr(big.NewInt(2), common.BytesToAddress([]byte{1}))
	assert.NotEmpty(t, err)

	addr, err := verifier.GetVerifierAddr(big.NewInt(1), common.BytesToAddress([]byte{1}))
	assert.Empty(t, err)
	assert.Equal(t, common.BytesToAddress([]byte{2}), addr)
}
