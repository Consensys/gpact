package net.consensys.gpact.test.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class EcdsaSignatureTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610395806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063c976588c1461003b578063f3a2281d14610062575b600080fd5b61004e6100493660046102ce565b610075565b604051901515815260200160405180910390f35b61004e61007036600461024e565b610090565b600061008486868686866100ad565b90505b95945050505050565b60006100a087878787878761012c565b90505b9695505050505050565b6000604182146100bf57506000610087565b600083838080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201829052506020850151604086015160608701519697509095909450901a915061011e90508a8a8a86868661012c565b9a9950505050505050505050565b600080868660405161013f92919061034f565b604051809103902090508260ff16601b1415801561016157508260ff16601c14155b156101705760009150506100a3565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa1580156101c3573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150506100a3565b80356001600160a01b038116811461020057600080fd5b919050565b60008083601f84011261021757600080fd5b50813567ffffffffffffffff81111561022f57600080fd5b60208301915083602082850101111561024757600080fd5b9250929050565b60008060008060008060a0878903121561026757600080fd5b610270876101e9565b9550602087013567ffffffffffffffff81111561028c57600080fd5b61029889828a01610205565b9096509450506040870135925060608701359150608087013560ff811681146102c057600080fd5b809150509295509295509295565b6000806000806000606086880312156102e657600080fd5b6102ef866101e9565b9450602086013567ffffffffffffffff8082111561030c57600080fd5b61031889838a01610205565b9096509450604088013591508082111561033157600080fd5b5061033e88828901610205565b969995985093965092949392505050565b818382376000910190815291905056fea26469706673582212202507da46adbd4183d948fcc54db5801294098377303eb96b1085154e6b7dc2f564736f6c63430008050033";

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
