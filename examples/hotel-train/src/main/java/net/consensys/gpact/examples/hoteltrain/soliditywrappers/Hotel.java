package net.consensys.gpact.examples.hoteltrain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516110c33803806110c383398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560098054821633179055600a80549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610ff8806100cb6000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c80637ebf2c60116100665780637ebf2c601461013f578063d131aa7e14610152578063f401099514610165578063f6aacfb114610185578063fcea5096146101b857600080fd5b80631358646e146100a3578063184ad3c6146100b85780631b474cf7146100cb5780633ca1a5b4146100fe5780637445b0d014610111575b600080fd5b6100b66100b1366004610eb2565b6101cb565b005b6100b66100c6366004610e4e565b610560565b6100de6100d9366004610dec565b6105be565b604080519384526020840192909252908201526060015b60405180910390f35b6100b661010c366004610e90565b610604565b61013161011f366004610dec565b60056020526000908152604090205481565b6040519081526020016100f5565b6100b661014d366004610e90565b61062b565b610131610160366004610dec565b610684565b610178610173366004610dec565b6106df565b6040516100f59190610ede565b6101a8610193366004610dec565b60036020526000908152604090205460ff1681565b60405190151581526020016100f5565b6100b66101c6366004610db7565b6107d8565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561021757600080fd5b505afa15801561022b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061024f9190610d93565b156102a15760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b60008054604080516311d5f13b60e31b8152815184936001600160a01b031692638eaf89d89260048082019391829003018186803b1580156102e257600080fd5b505afa1580156102f6573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061031a9190610e1e565b60008281526008602052604090205491935091506001600160a01b038083169116146103985760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b6064820152608401610298565b846103dc5760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b6044820152606401610298565b60005b60065481101561051b5760006103f66000836108bb565b905061040460018389610906565b1580156104115750848111155b8015610431575060006104266001848a61094d565b6001600160a01b0316145b15610508576104436001838932610993565b61044f600287846109ce565b61045b600387896109ce565b600a546001600160a01b03848116600090815260076020526040908190205460095491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b1580156104c657600080fd5b505af11580156104da573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104fe9190610d93565b5050505050505050565b508061051381610f42565b9150506103df565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b6044820152606401610298565b5050505050565b6009546001600160a01b0316331461057757600080fd5b600092835260086020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600790925290932080549093169116179055565b60008060006105ce6003856108bb565b9050806105e25760009250600091506105fd565b6105ed6002856108bb565b91506105fa6000836108bb565b92505b9193909250565b6009546001600160a01b0316331461061b57600080fd5b610627600083836109ce565b5050565b6009546001600160a01b0316331461064257600080fd5b60005b8181101561067f576006805461066d91600091908261066383610f42565b91905055856109ce565b8061067781610f42565b915050610645565b505050565b6000805b6006548110156106d95761069e60018285610906565b1580156106b457506106b26001828561094d565b155b156106c757816106c381610f42565b9250505b806106d181610f42565b915050610688565b50919050565b606060006106ec83610684565b9050806006546106fc9190610f2b565b67ffffffffffffffff81111561071457610714610f89565b60405190808252806020026020018201604052801561073d578160200160208202803683370190505b5091506000805b6006548110156107d05761075a60018287610906565b6107be57600061076c6001838861094d565b90506001600160a01b038116156107bc5780858461078981610f42565b95508151811061079b5761079b610f73565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b806107c881610f42565b915050610744565b505050919050565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b60008281526004602052604090205481101561089d57600082815260046020526040812080548390811061083957610839610f73565b906000526020600020015490508515610868576000818152600260209081526040808320546001909252909120555b600090815260026020908152604080832083905560039091529020805460ff191690558061089581610f42565b915050610803565b5060008181526004602052604081206108b591610d59565b50505050565b60008083836040516020016108da929190918252602082015260400190565b6040516020818303038152906040528051906020012090506108fe8160001c610a00565b949350505050565b604080516020808201959095528082019390935260608084019290925280518084039092018252608090920182528051908301206000908152600390925290205460ff1690565b6040805160208082018690528183018590526060808301859052835180840390910181526080909201909252805191012060009061098a81610a00565b95945050505050565b604080516020808201879052818301869052606080830186905283518084039091018152608090920190925280519101206105598183610a72565b604080516020808201869052818301859052825180830384018152606090920190925280519101206108b58183610a72565b60008181526003602052604081205460ff1615610a5f5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610298565b5060009081526001602052604090205490565b60008281526003602052604090205460ff1615610ac85760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610298565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b158015610b1457600080fd5b505afa158015610b28573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b4c9190610d93565b15610b635760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610bb257600080fd5b505afa158015610bc6573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610bea9190610e05565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610c3b57600080fd5b505afa158015610c4f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c739190610e05565b6040805160208082018690528183018490528251808303840181526060909201909252805191012090915060009060008681526005602052604080822083905590549051631ce7083f60e11b81523060048201529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b158015610cf757600080fd5b505af1158015610d0b573d6000803e3d6000fd5b50505060009182525060046020908152604080832080546001818101835591855283852001889055878452600283528184208790556003909252909120805460ff1916909117905550505050565b5080546000825590600052602060002090810190610d779190610d7a565b50565b5b80821115610d8f5760008155600101610d7b565b5090565b600060208284031215610da557600080fd5b8151610db081610fb4565b9392505050565b600080600060608486031215610dcc57600080fd5b8335610dd781610fb4565b95602085013595506040909401359392505050565b600060208284031215610dfe57600080fd5b5035919050565b600060208284031215610e1757600080fd5b5051919050565b60008060408385031215610e3157600080fd5b825191506020830151610e4381610f9f565b809150509250929050565b600080600060608486031215610e6357600080fd5b833592506020840135610e7581610f9f565b91506040840135610e8581610f9f565b809150509250925092565b60008060408385031215610ea357600080fd5b50508035926020909101359150565b600080600060608486031215610ec757600080fd5b505081359360208301359350604090920135919050565b6020808252825182820181905260009190848201906040850190845b81811015610f1f5783516001600160a01b031683529284019291840191600101610efa565b50909695505050505050565b600082821015610f3d57610f3d610f5d565b500390565b6000600019821415610f5657610f56610f5d565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fd5b6001600160a01b0381168114610d7757600080fd5b8015158114610d7757600080fdfea26469706673582212207e59139c608e039a38b8005957779640e5922a3c0c9a20bc93cc35a60fced63664736f6c63430008050033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

    public static final String FUNC_ADDROOMS = "addRooms";

    public static final String FUNC_BOOKROOM = "bookRoom";

    public static final String FUNC_CHANGEROOMRATE = "changeRoomRate";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBOOKINGINFORMATION = "getBookingInformation";

    public static final String FUNC_GETBOOKINGS = "getBookings";

    public static final String FUNC_GETNUMBERROOMSAVAILABLE = "getNumberRoomsAvailable";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

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

    public RemoteFunctionCall<TransactionReceipt> addApprovedTravelAgency(BigInteger _blockchainId, String _travelAgencyContract, String spendingAccount) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDTRAVELAGENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _travelAgencyContract), 
                new org.web3j.abi.datatypes.Address(160, spendingAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_addApprovedTravelAgency(BigInteger _blockchainId, String _travelAgencyContract, String spendingAccount) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDTRAVELAGENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _travelAgencyContract), 
                new org.web3j.abi.datatypes.Address(160, spendingAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addRooms(BigInteger _roomRate, BigInteger _numberOfRooms) {
        final Function function = new Function(
                FUNC_ADDROOMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_numberOfRooms)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_addRooms(BigInteger _roomRate, BigInteger _numberOfRooms) {
        final Function function = new Function(
                FUNC_ADDROOMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_numberOfRooms)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_bookRoom(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay) {
        final Function function = new Function(
                FUNC_BOOKROOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeRoomRate(BigInteger _roomNumber, BigInteger _roomRate) {
        final Function function = new Function(
                FUNC_CHANGEROOMRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_roomRate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_changeRoomRate(BigInteger _roomNumber, BigInteger _roomRate) {
        final Function function = new Function(
                FUNC_CHANGEROOMRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_roomNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_roomRate)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, BigInteger _rootBcId, BigInteger _crossTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Uint256(_rootBcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_crossTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_getBookingInformation(BigInteger _uniqueId) {
        final Function function = new Function(
                FUNC_GETBOOKINGINFORMATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_uniqueId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_getBookings(BigInteger _date) {
        final Function function = new Function(
                FUNC_GETBOOKINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getNumberRoomsAvailable(BigInteger _date) {
        final Function function = new Function(FUNC_GETNUMBERROOMSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getNumberRoomsAvailable(BigInteger _date) {
        final Function function = new Function(
                FUNC_GETNUMBERROOMSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger param0) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger param0) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_lockedByRootBlockchainIdTransactionId(BigInteger param0) {
        final Function function = new Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public static RemoteCall<Hotel> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Hotel.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Hotel> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Hotel.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Hotel> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Hotel.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Hotel> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Hotel.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
