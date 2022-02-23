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

	"github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/node"
	"github.com/consensys/gpact/services/relayer/internal/rpc"
)

// SetMsgStoreAddrReq is the request to set msg store addr.
type SetMsgStoreAddrReq struct {
	MsgStoreURL string `json:"msg_store_url"`
}

// SetMsgStoreAddrResp is the response to set msg store addr.
type SetMsgStoreAddrResp struct {
	Success bool `json:"success"`
}

// HandleSetMsgStoreAddr handles set msg store addr.
func HandleSetMsgStoreAddr(data []byte) ([]byte, error) {
	// Get node
	instance := node.GetSingleInstance()

	req := &SetMsgStoreAddrReq{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}
	instance.MessageStoreAddr = req.MsgStoreURL
	resp := SetMsgStoreAddrResp{
		Success: true,
	}
	data, err = json.Marshal(resp)
	if err != nil {
		return nil, err
	}
	return data, nil
}

// RequestSetMsgStoreAddr requests set msg store addr.
func RequestSetMsgStoreAddr(addr string, msgStoreURL string) (bool, error) {
	req := SetMsgStoreAddrReq{
		MsgStoreURL: msgStoreURL,
	}
	data, err := json.Marshal(req)
	if err != nil {
		return false, err
	}
	data, err = rpc.Request(addr, SetMsgStoreAddrReqType, data)
	if err != nil {
		return false, err
	}
	resp := &SetMsgStoreAddrResp{}
	err = json.Unmarshal(data, resp)
	if err != nil {
		return false, err
	}
	return resp.Success, nil
}
