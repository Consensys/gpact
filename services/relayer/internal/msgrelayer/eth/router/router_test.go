package router

import (
	v1 "github.com/consensys/gpact/services/relayer/pkg/messages/v1"
	badger "github.com/ipfs/go-ds-badger"
	"github.com/stretchr/testify/assert"
	"io/ioutil"
	"os"
	"testing"
)

func TestRegisterRouteToStore(t *testing.T) {
	fixSource := v1.ApplicationAddress{NetworkID: "31", ContractAddress: "0x8e215d06ea7ec1fdb4fc5fd21768f4b34ee92ef4"}

	ds, dsClose := newDS(t)
	defer dsClose()

	router := RelayRouter{ds}

	assert.False(t, router.ShouldRouteToStore(&fixSource))
	assert.Nil(t, router.RegisterRouteToStore(&fixSource))
	assert.True(t, router.ShouldRouteToStore(&fixSource))
}

func newDS(t *testing.T) (*badger.Datastore, func()) {
	path, err := ioutil.TempDir(os.TempDir(), "testing_badger_")
	if err != nil {
		t.Fatal(err)
	}

	d, err := badger.NewDatastore(path, nil)
	if err != nil {
		t.Fatal(err)
	}
	return d, func() {
		d.Close()
		os.RemoveAll(path)
	}
}
