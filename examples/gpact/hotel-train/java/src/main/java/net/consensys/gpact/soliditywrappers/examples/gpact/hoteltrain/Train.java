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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e4c380380610e4c83398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560078054821633179055600880549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610d81806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806399eb5d4c1161006657806399eb5d4c14610114578063eed6215d14610127578063f40109951461013a578063f6aacfb11461015a578063fc0ee3831461018c57600080fd5b8063184ad3c6146100985780631b474cf7146100ad5780632465d6fd146100e0578063834cad13146100f3575b600080fd5b6100ab6100a6366004610b58565b61019f565b005b6100c06100bb366004610b94565b6101fd565b604080519384526020840192909252908201526060015b60405180910390f35b6100ab6100ee366004610bad565b610243565b610106610101366004610b94565b6104f5565b6040519081526020016100d7565b6100ab610122366004610be7565b610550565b6100ab610135366004610c13565b610604565b61014d610148366004610b94565b61065d565b6040516100d79190610c35565b61017c610168366004610b94565b600090815260026020526040902054151590565b60405190151581526020016100d7565b6100ab61019a366004610c13565b610756565b6007546001600160a01b031633146101b657600080fd5b600092835260066020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600590925290932080549093169116179055565b600080600061020d600385610779565b90508061022157600092506000915061023c565b61022c600285610779565b9150610239600083610779565b92505b9193909250565b6000546001600160a01b031633146102a25760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b6000806102ad6107c4565b60008281526006602052604090205491945092506001600160a01b03808416911614905061032d5760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b6064820152608401610299565b846103715760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b6044820152606401610299565b60005b6004548110156104b057600061038b600083610779565b9050610399600183896107fc565b1580156103a65750848111155b80156103c6575060006103bb6001848a610850565b6001600160a01b0316145b1561049d576103d8600183893261088d565b6103e4600287846108c8565b6103f0600387896108c8565b6008546001600160a01b03848116600090815260056020526040908190205460075491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b15801561045b57600080fd5b505af115801561046f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104939190610c82565b5050505050505050565b50806104a881610cbc565b915050610374565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20736561747320617661696c61626c6560701b6044820152606401610299565b5050505050565b6000805b60045481101561054a5761050f600182856107fc565b158015610525575061052360018285610850565b155b15610538578161053481610cbc565b9250505b8061054281610cbc565b9150506104f9565b50919050565b60005b6000828152600360205260409020548110156105e857600082815260036020526040812080548390811061058957610589610cd7565b9060005260206000200154905083156105c7576000818152600260205260409020546105b790600190610ced565b6000828152600160205260409020555b600090815260026020526040812055806105e081610cbc565b915050610553565b50600081815260036020526040812061060091610b02565b5050565b6007546001600160a01b0316331461061b57600080fd5b60005b81811015610658576004805461064691600091908261063c83610cbc565b91905055856108c8565b8061065081610cbc565b91505061061e565b505050565b6060600061066a836104f5565b90508060045461067a9190610ced565b67ffffffffffffffff81111561069257610692610d04565b6040519080825280602002602001820160405280156106bb578160200160208202803683370190505b5091506000805b60045481101561074e576106d8600182876107fc565b61073c5760006106ea60018388610850565b90506001600160a01b0381161561073a5780858461070781610cbc565b95508151811061071957610719610cd7565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b8061074681610cbc565b9150506106c2565b505050919050565b6007546001600160a01b0316331461076d57600080fd5b610600600083836108c8565b6000808383604051602001610798929190918252602082015260400190565b6040516020818303038152906040528051906020012090506107bc8160001c610900565b949350505050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b604080516020810185905290810183905260608101829052600090819060800160408051601f1981840301815291815281516020928301206000818152600290935291205490915015155b95945050505050565b6040805160208082018690528183018590526060808301859052835180840390910181526080909201909252805191012060009061084781610900565b604080516020808201879052818301869052606080830186905283518084039091018152608090920190925280519101206104ee818361096f565b604080516020808201869052818301859052825180830384018152606090920190925280519101206108fa818361096f565b50505050565b6000818152600260205260408120541561095c5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610299565b5060009081526001602052604090205490565b600082815260026020526040902054156109c25760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610299565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b158015610a1157600080fd5b505afa158015610a25573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a499190610d1a565b905080610a63575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610aa857600080fd5b505af1158015610abc573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610aee915083610d33565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610b209190610b23565b50565b5b80821115610b385760008155600101610b24565b5090565b80356001600160a01b0381168114610b5357600080fd5b919050565b600080600060608486031215610b6d57600080fd5b83359250610b7d60208501610b3c565b9150610b8b60408501610b3c565b90509250925092565b600060208284031215610ba657600080fd5b5035919050565b600080600060608486031215610bc257600080fd5b505081359360208301359350604090920135919050565b8015158114610b2057600080fd5b60008060408385031215610bfa57600080fd5b8235610c0581610bd9565b946020939093013593505050565b60008060408385031215610c2657600080fd5b50508035926020909101359150565b6020808252825182820181905260009190848201906040850190845b81811015610c765783516001600160a01b031683529284019291840191600101610c51565b50909695505050505050565b600060208284031215610c9457600080fd5b8151610c9f81610bd9565b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610cd057610cd0610ca6565b5060010190565b634e487b7160e01b600052603260045260246000fd5b600082821015610cff57610cff610ca6565b500390565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610d2c57600080fd5b5051919050565b60008219821115610d4657610d46610ca6565b50019056fea2646970667358221220d0bc3aeac98dded22dfcef097ade004d3865ea35a7be2d41eba91bbba4f954f864736f6c63430008090033";

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
