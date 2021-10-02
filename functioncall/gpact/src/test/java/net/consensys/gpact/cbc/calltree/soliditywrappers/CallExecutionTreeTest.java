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
    public static final String BINARY = "608060405234801561001057600080fd5b506107d2806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063ac23902614610030575b600080fd5b61004361003e36600461059c565b61005b565b60405161005293929190610654565b60405180910390f35b600080606061006c85856001610079565b9250925092509250925092565b60008060606000805b8651811015610198576000806100988a856102bf565b905060ff811661012357600189516100b09190610728565b831461011e5760405162461bcd60e51b815260206004820152603260248201527f52656163686564206c6561662066756e6374696f6e206275742074686572652060448201527134b99036b7b9329031b0b636103830ba341760711b60648201526084015b60405180910390fd5b610177565b600089848151811061013757610137610770565b6020026020010151905061016d8b60016004846101549190610709565b61015e90896106f1565b61016891906106f1565b610325565b63ffffffff169250505b61018182856106f1565b9350505080806101909061073f565b915050610082565b5085600187516101a89190610728565b815181106101b8576101b8610770565b60200260200101516000146102355760006101d388836102bf565b905060ff8116156102265760405162461bcd60e51b815260206004820152601760248201527f4578706563746564206c6561662066756e6374696f6e2e0000000000000000006044820152606401610115565b6102316001836106f1565b9150505b61023f878261038b565b935061024c6020826106f1565b905061025b8782016014015190565b925084156102a25761026e6014826106f1565b9050600061027c88836103f1565b90506102896002836106f1565b915061029a88838361ffff16610457565b9250506102b5565b6040518060200160405280600081525091505b5093509350939050565b60006102cc8260016106f1565b8351101561031c5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e743829000000006044820152606401610115565b50016001015190565b60006103328260046106f1565b835110156103825760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743332290000006044820152606401610115565b50016004015190565b60006103988260206106f1565b835110156103e85760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e743235362900006044820152606401610115565b50016020015190565b60006103fe8260026106f1565b8351101561044e5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743136290000006044820152606401610115565b50016002015190565b606061046382846106f1565b845110156104a85760405162461bcd60e51b815260206004820152601260248201527152656164206f7574206f6620626f756e647360701b6044820152606401610115565b6060821580156104c35760405191506020820160405261050d565b6040519150601f8416801560200281840101858101878315602002848b0101015b818310156104fc5780518352602092830192016104e4565b5050858452601f01601f1916604052505b50949350505050565b600082601f83011261052757600080fd5b8135602067ffffffffffffffff82111561054357610543610786565b8160051b6105528282016106c0565b83815282810190868401838801850189101561056d57600080fd5b600093505b85841015610590578035835260019390930192918401918401610572565b50979650505050505050565b600080604083850312156105af57600080fd5b823567ffffffffffffffff808211156105c757600080fd5b818501915085601f8301126105db57600080fd5b81356020828211156105ef576105ef610786565b610601601f8301601f191682016106c0565b828152888284870101111561061557600080fd5b8282860183830137600092810182019290925290945085013591508082111561063d57600080fd5b5061064a85828601610516565b9150509250929050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156106965785810183015185820160800152820161067a565b818111156106a8576000608083870101525b50601f01601f19169290920160800195945050505050565b604051601f8201601f1916810167ffffffffffffffff811182821017156106e9576106e9610786565b604052919050565b600082198211156107045761070461075a565b500190565b60008160001904831182151516156107235761072361075a565b500290565b60008282101561073a5761073a61075a565b500390565b60006000198214156107535761075361075a565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122058b9e7430c3b17d075cfcca33993c7547ec74cfc6cd6c9ba5df99c30a66f0a9864736f6c63430008050033";

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
