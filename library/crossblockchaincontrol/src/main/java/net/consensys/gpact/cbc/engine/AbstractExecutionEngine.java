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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
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

  public boolean execute(RlpList callGraph, long timeout) throws Exception {
    LOG.info("start");
    BigInteger crossBlockchainTransactionId = AbstractCbc.generateRandomCrossBlockchainTransactionId();
    BigInteger timeoutBig = BigInteger.valueOf(timeout);


    BigInteger rootBlockchainId = callRootBlockchainId(callGraph);
    this.executor.init(RlpEncoder.encode(callGraph), timeoutBig, crossBlockchainTransactionId, rootBlockchainId);
    this.executor.startCall();
    callSegmentsAndRoot(callGraph, new ArrayList<>(), rootBlockchainId);

    this.executor.doSignallingCalls();

    return this.executor.getRootEventSuccess();
  }

  public boolean execute(CallExecutionTree callGraph, long timeout) throws Exception {
    LOG.info("start");
    BigInteger crossBlockchainTransactionId = AbstractCbc.generateRandomCrossBlockchainTransactionId();
    BigInteger timeoutBig = BigInteger.valueOf(timeout);


    BigInteger rootBlockchainId = callRootBlockchainId(callGraph);
    this.executor.init(callGraph.encode(), timeoutBig, crossBlockchainTransactionId, rootBlockchainId);
    this.executor.startCall();
    callSegmentsAndRoot(callGraph, new ArrayList<>(), rootBlockchainId);

    this.executor.doSignallingCalls();

    return this.executor.getRootEventSuccess();
  }

  protected void callSegmentsAndRoot(RlpList callGraph, List<BigInteger> callPath, BigInteger callerBlockchainId) throws Exception {
    List<RlpType> calls = callGraph.getValues();
    if (calls.get(0) instanceof  RlpList) {
      RlpList callerCall = (RlpList) calls.get(0);
      RlpString blockchainIdRlp = (RlpString) callerCall.getValues().get(0);
      BigInteger theCallerBlockchainId = blockchainIdRlp.asPositiveBigInteger();

      this.executeCalls1(calls, callPath, theCallerBlockchainId);

      // Now call the segment call that called all of the other segments.
      RlpList segCall = (RlpList) calls.get(0);
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(BigInteger.ZERO);
      callSegmentsAndRoot(segCall, nextCallPath, callerBlockchainId);
    }
    else {
      if (callPath.size() == 1 && callPath.get(0).compareTo(BigInteger.ZERO) == 0) {
        this.executor.root();
      }
      else {
        RlpString blockchainIdRlp = (RlpString) calls.get(0);
        BigInteger blockchainId = blockchainIdRlp.asPositiveBigInteger();

        this.executor.segment(blockchainId, callerBlockchainId, callPath);
      }
    }
  }

  protected void callSegmentsAndRoot(CallExecutionTree callGraph, List<BigInteger> callPath, BigInteger callerBlockchainId) throws Exception {
    BigInteger thisCallsBcId = callGraph.getBlockchainId();
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

  private static BigInteger callRootBlockchainId(RlpList callGraph) {
    List<RlpType> calls = callGraph.getValues();
    RlpList rootCall = (RlpList)calls.get(0);
    RlpString bcIdRlp = (RlpString) rootCall.getValues().get(0);
    BigInteger rootBcId = bcIdRlp.asPositiveBigInteger();
    LOG.info("Root blockchain detected as: 0x{}", rootBcId.toString(16));
    return rootBcId;
  }

  private static BigInteger callRootBlockchainId(CallExecutionTree callGraph) {
    BigInteger rootBcId = callGraph.getBlockchainId();
    LOG.info("Root blockchain detected as: 0x{}", rootBcId.toString(16));
    return rootBcId;
  }

  protected abstract void executeCalls1(List<RlpType> calls, List<BigInteger> callPath, BigInteger theCallerBlockchainId) throws Exception;
  protected abstract void executeCalls(List<CallExecutionTree> calls, List<BigInteger> callPath, BigInteger theCallerBlockchainId) throws Exception;

}
