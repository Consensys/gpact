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
	"github.com/ethereum/go-ethereum/common"
	"math/big"

	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/mqserver"
)

// Observer is an interface for the observer.
type Observer interface {
	// Start starts the observer's routine.
	Start() error

	// Stop safely stops the observer.
	Stop()

	// StartObserve starts a new observe.
	StartObserve(chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) error

	// StopObserve stops observe.
	StopObserve() error
}

// SingleSourceObserver listens to incoming events from a given bridge contract, transforms them into relayer messages
// and then enqueues them onto a message queue them for further processing by other Relayer components
type SingleSourceObserver struct {
	SourceId      string
	SourceNetwork *big.Int
	EventWatcher  EventWatcher
	EventHandler  EventHandler
}

func (o *SingleSourceObserver) Start() error {
	return o.EventWatcher.Watch()
}

func (o *SingleSourceObserver) Stop() {
	if o.EventWatcher != nil {
		o.EventWatcher.StopWatcher()
	}
}

func NewSFCRealtimeObserver(chainId *big.Int, sourceAddr common.Address, contract *functioncall.Sfc,
	mq mqserver.MessageQueue) (*SingleSourceObserver, error) {
	eventTransformer := NewSFCEventTransformer(chainId, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)
	removedEvHandler := NewLogEventHandler("removed event")

	// TODO: expose the start option of watcher opts
	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewSFCCrossCallRealtimeEventWatcher(watcherOpts, removedEvHandler, contract)
	if err != nil {
		return nil, err
	}

	sourceId := fmt.Sprintf("%s:%s:%s", chainId, sourceAddr.String(), "sfc")
	return &SingleSourceObserver{SourceId: sourceId, EventWatcher: eventWatcher, EventHandler: eventHandler,
			SourceNetwork: chainId},
		nil
}

func NewSFCFinalisedObserver(chainId *big.Int, sourceAddr common.Address, contract *functioncall.Sfc,
	mq mqserver.MessageQueue,
	confirmationsForFinality uint64, watcherProgressOpts WatcherProgressDsOpts, client BlockHeadProducer) (
	*SingleSourceObserver,
	error) {
	eventTransformer := NewSFCEventTransformer(chainId, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)

	// TODO: expose the start option of watcher opts
	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewSFCCrossCallFinalisedEventWatcher(watcherOpts, watcherProgressOpts, DefaultRetryOptions, confirmationsForFinality, contract, client)
	if err != nil {
		return nil, err
	}

	return &SingleSourceObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: chainId}, nil
}

func NewGPACTRealtimeObserver(chainId *big.Int, sourceAddr common.Address, contract *functioncall.Gpact,
	mq mqserver.MessageQueue) (*SingleSourceObserver, error) {
	eventTransformer := NewGPACTEventTransformer(chainId, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)
	removedEvHandler := NewLogEventHandler("removed event")

	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewGPACTRealtimeEventWatcher(watcherOpts, removedEvHandler, contract)
	if err != nil {
		return nil, err
	}

	return &SingleSourceObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: chainId}, nil
}
