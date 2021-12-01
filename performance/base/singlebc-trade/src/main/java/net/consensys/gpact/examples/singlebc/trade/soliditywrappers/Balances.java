package net.consensys.gpact.examples.singlebc.trade.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50610368806100206000396000f3fe608060405234801561001057600080fd5b506004361061005d577c01000000000000000000000000000000000000000000000000000000006000350463beabacc88114610062578063e30443bc14610077578063f8b2cb4f146100ae575b600080fd5b61007561007036600461024c565b6100f6565b005b610075610085366004610288565b73ffffffffffffffffffffffffffffffffffffffff909116600090815260208190526040902055565b6100e46100bc3660046102b2565b73ffffffffffffffffffffffffffffffffffffffff1660009081526020819052604090205490565b60405190815260200160405180910390f35b73ffffffffffffffffffffffffffffffffffffffff838116600090815260208190526040808220549285168252902054828210156101b9576040517f08c379a0000000000000000000000000000000000000000000000000000000008152602060048201526024808201527f56616c7565207472616e736665723a20696e73756666696369656e742062616c60448201527f616e636500000000000000000000000000000000000000000000000000000000606482015260840160405180910390fd5b6101c38383610303565b73ffffffffffffffffffffffffffffffffffffffff86166000908152602081905260409020556101f3838261031a565b73ffffffffffffffffffffffffffffffffffffffff90941660009081526020819052604090209390935550505050565b803573ffffffffffffffffffffffffffffffffffffffff8116811461024757600080fd5b919050565b60008060006060848603121561026157600080fd5b61026a84610223565b925061027860208501610223565b9150604084013590509250925092565b6000806040838503121561029b57600080fd5b6102a483610223565b946020939093013593505050565b6000602082840312156102c457600080fd5b6102cd82610223565b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600082821015610315576103156102d4565b500390565b6000821982111561032d5761032d6102d4565b50019056fea26469706673582212207e8f43868eaea1aa9c4d0eb39bfde1dc56b5852412ee3ae4ebe42c69d56e7f8264736f6c63430008090033";

    public static final String FUNC_GETBALANCE = "getBalance";

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

    public RemoteFunctionCall<BigInteger> getBalance(String _account) {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public static RemoteCall<Balances> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Balances.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Balances> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Balances.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Balances> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Balances.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Balances> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Balances.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
