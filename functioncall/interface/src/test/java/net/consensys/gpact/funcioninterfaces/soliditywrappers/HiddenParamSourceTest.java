package net.consensys.gpact.funcioninterfaces.soliditywrappers;

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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class HiddenParamSourceTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516107ea3803806107ea83398101604081905261002f91610088565b600080546001600160a01b039586166001600160a01b031991821617909155600193909355600291909155600380549190931691161790556100ce565b80516001600160a01b038116811461008357600080fd5b919050565b6000806000806080858703121561009e57600080fd5b6100a78561006c565b935060208501519250604085015191506100c36060860161006c565b905092959194509250565b61070d806100dd6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80637ce388e6116100665780637ce388e6146100ba5780638b2acaf1146100c2578063d8ffd063146100ca578063dd494307146100d2578063f42438a0146100da57600080fd5b806302175e85146100985780633893d940146100a2578063452d9f25146100aa57806362a46188146100b2575b600080fd5b6100a06100e2565b005b6100a0610164565b6100a06101bd565b6100a06101f1565b6100a0610243565b6100a061028e565b6100a06102c0565b6100a06102e6565b6100a0610338565b600054600154600254600354604051639356747b60e01b815260116004820152602481019390935260448301919091526001600160a01b03908116606483015290911690639356747b906084015b600060405180830381600087803b15801561014a57600080fd5b505af115801561015e573d6000803e3d6000fd5b50505050565b600054600154600254600354604051633848478d60e21b81526011600482015260176024820152604481019390935260648301919091526001600160a01b0390811660848301529091169063e1211e349060a401610130565b6040805160048152602481019091526020810180516001600160e01b031663e840e56f60e01b1790526101ef9061035e565b565b604051601160248201526101ef906362b95ba760e11b906044015b60408051601f198184030181529190526020810180516001600160e01b03166001600160e01b03199093169290921790915261041a565b6000546001546002546003546040516378c7717160e01b8152600481019390935260248301919091526001600160a01b039081166044830152909116906378c7717190606401610130565b6040805160048152602481019091526020810180516001600160e01b0316634748418360e01b1790526101ef9061041a565b60405160116024820152601760448201526101ef9063587136f360e01b9060640161020c565b604051601160248201526101ef906342b9385560e01b906044015b60408051601f198184030181529190526020810180516001600160e01b03166001600160e01b03199093169290921790915261035e565b60405160116024820152601760448201526101ef90635eb6d41960e01b90606401610301565b60015460025460035460009261037e9285926001600160a01b0316610438565b6000805460405192935090916060916001600160a01b0316906103a2908590610596565b6000604051808303816000865af19150503d80600081146103df576040519150601f19603f3d011682016040523d82523d6000602084013e6103e4565b606091505b5090925090508161015e576103f88161049e565b60405162461bcd60e51b815260040161041191906105b2565b60405180910390fd5b60015460035460009161037e918491906001600160a01b0316610503565b6040805160208101859052908101839052606082811b6bffffffffffffffffffffffff19168183015290859060740160408051601f198184030181529082905261048592916020016105e5565b6040516020818303038152906040529050949350505050565b60606044825110156104e357505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b600482019150818060200190518101906104fd919061062a565b92915050565b606083838360405160200161053492919091825260601b6bffffffffffffffffffffffff1916602082015260340190565b60408051601f198184030181529082905261055292916020016105e5565b60405160208183030381529060405290509392505050565b60005b8381101561058557818101518382015260200161056d565b8381111561015e5750506000910152565b600082516105a881846020870161056a565b9190910192915050565b60208152600082518060208401526105d181604085016020870161056a565b601f01601f19169190910160400192915050565b600083516105f781846020880161056a565b83519083019061060b81836020880161056a565b01949350505050565b634e487b7160e01b600052604160045260246000fd5b60006020828403121561063c57600080fd5b815167ffffffffffffffff8082111561065457600080fd5b818401915084601f83011261066857600080fd5b81518181111561067a5761067a610614565b604051601f8201601f19908116603f011681019083821181831017156106a2576106a2610614565b816040528281528760208487010111156106bb57600080fd5b6106cc83602083016020880161056a565b97965050505050505056fea26469706673582212202216aa8df9d5ee4b506b9769f39384082d78dabd2141bd892e8d914fab3fff5d64736f6c63430008090033";

    public static final String FUNC_CALLFUNCNOPARAMS = "callFuncNoParams";

    public static final String FUNC_CALLFUNCNOPARAMSEXPLICIT = "callFuncNoParamsExplicit";

    public static final String FUNC_CALLFUNCONEPARAM = "callFuncOneParam";

    public static final String FUNC_CALLFUNCONEPARAMEXPLICIT = "callFuncOneParamExplicit";

    public static final String FUNC_CALLFUNCTWOPARAMS = "callFuncTwoParams";

    public static final String FUNC_CALLFUNCTWOPARAMSEXPLICIT = "callFuncTwoParamsExplicit";

    public static final String FUNC_TWOPARAMCALLFUNCNOPARAMS = "twoParamCallFuncNoParams";

    public static final String FUNC_TWOPARAMCALLFUNCONEPARAM = "twoParamCallFuncOneParam";

    public static final String FUNC_TWOPARAMCALLFUNCTWOPARAMS = "twoParamCallFuncTwoParams";

    public static final Event DUMP_EVENT = new Event("Dump", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected HiddenParamSourceTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HiddenParamSourceTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HiddenParamSourceTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HiddenParamSourceTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DumpEventResponse> getDumpEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DUMP_EVENT, transactionReceipt);
        ArrayList<DumpEventResponse> responses = new ArrayList<DumpEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DumpEventResponse typedResponse = new DumpEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._b = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DumpEventResponse> dumpEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DumpEventResponse>() {
            @Override
            public DumpEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DUMP_EVENT, log);
                DumpEventResponse typedResponse = new DumpEventResponse();
                typedResponse.log = log;
                typedResponse._b = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DumpEventResponse> dumpEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DUMP_EVENT));
        return dumpEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncNoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCNOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncNoParamsExplicit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCNOPARAMSEXPLICIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncOneParam() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCONEPARAM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncOneParamExplicit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCONEPARAMEXPLICIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncTwoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCTWOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> callFuncTwoParamsExplicit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLFUNCTWOPARAMSEXPLICIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamCallFuncNoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMCALLFUNCNOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamCallFuncOneParam() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMCALLFUNCONEPARAM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamCallFuncTwoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMCALLFUNCTWOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HiddenParamSourceTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HiddenParamSourceTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HiddenParamSourceTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HiddenParamSourceTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HiddenParamSourceTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HiddenParamSourceTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HiddenParamSourceTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HiddenParamSourceTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _dest, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _dest, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _dest, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _dest, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DumpEventResponse extends BaseEventResponse {
        public byte[] _b;
    }
}
