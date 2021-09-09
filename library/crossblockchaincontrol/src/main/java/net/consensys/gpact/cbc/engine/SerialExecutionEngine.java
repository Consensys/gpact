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
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SerialExecutionEngine extends AbstractExecutionEngine {
//  static final Logger LOG = LogManager.getLogger(SerialExecutionEngine.class);

  public SerialExecutionEngine(AbstractCbcExecutor executor) {
    super(executor);
  }

  protected void executeCalls1(List<RlpType> calls, List<BigInteger> callPath, BigInteger theCallerBlockchainId) throws Exception {
    BigInteger callOffset = BigInteger.ONE;
    for (int i = 1; i < calls.size(); i++) {
      RlpList segCall = (RlpList) calls.get(i);
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(callOffset);
      callSegmentsAndRoot(segCall, nextCallPath, theCallerBlockchainId);
      callOffset = callOffset.add(BigInteger.ONE);
    }
  }

  protected void executeCalls(List<CallExecutionTree> calls, List<BigInteger> callPath, BigInteger theCallerBlockchainId) throws Exception {
    BigInteger callOffset = BigInteger.ONE;
    for (int i = 1; i < calls.size(); i++) {
      CallExecutionTree segCall = calls.get(i);
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(callOffset);
      callSegmentsAndRoot(segCall, nextCallPath, theCallerBlockchainId);
      callOffset = callOffset.add(BigInteger.ONE);
    }
  }
}
