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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610f53380380610f5383398101604081905261002f91610089565b60008054600160a060020a03928316600160a060020a03199182161790915560078054821633179055600880549390921692169190911790556100bc565b8051600160a060020a038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610e88806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c0100000000000000000000000000000000000000000000000000000000900480637ebf2c60116100835780637ebf2c601461012357806399eb5d4c14610136578063d131aa7e14610149578063f40109951461016a578063f6aacfb11461018a57600080fd5b80631358646e146100b5578063184ad3c6146100ca5780631b474cf7146100dd5780633ca1a5b414610110575b600080fd5b6100c86100c3366004610bf8565b6101bc565b005b6100c86100d8366004610c40565b6104bd565b6100f06100eb366004610c7c565b610528565b604080519384526020840192909252908201526060015b60405180910390f35b6100c861011e366004610c95565b61056e565b6100c8610131366004610c95565b610595565b6100c8610144366004610cc5565b6105ee565b61015c610157366004610c7c565b61069e565b604051908152602001610107565b61017d610178366004610c7c565b6106f9565b6040516101079190610cf1565b6101ac610198366004610c7c565b600090815260026020526040902054151590565b6040519015158152602001610107565b600054600160a060020a0316331461021e5760405160e560020a62461bcd02815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b6000806102296107f2565b6000828152600660205260409020549194509250600160a060020a0380841691161490506102c25760405160e560020a62461bcd02815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c60448201527f206167656e6379000000000000000000000000000000000000000000000000006064820152608401610215565b846103125760405160e560020a62461bcd02815260206004820152601460248201527f446174652063616e206e6f74206265207a65726f0000000000000000000000006044820152606401610215565b60005b60045481101561046a57600061032c60008361082a565b905061033a60018389610874565b1580156103475750848111155b80156103675750600061035c6001848a6108c8565b600160a060020a0316145b15610457576103796001838932610905565b61038560028784610940565b61039160038789610940565b600854600160a060020a03848116600090815260056020526040908190205460075491517f23b872dd00000000000000000000000000000000000000000000000000000000815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b15801561041557600080fd5b505af1158015610429573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061044d9190610d3e565b5050505050505050565b508061046281610d91565b915050610315565b5060405160e560020a62461bcd02815260206004820152601260248201527f4e6f20726f6f6d7320617661696c61626c6500000000000000000000000000006044820152606401610215565b5050505050565b600754600160a060020a031633146104d457600080fd5b6000928352600660209081526040808520805473ffffffffffffffffffffffffffffffffffffffff19908116600160a060020a03968716908117909255908652600590925290932080549093169116179055565b600080600061053860038561082a565b90508061054c576000925060009150610567565b61055760028561082a565b915061056460008361082a565b92505b9193909250565b600754600160a060020a0316331461058557600080fd5b61059160008383610940565b5050565b600754600160a060020a031633146105ac57600080fd5b60005b818110156105e957600480546105d79160009190826105cd83610d91565b9190505585610940565b806105e181610d91565b9150506105af565b505050565b60005b60008281526003602052604090205481101561068657600082815260036020526040812080548390811061062757610627610dac565b9060005260206000200154905083156106655760008181526002602052604090205461065590600190610ddb565b6000828152600160205260409020555b6000908152600260205260408120558061067e81610d91565b9150506105f1565b50600081815260036020526040812061059191610bbe565b6000805b6004548110156106f3576106b860018285610874565b1580156106ce57506106cc600182856108c8565b155b156106e157816106dd81610d91565b9250505b806106eb81610d91565b9150506106a2565b50919050565b606060006107068361069e565b9050806004546107169190610ddb565b67ffffffffffffffff81111561072e5761072e610df2565b604051908082528060200260200182016040528015610757578160200160208202803683370190505b5091506000805b6004548110156107ea5761077460018287610874565b6107d8576000610786600183886108c8565b9050600160a060020a038116156107d6578085846107a381610d91565b9550815181106107b5576107b5610dac565b6020026020010190600160a060020a03169081600160a060020a0316815250505b505b806107e281610d91565b91505061075e565b505050919050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b6000808383604051602001610849929190918252602082015260400190565b60408051601f198184030181529190528051602090910120905061086c81610978565b949350505050565b604080516020810185905290810183905260608101829052600090819060800160408051808303601f1901815291815281516020928301206000818152600290935291205490915015155b95945050505050565b604080516020808201869052818301859052606080830185905283518084039091018152608090920190925280519101206000906108bf81610978565b604080516020808201879052818301869052606080830186905283518084039091018152608090920190925280519101206104b681836109ea565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061097281836109ea565b50505050565b600081815260026020526040812054156109d75760405160e560020a62461bcd02815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610215565b5060009081526001602052604090205490565b60008281526002602052604090205415610a495760405160e560020a62461bcd02815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b65640000000000000000000000006044820152606401610215565b60008060009054906101000a9004600160a060020a0316600160a060020a0316637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b158015610ab457600080fd5b505afa158015610ac8573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610aec9190610e21565b905080610b06575060009182526001602052604090912055565b6000546040517f39ce107e000000000000000000000000000000000000000000000000000000008152306004820152600160a060020a03909116906339ce107e90602401600060405180830381600087803b158015610b6457600080fd5b505af1158015610b78573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610baa915083610e3a565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610bdc9190610bdf565b50565b5b80821115610bf45760008155600101610be0565b5090565b600080600060608486031215610c0d57600080fd5b505081359360208301359350604090920135919050565b8035600160a060020a0381168114610c3b57600080fd5b919050565b600080600060608486031215610c5557600080fd5b83359250610c6560208501610c24565b9150610c7360408501610c24565b90509250925092565b600060208284031215610c8e57600080fd5b5035919050565b60008060408385031215610ca857600080fd5b50508035926020909101359150565b8015158114610bdc57600080fd5b60008060408385031215610cd857600080fd5b8235610ce381610cb7565b946020939093013593505050565b6020808252825182820181905260009190848201906040850190845b81811015610d32578351600160a060020a031683529284019291840191600101610d0d565b50909695505050505050565b600060208284031215610d5057600080fd5b8151610d5b81610cb7565b9392505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000600019821415610da557610da5610d62565b5060010190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600082821015610ded57610ded610d62565b500390565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600060208284031215610e3357600080fd5b5051919050565b60008219821115610e4d57610e4d610d62565b50019056fea2646970667358221220217d9e305aadee6c4fd77fbbf5755c140321758f5fe4463777191f80af945cd364736f6c63430008090033";

    public static final String FUNC_ADDAPPROVEDTRAVELAGENCY = "addApprovedTravelAgency";

    public static final String FUNC_ADDROOMS = "addRooms";

    public static final String FUNC_BOOKROOM = "bookRoom";

    public static final String FUNC_CHANGEROOMRATE = "changeRoomRate";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
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

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
