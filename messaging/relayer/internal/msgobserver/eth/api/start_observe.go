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

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/node"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// StartObserveReq is the request to start observe.
type StartObserveReq struct {
	ChainID      string `json:"chain_id"`
	ChainAP      string `json:"chain_ap"`
	ContractAddr string `json:"contract_addr"`
}

// StartObserveResp is the response to start observe.
type StartObserveResp struct {
	Success bool `json:"success"`
}

// HandleStartObserve handles start observe request.
func HandleStartObserve(data []byte) ([]byte, error) {
	// Get node
	node := node.GetSingleInstance()

	req := &StartObserveReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}

	err = node.Observer.StartObserve(chainID, req.ChainAP, common.HexToAddress(req.ContractAddr))
	if err != nil {
		return nil, err
	}
	resp := StartObserveResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestStartObserve requests start observe.
func RequestStartObserve(addr string, chainID *big.Int, chainAP string, contractAddr common.Address) (bool, error) {
	req := StartObserveReq{
		ChainID:      chainID.String(),
		ChainAP:      chainAP,
		ContractAddr: contractAddr.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, StartObserveReqType, data)
	if err != nil {
		return false, err
	}
	resp := &StartObserveResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
