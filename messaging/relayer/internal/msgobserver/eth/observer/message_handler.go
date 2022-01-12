package observer

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
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/consensys/gpact/messaging/relayer/internal/mqserver"
)

// MessageHandler processes relayer messages
type MessageHandler interface {
	Handle(m *v1.Message) error
}

// MessageEnqueueHandler enqueues relayer messages onto a configured message queue server
type MessageEnqueueHandler struct {
	MQ mqserver.MessageQueue
}

// Handle sends the provided message to the configured message queue.
// The method assumes that the message queue is configured and started.
func (mq *MessageEnqueueHandler) Handle(m *v1.Message) error {
	mq.sendMessage(m)
	return nil
}

func NewMessageEnqueueHandler(qServer mqserver.MessageQueue) *MessageEnqueueHandler {
	return &MessageEnqueueHandler{qServer}
}

func (s *MessageEnqueueHandler) sendMessage(msg *v1.Message) {
	logging.Info("Send message with ID: %s\n", msg.ID)
	s.MQ.Request(v1.Version, v1.MessageType, msg)
}
