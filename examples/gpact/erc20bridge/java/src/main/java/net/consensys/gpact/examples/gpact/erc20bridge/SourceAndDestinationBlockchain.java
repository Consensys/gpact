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
package net.consensys.gpact.examples.gpact.erc20bridge;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.soliditywrappers.examples.gpact.erc20bridge.GpactERC20Bridge;
import net.consensys.gpact.soliditywrappers.examples.gpact.erc20bridge.LockableERC20PresetFixedSupply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;

public class SourceAndDestinationBlockchain extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(SourceAndDestinationBlockchain.class);

  // Total number of tokens issued for booking.
  public final BigInteger tokenSupply;
  LockableERC20PresetFixedSupply erc20;
  GpactERC20Bridge bridge;
  public final String entity;

  public SourceAndDestinationBlockchain(
      final String entity,
      BigInteger tokenSupply,
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
    this.entity = entity;
    this.tokenSupply = tokenSupply;
  }

  public void deployContract(String cbcAddress) throws Exception {
    String name = this.entity;
    String symbol = this.entity;
    String owner = this.credentials.getAddress();
    this.erc20 =
        LockableERC20PresetFixedSupply.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                name,
                symbol,
                cbcAddress,
                this.tokenSupply,
                owner)
            .send();
    LOG.info(
        " Deployed {} ERC20: {}. Token Supply: {}",
        this.entity,
        this.erc20.getContractAddress(),
        this.tokenSupply);

    this.bridge = GpactERC20Bridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
    LOG.info(" Deployed ERC20 Bridge: {}", this.bridge.getContractAddress());
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }

  public String getBridgeContractAddress() {
    return this.bridge.getContractAddress();
  }

  public void giveTokens(final Erc20User user, final int number) throws Exception {
    this.erc20.transfer(user.getAddress(), BigInteger.valueOf(number)).send();
  }

  public void giveTokensToBridge(final int number) throws Exception {
    this.erc20.transfer(this.bridge.getContractAddress(), BigInteger.valueOf(number)).send();
  }

  public void addRemoteERC20Bridge(BlockchainId remoteBcId, String remoteERC20BridgeContractAddress)
      throws Exception {
    LOG.info(
        "Add bridge: My BcId: {}, My ERC 20 Bridge Contract: {}, Remote BcId: {}, Remote ERC 20 Bridge Contract: {}",
        this.blockchainId,
        this.bridge.getContractAddress(),
        remoteBcId,
        remoteERC20BridgeContractAddress);
    try {
      this.bridge
          .addRemoteERC20Bridge(remoteBcId.asBigInt(), remoteERC20BridgeContractAddress)
          .send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
  }

  public void addFirstRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress)
      throws Exception {
    LOG.info(
        "Add remote ERC 20: My BcId: {}, My ERC 20 Contract: {}, Remote BcId: {}, Remote ERC 20 Contract: {}",
        this.blockchainId,
        this.erc20.getContractAddress(),
        remoteBcId,
        remoteERC20ContractAddress);
    try {
      this.bridge
          .addContractFirstMapping(
              this.erc20.getContractAddress(),
              remoteBcId.asBigInt(),
              remoteERC20ContractAddress,
              true)
          .send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
  }

  public void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress)
      throws Exception {
    LOG.info(
        "Add remote ERC 20: My BcId: {}, My ERC 20 Contract: {}, Remote BcId: {}, Remote ERC 20 Contract: {}",
        this.blockchainId,
        this.erc20.getContractAddress(),
        remoteBcId,
        remoteERC20ContractAddress);
    try {
      this.bridge
          .changeContractMapping(
              this.erc20.getContractAddress(), remoteBcId.asBigInt(), remoteERC20ContractAddress)
          .send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
  }

  public void addTrustedRootBlockchain(BlockchainId remoteBcId) throws Exception {
    LOG.info(
        "Add trusted root blockchain: My BcId: {}, My ERC 20 Bridge Contract: {}, Remote BcId: {}",
        this.blockchainId,
        this.erc20.getContractAddress(),
        remoteBcId);
    try {
      this.bridge.addApprovedRootBcId(remoteBcId.asBigInt()).send();
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
  }

  public void showErc20Balances(Erc20User[] users) throws Exception {
    LOG.info(" {} ERC 20 Balances", this.entity);
    String bridgeAccount = this.bridge.getContractAddress();
    BigInteger bal = this.erc20.balanceOf(bridgeAccount).send();
    LOG.info(" Bridge {}: balance: {}", bridgeAccount, bal);
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
