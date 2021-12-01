package net.consensys.gpact.cbc.calltree.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tuples.generated.Tuple3;
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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class CallExecutionTreeTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061084c806100206000396000f3fe608060405234801561001057600080fd5b5060043610610047577c01000000000000000000000000000000000000000000000000000000006000350463ac239026811461004c575b600080fd5b61005f61005a36600461061d565b610077565b60405161006e939291906106d5565b60405180910390f35b600080606061008885856001610095565b9250925092509250925092565b60008060606000805b86518110156101c2576000806100b48a856102cd565b905060ff811661014d57600189516100cc919061077e565b83146101485760405160e560020a62461bcd02815260206004820152603260248201527f52656163686564206c6561662066756e6374696f6e206275742074686572652060448201527f6973206d6f72652063616c6c20706174682e000000000000000000000000000060648201526084015b60405180910390fd5b6101a1565b600089848151811061016157610161610795565b602002602001015190506101978b600160048461017e91906107c4565b61018890896107e3565b61019291906107e3565b610336565b63ffffffff169250505b6101ab82856107e3565b9350505080806101ba906107fb565b91505061009e565b5085600187516101d2919061077e565b815181106101e2576101e2610795565b60200260200101516000146102435760006101fd88836102cd565b905060ff811615610233576000610219896101926001866107e3565b63ffffffff16905061022b81846107e3565b925050610241565b61023e6001836107e3565b91505b505b61024d878261039f565b935061025a6020826107e3565b90506102698782016014015190565b925084156102b05761027c6014826107e3565b9050600061028a8883610408565b90506102976002836107e3565b91506102a888838361ffff16610471565b9250506102c3565b6040518060200160405280600081525091505b5093509350939050565b60006102da8260016107e3565b8351101561032d5760405160e560020a62461bcd02815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e74382900000000604482015260640161013f565b50016001015190565b60006103438260046107e3565b835110156103965760405160e560020a62461bcd02815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e74333229000000604482015260640161013f565b50016004015190565b60006103ac8260206107e3565b835110156103ff5760405160e560020a62461bcd02815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e74323536290000604482015260640161013f565b50016020015190565b60006104158260026107e3565b835110156104685760405160e560020a62461bcd02815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e74313629000000604482015260640161013f565b50016002015190565b606061047d82846107e3565b845110156104d05760405160e560020a62461bcd02815260206004820152601260248201527f52656164206f7574206f6620626f756e64730000000000000000000000000000604482015260640161013f565b6060821580156104eb57604051915060208201604052610535565b6040519150601f8416801560200281840101858101878315602002848b0101015b8183101561052457805183526020928301920161050c565b5050858452601f01601f1916604052505b50949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff811182821017156105965761059661053e565b604052919050565b600082601f8301126105af57600080fd5b8135602067ffffffffffffffff8211156105cb576105cb61053e565b8082026105d982820161056d565b92835284810182019282810190878511156105f357600080fd5b83870192505b84831015610612578235825291830191908301906105f9565b979650505050505050565b6000806040838503121561063057600080fd5b823567ffffffffffffffff8082111561064857600080fd5b818501915085601f83011261065c57600080fd5b81356020828211156106705761067061053e565b610682601f8301601f1916820161056d565b828152888284870101111561069657600080fd5b828286018383013760009281018201929092529094508501359150808211156106be57600080fd5b506106cb8582860161059e565b9150509250929050565b8381526000602073ffffffffffffffffffffffffffffffffffffffff85168184015260606040840152835180606085015260005b8181101561072557858101830151858201608001528201610709565b81811115610737576000608083870101525b50601f01601f19169290920160800195945050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000828210156107905761079061074f565b500390565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b60008160001904831182151516156107de576107de61074f565b500290565b600082198211156107f6576107f661074f565b500190565b600060001982141561080f5761080f61074f565b506001019056fea26469706673582212209f5a61f6165282f8533457e110bfb53dd62fb2a4e7232c777cc2f3791c43c54364736f6c63430008090033";

    public static final String FUNC_EXTRACTTARGETFROMCALLGRAPH1 = "extractTargetFromCallGraph1";

    @Deprecated
    protected CallExecutionTreeTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CallExecutionTreeTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CallExecutionTreeTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CallExecutionTreeTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, byte[]>> extractTargetFromCallGraph1(byte[] _callGraph, List<BigInteger> _callPath) {
        final Function function = new Function(FUNC_EXTRACTTARGETFROMCALLGRAPH1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_callGraph), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_callPath, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, byte[]>>(function,
                new Callable<Tuple3<BigInteger, String, byte[]>>() {
                    @Override
                    public Tuple3<BigInteger, String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static CallExecutionTreeTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CallExecutionTreeTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CallExecutionTreeTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CallExecutionTreeTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CallExecutionTreeTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CallExecutionTreeTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CallExecutionTreeTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CallExecutionTreeTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CallExecutionTreeTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CallExecutionTreeTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CallExecutionTreeTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CallExecutionTreeTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CallExecutionTreeTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CallExecutionTreeTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CallExecutionTreeTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CallExecutionTreeTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
