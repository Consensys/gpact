package api

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
	"encoding/json"
	"fmt"
	"math/big"

	"github.com/consensys/gpact/services/relayer/internal/rpc"

	"github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/node"
	"github.com/ethereum/go-ethereum/ethclient"
)

// SetTransactionOptsReq is the request to set transaction opts.
type SetTransactionOptsReq struct {
	ChainID string `json:"chain_id"`
	ChainAP string `json:"chain_ap"`
	Key     []byte `json:"key"`
}

// SetTransactionOptsResp is the response to set transaction opts.
type SetTransactionOptsResp struct {
	Success bool `json:"success"`
}

// HandleSetTransactionOpts handles set transaction opts request.
func HandleSetTransactionOpts(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &SetTransactionOptsReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	// Do a test connection
	conn, err := ethclient.Dial(req.ChainAP)
	if err != nil {
		return nil, err
	}
	conn.Close()
	err = instance.Transactor.SetTransactionOpts(chainID, req.ChainAP, req.Key)
	if err != nil {
		return nil, err
	}
	resp := SetTransactionOptsResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestSetTransactionOpts requests set transaction opts.
func RequestSetTransactionOpts(addr string, chainID *big.Int, chainAP string, key []byte) (bool, error) {
	req := SetTransactionOptsReq{
		ChainID: chainID.String(),
		ChainAP: chainAP,
		Key:     key,
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, SetTransactionOptsReqType, data)
	if err != nil {
		return false, err
	}
	resp := &SetTransactionOptsResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
