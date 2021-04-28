package net.consensys.gpact.examples.twochain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
public class OtherBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161053d38038061053d83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610082565b600060208284031215610065578081fd5b81516001600160a01b038116811461007b578182fd5b9392505050565b6104ac806100916000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630d7f9bde1461005c57806311ce0267146100665780633d4197f0146100965780639c3dfaf6146100a9578063e1cb0e52146100bc575b600080fd5b6100646100d2565b005b600054610079906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100646100a43660046102a5565b6100fb565b6100646100b73660046102d5565b610106565b6100c4610117565b60405190815260200161008d565b60006100de6001610129565b9050806100ea8161039c565b9150506100f86001826101ac565b50565b6100f86001826101ac565b6101136100a48284610349565b5050565b60006101236001610129565b90505b90565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561016e57600080fd5b505afa158015610182573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101a691906102bd565b92915050565b600054604051632dfcbaaf60e11b815260048101849052602481018390526001600160a01b0390911690635bf9755e90604401600060405180830381600087803b1580156101f957600080fd5b505af192505050801561020a575060015b610113576102166103cd565b806308c379a01415610259575061022b6103e4565b80610236575061025b565b8060405162461bcd60e51b815260040161025091906102f6565b60405180910390fd5b505b3d808015610285576040519150601f19603f3d011682016040523d82523d6000602084013e61028a565b606091505b508060405162461bcd60e51b815260040161025091906102f6565b6000602082840312156102b6578081fd5b5035919050565b6000602082840312156102ce578081fd5b5051919050565b600080604083850312156102e7578081fd5b50508035926020909101359150565b6000602080835283518082850152825b8181101561032257858101830151858201604001528201610306565b818111156103335783604083870101525b50601f01601f1916929092016040019392505050565b6000821982111561035c5761035c6103b7565b500190565b601f8201601f1916810167ffffffffffffffff8111828210171561039557634e487b7160e01b600052604160045260246000fd5b6040525050565b60006000198214156103b0576103b06103b7565b5060010190565b634e487b7160e01b600052601160045260246000fd5b600060033d111561012657600481823e5160e01c90565b600060443d10156103f457610126565b6040516003193d81016004833e81513d67ffffffffffffffff8160248401118184111715610426575050505050610126565b828501915081518181111561044057505050505050610126565b843d870101602082850101111561045c57505050505050610126565b61046b60208286010187610361565b50909450505050509056fea264697066735822122003f28266f247de961c3675c18dbc2f4d132443c0044172fb0fc0c1181ef7c68e64736f6c63430008020033";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_INCREMENTVAL = "incrementVal";

    public static final String FUNC_SETVAL = "setVal";

    public static final String FUNC_SETVALUES = "setValues";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getVal() {
        final Function function = new Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal() {
        final Function function = new Function(
                FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValues(BigInteger _val1, BigInteger _val2) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setValues(BigInteger _val1, BigInteger _val2) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val1), 
                new org.web3j.abi.datatypes.generated.Uint256(_val2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> storageContract() {
        final Function function = new Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_storageContract() {
        final Function function = new Function(
                FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
