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
	"github.com/ipfs/go-datastore"
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

// ObserverImplV1 implements observer.
type ObserverImplV1 struct {
	path string
	mq   *mqserver.MQServer

	ds            datastore.Datastore
	sfcObserver   *SFCBridgeObserver
	gpactObserver *GPACTBridgeObserver
}

// NewObserverImplV1 creates a new observer.
func NewObserverImplV1(path string, mq *mqserver.MQServer) Observer {
	return &ObserverImplV1{path: path, mq: mq}
}

// Start starts the observer's routine.
func (o *ObserverImplV1) Start() error {
	var err error
	if o.ds == nil {
		dsopts := badgerds.DefaultOptions
		dsopts.SyncWrites = false
		dsopts.Truncate = true
		o.ds, err = badgerds.NewDatastore(o.path, &dsopts)
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
func (o *ObserverImplV1) Stop() {
	if o.sfcObserver != nil {
		o.sfcObserver.Stop()
	}
	if o.gpactObserver != nil {
		o.gpactObserver.Stop()
	}
}

// StartObserve starts a new observe.
func (o *ObserverImplV1) StartObserve(chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) error {
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
func (o *ObserverImplV1) StopObserve() error {
	// Close any existing observe.
	o.Stop()

	err := o.ds.Delete(context.Background(), datastore.NewKey(activeKey))
	if err != nil {
		return err
	}
	return nil
}

// routineSFC is the observe SFC routine.
func (o *ObserverImplV1) routineSFC(chainID *big.Int, chainAP string, addr common.Address) {
	for {
		chain, err := ethclient.Dial(chainAP)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		defer chain.Close()

		sfc, err := functioncall.NewSfc(addr, chain)
		if err != nil {
			logging.Error(err.Error())
			return
		}

		observer, err := NewSFCBridgeRealtimeObserver(chainID.String(), addr.String(), sfc, o.mq)

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

// routineGPACT is the observe GPACT routine.
func (o *ObserverImplV1) routineGPACT(chainID *big.Int, chainAP string, addr common.Address) {
	for {
		chain, err := ethclient.Dial(chainAP)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		defer chain.Close()

		gpact, err := functioncall.NewGpact(addr, chain)
		if err != nil {
			logging.Error(err.Error())
			return
		}

		observer, err := NewGPACTBridgeRealtimeObserver(chainID.String(), addr.String(), gpact, o.mq)

		if err != nil {
			logging.Error(err.Error())
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

func (o *ObserverImplV1) createFinalisedEventObserver(source string, sourceAddr string, contract *functioncall.Sfc, mq mqserver.MessageQueue,
	client *ethclient.Client) (*SFCBridgeObserver, error) {
	dsProgKey := datastore.NewKey(fmt.Sprintf("/%s/%s/last_finalised_block", source, sourceAddr))
	watcherProgOpts := WatcherProgressDsOpts{o.ds, dsProgKey, DefaultRetryOptions}
	return NewSFCBridgeFinalisedObserver(source, sourceAddr, contract, mq, 4, watcherProgOpts, client)
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
