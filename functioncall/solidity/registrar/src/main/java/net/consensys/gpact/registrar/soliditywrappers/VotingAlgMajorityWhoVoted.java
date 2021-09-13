package net.consensys.gpact.registrar.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class VotingAlgMajorityWhoVoted extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506101e8806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c806319ab36c114610030575b600080fd5b61004761003e36600461011f565b51905111919050565b604051901515815260200160405180910390f35b80356001600160a01b038116811461007257600080fd5b919050565b600082601f83011261008857600080fd5b8135602067ffffffffffffffff808311156100a5576100a561019c565b8260051b604051601f19603f830116810181811084821117156100ca576100ca61019c565b604052848152838101925086840182880185018910156100e957600080fd5b600092505b85831015610113576100ff8161005b565b8452928401926001929092019184016100ee565b50979650505050505050565b60008060006060848603121561013457600080fd5b833567ffffffffffffffff808216821461014d57600080fd5b9093506020850135908082111561016357600080fd5b61016f87838801610077565b9350604086013591508082111561018557600080fd5b5061019286828701610077565b9150509250925092565b634e487b7160e01b600052604160045260246000fdfea26469706673582212206b6d502c5d778469d2b29284cea8f2364cf80fb4691a0ec68a2d6510e96861fd64736f6c63430008050033";

    public static final String FUNC_ASSESS = "assess";

    @Deprecated
    protected VotingAlgMajorityWhoVoted(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingAlgMajorityWhoVoted(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingAlgMajorityWhoVoted(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingAlgMajorityWhoVoted(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> assess(BigInteger param0, List<String> votedFor, List<String> votedAgainst) {
        final Function function = new Function(FUNC_ASSESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(param0), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(votedFor, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(votedAgainst, org.web3j.abi.datatypes.Address.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static VotingAlgMajorityWhoVoted load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingAlgMajorityWhoVoted(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingAlgMajorityWhoVoted load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingAlgMajorityWhoVoted(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingAlgMajorityWhoVoted load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VotingAlgMajorityWhoVoted(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingAlgMajorityWhoVoted load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VotingAlgMajorityWhoVoted(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VotingAlgMajorityWhoVoted> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingAlgMajorityWhoVoted.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingAlgMajorityWhoVoted> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingAlgMajorityWhoVoted.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<VotingAlgMajorityWhoVoted> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingAlgMajorityWhoVoted.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingAlgMajorityWhoVoted> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingAlgMajorityWhoVoted.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
