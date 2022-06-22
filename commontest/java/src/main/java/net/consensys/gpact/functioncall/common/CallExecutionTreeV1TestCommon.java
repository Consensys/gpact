package net.consensys.gpact.functioncall.common;

import java.math.BigInteger;
import java.util.ArrayList;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class CallExecutionTreeTestCommon extends AbstractWeb3Test {
  public static byte[] BIG32 =
      new byte[] {
        0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
        0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
        0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18,
        0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x01
      };
  BlockchainId blockchainId1 = new BlockchainId(new BigInteger(BIG32));
  BlockchainId blockchainId2 = new BlockchainId(blockchainId1.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId3 = new BlockchainId(blockchainId2.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId4 = new BlockchainId(blockchainId3.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId5 = new BlockchainId(blockchainId4.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId6 = new BlockchainId(blockchainId5.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId7 = new BlockchainId(blockchainId6.asBigInt().add(BigInteger.ONE));
  BlockchainId blockchainId8 = new BlockchainId(blockchainId7.asBigInt().add(BigInteger.ONE));

  public static String BIG19 = "0x0102030405060708090a0b0c0d0e0f10111213";
  String contract1 = BIG19 + "01";
  String contract2 = BIG19 + "02";
  String contract3 = BIG19 + "03";
  String contract4 = BIG19 + "04";
  String contract5 = BIG19 + "05";
  String contract6 = BIG19 + "06";
  String contract7 = BIG19 + "07";
  String contract8 = BIG19 + "08";

  String noFuncData = "";
  String onlyFunctionSelection = "0x31323334";
  String function1 = "0x410203040a";
  String function2 = "0x410203040a010203040506070809aabb";
  String function3 = "0x410203040a010203040506070809aabc";
  String function4 = "0x410203040a010203040506070809aabd";
  String function5 = "0x410203040a010203040506070809aa02";
  String function6 = "0x410203040a010203040506070809aa03";
  String function7 = "0x410203040a010203040506070809aa05";
  String function8 = "0x410203040a010203040506070809aa09";

  abstract Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException;

  @Test
  public void singleFunc() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, function1);
    seg.toString();
  }

  @Test
  public void singleFuncNoFuncData() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, noFuncData);
    seg.toString();
  }

  @Test
  public void singleFuncOnlyFunctionSelector() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, onlyFunctionSelection);
    seg.toString();
  }

  @Test
  public void rootOneSeg() throws Exception {
    CallExecutionTree seg = new CallExecutionTree(blockchainId2, contract2, function2);
    ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
    rootCalls1.add(seg);
    CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
    System.out.println(root.toString());

    byte[] encoded = root.encode();
    Tuple<BigInteger, String, String> func = extractFunction(encoded, new int[] {0});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId1.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract1);
    Assertions.assertEquals(func.getThird(), function1);

    func = extractFunction(encoded, new int[] {1});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId2.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract2);
    Assertions.assertEquals(func.getThird(), function2);
  }

  @Test
  public void rootTwoSeg() throws Exception {
    CallExecutionTree seg1 = new CallExecutionTree(blockchainId2, contract2, function2);
    CallExecutionTree seg2 = new CallExecutionTree(blockchainId3, contract3, function3);
    ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
    rootCalls1.add(seg1);
    rootCalls1.add(seg2);
    CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
    root.toString();

    byte[] encoded = root.encode();
    Tuple<BigInteger, String, String> func = extractFunction(encoded, new int[] {0});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId1.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract1);
    Assertions.assertEquals(func.getThird(), function1);

    func = extractFunction(encoded, new int[] {1});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId2.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract2);
    Assertions.assertEquals(func.getThird(), function2);

    func = extractFunction(encoded, new int[] {2});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId3.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract3);
    Assertions.assertEquals(func.getThird(), function3);
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
    root.toString();

    byte[] encoded = root.encode();
    Tuple<BigInteger, String, String> func = extractFunction(encoded, new int[] {0});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId1.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract1);
    Assertions.assertEquals(func.getThird(), function1);

    func = extractFunction(encoded, new int[] {1});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId5.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract5);
    Assertions.assertEquals(func.getThird(), function5);

    func = extractFunction(encoded, new int[] {2, 0});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId6.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract6);
    Assertions.assertEquals(func.getThird(), function6);

    func = extractFunction(encoded, new int[] {2, 1, 0});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId4.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract4);
    Assertions.assertEquals(func.getThird(), function4);

    func = extractFunction(encoded, new int[] {2, 1, 1});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId2.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract2);
    Assertions.assertEquals(func.getThird(), function2);

    func = extractFunction(encoded, new int[] {2, 1, 2});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId3.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract3);
    Assertions.assertEquals(func.getThird(), function3);

    func = extractFunction(encoded, new int[] {3});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId7.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract7);
    Assertions.assertEquals(func.getThird(), function7);

    func = extractFunction(encoded, new int[] {4});
    Assertions.assertEquals(func.getFirst().toString(16), blockchainId8.toPlainString());
    Assertions.assertEquals(func.getSecond(), contract8);
    Assertions.assertEquals(func.getThird(), function8);
  }
}
