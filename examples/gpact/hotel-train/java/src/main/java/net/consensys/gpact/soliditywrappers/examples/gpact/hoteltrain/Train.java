package net.consensys.gpact.soliditywrappers.examples.gpact.hoteltrain;

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
public class Train extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610ec0380380610ec083398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560078054821633179055600880549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610df5806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806399eb5d4c1161006657806399eb5d4c14610114578063eed6215d14610127578063f40109951461013a578063f6aacfb11461015a578063fc0ee3831461018c57600080fd5b8063184ad3c6146100985780631b474cf7146100ad5780632465d6fd146100e0578063834cad13146100f3575b600080fd5b6100ab6100a6366004610bcc565b61019f565b005b6100c06100bb366004610c08565b6101fd565b604080519384526020840192909252908201526060015b60405180910390f35b6100ab6100ee366004610c21565b610243565b610106610101366004610c08565b610562565b6040519081526020016100d7565b6100ab610122366004610c5b565b6105bd565b6100ab610135366004610c87565b610671565b61014d610148366004610c08565b6106ca565b6040516100d79190610ca9565b61017c610168366004610c08565b600090815260026020526040902054151590565b60405190151581526020016100d7565b6100ab61019a366004610c87565b6107c3565b6007546001600160a01b031633146101b657600080fd5b600092835260066020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600590925290932080549093169116179055565b600080600061020d6003856107e6565b90508061022157600092506000915061023c565b61022c6002856107e6565b91506102396000836107e6565b92505b9193909250565b6000546001600160a01b031633146102a25760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b60006102af6002846107e6565b905080156103155760405162461bcd60e51b815260206004820152602d60248201527f547261696e3a204578697374696e6720626f6f6b696e6720666f7220626f6f6b60448201526c696e67207265666572656e636560981b6064820152608401610299565b600080610320610831565b60008281526006602052604090205491945092506001600160a01b0380841691161490506103a05760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b6064820152608401610299565b856103e45760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b6044820152606401610299565b60005b6004548110156105245760006103fe6000836107e6565b905061040c6001838a610869565b1580156104195750858111155b80156104395750600061042e6001848b6108bd565b6001600160a01b0316145b156105115761044b6001838a326108fa565b6104576002888461093c565b6104636003888a61093c565b6008546001600160a01b03848116600090815260056020526040908190205460075491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b1580156104ce57600080fd5b505af11580156104e2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105069190610cf6565b505050505050505050565b508061051c81610d30565b9150506103e7565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20736561747320617661696c61626c6560701b6044820152606401610299565b6000805b6004548110156105b75761057c60018285610869565b1580156105925750610590600182856108bd565b155b156105a557816105a181610d30565b9250505b806105af81610d30565b915050610566565b50919050565b60005b6000828152600360205260409020548110156106555760008281526003602052604081208054839081106105f6576105f6610d4b565b9060005260206000200154905083156106345760008181526002602052604090205461062490600190610d61565b6000828152600160205260409020555b6000908152600260205260408120558061064d81610d30565b9150506105c0565b50600081815260036020526040812061066d91610b76565b5050565b6007546001600160a01b0316331461068857600080fd5b60005b818110156106c557600480546106b39160009190826106a983610d30565b919050558561093c565b806106bd81610d30565b91505061068b565b505050565b606060006106d783610562565b9050806004546106e79190610d61565b67ffffffffffffffff8111156106ff576106ff610d78565b604051908082528060200260200182016040528015610728578160200160208202803683370190505b5091506000805b6004548110156107bb5761074560018287610869565b6107a9576000610757600183886108bd565b90506001600160a01b038116156107a75780858461077481610d30565b95508151811061078657610786610d4b565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b806107b381610d30565b91505061072f565b505050919050565b6007546001600160a01b031633146107da57600080fd5b61066d6000838361093c565b6000808383604051602001610805929190918252602082015260400190565b6040516020818303038152906040528051906020012090506108298160001c610974565b949350505050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b604080516020810185905290810183905260608101829052600090819060800160408051601f1981840301815291815281516020928301206000818152600290935291205490915015155b95945050505050565b604080516020808201869052818301859052606080830185905283518084039091018152608090920190925280519101206000906108b481610974565b6040805160208082018790528183018690526060808301869052835180840390910181526080909201909252805191012061093581836109e3565b5050505050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061096e81836109e3565b50505050565b600081815260026020526040812054156109d05760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610299565b5060009081526001602052604090205490565b60008281526002602052604090205415610a365760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610299565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b158015610a8557600080fd5b505afa158015610a99573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610abd9190610d8e565b905080610ad7575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610b1c57600080fd5b505af1158015610b30573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610b62915083610da7565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610b949190610b97565b50565b5b80821115610bac5760008155600101610b98565b5090565b80356001600160a01b0381168114610bc757600080fd5b919050565b600080600060608486031215610be157600080fd5b83359250610bf160208501610bb0565b9150610bff60408501610bb0565b90509250925092565b600060208284031215610c1a57600080fd5b5035919050565b600080600060608486031215610c3657600080fd5b505081359360208301359350604090920135919050565b8015158114610b9457600080fd5b60008060408385031215610c6e57600080fd5b8235610c7981610c4d565b946020939093013593505050565b60008060408385031215610c9a57600080fd5b50508035926020909101359150565b6020808252825182820181905260009190848201906040850190845b81811015610cea5783516001600160a01b031683529284019291840191600101610cc5565b50909695505050505050565b600060208284031215610d0857600080fd5b8151610d1381610c4d565b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610d4457610d44610d1a565b5060010190565b634e487b7160e01b600052603260045260246000fd5b600082821015610d7357610d73610d1a565b500390565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610da057600080fd5b5051919050565b60008219821115610dba57610dba610d1a565b50019056fea26469706673582212201ee85a4e868a01286d7a0cad0f61ad3625a75b536c50befd9955baa42cde987664736f6c63430008090033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

    public static final String FUNC_ADDSEATS = "addSeats";

    public static final String FUNC_BOOKSEAT = "bookSeat";

    public static final String FUNC_CHANGESEATRATE = "changeSeatRate";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBOOKINGINFORMATION = "getBookingInformation";

    public static final String FUNC_GETBOOKINGS = "getBookings";

    public static final String FUNC_GETNUMBERSEATSAVAILABLE = "getNumberSeatsAvailable";

    public static final String FUNC_ISLOCKED = "isLocked";

    @Deprecated
    protected Train(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Train(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Train(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Train(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public String getABI_addApprovedTravelAgency(BigInteger _blockchainId, String _travelAgencyContract, String spendingAccount) {
        final Function function = new Function(
                FUNC_ADDAPPROVEDTRAVELAGENCY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _travelAgencyContract), 
                new org.web3j.abi.datatypes.Address(160, spendingAccount)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSeats(BigInteger _seatRate, BigInteger _numberOfSeats) {
        final Function function = new Function(
                FUNC_ADDSEATS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_seatRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_numberOfSeats)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_addSeats(BigInteger _seatRate, BigInteger _numberOfSeats) {
        final Function function = new Function(
                FUNC_ADDSEATS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_seatRate), 
                new org.web3j.abi.datatypes.generated.Uint256(_numberOfSeats)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> bookSeat(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay) {
        final Function function = new Function(
                FUNC_BOOKSEAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_bookSeat(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay) {
        final Function function = new Function(
                FUNC_BOOKSEAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeSeatRate(BigInteger _seatNumber, BigInteger _seatRate) {
        final Function function = new Function(
                FUNC_CHANGESEATRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_seatNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_seatRate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_changeSeatRate(BigInteger _seatNumber, BigInteger _seatRate) {
        final Function function = new Function(
                FUNC_CHANGESEATRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_seatNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_seatRate)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getABI_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
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

    public String getABI_getBookingInformation(BigInteger _uniqueId) {
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

    public String getABI_getBookings(BigInteger _date) {
        final Function function = new Function(
                FUNC_GETBOOKINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getNumberSeatsAvailable(BigInteger _date) {
        final Function function = new Function(FUNC_GETNUMBERSEATSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getNumberSeatsAvailable(BigInteger _date) {
        final Function function = new Function(
                FUNC_GETNUMBERSEATSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getABI_isLocked(BigInteger _key) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static Train load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Train(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Train load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Train(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Train load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Train(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Train load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Train(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Train> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Train.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Train> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Train.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Train> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Train.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Train> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _erc20, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(Train.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
