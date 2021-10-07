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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class HiddenParamDestTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161079538038061079583398101604081905261002f91610040565b60009290925560015560025561006e565b60008060006060848603121561005557600080fd5b8351925060208401519150604084015190509250925092565b6107188061007d6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80635eb6d419116100665780635eb6d419146100db5780638963b2bc146100ee578063c572b74e14610101578063e087b8aa14610114578063e840e56f1461012757600080fd5b80631a763bc31461009857806342b93855146100ad57806347484183146100c0578063587136f3146100c8575b600080fd5b6100ab6100a63660046105ee565b61012f565b005b6100ab6100bb366004610620565b6101a4565b6100ab6101c2565b6100ab6100d6366004610639565b610201565b6100ab6100e9366004610639565b610296565b6100ab6100fc36600461065b565b610359565b6100ab61010f366004610620565b610364565b6100ab610122366004610696565b6103e1565b6100ab6103ec565b61013a838383610406565b8360111461017c5760405162461bcd60e51b815260206004820152600a602482015269115c9c9bdc8e8815985b60b21b60448201526064015b60405180910390fd5b604051600181526000805160206106c38339815191529060200160405180910390a150505050565b60008060006101b16104f3565b92509250925061013a838383610406565b6000806101cd61052b565b915091506101db8282610553565b604051600181526000805160206106c38339815191529060200160405180910390a15050565b60008061020c61052b565b9150915061021a8282610553565b836011146102585760405162461bcd60e51b815260206004820152600b60248201526a4572726f723a2056616c3160a81b6044820152606401610173565b8260171461017c5760405162461bcd60e51b815260206004820152600b60248201526a22b93937b91d102b30b61960a91b6044820152606401610173565b60008060006102a36104f3565b9250925092506102b4838383610406565b846011146102f25760405162461bcd60e51b815260206004820152600b60248201526a4572726f723a2056616c3160a81b6044820152606401610173565b836017146103305760405162461bcd60e51b815260206004820152600b60248201526a22b93937b91d102b30b61960a91b6044820152606401610173565b604051600181526000805160206106c38339815191529060200160405180910390a15050505050565b6102b4838383610406565b60008061036f61052b565b9150915061037d8282610553565b826011146103ba5760405162461bcd60e51b815260206004820152600a602482015269115c9c9bdc8e8815985b60b21b6044820152606401610173565b604051600181526000805160206106c38339815191529060200160405180910390a1505050565b6103ba838383610406565b60008060006103f96104f3565b9250925092506103ba8383835b60005483146104515760405162461bcd60e51b8152602060048201526017602482015276119a5c9cdd081c185c985b481b9bdd0818dbdc9c9958dd604a1b6044820152606401610173565b600154821461049d5760405162461bcd60e51b815260206004820152601860248201527714d958dbdb99081c185c985b481b9bdd0818dbdc9c9958dd60421b6044820152606401610173565b60025481146104ee5760405162461bcd60e51b815260206004820152601760248201527f546869726420706172616d206e6f7420636f72726563740000000000000000006044820152606401610173565b505050565b600080808036806020605f19820184376000519550602060408203600037600051945060208082036000376000519350505050909192565b6000808036806020603f19820184376000519450602080820360003760005193505050509091565b600054821461059e5760405162461bcd60e51b8152602060048201526017602482015276119a5c9cdd081c185c985b481b9bdd0818dbdc9c9958dd604a1b6044820152606401610173565b60015481146105ea5760405162461bcd60e51b815260206004820152601860248201527714d958dbdb99081c185c985b481b9bdd0818dbdc9c9958dd60421b6044820152606401610173565b5050565b6000806000806080858703121561060457600080fd5b5050823594602084013594506040840135936060013592509050565b60006020828403121561063257600080fd5b5035919050565b6000806040838503121561064c57600080fd5b50508035926020909101359150565b600080600080600060a0868803121561067357600080fd5b505083359560208501359550604085013594606081013594506080013592509050565b6000806000606084860312156106ab57600080fd5b50508135936020830135935060409092013591905056fef7c630da6df086d6ed502f0fc2cb33c52db1e56e1aee68b2a159b6e011767377a2646970667358221220133b91382e24a6a7a19653378ab20cbe9f8f78ad1ba118ba4f7c3b86af9eaede64736f6c63430008090033";

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

    public RemoteFunctionCall<TransactionReceipt> funcNoParamsExplicit(BigInteger a1, BigInteger a2, BigInteger a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCNOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.generated.Uint256(a3)), 
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

    public RemoteFunctionCall<TransactionReceipt> funcOneParamExplicit(BigInteger _val, BigInteger a1, BigInteger a2, BigInteger a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCONEPARAMEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val), 
                new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.generated.Uint256(a3)), 
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

    public RemoteFunctionCall<TransactionReceipt> funcTwoParamsExplicit(BigInteger _val1, BigInteger _val2, BigInteger a1, BigInteger a2, BigInteger a3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FUNCTWOPARAMSEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2), 
                new org.web3j.abi.datatypes.generated.Uint256(a1), 
                new org.web3j.abi.datatypes.generated.Uint256(a2), 
                new org.web3j.abi.datatypes.generated.Uint256(a3)), 
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

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HiddenParamDestTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _expected1, BigInteger _expected2, BigInteger _expected3) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expected1), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected2), 
                new org.web3j.abi.datatypes.generated.Uint256(_expected3)));
        return deployRemoteCall(HiddenParamDestTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AllGoodEventResponse extends BaseEventResponse {
        public Boolean happy;
    }
}
