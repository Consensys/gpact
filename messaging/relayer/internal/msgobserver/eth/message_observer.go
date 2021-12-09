package eth

import (
	"context"
)

type MessageObserver struct {
	Listener *FilteredEventListener
	Handler  *EventHandler
}

type MessageObserverConfig struct {
	EventLogWSURL string
	FilterAddress string
}

func CreateMessageObserver(config MessageObserverConfig) (*MessageObserver, error) {
	listener, err := CreateFilteredEventListener(config.EventLogWSURL, config.FilterAddress, context.Background())
	if err != nil {
		return nil, err
	}
	transformer := BridgeEventToMessageTransformer{}
	handler := CreateSendEventToQueueHandler(transformer, MessageQueueSender{})
	return &MessageObserver{Listener: listener, Handler: &handler}, nil
}

func (o *MessageObserver) Start() {
	o.Listener.StreamEvents()
}
