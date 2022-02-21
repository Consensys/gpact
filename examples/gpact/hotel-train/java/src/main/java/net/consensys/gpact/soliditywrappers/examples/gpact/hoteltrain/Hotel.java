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
public class Hotel extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610e7f380380610e7f83398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560078054821633179055600880549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610db4806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806399eb5d4c1161005b57806399eb5d4c146100fb578063d131aa7e1461010e578063f40109951461012f578063f6aacfb11461014f57600080fd5b80631358646e1461008d578063184ad3c6146100a25780631b474cf7146100b55780637ebf2c60146100e8575b600080fd5b6100a061009b366004610b6f565b610181565b005b6100a06100b0366004610bb7565b6104a0565b6100c86100c3366004610bf3565b6104fe565b604080519384526020840192909252908201526060015b60405180910390f35b6100a06100f6366004610c0c565b610544565b6100a0610109366004610c3c565b61059d565b61012161011c366004610bf3565b610651565b6040519081526020016100df565b61014261013d366004610bf3565b6106ac565b6040516100df9190610c68565b61017161015d366004610bf3565b600090815260026020526040902054151590565b60405190151581526020016100df565b6000546001600160a01b031633146101e05760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b60006101ed6002846107a5565b905080156102535760405162461bcd60e51b815260206004820152602d60248201527f486f74656c3a204578697374696e6720626f6f6b696e6720666f7220626f6f6b60448201526c696e67207265666572656e636560981b60648201526084016101d7565b60008061025e6107f0565b60008281526006602052604090205491945092506001600160a01b0380841691161490506102de5760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b60648201526084016101d7565b856103225760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b60448201526064016101d7565b60005b60045481101561046257600061033c6000836107a5565b905061034a6001838a610828565b1580156103575750858111155b80156103775750600061036c6001848b61087c565b6001600160a01b0316145b1561044f576103896001838a326108b9565b610395600288846108fb565b6103a16003888a6108fb565b6008546001600160a01b03848116600090815260056020526040908190205460075491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b15801561040c57600080fd5b505af1158015610420573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104449190610cb5565b505050505050505050565b508061045a81610cef565b915050610325565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b60448201526064016101d7565b6007546001600160a01b031633146104b757600080fd5b600092835260066020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600590925290932080549093169116179055565b600080600061050e6003856107a5565b90508061052257600092506000915061053d565b61052d6002856107a5565b915061053a6000836107a5565b92505b9193909250565b6007546001600160a01b0316331461055b57600080fd5b60005b81811015610598576004805461058691600091908261057c83610cef565b91905055856108fb565b8061059081610cef565b91505061055e565b505050565b60005b6000828152600360205260409020548110156106355760008281526003602052604081208054839081106105d6576105d6610d0a565b9060005260206000200154905083156106145760008181526002602052604090205461060490600190610d20565b6000828152600160205260409020555b6000908152600260205260408120558061062d81610cef565b9150506105a0565b50600081815260036020526040812061064d91610b35565b5050565b6000805b6004548110156106a65761066b60018285610828565b158015610681575061067f6001828561087c565b155b15610694578161069081610cef565b9250505b8061069e81610cef565b915050610655565b50919050565b606060006106b983610651565b9050806004546106c99190610d20565b67ffffffffffffffff8111156106e1576106e1610d37565b60405190808252806020026020018201604052801561070a578160200160208202803683370190505b5091506000805b60045481101561079d5761072760018287610828565b61078b5760006107396001838861087c565b90506001600160a01b038116156107895780858461075681610cef565b95508151811061076857610768610d0a565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b8061079581610cef565b915050610711565b505050919050565b60008083836040516020016107c4929190918252602082015260400190565b6040516020818303038152906040528051906020012090506107e88160001c610933565b949350505050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b604080516020810185905290810183905260608101829052600090819060800160408051601f1981840301815291815281516020928301206000818152600290935291205490915015155b95945050505050565b6040805160208082018690528183018590526060808301859052835180840390910181526080909201909252805191012060009061087381610933565b604080516020808201879052818301869052606080830186905283518084039091018152608090920190925280519101206108f481836109a2565b5050505050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061092d81836109a2565b50505050565b6000818152600260205260408120541561098f5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101d7565b5060009081526001602052604090205490565b600082815260026020526040902054156109f55760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016101d7565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b158015610a4457600080fd5b505afa158015610a58573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a7c9190610d4d565b905080610a96575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610adb57600080fd5b505af1158015610aef573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610b21915083610d66565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610b539190610b56565b50565b5b80821115610b6b5760008155600101610b57565b5090565b600080600060608486031215610b8457600080fd5b505081359360208301359350604090920135919050565b80356001600160a01b0381168114610bb257600080fd5b919050565b600080600060608486031215610bcc57600080fd5b83359250610bdc60208501610b9b565b9150610bea60408501610b9b565b90509250925092565b600060208284031215610c0557600080fd5b5035919050565b60008060408385031215610c1f57600080fd5b50508035926020909101359150565b8015158114610b5357600080fd5b60008060408385031215610c4f57600080fd5b8235610c5a81610c2e565b946020939093013593505050565b6020808252825182820181905260009190848201906040850190845b81811015610ca95783516001600160a01b031683529284019291840191600101610c84565b50909695505050505050565b600060208284031215610cc757600080fd5b8151610cd281610c2e565b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610d0357610d03610cd9565b5060010190565b634e487b7160e01b600052603260045260246000fd5b600082821015610d3257610d32610cd9565b500390565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610d5f57600080fd5b5051919050565b60008219821115610d7957610d79610cd9565b50019056fea2646970667358221220a34ff56c6236a27b6428dbdd7d7499ad2c4fcf3f7331ff115d1c67f19248f3c664736f6c63430008090033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

    public static final String FUNC_ADDROOMS = "addRooms";

    public static final String FUNC_BOOKROOM = "bookRoom";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBOOKINGINFORMATION = "getBookingInformation";

    public static final String FUNC_GETBOOKINGS = "getBookings";

    public static final String FUNC_GETNUMBERROOMSAVAILABLE = "getNumberRoomsAvailable";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public String getABI_addApprovedTravelAgency(BigInteger _blockchainId, String _travelAgencyContract, String spendingAccount) {
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

    public String getABI_addRooms(BigInteger _roomRate, BigInteger _numberOfRooms) {
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

    public String getABI_bookRoom(BigInteger _date, BigInteger _uniqueId, BigInteger _maxAmountToPay) {
        final Function function = new Function(
                FUNC_BOOKROOM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_uniqueId), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmountToPay)), 
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

    public RemoteFunctionCall<BigInteger> getNumberRoomsAvailable(BigInteger _date) {
        final Function function = new Function(FUNC_GETNUMBERROOMSAVAILABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getABI_getNumberRoomsAvailable(BigInteger _date) {
        final Function function = new Function(
                FUNC_GETNUMBERROOMSAVAILABLE, 
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
