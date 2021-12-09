package eth

import (
	"context"
)

type MessageObserver struct {
	Listener EventListener
	Handler  EventHandler
}

type MessageObserverConfig struct {
	EventLogWSURL string
	FilterAddress string
}

func NewMessageObserver(config MessageObserverConfig) (*MessageObserver, error) {
	listener, err := NewFilteredEventListener(config.EventLogWSURL, config.FilterAddress, context.Background())
	if err != nil {
		return nil, err
	}
	transformer := SFCBridgeEventTransformer{}
	handler := NewSimpleEventHandler(&transformer, MessageQueueSender{})
	return &MessageObserver{Listener: listener, Handler: handler}, nil
}

func (o *MessageObserver) Start() {
	o.Listener.StartListener()
}
