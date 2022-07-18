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
package net.consensys.gpact.functioncall;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.functioncall.common.CallExecutionTreeEncoderBase;
import net.consensys.gpact.functioncall.common.CallExecutionTreeEncoderV1;
import net.consensys.gpact.functioncall.common.CallExecutionTreeEncoderV2;

/** Represents a part or a whole call execution tree. */
public class CallExecutionTree {
  public static final int ENCODING_V1 = 0;
  public static final int ENCODING_V2 = 1;

  private final BlockchainId blockchainId;
  private final String contractAddress;
  private final String functionCallData;
  // In order list of functions called by this function.
  private ArrayList<CallExecutionTree> calledFunctions;

  /**
   * Create a call execution tree object that represents a leaf function. That is, a function that
   * doesn't call other functions.
   *
   * @param blockchainId Blockchain that this function call should execute on.
   * @param contractAddress Address of contract to execute function in.
   * @param functionCallData ABI encoded function selector and parameter data.
   */
  public CallExecutionTree(
      final BlockchainId blockchainId,
      final String contractAddress,
      final String functionCallData) {
    this.blockchainId = blockchainId;
    this.contractAddress = contractAddress;
    this.functionCallData = functionCallData;
  }

  /**
   * Create a call execution tree object that represents a function that calls one or more other
   * functions.
   *
   * @param blockchainId Blockchain that this function call should execute on.
   * @param contractAddress Address of contract to execute function in.
   * @param functionCallData ABI encoded function selector and parameter data.
   * @param calledFunctions In order list of functions called from this function.
   */
  public CallExecutionTree(
      final BlockchainId blockchainId,
      final String contractAddress,
      final String functionCallData,
      final ArrayList<CallExecutionTree> calledFunctions) {
    this(blockchainId, contractAddress, functionCallData);
    this.calledFunctions = calledFunctions;
  }

  /** @return Blockchain that this function call should execute on. */
  public BlockchainId getBlockchainId() {
    return blockchainId;
  }

  /** @return Address of contract to execute function in. */
  public String getContractAddress() {
    return contractAddress;
  }

  /** @return ABI encoded function selector and parameter data. */
  public String getFunctionCallData() {
    return functionCallData;
  }

  /** @return ABI encoded function selector and parameter data. */
  public byte[] getFunctionCallDataAsBytes() {
    return FormatConversion.hexStringToByteArray(this.functionCallData);
  }

  /** @return In order list of functions called from the function represented by this object. */
  public ArrayList<CallExecutionTree> getCalledFunctions() {
    return calledFunctions;
  }

  /** @return The number of functions called by this function. */
  public int getNumCalledFunctions() {
    return this.calledFunctions.size();
  }

  /** @return true if this function does not call any other function. */
  public boolean isLeaf() {
    return this.calledFunctions == null || this.calledFunctions.isEmpty();
  }

  public byte[] getFunctionHash() {
    return CallExecutionTreeEncoderBase.encodeFunctionCallAndHash(this);
  }

  /**
   * Locate the CallExecutionTree object that matches the call path.
   *
   * @param callPath Call path of object to fetch.
   * @return CallExecutionTree object of call path.
   */
  public CallExecutionTree fetchFunctionCall(final List<BigInteger> callPath) {
    List<BigInteger> realCallPath = new ArrayList<>(callPath);

    // If the call path ends in zero, remove the last element. For instance, [3, 4, 0] becomes [3,
    // 4].
    int lastElement = realCallPath.size() - 1;
    if (realCallPath.get(lastElement).compareTo(BigInteger.ZERO) == 0) {
      realCallPath.remove(lastElement);
    }

    CallExecutionTree found = this;
    for (BigInteger callPathElement : realCallPath) {
      int callPathOffet = callPathElement.intValue() - 1;
      found = found.calledFunctions.get(callPathOffet);
    }
    return found;
  }

  /**
   * Encode the call execution tree using ENCODING_V1 format.
   *
   * @return binary call execution tree.
   * @throws CallExecutionTreeException If an error occurs processing the call execution tree.
   */
  public byte[] encode() throws CallExecutionTreeException {
    return encode(ENCODING_V1);
  }

  /**
   * Encode the call execution tree.
   *
   * @param encodingVersion Version of the encoding format to use.
   * @return binary call execution tree.
   * @throws CallExecutionTreeException If an error occurs processing the call execution tree.
   */
  public byte[] encode(final int encodingVersion) throws CallExecutionTreeException {
    switch (encodingVersion) {
      case ENCODING_V1:
        return CallExecutionTreeEncoderV1.encode(this);
      case ENCODING_V2:
        return CallExecutionTreeEncoderV2.encode(this);
      default:
        throw new CallExecutionTreeException("Unknown encoding version");
    }
  }

  @Override
  public String toString() {
    try {
      return dump(encode());
    } catch (CallExecutionTreeException ex) {
      return ex.getMessage();
    }
  }

  /**
   * Dump an encoded Call Execution Tree.
   *
   * @param encodedCallExecutionTree Tree to be processed.
   */
  public static String dump(byte[] encodedCallExecutionTree) throws CallExecutionTreeException {
    if (CallExecutionTreeEncoderV1.isV1Encoded(encodedCallExecutionTree)) {
      return CallExecutionTreeEncoderV1.dump(encodedCallExecutionTree);
    } else if (CallExecutionTreeEncoderV2.isV2Encoded(encodedCallExecutionTree)) {
      return CallExecutionTreeEncoderV2.dump(encodedCallExecutionTree);
    }
    return "Unknown Call Execution Tree encoding format";
  }

  /**
   * Verify that a byte array is a valid encoded call execution tree.
   *
   * @param encodedCallExecutionTree Encoded call execution tree.
   * @throws CallExecutionTreeException Thrown if the encoded data is invalid.
   */
  public static void verify(byte[] encodedCallExecutionTree) throws CallExecutionTreeException {
    if (CallExecutionTreeEncoderV1.isV1Encoded(encodedCallExecutionTree)) {
      CallExecutionTreeEncoderV1.verify(encodedCallExecutionTree);
    } else if (CallExecutionTreeEncoderV2.isV2Encoded(encodedCallExecutionTree)) {
      CallExecutionTreeEncoderV2.verify(encodedCallExecutionTree);
    }
    throw new CallExecutionTreeException("Unknown Call Execution Tree encoding format");
  }
}
