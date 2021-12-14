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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b15380380610b1583398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b039490941693909317815563400693dd60e01b9091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc80549092161790556100ed565b6000602082840312156100cf57600080fd5b81516001600160a01b03811681146100e657600080fd5b9392505050565b610a19806100fc6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a714610051578063674f78ae14610092578063917ede99146100a7578063b63735ea146100ba575b600080fd5b61007e61005f3660046104ee565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b6100a56100a036600461056b565b6100e8565b005b61007e6100b5366004610661565b6101c0565b61007e6100c836600461074f565b600091825260026020908152604080842092845291905290205460ff1690565b6000816040516020016100fd91815260200190565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b9061014e908e908e908e908e908e908e908e908e908e908d9060040161083d565b602060405180830381865afa15801561016b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061018f91906108ec565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff166102545760405162461bcd60e51b815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146102bd5760405162461bcd60e51b815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656044820152700e8e640c8decae640dcdee840dac2e8c6d607b1b606482015260840161024b565b600087876040516102cf92919061090e565b6040518091039020905060005b838110156104185760006103628686848181106102fb576102fb61091e565b905060200281019061030d9190610934565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b91508690508181106103565761035661091e565b90506020020135610489565b90508281146103c85760405162461bcd60e51b815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201526b0c6ead8c2e8cac840d0c2e6d60a31b606482015260840161024b565b8585838181106103da576103da61091e565b90506020028101906103ec9190610934565b6040516103fa92919061090e565b6040518091039020925050808061041090610991565b9150506102dc565b508089146104785760405162461bcd60e51b815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746044820152660cac840d0c2e6d60cb1b606482015260840161024b565b5060019a9950505050505050505050565b60008060005b60208110156104e6576104a38160086109ac565b856104ae83876109cb565b815181106104be576104be61091e565b01602001516001600160f81b031916901c9190911790806104de81610991565b91505061048f565b509392505050565b60006020828403121561050057600080fd5b81356001600160e01b03198116811461051857600080fd5b9392505050565b60008083601f84011261053157600080fd5b50813567ffffffffffffffff81111561054957600080fd5b6020830191508360208260051b850101111561056457600080fd5b9250929050565b60008060008060008060008060008060c08b8d03121561058a57600080fd5b8a35995060208b013567ffffffffffffffff808211156105a957600080fd5b6105b58e838f0161051f565b909b50995060408d01359150808211156105ce57600080fd5b6105da8e838f0161051f565b909950975060608d01359150808211156105f357600080fd5b6105ff8e838f0161051f565b909750955060808d013591508082111561061857600080fd5b506106258d828e0161051f565b9150809450508092505060a08b013590509295989b9194979a5092959850565b80356001600160a01b038116811461065c57600080fd5b919050565b600080600080600080600080600060c08a8c03121561067f57600080fd5b8935985061068f60208b01610645565b975060408a0135965060608a013567ffffffffffffffff808211156106b357600080fd5b818c0191508c601f8301126106c757600080fd5b8135818111156106d657600080fd5b8d60208285010111156106e857600080fd5b6020830198508097505060808c013591508082111561070657600080fd5b6107128d838e0161051f565b909650945060a08c013591508082111561072b57600080fd5b506107388c828d0161051f565b915080935050809150509295985092959850929598565b6000806040838503121561076257600080fd5b50508035926020909101359150565b81835260006001600160fb1b0383111561078a57600080fd5b8260051b8083602087013760009401602001938452509192915050565b818352600060208085019450826000805b868110156107e457823560ff81168082146107d1578384fd5b89525096830196918301916001016107b8565b50959695505050505050565b6000815180845260005b81811015610816576020818501810151868301820152016107fa565b81811115610828576000602083870101525b50601f01601f19169290920160200192915050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d811015610887576001600160a01b0361087485610645565b168252928201929082019060010161085b565b50848103604086015261089b818c8e610771565b9250505082810360608401526108b281888a610771565b905082810360808401526108c78186886107a7565b905082810360a08401526108db81856107f0565b9d9c50505050505050505050505050565b6000602082840312156108fe57600080fd5b8151801515811461051857600080fd5b8183823760009101908152919050565b634e487b7160e01b600052603260045260246000fd5b6000808335601e1984360301811261094b57600080fd5b83018035915067ffffffffffffffff82111561096657600080fd5b60200191503681900382131561056457600080fd5b634e487b7160e01b600052601160045260246000fd5b60006000198214156109a5576109a561097b565b5060010190565b60008160001904831182151516156109c6576109c661097b565b500290565b600082198211156109de576109de61097b565b50019056fea2646970667358221220d771b4d2e906361dd6c986905ccecd1f907bae0823258c6a73e2420be96ee6c864736f6c634300080a0033";

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
