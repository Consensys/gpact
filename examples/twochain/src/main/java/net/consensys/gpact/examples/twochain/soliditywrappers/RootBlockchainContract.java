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
public class RootBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516107d23803806107d283398101604081905261002f91610091565b600080546001600160a01b03199081166001600160a01b0393841617909155600180548216958316959095179094556002929092556003805490931691161790556100de565b80516001600160a01b038116811461008c57600080fd5b919050565b600080600080608085870312156100a757600080fd5b6100b085610075565b9350602085015192506100c560408601610075565b91506100d360608601610075565b905092959194509250565b6106e5806100ed6000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806311ce02671461006757806358033de114610097578063b5133231146100ac578063b52b657d146100bf578063cb8b383b146100d2578063d9801925146100e8575b600080fd5b60005461007a906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100aa6100a53660046104dc565b6100f0565b005b6100aa6100ba3660046104dc565b61032a565b6100aa6100cd3660046104dc565b610338565b6100da610343565b60405190815260200161008e565b6100da610354565b6001546002546003546040805160048082526024820183526020820180516001600160e01b03166370e5872960e11b1790529151632388b54d60e21b81526000956001600160a01b0390811695638e22d5349561015295919492169201610575565b602060405180830381600087803b15801561016c57600080fd5b505af1158015610180573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101a491906104f5565b90506101af81610338565b8082111561026c576001546002546003546040805160248101879052604480820187905282518083039091018152606490910182526020810180516001600160e01b0316634e1efd7b60e11b17905290516392b2c33560e01b81526001600160a01b03948516946392b2c3359461022d949093911691600401610575565b600060405180830381600087803b15801561024757600080fd5b505af115801561025b573d6000803e3d6000fd5b505050506102688161032a565b5050565b600061027983600d6105a8565b60015460025460035460408051602480820187905282518083039091018152604490910182526020810180516001600160e01b03166303d4197f60e41b17905290516392b2c33560e01b81529495506001600160a01b03938416946392b2c335946102ea9493169190600401610575565b600060405180830381600087803b15801561030457600080fd5b505af1158015610318573d6000803e3d6000fd5b505050506103258361032a565b505050565b610335600082610360565b50565b610335600182610360565b600061034f6000610459565b905090565b600061034f6001610459565b600054604051632dfcbaaf60e11b815260048101849052602481018390526001600160a01b0390911690635bf9755e90604401600060405180830381600087803b1580156103ad57600080fd5b505af19250505080156103be575060015b610268576103ca610609565b806308c379a0141561040d57506103df610625565b806103ea575061040f565b8060405162461bcd60e51b8152600401610404919061055b565b60405180910390fd5b505b3d808015610439576040519150601f19603f3d011682016040523d82523d6000602084013e61043e565b606091505b508060405162461bcd60e51b8152600401610404919061055b565b60008054604051631106aeeb60e21b8152600481018490526001600160a01b039091169063441abbac9060240160206040518083038186803b15801561049e57600080fd5b505afa1580156104b2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104d691906104f5565b92915050565b6000602082840312156104ee57600080fd5b5035919050565b60006020828403121561050757600080fd5b5051919050565b6000815180845260005b8181101561053457602081850181015186830182015201610518565b81811115610546576000602083870101525b50601f01601f19169290920160200192915050565b60208152600061056e602083018461050e565b9392505050565b8381526001600160a01b038316602082015260606040820181905260009061059f9083018461050e565b95945050505050565b600082198211156105c957634e487b7160e01b600052601160045260246000fd5b500190565b601f8201601f1916810167ffffffffffffffff8111828210171561060257634e487b7160e01b600052604160045260246000fd5b6040525050565b600060033d11156106225760046000803e5060005160e01c5b90565b600060443d10156106335790565b6040516003193d81016004833e81513d67ffffffffffffffff816024840111818411171561066357505050505090565b828501915081518181111561067b5750505050505090565b843d87010160208285010111156106955750505050505090565b6106a4602082860101876105ce565b50909594505050505056fea2646970667358221220fd10424652d631608df9d957a471decc5fdeba51e41af4509e22d5adacecc05f64736f6c63430008050033";

    public static final String FUNC_GETVAL1 = "getVal1";

    public static final String FUNC_GETVAL2 = "getVal2";

    public static final String FUNC_SETVAL1 = "setVal1";

    public static final String FUNC_SETVAL2 = "setVal2";

    public static final String FUNC_SOMECOMPLEXBUSINESSLOGIC = "someComplexBusinessLogic";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

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

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
