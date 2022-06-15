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
	"github.com/ethereum/go-ethereum/common"
	"math/big"

	"github.com/consensys/gpact/services/relayer/internal/msgobserver/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
)

// HandleStopObserver handles the request to stop the multi-source observer
func HandleStopObserver(data []byte) ([]byte, error) {
	instance := node.GetSingleInstance()
	instance.Observer.Stop()
	return success()
}

// HandleStopObservation handles the request to stop a specific observer managed by the multi-source observer
func HandleStopObservation(rawReq []byte) ([]byte, error) {
	instance := node.GetSingleInstance()
	req := &ObservationReq{}
	err := json.Unmarshal(rawReq, req)
	if err != nil {
		return fail(err)
	}

	chainID, ok := big.NewInt(0).SetString(req.ChainID, 10)
	if !ok {
		return fail(fmt.Errorf("failed to decode chain id"))
	}

	err = instance.Observer.StopObservation(chainID, req.ContractType, common.HexToAddress(req.ContractAddr), req.WatcherType)
	if err != nil {
		return fail(err)
	}

	return success()
}

// RequestStopObserver makes a requests a stop the multi-source observer
func RequestStopObserver(addr string) (bool, error) {
	data, err := rpc.Request(addr, StopMultisourceObserverReqType, []byte{1})
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
