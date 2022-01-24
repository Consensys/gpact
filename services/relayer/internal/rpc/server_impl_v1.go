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
	"context"
	"fmt"
	"io/ioutil"
	"net/http"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/logging"
)

// ServerImplV1 implements the Server interface.
type ServerImplV1 struct {
	start    bool
	server   *http.Server
	handlers map[byte]func(data []byte) ([]byte, error)
}

// NewServerImplV1 creates a new server.
func NewServerImplV1(port int) Server {
	s := &ServerImplV1{
		start:    false,
		handlers: make(map[byte]func(data []byte) ([]byte, error)),
	}
	s.server = &http.Server{
		Addr:           fmt.Sprintf(":%v", port),
		Handler:        s,
		ReadTimeout:    60 * time.Second,
		WriteTimeout:   60 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}
	return s
}

// Start starts the server.
func (s *ServerImplV1) Start() error {
	if s.start {
		// Already started.
		return nil
	}
	errChan := make(chan error)
	go func() {
		// Start server.
		errChan <- s.server.ListenAndServe()
	}()
	// Wait for 3 seconds for the server to start
	tc := time.After(3 * time.Second)
	select {
	case <-tc:
		s.start = true
		return nil
	case err := <-errChan:
		return err
	}
}

// Stop stops the server.
func (s *ServerImplV1) Stop() {
	if s.start {
		if err := s.server.Shutdown(context.Background()); err != nil {
			logging.Error("Error stopping the server: %v", err.Error())
		} else {
			s.start = false
		}
	}
}

// AddHandler adds a handler.
func (s *ServerImplV1) AddHandler(method byte, handler func(data []byte) ([]byte, error)) Server {
	s.handlers[method] = handler
	return s
}

// ServeHTTP handles the api calls.
func (s *ServerImplV1) ServeHTTP(w http.ResponseWriter, r *http.Request) {
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
