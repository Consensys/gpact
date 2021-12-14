package net.consensys.gpact.examples.singlebc.hoteltrain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Hotel extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610a4a380380610a4a83398101604081905261002f91610062565b60058054336001600160a01b031991821617909155600680549091166001600160a01b0392909216919091179055610092565b60006020828403121561007457600080fd5b81516001600160a01b038116811461008b57600080fd5b9392505050565b6109a9806100a16000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80637ebf2c601161005b5780637ebf2c60146100fb57806387441ba61461010e578063d131aa7e14610121578063f40109951461014257600080fd5b80631076575f1461008d5780631358646e146100a25780631b474cf7146100b55780633ca1a5b4146100e8575b600080fd5b6100a061009b3660046107b0565b610162565b005b6100a06100b03660046107e3565b6101a7565b6100c86100c336600461080f565b6103e7565b604080519384526020840192909252908201526060015b60405180910390f35b6100a06100f6366004610828565b610428565b6100a0610109366004610828565b610451565b6100a061011c36600461084a565b6104b2565b61013461012f36600461080f565b610643565b6040519081526020016100df565b61015561015036600461080f565b6106a1565b6040516100df9190610889565b6005546001600160a01b0316331461017957600080fd5b6001600160a01b03918216600090815260046020526040902080546001600160a01b03191691909216179055565b336000908152600460205260409020546001600160a01b03166102215760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b60648201526084015b60405180910390fd5b826102655760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b6044820152606401610218565b60005b6001548110156103a4576000818152602081905260409020548281118015906102b257506000828152602081815260408083208884526001019091529020546001600160a01b0316155b1561039157600082815260208181526040808320888452600101825280832080546001600160a01b031916321790558683526002825280832085905560038252808320889055600654338452600492839052928190205460055491516323b872dd60e01b81526001600160a01b0391821693810193909352908116602483015260448201849052909116906323b872dd906064016020604051808303816000875af1158015610365573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061038991906108d6565b505050505050565b508061039c81610915565b915050610268565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b6044820152606401610218565b505050565b60008181526002602090815260408083205460039092528220548061040f5760009250610421565b60008281526020819052604090205492505b9193909250565b6005546001600160a01b0316331461043f57600080fd5b60009182526020829052604090912055565b6005546001600160a01b0316331461046857600080fd5b60005b818110156103e25760018054600091829182918261048883610915565b909155508152602081019190915260400160002084905550806104aa81610915565b91505061046b565b6005546001600160a01b031633146104c957600080fd5b8361050d5760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b6044820152606401610218565b60005b6001548110156103a45760008181526020819052604090205483811180159061055a57506000828152602081815260408083208984526001019091529020546001600160a01b0316155b1561062a57600082815260208181526040808320898452600101825280832080546001600160a01b031916321790558783526002825280832085905560039091529081902087905560065460055491516323b872dd60e01b81526001600160a01b0386811660048301529283166024820152604481018490529116906323b872dd906064016020604051808303816000875af11580156105fe573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061062291906108d6565b50505061063d565b508061063581610915565b915050610510565b50505050565b6000805b60015481101561069b576000818152602081815260408083208684526001019091529020546001600160a01b031680610688578261068481610915565b9350505b508061069381610915565b915050610647565b50919050565b606060006106ae83610643565b9050806001546106be9190610930565b67ffffffffffffffff8111156106d6576106d6610947565b6040519080825280602002602001820160405280156106ff578160200160208202803683370190505b5091506000805b60015481101561078c576000818152602081815260408083208884526001019091529020546001600160a01b031680156107795780858461074681610915565b9550815181106107585761075861095d565b60200260200101906001600160a01b031690816001600160a01b0316815250505b508061078481610915565b915050610706565b505050919050565b80356001600160a01b03811681146107ab57600080fd5b919050565b600080604083850312156107c357600080fd5b6107cc83610794565b91506107da60208401610794565b90509250929050565b6000806000606084860312156107f857600080fd5b505081359360208301359350604090920135919050565b60006020828403121561082157600080fd5b5035919050565b6000806040838503121561083b57600080fd5b50508035926020909101359150565b6000806000806080858703121561086057600080fd5b84359350602085013592506040850135915061087e60608601610794565b905092959194509250565b6020808252825182820181905260009190848201906040850190845b818110156108ca5783516001600160a01b0316835292840192918401916001016108a5565b50909695505050505050565b6000602082840312156108e857600080fd5b815180151581146108f857600080fd5b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610929576109296108ff565b5060010190565b600082821015610942576109426108ff565b500390565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea2646970667358221220fd085b5ace83fff6b21678379b12b795327b68faff1d04c3e49bfef97124d20e64736f6c634300080a0033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

    public static final String FUNC_ADDROOMS = "addRooms";

    public static final String FUNC_BOOKROOM = "bookRoom";

    public static final String FUNC_CHANGEROOMRATE = "changeRoomRate";

    public static final String FUNC_GETBOOKINGINFORMATION = "getBookingInformation";

    public static final String FUNC_GETBOOKINGS = "getBookings";

    public static final String FUNC_GETNUMBERROOMSAVAILABLE = "getNumberRoomsAvailable";

    public static final String FUNC_SEPARATEDBOOKROOM = "separatedBookRoom";

    @Deprecated
    protected Hotel(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Hotel(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Hotel(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Hotel(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addApprovedTravelAgency(String _travelAgencyContract, String spendingAccount) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDTRAVELAGENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _travelAgencyContract), 
                new org.web3j.abi.datatypes.Address(160, spendingAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addRooms(BigInteger _roomRate, BigInteger _numberOfRooms) {
        final Function function = new Function(
                FUNC_ADDROOMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_numberOfRooms)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> bookRoom(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay) {
        final Function function = new Function(
                FUNC_BOOKROOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeRoomRate(BigInteger _roomNumber, BigInteger _roomRate) {
        final Function function = new Function(
                FUNC_CHANGEROOMRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_roomRate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getBookingInformation(BigInteger _uniqueId) {
        final Function function = new Function(FUNC_GETBOOKINGINFORMATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_uniqueId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getBookings(BigInteger _date) {
        final Function function = new Function(FUNC_GETBOOKINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getNumberRoomsAvailable(BigInteger _date) {
        final Function function = new Function(FUNC_GETNUMBERROOMSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> separatedBookRoom(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay, String _travelAgencySpender) {
        final Function function = new Function(
                FUNC_SEPARATEDBOOKROOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay), 
                new org.web3j.abi.datatypes.Address(160, _travelAgencySpender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Hotel load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hotel(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Hotel load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hotel(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Hotel load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Hotel(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Hotel load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Hotel(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Hotel> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _erc20) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20)));
        return deployRemoteCall(Hotel.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Hotel> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _erc20) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20)));
        return deployRemoteCall(Hotel.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Hotel> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _erc20) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20)));
        return deployRemoteCall(Hotel.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Hotel> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _erc20) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20)));
        return deployRemoteCall(Hotel.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
