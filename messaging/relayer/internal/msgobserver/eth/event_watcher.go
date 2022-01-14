package eth

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

// SFCCrossCallRealtimeEventWatcher subscribes and listens to events from a simple-function-call bridge contract
type SFCCrossCallRealtimeEventWatcher struct {
	EventWatcherConfig
	SfcContract *functioncall.Sfc
	end         chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given simple-function-call contract.
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
		case log := <-chanEvents:
			l.Handler.Handle(log)
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

type SFCCrossCallFinalisedEventWatcher struct {
	EventWatcherConfig
	// confirmationsForFinality refers to the number of block confirmations required before an event is considered finalised
	confirmationsForFinality uint64
	SfcContract              *functioncall.Sfc
	nextBlockToProcess       uint64
	client                   BlockHeadProducer
	end                      chan bool
}

func (l *SFCCrossCallFinalisedEventWatcher) Watch() {
	l.nextBlockToProcess = l.Start
	headers := make(chan *types.Header)

	sub, err := l.client.SubscribeNewHead(l.Context, headers)
	if err != nil {
		log.Fatalf("failed to subscribe to new block headers %v", err)
	}
	// TODO: better handling and retries
	for {
		select {
		case err := <-sub.Err():
			// TODO: communicate this to the calling context
			logging.Error("error in log subscription %v", err)
		case latestHead := <-headers:
			l.processFinalisedEvents(latestHead)
		}
	}
}

func (l *SFCCrossCallFinalisedEventWatcher) processFinalisedEvents(latest *types.Header) {
	latestBlock := latest.Number.Uint64()

	if latestBlock < l.nextBlockToProcess {
		return
	}

	confirmations := (latestBlock - l.nextBlockToProcess) + 1
	logging.Debug("Latest: '%d', Next to Process: '%d', Confirmations: '%d', Required Confirmations: %d", latestBlock, l.nextBlockToProcess,
		confirmations, l.confirmationsForFinality)

	if latestBlock >= l.nextBlockToProcess && confirmations >= l.confirmationsForFinality {
		numFinalisedBlocks := confirmations - l.confirmationsForFinality
		blockFrom := l.nextBlockToProcess
		blockTo := blockFrom + numFinalisedBlocks
		logging.Debug("Finalising blocks '%d' to '%d'", blockFrom, blockTo)

		opts := bind.FilterOpts{Start: blockFrom, End: &blockTo, Context: l.Context}
		eventsIter, err := l.SfcContract.FilterCrossCall(&opts)
		if err != nil {
			//TODO: better error handling and retries
			logging.Error("error filtering logs from block: %d to %d, error: %v", blockFrom, blockTo, err)
			return
		}

		for eventsIter.Next() {
			ev := eventsIter.Event
			err := l.Handler.Handle(ev)
			if err != nil {
				//TODO: better error handling
				logging.Error("failed to handle event: %v, error: %v", ev, err)
			}
		}
		l.nextBlockToProcess = blockTo + 1
	}
}
func NewSFCCrossCallFinalisedEventWatcher(context context.Context, blockConfirmations uint64, handler EventHandler, contract *functioncall.Sfc,
	start uint64, client BlockHeadProducer, end chan bool) (*SFCCrossCallFinalisedEventWatcher, error) {

	if blockConfirmations < 1 {
		return nil, fmt.Errorf("block confirmationsForFinality cannot be less than 1. supplied value: %d", blockConfirmations)
	}
	return &SFCCrossCallFinalisedEventWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler, Start: start},
		SfcContract: contract, confirmationsForFinality: blockConfirmations, client: client, end: end}, nil
}
