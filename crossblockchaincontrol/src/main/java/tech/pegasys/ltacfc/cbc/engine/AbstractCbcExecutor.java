/*
 * Copyright 2020 ConsenSys AG.
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
package tech.pegasys.ltacfc.cbc.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensusType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(AbstractCbcExecutor.class);

  // The maximum number of calls that can be done from any one function. The value
  // has been set to an aritrarily largish number. If people write complicated
  // functions that have a 1000 calls, or write functions that have loops and
  // do many cross-blockchain function calls, then this number might need to be made larger.
  protected static final BigInteger MAX_CALLS_FROM_ONE_FUNCTION = BigInteger.valueOf(1000);

  protected static final BigInteger ROOT_CALL_MAP_KEY = calculateRootCallMapKey();
  private static BigInteger calculateRootCallMapKey() {
    List<BigInteger> rootCallPath = new ArrayList<BigInteger>();
    rootCallPath.add(BigInteger.ZERO);
    return callPathToMapKey(rootCallPath);
  }

  protected Map<BigInteger, TransactionReceipt> transactionReceipts = new HashMap<>();

  private CrossBlockchainConsensusType consensusMethodology;

  CbcManager cbcManager;


  protected byte[] callGraph;

  protected BigInteger timeout;
  protected BigInteger crossBlockchainTransactionId;
  protected BigInteger rootBcId;

  boolean success = false;


  public AbstractCbcExecutor(CrossBlockchainConsensusType consensusMethodology, CbcManager cbcManager) {
    this.consensusMethodology = consensusMethodology;
    this.cbcManager = cbcManager;
  }


  public void init(byte[] callGraph, BigInteger timeout, BigInteger transactionId, BigInteger rootBcId) {
    this.callGraph = callGraph;
    this.timeout = timeout;
    this.crossBlockchainTransactionId = transactionId;
    this.rootBcId = rootBcId;
  }

  protected abstract void startCall()throws Exception;

  protected abstract void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception;

  protected abstract void root() throws Exception;

  protected abstract void doSignallingCalls() throws Exception;

  public boolean getRootEventSuccess() {
    return this.success;
  }


  public TransactionReceipt getTransationReceipt(List<BigInteger> callPath) {
    return this.transactionReceipts.get(callPathToMapKey(callPath));
  }

  /**
   * Determine a key that can be used for a map that uniquely identifies the call path's
   * caller.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  protected BigInteger determineMapKeyOfCaller(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      return BigInteger.ZERO;
    }
    else {
      List<BigInteger> parentCallPath = new ArrayList<>(callPath);

      BigInteger bottomOfCallPath = callPath.get(callPath.size() - 1);
      if (bottomOfCallPath.compareTo(BigInteger.ZERO) == 0) {
        parentCallPath.remove(parentCallPath.size() - 1);
      }
      parentCallPath.set(parentCallPath.size() - 1, BigInteger.ZERO);

      return callPathToMapKey(parentCallPath);
    }
  }



  /**
   * Determine a key that can be used for a map that uniquely identifies the call path.
   * A message digest of the call path could be used, but a simpler multiplication method
   * will work just as well.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  protected static BigInteger callPathToMapKey(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      throw new RuntimeException("Invalid call path length: " + callPath.size());
    }
    else {
      BigInteger key = BigInteger.ONE;
      for (BigInteger call: callPath) {
        if (call.compareTo(MAX_CALLS_FROM_ONE_FUNCTION) >= 0) {
          throw new RuntimeException("Maximum calls from one function is: " + MAX_CALLS_FROM_ONE_FUNCTION);
        }

        key = key.multiply(MAX_CALLS_FROM_ONE_FUNCTION);
        key = key.add(call.add(BigInteger.ONE));
      }
      return key;
    }
  }
}
