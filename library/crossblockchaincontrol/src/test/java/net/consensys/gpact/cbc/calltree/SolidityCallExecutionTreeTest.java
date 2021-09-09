package net.consensys.gpact.cbc.calltree;


import net.consensys.gpact.cbc.calltree.soliditywrappers.CallExecutionTreeTest;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.common.Tuple;
import org.junit.Before;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class SolidityCallExecutionTreeTest extends CallExecutionTreeTestCommon {

    CallExecutionTreeTest callExecutionTreeContract;

    Tuple<BigInteger, String, String> extractFunction(byte[] encodedCallTree, int[] callPath) throws CallTreeException {
        List<BigInteger> callPathB = new ArrayList<>();
        for (int callPathElement: callPath) {
            callPathB.add(BigInteger.valueOf(callPathElement));
        }

        try {
            Tuple3<BigInteger, String, byte[]> result =
                this.callExecutionTreeContract.extractTargetFromCallGraph1(encodedCallTree, callPathB).send();
            return new Tuple<BigInteger, String, String>(
                    result.component1(),
                    result.component2(),
                    FormatConversion.byteArrayToString(result.component3()));
        } catch (Exception ex) {
            throw new CallTreeException(ex.toString());
        }
    }


    @Before
    public void setup() throws Exception {
        setupWeb3();
        this.callExecutionTreeContract = CallExecutionTreeTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    }

}
