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
import org.web3j.abi.datatypes.generated.Bytes32;
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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TravelAgency extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b5c380380610b5c83398101604081905261002f916100a2565b600080546001600160a01b03199081166001600160a01b03938416179091556005805433908316179055600695909555600780548616948216949094179093556008919091556009805490931691161790556100f9565b80516001600160a01b038116811461009d57600080fd5b919050565b600080600080600060a086880312156100ba57600080fd5b855194506100ca60208701610086565b9350604086015192506100df60608701610086565b91506100ed60808701610086565b90509295509295909350565b610a54806101086000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80637445b0d0116100665780637445b0d01461010b5780638146b5c01461012b5780639d0599ac14610140578063f6aacfb114610153578063fcea50961461017557600080fd5b8063036b0edf14610098578063168fd46f146100c057806341b1637c146100d75780634d1177da14610102575b600080fd5b6100ab6100a6366004610911565b610188565b60405190151581526020015b60405180910390f35b6100c960085481565b6040519081526020016100b7565b6009546100ea906001600160a01b031681565b6040516001600160a01b0390911681526020016100b7565b6100c960065481565b6100c9610119366004610911565b60046020526000908152604090205481565b61013e610139366004610943565b61019f565b005b6007546100ea906001600160a01b031681565b6100ab610161366004610911565b600090815260046020526040902054151590565b61013e6101833660046108dc565b6103e2565b6000806101966000846104be565b15159392505050565b6000546001600160a01b0316331461021b5760405162461bcd60e51b815260206004820152603460248201527f53686f756c64206f6e6c792062652063616c6c65642062792043726f73736368604482015273185a5b8810dbdb9d1c9bdb0810dbdb9d1c9858dd60621b60648201526084015b60405180910390fd5b6005546001600160a01b031632146102755760405162461bcd60e51b815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e67730000000000006044820152606401610212565b600054600654600754604080516024810187905260448101869052606480820181905282518083039091018152608490910182526020810180516001600160e01b03166309ac323760e11b17905290516392b2c33560e01b81526001600160a01b03948516946392b2c335946102f2949093911691600401610965565b600060405180830381600087803b15801561030c57600080fd5b505af1158015610320573d6000803e3d6000fd5b5050600054600854600954604080516024810189905260448101889052606480820181905282518083039091018152608490910182526020810180516001600160e01b03166309ac323760e11b17905290516392b2c33560e01b81526001600160a01b0394851696506392b2c33595506103a09490921691600401610965565b600060405180830381600087803b1580156103ba57600080fd5b505af11580156103ce573d6000803e3d6000fd5b505050506103de60008284610509565b5050565b6040805160208082018590528183018490528251808303840181526060909201909252805191012060005b6000828152600360205260409020548110156104a0576000828152600360205260408120805483908110610443576104436109fa565b906000526020600020015490508515610472576000818152600260209081526040808320546001909252909120555b6000908152600260209081526040808320839055600490915281205580610498816109d1565b91505061040d565b5060008181526003602052604081206104b89161087e565b50505050565b60008083836040516020016104dd929190918252602082015260400190565b6040516020818303038152906040528051906020012090506105018160001c61053b565b949350505050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206104b881836105aa565b600081815260046020526040812054156105975760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b6564006044820152606401610212565b5060009081526001602052604090205490565b600082815260046020526040902054156105fd5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b6044820152606401610212565b60008054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561064957600080fd5b505afa15801561065d573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061068191906108b8565b156106985760009182526001602052604090912055565b60008060009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b1580156106e757600080fd5b505afa1580156106fb573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061071f919061092a565b905060008060009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b15801561077057600080fd5b505afa158015610784573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107a8919061092a565b60408051602080820186905281830184905282518083038401815260609092019092528051910120909150600090600086815260046020819052604080832084905591549151631ce7083f60e11b815230918101919091529192506001600160a01b0316906339ce107e90602401600060405180830381600087803b15801561083057600080fd5b505af1158015610844573d6000803e3d6000fd5b5050506000918252506003602090815260408083208054600181018255908452828420018790558683526002909152902083905550505050565b508054600082559060005260206000209081019061089c919061089f565b50565b5b808211156108b457600081556001016108a0565b5090565b6000602082840312156108ca57600080fd5b81516108d581610a10565b9392505050565b6000806000606084860312156108f157600080fd5b83356108fc81610a10565b95602085013595506040909401359392505050565b60006020828403121561092357600080fd5b5035919050565b60006020828403121561093c57600080fd5b5051919050565b6000806040838503121561095657600080fd5b50508035926020909101359150565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156109a75785810183015185820160800152820161098b565b818111156109b9576000608083870101525b50601f01601f19169290920160800195945050505050565b60006000198214156109f357634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fd5b801515811461089c57600080fdfea26469706673582212204ddab5cc9ac1680c88b6cb8c4de9a44d2a234af5ab999354d9739cc2fa11bd4864736f6c63430008050033";

    public static final String FUNC_BOOKHOTELANDTRAIN = "bookHotelAndTrain";

    public static final String FUNC_BOOKINGCONFIRMED = "bookingConfirmed";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_HOTELBLOCKCHAINID = "hotelBlockchainId";

    public static final String FUNC_HOTELCONTRACT = "hotelContract";

    public static final String FUNC_ISLOCKED = "isLocked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINIDTRANSACTIONID = "lockedByRootBlockchainIdTransactionId";

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

    public String getRLP_bookHotelAndTrain(BigInteger _date, BigInteger _bookingId) {
        final Function function = new Function(
                FUNC_BOOKHOTELANDTRAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_date), 
                new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> bookingConfirmed(BigInteger _bookingId) {
        final Function function = new Function(FUNC_BOOKINGCONFIRMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_bookingConfirmed(BigInteger _bookingId) {
        final Function function = new Function(
                FUNC_BOOKINGCONFIRMED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bookingId)), 
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

    public RemoteFunctionCall<BigInteger> hotelBlockchainId() {
        final Function function = new Function(FUNC_HOTELBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_hotelBlockchainId() {
        final Function function = new Function(
                FUNC_HOTELBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> hotelContract() {
        final Function function = new Function(FUNC_HOTELCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_hotelContract() {
        final Function function = new Function(
                FUNC_HOTELCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isLocked(BigInteger _key) {
        final Function function = new Function(FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isLocked(BigInteger _key) {
        final Function function = new Function(
                FUNC_ISLOCKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public RemoteFunctionCall<BigInteger> trainBlockchainId() {
        final Function function = new Function(FUNC_TRAINBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_trainBlockchainId() {
        final Function function = new Function(
                FUNC_TRAINBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> trainContract() {
        final Function function = new Function(FUNC_TRAINCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_trainContract() {
        final Function function = new Function(
                FUNC_TRAINCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
