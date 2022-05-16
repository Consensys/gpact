package net.consensys.gpact.soliditywrappers.applications.gpact.erc20bridge;

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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class MockCbcForERC20Test extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061031b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806339ce107e146100675780637bf37a091461007c57806392b2c33514610092578063b4c3b756146100a6578063bd682e45146100b8578063ef3adc4a146100c0575b600080fd5b61007a6100753660046101e4565b6100d3565b005b6000546040519081526020015b60405180910390f35b61007a6100a0366004610206565b50505050565b60005460405190158152602001610089565b61007a610180565b61007a6100ce36600461028d565b600055565b60005b60015481101561012e57816001600160a01b0316600182815481106100fd576100fd6102a6565b6000918252602090912001546001600160a01b0316141561011c575050565b80610126816102bc565b9150506100d6565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b0392909216919091179055565b61018c6001600061018e565b565b50805460008255906000526020600020908101906101ac91906101af565b50565b5b808211156101c457600081556001016101b0565b5090565b80356001600160a01b03811681146101df57600080fd5b919050565b6000602082840312156101f657600080fd5b6101ff826101c8565b9392505050565b6000806000806060858703121561021c57600080fd5b8435935061022c602086016101c8565b9250604085013567ffffffffffffffff8082111561024957600080fd5b818701915087601f83011261025d57600080fd5b81358181111561026c57600080fd5b88602082850101111561027e57600080fd5b95989497505060200194505050565b60006020828403121561029f57600080fd5b5035919050565b634e487b7160e01b600052603260045260246000fd5b60006000198214156102de57634e487b7160e01b600052601160045260246000fd5b506001019056fea26469706673582212200d734715d024a0231d24a02b8e796a9aca19b58dc2fd1b27b18d15fa62f670bd64736f6c634300080a0033";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CLEARLISTOFLOCKEDCONTRACTS = "clearListOfLockedContracts";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_GETACTIVECALLCROSSCHAINROOTTXID = "getActiveCallCrosschainRootTxId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_SETCROSSCHAINROOTTXID = "setCrosschainRootTxId";

    @Deprecated
    protected MockCbcForERC20Test(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MockCbcForERC20Test(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MockCbcForERC20Test(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MockCbcForERC20Test(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addToListOfLockedContracts(String _contractToLock) {
        final Function function = new Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_addToListOfLockedContracts(String _contractToLock) {
        final Function function = new Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> clearListOfLockedContracts() {
        final Function function = new Function(
                FUNC_CLEARLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_clearListOfLockedContracts() {
        final Function function = new Function(
                FUNC_CLEARLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> getActiveCallCrosschainRootTxId() {
        final Function function = new Function(FUNC_GETACTIVECALLCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getABI_getActiveCallCrosschainRootTxId() {
        final Function function = new Function(
                FUNC_GETACTIVECALLCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isSingleBlockchainCall() {
        final Function function = new Function(FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getABI_isSingleBlockchainCall() {
        final Function function = new Function(
                FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCrosschainRootTxId(byte[] _txId) {
        final Function function = new Function(
                FUNC_SETCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_setCrosschainRootTxId(byte[] _txId) {
        final Function function = new Function(
                FUNC_SETCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static MockCbcForERC20Test load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForERC20Test(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MockCbcForERC20Test load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForERC20Test(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MockCbcForERC20Test load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MockCbcForERC20Test(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MockCbcForERC20Test load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MockCbcForERC20Test(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MockCbcForERC20Test> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForERC20Test.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForERC20Test> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForERC20Test.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MockCbcForERC20Test> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForERC20Test.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForERC20Test> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForERC20Test.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
