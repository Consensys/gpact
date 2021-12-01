package net.consensys.gpact.examples.trade.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class Stock extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161087638038061087683398101604081905261002f91610054565b60008054600160a060020a031916600160a060020a0392909216919091179055610084565b60006020828403121561006657600080fd5b8151600160a060020a038116811461007d57600080fd5b9392505050565b6107e3806100936000396000f3fe608060405234801561001057600080fd5b5060043610610073577c0100000000000000000000000000000000000000000000000000000000600035046310b0e2ec81146100785780638edf30ef1461008d57806399eb5d4c146100a0578063c5eaabfb146100b3578063f6aacfb1146100d9575b600080fd5b61008b610086366004610628565b61010b565b005b61008b61009b366004610664565b6101f7565b61008b6100ae36600461068e565b610210565b6100c66100c13660046106b1565b6102c0565b6040519081526020015b60405180910390f35b6100fb6100e73660046106d3565b600090815260026020526040902054151590565b60405190151581526020016100d0565b6000610116846102c0565b90506000610123846102c0565b9050828210156101b9576040517f08c379a0000000000000000000000000000000000000000000000000000000008152602060048201526024808201527f53746f636b207472616e736665723a20696e73756666696369656e742062616c60448201527f616e63650000000000000000000000000000000000000000000000000000000060648201526084015b60405180910390fd5b6101d76000600160a060020a0387166101d2868661071b565b6102dc565b6101f06000600160a060020a0386166101d28685610732565b5050505050565b61020c600083600160a060020a0316836102dc565b5050565b60005b6000828152600360205260409020548110156102a85760008281526003602052604081208054839081106102495761024961074a565b906000526020600020015490508315610287576000818152600260205260409020546102779060019061071b565b6000828152600160205260409020555b600090815260026020526040812055806102a081610779565b915050610213565b50600081815260036020526040812061020c916105d2565b60006102d6600083600160a060020a0316610314565b92915050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061030e818361035e565b50505050565b6000808383604051602001610333929190918252602082015260400190565b60408051601f198184030181529190528051602090910120905061035681610549565b949350505050565b600082815260026020526040902054156103d4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b656400000000000000000000000060448201526064016101b0565b60008060009054906101000a9004600160a060020a0316600160a060020a0316637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b15801561043f57600080fd5b505afa158015610453573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104779190610794565b905080610491575060009182526001602052604090912055565b6000546040517f39ce107e000000000000000000000000000000000000000000000000000000008152306004820152600160a060020a03909116906339ce107e90602401600060405180830381600087803b1580156104ef57600080fd5b505af1158015610503573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610535915083610732565b600084815260026020526040902055505050565b600081815260026020526040812054156105bf576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101b0565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906105f091906105f3565b50565b5b8082111561060857600081556001016105f4565b5090565b8035600160a060020a038116811461062357600080fd5b919050565b60008060006060848603121561063d57600080fd5b6106468461060c565b92506106546020850161060c565b9150604084013590509250925092565b6000806040838503121561067757600080fd5b6106808361060c565b946020939093013593505050565b600080604083850312156106a157600080fd5b8235801515811461068057600080fd5b6000602082840312156106c357600080fd5b6106cc8261060c565b9392505050565b6000602082840312156106e557600080fd5b5035919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008282101561072d5761072d6106ec565b500390565b60008219821115610745576107456106ec565b500190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600060001982141561078d5761078d6106ec565b5060010190565b6000602082840312156107a657600080fd5b505191905056fea26469706673582212206afcfa3998e725b8ccbf1fcaabc385f40ef1c4513424722eca28d2e91a4845f964736f6c63430008090033";

    public static final String FUNC_DELIVERY = "delivery";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETSTOCK = "getStock";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_SETSTOCK = "setStock";

    @Deprecated
    protected Stock(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Stock(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Stock(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Stock(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> delivery(String _from, String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_DELIVERY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getStock(String _account) {
        final Function function = new Function(FUNC_GETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setStock(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Stock load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Stock(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Stock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Stock(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Stock load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Stock(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Stock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Stock(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Stock> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Stock.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Stock> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Stock.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Stock> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Stock.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Stock> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Stock.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
