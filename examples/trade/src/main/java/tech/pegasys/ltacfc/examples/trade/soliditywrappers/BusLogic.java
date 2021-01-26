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
public class BusLogic extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610532380380610532833981810160405260e081101561003357600080fd5b508051602082015160408301516060840151608085015160a086015160c090960151600680546001600160a01b039788166001600160a01b0319918216179091556000959095556001805494871694861694909417909355600291909155600380549185169184169190911790556004939093556005805492909316911617905561046f806100c36000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063d196c76e14610030575b600080fd5b6100666004803603606081101561004657600080fd5b506001600160a01b03813581169160208101359091169060400135610068565b005b600654600254600354604080516004808252602480830184526020830180516001600160e01b0316634c6afee560e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b838110156101095781810151838201526020016100f1565b50505050905090810190601f1680156101365780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561015757600080fd5b505af115801561016b573d6000803e3d6000fd5b505050506040513d602081101561018157600080fd5b505160065460008054600154604080516001600160a01b038a81166024808401919091528c82166044808501919091528b8a026064808601829052865180870382018152608496870188526020810180516001600160e01b03166317d5759960e31b17815297516392b2c33560e01b8152600481018b8152998716958101869052606094810194855281519281019290925280519c9d50919b94909a16996392b2c3359993979196919592949201929091908190849084905b8381101561025257818101518382015260200161023a565b50505050905090810190601f16801561027f5780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b1580156102a057600080fd5b505af11580156102b4573d6000803e3d6000fd5b505060065460048054600554604080516001600160a01b038d81166024808401919091528d821660448085019190915260648085018f9052855180860382018152608495860187526020810180516001600160e01b031663042c38bb60e21b17815296516392b2c33560e01b8152998a01898152978516938a018490526060928a019283528051918a01919091528051939099169a506392b2c3359950959790969095939201919080838360005b8381101561037a578181015183820152602001610362565b50505050905090810190601f1680156103a75780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b1580156103c857600080fd5b505af11580156103dc573d6000803e3d6000fd5b5050604080516001600160a01b03808a168252881660208201528082018790526060810186905290517fe98a754d57da22349279d2240bff334f71728546e2052a6d667080cf21d97d979350908190036080019150a1505050505056fea2646970667358221220b91d612bea17ec22ab3288d771e2a09fd9a287bb1e46f11c6fa1800e38ab8d6c64736f6c63430007040033";

    public static final String FUNC_STOCKSHIPMENT = "stockShipment";

    public static final Event STOCKSHIPMENT_EVENT = new Event("StockShipment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected BusLogic(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BusLogic(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BusLogic(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BusLogic(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<StockShipmentEventResponse> getStockShipmentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STOCKSHIPMENT_EVENT, transactionReceipt);
        ArrayList<StockShipmentEventResponse> responses = new ArrayList<StockShipmentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StockShipmentEventResponse typedResponse = new StockShipmentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._seller = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._buyer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._price = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StockShipmentEventResponse> stockShipmentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StockShipmentEventResponse>() {
            @Override
            public StockShipmentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STOCKSHIPMENT_EVENT, log);
                StockShipmentEventResponse typedResponse = new StockShipmentEventResponse();
                typedResponse.log = log;
                typedResponse._seller = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._buyer = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._quantity = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._price = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StockShipmentEventResponse> stockShipmentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STOCKSHIPMENT_EVENT));
        return stockShipmentEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> stockShipment(String _seller, String _buyer, BigInteger _quantity) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOCKSHIPMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_stockShipment(String _seller, String _buyer, BigInteger _quantity) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STOCKSHIPMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint256(_quantity)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static BusLogic load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BusLogic(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BusLogic load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BusLogic(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BusLogic load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BusLogic(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BusLogic load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BusLogic(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BusLogic> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc, BigInteger _balancesBcId, String _balances, BigInteger _oracleBcId, String _oracle, BigInteger _stockBcId, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_balancesBcId), 
                new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.generated.Uint256(_oracleBcId), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.generated.Uint256(_stockBcId), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<BusLogic> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc, BigInteger _balancesBcId, String _balances, BigInteger _oracleBcId, String _oracle, BigInteger _stockBcId, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_balancesBcId), 
                new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.generated.Uint256(_oracleBcId), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.generated.Uint256(_stockBcId), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BusLogic> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _balancesBcId, String _balances, BigInteger _oracleBcId, String _oracle, BigInteger _stockBcId, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_balancesBcId), 
                new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.generated.Uint256(_oracleBcId), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.generated.Uint256(_stockBcId), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BusLogic> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _balancesBcId, String _balances, BigInteger _oracleBcId, String _oracle, BigInteger _stockBcId, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_balancesBcId), 
                new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.generated.Uint256(_oracleBcId), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.generated.Uint256(_stockBcId), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class StockShipmentEventResponse extends BaseEventResponse {
        public String _seller;

        public String _buyer;

        public BigInteger _quantity;

        public BigInteger _price;
    }
}
