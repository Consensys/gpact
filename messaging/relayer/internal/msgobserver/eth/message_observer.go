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

func NewSFCBridgeObserver(config MessageObserverConfig) (*MessageObserver, error) {
	sfcEventTransformer := SFCBridgeEventTransformer{}
	sendToQHandler := NewSimpleEventHandler(&sfcEventTransformer, SendToQueueConsumer{})
	listener, err := NewFilteredEventListener(config.EventLogWSURL, config.FilterAddress, sendToQHandler, context.Background())
	if err != nil {
		return nil, err
	}

	return &MessageObserver{Listener: listener, Handler: sendToQHandler}, nil
}

func (o *MessageObserver) Start() {
	o.Listener.StartListener()
}
