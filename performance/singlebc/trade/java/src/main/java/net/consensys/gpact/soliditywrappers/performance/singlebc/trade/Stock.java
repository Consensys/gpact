package net.consensys.gpact.soliditywrappers.performance.singlebc.trade;

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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Stock extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506102b2806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806310b0e2ec146100465780638edf30ef1461005b578063c5eaabfb14610085575b600080fd5b6100596100543660046101af565b6100c0565b005b6100596100693660046101eb565b6001600160a01b03909116600090815260208190526040902055565b6100ae610093366004610215565b6001600160a01b031660009081526020819052604090205490565b60405190815260200160405180910390f35b6001600160a01b03838116600090815260208190526040808220549285168252902054828210156101435760405162461bcd60e51b8152602060048201526024808201527f53746f636b207472616e736665723a20696e73756666696369656e742062616c604482015263616e636560e01b606482015260840160405180910390fd5b61014d838361024d565b6001600160a01b0386166000908152602081905260409020556101708382610264565b6001600160a01b0390941660009081526020819052604090209390935550505050565b80356001600160a01b03811681146101aa57600080fd5b919050565b6000806000606084860312156101c457600080fd5b6101cd84610193565b92506101db60208501610193565b9150604084013590509250925092565b600080604083850312156101fe57600080fd5b61020783610193565b946020939093013593505050565b60006020828403121561022757600080fd5b61023082610193565b9392505050565b634e487b7160e01b600052601160045260246000fd5b60008282101561025f5761025f610237565b500390565b6000821982111561027757610277610237565b50019056fea2646970667358221220aa40c6a93fb92f8eebf1ae55688e1a78600ab74ccb3aba55b378f5411a228c7664736f6c63430008090033";

    public static final String FUNC_DELIVERY = "delivery";

    public static final String FUNC_GETSTOCK = "getStock";

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

    public RemoteFunctionCall<BigInteger> getStock(String _account) {
        final Function function = new Function(FUNC_GETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public static RemoteCall<Stock> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Stock.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Stock> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Stock.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Stock> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Stock.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Stock> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Stock.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
