package tech.pegasys.ltacfc.receipts.soliditywrappers;

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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestReceipts extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610bbd806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630fe640261461006757806310ba2039146101955780632ae31cc0146102555780638feb676c14610195578063b401d83f14610274578063e4e2622314610328575b600080fd5b6101206004803603606081101561007d57600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b8111156100ac57600080fd5b8201836020820111156100be57600080fd5b803590602001918460018302840111600160201b831117156100df57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103de945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015a578181015183820152602001610142565b50505050905090810190601f1680156101875780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610239600480360360208110156101ab57600080fd5b810190602081018135600160201b8111156101c557600080fd5b8201836020820111156101d757600080fd5b803590602001918460018302840111600160201b831117156101f857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103f6945050505050565b604080516001600160a01b039092168252519081900360200190f35b6102726004803603602081101561026b57600080fd5b5035610483565b005b6101206004803603604081101561028a57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102b457600080fd5b8201836020820111156102c657600080fd5b803590602001918460018302840111600160201b831117156102e757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104b9945050505050565b6103cc6004803603602081101561033e57600080fd5b810190602081018135600160201b81111561035857600080fd5b82018360208201111561036a57600080fd5b803590602001918460018302840111600160201b8311171561038b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104f0945050505050565b60408051918252519081900360200190f35b6060806103ec85858561051d565b9695505050505050565b6000606061040b6104068461063a565b61067d565b9050606061042c8260038151811061041f57fe5b602002602001015161067d565b9050805160011461043c57600080fd5b606061044e8260008151811061041f57fe5b905060006104776104728360008151811061046557fe5b6020026020010151610746565b6107c2565b9450505050505b919050565b6040805182815290517f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2959181900360200190a150565b6060806104e7847f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2958561051d565b95945050505050565b600060606105006104068461063a565b905060606105148260038151811061041f57fe5b51949350505050565b606080606061052e6104068561063a565b905060606105428260038151811061041f57fe5b905060005b81518110156105fa57606061056183838151811061041f57fe5b905060006105786104728360008151811061046557fe5b905061058a8260018151811061041f57fe5b965061059c8260028151811061046557fe5b955060006105bd886000815181106105b057fe5b60200260200101516107c9565b905089811480156105df57508a6001600160a01b0316826001600160a01b0316145b156105ef57505050505050610632565b505050600101610547565b5060405162461bcd60e51b8152600401808060200182810382526025815260200180610b636025913960400191505060405180910390fd5b935093915050565b610642610b28565b815180610664575050604080518082019091526000808252602082015261047e565b6040805180820190915260209384018152928301525090565b6060610688826107da565b61069157600080fd5b600061069c83610801565b90508067ffffffffffffffff811180156106b557600080fd5b506040519080825280602002602001820160405280156106ef57816020015b6106dc610b28565b8152602001906001900390816106d45790505b5091506106fa610b42565b6107038461085b565b905060005b61071182610892565b1561073e5761071f826108b1565b84828151811061072b57fe5b6020908102919091010152600101610708565b505050919050565b6060610751826108f1565b61075a57600080fd5b60008061076684610917565b90925090508067ffffffffffffffff8111801561078257600080fd5b506040519080825280601f01601f1916602001820160405280156107ad576020820181803683370190505b5092506107bb828483610987565b5050919050565b6014015190565b60006107d4826109c5565b92915050565b60008160200151600014156107f15750600061047e565b50515160c060009190911a101590565b600061080c826107da565b6108185750600061047e565b81518051600090811a919061082c85610a1c565b6020860151908301915082016000190160005b8183116104775761084f83610a9a565b9092019160010161083f565b610863610b42565b61086c826107da565b61087557600080fd5b600061088083610a1c565b83519383529092016020820152919050565b600061089c610b28565b50508051602080820151915192015191011190565b6108b9610b28565b6108c282610892565b1561006257602082015160006108d782610a9a565b82845260208085018290529201918401919091525061047e565b60008160200151600014156109085750600061047e565b50515160c060009190911a1090565b600080610923836108f1565b61092c57600080fd5b8251805160001a90608082101561094a579250600191506109829050565b60b8821015610968576001856020015103925080600101935061097f565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b838110156109b257602081028381015190830152600101610996565b5050505060008251602001830152505050565b60006109d0826108f1565b6109d957600080fd5b6000806109e584610917565b909250905060208111156109f857600080fd5b80610a085760009250505061047e565b806020036101000a82510492505050919050565b6000816020015160001415610a335750600061047e565b8151805160001a906080821015610a4f5760009250505061047e565b60b8821080610a6a575060c08210158015610a6a575060f882105b15610a7a5760019250505061047e565b60c0821015610a8f575060b51901905061047e565b5060f5190192915050565b8051600090811a6080811015610ab35760019150610b22565b60b8811015610ac857607e1981019150610b22565b60c0811015610af157600183015160b76020839003016101000a9004810160b519019150610b22565b60f8811015610b065760be1981019150610b22565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b604051806040016040528060008152602001600081525090565b6040518060400160405280610b55610b28565b815260200160008152509056fe4e6f206576656e7420666f756e6420696e207472616e73616374696f6e2072656365697074a264697066735822122072a563fc65bb0774799168922349151d435dec61eaa7accf7768f22468d18ec064736f6c63430007040033";

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

    public String getRLP_emittingContractFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EMITTINGCONTRACTFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> getEventSignatureFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEVENTSIGNATUREFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getEventSignatureFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETEVENTSIGNATUREFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> numLogsFound(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMLOGSFOUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_numLogsFound(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NUMLOGSFOUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> retrieveALog(String _contractAddress, byte[] _eventFunctionSignature, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVEALOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(_eventFunctionSignature), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_retrieveALog(String _contractAddress, byte[] _eventFunctionSignature, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETRIEVEALOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(_eventFunctionSignature), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> retrieveStartLog(String _contractAddress, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVESTARTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_retrieveStartLog(String _contractAddress, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETRIEVESTARTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> triggerStartEvent(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERSTARTEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_triggerStartEvent(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERSTARTEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
