package net.consensys.gpact.functioncall.common;

import java.util.ArrayList;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class CallExecutionTreeV2TestCommon extends CallExecutionTreeTestCommon {
  abstract byte[] extractFunctionHash(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException;

  public void checkJavaEncodeDecode(final CallExecutionTree seg) throws CallExecutionTreeException {
    byte[] encoded = seg.encode(CallExecutionTree.ENCODING_V2);
    CallExecutionTree.dump(encoded);
  }

  @Test
  public void rootOneSeg() throws Exception {
    CallExecutionTree seg = new CallExecutionTree(blockchainId2, contract2, function2);
    ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
    rootCalls1.add(seg);
    CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
    checkJavaEncodeDecode(root);

    byte[] encoded = root.encode(CallExecutionTree.ENCODING_V2);
    byte[] funcHash = extractFunctionHash(encoded, new int[] {0});

    Assertions.assertArrayEquals(funcHash, root.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {1});
    Assertions.assertArrayEquals(funcHash, seg.getFunctionHash());
  }

  @Test
  public void rootTwoSeg() throws Exception {
    CallExecutionTree seg1 = new CallExecutionTree(blockchainId2, contract2, function2);
    CallExecutionTree seg2 = new CallExecutionTree(blockchainId3, contract3, function3);
    ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
    rootCalls1.add(seg1);
    rootCalls1.add(seg2);
    CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
    checkJavaEncodeDecode(root);

    byte[] encoded = root.encode(CallExecutionTree.ENCODING_V2);
    byte[] funcHash = extractFunctionHash(encoded, new int[] {0});
    Assertions.assertArrayEquals(funcHash, root.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {1});
    Assertions.assertArrayEquals(funcHash, seg1.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {2});
    Assertions.assertArrayEquals(funcHash, seg2.getFunctionHash());
  }

  @Test
  public void complex() throws Exception {
    CallExecutionTree seg1 = new CallExecutionTree(blockchainId2, contract2, function2);
    CallExecutionTree seg2 = new CallExecutionTree(blockchainId3, contract3, function3);

    CallExecutionTree seg3 = new CallExecutionTree(blockchainId5, contract5, function5);
    ArrayList<CallExecutionTree> seg4Calls = new ArrayList<>();
    seg4Calls.add(seg1);
    seg4Calls.add(seg2);
    CallExecutionTree seg4 = new CallExecutionTree(blockchainId4, contract4, function4, seg4Calls);
    ArrayList<CallExecutionTree> seg5Calls = new ArrayList<>();
    seg5Calls.add(seg4);
    CallExecutionTree seg5 = new CallExecutionTree(blockchainId6, contract6, function6, seg5Calls);
    CallExecutionTree seg6 = new CallExecutionTree(blockchainId7, contract7, function7);
    CallExecutionTree seg7 = new CallExecutionTree(blockchainId8, contract8, function8);

    ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
    rootCalls1.add(seg3);
    rootCalls1.add(seg5);
    rootCalls1.add(seg6);
    rootCalls1.add(seg7);
    CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
    checkJavaEncodeDecode(root);

    byte[] encoded = root.encode(CallExecutionTree.ENCODING_V2);
    byte[] funcHash = extractFunctionHash(encoded, new int[] {0});
    Assertions.assertArrayEquals(funcHash, root.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {1});
    Assertions.assertArrayEquals(funcHash, seg3.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {2, 0});
    Assertions.assertArrayEquals(funcHash, seg5.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {2, 1, 0});
    Assertions.assertArrayEquals(funcHash, seg4.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {2, 1, 1});
    Assertions.assertArrayEquals(funcHash, seg1.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {2, 1, 2});
    Assertions.assertArrayEquals(funcHash, seg2.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {3});
    Assertions.assertArrayEquals(funcHash, seg6.getFunctionHash());

    funcHash = extractFunctionHash(encoded, new int[] {4});
    Assertions.assertArrayEquals(funcHash, seg7.getFunctionHash());
  }
}
