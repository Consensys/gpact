package net.consensys.gpact.examples.write.soliditywrappers;

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
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class ContractB extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161076038038061076083398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6106cd806100936000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806360fe47b11461005c5780637445b0d014610071578063e1cb0e52146100a4578063f6aacfb1146100ac578063fcea5096146100df575b600080fd5b61006f61006a366004610618565b6100f2565b005b61009161007f366004610618565b60056020526000908152604090205481565b6040519081526020015b60405180910390f35b610091610133565b6100cf6100ba366004610618565b60036020526000908152604090205460ff1681565b604051901515815260200161009b565b61006f6100ed3660046105e3565b610144565b6100fd600082610227565b6040518181527fbc9e8ea3b0777e6b9815f4c534668e08ddcaa8595a383ac46aebb40336ce81d79060200160405180910390a150565b600061013f6000610513565b905090565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b6000828152600460205260409020548110156102095760008281526004602052604081208054839081106101a5576101a5610673565b9060005260206000200154905085156101d4576000818152600260209081526040808320546001909252909120555b600090815260026020908152604080832083905560039091529020805460ff19169055806102018161064a565b91505061016f565b50600081815260046020526040812061022191610585565b50505050565b60008281526003602052604090205460ff16156102825760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064015b60405180910390fd5b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156102ce57600080fd5b505afa1580156102e2573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061030691906105bf565b1561031d5760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b15801561036c57600080fd5b505afa158015610380573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103a49190610631565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b1580156103f557600080fd5b505afa158015610409573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061042d9190610631565b6040805160208082018690528183018490528251808303840181526060909201909252805191012090915060009060008681526005602052604080822083905590549051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b1580156104b157600080fd5b505af11580156104c5573d6000803e3d6000fd5b50505060009182525060046020908152604080832080546001818101835591855283852001889055878452600283528184208790556003909252909120805460ff1916909117905550505050565b60008181526003602052604081205460ff16156105725760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610279565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906105a391906105a6565b50565b5b808211156105bb57600081556001016105a7565b5090565b6000602082840312156105d157600080fd5b81516105dc81610689565b9392505050565b6000806000606084860312156105f857600080fd5b833561060381610689565b95602085013595506040909401359392505050565b60006020828403121561062a57600080fd5b5035919050565b60006020828403121561064357600080fd5b5051919050565b600060001982141561066c57634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fd5b80151581146105a357600080fdfea2646970667358221220122e1f8ec4be6a7c744e7a1116fe4dd911cc9573284d6efc7a7a70e405401e9964736f6c63430008050033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

    public static final String FUNC_SET = "set";

    public static final Event VALUEWRITTEN_EVENT = new Event("ValueWritten", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ContractB(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractB(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractB(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractB(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ValueWrittenEventResponse> getValueWrittenEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VALUEWRITTEN_EVENT, transactionReceipt);
        ArrayList<ValueWrittenEventResponse> responses = new ArrayList<ValueWrittenEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValueWrittenEventResponse typedResponse = new ValueWrittenEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ValueWrittenEventResponse> valueWrittenEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ValueWrittenEventResponse>() {
            @Override
            public ValueWrittenEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VALUEWRITTEN_EVENT, log);
                ValueWrittenEventResponse typedResponse = new ValueWrittenEventResponse();
                typedResponse.log = log;
                typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ValueWrittenEventResponse> valueWrittenEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEWRITTEN_EVENT));
        return valueWrittenEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> set(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_set(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static ContractB load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractB(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractB load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractB(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractB load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContractB(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractB load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContractB(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractB> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(ContractB.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ContractB> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(ContractB.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractB> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(ContractB.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractB> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(ContractB.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ValueWrittenEventResponse extends BaseEventResponse {
        public BigInteger _val;
    }
}
