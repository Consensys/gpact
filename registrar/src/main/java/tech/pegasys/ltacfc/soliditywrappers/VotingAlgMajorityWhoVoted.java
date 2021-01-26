package tech.pegasys.ltacfc.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class VotingAlgMajorityWhoVoted extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506101bb806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c806319ab36c114610030575b600080fd5b6101686004803603606081101561004657600080fd5b67ffffffffffffffff823516919081019060408101602082013564010000000081111561007257600080fd5b82018360208201111561008457600080fd5b803590602001918460208302840111640100000000831117156100a657600080fd5b91908080602002602001604051908101604052809392919081815260200183836020028082843760009201919091525092959493602081019350359150506401000000008111156100f657600080fd5b82018360208201111561010857600080fd5b8035906020019184602083028401116401000000008311171561012a57600080fd5b91908080602002602001604051908101604052809392919081815260200183836020028082843760009201919091525092955061017c945050505050565b604080519115158252519081900360200190f35b5190511191905056fea264697066735822122053fa7f5d22cc661651c5a16a0206796eb1a5cc8919ee581bcf05be14aedf233164736f6c63430007040033";

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

    public String getRLP_assess(BigInteger param0, List<String> votedFor, List<String> votedAgainst) {
        final Function function = new Function(
                FUNC_ASSESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(param0), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(votedFor, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(votedAgainst, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
