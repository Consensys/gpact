package signer

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

// SignerImplV1 implements the Signer interface.
type SignerImplV1 struct {
	path string

	ds datastore.Datastore
}

// NewSignerImplV1 creates a new Signer.
func NewSignerImplV1(path string) *SignerImplV1 {
	return &SignerImplV1{path: path}
}

// Start starts the signer's routine.
func (s *SignerImplV1) Start() error {
	var err error
	if s.ds == nil {
		dsopts := badgerds.DefaultOptions
		dsopts.SyncWrites = false
		dsopts.Truncate = true
		s.ds, err = badgerds.NewDatastore(s.path, &dsopts)
	}
	return err
}

// Stop stops the signer's routine.
func (s *SignerImplV1) Stop() {
	if s.ds != nil {
		if err := s.ds.Close(); err != nil {
			logging.Error("Error in closing the db %v", err.Error())
		}
	}
}

// SetKey sets a signing key for given chain and a given contract addr.
func (s *SignerImplV1) SetKey(chainID *big.Int, contractAddr common.Address, keyType byte, key []byte) error {
	_, ok := supportedKeyTypes[keyType]
	if !ok {
		return fmt.Errorf("key type %v not supported", keyType)
	}
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()
	return s.ds.Put(ctx, dsKey(chainID, contractAddr), append([]byte{keyType}, key...))
}

// GetAddr gets the associated address for a signing key linked with given chain and given contract addr.
func (s *SignerImplV1) GetAddr(chainID *big.Int, contractAddr common.Address) (byte, common.Address, error) {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()
	val, err := s.ds.Get(ctx, dsKey(chainID, contractAddr))
	if err != nil {
		return 0, common.Address{}, err
	}
	addr, err := keyToAddr(val[0], val[1:])
	if err != nil {
		return 0, common.Address{}, err
	}
	return val[0], addr, nil
}

// Sign signs given message with internal stored key correspdoing to given chainID and given contract addr.
func (s *SignerImplV1) Sign(chainID *big.Int, contractAddr common.Address, msg []byte) (byte, []byte, error) {
	ctx, cancel := context.WithTimeout(context.Background(), dsTimeout)
	defer cancel()
	val, err := s.ds.Get(ctx, dsKey(chainID, contractAddr))
	if err != nil {
		return 0, nil, err
	}
	sig, err := sign(val[0], val[1:], msg)
	if err != nil {
		return 0, nil, err
	}
	return val[0], sig, nil
}

// dsKey gets the datastore key from given chainID and contract address.
func dsKey(chainID *big.Int, contractAddr common.Address) datastore.Key {
	return datastore.NewKey(fmt.Sprintf("%v-%v", chainID.String(), contractAddr.String()))
}
