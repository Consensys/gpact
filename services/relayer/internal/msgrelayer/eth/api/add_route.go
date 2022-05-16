package api

import (
	"encoding/json"
	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/node"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
)

type RouteApiResponse struct {
	Success bool   `json:"success"`
	Message string `json:"message"`
}

func HandleAddRouteToStore(data []byte) ([]byte, error) {
	req := &v1.ApplicationAddress{}
	err := json.Unmarshal(data, req)
	if err != nil {
		return nil, err
	}

	instance := node.GetSingleInstance()
	err = instance.RelayRoutes.AddRouteToStore(req)
	if err != nil {
		return getResponse(false, err.Error())
	}
	return getResponse(true, "")
}

func getResponse(success bool, message string) ([]byte, error) {
	return json.Marshal(RouteApiResponse{Success: success, Message: message})
}
