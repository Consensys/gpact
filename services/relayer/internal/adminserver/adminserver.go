package adminserver

import (
	"bytes"
	"context"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"

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

// AdminServer is an interface for the admin server.
type AdminServer interface {
	Start() error

	Stop()
}

// AdminServerImpl implements AdminServer.
type AdminServerImpl struct {
	handlers map[byte]func(req []byte) ([]byte, error)
	server   *http.Server
}

// NewAdminServerImpl creates a new admin server.
func NewAdminServerImpl(APIPort int, handlers map[byte]func(req []byte) ([]byte, error)) AdminServer {
	s := &AdminServerImpl{
		handlers: handlers,
	}
	s.server = &http.Server{
		Addr:           fmt.Sprintf(":%v", APIPort),
		Handler:        s,
		ReadTimeout:    60 * time.Second,
		WriteTimeout:   60 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	return s
}

// Start starts the server.
func (s *AdminServerImpl) Start() error {
	errChan := make(chan error)
	go func() {
		// Start server.
		errChan <- s.server.ListenAndServe()
	}()
	// Wait for 3 seconds for the server to start
	tc := time.After(3 * time.Second)
	select {
	case <-tc:
		return nil
	case err := <-errChan:
		return err
	}
}

// Stop shuts down the server.
func (s *AdminServerImpl) Stop() {
	s.server.Shutdown(context.Background())
}

// ServeHTTP handles the api calls.
// It takes a response writer, the http request as arguments.
func (s *AdminServerImpl) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	content, err := ioutil.ReadAll(r.Body)
	if closeErr := r.Body.Close(); closeErr != nil {
		logging.Error("HTTP can't close request body")
	}
	if err != nil {
		logging.Error("Error reading request %v", err.Error())
		return
	}
	if len(content) <= 1 {
		logging.Error("Received content with empty request %v", content)
		return
	}
	msgType := content[0]
	msgData := content[1:]
	handler, ok := s.handlers[msgType]
	if !ok {
		logging.Error("Unsupported message type: %v", msgType)
		return
	}
	respData, err := handler(msgData)
	if err != nil {
		logging.Error("Error handling request: %v", err.Error())
		return
	}
	w.WriteHeader(200)
	_, err = w.Write(respData)
	if err != nil {
		logging.Error("Error responding to client: %v", err.Error())
	}
}

// Request sends a request to given addr with given msg type and data.
func Request(addr string, msgType byte, data []byte) ([]byte, error) {
	if !strings.HasPrefix(addr, "http://") {
		addr = "http://" + addr
	}
	req, err := http.NewRequest("POST", addr, bytes.NewReader(append([]byte{msgType}, data...)))
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
