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
package net.consensys.gpact.functioncall.common;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;

public class CallExecutionTreeEncoderV2 extends CallExecutionTreeEncoderBase {

  private static final int HASH_SIZE = 32;

  private CallExecutionTreeEncoderV2() {
    // Block instantiation.
  }

  public static byte[] encode(final CallExecutionTree callTree) throws CallExecutionTreeException {
    if (isSingleLayer(callTree)) {
      return encodeSingleLayer(callTree);
    }
    // Encode multi-layer.
    return encodeMultiLayer(callTree);
  }

  private static byte[] encodeSingleLayer(CallExecutionTree function)
      throws CallExecutionTreeException {
    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
    buf.put(ENCODING_FORMAT_V2_SINGLE_LAYER);

    List<CallExecutionTree> calledFunctions = function.getCalledFunctions();

    List<byte[]> encodedCalledFunctionHashes = new ArrayList<>();
    encodedCalledFunctionHashes.add(encodeFunctionCallAndHash(function));

    int numCalledFunctions = 0;
    if (calledFunctions != null) {
      numCalledFunctions = calledFunctions.size();
      for (CallExecutionTree func : calledFunctions) {
        encodedCalledFunctionHashes.add(encodeFunctionCallAndHash(func));
      }
    }

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

  private static byte[] encodeMultiLayer(CallExecutionTree function)
      throws CallExecutionTreeException {
    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
    buf.put(ENCODING_FORMAT_V2_MULTI_LAYER);
    buf.put(encodeRecursive(function));
    buf.flip();
    byte[] output = new byte[buf.limit()];
    buf.get(output);
    return output;
  }

  private static byte[] encodeRecursive(CallExecutionTree function)
      throws CallExecutionTreeException {
    List<CallExecutionTree> calledFunctions = function.getCalledFunctions();

    ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);

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
      case ENCODING_FORMAT_V2_SINGLE_LAYER:
        size = processSingleLayer(out, buf);
        break;
      case ENCODING_FORMAT_V2_MULTI_LAYER:
        size = processRecursive(out, buf, 0) + 1; // the 1 accounts for the size of the type field
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
    int size = 1;
    size += NUM_FUNCS_CALLED_SIZE;
    int numFuncsCalled = uint8(buf.get());

    for (int i = 0; i < numFuncsCalled + 1; i++) {
      size += decodeFunctionHash(out, buf, 0);
    }
    return size;
  }

  private static int processRecursive(StringBuilder out, ByteBuffer buf, int level)
      throws CallExecutionTreeException {
    int size = 0;
    size += NUM_FUNCS_CALLED_SIZE;
    int numFuncsCalled = uint8(buf.get());
    if (out != null) {
      addSpaces(out, level);
      out.append("NumFuncsCalled: ");
      out.append(numFuncsCalled);
      out.append("\n");
    }

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

      int used = decodeFunctionHash(out, buf, level);
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
      size += decodeFunctionHash(out, buf, level);
    }
    return size;
  }

  /**
   * Given a call path, extract the blockchain id, address and call data. NOTE: This code will only
   * work for call trees with at least two functions in them.
   *
   * @param callExecutionTree Call tree to extract function call from
   */
  public static byte[] extractFunctionHash(byte[] callExecutionTree, int[] callPath)
      throws CallExecutionTreeException {
    ByteBuffer buf = ByteBuffer.wrap(callExecutionTree);
    int encodingType = uint8(buf.get());
    switch (encodingType) {
      case ENCODING_FORMAT_V2_SINGLE_LAYER:
        return extractFunctionHashSingleLayer(buf, callPath);
      case ENCODING_FORMAT_V2_MULTI_LAYER:
        return extractFunctionHashMultiLayer(buf, callPath);
      default:
        throw new CallExecutionTreeException("Unknown encoding type: " + encodingType);
    }
  }

  public static byte[] extractFunctionHashSingleLayer(ByteBuffer buf, int[] callPath)
      throws CallExecutionTreeException {
    if (callPath.length != 1) {
      throw new CallExecutionTreeException("Single layer call path not length 1");
    }

    int numFuncsCalled = uint8(buf.get());
    int funcCalled = callPath[0];
    if (funcCalled > numFuncsCalled) {
      throw new CallExecutionTreeException("Invalid call path: Call Path offset too high");
    }

    buf.position(buf.position() + HASH_SIZE * funcCalled);

    byte[] hash = new byte[HASH_SIZE];
    buf.get(hash);
    return hash;
  }

  public static byte[] extractFunctionHashMultiLayer(ByteBuffer buf, int[] callPath)
      throws CallExecutionTreeException {

    int index = 0;

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
    byte[] hash = new byte[HASH_SIZE];
    buf.get(hash);
    return hash;
  }

  private static int decodeFunctionHash(StringBuilder out, ByteBuffer buf, int level)
      throws CallExecutionTreeException {
    byte[] hash = new byte[HASH_SIZE];
    buf.get(hash);
    String hashStr = FormatConversion.byteArrayToString(hash);
    out.append("Function Hash: ");
    out.append(hashStr);
    out.append("\n");
    return HASH_SIZE;
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

  public static boolean isV2Encoded(final byte[] encodedCallExecutionTree) {
    ByteBuffer buf = ByteBuffer.wrap(encodedCallExecutionTree);

    byte encodingFormat = buf.get();
    return (encodingFormat == ENCODING_FORMAT_V2_MULTI_LAYER)
        || (encodingFormat == ENCODING_FORMAT_V2_SINGLE_LAYER);
  }
}
