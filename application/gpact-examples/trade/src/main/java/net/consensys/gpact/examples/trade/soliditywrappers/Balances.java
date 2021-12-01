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
public class Balances extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161087638038061087683398101604081905261002f91610054565b60008054600160a060020a031916600160a060020a0392909216919091179055610084565b60006020828403121561006657600080fd5b8151600160a060020a038116811461007d57600080fd5b9392505050565b6107e3806100936000396000f3fe608060405234801561001057600080fd5b5060043610610073577c0100000000000000000000000000000000000000000000000000000000600035046399eb5d4c8114610078578063beabacc81461008d578063e30443bc146100a0578063f6aacfb1146100b3578063f8b2cb4f146100ea575b600080fd5b61008b61008636600461060c565b61010b565b005b61008b61009b366004610659565b6101bf565b61008b6100ae366004610695565b6102ab565b6100d56100c13660046106b1565b600090815260026020526040902054151590565b60405190151581526020015b60405180910390f35b6100fd6100f83660046106ca565b6102c0565b6040519081526020016100e1565b60005b6000828152600360205260409020548110156101a3576000828152600360205260408120805483908110610144576101446106ec565b906000526020600020015490508315610182576000818152600260205260409020546101729060019061074a565b6000828152600160205260409020555b6000908152600260205260408120558061019b81610761565b91505061010e565b5060008181526003602052604081206101bb916105d2565b5050565b60006101ca846102c0565b905060006101d7846102c0565b90508282101561026d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152602060048201526024808201527f56616c7565207472616e736665723a20696e73756666696369656e742062616c60448201527f616e63650000000000000000000000000000000000000000000000000000000060648201526084015b60405180910390fd5b61028b6000600160a060020a038716610286868661074a565b6102dc565b6102a46000600160a060020a038616610286868561077c565b5050505050565b6101bb600083600160a060020a0316836102dc565b60006102d6600083600160a060020a0316610314565b92915050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061030e818361035e565b50505050565b6000808383604051602001610333929190918252602082015260400190565b60408051601f198184030181529190528051602090910120905061035681610549565b949350505050565b600082815260026020526040902054156103d4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b65640000000000000000000000006044820152606401610264565b60008060009054906101000a9004600160a060020a0316600160a060020a0316637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b15801561043f57600080fd5b505afa158015610453573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104779190610794565b905080610491575060009182526001602052604090912055565b6000546040517f39ce107e000000000000000000000000000000000000000000000000000000008152306004820152600160a060020a03909116906339ce107e90602401600060405180830381600087803b1580156104ef57600080fd5b505af1158015610503573d6000803e3d6000fd5b50505060008281526003602090815260408220805460018181018355918452919092200185905561053591508361077c565b600084815260026020526040902055505050565b600081815260026020526040812054156105bf576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610264565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906105f091906105f3565b50565b5b8082111561060857600081556001016105f4565b5090565b6000806040838503121561061f57600080fd5b8235801515811461062f57600080fd5b946020939093013593505050565b8035600160a060020a038116811461065457600080fd5b919050565b60008060006060848603121561066e57600080fd5b6106778461063d565b92506106856020850161063d565b9150604084013590509250925092565b600080604083850312156106a857600080fd5b61062f8361063d565b6000602082840312156106c357600080fd5b5035919050565b6000602082840312156106dc57600080fd5b6106e58261063d565b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008282101561075c5761075c61071b565b500390565b60006000198214156107755761077561071b565b5060010190565b6000821982111561078f5761078f61071b565b500190565b6000602082840312156107a657600080fd5b505191905056fea2646970667358221220c222a87e7f37856963c5168a12ad1e51c7aef071e6b35f2041e5a95c43f019cc64736f6c63430008090033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_SETBALANCE = "setBalance";

    public static final String FUNC_TRANSFER = "transfer";

    @Deprecated
    protected Balances(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Balances(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Balances(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Balances(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<BigInteger> getBalance(String _account) {
        final Function function = new Function(FUNC_GETBALANCE, 
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

    public RemoteFunctionCall<TransactionReceipt> setBalance(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _from, String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Balances load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Balances(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Balances load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Balances(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Balances load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Balances(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Balances load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Balances(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Balances> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Balances.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Balances> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Balances.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Balances> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Balances.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Balances> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Balances.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
