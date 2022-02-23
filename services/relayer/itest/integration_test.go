package itest

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
	"crypto/ecdsa"
	"errors"
	"fmt"
	"math/big"
	"sync"
	"testing"
	"time"

	"github.com/ConsenSys/gpact/sdk/go/chainap"
	"github.com/ConsenSys/gpact/sdk/go/executor"
	"github.com/ConsenSys/gpact/sdk/go/simulator"
	"github.com/consensys/gpact/services/relayer/internal/contracts/application"
	"github.com/consensys/gpact/services/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/services/relayer/internal/contracts/messaging"
	"github.com/consensys/gpact/services/relayer/internal/crypto"
	dispatcherapi "github.com/consensys/gpact/services/relayer/internal/msgdispatcher/eth/api"
	observerapi "github.com/consensys/gpact/services/relayer/internal/msgobserver/eth/api"
	relayerapi "github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/api"
	"github.com/consensys/gpact/services/relayer/internal/msgrelayer/eth/signer"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/crypto/secp256k1"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/stretchr/testify/assert"
)

// Function sigs.
var startFuncSig = [32]byte{0x77, 0xda, 0xb6, 0x11, 0xad, 0x9a, 0x24, 0xb7, 0x63, 0xe2, 0x74, 0x2f, 0x57, 0x74, 0x9a, 0x02, 0x27, 0x39, 0x3e, 0x0d, 0xa7, 0x62, 0x12, 0xd7, 0x4f, 0xce, 0xb3, 0x26, 0xb0, 0x66, 0x14, 0x24}
var segmentFuncSig = [32]byte{0xb0, 0x15, 0x57, 0xf1, 0xf6, 0x34, 0xb7, 0xc5, 0x07, 0x2a, 0xb5, 0xe3, 0x6d, 0x07, 0xa2, 0x35, 0x5e, 0xf8, 0x19, 0xfa, 0xca, 0x5a, 0x3d, 0x32, 0x14, 0x30, 0xd7, 0x19, 0x87, 0x15, 0x5b, 0x8f}
var rootFuncSig = [32]byte{0xe6, 0x76, 0x3d, 0xd9, 0x9b, 0xf8, 0x94, 0xd7, 0x2f, 0x34, 0x99, 0xdd, 0x57, 0x2a, 0xa4, 0x28, 0x76, 0xea, 0xe7, 0xae, 0x02, 0x8c, 0x32, 0xff, 0xf2, 0x16, 0x54, 0xe1, 0xbb, 0xc4, 0xc8, 0x07}

var userA *bind.TransactOpts
var userB *bind.TransactOpts
var relayer *bind.TransactOpts
var relayerKey []byte
var erc20A common.Address
var erc20B common.Address
var bridgeA common.Address
var bridgeB common.Address
var gpactA common.Address
var gpactB common.Address

// TestERC20SetupSFC setups a crosschain erc20 transfer (SFC).
func TestERC20SetupSFC(t *testing.T) {
	// First of all, connect to chains.
	chainA, err := ethclient.Dial("ws://127.0.0.1:8311")
	if err != nil {
		panic(err)
	}
	defer chainA.Close()

	chainB, err := ethclient.Dial("ws://127.0.0.1:8321")
	if err != nil {
		panic(err)
	}
	defer chainB.Close()

	// Create accounts
	_, admin := createUser()           // admin to create all contracts
	_, userA = createUser()            // userA on chainA
	_, userB = createUser()            // userB on chainB
	relayerKey, relayer = createUser() // relayer key
	dispatcherKey, _ := createUser()   // dispatcher key

	var tx *types.Transaction
	// Deploy ERC20 contracts on both chains.
	t.Log("Deploy ERC20 contract on chainA...")
	erc20AddrA, tx, ierc20A, err := application.DeployERC20FixedSupply(admin, chainA, "chainA", "ca", big.NewInt(10000), admin.From)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(erc20AddrA)
	t.Log("Deploy ERC20 contract on chainB...")
	erc20AddrB, tx, ierc20B, err := application.DeployERC20FixedSupply(admin, chainB, "chainB", "ca", big.NewInt(10000), admin.From)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(erc20AddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))
	erc20A = erc20AddrA
	erc20B = erc20AddrB

	// Deploy SFC contracts on both chains
	t.Log("Deploy SFC contract on chainA...")
	sfcAddrA, tx, isfcA, err := functioncall.DeploySfc(admin, chainA, big.NewInt(31), big.NewInt(100000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(sfcAddrA)
	t.Log("Deploy SFC contract on chainB...")
	sfcAddrB, tx, isfcB, err := functioncall.DeploySfc(admin, chainB, big.NewInt(32), big.NewInt(100000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(sfcAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy registrar on both chains
	t.Log("Deploy registrar on chainA...")
	regAddrA, tx, iregA, err := messaging.DeployRegistrar(admin, chainA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(regAddrA)
	t.Log("Deploy registrar on chainB...")
	regAddrB, tx, iregB, err := messaging.DeployRegistrar(admin, chainB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(regAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy verifier on both chains
	t.Log("Deploy verifier on chainA...")
	verifierAddrA, tx, _, err := messaging.DeploySignedEventStore(admin, chainA, regAddrA, sfcAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(verifierAddrA)
	t.Log("Deploy verifier on chainB...")
	verifierAddrB, tx, _, err := messaging.DeploySignedEventStore(admin, chainB, regAddrB, sfcAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(verifierAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add relayer on both chains
	t.Log("Add signer on chainA...")
	tx, err = iregA.AddSignerSetThreshold(admin, big.NewInt(32), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add signer on chainB...")
	tx, err = iregB.AddSignerSetThreshold(admin, big.NewInt(31), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add verifier on both chains
	t.Log("Add verifier on chainA...")
	tx, err = isfcA.AddVerifier(admin, big.NewInt(32), verifierAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add verifier on chainB...")
	tx, err = isfcB.AddVerifier(admin, big.NewInt(31), verifierAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add sfc mapping on both chains
	t.Log("Add sfc mapping on chainA...")
	tx, err = isfcA.AddRemoteCrosschainControl(admin, big.NewInt(32), sfcAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add sfc mapping on chainB...")
	tx, err = isfcB.AddRemoteCrosschainControl(admin, big.NewInt(31), sfcAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy bridge
	t.Log("Deploy bridge on chainA...")
	bridgeAddrA, tx, ibridgeA, err := application.DeploySfcERC20Bridge(admin, chainA, sfcAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(bridgeAddrA)
	t.Log("Deploy bridge on chainB...")
	bridgeAddrB, tx, ibridgeB, err := application.DeploySfcERC20Bridge(admin, chainB, sfcAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(bridgeAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))
	bridgeA = bridgeAddrA
	bridgeB = bridgeAddrB

	// Setup bridge
	t.Log("Change blockchain mapping on chainA...")
	tx, err = ibridgeA.ChangeBlockchainMapping(admin, big.NewInt(32), bridgeAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Change blockchain mapping on chainB...")
	tx, err = ibridgeB.ChangeBlockchainMapping(admin, big.NewInt(31), bridgeAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))
	t.Log("Add contract first mapping on chainA...")
	tx, err = ibridgeA.AddContractFirstMapping(admin, erc20AddrA, big.NewInt(32), erc20AddrB, true)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add contract first mapping on chainB...")
	tx, err = ibridgeB.AddContractFirstMapping(admin, erc20AddrB, big.NewInt(31), erc20AddrA, true)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Transfer userA & bridges some balance
	t.Log("Transfer tokens to userA...")
	tx, err = ierc20A.Transfer(admin, userA.From, big.NewInt(100))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))
	t.Log("Transfer tokens to bridgeA...")
	tx, err = ierc20A.Transfer(admin, bridgeAddrA, big.NewInt(1000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	admin.Nonce.Sub(admin.Nonce, big.NewInt(1))
	t.Log("Transfer tokens to bridgeB...")
	tx, err = ierc20B.Transfer(admin, bridgeAddrB, big.NewInt(1000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")

	// Setup relayers
	t.Log("Setup relayers...")

	assert.Empty(t, setupObserver("127.0.0.1:9525", big.NewInt(31), "ws://bc31node1:8546", "SFC", sfcAddrA))
	assert.Empty(t, setupObserver("127.0.0.1:9526", big.NewInt(32), "ws://bc32node1:8546", "SFC", sfcAddrB))
	assert.Empty(t, setupRelayer("127.0.0.1:9625", big.NewInt(31), bridgeAddrA, signer.SECP256K1_KEY_TYPE, relayerKey))
	assert.Empty(t, setupRelayer("127.0.0.1:9625", big.NewInt(32), bridgeAddrB, signer.SECP256K1_KEY_TYPE, relayerKey))
	assert.Empty(t, setupDispatcher("127.0.0.1:9725", big.NewInt(31), "ws://bc31node1:8546", dispatcherKey, bridgeAddrA, verifierAddrA))
	assert.Empty(t, setupDispatcher("127.0.0.1:9725", big.NewInt(32), "ws://bc32node1:8546", dispatcherKey, bridgeAddrB, verifierAddrB))

	t.Log("Setup done")
}

// TestERC20TransferSFC tests a crosschain erc20 transfer (SFC).
func TestERC20TransferSFC(t *testing.T) {
	// First of all, connect to chains.
	chainA, err := ethclient.Dial("ws://127.0.0.1:8311")
	if err != nil {
		panic(err)
	}
	defer chainA.Close()

	chainB, err := ethclient.Dial("ws://127.0.0.1:8321")
	if err != nil {
		panic(err)
	}
	defer chainB.Close()

	// Load contracts
	ierc20A, err := application.NewERC20FixedSupply(erc20A, chainA)
	if err != nil {
		panic(err)
	}
	ierc20B, err := application.NewERC20FixedSupply(erc20B, chainB)
	if err != nil {
		panic(err)
	}
	ibridgeA, err := application.NewSfcERC20Bridge(bridgeA, chainA)
	if err != nil {
		panic(err)
	}
	ibridgeB, err := application.NewSfcERC20Bridge(bridgeB, chainB)
	if err != nil {
		panic(err)
	}

	balA, err := ierc20A.BalanceOf(nil, userA.From)
	if err != nil {
		panic(err)
	}
	balB, err := ierc20B.BalanceOf(nil, userB.From)
	if err != nil {
		panic(err)
	}
	assert.Equal(t, 0, balA.Cmp(big.NewInt(100)))
	assert.Equal(t, 0, balB.Cmp(big.NewInt(0)))
	t.Log("Balances before transfer: ", balA, balB)

	// userA do crosschain transfer to userB
	t.Log("UserA crosschain transfer to UserB with 10 tokens...")
	tx, err := ierc20A.Approve(userA, bridgeA, big.NewInt(10))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	userA.Nonce.Add(userA.Nonce, big.NewInt(1))
	tx, err = ibridgeA.TransferToOtherBlockchain(userA, big.NewInt(32), erc20A, userB.From, big.NewInt(10))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	userA.Nonce.Add(userA.Nonce, big.NewInt(1))
	t.Log("Done")

	// Wait for 5 seconds, TODO: Monitor bridge contracts to know current step.
	t.Log("Wait for relayers to process events...")
	time.Sleep(5 * time.Second)
	t.Log("Done")
	balA, _ = ierc20A.BalanceOf(nil, userA.From)
	balB, _ = ierc20B.BalanceOf(nil, userB.From)
	assert.Equal(t, 0, balA.Cmp(big.NewInt(90)))
	assert.Equal(t, 0, balB.Cmp(big.NewInt(10)))
	t.Log("Balances after transfer: ", balA, balB)

	// Transfer back
	t.Log("UserB crosschain transfer back to UserA with 5 tokens...")
	tx, err = ierc20B.Approve(userB, bridgeB, big.NewInt(5))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	userB.Nonce.Add(userB.Nonce, big.NewInt(1))
	tx, err = ibridgeB.TransferToOtherBlockchain(userB, big.NewInt(31), erc20B, userA.From, big.NewInt(5))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	userB.Nonce.Add(userB.Nonce, big.NewInt(1))
	t.Log("Done")

	// Wait for 5 seconds, TODO: Monitor bridge contracts to know current step.
	t.Log("Wait for relayers to process events...")
	time.Sleep(5 * time.Second)
	t.Log("Done")
	balA, _ = ierc20A.BalanceOf(nil, userA.From)
	balB, _ = ierc20B.BalanceOf(nil, userB.From)
	assert.Equal(t, 0, balA.Cmp(big.NewInt(95)))
	assert.Equal(t, 0, balB.Cmp(big.NewInt(5)))
	t.Log("Balances after transfer back: ", balA, balB)
}

// TestERC20SetupGpact setups a crosschain erc20 transfer (GPACT).
func TestERC20SetupGpact(t *testing.T) {
	// First of all, connect to chains.
	chainA, err := ethclient.Dial("ws://127.0.0.1:8311")
	if err != nil {
		panic(err)
	}
	defer chainA.Close()

	chainB, err := ethclient.Dial("ws://127.0.0.1:8321")
	if err != nil {
		panic(err)
	}
	defer chainB.Close()

	// Create accounts
	_, admin := createUser() // admin to create all contracts
	_, userA = createUser()  // userA on chainA
	_, userB = createUser()  // userB on chainB

	// Deploy registrar on both chains
	t.Log("Deploy registrar on chainA...")
	regAddrA, tx, iregA, err := messaging.DeployRegistrar(admin, chainA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(regAddrA)
	t.Log("Deploy registrar on chainB...")
	regAddrB, tx, iregB, err := messaging.DeployRegistrar(admin, chainB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(regAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add relayer on both chains
	t.Log("Add signer on chainA...")
	tx, err = iregA.AddSignerSetThreshold(admin, big.NewInt(32), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add signer on chainB...")
	tx, err = iregB.AddSignerSetThreshold(admin, big.NewInt(31), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	t.Log("Add signer on chainA...")
	tx, err = iregA.AddSignerSetThreshold(admin, big.NewInt(31), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add signer on chainB...")
	tx, err = iregB.AddSignerSetThreshold(admin, big.NewInt(32), relayer.From, big.NewInt(1))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy verifiers
	t.Log("Deploy verifier on chainA...")
	verifierAddrA, tx, _, err := messaging.DeployEventAttestationVerifier(admin, chainA, regAddrA)
	waitForReceipt(chainA, tx)
	t.Log(verifierAddrA)
	t.Log("Deploy verifier on chainB...")
	verifierAddrB, tx, _, err := messaging.DeployEventAttestationVerifier(admin, chainB, regAddrB)
	waitForReceipt(chainB, tx)
	t.Log(verifierAddrB)
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy GPACT contracts on both chains
	t.Log("Deploy GPACT contract on chainA...")
	gpactAddrA, tx, igpactA, err := functioncall.DeployGpact(admin, chainA, big.NewInt(31))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(gpactAddrA)
	gpactA = gpactAddrA
	t.Log("Deploy GPACT contract on chainB...")
	gpactAddrB, tx, igpactB, err := functioncall.DeployGpact(admin, chainB, big.NewInt(32))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(gpactAddrB)
	gpactB = gpactAddrB
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add verifiers on both chains.
	t.Log("Add verifier on chainA...")
	tx, err = igpactA.AddVerifier(admin, big.NewInt(31), verifierAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add verifier on chainB...")
	tx, err = igpactB.AddVerifier(admin, big.NewInt(31), verifierAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	t.Log("Add verifier on chainA...")
	tx, err = igpactA.AddVerifier(admin, big.NewInt(32), verifierAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add verifier on chainB...")
	tx, err = igpactB.AddVerifier(admin, big.NewInt(32), verifierAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add gpact mapping on both chains.
	t.Log("Add GPACT mapping on chainA...")
	tx, err = igpactA.AddRemoteCrosschainControl(admin, big.NewInt(32), gpactAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add GPACT mapping on chainB...")
	tx, err = igpactB.AddRemoteCrosschainControl(admin, big.NewInt(31), gpactAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	t.Log("Add GPACT mapping on chainA...")
	tx, err = igpactA.AddRemoteCrosschainControl(admin, big.NewInt(31), gpactAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add GPACT mapping on chainB...")
	tx, err = igpactB.AddRemoteCrosschainControl(admin, big.NewInt(32), gpactAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy Lockable ERC20 contracts on both chains.
	t.Log("Deploy ERC20 contract on chainA...")
	erc20AddrA, tx, ierc20A, err := application.DeployLockableERC20FixedSupply(admin, chainA, "chainA", "ca", gpactAddrA, big.NewInt(10000), admin.From)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(erc20AddrA)
	erc20A = erc20AddrA
	t.Log("Deploy ERC20 contract on chainB...")
	erc20AddrB, tx, ierc20B, err := application.DeployLockableERC20FixedSupply(admin, chainB, "chainB", "cb", gpactAddrB, big.NewInt(10000), admin.From)
	waitForReceipt(chainB, tx)
	t.Log(erc20AddrB)
	erc20B = erc20AddrB
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Deploy bridge
	t.Log("Deploy bridge on chainA...")
	bridgeAddrA, tx, ibridgeA, err := application.DeployGpactERC20Bridge(admin, chainA, gpactAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log(bridgeAddrA)
	bridgeA = bridgeAddrA
	t.Log("Deploy bridge on chainB...")
	bridgeAddrB, tx, ibridgeB, err := application.DeployGpactERC20Bridge(admin, chainB, gpactAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log(bridgeAddrB)
	bridgeB = bridgeAddrB
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add contract first mapping
	t.Log("Add contract first mapping on chainA...")
	tx, err = ibridgeA.AddContractFirstMapping(admin, erc20AddrA, big.NewInt(32), erc20AddrB, true)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add contract first mapping on chainB...")
	tx, err = ibridgeB.AddContractFirstMapping(admin, erc20AddrB, big.NewInt(31), erc20AddrA, true)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add remote erc20 bridge address
	t.Log("Add remote erc20 bridge addr on chainA...")
	tx, err = ibridgeA.AddRemoteERC20Bridge(admin, big.NewInt(32), bridgeAddrB)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add remote erc20 bridge addr on chainB...")
	tx, err = ibridgeB.AddRemoteERC20Bridge(admin, big.NewInt(31), bridgeAddrA)
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Add approved chain
	t.Log("Add approved root bc on chainA...")
	tx, err = ibridgeA.AddApprovedRootBcId(admin, big.NewInt(32))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Add approved root bc on chainB...")
	tx, err = ibridgeB.AddApprovedRootBcId(admin, big.NewInt(31))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")
	admin.Nonce.Add(admin.Nonce, big.NewInt(1))

	// Give some balances.
	t.Log("Give some balance to userA on chainA...")
	tx, err = ierc20A.Transfer(admin, userA.From, big.NewInt(100))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Give some balance to bridgeB on chainB...")
	tx, err = ierc20B.Transfer(admin, bridgeAddrB, big.NewInt(1000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")

	t.Log("Setup relayers...")

	assert.Empty(t, setupObserver("127.0.0.1:9527", big.NewInt(31), "ws://bc31node1:8546", "GPACT", gpactAddrA))
	assert.Empty(t, setupObserver("127.0.0.1:9526", big.NewInt(32), "ws://bc32node1:8546", "GPACT", gpactAddrB))
	assert.Empty(t, setupRelayer("127.0.0.1:9625", big.NewInt(0), common.Address{}, signer.SECP256K1_KEY_TYPE, relayerKey))
	assert.Empty(t, setupMessageStore("127.0.0.1:9725", "msgstore:8080"))

	t.Log("Setup done")
}

func TestERC20TransferGpact(t *testing.T) {
	// First of all, connect to chains.
	chainA, err := ethclient.Dial("ws://127.0.0.1:8311")
	if err != nil {
		panic(err)
	}
	defer chainA.Close()

	chainB, err := ethclient.Dial("ws://127.0.0.1:8321")
	if err != nil {
		panic(err)
	}
	defer chainB.Close()

	// Load contracts
	ierc20A, err := application.NewLockableERC20FixedSupply(erc20A, chainA)
	if err != nil {
		panic(err)
	}
	ierc20B, err := application.NewLockableERC20FixedSupply(erc20B, chainB)
	if err != nil {
		panic(err)
	}
	igpactA, err := functioncall.NewGpact(gpactA, chainA)
	if err != nil {
		panic(err)
	}
	igpactB, err := functioncall.NewGpact(gpactB, chainB)
	if err != nil {
		panic(err)
	}

	// Approve first
	t.Log("Approve transfer")
	tx, err := ierc20A.Approve(userA, bridgeA, big.NewInt(10))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")

	// Create simulator & executor
	cmgr := chainap.NewChainAPManagerImplV1()
	cmgr.RegisterChainAP(big.NewInt(31), chainA)
	cmgr.RegisterChainAP(big.NewInt(32), chainB)
	sim := simulator.NewSimulatorImplV1(cmgr)
	ms := &MockMsgStore{Addr: relayer.From, Key: relayerKey, Events: map[string]map[string][]byte{}, Lock: sync.RWMutex{}}
	ms.Watch(big.NewInt(31), chainA, igpactA)
	ms.Watch(big.NewInt(32), chainB, igpactB)
	exec := executor.NewExecutorImplV1(cmgr, ms, userA)
	exec.RegisterGPACT(big.NewInt(31), gpactA)
	exec.RegisterGPACT(big.NewInt(32), gpactB)
	bridgeABI, _ := application.GpactERC20BridgeMetaData.GetAbi()
	sim.RegisterABI("bridge", bridgeABI)
	// Register contract logic
	sim.RegisterCallLink("bridge", "transferToOtherBlockchain", func(resource simulator.Resource, chainID *big.Int, contractAddr common.Address, params ...interface{}) ([]simulator.CrosschainCall, error) {
		// Parse arguments.
		if len(params) != 4 {
			return nil, fmt.Errorf("error parsing arguments, expect 4, got %v", len(params))
		}
		destBcID, ok0 := params[0].(*big.Int)
		srcTokenContractAddr, ok1 := params[1].(common.Address)
		recipient, ok2 := params[2].(common.Address)
		amount, ok3 := params[3].(*big.Int)
		if !ok0 || !ok1 || !ok2 || !ok3 {
			return nil, fmt.Errorf("error parsing arguments, casting error")
		}
		ap, err := resource.ChainAPMgr.ChainAP(chainID)
		if err != nil {
			return nil, err
		}
		ibridge, err := application.NewGpactERC20Bridge(contractAddr, ap)
		if err != nil {
			return nil, err
		}
		destAddr, err := ibridge.GetRemoteErc20BridgeContract(nil, destBcID)
		if err != nil {
			return nil, err
		}
		empty := common.Address{}
		if destAddr == empty {
			return nil, fmt.Errorf("empty dest address")
		}
		destTokenContractAddr, err := ibridge.GetBcIdTokenMaping(nil, destBcID, srcTokenContractAddr)
		if err != nil {
			return nil, err
		}
		if destTokenContractAddr == empty {
			return nil, fmt.Errorf("empty dest address")
		}
		return []simulator.CrosschainCall{
			{
				ChainID:      destBcID,
				ContractType: "bridge",
				ContractAddr: destAddr,
				Method:       "receiveFromOtherBlockchain",
				Params: []interface{}{
					destTokenContractAddr,
					recipient,
					amount,
				},
			},
		}, nil
	})

	// Simulate crosschain call.
	root, err := sim.Simulate(big.NewInt(31), "bridge", bridgeA, "transferToOtherBlockchain", big.NewInt(32), erc20A, userB.From, big.NewInt(10))
	if err != nil {
		panic(err)
	}

	// Balance before transfer.
	balA, _ := ierc20A.BalanceOf(nil, userA.From)
	balB, _ := ierc20B.BalanceOf(nil, userB.From)
	assert.Equal(t, 0, balA.Cmp(big.NewInt(100)))
	assert.Equal(t, 0, balB.Cmp(big.NewInt(0)))

	// Transfer
	_, err = exec.CrosschainCall(root)
	if err != nil {
		panic(err)
	}
	time.Sleep(1 * time.Second)

	// Balance after transfer.
	balA, _ = ierc20A.BalanceOf(nil, userA.From)
	balB, _ = ierc20B.BalanceOf(nil, userB.From)
	assert.Equal(t, 0, balA.Cmp(big.NewInt(90)))
	assert.Equal(t, 0, balB.Cmp(big.NewInt(10)))
}

// createUser creates a user with random key.
func createUser() ([]byte, *bind.TransactOpts) {
	key, err := crypto.Secp256k1GenerateKey()
	if err != nil {
		panic(err)
	}
	// Create the same key
	x, y := secp256k1.S256().ScalarBaseMult(key)
	prv := &ecdsa.PrivateKey{}
	prv.D = big.NewInt(0).SetBytes(key)
	prv.PublicKey = ecdsa.PublicKey{
		X:     x,
		Y:     y,
		Curve: secp256k1.S256(),
	}
	auth := bind.NewKeyedTransactor(prv)
	auth.Nonce = big.NewInt(int64(0))
	auth.Value = big.NewInt(0)      // in wei
	auth.GasLimit = uint64(6000000) // in units
	auth.GasPrice = big.NewInt(0)
	return key, auth
}

// waitForReceipt waits for receipt.
func waitForReceipt(conn *ethclient.Client, tx *types.Transaction) error {
	for {
		rept, err := conn.TransactionReceipt(context.Background(), tx.Hash())
		if err == nil {
			if rept.Status != types.ReceiptStatusSuccessful {
				panic(errors.New("Transaction failed..."))
			} else {
			}
			break
		}
		time.Sleep(1 * time.Second)
	}
	return nil
}

// setupObserver sets up observer.
func setupObserver(url string, chainID *big.Int, chainAP string, contractType string, contractAddr common.Address) error {
	success, err := observerapi.RequestStartObserve(url, chainID, chainAP, contractType, contractAddr)
	if err != nil {
		return err
	}
	if !success {
		return fmt.Errorf("failed.")
	}
	return nil
}

// setupRelayer sets up relayer.
func setupRelayer(url string, chainID *big.Int, contractAddr common.Address, keyType byte, key []byte) error {
	success, err := relayerapi.RequestSetKey(url, chainID, contractAddr, keyType, key)
	if err != nil {
		return err
	}
	if !success {
		return fmt.Errorf("failed.")
	}
	return nil
}

// setupDispatcher sets up dispatcher.
func setupDispatcher(url string, chainID *big.Int, chainAP string, key []byte, contractAddr common.Address, esAddr common.Address) error {
	success, err := dispatcherapi.RequestSetTransactionOpts(url, chainID, chainAP, key)
	if err != nil {
		return err
	}
	if !success {
		return fmt.Errorf("failed.")
	}
	success, err = dispatcherapi.RequestSetVerifierAddr(url, chainID, contractAddr, esAddr)
	if err != nil {
		return err
	}
	if !success {
		return fmt.Errorf("failed.")
	}
	return nil
}

// setupMessageStore sets up message store.
func setupMessageStore(url string, msgStoreAddr string) error {
	success, err := dispatcherapi.RequestSetMsgStoreAddr(url, msgStoreAddr)
	if err != nil {
		return err
	}
	if !success {
		return fmt.Errorf("failed.")
	}
	return nil
}

// MockMsgStore is a mock msg store.
type MockMsgStore struct {
	Addr   common.Address
	Key    []byte
	Events map[string]map[string][]byte
	Lock   sync.RWMutex
}

func (mgr *MockMsgStore) Watch(chainID *big.Int, chain *ethclient.Client, igpact *functioncall.Gpact) {
	_, ok := mgr.Events[chainID.String()]
	if !ok {
		mgr.Events[chainID.String()] = make(map[string][]byte)
	}
	go func() {
		opts := bind.WatchOpts{Start: nil, Context: context.Background()}
		chanEvents := make(chan *functioncall.GpactStart)
		sub, err := igpact.WatchStart(&opts, chanEvents)
		if err != nil {
			return
		}
		for {
			select {
			case <-sub.Err():
				return
			case ev := <-chanEvents:
				mgr.Lock.Lock()
				mgr.Events[chainID.String()][ev.Raw.TxHash.String()], _ = mgr.sign(chainID, ev.Raw.Address, startFuncSig[:], ev.Raw.Data)
				mgr.Lock.Unlock()
			}
		}
	}()
	go func() {
		opts := bind.WatchOpts{Start: nil, Context: context.Background()}
		chanEvents := make(chan *functioncall.GpactSegment)
		sub, err := igpact.WatchSegment(&opts, chanEvents)
		if err != nil {
			return
		}
		for {
			select {
			case <-sub.Err():
				return
			case ev := <-chanEvents:
				mgr.Lock.Lock()
				mgr.Events[chainID.String()][ev.Raw.TxHash.String()], _ = mgr.sign(chainID, ev.Raw.Address, segmentFuncSig[:], ev.Raw.Data)
				mgr.Lock.Unlock()
			}
		}
	}()
	go func() {
		opts := bind.WatchOpts{Start: nil, Context: context.Background()}
		chanEvents := make(chan *functioncall.GpactRoot)
		sub, err := igpact.WatchRoot(&opts, chanEvents)
		if err != nil {
			return
		}
		for {
			select {
			case <-sub.Err():
				return
			case ev := <-chanEvents:
				mgr.Lock.Lock()
				mgr.Events[chainID.String()][ev.Raw.TxHash.String()], _ = mgr.sign(chainID, ev.Raw.Address, rootFuncSig[:], ev.Raw.Data)
				mgr.Lock.Unlock()
			}
		}
	}()
}

func (mgr *MockMsgStore) GetSignature(ctx context.Context, chainID *big.Int, transactionHash string) ([]byte, error) {
	time.Sleep(100 * time.Millisecond)
	events, ok := mgr.Events[chainID.String()]
	if !ok {
		return nil, fmt.Errorf("chain id does not exist")
	}
	sig, ok := events[transactionHash]
	if !ok {
		return nil, fmt.Errorf("transaction hash does not exist")
	}
	return sig, nil
}

func (mgr *MockMsgStore) sign(eventChain *big.Int, eventAddr common.Address, eventFuncSig []byte, eventData []byte) ([]byte, error) {
	toSign := make([]byte, 32)
	eventChain.FillBytes(toSign)
	toSign = append(toSign, eventAddr.Bytes()...)
	toSign = append(toSign, eventFuncSig...)
	toSign = append(toSign, eventData...)
	sig, err := crypto.Secp256k1Sign(mgr.Key, toSign)
	if err != nil {
		return nil, err
	}
	sig[len(sig)-1] += 27
	sig = append([]byte{0, 0, 0, 1}, append(mgr.Addr.Bytes(), sig...)...)
	return sig, nil
}
