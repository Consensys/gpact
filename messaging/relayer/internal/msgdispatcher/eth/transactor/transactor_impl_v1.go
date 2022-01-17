package transactor

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
	"crypto/ecdsa"
	"encoding/json"
	"math/big"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/crypto/secp256k1"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
)

const (
	dsTimeout = 3 * time.Second
)

// TransactorImplV1 implements the Transactor interface.
type TransactorImplV1 struct {
	path string

	ds datastore.Datastore
}

// record represents a value in the key-val ds.
type record struct {
	ChainAP string `json:"chain_ap"`
	Key     []byte `json:"key"`
}

// NewTransactorImplV1 creates a new transactor.
func NewTransactorImplV1(path string) Transactor {
	return &TransactorImplV1{path: path}
}

// Start starts the transactor's routine.
func (t *TransactorImplV1) Start() error {
	var err error
	if t.ds == nil {
		dsopts := badgerds.DefaultOptions
		dsopts.SyncWrites = false
		dsopts.Truncate = true
		t.ds, err = badgerds.NewDatastore(t.path, &dsopts)
	}
	return err
}

// Stop safely stops the transactor.
func (t *TransactorImplV1) Stop() {
	if t.ds != nil {
		if err := t.ds.Close(); err != nil {
			logging.Error("Error in closing the db %v", err.Error())
		}
	}
}

// SetTransactionOpts sets a key to given chain and chain AP.
func (t *TransactorImplV1) SetTransactionOpts(chainID *big.Int, chainAP string, key []byte) error {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()

	data, err := json.Marshal(record{
		ChainAP: chainAP,
		Key:     key,
	})
	if err != nil {
		return err
	}
	return t.ds.Put(ctx, datastore.NewKey(chainID.String()), data)
}

// GetChainAP gets a chain ap associated given chain id.
func (t *TransactorImplV1) GetChainAP(chainID *big.Int) (string, error) {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()

	data, err := t.ds.Get(ctx, datastore.NewKey(chainID.String()))
	if err != nil {
		return "", err
	}
	r := &record{}
	err = json.Unmarshal(data, r)
	if err != nil {
		return "", err
	}
	return r.ChainAP, nil
}

// GetAuth gets the auth for given chain id.
func (t *TransactorImplV1) GetAuth(chainID *big.Int) (*bind.TransactOpts, error) {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()

	data, err := t.ds.Get(ctx, datastore.NewKey(chainID.String()))
	if err != nil {
		return nil, err
	}
	r := &record{}
	err = json.Unmarshal(data, r)
	if err != nil {
		return nil, err
	}
	x, y := secp256k1.S256().ScalarBaseMult(r.Key)
	prv := &ecdsa.PrivateKey{}
	prv.D = big.NewInt(0).SetBytes(r.Key)
	prv.PublicKey = ecdsa.PublicKey{
		X:     x,
		Y:     y,
		Curve: secp256k1.S256(),
	}
	auth := bind.NewKeyedTransactor(prv)
	return auth, nil
}
