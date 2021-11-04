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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Stock extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161078438038061078483398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6106f1806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806310b0e2ec1461005c5780638edf30ef1461007157806399eb5d4c14610084578063c5eaabfb14610097578063f6aacfb1146100bd575b600080fd5b61006f61006a366004610568565b6100ef565b005b61006f61007f3660046105a4565b6101a8565b61006f6100923660046105ce565b6101c1565b6100aa6100a53660046105f1565b610271565b6040519081526020015b60405180910390f35b6100df6100cb366004610613565b600090815260026020526040902054151590565b60405190151581526020016100b4565b60006100fa84610271565b9050600061010784610271565b90508282101561016a5760405162461bcd60e51b8152602060048201526024808201527f53746f636b207472616e736665723a20696e73756666696369656e742062616c604482015263616e636560e01b60648201526084015b60405180910390fd5b61018860006001600160a01b0387166101838686610642565b61028d565b6101a160006001600160a01b0386166101838685610659565b5050505050565b6101bd6000836001600160a01b03168361028d565b5050565b60005b6000828152600360205260409020548110156102595760008281526003602052604081208054839081106101fa576101fa610671565b9060005260206000200154905083156102385760008181526002602052604090205461022890600190610642565b6000828152600160205260409020555b6000908152600260205260408120558061025181610687565b9150506101c4565b5060008181526003602052604081206101bd91610512565b60006102876000836001600160a01b03166102c5565b92915050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206102bf8183610310565b50505050565b60008083836040516020016102e4929190918252602082015260400190565b6040516020818303038152906040528051906020012090506103088160001c6104a3565b949350505050565b600082815260026020526040902054156103635760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610161565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156103b257600080fd5b505afa1580156103c6573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103ea91906106a2565b905080610404575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b15801561044957600080fd5b505af115801561045d573d6000803e3d6000fd5b50505060008281526003602090815260408220805460018181018355918452919092200185905561048f915083610659565b600084815260026020526040902055505050565b600081815260026020526040812054156104ff5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610161565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906105309190610533565b50565b5b808211156105485760008155600101610534565b5090565b80356001600160a01b038116811461056357600080fd5b919050565b60008060006060848603121561057d57600080fd5b6105868461054c565b92506105946020850161054c565b9150604084013590509250925092565b600080604083850312156105b757600080fd5b6105c08361054c565b946020939093013593505050565b600080604083850312156105e157600080fd5b823580151581146105c057600080fd5b60006020828403121561060357600080fd5b61060c8261054c565b9392505050565b60006020828403121561062557600080fd5b5035919050565b634e487b7160e01b600052601160045260246000fd5b6000828210156106545761065461062c565b500390565b6000821982111561066c5761066c61062c565b500190565b634e487b7160e01b600052603260045260246000fd5b600060001982141561069b5761069b61062c565b5060010190565b6000602082840312156106b457600080fd5b505191905056fea26469706673582212209d21a003bfe94482d24b9692f8aa2e91229bcba1921ef36e1fc8ba75cd515e0264736f6c63430008090033";

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

    public String getABI_delivery(String _from, String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_DELIVERY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public RemoteFunctionCall<BigInteger> getStock(String _account) {
        final Function function = new Function(FUNC_GETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getStock(String _account) {
        final Function function = new Function(
                FUNC_GETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
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

    public RemoteFunctionCall<TransactionReceipt> setStock(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_setStock(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETSTOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
