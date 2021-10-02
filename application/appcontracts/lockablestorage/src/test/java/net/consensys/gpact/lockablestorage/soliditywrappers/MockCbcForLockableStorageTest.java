package net.consensys.gpact.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class MockCbcForLockableStorageTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610267806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806339ce107e1461005c5780637bf37a0914610071578063b4c3b75614610087578063bd682e4514610099578063ef3adc4a146100a1575b600080fd5b61006f61006a3660046101a9565b6100b4565b005b6000546040519081526020015b60405180910390f35b6000546040519015815260200161007e565b61006f610161565b61006f6100af3660046101d9565b600055565b60005b60015481101561010f57816001600160a01b0316600182815481106100de576100de61021b565b6000918252602090912001546001600160a01b031614156100fd575050565b80610107816101f2565b9150506100b7565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b0392909216919091179055565b61016d6001600061016f565b565b508054600082559060005260206000209081019061018d9190610190565b50565b5b808211156101a55760008155600101610191565b5090565b6000602082840312156101bb57600080fd5b81356001600160a01b03811681146101d257600080fd5b9392505050565b6000602082840312156101eb57600080fd5b5035919050565b600060001982141561021457634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fdfea264697066735822122060633bddca026db04ed1428eb0bb1cc543d2c50680970368bc49d9337592b18e64736f6c63430008050033";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CLEARLISTOFLOCKEDCONTRACTS = "clearListOfLockedContracts";

    public static final String FUNC_GETACTIVECALLCROSSCHAINROOTTXID = "getActiveCallCrosschainRootTxId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_SETCROSSCHAINROOTTXID = "setCrosschainRootTxId";

    @Deprecated
    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addToListOfLockedContracts(String _contractToLock) {
        final Function function = new Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> clearListOfLockedContracts() {
        final Function function = new Function(
                FUNC_CLEARLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> getActiveCallCrosschainRootTxId() {
        final Function function = new Function(FUNC_GETACTIVECALLCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> isSingleBlockchainCall() {
        final Function function = new Function(FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setCrosschainRootTxId(byte[] _txId) {
        final Function function = new Function(
                FUNC_SETCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
