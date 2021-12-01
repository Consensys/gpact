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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class TradeWallet extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516109ef3803806109ef83398101604081905261002f91610082565b60008054600160a060020a03948516600160a060020a031991821617909155600492909255600580549190931691161790556100be565b8051600160a060020a038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b610922806100cd6000396000f3fe608060405234801561001057600080fd5b5060043610610073577c010000000000000000000000000000000000000000000000000000000060003504630b3504e681146100785780632db25e051461009357806399eb5d4c146100a6578063c8de7c60146100bb578063f6aacfb1146100ce575b600080fd5b610080610100565b6040519081526020015b60405180910390f35b6100806100a136600461074a565b610111565b6100b96100b4366004610763565b610124565b005b6100b96100c9366004610794565b6101d8565b6100f06100dc36600461074a565b600090815260026020526040902054151590565b604051901515815260200161008a565b600061010c6000610362565b905090565b600061011e60008361036d565b92915050565b60005b6000828152600360205260409020548110156101bc57600082815260036020526040812080548390811061015d5761015d6107be565b90600052602060002001549050831561019b5760008181526002602052604090205461018b9060019061081c565b6000828152600160205260409020555b600090815260026020526040812055806101b481610833565b915050610127565b5060008181526003602052604081206101d491610710565b5050565b6000546004805460055460408051600160a060020a038881166024830152326044830152606480830189905283518084039091018152608490920183526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167fd196c76e0000000000000000000000000000000000000000000000000000000017905291517f92b2c335000000000000000000000000000000000000000000000000000000008152958216956392b2c3359561029c959493909316920161084e565b600060405180830381600087803b1580156102b657600080fd5b505af11580156102ca573d6000803e3d6000fd5b50506040516c01000000000000000000000000600160a060020a03861681026020830152320260348201526048810184905260009250606801905060408051601f198184030181529190528051602090910120905061032a600082610433565b6040518181527f979597633b92b98cf82a1b3e32dee4f4e729c2be11ced37801cbcea939cd83a89060200160405180910390a1505050565b600061011e8261049c565b6000806103798461049c565b90508281116103e9576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601360248201527f496e646578206f7574206f6620626f756e64730000000000000000000000000060448201526064015b60405180910390fd5b6000846040516020016103fe91815260200190565b60408051601f198184030181529190528051602090910120905061042a61042585836108bb565b61049c565b95945050505050565b600061043e8361049c565b905060008360405160200161045591815260200190565b60408051601f198184030181529190528051602090910120905061048261047c83836108bb565b84610525565b610496846104918460016108bb565b610525565b50505050565b60008181526002602052604081205415610512576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016103e0565b5060009081526001602052604090205490565b6000828152600260205260409020541561059b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b656400000000000000000000000060448201526064016103e0565b60008060009054906101000a9004600160a060020a0316600160a060020a0316637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b15801561060657600080fd5b505afa15801561061a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061063e91906108d3565b905080610658575060009182526001602052604090912055565b6000546040517f39ce107e000000000000000000000000000000000000000000000000000000008152306004820152600160a060020a03909116906339ce107e90602401600060405180830381600087803b1580156106b657600080fd5b505af11580156106ca573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556106fc9150836108bb565b600084815260026020526040902055505050565b508054600082559060005260206000209081019061072e9190610731565b50565b5b808211156107465760008155600101610732565b5090565b60006020828403121561075c57600080fd5b5035919050565b6000806040838503121561077657600080fd5b8235801515811461078657600080fd5b946020939093013593505050565b600080604083850312156107a757600080fd5b8235600160a060020a038116811461078657600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008282101561082e5761082e6107ed565b500390565b6000600019821415610847576108476107ed565b5060010190565b83815260006020600160a060020a0385168184015260606040840152835180606085015260005b8181101561089157858101830151858201608001528201610875565b818111156108a3576000608083870101525b50601f01601f19169290920160800195945050505050565b600082198211156108ce576108ce6107ed565b500190565b6000602082840312156108e557600080fd5b505191905056fea26469706673582212204632662c0148108f24dd9af862601e5a7f876fac8be2ae1da88755448d69a20964736f6c63430008090033";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getNumTrades() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMTRADES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTrade(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
