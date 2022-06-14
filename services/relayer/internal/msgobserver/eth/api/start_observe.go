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
	"github.com/consensys/gpact/services/relayer/internal/msgobserver/eth/observer"
	"math/big"

	"github.com/consensys/gpact/services/relayer/internal/msgobserver/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
	logging "github.com/rs/zerolog/log"
)

// ObservationReq is the request to start an observation of an event source
type ObservationReq struct {
	ChainID      string               `json:"chain_id"`
	ChainAP      string               `json:"chain_ap"`
	ContractType string               `json:"contract_type"`
	ContractAddr string               `json:"contract_addr"`
	WatcherType  observer.WatcherType `json:"watcher_type"`
}

// ObserverActionResponse encapsulates an API response that indicates success or failure of an API call action
type ObserverActionResponse struct {
	Success bool   `json:"success"`
	Message string `json:"message"`
}

// HandleStartObserver handles request to start multi-source observer
func HandleStartObserver(data []byte) ([]byte, error) {
	instance := node.GetSingleInstance()
	err := instance.Observer.Start()
	if err != nil {
		return fail(err)
	}
	return success()
}

// HandleStartObservation handles request to start a specific observation by the multi-source observer
func HandleStartObservation(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &ObservationReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return nil, fmt.Errorf("fail to decode chain id")
	}

	logging.Info().Msgf("Start Observe: Chain: %v, Contract: %v, ChainAP: %v", chainID, req.ContractAddr, req.ChainAP)

	err = instance.Observer.StartObservation(chainID, req.ChainAP, req.ContractType,
		common.HexToAddress(req.ContractAddr), req.WatcherType)
	if err != nil {
		return nil, err
	}
	resp := ObserverActionResponse{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestStartObservation sends a request to start an observation for a specific event source
func RequestStartObservation(addr string, chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) (bool, error) {
	req := ObservationReq{
		ChainID:      chainID.String(),
		ChainAP:      chainAP,
		ContractType: contractType,
		ContractAddr: contractAddr.String(),
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, StartObservationReqType, data)
	if err != nil {
		return false, err
	}
	resp := &ObserverActionResponse{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
