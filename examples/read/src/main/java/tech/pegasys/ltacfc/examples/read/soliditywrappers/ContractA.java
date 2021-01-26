package tech.pegasys.ltacfc.examples.read.soliditywrappers;

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
public class ContractA extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161057c38038061057c8339818101604052608081101561003357600080fd5b50805160208201516040830151606090930151600080546001600160a01b039283166001600160a01b0319918216179091556003805494831694821694909417909355600191909155600280549190931691161790556104e4806100986000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630b4ed8131461004657806311ce026714610050578063e1cb0e5214610074575b600080fd5b61004e61008e565b005b6100586101ec565b604080516001600160a01b039092168252519081900360200190f35b61007c6101fb565b60408051918252519081900360200190f35b600354600154600254604080516004808252602480830184526020830180516001600160e01b0316631b53398f60e21b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b8381101561012f578181015183820152602001610117565b50505050905090810190601f16801561015c5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561017d57600080fd5b505af1158015610191573d6000803e3d6000fd5b505050506040513d60208110156101a757600080fd5b505190506101b660008261020d565b6040805182815290517fabdacda18abdb744b5ea95790fd361f37d4454a3e3269814cf272e2bafb7503d9181900360200190a150565b6000546001600160a01b031681565b60006102076000610384565b90505b90565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561025c57600080fd5b505af192505050801561026d575060015b61038057610279610409565b806102845750610309565b8060405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b838110156102ce5781810151838201526020016102b6565b50505050905090810190601f1680156102fb5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b3d808015610333576040519150601f19603f3d011682016040523d82523d6000602084013e610338565b606091505b5060405162461bcd60e51b81526020600482018181528351602484015283518493919283926044019190850190808383600083156102ce5781810151838201526020016102b6565b5050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156103d157600080fd5b505afa1580156103e5573d6000803e3d6000fd5b505050506040513d60208110156103fb57600080fd5b505192915050565b60e01c90565b600060443d10156104195761020a565b600481823e6308c379a061042d8251610403565b146104375761020a565b6040513d600319016004823e80513d67ffffffffffffffff8160248401118184111715610467575050505061020a565b82840192508251915080821115610481575050505061020a565b503d830160208284010111156104995750505061020a565b601f01601f191681016020016040529150509056fea264697066735822122030f975cee8111eef445c76a07c7db7500c641f10ada8fd168d85c1e71a085be064736f6c63430007040033";

    public static final String FUNC_DOCROSSCHAINREAD = "doCrosschainRead";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    public static final Event VALUEREAD_EVENT = new Event("ValueRead", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ContractA(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractA(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractA(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractA(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ValueReadEventResponse> getValueReadEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VALUEREAD_EVENT, transactionReceipt);
        ArrayList<ValueReadEventResponse> responses = new ArrayList<ValueReadEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValueReadEventResponse typedResponse = new ValueReadEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ValueReadEventResponse> valueReadEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ValueReadEventResponse>() {
            @Override
            public ValueReadEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VALUEREAD_EVENT, log);
                ValueReadEventResponse typedResponse = new ValueReadEventResponse();
                typedResponse.log = log;
                typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ValueReadEventResponse> valueReadEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEREAD_EVENT));
        return valueReadEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> doCrosschainRead() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DOCROSSCHAINREAD, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_doCrosschainRead() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DOCROSSCHAINREAD, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<String> storageContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_storageContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static ContractA load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractA(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractA load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractA(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractA load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContractA(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractA load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContractA(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractA> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc, BigInteger _otherBcId, String _contractBAddress, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(ContractA.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ContractA> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc, BigInteger _otherBcId, String _contractBAddress, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(ContractA.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractA> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _otherBcId, String _contractBAddress, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(ContractA.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractA> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _otherBcId, String _contractBAddress, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(ContractA.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ValueReadEventResponse extends BaseEventResponse {
        public BigInteger _val;
    }
}
