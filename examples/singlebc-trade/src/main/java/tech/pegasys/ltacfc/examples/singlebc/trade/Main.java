/*
 * Copyright 2020 ConsenSys AG.
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
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.cbc.engine.AbstractCbcExecutor;
import tech.pegasys.ltacfc.cbc.engine.CbcExecutorSignedEvents;
import tech.pegasys.ltacfc.cbc.engine.CbcExecutorTxReceiptRootTransfer;
import tech.pegasys.ltacfc.cbc.engine.ExecutionEngine;
import tech.pegasys.ltacfc.cbc.engine.SerialExecutionEngine;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CredentialsCreator;
import tech.pegasys.ltacfc.common.PropertiesLoader;
import tech.pegasys.ltacfc.common.StatsHolder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Used to calculate gas cost of executing the trade finance call graph on a single blockchain.
 */
public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Start");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
    Credentials creds = propsLoader.getCredentials();
    PropertiesLoader.BlockchainInfo root = propsLoader.getBlockchainInfo("ROOT");

    Bc1TradeWallet bc1TradeWalletBlockchain = new Bc1TradeWallet(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    bc1TradeWalletBlockchain.deployContracts();


    // Do some single blockchain calls to set things up, to show that values have changed.
    // Ensure the simulator is set-up the same way.
    BigInteger buyerInitialBalance = BigInteger.valueOf(10000000);
    BigInteger sellerInitialBalance = BigInteger.valueOf(10);
    String buyerAddress = creds.getAddress();
    LOG.info("Buyer address (EOA Account): {}", buyerAddress);
    String sellerAddress = CredentialsCreator.createCredentials().getAddress();
    LOG.info("Seller address: {}", sellerAddress);
    BigInteger buyerInitialStock = BigInteger.valueOf(1);
    BigInteger sellerInitialStock = BigInteger.valueOf(100);
    BigInteger price = BigInteger.valueOf(25);

    bc1TradeWalletBlockchain.setPrice(price);
    bc1TradeWalletBlockchain.setStock(buyerAddress, buyerInitialStock);
    bc1TradeWalletBlockchain.setStock(sellerAddress, sellerInitialStock);
    bc1TradeWalletBlockchain.setBalance(buyerAddress, buyerInitialBalance);
    bc1TradeWalletBlockchain.setBalance(sellerAddress, sellerInitialBalance);


    // Simulate passing in the parameter value 7 for the cross-blockchain call.
    BigInteger quantity = BigInteger.valueOf(7);

    TransactionReceipt txR = bc1TradeWalletBlockchain.executeTrade(sellerAddress, quantity);

    bc1TradeWalletBlockchain.showAllTrades();

    List<BigInteger> callP = new ArrayList<>();
    callP.add(BigInteger.ONE);
    callP.add(BigInteger.ZERO);
    bc1TradeWalletBlockchain.showEvents(txR);

    LOG.info("Buyer: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        buyerInitialStock,
        buyerInitialBalance,
        bc1TradeWalletBlockchain.getStock(buyerAddress),
        bc1TradeWalletBlockchain.getBalance(buyerAddress));
    LOG.info("Seller: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        sellerInitialStock, sellerInitialBalance, bc1TradeWalletBlockchain.getStock(sellerAddress), bc1TradeWalletBlockchain.getBalance(sellerAddress));

    bc1TradeWalletBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
