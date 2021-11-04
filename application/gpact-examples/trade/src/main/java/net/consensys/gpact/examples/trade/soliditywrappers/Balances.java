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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Balances extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161078438038061078483398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6106f1806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806399eb5d4c1461005c578063beabacc814610071578063e30443bc14610084578063f6aacfb114610097578063f8b2cb4f146100ce575b600080fd5b61006f61006a36600461054c565b6100ef565b005b61006f61007f366004610599565b6101a3565b61006f6100923660046105d5565b61025c565b6100b96100a53660046105f1565b600090815260026020526040902054151590565b60405190151581526020015b60405180910390f35b6100e16100dc36600461060a565b610271565b6040519081526020016100c5565b60005b6000828152600360205260409020548110156101875760008281526003602052604081208054839081106101285761012861062c565b9060005260206000200154905083156101665760008181526002602052604090205461015690600190610658565b6000828152600160205260409020555b6000908152600260205260408120558061017f8161066f565b9150506100f2565b50600081815260036020526040812061019f91610512565b5050565b60006101ae84610271565b905060006101bb84610271565b90508282101561021e5760405162461bcd60e51b8152602060048201526024808201527f56616c7565207472616e736665723a20696e73756666696369656e742062616c604482015263616e636560e01b60648201526084015b60405180910390fd5b61023c60006001600160a01b0387166102378686610658565b61028d565b61025560006001600160a01b038616610237868561068a565b5050505050565b61019f6000836001600160a01b03168361028d565b60006102876000836001600160a01b03166102c5565b92915050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206102bf8183610310565b50505050565b60008083836040516020016102e4929190918252602082015260400190565b6040516020818303038152906040528051906020012090506103088160001c6104a3565b949350505050565b600082815260026020526040902054156103635760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610215565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156103b257600080fd5b505afa1580156103c6573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103ea91906106a2565b905080610404575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b15801561044957600080fd5b505af115801561045d573d6000803e3d6000fd5b50505060008281526003602090815260408220805460018181018355918452919092200185905561048f91508361068a565b600084815260026020526040902055505050565b600081815260026020526040812054156104ff5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610215565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906105309190610533565b50565b5b808211156105485760008155600101610534565b5090565b6000806040838503121561055f57600080fd5b8235801515811461056f57600080fd5b946020939093013593505050565b80356001600160a01b038116811461059457600080fd5b919050565b6000806000606084860312156105ae57600080fd5b6105b78461057d565b92506105c56020850161057d565b9150604084013590509250925092565b600080604083850312156105e857600080fd5b61056f8361057d565b60006020828403121561060357600080fd5b5035919050565b60006020828403121561061c57600080fd5b6106258261057d565b9392505050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60008282101561066a5761066a610642565b500390565b600060001982141561068357610683610642565b5060010190565b6000821982111561069d5761069d610642565b500190565b6000602082840312156106b457600080fd5b505191905056fea2646970667358221220b9e525da05fd401f91c205108f18c00bb27cd4b6d2caf734bf1855f2f726ee5364736f6c63430008090033";

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

    public String getABI_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance(String _account) {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getBalance(String _account) {
        final Function function = new Function(
                FUNC_GETBALANCE, 
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

    public RemoteFunctionCall<TransactionReceipt> setBalance(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_setBalance(String _account, BigInteger _newBalance) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_newBalance)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getABI_transfer(String _from, String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
