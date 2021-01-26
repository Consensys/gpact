/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.examples.singlebc.trade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.cbc.AbstractBlockchain;
import tech.pegasys.ltacfc.common.StatsHolder;
import tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers.Balances;
import tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers.BusLogic;
import tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers.PriceOracle;
import tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers.Stock;
import tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers.TradeWallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class Bc1TradeWallet extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc1TradeWallet.class);

  TradeWallet tradeWalletContract;
  BusLogic busLogicContract;
  Balances balancesContract;
  Stock stockContract;
  PriceOracle priceOracleContract;

  public Bc1TradeWallet(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts() throws Exception {
    this.balancesContract = Balances.deploy(this.web3j, this.tm, this.gasProvider).send();
    this.stockContract = Stock.deploy(this.web3j, this.tm, this.gasProvider).send();
    this.priceOracleContract = PriceOracle.deploy(this.web3j, this.tm, this.gasProvider).send();
    this.busLogicContract = BusLogic.deploy(this.web3j, this.tm, this.gasProvider,
        this.balancesContract.getContractAddress(),
        this.priceOracleContract.getContractAddress(),
        this.stockContract.getContractAddress()).send();
    this.tradeWalletContract =
        TradeWallet.deploy(this.web3j, this.tm, this.gasProvider, this.busLogicContract.getContractAddress()).send();

    LOG.info("Balances contract deployed to {} on blockchain 0x{}",
        this.balancesContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Stock contract deployed to {} on blockchain 0x{}",
        this.stockContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Price Oracle contract deployed to {}, on blockchain 0x{}",
        this.priceOracleContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Business Logic contract deployed to {} on blockchain 0x{}",
        this.busLogicContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Trade Wallet contract deployed to {} on blockchain 0x{}",
        this.tradeWalletContract.getContractAddress(), this.blockchainId.toString(16));
  }


  public TransactionReceipt executeTrade(String seller, BigInteger quantity) throws Exception {
    TransactionReceipt txR = this.tradeWalletContract.executeTrade(seller, quantity).send();
    BigInteger gasCost = txR.getGasUsed();
    StatsHolder.logGas("Execute Trade", gasCost);
    return txR;
  }


  public void setBalance(String account, BigInteger newBalance) throws Exception {
    this.balancesContract.setBalance(account, newBalance).send();
  }

  public BigInteger getBalance(String account) throws Exception {
    return this.balancesContract.getBalance(account).send();
  }

  public void setPrice(BigInteger newPrice) throws Exception {
    this.priceOracleContract.setPrice(newPrice).send();
  }

  public BigInteger getPrice() throws Exception {
    return this.priceOracleContract.getPrice().send();
  }

  public void setStock(String account, BigInteger newAmount) throws Exception {
    this.stockContract.setStock(account, newAmount).send();
  }

  public BigInteger getStock(String account) throws Exception {
    return this.stockContract.getStock(account).send();
  }

  public void showValues() throws Exception {
    LOG.info("Price Oracle: Price: {}", this.priceOracleContract.getPrice().send());
  }


  public void showAllTrades() throws Exception {
    LOG.info("Trades:");
    BigInteger numTradesBig = this.tradeWalletContract.getNumTrades().send();
    int numTrades = (int) numTradesBig.longValue();
    if (numTrades == 0) {
      LOG.info(" None");
    }

    for (int i = 0; i < numTrades; i++) {
      BigInteger trade = this.tradeWalletContract.getTrade(BigInteger.valueOf(i)).send();
      LOG.info(" 0x{}", trade.toString(16));
    }
  }


  public void showEvents(TransactionReceipt txR) {
    LOG.info("Business Logic: Stock Shipment Events");
    List<BusLogic.StockShipmentEventResponse> events = this.busLogicContract.getStockShipmentEvents(txR);
    for (BusLogic.StockShipmentEventResponse e: events) {
      LOG.info(" Seller: {}", e._seller);
      LOG.info(" Buyer: {}", e._buyer);
      LOG.info(" Quantity: {}", e._quantity);
      LOG.info(" Price: {}", e._price);
    }
  }

}
