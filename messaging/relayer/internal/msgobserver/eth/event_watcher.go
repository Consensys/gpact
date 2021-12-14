package eth

import (
	"context"
	"log"

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
)

type EventWatcher interface {
	Watch()
}

type EventWatcherConfig struct {
	Start *uint64
	Context context.Context
	Handler EventHandler
}

type SFCCrossCallWatcher struct {
	EventWatcherConfig
	SfcContract *sfc.Sfc
}

func (l *SFCCrossCallWatcher) Watch() {
	opts := bind.WatchOpts{Start: l.Start, Context: l.Context}
	chanEvents := make(chan *sfc.SfcCrossCall)
	sub, err := l.SfcContract.WatchCrossCall(&opts, chanEvents)
	if err != nil {
		log.Fatalf("failed to subscribe to crosschaincall event %v", err)
	}
	l.start(sub, chanEvents)
}

func (l *SFCCrossCallWatcher) start(sub event.Subscription, chanEvents <-chan *sfc.SfcCrossCall) {
	for {
		select {
		case err := <-sub.Err():
			log.Fatalf("error in log subscription %v", err)
		case log := <-chanEvents:
			l.Handler.Handle(log)
		}
	}
}

func NewSFCCrossCallWatcher(context context.Context, handler EventHandler, contract *sfc.Sfc) *SFCCrossCallWatcher {
	return &SFCCrossCallWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler}, SfcContract: contract}
}
