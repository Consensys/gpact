package eth

import (
	"fmt"
)

type EventHandler interface {
	Handle(event interface{}) error
}

// SimpleEventHandler first transforms an event to a relayer message then passes it to a handler to process
type SimpleEventHandler struct {
	// EventTransformer transforms a given event to a relayer message (v1.Message)
	EventTransformer EventTransformer
	// MessageHandler processes a relayer message that has been created from a event
	MessageHandler MessageHandler
}

// Handle transforms the provided event to a message then forwards it to a message handler to process.
func (h *SimpleEventHandler) Handle(event interface{}) error {
	message, err := h.EventTransformer.ToMessage(event)
	if err != nil {
		return fmt.Errorf("error transforming event. %v", err)

	}
	return h.MessageHandler.Handle(message)
}

func NewSimpleEventHandler(transformer EventTransformer, sender MessageHandler) *SimpleEventHandler {
	return &SimpleEventHandler{EventTransformer: transformer, MessageHandler: sender}
}
