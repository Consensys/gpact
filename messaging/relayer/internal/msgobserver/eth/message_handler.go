package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

type MessageHandler interface {
	Handle(m *v1.Message)
	//TODO: handle fault method
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

func NewMessageEnqueueHandler(qServer mqserver.MQServer) MessageHandler {
	return &MessageEnqueueHandler{qServer}
}

func (s *MessageEnqueueHandler) sendMessage(msg *v1.Message) {
	logging.Info("Send message with ID: %v", msg.ID)
	s.MQ.Request(v1.Version, v1.MessageType, msg)
}
