package net.consensys.gpact.registrar.soliditywrappers;

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
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
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
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b507f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c5805460ff199081166001908117909255600580548084019091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319163390811790915560009081526004602090815260408220849055600380546001600160401b03191685179055631c6e4c1760e31b8252527f17c19a682e8fe984fd595b0bb0646415bdafce0ac6ac60b1a04898730b1f78c3805490911690911790556118c4806100eb6000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806360ce281211610097578063cb103ef511610066578063cb103ef5146102de578063ea13ec8b146102f1578063f5e232ea14610304578063fc29175e1461033857600080fd5b806360ce281214610274578063a130e04514610287578063a64ce199146102a8578063aa6dc926146102d657600080fd5b806324d7806c116100d357806324d7806c146101b45780632ef6c66d146101df57806348bcbd2d14610227578063581cb2dc1461026357600080fd5b806301ffc9a71461010557806307c099da146101475780630a3b7f3b1461018c578063111fd88b146101a1575b600080fd5b610132610113366004611513565b6001600160e01b03191660009081526020819052604090205460ff1690565b60405190151581526020015b60405180910390f35b6101746101553660046115b6565b600090815260016020819052604090912001546001600160a01b031690565b6040516001600160a01b03909116815260200161013e565b61019f61019a36600461157d565b61034b565b005b6101746101af3660046115b6565b61065b565b6101326101c23660046114d4565b6001600160a01b0316600090815260046020526040902054151590565b61020f6101ed3660046115b6565b600090815260016020526040902054600160481b90046001600160401b031690565b6040516001600160401b03909116815260200161013e565b6101326102353660046115cf565b60008281526001602090815260408083206001600160a01b0385168452600201909152902054151592915050565b6003546001600160401b031661020f565b6101326102823660046115cf565b61068b565b61029a6102953660046115b6565b610713565b60405190815260200161013e565b61020f6102b63660046115b6565b60009081526001602052604090205461010090046001600160401b031690565b60055461029a565b61019f6102ec3660046115b6565b61073d565b6101326102ff3660046115fb565b6108b6565b61020f6103123660046115b6565b60009081526001602081905260409091200154600160a01b90046001600160401b031690565b61019f61034636600461153d565b610ba0565b3360009081526004602052604090205461036457600080fd5b60008461ffff16600881111561037c5761037c611854565b90506000808581526006602052604090205460ff1660088111156103a2576103a2611854565b146103ac57600080fd5b838360018360088111156103c2576103c2611854565b14156103f0576001600160a01b038216600090815260046020526040902054156103eb57600080fd5b6105b6565b600283600881111561040457610404611854565b1415610442576001600160a01b03821660009081526004602052604090205461042c57600080fd5b6001600160a01b0382163314156103eb57600080fd5b600483600881111561045657610456611854565b14156104bd5760008681526001602052604090205461010090046001600160401b03161561048357600080fd5b600084600181111561049757610497611854565b905060018160018111156104ad576104ad611854565b146104b757600080fd5b506105b6565b60058360088111156104d1576104d1611854565b14156104fd5760008681526001602052604090205461010090046001600160401b03166103eb57600080fd5b600683600881111561051157610511611854565b141561056d5760008681526001602052604090205461010090046001600160401b031661053d57600080fd5b60008681526001602090815260408083206001600160a01b0385168452600201909152902054156103eb57600080fd5b600783600881111561058157610581611854565b14156105b65760008681526001602090815260408083206001600160a01b03851684526002019091529020546105b657600080fd5b6000868152600660205260409020805484919060ff191660018360088111156105e1576105e1611854565b021790555060025461060390600160a01b90046001600160401b0316436117aa565b60008781526006602052604090206003810191909155600181018690556002908101859055546001600160a01b031661064657610641866001610c65565b610652565b61065287876001611238565b50505050505050565b6000600582815481106106705761067061186a565b6000918252602090912001546001600160a01b031692915050565b6000828152600160208190526040822001546001600160a01b0383811691161461070a5760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b60648201526084015b60405180910390fd5b50600192915050565b600081815260016020819052604082205460ff169081111561073757610737611854565b92915050565b3360009081526004602052604090205461075657600080fd5b60008181526006602052604081205460ff169081600881111561077b5761077b611854565b141561078657600080fd5b60008281526006602052604090206003015443116107a357600080fd5b600254600354600084815260066020819052604080832090516319ab36c160e01b81526001600160a01b0390951694929385936319ab36c1936107fc936001600160401b03909316926005810192910190600401611776565b60206040518083038186803b15801561081457600080fd5b505afa158015610828573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061084c91906114f6565b90507ff36941b213653867ab41db7916ff3975cd4d6cfe42f73e61aeda6257d1eab74b83600881111561088157610881611854565b6040805161ffff9092168252602082018790528315159082015260600160405180910390a16108b08482610c65565b50505050565b6000898881146108ff5760405162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b6044820152606401610701565b8087146109455760405162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b6044820152606401610701565b80851461098b5760405162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b6044820152606401610701565b60008d81526001602052604090205461010090046001600160401b03168110156109ec5760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b6044820152606401610701565b60005b81811015610b8c5760008e8152600160205260408120600201908e8e84818110610a1b57610a1b61186a565b9050602002016020810190610a3091906114d4565b6001600160a01b03168152602081019190915260400160002054610aa45760405162461bcd60e51b815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201526431b430b4b760d91b6064820152608401610701565b610b2e8d8d83818110610ab957610ab961186a565b9050602002016020810190610ace91906114d4565b86868e8e86818110610ae257610ae261186a565b905060200201358d8d87818110610afb57610afb61186a565b905060200201358c8c88818110610b1457610b1461186a565b9050602002016020810190610b2991906116fa565b61131d565b610b7a5760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f742076657269667900000000000000006044820152606401610701565b80610b84816117fc565b9150506109ef565b5060019d9c50505050505050505050505050565b33600090815260046020526040902054610bb957600080fd5b60008361ffff166008811115610bd157610bd1611854565b9050806008811115610be557610be5611854565b60008481526006602052604090205460ff166008811115610c0857610c08611854565b14610c1257600080fd5b600083815260066020908152604080832033845260040190915290205460ff1615610c3c57600080fd5b600083815260066020526040902060030154431115610c5a57600080fd5b6108b0848484611238565b801561110a576000828152600660205260409020805460018083015460029093015460ff90921692919085908390856008811115610ca557610ca5611854565b1415610d4557600580546001810182557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319166001600160a01b0385169081179091559054600091825260046020526040822055600380546001600160401b031691610d1b83611817565b91906101000a8154816001600160401b0302191690836001600160401b0316021790555050611104565b6002856008811115610d5957610d59611854565b1415610e09576001600160a01b0382166000908152600460205260409020546005610d856001836117c2565b81548110610d9557610d9561186a565b6000918252602080832090910180546001600160a01b03191690556001600160a01b03851682526004905260408120819055600380546001600160401b031691610dde836117d9565b91906101000a8154816001600160401b0302191690836001600160401b031602179055505050611104565b6003856008811115610e1d57610e1d611854565b1415610e5657600280546001600160401b038616600160a01b026001600160e01b03199091166001600160a01b03851617179055611104565b6004856008811115610e6a57610e6a611854565b1415610f06576000878152600160208190526040909120810180546001600160a01b0319166001600160a01b0384161790558390811115610ead57610ead611854565b60008881526001602081905260409091208054909160ff19909116908381811115610eda57610eda611854565b02179055506000878152600160205260409020805468ffffffffffffffff001916610100179055611104565b6005856008811115610f1a57610f1a611854565b1415610f52576000878152600160205260409020805468ffffffffffffffff0019166101006001600160401b03871602179055611104565b6006856008811115610f6657610f66611854565b1415610fe357600087815260016020818152604080842060038101805480860182558187528487200180546001600160a01b0319166001600160a01b038916908117909155905490865260028201845291852091909155928a9052819052018054600160a01b90046001600160401b0316906014610d1b83611817565b6007856008811115610ff757610ff7611854565b14156110b25760008781526001602081815260408084206001600160a01b038616855260028101835290842054938b9052908290526003019061103a90836117c2565b8154811061104a5761104a61186a565b6000918252602080832090910180546001600160a01b0319169055898252600180825260408084206001600160a01b0387168552600281018452908420849055928b9052908190520180546001600160401b03600160a01b90910416906014610dde836117d9565b60088560088111156110c6576110c6611854565b1415611104576000878152600160205260409020805470ffffffffffffffff0000000000000000001916600160481b6001600160401b038716021790555b50505050505b60005b6005548110156111f05760006001600160a01b0316600582815481106111355761113561186a565b6000918252602090912001546001600160a01b0316146111de576006600084815260200190815260200160002060040160006005838154811061117a5761117a61186a565b60009182526020808320909101546001600160a01b031683528281019390935260409182018120805460ff19169055858152600690925281206111c2916005909101906113e0565b600083815260066020819052604082206111de929101906113e0565b806111e8816117fc565b91505061110d565b506000828152600660205260408120805460ff191681556001810182905560028101829055600381018290559061122a60058301826113e0565b6108b06006830160006113e0565b6040805133815261ffff8516602082015290810183905281151560608201527f2cf518b4f57f241b79cee0c80f2d18fb40e9a7044c6a5fcad55cb1c74b95bbb49060800160405180910390a160008281526006602090815260408083203384526004019091529020805460ff1916600117905580156112e75760008281526006602090815260408220600501805460018101825590835291200180546001600160a01b03191633179055505050565b60008281526006602081815260408320909101805460018101825590835291200180546001600160a01b03191633179055505050565b6000808686604051611330929190611766565b604051809103902090508260ff16601b1415801561135257508260ff16601c14155b156113615760009150506113d6565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa1580156113b4573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b50805460008255906000526020600020908101906113fe9190611401565b50565b5b808211156114165760008155600101611402565b5090565b80356001600160a01b038116811461143157600080fd5b919050565b60008083601f84011261144857600080fd5b5081356001600160401b0381111561145f57600080fd5b6020830191508360208260051b850101111561147a57600080fd5b9250929050565b60008083601f84011261149357600080fd5b5081356001600160401b038111156114aa57600080fd5b60208301915083602082850101111561147a57600080fd5b803561ffff8116811461143157600080fd5b6000602082840312156114e657600080fd5b6114ef8261141a565b9392505050565b60006020828403121561150857600080fd5b81516114ef81611880565b60006020828403121561152557600080fd5b81356001600160e01b0319811681146114ef57600080fd5b60008060006060848603121561155257600080fd5b61155b846114c2565b925060208401359150604084013561157281611880565b809150509250925092565b6000806000806080858703121561159357600080fd5b61159c856114c2565b966020860135965060408601359560600135945092505050565b6000602082840312156115c857600080fd5b5035919050565b600080604083850312156115e257600080fd5b823591506115f26020840161141a565b90509250929050565b600080600080600080600080600080600060c08c8e03121561161c57600080fd5b8b359a506001600160401b038060208e0135111561163957600080fd5b6116498e60208f01358f01611436565b909b50995060408d013581101561165f57600080fd5b61166f8e60408f01358f01611436565b909950975060608d013581101561168557600080fd5b6116958e60608f01358f01611436565b909750955060808d01358110156116ab57600080fd5b6116bb8e60808f01358f01611436565b909550935060a08d01358110156116d157600080fd5b506116e28d60a08e01358e01611481565b81935080925050509295989b509295989b9093969950565b60006020828403121561170c57600080fd5b813560ff811681146114ef57600080fd5b6000815480845260208085019450836000528060002060005b8381101561175b5781546001600160a01b031687529582019560019182019101611736565b509495945050505050565b8183823760009101908152919050565b6001600160401b0384168152606060208201526000611798606083018561171d565b82810360408401526113d6818561171d565b600082198211156117bd576117bd61183e565b500190565b6000828210156117d4576117d461183e565b500390565b60006001600160401b038216806117f2576117f261183e565b6000190192915050565b60006000198214156118105761181061183e565b5060010190565b60006001600160401b03808316818114156118345761183461183e565b6001019392505050565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052602160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b80151581146113fe57600080fdfea264697066735822122028b49894b38bd0535770e48edecbb221217ae87d5c0985cfd6fac56484707a8564736f6c63430008050033";

    public static final String FUNC_ACTIONVOTES = "actionVotes";

    public static final String FUNC_ADMINARRAYSIZE = "adminArraySize";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETAPPROVEDCONTRACT = "getApprovedContract";

    public static final String FUNC_GETCHAINFINALITY = "getChainFinality";

    public static final String FUNC_GETNUMADMINS = "getNumAdmins";

    public static final String FUNC_GETSIGALGORITHM = "getSigAlgorithm";

    public static final String FUNC_GETSIGNINGTHRESHOLD = "getSigningThreshold";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_ISSIGNER = "isSigner";

    public static final String FUNC_NUMSIGNERS = "numSigners";

    public static final String FUNC_PROPOSEVOTE = "proposeVote";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_VERIFY = "verify";

    public static final String FUNC_VERIFYCONTRACT = "verifyContract";

    public static final String FUNC_VOTE = "vote";

    public static final Event VOTERESULT_EVENT = new Event("VoteResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<VoteResultEventResponse> getVoteResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTERESULT_EVENT, transactionReceipt);
        ArrayList<VoteResultEventResponse> responses = new ArrayList<VoteResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoteResultEventResponse typedResponse = new VoteResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._result = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VoteResultEventResponse> voteResultEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VoteResultEventResponse>() {
            @Override
            public VoteResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTERESULT_EVENT, log);
                VoteResultEventResponse typedResponse = new VoteResultEventResponse();
                typedResponse.log = log;
                typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._result = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VoteResultEventResponse> voteResultEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTERESULT_EVENT));
        return voteResultEventFlowable(filter);
    }

    public List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._participant = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._votedFor = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VotedEventResponse>() {
            @Override
            public VotedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTED_EVENT, log);
                VotedEventResponse typedResponse = new VotedEventResponse();
                typedResponse.log = log;
                typedResponse._participant = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._votedFor = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> actionVotes(BigInteger _voteTarget) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIONVOTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteTarget)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getApprovedContract(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVEDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getChainFinality(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCHAINFINALITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> proposeVote(BigInteger _action, BigInteger _voteTarget, BigInteger _additionalInfo1, BigInteger _additionalInfo2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROPOSEVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo1), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<Boolean> verifyContract(BigInteger _blockchainId, String _emittingContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _emittingContract)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger _action, BigInteger _voteTarget, Boolean _voteFor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.Bool(_voteFor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class VoteResultEventResponse extends BaseEventResponse {
        public BigInteger _action;

        public BigInteger _voteTarget;

        public Boolean _result;
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public String _participant;

        public BigInteger _action;

        public BigInteger _voteTarget;

        public Boolean _votedFor;
    }
}
