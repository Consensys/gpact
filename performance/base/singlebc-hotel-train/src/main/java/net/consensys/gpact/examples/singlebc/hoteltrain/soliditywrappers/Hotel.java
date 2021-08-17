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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Hotel extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161070e38038061070e83398101604081905261002f91610062565b60048054336001600160a01b031991821617909155600580549091166001600160a01b0392909216919091179055610092565b60006020828403121561007457600080fd5b81516001600160a01b038116811461008b57600080fd5b9392505050565b61066d806100a16000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80631358646e146100675780631b474cf71461007c5780633ca1a5b4146100af5780637ebf2c60146100cf578063d131aa7e146100e2578063f401099514610103575b600080fd5b61007a61007536600461054a565b610123565b005b61008f61008a36600461050f565b6102f3565b604080519384526020840192909252908201526060015b60405180910390f35b61007a6100bd366004610528565b60009182526020829052604090912055565b61007a6100dd366004610528565b610334565b6100f56100f036600461050f565b610395565b6040519081526020016100a6565b61011661011136600461050f565b6103f3565b6040516100a69190610576565b8261016c5760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b60448201526064015b60405180910390fd5b60005b6001548110156102b0576000818152602081905260409020548281118015906101b957506000828152602081815260408083208884526001019091529020546001600160a01b0316155b1561029d57600082815260208181526040808320888452600101825280832080546001600160a01b03191632908117909155878452600283528184208690556003909252918290208790556005546004805493516323b872dd60e01b8152908101929092526001600160a01b0392831660248301526044820184905291909116906323b872dd90606401602060405180830381600087803b15801561025d57600080fd5b505af1158015610271573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061029591906104e6565b505050505050565b50806102a8816105da565b91505061016f565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b6044820152606401610163565b505050565b60008181526002602090815260408083205460039092528220548061031b576000925061032d565b60008281526020819052604090205492505b9193909250565b6004546001600160a01b0316331461034b57600080fd5b60005b818110156102ee5760018054600091829182918261036b836105da565b9091555081526020810191909152604001600020849055508061038d816105da565b91505061034e565b6000805b6001548110156103ed576000818152602081815260408083208684526001019091529020546001600160a01b0316806103da57826103d6816105da565b9350505b50806103e5816105da565b915050610399565b50919050565b6060600061040083610395565b90508060015461041091906105c3565b67ffffffffffffffff81111561042857610428610621565b604051908082528060200260200182016040528015610451578160200160208202803683370190505b5091506000805b6001548110156104de576000818152602081815260408083208884526001019091529020546001600160a01b031680156104cb57808584610498816105da565b9550815181106104aa576104aa61060b565b60200260200101906001600160a01b031690816001600160a01b0316815250505b50806104d6816105da565b915050610458565b505050919050565b6000602082840312156104f857600080fd5b8151801515811461050857600080fd5b9392505050565b60006020828403121561052157600080fd5b5035919050565b6000806040838503121561053b57600080fd5b50508035926020909101359150565b60008060006060848603121561055f57600080fd5b505081359360208301359350604090920135919050565b6020808252825182820181905260009190848201906040850190845b818110156105b75783516001600160a01b031683529284019291840191600101610592565b50909695505050505050565b6000828210156105d5576105d56105f5565b500390565b60006000198214156105ee576105ee6105f5565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122034c3a9b4bc00f06fc648f63042f98a28b93877d527efaba8fec476e65531fa4364736f6c63430008050033";

    public static final String FUNC_ADDROOMS = "addRooms";

    public static final String FUNC_BOOKROOM = "bookRoom";

    public static final String FUNC_CHANGEROOMRATE = "changeRoomRate";

    public static final String FUNC_GETBOOKINGINFORMATION = "getBookingInformation";

    public static final String FUNC_GETBOOKINGS = "getBookings";

    public static final String FUNC_GETNUMBERROOMSAVAILABLE = "getNumberRoomsAvailable";

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
