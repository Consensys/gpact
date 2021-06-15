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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class ContractA extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516105d53803806105d583398101604081905261002f91610091565b600080546001600160a01b03199081166001600160a01b0393841617909155600380548216958316959095179094556001929092556002805490931691161790556100de565b80516001600160a01b038116811461008c57600080fd5b919050565b600080600080608085870312156100a757600080fd5b6100b085610075565b9350602085015192506100c560408601610075565b91506100d360608601610075565b905092959194509250565b6104e8806100ed6000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630b4ed8131461004657806311ce026714610050578063e1cb0e5214610080575b600080fd5b61004e610096565b005b600054610063906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b61008861018d565b604051908152602001610077565b6003546001546002546040805160048082526024820183526020820180516001600160e01b0316631b53398f60e21b1790529151632388b54d60e21b81526000956001600160a01b0390811695638e22d534956100f89591949216920161039e565b602060405180830381600087803b15801561011257600080fd5b505af1158015610126573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061014a919061031e565b905061015760008261019e565b6040518181527fabdacda18abdb744b5ea95790fd361f37d4454a3e3269814cf272e2bafb7503d9060200160405180910390a150565b6000610199600061029b565b905090565b600054604051632dfcbaaf60e11b815260048101849052602481018390526001600160a01b0390911690635bf9755e90604401600060405180830381600087803b1580156101eb57600080fd5b505af19250505080156101fc575060015b6102975761020861040c565b806308c379a0141561024b575061021d610428565b80610228575061024d565b8060405162461bcd60e51b81526004016102429190610384565b60405180910390fd5b505b3d808015610277576040519150601f19603f3d011682016040523d82523d6000602084013e61027c565b606091505b508060405162461bcd60e51b81526004016102429190610384565b5050565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b1580156102e057600080fd5b505afa1580156102f4573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610318919061031e565b92915050565b60006020828403121561033057600080fd5b5051919050565b6000815180845260005b8181101561035d57602081850181015186830182015201610341565b8181111561036f576000602083870101525b50601f01601f19169290920160200192915050565b6020815260006103976020830184610337565b9392505050565b8381526001600160a01b03831660208201526060604082018190526000906103c890830184610337565b95945050505050565b601f8201601f1916810167ffffffffffffffff8111828210171561040557634e487b7160e01b600052604160045260246000fd5b6040525050565b600060033d11156104255760046000803e5060005160e01c5b90565b600060443d10156104365790565b6040516003193d81016004833e81513d67ffffffffffffffff816024840111818411171561046657505050505090565b828501915081518181111561047e5750505050505090565b843d87010160208285010111156104985750505050505090565b6104a7602082860101876103d1565b50909594505050505056fea26469706673582212207f13f4d09259c21e2df4a6442e6aa4dee0a8d8cb0a2071c054127a9624a3e45e64736f6c63430008050033";

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
