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
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class SimpleCrosschainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161129038038061129083398101604081905261002f9161007e565b600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3506005919091556003556100a2565b6000806040838503121561009157600080fd5b505080516020909101519092909150565b6111df806100b16000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c8063715018a611610071578063715018a6146101395780638da5cb5b1461014157806392b2c3351461015c578063b28320961461016f578063f2fde38b14610182578063f51a72d81461019557600080fd5b8063057ced19146100b95780630a3ef1f2146100ce5780631101b8f01461010157806319836dc71461010a578063408840521461011d578063439160df14610130575b600080fd5b6100cc6100c7366004610af0565b6101a8565b005b6100ee6100dc366004610b45565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100ee60035481565b6100cc610118366004610c96565b610245565b6100cc61012b366004610d22565b61029d565b6100ee60055481565b6100cc6104da565b6000546040516001600160a01b0390911681526020016100f8565b6100cc61016a366004610cc6565b61054e565b6100cc61017d366004610c96565b6105cf565b6100cc610190366004610acc565b6106c2565b6100cc6101a3366004610dae565b6107ac565b60006060846001600160a01b031684846040516101c6929190610ee9565b6000604051808303816000865af19150503d8060008114610203576040519150601f19603f3d011682016040523d82523d6000602084013e610208565b606091505b5090925090508161023e5761021c81610845565b60405162461bcd60e51b8152600401610235919061100d565b60405180910390fd5b5050505050565b6000546001600160a01b0316331461026f5760405162461bcd60e51b815260040161023590611020565b60009182526002602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6102cc86867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27878787876108aa565b60008080808060606102e0898b018b610b5e565b600086815260046020526040902054959b509399509197509550935091501561034b5760405162461bcd60e51b815260206004820152601a60248201527f5472616e73616374696f6e20616c7265616479206578697374730000000000006044820152606401610235565b42851061039a5760405162461bcd60e51b815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e20746865206675747572656044820152606401610235565b42600354866103a991906110ec565b116103e95760405162461bcd60e51b815260206004820152601060248201526f115d995b9d081a5cc81d1bdbc81bdb1960821b6044820152606401610235565b6000868152600460205260409020859055600554831461040857600080fd5b600061041e828e876001600160a01b0316610a2e565b905060006060846001600160a01b03168360405161043c9190610ef9565b6000604051808303816000865af19150503d8060008114610479576040519150601f19603f3d011682016040523d82523d6000602084013e61047e565b606091505b509092509050816104c9577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a66104b382610845565b6040516104c0919061100d565b60405180910390a15b505050505050505050505050505050565b6000546001600160a01b031633146105045760405162461bcd60e51b815260040161023590611020565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000426005548686868660405160200161056d96959493929190610f7e565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27814233888888886040516105c09796959493929190610fbf565b60405180910390a15050505050565b6000546001600160a01b031633146105f95760405162461bcd60e51b815260040161023590611020565b8161063e5760405162461bcd60e51b8152602060048201526015602482015274125b9d985b1a5908189b1bd8dad8da185a5b881a59605a1b6044820152606401610235565b6001600160a01b0381166106945760405162461bcd60e51b815260206004820152601860248201527f496e76616c6964207665726966696572206164647265737300000000000000006044820152606401610235565b60009182526001602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6000546001600160a01b031633146106ec5760405162461bcd60e51b815260040161023590611020565b6001600160a01b0381166107515760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b6064820152608401610235565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60005b8181101561082c5760008383838181106107cb576107cb611165565b90506020020135905060006004600083815260200190815260200160002054905042600354826107fb91906110ec565b1115610817578015610817576000828152600460205260408120555b5050808061082490611134565b9150506107af565b5061083b88888888888861029d565b5050505050505050565b606060448251101561088a57505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b600482019150818060200190518101906108a49190610c1f565b92915050565b6000878152600160205260409020546001600160a01b03168061091d5760405162461bcd60e51b815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201526431b430b4b760d91b6064820152608401610235565b6000888152600260205260409020546001600160a01b038881169116146109945760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b6064820152608401610235565b600088888888886040516020016109af959493929190610f44565b60408051601f198184030181529082905263260e748160e11b825291506001600160a01b03831690634c1ce902906109f3908c908b9086908a908a90600401611055565b60006040518083038186803b158015610a0b57600080fd5b505afa158015610a1f573d6000803e3d6000fd5b50505050505050505050505050565b6060838383604051602001610a4d929190918252602082015260400190565b60408051601f1981840301815290829052610a6b9291602001610f15565b60405160208183030381529060405290509392505050565b60008083601f840112610a9557600080fd5b50813567ffffffffffffffff811115610aad57600080fd5b602083019150836020828501011115610ac557600080fd5b9250929050565b600060208284031215610ade57600080fd5b8135610ae981611191565b9392505050565b600080600060408486031215610b0557600080fd5b8335610b1081611191565b9250602084013567ffffffffffffffff811115610b2c57600080fd5b610b3886828701610a83565b9497909650939450505050565b600060208284031215610b5757600080fd5b5035919050565b60008060008060008060c08789031215610b7757600080fd5b86359550602087013594506040870135610b9081611191565b9350606087013592506080870135610ba781611191565b915060a087013567ffffffffffffffff811115610bc357600080fd5b8701601f81018913610bd457600080fd5b8035610be7610be2826110c4565b611093565b8181528a6020838501011115610bfc57600080fd5b816020840160208301376000602083830101528093505050509295509295509295565b600060208284031215610c3157600080fd5b815167ffffffffffffffff811115610c4857600080fd5b8201601f81018413610c5957600080fd5b8051610c67610be2826110c4565b818152856020838501011115610c7c57600080fd5b610c8d826020830160208601611104565b95945050505050565b60008060408385031215610ca957600080fd5b823591506020830135610cbb81611191565b809150509250929050565b60008060008060608587031215610cdc57600080fd5b843593506020850135610cee81611191565b9250604085013567ffffffffffffffff811115610d0a57600080fd5b610d1687828801610a83565b95989497509550505050565b60008060008060008060808789031215610d3b57600080fd5b863595506020870135610d4d81611191565b9450604087013567ffffffffffffffff80821115610d6a57600080fd5b610d768a838b01610a83565b90965094506060890135915080821115610d8f57600080fd5b50610d9c89828a01610a83565b979a9699509497509295939492505050565b60008060008060008060008060a0898b031215610dca57600080fd5b883597506020890135610ddc81611191565b9650604089013567ffffffffffffffff80821115610df957600080fd5b610e058c838d01610a83565b909850965060608b0135915080821115610e1e57600080fd5b610e2a8c838d01610a83565b909650945060808b0135915080821115610e4357600080fd5b818b0191508b601f830112610e5757600080fd5b813581811115610e6657600080fd5b8c60208260051b8501011115610e7b57600080fd5b6020830194508093505050509295985092959890939650565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b60008151808452610ed5816020860160208601611104565b601f01601f19169290920160200192915050565b8183823760009101908152919050565b60008251610f0b818460208701611104565b9190910192915050565b60008351610f27818460208801611104565b835190830190610f3b818360208801611104565b01949350505050565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b8681528560208201528460408201526bffffffffffffffffffffffff198460601b166060820152818360748301376000910160740190815295945050505050565b878152602081018790526001600160a01b038681166040830152606082018690528416608082015260c060a082018190526000906110009083018486610e94565b9998505050505050505050565b602081526000610ae96020830184610ebd565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b8581528460208201526080604082015260006110746080830186610ebd565b8281036060840152611087818587610e94565b98975050505050505050565b604051601f8201601f1916810167ffffffffffffffff811182821017156110bc576110bc61117b565b604052919050565b600067ffffffffffffffff8211156110de576110de61117b565b50601f01601f191660200190565b600082198211156110ff576110ff61114f565b500190565b60005b8381101561111f578181015183820152602001611107565b8381111561112e576000848401525b50505050565b60006000198214156111485761114861114f565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fd5b6001600160a01b03811681146111a657600080fd5b5056fea2646970667358221220518484dcc2ff8f8c967f9e0ad9bd73699114630e0424f6ced6d0449189f351e664736f6c63430008050033";

    public static final String FUNC_ADDREMOTECROSSCHAINCONTROL = "addRemoteCrosschainControl";

    public static final String FUNC_ADDVERIFIER = "addVerifier";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSCALLHANDLER = "crossCallHandler";

    public static final String FUNC_CROSSCALLHANDLERSAVEGAS = "crossCallHandlerSaveGas";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REPLAYPREVENTION = "replayPrevention";

    public static final String FUNC_START = "start";

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

    public RemoteFunctionCall<TransactionReceipt> start(String _srcContract, byte[] _functionCall) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _srcContract), 
                new org.web3j.abi.datatypes.DynamicBytes(_functionCall)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
