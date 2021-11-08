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
package net.consensys.gpact.examples.conditional;

import java.math.BigInteger;
import java.util.ArrayList;
import net.consensys.gpact.cbc.CrossControlManagerGroup;
import net.consensys.gpact.cbc.CrosschainExecutor;
import net.consensys.gpact.cbc.calltree.CallExecutionTree;
import net.consensys.gpact.cbc.engine.ExecutionEngine;
import net.consensys.gpact.common.*;
import net.consensys.gpact.examples.conditional.sim.SimOtherContract;
import net.consensys.gpact.examples.conditional.sim.SimRootContract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Conditional Logic");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    GpactExampleSystemManager exampleManager = new GpactExampleSystemManager(args[0]);
    exampleManager.gpactStandardExampleConfig(2);

    if (exampleManager.getExecutionEngineType() == ExecutionEngineType.PARALLEL) {
      throw new Exception(
          "This example will not work with a paralell execution engine as it has two segments that interact with the same contract on the same blockchain");
    }

    BlockchainInfo root = exampleManager.getRootBcInfo();
    BlockchainInfo bc2 = exampleManager.getBc2Info();
    CrossControlManagerGroup crossControlManagerGroup =
        exampleManager.getGpactCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    Credentials appCreds = CredentialsCreator.createCredentials();
    RootBc rootBlockchain =
        new RootBc(appCreds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    OtherBc otherBlockchain =
        new OtherBc(appCreds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);

    // Deploy application contracts.
    BlockchainId otherBcId = otherBlockchain.getBlockchainId();
    otherBlockchain.deployContracts(crossControlManagerGroup.getCbcAddress(otherBcId));
    String otherBlockchainContractAddress =
        otherBlockchain.otherBlockchainContract.getContractAddress();
    BlockchainId rootBcId = rootBlockchain.getBlockchainId();
    rootBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(rootBcId),
        otherBcId,
        otherBlockchainContractAddress);

    // Create simulators
    SimOtherContract simOtherContract = new SimOtherContract();
    SimRootContract simRootContract = new SimRootContract(simOtherContract);

    // Do some single blockchain calls to set things up, to show that values have changed.
    // Ensure the simulator is set-up the same way.
    BigInteger otherBcValInitialValue = BigInteger.valueOf(77);
    simOtherContract.setVal(otherBcValInitialValue);
    otherBlockchain.setVal(otherBcValInitialValue);

    BigInteger rootBcVal1InitialValue = BigInteger.valueOf(78);
    simRootContract.setVal1(rootBcVal1InitialValue);
    rootBlockchain.setVal1(rootBcVal1InitialValue);
    BigInteger rootBcVal2InitialValue = BigInteger.valueOf(79);
    simRootContract.setVal2(rootBcVal2InitialValue);
    rootBlockchain.setVal2(rootBcVal2InitialValue);

    // Simulate passing in the parameter value 7 for the cross-blockchain call.
    BigInteger param = BigInteger.valueOf(7);
    simRootContract.someComplexBusinessLogic(param);

    String rlpFunctionCall_SomeComplexBusinessLogic =
        rootBlockchain.getRlpFunctionSignature_SomeComplexBusinessLogic(param);
    LOG.info(
        "rlpFunctionCall_SomeComplexBusinessLogic: {}", rlpFunctionCall_SomeComplexBusinessLogic);
    String rlpFunctionCall_GetVal = otherBlockchain.getRlpFunctionSignature_GetVal();
    LOG.info("rlpFunctionCall_GetVal: {}", rlpFunctionCall_GetVal);
    String rlpFunctionCall_SetValues = null;
    String rlpFunctionCall_SetVal = null;
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      rlpFunctionCall_SetValues =
          otherBlockchain.getRlpFunctionSignature_SetValues(
              simRootContract.someComplexBusinessLogicSetValuesParameter1,
              simRootContract.someComplexBusinessLogicSetValuesParameter2);
      LOG.info("rlpFunctionCall_SetValues: {}", rlpFunctionCall_SetValues);
    } else {
      rlpFunctionCall_SetVal =
          otherBlockchain.getRlpFunctionSignature_SetVal(
              simRootContract.someComplexBusinessLogicSetValParameter);
      LOG.info("rlpFunctionCall_SetVal: {}", rlpFunctionCall_SetVal);
    }

    ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
    CallExecutionTree getValSeg =
        new CallExecutionTree(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_GetVal);
    rootCalls.add(getValSeg);
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      CallExecutionTree setValuesSeg =
          new CallExecutionTree(
              otherBcId, otherBlockchainContractAddress, rlpFunctionCall_SetValues);
      rootCalls.add(setValuesSeg);
    } else {
      CallExecutionTree setValSeg =
          new CallExecutionTree(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_SetVal);
      rootCalls.add(setValSeg);
    }
    CallExecutionTree rootCall =
        new CallExecutionTree(
            rootBcId,
            rootBlockchain.rootBlockchainContract.getContractAddress(),
            rlpFunctionCall_SomeComplexBusinessLogic,
            rootCalls);
    byte[] encoded = rootCall.encode();
    LOG.info(CallExecutionTree.dump(encoded));

    CrosschainExecutor executor = new CrosschainExecutor(crossControlManagerGroup);
    ExecutionEngine executionEngine = exampleManager.getExecutionEngine(executor);

    boolean success = executionEngine.execute(rootCall, 300);

    LOG.info("Success: {}", success);
    if (success) {
      LOG.info(
          " Simulated expected values: Root val1: {}, val2: {}, Other val: {}",
          simRootContract.getVal1(),
          simRootContract.getVal2(),
          simOtherContract.getVal());
    } else {
      LOG.info(
          " Expect unchanged initial values: Root val1: {}, val2: {}, Other val: {}",
          rootBcVal1InitialValue,
          rootBcVal2InitialValue,
          otherBcValInitialValue);
    }
    rootBlockchain.showValues();
    otherBlockchain.showValues();
    LOG.info(" Other contract's storage is locked: {}", otherBlockchain.storageIsLocked());

    rootBlockchain.shutdown();
    otherBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
