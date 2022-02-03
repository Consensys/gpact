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
package net.consensys.gpact.examples.sfc.erc721bridge;

import java.math.BigInteger;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.SfcExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/** Sample code showing how to use the Simple Function Call protocol ERC 721 bridge. */
public class Erc721TokenBridgeExample {
  static final Logger LOG = LogManager.getLogger(Erc721TokenBridgeExample.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: SFC: ERC 721 Token Bridge");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    SfcExampleSystemManager exampleManager = new SfcExampleSystemManager(args[0]);
    exampleManager.sfcStandardExampleConfig(2);

    BlockchainInfo root = exampleManager.getRootBcInfo();
    BlockchainInfo bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    Credentials erc20OwnerCreds = CredentialsCreator.createCredentials();
    AbstractERC721Bridge chainA =
        new HomeBcERC721Bridge(
            "ChainA", erc20OwnerCreds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    AbstractERC721Bridge chainB =
        new RemoteBcERC721Bridge(
            "ChainB", erc20OwnerCreds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    // Deploy application contracts.
    BlockchainId chainABcId = chainA.getBlockchainId();
    chainA.deployContracts(crossControlManagerGroup.getCbcAddress(chainABcId));
    BlockchainId chainBBcId = chainB.getBlockchainId();
    chainB.deployContracts(crossControlManagerGroup.getCbcAddress(chainBBcId));

    // Connect the ERC 721 Bridges
    chainA.addBlockchain(chainBBcId, chainB.getErc721BridgeContractAddress());
    chainB.addBlockchain(chainABcId, chainA.getErc721BridgeContractAddress());

    // Register the ERC 721 contracts with each blockchain.
    chainA.addFirstRemoteERC721(chainBBcId, chainB.getErc721ContractAddress(), true);
    chainB.addFirstRemoteERC721(chainABcId, chainA.getErc721ContractAddress(), false);

    // Create some users and give them some tokens.
    Erc721User user1 =
        new Erc721User(
            "User1",
            root.bcId,
            chainA.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress(),
            bc2.bcId,
            chainB.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress());
    Erc721User user2 =
        new Erc721User(
            "User2",
            root.bcId,
            chainA.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress(),
            bc2.bcId,
            chainB.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress());
    Erc721User user3 =
        new Erc721User(
            "User3",
            root.bcId,
            chainA.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress(),
            bc2.bcId,
            chainB.getErc721ContractAddress(),
            chainA.getErc721BridgeContractAddress());

    user1.createCbcManager(
        root,
        crossControlManagerGroup.getCbcAddress(chainABcId),
        crossControlManagerGroup.getMessageVerification(chainABcId),
        bc2,
        crossControlManagerGroup.getCbcAddress(chainBBcId),
        crossControlManagerGroup.getMessageVerification(chainBBcId));
    user2.createCbcManager(
        root,
        crossControlManagerGroup.getCbcAddress(chainABcId),
        crossControlManagerGroup.getMessageVerification(chainABcId),
        bc2,
        crossControlManagerGroup.getCbcAddress(chainBBcId),
        crossControlManagerGroup.getMessageVerification(chainBBcId));
    user3.createCbcManager(
        root,
        crossControlManagerGroup.getCbcAddress(chainABcId),
        crossControlManagerGroup.getMessageVerification(chainABcId),
        bc2,
        crossControlManagerGroup.getCbcAddress(chainBBcId),
        crossControlManagerGroup.getMessageVerification(chainBBcId));

    // Give some ERC 721 tokens to the users
    chainA.giveTokens(user1, 2);
    chainA.giveTokens(user2, 3);
    chainA.giveTokens(user3, 4);

    Erc721User[] users = new Erc721User[] {user1, user2, user3};

    chainA.showErc721Balances(users);
    chainB.showErc721Balances(users);

    // Transfer token to self from home blockchain to remote blockchain.
    BigInteger tokenId = chainA.getTokenId(user1, 1);
    user1.transfer(true, tokenId);

    chainA.showErc721Balances(users);
    chainB.showErc721Balances(users);

    // Transfer token to someone else from home blockchain to remote blockchain.
    tokenId = chainA.getTokenId(user3, 0);
    user3.transfer(true, user1.getAddress(), tokenId);

    chainA.showErc721Balances(users);
    chainB.showErc721Balances(users);

    // Transfer token to self from remote blockchain to home blockchain.
    tokenId = chainB.getTokenId(user1, 1);
    user1.transfer(false, tokenId);

    chainA.showErc721Balances(users);
    chainB.showErc721Balances(users);

    // Transfer token to someone else from remote blockchain to home blockchain.
    tokenId = chainB.getTokenId(user1, 0);
    user1.transfer(false, user2.getAddress(), tokenId);

    chainA.showErc721Balances(users);
    chainB.showErc721Balances(users);

    chainA.shutdown();
    chainB.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
