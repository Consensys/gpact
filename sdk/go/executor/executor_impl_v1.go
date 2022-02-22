package executor

/*
 * Copyright 2022 ConsenSys Software Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import (
	"context"
	"crypto/rand"
	"fmt"
	"math/big"
	"time"

	"github.com/consensys/gpact/sdk/go/chainap"
	"github.com/consensys/gpact/sdk/go/contracts/functioncall"
	"github.com/consensys/gpact/sdk/go/msgstore"
	"github.com/consensys/gpact/sdk/go/treenode"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/ethclient"
	logging "github.com/rs/zerolog/log"
)

// Note:
// At the moment, the nonce management is done by fetching from blockchain source and
// the gas price is obtained by asking blockchain source for suggest gas price.

const (
	// timeout for getting a signed event.
	eventTimeout = 30 * time.Second
	// retry attempt
	retryAttempt = 5
	// retry delay
	retryDelay = 5 * time.Second
)

// Function sigs.
var startFuncSig = [32]byte{0x77, 0xda, 0xb6, 0x11, 0xad, 0x9a, 0x24, 0xb7, 0x63, 0xe2, 0x74, 0x2f, 0x57, 0x74, 0x9a, 0x02, 0x27, 0x39, 0x3e, 0x0d, 0xa7, 0x62, 0x12, 0xd7, 0x4f, 0xce, 0xb3, 0x26, 0xb0, 0x66, 0x14, 0x24}
var segmentFuncSig = [32]byte{0xb0, 0x15, 0x57, 0xf1, 0xf6, 0x34, 0xb7, 0xc5, 0x07, 0x2a, 0xb5, 0xe3, 0x6d, 0x07, 0xa2, 0x35, 0x5e, 0xf8, 0x19, 0xfa, 0xca, 0x5a, 0x3d, 0x32, 0x14, 0x30, 0xd7, 0x19, 0x87, 0x15, 0x5b, 0x8f}
var rootFuncSig = [32]byte{0xe6, 0x76, 0x3d, 0xd9, 0x9b, 0xf8, 0x94, 0xd7, 0x2f, 0x34, 0x99, 0xdd, 0x57, 0x2a, 0xa4, 0x28, 0x76, 0xea, 0xe7, 0xae, 0x02, 0x8c, 0x32, 0xff, 0xf2, 0x16, 0x54, 0xe1, 0xbb, 0xc4, 0xc8, 0x07}

//ExecutorImplV1 implements Executor.
type ExecutorImplV1 struct {
	gpacts map[string]common.Address
	cmgr   chainap.ChainAPManager
	ms     msgstore.MessageStore
	// transactOpts stores the private key for execution. It is updated before every call (to obtain the latest nonce and suggested gas price).
	transactOpts *bind.TransactOpts
}

// NewExecutorImplV1 creates a new executor.
func NewExecutorImplV1(cmgr chainap.ChainAPManager, ms msgstore.MessageStore, transactOpts *bind.TransactOpts) *ExecutorImplV1 {
	return &ExecutorImplV1{
		gpacts:       make(map[string]common.Address),
		cmgr:         cmgr,
		ms:           ms,
		transactOpts: transactOpts,
	}
}

// RegisterGPACT registers a gpact contract to a given chain id.
func (exec *ExecutorImplV1) RegisterGPACT(chainID *big.Int, gpactAddr common.Address) {
	exec.gpacts[chainID.String()] = gpactAddr
}

// CrosschainCall executes a crosschain transaction from the root node of an execution tree.
func (exec *ExecutorImplV1) CrosschainCall(root *treenode.TreeNode) ([]interface{}, error) {
	// Generate a random transaction id
	max := new(big.Int)
	max.Exp(big.NewInt(2), big.NewInt(256), nil).Sub(max, big.NewInt(1))
	transID, err := rand.Int(rand.Reader, max)
	if err != nil {
		return nil, err
	}

	// Start.
	startEvent, startEventSig, err := exec.start(root, transID)
	if err != nil {
		return nil, err
	}

	// Get Segment events for every child.
	segmentChainIDs := make([]*big.Int, 0)
	segmentEvents := make([]*functioncall.GpactSegment, 0)
	segmentEventSigs := make([][]byte, 0)
	lockedSegments := make(map[string][]*functioncall.GpactSegment)
	lockedSegmentsSigs := make(map[string][][]byte)
	for index, child := range root.Children() {
		segEvent, segEventSig, err := exec.segment(transID, root.ChainID(), startEvent, startEventSig, child, []*big.Int{big.NewInt(int64(index + 1))}, lockedSegments, lockedSegmentsSigs)
		if err != nil {
			return nil, err
		}
		segmentChainIDs = append(segmentChainIDs, child.ChainID())
		segmentEvents = append(segmentEvents, segEvent)
		segmentEventSigs = append(segmentEventSigs, segEventSig)
	}

	// Root.
	rootEvent, rootEventSig, err := exec.root(transID, root.ChainID(), startEvent, startEventSig, segmentChainIDs, segmentEvents, segmentEventSigs)
	if err != nil {
		return nil, err
	}

	// Signal.
	err = exec.signal(transID, root.ChainID(), rootEvent, rootEventSig, lockedSegments, lockedSegmentsSigs)
	if err != nil {
		return nil, err
	}

	return nil, nil
}

// start executes start function call in gpact scheme.
func (exec *ExecutorImplV1) start(root *treenode.TreeNode, transID *big.Int) (*functioncall.GpactStart, []byte, error) {
	// Get chain ap.
	chainAP, err := exec.cmgr.ChainAP(root.ChainID())
	if err != nil {
		return nil, nil, err
	}
	// load contract
	gpactAddr, ok := exec.gpacts[root.ChainID().String()]
	if !ok {
		return nil, nil, fmt.Errorf("no gpact registered for chain id %v", root.ChainID().String())
	}
	igpact, err := functioncall.NewGpact(gpactAddr, chainAP)
	if err != nil {
		return nil, nil, err
	}
	err = exec.updateTransactOpts(chainAP)
	if err != nil {
		return nil, nil, err
	}
	// Try 5 times, 5 seconds apart
	var tx *types.Transaction
	for i := 0; i < retryAttempt; i++ {
		tx, err = igpact.Start(exec.transactOpts, transID, big.NewInt(10000), root.Encode())
		if err == nil {
			logging.Info().Msgf("Start transaction submitted with hash: %v", tx.Hash().String())
			break
		}
		logging.Info().Msgf("Error submitting transaction, Retry...: %v", err.Error())
		exec.transactOpts.GasPrice, err = chainAP.SuggestGasPrice(context.Background())
		if err != nil {
			return nil, nil, err
		}
		time.Sleep(retryDelay)
	}
	ctx, cancel := context.WithDeadline(context.Background(), time.Now().Add(eventTimeout))
	defer cancel()
	opts := bind.WatchOpts{Start: nil, Context: ctx}
	chanEvents := make(chan *functioncall.GpactStart)
	sub, err := igpact.WatchStart(&opts, chanEvents)
	if err != nil {
		return nil, nil, err
	}
	for {
		select {
		case err := <-sub.Err():
			return nil, nil, fmt.Errorf("error in log subscription: %v", err.Error())
		case ev := <-chanEvents:
			if ev.CrossBlockchainTransactionId.Cmp(transID) == 0 {
				// Get sig from message store
				evSig, err := exec.ms.GetSignature(ctx, root.ChainID(), tx.Hash().String())
				if err != nil {
					return nil, nil, err
				}
				return ev, evSig, nil
			}
		}
	}
}

// segment executes segment function call in gpact scheme.
// If the segment depends on other segments, it will recursively resolve dependent segments.
func (exec *ExecutorImplV1) segment(transID *big.Int, startChainID *big.Int, startEvent *functioncall.GpactStart, startEventSig []byte, node *treenode.TreeNode, callPath []*big.Int, lockedSegments map[string][]*functioncall.GpactSegment, lockedSegmentsSigs map[string][][]byte) (*functioncall.GpactSegment, []byte, error) {
	chainIDs := []*big.Int{startChainID}
	cbcAddrs := []common.Address{startEvent.Raw.Address}
	eventFuncSigs := [][32]byte{startFuncSig}
	eventDatas := [][]byte{startEvent.Raw.Data}
	eventSigs := [][]byte{startEventSig}
	if len(node.Children()) > 0 {
		// This is an intermediate node.
		// Append a 0 in the end of the call path.
		callPath = append(callPath, big.NewInt(0))
		// Copy the call path
		childCallPath := make([]*big.Int, 0)
		for _, path := range callPath {
			childCallPath = append(childCallPath, big.NewInt(0).Set(path))
		}
		// Need to collect segments event for all the child nodes.
		for index, child := range node.Children() {
			// Child call path last element starts with 1.
			childCallPath[len(childCallPath)-1] = big.NewInt(int64(index + 1))
			childSegEvent, childSegSig, err := exec.segment(transID, startChainID, startEvent, startEventSig, child, childCallPath, lockedSegments, lockedSegmentsSigs)
			if err != nil {
				return nil, nil, err
			}
			chainIDs = append(chainIDs, child.ChainID())
			cbcAddrs = append(cbcAddrs, childSegEvent.Raw.Address)
			eventFuncSigs = append(eventFuncSigs, segmentFuncSig)
			eventDatas = append(eventDatas, childSegEvent.Raw.Data)
			eventSigs = append(eventSigs, childSegSig)
		}
	}
	// Either this node is a leaf node or this node is an intermediate node and we have already collected all segment events for its child nodes.
	// Get chain ap.
	chainAP, err := exec.cmgr.ChainAP(node.ChainID())
	if err != nil {
		return nil, nil, err
	}
	// load contract
	gpactAddr, ok := exec.gpacts[node.ChainID().String()]
	if !ok {
		return nil, nil, fmt.Errorf("no gpact registered for chain id %v", node.ChainID().String())
	}
	igpact, err := functioncall.NewGpact(gpactAddr, chainAP)
	if err != nil {
		return nil, nil, err
	}
	err = exec.updateTransactOpts(chainAP)
	if err != nil {
		return nil, nil, err
	}
	// Try 5 times, 5 seconds apart
	var tx *types.Transaction
	for i := 0; i < retryAttempt; i++ {
		tx, err = igpact.Segment(exec.transactOpts, chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs, callPath)
		if err == nil {
			logging.Info().Msgf("Segment transaction submitted with hash: %v", tx.Hash().String())
			break
		}
		logging.Info().Msgf("Error submitting transaction, Retry...: %v", err.Error())
		exec.transactOpts.GasPrice, err = chainAP.SuggestGasPrice(context.Background())
		if err != nil {
			return nil, nil, err
		}
		time.Sleep(retryDelay)
	}
	ctx, cancel := context.WithDeadline(context.Background(), time.Now().Add(eventTimeout))
	defer cancel()
	opts := bind.WatchOpts{Start: nil, Context: ctx}
	chanEvents := make(chan *functioncall.GpactSegment)
	sub, err := igpact.WatchSegment(&opts, chanEvents)
	if err != nil {
		return nil, nil, err
	}
	for {
		select {
		case err := <-sub.Err():
			return nil, nil, fmt.Errorf("error in log subscription: %v", err.Error())
		case ev := <-chanEvents:
			if ev.CrossBlockchainTransactionId.Cmp(transID) == 0 {
				// Get sig from message store
				evSig, err := exec.ms.GetSignature(ctx, node.ChainID(), tx.Hash().String())
				if err != nil {
					return nil, nil, err
				}
				if len(ev.LockedContracts) != 0 {
					// Locked contracts.
					segments, ok := lockedSegments[node.ChainID().String()]
					if !ok {
						segments = make([]*functioncall.GpactSegment, 0)
					}
					segments = append(segments, ev)
					lockedSegments[node.ChainID().String()] = segments
					sigs, ok := lockedSegmentsSigs[node.ChainID().String()]
					if !ok {
						sigs = make([][]byte, 0)
					}
					sigs = append(sigs, evSig)
					lockedSegmentsSigs[node.ChainID().String()] = sigs
				}
				return ev, evSig, nil
			}
		}
	}
}

// root executes root function call in gpact scheme.
func (exec *ExecutorImplV1) root(transID *big.Int, startChainID *big.Int, startEvent *functioncall.GpactStart, startEventSig []byte, segmentChainIDs []*big.Int, segmentEvents []*functioncall.GpactSegment, segmentEventSigs [][]byte) (*functioncall.GpactRoot, []byte, error) {
	chainIDs := []*big.Int{startChainID}
	cbcAddrs := []common.Address{startEvent.Raw.Address}
	eventFuncSigs := [][32]byte{startFuncSig}
	eventDatas := [][]byte{startEvent.Raw.Data}
	eventSigs := [][]byte{startEventSig}
	for i := range segmentChainIDs {
		chainIDs = append(chainIDs, segmentChainIDs[i])
		cbcAddrs = append(cbcAddrs, segmentEvents[i].Raw.Address)
		eventFuncSigs = append(eventFuncSigs, segmentFuncSig)
		eventDatas = append(eventDatas, segmentEvents[i].Raw.Data)
		eventSigs = append(eventSigs, segmentEventSigs[i])
	}
	// Get chain ap.
	chainAP, err := exec.cmgr.ChainAP(startChainID)
	if err != nil {
		return nil, nil, err
	}
	// load contract
	gpactAddr, ok := exec.gpacts[startChainID.String()]
	if !ok {
		return nil, nil, fmt.Errorf("no gpact registered for chain id %v", startChainID.String())
	}
	igpact, err := functioncall.NewGpact(gpactAddr, chainAP)
	if err != nil {
		return nil, nil, err
	}
	err = exec.updateTransactOpts(chainAP)
	if err != nil {
		return nil, nil, err
	}
	// Try 5 times, 5 seconds apart
	var tx *types.Transaction
	for i := 0; i < retryAttempt; i++ {
		tx, err = igpact.Root(exec.transactOpts, chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs)
		if err == nil {
			logging.Info().Msgf("Root transaction submitted with hash: %v", tx.Hash().String())
			break
		}
		logging.Info().Msgf("Error submitting transaction, Retry...: %v", err.Error())
		exec.transactOpts.GasPrice, err = chainAP.SuggestGasPrice(context.Background())
		if err != nil {
			return nil, nil, err
		}
		time.Sleep(retryDelay)
	}
	ctx, cancel := context.WithDeadline(context.Background(), time.Now().Add(eventTimeout))
	defer cancel()
	opts := bind.WatchOpts{Start: nil, Context: ctx}
	chanEvents := make(chan *functioncall.GpactRoot)
	sub, err := igpact.WatchRoot(&opts, chanEvents)
	if err != nil {
		return nil, nil, err
	}
	for {
		select {
		case err := <-sub.Err():
			return nil, nil, fmt.Errorf("error in log subscription: %v", err.Error())
		case ev := <-chanEvents:
			if ev.CrossBlockchainTransactionId.Cmp(transID) == 0 {
				// Get sig from message store
				evSig, err := exec.ms.GetSignature(ctx, startChainID, tx.Hash().String())
				if err != nil {
					return nil, nil, err
				}
				return ev, evSig, nil
			}
		}
	}
}

// signal executes signal function call in gpact scheme.
func (exec *ExecutorImplV1) signal(transID *big.Int, rootChainID *big.Int, rootEvent *functioncall.GpactRoot, rootEventSig []byte, lockedSegments map[string][]*functioncall.GpactSegment, lockedSegmentsSigs map[string][][]byte) error {
	for segChainIDStr, segments := range lockedSegments {
		chainIDs := []*big.Int{rootChainID}
		cbcAddrs := []common.Address{rootEvent.Raw.Address}
		eventFuncSigs := [][32]byte{rootFuncSig}
		eventDatas := [][]byte{rootEvent.Raw.Data}
		eventSigs := [][]byte{rootEventSig}

		segChainID, _ := big.NewInt(0).SetString(segChainIDStr, 10)
		for _, segment := range segments {
			chainIDs = append(chainIDs, segChainID)
			cbcAddrs = append(cbcAddrs, segment.Raw.Address)
			eventFuncSigs = append(eventFuncSigs, segmentFuncSig)
			eventDatas = append(eventDatas, segment.Raw.Data)
		}
		eventSigs = append(eventSigs, lockedSegmentsSigs[segChainIDStr]...)
		// Get chain ap.
		chainAP, err := exec.cmgr.ChainAP(segChainID)
		if err != nil {
			return err
		}
		// load contract
		gpactAddr, ok := exec.gpacts[segChainIDStr]
		if !ok {
			return fmt.Errorf("no gpact registered for chain id %v", segChainIDStr)
		}
		igpact, err := functioncall.NewGpact(gpactAddr, chainAP)
		if err != nil {
			return err
		}
		err = exec.updateTransactOpts(chainAP)
		if err != nil {
			return err
		}
		// Try 5 times, 5 seconds apart
		for i := 0; i < retryAttempt; i++ {
			var tx *types.Transaction
			tx, err := igpact.Signalling(exec.transactOpts, chainIDs, cbcAddrs, eventFuncSigs, eventDatas, eventSigs)
			if err == nil {
				logging.Info().Msgf("Signalling transaction submitted with hash: %v", tx.Hash().String())
				break
			}
			logging.Info().Msgf("Error submitting transaction, Retry...: %v", err.Error())
			exec.transactOpts.GasPrice, err = chainAP.SuggestGasPrice(context.Background())
			if err != nil {
				return err
			}
			time.Sleep(retryDelay)
		}
	}
	return nil
}

// updateTransactOpts will update the transaction opts to the latest nonce and gas price.
func (exec *ExecutorImplV1) updateTransactOpts(chainAP *ethclient.Client) error {
	var err error
	exec.transactOpts.GasLimit = uint64(6000000)
	exec.transactOpts.GasPrice, err = chainAP.SuggestGasPrice(context.Background())
	if err != nil {
		return err
	}
	nonce, err := chainAP.NonceAt(context.Background(), exec.transactOpts.From, nil)
	if err != nil {
		return err
	}
	exec.transactOpts.Nonce = big.NewInt(int64(nonce))
	return nil
}
