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
package net.consensys.gpact.examples.read;

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
import net.consensys.gpact.examples.read.sim.SimContractA;
import net.consensys.gpact.examples.read.sim.SimContractB;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  // Running multiple times will reveal any gas difference due to rerunning.
  static int NUM_TIMES_EXECUTE = 2;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Read");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
    Credentials creds = CredentialsCreator.createCredentials();
    PropertiesLoader.BlockchainInfo root = propsLoader.getBlockchainInfo("ROOT");
    PropertiesLoader.BlockchainInfo bc2 = propsLoader.getBlockchainInfo("BC2");
    CrossBlockchainConsensusType consensusMethodology = propsLoader.getConsensusMethodology();
    StatsHolder.log(consensusMethodology.name());
    ExecutionEngineType engineType = propsLoader.getExecutionEnngine();
    StatsHolder.log(engineType.name());

    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    CbcManager cbcManager = new CbcManager(consensusMethodology);
    cbcManager.addBlockchainAndDeployContracts(creds, root);
    cbcManager.addBlockchainAndDeployContracts(creds, bc2);
    // Have each Crosschain Control contract trust the Crosschain Control
    // contracts on the other blockchains.
    // To keep the example simple, just have one signer for all blockchains.
    AnIdentity globalSigner = new AnIdentity();
    cbcManager.setupCrosschainTrust(globalSigner);

    // Set-up classes to manage blockchains.
    Credentials appCreds = CredentialsCreator.createCredentials();
    Bc1ContractA bc1ContractABlockchain = new Bc1ContractA(appCreds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2ContractB bc2ContractBBlockchain = new Bc2ContractB(appCreds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    BigInteger val = BigInteger.valueOf(7);

    // Set-up client side and deploy contracts on the blockchains.
    BigInteger bc2BcId = bc2ContractBBlockchain.getBlockchainId();
    bc2ContractBBlockchain.deployContracts(val);
    String contractBContractAddress = bc2ContractBBlockchain.contractB.getContractAddress();

    BigInteger rootBcId = bc1ContractABlockchain.getBlockchainId();
    bc1ContractABlockchain.deployContracts(cbcManager.getCbcAddress(rootBcId), bc2BcId, contractBContractAddress);
    String contractAContractAddress = bc1ContractABlockchain.contractA.getContractAddress();


    // Create simulators
    // Note that no simulation is needed, as there are no parameter values.
    SimContractA simContractA = new SimContractA(bc1ContractABlockchain);
    SimContractB simContractB = new SimContractB(bc2ContractBBlockchain);

    int numExecutions = 0;
    while (true) {
      LOG.info("Execution: {}  *****************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      LOG.info("Function Calls");
      String rlpGet = simContractB.getRlpFunctionSignature_Get();
      LOG.info(" ContractB: Get: {}", rlpGet);
      String rlpCrosschainRead = simContractA.getRlpFunctionSignature_DoCrosschainRead();
      LOG.info(" ContractA: DoCrosschainRead: {}", rlpCrosschainRead);

      ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
      CallExecutionTree getFunction = new CallExecutionTree(bc2BcId, contractBContractAddress, rlpGet);
      rootCalls.add(getFunction);
      CallExecutionTree callGraph = new CallExecutionTree(rootBcId, contractAContractAddress, rlpCrosschainRead, rootCalls);

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

      List<BigInteger> callP = new ArrayList<>();
      callP.add(BigInteger.ZERO);
      TransactionReceipt txR = executor.getTransationReceipt(callP);
      bc1ContractABlockchain.showEvents(txR);
      bc1ContractABlockchain.showValueRead();


      if (++numExecutions >= NUM_TIMES_EXECUTE) {
        break;
      }
    }


    bc1ContractABlockchain.shutdown();
    bc2ContractBBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
