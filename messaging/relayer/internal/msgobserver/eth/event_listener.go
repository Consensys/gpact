package eth

import (
	"context"
	"log"

	"github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
)

type FilteredEventListener struct {
	Client  *ethclient.Client
	Filter  ethereum.FilterQuery
	Handler EventHandler
	Context context.Context
}

func (l FilteredEventListener) StreamEvents() {
	chanEvents := make(chan types.Log)
	sub, err := l.Client.SubscribeFilterLogs(l.Context, l.Filter, chanEvents)
	if err != nil {
		log.Fatal(err)
	}

	for {
		select {
		case err := <-sub.Err():
			log.Fatal(err)
		case log := <-chanEvents:
			l.Handler.Handle(log)
		}
	}
}

func CreateFilteredEventListener(wsURL string, contractAddressHex string, context context.Context) (*FilteredEventListener, error) {
	client, err := ethclient.DialContext(context, wsURL)

	if err != nil {
		return nil, err
	}

	var contractAddress []common.Address
	if contractAddressHex != "" {
		contractAddress = []common.Address{common.HexToAddress(contractAddressHex)}

	}

	query := ethereum.FilterQuery{Addresses: contractAddress}

	return &FilteredEventListener{Client: client, Filter: query, Context: context}, nil
}
