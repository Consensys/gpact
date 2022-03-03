package observer

import (
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ipfs/go-datastore"
	badgerds "github.com/ipfs/go-ds-badger"
	"github.com/stretchr/testify/mock"
	"time"
)

type MockEventHandler struct {
	mock.Mock
}

func (m *MockEventHandler) Handle(event interface{}) {
	m.Called(event)
}

var fixWatcherProgressDsOpts = WatcherProgressDsOpts{
	FailureRetryOpts: FailureRetryOpts{
		RetryAttempts: 3,
		RetryDelay:    500 * time.Millisecond,
	},
}
var fixEventHandleRetryOpts = DefaultRetryOptions

func mineConfirmingBlocks(confirms uint64, simBackend *backends.SimulatedBackend) {
	for i := uint64(0); i < confirms; i++ {
		commit(simBackend)
	}
}

func commit(backend *backends.SimulatedBackend) {
	backend.Commit()
}

func commitAndSleep(backend *backends.SimulatedBackend) {
	commit(backend)
	time.Sleep(2 * time.Second)
}

func createProgressOpts(ds *badgerds.Datastore) WatcherProgressDsOpts {
	progOpts := fixWatcherProgressDsOpts
	progOpts.ds = ds
	progOpts.dsProgKey = datastore.NewKey("reorg_test")
	return progOpts
}
