package tech.pegasys.ltacfc.examples.trade.soliditywrappers;

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
    public static final String BINARY = "60a060405234801561001057600080fd5b506040516112ef3803806112ef8339818101604052602081101561003357600080fd5b505133606081901b608052600180546001600160a01b0319166001600160a01b039093169290921790915561127a61007560003980610b97525061127a6000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80635bf9755e116100665780635bf9755e146101f6578063634354661461021957806397cedc761461024a5780639c235a681461026f578063cf3090121461029557610093565b8063011827fd1461009857806301b6a88014610111578063441abbac1461013557806357bc2ef314610164575b600080fd5b61010f600480360360408110156100ae57600080fd5b813591908101906040810160208201356401000000008111156100d057600080fd5b8201836020820111156100e257600080fd5b8035906020019184600183028401116401000000008311171561010457600080fd5b50909250905061029d565b005b610119610433565b604080516001600160a01b039092168252519081900360200190f35b6101526004803603602081101561014b57600080fd5b5035610442565b60408051918252519081900360200190f35b6101816004803603602081101561017a57600080fd5b5035610563565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101bb5781810151838201526020016101a3565b50505050905090810190601f1680156101e85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61010f6004803603604081101561020c57600080fd5b5080359060200135610762565b6102366004803603602081101561022f57600080fd5b50356108e2565b604080519115158252519081900360200190f35b61010f6004803603604081101561026057600080fd5b50803590602001351515610a02565b61010f6004803603602081101561028557600080fd5b50356001600160a01b0316610b95565b610236610c02565b6000546001600160a01b031633146102e65760405162461bcd60e51b81526004018080602001828103825260268152602001806111f76026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561033457600080fd5b505afa158015610348573d6000803e3d6000fd5b505050506040513d602081101561035e57600080fd5b5051156103d057600154600160a01b900460ff16156103ae5760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b60008381526004602052604090206103ca9060020183836110f1565b5061042e565b600154600160a01b900460ff1615610407576103eb83610c12565b60008381526005602052604090206103ca9060020183836110f1565b61041083610dd1565b600083815260056020526040902061042c9060020183836110f1565b505b505050565b6000546001600160a01b031681565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561048757600080fd5b505afa15801561049b573d6000803e3d6000fd5b505050506040513d60208110156104b157600080fd5b50511561051957600154600160a01b900460ff16156105015760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b5060008181526004602052604090206001015461055e565b600154600160a01b900460ff161561050157610533610f7d565b60008281526007602052604090205460ff161561050157506000818152600560205260409020600101545b919050565b60015460408051635a61dbab60e11b815290516060926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b1580156105a857600080fd5b505afa1580156105bc573d6000803e3d6000fd5b505050506040513d60208110156105d257600080fd5b5051156106c557600154600160a01b900460ff16156106225760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b6000828152600460209081526040918290206002908101805484516001821615610100026000190190911692909204601f8101849004840283018401909452838252909290918301828280156106b95780601f1061068e576101008083540402835291602001916106b9565b820191906000526020600020905b81548152906001019060200180831161069c57829003601f168201915b5050505050905061055e565b600154600160a01b900460ff1615610622576106df610f7d565b60008281526007602052604090205460ff1615610622576000828152600560209081526040918290206002908101805484516001821615610100026000190190911692909204601f8101849004840283018401909452838252909290918301828280156106b95780601f1061068e576101008083540402835291602001916106b9565b6000546001600160a01b031633146107ab5760405162461bcd60e51b81526004018080602001828103825260268152602001806111f76026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156107f957600080fd5b505afa15801561080d573d6000803e3d6000fd5b505050506040513d602081101561082357600080fd5b50511561088c57600154600160a01b900460ff16156108735760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b60008281526004602052604090206001018190556108de565b600154600160a01b900460ff16156108c0576108a782610c12565b60008281526005602052604090206001018190556108de565b6108c982610dd1565b60008281526005602052604090206001018190555b5050565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561092757600080fd5b505afa15801561093b573d6000803e3d6000fd5b505050506040513d602081101561095157600080fd5b5051156109b957600154600160a01b900460ff16156109a15760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b5060008181526004602052604090205460ff1661055e565b600154600160a01b900460ff16156109a1576109d3610f7d565b60008281526007602052604090205460ff16156109a1575060008181526005602052604090205460ff1661055e565b6000546001600160a01b03163314610a4b5760405162461bcd60e51b81526004018080602001828103825260268152602001806111f76026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b158015610a9957600080fd5b505afa158015610aad573d6000803e3d6000fd5b505050506040513d6020811015610ac357600080fd5b505115610b3257600154600160a01b900460ff1615610b135760405162461bcd60e51b81526004018080602001828103825260358152602001806111c26035913960400191505060405180910390fd5b6000828152600460205260409020805460ff19168215151790556108de565b600154600160a01b900460ff1615610b6c57610b4d82610c12565b6000828152600560205260409020805460ff19168215151790556108de565b610b7582610dd1565b6000828152600560205260409020805482151560ff199091161790555050565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03163314610bca57600080fd5b6000546001600160a01b031615610be057600080fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600154600160a01b900460ff1681565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610c6057600080fd5b505afa158015610c74573d6000803e3d6000fd5b505050506040513d6020811015610c8a57600080fd5b505160025414610ccb5760405162461bcd60e51b815260040180806020018281038252602881526020018061121d6028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610d1957600080fd5b505afa158015610d2d573d6000803e3d6000fd5b505050506040513d6020811015610d4357600080fd5b505160035414610d845760405162461bcd60e51b815260040180806020018281038252603581526020018061118d6035913960400191505060405180910390fd5b6006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01829055600091825260076020526040909120805460ff19169091179055565b6001805460ff60a01b1916600160a01b17908190556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610e2857600080fd5b505afa158015610e3c573d6000803e3d6000fd5b505050506040513d6020811015610e5257600080fd5b50516002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610e9c57600080fd5b505afa158015610eb0573d6000803e3d6000fd5b505050506040513d6020811015610ec657600080fd5b505160035560015460408051630df9736160e01b815230600482015290516001600160a01b0390921691630df973619160248082019260009290919082900301818387803b158015610f1757600080fd5b505af1158015610f2b573d6000803e3d6000fd5b50506006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01849055600093845260076020526040909320805460ff19169093179092555050565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610fcb57600080fd5b505afa158015610fdf573d6000803e3d6000fd5b505050506040513d6020811015610ff557600080fd5b5051600254146110365760405162461bcd60e51b815260040180806020018281038252602881526020018061121d6028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b15801561108457600080fd5b505afa158015611098573d6000803e3d6000fd5b505050506040513d60208110156110ae57600080fd5b5051600354146110ef5760405162461bcd60e51b815260040180806020018281038252603581526020018061118d6035913960400191505060405180910390fd5b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111325782800160ff1982351617855561115f565b8280016001018555821561115f579182015b8281111561115f578235825591602001919060010190611144565b5061116b92915061116f565b5090565b61118991905b8082111561116b5760008155600101611175565b9056fe436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626c6f636b636861696e207472616e73616374696f6e417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c207768656e20636f6e7472616374206c6f636b65644f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6e7472616374436f6e7472616374206c6f636b6564206279206f7468657220726f6f7420626c6f636b636861696ea2646970667358221220545854444acfc3542bf256e6957c378e78bac845adcf697aa0bf341fd9462c1a64736f6c634300060b0033";

    public static final String FUNC_BUSINESSLOGICCONTRACT = "businessLogicContract";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_SETBOOL = "setBool";

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

    public RemoteFunctionCall<Boolean> getBool(BigInteger _key) {
        final Function function = new Function(FUNC_GETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_getBool(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public RemoteFunctionCall<TransactionReceipt> setBool(BigInteger _key, Boolean _val) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBool(BigInteger _key, Boolean _val) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_val)), 
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
