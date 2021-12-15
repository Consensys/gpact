package eth

import (
	"context"
	"log"

	"github.com/consensys/gpact/messaging/relayer/internal/soliditywrappers/sfc"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
)

// EventWatcher listens to blockchain events
type EventWatcher interface {
	Watch()
}

type EventWatcherConfig struct {
	Start   *uint64
	Context context.Context
	Handler EventHandler
}

// SFCCrossCallWatcher subscribes and listens to events from a simple-function-call bridge contract
type SFCCrossCallWatcher struct {
	EventWatcherConfig
	SfcContract *sfc.Sfc
}

// Watch subscribes and starts listening to 'CrossCall' events from a given simple-function-call contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network is not successful.
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
			// TODO: communicate this to the calling context
			log.Fatalf("error in log subscription %v", err)
		case log := <-chanEvents:
			l.Handler.Handle(log)
		}
	}
}

func NewSFCCrossCallWatcher(context context.Context, handler EventHandler, contract *sfc.Sfc) *SFCCrossCallWatcher {
	return &SFCCrossCallWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler}, SfcContract: contract}
}
