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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class TxReceiptsRootStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610c92380380610c9283398101604081905261002f916100d6565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff1991821681179092558154600160a060020a031916600160a060020a03949094169390931781557f400693dd000000000000000000000000000000000000000000000000000000009091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc8054909216179055610106565b6000602082840312156100e857600080fd5b8151600160a060020a03811681146100ff57600080fd5b9392505050565b610b7d806101156000396000f3fe608060405234801561001057600080fd5b5060043610610068577c0100000000000000000000000000000000000000000000000000000000600035046301ffc9a7811461006d578063674f78ae146100c3578063917ede99146100d8578063b63735ea146100eb575b600080fd5b6100af61007b3660046105d9565b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b6100d66100d136600461066a565b610119565b005b6100af6100e636600461076d565b610226565b6100af6100f936600461085b565b600091825260026020908152604080842092845291905290205460ff1690565b60008160405160200161012e91815260200190565b60408051601f19818403018152908290526001547fea13ec8b00000000000000000000000000000000000000000000000000000000835290925073ffffffffffffffffffffffffffffffffffffffff169063ea13ec8b906101a5908e908e908e908e908e908e908e908e908e908d90600401610962565b60206040518083038186803b1580156101bd57600080fd5b505afa1580156101d1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101f59190610a1e565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff166102bd5760405160e560020a62461bcd02815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146103355760405160e560020a62461bcd02815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f6666736560448201527f747320646f6573206e6f74206d6174636800000000000000000000000000000060648201526084016102b4565b60008787604051610347929190610a40565b6040518091039020905060005b838110156104a45760006103da86868481811061037357610373610a50565b90506020028101906103859190610a7f565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b91508690508181106103ce576103ce610a50565b9050602002013561052e565b90508281146104545760405160e560020a62461bcd02815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201527f63756c617465642068617368000000000000000000000000000000000000000060648201526084016102b4565b85858381811061046657610466610a50565b90506020028101906104789190610a7f565b604051610486929190610a40565b6040518091039020925050808061049c90610af5565b915050610354565b5080891461051d5760405160e560020a62461bcd02815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c617460448201527f656420686173680000000000000000000000000000000000000000000000000060648201526084016102b4565b5060019a9950505050505050505050565b60008060005b60208110156105d157610548816008610b10565b856105538387610b2f565b8151811061056357610563610a50565b60209101015160029190910a7f0100000000000000000000000000000000000000000000000000000000000000918290049091027fff0000000000000000000000000000000000000000000000000000000000000016049190911790806105c981610af5565b915050610534565b509392505050565b6000602082840312156105eb57600080fd5b81357bffffffffffffffffffffffffffffffffffffffffffffffffffffffff198116811461061857600080fd5b9392505050565b60008083601f84011261063157600080fd5b50813567ffffffffffffffff81111561064957600080fd5b602083019150836020808302850101111561066357600080fd5b9250929050565b60008060008060008060008060008060c08b8d03121561068957600080fd5b8a35995060208b013567ffffffffffffffff808211156106a857600080fd5b6106b48e838f0161061f565b909b50995060408d01359150808211156106cd57600080fd5b6106d98e838f0161061f565b909950975060608d01359150808211156106f257600080fd5b6106fe8e838f0161061f565b909750955060808d013591508082111561071757600080fd5b506107248d828e0161061f565b9150809450508092505060a08b013590509295989b9194979a5092959850565b803573ffffffffffffffffffffffffffffffffffffffff8116811461076857600080fd5b919050565b600080600080600080600080600060c08a8c03121561078b57600080fd5b8935985061079b60208b01610744565b975060408a0135965060608a013567ffffffffffffffff808211156107bf57600080fd5b818c0191508c601f8301126107d357600080fd5b8135818111156107e257600080fd5b8d60208285010111156107f457600080fd5b6020830198508097505060808c013591508082111561081257600080fd5b61081e8d838e0161061f565b909650945060a08c013591508082111561083757600080fd5b506108448c828d0161061f565b915080935050809150509295985092959850929598565b6000806040838503121561086e57600080fd5b50508035926020909101359150565b81835260007f07ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8311156108af57600080fd5b602083028083602087013760009401602001938452509192915050565b818352600060208085019450826000805b8681101561090957823560ff81168082146108f6578384fd5b89525096830196918301916001016108dd565b50959695505050505050565b6000815180845260005b8181101561093b5760208185018101518683018201520161091f565b8181111561094d576000602083870101525b50601f01601f19169290920160200192915050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d8110156109b95773ffffffffffffffffffffffffffffffffffffffff6109a685610744565b1682529282019290820190600101610980565b5084810360408601526109cd818c8e61087d565b9250505082810360608401526109e481888a61087d565b905082810360808401526109f98186886108cc565b905082810360a0840152610a0d8185610915565b9d9c50505050505050505050505050565b600060208284031215610a3057600080fd5b8151801515811461061857600080fd5b8183823760009101908152919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6000808335601e19843603018112610a9657600080fd5b83018035915067ffffffffffffffff821115610ab157600080fd5b60200191503681900382131561066357600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000600019821415610b0957610b09610ac6565b5060010190565b6000816000190483118215151615610b2a57610b2a610ac6565b500290565b60008219821115610b4257610b42610ac6565b50019056fea2646970667358221220489ade0159bb2c122134e964a918e562987f228f9872f8d86a875b8c493e882664736f6c63430008090033";

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
