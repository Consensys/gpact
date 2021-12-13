package eth

import (
	"context"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
)

type SFCObserver struct {
	Listener *SFCCrossCallListener
	Handler  *SimpleEventHandler
	Source   string
}

func NewSFCObserver(source string, contract *sfc.Sfc, mq *mqserver.MQServer) (*SFCObserver, error) {
	transformer := NewSFCEventTransformer(source)
	handler := NewSimpleEventHandler(transformer, NewMessageEnqueueHandler(mq))
	listener := NewSFCCrossCallListener(context.Background(), handler, contract)

	return &SFCObserver{Listener: listener, Handler: handler, Source: source}, nil
}

func (o *SFCObserver) Start() {
	o.Listener.Start()
}
