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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TravelAgency extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161049e38038061049e83398101604081905261002f91610088565b600093909355600180546001600160a01b039384166001600160a01b031991821617909155600291909155600380549290931691161790556100ce565b80516001600160a01b038116811461008357600080fd5b919050565b6000806000806080858703121561009e57600080fd5b845193506100ae6020860161006c565b9250604085015191506100c36060860161006c565b905092959194509250565b6103c1806100dd6000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80637652de461161005b5780637652de46146101005780638146b5c0146101135780639d0599ac14610128578063d598f8741461013b57600080fd5b8063036b0edf1461008d578063168fd46f146100b557806341b1637c146100cc5780634d1177da146100f7575b600080fd5b6100a061009b366004610311565b61014e565b60405190151581526020015b60405180910390f35b6100be60025481565b6040519081526020016100ac565b6003546100df906001600160a01b031681565b6040516001600160a01b0390911681526020016100ac565b6100be60005481565b6100be61010e366004610311565b6101a5565b61012661012136600461032a565b6101c6565b005b6001546100df906001600160a01b031681565b6100be610149366004610311565b610301565b6000805b60055481101561019c57826004828154811061017057610170610375565b9060005260206000200154141561018a5750600192915050565b806101948161034c565b915050610152565b50600092915050565b600581815481106101b557600080fd5b600091825260209091200154905081565b6001546040516309ac323760e11b815260048101849052602481018390526064604482018190526001600160a01b0390921691631358646e9101600060405180830381600087803b15801561021a57600080fd5b505af115801561022e573d6000803e3d6000fd5b50506003546040516309ac323760e11b815260048101869052602481018590526064604482018190526001600160a01b039092169350631358646e925001600060405180830381600087803b15801561028657600080fd5b505af115801561029a573d6000803e3d6000fd5b50506004805460018181019092557f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b019390935550506005805491820181556000527f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00155565b600481815481106101b557600080fd5b60006020828403121561032357600080fd5b5035919050565b6000806040838503121561033d57600080fd5b50508035926020909101359150565b600060001982141561036e57634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fdfea2646970667358221220c374af748f941f2f5543f111d8546d6bf8df9d67fb8002c7b8cadd25f48833f464736f6c63430008050033";

    public static final String FUNC_BOOKHOTELANDTRAIN = "bookHotelAndTrain";

    public static final String FUNC_BOOKINGCONFIRMED = "bookingConfirmed";

    public static final String FUNC_CONFIRMEDBOOKINGDATES = "confirmedBookingDates";

    public static final String FUNC_CONFIRMEDBOOKINGIDS = "confirmedBookingIds";

    public static final String FUNC_HOTELBLOCKCHAINID = "hotelBlockchainId";

    public static final String FUNC_HOTELCONTRACT = "hotelContract";

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
