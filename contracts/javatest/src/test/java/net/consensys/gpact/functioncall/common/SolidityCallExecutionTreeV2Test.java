package net.consensys.gpact.functioncall.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
import net.consensys.gpact.functioncall.gpact.CallExecutionTreeV2Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;

public class SolidityCallExecutionTreeV2Test extends CallExecutionTreeV2TestCommon {
  static final Logger LOG = LogManager.getLogger(SolidityCallExecutionTreeV2Test.class);

  CallExecutionTreeV2Test callExecutionTreeContract;

  byte[] extractFunctionHash(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException {
    List<BigInteger> callPathB = new ArrayList<>();
    for (int callPathElement : callPath) {
      callPathB.add(BigInteger.valueOf(callPathElement));
    }

    try {
      return this.callExecutionTreeContract
          .extractTargetHashFromCallGraph1(encodedCallTree, callPathB)
          .send();
    } catch (Exception ex) {
      throw new CallExecutionTreeException(ex.toString());
    }
  }

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    this.callExecutionTreeContract =
        CallExecutionTreeV2Test.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }
}
