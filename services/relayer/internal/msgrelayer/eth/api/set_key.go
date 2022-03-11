package api

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
	"encoding/json"
	"fmt"
	"math/big"

	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// SetKeyReq is the request to set key.
type SetKeyReq struct {
	ChainID      string `json:"chain_id"`
	ContractAddr string `json:"contract_addr"`
	KeyType      byte   `json:"key_type"`
	Key          []byte `json:"key"`
}

// SetKeyResp is the reponse to set key.
type SetKeyResp struct {
	Success bool `json:"success"`
}

// HandleSetKey handles set key request.
func HandleSetKey(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &SetKeyReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	err = instance.Signer.SetKey(chainID, common.HexToAddress(req.ContractAddr), req.KeyType, req.Key)
	if err != nil {
		return nil, err
	}
	resp := SetKeyResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestSetKey requests set key.
func RequestSetKey(addr string, chainID *big.Int, contractAddr common.Address, keyType byte, key []byte) (bool, error) {
	req := SetKeyReq{
		ChainID:      chainID.String(),
		ContractAddr: contractAddr.String(),
		KeyType:      keyType,
		Key:          key,
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, SetKeyReqType, data)
	if err != nil {
		return false, err
	}
	resp := &SetKeyResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
