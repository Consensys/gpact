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
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161086b38038061086b83398101604081905261002f91610062565b60058054336001600160a01b031991821617909155600680549091166001600160a01b0392909216919091179055610092565b60006020828403121561007457600080fd5b81516001600160a01b038116811461008b57600080fd5b9392505050565b6107ca806100a16000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80633ca1a5b41161005b5780633ca1a5b4146100dd5780637ebf2c60146100f0578063d131aa7e14610103578063f40109951461012457600080fd5b80631076575f146100825780631358646e146100975780631b474cf7146100aa575b600080fd5b610095610090366004610610565b610144565b005b6100956100a53660046106a7565b610189565b6100bd6100b836600461066c565b6103d8565b604080519384526020840192909252908201526060015b60405180910390f35b6100956100eb366004610685565b610419565b6100956100fe366004610685565b610442565b61011661011136600461066c565b6104a3565b6040519081526020016100d4565b61013761013236600461066c565b610501565b6040516100d491906106d3565b6005546001600160a01b0316331461015b57600080fd5b6001600160a01b03918216600090815260046020526040902080546001600160a01b03191691909216179055565b336000908152600460205260409020546001600160a01b03166102035760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b60648201526084015b60405180910390fd5b826102475760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b60448201526064016101fa565b60005b6001548110156103955760008181526020819052604090205482811180159061029457506000828152602081815260408083208884526001019091529020546001600160a01b0316155b1561038257600082815260208181526040808320888452600101825280832080546001600160a01b031916321790558683526002825280832085905560038252808320889055600654338452600492839052928190205460055491516323b872dd60e01b81526001600160a01b0391821693810193909352908116602483015260448201849052909116906323b872dd90606401602060405180830381600087803b15801561034257600080fd5b505af1158015610356573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061037a9190610643565b505050505050565b508061038d81610737565b91505061024a565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b60448201526064016101fa565b505050565b6000818152600260209081526040808320546003909252822054806104005760009250610412565b60008281526020819052604090205492505b9193909250565b6005546001600160a01b0316331461043057600080fd5b60009182526020829052604090912055565b6005546001600160a01b0316331461045957600080fd5b60005b818110156103d35760018054600091829182918261047983610737565b9091555081526020810191909152604001600020849055508061049b81610737565b91505061045c565b6000805b6001548110156104fb576000818152602081815260408083208684526001019091529020546001600160a01b0316806104e857826104e481610737565b9350505b50806104f381610737565b9150506104a7565b50919050565b6060600061050e836104a3565b90508060015461051e9190610720565b67ffffffffffffffff8111156105365761053661077e565b60405190808252806020026020018201604052801561055f578160200160208202803683370190505b5091506000805b6001548110156105ec576000818152602081815260408083208884526001019091529020546001600160a01b031680156105d9578085846105a681610737565b9550815181106105b8576105b8610768565b60200260200101906001600160a01b031690816001600160a01b0316815250505b50806105e481610737565b915050610566565b505050919050565b80356001600160a01b038116811461060b57600080fd5b919050565b6000806040838503121561062357600080fd5b61062c836105f4565b915061063a602084016105f4565b90509250929050565b60006020828403121561065557600080fd5b8151801515811461066557600080fd5b9392505050565b60006020828403121561067e57600080fd5b5035919050565b6000806040838503121561069857600080fd5b50508035926020909101359150565b6000806000606084860312156106bc57600080fd5b505081359360208301359350604090920135919050565b6020808252825182820181905260009190848201906040850190845b818110156107145783516001600160a01b0316835292840192918401916001016106ef565b50909695505050505050565b60008282101561073257610732610752565b500390565b600060001982141561074b5761074b610752565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122059ca5199b57eb67b5c968b3bf22ecd72ac87b721fd160cf63b5de38c3cf9881b64736f6c63430008050033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

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
