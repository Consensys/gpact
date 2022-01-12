package verifier

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
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
)

const (
	dsTimeout = 3 * time.Second
)

// VerifierImplV1 implements the verifier interface.
type VerifierImplV1 struct {
	path string

	ds datastore.Datastore
}

// NewVerifierImplV1 creates a new verifier.
func NewVerifierImplV1(path string) Verifier {
	return &VerifierImplV1{path: path}
}

// Start starts the verifier's routine
func (v *VerifierImplV1) Start() error {
	var err error
	if v.ds == nil {
		dsopts := badgerds.DefaultOptions
		dsopts.SyncWrites = false
		dsopts.Truncate = true
		v.ds, err = badgerds.NewDatastore(v.path, &dsopts)
	}
	return err
}

// Stop stops the verifier.
func (v *VerifierImplV1) Stop() {
	if v.ds != nil {
		if err := v.ds.Close(); err != nil {
			logging.Error("Error in closing the db %v", err.Error())
		}
	}
}

// SetVerifierAddr sets a verifier address with associated chain id and contract address.
func (v *VerifierImplV1) SetVerifierAddr(chainID *big.Int, contractAddr common.Address, esAddr common.Address) error {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()
	return v.ds.Put(ctx, dsKey(chainID, contractAddr), esAddr.Bytes())
}

// GetVerifierAddr gets a verifier address for given associated chain id and contract address.
func (v *VerifierImplV1) GetVerifierAddr(chainID *big.Int, contractAddr common.Address) (common.Address, error) {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()
	val, err := v.ds.Get(ctx, dsKey(chainID, contractAddr))
	if err != nil {
		return common.Address{}, err
	}
	return common.BytesToAddress(val), nil
}

// dsKey gets the datastore key from given chainID and contract address.
func dsKey(chainID *big.Int, contractAddr common.Address) datastore.Key {
	return datastore.NewKey(fmt.Sprintf("%v-%v", chainID.String(), contractAddr.String()))
}
