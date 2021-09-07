package net.consensys.gpact.cbc.calltree;



import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

public class CallExecutionTreeTest {
    public static byte[] BIG32 = new byte[] {
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
            0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
            0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18,
            0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x01};
    BigInteger blockchainId1 = new BigInteger(BIG32);
    BigInteger blockchainId2 = blockchainId1.add(BigInteger.ONE);
    BigInteger blockchainId3 = blockchainId2.add(BigInteger.ONE);
    BigInteger blockchainId4 = blockchainId3.add(BigInteger.ONE);
    BigInteger blockchainId5 = blockchainId4.add(BigInteger.ONE);
    BigInteger blockchainId6 = blockchainId5.add(BigInteger.ONE);
    BigInteger blockchainId7 = blockchainId6.add(BigInteger.ONE);
    BigInteger blockchainId8 = blockchainId7.add(BigInteger.ONE);

    public static String BIG19 = "0102030405060708090A0B0C0D0E0F10111213";
    String contract1 = BIG19 + "01";
    String contract2 = BIG19 + "02";
    String contract3 = BIG19 + "03";
    String contract4 = BIG19 + "04";
    String contract5 = BIG19 + "05";
    String contract6 = BIG19 + "06";
    String contract7 = BIG19 + "07";
    String contract8 = BIG19 + "08";

    String noFuncData = "";
    String onlyFunctionSelection = "31323334";
    String function1 = "410203040A";
    String function2 = "410203040A010203040506070809AABB";
    String function3 = "410203040A010203040506070809AABC";
    String function4 = "410203040A010203040506070809AABD";
    String function5 = "410203040A010203040506070809AA02";
    String function6 = "410203040A010203040506070809AA03";
    String function7 = "410203040A010203040506070809AA05";
    String function8 = "410203040A010203040506070809AA09";


    @Test
    public void singleFunc() throws CallTreeException {
        CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, function1);
        seg.dump();
    }
    @Test
    public void singleFuncNoFuncData() throws CallTreeException {
        CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, noFuncData);
        seg.dump();
    }
    @Test
    public void singleFuncOnlyFunctionSelector() throws CallTreeException {
        CallExecutionTree seg = new CallExecutionTree(blockchainId1, contract1, onlyFunctionSelection);
        seg.dump();
    }

    @Test
    public void rootOneSeg() throws Exception {
        CallExecutionTree seg = new CallExecutionTree(blockchainId2, contract2, function2);
        ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
        rootCalls1.add(seg);
        CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
        root.dump();
    }

    @Test
    public void rootTwoSeg() throws Exception {
        CallExecutionTree seg1 = new CallExecutionTree(blockchainId2, contract2, function2);
        CallExecutionTree seg2 = new CallExecutionTree(blockchainId3, contract3, function3);
        ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
        rootCalls1.add(seg1);
        rootCalls1.add(seg2);
        CallExecutionTree root = new CallExecutionTree(blockchainId1, contract1, function1, rootCalls1);
        root.dump();
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
        root.dump();
    }

}
