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
package net.consensys.gpact.examples.trade;

import net.consensys.gpact.cbc.calltree.CallExecutionTree;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.cbc.engine.AbstractCbcExecutor;
import net.consensys.gpact.cbc.engine.CbcExecutorSignedEvents;
import net.consensys.gpact.cbc.engine.CbcExecutorTxReceiptRootTransfer;
import net.consensys.gpact.cbc.engine.ExecutionEngine;
import net.consensys.gpact.cbc.engine.ParallelExecutionEngine;
import net.consensys.gpact.cbc.engine.SerialExecutionEngine;
import net.consensys.gpact.examples.trade.sim.SimBalancesContract;
import net.consensys.gpact.examples.trade.sim.SimBusLogicContract;
import net.consensys.gpact.examples.trade.sim.SimPriceOracleContract;
import net.consensys.gpact.examples.trade.sim.SimTradeWallet;
import net.consensys.gpact.examples.trade.sim.SimStockContract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Trade");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
    Credentials creds = CredentialsCreator.createCredentials();
    BlockchainInfo root = propsLoader.getBlockchainInfo("ROOT");
    BlockchainInfo bc2 = propsLoader.getBlockchainInfo("BC2");
    BlockchainInfo bc3 = propsLoader.getBlockchainInfo("BC3");
    BlockchainInfo bc4 = propsLoader.getBlockchainInfo("BC4");
    BlockchainInfo bc5 = propsLoader.getBlockchainInfo("BC5");
    CrossBlockchainConsensusType consensusMethodology = propsLoader.getConsensusMethodology();
    StatsHolder.log(consensusMethodology.name());
    ExecutionEngineType engineType = propsLoader.getExecutionEnngine();
    StatsHolder.log(engineType.name());

    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    CbcManager cbcManager = new CbcManager(consensusMethodology);
    cbcManager.addBlockchainAndDeployContracts(creds, root);
    cbcManager.addBlockchainAndDeployContracts(creds, bc2);
    cbcManager.addBlockchainAndDeployContracts(creds, bc3);
    cbcManager.addBlockchainAndDeployContracts(creds, bc4);
    cbcManager.addBlockchainAndDeployContracts(creds, bc5);
    // Have each Crosschain Control contract trust the Crosschain Control
    // contracts on the other blockchains.
    // To keep the example simple, just have one signer for all blockchains.
    AnIdentity globalSigner = new AnIdentity();
    cbcManager.setupCrosschainTrust(globalSigner);

    // Set-up classes to manage blockchains.
    Bc1TradeWallet bc1TradeWalletBlockchain = new Bc1TradeWallet(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2BusLogic bc2BusLogicBlockchain = new Bc2BusLogic(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
    Bc3Balances bc3BalancesBlockchain = new Bc3Balances(creds, bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
    Bc4Oracle bc4OracleBlockchain = new Bc4Oracle(creds, bc4.bcId, bc4.uri, bc4.gasPriceStrategy, bc4.period);
    Bc5Stock bc5StockBlockchain = new Bc5Stock(creds, bc5.bcId, bc5.uri, bc5.gasPriceStrategy, bc5.period);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc3BcId = bc3BalancesBlockchain.getBlockchainId();
    bc3BalancesBlockchain.deployContracts(cbcManager.getCbcAddress(bc3BcId));
    String balancesContractAddress = bc3BalancesBlockchain.balancesContract.getContractAddress();

    BlockchainId bc4BcId = bc4OracleBlockchain.getBlockchainId();
    bc4OracleBlockchain.deployContracts(cbcManager.getCbcAddress(bc4BcId));
    String priceOracleContractAddress = bc4OracleBlockchain.priceOracleContract.getContractAddress();

    BlockchainId bc5BcId = bc5StockBlockchain.getBlockchainId();
    bc5StockBlockchain.deployContracts(cbcManager.getCbcAddress(bc5BcId));
    String stockContractAddress = bc5StockBlockchain.stockContract.getContractAddress();

    BlockchainId bc2BcId = bc2BusLogicBlockchain.getBlockchainId();
    bc2BusLogicBlockchain.deployContracts(cbcManager.getCbcAddress(bc2BcId),
        bc3BcId, balancesContractAddress, bc4BcId, priceOracleContractAddress, bc5BcId, stockContractAddress);
    String businessLogicContractAddress = bc2BusLogicBlockchain.busLogicContract.getContractAddress();

    BlockchainId rootBcId = bc1TradeWalletBlockchain.getBlockchainId();
    bc1TradeWalletBlockchain.deployContracts(cbcManager.getCbcAddress(rootBcId), bc2BcId, businessLogicContractAddress);
    String tradeWalletContractAddress = bc1TradeWalletBlockchain.tradeWalletContract.getContractAddress();


    // Create simulators
    SimStockContract simStockContract = new SimStockContract(bc5StockBlockchain);
    SimBalancesContract simBalancesContract = new SimBalancesContract(bc3BalancesBlockchain);
    SimPriceOracleContract simPriceOracleContract = new SimPriceOracleContract(bc4OracleBlockchain);
    SimBusLogicContract simBusLogicContract = new SimBusLogicContract(bc2BusLogicBlockchain, simPriceOracleContract, simBalancesContract, simStockContract);
    SimTradeWallet simTradeWallet = new SimTradeWallet(bc1TradeWalletBlockchain, simBusLogicContract);

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

    CallExecutionTree getPrice = new CallExecutionTree(bc4BcId, priceOracleContractAddress, rlpPriceOracleGetPrice);
    CallExecutionTree balanceTransfer = new CallExecutionTree(bc3BcId, balancesContractAddress, rlpBalancesTransfer);
    CallExecutionTree stockDelivery = new CallExecutionTree(bc5BcId, stockContractAddress, rlpStockDelivery);
    ArrayList<CallExecutionTree> busLogicCalls = new ArrayList<>();
    busLogicCalls.add(getPrice);
    busLogicCalls.add(balanceTransfer);
    busLogicCalls.add(stockDelivery);
    CallExecutionTree businessLogic = new CallExecutionTree(bc2BcId, businessLogicContractAddress, rlpBusLogicStockShipment, busLogicCalls);
    ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
    rootCalls.add(businessLogic);
    CallExecutionTree callGraph = new CallExecutionTree(rootBcId, tradeWalletContractAddress, rlpRootExecuteTrade, rootCalls);

    AbstractCbcExecutor executor;
    switch (consensusMethodology) {
      case TRANSACTION_RECEIPT_SIGNING:
        executor = new CbcExecutorTxReceiptRootTransfer(cbcManager);
        break;
      case EVENT_SIGNING:
        executor = new CbcExecutorSignedEvents(cbcManager);
        break;
      default:
        throw new RuntimeException("Not implemented yet");
    }

    ExecutionEngine executionEngine;
    switch (engineType) {
      case SERIAL:
        executionEngine = new SerialExecutionEngine(executor);
        break;
      case PARALLEL:
        executionEngine = new ParallelExecutionEngine(executor);
        break;
      default:
        throw new RuntimeException("Not implemented yet");
    }
    boolean success = executionEngine.execute(callGraph, 300);

    LOG.info("Success: {}", success);

    bc1TradeWalletBlockchain.showAllTrades();

    List<BigInteger> callP = new ArrayList<>();
    callP.add(BigInteger.ONE);
    callP.add(BigInteger.ZERO);
    TransactionReceipt txR = executor.getTransationReceipt(callP);
    bc2BusLogicBlockchain.showEvents(txR);

    LOG.info("Trade Wallet contract's lockable storaged locked: {}", bc1TradeWalletBlockchain.storageIsLocked());
    LOG.info("Balances contract's lockable storaged locked: {}", bc3BalancesBlockchain.storageIsLocked());
    LOG.info("Stock contract's lockable storaged locked: {}", bc5StockBlockchain.storageIsLocked());

    LOG.info("Buyer: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        buyerInitialStock,
        buyerInitialBalance,
        bc5StockBlockchain.getStock(buyerAddress),
        bc3BalancesBlockchain.getBalance(buyerAddress));
    LOG.info("Seller: Initial Stock: {}, Initial Balance: {}, Final Stock: {}, Final Balance: {}",
        sellerInitialStock, sellerInitialBalance, bc5StockBlockchain.getStock(sellerAddress), bc3BalancesBlockchain.getBalance(sellerAddress));

    bc1TradeWalletBlockchain.shutdown();
    bc2BusLogicBlockchain.shutdown();
    bc3BalancesBlockchain.shutdown();
    bc4OracleBlockchain.shutdown();
    bc5StockBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
