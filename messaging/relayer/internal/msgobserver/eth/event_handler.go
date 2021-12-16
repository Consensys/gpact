package eth

/*
 * Copyright 2021 ConsenSys Software Inc.
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
	"fmt"
)

type EventHandler interface {
	Handle(event interface{}) error
}

// SimpleEventHandler first transforms an event to a relayer message then passes it to a handler to process
type SimpleEventHandler struct {
	// EventTransformer transforms a given event to a relayer message (v1.Message)
	EventTransformer EventTransformer
	// MessageHandler processes a relayer message that has been created from a event
	MessageHandler MessageHandler
}

// Handle transforms the provided event to a message then forwards it to a message handler to process.
func (h *SimpleEventHandler) Handle(event interface{}) error {
	message, err := h.EventTransformer.ToMessage(event)
	if err != nil {
		return fmt.Errorf("error transforming event. %v", err)

	}
	return h.MessageHandler.Handle(message)
}

func NewSimpleEventHandler(transformer EventTransformer, sender MessageHandler) *SimpleEventHandler {
	return &SimpleEventHandler{EventTransformer: transformer, MessageHandler: sender}
}
