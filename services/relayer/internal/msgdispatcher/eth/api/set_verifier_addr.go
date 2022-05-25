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

	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// SetVerifierAddrReq is the request to set verifier addr.
type SetVerifierAddrReq struct {
	SourceChainID        string `json:"source_chain_id"`
	TargetChainID        string `json:"target_chain_id"`
	VerifierContractAddr string `json:"verifier_contract_addr"`
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
	sourceChainID, ok := big.NewInt(0).SetString(req.SourceChainID, 10)
	if !ok {
		errStr := fmt.Sprintf("Failed to decode source chain id (%v): Error: %v", req.SourceChainID, err.Error())
		logging.Error(errStr)
		return nil, fmt.Errorf(errStr)
	}
	targetChainID, ok := big.NewInt(0).SetString(req.TargetChainID, 10)
	if !ok {
		errStr := fmt.Sprintf("Failed to decode target chain id (%v): Error: %v", req.TargetChainID, err.Error())
		logging.Error(errStr)
		return nil, fmt.Errorf(errStr)
	}
	if len(req.VerifierContractAddr) == 0 {
		errStr := fmt.Sprintf("Verifier contract addr not specified")
		logging.Error(errStr)
		return nil, fmt.Errorf(errStr)
	}

	err = instance.Verifier.SetVerifierAddr(sourceChainID, targetChainID, common.HexToAddress(req.VerifierContractAddr))
	if err != nil {
		logging.Error("Error setting verifier address: %v", err.Error())
		return nil, err
	}
	logging.Info("SetVerifierAddr: %v, %v, %v", req.SourceChainID, req.TargetChainID, req.VerifierContractAddr)

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
func RequestSetVerifierAddr(addr string, sourceChainID *big.Int, targetChainID *big.Int, verifierAddr common.Address) (bool, error) {
	req := SetVerifierAddrReq{
		SourceChainID:        sourceChainID.String(),
		TargetChainID:        targetChainID.String(),
		VerifierContractAddr: verifierAddr.String(),
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
