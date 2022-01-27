package net.consensys.gpact.soliditywrappers.examples.gpact.conditional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class OtherBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516105fa3803806105fa83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610567806100936000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630d7f9bde146100675780633d4197f01461007157806399eb5d4c146100845780639c3dfaf614610097578063e1cb0e52146100aa578063f6aacfb1146100c5575b600080fd5b61006f6100f7565b005b61006f61007f366004610436565b610120565b61006f61009236600461044f565b61012b565b61006f6100a5366004610480565b6101df565b6100b26101ec565b6040519081526020015b60405180910390f35b6100e76100d3366004610436565b600090815260026020526040902054151590565b60405190151581526020016100bc565b600061010360016101fd565b90508061010f816104b8565b91505061011d600182610271565b50565b61011d600182610271565b60005b6000828152600360205260409020548110156101c3576000828152600360205260408120805483908110610164576101646104d3565b9060005260206000200154905083156101a257600081815260026020526040902054610192906001906104e9565b6000828152600160205260409020555b600090815260026020526040812055806101bb816104b8565b91505061012e565b5060008181526003602052604081206101db91610404565b5050565b6101db61007f8284610500565b60006101f860016101fd565b905090565b6000818152600260205260408120541561025e5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064015b60405180910390fd5b5060009081526001602052604090205490565b600082815260026020526040902054156102c45760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610255565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b15801561031357600080fd5b505afa158015610327573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061034b9190610518565b905080610365575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b1580156103aa57600080fd5b505af11580156103be573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556103f0915083610500565b600084815260026020526040902055505050565b508054600082559060005260206000209081019061011d91905b80821115610432576000815560010161041e565b5090565b60006020828403121561044857600080fd5b5035919050565b6000806040838503121561046257600080fd5b8235801515811461047257600080fd5b946020939093013593505050565b6000806040838503121561049357600080fd5b50508035926020909101359150565b634e487b7160e01b600052601160045260246000fd5b60006000198214156104cc576104cc6104a2565b5060010190565b634e487b7160e01b600052603260045260246000fd5b6000828210156104fb576104fb6104a2565b500390565b60008219821115610513576105136104a2565b500190565b60006020828403121561052a57600080fd5b505191905056fea264697066735822122069a8894e9c78a03f3430f38569dc0616ea0149918897e2de3ad64c2dc76ecd8264736f6c63430008090033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_INCREMENTVAL = "incrementVal";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_SETVAL = "setVal";

    public static final String FUNC_SETVALUES = "setValues";

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal() {
        final Function function = new Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getVal() {
        final Function function = new Function(
                FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getABI_isLocked(BigInteger _key) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValues(BigInteger _val1, BigInteger _val2) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_setValues(BigInteger _val1, BigInteger _val2) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
