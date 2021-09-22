package net.consensys.gpact.attestorsign.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3506110a1806100616000396000f3fe608060405234801561001057600080fd5b50600436106100ce5760003560e01c80638da5cb5b1161008c578063d4c0d34d11610066578063d4c0d34d146101d7578063ea13ec8b146101ea578063f2fde38b146101fd578063f5e232ea1461021057600080fd5b80638da5cb5b1461017b578063a64ce19914610196578063b9c36209146101c457600080fd5b8062ab2414146100d357806315a09825146100e85780633156d37c146100fb57806348bcbd2d1461010e578063715018a6146101605780638d7678fd14610168575b600080fd5b6100e66100e1366004610de3565b610234565b005b6100e66100f6366004610db7565b6102b5565b6100e6610109366004610db7565b610388565b61014b61011c366004610db7565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff1692915050565b60405190151581526020015b60405180910390f35b6100e6610473565b6100e6610176366004610f18565b6104e7565b6000546040516001600160a01b039091168152602001610157565b6101b66101a4366004610d9e565b60009081526001602052604090205490565b604051908152602001610157565b6100e66101d2366004610f6b565b6105a5565b6100e66101e5366004610de3565b6105f4565b61014b6101f8366004610e18565b610645565b6100e661020b366004610d7c565b610924565b6101b661021e366004610d9e565b6000908152600160208190526040909120015490565b6000546001600160a01b031633146102675760405162461bcd60e51b815260040161025e90610fc0565b60405180910390fd5b6102718383610a0e565b6000838152600160208190526040822081015461028d91610ff5565b60008581526001602081905260409091200181905590506102af848284610ab0565b50505050565b6000546001600160a01b031633146102df5760405162461bcd60e51b815260040161025e90610fc0565b6000828152600160205260409020546103585760405162461bcd60e51b815260206004820152603560248201527f43616e206e6f7420616464207369676e657220666f7220626c6f636b636861696044820152741b881dda5d1a081e995c9bc81d1a1c995cda1bdb19605a1b606482015260840161025e565b6103628282610a0e565b600082815260016020819052604082200180549161037f83611024565b91905055505050565b6000546001600160a01b031633146103b25760405162461bcd60e51b815260040161025e90610fc0565b6103bc8282610b71565b600082815260016020819052604082208101546103d9919061100d565b6000848152600160205260409020549091508110156104585760405162461bcd60e51b815260206004820152603560248201527f50726f706f736564206e6577206e756d626572206f66207369676e65727320696044820152741cc81b195cdcc81d1a185b881d1a1c995cda1bdb19605a1b606482015260840161025e565b60009283526001602081905260409093209092019190915550565b6000546001600160a01b0316331461049d5760405162461bcd60e51b815260040161025e90610fc0565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000546001600160a01b031633146105115760405162461bcd60e51b815260040161025e90610fc0565b60005b8281101561055e5761054c8585858481811061053257610532611055565b90506020020160208101906105479190610d7c565b610a0e565b8061055681611024565b915050610514565b5060008481526001602081905260408220015461057c908490610ff5565b600086815260016020819052604090912001819055905061059e858284610ab0565b5050505050565b6000546001600160a01b031633146105cf5760405162461bcd60e51b815260040161025e90610fc0565b6105f082600160008581526020019081526020016000206001015483610ab0565b5050565b6000546001600160a01b0316331461061e5760405162461bcd60e51b815260040161025e90610fc0565b6106288383610b71565b6000838152600160208190526040822081015461028d919061100d565b60008988811461068e5760405162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b8087146106d45760405162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b80851461071a5760405162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b60008d81526001602052604090205481101561076d5760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b604482015260640161025e565b60005b818110156109105760008e8152600160205260408120600201908e8e8481811061079c5761079c611055565b90506020020160208101906107b19190610d7c565b6001600160a01b0316815260208101919091526040016000205460ff166108285760405162461bcd60e51b815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201526431b430b4b760d91b606482015260840161025e565b6108b28d8d8381811061083d5761083d611055565b90506020020160208101906108529190610d7c565b86868e8e8681811061086657610866611055565b905060200201358d8d8781811061087f5761087f611055565b905060200201358c8c8881811061089857610898611055565b90506020020160208101906108ad9190610f8d565b610c0f565b6108fe5760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f74207665726966790000000000000000604482015260640161025e565b8061090881611024565b915050610770565b5060019d9c50505050505050505050505050565b6000546001600160a01b0316331461094e5760405162461bcd60e51b815260040161025e90610fc0565b6001600160a01b0381166109b35760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b606482015260840161025e565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff1615610a7c5760405162461bcd60e51b81526020600482015260156024820152745369676e657220616c72656164792065786973747360581b604482015260640161025e565b60009182526001602081815260408085206001600160a01b039094168552600290930190529120805460ff19169091179055565b80821015610b0e5760405162461bcd60e51b815260206004820152602560248201527f4e756d626572206f66207369676e657273206c657373207468616e20746872656044820152641cda1bdb1960da1b606482015260840161025e565b80610b5b5760405162461bcd60e51b815260206004820152601960248201527f5468726573686f6c642063616e206e6f74206265207a65726f00000000000000604482015260640161025e565b6000928352600160205260409092209190915550565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff16610bde5760405162461bcd60e51b815260206004820152601560248201527414da59db995c88191bd95cc81b9bdd08195e1a5cdd605a1b604482015260640161025e565b60009182526001602090815260408084206001600160a01b039093168452600290920190529020805460ff19169055565b6000808686604051610c22929190610fb0565b604051809103902090508260ff16601b14158015610c4457508260ff16601c14155b15610c53576000915050610cc8565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa158015610ca6573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b80356001600160a01b0381168114610ce957600080fd5b919050565b60008083601f840112610d0057600080fd5b50813567ffffffffffffffff811115610d1857600080fd5b6020830191508360208260051b8501011115610d3357600080fd5b9250929050565b60008083601f840112610d4c57600080fd5b50813567ffffffffffffffff811115610d6457600080fd5b602083019150836020828501011115610d3357600080fd5b600060208284031215610d8e57600080fd5b610d9782610cd2565b9392505050565b600060208284031215610db057600080fd5b5035919050565b60008060408385031215610dca57600080fd5b82359150610dda60208401610cd2565b90509250929050565b600080600060608486031215610df857600080fd5b83359250610e0860208501610cd2565b9150604084013590509250925092565b600080600080600080600080600080600060c08c8e031215610e3957600080fd5b8b359a5067ffffffffffffffff8060208e01351115610e5757600080fd5b610e678e60208f01358f01610cee565b909b50995060408d0135811015610e7d57600080fd5b610e8d8e60408f01358f01610cee565b909950975060608d0135811015610ea357600080fd5b610eb38e60608f01358f01610cee565b909750955060808d0135811015610ec957600080fd5b610ed98e60808f01358f01610cee565b909550935060a08d0135811015610eef57600080fd5b50610f008d60a08e01358e01610d3a565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215610f2e57600080fd5b84359350602085013567ffffffffffffffff811115610f4c57600080fd5b610f5887828801610cee565b9598909750949560400135949350505050565b60008060408385031215610f7e57600080fd5b50508035926020909101359150565b600060208284031215610f9f57600080fd5b813560ff81168114610d9757600080fd5b8183823760009101908152919050565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b600082198211156110085761100861103f565b500190565b60008282101561101f5761101f61103f565b500390565b60006000198214156110385761103861103f565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea264697066735822122032442a200dd6959c2029d8c7bc8044d4e1faac524560def4c9f308f4bd249ac364736f6c63430008050033";

    public static final String FUNC_ADDSIGNER = "addSigner";

    public static final String FUNC_ADDSIGNERSETTHRESHOLD = "addSignerSetThreshold";

    public static final String FUNC_ADDSIGNERSSETTHRESHOLD = "addSignersSetThreshold";

    public static final String FUNC_GETSIGNINGTHRESHOLD = "getSigningThreshold";

    public static final String FUNC_ISSIGNER = "isSigner";

    public static final String FUNC_NUMSIGNERS = "numSigners";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVESIGNER = "removeSigner";

    public static final String FUNC_REMOVESIGNERSETTHRESHOLD = "removeSignerSetThreshold";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETTHRESHOLD = "setThreshold";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_VERIFY = "verify";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

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

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addSigner(BigInteger _bcId, String _signer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSignerSetThreshold(BigInteger _bcId, String _signer, BigInteger _newSigningThreshold) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDSIGNERSETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSignersSetThreshold(BigInteger _bcId, List<String> _signers, BigInteger _newSigningThreshold) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDSIGNERSSETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSigner(BigInteger _bcId, String _signer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVESIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSignerSetThreshold(BigInteger _bcId, String _signer, BigInteger _newSigningThreshold) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVESIGNERSETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _signer), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setThreshold(BigInteger _bcId, BigInteger _newSigningThreshold) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.generated.Uint256(_newSigningThreshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verify(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _plainText) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFY, 
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

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
