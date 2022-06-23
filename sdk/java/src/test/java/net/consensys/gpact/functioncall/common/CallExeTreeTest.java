package net.consensys.gpact.functioncall.common;

import java.math.BigInteger;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CallExecutionTreeException;

public class CallExeTreeTest extends CallExecutionTreeV1TestCommon {

  Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException {
    return CallExecutionTreeEncoderV1.extractFunction(encodedCallTree, callPath);
  }
}
