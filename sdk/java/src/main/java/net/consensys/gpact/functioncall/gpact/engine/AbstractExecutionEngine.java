/*
 * Copyright 2020 ConsenSys Software Inc
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
package net.consensys.gpact.functioncall.gpact.engine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.crypto.RandomNumbers;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.functioncall.common.CrosschainCallResultImpl;
import net.consensys.gpact.functioncall.gpact.GpactCrosschainExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractExecutionEngine implements ExecutionEngine {
  static final Logger LOG = LogManager.getLogger(AbstractExecutionEngine.class);

  private static final int LENGTH_OF_TX_ID_BYTES = 32;

  GpactCrosschainExecutor executor;

  public AbstractExecutionEngine(GpactCrosschainExecutor executor) {
    this.executor = executor;
  }

  public CrosschainCallResult execute(final CallExecutionTree callExecutionTree, final long timeout)
      throws Exception {
    LOG.info("Start: Begin");
    BigInteger crossBlockchainTransactionId =
        new BigInteger(1, RandomNumbers.generatePublicRandomBytes(LENGTH_OF_TX_ID_BYTES));
    BigInteger timeoutBig = BigInteger.valueOf(timeout);
    BlockchainId rootBlockchainId = callRootBlockchainId(callExecutionTree);
    this.executor.init(
        callExecutionTree, timeoutBig, crossBlockchainTransactionId, rootBlockchainId);
    this.executor.startCall();
    LOG.info("Start: End");

    LOG.info("Segments and Root: Begin");
    callSegmentsAndRoot(
        callExecutionTree,
        new ArrayList<>(),
        rootBlockchainId,
        callExecutionTree.getNumCalledFunctions());
    LOG.info("Segments and Root: End");

    LOG.info("Signalling: Begin");
    this.executor.doSignallingCalls();
    LOG.info("Signalling: End");

    return new CrosschainCallResultImpl(
        callExecutionTree,
        this.executor.getRootEventSuccess(),
        this.executor.getTransationReceipts());
  }

  protected void callSegmentsAndRoot(
      CallExecutionTree callGraph,
      List<BigInteger> callPath,
      BlockchainId callerBlockchainId,
      final int numCallsFromParent)
      throws Exception {
    BlockchainId thisCallsBcId = callGraph.getBlockchainId();
    if (!callGraph.isLeaf()) {
      executeCalls(callGraph.getCalledFunctions(), callPath, thisCallsBcId);

      // Now call the segment call that called all of the other segments.
      if (callPath.size() == 0) {
        this.executor.root();
      } else {
        List<BigInteger> nextCallPath = new ArrayList<>(callPath);
        nextCallPath.add(BigInteger.ZERO);
        this.executor.segment(thisCallsBcId, callerBlockchainId, nextCallPath, numCallsFromParent);
      }
    } else {
      this.executor.segment(thisCallsBcId, callerBlockchainId, callPath, numCallsFromParent);
    }
  }

  private static BlockchainId callRootBlockchainId(CallExecutionTree callGraph) {
    BlockchainId rootBcId = callGraph.getBlockchainId();
    LOG.info("Root blockchain detected as: {}", rootBcId);
    return rootBcId;
  }

  protected abstract void executeCalls(
      List<CallExecutionTree> calls, List<BigInteger> callPath, BlockchainId theCallerBlockchainId)
      throws Exception;
}
