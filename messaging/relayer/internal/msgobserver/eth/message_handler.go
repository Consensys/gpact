package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

type MessageHandler interface {
	//TODO: error handling
	Handle(m *v1.Message)
}

type MessageEnqueueHandler struct {
	MQ mqserver.MQServer
}

// Handle sends the provided message to the configured message queue.
// The method assumes that the message queue is configured and started.
func (mq *MessageEnqueueHandler) Handle(m *v1.Message) {
	logging.Info("sending message '%v' to queue '%s'", m, "some queue")
	mq.sendMessage(m)
}

func NewMessageEnqueueHandler(qServer mqserver.MQServer) *MessageEnqueueHandler {
	return &MessageEnqueueHandler{qServer}
}

func (s *MessageEnqueueHandler) sendMessage(msg *v1.Message) {
	logging.Info("Send message with ID: %v", msg.ID)
	// TODO: s.MQ.Request(v1.Version, v1.MessageType, msg)
}
