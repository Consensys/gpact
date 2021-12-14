package eth

import (
	"context"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
)

type SFCBridgeObserver struct {
	EventWatcher  *SFCCrossCallWatcher
	EventHandler  *SimpleEventHandler
	SourceNetwork string
}

func NewSFCBridgeObserver(source string, contract *sfc.Sfc, mq *mqserver.MQServer) (*SFCBridgeObserver, error) {
	eventTransformer := NewSFCEventTransformer(source)
	messageHandler := NewMessageEnqueueHandler(mq)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)
	eventWatcher := NewSFCCrossCallWatcher(context.Background(), eventHandler, contract)

	return &SFCBridgeObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: source}, nil
}

func (o *SFCBridgeObserver) Start() {
	o.EventWatcher.Watch()
}
