/*
 * Copyright 2019 ConsenSys Software Inc
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
package net.consensys.gpact.examples.gpact.trade;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.*;
import net.consensys.gpact.examples.gpact.trade.sim.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.GpactExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Trade");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    GpactExampleSystemManager exampleManager = new GpactExampleSystemManager(args[0]);
    // TODO can be configured for 5, however, need to set-up test system for 5 blockchains.
    exampleManager.standardExampleConfig(3);

    BlockchainConfig root = exampleManager.getRootBcInfo();
    BlockchainConfig bc2 = exampleManager.getBc2Info();
    BlockchainConfig bc3 = exampleManager.getBc3Info();
    BlockchainConfig bc4 =
        exampleManager.getBc2Info(); // Change this to 4 if 5 blockchains are available
    BlockchainConfig bc5 =
        exampleManager.getBc3Info(); // Change this to 5 if 5 blockchains are available
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    Credentials creds = CredentialsCreator.createCredentials();
    Bc1TradeWallet bc1TradeWalletBlockchain =
        new Bc1TradeWallet(
            creds, root.bcId, root.blockchainNodeRpcUri, root.gasPriceStrategy, root.period);
    Bc2BusLogic bc2BusLogicBlockchain =
        new Bc2BusLogic(
            creds, bc2.bcId, bc2.blockchainNodeRpcUri, bc2.gasPriceStrategy, bc2.period);
    Bc3Balances bc3BalancesBlockchain =
        new Bc3Balances(
            creds, bc3.bcId, bc3.blockchainNodeRpcUri, bc3.gasPriceStrategy, bc3.period);
    Bc4Oracle bc4OracleBlockchain =
        new Bc4Oracle(creds, bc4.bcId, bc4.blockchainNodeRpcUri, bc4.gasPriceStrategy, bc4.period);
    Bc5Stock bc5StockBlockchain =
        new Bc5Stock(creds, bc5.bcId, bc5.blockchainNodeRpcUri, bc5.gasPriceStrategy, bc5.period);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc3BcId = bc3BalancesBlockchain.getBlockchainId();
    bc3BalancesBlockchain.deployContracts(crossControlManagerGroup.getCbcAddress(bc3BcId));
    String balancesContractAddress = bc3BalancesBlockchain.balancesContract.getContractAddress();

    BlockchainId bc4BcId = bc4OracleBlockchain.getBlockchainId();
    bc4OracleBlockchain.deployContracts();
    String priceOracleContractAddress =
        bc4OracleBlockchain.priceOracleContract.getContractAddress();

    BlockchainId bc5BcId = bc5StockBlockchain.getBlockchainId();
    bc5StockBlockchain.deployContracts(crossControlManagerGroup.getCbcAddress(bc5BcId));
    String stockContractAddress = bc5StockBlockchain.stockContract.getContractAddress();

    BlockchainId bc2BcId = bc2BusLogicBlockchain.getBlockchainId();
    bc2BusLogicBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(bc2BcId),
        bc3BcId,
        balancesContractAddress,
        bc4BcId,
        priceOracleContractAddress,
        bc5BcId,
        stockContractAddress);
    String businessLogicContractAddress =
        bc2BusLogicBlockchain.busLogicContract.getContractAddress();

    BlockchainId rootBcId = bc1TradeWalletBlockchain.getBlockchainId();
    bc1TradeWalletBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(rootBcId), bc2BcId, businessLogicContractAddress);
    String tradeWalletContractAddress =
        bc1TradeWalletBlockchain.tradeWalletContract.getContractAddress();

    // Create simulators
    SimStockContract simStockContract = new SimStockContract(bc5StockBlockchain);
    SimBalancesContract simBalancesContract = new SimBalancesContract(bc3BalancesBlockchain);
    SimPriceOracleContract simPriceOracleContract = new SimPriceOracleContract(bc4OracleBlockchain);
    SimBusLogicContract simBusLogicContract =
        new SimBusLogicContract(
            bc2BusLogicBlockchain, simPriceOracleContract, simBalancesContract, simStockContract);
    SimTradeWallet simTradeWallet =
        new SimTradeWallet(bc1TradeWalletBlockchain, simBusLogicContract);

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

    bc4OracleBlockchain.setPrice(price);
    bc5StockBlockchain.setStock(buyerAddress, buyerInitialStock);
    bc5StockBlockchain.setStock(sellerAddress, sellerInitialStock);
    bc3BalancesBlockchain.setBalance(buyerAddress, buyerInitialBalance);
    bc3BalancesBlockchain.setBalance(sellerAddress, sellerInitialBalance);

    // Simulate passing in the parameter value 7 for the cross-blockchain call.
    BigInteger quantity = BigInteger.valueOf(7);
    simTradeWallet.executeTrade(buyerAddress, sellerAddress, quantity);

    LOG.info("Function Calls");
    String rlpRootExecuteTrade = simTradeWallet.getRlpFunctionSignature_executeTrade();
    LOG.info(" Trade Wallet: Execute Trade: {}", rlpRootExecuteTrade);
    String rlpBusLogicStockShipment = simBusLogicContract.getRlpFunctionSignature_stockShipment();
    LOG.info(" Business Logic: Stock Shipment: {}", rlpBusLogicStockShipment);
    String rlpPriceOracleGetPrice = simPriceOracleContract.getRlpFunctionSignature_getPrice();
    LOG.info(" Price Oracle: Get Price: {}", rlpPriceOracleGetPrice);
    String rlpBalancesTransfer = simBalancesContract.getRlpFunctionSignature_transfer();
    LOG.info(" Balances: Transfer: {}", rlpBalancesTransfer);
    String rlpStockDelivery = simStockContract.getRlpFunctionSignature_delivery();
    LOG.info(" Stock: Delivery: {}", rlpStockDelivery);

    CallExecutionTree getPrice =
        new CallExecutionTree(bc4BcId, priceOracleContractAddress, rlpPriceOracleGetPrice);
    CallExecutionTree balanceTransfer =
        new CallExecutionTree(bc3BcId, balancesContractAddress, rlpBalancesTransfer);
    CallExecutionTree stockDelivery =
        new CallExecutionTree(bc5BcId, stockContractAddress, rlpStockDelivery);
    ArrayList<CallExecutionTree> busLogicCalls = new ArrayList<>();
    busLogicCalls.add(getPrice);
    busLogicCalls.add(balanceTransfer);
    busLogicCalls.add(stockDelivery);
    CallExecutionTree businessLogic =
        new CallExecutionTree(
            bc2BcId, businessLogicContractAddress, rlpBusLogicStockShipment, busLogicCalls);
    ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
    rootCalls.add(businessLogic);
    CallExecutionTree callGraph =
        new CallExecutionTree(rootBcId, tradeWalletContractAddress, rlpRootExecuteTrade, rootCalls);

    CrosschainCallResult result =
        crossControlManagerGroup.executeCrosschainCall(
            exampleManager.getExecutionEngine(), callGraph, 300);

    LOG.info("Success: {}", result.isSuccessful());

    bc1TradeWalletBlockchain.showAllTrades();

    List<BigInteger> callP = new ArrayList<>();
    callP.add(BigInteger.ONE);
    callP.add(BigInteger.ZERO);
    TransactionReceipt txR = result.getTransactionReceipt(callP);
    bc2BusLogicBlockchain.showEvents(txR);

    LOG.info(
        "Trade Wallet contract's lockable storaged locked: {}",
        bc1TradeWalletBlockchain.storageIsLocked());
    LOG.info(
        "Balances contract's lockable storaged locked: {}",
        bc3BalancesBlockchain.storageIsLocked());
    LOG.info("Stock contract's lockable storaged locked: {}", bc5StockBlockchain.storageIsLocked());

    LOG.info(
        "Buyer: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        buyerInitialStock,
        buyerInitialBalance,
        bc5StockBlockchain.getStock(buyerAddress),
        bc3BalancesBlockchain.getBalance(buyerAddress));
    LOG.info(
        "Seller: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        sellerInitialStock,
        sellerInitialBalance,
        bc5StockBlockchain.getStock(sellerAddress),
        bc3BalancesBlockchain.getBalance(sellerAddress));

    bc1TradeWalletBlockchain.shutdown();
    bc2BusLogicBlockchain.shutdown();
    bc3BalancesBlockchain.shutdown();
    bc4OracleBlockchain.shutdown();
    bc5StockBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
