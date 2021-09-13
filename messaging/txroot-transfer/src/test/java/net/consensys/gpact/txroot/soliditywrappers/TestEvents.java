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
public class TestEvents extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506101a7806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c806395805dad14610030575b600080fd5b61004361003e366004610132565b610045565b005b6040518181527f04474795f5b996ff80cb47c148d4c5ccdbe09ef27551820caa9c2f8ed149cce39060200160405180910390a17f06df6fb2d6d0b17a870decb858cc46bf7b69142ab7b9318f7603ed3fd4ad240e6100a482600161014b565b60405190815260200160405180910390a17f38ee5a08acae32a0ccec0eef68b73ba44f4b09e2f3df37062af8e885a7fd23af6100e182600261014b565b60405190815260200160405180910390a17f04474795f5b996ff80cb47c148d4c5ccdbe09ef27551820caa9c2f8ed149cce361011e82600361014b565b60405190815260200160405180910390a150565b60006020828403121561014457600080fd5b5035919050565b6000821982111561016c57634e487b7160e01b600052601160045260246000fd5b50019056fea2646970667358221220882929d3b1f9a312953b64c5a43172ebee046fe0643c11b54e78bc4eebabd15864736f6c63430008050033";

    public static final String FUNC_START = "start";

    public static final Event EVENT1_EVENT = new Event("Event1", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event EVENT2_EVENT = new Event("Event2", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event EVENT3_EVENT = new Event("Event3", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected TestEvents(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestEvents(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestEvents(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestEvents(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<Event1EventResponse> getEvent1Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVENT1_EVENT, transactionReceipt);
        ArrayList<Event1EventResponse> responses = new ArrayList<Event1EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event1EventResponse typedResponse = new Event1EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Event1EventResponse> event1EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, Event1EventResponse>() {
            @Override
            public Event1EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EVENT1_EVENT, log);
                Event1EventResponse typedResponse = new Event1EventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Event1EventResponse> event1EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENT1_EVENT));
        return event1EventFlowable(filter);
    }

    public List<Event2EventResponse> getEvent2Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVENT2_EVENT, transactionReceipt);
        ArrayList<Event2EventResponse> responses = new ArrayList<Event2EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event2EventResponse typedResponse = new Event2EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Event2EventResponse> event2EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, Event2EventResponse>() {
            @Override
            public Event2EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EVENT2_EVENT, log);
                Event2EventResponse typedResponse = new Event2EventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Event2EventResponse> event2EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENT2_EVENT));
        return event2EventFlowable(filter);
    }

    public List<Event3EventResponse> getEvent3Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVENT3_EVENT, transactionReceipt);
        ArrayList<Event3EventResponse> responses = new ArrayList<Event3EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Event3EventResponse typedResponse = new Event3EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Event3EventResponse> event3EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, Event3EventResponse>() {
            @Override
            public Event3EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EVENT3_EVENT, log);
                Event3EventResponse typedResponse = new Event3EventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Event3EventResponse> event3EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENT3_EVENT));
        return event3EventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> start(BigInteger _Id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_Id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TestEvents load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestEvents(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestEvents load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestEvents(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestEvents load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestEvents(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestEvents load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestEvents(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestEvents> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestEvents.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestEvents> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestEvents.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TestEvents> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestEvents.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestEvents> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestEvents.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Event1EventResponse extends BaseEventResponse {
        public BigInteger id;
    }

    public static class Event2EventResponse extends BaseEventResponse {
        public BigInteger id;
    }

    public static class Event3EventResponse extends BaseEventResponse {
        public BigInteger id;
    }
}
