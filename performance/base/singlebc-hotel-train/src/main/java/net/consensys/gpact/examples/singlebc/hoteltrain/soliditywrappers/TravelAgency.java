package net.consensys.gpact.examples.singlebc.hoteltrain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class TravelAgency extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161069638038061069683398101604081905261002f91610091565b6000805433600160a060020a031991821617909155600194909455600280548516600160a060020a039485161790556003919091556004805490931691161790556100d7565b8051600160a060020a038116811461008c57600080fd5b919050565b600080600080608085870312156100a757600080fd5b845193506100b760208601610075565b9250604085015191506100cc60608601610075565b905092959194509250565b6105b0806100e66000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c0100000000000000000000000000000000000000000000000000000000900480637652de46116100835780637652de46146101285780638146b5c01461013b5780639d0599ac14610150578063a9dcc12414610163578063d598f8741461017657600080fd5b8063036b0edf146100b5578063168fd46f146100dd57806341b1637c146100f45780634d1177da1461011f575b600080fd5b6100c86100c33660046104ce565b610189565b60405190151581526020015b60405180910390f35b6100e660035481565b6040519081526020016100d4565b60045461010790600160a060020a031681565b604051600160a060020a0390911681526020016100d4565b6100e660015481565b6100e66101363660046104ce565b6101e0565b61014e6101493660046104e7565b610201565b005b60025461010790600160a060020a031681565b61014e6101713660046104e7565b6103e7565b6100e66101843660046104ce565b6104be565b6000805b6006548110156101d75782600582815481106101ab576101ab610509565b906000526020600020015414156101c55750600192915050565b806101cf81610538565b91505061018d565b50600092915050565b600681815481106101f057600080fd5b600091825260209091200154905081565b600054600160a060020a0316331461027a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e677300000000000060448201526064015b60405180910390fd5b6002546040517f1358646e0000000000000000000000000000000000000000000000000000000081526004810184905260248101839052606460448201819052600160a060020a0390921691631358646e9101600060405180830381600087803b1580156102e757600080fd5b505af11580156102fb573d6000803e3d6000fd5b5050600480546040517f1358646e00000000000000000000000000000000000000000000000000000000815291820186905260248201859052606460448301819052600160a060020a039091169350631358646e925001600060405180830381600087803b15801561036c57600080fd5b505af1158015610380573d6000803e3d6000fd5b50506005805460018181019092557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0019390935550506006805491820181556000527ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f0155565b600054600160a060020a0316331461045b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e67730000000000006044820152606401610271565b6005805460018181019092557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db001919091556006805491820181556000527ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f0155565b600581815481106101f057600080fd5b6000602082840312156104e057600080fd5b5035919050565b600080604083850312156104fa57600080fd5b50508035926020909101359150565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6000600019821415610573577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b506001019056fea26469706673582212202b398f46af957adf90b24cb57e58607524df78f68aaa665c9298058bc07c3b0d64736f6c63430008090033";

    public static final String FUNC_BOOKHOTELANDTRAIN = "bookHotelAndTrain";

    public static final String FUNC_BOOKINGCONFIRMED = "bookingConfirmed";

    public static final String FUNC_CONFIRMEDBOOKINGDATES = "confirmedBookingDates";

    public static final String FUNC_CONFIRMEDBOOKINGIDS = "confirmedBookingIds";

    public static final String FUNC_HOTELBLOCKCHAINID = "hotelBlockchainId";

    public static final String FUNC_HOTELCONTRACT = "hotelContract";

    public static final String FUNC_SEPARATEDBOOKHOTELANDTRAIN = "separatedBookHotelAndTrain";

    public static final String FUNC_TRAINBLOCKCHAINID = "trainBlockchainId";

    public static final String FUNC_TRAINCONTRACT = "trainContract";

    @Deprecated
    protected TravelAgency(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TravelAgency(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TravelAgency(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TravelAgency(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> bookHotelAndTrain(BigInteger _date, BigInteger _uniqueId) {
        final Function function = new Function(
                FUNC_BOOKHOTELANDTRAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> bookingConfirmed(BigInteger _bookingId) {
        final Function function = new Function(FUNC_BOOKINGCONFIRMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> confirmedBookingDates(BigInteger param0) {
        final Function function = new Function(FUNC_CONFIRMEDBOOKINGDATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> confirmedBookingIds(BigInteger param0) {
        final Function function = new Function(FUNC_CONFIRMEDBOOKINGIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> hotelBlockchainId() {
        final Function function = new Function(FUNC_HOTELBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> hotelContract() {
        final Function function = new Function(FUNC_HOTELCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> separatedBookHotelAndTrain(BigInteger _date, BigInteger _uniqueId) {
        final Function function = new Function(
                FUNC_SEPARATEDBOOKHOTELANDTRAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> trainBlockchainId() {
        final Function function = new Function(FUNC_TRAINBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> trainContract() {
        final Function function = new Function(FUNC_TRAINCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static TravelAgency load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TravelAgency(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TravelAgency load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TravelAgency(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TravelAgency load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TravelAgency(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TravelAgency load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TravelAgency(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TravelAgency> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract)));
        return deployRemoteCall(TravelAgency.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TravelAgency> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract)));
        return deployRemoteCall(TravelAgency.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TravelAgency> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract)));
        return deployRemoteCall(TravelAgency.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TravelAgency> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract)));
        return deployRemoteCall(TravelAgency.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
