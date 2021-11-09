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
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161078038038061078083398101604081905261002f91610061565b600080546001600160a01b0319166001600160a01b0395909516949094179093556001919091556002556003556100ac565b6000806000806080858703121561007757600080fd5b84516001600160a01b038116811461008e57600080fd5b60208601516040870151606090970151919890975090945092505050565b6106c5806100bb6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80637ce388e6116100665780637ce388e6146100ba5780638b2acaf1146100c2578063d8ffd063146100ca578063dd494307146100d2578063f42438a0146100da57600080fd5b806302175e85146100985780633893d940146100a2578063452d9f25146100aa57806362a46188146100b2575b600080fd5b6100a06100e2565b005b6100a0610161565b6100a06101b7565b6100a06101eb565b6100a061023d565b6100a0610285565b6100a06102b7565b6100a06102dd565b6100a061032f565b600054600154600254600354604051631a763bc360e01b8152601160048201526024810193909352604483019190915260648201526001600160a01b0390911690631a763bc3906084015b600060405180830381600087803b15801561014757600080fd5b505af115801561015b573d6000803e3d6000fd5b50505050565b600054600154600254600354604051632258ecaf60e21b815260116004820152601760248201526044810193909352606483019190915260848201526001600160a01b0390911690638963b2bc9060a40161012d565b6040805160048152602481019091526020810180516001600160e01b031663e840e56f60e01b1790526101e990610355565b565b604051601160248201526101e9906362b95ba760e11b906044015b60408051601f198184030181529190526020810180516001600160e01b03166001600160e01b031990931692909217909152610405565b600054600154600254600354604051637043dc5560e11b81526004810193909352602483019190915260448201526001600160a01b039091169063e087b8aa9060640161012d565b6040805160048152602481019091526020810180516001600160e01b0316634748418360e01b1790526101e990610405565b60405160116024820152601760448201526101e99063587136f360e01b90606401610206565b604051601160248201526101e9906342b9385560e01b906044015b60408051601f198184030181529190526020810180516001600160e01b03166001600160e01b031990931692909217909152610355565b60405160116024820152601760448201526101e990635eb6d41960e01b906064016102f8565b600061036982600154600254600354610416565b6000805460405192935090916060916001600160a01b03169061038d90859061054e565b6000604051808303816000865af19150503d80600081146103ca576040519150601f19603f3d011682016040523d82523d6000602084013e6103cf565b606091505b5090925090508161015b576103e381610468565b60405162461bcd60e51b81526004016103fc919061056a565b60405180910390fd5b6000610369826001546002546104cd565b60408051602081018590528082018490526060808201849052825180830382018152608083019093529161044f9187919060a00161059d565b6040516020818303038152906040529050949350505050565b60606044825110156104ad57505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b600482019150818060200190518101906104c791906105e2565b92915050565b60608383836040516020016104ec929190918252602082015260400190565b60408051601f198184030181529082905261050a929160200161059d565b60405160208183030381529060405290509392505050565b60005b8381101561053d578181015183820152602001610525565b8381111561015b5750506000910152565b60008251610560818460208701610522565b9190910192915050565b6020815260008251806020840152610589816040850160208701610522565b601f01601f19169190910160400192915050565b600083516105af818460208801610522565b8351908301906105c3818360208801610522565b01949350505050565b634e487b7160e01b600052604160045260246000fd5b6000602082840312156105f457600080fd5b815167ffffffffffffffff8082111561060c57600080fd5b818401915084601f83011261062057600080fd5b815181811115610632576106326105cc565b604051601f8201601f19908116603f0116810190838211818310171561065a5761065a6105cc565b8160405282815287602084870101111561067357600080fd5b610684836020830160208801610522565b97965050505050505056fea264697066735822122078d34f53c8d8fc0de5f7cdebe7960671729ae1d4c813aff3773abad62478049064736f6c63430008090033";

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

    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _dest, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _dest, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _dest, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamSourceTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _dest, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dest), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamSourceTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DumpEventResponse extends BaseEventResponse {
        public byte[] _b;
    }
}
