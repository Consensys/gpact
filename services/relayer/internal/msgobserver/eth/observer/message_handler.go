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
	"github.com/avast/retry-go"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/consensys/gpact/services/relayer/internal/mqserver"
	"github.com/consensys/gpact/services/relayer/pkg/messages/v1"
)

// MessageHandler processes relayer messages
type MessageHandler interface {
	Handle(m *v1.Message)
}

// MessageEnqueueHandler enqueues relayer messages onto a configured message queue server
type MessageEnqueueHandler struct {
	MQ mqserver.MessageQueue
	FailureRetryOpts
}

// Handle sends the provided message to the configured message queue.
// If sending the message fails, it is retried `MessageEnqueueHandler.retryAttempts` times.
// The method assumes that the message queue is configured and started.
func (h *MessageEnqueueHandler) Handle(m *v1.Message) {
	h.sendAsyncWithRetry(m)
}

// sendAsyncWithRetry starts a go routine that sends the given message to a queue, and retries if it fails.
func (h *MessageEnqueueHandler) sendAsyncWithRetry(msg *v1.Message) {
	logging.Info("Sending message to queue. Message ID: %s\n", msg.ID)
	go func() {
		err := retry.Do(
			func() error {
				return h.MQ.Request(v1.Version, v1.MessageType, msg)
			},
			retry.Attempts(h.RetryAttempts),
			retry.Delay(h.RetryDelay),
			retry.OnRetry(func(attempt uint, err error) {
				logging.Info("Retrying sending message. ID: %s, Attempt: %d, Error: %v", msg.ID, attempt+1, err)
			}))
		if err != nil {
			logging.Error("Failed to send message. ID: %s. Error: %v", msg.ID, err)
			return
		}
	}()
}

func NewMessageEnqueueHandler(qServer mqserver.MessageQueue, retryOpts FailureRetryOpts) *MessageEnqueueHandler {
	return &MessageEnqueueHandler{qServer, retryOpts}
}
