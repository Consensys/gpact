package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
)

type MessageSender interface {
	Send(m v1.Message)
}

type MessageQueueSender struct {
	Queue string
}

func (mq MessageQueueSender) Send(m v1.Message) {
	//TODO: send to mq
	logging.Info("sending message '%v' to queue '%s'", m.ID, mq.Queue)
}
