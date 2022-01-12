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

// GetVerifierAddrReq is the request to get verifier addr.
type GetVerifierAddrReq struct {
	ChainID      string `json:"chain_id"`
	ContractAddr string `json:"contract_addr"`
}

// GetVerifierAddrResp is the response to get verifier addr.
type GetVerifierAddrResp struct {
	EsAddr string `json:"es_addr"`
}

// HandleGetVerifierAddr handles get verifier addr.
func HandleGetVerifierAddr(data []byte) ([]byte, error) {
	// Get node
	node := node.GetSingleInstance()

	req := &GetVerifierAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	esAddr, err := node.Verifier.GetVerifierAddr(chainID, common.HexToAddress(req.ContractAddr))
	if err != nil {
		return nil, err
	}
	resp := GetVerifierAddrResp{
		EsAddr: esAddr.String(),
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestGetVerifierAddr requests get verifier addr.
func RequestGetVerifierAddr(addr string, chainID *big.Int, contractAddr common.Address) (common.Address, error) {
	req := GetVerifierAddrReq{
		ChainID:      chainID.String(),
		ContractAddr: contractAddr.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return common.Address{}, err
	}
	data, err = rpc.Request(addr, GetVerifierAddrReqType, data)
	if err != nil {
		return common.Address{}, err
	}
	resp := &GetVerifierAddrResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return common.Address{}, err
	}
	return common.HexToAddress(resp.EsAddr), nil
}
