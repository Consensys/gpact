package net.consensys.gpact.appcontracts.nonatomic.erc721bridge.soliditywrappers;

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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
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
public class SfcErc721Bridge extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620014403803806200144083398101604081905262000034916200018f565b6000805460ff1916815533906200004c9082620000f7565b620000787f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd582620000f7565b620000a47f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a82620000f7565b620000d07f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c82620000f7565b50600280546001600160a01b0319166001600160a01b0392909216919091179055620001c1565b62000103828262000107565b5050565b60008281526001602090815260408083206001600160a01b038516845290915290205460ff16620001035760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b600060208284031215620001a257600080fd5b81516001600160a01b0381168114620001ba57600080fd5b9392505050565b61126f80620001d16000396000f3fe608060405234801561001057600080fd5b50600436106101375760003560e01c80638278ef6d116100b8578063c3be3f361161007c578063c3be3f36146102e0578063d547741f14610307578063d6ca5d781461031a578063da72c1e814610343578063e63ab1e914610356578063e787282a1461037d57600080fd5b80638278ef6d146102975780638456cb59146102aa57806384aea633146102b257806391d14854146102c5578063a217fddf146102d857600080fd5b806336568abe116100ff57806336568abe146101f95780633f4ba83a1461020c57806343a12224146102145780635c975abb1461023b57806380ccbde41461024657600080fd5b806301ffc9a71461013c57806306b47e9e14610164578063248a9ca3146101795780632e44a5b5146101ab5780632f2ff15d146101e6575b600080fd5b61014f61014a366004610fa8565b610390565b60405190151581526020015b60405180910390f35b610177610172366004610ff5565b6103c7565b005b61019d610187366004611031565b6000908152600160208190526040909120015490565b60405190815260200161015b565b61014f6101b936600461104a565b6001600160a01b039081166000908152600360209081526040808320948352939052919091205416151590565b6101776101f436600461104a565b610492565b61017761020736600461104a565b610522565b61017761059c565b61019d7f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd581565b60005460ff1661014f565b61027f61025436600461104a565b6001600160a01b03908116600090815260036020908152604080832094835293905291909120541690565b6040516001600160a01b03909116815260200161015b565b6101776102a5366004611076565b6105ec565b6101776108a2565b6101776102c03660046110b2565b6108f0565b61014f6102d336600461104a565b610b44565b61019d600081565b61019d7f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c81565b61017761031536600461104a565b610b6f565b61027f610328366004611031565b6000908152600460205260409020546001600160a01b031690565b610177610351366004611076565b610bf0565b61019d7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b61017761038b36600461104a565b610cb8565b60006001600160e01b03198216637965db0b60e01b14806103c157506301ffc9a760e01b6001600160e01b03198316145b92915050565b6103f17f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd533610b44565b6104165760405162461bcd60e51b815260040161040d906110f6565b60405180910390fd5b6001600160a01b03838116600081815260036020908152604080832087845282529182902080546001600160a01b0319169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830906060015b60405180910390a1505050565b600082815260016020819052604090912001546104b0905b33610b44565b6105145760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526e0818591b5a5b881d1bc819dc985b9d608a1b606482015260840161040d565b61051e8282610d2c565b5050565b6001600160a01b03811633146105925760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b606482015260840161040d565b61051e8282610d97565b6105c67f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610b44565b6105e25760405162461bcd60e51b815260040161040d9061113b565b6105ea610dfe565b565b60005460ff161561060f5760405162461bcd60e51b815260040161040d9061117f565b6002546001600160a01b0316336001600160a01b0316146106be5760405162461bcd60e51b815260206004820152605760248201527f45524320373231204272696467653a2043616e206e6f742070726f636573732060448201527f7472616e73666572732066726f6d20636f6e747261637473206f74686572207460648201527f68616e207468652062726964676520636f6e7472616374000000000000000000608482015260a40161040d565b6000806106c9610e91565b9092509050806001600160a01b0381166107315760405162461bcd60e51b8152602060048201526024808201527f45524320373231204272696467653a2063616c6c657220636f6e7472616374206044820152630697320360e41b606482015260840161040d565b6000838152600460205260409020546001600160a01b0316806107be576040805162461bcd60e51b81526020600482015260248101919091527f45524320373231204272696467653a204e6f204552433732312042726964676560448201527f20737570706f7274656420666f7220736f7572636520626c6f636b636861696e606482015260840161040d565b806001600160a01b0316826001600160a01b0316146108365760405162461bcd60e51b815260206004820152602e60248201527f45524320373231204272696467653a20496e636f727265637420736f7572636560448201526d204552433732312042726964676560901b606482015260840161040d565b610841878787610eb9565b604080518581526001600160a01b03848116602083015289811682840152881660608201526080810187905290517f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19181900360a00190a150505050505050565b6108cc7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610b44565b6108e85760405162461bcd60e51b815260040161040d9061113b565b6105ea610f20565b60005460ff16156109135760405162461bcd60e51b815260040161040d9061117f565b6000848152600460205260409020546001600160a01b0316806109895760405162461bcd60e51b815260206004820152602860248201527f45524320373231204272696467653a20426c6f636b636861696e206e6f7420736044820152671d5c1c1bdc9d195960c21b606482015260840161040d565b6001600160a01b0380851660009081526003602090815260408083208984529091529020541680610a225760405162461bcd60e51b815260206004820152603e60248201527f45524320373231204272696467653a20546f6b656e206e6f74207472616e736660448201527f657261626c6520746f2072657175657374656420626c6f636b636861696e0000606482015260840161040d565b610a2d853385610f78565b6002546040516001600160a01b03909116906392b2c3359088908590638278ef6d60e01b90610a649087908b908b906024016111a9565b60408051601f198184030181529181526020820180516001600160e01b03166001600160e01b03199485161790525160e086901b9092168252610aab9392916004016111cd565b600060405180830381600087803b158015610ac557600080fd5b505af1158015610ad9573d6000803e3d6000fd5b5050604080518981526001600160a01b038981166020830152858116828401523360608301528816608082015260a0810187905290517f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b93509081900360c0019150a1505050505050565b60009182526001602090815260408084206001600160a01b0393909316845291905290205460ff1690565b60008281526001602081905260409091200154610b8b906104aa565b6105925760405162461bcd60e51b815260206004820152603060248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526f2061646d696e20746f207265766f6b6560801b606482015260840161040d565b610c1a7f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c33610b44565b610c7a5760405162461bcd60e51b815260206004820152602b60248201527f455243373231204272696467653a204d75737420686176652041444d494e545260448201526a414e5346455220726f6c6560a81b606482015260840161040d565b610c85838383610eb9565b7f728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358838383604051610485939291906111a9565b610ce27f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd533610b44565b610cfe5760405162461bcd60e51b815260040161040d906110f6565b60009182526004602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b610d368282610b44565b61051e5760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b610da18282610b44565b1561051e5760008281526001602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff16610e475760405162461bcd60e51b815260206004820152601460248201527314185d5cd8589b194e881b9bdd081c185d5cd95960621b604482015260640161040d565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b6040516001600160a01b03909116815260200160405180910390a1565b6000808036806020603f19820184376000519450602080820360003760005193505050509091565b6040516323b872dd60e01b81526001600160a01b038416906323b872dd90610ee9903090869086906004016111a9565b600060405180830381600087803b158015610f0357600080fd5b505af1158015610f17573d6000803e3d6000fd5b50505050505050565b60005460ff1615610f435760405162461bcd60e51b815260040161040d9061117f565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258610e743390565b6040516323b872dd60e01b81526001600160a01b038416906323b872dd90610ee9908590309086906004016111a9565b600060208284031215610fba57600080fd5b81356001600160e01b031981168114610fd257600080fd5b9392505050565b80356001600160a01b0381168114610ff057600080fd5b919050565b60008060006060848603121561100a57600080fd5b61101384610fd9565b92506020840135915061102860408501610fd9565b90509250925092565b60006020828403121561104357600080fd5b5035919050565b6000806040838503121561105d57600080fd5b8235915061106d60208401610fd9565b90509250929050565b60008060006060848603121561108b57600080fd5b61109484610fd9565b92506110a260208501610fd9565b9150604084013590509250925092565b600080600080608085870312156110c857600080fd5b843593506110d860208601610fd9565b92506110e660408601610fd9565b9396929550929360600135925050565b60208082526025908201527f455243373231204272696467653a204d7573742068617665204d415050494e4760408201526420726f6c6560d81b606082015260800190565b60208082526024908201527f455243373231204272696467653a204d75737420686176652050415553455220604082015263726f6c6560e01b606082015260800190565b60208082526010908201526f14185d5cd8589b194e881c185d5cd95960821b604082015260600190565b6001600160a01b039384168152919092166020820152604081019190915260600190565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b8181101561120f578581018301518582016080015282016111f3565b81811115611221576000608083870101525b50601f01601f1916929092016080019594505050505056fea2646970667358221220cade30213a44243a5564a305b3b370594289306f7663ff7cf5fdccaf648d314064736f6c63430008090033";

    public static final String FUNC_ADMINTRANSFER_ROLE = "ADMINTRANSFER_ROLE";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_MAPPING_ROLE = "MAPPING_ROLE";

    public static final String FUNC_PAUSER_ROLE = "PAUSER_ROLE";

    public static final String FUNC_ADMINTRANSFER = "adminTransfer";

    public static final String FUNC_CHANGEBLOCKCHAINMAPPING = "changeBlockchainMapping";

    public static final String FUNC_CHANGECONTRACTMAPPING = "changeContractMapping";

    public static final String FUNC_GETBCIDTOKENMAPING = "getBcIdTokenMaping";

    public static final String FUNC_GETREMOTEERC721BRIDGECONTRACT = "getRemoteErc721BridgeContract";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_ISBCIDTOKENALLOWED = "isBcIdTokenAllowed";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_RECEIVEFROMOTHERBLOCKCHAIN = "receiveFromOtherBlockchain";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TRANSFERTOOTHERBLOCKCHAIN = "transferToOtherBlockchain";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final Event ADMINTRANSFER_EVENT = new Event("AdminTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAUSED_EVENT = new Event("Paused", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event RECEIVEDFROM_EVENT = new Event("ReceivedFrom", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TOKENCONTRACTADDRESSMAPPINGCHANGED_EVENT = new Event("TokenContractAddressMappingChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event TRANSFERTO_EVENT = new Event("TransferTo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNPAUSED_EVENT = new Event("Unpaused", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected SfcErc721Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SfcErc721Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SfcErc721Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SfcErc721Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AdminTransferEventResponse> getAdminTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINTRANSFER_EVENT, transactionReceipt);
        ArrayList<AdminTransferEventResponse> responses = new ArrayList<AdminTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminTransferEventResponse typedResponse = new AdminTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._erc721Contract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AdminTransferEventResponse> adminTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AdminTransferEventResponse>() {
            @Override
            public AdminTransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINTRANSFER_EVENT, log);
                AdminTransferEventResponse typedResponse = new AdminTransferEventResponse();
                typedResponse.log = log;
                typedResponse._erc721Contract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AdminTransferEventResponse> adminTransferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADMINTRANSFER_EVENT));
        return adminTransferEventFlowable(filter);
    }

    public List<PausedEventResponse> getPausedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAUSED_EVENT, transactionReceipt);
        ArrayList<PausedEventResponse> responses = new ArrayList<PausedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PausedEventResponse typedResponse = new PausedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PausedEventResponse> pausedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PausedEventResponse>() {
            @Override
            public PausedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAUSED_EVENT, log);
                PausedEventResponse typedResponse = new PausedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PausedEventResponse> pausedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAUSED_EVENT));
        return pausedEventFlowable(filter);
    }

    public List<ReceivedFromEventResponse> getReceivedFromEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDFROM_EVENT, transactionReceipt);
        ArrayList<ReceivedFromEventResponse> responses = new ArrayList<ReceivedFromEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedFromEventResponse typedResponse = new ReceivedFromEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._srcBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._srcTokenContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._destTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReceivedFromEventResponse> receivedFromEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ReceivedFromEventResponse>() {
            @Override
            public ReceivedFromEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDFROM_EVENT, log);
                ReceivedFromEventResponse typedResponse = new ReceivedFromEventResponse();
                typedResponse.log = log;
                typedResponse._srcBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._srcTokenContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._destTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReceivedFromEventResponse> receivedFromEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDFROM_EVENT));
        return receivedFromEventFlowable(filter);
    }

    public List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses = new ArrayList<RoleAdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleAdminChangedEventResponse>() {
            @Override
            public RoleAdminChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);
                RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADMINCHANGED_EVENT));
        return roleAdminChangedEventFlowable(filter);
    }

    public List<RoleGrantedEventResponse> getRoleGrantedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses = new ArrayList<RoleGrantedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleGrantedEventResponse>() {
            @Override
            public RoleGrantedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEGRANTED_EVENT, log);
                RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGRANTED_EVENT));
        return roleGrantedEventFlowable(filter);
    }

    public List<RoleRevokedEventResponse> getRoleRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses = new ArrayList<RoleRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RoleRevokedEventResponse>() {
            @Override
            public RoleRevokedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEREVOKED_EVENT, log);
                RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREVOKED_EVENT));
        return roleRevokedEventFlowable(filter);
    }

    public List<TokenContractAddressMappingChangedEventResponse> getTokenContractAddressMappingChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENCONTRACTADDRESSMAPPINGCHANGED_EVENT, transactionReceipt);
        ArrayList<TokenContractAddressMappingChangedEventResponse> responses = new ArrayList<TokenContractAddressMappingChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenContractAddressMappingChangedEventResponse typedResponse = new TokenContractAddressMappingChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._thisBcTokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._otherBcId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._othercTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenContractAddressMappingChangedEventResponse> tokenContractAddressMappingChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenContractAddressMappingChangedEventResponse>() {
            @Override
            public TokenContractAddressMappingChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENCONTRACTADDRESSMAPPINGCHANGED_EVENT, log);
                TokenContractAddressMappingChangedEventResponse typedResponse = new TokenContractAddressMappingChangedEventResponse();
                typedResponse.log = log;
                typedResponse._thisBcTokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._otherBcId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._othercTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenContractAddressMappingChangedEventResponse> tokenContractAddressMappingChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENCONTRACTADDRESSMAPPINGCHANGED_EVENT));
        return tokenContractAddressMappingChangedEventFlowable(filter);
    }

    public List<TransferToEventResponse> getTransferToEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERTO_EVENT, transactionReceipt);
        ArrayList<TransferToEventResponse> responses = new ArrayList<TransferToEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferToEventResponse typedResponse = new TransferToEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._destBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._srcTokenContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._destTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._sender = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferToEventResponse> transferToEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferToEventResponse>() {
            @Override
            public TransferToEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERTO_EVENT, log);
                TransferToEventResponse typedResponse = new TransferToEventResponse();
                typedResponse.log = log;
                typedResponse._destBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._srcTokenContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._destTokenContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._sender = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferToEventResponse> transferToEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERTO_EVENT));
        return transferToEventFlowable(filter);
    }

    public List<UnpausedEventResponse> getUnpausedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UNPAUSED_EVENT, transactionReceipt);
        ArrayList<UnpausedEventResponse> responses = new ArrayList<UnpausedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnpausedEventResponse typedResponse = new UnpausedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UnpausedEventResponse> unpausedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UnpausedEventResponse>() {
            @Override
            public UnpausedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UNPAUSED_EVENT, log);
                UnpausedEventResponse typedResponse = new UnpausedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UnpausedEventResponse> unpausedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNPAUSED_EVENT));
        return unpausedEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> ADMINTRANSFER_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINTRANSFER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULT_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> MAPPING_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAPPING_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> PAUSER_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PAUSER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> adminTransfer(String _erc721Contract, String _recipient, BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADMINTRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc721Contract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeBlockchainMapping(BigInteger _otherBcId, String _otherErc721Bridge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEBLOCKCHAINMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _otherErc721Bridge)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeContractMapping(String _thisBcTokenContract, BigInteger _otherBcId, String _othercTokenContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGECONTRACTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _othercTokenContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getBcIdTokenMaping(BigInteger _bcId, String _tokenContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBCIDTOKENMAPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _tokenContract)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getRemoteErc721BridgeContract(BigInteger _bcId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREMOTEERC721BRIDGECONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROLEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> grantRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isBcIdTokenAllowed(BigInteger _bcId, String _tokenContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISBCIDTOKENALLOWED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId), 
                new org.web3j.abi.datatypes.Address(160, _tokenContract)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> pause() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> paused() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> receiveFromOtherBlockchain(String _destTokenContract, String _recipient, BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEFROMOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _destTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeRole(byte[] role, String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferToOtherBlockchain(BigInteger _destBcId, String _srcTokenContract, String _recipient, BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERTOOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_destBcId), 
                new org.web3j.abi.datatypes.Address(160, _srcTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unpause() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SfcErc721Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SfcErc721Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SfcErc721Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SfcErc721Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SfcErc721Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SfcErc721Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SfcErc721Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SfcErc721Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SfcErc721Bridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc721Bridge.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SfcErc721Bridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc721Bridge.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SfcErc721Bridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc721Bridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SfcErc721Bridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc721Bridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AdminTransferEventResponse extends BaseEventResponse {
        public String _erc721Contract;

        public String _recipient;

        public BigInteger _tokenId;
    }

    public static class PausedEventResponse extends BaseEventResponse {
        public String account;
    }

    public static class ReceivedFromEventResponse extends BaseEventResponse {
        public BigInteger _srcBcId;

        public String _srcTokenContract;

        public String _destTokenContract;

        public String _recipient;

        public BigInteger _tokenId;
    }

    public static class RoleAdminChangedEventResponse extends BaseEventResponse {
        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRevokedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class TokenContractAddressMappingChangedEventResponse extends BaseEventResponse {
        public String _thisBcTokenContract;

        public BigInteger _otherBcId;

        public String _othercTokenContract;
    }

    public static class TransferToEventResponse extends BaseEventResponse {
        public BigInteger _destBcId;

        public String _srcTokenContract;

        public String _destTokenContract;

        public String _sender;

        public String _recipient;

        public BigInteger _tokenId;
    }

    public static class UnpausedEventResponse extends BaseEventResponse {
        public String account;
    }
}
