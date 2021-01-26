package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class RootBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516108343803806108348339818101604052608081101561003357600080fd5b50805160208201516040830151606090930151600080546001600160a01b039283166001600160a01b03199182161790915560018054948316948216949094179093556002919091556003805491909316911617905561079c806100986000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806311ce02671461006757806358033de11461008b578063b5133231146100aa578063b52b657d146100c7578063cb8b383b146100e4578063d9801925146100fe575b600080fd5b61006f610106565b604080516001600160a01b039092168252519081900360200190f35b6100a8600480360360208110156100a157600080fd5b5035610115565b005b6100a8600480360360208110156100c057600080fd5b5035610492565b6100a8600480360360208110156100dd57600080fd5b50356104a0565b6100ec6104ab565b60408051918252519081900360200190f35b6100ec6104bd565b6000546001600160a01b031681565b600154600254600354604080516004808252602480830184526020830180516001600160e01b03166370e5872960e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b838110156101b657818101518382015260200161019e565b50505050905090810190601f1680156101e35780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561020457600080fd5b505af1158015610218573d6000803e3d6000fd5b505050506040513d602081101561022e57600080fd5b5051905061023b816104a0565b8082111561036b576001546002546003546040805160248082018890526044808301889052835180840382018152606493840185526020810180516001600160e01b0316634e1efd7b60e11b17815294516392b2c33560e01b8152600481018881526001600160a01b039788169482018590526060938201938452825195820195909552815196909816976392b2c335979693959194936084909101919080838360005b838110156102f75781810151838201526020016102df565b50505050905090810190601f1680156103245780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b15801561034557600080fd5b505af1158015610359573d6000803e3d6000fd5b5050505061036681610492565b61048e565b60015460025460035460408051600d87016024808301829052835180840382018152604493840185526020810180516001600160e01b03166303d4197f60e41b17815294516392b2c33560e01b8152600481018881526001600160a01b039788169382018490526060958201958652825160648301528251949997909716976392b2c3359790969395929490936084909201919080838360005b8381101561041d578181015183820152602001610405565b50505050905090810190601f16801561044a5780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b15801561046b57600080fd5b505af115801561047f573d6000803e3d6000fd5b5050505061048c83610492565b505b5050565b61049d6000826104c9565b50565b61049d6001826104c9565b60006104b7600061063c565b90505b90565b60006104b7600161063c565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561051857600080fd5b505af1925050508015610529575060015b61048e576105356106c1565b8061054057506105c5565b8060405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561058a578181015183820152602001610572565b50505050905090810190601f1680156105b75780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b3d8080156105ef576040519150601f19603f3d011682016040523d82523d6000602084013e6105f4565b606091505b5060405162461bcd60e51b815260206004820181815283516024840152835184939192839260440191908501908083836000831561058a578181015183820152602001610572565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561068957600080fd5b505afa15801561069d573d6000803e3d6000fd5b505050506040513d60208110156106b357600080fd5b505192915050565b60e01c90565b600060443d10156106d1576104ba565b600481823e6308c379a06106e582516106bb565b146106ef576104ba565b6040513d600319016004823e80513d67ffffffffffffffff816024840111818411171561071f57505050506104ba565b8284019250825191508082111561073957505050506104ba565b503d83016020828401011115610751575050506104ba565b601f01601f191681016020016040529150509056fea2646970667358221220331b1e713c5b636f3721ab70de3a207cbfb1847006a934691e41bc9cb1069a1a64736f6c63430007040033";

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
