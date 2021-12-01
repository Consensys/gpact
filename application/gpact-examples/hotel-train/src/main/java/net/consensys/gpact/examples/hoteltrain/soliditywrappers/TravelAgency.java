package net.consensys.gpact.examples.hoteltrain.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class TravelAgency extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610afb380380610afb83398101604081905261002f916100a2565b60008054600160a060020a0319908116600160a060020a03938416179091556004805433908316179055600595909555600680548616948216949094179093556007919091556008805490931691161790556100f9565b8051600160a060020a038116811461009d57600080fd5b919050565b600080600080600060a086880312156100ba57600080fd5b855194506100ca60208701610086565b9350604086015192506100df60608701610086565b91506100ed60808701610086565b90509295509295909350565b6109f3806101086000396000f3fe608060405234801561001057600080fd5b50600436106100a5576000357c0100000000000000000000000000000000000000000000000000000000900480638146b5c0116100785780638146b5c01461011d57806399eb5d4c146101325780639d0599ac14610145578063f6aacfb11461015857600080fd5b8063036b0edf146100aa578063168fd46f146100d257806341b1637c146100e95780634d1177da14610114575b600080fd5b6100bd6100b8366004610823565b61017a565b60405190151581526020015b60405180910390f35b6100db60075481565b6040519081526020016100c9565b6008546100fc90600160a060020a031681565b604051600160a060020a0390911681526020016100c9565b6100db60055481565b61013061012b36600461083c565b610191565b005b61013061014036600461085e565b610471565b6006546100fc90600160a060020a031681565b6100bd610166366004610823565b600090815260026020526040902054151590565b600080610188600084610521565b15159392505050565b600054600160a060020a031633146102195760405160e560020a62461bcd02815260206004820152603460248201527f53686f756c64206f6e6c792062652063616c6c65642062792043726f7373636860448201527f61696e20436f6e74726f6c20436f6e747261637400000000000000000000000060648201526084015b60405180910390fd5b600454600160a060020a031632146102765760405160e560020a62461bcd02815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e67730000000000006044820152606401610210565b600054600554600654604080516024810187905260448101869052606480820181905282518083039091018152608490910182526020810180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f1358646e0000000000000000000000000000000000000000000000000000000017905290517f92b2c335000000000000000000000000000000000000000000000000000000008152600160a060020a03948516946392b2c3359461033a94909391169160040161088f565b600060405180830381600087803b15801561035457600080fd5b505af1158015610368573d6000803e3d6000fd5b5050600054600754600854604080516024810189905260448101889052606480820181905282518083039091018152608490910182526020810180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f1358646e0000000000000000000000000000000000000000000000000000000017905290517f92b2c335000000000000000000000000000000000000000000000000000000008152600160a060020a0394851696506392b2c335955061042f949092169160040161088f565b600060405180830381600087803b15801561044957600080fd5b505af115801561045d573d6000803e3d6000fd5b5050505061046d6000828461056b565b5050565b60005b6000828152600360205260409020548110156105095760008281526003602052604081208054839081106104aa576104aa6108fc565b9060005260206000200154905083156104e8576000818152600260205260409020546104d89060019061095a565b6000828152600160205260409020555b6000908152600260205260408120558061050181610971565b915050610474565b50600081815260036020526040812061046d916107e9565b6000808383604051602001610540929190918252602082015260400190565b60408051601f1981840301815291905280516020909101209050610563816105a3565b949350505050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061059d8183610615565b50505050565b600081815260026020526040812054156106025760405160e560020a62461bcd02815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610210565b5060009081526001602052604090205490565b600082815260026020526040902054156106745760405160e560020a62461bcd02815260206004820152601460248201527f436f6e7472616374206974656d206c6f636b65640000000000000000000000006044820152606401610210565b60008060009054906101000a9004600160a060020a0316600160a060020a0316637bf37a096040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040160206040518083038186803b1580156106df57600080fd5b505afa1580156106f3573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610717919061098c565b905080610731575060009182526001602052604090912055565b6000546040517f39ce107e000000000000000000000000000000000000000000000000000000008152306004820152600160a060020a03909116906339ce107e90602401600060405180830381600087803b15801561078f57600080fd5b505af11580156107a3573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556107d59150836109a5565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610807919061080a565b50565b5b8082111561081f576000815560010161080b565b5090565b60006020828403121561083557600080fd5b5035919050565b6000806040838503121561084f57600080fd5b50508035926020909101359150565b6000806040838503121561087157600080fd5b8235801515811461088157600080fd5b946020939093013593505050565b83815260006020600160a060020a0385168184015260606040840152835180606085015260005b818110156108d2578581018301518582016080015282016108b6565b818111156108e4576000608083870101525b50601f01601f19169290920160800195945050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008282101561096c5761096c61092b565b500390565b60006000198214156109855761098561092b565b5060010190565b60006020828403121561099e57600080fd5b5051919050565b600082198211156109b8576109b861092b565b50019056fea2646970667358221220a620873b81ee7c6e626dc0820938525bf5109b04ad6ac0fa9970d87a1ea7c15864736f6c63430008090033";

    public static final String FUNC_BOOKHOTELANDTRAIN = "bookHotelAndTrain";

    public static final String FUNC_BOOKINGCONFIRMED = "bookingConfirmed";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_HOTELBLOCKCHAINID = "hotelBlockchainId";

    public static final String FUNC_HOTELCONTRACT = "hotelContract";

    public static final String FUNC_ISLOCKED = "isLocked";

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

    public RemoteFunctionCall<TransactionReceipt> bookHotelAndTrain(BigInteger _date, BigInteger _bookingId) {
        final Function function = new Function(
                FUNC_BOOKHOTELANDTRAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> bookingConfirmed(BigInteger _bookingId) {
        final Function function = new Function(FUNC_BOOKINGCONFIRMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public static RemoteCall<TravelAgency> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TravelAgency.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TravelAgency> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TravelAgency.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TravelAgency> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TravelAgency.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TravelAgency> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _hotelBlockchainId, String _hotelContract, BigInteger _trainBlockchainId, String _trainContract, String _cbc) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_hotelBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _hotelContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_trainBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _trainContract), 
                new org.web3j.abi.datatypes.Address(160, _cbc)));
        return deployRemoteCall(TravelAgency.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
