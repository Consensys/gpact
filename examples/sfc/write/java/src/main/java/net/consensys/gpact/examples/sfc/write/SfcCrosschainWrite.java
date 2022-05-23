/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.examples.sfc.write;

import java.math.BigInteger;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.SfcExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class SfcCrosschainWrite {
  static final Logger LOG = LogManager.getLogger(SfcCrosschainWrite.class);

  // Running multiple times will reveal any gas difference due to rerunning.
  static int NUM_TIMES_EXECUTE = 2;

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: SFC Write");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    SfcExampleSystemManager exampleManager = new SfcExampleSystemManager(args[0]);
    exampleManager.standardExampleConfig(2);

    BlockchainConfig root = exampleManager.getRootBcInfo();
    BlockchainConfig bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getCrossControlManagerGroup();

    Credentials creds = CredentialsCreator.createCredentials();
    Bc1ContractA bc1ContractABlockchain = new Bc1ContractA(creds, root);
    Bc2ContractB bc2ContractBBlockchain = new Bc2ContractB(creds, bc2);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc2BcId = bc2ContractBBlockchain.getBlockchainId();
    bc2ContractBBlockchain.deployContracts();
    String contractBContractAddress = bc2ContractBBlockchain.contractB.getContractAddress();

    BlockchainId rootBcId = bc1ContractABlockchain.getBlockchainId();
    bc1ContractABlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(rootBcId), bc2BcId, contractBContractAddress);

    for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
      LOG.info("Execution: {} **************************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      BigInteger val = BigInteger.valueOf(7);
      String rlpCrosschainWrite = bc1ContractABlockchain.getABI_doCrosschainWrite(val);

      String contractAContractAddress = bc1ContractABlockchain.getContractAddress();

      CallExecutionTree rootCall =
          new CallExecutionTree(rootBcId, contractAContractAddress, rlpCrosschainWrite);

      CrosschainCallResult result =
          crossControlManagerGroup.executeCrosschainCall(
              exampleManager.getExecutionEngine(), rootCall, 300);

      LOG.info("Success: {}", result.isSuccessful());
      if (!result.isSuccessful()) {
        LOG.error("Crosschain Execution failed. See log for details");
        String errorMsg = result.getAdditionalErrorInfo();
        if (errorMsg != null) {
          LOG.error("Error information: {}", errorMsg);
        }
        TransactionReceipt rootTxr = result.getTransactionReceipt(CrosschainCallResult.ROOT_CALL);
        LOG.error("Root Call Transaction Receipt: {}", rootTxr.toString());
        TransactionReceipt firstCallTxr =
            result.getTransactionReceipt(CrosschainCallResult.FIRST_CALL);
        LOG.error("Called function Transaction Receipt: {}", firstCallTxr.toString());
        throw new Exception("Crosschain Execution failed. See log for details");
      }

      TransactionReceipt rootTxr = result.getTransactionReceipt(CrosschainCallResult.ROOT_CALL);
      bc2ContractBBlockchain.showEvents(rootTxr);
      bc2ContractBBlockchain.checkValueWritten(val);
    }

    bc1ContractABlockchain.shutdown();
    bc2ContractBBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
