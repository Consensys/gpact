package eth

import (
	"context"

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
)

type EventListener interface {
	StartListener()
}

type ListenerConfig struct {
	Context context.Context
	Handler EventHandler
}

type SFCCrossCallListener struct {
	ListenerConfig
	SfcContract *sfc.Sfc
}

func (l *SFCCrossCallListener) StartListener() {
	opts := bind.WatchOpts{Context: l.Context}
	chanEvents := make(chan *sfc.SfcCrossCall)
	go l.startListener(chanEvents)
	l.SfcContract.WatchCrossCall(&opts, chanEvents)
}

func (l *SFCCrossCallListener) startListener(chanEvents <-chan *sfc.SfcCrossCall) {
	for {
		log := <-chanEvents
		l.Handler.Handle(log)
		// TODO: error handling
	}
}

func NewSFCCrossCallListener(context context.Context, handler EventHandler, contract *sfc.Sfc) *SFCCrossCallListener {
	return &SFCCrossCallListener{ListenerConfig: ListenerConfig{Context: context, Handler: handler}, SfcContract: contract}
}
