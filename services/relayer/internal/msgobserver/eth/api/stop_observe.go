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

	"github.com/consensys/gpact/services/relayer/internal/msgobserver/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
)

// StopObserveReq is the request to stop observe.
type StopObserveReq struct {
}

// StopObserveResp is the response to stop observe.
type StopObserveResp struct {
	Success bool `json:"success"`
}

// HandleStopObserve handles the request to stop observe.
func HandleStopObserve(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	err := instance.Observer.StopObserve()
	if err != nil {
		return nil, err
	}
	resp := StopObserveResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestStopObserve requests a stop observe.
func RequestStopObserve(addr string) (bool, error) {
	data, err := rpc.Request(addr, StopObserveReqType, []byte{1})
	if err != nil {
		return false, err
	}
	resp := &StopObserveResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
