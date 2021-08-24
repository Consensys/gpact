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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestLockableStorageWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610d85380380610d8583398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610cf2806100936000396000f3fe608060405234801561001057600080fd5b50600436106101165760003560e01c8063e0091dbc116100a2578063ee0a43d711610071578063ee0a43d714610244578063f6aacfb114610257578063f9bb6dd214610279578063fb91385f1461028c578063fcea5096146102b257600080fd5b8063e0091dbc146101f8578063e181aece1461020b578063e50f4b5d1461021e578063e97d08201461023157600080fd5b806378750777116100e957806378750777146101895780637ad304ae146101ac5780637ba44848146101bf578063a537ea9b146101d2578063d4382bbe146101e557600080fd5b8063317c0b291461011b578063415dba00146101415780637445b0d01461015457806375bb3c2a14610174575b600080fd5b61012e610129366004610bea565b6102c5565b6040519081526020015b60405180910390f35b61012e61014f366004610bea565b6102d8565b61012e610162366004610b57565b60046020526000908152604090205481565b610187610182366004610bc5565b6102e4565b005b61019c610197366004610b57565b6102f2565b6040519015158152602001610138565b6101876101ba366004610c0c565b610303565b6101876101cd366004610bea565b610313565b61012e6101e0366004610b57565b61031d565b6101876101f3366004610bea565b610328565b610187610206366004610c0c565b610332565b610187610219366004610b57565b61033d565b61012e61022c366004610b57565b610349565b61012e61023f366004610b57565b610354565b61012e610252366004610b57565b610368565b61019c610265366004610b57565b600090815260046020526040902054151590565b610187610287366004610b89565b610373565b61029a6101e0366004610b57565b6040516001600160a01b039091168152602001610138565b6101876102c0366004610b22565b61037d565b60006102d18383610459565b9392505050565b60006102d183836104fb565b6102ee8282610546565b5050565b60006102fd82610561565b92915050565b61030e838383610582565b505050565b6102ee82826105b4565b60006102fd82610349565b6102ee8282610612565b61030e8383836108e6565b6103468161097b565b50565b60006102fd82610a2a565b6000818152600160205260408120546102fd565b60006102fd82610a99565b6102ee8282610ac0565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b60008281526003602052604090205481101561043b5760008281526003602052604081208054839081106103de576103de610c98565b90600052602060002001549050851561040d576000818152600260209081526040808320546001909252909120555b600090815260026020908152604080832083905560049091528120558061043381610c67565b9150506103a8565b50600081815260036020526040812061045391610ad3565b50505050565b60008061046584610a2a565b90508281116104b15760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b6000846040516020016104c691815260200190565b60408051601f19818403018152919052805160209091012090506104f26104ed8583610c38565b610a2a565b95945050505050565b600080838360405160200161051a929190918252602082015260400190565b60405160208183030381529060405280519060200120905061053e8160001c610a2a565b949350505050565b6102ee8282610556576000610559565b60015b60ff16610612565b600061056c82610a2a565b60011461057a5760006102fd565b600192915050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206104538183610612565b60006105bf83610a2a565b90506000836040516020016105d691815260200190565b60408051601f19818403018152919052805160209091012090506106036105fd8383610c38565b84610612565b61045384610612846001610c38565b600082815260046020526040902054156106655760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016104a8565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156106b157600080fd5b505afa1580156106c5573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106e99190610b05565b156107005760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b15801561074f57600080fd5b505afa158015610763573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107879190610b70565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b1580156107d857600080fd5b505afa1580156107ec573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906108109190610b70565b60408051602080820186905281830184905282518083038401815260609092019092528051910120909150600090600086815260046020819052604080832084905591549151631ce7083f60e11b815230918101919091529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561089857600080fd5b505af11580156108ac573d6000803e3d6000fd5b5050506000918252506003602090815260408083208054600181018255908452828420018790558683526002909152902083905550505050565b60006108f184610a2a565b90508083106109385760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064016104a8565b60008460405160200161094d91815260200190565b60408051601f19818403018152919052805160209091012090506109746105fd8583610c38565b5050505050565b600061098682610a2a565b9050600081116109d85760405162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e677468206172726179000060448201526064016104a8565b6000826040516020016109ed91815260200190565b60408051601f1981840301815291905280516020909101209050610a1b610a148383610c38565b6000610612565b61030e83610612600185610c50565b60008181526004602052604081205415610a865760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016104a8565b5060009081526001602052604090205490565b60008181526004602052604081205415610a86575060009081526002602052604090205490565b6102ee82826001600160a01b0316610612565b508054600082559060005260206000209081019061034691905b80821115610b015760008155600101610aed565b5090565b600060208284031215610b1757600080fd5b81516102d181610cae565b600080600060608486031215610b3757600080fd5b8335610b4281610cae565b95602085013595506040909401359392505050565b600060208284031215610b6957600080fd5b5035919050565b600060208284031215610b8257600080fd5b5051919050565b60008060408385031215610b9c57600080fd5b8235915060208301356001600160a01b0381168114610bba57600080fd5b809150509250929050565b60008060408385031215610bd857600080fd5b823591506020830135610bba81610cae565b60008060408385031215610bfd57600080fd5b50508035926020909101359150565b600080600060608486031215610c2157600080fd5b505081359360208301359350604090920135919050565b60008219821115610c4b57610c4b610c82565b500190565b600082821015610c6257610c62610c82565b500390565b6000600019821415610c7b57610c7b610c82565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b801515811461034657600080fdfea26469706673582212206550f9372498d7ddedce62e212aeaecd555304a054e7a80189826958706c2bea64736f6c63430008050033";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
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
