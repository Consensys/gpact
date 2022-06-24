package net.consensys.gpact.functioncall.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CallExecutionTreeException;
import net.consensys.gpact.functioncall.gpact.CallExecutionTreeV1Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.web3j.tuples.generated.Tuple3;

public class SolidityCallExecutionTreeV1Test extends CallExecutionTreeV1TestCommon {
  static final Logger LOG = LogManager.getLogger(SolidityCallExecutionTreeV1Test.class);

  CallExecutionTreeV1Test callExecutionTreeContract;

  Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath)
      throws CallExecutionTreeException {
    List<BigInteger> callPathB = new ArrayList<>();
    for (int callPathElement : callPath) {
      callPathB.add(BigInteger.valueOf(callPathElement));
    }

    try {
      Tuple3<BigInteger, String, byte[]> result =
          this.callExecutionTreeContract
              .extractTargetFromCallGraph1(encodedCallTree, callPathB)
              .send();
      return new Tuple<BigInteger, String, String>(
          result.component1(),
          result.component2(),
          FormatConversion.byteArrayToString(result.component3()));
    } catch (Exception ex) {
      throw new CallExecutionTreeException(ex.toString());
    }
  }

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    this.callExecutionTreeContract =
        CallExecutionTreeV1Test.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }
}
