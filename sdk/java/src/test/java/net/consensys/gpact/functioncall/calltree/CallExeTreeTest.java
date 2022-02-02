package net.consensys.gpact.functioncall.calltree;

import java.math.BigInteger;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CallExecutionTreeException;

public class CallExeTreeTest extends CallExecutionTreeTestCommon {

  Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException {
    return CallExecutionTree.extractFunction(encodedCallTree, callPath);
  }
}
