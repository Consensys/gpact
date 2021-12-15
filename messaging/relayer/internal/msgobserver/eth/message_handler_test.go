package eth

import (
	"testing"

	"github.com/consensys/gpact/messaging/relayer/internal/messages"
	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
	"github.com/stretchr/testify/mock"
)

type MockMQ struct {
	mock.Mock
}

func (mk *MockMQ) Request(version string, msgType string, msg messages.Message) {
	mk.Called(version, msgType, msg)
}

func (mk *MockMQ) Start() {}

func TestMessageEnqueuHandler(t *testing.T) {
	/*
		1. mock the message queue
		2. pass in a message and check that it is being enqued
		3. deal with the fact that a go routine is spun up
	*/
	fixMsg := v1.Message{
		ID:          "msg-0001",
		Timestamp:   2384923489234,
		MsgType:     v1.MessageType,
		Version:     v1.Version,
		Destination: v1.ApplicationAddress{NetworkID: "network-001"},
		Source:      v1.ApplicationAddress{NetworkID: "network-002"},
	}

	mockMQ := new(MockMQ)

	mockMQ.On("Request", fixMsg.Version, fixMsg.MsgType, &fixMsg).Once()
	handler := NewMessageEnqueueHandler(mockMQ)
	handler.Handle(&fixMsg)

	mockMQ.AssertExpectations(t)
}
