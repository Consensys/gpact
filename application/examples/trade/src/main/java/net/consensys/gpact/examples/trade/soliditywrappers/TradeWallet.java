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
import org.web3j.abi.datatypes.Bool;
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
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161097038038061097083398101604081905261002f91610082565b600080546001600160a01b039485166001600160a01b031991821617909155600492909255600580549190931691161790556100be565b80516001600160a01b038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b6108a3806100cd6000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630b3504e61461005c5780632db25e051461007757806399eb5d4c1461008a578063c8de7c601461009f578063f6aacfb1146100b2575b600080fd5b6100646100e4565b6040519081526020015b60405180910390f35b610064610085366004610764565b6100f5565b61009d61009836600461072d565b610108565b005b61009d6100ad3660046106d1565b6101bc565b6100d46100c0366004610764565b600090815260026020526040902054151590565b604051901515815260200161006e565b60006100f060006102fd565b905090565b6000610102600083610308565b92915050565b60005b6000828152600360205260409020548110156101a057600082815260036020526040812080548390811061014157610141610849565b90600052602060002001549050831561017f5760008181526002602052604090205461016f90600190610801565b6000828152600160205260409020555b6000908152600260205260408120558061019881610818565b91505061010b565b5060008181526003602052604081206101b891610697565b5050565b60005460048054600554604080516001600160a01b038881166024830152326044830152606480830189905283518084039091018152608490920183526020820180516001600160e01b03166368cb63b760e11b17905291516392b2c33560e01b8152958216956392b2c33595610239959493909316920161077d565b600060405180830381600087803b15801561025357600080fd5b505af1158015610267573d6000803e3d6000fd5b50506040516bffffffffffffffffffffffff19606086811b8216602084015232901b166034820152604881018490526000925060680190506040516020818303038152906040528051906020012090506102c560008260001c6103aa565b6040518181527f979597633b92b98cf82a1b3e32dee4f4e729c2be11ced37801cbcea939cd83a89060200160405180910390a1505050565b600061010282610413565b60008061031484610413565b90508281116103605760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b60008460405160200161037591815260200190565b60408051601f19818403018152919052805160209091012090506103a161039c85836107e9565b610413565b95945050505050565b60006103b583610413565b90506000836040516020016103cc91815260200190565b60408051601f19818403018152919052805160209091012090506103f96103f383836107e9565b84610482565b61040d846104088460016107e9565b610482565b50505050565b6000818152600260205260408120541561046f5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610357565b5060009081526001602052604090205490565b600082815260026020526040902054156104d55760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610357565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561052157600080fd5b505afa158015610535573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105599190610709565b156105705760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156105bf57600080fd5b505afa1580156105d3573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105f7919061074b565b600054604051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561063d57600080fd5b505af1158015610651573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556106839150836107e9565b600084815260026020526040902055505050565b50805460008255906000526020600020908101906106b591906106b8565b50565b5b808211156106cd57600081556001016106b9565b5090565b600080604083850312156106e457600080fd5b82356001600160a01b03811681146106fb57600080fd5b946020939093013593505050565b60006020828403121561071b57600080fd5b81516107268161085f565b9392505050565b6000806040838503121561074057600080fd5b82356106fb8161085f565b60006020828403121561075d57600080fd5b5051919050565b60006020828403121561077657600080fd5b5035919050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156107bf578581018301518582016080015282016107a3565b818111156107d1576000608083870101525b50601f01601f19169290920160800195945050505050565b600082198211156107fc576107fc610833565b500190565b60008282101561081357610813610833565b500390565b600060001982141561082c5761082c610833565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b80151581146106b557600080fdfea26469706673582212202a907083492edff2c5095a95a524ec5835f323e1b1508fa38752006e6c8b899264736f6c63430008050033";

    public static final String FUNC_EXECUTETRADE = "executeTrade";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETNUMTRADES = "getNumTrades";

    public static final String FUNC_GETTRADE = "getTrade";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
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

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public static RemoteCall<TradeWallet> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc, BigInteger _busLogicBcId, String _busLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract)));
        return deployRemoteCall(TradeWallet.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TradeWallet> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc, BigInteger _busLogicBcId, String _busLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract)));
        return deployRemoteCall(TradeWallet.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TradeWallet> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _busLogicBcId, String _busLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract)));
        return deployRemoteCall(TradeWallet.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TradeWallet> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _busLogicBcId, String _busLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_busLogicBcId), 
                new org.web3j.abi.datatypes.Address(160, _busLogicContract)));
        return deployRemoteCall(TradeWallet.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TradeEventResponse extends BaseEventResponse {
        public byte[] _tradeId;
    }
}
