package observer

/*
 * Copyright 2022 ConsenSys Software Inc.
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
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ipfs/go-datastore"
)

// EventWatcher listens to blockchain events
type EventWatcher interface {
	// Watch starts the listening to relevant events
	Watch() error

	// StopWatcher stops the listening to events
	StopWatcher()
}

// EventWatcherOpts encapsulates common options used to configure an EventWatcher
type EventWatcherOpts struct {
	// Start is the block number to start watching from. This might be superseded by a saved checkpoint if one is available.
	Start uint64
	// EventHandler the handler that will process each blockchain event
	EventHandler EventHandler
	Context      context.Context
}

type BlockHeadProducer interface {
	SubscribeNewHead(ctx context.Context, ch chan<- *types.Header) (ethereum.Subscription, error)
}

// WatcherProgressDsOpts encapsulates configuration details for persisting the progress of a watcher
type WatcherProgressDsOpts struct {
	ds               datastore.Datastore // datastore for persisting the progress of a watcher
	dsProgKey        datastore.Key       // specific key used in a KV datastore, for storing the latest progress
	FailureRetryOpts                     // configuration for how retries will be performed if persisting progress fails
}

// FinalisedEventWatcher listens to events from a bridge and processes them only once they are
// 'finalised'. An event is considered 'finalised' once it receives a configurable number of block confirmations.
// An event has one block confirmation the instant it is mined into a block.
type FinalisedEventWatcher struct {
	EventWatcherOpts
	WatcherProgressOpts WatcherProgressDsOpts
	// EventHandleRetryOpts specifies how retries will be attempted if fetching or processing events fails
	EventHandleRetryOpts     FailureRetryOpts
	confirmationsForFinality uint64 //  the number of block confirmations required before an event is considered 'final'.
	client                   BlockHeadProducer
	end                      chan bool
	nextBlockToProcess       uint64
	fetchAndProcessEvsFunc   func(opts *bind.FilterOpts) error
}

func (l *FinalisedEventWatcher) StopWatcher() {
	l.end <- true
}

// Watch subscribes and starts listening to 'CrossCall' events from a given 'Simple Function Call' contract.
// Once an events receives sufficient block confirmations, it is passed to an event handler for processing.
func (l *FinalisedEventWatcher) Watch() error {
	headers := make(chan *types.Header)
	sub, err := l.client.SubscribeNewHead(l.Context, headers)
	if err != nil {
		return fmt.Errorf("failed to subscribe to new block headers %v", err)
	}

	// resume processing from last saved progress point if available.
	//if not, use the FinalisedEventWatcher.Start value provided
	l.setNextBlockToProcess()

	for {
		select {
		case err := <-sub.Err():
			return fmt.Errorf("error in log subscription %v", err)
		case <-l.end:
			logging.Info("Watcher stopped...")
			return nil
		case latestHead := <-headers:
			l.processFinalisedEvents(latestHead)
		}
	}
}

func (l *FinalisedEventWatcher) GetNextBlockToProcess() uint64 {
	return l.nextBlockToProcess
}

// GetSavedProgress fetches the last processed block number that has been persisted to the datastore.
// This value is updated in the datastore each time the watcher processes new blocks ( see `processFinalisedEvents(...)`).
// returns an error if querying the datastore or deserialising the result fails.
func (l *FinalisedEventWatcher) GetSavedProgress() (uint64, error) {
	p, err := l.WatcherProgressOpts.ds.Get(l.Context, l.WatcherProgressOpts.dsProgKey)
	if err != nil {
		return 0, err
	}
	progress, err := bytesToUint(p)
	if err != nil {
		return 0, err
	}
	return progress, nil
}

// processFinalisedEvents fetches all relevant events between the last processed block and the provided latest block that have received a sufficient
// number of confirmations, and passes them to the event handler for processing. If fetching or processing these events fails, the action is retried.
// If the function completes successfully, the last processed block is updated in the datastore.
func (l *FinalisedEventWatcher) processFinalisedEvents(latest *types.Header) {
	latestBlock := latest.Number.Uint64()
	confirmations := (latestBlock - l.nextBlockToProcess) + 1

	logging.Debug("latest: '%d', next to process: '%d', confirmations: '%d', required confirmations: %d", latestBlock, l.nextBlockToProcess,
		confirmations, l.confirmationsForFinality)

	if latestBlock >= l.nextBlockToProcess && confirmations >= l.confirmationsForFinality {
		numFinalisedBlocks := confirmations - l.confirmationsForFinality
		lastFinalisedBlock := l.nextBlockToProcess + numFinalisedBlocks

		err := l.handleEventsWithRetry(l.nextBlockToProcess, lastFinalisedBlock)
		if err == nil {
			l.nextBlockToProcess = lastFinalisedBlock + 1
			l.saveProgressToDsWithRetry(lastFinalisedBlock)
		} else {
			logging.Error("Failed to process finalised events in blocks %d-%d. Error: %v", l.nextBlockToProcess, lastFinalisedBlock, err)
		}
	}
}

// handleEventsWithRetry fetches relevant events that occurred within the given block range and passes them to an event handler for processing.
// If fetching the events from the network fails, the action is retried.
func (l *FinalisedEventWatcher) handleEventsWithRetry(startBlock uint64, lastBlock uint64) error {
	logging.Debug("Processing events from blocks %d to %d", startBlock, lastBlock)
	return retry.Do(
		func() error {
			filterOpts := &bind.FilterOpts{Start: startBlock, End: &lastBlock, Context: l.Context}
			return l.fetchAndProcessEvsFunc(filterOpts)
		},
		retry.Attempts(l.EventHandleRetryOpts.RetryAttempts),
		retry.Delay(l.EventHandleRetryOpts.RetryDelay),
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error processing finalised events in blocks %d-%d. Retry attempt: %d, error: %v", startBlock,
				lastBlock, attempt+1, err)
		}))
}

// saveProgressToDsWithRetry updates the progress of the watcher in the datastore and retries if it fails.
// This information is useful to track the progress of the observer and to resume processing if the observer fails or is restarted.
func (l *FinalisedEventWatcher) saveProgressToDsWithRetry(lastFinalisedBlock uint64) {
	err := retry.Do(
		func() error {
			return l.WatcherProgressOpts.ds.Put(context.Background(), l.WatcherProgressOpts.dsProgKey, uintToBytes(lastFinalisedBlock))
		},
		retry.Attempts(l.WatcherProgressOpts.RetryAttempts),
		retry.Delay(l.WatcherProgressOpts.RetryDelay),
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error persisting watcher progress to db. Last finalised block: %d, Retry Attempt: %d, Error: %v",
				lastFinalisedBlock, attempt+1, err)
		}))
	if err != nil {
		logging.Error("Failed to persist watcher progress to db. Last finalised block: %d, Error: %v", lastFinalisedBlock, err)
	}
}

// setNextBlockToProcess sets the next block to process to either the progress saved in the datastore or to start value provided.
// The method first checks to see if there is information about the last processed block stored in the data store.
// If there is, the next block to process is set to the block after the last processed block.
// If not it defaults to the using the start value provided to the watcher (FinalisedEventWatcher.Start)
func (l *FinalisedEventWatcher) setNextBlockToProcess() {
	lastProcessed, err := l.GetSavedProgress()
	if err != nil {
		logging.Info("Failed to fetch last processed block from datastore. Defaulting to provided start point: %d", l.Start)
		l.nextBlockToProcess = l.Start
	} else {
		l.nextBlockToProcess = lastProcessed + 1
	}
}
