package rpc

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
	"bytes"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

// Server is the interface for the RPC Server.
type Server interface {
	// Start starts the server.
	Start() error

	// Stop stops the server.
	Stop()

	// AddHandler adds a handler.
	AddHandler(method byte, handler func(data []byte) ([]byte, error)) Server
}

// Request sends a request to given addr with given msg type and data.
func Request(addr string, method byte, data []byte) ([]byte, error) {
	if !strings.HasPrefix(addr, "http://") {
		addr = "http://" + addr
	}
	req, err := http.NewRequest("POST", addr, bytes.NewReader(append([]byte{method}, data...)))
	if err != nil {
		return nil, err
	}
	client := &http.Client{Timeout: 90 * time.Second}
	r, err := client.Do(req)
	if err != nil {
		return nil, err
	}
	content, err := ioutil.ReadAll(r.Body)
	if closeErr := r.Body.Close(); closeErr != nil {
		return nil, closeErr
	}
	return content, nil
}
