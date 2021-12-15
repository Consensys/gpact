package eth

import (
	"math/big"
	"testing"
	"time"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/ethereum/go-ethereum/common"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
)

func TestSFCBridgeObserver(t *testing.T) {
	fixDesAddress := common.HexToAddress("0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4")
	fixDestID := big.NewInt(2)
	fixSourceID := "1"

	simBackend, auth := simulatedBackend(t)
	contract := deployContract(t, simBackend, auth)
	mockMQ := new(MockMQ)

	observer, err := NewSFCBridgeObserver(fixSourceID, contract, mockMQ)
	assert.Nil(t, err)
	go observer.Start()

	mockMQ.On("Request", v1.Version, v1.MessageType, mock.AnythingOfType("*v1.Message")).Once()

	_, err = contract.SfcTransactor.CrossBlockchainCall(auth, fixDestID, fixDesAddress, []byte("payload"))
	if err != nil {
		failNow(t, "failed to transact: %v", err)
	}

	simBackend.Commit()

	time.Sleep(2 * time.Second)
	mockMQ.AssertExpectations(t)

	sentMsg := mockMQ.LastMessage.(*v1.Message)

	assert.Equal(t, fixDesAddress.String(), sentMsg.Destination.ContractAddress)
	assert.Equal(t, fixDestID.String(), sentMsg.Destination.NetworkID)
	assert.Equal(t, fixSourceID, sentMsg.Source.NetworkID)
}
