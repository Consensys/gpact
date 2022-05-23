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

const (
	activeKey = "active"
)

// observation stores the information of an active observation.
type observation struct {
	ChainID      string `json:"chain_id"`
	ContractType string `json:"contract_type"`
	ContractAddr string `json:"contract_addr"`
	AP           string `json:"ap"`
}

// MultiSourceObserver is an observer that can observer multiple different event sources.
// It creates and manages distinct SingleSourceObserver instances for each event source.
// The contract persists the list of event sources it tracks. In the event of a failure related restart,
// the observer resumes observing the designated sources.
type MultiSourceObserver struct {
	dsPath string
	mq     *mqserver.MQServer

	ds            datastore.Datastore
	sfcObserver   *SingleSourceObserver
	gpactObserver *SingleSourceObserver
}

// NewMultiSourceObserver creates a new MultiSourceObserver instance
func NewMultiSourceObserver(dsPath string, mq *mqserver.MQServer) *MultiSourceObserver {
	return &MultiSourceObserver{dsPath: dsPath, mq: mq}
}

// Start starts the observer's routine.
func (o *MultiSourceObserver) Start() error {
	var err error
	if o.ds == nil {
		dsopts := badgerds.DefaultOptions
		dsopts.SyncWrites = false
		dsopts.Truncate = true
		o.ds, err = badgerds.NewDatastore(o.dsPath, &dsopts)
		if err != nil {
			return err
		}
		defer func() {
			if err != nil {
				o.ds.Close()
			}
		}()
		exists, err := o.ds.Has(context.Background(), datastore.NewKey(activeKey))
		if err != nil {
			return err
		}
		if exists {
			data, err := o.ds.Get(context.Background(), datastore.NewKey(activeKey))
			if err != nil {
				return err
			}
			val := observation{}
			err = json.Unmarshal(data, &val)
			if err != nil {
				return err
			}
			chainID, ok := big.NewInt(0).SetString(val.ChainID, 10)
			if !ok {
				err = fmt.Errorf("error in setting chain id")
				return err
			}
			if val.ContractType == "SFC" || val.ContractType == "sfc" {
				go o.routineSFC(chainID, val.AP, common.HexToAddress(val.ContractAddr))
			} else if val.ContractType == "GPACT" || val.ContractType == "gpact" {
				go o.routineGPACT(chainID, val.AP, common.HexToAddress(val.ContractAddr))
			} else {
				return fmt.Errorf("contract type %v is not supported", val.ContractType)
			}
		}
	}
	return nil
}

// Stop safely stops the observer.
func (o *MultiSourceObserver) Stop() {
	if o.sfcObserver != nil {
		o.sfcObserver.Stop()
		o.sfcObserver = nil
	}
	if o.gpactObserver != nil {
		o.gpactObserver.Stop()
		o.gpactObserver = nil
	}
}

// StartObserve starts a new observe.
func (o *MultiSourceObserver) StartObserve(chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) error {
	// First, close any existing observe.
	o.Stop()

	val := observation{
		ChainID:      chainID.String(),
		ContractType: contractType,
		ContractAddr: contractAddr.String(),
		AP:           chainAP,
	}
	data, err := json.Marshal(val)
	if err != nil {
		return err
	}
	err = o.ds.Put(context.Background(), datastore.NewKey(activeKey), data)
	if err != nil {
		return err
	}
	if contractType == "SFC" {
		go o.routineSFC(chainID, chainAP, contractAddr)
	} else if contractType == "GPACT" {
		go o.routineGPACT(chainID, chainAP, contractAddr)
	} else {
		return fmt.Errorf("contract type %v is not supported", contractType)
	}

	return nil
}

// StopObserve stops observe.
func (o *MultiSourceObserver) StopObserve() error {
	// Close any existing observe.
	o.Stop()

	err := o.ds.Delete(context.Background(), datastore.NewKey(activeKey))
	if err != nil {
		return err
	}
	return nil
}

// routineSFC starts an observation for an SFC source event
func (o *MultiSourceObserver) routineSFC(chainID *big.Int, chainAP string, contractAddr common.Address) {
	for {
		chain, err := ethclient.Dial(chainAP)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		defer chain.Close()

		sfc, err := functioncall.NewSfc(contractAddr, chain)
		if err != nil {
			logging.Error(err.Error())
			return
		}

		observer, err := o.createFinalisedEventObserver(chainID, contractAddr, sfc, o.mq, chain)

		if err != nil {
			logging.Error(err.Error())
			return
		}
		o.sfcObserver = observer
		if observer.Start() == nil {
			break
		} else {
			logging.Warn("Error in observing event. Retry in 3 seconds...")
			chain.Close()
			time.Sleep(3 * time.Second)
		}
	}
}

// routineGPACT starts an observation for a new GPACT source event
func (o *MultiSourceObserver) routineGPACT(chainID *big.Int, chainAP string, addr common.Address) {
	for {
		chain, err := ethclient.Dial(chainAP)
		if err != nil {
			logging.Error("Error connecting to Chain AP: %v", err.Error())
			return
		}
		defer chain.Close()

		gpact, err := functioncall.NewGpact(addr, chain)
		if err != nil {
			logging.Error("Error creating GPACT handler: %v", err.Error())
			return
		}

		observer, err := NewGPACTRealtimeObserver(chainID, addr, gpact, o.mq)
		if err != nil {
			logging.Error("Error creating observer for Chain: %v, Contract: %v, Error: %v", chainID.String(), addr.String(), err.Error())
			return
		}
		o.gpactObserver = observer
		if observer.Start() == nil {
			break
		} else {
			logging.Warn("Error in observing event. Retry in 3 seconds...")
			chain.Close()
			time.Sleep(3 * time.Second)
		}
	}
}

func (o *MultiSourceObserver) createFinalisedEventObserver(chainId *big.Int, contractAddr common.Address,
	contract *functioncall.Sfc, mq mqserver.MessageQueue, client *ethclient.Client) (*SingleSourceObserver, error) {
	dsProgKey := datastore.NewKey(fmt.Sprintf("/%s/%s/last_finalised_block", chainId, contractAddr))
	watcherProgOpts := WatcherProgressDsOpts{o.ds, dsProgKey, DefaultRetryOptions}
	return NewSFCFinalisedObserver(chainId, contractAddr, contract, mq, 4, watcherProgOpts, client)
}

// dsKey gets the datastore key from given chainID and contract address.
func dsKey(chainID *big.Int, contractAddr common.Address) datastore.Key {
	return datastore.NewKey(fmt.Sprintf("%v-%v", chainID.String(), contractAddr.String()))
}

// splitKey splits key to chainID and contract address.
func splitKey(key string) (*big.Int, common.Address) {
	res := strings.Split(key, "-")
	chainID, _ := big.NewInt(0).SetString(res[0], 10)
	addr := common.HexToAddress(res[1])
	return chainID, addr
}
