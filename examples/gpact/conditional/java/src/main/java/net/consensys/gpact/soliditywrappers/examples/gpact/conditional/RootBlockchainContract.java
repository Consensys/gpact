package net.consensys.gpact.soliditywrappers.examples.gpact.conditional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class RootBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516108a53803806108a583398101604081905261002f91610082565b600080546001600160a01b039485166001600160a01b031991821617909155600492909255600580549190931691161790556100be565b80516001600160a01b038116811461007d57600080fd5b919050565b60008060006060848603121561009757600080fd5b6100a084610066565b9250602084015191506100b560408501610066565b90509250925092565b6107d8806100cd6000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063b52b657d1161005b578063b52b657d146100bd578063cb8b383b146100d0578063d9801925146100eb578063f6aacfb1146100f357600080fd5b806358033de11461008257806399eb5d4c14610097578063b5133231146100aa575b600080fd5b61009561009036600461065d565b610125565b005b6100956100a5366004610676565b61034d565b6100956100b836600461065d565b6103fd565b6100956100cb36600461065d565b61040b565b6100d8610416565b6040519081526020015b60405180910390f35b6100d8610427565b61011561010136600461065d565b600090815260026020526040902054151590565b60405190151581526020016100e2565b6000805460048054600554604080518481526024810182526020810180516001600160e01b03166370e5872960e11b1790529051632388b54d60e21b81526001600160a01b0395861695638e22d5349561018595949091169291016106a7565b6020604051808303816000875af11580156101a4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101c89190610713565b90506101d38161040b565b8082111561029157600054600480546005546040805160248101889052604480820188905282518083039091018152606490910182526020810180516001600160e01b0316634e1efd7b60e11b17905290516392b2c33560e01b81526001600160a01b03958616956392b2c335956102529594919091169291016106a7565b600060405180830381600087803b15801561026c57600080fd5b505af1158015610280573d6000803e3d6000fd5b5050505061028d816103fd565b5050565b600061029e83600d610742565b6000546004805460055460408051602480820188905282518083039091018152604490910182526020810180516001600160e01b03166303d4197f60e41b17905290516392b2c33560e01b81529596506001600160a01b03948516956392b2c3359561030d95931692016106a7565b600060405180830381600087803b15801561032757600080fd5b505af115801561033b573d6000803e3d6000fd5b50505050610348836103fd565b505050565b60005b6000828152600360205260409020548110156103e55760008281526003602052604081208054839081106103865761038661075a565b9060005260206000200154905083156103c4576000818152600260205260409020546103b490600190610770565b6000828152600160205260409020555b600090815260026020526040812055806103dd81610787565b915050610350565b50600081815260036020526040812061028d9161062b565b610408600082610433565b50565b610408600182610433565b600061042260006105bc565b905090565b600061042260016105bc565b6000828152600260205260409020541561048b5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064015b60405180910390fd5b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b8152600401602060405180830381865afa1580156104df573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105039190610713565b90508061051d575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b15801561056257600080fd5b505af1158015610576573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556105a8915083610742565b600084815260026020526040902055505050565b600081815260026020526040812054156106185760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610482565b5060009081526001602052604090205490565b508054600082559060005260206000209081019061040891905b808211156106595760008155600101610645565b5090565b60006020828403121561066f57600080fd5b5035919050565b6000806040838503121561068957600080fd5b8235801515811461069957600080fd5b946020939093013593505050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156106e9578581018301518582016080015282016106cd565b818111156106fb576000608083870101525b50601f01601f19169290920160800195945050505050565b60006020828403121561072557600080fd5b5051919050565b634e487b7160e01b600052601160045260246000fd5b600082198211156107555761075561072c565b500190565b634e487b7160e01b600052603260045260246000fd5b6000828210156107825761078261072c565b500390565b600060001982141561079b5761079b61072c565b506001019056fea26469706673582212208af7a3fc3c3e03c067980d6bcfb769412843464e5b5934008f173f2798f91d6f64736f6c634300080a0033";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETVAL1 = "getVal1";

    public static final String FUNC_GETVAL2 = "getVal2";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal1() {
        final Function function = new Function(FUNC_GETVAL1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getVal1() {
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

    public String getABI_getVal2() {
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

    public String getABI_isLocked(BigInteger _key) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public String getABI_setVal1(BigInteger _val) {
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

    public String getABI_setVal2(BigInteger _val) {
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

    public String getABI_someComplexBusinessLogic(BigInteger _val) {
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
