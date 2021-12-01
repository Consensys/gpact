package net.consensys.gpact.functioninterfaces.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class HiddenParamSourceTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161095238038061095283398101604081905261002f91610088565b60008054600160a060020a03958616600160a060020a031991821617909155600193909355600291909155600380549190931691161790556100ce565b8051600160a060020a038116811461008357600080fd5b919050565b6000806000806080858703121561009e57600080fd5b6100a78561006c565b935060208501519250604085015191506100c36060860161006c565b905092959194509250565b610875806100dd6000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c0100000000000000000000000000000000000000000000000000000000900480637ce388e6116100835780637ce388e6146100d75780638b2acaf1146100df578063d8ffd063146100e7578063dd494307146100ef578063f42438a0146100f757600080fd5b806302175e85146100b55780633893d940146100bf578063452d9f25146100c757806362a46188146100cf575b600080fd5b6100bd6100ff565b005b6100bd61019a565b6100bd61020c565b6100bd610259565b6100bd6102d9565b6100bd61033d565b6100bd610388565b6100bd6103c7565b6100bd610447565b6000546001546002546003546040517f9356747b0000000000000000000000000000000000000000000000000000000081526011600482015260248101939093526044830191909152600160a060020a03908116606483015290911690639356747b906084015b600060405180830381600087803b15801561018057600080fd5b505af1158015610194573d6000803e3d6000fd5b50505050565b6000546001546002546003546040517fe1211e34000000000000000000000000000000000000000000000000000000008152601160048201526017602482015260448101939093526064830191909152600160a060020a0390811660848301529091169063e1211e349060a401610166565b604080516004815260248101909152602081018051600160e060020a03167fe840e56f0000000000000000000000000000000000000000000000000000000017905261025790610486565b565b60405160116024820152610257907fc572b74e00000000000000000000000000000000000000000000000000000000906044015b60408051601f19818403018152919052602081018051600160e060020a03167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff199093169290921790915261055c565b6000546001546002546003546040517f78c7717100000000000000000000000000000000000000000000000000000000815260048101939093526024830191909152600160a060020a039081166044830152909116906378c7717190606401610166565b604080516004815260248101909152602081018051600160e060020a03167f47484183000000000000000000000000000000000000000000000000000000001790526102579061055c565b6040516011602482015260176044820152610257907f587136f3000000000000000000000000000000000000000000000000000000009060640161028d565b60405160116024820152610257907f42b9385500000000000000000000000000000000000000000000000000000000906044015b60408051601f19818403018152919052602081018051600160e060020a03167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1990931692909217909152610486565b6040516011602482015260176044820152610257907f5eb6d41900000000000000000000000000000000000000000000000000000000906064016103fb565b6001546002546003546000926104a6928592600160a060020a031661057a565b600080546040519293509091606091600160a060020a0316906104ca9085906106e5565b6000604051808303816000865af19150503d8060008114610507576040519150601f19603f3d011682016040523d82523d6000602084013e61050c565b606091505b5090925090508161019457610520816105e7565b6040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105539190610701565b60405180910390fd5b6001546003546000916104a691849190600160a060020a031661064c565b6040805160208101859052808201849052600160a060020a0383166c01000000000000000000000000026060808301919091528251605481840301815260748301909352916105ce91879190609401610734565b6040516020818303038152906040529050949350505050565b606060448251101561062c57505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b600482019150818060200190518101906106469190610792565b92915050565b6060838383604051602001610683929190918252600160a060020a03166c0100000000000000000000000002602082015260340190565b60408051601f19818403018152908290526106a19291602001610734565b60405160208183030381529060405290509392505050565b60005b838110156106d45781810151838201526020016106bc565b838111156101945750506000910152565b600082516106f78184602087016106b9565b9190910192915050565b60208152600082518060208401526107208160408501602087016106b9565b601f01601f19169190910160400192915050565b600083516107468184602088016106b9565b83519083019061075a8183602088016106b9565b01949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000602082840312156107a457600080fd5b815167ffffffffffffffff808211156107bc57600080fd5b818401915084601f8301126107d057600080fd5b8151818111156107e2576107e2610763565b604051601f8201601f19908116603f0116810190838211818310171561080a5761080a610763565b8160405282815287602084870101111561082357600080fd5b6108348360208301602088016106b9565b97965050505050505056fea2646970667358221220ed5839f5042ffaef02238a6c540158e09527c759db8fb9f1a01172659bbc2e9c64736f6c63430008090033";

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
