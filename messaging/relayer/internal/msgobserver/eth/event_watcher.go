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
	"log"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/event"
)

// EventWatcher listens to blockchain events
type EventWatcher interface {
	Watch()
}

type EventWatcherConfig struct {
	Start   *uint64
	Context context.Context
	Handler EventHandler
}

// SFCCrossCallWatcher subscribes and listens to events from a simple-function-call bridge contract
type SFCCrossCallWatcher struct {
	EventWatcherConfig
	SfcContract *functioncall.Sfc

	end chan bool
}

// Watch subscribes and starts listening to 'CrossCall' events from a given simple-function-call contract.
// Events received are passed to an event handler for processing.
// The method fails if subscribing to the event with the underlying network is not successful.
func (l *SFCCrossCallWatcher) Watch() {
	opts := bind.WatchOpts{Start: l.Start, Context: l.Context}
	chanEvents := make(chan *functioncall.SfcCrossCall)
	sub, err := l.SfcContract.WatchCrossCall(&opts, chanEvents)
	if err != nil {
		log.Fatalf("failed to subscribe to crosschaincall event %v", err)
	}
	l.start(sub, chanEvents)
}

func (l *SFCCrossCallWatcher) start(sub event.Subscription, chanEvents <-chan *functioncall.SfcCrossCall) {
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

func NewSFCCrossCallWatcher(context context.Context, handler EventHandler, contract *functioncall.Sfc, end chan bool) *SFCCrossCallWatcher {
	return &SFCCrossCallWatcher{EventWatcherConfig: EventWatcherConfig{Context: context, Handler: handler}, SfcContract: contract, end: end}
}
