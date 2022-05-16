package router

import (
	"encoding/json"
	"fmt"
	"github.com/consensys/gpact/services/relayer/internal/logging"
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
	"golang.org/x/net/context"
)

type RelayRoutes struct {
	dsPath string
	ds     datastore.Datastore
}

func getKey(source *v1.ApplicationAddress) datastore.Key {
	return datastore.NewKey(fmt.Sprintf("%s-%s", source.NetworkID, source.ContractAddress))
}

func (r *RelayRoutes) AddRouteToStore(source *v1.ApplicationAddress) error {
	key := getKey(source)
	logging.Info("Adding message route for key: %v", key.String())
	value, err := json.Marshal(source)
	if err != nil {
		return err
	}
	err = r.ds.Put(context.Background(), key, value)
	if err != nil {
		return err
	}
	return nil
}

func (r *RelayRoutes) ShouldRouteToStore(source *v1.ApplicationAddress) bool {
	logging.Info("Checking message route for key: %s", getKey(source).String())
	has, err := r.ds.Has(context.Background(), getKey(source))
	if err != nil {
		return false
	}
	return has
}

func (r *RelayRoutes) Stop() {
	if r.ds != nil {
		if err := r.ds.Close(); err != nil {
			logging.Error("Error in closing the db %v", err.Error())
		}
	}
}

func NewRelayRoutes(dsPath string) (RelayRoutes, error) {
	dsopts := badgerds.DefaultOptions
	dsopts.SyncWrites = false
	dsopts.Truncate = true
	ds, err := badgerds.NewDatastore(dsPath, &dsopts)
	if err != nil {
		logging.Error("Could not create relayer routes datastore: %v", dsPath)
		return RelayRoutes{}, nil
	}
	logging.Info("Relayer routes datastore initialised: %v", dsPath)
	return RelayRoutes{dsPath: dsPath, ds: ds}, nil
}
