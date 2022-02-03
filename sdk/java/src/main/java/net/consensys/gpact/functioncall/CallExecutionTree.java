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

import java.util.ArrayList;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.functioncall.common.CallExecutionTreeEncoderV1;

public class CallExecutionTree {
  public static final int ENCODING_V1 = 0;

  private BlockchainId blockchainId;
  private String contractAddress;
  private String functionCallData;
  // In order list of functions called by this function.
  private ArrayList<CallExecutionTree> calledFunctions;

  public CallExecutionTree(
      BlockchainId blockchainId, String contractAddress, String functionCallData) {
    this.blockchainId = blockchainId;
    this.contractAddress = contractAddress;
    this.functionCallData = functionCallData;
  }

  public CallExecutionTree(
      BlockchainId blockchainId,
      String contractAddress,
      String functionCallData,
      ArrayList<CallExecutionTree> calledFunctions) {
    this(blockchainId, contractAddress, functionCallData);
    this.calledFunctions = calledFunctions;
  }

  public BlockchainId getBlockchainId() {
    return blockchainId;
  }

  public String getContractAddress() {
    return contractAddress;
  }

  public String getFunctionCallData() {
    return functionCallData;
  }

  public ArrayList<CallExecutionTree> getCalledFunctions() {
    return calledFunctions;
  }

  public int getNumCalledFunctions() {
    return this.calledFunctions.size();
  }

  public boolean isLeaf() {
    return this.calledFunctions == null || this.calledFunctions.isEmpty();
  }

  public byte[] encode() throws CallExecutionTreeException {
    return encode(ENCODING_V1);
  }

  public byte[] encode(final int encodingVersion) throws CallExecutionTreeException {
    if (encodingVersion != ENCODING_V1) {
      throw new CallExecutionTreeException("Unknown encoding version");
    }
    return CallExecutionTreeEncoderV1.encode(this);
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
    // TODO need to be able to differentiate between v1 and other versions.
    return CallExecutionTreeEncoderV1.dump(encodedCallExecutionTree);
  }

  public static void verify(byte[] encodedCallExecutionTree) throws CallExecutionTreeException {
    // TODO need to be able to differentiate between v1 and other versions.
    CallExecutionTreeEncoderV1.verify(encodedCallExecutionTree);
  }
}
