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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class TxReceiptsRootStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b24380380610b2483398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b039490941693909317815563400693dd60e01b9091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc80549092161790556100ed565b6000602082840312156100cf57600080fd5b81516001600160a01b03811681146100e657600080fd5b9392505050565b610a28806100fc6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a714610051578063674f78ae14610092578063917ede99146100a7578063b63735ea146100ba575b600080fd5b61007e61005f3660046104fd565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b6100a56100a036600461057a565b6100e8565b005b61007e6100b5366004610670565b6101cf565b61007e6100c836600461075e565b600091825260026020908152604080842092845291905290205460ff1690565b6000816040516020016100fd91815260200190565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b9061014e908e908e908e908e908e908e908e908e908e908d9060040161084c565b60206040518083038186803b15801561016657600080fd5b505afa15801561017a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061019e91906108fb565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff166102635760405162461bcd60e51b815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146102cc5760405162461bcd60e51b815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656044820152700e8e640c8decae640dcdee840dac2e8c6d607b1b606482015260840161025a565b600087876040516102de92919061091d565b6040518091039020905060005b8381101561042757600061037186868481811061030a5761030a61092d565b905060200281019061031c9190610943565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b91508690508181106103655761036561092d565b90506020020135610498565b90508281146103d75760405162461bcd60e51b815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201526b0c6ead8c2e8cac840d0c2e6d60a31b606482015260840161025a565b8585838181106103e9576103e961092d565b90506020028101906103fb9190610943565b60405161040992919061091d565b6040518091039020925050808061041f906109a0565b9150506102eb565b508089146104875760405162461bcd60e51b815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746044820152660cac840d0c2e6d60cb1b606482015260840161025a565b5060019a9950505050505050505050565b60008060005b60208110156104f5576104b28160086109bb565b856104bd83876109da565b815181106104cd576104cd61092d565b01602001516001600160f81b031916901c9190911790806104ed816109a0565b91505061049e565b509392505050565b60006020828403121561050f57600080fd5b81356001600160e01b03198116811461052757600080fd5b9392505050565b60008083601f84011261054057600080fd5b50813567ffffffffffffffff81111561055857600080fd5b6020830191508360208260051b850101111561057357600080fd5b9250929050565b60008060008060008060008060008060c08b8d03121561059957600080fd5b8a35995060208b013567ffffffffffffffff808211156105b857600080fd5b6105c48e838f0161052e565b909b50995060408d01359150808211156105dd57600080fd5b6105e98e838f0161052e565b909950975060608d013591508082111561060257600080fd5b61060e8e838f0161052e565b909750955060808d013591508082111561062757600080fd5b506106348d828e0161052e565b9150809450508092505060a08b013590509295989b9194979a5092959850565b80356001600160a01b038116811461066b57600080fd5b919050565b600080600080600080600080600060c08a8c03121561068e57600080fd5b8935985061069e60208b01610654565b975060408a0135965060608a013567ffffffffffffffff808211156106c257600080fd5b818c0191508c601f8301126106d657600080fd5b8135818111156106e557600080fd5b8d60208285010111156106f757600080fd5b6020830198508097505060808c013591508082111561071557600080fd5b6107218d838e0161052e565b909650945060a08c013591508082111561073a57600080fd5b506107478c828d0161052e565b915080935050809150509295985092959850929598565b6000806040838503121561077157600080fd5b50508035926020909101359150565b81835260006001600160fb1b0383111561079957600080fd5b8260051b8083602087013760009401602001938452509192915050565b818352600060208085019450826000805b868110156107f357823560ff81168082146107e0578384fd5b89525096830196918301916001016107c7565b50959695505050505050565b6000815180845260005b8181101561082557602081850181015186830182015201610809565b81811115610837576000602083870101525b50601f01601f19169290920160200192915050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d811015610896576001600160a01b0361088385610654565b168252928201929082019060010161086a565b5084810360408601526108aa818c8e610780565b9250505082810360608401526108c181888a610780565b905082810360808401526108d68186886107b6565b905082810360a08401526108ea81856107ff565b9d9c50505050505050505050505050565b60006020828403121561090d57600080fd5b8151801515811461052757600080fd5b8183823760009101908152919050565b634e487b7160e01b600052603260045260246000fd5b6000808335601e1984360301811261095a57600080fd5b83018035915067ffffffffffffffff82111561097557600080fd5b60200191503681900382131561057357600080fd5b634e487b7160e01b600052601160045260246000fd5b60006000198214156109b4576109b461098a565b5060010190565b60008160001904831182151516156109d5576109d561098a565b500290565b600082198211156109ed576109ed61098a565b50019056fea26469706673582212204845d7801877ae8e68601feff7ec7275facaf4912e8fb225056a0244f16efbde64736f6c63430008090033";

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
