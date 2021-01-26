package tech.pegasys.ltacfc.lockablestorage.soliditywrappers;

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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class LockableStorage extends Contract {
    public static final String BINARY = "60a060405234801561001057600080fd5b5060405161123b38038061123b8339818101604052602081101561003357600080fd5b505133606081901b608052600180546001600160a01b0319166001600160a01b03909316929092179091556111c661007560003980610bfe52506111c66000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c806357bc2ef31161006657806357bc2ef3146101965780635bf9755e146102285780639c235a681461024b578063cf30901214610271578063eda1824d1461028d5761009e565b8063011827fd146100a357806301b6a8801461011c578063160f474914610140578063441abbac1461015f5780635384d8bd1461018e575b600080fd5b61011a600480360360408110156100b957600080fd5b813591908101906040810160208201356401000000008111156100db57600080fd5b8201836020820111156100ed57600080fd5b8035906020019184600183028401116401000000008311171561010f57600080fd5b509092509050610295565b005b6101246104d1565b604080516001600160a01b039092168252519081900360200190f35b61011a6004803603602081101561015657600080fd5b503515156104e0565b61017c6004803603602081101561017557600080fd5b50356106cb565b60408051918252519081900360200190f35b61017c6107e6565b6101b3600480360360208110156101ac57600080fd5b50356107ec565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101ed5781810151838201526020016101d5565b50505050905090810190601f16801561021a5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61011a6004803603604081101561023e57600080fd5b50803590602001356109e3565b61011a6004803603602081101561026157600080fd5b50356001600160a01b0316610bfc565b610279610c69565b604080519115158252519081900360200190f35b61017c610c79565b6000546001600160a01b031633146102de5760405162461bcd60e51b81526004018080602001828103825260268152602001806111436026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561032c57600080fd5b505afa158015610340573d6000803e3d6000fd5b505050506040513d602081101561035657600080fd5b5051156103c557600154600160a01b900460ff16156103a65760405162461bcd60e51b815260040180806020018281038252603581526020018061110e6035913960400191505060405180910390fd5b60008381526008602052604090206103bf908383610f53565b506104cc565b600154600160a01b900460ff161561045f576103df610c7f565b60008381526009602052604090206103f8908383610f53565b506000838152600b602052604090205460ff1661045a57600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff191690911790555b6104cc565b610467610df3565b6000838152600960205260409020610480908383610f53565b50600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff191690911790555b505050565b6000546001600160a01b031681565b600154600160a01b900460ff16610534576040805162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f2066696e616c69736560681b604482015290519081900360640190fd5b80156105f15760005b6006548110156105825760006006828154811061055657fe5b60009182526020808320909101548252600581526040808320546004909252909120555060010161053d565b5060005b600a548110156105ef576000600a828154811061059f57fe5b6000918252602080832090910154808352600982526040808420600890935290922081549293506105e59290919060026000196101006001841615020190911604610fdf565b5050600101610586565b505b60005b6006548110156106415760006006828154811061060d57fe5b6000918252602080832090910154825260058152604080832083905560079091529020805460ff19169055506001016105f4565b5060005b600a548110156106a2576000600a828154811061065e57fe5b600091825260208083209091015480835260099091526040822090925061068491611062565b6000908152600b60205260409020805460ff19169055600101610645565b506106af600660006110a9565b6106bb600a60006110a9565b506001805460ff60a01b19169055565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561071057600080fd5b505afa158015610724573d6000803e3d6000fd5b505050506040513d602081101561073a57600080fd5b50511561079f57600154600160a01b900460ff161561078a5760405162461bcd60e51b815260040180806020018281038252603581526020018061110e6035913960400191505060405180910390fd5b506000818152600460205260409020546107e1565b600154600160a01b900460ff161561078a576107b9610c7f565b60008281526007602052604090205460ff161561078a57506000818152600560205260409020545b919050565b60025481565b60015460408051635a61dbab60e11b815290516060926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561083157600080fd5b505afa158015610845573d6000803e3d6000fd5b505050506040513d602081101561085b57600080fd5b50511561094a57600154600160a01b900460ff16156108ab5760405162461bcd60e51b815260040180806020018281038252603581526020018061110e6035913960400191505060405180910390fd5b60008281526008602090815260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452909183018282801561093e5780601f106109135761010080835404028352916020019161093e565b820191906000526020600020905b81548152906001019060200180831161092157829003601f168201915b505050505090506107e1565b600154600160a01b900460ff16156108ab57610964610c7f565b6000828152600b602052604090205460ff16156108ab5760008281526009602090815260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452909183018282801561093e5780601f106109135761010080835404028352916020019161093e565b6000546001600160a01b03163314610a2c5760405162461bcd60e51b81526004018080602001828103825260268152602001806111436026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b158015610a7a57600080fd5b505afa158015610a8e573d6000803e3d6000fd5b505050506040513d6020811015610aa457600080fd5b505115610b0a57600154600160a01b900460ff1615610af45760405162461bcd60e51b815260040180806020018281038252603581526020018061110e6035913960400191505060405180910390fd5b6000828152600460205260409020819055610bf8565b600154600160a01b900460ff1615610b9757610b24610c7f565b6000828152600560209081526040808320849055600790915290205460ff16610b92576006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f018390556000838152600760205260409020805460ff191690911790555b610bf8565b610b9f610df3565b60008281526005602090815260408083208490556006805460018082019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f018690556007909252909120805460ff191690911790555b5050565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03163314610c3157600080fd5b6000546001600160a01b031615610c4757600080fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600154600160a01b900460ff1681565b60035481565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610ccd57600080fd5b505afa158015610ce1573d6000803e3d6000fd5b505050506040513d6020811015610cf757600080fd5b505160025414610d385760405162461bcd60e51b81526004018080602001828103825260288152602001806111696028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610d8657600080fd5b505afa158015610d9a573d6000803e3d6000fd5b505050506040513d6020811015610db057600080fd5b505160035414610df15760405162461bcd60e51b81526004018080602001828103825260358152602001806110d96035913960400191505060405180910390fd5b565b6001805460ff60a01b1916600160a01b17908190556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610e4a57600080fd5b505afa158015610e5e573d6000803e3d6000fd5b505050506040513d6020811015610e7457600080fd5b50516002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610ebe57600080fd5b505afa158015610ed2573d6000803e3d6000fd5b505050506040513d6020811015610ee857600080fd5b505160035560015460408051631ce7083f60e11b815230600482015290516001600160a01b03909216916339ce107e9160248082019260009290919082900301818387803b158015610f3957600080fd5b505af1158015610f4d573d6000803e3d6000fd5b50505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282610f895760008555610fcf565b82601f10610fa25782800160ff19823516178555610fcf565b82800160010185558215610fcf579182015b82811115610fcf578235825591602001919060010190610fb4565b50610fdb9291506110c3565b5090565b828054600181600116156101000203166002900490600052602060002090601f0160209004810192826110155760008555610fcf565b82601f106110265780548555610fcf565b82800160010185558215610fcf57600052602060002091601f016020900482015b82811115610fcf578254825591600101919060010190611047565b50805460018160011615610100020316600290046000825580601f1061108857506110a6565b601f0160209004906000526020600020908101906110a691906110c3565b50565b50805460008255906000526020600020908101906110a691905b5b80821115610fdb57600081556001016110c456fe436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626c6f636b636861696e207472616e73616374696f6e417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c207768656e20636f6e7472616374206c6f636b65644f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6e7472616374436f6e7472616374206c6f636b6564206279206f7468657220726f6f7420626c6f636b636861696ea264697066735822122057271242b5f932c6b9ca7abadd7669191aea785a7daca2286e4ee31efd68bed164736f6c63430007040033";

    public static final String FUNC_BUSINESSLOGICCONTRACT = "businessLogicContract";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINID = "lockedByRootBlockchainId";

    public static final String FUNC_LOCKEDBYTRANSACTIONID = "lockedByTransactionId";

    public static final String FUNC_SETBUSINESSLOGICCONTRACT = "setBusinessLogicContract";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETUINT256 = "setUint256";

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> businessLogicContract() {
        final Function function = new Function(FUNC_BUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_businessLogicContract() {
        final Function function = new Function(
                FUNC_BUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> getBytes(BigInteger _key) {
        final Function function = new Function(FUNC_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_getBytes(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getUint256(BigInteger _key) {
        final Function function = new Function(FUNC_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getUint256(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> locked() {
        final Function function = new Function(FUNC_LOCKED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_locked() {
        final Function function = new Function(
                FUNC_LOCKED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> lockedByRootBlockchainId() {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_lockedByRootBlockchainId() {
        final Function function = new Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> lockedByTransactionId() {
        final Function function = new Function(FUNC_LOCKEDBYTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_lockedByTransactionId() {
        final Function function = new Function(
                FUNC_LOCKEDBYTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBusinessLogicContract(String _businessLogicContract) {
        final Function function = new Function(
                FUNC_SETBUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _businessLogicContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBusinessLogicContract(String _businessLogicContract) {
        final Function function = new Function(
                FUNC_SETBUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _businessLogicContract)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
