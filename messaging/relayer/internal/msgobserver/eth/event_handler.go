package eth

import (
	"github.com/ethereum/go-ethereum/core/types"
)

type EventHandler interface {
	Handle(types.Log)
}

// SimpleEventHandler executes a simple workflow of first transforming an event to a message and the
// forwarding it to a message consumer to process.
type SimpleEventHandler struct {
	EventTransformer EventTransformer
	MessageConsumer  MessageConsumer
}

// Handle transforms the provided even to a Message then forwards it to a message consumer to process.
func (h *SimpleEventHandler) Handle(event types.Log) {
	message, _ := h.EventTransformer.ToMessage(event)
	//TODO: error handling
	h.MessageConsumer.Consume(message)
}

func NewSimpleEventHandler(transformer EventTransformer, sender MessageConsumer) EventHandler {
	return &SimpleEventHandler{EventTransformer: transformer, MessageConsumer: sender}
}
