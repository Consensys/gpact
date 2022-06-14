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

import static net.consensys.gpact.common.crypto.Hash.keccak256;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
import org.apache.tuweni.bytes.Bytes;

public class CallExecutionTreeEncoderV2 extends CallExecutionTreeEncoderBase {
  // Call tree with root and called functions.
  private static final byte SINGLE_LAYER = 0;
  // Call tree with root, called functions, and function called by the called
  // functions, to an arbitrary call depth.
  private static final byte MULTI_LAYER = 1;

  private static final int HASH_SIZE = 32;

  private CallExecutionTreeEncoderV2() {
    // Block instantiation.
  }

  public static byte[] encode(final CallExecutionTree callTree) throws CallExecutionTreeException {
    if (isSingleLayer(callTree)) {
      return encodeSingleLayer(callTree);
    }
    // Encode multi-layer.
    return encodeRecursive(callTree);
  }

  private static byte[] encodeSingleLayer(CallExecutionTree function)
      throws CallExecutionTreeException {
    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
    buf.put(SINGLE_LAYER);

    List<CallExecutionTree> calledFunctions = function.getCalledFunctions();

    List<byte[]> encodedCalledFunctionHashes = new ArrayList<>();
    encodedCalledFunctionHashes.add(encodeFunctionCallAndHash(function));
    for (CallExecutionTree func : calledFunctions) {
      encodedCalledFunctionHashes.add(encodeFunctionCallAndHash(func));
    }

    // Non-leaf function
    int numCalledFunctions = calledFunctions.size();
    if (numCalledFunctions > MAX_CALLED_FUNCTIONS) {
      throw new CallExecutionTreeException("Too many called functions: " + calledFunctions.size());
    }
    buf.put((byte) numCalledFunctions);
    for (byte[] encodedCalledFuncHash : encodedCalledFunctionHashes) {
      buf.put(encodedCalledFuncHash);
    }

    buf.flip();
    byte[] output = new byte[buf.limit()];
    buf.get(output);
    return output;
  }

  private static byte[] encodeRecursive(CallExecutionTree function)
      throws CallExecutionTreeException {
    List<CallExecutionTree> calledFunctions = function.getCalledFunctions();

    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
    buf.put(MULTI_LAYER);

    if (function.isLeaf()) {
      buf.put(LEAF_FUNCTION);
      buf.put(encodeFunctionCallAndHash(function));
    } else {
      List<byte[]> encodedCalledFunctions = new ArrayList<>();
      encodedCalledFunctions.add(encodeFunctionCallAndHash(function));
      for (CallExecutionTree func : calledFunctions) {
        encodedCalledFunctions.add(encodeRecursive(func));
      }

      // Non-leaf function
      int numCalledFunctions = calledFunctions.size();
      if (numCalledFunctions > MAX_CALLED_FUNCTIONS) {
        throw new CallExecutionTreeException(
            "Too many called functions: " + calledFunctions.size());
      }
      buf.put((byte) numCalledFunctions);
      int offset = (numCalledFunctions + 1) * OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE;
      for (byte[] encodedCalledFunc : encodedCalledFunctions) {
        buf.putInt(offset);
        offset += encodedCalledFunc.length;
      }
      for (byte[] encodedCalledFunc : encodedCalledFunctions) {
        buf.put(encodedCalledFunc);
      }
    }

    buf.flip();
    byte[] output = new byte[buf.limit()];
    buf.get(output);
    return output;
  }

  private static byte[] encodeFunctionCallAndHash(final CallExecutionTree callTree) {
    byte[] encodedFunction = encodeFunctionCall(callTree);
    return keccak256(Bytes.wrap(encodedFunction)).toArray();
  }

  /**
   * Dump an encoded Call Execution Tree.
   *
   * @param encodedCallExecutionTree Tree to be processed.
   */
  public static String dump(byte[] encodedCallExecutionTree) throws CallExecutionTreeException {
    StringBuilder out = new StringBuilder();

    out.append("Encoded Call Execution Tree: ");
    out.append(encodedCallExecutionTree.length);
    out.append(" bytes");
    out.append("\n");

    process(encodedCallExecutionTree, out);

    return out.toString();
  }

  public static void verify(byte[] encodedCallExecutionTree) throws CallExecutionTreeException {
    process(encodedCallExecutionTree, null);
  }

  /**
   * Dump an encoded Call Execution Tree.
   *
   * @param encodedCallExecutionTree Tree to be processed.
   */
  private static void process(final byte[] encodedCallExecutionTree, final StringBuilder out)
      throws CallExecutionTreeException {
    ByteBuffer buf = ByteBuffer.wrap(encodedCallExecutionTree);
    int encodingType = uint8(buf.get());
    int size = 0;
    switch (encodingType) {
      case SINGLE_LAYER:
        size = processSingleLayer(out, buf);
        break;
      case MULTI_LAYER:
        size = processRecursive(out, buf, 0);
        break;
      default:
        out.append("Unknown encoding type: ");
        out.append(encodingType);
        return;
    }

    if (buf.remaining() != 0) {
      throw new CallExecutionTreeException(
          "Not all bytes from Call Execution Tree processed: " + buf.remaining() + "remaining",
          out);
    }
    if (size != buf.capacity()) {
      throw new CallExecutionTreeException(
          "Not all bytes from Call Execution Tree processed: " + size + ", " + buf.capacity(), out);
    }
  }

  private static int processSingleLayer(StringBuilder out, ByteBuffer buf)
      throws CallExecutionTreeException {
    int size = 0;
    size += NUM_FUNCS_CALLED_SIZE;
    int numFuncsCalled = uint8(buf.get());

    for (int i = 0; i < numFuncsCalled + 1; i++) {
      byte[] hash = new byte[HASH_SIZE];
      buf.get(hash, size, HASH_SIZE);
      size += HASH_SIZE;
      String hashStr = FormatConversion.byteArrayToString(hash);
      out.append("Function Hash: ");
      out.append(hashStr);
      out.append("\n");
    }
    return size;
  }

  private static int processRecursive(StringBuilder out, ByteBuffer buf, int level)
      throws CallExecutionTreeException {
    int size = 0;
    size += NUM_FUNCS_CALLED_SIZE;
    int numFuncsCalled = uint8(buf.get());

    if (numFuncsCalled > 0) {
      int[] offsets = new int[numFuncsCalled + 1];
      for (int i = 0; i < numFuncsCalled + 1; i++) {
        size += OFFSET_SIZE;
        int offset = buf.getInt();
        offsets[i] = offset;
        if (out != null) {
          addSpaces(out, level);
          out.append("Offset of Function: ");
          out.append(offset);
          out.append("\n");
        }
      }

      int used = decodeFunction(out, buf, level);
      if (used != offsets[1] - offsets[0]) {
        throw new CallExecutionTreeException(
            "Function offsets "
                + offsets[0]
                + " and "
                + offsets[1]
                + " do not match the length "
                + used,
            out);
      }
      size += used;

      for (int i = 1; i < numFuncsCalled + 1; i++) {
        used = processRecursive(out, buf, level + 1);
        if (i < numFuncsCalled) {
          if (used != offsets[i + 1] - offsets[i]) {
            throw new CallExecutionTreeException(
                "Function offsets "
                    + offsets[i]
                    + " and "
                    + offsets[i + 1]
                    + " do not match the length "
                    + used,
                out);
          }
        }
        size += used;
      }
    } else {
      size += decodeFunction(out, buf, level);
    }
    return size;
  }

  /**
   * Given a call path, extract the blockchain id, address and call data. NOTE: This code will only
   * work for call trees with at least two functions in them.
   *
   * @param callExecutionTree Call tree to extract function call from
   */
  public static Tuple<BigInteger, String, String> extractFunction(
      byte[] callExecutionTree, int[] callPath) throws CallExecutionTreeException {
    int index = 0;
    ByteBuffer buf = ByteBuffer.wrap(callExecutionTree);

    for (int i = 0; i < callPath.length; i++) {
      buf.position(index);
      int offset = 0;
      int numFuncsCalled = uint8(buf.get());
      if (numFuncsCalled == 0) {
        if (i < callPath.length - 1) {
          throw new CallExecutionTreeException(
              "Reached leaf function but there is more call path.");
        }
      } else {
        // Jump to the location of the offset of the function
        int functionCalled = callPath[i];
        buf.position(index + functionCalled * OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE);
        offset = buf.getInt();
      }

      // Jump to the function
      index = index + offset;
    }

    if (callPath[callPath.length - 1] != 0) {
      buf.position(index);
      int numFuncsCalled = uint8(buf.get());
      if (numFuncsCalled != 0) {
        throw new CallExecutionTreeException("Expected leaf function.");
      }
      index++;
    }

    buf.position(index);
    byte[] blockchainId = new byte[BLOCKCHAIN_ID_SIZE];
    buf.get(blockchainId);

    byte[] address = new byte[ADDRESS_SIZE];
    buf.get(address);

    // Length is an unsigned short (uint16)
    int len = uint16(buf.getShort());
    if (len > buf.remaining()) {
      throw new CallExecutionTreeException(
          "Decoded length "
              + len
              + " bytes, longer than remaining space "
              + buf.remaining()
              + " bytes");
    }
    byte[] data = new byte[len];
    buf.get(data);

    BigInteger bcId = new BigInteger(1, blockchainId);
    String addr = FormatConversion.byteArrayToString(address);
    String funcData = FormatConversion.byteArrayToString(data);
    return new Tuple<>(bcId, addr, funcData);
  }

  private static boolean isSingleLayer(CallExecutionTree callTree) {
    ArrayList<CallExecutionTree> calledFunctions = callTree.getCalledFunctions();
    if (calledFunctions == null) {
      // In the situation where there is only a root function call, this can be represented
      // most simply in the single layer encoding.
      return true;
    }
    for (CallExecutionTree calledFunction : calledFunctions) {
      if (!calledFunction.isLeaf()) {
        return false;
      }
    }
    return true;
  }
}
