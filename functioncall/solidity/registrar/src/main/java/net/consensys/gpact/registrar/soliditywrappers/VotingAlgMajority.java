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
public class VotingAlgMajority extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610235806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c806319ab36c114610030575b600080fd5b61004361003e36600461013f565b610057565b604051901515815260200160405180910390f35b60008367ffffffffffffffff168351600261007291906101bc565b11949350505050565b80356001600160a01b038116811461009257600080fd5b919050565b600082601f8301126100a857600080fd5b8135602067ffffffffffffffff808311156100c5576100c56101e9565b8260051b604051601f19603f830116810181811084821117156100ea576100ea6101e9565b6040528481528381019250868401828801850189101561010957600080fd5b600092505b858310156101335761011f8161007b565b84529284019260019290920191840161010e565b50979650505050505050565b60008060006060848603121561015457600080fd5b833567ffffffffffffffff808216821461016d57600080fd5b9093506020850135908082111561018357600080fd5b61018f87838801610097565b935060408601359150808211156101a557600080fd5b506101b286828701610097565b9150509250925092565b60008160001904831182151516156101e457634e487b7160e01b600052601160045260246000fd5b500290565b634e487b7160e01b600052604160045260246000fdfea26469706673582212202305d4933d6e00dc20dd21f22e762aa01e6fc7a4ca56696af01e314a86ee80a664736f6c63430008050033";

    public static final String FUNC_ASSESS = "assess";

    @Deprecated
    protected VotingAlgMajority(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingAlgMajority(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingAlgMajority(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingAlgMajority(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> assess(BigInteger numParticipants, List<String> votedFor, List<String> param2) {
        final Function function = new Function(FUNC_ASSESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(numParticipants), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(votedFor, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(param2, org.web3j.abi.datatypes.Address.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static VotingAlgMajority load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingAlgMajority(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingAlgMajority load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingAlgMajority(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingAlgMajority load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VotingAlgMajority(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingAlgMajority load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VotingAlgMajority(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VotingAlgMajority> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingAlgMajority.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingAlgMajority> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingAlgMajority.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<VotingAlgMajority> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingAlgMajority.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<VotingAlgMajority> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingAlgMajority.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
