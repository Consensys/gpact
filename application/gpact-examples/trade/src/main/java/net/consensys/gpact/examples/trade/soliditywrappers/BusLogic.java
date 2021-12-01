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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class BusLogic extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161068e38038061068e83398101604081905261002f916100ac565b60068054600160a060020a03988916600160a060020a0319918216179091556000969096556001805495881695871695909517909455600292909255600380549186169185169190911790556004556005805491909316911617905561011d565b8051600160a060020a03811681146100a757600080fd5b919050565b600080600080600080600060e0888a0312156100c757600080fd5b6100d088610090565b9650602088015195506100e560408901610090565b9450606088015193506100fa60808901610090565b925060a0880151915061010f60c08901610090565b905092959891949750929550565b6105628061012c6000396000f3fe608060405234801561001057600080fd5b5060043610610047577c01000000000000000000000000000000000000000000000000000000006000350463d196c76e811461004c575b600080fd5b61005f61005a366004610417565b610061565b005b6006546002546003546040805160048082526024820183526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f98d5fdca0000000000000000000000000000000000000000000000000000000017905291517f8e22d53400000000000000000000000000000000000000000000000000000000815260009573ffffffffffffffffffffffffffffffffffffffff90811695638e22d5349561011795919492169201610453565b602060405180830381600087803b15801561013157600080fd5b505af1158015610145573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061016991906104cd565b9050600061017783836104e6565b6006546000546001546040805173ffffffffffffffffffffffffffffffffffffffff8a811660248301528b81166044830152606480830188905283518084039091018152608490920183526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167fbeabacc80000000000000000000000000000000000000000000000000000000017905291517f92b2c335000000000000000000000000000000000000000000000000000000008152959650938116946392b2c3359461024f9493929092169190600401610453565b600060405180830381600087803b15801561026957600080fd5b505af115801561027d573d6000803e3d6000fd5b5050600654600480546005546040805173ffffffffffffffffffffffffffffffffffffffff8d811660248301528c8116604483015260648083018d905283518084039091018152608490920183526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f10b0e2ec0000000000000000000000000000000000000000000000000000000017905291517f92b2c33500000000000000000000000000000000000000000000000000000000815295821697506392b2c3359650610355959394919092169201610453565b600060405180830381600087803b15801561036f57600080fd5b505af1158015610383573d6000803e3d6000fd5b50506040805173ffffffffffffffffffffffffffffffffffffffff808a16825288166020820152908101869052606081018590527fe98a754d57da22349279d2240bff334f71728546e2052a6d667080cf21d97d979250608001905060405180910390a15050505050565b803573ffffffffffffffffffffffffffffffffffffffff8116811461041257600080fd5b919050565b60008060006060848603121561042c57600080fd5b610435846103ee565b9250610443602085016103ee565b9150604084013590509250925092565b8381526000602073ffffffffffffffffffffffffffffffffffffffff85168184015260606040840152835180606085015260005b818110156104a357858101830151858201608001528201610487565b818111156104b5576000608083870101525b50601f01601f19169290920160800195945050505050565b6000602082840312156104df57600080fd5b5051919050565b6000816000190483118215151615610527577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b50029056fea26469706673582212204ec1f903517afd55d629142529239ba2719b52c054f5d4a6c82742be32a55b8364736f6c63430008090033";

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
