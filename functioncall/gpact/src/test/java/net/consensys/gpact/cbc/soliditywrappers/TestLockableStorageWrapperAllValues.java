package net.consensys.gpact.cbc.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestLockableStorageWrapperAllValues extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610c98380380610c9883398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610c05806100936000396000f3fe608060405234801561001057600080fd5b50600436106101165760003560e01c8063d4382bbe116100a2578063e97d082011610071578063e97d082014610244578063ee0a43d714610257578063f6aacfb11461026a578063f9bb6dd21461028c578063fb91385f1461029f57600080fd5b8063d4382bbe146101f8578063e0091dbc1461020b578063e181aece1461021e578063e50f4b5d1461023157600080fd5b806378750777116100e957806378750777146101895780637ad304ae146101ac5780637ba44848146101bf57806399eb5d4c146101d2578063a537ea9b146101e557600080fd5b8063317c0b291461011b578063415dba00146101415780637445b0d01461015457806375bb3c2a14610174575b600080fd5b61012e610129366004610afd565b6102c5565b6040519081526020015b60405180910390f35b61012e61014f366004610afd565b6102d8565b61012e610162366004610a83565b60046020526000908152604090205481565b610187610182366004610ad8565b6102e4565b005b61019c610197366004610a83565b6102f2565b6040519015158152602001610138565b6101876101ba366004610b1f565b610303565b6101876101cd366004610afd565b610313565b6101876101e0366004610a3e565b61031d565b61012e6101f3366004610a83565b6103cb565b610187610206366004610afd565b6103d6565b610187610219366004610b1f565b6103e0565b61018761022c366004610a83565b6103eb565b61012e61023f366004610a83565b6103f7565b61012e610252366004610a83565b610402565b61012e610265366004610a83565b610416565b61019c610278366004610a83565b600090815260046020526040902054151590565b61018761029a366004610a9c565b610421565b6102ad6101f3366004610a83565b6040516001600160a01b039091168152602001610138565b60006102d1838361042b565b9392505050565b60006102d183836104cd565b6102ee8282610518565b5050565b60006102fd82610533565b92915050565b61030e838383610554565b505050565b6102ee828261058c565b60005b6000828152600360205260409020548110156103b357600082815260036020526040812080548390811061035657610356610bab565b906000526020600020015490508315610385576000818152600260209081526040808320546001909252909120555b60009081526002602090815260408083208390556004909152812055806103ab81610b7a565b915050610320565b5060008181526003602052604081206102ee916109ef565b60006102fd826103f7565b6102ee82826105ea565b61030e838383610802565b6103f481610897565b50565b60006102fd82610946565b6000818152600160205260408120546102fd565b60006102fd826109b5565b6102ee82826109dc565b60008061043784610946565b90508281116104835760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b60008460405160200161049891815260200190565b60408051601f19818403018152919052805160209091012090506104c46104bf8583610b4b565b610946565b95945050505050565b60008083836040516020016104ec929190918252602082015260400190565b6040516020818303038152906040528051906020012090506105108160001c610946565b949350505050565b6102ee828261052857600061052b565b60015b60ff166105ea565b600061053e82610946565b60011461054c5760006102fd565b600192915050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061058681836105ea565b50505050565b600061059783610946565b90506000836040516020016105ae91815260200190565b60408051601f19818403018152919052805160209091012090506105db6105d58383610b4b565b846105ea565b610586846105ea846001610b4b565b6000828152600460205260409020541561063d5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b604482015260640161047a565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561068957600080fd5b505afa15801561069d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106c19190610a21565b156106d85760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b15801561072757600080fd5b505afa15801561073b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061075f9190610a6a565b600084815260046020819052604080832084905591549151631ce7083f60e11b815230918101919091529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b1580156107b957600080fd5b505af11580156107cd573d6000803e3d6000fd5b505050600091825250600360209081526040808320805460018101825590845282842001859055938252600290529190912055565b600061080d84610946565b90508083106108545760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015260640161047a565b60008460405160200161086991815260200190565b60408051601f19818403018152919052805160209091012090506108906105d58583610b4b565b5050505050565b60006108a282610946565b9050600081116108f45760405162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e6774682061727261790000604482015260640161047a565b60008260405160200161090991815260200190565b60408051601f19818403018152919052805160209091012090506109376109308383610b4b565b60006105ea565b61030e836105ea600185610b63565b600081815260046020526040812054156109a25760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b656400604482015260640161047a565b5060009081526001602052604090205490565b600081815260046020526040812054156109a2575060009081526002602052604090205490565b6102ee82826001600160a01b03166105ea565b50805460008255906000526020600020908101906103f491905b80821115610a1d5760008155600101610a09565b5090565b600060208284031215610a3357600080fd5b81516102d181610bc1565b60008060408385031215610a5157600080fd5b8235610a5c81610bc1565b946020939093013593505050565b600060208284031215610a7c57600080fd5b5051919050565b600060208284031215610a9557600080fd5b5035919050565b60008060408385031215610aaf57600080fd5b8235915060208301356001600160a01b0381168114610acd57600080fd5b809150509250929050565b60008060408385031215610aeb57600080fd5b823591506020830135610acd81610bc1565b60008060408385031215610b1057600080fd5b50508035926020909101359150565b600080600060608486031215610b3457600080fd5b505081359360208301359350604090920135919050565b60008219821115610b5e57610b5e610b95565b500190565b600082821015610b7557610b75610b95565b500390565b6000600019821415610b8e57610b8e610b95565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b80151581146103f457600080fdfea264697066735822122075d389b74abcbca75203c4a1f861b70179cfc79097e4837ee47b6aa4e10c3d6c64736f6c63430008050033";

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
