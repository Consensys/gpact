package net.consensys.gpact.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516112af3803806112af83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b61121c806100936000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c8063a537ea9b11610097578063e50f4b5d11610066578063e50f4b5d146101da578063f20c899014610226578063f9bb6dd214610246578063fb91385f1461025957600080fd5b8063a537ea9b146101da578063d4382bbe146101ed578063e0091dbc14610200578063e181aece1461021357600080fd5b806375bb3c2a116100d357806375bb3c2a1461017e57806378750777146101915780637ad304ae146101b45780637ba44848146101c757600080fd5b806311ce026714610105578063317c0b2914610135578063415dba00146101565780635eac846a14610169575b600080fd5b600054610118906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b610148610143366004610fc5565b610267565b60405190815260200161012c565b610148610164366004610fc5565b61027a565b61017c610177366004610f49565b610286565b005b61017c61018c366004610f1f565b610296565b6101a461019f366004610eb1565b6102a4565b604051901515815260200161012c565b61017c6101c2366004610fe7565b6102b5565b61017c6101d5366004610fc5565b6102c0565b6101486101e8366004610eb1565b6102ca565b61017c6101fb366004610fc5565b6102d5565b61017c61020e366004610fe7565b6102df565b61017c610221366004610eb1565b6102ea565b610239610234366004610eb1565b6102f6565b60405161012c919061103f565b61017c610254366004610ee3565b610301565b6101186101e8366004610eb1565b6000610273838361030b565b9392505050565b60006102738383610496565b610291838383610550565b505050565b6102a082826105bb565b5050565b60006102af8261063a565b92915050565b6102918383836106cd565b6102a0828261075e565b60006102af826108c9565b6102a08282610946565b610291838383610a36565b6102f381610ba5565b50565b60606102af82610d5f565b6102a08282610de0565b60008054604051631106aeeb60e21b81526004810185905282916001600160a01b03169063441abbac9060240160206040518083038186803b15801561035057600080fd5b505afa158015610364573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103889190610eca565b90508281116103d45760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064015b60405180910390fd5b6000846040516020016103e991815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b031663441abbac61041f8684611088565b6040518263ffffffff1660e01b815260040161043d91815260200190565b60206040518083038186803b15801561045557600080fd5b505afa158015610469573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061048d9190610eca565b95945050505050565b60008083836040516020016104b5929190918252602082015260400190565b60408051808303601f19018152908290528051602090910120600054631106aeeb60e21b8352600483018290529092506001600160a01b03169063441abbac9060240160206040518083038186803b15801561051057600080fd5b505afa158015610524573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105489190610eca565b949350505050565b60005460405163011827fd60e01b81526001600160a01b039091169063011827fd9061058490869086908690600401611052565b600060405180830381600087803b15801561059e57600080fd5b505af11580156105b2573d6000803e3d6000fd5b50505050505050565b6000546001600160a01b0316635bf9755e83836105d95760006105dc565b60015b6040516001600160e01b031960e085901b168152600481019290925260ff1660248201526044015b600060405180830381600087803b15801561061e57600080fd5b505af1158015610632573d6000803e3d6000fd5b505050505050565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561067f57600080fd5b505afa158015610693573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106b79190610eca565b6001146106c55760006102af565b600192915050565b60408051602080820186905281830185905282518083038401815260608301938490528051910120600054632dfcbaaf60e11b9093526064820181905260848201849052916001600160a01b031690635bf9755e9060a4015b600060405180830381600087803b15801561074057600080fd5b505af1158015610754573d6000803e3d6000fd5b5050505050505050565b60008054604051631106aeeb60e21b8152600481018590526001600160a01b039091169063441abbac9060240160206040518083038186803b1580156107a357600080fd5b505afa1580156107b7573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107db9190610eca565b90506000836040516020016107f291815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b0316635bf9755e6108288484611088565b856040518363ffffffff1660e01b815260040161084f929190918252602082015260400190565b600060405180830381600087803b15801561086957600080fd5b505af115801561087d573d6000803e3d6000fd5b50506000546001600160a01b03169150635bf9755e9050856108a0856001611088565b6040516001600160e01b031960e085901b16815260048101929092526024820152604401610726565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561090e57600080fd5b505afa158015610922573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102af9190610eca565b600054604051632dfcbaaf60e11b815260048101849052602481018390526001600160a01b0390911690635bf9755e90604401600060405180830381600087803b15801561099357600080fd5b505af19250505080156109a4575060015b6102a0576109b0611140565b806308c379a014156109ea57506109c561115c565b806109d057506109ec565b8060405162461bcd60e51b81526004016103cb919061103f565b505b3d808015610a16576040519150601f19603f3d011682016040523d82523d6000602084013e610a1b565b606091505b508060405162461bcd60e51b81526004016103cb919061103f565b60008054604051631106aeeb60e21b8152600481018690526001600160a01b039091169063441abbac9060240160206040518083038186803b158015610a7b57600080fd5b505afa158015610a8f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610ab39190610eca565b9050808310610afa5760405162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b60448201526064016103cb565b600084604051602001610b0f91815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b0316635bf9755e610b458684611088565b856040518363ffffffff1660e01b8152600401610b6c929190918252602082015260400190565b600060405180830381600087803b158015610b8657600080fd5b505af1158015610b9a573d6000803e3d6000fd5b505050505050505050565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b158015610bea57600080fd5b505afa158015610bfe573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c229190610eca565b905060008111610c745760405162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e677468206172726179000060448201526064016103cb565b600082604051602001610c8991815260200190565b60408051601f1981840301815291905280516020909101206000549091506001600160a01b0316635bf9755e610cbf8484611088565b6040516001600160e01b031960e084901b168152600481019190915260006024820152604401600060405180830381600087803b158015610cff57600080fd5b505af1158015610d13573d6000803e3d6000fd5b50506000546001600160a01b03169150635bf9755e905084610d366001866110a0565b6040516001600160e01b031960e085901b16815260048101929092526024820152604401610584565b6000546040516357bc2ef360e01b8152600481018390526060916001600160a01b0316906357bc2ef39060240160006040518083038186803b158015610da457600080fd5b505afa158015610db8573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526102af9190810190610e19565b600054604051632dfcbaaf60e11b8152600481018490526001600160a01b03838116602483015290911690635bf9755e90604401610604565b600060208284031215610e2b57600080fd5b815167ffffffffffffffff80821115610e4357600080fd5b818401915084601f830112610e5757600080fd5b815181811115610e6957610e6961112a565b6040519150610e82601f8201601f1916602001836110e7565b808252856020828501011115610e9757600080fd5b610ea88160208401602086016110b7565b50949350505050565b600060208284031215610ec357600080fd5b5035919050565b600060208284031215610edc57600080fd5b5051919050565b60008060408385031215610ef657600080fd5b8235915060208301356001600160a01b0381168114610f1457600080fd5b809150509250929050565b60008060408385031215610f3257600080fd5b8235915060208301358015158114610f1457600080fd5b600080600060408486031215610f5e57600080fd5b83359250602084013567ffffffffffffffff80821115610f7d57600080fd5b818601915086601f830112610f9157600080fd5b813581811115610fa057600080fd5b876020828501011115610fb257600080fd5b6020830194508093505050509250925092565b60008060408385031215610fd857600080fd5b50508035926020909101359150565b600080600060608486031215610ffc57600080fd5b505081359360208301359350604090920135919050565b6000815180845261102b8160208601602086016110b7565b601f01601f19169290920160200192915050565b6020815260006102736020830184611013565b83815260406020820152816040820152818360608301376000818301606090810191909152601f909201601f1916010192915050565b6000821982111561109b5761109b611114565b500190565b6000828210156110b2576110b2611114565b500390565b60005b838110156110d25781810151838201526020016110ba565b838111156110e1576000848401525b50505050565b601f8201601f1916810167ffffffffffffffff8111828210171561110d5761110d61112a565b6040525050565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fd5b600060033d11156111595760046000803e5060005160e01c5b90565b600060443d101561116a5790565b6040516003193d81016004833e81513d67ffffffffffffffff816024840111818411171561119a57505050505090565b82850191508151818111156111b25750505050505090565b843d87010160208285010111156111cc5750505050505090565b6111db602082860101876110e7565b50909594505050505056fea264697066735822122057ea5251b3fdaa9e27d461cf0ec31b6eb180049d1f6976fcde83164b7cef5ff064736f6c63430008050033";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    public static final String FUNC_TEST_GETADDRESS = "test_getAddress";

    public static final String FUNC_TEST_GETARRAYLENGTH = "test_getArrayLength";

    public static final String FUNC_TEST_GETARRAYVALUE = "test_getArrayValue";

    public static final String FUNC_TEST_GETBOOL = "test_getBool";

    public static final String FUNC_TEST_GETBYTES = "test_getBytes";

    public static final String FUNC_TEST_GETMAPVALUE = "test_getMapValue";

    public static final String FUNC_TEST_GETUINT256 = "test_getUint256";

    public static final String FUNC_TEST_POPARRAYVALUE = "test_popArrayValue";

    public static final String FUNC_TEST_PUSHARRAYVALUE = "test_pushArrayValue";

    public static final String FUNC_TEST_SETADDRESS = "test_setAddress";

    public static final String FUNC_TEST_SETARRAYVALUE = "test_setArrayValue";

    public static final String FUNC_TEST_SETBOOL = "test_setBool";

    public static final String FUNC_TEST_SETBYTES = "test_setBytes";

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

    public RemoteFunctionCall<String> storageContract() {
        final Function function = new Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<byte[]> test_getBytes(BigInteger _key) {
        final Function function = new Function(FUNC_TEST_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
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

    public RemoteFunctionCall<TransactionReceipt> test_setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_TEST_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
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

    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TestLockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(TestLockableStorageWrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
