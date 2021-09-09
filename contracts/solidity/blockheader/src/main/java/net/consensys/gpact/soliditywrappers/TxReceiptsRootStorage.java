package net.consensys.gpact.soliditywrappers;

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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610ba9380380610ba983398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b039490941693909317815563400693dd60e01b9091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc80549092161790556100ed565b6000602082840312156100cf57600080fd5b81516001600160a01b03811681146100e657600080fd5b9392505050565b610aad806100fc6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a714610051578063674f78ae14610092578063917ede99146100a7578063b63735ea146100ba575b600080fd5b61007e61005f366004610613565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b6100a56100a036600461072b565b6100e8565b005b61007e6100b536600461063d565b6101cf565b61007e6100c8366004610805565b600091825260026020908152604080842092845291905290205460ff1690565b6000816040516020016100fd91815260200190565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b9061014e908e908e908e908e908e908e908e908e908e908d90600401610903565b60206040518083038186803b15801561016657600080fd5b505afa15801561017a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061019e91906105ea565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff166102635760405162461bcd60e51b815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146102cc5760405162461bcd60e51b815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656044820152700e8e640c8decae640dcdee840dac2e8c6d607b1b606482015260840161025a565b600154604051633067140960e11b8152600481018c90526001600160a01b038b81166024830152909116906360ce28129060440160206040518083038186803b15801561031857600080fd5b505afa15801561032c573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061035091906105ea565b50600087876040516103639291906108f3565b6040518091039020905060005b838110156104ac5760006103f686868481811061038f5761038f610a61565b90506020028101906103a191906109b2565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b91508690508181106103ea576103ea610a61565b9050602002013561051d565b905082811461045c5760405162461bcd60e51b815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201526b0c6ead8c2e8cac840d0c2e6d60a31b606482015260840161025a565b85858381811061046e5761046e610a61565b905060200281019061048091906109b2565b60405161048e9291906108f3565b604051809103902092505080806104a490610a30565b915050610370565b5080891461050c5760405162461bcd60e51b815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746044820152660cac840d0c2e6d60cb1b606482015260840161025a565b5060019a9950505050505050505050565b60008060005b602081101561057a57610537816008610a11565b8561054283876109f9565b8151811061055257610552610a61565b01602001516001600160f81b031916901c91909117908061057281610a30565b915050610523565b509392505050565b80356001600160a01b038116811461059957600080fd5b919050565b60008083601f8401126105b057600080fd5b50813567ffffffffffffffff8111156105c857600080fd5b6020830191508360208260051b85010111156105e357600080fd5b9250929050565b6000602082840312156105fc57600080fd5b8151801515811461060c57600080fd5b9392505050565b60006020828403121561062557600080fd5b81356001600160e01b03198116811461060c57600080fd5b600080600080600080600080600060c08a8c03121561065b57600080fd5b8935985061066b60208b01610582565b975060408a0135965060608a013567ffffffffffffffff8082111561068f57600080fd5b818c0191508c601f8301126106a357600080fd5b8135818111156106b257600080fd5b8d60208285010111156106c457600080fd5b6020830198508097505060808c01359150808211156106e257600080fd5b6106ee8d838e0161059e565b909650945060a08c013591508082111561070757600080fd5b506107148c828d0161059e565b915080935050809150509295985092959850929598565b60008060008060008060008060008060c08b8d03121561074a57600080fd5b8a35995060208b013567ffffffffffffffff8082111561076957600080fd5b6107758e838f0161059e565b909b50995060408d013591508082111561078e57600080fd5b61079a8e838f0161059e565b909950975060608d01359150808211156107b357600080fd5b6107bf8e838f0161059e565b909750955060808d01359150808211156107d857600080fd5b506107e58d828e0161059e565b9150809450508092505060a08b013590509295989b9194979a5092959850565b6000806040838503121561081857600080fd5b50508035926020909101359150565b81835260006001600160fb1b0383111561084057600080fd5b8260051b8083602087013760009401602001938452509192915050565b818352600060208085019450826000805b8681101561089a57823560ff8116808214610887578384fd5b895250968301969183019160010161086e565b50959695505050505050565b6000815180845260005b818110156108cc576020818501810151868301820152016108b0565b818111156108de576000602083870101525b50601f01601f19169290920160200192915050565b8183823760009101908152919050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d81101561094d576001600160a01b0361093a85610582565b1682529282019290820190600101610921565b508481036040860152610961818c8e610827565b92505050828103606084015261097881888a610827565b9050828103608084015261098d81868861085d565b905082810360a08401526109a181856108a6565b9d9c50505050505050505050505050565b6000808335601e198436030181126109c957600080fd5b83018035915067ffffffffffffffff8211156109e457600080fd5b6020019150368190038213156105e357600080fd5b60008219821115610a0c57610a0c610a4b565b500190565b6000816000190483118215151615610a2b57610a2b610a4b565b500290565b6000600019821415610a4457610a44610a4b565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea2646970667358221220c9ef73301d9cd4d9f4363302d22c6e7854af89d1bc82b7efb18929f3d2cd634864736f6c63430008050033";

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

    public RemoteFunctionCall<Boolean> verify(BigInteger _blockchainId, String _cbcContract, byte[] _txReceiptsRoot, byte[] _txReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _cbcContract), 
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
