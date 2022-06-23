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
package net.consensys.gpact.functioncall.common;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;
import static net.consensys.gpact.common.crypto.Hash.keccak256;

import java.nio.ByteBuffer;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.functioncall.CallExecutionTree;
import org.apache.tuweni.bytes.Bytes;

public class CallExecutionTreeEncoderBase {
  protected static final byte ENCODING_FORMAT_V1 = 0;

  // Call tree with root and called functions.
  protected static final byte ENCODING_FORMAT_V2_SINGLE_LAYER = 1;
  // Call tree with root, called functions, and function called by the called
  // functions, to an arbitrary call depth.
  protected static final byte ENCODING_FORMAT_V2_MULTI_LAYER = 2;

  protected static final int NUM_FUNCS_CALLED_SIZE = 1;
  protected static final int OFFSET_SIZE = 4;
  protected static final int BLOCKCHAIN_ID_SIZE = 32;
  protected static final int ADDRESS_SIZE = 20;
  protected static final int DATA_LEN_SIZE_SIZE = 2;

  public static final byte LEAF_FUNCTION = 0;
  public static final int MAX_CALL_EX_TREE_SIZE = 1000000;
  public static final int MAX_CALLED_FUNCTIONS = 255;

  protected CallExecutionTreeEncoderBase() {
    // Block instantiation.
  }

  protected static byte[] encodeFunctionCall(final CallExecutionTree callTree) {
    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
    byte[] blockchainIdBytes = callTree.getBlockchainId().asBytes();
    byte[] address = addressStringToBytes(callTree.getContractAddress());
    buf.put(blockchainIdBytes);
    buf.put(address);
    byte[] data = FormatConversion.hexStringToByteArray(callTree.getFunctionCallData());
    buf.putShort((short) data.length);
    buf.put(data);
    buf.flip();
    byte[] output = new byte[buf.limit()];
    buf.get(output);
    return output;
  }

  public static byte[] encodeFunctionCallAndHash(final CallExecutionTree callTree) {
    byte[] encodedFunction = encodeFunctionCall(callTree);
    return keccak256(Bytes.wrap(encodedFunction)).toArray();
  }

  protected static void addSpaces(StringBuilder out, int level) {
    for (int j = 0; j < level; j++) {
      out.append(" ");
    }
  }

  protected static int uint16(final short val) {
    int valInt = val;
    if (valInt < 0) {
      valInt += (Short.MAX_VALUE + 1) * 2;
    }
    return valInt;
  }

  protected static int uint8(final byte val) {
    int valInt = val;
    if (valInt < 0) {
      valInt += (Byte.MAX_VALUE + 1) * 2;
    }
    return valInt;
  }
}
