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
package net.consensys.gpact.examples.sfc.erc20bridge;

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
public abstract class AbstractERC20Bridge extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractERC20Bridge.class);

  public final String entity;

  protected String erc20BridgeAddress;

  public AbstractERC20Bridge(
      final String entity, final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
    this.entity = entity;
  }


  public void showErc20Balances(Erc20User[] users) throws Exception {
    LOG.info("{} ERC20 Balances", this.entity);
    LOG.info(" Total Supply: {}", totalSupply());
    LOG.info(" ERC20 Bridge Account {}: balance: {}", this.erc20BridgeAddress, getBalance(this.erc20BridgeAddress));
    for (Erc20User user : users) {
      LOG.info(" Account {}:{} balance: {}", user.getName(), user.getAddress(), getBalance(user.getAddress()));
    }
  }

  public void checkBalance(Erc20User user, int expectedBalance) throws Exception {
    BigInteger expectedBal = BigInteger.valueOf(expectedBalance);
    BigInteger actualBal = getBalance(user.getAddress());
    if (expectedBal.compareTo(actualBal) != 0) {
      throw new Exception(this.entity + ", " + user.getName() + ": actual balance " + actualBal + " does not match expected balance " + expectedBal);
    }
  }



  public abstract void deployContracts(String cbcAddress) throws Exception;

  public abstract void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress)
      throws Exception;

  public abstract void addBlockchain(
      BlockchainId remoteBcId, String remoteERC20BridgeContractAddress) throws Exception;

  public abstract String getErc20ContractAddress();

  public abstract String getErc20BridgeContractAddress();

  public abstract void giveTokensToERC20Bridge(final int number) throws Exception;

  public abstract void giveTokens(final Erc20User user, final int number) throws Exception;

  protected abstract BigInteger totalSupply() throws Exception;

  protected abstract BigInteger getBalance(String account) throws Exception;

  public abstract void showErc20Allowance(String owner, String spender) throws Exception;
}
