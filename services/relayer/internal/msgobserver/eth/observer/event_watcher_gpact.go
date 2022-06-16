package observer

import (
	"fmt"
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
	"sync"
)

// GPACTRealtimeEventWatcher subscribes and listens to three types of GPACT events: `GpactStart`, `GpactSegment` and `GpactRoot` events.
// The events produced by this watcher are generated the instant they are mined (i.e. 1 confirmation).
// The progress of this watcher is not persisted, and will always start from either EventWatcherOps.Start block if provided or the latest block if not.
// The watcher does not check to see if the event is affected by any reorgs.
type GPACTRealtimeEventWatcher struct {
	EventWatcherOpts
	RemovedEventHandler EventHandler
	GpactContract       *functioncall.Gpact
	end                 chan bool
}

// Watch subscribes and starts listening to `GpactStart`,
// `GpactSegment` and `GpactRoot` events from a given `Gpact` bridge contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network fails.
func (l *GPACTRealtimeEventWatcher) Watch() error {
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

func (l *GPACTRealtimeEventWatcher) start(
	subStart event.Subscription, startEvents <-chan *functioncall.GpactStart,
	subSegment event.Subscription, segmentEvents <-chan *functioncall.GpactSegment,
	subRoot event.Subscription, rootEvents <-chan *functioncall.GpactRoot,
) error {
	for {
		select {
		case err := <-subStart.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case err := <-subSegment.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case err := <-subRoot.Err():
			return fmt.Errorf("error in log subscription: %v", err)
		case ev := <-startEvents:
			l.handle(ev, ev.Raw.Removed)
		case ev := <-segmentEvents:
			l.handle(ev, ev.Raw.Removed)
		case ev := <-rootEvents:
			l.handle(ev, ev.Raw.Removed)
		case <-l.end:
			logging.Info("Watcher stopped...")
			subStart.Unsubscribe()
			subRoot.Unsubscribe()
			subSegment.Unsubscribe()
			return nil
		}
	}
}
func (l *GPACTRealtimeEventWatcher) handle(ev interface{}, removed bool) {
	if !removed {
		l.EventHandler.Handle(ev)
	} else {
		l.RemovedEventHandler.Handle(ev)
	}
}
func (l *GPACTRealtimeEventWatcher) StopWatcher() {
	l.end <- true
}

// NewGPACTRealtimeEventWatcher creates an instance of GPACTRealtimeEventWatcher
// Throws an error if the provided even handler or the removed event handler is nil.
func NewGPACTRealtimeEventWatcher(watcherOpts EventWatcherOpts, removedEventHandler EventHandler,
	contract *functioncall.Gpact) (*GPACTRealtimeEventWatcher, error) {
	if watcherOpts.EventHandler == nil || removedEventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &GPACTRealtimeEventWatcher{EventWatcherOpts: watcherOpts,
		RemovedEventHandler: removedEventHandler, GpactContract: contract,
		end: make(chan bool)}, nil
}

// GPACTFinalisedEventWatcher  listens to events from a `Gpact` bridge contract and processes them only once they are
// 'finalised'. It specifically listens to three types of GPACT events: `GpactStart`, `GpactSegment` and `GpactRoot` events.
// An event is considered 'finalised' once it receives a configurable number of block confirmations.
// An event has one block confirmation the instant it is mined into a block.
type GPACTFinalisedEventWatcher struct {
	FinalisedEventWatcher
	Contract *functioncall.Gpact
}

type EventIterator interface {
	Next() bool
	GetEvent() interface{}
}
type GpactStartEventIterator struct {
	*functioncall.GpactStartIterator
}
type GpactSegmentEventIterator struct {
	*functioncall.GpactSegmentIterator
}
type GpactRootEventIterator struct {
	*functioncall.GpactRootIterator
}

func (e *GpactStartEventIterator) GetEvent() interface{} {
	return e.Event
}
func (e *GpactSegmentEventIterator) GetEvent() interface{} {
	return e.Event
}
func (e *GpactRootEventIterator) GetEvent() interface{} {
	return e.Event
}

// TODO: reduce number of filter calls to one
func (l *GPACTFinalisedEventWatcher) fetchAndProcessEvents(filterOpts *bind.FilterOpts) error {
	var wg sync.WaitGroup
	finalisedStartEvs, err := l.Contract.FilterStart(filterOpts)
	if err != nil {
		return err
	}
	wg.Add(1)
	go l.processEvents(&GpactStartEventIterator{finalisedStartEvs}, &wg)

	finalisedSegmentEvs, err := l.Contract.FilterSegment(filterOpts)
	if err != nil {
		return err
	}
	wg.Add(1)
	go l.processEvents(&GpactSegmentEventIterator{finalisedSegmentEvs}, &wg)

	finalisedRootEvs, err := l.Contract.FilterRoot(filterOpts)
	if err != nil {
		return err
	}
	wg.Add(1)
	go l.processEvents(&GpactRootEventIterator{finalisedRootEvs}, &wg)

	wg.Wait()
	return nil
}

func (l *GPACTFinalisedEventWatcher) processEvents(events EventIterator, wg *sync.WaitGroup) {
	for events.Next() {
		ev := events.GetEvent()
		l.EventHandler.Handle(ev)
	}
	wg.Done()
}

// NewGPACTFinalisedEventWatcher creates a GPACTFinalisedEventWatcher instance that processes events only
// once they receive sufficient number of confirmations.The first block confirmation is achieved when the event is mined.
// Throws an error if the provided number of confirmations is 0
func NewGPACTFinalisedEventWatcher(watcherOpts EventWatcherOpts, watchProgressDbOpts WatcherProgressDsOpts,
	handlerRetryOpts FailureRetryOpts, confirmsForFinality uint64,
	contract *functioncall.Gpact, client BlockHeadProducer) (*GPACTFinalisedEventWatcher, error) {
	if confirmsForFinality < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d",
			confirmsForFinality)
	}
	if watcherOpts.EventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	finalisedEvW := FinalisedEventWatcher{EventWatcherOpts: watcherOpts, WatcherProgressOpts: watchProgressDbOpts,
		EventHandleRetryOpts: handlerRetryOpts, confirmationsForFinality: confirmsForFinality, client: client,
		end: make(chan bool)}

	gpactWatcher := GPACTFinalisedEventWatcher{FinalisedEventWatcher: finalisedEvW, Contract: contract}
	gpactWatcher.fetchAndProcessEvsFunc = gpactWatcher.fetchAndProcessEvents

	return &gpactWatcher, nil
}
