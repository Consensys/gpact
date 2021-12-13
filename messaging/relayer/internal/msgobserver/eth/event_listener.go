package eth

import (
	"context"
	"log"

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
)

type EventListener interface {
	Start()
}

type EventListenerConfig struct {
	Context context.Context
	Handler EventHandler
}

type SFCCrossCallListener struct {
	EventListenerConfig
	SfcContract *sfc.Sfc
}

func (l *SFCCrossCallListener) Start() {
	opts := bind.WatchOpts{Context: l.Context}
	chanEvents := make(chan *sfc.SfcCrossCall)
	sub, err := l.SfcContract.WatchCrossCall(&opts, chanEvents)
	if err != nil {
		log.Fatalf("failed to subscribe to crosschaincall event %v", err)
	}
	l.start(sub, chanEvents)
}

func (l *SFCCrossCallListener) start(sub event.Subscription, chanEvents <-chan *sfc.SfcCrossCall) {
	for {
		select {
		case err := <-sub.Err():
			log.Fatalf("error in log subscription %v", err)
		case log := <-chanEvents:
			l.Handler.Handle(log)
		}
	}
}

func NewSFCCrossCallListener(context context.Context, handler EventHandler, contract *sfc.Sfc) *SFCCrossCallListener {
	return &SFCCrossCallListener{EventListenerConfig: EventListenerConfig{Context: context, Handler: handler}, SfcContract: contract}
}
