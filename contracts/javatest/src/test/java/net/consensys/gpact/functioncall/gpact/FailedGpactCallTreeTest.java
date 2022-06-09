/*
 * Copyright 2021 ConsenSys Software.
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
package net.consensys.gpact.functioncall.gpact;

import java.math.BigInteger;
import java.util.ArrayList;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.AbstractExampleTest;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.GpactExampleSystemManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;

public class FailedGpactCallTreeTest extends AbstractExampleTest {
  static final Logger LOG = LogManager.getLogger(FailedGpactCallTreeTest.class);

  GpactExampleSystemManager exampleManager;

  FailTestContractManager rootBlockchain;
  FailTestContractManager intermediateBlockchain;
  FailTestContractManager leafBlockchain;

  BlockchainConfig root;
  BlockchainConfig bc2;
  BlockchainConfig bc3;

  CrossControlManagerGroup crossControlManagerGroup;

  @Test
  public void testFailingRootAndSegments() throws Exception {
    try {
      deployContracts();

      happyCase();
      failLeafSegment();
      failIntermediateSegment();
      failRoot();

    } finally {
      if (this.rootBlockchain != null) {
        this.rootBlockchain.shutdown();
      }
      if (this.intermediateBlockchain != null) {
        this.intermediateBlockchain.shutdown();
      }
      if (this.leafBlockchain != null) {
        leafBlockchain.shutdown();
      }

      StatsHolder.log("End");
      StatsHolder.print();
    }
  }

  private void deployContracts() throws Exception {
    LOG.info("****** Deploy Contracts *******");
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, false);

    this.exampleManager = new GpactExampleSystemManager(tempPropsFile);
    exampleManager.standardExampleConfig(3);

    this.root = exampleManager.getRootBcInfo();
    this.bc2 = exampleManager.getBc2Info();
    this.bc3 = exampleManager.getBc3Info();
    this.crossControlManagerGroup = exampleManager.getCrossControlManagerGroup();

    // Set-up classes to manage blockchains.
    Credentials appCreds = CredentialsCreator.createCredentials();
    this.rootBlockchain = new FailTestContractManager("root", appCreds, root);
    this.intermediateBlockchain = new FailTestContractManager("intermediate", appCreds, bc2);
    this.leafBlockchain = new FailTestContractManager("leaf", appCreds, bc3);

    // Deploy application contracts.
    leafBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(bc3.bcId),
        BlockchainId.empty(),
        DummyAddressGenerator.addressZero());
    intermediateBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(bc2.bcId),
        bc3.bcId,
        leafBlockchain.contractAddress());
    rootBlockchain.deployContracts(
        crossControlManagerGroup.getCbcAddress(root.bcId),
        bc2.bcId,
        intermediateBlockchain.contractAddress());
  }

  private void happyCase() throws Exception {
    // Specify the call depth for the recursive call to fail to be more than two. This will
    // mean that the code will not get to the point of failure.
    executeTest("Happy Case", 5, true);
  }

  private void failLeafSegment() throws Exception {
    executeTest("Fail Leaf Segment", 2, false);
  }

  private void failIntermediateSegment() throws Exception {
    executeTest("Fail Intermediate Segment", 1, false);
  }

  private void failRoot() throws Exception {
    executeTest("Fail Root", 0, false);
  }

  private void executeTest(String testName, int failAtCallDepth, boolean expectToPass)
      throws Exception {
    LOG.info("****** {} *******", testName);

    BigInteger totalCallDepth = BigInteger.TWO;
    BigInteger failAtCallDepthBigInt = BigInteger.valueOf(failAtCallDepth);
    BigInteger callDepthRoot = BigInteger.ZERO;
    BigInteger callDepthIntermediate = BigInteger.ONE;
    BigInteger callDepthLeaf = BigInteger.TWO;

    ArrayList<CallExecutionTree> intermediateCalls = new ArrayList<>();
    ArrayList<CallExecutionTree> rootCalls = new ArrayList<>();
    CallExecutionTree leafSeg =
        new CallExecutionTree(
            bc3.bcId,
            leafBlockchain.contractAddress(),
            intermediateBlockchain.getAbi(totalCallDepth, callDepthLeaf, failAtCallDepthBigInt));
    intermediateCalls.add(leafSeg);
    CallExecutionTree intermediateSeg =
        new CallExecutionTree(
            bc2.bcId,
            intermediateBlockchain.contractAddress(),
            intermediateBlockchain.getAbi(
                totalCallDepth, callDepthIntermediate, failAtCallDepthBigInt),
            intermediateCalls);
    rootCalls.add(intermediateSeg);
    CallExecutionTree rootCall =
        new CallExecutionTree(
            root.bcId,
            rootBlockchain.contractAddress(),
            rootBlockchain.getAbi(totalCallDepth, callDepthRoot, failAtCallDepthBigInt),
            rootCalls);

    CrosschainCallResult result =
        crossControlManagerGroup.executeCrosschainCall(
            exampleManager.getExecutionEngine(), rootCall, 300);

    if (result.isSuccessful() != expectToPass) {
      String successStr = expectToPass ? "successful" : "unsuccessful";
      throw new Exception(testName + " was unexpectedly " + successStr);
    }
  }
}
