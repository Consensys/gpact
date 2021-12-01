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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class MockCbcForLockableStorageTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506102f6806100206000396000f3fe608060405234801561001057600080fd5b5060043610610073577c0100000000000000000000000000000000000000000000000000000000600035046339ce107e81146100785780637bf37a091461008d578063b4c3b756146100a3578063bd682e45146100b5578063ef3adc4a146100bd575b600080fd5b61008b6100863660046101f9565b6100d0565b005b6000546040519081526020015b60405180910390f35b6000546040519015815260200161009a565b61008b6101b1565b61008b6100cb366004610236565b600055565b60005b600154811015610145578173ffffffffffffffffffffffffffffffffffffffff16600182815481106101075761010761024f565b60009182526020909120015473ffffffffffffffffffffffffffffffffffffffff161415610133575050565b8061013d8161027e565b9150506100d3565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff92909216919091179055565b6101bd600160006101bf565b565b50805460008255906000526020600020908101906101dd91906101e0565b50565b5b808211156101f557600081556001016101e1565b5090565b60006020828403121561020b57600080fd5b813573ffffffffffffffffffffffffffffffffffffffff8116811461022f57600080fd5b9392505050565b60006020828403121561024857600080fd5b5035919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b60006000198214156102b9577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b506001019056fea2646970667358221220bcd63d2983740e935734135de0ed76742c9b5fc7e110c44cb38b7a9de706bebc64736f6c63430008090033";

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
