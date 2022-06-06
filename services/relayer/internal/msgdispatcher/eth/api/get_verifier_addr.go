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

// GetVerifierAddrReq is the request to get verifier addr.
type GetVerifierAddrReq struct {
	SourceChainID string `json:"source_chain_id"`
	TargetChainID string `json:"target_chain_id"`
}

// GetVerifierAddrResp is the response to get verifier addr.
type GetVerifierAddrResp struct {
	VerifierContractAddr string `json:"verifier_contract_addr"`
}

// HandleGetVerifierAddr handles get verifier addr.
func HandleGetVerifierAddr(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &GetVerifierAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	sourceChainID, ok := big.NewInt(0).SetString(req.SourceChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode source chain id")
	}
	targetChainID, ok := big.NewInt(0).SetString(req.TargetChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode target chain id")
	}
	verifierContractAddr, err := instance.Verifier.GetVerifierAddr(sourceChainID, targetChainID)
	if err != nil {
		return nil, err
	}
	resp := GetVerifierAddrResp{
		VerifierContractAddr: verifierContractAddr.String(),
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestGetVerifierAddr requests get verifier addr.
func RequestGetVerifierAddr(addr string, sourceChainID *big.Int, targetChainID *big.Int, contractAddr common.Address) (common.Address, error) {
	req := GetVerifierAddrReq{
		SourceChainID: sourceChainID.String(),
		TargetChainID: targetChainID.String(),
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
	return common.HexToAddress(resp.VerifierContractAddr), nil
}
