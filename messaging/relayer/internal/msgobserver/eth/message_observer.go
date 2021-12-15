package eth

import (
	"context"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
)

// SFCBridgeObserver listens to incoming events from an SFC contract, transforms them into relayer messages
// and then enqueues them onto a message queue them for further processing by other Relayer components
type SFCBridgeObserver struct {
	EventWatcher  *SFCCrossCallWatcher
	EventHandler  *SimpleEventHandler
	SourceNetwork string
}

func NewSFCBridgeObserver(source string, contract *sfc.Sfc, mq mqserver.MessageQueue) (*SFCBridgeObserver, error) {
	eventTransformer := NewSFCEventTransformer(source)
	messageHandler := NewMessageEnqueueHandler(mq)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)
	eventWatcher := NewSFCCrossCallWatcher(context.Background(), eventHandler, contract)

	return &SFCBridgeObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: source}, nil
}

func (o *SFCBridgeObserver) Start() {
	o.EventWatcher.Watch()
}
