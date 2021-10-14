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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TravelAgency extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516109cb3803806109cb83398101604081905261002f916100a2565b600080546001600160a01b03199081166001600160a01b03938416179091556004805433908316179055600595909555600680548616948216949094179093556007919091556008805490931691161790556100f9565b80516001600160a01b038116811461009d57600080fd5b919050565b600080600080600060a086880312156100ba57600080fd5b855194506100ca60208701610086565b9350604086015192506100df60608701610086565b91506100ed60808701610086565b90509295509295909350565b6108c3806101086000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80638146b5c01161005b5780638146b5c01461010057806399eb5d4c146101155780639d0599ac14610128578063f6aacfb11461013b57600080fd5b8063036b0edf1461008d578063168fd46f146100b557806341b1637c146100cc5780634d1177da146100f7575b600080fd5b6100a061009b366004610726565b61015d565b60405190151581526020015b60405180910390f35b6100be60075481565b6040519081526020016100ac565b6008546100df906001600160a01b031681565b6040516001600160a01b0390911681526020016100ac565b6100be60055481565b61011361010e36600461073f565b610174565b005b610113610123366004610761565b6103b7565b6006546100df906001600160a01b031681565b6100a0610149366004610726565b600090815260026020526040902054151590565b60008061016b600084610467565b15159392505050565b6000546001600160a01b031633146101f05760405162461bcd60e51b815260206004820152603460248201527f53686f756c64206f6e6c792062652063616c6c65642062792043726f73736368604482015273185a5b8810dbdb9d1c9bdb0810dbdb9d1c9858dd60621b60648201526084015b60405180910390fd5b6004546001600160a01b0316321461024a5760405162461bcd60e51b815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e677300000000000060448201526064016101e7565b600054600554600654604080516024810187905260448101869052606480820181905282518083039091018152608490910182526020810180516001600160e01b03166309ac323760e11b17905290516392b2c33560e01b81526001600160a01b03948516946392b2c335946102c7949093911691600401610792565b600060405180830381600087803b1580156102e157600080fd5b505af11580156102f5573d6000803e3d6000fd5b5050600054600754600854604080516024810189905260448101889052606480820181905282518083039091018152608490910182526020810180516001600160e01b03166309ac323760e11b17905290516392b2c33560e01b81526001600160a01b0394851696506392b2c33595506103759490921691600401610792565b600060405180830381600087803b15801561038f57600080fd5b505af11580156103a3573d6000803e3d6000fd5b505050506103b3600082846104b2565b5050565b60005b60008281526003602052604090205481101561044f5760008281526003602052604081208054839081106103f0576103f06107fe565b90600052602060002001549050831561042e5760008181526002602052604090205461041e9060019061082a565b6000828152600160205260409020555b6000908152600260205260408120558061044781610841565b9150506103ba565b5060008181526003602052604081206103b3916106ec565b6000808383604051602001610486929190918252602082015260400190565b6040516020818303038152906040528051906020012090506104aa8160001c6104ea565b949350505050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206104e48183610559565b50505050565b600081815260026020526040812054156105465760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101e7565b5060009081526001602052604090205490565b600082815260026020526040902054156105ac5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016101e7565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156105fb57600080fd5b505afa15801561060f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610633919061085c565b90508061064d575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b15801561069257600080fd5b505af11580156106a6573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556106d8915083610875565b600084815260026020526040902055505050565b508054600082559060005260206000209081019061070a919061070d565b50565b5b80821115610722576000815560010161070e565b5090565b60006020828403121561073857600080fd5b5035919050565b6000806040838503121561075257600080fd5b50508035926020909101359150565b6000806040838503121561077457600080fd5b8235801515811461078457600080fd5b946020939093013593505050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156107d4578581018301518582016080015282016107b8565b818111156107e6576000608083870101525b50601f01601f19169290920160800195945050505050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60008282101561083c5761083c610814565b500390565b600060001982141561085557610855610814565b5060010190565b60006020828403121561086e57600080fd5b5051919050565b6000821982111561088857610888610814565b50019056fea2646970667358221220aef162adf880a3c4a47caa33148b2bae9ee9143d364c6f8f8391afeed58f4f1d64736f6c63430008090033";

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

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_finalise(Boolean _commit, byte[] _crossRootTxId) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit), 
                new org.web3j.abi.datatypes.generated.Bytes32(_crossRootTxId)), 
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
