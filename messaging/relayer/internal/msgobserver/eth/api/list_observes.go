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

	"github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/node"
	"github.com/consensys/gpact/messaging/relayer/internal/rpc"
	"github.com/ethereum/go-ethereum/common"
)

// ListObservesReq is the request to list observes.
type ListObservesReq struct {
}

// ListObservesResp is the response to list observes.
type ListObservesResp struct {
	Observes map[string][]string `json:"observes"`
}

// HandleListObserves handles the request to list observes.
func HandleListObserves(data []byte) ([]byte, error) {
	// Get node
	node := node.GetSingleInstance()

	temp, err := node.Observer.ListObserves()
	if err != nil {
		return nil, err
	}
	res := make(map[string][]string)
	for chain, addrs := range temp {
		res[chain] = make([]string, 0)
		for _, addr := range addrs {
			res[chain] = append(res[chain], addr.String())
		}
	}
	resp := ListObservesResp{
		Observes: res,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestListObserves requests a list observes.
func RequestListObserves(addr string) (map[string][]common.Address, error) {
	data, err := rpc.Request(addr, ListObservesReqType, []byte{1})
	if err != nil {
		return nil, err
	}
	resp := &ListObservesResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return nil, err
	}
	res := make(map[string][]common.Address)
	for chain, addrs := range resp.Observes {
		res[chain] = make([]common.Address, 0)
		for _, addr := range addrs {
			res[chain] = append(res[chain], common.HexToAddress(addr))
		}
	}
	return res, nil
}
