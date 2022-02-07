package observer

/*
 * Copyright 2021 ConsenSys Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import (
	"context"
	"fmt"
	"github.com/avast/retry-go"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/event"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
	"log"
	"time"
)

// EventWatcher listens to blockchain events
type EventWatcher interface {
	Watch() error
	StopWatcher()
}

type EventWatcherOpts struct {
	Start        uint64
	Context      context.Context
	EventHandler EventHandler
}

type WatcherProgressDsOpts struct {
	ds         *badgerds.Datastore
	dsProgKey  datastore.Key
	retry      uint
	retryDelay time.Duration
}

var DefaultWatcherProgressDsOpts = WatcherProgressDsOpts{
	retry:      3,
	retryDelay: time.Second,
}

// SFCCrossCallRealtimeEventWatcher subscribes and listens to events from a Simple Function Call bridge contract.
// The events produced by this watcher are generated the instant they are mined (i.e. 1 confirmation).
// Note: The watcher does not check to see if the event is affected by any reorgs.
type SFCCrossCallRealtimeEventWatcher struct {
	EventWatcherOpts
	RemovedEventHandler EventHandler
	SfcContract         *functioncall.Sfc
	end                 chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given Simple Function Call contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network is not successful.
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
			logging.Info("Stop watching %v.", l.SfcContract)
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

func NewSFCCrossCallRealtimeEventWatcher(watcherOpts EventWatcherOpts, removedEventHandler EventHandler, contract *functioncall.Sfc,
	end chan bool) (*SFCCrossCallRealtimeEventWatcher, error) {
	if watcherOpts.EventHandler == nil || removedEventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &SFCCrossCallRealtimeEventWatcher{EventWatcherOpts: watcherOpts, RemovedEventHandler: removedEventHandler, SfcContract: contract, end: end}, nil
}

type BlockHeadProducer interface {
	SubscribeNewHead(ctx context.Context, ch chan<- *types.Header) (ethereum.Subscription, error)
}

// SFCCrossCallFinalisedEventWatcher listens for events from a Simple Function Call bridge and processes these events only once they are
// finalised. An event is considered finalised once it receives a configurable number of block confirmations.
// One block confirmation means the instant the transaction generating the event is mined,
// and is equivalent in behaviour to the SFCCrossCallRealtimeEventWatcher
type SFCCrossCallFinalisedEventWatcher struct {
	EventWatcherOpts
	WatcherProgressDsOpts
	SfcContract              *functioncall.Sfc
	confirmationsForFinality uint64 //  the number of block confirmations required before an event is considered finalised.
	client                   BlockHeadProducer
	end                      chan bool
	nextBlockToProcess       uint64
}

func (l *SFCCrossCallFinalisedEventWatcher) StopWatcher() {
	l.end <- true
}

// Watch subscribes and starts listening to 'CrossCall' events from a given Simple Function Call contract.
// Once an events receives sufficient block confirmations, it is passed to an event handler for processing.
func (l *SFCCrossCallFinalisedEventWatcher) Watch() error {
	headers := make(chan *types.Header)
	sub, err := l.client.SubscribeNewHead(l.Context, headers)
	if err != nil {
		return fmt.Errorf("failed to subscribe to new block headers %v", err)
	}

	l.setNextBlockToProcess()
	for {
		select {
		case err := <-sub.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case <-l.end:
			logging.Info("Stop watching %v.", l.SfcContract)
			return nil
		case latestHead := <-headers:
			l.processFinalisedEvents(latestHead)
		}
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) GetNextBlockToProcess() uint64 {
	return l.nextBlockToProcess
}

// GetSavedProgress fetches the last processed block number that has been persisted to the datastore.
// This value is updated in the datastore each time events in a block are processed (see `processFinalisedEvents(...)`).
// Hence, the value should typically be the same as `SFCCrossCallFinalisedEventWatcher.nextBlockToProcess-1`.
// returns an error if querying the datastore or deserialising the result fails.
func (l *SFCCrossCallFinalisedEventWatcher) GetSavedProgress() (uint64, error) {
	p, err := l.ds.Get(l.Context, l.dsProgKey)
	if err != nil {
		return 0, err
	}
	progress, err := bytesToUint(p)
	if err != nil {
		return 0, err
	}
	return progress, nil
}

func (l *SFCCrossCallFinalisedEventWatcher) processFinalisedEvents(latest *types.Header) {
	latestBlock := latest.Number.Uint64()
	confirmations := (latestBlock - l.nextBlockToProcess) + 1

	logging.Debug("latest: '%d', next to process: '%d', confirmations: '%d', required confirmations: %d", latestBlock, l.nextBlockToProcess,
		confirmations, l.confirmationsForFinality)

	if latestBlock >= l.nextBlockToProcess && confirmations >= l.confirmationsForFinality {
		numFinalisedBlocks := confirmations - l.confirmationsForFinality
		lastFinalisedBlock := l.nextBlockToProcess + numFinalisedBlocks

		logging.Debug("Finalising events from blocks %d to %d", l.nextBlockToProcess, lastFinalisedBlock)

		err := l.handleEventsWithRetry(l.nextBlockToProcess, lastFinalisedBlock)
		if err == nil {
			l.nextBlockToProcess = lastFinalisedBlock + 1
			saveProgressWithRetry(lastFinalisedBlock, l.dsProgKey, l.ds)
		} else {
			logging.Error("Failed to process finalised events in blocks %d-%d. Error: %v", l.nextBlockToProcess, lastFinalisedBlock, err)
		}
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) handleEventsWithRetry(startBlock uint64, lastBlock uint64) error {
	return retry.Do(
		func() error {
			filterOpts := &bind.FilterOpts{Start: startBlock, End: &lastBlock, Context: l.Context}
			finalisedEvs, err := l.SfcContract.FilterCrossCall(filterOpts)
			if err != nil {
				return err
			}
			l.handleEvents(finalisedEvs)
			return nil
		},
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error processing finalised events in blocks %d-%d. Retry attempt: %d, error: %v", startBlock,
				lastBlock, attempt+1, err)
		}))
}

func saveProgressWithRetry(lastFinalisedBlock uint64, key datastore.Key, ds *badgerds.Datastore) {
	err := retry.Do(
		func() error {
			return ds.Put(context.Background(), key, uintToBytes(lastFinalisedBlock))
		},
		retry.Attempts(3),
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error persisting watcher progress to db. Last finalised block: %d, Retry Attempt: %d, Error: %v",
				lastFinalisedBlock, attempt+1, err)
		}))
	if err != nil {
		logging.Error("Failed to persist watcher progress to db. Last finalised block: %d, Error: %v", lastFinalisedBlock, err)
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) handleEvents(events *functioncall.SfcCrossCallIterator) {
	for events.Next() {
		ev := events.Event
		l.EventHandler.Handle(ev)
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) setNextBlockToProcess() {
	lastProcessed, err := l.GetSavedProgress()
	if err != nil {
		logging.Info("Failed to fetch last processed block from datastore. Defaulting to provided start point: %d", l.Start)
		l.nextBlockToProcess = l.Start
	} else {
		l.nextBlockToProcess = lastProcessed + 1
	}
}

// NewSFCCrossCallFinalisedEventWatcher creates an SFCCrossCall event watcher that only returns events once they receive a configured number of
// confirmations. Note: 1 block confirmation means the instant the transaction generating the event is mined
func NewSFCCrossCallFinalisedEventWatcher(watcherOpts EventWatcherOpts, watchProgressDbOpts WatcherProgressDsOpts, confirmsForFinality uint64,
	contract *functioncall.Sfc, client BlockHeadProducer, end chan bool) (*SFCCrossCallFinalisedEventWatcher, error) {
	if confirmsForFinality < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d", confirmsForFinality)
	}
	if watcherOpts.EventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &SFCCrossCallFinalisedEventWatcher{EventWatcherOpts: watcherOpts, WatcherProgressDsOpts: watchProgressDbOpts,
		SfcContract: contract, confirmationsForFinality: confirmsForFinality, client: client, end: end}, nil
}
