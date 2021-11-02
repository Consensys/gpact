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
package net.consensys.gpact.examples.tokenbridge;

import net.consensys.gpact.cbc.CrossControlManagerGroup;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;


public class TokenBridge {
  static final Logger LOG = LogManager.getLogger(TokenBridge.class);

  public static final int NUM_TIMES_EXECUTE = 2;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Token Bridge");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    GpactExampleSystemManager exampleManager = new GpactExampleSystemManager(args[0]);
    exampleManager.gpactStandardExampleConfig(2);

    BlockchainInfo root = exampleManager.getRootBcInfo();
    BlockchainInfo bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup = exampleManager.getGpactCrossControlManagerGroup();

    final int CHAIN_A_TOKEN_SUPPLY = 1000;
    final int CHAIN_B_TOKEN_SUPPLY = 2000;

    // Set-up classes to manage blockchains.
    Credentials erc20OwnerCreds = CredentialsCreator.createCredentials();
    SourceAndDestinationBlockchain chainA = new SourceAndDestinationBlockchain(
            "ChainA", BigInteger.valueOf(CHAIN_A_TOKEN_SUPPLY),
            erc20OwnerCreds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    SourceAndDestinationBlockchain chainB = new SourceAndDestinationBlockchain(
            "ChainB", BigInteger.valueOf(CHAIN_B_TOKEN_SUPPLY),
            erc20OwnerCreds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    // Deploy application contracts.
    BlockchainId chainABcId = chainA.getBlockchainId();
    chainA.deployContract(crossControlManagerGroup.getCbcAddress(chainABcId));
    BlockchainId chainBBcId = chainB.getBlockchainId();
    chainB.deployContract(crossControlManagerGroup.getCbcAddress(chainBBcId));

    // Register the bridges with each other.
    chainA.addRemoteERC20Bridge(chainBBcId, chainB.getBridgeContractAddress());
    chainB.addRemoteERC20Bridge(chainABcId, chainA.getBridgeContractAddress());

    // Register the ERC20 contracts with each other.
    chainA.addFirstRemoteERC20(chainBBcId, chainB.getErc20ContractAddress());
    chainB.addFirstRemoteERC20(chainABcId, chainA.getErc20ContractAddress());

    // Register the other blockchain as a trusted root blockchain
    // If the call execution tree could possibly go: chain A, chain B, chain A, then
    // chain A should trust itself, as well as B.
    chainA.addTrustedRootBlockchain(chainBBcId);
    chainB.addTrustedRootBlockchain(chainABcId);

      // Create some users and give them some tokens.
    Erc20User user1 = new Erc20User(
            "User1",
            root.bcId, chainA.getBridgeContractAddress(),chainA.getErc20ContractAddress(),
            bc2.bcId, chainB.getBridgeContractAddress(), chainB.getErc20ContractAddress());
    Erc20User user2 = new Erc20User(
            "User2",
            root.bcId, chainA.getBridgeContractAddress(),chainA.getErc20ContractAddress(),
            bc2.bcId, chainB.getBridgeContractAddress(), chainB.getErc20ContractAddress());
    Erc20User user3 = new Erc20User(
            "User3",
            root.bcId, chainA.getBridgeContractAddress(),chainA.getErc20ContractAddress(),
            bc2.bcId, chainB.getBridgeContractAddress(), chainB.getErc20ContractAddress());

    user1.createCbcManager(
            root, crossControlManagerGroup.getInfrastructureAddresses(chainABcId), crossControlManagerGroup.getMessageVerification(chainABcId),
            bc2, crossControlManagerGroup.getInfrastructureAddresses(chainBBcId), crossControlManagerGroup.getMessageVerification(chainBBcId));
    user2.createCbcManager(
            root, crossControlManagerGroup.getInfrastructureAddresses(chainABcId), crossControlManagerGroup.getMessageVerification(chainABcId),
            bc2, crossControlManagerGroup.getInfrastructureAddresses(chainBBcId), crossControlManagerGroup.getMessageVerification(chainBBcId));
    user3.createCbcManager(
            root, crossControlManagerGroup.getInfrastructureAddresses(chainABcId), crossControlManagerGroup.getMessageVerification(chainABcId),
            bc2, crossControlManagerGroup.getInfrastructureAddresses(chainBBcId), crossControlManagerGroup.getMessageVerification(chainBBcId));


      // Give some balance to the users
    chainA.giveTokens(user1, 500);
    chainA.giveTokens(user2, 200);
    chainA.giveTokens(user3, 300);
    chainB.giveTokensToBridge(1000);

    Erc20User[] users = new Erc20User[]{user1, user2, user3};

    chainA.showErc20Balances(users);
    chainB.showErc20Balances(users);

    for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
      LOG.info("Execution: {}  *****************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      user1.transfer(true, numExecutions + 7);

      chainA.showErc20Balances(users);
      chainB.showErc20Balances(users);
    }

    chainA.shutdown();
    chainB.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
