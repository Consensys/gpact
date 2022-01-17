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
	Watch()
}

type EventWatcherConfig struct {
	Start   uint64
	Context context.Context
	Handler EventHandler
}

// SFCCrossCallRealtimeEventWatcher subscribes and listens to events from a Simple Function Call bridge contract.
// The events produced by this watcher are generated the instant they are mined (i.e. 1 confirmation).
// Note: The watcher does not check to see if the event is affected by any reorgs.
type SFCCrossCallRealtimeEventWatcher struct {
	EventWatcherConfig
	SfcContract *functioncall.Sfc
	end         chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given Simple Function Call contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network is not successful.
func (l *SFCCrossCallRealtimeEventWatcher) Watch() {
	opts := bind.WatchOpts{Start: &l.Start, Context: l.Context}
	chanEvents := make(chan *functioncall.SfcCrossCall)
	sub, err := l.SfcContract.WatchCrossCall(&opts, chanEvents)
	if err != nil {
		log.Fatalf("failed to subscribe to crosschaincall event %v", err)
	}
	l.start(sub, chanEvents)
}

func (l *SFCCrossCallRealtimeEventWatcher) start(sub event.Subscription, chanEvents <-chan *functioncall.SfcCrossCall) {
	logging.Info("Start watching %v...", l.SfcContract)
	for {
		select {
		case err := <-sub.Err():
			// TODO: communicate this to the calling context
			logging.Error("error in log subscription %v", err)
		case ev := <-chanEvents:
			l.Handler.Handle(ev)
		case <-l.end:
			logging.Info("Stop watching %v.", l.SfcContract)
			return
		}
	}
}

func NewSFCCrossCallRealtimeEventWatcher(context context.Context, handler EventHandler, contract *functioncall.Sfc, end chan bool) *SFCCrossCallRealtimeEventWatcher {
	return &SFCCrossCallRealtimeEventWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler}, SfcContract: contract, end: end}
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
func (l *SFCCrossCallFinalisedEventWatcher) Watch() {
	l.nextBlockToProcess = l.Start
	headers := make(chan *types.Header)

	sub, err := l.client.SubscribeNewHead(l.Context, headers)
	if err != nil {
		log.Fatalf("failed to subscribe to new block headers %v", err)
	}
	for {
		select {
		case err := <-sub.Err():
			// TODO: communicate this to the calling context
			logging.Error("error in log subscription %v", err)
		case latestHead := <-headers:
			// TODO: communicate err to the calling context
			l.processFinalisedEvents(latestHead)
		}
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) processFinalisedEvents(latest *types.Header) error {
	// TODO: maintain persistent state about last finalised block and observed messages
	latestBlock := latest.Number.Uint64()
	confirmations := (latestBlock - l.nextBlockToProcess) + 1

	logging.Debug("latest: '%d', next to process: '%d', confirmations: '%d', required confirmations: %d", latestBlock, l.nextBlockToProcess,
		confirmations, l.confirmationsForFinality)

	if latestBlock >= l.nextBlockToProcess && confirmations >= l.confirmationsForFinality {
		numFinalisedBlocks := confirmations - l.confirmationsForFinality
		startFinalisedBlock := l.nextBlockToProcess
		lastFinalisedBlock := startFinalisedBlock + numFinalisedBlocks

		logging.Debug("Finalising blocks '%d' to '%d'", startFinalisedBlock, lastFinalisedBlock)

		filterOpts := &bind.FilterOpts{Start: startFinalisedBlock, End: &lastFinalisedBlock, Context: l.Context}
		finalisedEvs, err := l.SfcContract.FilterCrossCall(filterOpts)
		if err != nil {
			logging.Error("error filtering logs from block: %d to %d, error: %v", startFinalisedBlock, lastFinalisedBlock, err)
			return err
		}

		// TODO: Handle partial failure scenarios when handling events.
		// Three cases to consider:
		// 1. A range of blocks are being processed and some blocks fail
		// 2. A block is being processed and some events in the block fail processing
		// 3. Combination of 1 and 2
		err = l.handleEvents(finalisedEvs)
		if err != nil {
			return err
		}

		l.nextBlockToProcess = lastFinalisedBlock + 1
	}
	return nil
}

func (l *SFCCrossCallFinalisedEventWatcher) handleEvents(events *functioncall.SfcCrossCallIterator) error {
	for events.Next() {
		ev := events.Event
		err := l.Handler.Handle(ev)
		if err != nil {
			logging.Error("failed to handle event: %v, error: %v", ev, err)
			return err
		}
	}
	return nil
}

// NewSFCCrossCallFinalisedEventWatcher creates an SFCCrossCall event watcher that only returns events once they receive a configured number of
// confirmations. Note: 1 block confirmation means the instant the transaction generating the event is mined
func NewSFCCrossCallFinalisedEventWatcher(context context.Context, blockConfirmations uint64, handler EventHandler, contract *functioncall.Sfc,
	start uint64, client BlockHeadProducer, end chan bool) (*SFCCrossCallFinalisedEventWatcher, error) {
	if blockConfirmations < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d", blockConfirmations)
	}
	return &SFCCrossCallFinalisedEventWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler, Start: start},
		SfcContract: contract, confirmationsForFinality: blockConfirmations, client: client, end: end}, nil
}
