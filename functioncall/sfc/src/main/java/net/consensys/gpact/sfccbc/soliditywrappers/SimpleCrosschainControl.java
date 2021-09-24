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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516114d83803806114d883398101604081905261002f91610078565b600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350600555610091565b60006020828403121561008a57600080fd5b5051919050565b611438806100a06000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c80638da5cb5b1161008c578063b283209611610066578063b2832096146101d1578063b4c3b756146101e4578063f2fde38b146101f6578063f51a72d81461020957600080fd5b80638da5cb5b146101775780638eaf89d81461019257806392b2c335146101be57600080fd5b806319836dc7116100c857806319836dc7146101405780634088405214610153578063439160df14610166578063715018a61461016f57600080fd5b8063057ced19146100ef5780630a3ef1f2146101045780631101b8f014610137575b600080fd5b6101026100fd366004610e61565b61021c565b005b610124610112366004610eb4565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b61012460035481565b61010261014e366004610f7a565b6102b9565b610102610161366004611000565b610311565b61012460055481565b6101026106fb565b6000546040516001600160a01b03909116815260200161012e565b6006546007546040805183815260208101939093526001600160a01b039091169082015260600161012e565b6101026101cc366004610fa6565b61076f565b6101026101df366004610f7a565b6107f0565b6006546040519015815260200161012e565b610102610204366004610e3f565b6108e3565b61010261021736600461108a565b6109cd565b60006060846001600160a01b0316848460405161023a9291906111c3565b6000604051808303816000865af19150503d8060008114610277576040519150601f19603f3d011682016040523d82523d6000602084013e61027c565b606091505b509092509050816102b25761029081610a66565b60405162461bcd60e51b81526004016102a991906112b8565b60405180910390fd5b5050505050565b6000546001600160a01b031633146102e35760405162461bcd60e51b81526004016102a9906112cb565b60009182526002602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b61034086867f876f3d825029aa36c93fca063cbf14cc3c4e231458a5b2813718e6f62d8f238887878787610acb565b600061034e85856000610c4f565b600081815260046020526040902054909150156103ad5760405162461bcd60e51b815260206004820152601a60248201527f5472616e73616374696f6e20616c72656164792065786973747300000000000060448201526064016102a9565b60006103f186868080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060209250610cb6915050565b90504281106104425760405162461bcd60e51b815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e207468652066757475726560448201526064016102a9565b4260035482610451919061133e565b116104915760405162461bcd60e51b815260206004820152601060248201526f115d995b9d081a5cc81d1bdbc81bdb1960821b60448201526064016102a9565b60008281526004602090815260408083208490558051601f89018390048302810183019091528781526104e191899089908190840183828082843760009201919091525060409250610d1c915050565b9050600061052788888080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060609250610cb6915050565b9050600554811461053757600080fd5b600061057b89898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060809250610d1c915050565b905060006105c18a8a8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060a09250610cb6915050565b905060006106098b8b8080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525060c09250869150610d249050565b60068e9055600780546001600160a01b0319166001600160a01b03888116919091179091556040519192506000916060918616906106489085906111d3565b6000604051808303816000865af19150503d8060008114610685576040519150601f19603f3d011682016040523d82523d6000602084013e61068a565b606091505b509092509050816106d5577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a66106bf82610a66565b6040516106cc91906112b8565b60405180910390a15b505060006006555050600780546001600160a01b03191690555050505050505050505050565b6000546001600160a01b031633146107255760405162461bcd60e51b81526004016102a9906112cb565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000426005548686868660405160200161078e96959493929190611229565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27814233888888886040516107e1979695949392919061126a565b60405180910390a15050505050565b6000546001600160a01b0316331461081a5760405162461bcd60e51b81526004016102a9906112cb565b8161085f5760405162461bcd60e51b8152602060048201526015602482015274125b9d985b1a5908189b1bd8dad8da185a5b881a59605a1b60448201526064016102a9565b6001600160a01b0381166108b55760405162461bcd60e51b815260206004820152601860248201527f496e76616c69642076657269666965722061646472657373000000000000000060448201526064016102a9565b60009182526001602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6000546001600160a01b0316331461090d5760405162461bcd60e51b81526004016102a9906112cb565b6001600160a01b0381166109725760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016102a9565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60005b81811015610a4d5760008383838181106109ec576109ec6113d6565b9050602002013590506000600460008381526020019081526020016000205490504260035482610a1c919061133e565b1115610a38578015610a38576000828152600460205260408120555b50508080610a45906113a5565b9150506109d0565b50610a5c888888888888610311565b5050505050505050565b6060604482511015610aab57505060408051808201909152601d81527f5472616e73616374696f6e2072657665727465642073696c656e746c79000000602082015290565b60048201915081806020019051810190610ac59190610ecd565b92915050565b6000878152600160205260409020546001600160a01b031680610b3e5760405162461bcd60e51b815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201526431b430b4b760d91b60648201526084016102a9565b6000888152600260205260409020546001600160a01b03888116911614610bb55760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b60648201526084016102a9565b60008888888888604051602001610bd09594939291906111ef565b60408051601f198184030181529082905263260e748160e11b825291506001600160a01b03831690634c1ce90290610c14908c908b9086908a908a90600401611300565b60006040518083038186803b158015610c2c57600080fd5b505afa158015610c40573d6000803e3d6000fd5b50505050505050505050505050565b60008060005b6020811015610cad57610c69816008611356565b8686610c75848861133e565b818110610c8457610c846113d6565b909101356001600160f81b03191690911c92909217915080610ca5816113a5565b915050610c55565b50949350505050565b6000610cc382602061133e565b83511015610d135760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e7432353629000060448201526064016102a9565b50016020015190565b016020015190565b606060008267ffffffffffffffff811115610d4157610d416113ec565b6040519080825280601f01601f191660200182016040528015610d6b576020820181803683370190505b50905060005b83811015610cad5785610d84828761133e565b81518110610d9457610d946113d6565b602001015160f81c60f81b828281518110610db157610db16113d6565b60200101906001600160f81b031916908160001a90535080610dd2816113a5565b915050610d71565b80356001600160a01b0381168114610df157600080fd5b919050565b60008083601f840112610e0857600080fd5b50813567ffffffffffffffff811115610e2057600080fd5b602083019150836020828501011115610e3857600080fd5b9250929050565b600060208284031215610e5157600080fd5b610e5a82610dda565b9392505050565b600080600060408486031215610e7657600080fd5b610e7f84610dda565b9250602084013567ffffffffffffffff811115610e9b57600080fd5b610ea786828701610df6565b9497909650939450505050565b600060208284031215610ec657600080fd5b5035919050565b600060208284031215610edf57600080fd5b815167ffffffffffffffff80821115610ef757600080fd5b818401915084601f830112610f0b57600080fd5b815181811115610f1d57610f1d6113ec565b604051601f8201601f19908116603f01168101908382118183101715610f4557610f456113ec565b81604052828152876020848701011115610f5e57600080fd5b610f6f836020830160208801611375565b979650505050505050565b60008060408385031215610f8d57600080fd5b82359150610f9d60208401610dda565b90509250929050565b60008060008060608587031215610fbc57600080fd5b84359350610fcc60208601610dda565b9250604085013567ffffffffffffffff811115610fe857600080fd5b610ff487828801610df6565b95989497509550505050565b6000806000806000806080878903121561101957600080fd5b8635955061102960208801610dda565b9450604087013567ffffffffffffffff8082111561104657600080fd5b6110528a838b01610df6565b9096509450606089013591508082111561106b57600080fd5b5061107889828a01610df6565b979a9699509497509295939492505050565b60008060008060008060008060a0898b0312156110a657600080fd5b883597506110b660208a01610dda565b9650604089013567ffffffffffffffff808211156110d357600080fd5b6110df8c838d01610df6565b909850965060608b01359150808211156110f857600080fd5b6111048c838d01610df6565b909650945060808b013591508082111561111d57600080fd5b818b0191508b601f83011261113157600080fd5b81358181111561114057600080fd5b8c60208260051b850101111561115557600080fd5b6020830194508093505050509295985092959890939650565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b600081518084526111af816020860160208601611375565b601f01601f19169290920160200192915050565b8183823760009101908152919050565b600082516111e5818460208701611375565b9190910192915050565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b8681528560208201528460408201526bffffffffffffffffffffffff198460601b166060820152818360748301376000910160740190815295945050505050565b878152602081018790526001600160a01b038681166040830152606082018690528416608082015260c060a082018190526000906112ab908301848661116e565b9998505050505050505050565b602081526000610e5a6020830184611197565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b85815284602082015260806040820152600061131f6080830186611197565b828103606084015261133281858761116e565b98975050505050505050565b60008219821115611351576113516113c0565b500190565b6000816000190483118215151615611370576113706113c0565b500290565b60005b83811015611390578181015183820152602001611378565b8381111561139f576000848401525b50505050565b60006000198214156113b9576113b96113c0565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea26469706673582212207accb0c1d06f4e46b81d8e00c2da6c3c4cfb4fd0e75f09ebb912797f59cff41e64736f6c63430008050033";

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

    public static final String FUNC_START = "start";

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
