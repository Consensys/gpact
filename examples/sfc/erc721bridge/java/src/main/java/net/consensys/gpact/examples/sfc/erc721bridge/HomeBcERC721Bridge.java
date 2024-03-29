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
import java.util.List;
import net.consensys.gpact.common.BlockchainConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge contract on a certain
 * blockchain.
 */
public class HomeBcERC721Bridge extends AbstractERC721Bridge {
  private static final Logger LOG = LogManager.getLogger(HomeBcERC721Bridge.class);

  ERC721PresetMinterPauserAutoId erc721;

  public HomeBcERC721Bridge(
      final String entity, final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(entity, credentials, bcConfig);
  }

  public void deployContracts(String cbcAddress) throws Exception {
    String name = this.entity;
    String symbol = this.entity;
    this.erc721 =
        ERC721PresetMinterPauserAutoId.deploy(
                this.web3j, this.tm, this.gasProvider, name, symbol, TOKEN_URI)
            .send();
    this.erc721Address = this.erc721.getContractAddress();
    LOG.info(" Deploy {} ERC721: {}", this.entity, this.erc721Address);

    this.erc721Bridge =
        SfcErc721Bridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
    this.erc721BridgeAddress = this.erc721Bridge.getContractAddress();
    LOG.info(" Deploy ERC721 Bridge: {}", this.erc721BridgeAddress);
  }

  public void giveTokensToERC20Bridge(final int number) throws Exception {
    LOG.info("{} Transferring {} tokens to bridge", this.entity, number);
    //        this.erc721.transfer(this.erc721BridgeAddress, BigInteger.valueOf(number)).send();
    throw new Error("TODO");
  }

  public void giveTokens(final Erc721User user, final int number) throws Exception {
    LOG.info("{} Transferring {} tokens to {}", this.entity, number, user.getName());
    for (int i = 0; i < number; i++) {
      TransactionReceipt txr = this.erc721.mint(user.getAddress()).send();
      List<ERC721PresetMinterPauserAutoId.TransferEventResponse> transfers =
          this.erc721.getTransferEvents(txr);
      ERC721PresetMinterPauserAutoId.TransferEventResponse transfer = transfers.get(0);
      LOG.info(
          " Mint/Transfer: From: {}, To: {}, TokenId: {}",
          transfer.from,
          transfer.to,
          transfer.tokenId);
    }
  }

  protected BigInteger totalSupply() throws Exception {
    return this.erc721.totalSupply().send();
  }

  protected BigInteger getBalance(String account) throws Exception {
    return this.erc721.balanceOf(account).send();
  }

  public void showErc721Allowance(String owner, String spender) throws Exception {
    //        BigInteger allowance = this.erc721.allowance(owner, spender).send();
    //        LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender,
    // allowance);
  }

  public BigInteger getTokenId(Erc721User user, int index) throws Exception {
    return this.erc721.tokenOfOwnerByIndex(user.getAddress(), BigInteger.valueOf(index)).send();
  }
}
