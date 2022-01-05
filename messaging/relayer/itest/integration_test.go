package itest

/*
 * Copyright 2021 ConsenSys Software Inc
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
	"encoding/hex"
	"encoding/json"
	"errors"
	"fmt"
	"math/big"
	"testing"
	"time"

	"github.com/consensys/gpact/messaging/relayer/internal/adminserver"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/application"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/functioncall"
	"github.com/consensys/gpact/messaging/relayer/internal/contracts/messaging"
	"github.com/consensys/gpact/messaging/relayer/internal/crypto"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/crypto/secp256k1"
	"github.com/ethereum/go-ethereum/ethclient"
	"github.com/stretchr/testify/assert"
)

var userA *bind.TransactOpts
var userB *bind.TransactOpts
var erc20A common.Address
var erc20B common.Address
var bridgeA common.Address

// TestERC20Setup setups a crosschain erc20 transfer.
func TestERC20Setup(t *testing.T) {
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
	_, admin := createUser()            // admin to create all contracts
	_, userA = createUser()             // userA on chainA
	_, userB = createUser()             // userB on chainB
	relayerKey, relayer := createUser() // relayer key
	dispatcherKey, _ := createUser()    // dispatcher key

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

	// Transfer userA & bridgeB some balance
	t.Log("Transfer tokens to userA...")
	tx, err = ierc20A.Transfer(admin, userA.From, big.NewInt(100))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainA, tx)
	t.Log("Done")
	t.Log("Transfer tokens to bridgeB...")
	tx, err = ierc20B.Transfer(admin, bridgeAddrB, big.NewInt(1000))
	if err != nil {
		panic(err)
	}
	waitForReceipt(chainB, tx)
	t.Log("Done")

	// Setup relayers
	t.Log("Setup relayers...")
	assert.Empty(t, setupObserver("127.0.0.1:9525", 31, "ws://bc31node1:8546", sfcAddrA.Hex()))
	assert.Empty(t, setupRelayer("127.0.0.1:9625", relayerKey))
	assert.Empty(t, setupDispatcher("127.0.0.1:9725", 32, dispatcherKey, 0, "ws://bc32node1:8546", verifierAddrB.Hex()))
	t.Log("Setup done")
}

// TestERC20Transfer tests a crosschain erc20 transfer.
func TestERC20Transfer(t *testing.T) {
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
		t.Fatal(err)
	}
	waitForReceipt(chainA, tx)
	userA.Nonce.Add(userA.Nonce, big.NewInt(1))
	tx, err = ibridgeA.TransferToOtherBlockchain(userA, big.NewInt(32), erc20A, userB.From, big.NewInt(10))
	if err != nil {
		t.Fatal(err)
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
	auth.GasLimit = uint64(3000000) // in units
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
func setupObserver(url string, bcID byte, chain string, sfcAddr string) error {
	data, err := json.Marshal(struct {
		BcID    string `json:"bc_id"`
		Chain   string `json:"chain"`
		SFCAddr string `json:"sfc_addr"`
	}{
		BcID:    fmt.Sprintf("%v", bcID),
		Chain:   chain,
		SFCAddr: sfcAddr,
	})
	if err != nil {
		return err
	}
	resp, err := adminserver.Request(url, 1, data)
	if err != nil {
		return err
	}
	if resp[0] != 0 {
		return fmt.Errorf("Request failed.")
	}
	return nil
}

// setupRelayer sets up relayer.
func setupRelayer(url string, key []byte) error {
	resp, err := adminserver.Request(url, 1, key)
	if err != nil {
		return err
	}
	if resp[0] != 0 {
		return fmt.Errorf("Request failed.")
	}
	return nil
}

// setupDispatcher sets up dispatcher.
func setupDispatcher(url string, bcID byte, key []byte, nonce int, chain string, esAddr string) error {
	data, err := json.Marshal(struct {
		BcID   byte   `json:"bc_id"`
		Key    string `json:"key"`
		Nonce  int    `json:"nonce"`
		Chain  string `json:"chain"`
		EsAddr string `json:"es_addr"`
	}{
		BcID:   bcID,
		Key:    hex.EncodeToString(key),
		Nonce:  nonce,
		Chain:  chain,
		EsAddr: esAddr,
	})
	if err != nil {
		return err
	}
	resp, err := adminserver.Request(url, 1, data)
	if err != nil {
		return err
	}
	if resp[0] != 0 {
		return fmt.Errorf("Request failed.")
	}
	return nil
}
