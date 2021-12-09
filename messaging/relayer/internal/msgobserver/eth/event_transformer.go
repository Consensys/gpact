package eth

import (
	"fmt"
	"math/rand"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/ethereum/go-ethereum/core/types"
)

type EventToMessageTransformer interface {
	ToMessage(log types.Log) v1.Message
}

type BridgeEventToMessageTransformer struct {
}

func (h BridgeEventToMessageTransformer) ToMessage(event types.Log) v1.Message {
	// TODO: decode log based on contract ABI
	// TODO: construct a Message object that encapsulates things
	return v1.Message{ID:fmt.Sprintf("some-random-id-%d", rand.Intn(10000))}
}
