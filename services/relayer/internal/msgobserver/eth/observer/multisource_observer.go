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
	"github.com/ipfs/go-datastore/query"
	"math/big"
	"strings"
	"time"

	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	datastore "github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
)

// Observation stores the information of an active Observation.
type Observation struct {
	Id           string      `json:"observer_id"`
	ChainID      string      `json:"chain_id"`
	ContractType string      `json:"contract_type"`
	ContractAddr string      `json:"contract_addr"`
	WatcherType  WatcherType `json:"watcher_type"`
	AP           string      `json:"ap"`
}

type WatcherType string

const (
	RealtimeWatcher  WatcherType = "realtime"
	FinalisedWatcher WatcherType = "finalised"
)

// MultiSourceObserver is an observer that can observer multiple different event sources.
// It creates and manages distinct SingleSourceObserver instances for each event source.
// The contract persists the list of event sources it tracks. In the event of a restart,
// the observer resumes Observation of the persisted sources.
type MultiSourceObserver struct {
	dsPath string
	mq     *mqserver.MQServer

	ds        datastore.Datastore
	observers map[string]*SingleSourceObserver
	running   bool
}

// NewMultiSourceObserver creates a new MultiSourceObserver instance
func NewMultiSourceObserver(dsPath string, mq *mqserver.MQServer) *MultiSourceObserver {
	return &MultiSourceObserver{dsPath: dsPath, mq: mq}
}

// Start starts the observer's routine.
func (o *MultiSourceObserver) Start() error {
	if o.running {
		logging.Info("Multi-observer already running. Start request ignored")
		return nil
	}

	var err error
	if o.ds == nil {
		dsOpts := badgerds.DefaultOptions
		dsOpts.SyncWrites = false
		dsOpts.Truncate = true
		o.ds, err = badgerds.NewDatastore(o.dsPath, &dsOpts)
		if err != nil {
			return err
		}
	}
	defer func() {
		err := o.ds.Close()
		if err != nil {
			logging.Error("error closing database connection: %v", err)
			return
		}
	}()

	results, err := o.ds.Query(context.Background(), query.Query{})
	if err != nil {
		logging.Error("error querying datastore for stored observers: %v", err)
		return err
	}

	logging.Info("starting saved observers...")
	for r := range results.Next() {
		data := r.Value
		obs := Observation{}
		err = json.Unmarshal(data, &obs)
		if err != nil {
			logging.Error("error deserialising saved observer, error: %v", err)
			return err
		}
		err := o.startObservation(obs)
		if err != nil {
			logging.Error("error starting observer for source '%s', error: %v", obs.Id, err)
			return err
		}
		logging.Info("started observer for source '%s'", obs.Id)
	}
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
}

// StartObservation starts a new observe.
func (o *MultiSourceObserver) StartObservation(chainID *big.Int, chainAP string, contractType string,
	contractAddr common.Address, watcherType WatcherType) error {
	// If an observer for the source exists, and is not currently running, start it.
	sourceId := GetSourceID(chainID, contractAddr, contractType, watcherType)
	if v, ok := o.observers[sourceId]; ok {
		if !v.IsRunning() {
			go func() {
				err := v.Start()
				if err != nil {
					logging.Error("error observing source '%s', error: %v", sourceId, err)
				}
			}()
		} else {
			logging.Info("Ignoring observer start request for source '%s'. " +
				"An observer for the source is already running.")
		}
		return nil
	}

	obs := Observation{
		Id:           sourceId,
		ChainID:      chainID.String(),
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

	return o.startObservation(obs)
}

func (o *MultiSourceObserver) IsRunning() bool {
	return o.running
}

func (o *MultiSourceObserver) startObservation(obs Observation) error {
	chainID, ok := big.NewInt(0).SetString(obs.ChainID, 10)
	if !ok {
		return fmt.Errorf("error in setting chain id")
	}
	// TODO: support different watcher types
	if strings.EqualFold(obs.ContractType, "SFC") {
		go o.startSFCObserver(obs.Id, chainID, obs.AP, common.HexToAddress(obs.ContractAddr))
	} else if strings.EqualFold(obs.ContractType, "GPACT") {
		go o.startGPACTObserver(obs.Id, chainID, obs.AP, common.HexToAddress(obs.ContractAddr))
	} else {
		return fmt.Errorf("contract type %v is not supported", obs.ContractType)
	}
	if !o.running {
		o.running = true
	}
	return nil
}

// StopObservation stops the observer associated with the given source if one exists and is currently running.
func (o *MultiSourceObserver) StopObservation(chainID *big.Int, contractType string, contractAddr common.Address,
	watcherType WatcherType) error {
	sourceId := GetSourceID(chainID, contractAddr, contractType, watcherType)
	if v, ok := o.observers[sourceId]; ok {
		if v.IsRunning() {
			v.Stop()
		} else {
			logging.Info("Ignoring observer stop request for source '%s'. " +
				"The observer for the source is not running.")
		}
		return nil
	}

	return fmt.Errorf("could not stop observation for source '%s'. An observer for the source was not found",
		sourceId)
}

// startSFCObserver starts an Observation for an SFC source event
func (o *MultiSourceObserver) startSFCObserver(observerId string, chainID *big.Int, chainAP string,
	contractAddr common.Address) {

	observerGen := func(cId *big.Int, addr common.Address, client *ethclient.Client) (*SingleSourceObserver, error) {
		sfc, err := functioncall.NewSfc(contractAddr, client)
		if err != nil {
			return nil, err
		}

		// TODO: make the type of watcher to use configurable as part of the observation params
		observer, err := o.createFinalisedEventObserver(chainID, contractAddr, sfc, o.mq, client)
		return observer, err
	}
	o.startObserver(observerId, chainID, chainAP, contractAddr, observerGen)
}

type observerCreator func(int2 *big.Int, address common.Address, client *ethclient.Client) (*SingleSourceObserver,
	error)

func (o *MultiSourceObserver) startObserver(observerId string, chainID *big.Int, chainAP string,
	contractAddr common.Address, fnObsCreator observerCreator) {
	err := withRetryWrapper(
		func() error {
			chain, err := ethclient.Dial(chainAP)
			if err != nil {
				return err
			}
			defer chain.Close()
			observer, err := fnObsCreator(chainID, contractAddr, chain)
			if err != nil {
				logging.Error(err.Error())
				return err
			}
			o.observers[observerId] = observer
			err = observer.Start()
			if err != nil {
				return err
			}
			return nil
		}, "SFC observer routine")

	if err != nil {
		logging.Error("Error starting SFC routine. All retry attempts failed.")
		return
	}

}
func withRetryWrapper(fn func() error, desc string) error {
	// TODO: make the retry parameters configurable
	return retry.Do(fn,
		retry.Attempts(5),
		retry.Delay(3*time.Second),
		retry.OnRetry(func(attempt uint, err error) {
			logging.Error("Error starting %s routine. Retry attempt: %d, error: %v", desc, attempt+1, err)
		}))
}

// startGPACTObserver starts an Observation for a new GPACT source event
func (o *MultiSourceObserver) startGPACTObserver(observerId string, chainID *big.Int, chainAP string,
	contractAddr common.Address) {

	observerGen := func(cId *big.Int, addr common.Address, client *ethclient.Client) (*SingleSourceObserver, error) {
		gpact, err := functioncall.NewGpact(addr, client)
		if err != nil {
			return nil, err
		}
		return NewGPACTRealtimeObserver(chainID, addr, gpact, o.mq)
	}
	o.startObserver(observerId, chainID, chainAP, contractAddr, observerGen)
}

func (o *MultiSourceObserver) createFinalisedEventObserver(chainId *big.Int, contractAddr common.Address,
	contract *functioncall.Sfc, mq mqserver.MessageQueue, client *ethclient.Client) (*SingleSourceObserver, error) {
	dsProgKey := datastore.NewKey(fmt.Sprintf("/%s/%s/last_finalised_block", chainId, contractAddr))
	watcherProgOpts := WatcherProgressDsOpts{o.ds, dsProgKey, DefaultRetryOptions}
	return NewSFCFinalisedObserver(chainId, contractAddr, contract, mq, 4, watcherProgOpts, client)
}

func (o *MultiSourceObserver) saveObservation(obs Observation) error {
	data, err := json.Marshal(obs)
	if err != nil {
		return err
	}
	return o.ds.Put(context.Background(), datastore.NewKey(obs.Id), data)
}
