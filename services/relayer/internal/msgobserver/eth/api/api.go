package api

import (
	"encoding/json"
	"github.com/consensys/gpact/services/relayer/internal/logging"
)

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

const (
	StartMultisourceObserverReqType = byte(1)
	StopMultisourceObserverReqType  = byte(2)
	StartObservationReqType         = byte(3)
	StopObservationReqType          = byte(4)
)

func createResponse(err error) ([]byte, error) {
	var resp ObserverActionResponse
	if err != nil {
		resp = ObserverActionResponse{
			Success: false,
			Message: err.Error(),
		}
	} else {
		resp = ObserverActionResponse{
			Success: true,
		}
	}

	rawResp, err := json.Marshal(resp)
	if err != nil {
		logging.Error("error serialising createResponse: %v", err)
		return nil, err
	}
	return rawResp, err
}

func fail(err error) ([]byte, error) {
	return createResponse(err)
}

func success() ([]byte, error) {
	return createResponse(nil)
}
