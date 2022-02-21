package main

import (
	"github.com/consensys/gpact/messaging/message-store/internal/api"
	"github.com/gin-gonic/gin"
	badger "github.com/ipfs/go-ds-badger2"
	"io/ioutil"
	"log"
	"os"
)

func main() {
	r := gin.Default()
	dsopts := badger.DefaultOptions
	dsopts.SyncWrites = false
	dsopts.Truncate = true

	// TODO: configurable message ds path and other params
	path, err := ioutil.TempDir(os.TempDir(), "message_store_ds")
	if err != nil {
		log.Fatal("error creating data store")
	}

	m, err := api.NewMessageStoreService(path, &dsopts)
	defer func() {
		m.DataStore.Close()
		os.RemoveAll(path)
	}()

	api.SetupRouter(r, m)
	err = r.Run()
	if err != nil {
		log.Fatal("error configuring routes")
	}
}
