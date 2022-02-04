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
	"log"
)

// EventWatcher listens to blockchain events
type EventWatcher interface {
	Watch() error
}

type EventWatcherConfig struct {
	Start        uint64
	Context      context.Context
	EventHandler EventHandler
}

// SFCCrossCallRealtimeEventWatcher subscribes and listens to events from a Simple Function Call bridge contract.
// The events produced by this watcher are generated the instant they are mined (i.e. 1 confirmation).
// Note: The watcher does not check to see if the event is affected by any reorgs.
type SFCCrossCallRealtimeEventWatcher struct {
	EventWatcherConfig
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
		case ev := <-chanEvents:
			if ev.Raw.Removed {
				l.RemovedEventHandler.Handle(ev)
			} else {
				l.EventHandler.Handle(ev)
			}
		case <-l.end:
			logging.Info("Stop watching %v.", l.SfcContract)
			return nil
		}
	}
}

func NewSFCCrossCallRealtimeEventWatcher(context context.Context, eventHandler EventHandler, removedEventHandler EventHandler, contract *functioncall.Sfc,
	end chan bool) (*SFCCrossCallRealtimeEventWatcher, error) {
	if eventHandler == nil || removedEventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &SFCCrossCallRealtimeEventWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, EventHandler: eventHandler},
		RemovedEventHandler: removedEventHandler, SfcContract: contract, end: end}, nil
}

type BlockHeadProducer interface {
	SubscribeNewHead(ctx context.Context, ch chan<- *types.Header) (ethereum.Subscription, error)
}

// SFCCrossCallFinalisedEventWatcher listens for events from a Simple Function Call bridge and processes these events only once they are
// finalised. An event is considered finalised once it receives a configurable number of block confirmations.
// One block confirmation means the instant the transaction generating the event is mined,
// and is equivalent in behaviour to the SFCCrossCallRealtimeEventWatcher
type SFCCrossCallFinalisedEventWatcher struct {
	EventWatcherConfig
	// confirmationsForFinality refers to the number of block confirmations required before an event is considered finalised.
	confirmationsForFinality uint64
	SfcContract              *functioncall.Sfc
	nextBlockToProcess       uint64
	client                   BlockHeadProducer
	end                      chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given Simple Function Call contract.
// Once an events receives sufficient block confirmations, it is passed to an event handler for processing.
func (l *SFCCrossCallFinalisedEventWatcher) Watch() error {
	l.nextBlockToProcess = l.Start
	headers := make(chan *types.Header)

	sub, err := l.client.SubscribeNewHead(l.Context, headers)
	if err != nil {
		log.Fatalf("failed to subscribe to new block headers %v", err)
	}
	for {
		select {
		case err := <-sub.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case latestHead := <-headers:
			l.processFinalisedEventsWithRetry(latestHead)
		case <-l.end:
			logging.Info("Stop watching %v.", l.SfcContract)
			return nil
		}
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) processFinalisedEventsWithRetry(latest *types.Header) {
	latestBlock := latest.Number.Uint64()
	confirmations := (latestBlock - l.nextBlockToProcess) + 1

	logging.Debug("latest: '%d', next to process: '%d', confirmations: '%d', required confirmations: %d", latestBlock, l.nextBlockToProcess,
		confirmations, l.confirmationsForFinality)

	if latestBlock >= l.nextBlockToProcess && confirmations >= l.confirmationsForFinality {
		numFinalisedBlocks := confirmations - l.confirmationsForFinality
		startFinalisedBlock := l.nextBlockToProcess
		lastFinalisedBlock := startFinalisedBlock + numFinalisedBlocks

		logging.Debug("Finalising blocks %d-%d", startFinalisedBlock, lastFinalisedBlock)

		err := retry.Do(
			func() error {
				filterOpts := &bind.FilterOpts{Start: startFinalisedBlock, End: &lastFinalisedBlock, Context: l.Context}
				finalisedEvs, err := l.SfcContract.FilterCrossCall(filterOpts)
				if err != nil {
					return err
				}
				l.handleEvents(finalisedEvs)
				return nil
			},
			retry.OnRetry(func(attempt uint, err error) {
				logging.Error("Failed to process finalised events in blocks %d-%d. Retry attempt: %d, error: %v", startFinalisedBlock,
					lastFinalisedBlock, attempt, err)
			}))
		if err != nil {
			logging.Error("Failed to process finalised events in blocks %d-%d. Error: %v", startFinalisedBlock, lastFinalisedBlock, err)
		}
		l.nextBlockToProcess = lastFinalisedBlock + 1
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) handleEvents(events *functioncall.SfcCrossCallIterator) {
	for events.Next() {
		ev := events.Event
		l.EventHandler.Handle(ev)
	}
}

// NewSFCCrossCallFinalisedEventWatcher creates an SFCCrossCall event watcher that only returns events once they receive a configured number of
// confirmations. Note: 1 block confirmation means the instant the transaction generating the event is mined
func NewSFCCrossCallFinalisedEventWatcher(context context.Context, blockConfirmations uint64, eventHandler EventHandler,
	contract *functioncall.Sfc, start uint64, client BlockHeadProducer, end chan bool) (*SFCCrossCallFinalisedEventWatcher, error) {
	if blockConfirmations < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d", blockConfirmations)
	}
	if eventHandler == nil {
		return nil, fmt.Errorf("handler cannot be nil")
	}
	return &SFCCrossCallFinalisedEventWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, EventHandler: eventHandler, Start: start},
		SfcContract: contract, confirmationsForFinality: blockConfirmations, client: client, end: end}, nil
}
