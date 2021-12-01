package net.consensys.gpact.txroot.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class AttestorSignRegistrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610033610025640100000000610038810204565b64010000000061003c810204565b61008c565b3390565b60008054600160a060020a03838116600160a060020a0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b61118b8061009b6000396000f3fe608060405234801561001057600080fd5b50600436106100eb576000357c0100000000000000000000000000000000000000000000000000000000900480638da5cb5b116100a9578063d4c0d34d11610083578063d4c0d34d146101f4578063ea13ec8b14610207578063f2fde38b1461021a578063f5e232ea1461022d57600080fd5b80638da5cb5b14610198578063a64ce199146101b3578063b9c36209146101e157600080fd5b8062ab2414146100f057806315a09825146101055780633156d37c1461011857806348bcbd2d1461012b578063715018a61461017d5780638d7678fd14610185575b600080fd5b6101036100fe366004610da7565b610251565b005b610103610113366004610ddc565b6102d5565b610103610126366004610ddc565b6103b6565b610168610139366004610ddc565b6000828152600160209081526040808320600160a060020a038516845260020190915290205460ff1692915050565b60405190151581526020015b60405180910390f35b6101036104af565b610103610193366004610e53565b6104e8565b600054604051600160a060020a039091168152602001610174565b6101d36101c1366004610ea6565b60009081526001602052604090205490565b604051908152602001610174565b6101036101ef366004610ebf565b6105a9565b610103610202366004610da7565b6105fb565b610168610215366004610f23565b61064f565b610103610228366004611023565b61097e565b6101d361023b366004610ea6565b6000908152600160208190526040909120015490565b600054600160a060020a031633146102875760405160e560020a62461bcd02815260040161027e90611045565b60405180910390fd5b6102918383610a36565b600083815260016020819052604082208101546102ad916110a9565b60008581526001602081905260409091200181905590506102cf848284610ae3565b50505050565b600054600160a060020a031633146103025760405160e560020a62461bcd02815260040161027e90611045565b6000828152600160205260409020546103865760405160e560020a62461bcd02815260206004820152603560248201527f43616e206e6f7420616464207369676e657220666f7220626c6f636b6368616960448201527f6e2077697468207a65726f207468726573686f6c640000000000000000000000606482015260840161027e565b6103908282610a36565b60008281526001602081905260408220018054916103ad836110c1565b91905055505050565b600054600160a060020a031633146103e35760405160e560020a62461bcd02815260040161027e90611045565b6103ed8282610bc2565b6000828152600160208190526040822081015461040a91906110dc565b6000848152600160205260409020549091508110156104945760405160e560020a62461bcd02815260206004820152603560248201527f50726f706f736564206e6577206e756d626572206f66207369676e657273206960448201527f73206c657373207468616e207468726573686f6c640000000000000000000000606482015260840161027e565b60009283526001602081905260409093209092019190915550565b600054600160a060020a031633146104dc5760405160e560020a62461bcd02815260040161027e90611045565b6104e66000610c6b565b565b600054600160a060020a031633146105155760405160e560020a62461bcd02815260040161027e90611045565b60005b828110156105625761055085858584818110610536576105366110f3565b905060200201602081019061054b9190611023565b610a36565b8061055a816110c1565b915050610518565b506000848152600160208190526040822001546105809084906110a9565b60008681526001602081905260409091200181905590506105a2858284610ae3565b5050505050565b600054600160a060020a031633146105d65760405160e560020a62461bcd02815260040161027e90611045565b6105f782600160008581526020019081526020016000206001015483610ae3565b5050565b600054600160a060020a031633146106285760405160e560020a62461bcd02815260040161027e90611045565b6106328383610bc2565b600083815260016020819052604082208101546102ad91906110dc565b6000898881146106a45760405160e560020a62461bcd02815260206004820152601460248201527f73696752206c656e677468206d69736d61746368000000000000000000000000604482015260640161027e565b8087146106f65760405160e560020a62461bcd02815260206004820152601460248201527f73696753206c656e677468206d69736d61746368000000000000000000000000604482015260640161027e565b8085146107485760405160e560020a62461bcd02815260206004820152601460248201527f73696756206c656e677468206d69736d61746368000000000000000000000000604482015260640161027e565b60008d8152600160205260409020548110156107a95760405160e560020a62461bcd02815260206004820152601260248201527f4e6f7420656e6f756768207369676e6572730000000000000000000000000000604482015260640161027e565b60005b8181101561096a5760008e8152600160205260408120600201908e8e848181106107d8576107d86110f3565b90506020020160208101906107ed9190611023565b600160a060020a0316815260208101919091526040016000205460ff1661087f5760405160e560020a62461bcd02815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201527f636861696e000000000000000000000000000000000000000000000000000000606482015260840161027e565b6109098d8d83818110610894576108946110f3565b90506020020160208101906108a99190611023565b86868e8e868181106108bd576108bd6110f3565b905060200201358d8d878181106108d6576108d66110f3565b905060200201358c8c888181106108ef576108ef6110f3565b90506020020160208101906109049190611122565b610cc8565b6109585760405160e560020a62461bcd02815260206004820152601860248201527f5369676e617475726520646964206e6f74207665726966790000000000000000604482015260640161027e565b80610962816110c1565b9150506107ac565b5060019d9c50505050505050505050505050565b600054600160a060020a031633146109ab5760405160e560020a62461bcd02815260040161027e90611045565b600160a060020a038116610a2a5760405160e560020a62461bcd02815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201527f6464726573730000000000000000000000000000000000000000000000000000606482015260840161027e565b610a3381610c6b565b50565b6000828152600160209081526040808320600160a060020a038516845260020190915290205460ff1615610aaf5760405160e560020a62461bcd02815260206004820152601560248201527f5369676e657220616c7265616479206578697374730000000000000000000000604482015260640161027e565b6000918252600160208181526040808520600160a060020a039094168552600290930190529120805460ff19169091179055565b80821015610b5c5760405160e560020a62461bcd02815260206004820152602560248201527f4e756d626572206f66207369676e657273206c657373207468616e207468726560448201527f73686f6c64000000000000000000000000000000000000000000000000000000606482015260840161027e565b80610bac5760405160e560020a62461bcd02815260206004820152601960248201527f5468726573686f6c642063616e206e6f74206265207a65726f00000000000000604482015260640161027e565b6000928352600160205260409092209190915550565b6000828152600160209081526040808320600160a060020a038516845260020190915290205460ff16610c3a5760405160e560020a62461bcd02815260206004820152601560248201527f5369676e657220646f6573206e6f742065786973740000000000000000000000604482015260640161027e565b6000918252600160209081526040808420600160a060020a039093168452600290920190529020805460ff19169055565b60008054600160a060020a0383811673ffffffffffffffffffffffffffffffffffffffff19831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6000808686604051610cdb929190611145565b604051809103902090508260ff16601b14158015610cfd57508260ff16601c14155b15610d0c576000915050610d81565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa158015610d5f573d6000803e3d6000fd5b50505060206040510351600160a060020a031688600160a060020a0316149150505b9695505050505050565b8035600160a060020a0381168114610da257600080fd5b919050565b600080600060608486031215610dbc57600080fd5b83359250610dcc60208501610d8b565b9150604084013590509250925092565b60008060408385031215610def57600080fd5b82359150610dff60208401610d8b565b90509250929050565b60008083601f840112610e1a57600080fd5b50813567ffffffffffffffff811115610e3257600080fd5b6020830191508360208083028501011115610e4c57600080fd5b9250929050565b60008060008060608587031215610e6957600080fd5b84359350602085013567ffffffffffffffff811115610e8757600080fd5b610e9387828801610e08565b9598909750949560400135949350505050565b600060208284031215610eb857600080fd5b5035919050565b60008060408385031215610ed257600080fd5b50508035926020909101359150565b60008083601f840112610ef357600080fd5b50813567ffffffffffffffff811115610f0b57600080fd5b602083019150836020828501011115610e4c57600080fd5b600080600080600080600080600080600060c08c8e031215610f4457600080fd5b8b359a5067ffffffffffffffff8060208e01351115610f6257600080fd5b610f728e60208f01358f01610e08565b909b50995060408d0135811015610f8857600080fd5b610f988e60408f01358f01610e08565b909950975060608d0135811015610fae57600080fd5b610fbe8e60608f01358f01610e08565b909750955060808d0135811015610fd457600080fd5b610fe48e60808f01358f01610e08565b909550935060a08d0135811015610ffa57600080fd5b5061100b8d60a08e01358e01610ee1565b81935080925050509295989b509295989b9093969950565b60006020828403121561103557600080fd5b61103e82610d8b565b9392505050565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600082198211156110bc576110bc61107a565b500190565b60006000198214156110d5576110d561107a565b5060010190565b6000828210156110ee576110ee61107a565b500390565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b60006020828403121561113457600080fd5b813560ff8116811461103e57600080fd5b818382376000910190815291905056fea2646970667358221220d8902bed1fa96fdb4205cc5452bfa8bfacbb82ac02ee9df473152ee33f31b44964736f6c63430008090033";

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
