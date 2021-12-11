package eth

import (
	"fmt"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/core/types"
	"strings"
)

type EventTransformer interface {
	ToMessage(event types.Log) (*v1.Message, error)
}

const SFCEventName = "CrossCall"

type SFCEventTransformer struct {
	ContractABI abi.ABI
}

func (t *SFCEventTransformer) ToMessage(event types.Log) (*v1.Message, error) {
	//TODO: addressing events that are being removed because of a reorg
	e, err := t.ContractABI.Unpack(SFCEventName, event.Data)

	if err != nil {
		return nil, err
	}

	if len(e) == 0 {
		return nil, fmt.Errorf("failed to transform event to message: %v", event)
	}

	sfcEvent := e[0].(sfc.SfcCrossCall)
	message := v1.Message{
		ID:          string(sfcEvent.TxId[:]), // TODO: replace with a proper message id scheme
		Timestamp:   sfcEvent.Timestamp.Int64(),
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: v1.ApplicationAddress{NetworkID: sfcEvent.DestBcId.String(), ContractAddress: string(sfcEvent.DestContract[:])},
		Payload:     string(sfcEvent.DestFunctionCall),
	}

	return &message, nil
}

func NewSFCEventTransformer() (EventTransformer, error) {
	abi, err := abi.JSON(strings.NewReader(string(sfc.SfcMetaData.ABI)))
	if err != nil {
		return nil, err
	}
	return &SFCEventTransformer{abi}, nil
}
