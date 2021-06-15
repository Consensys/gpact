package net.consensys.gpact.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
public class LockableStorageWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161012338038061012383398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6091806100926000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c806311ce026714602d575b600080fd5b600054603f906001600160a01b031681565b6040516001600160a01b03909116815260200160405180910390f3fea2646970667358221220edcabd318da4b9d8d1f999db5a1f71385f4d4ce3bbb4b993cc4c9045ccdb491664736f6c63430008050033";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    @Deprecated
    protected LockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> storageContract() {
        final Function function = new Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorageWrapper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorageWrapper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LockableStorageWrapper(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LockableStorageWrapper(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
