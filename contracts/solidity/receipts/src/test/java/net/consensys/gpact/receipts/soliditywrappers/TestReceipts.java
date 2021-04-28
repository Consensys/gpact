package net.consensys.gpact.receipts.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestReceipts extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610cf0806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630fe640261461006757806310ba2039146100905780632ae31cc0146100bb5780638feb676c14610090578063b401d83f146100d0578063e4e26223146100e3575b600080fd5b61007a610075366004610b05565b610104565b6040516100879190610bf1565b60405180910390f35b6100a361009e366004610ba6565b61011d565b6040516001600160a01b039091168152602001610087565b6100ce6100c9366004610bd9565b6101e7565b005b61007a6100de366004610b5a565b61021d565b6100f66100f1366004610ba6565b610255565b604051908152602001610087565b60606000610113858585610295565b9695505050505050565b60008061013161012c84610458565b6104a7565b905060006101668260038151811061015957634e487b7160e01b600052603260045260246000fd5b60200260200101516104a7565b9050805160011461017657600080fd5b600061019c8260008151811061015957634e487b7160e01b600052603260045260246000fd5b905060006101db6101d4836000815181106101c757634e487b7160e01b600052603260045260246000fd5b60200260200101516105a4565b6014015190565b9450505050505b919050565b6040518181527f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2959060200160405180910390a150565b6060600061024c847f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e29585610295565b95945050505050565b60008061026461012c84610458565b9050600061028c8260038151811061015957634e487b7160e01b600052603260045260246000fd5b51949350505050565b60608060006102a661012c85610458565b905060006102ce8260038151811061015957634e487b7160e01b600052603260045260246000fd5b905060005b81518110156103f557600061030183838151811061015957634e487b7160e01b600052603260045260246000fd5b9050600061032c6101d4836000815181106101c757634e487b7160e01b600052603260045260246000fd5b90506103528260018151811061015957634e487b7160e01b600052603260045260246000fd5b9650610378826002815181106101c757634e487b7160e01b600052603260045260246000fd5b955060006103ad886000815181106103a057634e487b7160e01b600052603260045260246000fd5b602002602001015161062f565b905089811480156103cf57508a6001600160a01b0316826001600160a01b0316145b156103df57505050505050610450565b50505080806103ed90610c73565b9150506102d3565b5060405162461bcd60e51b815260206004820152602560248201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560448201526418d95a5c1d60da1b606482015260840160405180910390fd5b935093915050565b604080518082019091526000808252602082015281518061048e57505060408051808201909152600080825260208201526101e2565b6040805180820190915260209384018152928301525090565b60606104b282610640565b6104bb57600080fd5b60006104c683610667565b90508067ffffffffffffffff8111156104ef57634e487b7160e01b600052604160045260246000fd5b60405190808252806020026020018201604052801561053457816020015b604080518082019091526000808252602082015281526020019060019003908161050d5790505b5091506000610542846106ef565b905060005b6105508261074a565b1561059c5761055e8261076e565b84828151811061057e57634e487b7160e01b600052603260045260246000fd5b6020026020010181905250808061059490610c73565b915050610547565b505050919050565b60606105af826107c3565b6105b857600080fd5b6000806105c4846107e9565b90925090508067ffffffffffffffff8111156105f057634e487b7160e01b600052604160045260246000fd5b6040519080825280601f01601f19166020018201604052801561061a576020820181803683370190505b509250610628828483610890565b5050919050565b600061063a826108ce565b92915050565b6000816020015160001415610657575060006101e2565b50515160c060009190911a101590565b600061067282610640565b61067e575060006101e2565b81518051600090811a919061069285610925565b61069c9083610c44565b9050600060018660200151846106b29190610c44565b6106bc9190610c5c565b905060005b8183116101db576106d1836109c9565b6106db9084610c44565b9250806106e781610c73565b9150506106c1565b604080516080810182526000918101828152606082018390528152602081019190915261071b82610640565b61072457600080fd5b600061072f83610925565b835161073b9190610c44565b92825250602081019190915290565b8051602081015181516000929161076091610c44565b836020015110915050919050565b604080518082019091526000808252602082015261078b8261074a565b1561006257602082015160006107a0826109c9565b8284526020840181905290506107b68183610c44565b6020850152506101e29050565b60008160200151600014156107da575060006101e2565b50515160c060009190911a1090565b6000806107f5836107c3565b6107fe57600080fd5b8251805160001a90608082101561081c5792506001915061088b9050565b60b882101561084a57600185602001516108369190610c5c565b9250610843816001610c44565b9350610888565b602085015160b619830190819061086390600190610c5c565b61086d9190610c5c565b93506108798183610c44565b610884906001610c44565b9450505b50505b915091565b6020601f820104836020840160005b838110156108bb5760208102838101519083015260010161089f565b5050505060008251602001830152505050565b60006108d9826107c3565b6108e257600080fd5b6000806108ee846107e9565b9092509050602081111561090157600080fd5b80610911576000925050506101e2565b806020036101000a82510492505050919050565b600081602001516000141561093c575060006101e2565b8151805160001a906080821015610958576000925050506101e2565b60b8821080610973575060c08210158015610973575060f882105b15610983576001925050506101e2565b60c08210156109ab5761099760b783610c5c565b6109a2906001610c44565b925050506101e2565b6109b660f783610c5c565b6109c1906001610c44565b949350505050565b8051600090811a60808110156109e25760019150610a61565b60b8811015610a08576109f6608082610c5c565b610a01906001610c44565b9150610a61565b60c0811015610a3157600183015160b76020839003016101000a9004810160b519019150610a61565b60f8811015610a45576109f660c082610c5c565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b80356001600160a01b03811681146101e257600080fd5b600082601f830112610a8e578081fd5b813567ffffffffffffffff80821115610aa957610aa9610ca4565b604051601f8301601f19908116603f01168101908282118183101715610ad157610ad1610ca4565b81604052838152866020858801011115610ae9578485fd5b8360208701602083013792830160200193909352509392505050565b600080600060608486031215610b19578283fd5b610b2284610a67565b925060208401359150604084013567ffffffffffffffff811115610b44578182fd5b610b5086828701610a7e565b9150509250925092565b60008060408385031215610b6c578182fd5b610b7583610a67565b9150602083013567ffffffffffffffff811115610b90578182fd5b610b9c85828601610a7e565b9150509250929050565b600060208284031215610bb7578081fd5b813567ffffffffffffffff811115610bcd578182fd5b6109c184828501610a7e565b600060208284031215610bea578081fd5b5035919050565b6000602080835283518082850152825b81811015610c1d57858101830151858201604001528201610c01565b81811115610c2e5783604083870101525b50601f01601f1916929092016040019392505050565b60008219821115610c5757610c57610c8e565b500190565b600082821015610c6e57610c6e610c8e565b500390565b6000600019821415610c8757610c87610c8e565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fdfea2646970667358221220a84d80a86e0d7d66731eb0841712d39d896ee3a80e841a7f0beb9e3c53e287dd64736f6c63430008020033";

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
