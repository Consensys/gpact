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
	"fmt"
	"math/big"
	"strings"

	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/ipfs/go-datastore"
	dsq "github.com/ipfs/go-datastore/query"
	badgerds "github.com/ipfs/go-ds-badger"
)

// ObserverImplV1 implements observer.
type ObserverImplV1 struct {
	path string
	mq   *mqserver.MQServer

	ds       datastore.Datastore
	stopsMap map[string](*map[string]chan bool)
}

// NewObserverImplV1 creates a new observer.
func NewObserverImplV1(path string, mq *mqserver.MQServer) Observer {
	return &ObserverImplV1{path: path}
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
		o.stopsMap = make(map[string]*map[string]chan bool)
		observers, err := o.ListObserves()
		if err != nil {
			o.ds.Close()
			return err
		}
		for chainID, addrs := range observers {
			for _, addr := range addrs {
				id, _ := big.NewInt(0).SetString(chainID, 10)
				ap, err := o.ds.Get(context.Background(), dsKey(id, addr))
				if err != nil {
					o.ds.Close()
					return err
				}
				chain, err := ethclient.Dial(string(ap))
				if err != nil {
					o.ds.Close()
					return err
				}
				end := make(chan bool, 1)
				go o.routine(id, chain, addr, end)

				stops, ok := o.stopsMap[id.String()]
				if !ok {
					stopsNew := make(map[string]chan bool)
					stops = &stopsNew
					o.stopsMap[id.String()] = stops
				}
				(*stops)[addr.String()] = end
			}
		}
	}
	return nil
}

// Stop safely stops the observer.
func (o *ObserverImplV1) Stop() {
	if o.ds != nil {
		for _, stops := range o.stopsMap {
			for _, stop := range *stops {
				stop <- true
			}
		}
		if err := o.ds.Close(); err != nil {
			logging.Error("Error in closing the db %v", err.Error())
		}
	}
}

// StartObserve starts a new observe.
func (o *ObserverImplV1) StartObserve(chainID *big.Int, chainAP string, contractAddr common.Address) error {
	// First, close any existing observe.
	stops, ok := o.stopsMap[chainID.String()]
	if ok {
		stop, ok := (*stops)[contractAddr.String()]
		if ok {
			stop <- true
		}
	} else {
		stopsNew := make(map[string]chan bool)
		stops = &stopsNew
		o.stopsMap[chainID.String()] = stops
	}
	err := o.ds.Put(context.Background(), dsKey(chainID, contractAddr), []byte(chainAP))
	if err != nil {
		return err
	}
	chain, err := ethclient.Dial(chainAP)
	if err != nil {
		return err
	}
	end := make(chan bool, 1)
	go o.routine(chainID, chain, contractAddr, end)
	(*stops)[contractAddr.String()] = end
	return nil
}

// StopObserve stops observe.
func (o *ObserverImplV1) StopObserve(chainID *big.Int, contractAddr common.Address) error {
	// Close any existing observe.
	stops, ok := o.stopsMap[chainID.String()]
	if ok {
		stop, ok := (*stops)[contractAddr.String()]
		if ok {
			stop <- true
			err := o.ds.Delete(context.Background(), dsKey(chainID, contractAddr))
			if err != nil {
				return err
			}
			delete(*stops, contractAddr.String())
		}
	}
	return nil
}

// ListObserves lists all observes stored.
func (o *ObserverImplV1) ListObserves() (map[string][]common.Address, error) {
	res := make(map[string][]common.Address)
	q := dsq.Query{KeysOnly: true}
	tempRes, err := o.ds.Query(context.Background(), q)
	if err != nil {
		return nil, err
	}
	output := make(chan string, dsq.KeysOnlyBufSize)

	go func() {
		defer func() {
			tempRes.Close()
			close(output)
		}()
		for {
			e, ok := tempRes.NextSync()
			if !ok {
				break
			}
			if e.Error != nil {
				logging.Error("error querying from storage: %v", e.Error.Error())
				return
			}
			select {
			case output <- e.Key[1:]:
			}
		}
	}()

	for key := range output {
		id, addr := splitKey(key)
		addrs, ok := res[id.String()]
		if !ok {
			addrs = make([]common.Address, 0)
			res[id.String()] = addrs
		}
		addrs = append(addrs, addr)
	}

	return res, nil
}

// routine is the observe routine.
func (o *ObserverImplV1) routine(chainID *big.Int, chain *ethclient.Client, addr common.Address, end chan bool) {
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
	observer.Start()
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
