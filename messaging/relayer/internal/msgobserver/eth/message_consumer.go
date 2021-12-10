package eth

import (
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
)

type MessageConsumer interface {
	Consume(m *v1.Message)
}

type SendToQueueConsumer struct {
	Queue string
}

func (mq SendToQueueConsumer) Consume(m *v1.Message) {
	//TODO: send to mq
	logging.Info("sending message '%v' to queue '%s'", m, "some queue")
}

func NewSendToQueueConsumer() MessageConsumer {
	return SendToQueueConsumer{}
}
