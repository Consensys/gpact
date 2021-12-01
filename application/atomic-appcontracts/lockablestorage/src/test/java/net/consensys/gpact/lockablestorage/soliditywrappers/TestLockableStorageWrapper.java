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
public class TestLockableStorageWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610cde380380610cde83398101604081905261002f91610054565b60008054600160a060020a031916600160a060020a0392909216919091179055610084565b60006020828403121561006657600080fd5b8151600160a060020a038116811461007d57600080fd5b9392505050565b610c4b806100936000396000f3fe608060405234801561001057600080fd5b5060043610610128576000357c010000000000000000000000000000000000000000000000000000000090048063d4382bbe116100bf578063e97d08201161008e578063e97d082014610236578063ee0a43d714610249578063f6aacfb11461025c578063f9bb6dd21461027e578063fb91385f1461029157600080fd5b8063d4382bbe146101ea578063e0091dbc146101fd578063e181aece14610210578063e50f4b5d1461022357600080fd5b80637ad304ae116100fb5780637ad304ae1461019e5780637ba44848146101b157806399eb5d4c146101c4578063a537ea9b146101d757600080fd5b8063317c0b291461012d578063415dba001461015357806375bb3c2a14610166578063787507771461017b575b600080fd5b61014061013b366004610a39565b6102c4565b6040519081526020015b60405180910390f35b610140610161366004610a39565b6102d7565b610179610174366004610a70565b6102e3565b005b61018e610189366004610a9c565b6102f1565b604051901515815260200161014a565b6101796101ac366004610ab5565b610302565b6101796101bf366004610a39565b610312565b6101796101d2366004610ae1565b61031c565b6101406101e5366004610a9c565b6103cc565b6101796101f8366004610a39565b6103d7565b61017961020b366004610ab5565b6103e1565b61017961021e366004610a9c565b6103ec565b610140610231366004610a9c565b6103f8565b610140610244366004610a9c565b610403565b610140610257366004610a9c565b610417565b61018e61026a366004610a9c565b600090815260026020526040902054151590565b61017961028c366004610b0b565b610422565b61029f6101e5366004610a9c565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200161014a565b60006102d0838361042c565b9392505050565b60006102d083836104db565b6102ed8282610525565b5050565b60006102fc82610540565b92915050565b61030d838383610561565b505050565b6102ed8282610599565b60005b6000828152600360205260409020548110156103b457600082815260036020526040812080548390811061035557610355610b54565b9060005260206000200154905083156103935760008181526002602052604090205461038390600190610bb2565b6000828152600160205260409020555b600090815260026020526040812055806103ac81610bc9565b91505061031f565b5060008181526003602052604081206102ed91610a07565b60006102fc826103f8565b6102ed82826105f7565b61030d8383836107f2565b6103f581610894565b50565b60006102fc82610946565b6000818152600160205260408120546102fc565b60006102fc826109b8565b6102ed82826109e7565b60008061043884610946565b90508281116104915760405160e560020a62461bcd02815260206004820152601360248201527f496e646578206f7574206f6620626f756e64730000000000000000000000000060448201526064015b60405180910390fd5b6000846040516020016104a691815260200190565b60408051601f19818403018152919052805160209091012090506104d26104cd8583610be4565b610946565b95945050505050565b60008083836040516020016104fa929190918252602082015260400190565b60408051601f198184030181529190528051602090910120905061051d81610946565b949350505050565b6102ed8282610535576000610538565b60015b60ff166105f7565b600061054b82610946565b6001146105595760006102fc565b600192915050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061059381836105f7565b50505050565b60006105a483610946565b90506000836040516020016105bb91815260200190565b60408051601f19818403018152919052805160209091012090506105e86105e28383610be4565b846105f7565b610593846105f7846001610be4565b600082815260026020526040902054156106565760405160e560020a62461bcd02815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b65640000000000000000000000006044820152606401610488565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b1580156106db57600080fd5b505afa1580156106ef573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107139190610bfc565b90508061072d575060009182526001602052604090912055565b6000546040517f39ce107e00000000000000000000000000000000000000000000000000000000815230600482015273ffffffffffffffffffffffffffffffffffffffff909116906339ce107e90602401600060405180830381600087803b15801561079857600080fd5b505af11580156107ac573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556107de915083610be4565b600084815260026020526040902055505050565b60006107fd84610946565b90508083106108515760405160e560020a62461bcd02815260206004820152601360248201527f496e646578206f7574206f6620626f756e6473000000000000000000000000006044820152606401610488565b60008460405160200161086691815260200190565b60408051601f198184030181529190528051602090910120905061088d6105e28583610be4565b5050505050565b600061089f82610946565b9050600081116108f45760405160e560020a62461bcd02815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e67746820617272617900006044820152606401610488565b60008260405160200161090991815260200190565b60408051601f19818403018152919052805160209091012090506109376109308383610be4565b60006105f7565b61030d836105f7600185610bb2565b600081815260026020526040812054156109a55760405160e560020a62461bcd02815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610488565b5060009081526001602052604090205490565b600081815260026020526040812054156109a5576000828152600260205260409020546102fc90600190610bb2565b6102ed828273ffffffffffffffffffffffffffffffffffffffff166105f7565b50805460008255906000526020600020908101906103f591905b80821115610a355760008155600101610a21565b5090565b60008060408385031215610a4c57600080fd5b50508035926020909101359150565b80358015158114610a6b57600080fd5b919050565b60008060408385031215610a8357600080fd5b82359150610a9360208401610a5b565b90509250929050565b600060208284031215610aae57600080fd5b5035919050565b600080600060608486031215610aca57600080fd5b505081359360208301359350604090920135919050565b60008060408385031215610af457600080fd5b610afd83610a5b565b946020939093013593505050565b60008060408385031215610b1e57600080fd5b82359150602083013573ffffffffffffffffffffffffffffffffffffffff81168114610b4957600080fd5b809150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600082821015610bc457610bc4610b83565b500390565b6000600019821415610bdd57610bdd610b83565b5060010190565b60008219821115610bf757610bf7610b83565b500190565b600060208284031215610c0e57600080fd5b505191905056fea2646970667358221220a95b86ecd0f60d8b03fb05730ba5140d477c8d052a47df7e9eedaf900291538e64736f6c63430008090033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_ISLOCKED = "isLocked";

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
    protected TestLockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestLockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestLockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestLockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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
    public static TestLockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLockableStorageWrapper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestLockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestLockableStorageWrapper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestLockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestLockableStorageWrapper(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestLockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestLockableStorageWrapper(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
