package net.consensys.gpact.functioncall.gpact.calltree;

import java.math.BigInteger;
import net.consensys.gpact.common.Tuple;

public class CallExeTreeTest extends CallExecutionTreeTestCommon {

  Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath)
      throws CallTreeException {
    return CallExecutionTree.extractFunction(encodedCallTree, callPath);
  }
}
