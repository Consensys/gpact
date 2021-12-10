package eth

import (
	"context"
	"log"
	"math/big"

	"github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
)

type EventListener interface {
	StartListener()
}

type FilteredEventListener struct {
	Client  *ethclient.Client
	Filter  ethereum.FilterQuery
	Handler EventHandler
	Context context.Context
}

// StartListener starts listening for events that match the specified filter criteria and
// dispatching them to the configured handler for processsing.
func (l *FilteredEventListener) StartListener() {
	chanEvents := make(chan types.Log)
	sub, err := l.Client.SubscribeFilterLogs(l.Context, l.Filter, chanEvents)
	if err != nil {
		log.Fatal(err)
	}

	for {
		select {
		case err := <-sub.Err():
			// TODO: better error handling
			log.Fatal(err)
		case log := <-chanEvents:
			l.Handler.Handle(log)
		}
	}
}

func NewFilteredEventListener(wsURL string, contractAddressHex string, handler EventHandler, context context.Context) (EventListener, error) {
	client, err := ethclient.DialContext(context, wsURL)

	if err != nil {
		return nil, err
	}

	var contractAddress []common.Address
	if contractAddressHex != "" {
		contractAddress = []common.Address{common.HexToAddress(contractAddressHex)}
	}

	query := ethereum.FilterQuery{Addresses: contractAddress}

	return &FilteredEventListener{Client: client, Filter: query, Context: context, Handler: handler}, nil
}
