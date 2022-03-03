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

	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/mqserver"
)

// SFCBridgeObserver listens to incoming events from an SFC contract, transforms them into relayer messages
// and then enqueues them onto a message queue them for further processing by other Relayer components
type SFCBridgeObserver struct {
	EventWatcher  EventWatcher
	EventHandler  EventHandler
	SourceNetwork string
}

func NewSFCBridgeRealtimeObserver(source string, sourceAddr string, contract *functioncall.Sfc, mq mqserver.MessageQueue) (*SFCBridgeObserver, error) {
	eventTransformer := NewSFCEventTransformer(source, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)
	removedEvHandler := NewLogEventHandler("removed event")

	// TODO: expose the start option of watcher opts
	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewSFCCrossCallRealtimeEventWatcher(watcherOpts, removedEvHandler, contract)
	if err != nil {
		return nil, err
	}

	return &SFCBridgeObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: source}, nil
}

func NewSFCBridgeFinalisedObserver(source string, sourceAddr string, contract *functioncall.Sfc, mq mqserver.MessageQueue,
	confirmationsForFinality uint64, watcherProgressOpts WatcherProgressDsOpts, client BlockHeadProducer) (
	*SFCBridgeObserver,
	error) {
	eventTransformer := NewSFCEventTransformer(source, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)

	// TODO: expose the start option of watcher opts
	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewSFCCrossCallFinalisedEventWatcher(watcherOpts, watcherProgressOpts, DefaultRetryOptions, confirmationsForFinality, contract, client)
	if err != nil {
		return nil, err
	}

	return &SFCBridgeObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: source}, nil
}

func (o *SFCBridgeObserver) Start() error {
	return o.EventWatcher.Watch()
}

func (o *SFCBridgeObserver) Stop() {
	if o.EventWatcher != nil {
		o.EventWatcher.StopWatcher()
	}
}

// GPACTBridgeObserver is a simple gpact bridge observer.
type GPACTBridgeObserver struct {
	EventWatcher  EventWatcher
	EventHandler  EventHandler
	SourceNetwork string
}

func NewGPACTBridgeRealtimeObserver(source string, sourceAddr string, contract *functioncall.Gpact, mq mqserver.MessageQueue) (*GPACTBridgeObserver, error) {
	eventTransformer := NewGPACTEventTransformer(source, sourceAddr)
	messageHandler := NewMessageEnqueueHandler(mq, DefaultRetryOptions)
	eventHandler := NewSimpleEventHandler(eventTransformer, messageHandler)

	watcherOpts := EventWatcherOpts{Context: context.Background(), EventHandler: eventHandler}
	eventWatcher, err := NewGPACTRealtimeEventWatcher(watcherOpts, contract)
	if err != nil {
		return nil, err
	}

	return &GPACTBridgeObserver{EventWatcher: eventWatcher, EventHandler: eventHandler, SourceNetwork: source}, nil
}

func (o *GPACTBridgeObserver) Start() error {
	return o.EventWatcher.Watch()
}

func (o *GPACTBridgeObserver) Stop() {
	if o.EventWatcher != nil {
		o.EventWatcher.StopWatcher()
	}
}
