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

	"github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// SetVerifierAddrReq is the request to set verifier addr.
type SetVerifierAddrReq struct {
	ChainID      string `json:"chain_id"`
	ContractAddr string `json:"contract_addr"`
	EsAddr       string `json:"es_addr"`
}

// SetVerifierAddrResp is the response to set verifier addr.
type SetVerifierAddrResp struct {
	Success bool `json:"success"`
}

// HandleSetVerifierAddr handles set verifier addr.
func HandleSetVerifierAddr(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &SetVerifierAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}
	err = instance.Verifier.SetVerifierAddr(chainID, common.HexToAddress(req.ContractAddr), common.HexToAddress(req.EsAddr))
	if err != nil {
		return nil, err
	}
	resp := SetVerifierAddrResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestSetVerifierAddr requests set verifier addr.
func RequestSetVerifierAddr(addr string, chainID *big.Int, contractAddr common.Address, esAddr common.Address) (bool, error) {
	req := SetVerifierAddrReq{
		ChainID:      chainID.String(),
		ContractAddr: contractAddr.String(),
		EsAddr:       esAddr.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, SetVerifierAddrReqType, data)
	if err != nil {
		return false, err
	}
	resp := &SetVerifierAddrResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
