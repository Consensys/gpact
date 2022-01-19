package main

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
	"encoding/hex"
	"fmt"
	"math/big"
	"os"
	"strconv"

	dispatcherapi "github.com/consensys/gpact/messaging/relayer/internal/msgdispatcher/eth/api"
	observerapi "github.com/consensys/gpact/messaging/relayer/internal/msgobserver/eth/api"
	relayerapi "github.com/consensys/gpact/messaging/relayer/internal/msgrelayer/eth/api"
	"github.com/ethereum/go-ethereum/common"
	"github.com/urfave/cli/v2"
)

func main() {
	app := &cli.App{
		Name:        "admin",
		HelpName:    "admin",
		Version:     "0.0.1",
		Usage:       "Relayer admin cli",
		Description: "This is a simple relayer admin cli.",
		Commands: []*cli.Command{
			{
				Name:      "observer",
				Usage:     "Send commands to observer",
				ArgsUsage: " ",
				Subcommands: []*cli.Command{
					{
						Name:      "start",
						Usage:     "Start observing contract",
						ArgsUsage: "[url chainID chainAP contractAddr]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							chainAP := c.Args().Get(2)
							contractAddr := c.Args().Get(3)
							success, err := observerapi.RequestStartObserve(url, big.NewInt(int64(chainID)), chainAP, common.HexToAddress(contractAddr))
							if err != nil {
								return err
							}
							if success {
								fmt.Println("Success.")
							} else {
								fmt.Println("Failed.")
							}
							return nil
						},
					},
					{
						Name:      "stop",
						Usage:     "Stop observing contract",
						ArgsUsage: "[url]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							success, err := observerapi.RequestStopObserve(url)
							if err != nil {
								return err
							}
							if success {
								fmt.Println("Success.")
							} else {
								fmt.Println("Failed.")
							}
							return nil
						},
					},
				},
			},
			{
				Name:      "relayer",
				Usage:     "Send commands to relayer",
				ArgsUsage: " ",
				Subcommands: []*cli.Command{
					{
						Name:      "set-key",
						Usage:     "Set signer key",
						ArgsUsage: "[url chainID contractAddr key]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							contractAddr := c.Args().Get(2)
							key, err := hex.DecodeString(c.Args().Get(3))
							if err != nil {
								return err
							}
							success, err := relayerapi.RequestSetKey(url, big.NewInt(int64(chainID)), common.HexToAddress(contractAddr), 1, key)
							if err != nil {
								return err
							}
							if success {
								fmt.Println("Success.")
							} else {
								fmt.Println("Failed.")
							}
							return nil
						},
					},
					{
						Name:      "get-addr",
						Usage:     "Get signer address",
						ArgsUsage: "[url chainID contractAddr]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							contractAddr := c.Args().Get(2)
							_, addr, err := relayerapi.RequestGetAddr(url, big.NewInt(int64(chainID)), common.HexToAddress(contractAddr))
							if err != nil {
								return err
							}
							fmt.Println(addr.String())
							return nil
						},
					},
				},
			},
			{
				Name:      "dispatcher",
				Usage:     "Send commands to dispatcher",
				ArgsUsage: " ",
				Subcommands: []*cli.Command{
					{
						Name:      "set-trans",
						Usage:     "Set transaction opts",
						ArgsUsage: "[url chainID chainAP key]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							chainAP := c.Args().Get(2)
							key, err := hex.DecodeString(c.Args().Get(3))
							if err != nil {
								return err
							}
							success, err := dispatcherapi.RequestSetTransactionOpts(url, big.NewInt(int64(chainID)), chainAP, key)
							if err != nil {
								return err
							}
							if success {
								fmt.Println("Success.")
							} else {
								fmt.Println("Failed.")
							}
							return nil
						},
					},
					{
						Name:      "get-addr",
						Usage:     "Get dispatcher address",
						ArgsUsage: "[url chainID]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							addr, err := dispatcherapi.RequestGetAuthAddr(url, big.NewInt(int64(chainID)))
							fmt.Println(addr.String())
							return nil
						},
					},
					{
						Name:      "get-ap",
						Usage:     "Get dispatcher chain ap",
						ArgsUsage: "[url chainID]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							ap, err := dispatcherapi.RequestGetChainAP(url, big.NewInt(int64(chainID)))
							fmt.Println(ap)
							return nil
						},
					},
					{
						Name:      "set-ver",
						Usage:     "Set verifier",
						ArgsUsage: "[url chainID contractAddr esAddr]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							contractAddr := c.Args().Get(2)
							esAddr := c.Args().Get(3)
							success, err := dispatcherapi.RequestSetVerifierAddr(url, big.NewInt(int64(chainID)), common.HexToAddress(contractAddr), common.HexToAddress(esAddr))
							if err != nil {
								return err
							}
							if success {
								fmt.Println("Success.")
							} else {
								fmt.Println("Failed.")
							}
							return nil
						},
					},
					{
						Name:      "get-ver",
						Usage:     "Get verifier",
						ArgsUsage: "[url chainID contractAddr]",
						Action: func(c *cli.Context) error {
							url := c.Args().Get(0)
							chainID, err := strconv.ParseUint(c.Args().Get(1), 10, 64)
							if err != nil {
								return fmt.Errorf("error parsing chain id: %v", err.Error())
							}
							contractAddr := c.Args().Get(2)
							addr, err := dispatcherapi.RequestGetVerifierAddr(url, big.NewInt(int64(chainID)), common.HexToAddress(contractAddr))
							fmt.Println(addr.String())
							return nil
						},
					},
				},
			},
		},
	}
	err := app.Run(os.Args)
	if err != nil {
		panic(err)
	}
}
