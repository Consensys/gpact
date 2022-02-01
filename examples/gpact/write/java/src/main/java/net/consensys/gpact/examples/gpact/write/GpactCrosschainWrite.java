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
package net.consensys.gpact.examples.gpact.write;

import java.math.BigInteger;
import java.util.ArrayList;
import net.consensys.gpact.common.*;
import net.consensys.gpact.examples.gpact.write.sim.SimContractA;
import net.consensys.gpact.examples.gpact.write.sim.SimContractB;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.functioncall.calltree.CallExecutionTree;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.GpactExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class GpactCrosschainWrite {
  static final Logger LOG = LogManager.getLogger(GpactCrosschainWrite.class);

  // Running multiple times will reveal any gas difference due to rerunning.
  static int NUM_TIMES_EXECUTE = 2;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Write");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    GpactExampleSystemManager exampleManager = new GpactExampleSystemManager(args[0]);
    exampleManager.gpactStandardExampleConfig(2);

    BlockchainInfo root = exampleManager.getRootBcInfo();
    BlockchainInfo bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    Credentials creds = CredentialsCreator.createCredentials();
    Bc1ContractA bc1ContractABlockchain =
        new Bc1ContractA(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2ContractB bc2ContractBBlockchain =
        new Bc2ContractB(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc2BcId = bc2ContractBBlockchain.getBlockchainId();
    bc2ContractBBlockchain.deployContracts(crossControlManagerGroup.getCbcAddress(bc2BcId));
    String contractBContractAddress = bc2ContractBBlockchain.contractB.getContractAddress();

    BlockchainId rootBcId = bc1ContractABlockchain.getBlockchainId();
    bc1ContractABlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(rootBcId), bc2BcId, contractBContractAddress);
    String contractAContractAddress = bc1ContractABlockchain.contractA.getContractAddress();

    // Create simulators
    SimContractB simContractB = new SimContractB(bc2ContractBBlockchain);
    SimContractA simContractA = new SimContractA(bc1ContractABlockchain, simContractB);

    for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
      LOG.info("Execution: {} **************************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      BigInteger val = BigInteger.valueOf(7);

      simContractA.doCrosschainWrite(val);

      LOG.info("Function Calls");
      String rlpSet = simContractB.getRlpFunctionSignature_Set();
      LOG.info(" ContractB: Set: {}", rlpSet);
      String rlpCrosschainWrite = simContractA.getRlpFunctionSignature_DoCrosschainWrite();
      LOG.info(" ContractA: DoCrosschainWrite: {}", rlpCrosschainWrite);

      CallExecutionTree getFunction =
          new CallExecutionTree(bc2BcId, contractBContractAddress, rlpSet);
      ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
      rootCalls.add(getFunction);
      CallExecutionTree callGraph =
          new CallExecutionTree(rootBcId, contractAContractAddress, rlpCrosschainWrite, rootCalls);

      CrosschainCallResult result =
          crossControlManagerGroup.executeCrosschainCall(
              exampleManager.getExecutionEngine(), callGraph, 300);

      LOG.info("Success: {}", result.successful());

      //      List<BigInteger> callP = new ArrayList<>();
      //      callP.add(BigInteger.ZERO);
      //      TransactionReceipt txR = executor.getTransationReceipt(callP);
      //      bc2ContractBBlockchain.showEvents(txR);
      bc2ContractBBlockchain.showValueWritten();
    }

    bc1ContractABlockchain.shutdown();
    bc2ContractBBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
