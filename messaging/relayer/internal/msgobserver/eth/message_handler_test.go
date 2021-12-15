package eth

import (
	"testing"

	v1 "github.com/consensys/gpact/messaging/relayer/internal/messages/v1"
)
func TestMessageEnqueuHandler(t *testing.T) {
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

// TODO: verify encoded payload