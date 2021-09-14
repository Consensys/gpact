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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TestLockableStorageWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610c74380380610c7483398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610be1806100936000396000f3fe608060405234801561001057600080fd5b506004361061010b5760003560e01c8063d4382bbe116100a2578063e97d082011610071578063e97d082014610219578063ee0a43d71461022c578063f6aacfb11461023f578063f9bb6dd214610261578063fb91385f1461027457600080fd5b8063d4382bbe146101cd578063e0091dbc146101e0578063e181aece146101f3578063e50f4b5d1461020657600080fd5b80637ad304ae116100de5780637ad304ae146101815780637ba448481461019457806399eb5d4c146101a7578063a537ea9b146101ba57600080fd5b8063317c0b2914610110578063415dba001461013657806375bb3c2a14610149578063787507771461015e575b600080fd5b61012361011e366004610ad9565b61029a565b6040519081526020015b60405180910390f35b610123610144366004610ad9565b6102ad565b61015c610157366004610ab4565b6102b9565b005b61017161016c366004610a5f565b6102c7565b604051901515815260200161012d565b61015c61018f366004610afb565b6102d8565b61015c6101a2366004610ad9565b6102e8565b61015c6101b5366004610a1a565b6102f2565b6101236101c8366004610a5f565b6103a2565b61015c6101db366004610ad9565b6103ad565b61015c6101ee366004610afb565b6103b7565b61015c610201366004610a5f565b6103c2565b610123610214366004610a5f565b6103ce565b610123610227366004610a5f565b6103d9565b61012361023a366004610a5f565b6103ed565b61017161024d366004610a5f565b600090815260026020526040902054151590565b61015c61026f366004610a78565b6103f8565b6102826101c8366004610a5f565b6040516001600160a01b03909116815260200161012d565b60006102a68383610402565b9392505050565b60006102a683836104a4565b6102c382826104ef565b5050565b60006102d28261050a565b92915050565b6102e383838361052b565b505050565b6102c38282610563565b60005b60008281526003602052604090205481101561038a57600082815260036020526040812080548390811061032b5761032b610b87565b9060005260206000200154905083156103695760008181526002602052604090205461035990600190610b3f565b6000828152600160205260409020555b6000908152600260205260408120558061038281610b56565b9150506102f5565b5060008181526003602052604081206102c3916109cb565b60006102d2826103ce565b6102c382826105c1565b6102e38383836107d6565b6103cb8161086b565b50565b60006102d28261091a565b6000818152600160205260408120546102d2565b60006102d282610989565b6102c382826109b8565b60008061040e8461091a565b905082811161045a5760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b60008460405160200161046f91815260200190565b60408051601f198184030181529190528051602090910120905061049b6104968583610b27565b61091a565b95945050505050565b60008083836040516020016104c3929190918252602082015260400190565b6040516020818303038152906040528051906020012090506104e78160001c61091a565b949350505050565b6102c382826104ff576000610502565b60015b60ff166105c1565b60006105158261091a565b6001146105235760006102d2565b600192915050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061055d81836105c1565b50505050565b600061056e8361091a565b905060008360405160200161058591815260200190565b60408051601f19818403018152919052805160209091012090506105b26105ac8383610b27565b846105c1565b61055d846105c1846001610b27565b600082815260026020526040902054156106145760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610451565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561066057600080fd5b505afa158015610674573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061069891906109fd565b156106af5760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156106fe57600080fd5b505afa158015610712573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107369190610a46565b600054604051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561077c57600080fd5b505af1158015610790573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556107c2915083610b27565b600084815260026020526040902055505050565b60006107e18461091a565b90508083106108285760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b6044820152606401610451565b60008460405160200161083d91815260200190565b60408051601f19818403018152919052805160209091012090506108646105ac8583610b27565b5050505050565b60006108768261091a565b9050600081116108c85760405162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e67746820617272617900006044820152606401610451565b6000826040516020016108dd91815260200190565b60408051601f198184030181529190528051602090910120905061090b6109048383610b27565b60006105c1565b6102e3836105c1600185610b3f565b600081815260026020526040812054156109765760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610451565b5060009081526001602052604090205490565b60008181526002602052604081205415610976576000828152600260205260409020546102d290600190610b3f565b6102c382826001600160a01b03166105c1565b50805460008255906000526020600020908101906103cb91905b808211156109f957600081556001016109e5565b5090565b600060208284031215610a0f57600080fd5b81516102a681610b9d565b60008060408385031215610a2d57600080fd5b8235610a3881610b9d565b946020939093013593505050565b600060208284031215610a5857600080fd5b5051919050565b600060208284031215610a7157600080fd5b5035919050565b60008060408385031215610a8b57600080fd5b8235915060208301356001600160a01b0381168114610aa957600080fd5b809150509250929050565b60008060408385031215610ac757600080fd5b823591506020830135610aa981610b9d565b60008060408385031215610aec57600080fd5b50508035926020909101359150565b600080600060608486031215610b1057600080fd5b505081359360208301359350604090920135919050565b60008219821115610b3a57610b3a610b71565b500190565b600082821015610b5157610b51610b71565b500390565b6000600019821415610b6a57610b6a610b71565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b80151581146103cb57600080fdfea264697066735822122059a83ac487b5b592a897b3eebab4843d9e6e8d558c420be7de45306e6af0130264736f6c63430008050033";

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
