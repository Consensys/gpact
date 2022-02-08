package mqserver

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
	"github.com/consensys/gpact/messaging/relayer/internal/logging"
	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	"github.com/streadway/amqp"
)

type MessageQueue interface {
	// Request sends a message to the message queue.
	Request(version string, msgType string, msg messages.Message) error

	// Start starts the message handling routine.
	Start() error

	// Stop shuts down the message handling routine.
	Stop()
}

// MQServer provides a wrapper over the message queue.
type MQServer struct {
	// connIn is the connection to message queue for incoming messages.
	connIn *amqp.Connection
	// chanIn is the channel to receive incoming messages.
	chanIn *amqp.Channel
	// delivery is for receiving incoming messages.
	delivery <-chan amqp.Delivery

	// connOut is the connection to message queue for outgoing messages.
	connOut *amqp.Connection
	// chanOut is the channel to send outgoing messages.
	chanOut *amqp.Channel
	// queue is for sending outgoing messages.
	queue amqp.Queue

	// handlers store handlers for different messages depending on the message version & message type.
	handlers map[string]map[string]func(msg messages.Message)

	// shutdown is used to safely shutdown the routine.
	shutdown chan bool
}

// NewMQServer creates a new message queue server.
// inboundMQAddr is the address of the inbound MQ this server listens from.
// inboundChName is the channel name of inbound MQ.
// outboundMQAddr is the address of the outbound MQ this server pushes to.
// outboundChName is the channel name of the outbound MQ.
// handlers are handlers for different messages depending on the message version & message type.
func NewMQServer(
	inboundMQAddr string,
	inboundChName string,
	outboundMQAddr string,
	outboundChName string,
	handlers map[string]map[string]func(msg messages.Message),
) (*MQServer, error) {
	var err error
	s := &MQServer{
		handlers: handlers,
		shutdown: make(chan bool),
	}
	if inboundMQAddr != "" && inboundChName != "" {
		s.connIn, err = amqp.Dial(inboundMQAddr)
		if err != nil {
			return nil, err
		}
		s.chanIn, err = s.connIn.Channel()
		if err != nil {
			return nil, err
		}
		// TODO: Check parameter settings later.
		_, err := s.chanIn.QueueDeclare(
			inboundChName,
			false,
			false,
			false,
			false,
			nil,
		)
		if err != nil {
			return nil, err
		}
		// TODO: Check parameter settings later.
		s.delivery, err = s.chanIn.Consume(
			inboundChName,
			"",
			true,
			false,
			false,
			false,
			nil,
		)
		if err != nil {
			return nil, err
		}
	}
	if outboundMQAddr != "" && outboundChName != "" {
		if outboundMQAddr == inboundMQAddr {
			s.connOut = s.connIn
			s.chanOut = s.chanIn
		} else {
			s.connOut, err = amqp.Dial(outboundMQAddr)
			if err != nil {
				return nil, err
			}
			s.chanOut, err = s.connOut.Channel()
			if err != nil {
				return nil, err
			}
		}
		// TODO: Check parameter settings later.
		s.queue, err = s.chanOut.QueueDeclare(
			outboundChName,
			false,
			false,
			false,
			false,
			nil,
		)
		if err != nil {
			return nil, err
		}
	}
	return s, nil
}

// Start starts the message handling routine.
func (s *MQServer) Start() error {
	go s.handleIncomingMsgRoutine()
	return nil
}

// Request sends a message to the message queue.
func (s *MQServer) Request(version string, msgType string, msg messages.Message) error {
	return s.chanOut.Publish(
		"",
		s.queue.Name,
		false,
		false,
		amqp.Publishing{
			Headers: amqp.Table{"version": version},
			Type:    msgType,
			Body:    msg.ToBytes(),
		})
}

// handleIncomingMsgRoutine is a routine for handling incoming messages.
func (s *MQServer) handleIncomingMsgRoutine() {
	for {
		select {
		case req := <-s.delivery:
			version, ok := req.Headers["version"].(string)
			if !ok {
				// Every message should have a valid version.
				logging.Info("Incoming message does not have version :%v", req)
				continue
			}
			msgType := req.Type
			msg, err := messages.DecodeMessage(version, msgType, req.Body)
			if err != nil {
				logging.Info("Fail to decode message %v: %v", req, err.Error())
				continue
			}
			// Find handler
			handlers, ok := s.handlers[version]
			if !ok {
				logging.Info("Fail to find handler for message %v with version %v", req, version)
				continue
			}
			handler, ok := handlers[msgType]
			if !ok {
				logging.Info("Fail to find handler for message %v with type %v", req, msgType)
				continue
			}
			// Start a routine to handle message.
			// logging.Debug("Handled messasge %v with version %v and type %v", req, version, msgType)
			go handler(msg)
		case <-s.shutdown:
			// Shutdown the routine
			logging.Info("Routine shutdown...")
			return
		}
	}
}

// Stop shuts down the server.
func (s *MQServer) Stop() {
	s.shutdown <- true
	if s.chanIn != nil {
		s.chanIn.Close()
		s.connIn.Close()
	}
	if s.chanOut != nil {
		s.chanOut.Close()
		s.connOut.Close()
	}
}
