/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.examples.gpact.returnmultivalues;

import java.math.BigInteger;
import java.util.ArrayList;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.GpactExampleBase;
import net.consensys.gpact.helpers.GpactExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class ReturnMultiExample extends GpactExampleBase {
  static final Logger LOG = LogManager.getLogger(ReturnMultiExample.class);

  // Running multiple times will reveal any gas difference due to rerunning.
  static int NUM_TIMES_EXECUTE = 2;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Return Multi");
    LOG.info("Started");

    GpactExampleSystemManager exampleManager = getExampleSystemManager(args);
    exampleManager.standardExampleConfig(2);

    BlockchainConfig root = exampleManager.getRootBcInfo();
    BlockchainConfig bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    Credentials appCreds = CredentialsCreator.createCredentials();
    Bc1ContractA bc1ContractABlockchain = new Bc1ContractA(appCreds, root);
    Bc2ContractB bc2ContractBBlockchain = new Bc2ContractB(appCreds, bc2);

    BigInteger val = BigInteger.valueOf(7);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc2BcId = bc2ContractBBlockchain.getBlockchainId();
    bc2ContractBBlockchain.deployContracts(val);
    String contractBContractAddress = bc2ContractBBlockchain.contractB.getContractAddress();

    BlockchainId rootBcId = bc1ContractABlockchain.getBlockchainId();
    bc1ContractABlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(rootBcId),
        bc2BcId.asBigInt(),
        contractBContractAddress);
    String contractAContractAddress = bc1ContractABlockchain.contractA.getContractAddress();

    try {
      for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
        LOG.info("Execution: {}  *****************", numExecutions);
        StatsHolder.log("Execution: " + numExecutions + " **************************");

        LOG.info("Function Calls");
        String rlpGet = bc2ContractBBlockchain.getRlpFunctionSignature_Get(val);
        LOG.info(" ContractB: Get: {}", rlpGet);
        String rlpCrosschainRead =
            bc1ContractABlockchain.getRlpFunctionSignature_DoCrosschainRead(val);
        LOG.info(" ContractA: DoCrosschainRead: {}", rlpCrosschainRead);

        ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
        CallExecutionTree getFunction =
            new CallExecutionTree(bc2BcId, contractBContractAddress, rlpGet);
        rootCalls.add(getFunction);
        CallExecutionTree callTree =
            new CallExecutionTree(rootBcId, contractAContractAddress, rlpCrosschainRead, rootCalls);

        CrosschainCallResult result =
            crossControlManagerGroup.executeCrosschainCall(
                exampleManager.getExecutionEngine(), callTree, 300);

        LOG.info("Success: {}", result.isSuccessful());
        if (!result.isSuccessful()) {
          throw new Exception("Crosschain transaction failed");
        }

        TransactionReceipt txR = result.getTransactionReceipt(CrosschainCallResult.ROOT_CALL);
        bc1ContractABlockchain.checkEvents(txR, val.add(BigInteger.ONE));
      }
    } finally {
      bc1ContractABlockchain.shutdown();
      bc2ContractBBlockchain.shutdown();

      StatsHolder.log("End");
      StatsHolder.print();
    }
  }
}
