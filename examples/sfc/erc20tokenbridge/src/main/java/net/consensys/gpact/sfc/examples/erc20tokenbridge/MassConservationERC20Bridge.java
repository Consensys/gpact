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
package net.consensys.gpact.sfc.examples.erc20tokenbridge;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers.SfcErc20Bridge;
import net.consensys.gpact.openzeppelin.soliditywrappers.ERC20PresetFixedSupply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge contract on a certain
 * blockchain.
 */
public class MassConservationERC20Bridge extends AbstractERC20Bridge {
  private static final Logger LOG = LogManager.getLogger(MassConservationERC20Bridge.class);

  ERC20PresetFixedSupply erc20;
  SfcErc20Bridge erc20Bridge;

  public MassConservationERC20Bridge(
      final String entity,
      BigInteger tokenSupply,
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(entity, tokenSupply, credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(String cbcAddress) throws Exception {
    String name = this.entity;
    String symbol = this.entity;
    String owner = this.credentials.getAddress();
    this.erc20 =
        ERC20PresetFixedSupply.deploy(
                this.web3j, this.tm, this.gasProvider, name, symbol, this.tokenSupply, owner)
            .send();
    LOG.info(
        " Deploy {} ERC20: {}. Token Supply: {}",
        this.entity,
        this.erc20.getContractAddress(),
        this.tokenSupply);

    this.erc20Bridge =
        SfcErc20Bridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
    LOG.info(" Deploy ERC20 Mass Conservation Bridge: {}", this.erc20Bridge.getContractAddress());
    this.erc20BridgeAddress = this.erc20Bridge.getContractAddress();
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }

  public String getErc20BridgeContractAddress() {
    return this.erc20Bridge.getContractAddress();
  }

  public void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress)
      throws Exception {
    LOG.info(
        " Setting Remote ERC20: Local BcId: {}, Remote BcId: {}, Local ERC20: {}, Remote ERC20: {}",
        this.blockchainId,
        remoteBcId,
        this.erc20.getContractAddress(),
        remoteERC20ContractAddress);
    this.erc20Bridge
        .addContractFirstMapping(
            this.erc20.getContractAddress(),
            remoteBcId.asBigInt(),
            remoteERC20ContractAddress,
            true)
        .send();
  }

  public void addBlockchain(BlockchainId remoteBcId, String remoteERC20BridgeContractAddress)
      throws Exception {
    LOG.info(
        " Setting Remote ERC20 Bridge: Remote BcId: {}, Remote ERC20Bridge: {}",
        remoteBcId,
        remoteERC20BridgeContractAddress);
    this.erc20Bridge
        .changeBlockchainMapping(remoteBcId.asBigInt(), remoteERC20BridgeContractAddress)
        .send();
  }

  public void giveTokensToERC20Bridge(final int number) throws Exception {
    LOG.info("{} Transferring {} tokens to bridge", this.entity, number);
    this.erc20.transfer(this.erc20BridgeAddress, BigInteger.valueOf(number)).send();
  }

  public void giveTokens(final Erc20User user, final int number) throws Exception {
    LOG.info("{} Transferring {} tokens to {}", this.entity, number, user.getName());
    this.erc20.transfer(user.getAddress(), BigInteger.valueOf(number)).send();
  }

  public void showErc20Balances(Erc20User[] users) throws Exception {
    LOG.info("{} ERC20 Balances", this.entity);
    BigInteger totalSupply = this.erc20.totalSupply().send();
    LOG.info(" Total Supply: {}", totalSupply);
    BigInteger bal = this.erc20.balanceOf(this.erc20BridgeAddress).send();
    LOG.info(" ERC20 Bridge Account {}: balance: {}", this.erc20BridgeAddress, bal);
    for (Erc20User user : users) {
      bal = this.erc20.balanceOf(user.getAddress()).send();
      LOG.info(" Account {}:{} balance: {}", user.getName(), user.getAddress(), bal);
    }
  }

  public void showErc20Allowance(String owner, String spender) throws Exception {
    BigInteger allowance = this.erc20.allowance(owner, spender).send();
    LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender, allowance);
  }
}
