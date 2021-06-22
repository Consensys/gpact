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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610a9a380380610a9a83398101604081905261002f91610082565b600080546001600160a01b039485166001600160a01b031991821617909155600692909255600780549190931691161790556100be565b80516001600160a01b038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b6109cd806100cd6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630b3504e6146100675780632db25e05146100825780637445b0d014610095578063c8de7c60146100b5578063f6aacfb1146100ca578063fcea5096146100fd575b600080fd5b61006f610110565b6040519081526020015b60405180910390f35b61006f61009036600461088c565b610121565b61006f6100a336600461088c565b60056020526000908152604090205481565b6100c86100c33660046107fb565b610134565b005b6100ed6100d836600461088c565b60036020526000908152604090205460ff1681565b6040519015158152602001610079565b6100c861010b366004610857565b610275565b600061011c6000610358565b905090565b600061012e600083610363565b92915050565b600054600654600754604080516001600160a01b038781166024830152326044830152606480830188905283518084039091018152608490920183526020820180516001600160e01b03166368cb63b760e11b17905291516392b2c33560e01b8152948216946392b2c335946101b19490931691906004016108be565b600060405180830381600087803b1580156101cb57600080fd5b505af11580156101df573d6000803e3d6000fd5b50506040516bffffffffffffffffffffffff19606086811b8216602084015232901b1660348201526048810184905260009250606801905060405160208183030381529060405280519060200120905061023d60008260001c610405565b6040518181527f979597633b92b98cf82a1b3e32dee4f4e729c2be11ced37801cbcea939cd83a89060200160405180910390a1505050565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b60008281526004602052604090205481101561033a5760008281526004602052604081208054839081106102d6576102d6610973565b906000526020600020015490508515610305576000818152600260209081526040808320546001909252909120555b600090815260026020908152604080832083905560039091529020805460ff191690558061033281610942565b9150506102a0565b506000818152600460205260408120610352916107c1565b50505050565b600061012e82610468565b60008061036f84610468565b90508281116103bb5760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b6000846040516020016103d091815260200190565b60408051601f19818403018152919052805160209091012090506103fc6103f7858361092a565b610468565b95945050505050565b600061041083610468565b905060008360405160200161042791815260200190565b60408051601f198184030181529190528051602090910120905061045461044e838361092a565b846104da565b6103528461046384600161092a565b6104da565b60008181526003602052604081205460ff16156104c75760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016103b2565b5060009081526001602052604090205490565b60008281526003602052604090205460ff16156105305760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016103b2565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561057c57600080fd5b505afa158015610590573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105b49190610833565b156105cb5760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b15801561061a57600080fd5b505afa15801561062e573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061065291906108a5565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b1580156106a357600080fd5b505afa1580156106b7573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106db91906108a5565b6040805160208082018690528183018490528251808303840181526060909201909252805191012090915060009060008681526005602052604080822083905590549051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561075f57600080fd5b505af1158015610773573d6000803e3d6000fd5b50505060009182525060046020908152604080832080546001818101835591855283852001889055878452600283528184208790556003909252909120805460ff1916909117905550505050565b50805460008255906000526020600020908101906107df91906107e2565b50565b5b808211156107f757600081556001016107e3565b5090565b6000806040838503121561080e57600080fd5b82356001600160a01b038116811461082557600080fd5b946020939093013593505050565b60006020828403121561084557600080fd5b815161085081610989565b9392505050565b60008060006060848603121561086c57600080fd5b833561087781610989565b95602085013595506040909401359392505050565b60006020828403121561089e57600080fd5b5035919050565b6000602082840312156108b757600080fd5b5051919050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b81811015610900578581018301518582016080015282016108e4565b81811115610912576000608083870101525b50601f01601f19169290920160800195945050505050565b6000821982111561093d5761093d61095d565b500190565b60006000198214156109565761095661095d565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b80151581146107df57600080fdfea2646970667358221220dcbf6b1239bc4a24cdda9ecf729ce0d42dedd9e285e94ce4bdc04f7917f201b164736f6c63430008050033";

    public static final String FUNC_EXECUTETRADE = "executeTrade";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETNUMTRADES = "getNumTrades";

    public static final String FUNC_GETTRADE = "getTrade";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
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

    public RemoteFunctionCall<Boolean> isLocked(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
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
