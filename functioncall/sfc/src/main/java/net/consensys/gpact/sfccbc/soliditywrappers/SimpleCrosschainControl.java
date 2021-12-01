package net.consensys.gpact.sfccbc.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class SimpleCrosschainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161152b38038061152b83398101604081905261002f916100b3565b61005161004364010000000061005f810204565b640100000000610063810204565b6005919091556003556100d7565b3390565b60008054600160a060020a03838116600160a060020a0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b600080604083850312156100c657600080fd5b505080516020909101519092909150565b611445806100e66000396000f3fe608060405234801561001057600080fd5b50600436106100c6576000357c010000000000000000000000000000000000000000000000000000000090048063715018a61161008e578063715018a6146101385780638da5cb5b1461014057806392b2c3351461015b578063b28320961461016e578063f2fde38b14610181578063f51a72d81461019457600080fd5b80630a3ef1f2146100cb5780631101b8f0146100fe57806319836dc714610107578063408840521461011c578063439160df1461012f575b600080fd5b6100eb6100d9366004610c25565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100eb60035481565b61011a610115366004610c53565b6101a7565b005b61011a61012a366004610ccc565b610218565b6100eb60055481565b61011a610462565b600054604051600160a060020a0390911681526020016100f5565b61011a610169366004610d58565b61049b565b61011a61017c366004610c53565b61051c565b61011a61018f366004610db4565b61062d565b61011a6101a2366004610dd1565b6106e5565b600054600160a060020a031633146101dd5760405160e560020a62461bcd0281526004016101d490610eb6565b60405180910390fd5b600091825260026020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b61024786867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278787878761077e565b600080808080606061025b898b018b610f5d565b600086815260046020526040902054959b50939950919750955093509150156102c95760405160e560020a62461bcd02815260206004820152601a60248201527f5472616e73616374696f6e20616c72656164792065786973747300000000000060448201526064016101d4565b42851061031b5760405160e560020a62461bcd02815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e207468652066757475726560448201526064016101d4565b426003548661032a9190611037565b1161037a5760405160e560020a62461bcd02815260206004820152601060248201527f4576656e7420697320746f6f206f6c640000000000000000000000000000000060448201526064016101d4565b6000868152600460205260409020859055600554831461039957600080fd5b60006103a6828e87610951565b90506000606084600160a060020a0316836040516103c4919061107f565b6000604051808303816000865af19150503d8060008114610401576040519150601f19603f3d011682016040523d82523d6000602084013e610406565b606091505b50909250905081610451577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a661043b826109be565b60405161044891906110c7565b60405180910390a15b505050505050505050505050505050565b600054600160a060020a0316331461048f5760405160e560020a62461bcd0281526004016101d490610eb6565b6104996000610a6f565b565b600042600554868686866040516020016104ba969594939291906110da565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278142338888888860405161050d979695949392919061114a565b60405180910390a15050505050565b600054600160a060020a031633146105495760405160e560020a62461bcd0281526004016101d490610eb6565b816105995760405160e560020a62461bcd02815260206004820152601560248201527f496e76616c696420626c6f636b636861696e206964000000000000000000000060448201526064016101d4565b600160a060020a0381166105f25760405160e560020a62461bcd02815260206004820152601860248201527f496e76616c69642076657269666965722061646472657373000000000000000060448201526064016101d4565b600091825260016020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b600054600160a060020a0316331461065a5760405160e560020a62461bcd0281526004016101d490610eb6565b600160a060020a0381166106d95760405160e560020a62461bcd02815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201527f646472657373000000000000000000000000000000000000000000000000000060648201526084016101d4565b6106e281610a6f565b50565b60005b8181101561076557600083838381811061070457610704611196565b90506020020135905060006004600083815260200190815260200160002054905042600354826107349190611037565b1115610750578015610750576000828152600460205260408120555b5050808061075d906111af565b9150506106e8565b50610774888888888888610218565b5050505050505050565b600087815260016020526040902054600160a060020a03168061080c5760405160e560020a62461bcd02815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201527f636861696e00000000000000000000000000000000000000000000000000000060648201526084016101d4565b600088815260026020526040902054600160a060020a0388811691161461089e5760405160e560020a62461bcd02815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e60448201527f747261637400000000000000000000000000000000000000000000000000000060648201526084016101d4565b600088888888886040516020016108b99594939291906111ca565b60408051601f19818403018152908290527f4c1ce9020000000000000000000000000000000000000000000000000000000082529150600160a060020a03831690634c1ce90290610916908c908b9086908a908a9060040161120a565b60006040518083038186803b15801561092e57600080fd5b505afa158015610942573d6000803e3d6000fd5b50505050505050505050505050565b6060838383604051602001610988929190918252600160a060020a03166c0100000000000000000000000002602082015260340190565b60408051601f19818403018152908290526109a69291602001611248565b60405160208183030381529060405290509392505050565b60606024825110156109fa576109d48251610acc565b6040516020016109e49190611277565b6040516020818303038152906040529050919050565b81516004909201916044118015610a5457600083806020019051810190610a2191906112e2565b9050610a2c81610acc565b604051602001610a3c91906112fb565b60405160208183030381529060405292505050919050565b82806020019051810190610a689190611340565b9392505050565b60008054600160a060020a0383811673ffffffffffffffffffffffffffffffffffffffff19831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b606081610b0c57505060408051808201909152600181527f3000000000000000000000000000000000000000000000000000000000000000602082015290565b8160005b8115610b365780610b20816111af565b9150610b2f9050600a836113d0565b9150610b10565b60008167ffffffffffffffff811115610b5157610b51610eeb565b6040519080825280601f01601f191660200182016040528015610b7b576020820181803683370190505b5090505b8415610c1d57610b906001836113e4565b9150610b9d600a866113fb565b610ba8906030611037565b7f010000000000000000000000000000000000000000000000000000000000000002818381518110610bdc57610bdc611196565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350610c16600a866113d0565b9450610b7f565b949350505050565b600060208284031215610c3757600080fd5b5035919050565b600160a060020a03811681146106e257600080fd5b60008060408385031215610c6657600080fd5b823591506020830135610c7881610c3e565b809150509250929050565b60008083601f840112610c9557600080fd5b50813567ffffffffffffffff811115610cad57600080fd5b602083019150836020828501011115610cc557600080fd5b9250929050565b60008060008060008060808789031215610ce557600080fd5b863595506020870135610cf781610c3e565b9450604087013567ffffffffffffffff80821115610d1457600080fd5b610d208a838b01610c83565b90965094506060890135915080821115610d3957600080fd5b50610d4689828a01610c83565b979a9699509497509295939492505050565b60008060008060608587031215610d6e57600080fd5b843593506020850135610d8081610c3e565b9250604085013567ffffffffffffffff811115610d9c57600080fd5b610da887828801610c83565b95989497509550505050565b600060208284031215610dc657600080fd5b8135610a6881610c3e565b60008060008060008060008060a0898b031215610ded57600080fd5b883597506020890135610dff81610c3e565b9650604089013567ffffffffffffffff80821115610e1c57600080fd5b610e288c838d01610c83565b909850965060608b0135915080821115610e4157600080fd5b610e4d8c838d01610c83565b909650945060808b0135915080821115610e6657600080fd5b818b0191508b601f830112610e7a57600080fd5b813581811115610e8957600080fd5b8c60208083028501011115610e9d57600080fd5b6020830194508093505050509295985092959890939650565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b60e060020a634e487b7102600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610f2d57610f2d610eeb565b604052919050565b600067ffffffffffffffff821115610f4f57610f4f610eeb565b50601f01601f191660200190565b60008060008060008060c08789031215610f7657600080fd5b86359550602087013594506040870135610f8f81610c3e565b9350606087013592506080870135610fa681610c3e565b915060a087013567ffffffffffffffff811115610fc257600080fd5b8701601f81018913610fd357600080fd5b8035610fe6610fe182610f35565b610f04565b8181528a6020838501011115610ffb57600080fd5b816020840160208301376000602083830101528093505050509295509295509295565b60e060020a634e487b7102600052601160045260246000fd5b6000821982111561104a5761104a61101e565b500190565b60005b8381101561106a578181015183820152602001611052565b83811115611079576000848401525b50505050565b6000825161109181846020870161104f565b9190910192915050565b600081518084526110b381602086016020860161104f565b601f01601f19169290920160200192915050565b602081526000610a68602083018461109b565b8681528560208201528460408201526c01000000000000000000000000600160a060020a038516026060820152818360748301376000910160740190815295945050505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b8781528660208201526000600160a060020a03808816604084015286606084015280861660808401525060c060a083015261118960c083018486611121565b9998505050505050505050565b60e060020a634e487b7102600052603260045260246000fd5b60006000198214156111c3576111c361101e565b5060010190565b8581526c01000000000000000000000000600160a060020a0386160260208201528360348201528183605483013760009101605401908152949350505050565b858152846020820152608060408201526000611229608083018661109b565b828103606084015261123c818587611121565b98975050505050505050565b6000835161125a81846020880161104f565b83519083019061126e81836020880161104f565b01949350505050565b7f52657665727420666f7220756e6b6e6f776e206572726f722e204572726f722081527f6c656e6774683a200000000000000000000000000000000000000000000000006020820152600082516112d581602885016020870161104f565b9190910160280192915050565b6000602082840312156112f457600080fd5b5051919050565b7f50616e69633a200000000000000000000000000000000000000000000000000081526000825161133381600785016020870161104f565b9190910160070192915050565b60006020828403121561135257600080fd5b815167ffffffffffffffff81111561136957600080fd5b8201601f8101841361137a57600080fd5b8051611388610fe182610f35565b81815285602083850101111561139d57600080fd5b6113ae82602083016020860161104f565b95945050505050565b60e060020a634e487b7102600052601260045260246000fd5b6000826113df576113df6113b7565b500490565b6000828210156113f6576113f661101e565b500390565b60008261140a5761140a6113b7565b50069056fea264697066735822122083dc5ae6ea35f1e1fafb43de8fa8adf01d16a6f58d4542fd31805a2166f51b9664736f6c63430008090033";

    public static final String FUNC_ADDREMOTECROSSCHAINCONTROL = "addRemoteCrosschainControl";

    public static final String FUNC_ADDVERIFIER = "addVerifier";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSCALLHANDLER = "crossCallHandler";

    public static final String FUNC_CROSSCALLHANDLERSAVEGAS = "crossCallHandlerSaveGas";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REPLAYPREVENTION = "replayPrevention";

    public static final String FUNC_TIMEHORIZON = "timeHorizon";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event CALLFAILURE_EVENT = new Event("CallFailure", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CROSSCALL_EVENT = new Event("CrossCall", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected SimpleCrosschainControl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleCrosschainControl(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SimpleCrosschainControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SimpleCrosschainControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CallFailureEventResponse> getCallFailureEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CALLFAILURE_EVENT, transactionReceipt);
        ArrayList<CallFailureEventResponse> responses = new ArrayList<CallFailureEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CallFailureEventResponse typedResponse = new CallFailureEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._revertReason = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CallFailureEventResponse> callFailureEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CallFailureEventResponse>() {
            @Override
            public CallFailureEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CALLFAILURE_EVENT, log);
                CallFailureEventResponse typedResponse = new CallFailureEventResponse();
                typedResponse.log = log;
                typedResponse._revertReason = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CallFailureEventResponse> callFailureEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLFAILURE_EVENT));
        return callFailureEventFlowable(filter);
    }

    public List<CrossCallEventResponse> getCrossCallEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CROSSCALL_EVENT, transactionReceipt);
        ArrayList<CrossCallEventResponse> responses = new ArrayList<CrossCallEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CrossCallEventResponse typedResponse = new CrossCallEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._txId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._caller = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._destBcId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._destContract = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse._destFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrossCallEventResponse> crossCallEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrossCallEventResponse>() {
            @Override
            public CrossCallEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CROSSCALL_EVENT, log);
                CrossCallEventResponse typedResponse = new CrossCallEventResponse();
                typedResponse.log = log;
                typedResponse._txId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._caller = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._destBcId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._destContract = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse._destFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrossCallEventResponse> crossCallEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROSSCALL_EVENT));
        return crossCallEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> addRemoteCrosschainControl(BigInteger _blockchainId, String _cbc) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREMOTECROSSCHAINCONTROL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _cbc)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addVerifier(BigInteger _blockchainId, String _verifier) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDVERIFIER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _verifier)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCall(BigInteger _destBcId, String _destContract, byte[] _destData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_destBcId), 
                new org.web3j.abi.datatypes.Address(160, _destContract), 
                new org.web3j.abi.datatypes.DynamicBytes(_destData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossCallHandler(BigInteger _sourceBcId, String _cbcAddress, byte[] _eventData, byte[] _signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSCALLHANDLER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_sourceBcId), 
                new org.web3j.abi.datatypes.Address(160, _cbcAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_eventData), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossCallHandlerSaveGas(BigInteger _sourceBcId, String _cbcAddress, byte[] _eventData, byte[] _signature, List<byte[]> _oldTxIds) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSCALLHANDLERSAVEGAS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_sourceBcId), 
                new org.web3j.abi.datatypes.Address(160, _cbcAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_eventData), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_oldTxIds, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> myBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MYBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> replayPrevention(byte[] param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REPLAYPREVENTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> timeHorizon() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TIMEHORIZON, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SimpleCrosschainControl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleCrosschainControl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SimpleCrosschainControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleCrosschainControl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SimpleCrosschainControl load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SimpleCrosschainControl(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SimpleCrosschainControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SimpleCrosschainControl(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, BigInteger _timeHorizon) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeHorizon)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, BigInteger _timeHorizon) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeHorizon)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, BigInteger _timeHorizon) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeHorizon)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, BigInteger _timeHorizon) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeHorizon)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CallFailureEventResponse extends BaseEventResponse {
        public String _revertReason;
    }

    public static class CrossCallEventResponse extends BaseEventResponse {
        public byte[] _txId;

        public BigInteger _timestamp;

        public String _caller;

        public BigInteger _destBcId;

        public String _destContract;

        public byte[] _destFunctionCall;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
