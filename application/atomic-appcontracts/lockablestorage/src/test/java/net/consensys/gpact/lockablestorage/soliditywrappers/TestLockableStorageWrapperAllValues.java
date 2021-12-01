package net.consensys.gpact.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class TestLockableStorageWrapperAllValues extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610d02380380610d0283398101604081905261002f91610054565b60008054600160a060020a031916600160a060020a0392909216919091179055610084565b60006020828403121561006657600080fd5b8151600160a060020a038116811461007d57600080fd5b9392505050565b610c6f806100936000396000f3fe608060405234801561001057600080fd5b5060043610610133576000357c010000000000000000000000000000000000000000000000000000000090048063d4382bbe116100bf578063e97d08201161008e578063e97d082014610261578063ee0a43d714610274578063f6aacfb114610287578063f9bb6dd2146102a9578063fb91385f146102bc57600080fd5b8063d4382bbe14610215578063e0091dbc14610228578063e181aece1461023b578063e50f4b5d1461024e57600080fd5b8063787507771161010657806378750777146101a65780637ad304ae146101c95780637ba44848146101dc57806399eb5d4c146101ef578063a537ea9b1461020257600080fd5b8063317c0b2914610138578063415dba001461015e5780637445b0d01461017157806375bb3c2a14610191575b600080fd5b61014b610146366004610a5d565b6102ef565b6040519081526020015b60405180910390f35b61014b61016c366004610a5d565b610302565b61014b61017f366004610a7f565b60046020526000908152604090205481565b6101a461019f366004610aad565b61030e565b005b6101b96101b4366004610a7f565b61031c565b6040519015158152602001610155565b6101a46101d7366004610ad9565b61032d565b6101a46101ea366004610a5d565b61033d565b6101a46101fd366004610b05565b610347565b61014b610210366004610a7f565b6103f5565b6101a4610223366004610a5d565b610400565b6101a4610236366004610ad9565b61040a565b6101a4610249366004610a7f565b610415565b61014b61025c366004610a7f565b610421565b61014b61026f366004610a7f565b61042c565b61014b610282366004610a7f565b610440565b6101b9610295366004610a7f565b600090815260046020526040902054151590565b6101a46102b7366004610b2f565b61044b565b6102ca610210366004610a7f565b60405173ffffffffffffffffffffffffffffffffffffffff9091168152602001610155565b60006102fb8383610455565b9392505050565b60006102fb8383610504565b610318828261054e565b5050565b600061032782610569565b92915050565b61033883838361058a565b505050565b61031882826105c2565b60005b6000828152600360205260409020548110156103dd57600082815260036020526040812080548390811061038057610380610b78565b9060005260206000200154905083156103af576000818152600260209081526040808320546001909252909120555b60009081526002602090815260408083208390556004909152812055806103d581610bd6565b91505061034a565b50600081815260036020526040812061031891610a2b565b600061032782610421565b6103188282610620565b61033883838361081e565b61041e816108c0565b50565b600061032782610972565b600081815260016020526040812054610327565b6000610327826109e4565b6103188282610a0b565b60008061046184610972565b90508281116104ba5760405160e560020a62461bcd02815260206004820152601360248201527f496e646578206f7574206f6620626f756e64730000000000000000000000000060448201526064015b60405180910390fd5b6000846040516020016104cf91815260200190565b60408051601f19818403018152919052805160209091012090506104fb6104f68583610bf1565b610972565b95945050505050565b6000808383604051602001610523929190918252602082015260400190565b60408051601f198184030181529190528051602090910120905061054681610972565b949350505050565b610318828261055e576000610561565b60015b60ff16610620565b600061057482610972565b600114610582576000610327565b600192915050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206105bc8183610620565b50505050565b60006105cd83610972565b90506000836040516020016105e491815260200190565b60408051601f198184030181529190528051602090910120905061061161060b8383610bf1565b84610620565b6105bc84610620846001610bf1565b6000828152600460205260409020541561067f5760405160e560020a62461bcd02815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b656400000000000000000000000060448201526064016104b1565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b15801561070457600080fd5b505afa158015610718573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061073c9190610c09565b905080610756575060009182526001602052604090912055565b6000838152600460208190526040808320849055915491517f39ce107e000000000000000000000000000000000000000000000000000000008152309181019190915273ffffffffffffffffffffffffffffffffffffffff909116906339ce107e90602401600060405180830381600087803b1580156107d557600080fd5b505af11580156107e9573d6000803e3d6000fd5b505050600091825250600360209081526040808320805460018101825590845282842001859055938252600290529190912055565b600061082984610972565b905080831061087d5760405160e560020a62461bcd02815260206004820152601360248201527f496e646578206f7574206f6620626f756e64730000000000000000000000000060448201526064016104b1565b60008460405160200161089291815260200190565b60408051601f19818403018152919052805160209091012090506108b961060b8583610bf1565b5050505050565b60006108cb82610972565b9050600081116109205760405160e560020a62461bcd02815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e677468206172726179000060448201526064016104b1565b60008260405160200161093591815260200190565b60408051601f198184030181529190528051602090910120905061096361095c8383610bf1565b6000610620565b61033883610620600185610c22565b600081815260046020526040812054156109d15760405160e560020a62461bcd02815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016104b1565b5060009081526001602052604090205490565b600081815260046020526040812054156109d1575060009081526002602052604090205490565b610318828273ffffffffffffffffffffffffffffffffffffffff16610620565b508054600082559060005260206000209081019061041e91905b80821115610a595760008155600101610a45565b5090565b60008060408385031215610a7057600080fd5b50508035926020909101359150565b600060208284031215610a9157600080fd5b5035919050565b80358015158114610aa857600080fd5b919050565b60008060408385031215610ac057600080fd5b82359150610ad060208401610a98565b90509250929050565b600080600060608486031215610aee57600080fd5b505081359360208301359350604090920135919050565b60008060408385031215610b1857600080fd5b610b2183610a98565b946020939093013593505050565b60008060408385031215610b4257600080fd5b82359150602083013573ffffffffffffffffffffffffffffffffffffffff81168114610b6d57600080fd5b809150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000600019821415610bea57610bea610ba7565b5060010190565b60008219821115610c0457610c04610ba7565b500190565b600060208284031215610c1b57600080fd5b5051919050565b600082821015610c3457610c34610ba7565b50039056fea2646970667358221220342de158ee78be9ae733f67f5af02e9d56ee0d8422f477bfe2af589f0298913564736f6c63430008090033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

    public static final String FUNC_TEST_GETADDRESS = "test_getAddress";

    public static final String FUNC_TEST_GETARRAYLENGTH = "test_getArrayLength";

    public static final String FUNC_TEST_GETARRAYVALUE = "test_getArrayValue";

    public static final String FUNC_TEST_GETBOOL = "test_getBool";

    public static final String FUNC_TEST_GETMAPVALUE = "test_getMapValue";

    public static final String FUNC_TEST_GETUINT256 = "test_getUint256";

    public static final String FUNC_TEST_GETUINT256COMMITTED = "test_getUint256Committed";

    public static final String FUNC_TEST_GETUINT256PROVISIONAL = "test_getUint256Provisional";

    public static final String FUNC_TEST_POPARRAYVALUE = "test_popArrayValue";

    public static final String FUNC_TEST_PUSHARRAYVALUE = "test_pushArrayValue";

    public static final String FUNC_TEST_SETADDRESS = "test_setAddress";

    public static final String FUNC_TEST_SETARRAYVALUE = "test_setArrayValue";

    public static final String FUNC_TEST_SETBOOL = "test_setBool";

    public static final String FUNC_TEST_SETMAPVALUE = "test_setMapValue";

    public static final String FUNC_TEST_SETUINT256 = "test_setUint256";

    @Deprecated
    protected TestLockableStorageWrapperAllValues(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestLockableStorageWrapperAllValues(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestLockableStorageWrapperAllValues(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestLockableStorageWrapperAllValues(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> test_getAddress(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> test_getArrayLength(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETARRAYLENGTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> test_getArrayValue(BigInteger _key, BigInteger _index) {
        final Function function = new Function(FUNC_TEST_GETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> test_getBool(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> test_getMapValue(BigInteger _key, BigInteger _mapKey) {
        final Function function = new Function(FUNC_TEST_GETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> test_getUint256(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> test_getUint256Committed(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETUINT256COMMITTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> test_getUint256Provisional(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETUINT256PROVISIONAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> test_popArrayValue(BigInteger _key) {
        final Function function = new Function(
                FUNC_TEST_POPARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_pushArrayValue(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_TEST_PUSHARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_setAddress(BigInteger _key, String _address) {
        final Function function = new Function(
                FUNC_TEST_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_setArrayValue(BigInteger _key, BigInteger _index, BigInteger _val) {
        final Function function = new Function(
                FUNC_TEST_SETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_setBool(BigInteger _key, Boolean _flag) {
        final Function function = new Function(
                FUNC_TEST_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_flag)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_setMapValue(BigInteger _key, BigInteger _mapKey, BigInteger _val) {
        final Function function = new Function(
                FUNC_TEST_SETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> test_setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_TEST_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TestLockableStorageWrapperAllValues load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLockableStorageWrapperAllValues(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestLockableStorageWrapperAllValues load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLockableStorageWrapperAllValues(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestLockableStorageWrapperAllValues load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestLockableStorageWrapperAllValues(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestLockableStorageWrapperAllValues load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestLockableStorageWrapperAllValues(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestLockableStorageWrapperAllValues> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapperAllValues.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TestLockableStorageWrapperAllValues> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapperAllValues.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapperAllValues> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapperAllValues.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapperAllValues> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapperAllValues.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
