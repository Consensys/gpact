package eth

import (
	"crypto/rand"
	"encoding/hex"
	"errors"
	"fmt"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/common"
)

type EventTransformer interface {
	ToMessage(event interface{}) (*v1.Message, error)
}

type SFCEventTransformer struct {
	Source string
}

func (t *SFCEventTransformer) ToMessage(event interface{}) (*v1.Message, error) {
	sfcEvent := event.(*sfc.SfcCrossCall)

	if err := t.validate(sfcEvent); err != nil {
		return nil, err
	}

	source := v1.ApplicationAddress{NetworkID: t.Source, ContractAddress: sfcEvent.DestContract.String()}
	message := v1.Message{
		ID:          hex.EncodeToString(randomBytes(16)), // TODO: replace with a proper message id scheme
		Timestamp:   sfcEvent.Timestamp.Int64(),
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: v1.ApplicationAddress{NetworkID: sfcEvent.DestBcId.String(), ContractAddress: string(sfcEvent.DestContract[:])},
		Source:      source,
		Payload:     string(sfcEvent.DestFunctionCall),
	}

	return &message, nil
}

func (t *SFCEventTransformer) validate(event *sfc.SfcCrossCall) error {
	if event.DestBcId == nil {
		return errors.New("destination network id cannot be empty")
	}
	if len(event.DestContract) != common.AddressLength {
		return fmt.Errorf("destination contract address is invalid: '%s'", event.DestContract)
	}

	if event.Timestamp == nil || event.Timestamp.Int64() <= 0 {
		return fmt.Errorf("invalid timestamp '%s'", event.Timestamp)
	}

	return nil
}

func NewSFCEventTransformer(sourceNetwork string) *SFCEventTransformer {
	return &SFCEventTransformer{sourceNetwork}
}

func randomBytes(n int) []byte {
	res := make([]byte, n)
	rand.Read(res)
	return res
}
