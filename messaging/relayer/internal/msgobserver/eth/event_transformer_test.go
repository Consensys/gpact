package eth

import (
	"math/big"
	"testing"

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/common"
	"github.com/stretchr/testify/assert"
)

var fixValidEvent = sfc.SfcCrossCall{
	DestBcId:         big.NewInt(1),
	DestContract:     common.HexToAddress("0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4"),
	Timestamp:        big.NewInt(1639527190),
	DestFunctionCall: randomBytes(10),
}
var transformer = NewSFCEventTransformer("network-001")

func TestSFCTransformer(t *testing.T) {
	message, err := transformer.ToMessage(&fixValidEvent)
	assert.Nil(t, err)

	assert.Equal(t, fixValidEvent.DestBcId.String(), message.Destination.NetworkID)
	assert.Equal(t, fixValidEvent.DestContract.String(), message.Destination.ContractAddress)
	assert.Equal(t, fixValidEvent.Timestamp, big.NewInt(message.Timestamp))
	assert.Equal(t, toBase64String(fixValidEvent.DestFunctionCall), message.Payload)
}
func TestSFCTransformerFailsOnInvalidEventType(t *testing.T) {
	assert.Panics(t, func() { transformer.ToMessage("invalid event") })
}
func TestSFCTransformerFailsOnInvalidTimestamp(t *testing.T) {
	invalidTimestamp := fixValidEvent
	invalidTimestamp.Timestamp = big.NewInt(-1)

	_, err := transformer.ToMessage(&invalidTimestamp)
	assert.NotNil(t, err)
	assert.Regexp(t, "invalid timestamp", err.Error())

}

func TestSFCTransformerFailsOnInvalidDestination(t *testing.T) {
	invalidDesBcId := fixValidEvent
	invalidDesBcId.DestBcId = nil

	_, err := transformer.ToMessage(&invalidDesBcId)
	assert.NotNil(t, err)
	assert.Regexp(t, "destination network id", err.Error())
}

// TODO: verify encoded payload