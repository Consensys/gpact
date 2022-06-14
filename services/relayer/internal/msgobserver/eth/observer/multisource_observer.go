package observer

/*
 * Copyright 2022 ConsenSys Software Inc
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
	"encoding/json"
	"fmt"
	"github.com/avast/retry-go"
	"github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ipfs/go-datastore/query"
	badgerds "github.com/ipfs/go-ds-badger"
	"math/big"
	"strings"
	"time"

	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	datastore "github.com/ipfs/go-datastore"
)

// observation stores details about an event source to observe
type observation struct {
	ObserverId   string      `json:"observer_id"`
	ChainId      string      `json:"chain_id"`
	ContractType string      `json:"contract_type"`
	ContractAddr string      `json:"contract_addr"`
	WatcherType  WatcherType `json:"watcher_type"`
	AP           string      `json:"ap"`
}

type WatcherType string

type Backend interface {
	ethereum.ChainReader
	bind.ContractBackend
}

type ClientFactory = func(string) (Backend, error)

const (
	RealtimeWatcher  WatcherType = "realtime"
	FinalisedWatcher WatcherType = "finalised"
)

// MultiSourceObserver is an observer that can observer multiple different event sources.
// It creates and manages distinct SingleSourceObserver instances for each event source.
// The contract persists the list of event sources it tracks. In the event of a restart,
// the observer resumes observation of the persisted sources.
type MultiSourceObserver struct {
	mq            mqserver.MessageQueue
	ds            datastore.Datastore
	observers     map[string]*SingleSourceObserver
	clientFactory ClientFactory
	running       bool
}

// NewMultiSourceObserver creates a new MultiSourceObserver instance
func NewMultiSourceObserver(dsPath string, mq mqserver.MessageQueue, clientFactory ClientFactory) (*MultiSourceObserver, error) {
	if clientFactory == nil {
		clientFactory = defaultBackendFactory
	}
	observer := &MultiSourceObserver{mq: mq, observers: make(map[string]*SingleSourceObserver),
		clientFactory: clientFactory}

	dsOpts := badgerds.DefaultOptions
	dsOpts.SyncWrites = false
	dsOpts.Truncate = true
	var err error
	observer.ds, err = badgerds.NewDatastore(dsPath, &dsOpts)
	if err != nil {
		return nil, err
	}

	return observer, nil
}

func defaultBackendFactory(url string) (Backend, error) {
	return ethclient.Dial(url)
}

// Start starts the observer's routine.
func (o *MultiSourceObserver) Start() error {
	if o.running {
		logging.Info("Multi-observer already running. Start request ignored")
		return nil
	}

	logging.Info("Querying for saved observations...")
	results, err := o.ds.Query(context.Background(), query.Query{})
	if err != nil {
		logging.Error("Error querying datastore for stored observations: %v", err)
		return err
	}

	logging.Info("Starting saved observers...")
	for r := range results.Next() {
		data := r.Value
		obs := observation{}
		err = json.Unmarshal(data, &obs)
		if err != nil {
			logging.Error("Error deserialising saved observer, error: %v", err)
			return err
		}
		err := o.startNewObservation(obs)
		if err != nil {
			logging.Error("Error starting observer for source '%s', error: %v", obs.ObserverId, err)
			return err
		}
		logging.Info("Started observer for source '%s'", obs.ObserverId)
	}
	logging.Info("Multi-source observer started")
	return nil
}

// Stop all observer that the multi-source observer manages
func (o *MultiSourceObserver) Stop() {
	if !o.running {
		logging.Info("Multi-observer is not running. Stop request ignored")
		return
	}
	for k := range o.observers {
		o.observers[k].Stop()
	}
	o.running = false
	err := o.ds.Close()
	if err != nil {
		logging.Error("error closing database connection: %v", err)
		return
	}
	o.ds = nil
}

func (o *MultiSourceObserver) IsRunning() bool {
	return o.running
}

// StartObservation starts a new observe.
func (o *MultiSourceObserver) StartObservation(chainID *big.Int, chainAP string, contractType string,
	contractAddr common.Address, watcherType WatcherType) error {
	// If an observer for the source exists, and is not currently running, start it.
	sourceId := GetSourceID(chainID, contractAddr, contractType, watcherType)
	logging.Info("Starting observation '%s'", sourceId)
	if v, ok := o.observers[sourceId]; ok {
		if !v.IsRunning() {
			logging.Info("observer with id '%s' already exists. starting existing observer..", sourceId)
			go func() {
				err := v.Start()
				if err != nil {
					logging.Error("error observing source '%s', error: %v", sourceId, err)
				}
			}()
		} else {
			logging.Info("Ignoring observer start request for source '%s'. "+
				"An observer for the source is already running.", sourceId)
		}
		return nil
	}

	obs := observation{
		ObserverId:   sourceId,
		ChainId:      chainID.String(),
		ContractType: contractType,
		ContractAddr: contractAddr.String(),
		WatcherType:  watcherType,
		AP:           chainAP,
	}

	// save the observation to the database for recovery purposes
	err := o.saveObservation(obs)
	if err != nil {
		return err
	}

	return o.startNewObservation(obs)
}

// StopObservation stops the observer associated with the given source if one exists and is currently running.
func (o *MultiSourceObserver) StopObservation(chainID *big.Int, contractType string, contractAddr common.Address,
	watcherType WatcherType) error {
	sourceId := GetSourceID(chainID, contractAddr, contractType, watcherType)
	if v, ok := o.observers[sourceId]; ok {
		if v.IsRunning() {
			v.Stop()
		} else {
			logging.Info("Ignoring observer stop request for source '%s'. "+
				"The observer for the source is not running.", sourceId)
		}
		return nil
	}

	return fmt.Errorf("could not stop observation for source '%s'. An observer for the source was not found",
		sourceId)
}

func (o *MultiSourceObserver) startNewObservation(obs observation) error {
	chainID, ok := big.NewInt(0).SetString(obs.ChainId, 10)
	if !ok {
		return fmt.Errorf("error in setting chain id")
	}
	// TODO: support different watcher types
	if strings.EqualFold(obs.ContractType, "SFC") {
		go o.startNewSFCObserver(obs.ObserverId, chainID, obs.AP, common.HexToAddress(obs.ContractAddr), obs.WatcherType)
	} else if strings.EqualFold(obs.ContractType, "GPACT") {
		go o.startNewGPACTObserver(obs.ObserverId, chainID, obs.AP, common.HexToAddress(obs.ContractAddr), obs.WatcherType)
	} else {
		return fmt.Errorf("contract type %v is not supported", obs.ContractType)
	}
	if !o.running {
		o.running = true
	}
	return nil
}

// startNewSFCObserver starts an observation for an SFC source event
func (o *MultiSourceObserver) startNewSFCObserver(observerId string, chainId *big.Int, chainAP string, contractAddr common.Address, watcherType WatcherType) {
	observerGen := func(cId *big.Int, addr common.Address, client Backend) (*SingleSourceObserver, error) {
		sfc, err := functioncall.NewSfc(contractAddr, client)
		if err != nil {
			return nil, err
		}

		if watcherType == FinalisedWatcher {
			dsProgKey := datastore.NewKey(fmt.Sprintf("/%s/%s/last_finalised_block", chainId, contractAddr))
			watcherProgOpts := WatcherProgressDsOpts{o.ds, dsProgKey, DefaultRetryOptions}
			logging.Info("Starting finalised sfc observer '%s'...", observerId)
			// TODO: number of confirmations to wait for should be configurable
			return NewSFCFinalisedObserver(chainId, contractAddr, sfc, o.mq, 4, watcherProgOpts, client)
		} else {
			logging.Info("Starting realtime sfc observer '%s'...", observerId)
			return NewSFCRealtimeObserver(chainId, contractAddr, sfc, o.mq)
		}
	}
	o.startNewObserver(observerId, chainId, chainAP, contractAddr, observerGen)
}

// startNewGPACTObserver starts an observation for a new GPACT source event
func (o *MultiSourceObserver) startNewGPACTObserver(observerId string, chainId *big.Int, chainAP string, contractAddr common.Address, watcherType WatcherType) {
	observerGen := func(cId *big.Int, addr common.Address, client Backend) (*SingleSourceObserver, error) {
		gpact, err := functioncall.NewGpact(addr, client)
		if err != nil {
			return nil, err
		}
		if watcherType == FinalisedWatcher {
			dsProgKey := datastore.NewKey(fmt.Sprintf("/%s/%s/last_finalised_block", chainId, contractAddr))
			watcherProgOpts := WatcherProgressDsOpts{o.ds, dsProgKey, DefaultRetryOptions}
			logging.Info("Starting finalised gpact observer '%s'...", observerId)
			return NewGPACTFinalisedObserver(chainId, addr, gpact, o.mq, 4, watcherProgOpts, client)
		} else {
			logging.Info("Starting realtime gpact observer '%s'...", observerId)
			return NewGPACTRealtimeObserver(chainId, addr, gpact, o.mq)
		}
	}
	o.startNewObserver(observerId, chainId, chainAP, contractAddr, observerGen)
}

type observerFactory func(int2 *big.Int, address common.Address, client Backend) (*SingleSourceObserver,
	error)

func (o *MultiSourceObserver) startNewObserver(observerId string, chainID *big.Int, chainAP string,
	contractAddr common.Address, fnObsFactory observerFactory) {
	err := withRetryWrapper(
		func() error {
			chain, err := o.clientFactory(chainAP)
			if err != nil {
				logging.Error("error creating backend: %v", err)
				return err
			}
			observer, err := fnObsFactory(chainID, contractAddr, chain)
			if err != nil {
				logging.Error(err.Error())
				return err
			}
			o.observers[observerId] = observer
			err = observer.Start()
			if err != nil {
				return err
			}
			logging.Info("successfully started new observer '%s'", observerId)
			return nil
		}, fmt.Sprintf("observer id: %s", observerId))

	if err != nil {
		logging.Error("Error starting observer '%s'. All retry attempts failed.", observerId)
		return
	}

}

func (o *MultiSourceObserver) saveObservation(obs observation) error {
	data, err := json.Marshal(obs)
	if err != nil {
		return err
	}
	return o.ds.Put(context.Background(), datastore.NewKey(obs.ObserverId), data)
}

func withRetryWrapper(fn func() error, desc string) error {
	// TODO: make the retry parameters configurable
	return retry.Do(fn,
		retry.Attempts(5),
		retry.Delay(3*time.Second),
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error starting %s, attempt: %d, error: %v", desc, attempt+1, err)
		}))
}
