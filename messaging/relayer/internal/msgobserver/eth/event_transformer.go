package eth

import (
	"encoding/hex"
	"errors"
	"fmt"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
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
	destination := v1.ApplicationAddress{NetworkID: sfcEvent.DestBcId.String(), ContractAddress: sfcEvent.DestContract.String()}

	message := v1.Message{
		ID:          hex.EncodeToString(randomBytes(16)), // TODO: replace with a proper message id scheme
		Timestamp:   sfcEvent.Timestamp.Int64(),
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: destination,
		Source:      source,
		Payload:     toBase64String(sfcEvent.DestFunctionCall),
	}

	return &message, nil
}

func (t *SFCEventTransformer) validate(event *sfc.SfcCrossCall) error {
	if event.DestBcId == nil {
		return errors.New("destination network id cannot be empty")
	}

	if event.Timestamp == nil || event.Timestamp.Int64() <= 0 {
		return fmt.Errorf("invalid timestamp '%s'", event.Timestamp)
	}

	return nil
}

func NewSFCEventTransformer(sourceNetwork string) *SFCEventTransformer {
	return &SFCEventTransformer{sourceNetwork}
}
