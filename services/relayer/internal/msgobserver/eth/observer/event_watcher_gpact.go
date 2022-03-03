package observer

import (
	"fmt"
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
	"sync"
)

// GPACTRealtimeEventWatcher is a simple gpact contract watcher.
type GPACTRealtimeEventWatcher struct {
	EventWatcherOpts
	RemovedEventHandler EventHandler
	GpactContract       *functioncall.Gpact
	end                 chan bool
}

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
			subStart.Unsubscribe()
			subRoot.Unsubscribe()
			subSegment.Unsubscribe()
			return nil
		}
	}
}

func (l *GPACTRealtimeEventWatcher) StopWatcher() {
	l.end <- true
}

// NewGPACTRealtimeEventWatcher creates an instance of SFCCrossCallRealtimeEventWatcher.
// Throws an error if the provided even handler or the removed event handler is nil.
func NewGPACTRealtimeEventWatcher(watcherOpts EventWatcherOpts, contract *functioncall.Gpact) (*GPACTRealtimeEventWatcher, error) {
	if watcherOpts.EventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &GPACTRealtimeEventWatcher{EventWatcherOpts: watcherOpts, GpactContract: contract, end: make(chan bool)}, nil
}

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
	go l.processEvents(&GpactStartEventIterator{finalisedStartEvs}, &wg)

	finalisedSegmentEvs, err := l.Contract.FilterSegment(filterOpts)
	if err != nil {
		return err
	}
	go l.processEvents(&GpactSegmentEventIterator{finalisedSegmentEvs}, &wg)

	finalisedRootEvs, err := l.Contract.FilterRoot(filterOpts)
	if err != nil {
		return err
	}
	go l.processEvents(&GpactRootEventIterator{finalisedRootEvs}, &wg)

	wg.Wait()
	return nil
}

func (l *GPACTFinalisedEventWatcher) processEvents(events EventIterator, wg *sync.WaitGroup) {
	wg.Add(1)
	for events.Next() {
		ev := events.GetEvent()
		l.EventHandler.Handle(ev)
	}
	wg.Done()
}

func NewGpactFinalisedEventWatcher(watcherOpts EventWatcherOpts, watchProgressDbOpts WatcherProgressDsOpts,
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
