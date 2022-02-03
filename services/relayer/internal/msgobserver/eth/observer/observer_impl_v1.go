package observer

/*
 * Copyright 2021 ConsenSys Software Inc
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

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
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
	ContractAddr string `json:"contract_addr"`
	AP           string `json:"ap"`
}

// ObserverImplV1 implements observer.
type ObserverImplV1 struct {
	path string
	mq   *mqserver.MQServer

	ds   datastore.Datastore
	stop chan bool
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
			o.stop = make(chan bool)
			go o.routine(chainID, val.AP, common.HexToAddress(val.ContractAddr), o.stop)
		}
	}
	return nil
}

// Stop safely stops the observer.
func (o *ObserverImplV1) Stop() {
	if o.ds != nil && o.stop != nil {
		o.stop <- true
	}
}

// StartObserve starts a new observe.
func (o *ObserverImplV1) StartObserve(chainID *big.Int, chainAP string, contractAddr common.Address) error {
	// First, close any existing observe.
	if o.stop != nil {
		o.stop <- true
	}
	val := observation{
		ChainID:      chainID.String(),
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
	o.stop = make(chan bool)
	go o.routine(chainID, chainAP, contractAddr, o.stop)
	return nil
}

// StopObserve stops observe.
func (o *ObserverImplV1) StopObserve() error {
	// Close any existing observe.
	if o.stop != nil {
		o.stop <- true
		o.stop = nil
	}
	err := o.ds.Delete(context.Background(), datastore.NewKey(activeKey))
	if err != nil {
		return err
	}
	return nil
}

// routine is the observe routine.
func (o *ObserverImplV1) routine(chainID *big.Int, chainAP string, addr common.Address, end chan bool) {
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

		observer, err := NewSFCBridgeObserver(chainID.String(), addr.String(), sfc, o.mq, end)
		if err != nil {
			logging.Error(err.Error())
			return
		}
		if observer.Start() == nil {
			break
		} else {
			logging.Warn("Error in observing event. Retry in 3 seconds...")
			chain.Close()
			time.Sleep(3 * time.Second)
		}
	}
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
