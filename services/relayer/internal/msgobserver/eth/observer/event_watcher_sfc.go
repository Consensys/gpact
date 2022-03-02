package observer

import (
	"fmt"
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
	"log"
)

// SFCCrossCallRealtimeEventWatcher subscribes and listens to events from a 'Simple Function Call' bridge contract.
// The events produced by this watcher are generated the instant they are mined (i.e. 1 confirmation).
// The progress of this watcher is not persisted, and will always start from either EventWatcherOps.Start block if provided or the latest block if not.
// The watcher does not check to see if the event is affected by any reorgs.
type SFCCrossCallRealtimeEventWatcher struct {
	EventWatcherOpts
	// RemovedEventHandler handles events that have been affected by a reorg and are no longer a part of the canonical chain
	RemovedEventHandler EventHandler
	SfcContract         *functioncall.Sfc
	end                 chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given 'Simple Function Call' contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network fails.
func (l *SFCCrossCallRealtimeEventWatcher) Watch() error {
	opts := bind.WatchOpts{Start: &l.Start, Context: l.Context}
	chanEvents := make(chan *functioncall.SfcCrossCall)
	sub, err := l.SfcContract.WatchCrossCall(&opts, chanEvents)
	if err != nil {
		log.Fatalf("failed to subscribe to crosschaincall event %v", err)
	}
	return l.start(sub, chanEvents)
}

func (l *SFCCrossCallRealtimeEventWatcher) start(sub event.Subscription, chanEvents <-chan *functioncall.SfcCrossCall) error {
	logging.Info("Start watching %v...", l.SfcContract)
	for {
		select {
		case err := <-sub.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case <-l.end:
			logging.Info("Watcher stopped...")
			sub.Unsubscribe()
			return nil
		case ev := <-chanEvents:
			if ev.Raw.Removed {
				l.RemovedEventHandler.Handle(ev)
			} else {
				l.EventHandler.Handle(ev)
			}
		}
	}
}

func (l *SFCCrossCallRealtimeEventWatcher) StopWatcher() {
	l.end <- true
}

// NewSFCCrossCallRealtimeEventWatcher creates an instance of SFCCrossCallRealtimeEventWatcher.
// Throws an error if the provided even handler or the removed event handler is nil.
func NewSFCCrossCallRealtimeEventWatcher(watcherOpts EventWatcherOpts, removedEventHandler EventHandler, contract *functioncall.Sfc) (*SFCCrossCallRealtimeEventWatcher, error) {
	if watcherOpts.EventHandler == nil || removedEventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &SFCCrossCallRealtimeEventWatcher{EventWatcherOpts: watcherOpts, RemovedEventHandler: removedEventHandler, SfcContract: contract,
		end: make(chan bool)}, nil
}

// SFCCrossCallFinalisedEventWatcher listens to events from a 'Simple Function Call' bridge and processes them only once they are
// 'finalised'. An event is considered 'finalised' once it receives a configurable number of block confirmations.
// An event has one block confirmation the instant it is mined into a block.
type SFCCrossCallFinalisedEventWatcher struct {
	FinalisedEventWatcher
	SfcContract *functioncall.Sfc
}

func (l *SFCCrossCallFinalisedEventWatcher) fetchAndProcessEvents(filterOpts *bind.FilterOpts) error {
	finalisedEvs, err := l.SfcContract.FilterCrossCall(filterOpts)
	if err != nil {
		return err
	}
	l.handleEvents(finalisedEvs)
	return nil
}

func (l *SFCCrossCallFinalisedEventWatcher) handleEvents(events *functioncall.SfcCrossCallIterator) {
	for events.Next() {
		ev := events.Event
		logging.Debug("Processing event, Block: %d, Tx: %d, Log: %d", ev.Raw.BlockNumber, ev.Raw.TxIndex, ev.Raw.Index)
		l.EventHandler.Handle(ev)
	}
}

// NewSFCCrossCallFinalisedEventWatcher creates an `SFCCrossCall` event watcher that processes events only once they receive sufficient confirmations.
// Note: 1 block confirmation means the instant the transaction generating the event is mined.
func NewSFCCrossCallFinalisedEventWatcher(watcherOpts EventWatcherOpts, watchProgressDbOpts WatcherProgressDsOpts,
	handlerRetryOpts FailureRetryOpts, confirmsForFinality uint64,
	contract *functioncall.Sfc, client BlockHeadProducer) (*SFCCrossCallFinalisedEventWatcher, error) {
	if confirmsForFinality < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d", confirmsForFinality)
	}
	if watcherOpts.EventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	finalisedEvW := FinalisedEventWatcher{EventWatcherOpts: watcherOpts, WatcherProgressOpts: watchProgressDbOpts, EventHandleRetryOpts: handlerRetryOpts, confirmationsForFinality: confirmsForFinality, client: client, end: make(chan bool)}

	sfcWatcher := SFCCrossCallFinalisedEventWatcher{FinalisedEventWatcher: finalisedEvW, SfcContract: contract}
	sfcWatcher.fetchAndProcessEvsFunc = sfcWatcher.fetchAndProcessEvents

	return &sfcWatcher, nil
}
