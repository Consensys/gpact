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
package net.consensys.gpact.sfc.examples.write;

import net.consensys.gpact.common.*;
import net.consensys.gpact.sfccbc.SimpleCrossControlManagerGroup;
import net.consensys.gpact.sfccbc.SimpleCrosschainExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

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
    exampleManager.sfcStandardExampleConfig(2);

    BlockchainInfo root = exampleManager.getRootBcInfo();
    BlockchainInfo bc2 = exampleManager.getBc2Info();
    SimpleCrossControlManagerGroup crossControlManagerGroup = exampleManager.getSfcCrossControlManagerGroup();

    Credentials creds = CredentialsCreator.createCredentials();
    Bc1ContractA bc1ContractABlockchain = new Bc1ContractA(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2ContractB bc2ContractBBlockchain = new Bc2ContractB(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    // Set-up client side and deploy contracts on the blockchains.
    BlockchainId bc2BcId = bc2ContractBBlockchain.getBlockchainId();
    bc2ContractBBlockchain.deployContracts();
    String contractBContractAddress = bc2ContractBBlockchain.contractB.getContractAddress();

    BlockchainId rootBcId = bc1ContractABlockchain.getBlockchainId();
    bc1ContractABlockchain.deployContracts(crossControlManagerGroup.getCbcAddress(rootBcId), bc2BcId, contractBContractAddress);
    String contractAContractAddress = bc1ContractABlockchain.contractA.getContractAddress();

    int numExecutions = 0;
    boolean success = false;
    while (true) {
      LOG.info("Execution: {} **************************", numExecutions);
      StatsHolder.log("Execution: " + numExecutions + " **************************");

      BigInteger val = BigInteger.valueOf(7);

      LOG.info("Function Calls");
      String encodedCrosschainWrite = bc1ContractABlockchain.getRlpFunctionSignature_DoCrosschainWrite(val);
      LOG.info(" ContractA: DoCrosschainWrite: {}", encodedCrosschainWrite);

      SimpleCrosschainExecutor executor = new SimpleCrosschainExecutor(crossControlManagerGroup);
      TransactionReceipt[] receipts =
              executor.execute(rootBcId, contractAContractAddress, FormatConversion.hexStringToByteArray(encodedCrosschainWrite));

      success = (receipts.length == 2) && receipts[0].isStatusOK() && receipts[1].isStatusOK();
      LOG.info("Success: {}", success);

      bc2ContractBBlockchain.showEvents(receipts[1]);
      bc2ContractBBlockchain.checkValueWritten(val);

      if (++numExecutions >= NUM_TIMES_EXECUTE) {
        break;
      }
    }


    bc1ContractABlockchain.shutdown();
    bc2ContractBBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();

    if (!success) {
      throw new Exception("Execution was not successful");
    }
  }
}
