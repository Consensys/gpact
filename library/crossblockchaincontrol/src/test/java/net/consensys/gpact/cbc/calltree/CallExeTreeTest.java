package net.consensys.gpact.cbc.calltree;



import net.consensys.gpact.common.Tuple;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;


public class CallExeTreeTest extends CallExecutionTreeTestCommon {

    Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath) throws CallTreeException {
        return CallExecutionTree.extractFunction(encodedCallTree, callPath);
    }

}
