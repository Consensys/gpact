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
public class ContractB extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161069638038061069683398101604081905261002f91610054565b60008054600160a060020a031916600160a060020a0392909216919091179055610084565b60006020828403121561006657600080fd5b8151600160a060020a038116811461007d57600080fd5b9392505050565b610603806100936000396000f3fe608060405234801561001057600080fd5b5060043610610068577c0100000000000000000000000000000000000000000000000000000000600035046360fe47b1811461006d57806399eb5d4c14610082578063e1cb0e5214610095578063f6aacfb1146100b0575b600080fd5b61008061007b3660046104c2565b6100e2565b005b6100806100903660046104db565b610123565b61009d6101d7565b6040519081526020015b60405180910390f35b6100d26100be3660046104c2565b600090815260026020526040902054151590565b60405190151581526020016100a7565b6100ed6000826101e8565b6040518181527fbc9e8ea3b0777e6b9815f4c534668e08ddcaa8595a383ac46aebb40336ce81d79060200160405180910390a150565b60005b6000828152600360205260409020548110156101bb57600082815260036020526040812080548390811061015c5761015c61050c565b90600052602060002001549050831561019a5760008181526002602052604090205461018a9060019061056a565b6000828152600160205260409020555b600090815260026020526040812055806101b381610581565b915050610126565b5060008181526003602052604081206101d391610488565b5050565b60006101e360006103ff565b905090565b60008281526002602052604090205415610263576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b656400000000000000000000000060448201526064015b60405180910390fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b1580156102e857600080fd5b505afa1580156102fc573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610320919061059c565b90508061033a575060009182526001602052604090912055565b6000546040517f39ce107e00000000000000000000000000000000000000000000000000000000815230600482015273ffffffffffffffffffffffffffffffffffffffff909116906339ce107e90602401600060405180830381600087803b1580156103a557600080fd5b505af11580156103b9573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556103eb9150836105b5565b600084815260026020526040902055505050565b60008181526002602052604081205415610475576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b656400604482015260640161025a565b5060009081526001602052604090205490565b50805460008255906000526020600020908101906104a691906104a9565b50565b5b808211156104be57600081556001016104aa565b5090565b6000602082840312156104d457600080fd5b5035919050565b600080604083850312156104ee57600080fd5b823580151581146104fe57600080fd5b946020939093013593505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008282101561057c5761057c61053b565b500390565b60006000198214156105955761059561053b565b5060010190565b6000602082840312156105ae57600080fd5b5051919050565b600082198211156105c8576105c861053b565b50019056fea26469706673582212203f0f7c8145dc0fc801d2f2219047b85540542196e77c6a79a414d6f11a05cd9f64736f6c63430008090033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getVal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> set(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
