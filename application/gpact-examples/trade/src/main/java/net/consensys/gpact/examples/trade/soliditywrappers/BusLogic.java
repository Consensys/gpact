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
public class BusLogic extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161053538038061053583398101604081905261002f916100ac565b600680546001600160a01b039889166001600160a01b0319918216179091556000969096556001805495881695871695909517909455600292909255600380549186169185169190911790556004556005805491909316911617905561011d565b80516001600160a01b03811681146100a757600080fd5b919050565b600080600080600080600060e0888a0312156100c757600080fd5b6100d088610090565b9650602088015195506100e560408901610090565b9450606088015193506100fa60808901610090565b925060a0880151915061010f60c08901610090565b905092959891949750929550565b6104098061012c6000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063d196c76e14610030575b600080fd5b61004361003e3660046102e5565b610045565b005b6006546002546003546040805160048082526024820183526020820180516001600160e01b0316634c6afee560e11b1790529151632388b54d60e21b81526000956001600160a01b0390811695638e22d534956100a795919492169201610321565b602060405180830381600087803b1580156100c157600080fd5b505af11580156100d5573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100f9919061038d565b9050600061010783836103a6565b600654600054600154604080516001600160a01b038a811660248301528b81166044830152606480830188905283518084039091018152608490920183526020820180516001600160e01b03166317d5759960e31b17905291516392b2c33560e01b8152959650938116946392b2c3359461018b9493929092169190600401610321565b600060405180830381600087803b1580156101a557600080fd5b505af11580156101b9573d6000803e3d6000fd5b505060065460048054600554604080516001600160a01b038d811660248301528c8116604483015260648083018d905283518084039091018152608490920183526020820180516001600160e01b031663042c38bb60e21b17905291516392b2c33560e01b815295821697506392b2c335965061023d959394919092169201610321565b600060405180830381600087803b15801561025757600080fd5b505af115801561026b573d6000803e3d6000fd5b5050604080516001600160a01b03808a16825288166020820152908101869052606081018590527fe98a754d57da22349279d2240bff334f71728546e2052a6d667080cf21d97d979250608001905060405180910390a15050505050565b80356001600160a01b03811681146102e057600080fd5b919050565b6000806000606084860312156102fa57600080fd5b610303846102c9565b9250610311602085016102c9565b9150604084013590509250925092565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b8181101561036357858101830151858201608001528201610347565b81811115610375576000608083870101525b50601f01601f19169290920160800195945050505050565b60006020828403121561039f57600080fd5b5051919050565b60008160001904831182151516156103ce57634e487b7160e01b600052601160045260246000fd5b50029056fea264697066735822122028076e9a0e19701fadb91f6ee6a1cee0186538d50ab3b0da3f12c35f686417cc64736f6c63430008090033";

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

    public String getABI_stockShipment(String _seller, String _buyer, BigInteger _quantity) {
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
