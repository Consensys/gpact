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
	"github.com/ethereum/go-ethereum"
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
