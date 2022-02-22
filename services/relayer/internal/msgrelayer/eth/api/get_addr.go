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

	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// GetAddrReq is the request to get signer addr and type.
type GetAddrReq struct {
	ChainID      string `json:"chain_id"`
	ContractAddr string `json:"contract_addr"`
}

// GetAddrResp is the response to get addr.
type GetAddrResp struct {
	KeyType byte   `json:"key_type"`
	Addr    string `json:"addr"`
}

// HandleGetAddr handles get addr request.
func HandleGetAddr(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &GetAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	keyType, addr, err := instance.Signer.GetAddr(chainID, common.HexToAddress(req.ContractAddr))
	if err != nil {
		return nil, err
	}
	resp := GetAddrResp{
		KeyType: keyType,
		Addr:    addr.String(),
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestGetAddr requests get addr.
func RequestGetAddr(addr string, chainID *big.Int, contractAddr common.Address) (byte, common.Address, error) {
	req := GetAddrReq{
		ChainID:      chainID.String(),
		ContractAddr: contractAddr.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return 0, common.Address{}, err
	}
	data, err = rpc.Request(addr, GetAddrReqType, data)
	if err != nil {
		return 0, common.Address{}, err
	}
	resp := &GetAddrResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return 0, common.Address{}, err
	}
	return resp.KeyType, common.HexToAddress(resp.Addr), nil
}
