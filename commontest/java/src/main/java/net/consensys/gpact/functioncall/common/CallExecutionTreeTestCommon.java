package net.consensys.gpact.functioncall.common;

import java.math.BigInteger;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
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

  abstract void checkJavaEncodeDecode(final CallExecutionTree seg)
      throws CallExecutionTreeException;

  @Test
  public void singleFunc() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, function1);
    checkJavaEncodeDecode(seg);
  }

  @Test
  public void singleFuncNoFuncData() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, noFuncData);
    checkJavaEncodeDecode(seg);
  }

  @Test
  public void singleFuncOnlyFunctionSelector() throws CallExecutionTreeException {
    CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, onlyFunctionSelection);
    checkJavaEncodeDecode(seg);
  }
}
