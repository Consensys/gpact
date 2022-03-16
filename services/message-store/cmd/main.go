package main

import (
	"log"

	"github.com/consensys/gpact/messaging/message-store/internal/api"
	config "github.com/consensys/gpact/messaging/message-store/internal/config"
	"github.com/gin-gonic/gin"
	badger "github.com/ipfs/go-ds-badger"
)

func main() {
	conf := config.NewConfig()

	if len(conf.MessageDataStorePath) == 0 {
		log.Fatal("Message data store path cannot be empty.")
	}

	m, err := api.NewMessageStoreService(conf.MessageDataStorePath, &badger.DefaultOptions)

	r := gin.Default()
	api.SetupRouter(r, m)

	if len(conf.ServiceAddress) > 0 {
		err = r.Run(conf.ServiceAddress)
	} else {
		err = r.Run()
	}

	if err != nil {
		log.Fatal("error starting message store service")
	}
}
