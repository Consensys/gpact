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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class TestReceipts extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610d0b806100206000396000f3fe608060405234801561001057600080fd5b506004361061007e577c010000000000000000000000000000000000000000000000000000000060003504630fe64026811461008357806310ba2039146100ac5780632ae31cc0146100e45780638feb676c146100ac578063b401d83f146100f9578063e4e262231461010c575b600080fd5b610096610091366004610ae5565b61012d565b6040516100a39190610b3c565b60405180910390f35b6100bf6100ba366004610b91565b610146565b60405173ffffffffffffffffffffffffffffffffffffffff90911681526020016100a3565b6100f76100f2366004610bc6565b6101da565b005b610096610107366004610bdf565b610210565b61011f61011a366004610b91565b610248565b6040519081526020016100a3565b6060600061013c85858561027a565b9695505050505050565b60008061015a61015584610435565b610484565b905060006101818260038151811061017457610174610c2d565b6020026020010151610484565b9050805160011461019157600080fd5b60006101a98260008151811061017457610174610c2d565b9050600061013c6101d3836000815181106101c6576101c6610c2d565b6020026020010151610565565b6014015190565b6040518181527f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2959060200160405180910390a150565b6060600061023f847f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2958561027a565b95945050505050565b60008061025761015584610435565b905060006102718260038151811061017457610174610c2d565b51949350505050565b606080600061028b61015585610435565b905060006102a58260038151811061017457610174610c2d565b905060005b81518110156103a05760006102ca83838151811061017457610174610c2d565b905060006102e76101d3836000815181106101c6576101c6610c2d565b90506102ff8260018151811061017457610174610c2d565b9650610317826002815181106101c6576101c6610c2d565b9550600061033e8860008151811061033157610331610c2d565b60200260200101516105e2565b9050898114801561037a57508a73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16145b1561038a5750505050505061042d565b505050808061039890610c8b565b9150506102aa565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602560248201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560448201527f6365697074000000000000000000000000000000000000000000000000000000606482015260840160405180910390fd5b935093915050565b604080518082019091526000808252602082015281518061046b5750506040805180820190915260008082526020820152919050565b6040805180820190915260209384018152928301525090565b606061048f826105f3565b61049857600080fd5b60006104a38361061a565b90508067ffffffffffffffff8111156104be576104be610a29565b60405190808252806020026020018201604052801561050357816020015b60408051808201909152600080825260208201528152602001906001900390816104dc5790505b5091506000610511846106a2565b905060005b61051f826106fd565b1561055d5761052d82610721565b84828151811061053f5761053f610c2d565b6020026020010181905250808061055590610c8b565b915050610516565b505050919050565b60606105708261077b565b61057957600080fd5b600080610585846107a1565b90925090508067ffffffffffffffff8111156105a3576105a3610a29565b6040519080825280601f01601f1916602001820160405280156105cd576020820181803683370190505b5092506105db828483610845565b5050919050565b60006105ed82610883565b92915050565b600081602001516000141561060a57506000919050565b50515160c060009190911a101590565b6000610625826105f3565b61063157506000919050565b81518051600090811a9190610645856108d9565b61064f9083610ca6565b9050600060018660200151846106659190610ca6565b61066f9190610cbe565b905060005b81831161013c5761068483610967565b61068e9084610ca6565b92508061069a81610c8b565b915050610674565b60408051608081018252600091810182815260608201839052815260208101919091526106ce826105f3565b6106d757600080fd5b60006106e2836108d9565b83516106ee9190610ca6565b92825250602081019190915290565b8051602081015181516000929161071391610ca6565b836020015110915050919050565b604080518082019091526000808252602082015261073e826106fd565b1561007e576020820151600061075382610967565b8284526020840181905290506107698183610ca6565b6020850152506107769050565b919050565b600081602001516000141561079257506000919050565b50515160c060009190911a1090565b6000806107ad8361077b565b6107b657600080fd5b8251805160001a9060808210156107d257946001945092505050565b60b882101561080057600185602001516107ec9190610cbe565b92506107f9816001610ca6565b935061083e565b602085015160b619830190819061081990600190610cbe565b6108239190610cbe565b935061082f8183610ca6565b61083a906001610ca6565b9450505b5050915091565b6020601f820104836020840160005b8381101561087057602081028381015190830152600101610854565b5050505060008251602001830152505050565b600061088e8261077b565b61089757600080fd5b6000806108a3846107a1565b909250905060208111156108b657600080fd5b806108c5575060009392505050565b806020036101000a82510492505050919050565b60008160200151600014156108f057506000919050565b8151805160001a90608082101561090b575060009392505050565b60b8821080610926575060c08210158015610926575060f882105b15610935575060019392505050565b60c082101561095c5761094960b783610cbe565b610954906001610ca6565b949350505050565b61094960f783610cbe565b8051600090811a608081101561098057600191506109ff565b60b88110156109a657610994608082610cbe565b61099f906001610ca6565b91506109ff565b60c08110156109cf57600183015160b76020839003016101000a9004810160b5190191506109ff565b60f88110156109e35761099460c082610cbe565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b803573ffffffffffffffffffffffffffffffffffffffff8116811461077657600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600082601f830112610a6957600080fd5b813567ffffffffffffffff80821115610a8457610a84610a29565b604051601f8301601f19908116603f01168101908282118183101715610aac57610aac610a29565b81604052838152866020858801011115610ac557600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600060608486031215610afa57600080fd5b610b0384610a05565b925060208401359150604084013567ffffffffffffffff811115610b2657600080fd5b610b3286828701610a58565b9150509250925092565b600060208083528351808285015260005b81811015610b6957858101830151858201604001528201610b4d565b81811115610b7b576000604083870101525b50601f01601f1916929092016040019392505050565b600060208284031215610ba357600080fd5b813567ffffffffffffffff811115610bba57600080fd5b61095484828501610a58565b600060208284031215610bd857600080fd5b5035919050565b60008060408385031215610bf257600080fd5b610bfb83610a05565b9150602083013567ffffffffffffffff811115610c1757600080fd5b610c2385828601610a58565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000600019821415610c9f57610c9f610c5c565b5060010190565b60008219821115610cb957610cb9610c5c565b500190565b600082821015610cd057610cd0610c5c565b50039056fea264697066735822122081716ca6523b910a3c61024ee0a404d0c8d661d4ba94893f8cdebf98214e613e64736f6c63430008090033";

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
