/*
 * Copyright 2021 ConsenSys Software Inc.
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

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge contract on a certain
 * blockchain.
 */
public abstract class AbstractERC721Bridge extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractERC721Bridge.class);

  public static final String TOKEN_URI = "http://example.com/";

  public final String entity;

  protected String erc721Address;
  protected String erc721BridgeAddress;
  SfcErc721Bridge erc721Bridge;

  public AbstractERC721Bridge(
      final String entity, final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
    this.entity = entity;
  }

  public abstract void deployContracts(String cbcAddress) throws Exception;

  public String getErc721ContractAddress() {
    return this.erc721Address;
  }

  public String getErc721BridgeContractAddress() {
    return this.erc721BridgeAddress;
  }

  public void addFirstRemoteERC721(
      BlockchainId remoteBcId, String remoteERC721ContractAddress, boolean thisBcIsHome)
      throws Exception {
    LOG.info(
        " Setting Remote ERC 721: Local BcId: {}, Remote BcId: {}, Local ERC 721: {}, Remote ERC 721: {}, ThisBcIsHome: {}",
        this.blockchainId,
        remoteBcId,
        this.erc721Address,
        remoteERC721ContractAddress,
        thisBcIsHome);
    this.erc721Bridge
        .addContractFirstMapping(
            this.erc721Address, remoteBcId.asBigInt(), remoteERC721ContractAddress, thisBcIsHome)
        .send();
  }

  public void addRemoteERC721(BlockchainId remoteBcId, String remoteERC721ContractAddress)
      throws Exception {
    LOG.info(
        " Setting Remote ERC 721: Local BcId: {}, Remote BcId: {}, Local ERC 721: {}, Remote ERC 721: {}",
        this.blockchainId,
        remoteBcId,
        this.erc721Address,
        remoteERC721ContractAddress);
    this.erc721Bridge
        .changeContractMapping(
            this.erc721Address, remoteBcId.asBigInt(), remoteERC721ContractAddress)
        .send();
  }

  public void addBlockchain(BlockchainId remoteBcId, String remoteERC721BridgeContractAddress)
      throws Exception {
    LOG.info(
        " Setting Remote ERC 721 Bridge: Remote BcId: {}, Remote ERC 721 Bridge: {}",
        remoteBcId,
        remoteERC721BridgeContractAddress);
    this.erc721Bridge
        .changeBlockchainMapping(remoteBcId.asBigInt(), remoteERC721BridgeContractAddress)
        .send();
  }

  public void showErc721Balances(Erc721User[] users) throws Exception {
    LOG.info("{} ERC721 Balances", this.entity);
    LOG.info(" Total Supply: {}", totalSupply());
    LOG.info(
        " ERC721 Bridge Account {}: balance: {}",
        this.erc721BridgeAddress,
        getBalance(this.erc721BridgeAddress));
    for (Erc721User user : users) {
      LOG.info(
          " Account {}:{} balance: {}",
          user.getName(),
          user.getAddress(),
          getBalance(user.getAddress()));
    }
  }

  public void checkBalance(Erc721User user, int expectedBalance) throws Exception {
    final int numRetires = 10;

    BigInteger expectedBal = BigInteger.valueOf(expectedBalance);
    BigInteger actualBal = null;

    // Retry some number of retries. The issue could be that the transaction
    // that will update the balance hasn't been mined yet.
    for (int i = 0; i < numRetires; i++) {
      actualBal = getBalance(user.getAddress());
      if (expectedBal.compareTo(actualBal) == 0) {
        return;
      }
      pauseWhileTransactionMined();
    }
    throw new Exception(
        this.entity
            + ", "
            + user.getName()
            + ": actual balance "
            + actualBal
            + " does not match expected balance "
            + expectedBal);
  }

  public abstract void giveTokens(final Erc721User user, final int number) throws Exception;

  public abstract void showErc721Allowance(String owner, String spender) throws Exception;

  protected abstract BigInteger totalSupply() throws Exception;

  protected abstract BigInteger getBalance(String account) throws Exception;

  public abstract BigInteger getTokenId(Erc721User user, int index) throws Exception;
}
