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
package net.consensys.gpact.examples.gpact.hoteltrain;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class EntityBase extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(EntityBase.class);

  // Total number of tokens issued for booking.
  public static final BigInteger TOKEN_SUPPLY = BigInteger.valueOf(1000);

  private LockableERC20PresetFixedSupply erc20;
  public String entity;

  public EntityBase(
      final String entity,
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
    this.entity = entity;
  }

  protected String deployERC20Contract(String cbcAddress) throws Exception {
    LOG.info(" Deploy ERC20 contract for {}", this.entity);
    LOG.info(" Setting total supply as {} tokens", TOKEN_SUPPLY);
    String name = "ABC";
    String symbol = "ABC";
    String owner = this.credentials.getAddress();
    this.erc20 =
        LockableERC20PresetFixedSupply.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                name,
                symbol,
                cbcAddress,
                TOKEN_SUPPLY,
                owner)
            .send();

    return this.erc20.getContractAddress();
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }

  public void buyTokens(final String account, final int number) throws Exception {
    LOG.info("Buy some tokens to be used for bookings: Account: {}, Number: {}", account, number);
    String myAccount = this.credentials.getAddress();

    this.erc20.transfer(account, BigInteger.valueOf(number)).send();
    BigInteger balance1 = this.erc20.balanceOf(account).send();
    LOG.info(" New balance of account {}: {}", account, balance1);
  }

  public void showErc20Balances(String[] accounts) throws Exception {
    LOG.info(" {} ERC 20 Balances", this.entity);
    BigInteger myBal = this.erc20.balanceOf(this.credentials.getAddress()).send();
    LOG.info(" Owner {} balance: {}", this.credentials.getAddress(), myBal);

    for (String acc : accounts) {
      BigInteger bal = this.erc20.balanceOf(acc).send();
      LOG.info(" Account {} balance: {}", acc, bal);
    }
  }

  public void showErc20Allowance(String owner, String spender) throws Exception {
    BigInteger allowance = this.erc20.allowance(owner, spender).send();
    LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender, allowance);
  }
}
