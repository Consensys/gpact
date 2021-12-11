package eth

import (
	"context"

	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

type MessageObserver struct {
	Listener EventListener
	Handler  EventHandler
}

type MessageObserverConfig struct {
	EventLogWSURL string
	FilterAddress string
}

func NewSFCObserver(config MessageObserverConfig, mq mqserver.MQServer) (*MessageObserver, error) {
	transformer, err := NewSFCEventTransformer()
	if err != nil {
		return nil, err
	}

	handler := NewSimpleEventHandler(transformer, NewSendToQueueHandler(mq))
	listener, err := NewFilteredEventListener(config.EventLogWSURL, config.FilterAddress, handler, context.Background())
	if err != nil {
		return nil, err
	}

	return &MessageObserver{Listener: listener, Handler: handler}, nil
}

func (o *MessageObserver) Start() {
	o.Listener.StartListener()
}
