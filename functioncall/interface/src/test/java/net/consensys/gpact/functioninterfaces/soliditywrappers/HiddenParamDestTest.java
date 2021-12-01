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
import org.web3j.abi.datatypes.Bool;
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
public class HiddenParamDestTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161095a38038061095a83398101604081905261002f9161005c565b60009290925560015560028054600160a060020a031916600160a060020a039092169190911790556100a2565b60008060006060848603121561007157600080fd5b8351602085015160408601519194509250600160a060020a038116811461009757600080fd5b809150509250925092565b6108a9806100b16000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c01000000000000000000000000000000000000000000000000000000009004806378c771711161008357806378c77171146100f85780639356747b1461010b578063c572b74e1461011e578063e1211e3414610131578063e840e56f1461014457600080fd5b806342b93855146100b557806347484183146100ca578063587136f3146100d25780635eb6d419146100e5575b600080fd5b6100c86100c3366004610732565b61014c565b005b6100c86101ea565b6100c86100e036600461074b565b610229565b6100c86100f336600461074b565b6102e8565b6100c8610106366004610796565b6103d5565b6100c86101193660046107cb565b610407565b6100c861012c366004610732565b610412565b6100c861013f36600461080a565b61047e565b6100c8610489565b60008060006101596104a7565b92509250925061016a8383836104df565b836011146101c25760405160e560020a62461bcd02815260206004820152600a60248201527f4572726f723a2056616c0000000000000000000000000000000000000000000060448201526064015b60405180910390fd5b604051600181526000805160206108548339815191529060200160405180910390a150505050565b6000806101f561061f565b915091506102038282610647565b604051600181526000805160206108548339815191529060200160405180910390a15050565b60008061023461061f565b915091506102428282610647565b836011146102955760405160e560020a62461bcd02815260206004820152600b60248201527f4572726f723a2056616c3100000000000000000000000000000000000000000060448201526064016101b9565b826017146101c25760405160e560020a62461bcd02815260206004820152600b60248201527f4572726f723a2056616c3200000000000000000000000000000000000000000060448201526064016101b9565b60008060006102f56104a7565b9250925092506103068383836104df565b846011146103595760405160e560020a62461bcd02815260206004820152600b60248201527f4572726f723a2056616c3100000000000000000000000000000000000000000060448201526064016101b9565b836017146103ac5760405160e560020a62461bcd02815260206004820152600b60248201527f4572726f723a2056616c3200000000000000000000000000000000000000000060448201526064016101b9565b604051600181526000805160206108548339815191529060200160405180910390a15050505050565b6103e08383836104df565b604051600181526000805160206108548339815191529060200160405180910390a1505050565b61016a8383836104df565b60008061041d61061f565b9150915061042b8282610647565b826011146103e05760405160e560020a62461bcd02815260206004820152600a60248201527f4572726f723a2056616c0000000000000000000000000000000000000000000060448201526064016101b9565b6103068383836104df565b60008060006104966104a7565b9250925092506103e08383836104df565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b60005483146105335760405160e560020a62461bcd02815260206004820152601760248201527f466972737420706172616d206e6f7420636f727265637400000000000000000060448201526064016101b9565b60015482146105875760405160e560020a62461bcd02815260206004820152601860248201527f5365636f6e6420706172616d206e6f7420636f7272656374000000000000000060448201526064016101b9565b60025473ffffffffffffffffffffffffffffffffffffffff82811691161461061a5760405160e560020a62461bcd02815260206004820152602560248201527f546869726420706172616d2028746865206164647265737329206e6f7420636f60448201527f727265637400000000000000000000000000000000000000000000000000000060648201526084016101b9565b505050565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b600054821461069b5760405160e560020a62461bcd02815260206004820152601760248201527f466972737420706172616d206e6f7420636f727265637400000000000000000060448201526064016101b9565b60025473ffffffffffffffffffffffffffffffffffffffff82811691161461072e5760405160e560020a62461bcd02815260206004820152602660248201527f5365636f6e6420706172616d2028746865206164647265737329206e6f74206360448201527f6f7272656374000000000000000000000000000000000000000000000000000060648201526084016101b9565b5050565b60006020828403121561074457600080fd5b5035919050565b6000806040838503121561075e57600080fd5b50508035926020909101359150565b803573ffffffffffffffffffffffffffffffffffffffff8116811461079157600080fd5b919050565b6000806000606084860312156107ab57600080fd5b83359250602084013591506107c26040850161076d565b90509250925092565b600080600080608085870312156107e157600080fd5b8435935060208501359250604085013591506107ff6060860161076d565b905092959194509250565b600080600080600060a0868803121561082257600080fd5b853594506020860135935060408601359250606086013591506108476080870161076d565b9050929550929590935056fef7c630da6df086d6ed502f0fc2cb33c52db1e56e1aee68b2a159b6e011767377a2646970667358221220dc99525c929c0219429b8bc490bfea1d318631602fd2629c3cd944ebbbee226364736f6c63430008090033";

    public static final String FUNC_FUNCNOPARAMS = "funcNoParams";

    public static final String FUNC_FUNCNOPARAMSEXPLICIT = "funcNoParamsExplicit";

    public static final String FUNC_FUNCONEPARAM = "funcOneParam";

    public static final String FUNC_FUNCONEPARAMEXPLICIT = "funcOneParamExplicit";

    public static final String FUNC_FUNCTWOPARAMS = "funcTwoParams";

    public static final String FUNC_FUNCTWOPARAMSEXPLICIT = "funcTwoParamsExplicit";

    public static final String FUNC_TWOPARAMFUNCNOPARAMS = "twoParamFuncNoParams";

    public static final String FUNC_TWOPARAMFUNCONEPARAM = "twoParamFuncOneParam";

    public static final String FUNC_TWOPARAMFUNCTWOPARAMS = "twoParamFuncTwoParams";

    public static final Event ALLGOOD_EVENT = new Event("AllGood", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected HiddenParamDestTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HiddenParamDestTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HiddenParamDestTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HiddenParamDestTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AllGoodEventResponse> getAllGoodEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ALLGOOD_EVENT, transactionReceipt);
        ArrayList<AllGoodEventResponse> responses = new ArrayList<AllGoodEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AllGoodEventResponse typedResponse = new AllGoodEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.happy = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AllGoodEventResponse> allGoodEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AllGoodEventResponse>() {
            @Override
            public AllGoodEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ALLGOOD_EVENT, log);
                AllGoodEventResponse typedResponse = new AllGoodEventResponse();
                typedResponse.log = log;
                typedResponse.happy = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AllGoodEventResponse> allGoodEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALLGOOD_EVENT));
        return allGoodEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> funcNoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCNOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> funcNoParamsExplicit(BigInteger _actualUint256_1, BigInteger _actualUint256_2, String _actualAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCNOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_actualUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_actualUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _actualAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> funcOneParam(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCONEPARAM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> funcOneParamExplicit(BigInteger _val, BigInteger _actualUint256_1, BigInteger actualUint256_2, String actualAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCONEPARAMEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val), 
                new org.web3j.abi.datatypes.generated.Uint256(_actualUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(actualUint256_2), 
                new org.web3j.abi.datatypes.Address(160, actualAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> funcTwoParams(BigInteger _val1, BigInteger _val2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCTWOPARAMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> funcTwoParamsExplicit(BigInteger _val1, BigInteger _val2, BigInteger _actualUint256_1, BigInteger _actualUint256_2, String _actualAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCTWOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2), 
                new org.web3j.abi.datatypes.generated.Uint256(_actualUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_actualUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _actualAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamFuncNoParams() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMFUNCNOPARAMS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamFuncOneParam(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMFUNCONEPARAM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> twoParamFuncTwoParams(BigInteger _val1, BigInteger _val2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TWOPARAMFUNCTWOPARAMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HiddenParamDestTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HiddenParamDestTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HiddenParamDestTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HiddenParamDestTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HiddenParamDestTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HiddenParamDestTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HiddenParamDestTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HiddenParamDestTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _expectedUint256_1, BigInteger _expectedUint256_2, String _expectedAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _expectedAddress)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _expectedUint256_1, BigInteger _expectedUint256_2, String _expectedAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _expectedAddress)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expectedUint256_1, BigInteger _expectedUint256_2, String _expectedAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _expectedAddress)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expectedUint256_1, BigInteger _expectedUint256_2, String _expectedAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expectedUint256_2), 
                new org.web3j.abi.datatypes.Address(160, _expectedAddress)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AllGoodEventResponse extends BaseEventResponse {
        public Boolean happy;
    }
}
