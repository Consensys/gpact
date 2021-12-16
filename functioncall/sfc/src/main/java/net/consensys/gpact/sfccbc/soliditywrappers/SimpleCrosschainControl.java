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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class SimpleCrosschainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516113c03803806113c083398101604081905261002f9161007e565b600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3506005919091556003556100a2565b6000806040838503121561009157600080fd5b505080516020909101519092909150565b61130f806100b16000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063715018a611610071578063715018a61461011b5780638da5cb5b1461012357806392b2c3351461013e578063b283209614610151578063f2fde38b14610164578063f51a72d81461017757600080fd5b80630a3ef1f2146100ae5780631101b8f0146100e157806319836dc7146100ea57806340884052146100ff578063439160df14610112575b600080fd5b6100ce6100bc366004610b2c565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100ce60035481565b6100fd6100f8366004610b5d565b61018a565b005b6100fd61010d366004610bd6565b6101eb565b6100ce60055481565b6100fd61041f565b6000546040516001600160a01b0390911681526020016100d8565b6100fd61014c366004610c62565b610493565b6100fd61015f366004610b5d565b610514565b6100fd610172366004610cbe565b610607565b6100fd610185366004610cdb565b6106f1565b6000546001600160a01b031633146101bd5760405162461bcd60e51b81526004016101b490610dc1565b60405180910390fd5b60009182526002602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b61021a86867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278787878761078a565b600080808080606061022e898b018b610e65565b600086815260046020526040902054959b50939950919750955093509150156102995760405162461bcd60e51b815260206004820152601a60248201527f5472616e73616374696f6e20616c72656164792065786973747300000000000060448201526064016101b4565b4285106102e85760405162461bcd60e51b815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e207468652066757475726560448201526064016101b4565b42600354866102f79190610f3c565b116103375760405162461bcd60e51b815260206004820152601060248201526f115d995b9d081a5cc81d1bdbc81bdb1960821b60448201526064016101b4565b6000868152600460205260409020859055600554831461035657600080fd5b6000610363828e8761090e565b905060006060846001600160a01b0316836040516103819190610f84565b6000604051808303816000865af19150503d80600081146103be576040519150601f19603f3d011682016040523d82523d6000602084013e6103c3565b606091505b5090925090508161040e577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a66103f882610975565b6040516104059190610fcc565b60405180910390a15b505050505050505050505050505050565b6000546001600160a01b031633146104495760405162461bcd60e51b81526004016101b490610dc1565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b600042600554868686866040516020016104b296959493929190610fdf565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27814233888888886040516105059796959493929190611049565b60405180910390a15050505050565b6000546001600160a01b0316331461053e5760405162461bcd60e51b81526004016101b490610dc1565b816105835760405162461bcd60e51b8152602060048201526015602482015274125b9d985b1a5908189b1bd8dad8da185a5b881a59605a1b60448201526064016101b4565b6001600160a01b0381166105d95760405162461bcd60e51b815260206004820152601860248201527f496e76616c69642076657269666965722061646472657373000000000000000060448201526064016101b4565b60009182526001602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6000546001600160a01b031633146106315760405162461bcd60e51b81526004016101b490610dc1565b6001600160a01b0381166106965760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016101b4565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60005b8181101561077157600083838381811061071057610710611097565b90506020020135905060006004600083815260200190815260200160002054905042600354826107409190610f3c565b111561075c57801561075c576000828152600460205260408120555b50508080610769906110ad565b9150506106f4565b506107808888888888886101eb565b5050505050505050565b6000878152600160205260409020546001600160a01b0316806107fd5760405162461bcd60e51b815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201526431b430b4b760d91b60648201526084016101b4565b6000888152600260205260409020546001600160a01b038881169116146108745760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b60648201526084016101b4565b6000888888888860405160200161088f9594939291906110c8565b60408051601f198184030181529082905263260e748160e11b825291506001600160a01b03831690634c1ce902906108d3908c908b9086908a908a90600401611102565b60006040518083038186803b1580156108eb57600080fd5b505afa1580156108ff573d6000803e3d6000fd5b50505050505050505050505050565b606083838360405160200161093f92919091825260601b6bffffffffffffffffffffffff1916602082015260340190565b60408051601f198184030181529082905261095d9291602001611140565b60405160208183030381529060405290509392505050565b60606024825110156109b15761098b8251610a26565b60405160200161099b919061116f565b6040516020818303038152906040529050919050565b81516004909201916044118015610a0b576000838060200190518101906109d891906111c5565b90506109e381610a26565b6040516020016109f391906111de565b60405160208183030381529060405292505050919050565b82806020019051810190610a1f919061120d565b9392505050565b606081610a4a5750506040805180820190915260018152600360fc1b602082015290565b8160005b8115610a745780610a5e816110ad565b9150610a6d9050600a8361129a565b9150610a4e565b60008167ffffffffffffffff811115610a8f57610a8f610df6565b6040519080825280601f01601f191660200182016040528015610ab9576020820181803683370190505b5090505b8415610b2457610ace6001836112ae565b9150610adb600a866112c5565b610ae6906030610f3c565b60f81b818381518110610afb57610afb611097565b60200101906001600160f81b031916908160001a905350610b1d600a8661129a565b9450610abd565b949350505050565b600060208284031215610b3e57600080fd5b5035919050565b6001600160a01b0381168114610b5a57600080fd5b50565b60008060408385031215610b7057600080fd5b823591506020830135610b8281610b45565b809150509250929050565b60008083601f840112610b9f57600080fd5b50813567ffffffffffffffff811115610bb757600080fd5b602083019150836020828501011115610bcf57600080fd5b9250929050565b60008060008060008060808789031215610bef57600080fd5b863595506020870135610c0181610b45565b9450604087013567ffffffffffffffff80821115610c1e57600080fd5b610c2a8a838b01610b8d565b90965094506060890135915080821115610c4357600080fd5b50610c5089828a01610b8d565b979a9699509497509295939492505050565b60008060008060608587031215610c7857600080fd5b843593506020850135610c8a81610b45565b9250604085013567ffffffffffffffff811115610ca657600080fd5b610cb287828801610b8d565b95989497509550505050565b600060208284031215610cd057600080fd5b8135610a1f81610b45565b60008060008060008060008060a0898b031215610cf757600080fd5b883597506020890135610d0981610b45565b9650604089013567ffffffffffffffff80821115610d2657600080fd5b610d328c838d01610b8d565b909850965060608b0135915080821115610d4b57600080fd5b610d578c838d01610b8d565b909650945060808b0135915080821115610d7057600080fd5b818b0191508b601f830112610d8457600080fd5b813581811115610d9357600080fd5b8c60208260051b8501011115610da857600080fd5b6020830194508093505050509295985092959890939650565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610e3557610e35610df6565b604052919050565b600067ffffffffffffffff821115610e5757610e57610df6565b50601f01601f191660200190565b60008060008060008060c08789031215610e7e57600080fd5b86359550602087013594506040870135610e9781610b45565b9350606087013592506080870135610eae81610b45565b915060a087013567ffffffffffffffff811115610eca57600080fd5b8701601f81018913610edb57600080fd5b8035610eee610ee982610e3d565b610e0c565b8181528a6020838501011115610f0357600080fd5b816020840160208301376000602083830101528093505050509295509295509295565b634e487b7160e01b600052601160045260246000fd5b60008219821115610f4f57610f4f610f26565b500190565b60005b83811015610f6f578181015183820152602001610f57565b83811115610f7e576000848401525b50505050565b60008251610f96818460208701610f54565b9190910192915050565b60008151808452610fb8816020860160208601610f54565b601f01601f19169290920160200192915050565b602081526000610a1f6020830184610fa0565b8681528560208201528460408201526bffffffffffffffffffffffff198460601b166060820152818360748301376000910160740190815295945050505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b878152602081018790526001600160a01b038681166040830152606082018690528416608082015260c060a0820181905260009061108a9083018486611020565b9998505050505050505050565b634e487b7160e01b600052603260045260246000fd5b60006000198214156110c1576110c1610f26565b5060010190565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b8581528460208201526080604082015260006111216080830186610fa0565b8281036060840152611134818587611020565b98975050505050505050565b60008351611152818460208801610f54565b835190830190611166818360208801610f54565b01949350505050565b7f52657665727420666f7220756e6b6e6f776e206572726f722e204572726f722081526703632b733ba341d160c51b6020820152600082516111b8816028850160208701610f54565b9190910160280192915050565b6000602082840312156111d757600080fd5b5051919050565b6602830b734b19d160cd1b815260008251611200816007850160208701610f54565b9190910160070192915050565b60006020828403121561121f57600080fd5b815167ffffffffffffffff81111561123657600080fd5b8201601f8101841361124757600080fd5b8051611255610ee982610e3d565b81815285602083850101111561126a57600080fd5b61127b826020830160208601610f54565b95945050505050565b634e487b7160e01b600052601260045260246000fd5b6000826112a9576112a9611284565b500490565b6000828210156112c0576112c0610f26565b500390565b6000826112d4576112d4611284565b50069056fea2646970667358221220abb829896f4fb38e8171fec15d432d8cd81ade6932ccac37f675e221a49d27ae64736f6c63430008090033";

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
