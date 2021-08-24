package net.consensys.gpact.examples.conditional.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
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
public class RootBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610a7c380380610a7c83398101604081905261002f91610082565b600080546001600160a01b039485166001600160a01b031991821617909155600592909255600680549190931691161790556100be565b80516001600160a01b038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b6109af806100cd6000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063cb8b383b1161005b578063cb8b383b146100fb578063d980192514610103578063f6aacfb11461010b578063fcea50961461013d57600080fd5b806358033de11461008d5780637445b0d0146100a2578063b5133231146100d5578063b52b657d146100e8575b600080fd5b6100a061009b36600461086e565b610150565b005b6100c26100b036600461086e565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100a06100e336600461086e565b610389565b6100a06100f636600461086e565b610397565b6100c26103a2565b6100c26103b3565b61012d61011936600461086e565b600090815260046020526040902054151590565b60405190151581526020016100cc565b6100a061014b366004610839565b6103bf565b600080546005546006546040805160048082526024820183526020820180516001600160e01b03166370e5872960e11b1790529151632388b54d60e21b81526001600160a01b0395861695638e22d534956101b195909491169291016108a0565b602060405180830381600087803b1580156101cb57600080fd5b505af11580156101df573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102039190610887565b905061020e81610397565b808211156102cb576000546005546006546040805160248101879052604480820187905282518083039091018152606490910182526020810180516001600160e01b0316634e1efd7b60e11b17905290516392b2c33560e01b81526001600160a01b03948516946392b2c3359461028c9490939116916004016108a0565b600060405180830381600087803b1580156102a657600080fd5b505af11580156102ba573d6000803e3d6000fd5b505050506102c781610389565b5050565b60006102d883600d61090c565b60005460055460065460408051602480820187905282518083039091018152604490910182526020810180516001600160e01b03166303d4197f60e41b17905290516392b2c33560e01b81529495506001600160a01b03938416946392b2c3359461034994931691906004016108a0565b600060405180830381600087803b15801561036357600080fd5b505af1158015610377573d6000803e3d6000fd5b5050505061038483610389565b505050565b61039460008261049b565b50565b61039460018261049b565b60006103ae6000610774565b905090565b60006103ae6001610774565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b60008281526003602052604090205481101561047d57600082815260036020526040812080548390811061042057610420610955565b90600052602060002001549050851561044f576000818152600260209081526040808320546001909252909120555b600090815260026020908152604080832083905560049091528120558061047581610924565b9150506103ea565b506000818152600360205260408120610495916107e3565b50505050565b600082815260046020526040902054156104f35760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064015b60405180910390fd5b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561053f57600080fd5b505afa158015610553573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105779190610815565b1561058e5760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b1580156105dd57600080fd5b505afa1580156105f1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106159190610887565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b15801561066657600080fd5b505afa15801561067a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061069e9190610887565b60408051602080820186905281830184905282518083038401815260609092019092528051910120909150600090600086815260046020819052604080832084905591549151631ce7083f60e11b815230918101919091529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561072657600080fd5b505af115801561073a573d6000803e3d6000fd5b5050506000918252506003602090815260408083208054600181018255908452828420018790558683526002909152902083905550505050565b600081815260046020526040812054156107d05760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016104ea565b5060009081526001602052604090205490565b508054600082559060005260206000209081019061039491905b8082111561081157600081556001016107fd565b5090565b60006020828403121561082757600080fd5b81516108328161096b565b9392505050565b60008060006060848603121561084e57600080fd5b83356108598161096b565b95602085013595506040909401359392505050565b60006020828403121561088057600080fd5b5035919050565b60006020828403121561089957600080fd5b5051919050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156108e2578581018301518582016080015282016108c6565b818111156108f4576000608083870101525b50601f01601f19169290920160800195945050505050565b6000821982111561091f5761091f61093f565b500190565b60006000198214156109385761093861093f565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b801515811461039457600080fdfea2646970667358221220ceee39bf8c06ab77debacd4f2fa9a11078f8a3ed0cc4a76008e3e90cdae759d464736f6c63430008050033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL1 = "getVal1";

    public static final String FUNC_GETVAL2 = "getVal2";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

    public static final String FUNC_SETVAL1 = "setVal1";

    public static final String FUNC_SETVAL2 = "setVal2";

    public static final String FUNC_SOMECOMPLEXBUSINESSLOGIC = "someComplexBusinessLogic";

    @Deprecated
    protected RootBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RootBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RootBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RootBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public String getRLP_finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal1() {
        final Function function = new Function(FUNC_GETVAL1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal1() {
        final Function function = new Function(
                FUNC_GETVAL1, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal2() {
        final Function function = new Function(FUNC_GETVAL2, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal2() {
        final Function function = new Function(
                FUNC_GETVAL2, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger _key) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final Function function = new Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal1(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal1(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal2(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal2(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> someComplexBusinessLogic(BigInteger _val) {
        final Function function = new Function(
                FUNC_SOMECOMPLEXBUSINESSLOGIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_someComplexBusinessLogic(BigInteger _val) {
        final Function function = new Function(
                FUNC_SOMECOMPLEXBUSINESSLOGIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static RootBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootBlockchainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RootBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootBlockchainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RootBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RootBlockchainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RootBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RootBlockchainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
