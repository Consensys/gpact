package tech.pegasys.ltacfc.examples.trade.soliditywrappers;

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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TradeWallet extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516106f53803806106f58339818101604052608081101561003357600080fd5b50805160208201516040830151606090930151600080546001600160a01b039283166001600160a01b03199182161790915560018054948316948216949094179093556002919091556003805491909316911617905561065d806100986000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630b3504e61461005157806311ce02671461006b5780632db25e051461008f578063c8de7c60146100ac575b600080fd5b6100596100da565b60408051918252519081900360200190f35b6100736100eb565b604080516001600160a01b039092168252519081900360200190f35b610059600480360360208110156100a557600080fd5b50356100fa565b6100d8600480360360408110156100c257600080fd5b506001600160a01b03813516906020013561010d565b005b60006100e660006102cf565b905090565b6000546001600160a01b031681565b600061010760008361034e565b92915050565b600154600254600354604080516001600160a01b038781166024808401919091523260448085019190915260648085018a9052855180860382018152608495860187526020810180516001600160e01b03166368cb63b760e11b17815296516392b2c33560e01b8152600481018a81529886169481018590526060938101938452815192810192909252805194909916986392b2c33598979396909593949293919091019180838360005b838110156101d05781810151838201526020016101b8565b50505050905090810190601f1680156101fd5780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b15801561021e57600080fd5b505af1158015610232573d6000803e3d6000fd5b50505050600082328360405160200180846001600160a01b031660601b8152601401836001600160a01b031660601b8152601401828152602001935050505060405160208183030381529060405280519060200120905061029760008260001c6104b1565b6040805182815290517f979597633b92b98cf82a1b3e32dee4f4e729c2be11ced37801cbcea939cd83a89181900360200190a1505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561031c57600080fd5b505afa158015610330573d6000803e3d6000fd5b505050506040513d602081101561034657600080fd5b505192915050565b6000805460408051631106aeeb60e21b815260048101869052905183926001600160a01b03169163441abbac916024808301926020929190829003018186803b15801561039a57600080fd5b505afa1580156103ae573d6000803e3d6000fd5b505050506040513d60208110156103c457600080fd5b50519050828111610412576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b604080516020808201879052825180830382018152828401808552815191830191909120600054631106aeeb60e21b909252878101604485015293516001600160a01b039091169263441abbac926064808301939192829003018186803b15801561047c57600080fd5b505afa158015610490573d6000803e3d6000fd5b505050506040513d60208110156104a657600080fd5b505195945050505050565b6000805460408051631106aeeb60e21b81526004810186905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156104fe57600080fd5b505afa158015610512573d6000803e3d6000fd5b505050506040513d602081101561052857600080fd5b50516040805160208181018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b9093528186016044850152606484018890529351949550936001600160a01b0390911692635bf9755e926084808201939182900301818387803b15801561059e57600080fd5b505af11580156105b2573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018a905260018801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b15801561060957600080fd5b505af115801561061d573d6000803e3d6000fd5b505050505050505056fea264697066735822122026614952e8b352ec092e6145a3bf74e39a683f94f854c49818da3ad37893d26d64736f6c63430007040033";

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
