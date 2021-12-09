package eth

import (
	"github.com/ethereum/go-ethereum/core/types"
)

type EventHandler interface {
	Handle(types.Log)
}

type SendEventToQueueHandler struct {
	EventTransformer EventToMessageTransformer
	MessageSender    MessageSender
}

func (h *SendEventToQueueHandler) Handle(event types.Log) {
	message := h.EventTransformer.ToMessage(event)
	h.MessageSender.Send(message)
}

func CreateSendEventToQueueHandler(transformer EventToMessageTransformer, qSender MessageQueueSender) EventHandler {
	return &SendEventToQueueHandler{EventTransformer: transformer, MessageSender: qSender}
}
