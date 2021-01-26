package tech.pegasys.ltacfc.examples.singlebc.trade.soliditywrappers;

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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516102e63803806102e68339818101604052606081101561003357600080fd5b5080516020820151604090920151600080546001600160a01b039384166001600160a01b03199182161790915560018054948416948216949094179093556002805492909116919092161790556102578061008f6000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063d196c76e14610030575b600080fd5b6100666004803603606081101561004657600080fd5b506001600160a01b03813581169160208101359091169060400135610068565b005b60015460408051634c6afee560e11b815290516000926001600160a01b0316916398d5fdca916004808301926020929190829003018186803b1580156100ad57600080fd5b505afa1580156100c1573d6000803e3d6000fd5b505050506040513d60208110156100d757600080fd5b505160008054604080516317d5759960e31b81526001600160a01b03888116600483015289811660248301528786026044830181905292519596509194919092169263beabacc8926064808201939182900301818387803b15801561013b57600080fd5b505af115801561014f573d6000803e3d6000fd5b50506002546040805163042c38bb60e21b81526001600160a01b038a8116600483015289811660248301526044820189905291519190921693506310b0e2ec9250606480830192600092919082900301818387803b1580156101b057600080fd5b505af11580156101c4573d6000803e3d6000fd5b5050604080516001600160a01b03808a168252881660208201528082018790526060810186905290517fe98a754d57da22349279d2240bff334f71728546e2052a6d667080cf21d97d979350908190036080019150a1505050505056fea2646970667358221220fbf48bd9416028bb6767d8e724bd45421ecb74b9961e38a207042461bba8a92164736f6c63430007040033";

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

    public static RemoteCall<BusLogic> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _balances, String _oracle, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<BusLogic> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _balances, String _oracle, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BusLogic> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _balances, String _oracle, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
                new org.web3j.abi.datatypes.Address(160, _stock)));
        return deployRemoteCall(BusLogic.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BusLogic> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _balances, String _oracle, String _stock) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _balances), 
                new org.web3j.abi.datatypes.Address(160, _oracle), 
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
