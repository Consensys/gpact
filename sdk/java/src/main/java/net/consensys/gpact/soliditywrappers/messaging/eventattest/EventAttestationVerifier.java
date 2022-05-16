package net.consensys.gpact.soliditywrappers.messaging.eventattest;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
public class EventAttestationVerifier extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161097d38038061097d83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6108ea806100936000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80634c1ce90214610030575b600080fd5b61004361003e366004610632565b610045565b005b606080606080600061008c87878080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525092506104b0915050565b905061009f605563ffffffff83166106cb565b6100aa9060046106ea565b86146100fd5760405162461bcd60e51b815260206004820152601a60248201527f5369676e617475726520696e636f7272656374206c656e67746800000000000060448201526064015b60405180910390fd5b8063ffffffff1667ffffffffffffffff81111561011c5761011c610702565b604051908082528060200260200182016040528015610145578160200160208202803683370190505b5094508063ffffffff1667ffffffffffffffff81111561016757610167610702565b604051908082528060200260200182016040528015610190578160200160208202803683370190505b5093508063ffffffff1667ffffffffffffffff8111156101b2576101b2610702565b6040519080825280602002602001820160405280156101db578160200160208202803683370190505b5092508063ffffffff1667ffffffffffffffff8111156101fd576101fd610702565b604051908082528060200260200182016040528015610226578160200160208202803683370190505b509150600460005b8263ffffffff168110156104145761027d89898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610516915050565b87828151811061028f5761028f610718565b6001600160a01b03909216602092830291909101909101526102b26014836106ea565b91506102f589898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061051e915050565b86828151811061030757610307610718565b60200260200101818152505060208261032091906106ea565b915061036389898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061051e915050565b85828151811061037557610375610718565b60200260200101818152505060208261038e91906106ea565b91506103d189898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610583915050565b8482815181106103e3576103e3610718565b60ff909216602092830291909101909101526104006001836106ea565b91508061040c8161072e565b91505061022e565b50505060008054906101000a90046001600160a01b03166001600160a01b031663ad107bb48b868686868e8e6040518863ffffffff1660e01b815260040161046297969594939291906107e0565b602060405180830381865afa15801561047f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104a3919061088b565b5050505050505050505050565b60006104bd8260046106ea565b8351101561050d5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e7433322900000060448201526064016100f4565b50016004015190565b016014015190565b60008060005b602081101561057b576105388160086106cb565b8561054383876106ea565b8151811061055357610553610718565b01602001516001600160f81b031916901c9190911790806105738161072e565b915050610524565b509392505050565b60006105908260016106ea565b835110156105e05760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e7438290000000060448201526064016100f4565b50016001015190565b60008083601f8401126105fb57600080fd5b50813567ffffffffffffffff81111561061357600080fd5b60208301915083602082850101111561062b57600080fd5b9250929050565b6000806000806000806080878903121561064b57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561067157600080fd5b61067d8a838b016105e9565b9096509450606089013591508082111561069657600080fd5b506106a389828a016105e9565b979a9699509497509295939492505050565b634e487b7160e01b600052601160045260246000fd5b60008160001904831182151516156106e5576106e56106b5565b500290565b600082198211156106fd576106fd6106b5565b500190565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b6000600019821415610742576107426106b5565b5060010190565b600081518084526020808501945080840160005b838110156107795781518752958201959082019060010161075d565b509495945050505050565b600081518084526020808501945080840160005b8381101561077957815160ff1687529582019590820190600101610798565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b600060c08201898352602060c081850152818a5180845260e086019150828c01935060005b8181101561082a5784516001600160a01b031683529383019391830191600101610805565b5050848103604086015261083e818b610749565b9250505082810360608401526108548188610749565b905082810360808401526108688187610784565b905082810360a084015261087d8185876107b7565b9a9950505050505050505050565b60006020828403121561089d57600080fd5b815180151581146108ad57600080fd5b939250505056fea2646970667358221220f84ad419742458bc7974f6976dea68ed9c94b8dd73c7eda915f2c88c26f13a9964736f6c634300080a0033";

    public static final String FUNC_DECODEANDVERIFYEVENT = "decodeAndVerifyEvent";

    @Deprecated
    protected EventAttestationVerifier(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EventAttestationVerifier(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EventAttestationVerifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EventAttestationVerifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static EventAttestationVerifier load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventAttestationVerifier(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EventAttestationVerifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventAttestationVerifier(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EventAttestationVerifier load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EventAttestationVerifier(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EventAttestationVerifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EventAttestationVerifier(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EventAttestationVerifier> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(EventAttestationVerifier.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<EventAttestationVerifier> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(EventAttestationVerifier.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EventAttestationVerifier> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(EventAttestationVerifier.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EventAttestationVerifier> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(EventAttestationVerifier.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
