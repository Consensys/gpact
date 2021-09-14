package net.consensys.gpact.txroot.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class TxReceiptsRootStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b24380380610b2483398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b039490941693909317815563400693dd60e01b9091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc80549092161790556100ed565b6000602082840312156100cf57600080fd5b81516001600160a01b03811681146100e657600080fd5b9392505050565b610a28806100fc6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a714610051578063674f78ae14610092578063917ede99146100a7578063b63735ea146100ba575b600080fd5b61007e61005f36600461058e565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b6100a56100a03660046106a6565b6100e8565b005b61007e6100b53660046105b8565b6101cf565b61007e6100c8366004610780565b600091825260026020908152604080842092845291905290205460ff1690565b6000816040516020016100fd91815260200190565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b9061014e908e908e908e908e908e908e908e908e908e908d9060040161087e565b60206040518083038186803b15801561016657600080fd5b505afa15801561017a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061019e9190610565565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff166102635760405162461bcd60e51b815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146102cc5760405162461bcd60e51b815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656044820152700e8e640c8decae640dcdee840dac2e8c6d607b1b606482015260840161025a565b600087876040516102de92919061086e565b6040518091039020905060005b8381101561042757600061037186868481811061030a5761030a6109dc565b905060200281019061031c919061092d565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b9150869050818110610365576103656109dc565b90506020020135610498565b90508281146103d75760405162461bcd60e51b815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201526b0c6ead8c2e8cac840d0c2e6d60a31b606482015260840161025a565b8585838181106103e9576103e96109dc565b90506020028101906103fb919061092d565b60405161040992919061086e565b6040518091039020925050808061041f906109ab565b9150506102eb565b508089146104875760405162461bcd60e51b815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746044820152660cac840d0c2e6d60cb1b606482015260840161025a565b5060019a9950505050505050505050565b60008060005b60208110156104f5576104b281600861098c565b856104bd8387610974565b815181106104cd576104cd6109dc565b01602001516001600160f81b031916901c9190911790806104ed816109ab565b91505061049e565b509392505050565b80356001600160a01b038116811461051457600080fd5b919050565b60008083601f84011261052b57600080fd5b50813567ffffffffffffffff81111561054357600080fd5b6020830191508360208260051b850101111561055e57600080fd5b9250929050565b60006020828403121561057757600080fd5b8151801515811461058757600080fd5b9392505050565b6000602082840312156105a057600080fd5b81356001600160e01b03198116811461058757600080fd5b600080600080600080600080600060c08a8c0312156105d657600080fd5b893598506105e660208b016104fd565b975060408a0135965060608a013567ffffffffffffffff8082111561060a57600080fd5b818c0191508c601f83011261061e57600080fd5b81358181111561062d57600080fd5b8d602082850101111561063f57600080fd5b6020830198508097505060808c013591508082111561065d57600080fd5b6106698d838e01610519565b909650945060a08c013591508082111561068257600080fd5b5061068f8c828d01610519565b915080935050809150509295985092959850929598565b60008060008060008060008060008060c08b8d0312156106c557600080fd5b8a35995060208b013567ffffffffffffffff808211156106e457600080fd5b6106f08e838f01610519565b909b50995060408d013591508082111561070957600080fd5b6107158e838f01610519565b909950975060608d013591508082111561072e57600080fd5b61073a8e838f01610519565b909750955060808d013591508082111561075357600080fd5b506107608d828e01610519565b9150809450508092505060a08b013590509295989b9194979a5092959850565b6000806040838503121561079357600080fd5b50508035926020909101359150565b81835260006001600160fb1b038311156107bb57600080fd5b8260051b8083602087013760009401602001938452509192915050565b818352600060208085019450826000805b8681101561081557823560ff8116808214610802578384fd5b89525096830196918301916001016107e9565b50959695505050505050565b6000815180845260005b818110156108475760208185018101518683018201520161082b565b81811115610859576000602083870101525b50601f01601f19169290920160200192915050565b8183823760009101908152919050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d8110156108c8576001600160a01b036108b5856104fd565b168252928201929082019060010161089c565b5084810360408601526108dc818c8e6107a2565b9250505082810360608401526108f381888a6107a2565b905082810360808401526109088186886107d8565b905082810360a084015261091c8185610821565b9d9c50505050505050505050505050565b6000808335601e1984360301811261094457600080fd5b83018035915067ffffffffffffffff82111561095f57600080fd5b60200191503681900382131561055e57600080fd5b60008219821115610987576109876109c6565b500190565b60008160001904831182151516156109a6576109a66109c6565b500290565b60006000198214156109bf576109bf6109c6565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea26469706673582212205f6ecf016cf7d94b886c2822bf1f9c7f61f6c19948fd7da52c1b67ba426a0c1764736f6c63430008050033";

    public static final String FUNC_ADDTXRECEIPTROOT = "addTxReceiptRoot";

    public static final String FUNC_CONTAINSTXRECEIPTROOT = "containsTxReceiptRoot";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addTxReceiptRoot(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _txReceiptsRoot) {
        final Function function = new Function(
                FUNC_ADDTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> containsTxReceiptRoot(BigInteger _blockchainId, byte[] _txReceiptsRoot) {
        final Function function = new Function(FUNC_CONTAINSTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verify(BigInteger _blockchainId, String param1, byte[] _txReceiptsRoot, byte[] _txReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_txReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TxReceiptsRootStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TxReceiptsRootStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TxReceiptsRootStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TxReceiptsRootStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
