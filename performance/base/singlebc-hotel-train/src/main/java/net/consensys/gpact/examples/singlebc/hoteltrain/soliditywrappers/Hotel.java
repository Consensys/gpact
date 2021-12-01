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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class Hotel extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b68380380610b6883398101604081905261002f91610062565b6005805433600160a060020a03199182161790915560068054909116600160a060020a0392909216919091179055610092565b60006020828403121561007457600080fd5b8151600160a060020a038116811461008b57600080fd5b9392505050565b610ac7806100a16000396000f3fe608060405234801561001057600080fd5b50600436106100a5576000357c0100000000000000000000000000000000000000000000000000000000900480637ebf2c60116100785780637ebf2c601461011857806387441ba61461012b578063d131aa7e1461013e578063f40109951461015f57600080fd5b80631076575f146100aa5780631358646e146100bf5780631b474cf7146100d25780633ca1a5b414610105575b600080fd5b6100bd6100b8366004610883565b61017f565b005b6100bd6100cd3660046108b6565b6101d1565b6100e56100e03660046108e2565b610479565b604080519384526020840192909252908201526060015b60405180910390f35b6100bd6101133660046108fb565b6104ba565b6100bd6101263660046108fb565b6104e3565b6100bd61013936600461091d565b610544565b61015161014c3660046108e2565b610716565b6040519081526020016100fc565b61017261016d3660046108e2565b610774565b6040516100fc919061095c565b600554600160a060020a0316331461019657600080fd5b600160a060020a039182166000908152600460205260409020805473ffffffffffffffffffffffffffffffffffffffff191691909216179055565b33600090815260046020526040902054600160a060020a03166102645760405160e560020a62461bcd02815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c60448201527f206167656e63790000000000000000000000000000000000000000000000000060648201526084015b60405180910390fd5b826102b45760405160e560020a62461bcd02815260206004820152601460248201527f446174652063616e206e6f74206265207a65726f000000000000000000000000604482015260640161025b565b60005b600154811015610428576000818152602081905260409020548281118015906103015750600082815260208181526040808320888452600101909152902054600160a060020a0316155b15610415576000828152602081815260408083208884526001018252808320805473ffffffffffffffffffffffffffffffffffffffff1916321790558683526002825280832085905560038252808320889055600654338452600492839052928190205460055491517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a0391821693810193909352908116602483015260448201849052909116906323b872dd90606401602060405180830381600087803b1580156103d557600080fd5b505af11580156103e9573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061040d91906109a9565b505050505050565b508061042081610a01565b9150506102b7565b5060405160e560020a62461bcd02815260206004820152601260248201527f4e6f20726f6f6d7320617661696c61626c650000000000000000000000000000604482015260640161025b565b505050565b6000818152600260209081526040808320546003909252822054806104a157600092506104b3565b60008281526020819052604090205492505b9193909250565b600554600160a060020a031633146104d157600080fd5b60009182526020829052604090912055565b600554600160a060020a031633146104fa57600080fd5b60005b818110156104745760018054600091829182918261051a83610a01565b9091555081526020810191909152604001600020849055508061053c81610a01565b9150506104fd565b600554600160a060020a0316331461055b57600080fd5b836105ab5760405160e560020a62461bcd02815260206004820152601460248201527f446174652063616e206e6f74206265207a65726f000000000000000000000000604482015260640161025b565b60005b600154811015610428576000818152602081905260409020548381118015906105f85750600082815260208181526040808320898452600101909152902054600160a060020a0316155b156106fd576000828152602081815260408083208984526001018252808320805473ffffffffffffffffffffffffffffffffffffffff1916321790558783526002825280832085905560039091529081902087905560065460055491517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a0386811660048301529283166024820152604481018490529116906323b872dd90606401602060405180830381600087803b1580156106bd57600080fd5b505af11580156106d1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106f591906109a9565b505050610710565b508061070881610a01565b9150506105ae565b50505050565b6000805b60015481101561076e57600081815260208181526040808320868452600101909152902054600160a060020a03168061075b578261075781610a01565b9350505b508061076681610a01565b91505061071a565b50919050565b6060600061078183610716565b9050806001546107919190610a1c565b67ffffffffffffffff8111156107a9576107a9610a33565b6040519080825280602002602001820160405280156107d2578160200160208202803683370190505b5091506000805b60015481101561085f57600081815260208181526040808320888452600101909152902054600160a060020a0316801561084c5780858461081981610a01565b95508151811061082b5761082b610a62565b6020026020010190600160a060020a03169081600160a060020a0316815250505b508061085781610a01565b9150506107d9565b505050919050565b8035600160a060020a038116811461087e57600080fd5b919050565b6000806040838503121561089657600080fd5b61089f83610867565b91506108ad60208401610867565b90509250929050565b6000806000606084860312156108cb57600080fd5b505081359360208301359350604090920135919050565b6000602082840312156108f457600080fd5b5035919050565b6000806040838503121561090e57600080fd5b50508035926020909101359150565b6000806000806080858703121561093357600080fd5b84359350602085013592506040850135915061095160608601610867565b905092959194509250565b6020808252825182820181905260009190848201906040850190845b8181101561099d578351600160a060020a031683529284019291840191600101610978565b50909695505050505050565b6000602082840312156109bb57600080fd5b815180151581146109cb57600080fd5b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000600019821415610a1557610a156109d2565b5060010190565b600082821015610a2e57610a2e6109d2565b500390565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fdfea26469706673582212204614e4b96aa419a0e37ab42b752e25bc9309b0a2f57a2e41e69ab5480db2f65164736f6c63430008090033";

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
