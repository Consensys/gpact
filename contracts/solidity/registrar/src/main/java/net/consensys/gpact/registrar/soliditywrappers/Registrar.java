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
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b507f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c5805460ff199081166001908117909255600580548084019091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319163390811790915560009081526004602090815260408220849055600380546001600160401b03191685179055631c6e4c1760e31b8252527f17c19a682e8fe984fd595b0bb0646415bdafce0ac6ac60b1a04898730b1f78c380549091169091179055611a97806100eb6000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806360ce281211610097578063cb103ef511610066578063cb103ef5146102c4578063ea13ec8b146102d7578063f5e232ea146102ea578063fc29175e1461031e57610100565b806360ce28121461025a578063a130e0451461026d578063a64ce1991461028e578063aa6dc926146102bc57610100565b806324d7806c116100d357806324d7806c1461019a5780632ef6c66d146101c557806348bcbd2d1461020d578063581cb2dc1461024957610100565b806301ffc9a71461010557806307c099da1461012d5780630a3b7f3b14610172578063111fd88b14610187575b600080fd5b610118610113366004611723565b610331565b60405190151581526020015b60405180910390f35b61015a61013b3660046117c2565b600090815260016020819052604090912001546001600160a01b031690565b6040516001600160a01b039091168152602001610124565b61018561018036600461178a565b610354565b005b61015a6101953660046117c2565b6106fe565b6101186101a83660046116e6565b6001600160a01b0316600090815260046020526040902054151590565b6101f56101d33660046117c2565b600090815260016020526040902054600160481b90046001600160401b031690565b6040516001600160401b039091168152602001610124565b61011861021b3660046117da565b60008281526001602090815260408083206001600160a01b0385168452600201909152902054151592915050565b6003546001600160401b03166101f5565b6101186102683660046117da565b61073c565b61028061027b3660046117c2565b6107c4565b604051908152602001610124565b6101f561029c3660046117c2565b60009081526001602052604090205461010090046001600160401b031690565b600554610280565b6101856102d23660046117c2565b6107fc565b6101186102e5366004611805565b610991565b6101f56102f83660046117c2565b60009081526001602081905260409091200154600160a01b90046001600160401b031690565b61018561032c36600461174b565b610cc1565b6001600160e01b0319811660009081526020819052604090205460ff165b919050565b3360009081526004602052604090205461036d57600080fd5b60008461ffff16600881111561039357634e487b7160e01b600052602160045260246000fd5b90506000808581526006602052604090205460ff1660088111156103c757634e487b7160e01b600052602160045260246000fd5b146103d157600080fd5b838360018360088111156103f557634e487b7160e01b600052602160045260246000fd5b1415610423576001600160a01b0382166000908152600460205260409020541561041e57600080fd5b61064b565b600283600881111561044557634e487b7160e01b600052602160045260246000fd5b1415610483576001600160a01b03821660009081526004602052604090205461046d57600080fd5b6001600160a01b03821633141561041e57600080fd5b60048360088111156104a557634e487b7160e01b600052602160045260246000fd5b14156105285760008681526001602052604090205461010090046001600160401b0316156104d257600080fd5b60008460018111156104f457634e487b7160e01b600052602160045260246000fd5b9050600181600181111561051857634e487b7160e01b600052602160045260246000fd5b1461052257600080fd5b5061064b565b600583600881111561054a57634e487b7160e01b600052602160045260246000fd5b14156105765760008681526001602052604090205461010090046001600160401b031661041e57600080fd5b600683600881111561059857634e487b7160e01b600052602160045260246000fd5b14156105f45760008681526001602052604090205461010090046001600160401b03166105c457600080fd5b60008681526001602090815260408083206001600160a01b03851684526002019091529020541561041e57600080fd5b600783600881111561061657634e487b7160e01b600052602160045260246000fd5b141561064b5760008681526001602090815260408083206001600160a01b038516845260020190915290205461064b57600080fd5b6000868152600660205260409020805484919060ff1916600183600881111561068457634e487b7160e01b600052602160045260246000fd5b02179055506002546106a690600160a01b90046001600160401b0316436119a9565b60008781526006602052604090206003810191909155600181018690556002908101859055546001600160a01b03166106e9576106e4866001610db0565b6106f5565b6106f587876001611453565b50505050505050565b60006005828154811061072157634e487b7160e01b600052603260045260246000fd5b6000918252602090912001546001600160a01b031692915050565b6000828152600160208190526040822001546001600160a01b038381169116146107bb5760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b60648201526084015b60405180910390fd5b50600192915050565b600081815260016020819052604082205460ff16908111156107f657634e487b7160e01b600052602160045260246000fd5b92915050565b3360009081526004602052604090205461081557600080fd5b60008181526006602052604081205460ff169081600881111561084857634e487b7160e01b600052602160045260246000fd5b141561085357600080fd5b600082815260066020526040902060030154431161087057600080fd5b600254600354600084815260066020819052604080832090516319ab36c160e01b81526001600160a01b0390951694929385936319ab36c1936108c9936001600160401b03909316926005810192910190600401611975565b60206040518083038186803b1580156108e157600080fd5b505afa1580156108f5573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109199190611707565b90507ff36941b213653867ab41db7916ff3975cd4d6cfe42f73e61aeda6257d1eab74b83600881111561095c57634e487b7160e01b600052602160045260246000fd5b6040805161ffff9092168252602082018790528315159082015260600160405180910390a161098b8482610db0565b50505050565b6000898881146109da5760405162461bcd60e51b81526020600482015260146024820152730e6d2cea440d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016107b2565b808714610a205760405162461bcd60e51b81526020600482015260146024820152730e6d2cea640d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016107b2565b808514610a665760405162461bcd60e51b81526020600482015260146024820152730e6d2ceac40d8cadccee8d040dad2e6dac2e8c6d60631b60448201526064016107b2565b60008d81526001602052604090205461010090046001600160401b0316811015610ac75760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b60448201526064016107b2565b60005b81811015610cad5760008e8152600160205260408120600201908e8e84818110610b0457634e487b7160e01b600052603260045260246000fd5b9050602002016020810190610b1991906116e6565b6001600160a01b03168152602081019190915260400160002054610b8d5760405162461bcd60e51b815260206004820152602560248201527f5369676e6572206e6f74207369676e657220666f72207468697320626c6f636b60448201526431b430b4b760d91b60648201526084016107b2565b610c4f8d8d83818110610bb057634e487b7160e01b600052603260045260246000fd5b9050602002016020810190610bc591906116e6565b86868e8e86818110610be757634e487b7160e01b600052603260045260246000fd5b905060200201358d8d87818110610c0e57634e487b7160e01b600052603260045260246000fd5b905060200201358c8c88818110610c3557634e487b7160e01b600052603260045260246000fd5b9050602002016020810190610c4a91906118fe565b611539565b610c9b5760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f7420766572696679000000000000000060448201526064016107b2565b80610ca5816119fb565b915050610aca565b5060019d9c50505050505050505050505050565b33600090815260046020526040902054610cda57600080fd5b60008361ffff166008811115610d0057634e487b7160e01b600052602160045260246000fd5b9050806008811115610d2257634e487b7160e01b600052602160045260246000fd5b60008481526006602052604090205460ff166008811115610d5357634e487b7160e01b600052602160045260246000fd5b14610d5d57600080fd5b600083815260066020908152604080832033845260040190915290205460ff1615610d8757600080fd5b600083815260066020526040902060030154431115610da557600080fd5b61098b848484611453565b8015611309576000828152600660205260409020805460018083015460029093015460ff90921692919085908390856008811115610dfe57634e487b7160e01b600052602160045260246000fd5b1415610e9e57600580546001810182557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319166001600160a01b0385169081179091559054600091825260046020526040822055600380546001600160401b031691610e7483611a16565b91906101000a8154816001600160401b0302191690836001600160401b0316021790555050611303565b6002856008811115610ec057634e487b7160e01b600052602160045260246000fd5b1415610f7e576001600160a01b0382166000908152600460205260409020546005610eec6001836119c1565b81548110610f0a57634e487b7160e01b600052603260045260246000fd5b6000918252602080832090910180546001600160a01b03191690556001600160a01b03851682526004905260408120819055600380546001600160401b031691610f53836119d8565b91906101000a8154816001600160401b0302191690836001600160401b031602179055505050611303565b6003856008811115610fa057634e487b7160e01b600052602160045260246000fd5b1415610fe557600280546001600160a01b0319166001600160a01b0384161767ffffffffffffffff60a01b1916600160a01b6001600160401b03871602179055611303565b600485600881111561100757634e487b7160e01b600052602160045260246000fd5b14156110bf576000878152600160208190526040909120810180546001600160a01b0319166001600160a01b038416179055839081111561105857634e487b7160e01b600052602160045260246000fd5b60008881526001602081905260409091208054909160ff1990911690838181111561109357634e487b7160e01b600052602160045260246000fd5b02179055506000878152600160205260409020805468ffffffffffffffff001916610100179055611303565b60058560088111156110e157634e487b7160e01b600052602160045260246000fd5b1415611119576000878152600160205260409020805468ffffffffffffffff0019166101006001600160401b03871602179055611303565b600685600881111561113b57634e487b7160e01b600052602160045260246000fd5b14156111b857600087815260016020818152604080842060038101805480860182558187528487200180546001600160a01b0319166001600160a01b038916908117909155905490865260028201845291852091909155928a9052819052018054600160a01b90046001600160401b0316906014610e7483611a16565b60078560088111156111da57634e487b7160e01b600052602160045260246000fd5b14156112a35760008781526001602081815260408084206001600160a01b038616855260028101835290842054938b9052908290526003019061121d90836119c1565b8154811061123b57634e487b7160e01b600052603260045260246000fd5b6000918252602080832090910180546001600160a01b0319169055898252600180825260408084206001600160a01b0387168552600281018452908420849055928b9052908190520180546001600160401b03600160a01b90910416906014610f53836119d8565b60088560088111156112c557634e487b7160e01b600052602160045260246000fd5b1415611303576000878152600160205260409020805470ffffffffffffffff0000000000000000001916600160481b6001600160401b038716021790555b50505050505b60005b60055481101561140b5760006001600160a01b03166005828154811061134257634e487b7160e01b600052603260045260246000fd5b6000918252602090912001546001600160a01b0316146113f9576006600084815260200190815260200160002060040160006005838154811061139557634e487b7160e01b600052603260045260246000fd5b60009182526020808320909101546001600160a01b031683528281019390935260409182018120805460ff19169055858152600690925281206113dd916005909101906115fc565b600083815260066020819052604082206113f9929101906115fc565b80611403816119fb565b91505061130c565b506000828152600660205260408120805460ff191681556001810182905560028101829055600381018290559061144560058301826115fc565b61098b6006830160006115fc565b6040805133815261ffff8516602082015290810183905281151560608201527f2cf518b4f57f241b79cee0c80f2d18fb40e9a7044c6a5fcad55cb1c74b95bbb49060800160405180910390a160008281526006602090815260408083203384526004019091529020805460ff1916600117905580156115025760008281526006602090815260408220600501805460018101825590835291200180546001600160a01b03191633179055611534565b60008281526006602081815260408320909101805460018101825590835291200180546001600160a01b031916331790555b505050565b600080868660405161154c929190611965565b604051809103902090508260ff16601b1415801561156e57508260ff16601c14155b1561157d5760009150506115f2565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa1580156115d0573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b508054600082559060005260206000209081019061161a919061161d565b50565b5b80821115611632576000815560010161161e565b5090565b80356001600160a01b038116811461034f57600080fd5b60008083601f84011261165e578182fd5b5081356001600160401b03811115611674578182fd5b602083019150836020808302850101111561168e57600080fd5b9250929050565b60008083601f8401126116a6578182fd5b5081356001600160401b038111156116bc578182fd5b60208301915083602082850101111561168e57600080fd5b803561ffff8116811461034f57600080fd5b6000602082840312156116f7578081fd5b61170082611636565b9392505050565b600060208284031215611718578081fd5b815161170081611a53565b600060208284031215611734578081fd5b81356001600160e01b031981168114611700578182fd5b60008060006060848603121561175f578182fd5b611768846116d4565b925060208401359150604084013561177f81611a53565b809150509250925092565b6000806000806080858703121561179f578081fd5b6117a8856116d4565b966020860135965060408601359560600135945092505050565b6000602082840312156117d3578081fd5b5035919050565b600080604083850312156117ec578182fd5b823591506117fc60208401611636565b90509250929050565b600080600080600080600080600080600060c08c8e031215611825578687fd5b8b359a506001600160401b038060208e01351115611841578788fd5b6118518e60208f01358f0161164d565b909b50995060408d0135811015611866578788fd5b6118768e60408f01358f0161164d565b909950975060608d013581101561188b578687fd5b61189b8e60608f01358f0161164d565b909750955060808d01358110156118b0578485fd5b6118c08e60808f01358f0161164d565b909550935060a08d01358110156118d5578283fd5b506118e68d60a08e01358e01611695565b81935080925050509295989b509295989b9093969950565b60006020828403121561190f578081fd5b813560ff81168114611700578182fd5b6000815480845260208085019450838352808320835b8381101561195a5781546001600160a01b031687529582019560019182019101611935565b509495945050505050565b6000828483379101908152919050565b60006001600160401b038516825260606020830152611997606083018561191f565b82810360408401526115f2818561191f565b600082198211156119bc576119bc611a3d565b500190565b6000828210156119d3576119d3611a3d565b500390565b60006001600160401b038216806119f1576119f1611a3d565b6000190192915050565b6000600019821415611a0f57611a0f611a3d565b5060010190565b60006001600160401b0380831681811415611a3357611a33611a3d565b6001019392505050565b634e487b7160e01b600052601160045260246000fd5b801515811461161a57600080fdfea2646970667358221220b9aeac05d9e11d1dee0543ac6102bdc349229fcf023e90be046123f19434fa2564736f6c63430008020033";

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
