package net.consensys.gpact.sfccbc.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
import org.web3j.tuples.generated.Tuple3;
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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516113b33803806113b383398101604081905261002f91610078565b600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350600555610091565b60006020828403121561008a57600080fd5b5051919050565b611313806100a06000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80638da5cb5b1161008c578063b283209611610066578063b2832096146101a3578063b4c3b756146101b6578063f2fde38b146101c8578063f51a72d8146101db57600080fd5b80638da5cb5b146101495780638eaf89d81461016457806392b2c3351461019057600080fd5b80630a3ef1f2146100d45780631101b8f01461010757806319836dc7146101105780634088405214610125578063439160df14610138578063715018a614610141575b600080fd5b6100f46100e2366004610d9f565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100f460035481565b61012361011e366004610e65565b6101ee565b005b610123610133366004610eeb565b61024f565b6100f460055481565b610123610639565b6000546040516001600160a01b0390911681526020016100fe565b6006546007546040805183815260208101939093526001600160a01b03909116908201526060016100fe565b61012361019e366004610e91565b6106ad565b6101236101b1366004610e65565b61072e565b600654604051901581526020016100fe565b6101236101d6366004610d7d565b610821565b6101236101e9366004610f75565b61090b565b6000546001600160a01b031633146102215760405162461bcd60e51b8152600401610218906111a6565b60405180910390fd5b60009182526002602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b61027e86867f876f3d825029aa36c93fca063cbf14cc3c4e231458a5b2813718e6f62d8f2388878787876109a4565b600061028c85856000610b28565b600081815260046020526040902054909150156102eb5760405162461bcd60e51b815260206004820152601a60248201527f5472616e73616374696f6e20616c7265616479206578697374730000000000006044820152606401610218565b600061032f86868080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060209250610b8f915050565b90504281106103805760405162461bcd60e51b815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e20746865206675747572656044820152606401610218565b426003548261038f9190611219565b116103cf5760405162461bcd60e51b815260206004820152601060248201526f115d995b9d081a5cc81d1bdbc81bdb1960821b6044820152606401610218565b60008281526004602090815260408083208490558051601f890183900483028101830190915287815261041f91899089908190840183828082843760009201919091525060409250610bf5915050565b9050600061046588888080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060609250610b8f915050565b9050600554811461047557600080fd5b60006104b989898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060809250610bf5915050565b905060006104ff8a8a8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060a09250610b8f915050565b905060006105478b8b8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060c09250869150610bfd9050565b60068e9055600780546001600160a01b0319166001600160a01b03888116919091179091556040519192506000916060918616906105869085906110ae565b6000604051808303816000865af19150503d80600081146105c3576040519150601f19603f3d011682016040523d82523d6000602084013e6105c8565b606091505b50909250905081610613577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a66105fd82610cb3565b60405161060a9190611193565b60405180910390a15b505060006006555050600780546001600160a01b03191690555050505050505050505050565b6000546001600160a01b031633146106635760405162461bcd60e51b8152600401610218906111a6565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b600042600554868686866040516020016106cc96959493929190611104565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278142338888888860405161071f9796959493929190611145565b60405180910390a15050505050565b6000546001600160a01b031633146107585760405162461bcd60e51b8152600401610218906111a6565b8161079d5760405162461bcd60e51b8152602060048201526015602482015274125b9d985b1a5908189b1bd8dad8da185a5b881a59605a1b6044820152606401610218565b6001600160a01b0381166107f35760405162461bcd60e51b815260206004820152601860248201527f496e76616c6964207665726966696572206164647265737300000000000000006044820152606401610218565b60009182526001602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6000546001600160a01b0316331461084b5760405162461bcd60e51b8152600401610218906111a6565b6001600160a01b0381166108b05760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b6064820152608401610218565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60005b8181101561098b57600083838381811061092a5761092a6112b1565b905060200201359050600060046000838152602001908152602001600020549050426003548261095a9190611219565b1115610976578015610976576000828152600460205260408120555b5050808061098390611280565b91505061090e565b5061099a88888888888861024f565b5050505050505050565b6000878152600160205260409020546001600160a01b031680610a175760405162461bcd60e51b815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201526431b430b4b760d91b6064820152608401610218565b6000888152600260205260409020546001600160a01b03888116911614610a8e5760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b6064820152608401610218565b60008888888888604051602001610aa99594939291906110ca565b60408051601f198184030181529082905263260e748160e11b825291506001600160a01b03831690634c1ce90290610aed908c908b9086908a908a906004016111db565b60006040518083038186803b158015610b0557600080fd5b505afa158015610b19573d6000803e3d6000fd5b50505050505050505050505050565b60008060005b6020811015610b8657610b42816008611231565b8686610b4e8488611219565b818110610b5d57610b5d6112b1565b909101356001600160f81b03191690911c92909217915080610b7e81611280565b915050610b2e565b50949350505050565b6000610b9c826020611219565b83511015610bec5760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e743235362900006044820152606401610218565b50016020015190565b016020015190565b606060008267ffffffffffffffff811115610c1a57610c1a6112c7565b6040519080825280601f01601f191660200182016040528015610c44576020820181803683370190505b50905060005b83811015610b865785610c5d8287611219565b81518110610c6d57610c6d6112b1565b602001015160f81c60f81b828281518110610c8a57610c8a6112b1565b60200101906001600160f81b031916908160001a90535080610cab81611280565b915050610c4a565b6060604482511015610cf857505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b60048201915081806020019051810190610d129190610db8565b92915050565b80356001600160a01b0381168114610d2f57600080fd5b919050565b60008083601f840112610d4657600080fd5b50813567ffffffffffffffff811115610d5e57600080fd5b602083019150836020828501011115610d7657600080fd5b9250929050565b600060208284031215610d8f57600080fd5b610d9882610d18565b9392505050565b600060208284031215610db157600080fd5b5035919050565b600060208284031215610dca57600080fd5b815167ffffffffffffffff80821115610de257600080fd5b818401915084601f830112610df657600080fd5b815181811115610e0857610e086112c7565b604051601f8201601f19908116603f01168101908382118183101715610e3057610e306112c7565b81604052828152876020848701011115610e4957600080fd5b610e5a836020830160208801611250565b979650505050505050565b60008060408385031215610e7857600080fd5b82359150610e8860208401610d18565b90509250929050565b60008060008060608587031215610ea757600080fd5b84359350610eb760208601610d18565b9250604085013567ffffffffffffffff811115610ed357600080fd5b610edf87828801610d34565b95989497509550505050565b60008060008060008060808789031215610f0457600080fd5b86359550610f1460208801610d18565b9450604087013567ffffffffffffffff80821115610f3157600080fd5b610f3d8a838b01610d34565b90965094506060890135915080821115610f5657600080fd5b50610f6389828a01610d34565b979a9699509497509295939492505050565b60008060008060008060008060a0898b031215610f9157600080fd5b88359750610fa160208a01610d18565b9650604089013567ffffffffffffffff80821115610fbe57600080fd5b610fca8c838d01610d34565b909850965060608b0135915080821115610fe357600080fd5b610fef8c838d01610d34565b909650945060808b013591508082111561100857600080fd5b818b0191508b601f83011261101c57600080fd5b81358181111561102b57600080fd5b8c60208260051b850101111561104057600080fd5b6020830194508093505050509295985092959890939650565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b6000815180845261109a816020860160208601611250565b601f01601f19169290920160200192915050565b600082516110c0818460208701611250565b9190910192915050565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b8681528560208201528460408201526bffffffffffffffffffffffff198460601b166060820152818360748301376000910160740190815295945050505050565b878152602081018790526001600160a01b038681166040830152606082018690528416608082015260c060a082018190526000906111869083018486611059565b9998505050505050505050565b602081526000610d986020830184611082565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b8581528460208201526080604082015260006111fa6080830186611082565b828103606084015261120d818587611059565b98975050505050505050565b6000821982111561122c5761122c61129b565b500190565b600081600019048311821515161561124b5761124b61129b565b500290565b60005b8381101561126b578181015183820152602001611253565b8381111561127a576000848401525b50505050565b60006000198214156112945761129461129b565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122099e954f02461cf18f61ea626da7caae23d82ec8ed7fab56a89756ac2b5df664464736f6c63430008050033";

    public static final String FUNC_ADDREMOTECROSSCHAINCONTROL = "addRemoteCrosschainControl";

    public static final String FUNC_ADDVERIFIER = "addVerifier";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSCALLHANDLER = "crossCallHandler";

    public static final String FUNC_CROSSCALLHANDLERSAVEGAS = "crossCallHandlerSaveGas";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REPLAYPREVENTION = "replayPrevention";

    public static final String FUNC_TIMEHORIZON = "timeHorizon";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WHOCALLEDME = "whoCalledMe";

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

    public RemoteFunctionCall<Boolean> isSingleBlockchainCall() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>> whoCalledMe() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WHOCALLEDME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
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

    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId)));
        return deployRemoteCall(SimpleCrosschainControl.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SimpleCrosschainControl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId)));
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
