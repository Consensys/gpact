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
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350611173806100616000396000f3fe608060405234801561001057600080fd5b50600436106100ce5760003560e01c8063a64ce1991161008c578063de789ee311610066578063de789ee3146101d7578063ea13ec8b146101ea578063f2fde38b146101fd578063f5e232ea1461021057600080fd5b8063a64ce19914610183578063b9c36209146101b1578063d4c0d34d146101c457600080fd5b8062ab2414146100d357806315a09825146100e85780633156d37c146100fb57806348bcbd2d1461010e578063715018a6146101605780638da5cb5b14610168575b600080fd5b6100e66100e1366004610e7e565b610234565b005b6100e66100f6366004610e52565b6102e0565b6100e6610109366004610e52565b610365565b61014b61011c366004610e52565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff1692915050565b60405190151581526020015b60405180910390f35b6100e661047b565b6000546040516001600160a01b039091168152602001610157565b6101a3610191366004610e39565b60009081526001602052604090205490565b604051908152602001610157565b6100e66101bf366004610fb3565b6104ef565b6100e66101d2366004610e7e565b610569565b6100e66101e5366004610fd5565b6105e5565b61014b6101f8366004610eb3565b61072d565b6100e661020b366004610e17565b610a0c565b6101a361021e366004610e39565b6000908152600160208190526040909120015490565b6000546001600160a01b031633146102675760405162461bcd60e51b815260040161025e9061105b565b60405180910390fd5b6000838152600160205260409020546102925760405162461bcd60e51b815260040161025e90611090565b61029c8383610af6565b600083815260016020819052604082208101546102b8916110c7565b60008581526001602081905260409091200181905590506102da848284610b98565b50505050565b6000546001600160a01b0316331461030a5760405162461bcd60e51b815260040161025e9061105b565b6000828152600160205260409020546103355760405162461bcd60e51b815260040161025e90611090565b61033f8282610af6565b600082815260016020819052604082200180549161035c836110f6565b91905055505050565b6000546001600160a01b0316331461038f5760405162461bcd60e51b815260040161025e9061105b565b6000828152600160205260409020546103ba5760405162461bcd60e51b815260040161025e90611090565b6103c48282610c0c565b600082815260016020819052604082208101546103e191906110df565b6000848152600160205260409020549091508110156104605760405162461bcd60e51b815260206004820152603560248201527f50726f706f736564206e6577206e756d626572206f66207369676e65727320696044820152741cc81b195cdcc81d1a185b881d1a1c995cda1bdb19605a1b606482015260840161025e565b60009283526001602081905260409093209092019190915550565b6000546001600160a01b031633146104a55760405162461bcd60e51b815260040161025e9061105b565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000546001600160a01b031633146105195760405162461bcd60e51b815260040161025e9061105b565b6000828152600160205260409020546105445760405162461bcd60e51b815260040161025e90611090565b61056582600160008581526020019081526020016000206001015483610b98565b5050565b6000546001600160a01b031633146105935760405162461bcd60e51b815260040161025e9061105b565b6000838152600160205260409020546105be5760405162461bcd60e51b815260040161025e90611090565b6105c88383610c0c565b600083815260016020819052604082208101546102b891906110df565b6000546001600160a01b0316331461060f5760405162461bcd60e51b815260040161025e9061105b565b6000848152600160205260409020541561066b5760405162461bcd60e51b815260206004820152601960248201527f426c6f636b636861696e20616c72656164792065786973747300000000000000604482015260640161025e565b826106c25760405162461bcd60e51b815260206004820152602160248201527f5369676e696e67207468726573686f6c642063616e206e6f74206265207a65726044820152606f60f81b606482015260840161025e565b60008481526001602081905260408220018290555b818110156107215761070f858484848181106106f5576106f5611127565b905060200201602081019061070a9190610e17565b610af6565b80610719816110f6565b9150506106d7565b506102da848285610b98565b6000898881146107765760405162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b8087146107bc5760405162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b8085146108025760405162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b604482015260640161025e565b60008d8152600160205260409020548110156108555760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b604482015260640161025e565b60005b818110156109f85760008e8152600160205260408120600201908e8e8481811061088457610884611127565b90506020020160208101906108999190610e17565b6001600160a01b0316815260208101919091526040016000205460ff166109105760405162461bcd60e51b815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201526431b430b4b760d91b606482015260840161025e565b61099a8d8d8381811061092557610925611127565b905060200201602081019061093a9190610e17565b86868e8e8681811061094e5761094e611127565b905060200201358d8d8781811061096757610967611127565b905060200201358c8c8881811061098057610980611127565b90506020020160208101906109959190611028565b610caa565b6109e65760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f74207665726966790000000000000000604482015260640161025e565b806109f0816110f6565b915050610858565b5060019d9c50505050505050505050505050565b6000546001600160a01b03163314610a365760405162461bcd60e51b815260040161025e9061105b565b6001600160a01b038116610a9b5760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b606482015260840161025e565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff1615610b645760405162461bcd60e51b81526020600482015260156024820152745369676e657220616c72656164792065786973747360581b604482015260640161025e565b60009182526001602081815260408085206001600160a01b039094168552600290930190529120805460ff19169091179055565b80821015610bf65760405162461bcd60e51b815260206004820152602560248201527f4e756d626572206f66207369676e657273206c657373207468616e20746872656044820152641cda1bdb1960da1b606482015260840161025e565b6000928352600160205260409092209190915550565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff16610c795760405162461bcd60e51b815260206004820152601560248201527414da59db995c88191bd95cc81b9bdd08195e1a5cdd605a1b604482015260640161025e565b60009182526001602090815260408084206001600160a01b039093168452600290920190529020805460ff19169055565b6000808686604051610cbd92919061104b565b604051809103902090508260ff16601b14158015610cdf57508260ff16601c14155b15610cee576000915050610d63565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa158015610d41573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b80356001600160a01b0381168114610d8457600080fd5b919050565b60008083601f840112610d9b57600080fd5b50813567ffffffffffffffff811115610db357600080fd5b6020830191508360208260051b8501011115610dce57600080fd5b9250929050565b60008083601f840112610de757600080fd5b50813567ffffffffffffffff811115610dff57600080fd5b602083019150836020828501011115610dce57600080fd5b600060208284031215610e2957600080fd5b610e3282610d6d565b9392505050565b600060208284031215610e4b57600080fd5b5035919050565b60008060408385031215610e6557600080fd5b82359150610e7560208401610d6d565b90509250929050565b600080600060608486031215610e9357600080fd5b83359250610ea360208501610d6d565b9150604084013590509250925092565b600080600080600080600080600080600060c08c8e031215610ed457600080fd5b8b359a5067ffffffffffffffff8060208e01351115610ef257600080fd5b610f028e60208f01358f01610d89565b909b50995060408d0135811015610f1857600080fd5b610f288e60408f01358f01610d89565b909950975060608d0135811015610f3e57600080fd5b610f4e8e60608f01358f01610d89565b909750955060808d0135811015610f6457600080fd5b610f748e60808f01358f01610d89565b909550935060a08d0135811015610f8a57600080fd5b50610f9b8d60a08e01358e01610dd5565b81935080925050509295989b509295989b9093969950565b60008060408385031215610fc657600080fd5b50508035926020909101359150565b60008060008060608587031215610feb57600080fd5b8435935060208501359250604085013567ffffffffffffffff81111561101057600080fd5b61101c87828801610d89565b95989497509550505050565b60006020828403121561103a57600080fd5b813560ff81168114610e3257600080fd5b8183823760009101908152919050565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b60208082526019908201527f426c6f636b636861696e20646f6573206e6f7420657869737400000000000000604082015260600190565b600082198211156110da576110da611111565b500190565b6000828210156110f1576110f1611111565b500390565b600060001982141561110a5761110a611111565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fdfea264697066735822122027a2f84fc03d39b8efe82ff5bdb2a43ba25d5d52c9109661df845b6c642c8f5064736f6c63430008050033";

    public static final String FUNC_ADDBLOCKCHAIN = "addBlockchain";

    public static final String FUNC_ADDSIGNER = "addSigner";

    public static final String FUNC_ADDSIGNERSETTHRESHOLD = "addSignerSetThreshold";

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

    public RemoteFunctionCall<TransactionReceipt> addBlockchain(BigInteger _bcId, BigInteger _signingThreshold, List<String> _signers) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
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
