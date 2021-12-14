package net.consensys.gpact.txroot.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestReceipts extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610c3e806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630fe640261461006757806310ba2039146100905780632ae31cc0146100bb5780638feb676c14610090578063b401d83f146100d0578063e4e26223146100e3575b600080fd5b61007a610075366004610a4a565b610104565b6040516100879190610aa1565b60405180910390f35b6100a361009e366004610af6565b61011d565b6040516001600160a01b039091168152602001610087565b6100ce6100c9366004610b2b565b6101b1565b005b61007a6100de366004610b44565b6101e7565b6100f66100f1366004610af6565b61021f565b604051908152602001610087565b60606000610113858585610251565b9695505050505050565b60008061013161012c846103c0565b61040f565b905060006101588260038151811061014b5761014b610b92565b602002602001015161040f565b9050805160011461016857600080fd5b60006101808260008151811061014b5761014b610b92565b905060006101136101aa8360008151811061019d5761019d610b92565b60200260200101516104f0565b6014015190565b6040518181527f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2959060200160405180910390a150565b60606000610216847f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e29585610251565b95945050505050565b60008061022e61012c846103c0565b905060006102488260038151811061014b5761014b610b92565b51949350505050565b606080600061026261012c856103c0565b9050600061027c8260038151811061014b5761014b610b92565b905060005b815181101561035d5760006102a183838151811061014b5761014b610b92565b905060006102be6101aa8360008151811061019d5761019d610b92565b90506102d68260018151811061014b5761014b610b92565b96506102ee8260028151811061019d5761019d610b92565b955060006103158860008151811061030857610308610b92565b602002602001015161056d565b9050898114801561033757508a6001600160a01b0316826001600160a01b0316145b15610347575050505050506103b8565b505050808061035590610bbe565b915050610281565b5060405162461bcd60e51b815260206004820152602560248201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560448201526418d95a5c1d60da1b606482015260840160405180910390fd5b935093915050565b60408051808201909152600080825260208201528151806103f65750506040805180820190915260008082526020820152919050565b6040805180820190915260209384018152928301525090565b606061041a8261057e565b61042357600080fd5b600061042e836105a5565b90508067ffffffffffffffff811115610449576104496109a7565b60405190808252806020026020018201604052801561048e57816020015b60408051808201909152600080825260208201528152602001906001900390816104675790505b509150600061049c8461062d565b905060005b6104aa82610688565b156104e8576104b8826106ac565b8482815181106104ca576104ca610b92565b602002602001018190525080806104e090610bbe565b9150506104a1565b505050919050565b60606104fb82610706565b61050457600080fd5b6000806105108461072c565b90925090508067ffffffffffffffff81111561052e5761052e6109a7565b6040519080825280601f01601f191660200182016040528015610558576020820181803683370190505b5092506105668284836107d0565b5050919050565b60006105788261080e565b92915050565b600081602001516000141561059557506000919050565b50515160c060009190911a101590565b60006105b08261057e565b6105bc57506000919050565b81518051600090811a91906105d085610864565b6105da9083610bd9565b9050600060018660200151846105f09190610bd9565b6105fa9190610bf1565b905060005b8183116101135761060f836108f2565b6106199084610bd9565b92508061062581610bbe565b9150506105ff565b60408051608081018252600091810182815260608201839052815260208101919091526106598261057e565b61066257600080fd5b600061066d83610864565b83516106799190610bd9565b92825250602081019190915290565b8051602081015181516000929161069e91610bd9565b836020015110915050919050565b60408051808201909152600080825260208201526106c982610688565b1561006257602082015160006106de826108f2565b8284526020840181905290506106f48183610bd9565b6020850152506107019050565b919050565b600081602001516000141561071d57506000919050565b50515160c060009190911a1090565b60008061073883610706565b61074157600080fd5b8251805160001a90608082101561075d57946001945092505050565b60b882101561078b57600185602001516107779190610bf1565b9250610784816001610bd9565b93506107c9565b602085015160b61983019081906107a490600190610bf1565b6107ae9190610bf1565b93506107ba8183610bd9565b6107c5906001610bd9565b9450505b5050915091565b6020601f820104836020840160005b838110156107fb576020810283810151908301526001016107df565b5050505060008251602001830152505050565b600061081982610706565b61082257600080fd5b60008061082e8461072c565b9092509050602081111561084157600080fd5b80610850575060009392505050565b806020036101000a82510492505050919050565b600081602001516000141561087b57506000919050565b8151805160001a906080821015610896575060009392505050565b60b88210806108b1575060c082101580156108b1575060f882105b156108c0575060019392505050565b60c08210156108e7576108d460b783610bf1565b6108df906001610bd9565b949350505050565b6108d460f783610bf1565b8051600090811a608081101561090b576001915061098a565b60b88110156109315761091f608082610bf1565b61092a906001610bd9565b915061098a565b60c081101561095a57600183015160b76020839003016101000a9004810160b51901915061098a565b60f881101561096e5761091f60c082610bf1565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b80356001600160a01b038116811461070157600080fd5b634e487b7160e01b600052604160045260246000fd5b600082601f8301126109ce57600080fd5b813567ffffffffffffffff808211156109e9576109e96109a7565b604051601f8301601f19908116603f01168101908282118183101715610a1157610a116109a7565b81604052838152866020858801011115610a2a57600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600060608486031215610a5f57600080fd5b610a6884610990565b925060208401359150604084013567ffffffffffffffff811115610a8b57600080fd5b610a97868287016109bd565b9150509250925092565b600060208083528351808285015260005b81811015610ace57858101830151858201604001528201610ab2565b81811115610ae0576000604083870101525b50601f01601f1916929092016040019392505050565b600060208284031215610b0857600080fd5b813567ffffffffffffffff811115610b1f57600080fd5b6108df848285016109bd565b600060208284031215610b3d57600080fd5b5035919050565b60008060408385031215610b5757600080fd5b610b6083610990565b9150602083013567ffffffffffffffff811115610b7c57600080fd5b610b88858286016109bd565b9150509250929050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b6000600019821415610bd257610bd2610ba8565b5060010190565b60008219821115610bec57610bec610ba8565b500190565b600082821015610c0357610c03610ba8565b50039056fea26469706673582212203390cc55e97805220695e22538ce8ee9e6c2ee03fbd9d98280d4940b5e4ee24664736f6c634300080a0033";

    public static final String FUNC_EMITTINGCONTRACTFIRSTLOG = "emittingContractFirstLog";

    public static final String FUNC_GETEVENTSIGNATUREFIRSTLOG = "getEventSignatureFirstLog";

    public static final String FUNC_NUMLOGSFOUND = "numLogsFound";

    public static final String FUNC_RETRIEVEALOG = "retrieveALog";

    public static final String FUNC_RETRIEVESTARTLOG = "retrieveStartLog";

    public static final String FUNC_TRIGGERSTARTEVENT = "triggerStartEvent";

    public static final Event STARTEVENT_EVENT = new Event("StartEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected TestReceipts(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestReceipts(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestReceipts(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestReceipts(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<StartEventEventResponse> getStartEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STARTEVENT_EVENT, transactionReceipt);
        ArrayList<StartEventEventResponse> responses = new ArrayList<StartEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StartEventEventResponse typedResponse = new StartEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StartEventEventResponse> startEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StartEventEventResponse>() {
            @Override
            public StartEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STARTEVENT_EVENT, log);
                StartEventEventResponse typedResponse = new StartEventEventResponse();
                typedResponse.log = log;
                typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartEventEventResponse> startEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STARTEVENT_EVENT));
        return startEventEventFlowable(filter);
    }

    public RemoteFunctionCall<String> emittingContractFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EMITTINGCONTRACTFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getEventSignatureFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEVENTSIGNATUREFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> numLogsFound(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMLOGSFOUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> retrieveALog(String _contractAddress, byte[] _eventFunctionSignature, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVEALOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(_eventFunctionSignature), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> retrieveStartLog(String _contractAddress, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVESTARTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> triggerStartEvent(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERSTARTEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TestReceipts load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestReceipts(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestReceipts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestReceipts(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestReceipts load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestReceipts(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestReceipts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestReceipts(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestReceipts> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestReceipts.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestReceipts> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestReceipts.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TestReceipts> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestReceipts.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestReceipts> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestReceipts.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class StartEventEventResponse extends BaseEventResponse {
        public BigInteger _val;
    }
}
