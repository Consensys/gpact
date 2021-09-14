package net.consensys.gpact.examples.read.soliditywrappers;

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
public class ContractA extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516107ad3803806107ad83398101604081905261002f91610082565b600080546001600160a01b039485166001600160a01b031991821617909155600492909255600580549190931691161790556100be565b80516001600160a01b038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b6106e0806100cd6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630b4ed8131461005157806399eb5d4c1461005b578063e1cb0e521461006e578063f6aacfb114610089575b600080fd5b6100596100bb565b005b61005961006936600461055c565b6101b0565b610076610264565b6040519081526020015b60405180910390f35b6100ab6100973660046105a1565b600090815260026020526040902054151590565b6040519015158152602001610080565b6000805460048054600554604080518481526024810182526020810180516001600160e01b0316631b53398f60e21b1790529051632388b54d60e21b81526001600160a01b0395861695638e22d5349561011b95949091169291016105ba565b602060405180830381600087803b15801561013557600080fd5b505af1158015610149573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061016d9190610588565b905061017a600082610275565b6040518181527fabdacda18abdb744b5ea95790fd361f37d4454a3e3269814cf272e2bafb7503d9060200160405180910390a150565b60005b6000828152600360205260409020548110156102485760008281526003602052604081208054839081106101e9576101e9610686565b906000526020600020015490508315610227576000818152600260205260409020546102179060019061063e565b6000828152600160205260409020555b6000908152600260205260408120558061024081610655565b9150506101b3565b506000818152600360205260408120610260916104fe565b5050565b6000610270600061048f565b905090565b600082815260026020526040902054156102cd5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064015b60405180910390fd5b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561031957600080fd5b505afa15801561032d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103519190610538565b156103685760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156103b757600080fd5b505afa1580156103cb573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103ef9190610588565b600054604051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561043557600080fd5b505af1158015610449573d6000803e3d6000fd5b50505060008281526003602090815260408220805460018181018355918452919092200185905561047b915083610626565b600084815260026020526040902055505050565b600081815260026020526040812054156104eb5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016102c4565b5060009081526001602052604090205490565b508054600082559060005260206000209081019061051c919061051f565b50565b5b808211156105345760008155600101610520565b5090565b60006020828403121561054a57600080fd5b81516105558161069c565b9392505050565b6000806040838503121561056f57600080fd5b823561057a8161069c565b946020939093013593505050565b60006020828403121561059a57600080fd5b5051919050565b6000602082840312156105b357600080fd5b5035919050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156105fc578581018301518582016080015282016105e0565b8181111561060e576000608083870101525b50601f01601f19169290920160800195945050505050565b6000821982111561063957610639610670565b500190565b60008282101561065057610650610670565b500390565b600060001982141561066957610669610670565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b801515811461051c57600080fdfea2646970667358221220ba4e4750476050c9c7c493f1fa2557d65855cd3abb553e2b425454c830cf889b64736f6c63430008050033";

    public static final String FUNC_DOCROSSCHAINREAD = "doCrosschainRead";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
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

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public static RemoteCall<ContractA> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc, BigInteger _otherBcId, String _contractBAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress)));
        return deployRemoteCall(ContractA.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ContractA> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc, BigInteger _otherBcId, String _contractBAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress)));
        return deployRemoteCall(ContractA.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractA> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _otherBcId, String _contractBAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress)));
        return deployRemoteCall(ContractA.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ContractA> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc, BigInteger _otherBcId, String _contractBAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _contractBAddress)));
        return deployRemoteCall(ContractA.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ValueReadEventResponse extends BaseEventResponse {
        public BigInteger _val;
    }
}
