package observer

import (
	"fmt"
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
)

// GPACTCrossCallRealtimeEventWatcher is a simple gpact contract watcher.
type GPACTCrossCallRealtimeEventWatcher struct {
	EventWatcherOpts
	GpactContract *functioncall.Gpact
	end           chan bool
}

func (l *GPACTCrossCallRealtimeEventWatcher) Watch() error {
	opts := bind.WatchOpts{Start: &l.Start, Context: l.Context}
	startEvents := make(chan *functioncall.GpactStart)
	segmentEvents := make(chan *functioncall.GpactSegment)
	rootEvents := make(chan *functioncall.GpactRoot)
	subStart, err := l.GpactContract.WatchStart(&opts, startEvents)
	if err != nil {
		return err
	}
	subSegment, err := l.GpactContract.WatchSegment(&opts, segmentEvents)
	if err != nil {
		return err
	}
	subRoot, err := l.GpactContract.WatchRoot(&opts, rootEvents)
	if err != nil {
		return err
	}
	return l.start(subStart, startEvents, subSegment, segmentEvents, subRoot, rootEvents)
}

func (l *GPACTCrossCallRealtimeEventWatcher) start(
	subStart event.Subscription, startEvents <-chan *functioncall.GpactStart,
	subSegment event.Subscription, segmentEvents <-chan *functioncall.GpactSegment,
	subRoot event.Subscription, rootEvents <-chan *functioncall.GpactRoot,
) error {
	logging.Info("Start watching GPACT %v...", l.GpactContract)
	for {
		select {
		case err := <-subStart.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case err := <-subSegment.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case err := <-subRoot.Err():
			return fmt.Errorf("error in log subscription: %v", err)
		case ev := <-startEvents:
			l.EventHandler.Handle(ev)
		case ev := <-segmentEvents:
			l.EventHandler.Handle(ev)
		case ev := <-rootEvents:
			l.EventHandler.Handle(ev)
		case <-l.end:
			logging.Info("Watcher stopped...")
			return nil
		}
	}
}

func (l *GPACTCrossCallRealtimeEventWatcher) StopWatcher() {
	l.end <- true
}

// NewGPACTCrossCallRealtimeEventWatcher creates an instance of SFCCrossCallRealtimeEventWatcher.
// Throws an error if the provided even handler or the removed event handler is nil.
func NewGPACTCrossCallRealtimeEventWatcher(watcherOpts EventWatcherOpts, contract *functioncall.Gpact) (*GPACTCrossCallRealtimeEventWatcher, error) {
	if watcherOpts.EventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &GPACTCrossCallRealtimeEventWatcher{EventWatcherOpts: watcherOpts, GpactContract: contract, end: make(chan bool)}, nil
}
