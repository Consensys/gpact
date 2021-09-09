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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class CallExecutionTreeTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506107b0806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063ac23902614610030575b600080fd5b61004361003e36600461057a565b61005b565b60405161005293929190610632565b60405180910390f35b600080606061006a8585610077565b9250925092509250925092565b60008060606000805b855181101561019657600080610096898561029d565b905060ff811661012157600188516100ae9190610706565b831461011c5760405162461bcd60e51b815260206004820152603260248201527f52656163686564206c6561662066756e6374696f6e206275742074686572652060448201527134b99036b7b9329031b0b636103830ba341760711b60648201526084015b60405180910390fd5b610175565b60008884815181106101355761013561074e565b6020026020010151905061016b8a600160048461015291906106e7565b61015c90896106cf565b61016691906106cf565b610303565b63ffffffff169250505b61017f82856106cf565b93505050808061018e9061071d565b915050610080565b5084600186516101a69190610706565b815181106101b6576101b661074e565b60200260200101516000146102335760006101d1878361029d565b905060ff8116156102245760405162461bcd60e51b815260206004820152601760248201527f4578706563746564206c6561662066756e6374696f6e2e0000000000000000006044820152606401610113565b61022f6001836106cf565b9150505b61023d8682610369565b935061024a6020826106cf565b90506102598682016014015190565b92506102666014826106cf565b9050600061027487836103cf565b90506102816002836106cf565b915061029287838361ffff16610435565b925050509250925092565b60006102aa8260016106cf565b835110156102fa5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e743829000000006044820152606401610113565b50016001015190565b60006103108260046106cf565b835110156103605760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743332290000006044820152606401610113565b50016004015190565b60006103768260206106cf565b835110156103c65760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e743235362900006044820152606401610113565b50016020015190565b60006103dc8260026106cf565b8351101561042c5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743136290000006044820152606401610113565b50016002015190565b606060008267ffffffffffffffff81111561045257610452610764565b6040519080825280601f01601f19166020018201604052801561047c576020820181803683370190505b50905060005b838110156104eb578561049582876106cf565b815181106104a5576104a561074e565b602001015160f81c60f81b8282815181106104c2576104c261074e565b60200101906001600160f81b031916908160001a905350806104e38161071d565b915050610482565b50949350505050565b600082601f83011261050557600080fd5b8135602067ffffffffffffffff82111561052157610521610764565b8160051b61053082820161069e565b83815282810190868401838801850189101561054b57600080fd5b600093505b8584101561056e578035835260019390930192918401918401610550565b50979650505050505050565b6000806040838503121561058d57600080fd5b823567ffffffffffffffff808211156105a557600080fd5b818501915085601f8301126105b957600080fd5b81356020828211156105cd576105cd610764565b6105df601f8301601f1916820161069e565b82815288828487010111156105f357600080fd5b8282860183830137600092810182019290925290945085013591508082111561061b57600080fd5b50610628858286016104f4565b9150509250929050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b8181101561067457858101830151858201608001528201610658565b81811115610686576000608083870101525b50601f01601f19169290920160800195945050505050565b604051601f8201601f1916810167ffffffffffffffff811182821017156106c7576106c7610764565b604052919050565b600082198211156106e2576106e2610738565b500190565b600081600019048311821515161561070157610701610738565b500290565b60008282101561071857610718610738565b500390565b600060001982141561073157610731610738565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122099058a11c745ba9268acfab680e711e21d3cfd130d6cc8b28e01dee52f2d40c664736f6c63430008050033";

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
