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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class HiddenParamDestTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161082538038061082583398101604081905261002f9161005c565b600092909255600155600280546001600160a01b0319166001600160a01b039092169190911790556100a2565b60008060006060848603121561007157600080fd5b83516020850151604086015191945092506001600160a01b038116811461009757600080fd5b809150509250925092565b610774806100b16000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806378c771711161006657806378c77171146100db5780639356747b146100ee578063c572b74e14610101578063e1211e3414610114578063e840e56f1461012757600080fd5b806342b938551461009857806347484183146100ad578063587136f3146100b55780635eb6d419146100c8575b600080fd5b6100ab6100a636600461060a565b61012f565b005b6100ab6101b7565b6100ab6100c3366004610623565b6101f6565b6100ab6100d6366004610623565b61028b565b6100ab6100e9366004610661565b61034e565b6100ab6100fc366004610696565b610380565b6100ab61010f36600461060a565b61038b565b6100ab6101223660046106d5565b6103e1565b6100ab6103ec565b600080600061013c61040a565b92509250925061014d838383610442565b8360111461018f5760405162461bcd60e51b815260206004820152600a602482015269115c9c9bdc8e8815985b60b21b60448201526064015b60405180910390fd5b6040516001815260008051602061071f8339815191529060200160405180910390a150505050565b6000806101c261053b565b915091506101d08282610563565b6040516001815260008051602061071f8339815191529060200160405180910390a15050565b60008061020161053b565b9150915061020f8282610563565b8360111461024d5760405162461bcd60e51b815260206004820152600b60248201526a4572726f723a2056616c3160a81b6044820152606401610186565b8260171461018f5760405162461bcd60e51b815260206004820152600b60248201526a22b93937b91d102b30b61960a91b6044820152606401610186565b600080600061029861040a565b9250925092506102a9838383610442565b846011146102e75760405162461bcd60e51b815260206004820152600b60248201526a4572726f723a2056616c3160a81b6044820152606401610186565b836017146103255760405162461bcd60e51b815260206004820152600b60248201526a22b93937b91d102b30b61960a91b6044820152606401610186565b6040516001815260008051602061071f8339815191529060200160405180910390a15050505050565b610359838383610442565b6040516001815260008051602061071f8339815191529060200160405180910390a1505050565b61014d838383610442565b60008061039661053b565b915091506103a48282610563565b826011146103595760405162461bcd60e51b815260206004820152600a602482015269115c9c9bdc8e8815985b60b21b6044820152606401610186565b6102a9838383610442565b60008060006103f961040a565b925092509250610359838383610442565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b600054831461048d5760405162461bcd60e51b8152602060048201526017602482015276119a5c9cdd081c185c985b481b9bdd0818dbdc9c9958dd604a1b6044820152606401610186565b60015482146104d95760405162461bcd60e51b815260206004820152601860248201527714d958dbdb99081c185c985b481b9bdd0818dbdc9c9958dd60421b6044820152606401610186565b6002546001600160a01b038281169116146105365760405162461bcd60e51b815260206004820152601760248201527f546869726420706172616d206e6f7420636f72726563740000000000000000006044820152606401610186565b505050565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b60005482146105ae5760405162461bcd60e51b8152602060048201526017602482015276119a5c9cdd081c185c985b481b9bdd0818dbdc9c9958dd604a1b6044820152606401610186565b6002546001600160a01b038281169116146106065760405162461bcd60e51b815260206004820152601860248201527714d958dbdb99081c185c985b481b9bdd0818dbdc9c9958dd60421b6044820152606401610186565b5050565b60006020828403121561061c57600080fd5b5035919050565b6000806040838503121561063657600080fd5b50508035926020909101359150565b80356001600160a01b038116811461065c57600080fd5b919050565b60008060006060848603121561067657600080fd5b833592506020840135915061068d60408501610645565b90509250925092565b600080600080608085870312156106ac57600080fd5b8435935060208501359250604085013591506106ca60608601610645565b905092959194509250565b600080600080600060a086880312156106ed57600080fd5b8535945060208601359350604086013592506060860135915061071260808701610645565b9050929550929590935056fef7c630da6df086d6ed502f0fc2cb33c52db1e56e1aee68b2a159b6e011767377a2646970667358221220236cdc0d7ea37363936953d884069f89c54ca5c57761759eaedfe3b42256e08364736f6c63430008090033";

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

    public RemoteFunctionCall<TransactionReceipt> funcNoParamsExplicit(BigInteger a1, BigInteger a2, String a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCNOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.Address(160, a3)), 
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

    public RemoteFunctionCall<TransactionReceipt> funcOneParamExplicit(BigInteger _val, BigInteger a1, BigInteger a2, String a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCONEPARAMEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val), 
                new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.Address(160, a3)), 
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

    public RemoteFunctionCall<TransactionReceipt> funcTwoParamsExplicit(BigInteger _val1, BigInteger _val2, BigInteger a1, BigInteger a2, String a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCTWOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2), 
                new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.Address(160, a3)), 
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

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expected1, BigInteger _expected2, String _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.Address(160, _expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AllGoodEventResponse extends BaseEventResponse {
        public Boolean happy;
    }
}
