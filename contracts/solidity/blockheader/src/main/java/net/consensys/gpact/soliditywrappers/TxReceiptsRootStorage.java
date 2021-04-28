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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610bb0380380610bb083398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b039490941693909317815563400693dd60e01b9091527f5ca99457c0861cd8c45ec8ac85e4b4c16d2aafa71de9f5b4a576be77670c47fc80549092161790556100eb565b6000602082840312156100ce578081fd5b81516001600160a01b03811681146100e4578182fd5b9392505050565b610ab6806100fa6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a714610051578063674f78ae14610078578063917ede991461008d578063b63735ea146100a0575b600080fd5b61006461005f36600461064a565b6100ce565b604051901515815260200160405180910390f35b61008b610086366004610759565b6100f1565b005b61006461009b366004610672565b6101d8565b6100646100ae36600461082e565b600091825260026020908152604080842092845291905290205460ff1690565b6001600160e01b0319811660009081526020819052604090205460ff165b919050565b60008160405160200161010691815260200190565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b90610157908e908e908e908e908e908e908e908e908e908d90600401610924565b60206040518083038186803b15801561016f57600080fd5b505afa158015610183573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101a79190610623565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b60008981526002602090815260408083208a845290915281205460ff1661026c5760405162461bcd60e51b815260206004820152603960248201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60448201527f7420657869737420666f7220626c6f636b636861696e2069640000000000000060648201526084015b60405180910390fd5b8184146102d55760405162461bcd60e51b815260206004820152603160248201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656044820152700e8e640c8decae640dcdee840dac2e8c6d607b1b6064820152608401610263565b600154604051633067140960e11b8152600481018c90526001600160a01b038b81166024830152909116906360ce28129060440160206040518083038186803b15801561032157600080fd5b505afa158015610335573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103599190610623565b506000878760405161036c929190610914565b6040518091039020905060005b838110156104df57600061041b8686848181106103a657634e487b7160e01b600052603260045260246000fd5b90506020028101906103b891906109d3565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b915086905081811061040f57634e487b7160e01b600052603260045260246000fd5b90506020020135610550565b90508281146104815760405162461bcd60e51b815260206004820152602c60248201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60448201526b0c6ead8c2e8cac840d0c2e6d60a31b6064820152608401610263565b8585838181106104a157634e487b7160e01b600052603260045260246000fd5b90506020028101906104b391906109d3565b6040516104c1929190610914565b604051809103902092505080806104d790610a4f565b915050610379565b5080891461053f5760405162461bcd60e51b815260206004820152602760248201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746044820152660cac840d0c2e6d60cb1b6064820152608401610263565b5060019a9950505050505050505050565b60008060005b60208110156105bb5761056a816008610a30565b856105758387610a18565b8151811061059357634e487b7160e01b600052603260045260246000fd5b01602001516001600160f81b031916901c9190911790806105b381610a4f565b915050610556565b509392505050565b80356001600160a01b03811681146100ec57600080fd5b60008083601f8401126105eb578182fd5b50813567ffffffffffffffff811115610602578182fd5b602083019150836020808302850101111561061c57600080fd5b9250929050565b600060208284031215610634578081fd5b81518015158114610643578182fd5b9392505050565b60006020828403121561065b578081fd5b81356001600160e01b031981168114610643578182fd5b600080600080600080600080600060c08a8c03121561068f578485fd5b8935985061069f60208b016105c3565b975060408a0135965060608a013567ffffffffffffffff808211156106c2578687fd5b818c0191508c601f8301126106d5578687fd5b8135818111156106e3578788fd5b8d60208285010111156106f4578788fd5b6020830198508097505060808c0135915080821115610711578586fd5b61071d8d838e016105da565b909650945060a08c0135915080821115610735578384fd5b506107428c828d016105da565b915080935050809150509295985092959850929598565b60008060008060008060008060008060c08b8d031215610777578081fd5b8a35995060208b013567ffffffffffffffff80821115610795578283fd5b6107a18e838f016105da565b909b50995060408d01359150808211156107b9578283fd5b6107c58e838f016105da565b909950975060608d01359150808211156107dd578283fd5b6107e98e838f016105da565b909750955060808d0135915080821115610801578283fd5b5061080e8d828e016105da565b9150809450508092505060a08b013590509295989b9194979a5092959850565b60008060408385031215610840578182fd5b50508035926020909101359150565b81835260006001600160fb1b03831115610867578081fd5b6020830280836020870137939093016020019283525090919050565b60008284526020808501945082825b858110156108be57813560ff81168082146108ab578586fd5b8852509582019590820190600101610892565b509495945050505050565b60008151808452815b818110156108ee576020818501810151868301820152016108d2565b818111156108ff5782602083870101525b50601f01601f19169290920160200192915050565b6000828483379101908152919050565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d81101561096e576001600160a01b0361095b856105c3565b1682529282019290820190600101610942565b508481036040860152610982818c8e61084f565b92505050828103606084015261099981888a61084f565b905082810360808401526109ae818688610883565b905082810360a08401526109c281856108c9565b9d9c50505050505050505050505050565b6000808335601e198436030181126109e9578283fd5b83018035915067ffffffffffffffff821115610a03578283fd5b60200191503681900382131561061c57600080fd5b60008219821115610a2b57610a2b610a6a565b500190565b6000816000190483118215151615610a4a57610a4a610a6a565b500290565b6000600019821415610a6357610a63610a6a565b5060010190565b634e487b7160e01b600052601160045260246000fdfea2646970667358221220634dffac20d280cb7086c7ab37f508062523f397336c6dc13ee9f2456bf92aec64736f6c63430008020033";

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
