package tech.pegasys.ltacfc.soliditywrappers;

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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b507f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c5805460ff199081166001908117909255600580548084019091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319163390811790915560009081526004602090815260408220849055600380546001600160401b03191685179055631c6e4c1760e31b8252527f17c19a682e8fe984fd595b0bb0646415bdafce0ac6ac60b1a04898730b1f78c3805490911690911790556116ac806100eb6000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806360ce281211610097578063cb103ef511610066578063cb103ef5146102de578063ea13ec8b146102fb578063f5e232ea146104b0578063fc29175e146104cd57610100565b806360ce28121461025e578063a130e0451461028a578063a64ce199146102b9578063aa6dc926146102d657610100565b806324d7806c116100d357806324d7806c146101cb5780632ef6c66d146101f157806348bcbd2d1461022a578063581cb2dc1461025657610100565b806301ffc9a71461010557806307c099da146101405780630a3b7f3b14610179578063111fd88b146101ae575b600080fd5b61012c6004803603602081101561011b57600080fd5b50356001600160e01b0319166104fc565b604080519115158252519081900360200190f35b61015d6004803603602081101561015657600080fd5b503561051b565b604080516001600160a01b039092168252519081900360200190f35b6101ac6004803603608081101561018f57600080fd5b5061ffff813516906020810135906040810135906060013561053a565b005b61015d600480360360208110156101c457600080fd5b50356107dc565b61012c600480360360208110156101e157600080fd5b50356001600160a01b0316610806565b61020e6004803603602081101561020757600080fd5b5035610823565b604080516001600160401b039092168252519081900360200190f35b61012c6004803603604081101561024057600080fd5b50803590602001356001600160a01b0316610845565b61020e610873565b61012c6004803603604081101561027457600080fd5b50803590602001356001600160a01b0316610882565b6102a7600480360360208110156102a057600080fd5b50356108e6565b60408051918252519081900360200190f35b61020e600480360360208110156102cf57600080fd5b503561090a565b6102a761092a565b6101ac600480360360208110156102f457600080fd5b5035610930565b61012c600480360360c081101561031157600080fd5b81359190810190604081016020820135600160201b81111561033257600080fd5b82018360208201111561034457600080fd5b803590602001918460208302840111600160201b8311171561036557600080fd5b919390929091602081019035600160201b81111561038257600080fd5b82018360208201111561039457600080fd5b803590602001918460208302840111600160201b831117156103b557600080fd5b919390929091602081019035600160201b8111156103d257600080fd5b8201836020820111156103e457600080fd5b803590602001918460208302840111600160201b8311171561040557600080fd5b919390929091602081019035600160201b81111561042257600080fd5b82018360208201111561043457600080fd5b803590602001918460208302840111600160201b8311171561045557600080fd5b919390929091602081019035600160201b81111561047257600080fd5b82018360208201111561048457600080fd5b803590602001918460018302840111600160201b831117156104a557600080fd5b509092509050610b35565b61020e600480360360208110156104c657600080fd5b5035610de7565b6101ac600480360360608110156104e357600080fd5b5061ffff81351690602081013590604001351515610e0d565b6001600160e01b03191660009081526020819052604090205460ff1690565b600090815260016020819052604090912001546001600160a01b031690565b3360009081526004602052604090205461055357600080fd5b60008461ffff16600881111561056557fe5b90506000808581526006602052604090205460ff16600881111561058557fe5b1461058f57600080fd5b8383600183600881111561059f57fe5b14156105bd576105ae82610806565b156105b857600080fd5b610749565b60028360088111156105cb57fe5b14156105f9576105da82610806565b6105e357600080fd5b6001600160a01b0382163314156105b857600080fd5b600483600881111561060757fe5b14156106625760008681526001602052604090205461010090046001600160401b03161561063457600080fd5b600084600181111561064257fe5b9050600181600181111561065257fe5b1461065c57600080fd5b50610749565b600583600881111561067057fe5b141561069c5760008681526001602052604090205461010090046001600160401b03166105b857600080fd5b60068360088111156106aa57fe5b14156107065760008681526001602052604090205461010090046001600160401b03166106d657600080fd5b60008681526001602090815260408083206001600160a01b0385168452600201909152902054156105b857600080fd5b600783600881111561071457fe5b14156107495760008681526001602090815260408083206001600160a01b038516845260020190915290205461074957600080fd5b6000868152600660205260409020805484919060ff1916600183600881111561076e57fe5b0217905550600280546000888152600660205260409020600160a01b9091046001600160401b031643016003820155600181018790558101859055546001600160a01b03166107c7576107c2866001610ec0565b6107d3565b6107d387876001611440565b50505050505050565b6000600582815481106107eb57fe5b6000918252602090912001546001600160a01b031692915050565b6001600160a01b0316600090815260046020526040902054151590565b600090815260016020526040902054600160481b90046001600160401b031690565b60008281526001602090815260408083206001600160a01b0385168452600201909152902054151592915050565b6003546001600160401b031690565b6000828152600160208190526040822001546001600160a01b038381169116146108dd5760405162461bcd60e51b81526004018080602001828103825260258152602001806116526025913960400191505060405180910390fd5b50600192915050565b600081815260016020819052604082205460ff169081111561090457fe5b92915050565b60009081526001602052604090205461010090046001600160401b031690565b60055490565b3360009081526004602052604090205461094957600080fd5b60008181526006602052604081205460ff169081600881111561096857fe5b141561097357600080fd5b600082815260066020526040902060030154431161099057600080fd5b600254600354600084815260066020819052604080832090516319ab36c160e01b81526001600160401b0390941660048501818152606060248701908152600584018054606489018190526001600160a01b0390991698969789976319ab36c197959692959290920193929160448201916084019086908015610a3c57602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610a1e575b50508381038252848181548152602001915080548015610a8557602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610a67575b50509550505050505060206040518083038186803b158015610aa657600080fd5b505afa158015610aba573d6000803e3d6000fd5b505050506040513d6020811015610ad057600080fd5b505190507ff36941b213653867ab41db7916ff3975cd4d6cfe42f73e61aeda6257d1eab74b836008811115610b0157fe5b6040805161ffff90921682526020820187905283151582820152519081900360600190a1610b2f8482610ec0565b50505050565b600089888114610b83576040805162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b604482015290519081900360640190fd5b808714610bce576040805162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b604482015290519081900360640190fd5b808514610c19576040805162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b604482015290519081900360640190fd5b60008d81526001602052604090205461010090046001600160401b0316811015610c7f576040805162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b604482015290519081900360640190fd5b60005b81811015610dd35760008e8152600160205260408120600201908e8e84818110610ca857fe5b905060200201356001600160a01b03166001600160a01b03166001600160a01b031681526020019081526020016000205460001415610d185760405162461bcd60e51b815260040180806020018281038252602581526020018061162d6025913960400191505060405180910390fd5b610d7a8d8d83818110610d2757fe5b905060200201356001600160a01b031686868e8e86818110610d4557fe5b905060200201358d8d87818110610d5857fe5b905060200201358c8c88818110610d6b57fe5b9050602002013560ff16611525565b610dcb576040805162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f74207665726966790000000000000000604482015290519081900360640190fd5b600101610c82565b5060019d9c50505050505050505050505050565b60009081526001602081905260409091200154600160a01b90046001600160401b031690565b33600090815260046020526040902054610e2657600080fd5b60008361ffff166008811115610e3857fe5b9050806008811115610e4657fe5b60008481526006602052604090205460ff166008811115610e6357fe5b14610e6d57600080fd5b600083815260066020908152604080832033845260040190915290205460ff1615610e9757600080fd5b600083815260066020526040902060030154431115610eb557600080fd5b610b2f848484611440565b8015611328576000828152600660205260409020805460018083015460029093015460ff90921692919085908390856008811115610efa57fe5b1415610f8a5760058054600180820183557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db090910180546001600160a01b0319166001600160a01b0386169081179091559154600092835260046020526040909220919091556003805467ffffffffffffffff1981166001600160401b0391821690930116919091179055611322565b6002856008811115610f9857fe5b1415611025576001600160a01b038216600090815260046020526040902054600580546000198301908110610fc957fe5b600091825260208083209190910180546001600160a01b03191690556001600160a01b0385168252600490526040812055506003805467ffffffffffffffff1981166001600160401b0391821660001901909116179055611322565b600385600881111561103357fe5b141561107857600280546001600160a01b0319166001600160a01b0384161767ffffffffffffffff60a01b1916600160a01b6001600160401b03871602179055611322565b600485600881111561108657fe5b1415611116576000878152600160208190526040909120810180546001600160a01b0319166001600160a01b03841617905583908111156110c357fe5b60008881526001602081905260409091208054909160ff199091169083818111156110ea57fe5b02179055506000878152600160205260409020805468ffffffffffffffff001916610100179055611322565b600585600881111561112457fe5b141561115c576000878152600160205260409020805468ffffffffffffffff0019166101006001600160401b03871602179055611322565b600685600881111561116a57fe5b141561120257600087815260016020818152604080842060038101805480860182558187528487200180546001600160a01b0389166001600160a01b03199091168117909155905490865260028201845291852091909155928a905281905290810180546001600160401b03600160a01b80830482169094011690920267ffffffffffffffff60a01b19909216919091179055611322565b600785600881111561121057fe5b14156112d65760008781526001602081815260408084206001600160a01b038616855260028101835290842054938b90529190526003018054600019830190811061125757fe5b6000918252602080832090910180546001600160a01b0319169055898252600180825260408084206001600160a01b0387168552600281018452908420849055928b9052908190520180546000196001600160401b03600160a01b808404821692909201160267ffffffffffffffff60a01b1990911617905550611322565b60088560088111156112e457fe5b1415611322576000878152600160205260409020805470ffffffffffffffff0000000000000000001916600160481b6001600160401b038716021790555b50505050505b60005b6005548110156113f85760006001600160a01b03166005828154811061134d57fe5b6000918252602090912001546001600160a01b0316146113f0576006600084815260200190815260200160002060040160006005838154811061138c57fe5b60009182526020808320909101546001600160a01b031683528281019390935260409182018120805460ff19169055858152600690925281206113d4916005909101906115f2565b600083815260066020819052604082206113f0929101906115f2565b60010161132b565b506000828152600660205260408120805460ff191681556001810182905560028101829055600381018290559061143260058301826115f2565b610b2f6006830160006115f2565b6040805133815261ffff85166020820152808201849052821515606082015290517f2cf518b4f57f241b79cee0c80f2d18fb40e9a7044c6a5fcad55cb1c74b95bbb49181900360800190a160008281526006602090815260408083203384526004019091529020805460ff1916600117905580156114ee5760008281526006602090815260408220600501805460018101825590835291200180546001600160a01b03191633179055611520565b60008281526006602081815260408320909101805460018101825590835291200180546001600160a01b031916331790555b505050565b600080868660405180838380828437604051920182900390912094505050505060ff8316601b1480159061155d57508260ff16601c14155b1561156c5760009150506115e8565b60018184878760405160008152602001604052604051808581526020018460ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa1580156115c6573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b50805460008255906000526020600020908101906116109190611613565b50565b5b808211156116285760008155600101611614565b509056fe5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b636861696e44617461206e6f7420656d697474656420627920617070726f76656420636f6e7472616374a2646970667358221220a691776b22756d90ee4b07183471d5eb75f2db74051f3e47d156841b8fde994464736f6c63430007040033";

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

    public String getRLP_actionVotes(BigInteger _voteTarget) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIONVOTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteTarget)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> getApprovedContract(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVEDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getApprovedContract(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETAPPROVEDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getChainFinality(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCHAINFINALITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getChainFinality(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCHAINFINALITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_proposeVote(BigInteger _action, BigInteger _voteTarget, BigInteger _additionalInfo1, BigInteger _additionalInfo2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROPOSEVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo1), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_verify(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _plainText) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFY, 
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
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> verifyContract(BigInteger _blockchainId, String _emittingContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _emittingContract)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_verifyContract(BigInteger _blockchainId, String _emittingContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFYCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _emittingContract)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_vote(BigInteger _action, BigInteger _voteTarget, Boolean _voteFor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.Bool(_voteFor)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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
