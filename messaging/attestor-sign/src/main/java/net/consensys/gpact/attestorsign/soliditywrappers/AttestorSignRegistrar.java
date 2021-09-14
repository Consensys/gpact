package net.consensys.gpact.attestorsign.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
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
public class AttestorSignRegistrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610e6f806100206000396000f3fe608060405234801561001057600080fd5b506004361061009d5760003560e01c8063b9c3620911610066578063b9c362091461015b578063d4c0d34d1461016e578063de789ee314610181578063ea13ec8b14610194578063f5e232ea146101a757600080fd5b8062ab2414146100a257806315a09825146100b75780633156d37c146100ca57806348bcbd2d146100dd578063a64ce1991461012d575b600080fd5b6100b56100b0366004610baf565b6101ca565b005b6100b56100c5366004610b83565b61024c565b6100b56100d8366004610b83565b6102a7565b6101186100eb366004610b83565b6000828152602081815260408083206001600160a01b038516845260020190915290205460ff1692915050565b60405190151581526020015b60405180910390f35b61014d61013b366004610b6a565b60009081526020819052604090205490565b604051908152602001610124565b6100b5610169366004610ce4565b610392565b6100b561017c366004610baf565b6103df565b6100b561018f366004610d06565b610432565b6101186101a2366004610be4565b610550565b61014d6101b5366004610b6a565b60009081526020819052604090206001015490565b6000838152602081905260409020546101fe5760405162461bcd60e51b81526004016101f590610d8c565b60405180910390fd5b610208838361082f565b600083815260208190526040812060019081015461022591610dc3565b600085815260208190526040902060010181905590506102468482846108cd565b50505050565b6000828152602081905260409020546102775760405162461bcd60e51b81526004016101f590610d8c565b610281828261082f565b600082815260208190526040812060010180549161029e83610df2565b91905055505050565b6000828152602081905260409020546102d25760405162461bcd60e51b81526004016101f590610d8c565b6102dc8282610941565b60008281526020819052604081206001908101546102fa9190610ddb565b6000848152602081905260409020549091508110156103795760405162461bcd60e51b815260206004820152603560248201527f50726f706f736564206e6577206e756d626572206f66207369676e65727320696044820152741cc81b195cdcc81d1a185b881d1a1c995cda1bdb19605a1b60648201526084016101f5565b6000928352602083905260409092206001019190915550565b6000828152602081905260409020546103bd5760405162461bcd60e51b81526004016101f590610d8c565b6000828152602081905260409020600101546103db908390836108cd565b5050565b60008381526020819052604090205461040a5760405162461bcd60e51b81526004016101f590610d8c565b6104148383610941565b60008381526020819052604081206001908101546102259190610ddb565b6000848152602081905260409020541561048e5760405162461bcd60e51b815260206004820152601960248201527f426c6f636b636861696e20616c7265616479206578697374730000000000000060448201526064016101f5565b826104e55760405162461bcd60e51b815260206004820152602160248201527f5369676e696e67207468726573686f6c642063616e206e6f74206265207a65726044820152606f60f81b60648201526084016101f5565b60008481526020819052604081206001018290555b81811015610544576105328584848481811061051857610518610e23565b905060200201602081019061052d9190610b48565b61082f565b8061053c81610df2565b9150506104fa565b506102468482856108cd565b6000898881146105995760405162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016101f5565b8087146105df5760405162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016101f5565b8085146106255760405162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016101f5565b60008d8152602081905260409020548110156106785760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b60448201526064016101f5565b60005b8181101561081b5760008e8152602081905260408120600201908e8e848181106106a7576106a7610e23565b90506020020160208101906106bc9190610b48565b6001600160a01b0316815260208101919091526040016000205460ff166107335760405162461bcd60e51b815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201526431b430b4b760d91b60648201526084016101f5565b6107bd8d8d8381811061074857610748610e23565b905060200201602081019061075d9190610b48565b86868e8e8681811061077157610771610e23565b905060200201358d8d8781811061078a5761078a610e23565b905060200201358c8c888181106107a3576107a3610e23565b90506020020160208101906107b89190610d59565b6109db565b6108095760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f7420766572696679000000000000000060448201526064016101f5565b8061081381610df2565b91505061067b565b5060019d9c50505050505050505050505050565b6000828152602081815260408083206001600160a01b038516845260020190915290205460ff161561089b5760405162461bcd60e51b81526020600482015260156024820152745369676e657220616c72656164792065786973747360581b60448201526064016101f5565b6000918252602082815260408084206001600160a01b039093168452600290920190529020805460ff19166001179055565b8082101561092b5760405162461bcd60e51b815260206004820152602560248201527f4e756d626572206f66207369676e657273206c657373207468616e20746872656044820152641cda1bdb1960da1b60648201526084016101f5565b6000928352602083905260409092209190915550565b6000828152602081815260408083206001600160a01b038516845260020190915290205460ff166109ac5760405162461bcd60e51b815260206004820152601560248201527414da59db995c88191bd95cc81b9bdd08195e1a5cdd605a1b60448201526064016101f5565b6000918252602082815260408084206001600160a01b039093168452600290920190529020805460ff19169055565b60008086866040516109ee929190610d7c565b604051809103902090508260ff16601b14158015610a1057508260ff16601c14155b15610a1f576000915050610a94565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa158015610a72573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b80356001600160a01b0381168114610ab557600080fd5b919050565b60008083601f840112610acc57600080fd5b50813567ffffffffffffffff811115610ae457600080fd5b6020830191508360208260051b8501011115610aff57600080fd5b9250929050565b60008083601f840112610b1857600080fd5b50813567ffffffffffffffff811115610b3057600080fd5b602083019150836020828501011115610aff57600080fd5b600060208284031215610b5a57600080fd5b610b6382610a9e565b9392505050565b600060208284031215610b7c57600080fd5b5035919050565b60008060408385031215610b9657600080fd5b82359150610ba660208401610a9e565b90509250929050565b600080600060608486031215610bc457600080fd5b83359250610bd460208501610a9e565b9150604084013590509250925092565b600080600080600080600080600080600060c08c8e031215610c0557600080fd5b8b359a5067ffffffffffffffff8060208e01351115610c2357600080fd5b610c338e60208f01358f01610aba565b909b50995060408d0135811015610c4957600080fd5b610c598e60408f01358f01610aba565b909950975060608d0135811015610c6f57600080fd5b610c7f8e60608f01358f01610aba565b909750955060808d0135811015610c9557600080fd5b610ca58e60808f01358f01610aba565b909550935060a08d0135811015610cbb57600080fd5b50610ccc8d60a08e01358e01610b06565b81935080925050509295989b509295989b9093969950565b60008060408385031215610cf757600080fd5b50508035926020909101359150565b60008060008060608587031215610d1c57600080fd5b8435935060208501359250604085013567ffffffffffffffff811115610d4157600080fd5b610d4d87828801610aba565b95989497509550505050565b600060208284031215610d6b57600080fd5b813560ff81168114610b6357600080fd5b8183823760009101908152919050565b60208082526019908201527f426c6f636b636861696e20646f6573206e6f7420657869737400000000000000604082015260600190565b60008219821115610dd657610dd6610e0d565b500190565b600082821015610ded57610ded610e0d565b500390565b6000600019821415610e0657610e06610e0d565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea264697066735822122045f62d79ee1affd25eba9cfd847dcac94dd06b2179633194f0e5a0d25b4ef1da64736f6c63430008050033";

    public static final String FUNC_ADDBLOCKCHAIN = "addBlockchain";

    public static final String FUNC_ADDSIGNER = "addSigner";

    public static final String FUNC_ADDSIGNERSETTHRESHOLD = "addSignerSetThreshold";

    public static final String FUNC_GETSIGNINGTHRESHOLD = "getSigningThreshold";

    public static final String FUNC_ISSIGNER = "isSigner";

    public static final String FUNC_NUMSIGNERS = "numSigners";

    public static final String FUNC_REMOVESIGNER = "removeSigner";

    public static final String FUNC_REMOVESIGNERSETTHRESHOLD = "removeSignerSetThreshold";

    public static final String FUNC_SETTHRESHOLD = "setThreshold";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected AttestorSignRegistrar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AttestorSignRegistrar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AttestorSignRegistrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AttestorSignRegistrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addBlockchain(BigInteger _bcId, BigInteger _signingThreshold, List<String> _signers) {
        final Function function = new Function(
                FUNC_ADDBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_signingThreshold), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSigner(BigInteger _bcId, String _signer) {
        final Function function = new Function(
                FUNC_ADDSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSignerSetThreshold(BigInteger _bcId, String _signer, BigInteger _newSigningThreshold) {
        final Function function = new Function(
                FUNC_ADDSIGNERSETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final Function function = new Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final Function function = new Function(FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> numSigners(BigInteger _blockchainId) {
        final Function function = new Function(FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSigner(BigInteger _bcId, String _signer) {
        final Function function = new Function(
                FUNC_REMOVESIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSignerSetThreshold(BigInteger _bcId, String _signer, BigInteger _newSigningThreshold) {
        final Function function = new Function(
                FUNC_REMOVESIGNERSETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setThreshold(BigInteger _bcId, BigInteger _newSigningThreshold) {
        final Function function = new Function(
                FUNC_SETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verify(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _plainText) {
        final Function function = new Function(FUNC_VERIFY, 
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
                new org.web3j.abi.datatypes.DynamicBytes(_plainText)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static AttestorSignRegistrar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AttestorSignRegistrar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AttestorSignRegistrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AttestorSignRegistrar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AttestorSignRegistrar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AttestorSignRegistrar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AttestorSignRegistrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AttestorSignRegistrar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AttestorSignRegistrar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AttestorSignRegistrar.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AttestorSignRegistrar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AttestorSignRegistrar.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AttestorSignRegistrar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AttestorSignRegistrar.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AttestorSignRegistrar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AttestorSignRegistrar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
