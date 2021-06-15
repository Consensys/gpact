package net.consensys.gpact.examples.trade.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
public class TradeWallet extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516107e83803806107e883398101604081905261002f91610091565b600080546001600160a01b03199081166001600160a01b0393841617909155600180548216958316959095179094556002929092556003805490931691161790556100de565b80516001600160a01b038116811461008c57600080fd5b919050565b600080600080608085870312156100a757600080fd5b6100b085610075565b9350602085015192506100c560408601610075565b91506100d360608601610075565b905092959194509250565b6106fb806100ed6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630b3504e61461005157806311ce02671461006c5780632db25e0514610097578063c8de7c60146100aa575b600080fd5b6100596100bf565b6040519081526020015b60405180910390f35b60005461007f906001600160a01b031681565b6040516001600160a01b039091168152602001610063565b6100596100a5366004610601565b6100d0565b6100bd6100b83660046105c9565b6100e3565b005b60006100cb6000610224565b905090565b60006100dd6000836102a1565b92915050565b600154600254600354604080516001600160a01b038781166024830152326044830152606480830188905283518084039091018152608490920183526020820180516001600160e01b03166368cb63b760e11b17905291516392b2c33560e01b8152948216946392b2c33594610160949093169190600401610633565b600060405180830381600087803b15801561017a57600080fd5b505af115801561018e573d6000803e3d6000fd5b50506040516bffffffffffffffffffffffff19606086811b8216602084015232901b166034820152604881018490526000925060680190506040516020818303038152906040528051906020012090506101ec60008260001c61042b565b6040518181527f979597633b92b98cf82a1b3e32dee4f4e729c2be11ced37801cbcea939cd83a89060200160405180910390a1505050565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561026957600080fd5b505afa15801561027d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100dd919061061a565b60008054604051631106aeeb60e21b81526004810185905282916001600160a01b03169063441abbac9060240160206040518083038186803b1580156102e657600080fd5b505afa1580156102fa573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061031e919061061a565b90508281116103695760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015260640160405180910390fd5b60008460405160200161037e91815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b031663441abbac6103b4868461069f565b6040518263ffffffff1660e01b81526004016103d291815260200190565b60206040518083038186803b1580156103ea57600080fd5b505afa1580156103fe573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610422919061061a565b95945050505050565b60008054604051631106aeeb60e21b8152600481018590526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561047057600080fd5b505afa158015610484573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104a8919061061a565b90506000836040516020016104bf91815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b0316635bf9755e6104f5848461069f565b856040518363ffffffff1660e01b815260040161051c929190918252602082015260400190565b600060405180830381600087803b15801561053657600080fd5b505af115801561054a573d6000803e3d6000fd5b50506000546001600160a01b03169150635bf9755e90508561056d85600161069f565b6040516001600160e01b031960e085901b16815260048101929092526024820152604401600060405180830381600087803b1580156105ab57600080fd5b505af11580156105bf573d6000803e3d6000fd5b5050505050505050565b600080604083850312156105dc57600080fd5b82356001600160a01b03811681146105f357600080fd5b946020939093013593505050565b60006020828403121561061357600080fd5b5035919050565b60006020828403121561062c57600080fd5b5051919050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b8181101561067557858101830151858201608001528201610659565b81811115610687576000608083870101525b50601f01601f19169290920160800195945050505050565b600082198211156106c057634e487b7160e01b600052601160045260246000fd5b50019056fea264697066735822122010fd0b88d4471aa67cd42b641c9a9835ff0c3ccffeffa692319aa426fef0a40064736f6c63430008050033";

    public static final String FUNC_EXECUTETRADE = "executeTrade";

    public static final String FUNC_GETNUMTRADES = "getNumTrades";

    public static final String FUNC_GETTRADE = "getTrade";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    public static final Event TRADE_EVENT = new Event("Trade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    @Deprecated
    protected TradeWallet(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TradeWallet(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TradeWallet(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TradeWallet(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<TradeEventResponse> getTradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRADE_EVENT, transactionReceipt);
        ArrayList<TradeEventResponse> responses = new ArrayList<TradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeEventResponse typedResponse = new TradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._tradeId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TradeEventResponse>() {
            @Override
            public TradeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRADE_EVENT, log);
                TradeEventResponse typedResponse = new TradeEventResponse();
                typedResponse.log = log;
                typedResponse._tradeId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TradeEventResponse> tradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADE_EVENT));
        return tradeEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> executeTrade(String _seller, BigInteger _quantity) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXECUTETRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_executeTrade(String _seller, BigInteger _quantity) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXECUTETRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getNumTrades() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMTRADES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getNumTrades() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETNUMTRADES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getTrade(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getTrade(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETTRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> storageContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_storageContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static TradeWallet load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeWallet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TradeWallet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeWallet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TradeWallet load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TradeWallet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TradeWallet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TradeWallet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TradeWallet> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc, BigInteger _busLogicBcId, String _busLogicContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TradeWallet.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TradeWallet> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc, BigInteger _busLogicBcId, String _busLogicContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TradeWallet.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TradeWallet> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _busLogicBcId, String _busLogicContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TradeWallet.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TradeWallet> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _busLogicBcId, String _busLogicContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TradeWallet.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TradeEventResponse extends BaseEventResponse {
        public byte[] _tradeId;
    }
}
