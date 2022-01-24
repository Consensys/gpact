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

	"github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/node"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
)

// GetChainAPReq is the request to get chain ap.
type GetChainAPReq struct {
	ChainID string `json:"chain_id"`
}

// GetChainAPResp is the response to get chain ap.
type GetChainAPResp struct {
	ChainAP string `json:"chain_ap"`
}

// HandleGetChainAP handles get chain ap request.
func HandleGetChainAP(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &GetChainAPReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	chainAP, err := instance.Transactor.GetChainAP(chainID)
	if err != nil {
		return nil, err
	}
	resp := GetChainAPResp{
		ChainAP: chainAP,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, err
}

// RequestGetChainAP requests get chain ap.
func RequestGetChainAP(addr string, chainID *big.Int) (string, error) {
	req := GetChainAPReq{
		ChainID: chainID.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return "", err
	}
	data, err = rpc.Request(addr, GetChainAPReqType, data)
	if err != nil {
		return "", err
	}
	resp := &GetChainAPResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return "", err
	}
	return resp.ChainAP, nil
}
