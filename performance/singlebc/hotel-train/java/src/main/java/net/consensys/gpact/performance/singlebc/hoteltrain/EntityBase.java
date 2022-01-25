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
package net.consensys.gpact.performance.singlebc.hoteltrain;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.*;
import net.consensys.gpact.soliditywrappers.performance.singlebc.hoteltrain.ERC20PresetFixedSupply;
import net.consensys.gpact.soliditywrappers.performance.singlebc.hoteltrain.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class EntityBase extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(EntityBase.class);

  // Total number of tokens issued for booking.
  public static final BigInteger TOKEN_SUPPLY = BigInteger.valueOf(1000);

  ERC20PresetFixedSupply erc20;
  Hotel hotelContract;
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

  public void deployContracts() throws Exception {
    LOG.info(" Deploy ERC20 contract for {}", this.entity);
    LOG.info(" Setting total supply as {} tokens", TOKEN_SUPPLY);
    String name = "ABC";
    String symbol = "ABC";
    String owner = this.credentials.getAddress();
    this.erc20 =
        ERC20PresetFixedSupply.deploy(
                this.web3j, this.tm, this.gasProvider, name, symbol, TOKEN_SUPPLY, owner)
            .send();

    LOG.info(" Deploy {} contract", this.entity);
    this.hotelContract =
        Hotel.deploy(this.web3j, this.tm, this.gasProvider, this.erc20.getContractAddress()).send();
  }

  public String getHotelContractAddress() {
    return this.hotelContract.getContractAddress();
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

  public void addTravelAgency(String contractAddress, String tokenHoldingAccount) throws Exception {
    this.hotelContract.addApprovedTravelAgency(contractAddress, tokenHoldingAccount).send();
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

  public void showBookings(int date) throws Exception {
    LOG.info(" Hotel Bookings for date: {}", date);
    List<String> hotelBookings = this.hotelContract.getBookings(BigInteger.valueOf(date)).send();
    for (String booking : hotelBookings) {
      LOG.info("  Room booked for {}", booking);
    }

    LOG.info(" Train Bookings for date: {}", date);
    List<String> trainBookings = this.hotelContract.getBookings(BigInteger.valueOf(date)).send();
    for (String booking : trainBookings) {
      LOG.info("  Seat booked for {}", booking);
    }
  }

  public void separatedBook(int date, BigInteger uniqueId, int maxAmountToPay, String spender)
      throws Exception {
    try {
      TransactionReceipt txr =
          this.hotelContract
              .separatedBookRoom(
                  BigInteger.valueOf(date), uniqueId, BigInteger.valueOf(maxAmountToPay), spender)
              .send();
      StatsHolder.logGas("Separated Hotel Book", txr.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
  }
}
