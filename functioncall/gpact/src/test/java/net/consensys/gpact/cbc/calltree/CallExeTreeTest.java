package net.consensys.gpact.cbc.calltree;



import net.consensys.gpact.common.Tuple;

import java.math.BigInteger;

import static junit.framework.TestCase.assertEquals;


public class CallExeTreeTest extends CallExecutionTreeTestCommon {

    Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath) throws CallTreeException {
        return CallExecutionTree.extractFunction(encodedCallTree, callPath);
    }

}
