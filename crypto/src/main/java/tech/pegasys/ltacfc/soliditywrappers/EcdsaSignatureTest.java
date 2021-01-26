package tech.pegasys.ltacfc.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class EcdsaSignatureTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610364806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063c976588c1461003b578063f3a2281d14610121575b600080fd5b61010d6004803603606081101561005157600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561007c57600080fd5b82018360208201111561008e57600080fd5b803590602001918460018302840111640100000000831117156100b057600080fd5b9193909290916020810190356401000000008111156100ce57600080fd5b8201836020820111156100e057600080fd5b8035906020019184600183028401116401000000008311171561010257600080fd5b5090925090506101b0565b604080519115158252519081900360200190f35b61010d600480360360a081101561013757600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561016257600080fd5b82018360208201111561017457600080fd5b8035906020019184600183028401116401000000008311171561019657600080fd5b91935091508035906020810135906040013560ff166101cb565b60006101bf86868686866101e8565b90505b95945050505050565b60006101db878787878787610267565b90505b9695505050505050565b6000604182146101fa575060006101c2565b606083838080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201829052506020850151604086015160608701519697509095909450901a915061025990508a8a8a868686610267565b9a9950505050505050505050565b600080868660405180838380828437604051920182900390912094505050505060ff8316601b1480159061029f57508260ff16601c14155b156102ae5760009150506101de565b60018184878760405160008152602001604052604051808581526020018460ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa158015610308573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150506101de56fea2646970667358221220bc9f8d572c511c9d70d32bf29fbfe304c5294c35bb3eddcc3883b6a13c18542264736f6c63430007040033";

    public static final String FUNC_VERIFY2 = "verify2";

    public static final String FUNC_VERIFYSIGCOMPONENTS2 = "verifySigComponents2";

    @Deprecated
    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> verify2(String _signer, byte[] _message, byte[] _signature) {
        final Function function = new Function(FUNC_VERIFY2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_verify2(String _signer, byte[] _message, byte[] _signature) {
        final Function function = new Function(
                FUNC_VERIFY2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> verifySigComponents2(String _signer, byte[] _message, byte[] _sigR, byte[] _sigS, BigInteger _sigV) {
        final Function function = new Function(FUNC_VERIFYSIGCOMPONENTS2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.generated.Bytes32(_sigR), 
                new org.web3j.abi.datatypes.generated.Bytes32(_sigS), 
                new org.web3j.abi.datatypes.generated.Uint8(_sigV)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_verifySigComponents2(String _signer, byte[] _message, byte[] _sigR, byte[] _sigS, BigInteger _sigV) {
        final Function function = new Function(
                FUNC_VERIFYSIGCOMPONENTS2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.generated.Bytes32(_sigR), 
                new org.web3j.abi.datatypes.generated.Bytes32(_sigS), 
                new org.web3j.abi.datatypes.generated.Uint8(_sigV)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EcdsaSignatureTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EcdsaSignatureTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EcdsaSignatureTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EcdsaSignatureTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
