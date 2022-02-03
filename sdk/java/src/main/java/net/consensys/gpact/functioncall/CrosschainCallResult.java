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
package net.consensys.gpact.functioncall;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface CrosschainCallResult {
  // ROOT_CALL is the call path to the root function call.
  List<BigInteger> ROOT_CALL = createRootCall();

  static List<BigInteger> createRootCall() {
    List<BigInteger> callPath = new ArrayList<>();
    callPath.add(BigInteger.ZERO);
    return callPath;
  }

  // FIRST_CALL is the call path of the first function called by the root function,
  // assuming a simple root and one other level call execution tree. For SFC, this
  // is the function called by the ROOT_CALL.
  List<BigInteger> FIRST_CALL = createFirstCall();

  static List<BigInteger> createFirstCall() {
    List<BigInteger> callPath = new ArrayList<>();
    callPath.add(BigInteger.ONE);
    return callPath;
  }

  CallExecutionTree getCallTree();

  boolean isSuccessful();

  /**
   * Return the transaction receipt for part of the call path. This allows applications to see what
   * events have been emitted across the call execution tree.
   *
   * @param callPath Part of the call execution tree to get the transaction receipt for.
   * @return the transaction receipt that was returned with callPath part of the call execution tree
   *     was executed.
   */
  TransactionReceipt getTransactionReceipt(List<BigInteger> callPath);

  /**
   * Additional error information.
   *
   * @return Either null or some additional error information.
   */
  String getAdditionalErrorInfo();
}
