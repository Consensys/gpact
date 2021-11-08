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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class CallExecutionTreeTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506107b0806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063ac23902614610030575b600080fd5b61004361003e3660046105c1565b61005b565b60405161005293929190610679565b60405180910390f35b600080606061006c85856001610079565b9250925092509250925092565b60008060606000805b8651811015610198576000806100988a856102a3565b905060ff811661012357600189516100b091906106fb565b831461011e5760405162461bcd60e51b815260206004820152603260248201527f52656163686564206c6561662066756e6374696f6e206275742074686572652060448201527134b99036b7b9329031b0b636103830ba341760711b60648201526084015b60405180910390fd5b610177565b600089848151811061013757610137610712565b6020026020010151905061016d8b60016004846101549190610728565b61015e9089610747565b6101689190610747565b610309565b63ffffffff169250505b6101818285610747565b9350505080806101909061075f565b915050610082565b5085600187516101a891906106fb565b815181106101b8576101b8610712565b60200260200101516000146102195760006101d388836102a3565b905060ff8116156102095760006101ef89610168600186610747565b63ffffffff1690506102018184610747565b925050610217565b610214600183610747565b91505b505b610223878261036f565b9350610230602082610747565b905061023f8782016014015190565b9250841561028657610252601482610747565b9050600061026088836103d5565b905061026d600283610747565b915061027e88838361ffff1661043b565b925050610299565b6040518060200160405280600081525091505b5093509350939050565b60006102b0826001610747565b835110156103005760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e743829000000006044820152606401610115565b50016001015190565b6000610316826004610747565b835110156103665760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743332290000006044820152606401610115565b50016004015190565b600061037c826020610747565b835110156103cc5760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e743235362900006044820152606401610115565b50016020015190565b60006103e2826002610747565b835110156104325760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743136290000006044820152606401610115565b50016002015190565b60606104478284610747565b8451101561048c5760405162461bcd60e51b815260206004820152601260248201527152656164206f7574206f6620626f756e647360701b6044820152606401610115565b6060821580156104a7576040519150602082016040526104f1565b6040519150601f8416801560200281840101858101878315602002848b0101015b818310156104e05780518352602092830192016104c8565b5050858452601f01601f1916604052505b50949350505050565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610539576105396104fa565b604052919050565b600082601f83011261055257600080fd5b8135602067ffffffffffffffff82111561056e5761056e6104fa565b8160051b61057d828201610510565b928352848101820192828101908785111561059757600080fd5b83870192505b848310156105b65782358252918301919083019061059d565b979650505050505050565b600080604083850312156105d457600080fd5b823567ffffffffffffffff808211156105ec57600080fd5b818501915085601f83011261060057600080fd5b8135602082821115610614576106146104fa565b610626601f8301601f19168201610510565b828152888284870101111561063a57600080fd5b8282860183830137600092810182019290925290945085013591508082111561066257600080fd5b5061066f85828601610541565b9150509250929050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156106bb5785810183015185820160800152820161069f565b818111156106cd576000608083870101525b50601f01601f19169290920160800195945050505050565b634e487b7160e01b600052601160045260246000fd5b60008282101561070d5761070d6106e5565b500390565b634e487b7160e01b600052603260045260246000fd5b6000816000190483118215151615610742576107426106e5565b500290565b6000821982111561075a5761075a6106e5565b500190565b6000600019821415610773576107736106e5565b506001019056fea2646970667358221220d8f801544d59802aacc97c04285cc0f097abc6169d79d984ce2a49fa1d18295d64736f6c63430008090033";

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
