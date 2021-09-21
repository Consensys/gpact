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
package net.consensys.gpact.cbc.engine;

import net.consensys.gpact.cbc.calltree.CallExecutionTree;
import net.consensys.gpact.common.BlockchainId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.consensys.gpact.cbc.AbstractCbc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExecutionEngine implements ExecutionEngine {
  static final Logger LOG = LogManager.getLogger(AbstractExecutionEngine.class);

  AbstractCbcExecutor executor;

  public AbstractExecutionEngine(AbstractCbcExecutor executor) {
    this.executor = executor;
  }

  public boolean execute(CallExecutionTree callGraph, long timeout) throws Exception {
    LOG.info("start");
    BigInteger crossBlockchainTransactionId = AbstractCbc.generateRandomCrossBlockchainTransactionId();
    BigInteger timeoutBig = BigInteger.valueOf(timeout);


    BlockchainId rootBlockchainId = callRootBlockchainId(callGraph);
    this.executor.init(callGraph.encode(), timeoutBig, crossBlockchainTransactionId, rootBlockchainId);
    this.executor.startCall();
    callSegmentsAndRoot(callGraph, new ArrayList<>(), rootBlockchainId);

    this.executor.doSignallingCalls();

    return this.executor.getRootEventSuccess();
  }


  protected void callSegmentsAndRoot(CallExecutionTree callGraph, List<BigInteger> callPath, BlockchainId callerBlockchainId) throws Exception {
    BlockchainId thisCallsBcId = callGraph.getBlockchainId();
    if (!callGraph.isLeaf()) {
      executeCalls(callGraph.getCalledFunctions(), callPath, thisCallsBcId);

      // Now call the segment call that called all of the other segments.
      if (callPath.size() == 0) {
        this.executor.root();
      }
      else {
        List<BigInteger> nextCallPath = new ArrayList<>(callPath);
        nextCallPath.add(BigInteger.ZERO);
        this.executor.segment(thisCallsBcId, callerBlockchainId, nextCallPath);
      }
    }
    else {
      this.executor.segment(thisCallsBcId, callerBlockchainId, callPath);
    }
  }

  private static BlockchainId callRootBlockchainId(CallExecutionTree callGraph) {
    BlockchainId rootBcId = callGraph.getBlockchainId();
    LOG.info("Root blockchain detected as: {}", rootBcId);
    return rootBcId;
  }

  protected abstract void executeCalls(List<CallExecutionTree> calls, List<BigInteger> callPath, BlockchainId theCallerBlockchainId) throws Exception;

}
