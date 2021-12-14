package eth

import (
	"fmt"
)

type EventHandler interface {
	Handle(event interface{}) error
}

// SimpleEventHandler executes a simple workflow of first transforming an event to a message and the
// forwarding it to a message handler to process.
type SimpleEventHandler struct {
	EventTransformer EventTransformer
	MessageHandler   MessageHandler
}

// Handle transforms the provided even to a Message then forwards it to a message handler to process.
func (h *SimpleEventHandler) Handle(event interface{}) error {
	message, err := h.EventTransformer.ToMessage(event)
	if err != nil {
		return fmt.Errorf("error transforming event. %v", err)

	}
	h.MessageHandler.Handle(message)
	return nil
}

func NewSimpleEventHandler(transformer EventTransformer, sender MessageHandler) *SimpleEventHandler {
	return &SimpleEventHandler{EventTransformer: transformer, MessageHandler: sender}
}
