package eth

import (
	"context"

	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
)

type MessageObserver interface {
	Start()
}

type SFCBridgeObserver struct {
	Listener      *SFCCrossCallListener
	Handler       *SimpleEventHandler
	SourceNetwork string
}

func NewSFCBridgeObserver(sourceNetwork string, contract *sfc.Sfc, mq mqserver.MQServer) (*SFCBridgeObserver, error) {
	transformer := NewSFCEventTransformer(sourceNetwork)
	handler := NewSimpleEventHandler(transformer, NewMessageEnqueueHandler(mq))
	listener := NewSFCCrossCallListener(context.Background(), handler, contract)

	return &SFCBridgeObserver{Listener: listener, Handler: handler, SourceNetwork: sourceNetwork}, nil
}

func (o *SFCBridgeObserver) Start() {
	o.Listener.StartListener()
}
