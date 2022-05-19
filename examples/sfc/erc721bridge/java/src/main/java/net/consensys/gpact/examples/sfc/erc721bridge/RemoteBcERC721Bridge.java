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
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.crypto.Hash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.crypto.Credentials;

/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge contract on a certain
 * blockchain.
 */
public class RemoteBcERC721Bridge extends AbstractERC721Bridge {
  private static final Logger LOG = LogManager.getLogger(RemoteBcERC721Bridge.class);

  public static final byte[] MINTER_ROLE =
      Hash.keccak256(Bytes.wrap("MINTER_ROLE".getBytes())).toArray();

  ERC721AutoURIRemoteBlockchain erc721;

  public RemoteBcERC721Bridge(
      final String entity, final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(entity, credentials, bcConfig);
  }

  public void deployContracts(String cbcAddress) throws Exception {
    String name = this.entity;
    String symbol = this.entity;
    this.erc721 =
        ERC721AutoURIRemoteBlockchain.deploy(
                this.web3j, this.tm, this.gasProvider, name, symbol, TOKEN_URI)
            .send();
    this.erc721Address = this.erc721.getContractAddress();
    LOG.info(" Deploy {} ERC721: {}", this.entity, this.erc721Address);

    this.erc721Bridge =
        SfcErc721Bridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
    this.erc721BridgeAddress = this.erc721Bridge.getContractAddress();
    LOG.info(" Deploy ERC721 Bridge: {}", this.erc721BridgeAddress);

    LOG.info(" Grant ERC 721 Bridge contract MINTER role");
    this.erc721.grantRole(MINTER_ROLE, this.erc721BridgeAddress).send();
  }

  public void giveTokensToERC20Bridge(final int number) throws Exception {
    LOG.info("{} Transferring {} tokens to bridge", this.entity, number);
    //        this.erc721.transfer(this.erc721BridgeAddress, BigInteger.valueOf(number)).send();
    throw new Error("TODO");
  }

  public void giveTokens(final Erc721User user, final int number) throws Exception {
    throw new RuntimeException("New tokens can not be created on the remote blockchain");
  }

  public void showErc721Balances(Erc721User[] users) throws Exception {
    LOG.info("{} ERC 721 Balances", this.entity);
    BigInteger totalSupply = this.erc721.totalSupply().send();
    LOG.info(" Total Supply: {}", totalSupply);
    BigInteger bal = this.erc721.balanceOf(this.erc721BridgeAddress).send();
    LOG.info(" ERC 721 Bridge Account {}: balance: {}", this.erc721BridgeAddress, bal);
    for (Erc721User user : users) {
      bal = this.erc721.balanceOf(user.getAddress()).send();
      LOG.info(" Account {}:{} balance: {}", user.getName(), user.getAddress(), bal);
    }
  }

  public void showErc721Allowance(String owner, String spender) throws Exception {
    //        BigInteger allowance = this.erc20.allowance(owner, spender).send();
    //        LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender,
    // allowance);
  }

  public BigInteger getTokenId(Erc721User user, int index) throws Exception {
    return this.erc721.tokenOfOwnerByIndex(user.getAddress(), BigInteger.valueOf(index)).send();
  }
}
