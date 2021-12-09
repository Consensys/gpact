package main

import (
	"log"

	ethobserver "github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth"
)

func main() {
	config := ethobserver.MessageObserverConfig{EventLogWSURL: "", FilterAddress: ""}

	observer, err := ethobserver.CreateMessageObserver(config)
	if err != nil {
		log.Fatal(err)
	}

	observer.Start()
}
