package net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers;

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
public class MockCbcForERC20Test extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506103aa806100206000396000f3fe608060405234801561001057600080fd5b506004361061007e577c0100000000000000000000000000000000000000000000000000000000600035046339ce107e81146100835780637bf37a091461009857806392b2c335146100ae578063b4c3b756146100c2578063bd682e45146100d4578063ef3adc4a146100dc575b600080fd5b610096610091366004610241565b6100ef565b005b6000546040519081526020015b60405180910390f35b6100966100bc366004610263565b50505050565b600054604051901581526020016100a5565b6100966101d0565b6100966100ea3660046102ea565b600055565b60005b600154811015610164578173ffffffffffffffffffffffffffffffffffffffff166001828154811061012657610126610303565b60009182526020909120015473ffffffffffffffffffffffffffffffffffffffff161415610152575050565b8061015c81610332565b9150506100f2565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff92909216919091179055565b6101dc600160006101de565b565b50805460008255906000526020600020908101906101fc91906101ff565b50565b5b808211156102145760008155600101610200565b5090565b803573ffffffffffffffffffffffffffffffffffffffff8116811461023c57600080fd5b919050565b60006020828403121561025357600080fd5b61025c82610218565b9392505050565b6000806000806060858703121561027957600080fd5b8435935061028960208601610218565b9250604085013567ffffffffffffffff808211156102a657600080fd5b818701915087601f8301126102ba57600080fd5b8135818111156102c957600080fd5b8860208285010111156102db57600080fd5b95989497505060200194505050565b6000602082840312156102fc57600080fd5b5035919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600060001982141561036d577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b506001019056fea264697066735822122012ae42a865a236284de7a33124397454b70ea953d7b46757408a222b076b79dd64736f6c63430008090033";

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

    public RemoteFunctionCall<TransactionReceipt> clearListOfLockedContracts() {
        final Function function = new Function(
                FUNC_CLEARLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
