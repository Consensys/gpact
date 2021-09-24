package net.consensys.gpact.appcontracts.erc20.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class MockCbcForERC20Test extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610355806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806392b2c3351161005b57806392b2c335146100cc578063b4c3b756146100e0578063bd682e45146100f2578063ef3adc4a146100fa57600080fd5b806339ce107e146100825780637bf37a09146100975780638eaf89d8146100ad575b600080fd5b61009561009036600461021e565b61010d565b005b6000546040519081526020015b60405180910390f35b60408051600080825260208201819052918101919091526060016100a4565b6100956100da366004610259565b50505050565b600054604051901581526020016100a4565b6100956101ba565b610095610108366004610240565b600055565b60005b60015481101561016857816001600160a01b03166001828154811061013757610137610309565b6000918252602090912001546001600160a01b03161415610156575050565b80610160816102e0565b915050610110565b506001805480820182556000919091527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b0392909216919091179055565b6101c6600160006101c8565b565b50805460008255906000526020600020908101906101e691906101e9565b50565b5b808211156101fe57600081556001016101ea565b5090565b80356001600160a01b038116811461021957600080fd5b919050565b60006020828403121561023057600080fd5b61023982610202565b9392505050565b60006020828403121561025257600080fd5b5035919050565b6000806000806060858703121561026f57600080fd5b8435935061027f60208601610202565b9250604085013567ffffffffffffffff8082111561029c57600080fd5b818701915087601f8301126102b057600080fd5b8135818111156102bf57600080fd5b8860208285010111156102d157600080fd5b95989497505060200194505050565b600060001982141561030257634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fdfea26469706673582212208af588e63eaaef7e5230533d2652532ca12c383a979a7f5fbb819af6236fcfa264736f6c63430008050033";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CLEARLISTOFLOCKEDCONTRACTS = "clearListOfLockedContracts";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_GETACTIVECALLCROSSCHAINROOTTXID = "getActiveCallCrosschainRootTxId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_SETCROSSCHAINROOTTXID = "setCrosschainRootTxId";

    public static final String FUNC_WHOCALLEDME = "whoCalledMe";

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

    public String getRLP_addToListOfLockedContracts(String _contractToLock) {
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

    public String getRLP_clearListOfLockedContracts() {
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

    public String getRLP_crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
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

    public String getRLP_getActiveCallCrosschainRootTxId() {
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

    public String getRLP_isSingleBlockchainCall() {
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

    public String getRLP_setCrosschainRootTxId(byte[] _txId) {
        final Function function = new Function(
                FUNC_SETCROSSCHAINROOTTXID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>> whoCalledMe() {
        final Function function = new Function(FUNC_WHOCALLEDME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public String getRLP_whoCalledMe() {
        final Function function = new Function(
                FUNC_WHOCALLEDME, 
                Arrays.<Type>asList(), 
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
