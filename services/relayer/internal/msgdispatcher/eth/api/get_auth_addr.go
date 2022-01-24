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
	"github.com/ethereum/go-ethereum/common"
)

// GetAuthAddrReq is the request to get auth addr.
type GetAuthAddrReq struct {
	ChainID string `json:"chain_id"`
}

// GetAuthAddrResp is the response to get auth addr.
type GetAuthAddrResp struct {
	Addr string `json:"addr"`
}

// HandleGetAuthAddr handles get auth addr.
func HandleGetAuthAddr(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &GetAuthAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	auth, err := instance.Transactor.GetAuth(chainID)
	if err != nil {
		return nil, err
	}
	resp := GetAuthAddrResp{
		Addr: auth.From.String(),
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, err
}

// RequestGetAuthAddr requests get auth addr.
func RequestGetAuthAddr(addr string, chainID *big.Int) (common.Address, error) {
	req := GetAuthAddrReq{
		ChainID: chainID.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return common.Address{}, err
	}
	data, err = rpc.Request(addr, GetAuthAddrReqType, data)
	if err != nil {
		return common.Address{}, err
	}
	resp := &GetAuthAddrResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return common.Address{}, err
	}
	return common.HexToAddress(resp.Addr), nil
}
