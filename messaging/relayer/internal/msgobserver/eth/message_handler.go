package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

type MessageHandler interface {
	Handle(m *v1.Message)
}

type SendToQHandler struct {
	MQ mqserver.MQServer
}

// Handle sends the provided message to the configured message queue.
// The method assumes that the message queue is configured and started.
func (mq *SendToQHandler) Handle(m *v1.Message) {
	logging.Info("sending message '%v' to queue '%s'", m, "some queue")
	mq.sendMessage(m)
}

func NewSendToQueueHandler(qServer mqserver.MQServer) MessageHandler {
	return &SendToQHandler{qServer}
}

func (s *SendToQHandler) sendMessage(msg *v1.Message) {
	logging.Info("Send message with ID: %v", msg.ID)
	s.MQ.Request(v1.Version, v1.MessageType, msg)
}
