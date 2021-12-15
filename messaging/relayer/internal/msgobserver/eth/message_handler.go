package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

type MessageHandler interface {
	Handle(m *v1.Message)
}

type MessageEnqueueHandler struct {
	MQ mqserver.MessageQueue
}

// Handle sends the provided message to the configured message queue.
// The method assumes that the message queue is configured and started.
func (mq *MessageEnqueueHandler) Handle(m *v1.Message) {
	mq.sendMessage(m)
}

func NewMessageEnqueueHandler(qServer mqserver.MessageQueue) *MessageEnqueueHandler {
	return &MessageEnqueueHandler{qServer}
}

func (s *MessageEnqueueHandler) sendMessage(msg *v1.Message) {
	logging.Info("Send message with ID: %s\n", msg.ID)
	s.MQ.Request(v1.Version, v1.MessageType, msg)
}
