package net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers;

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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class SfcErc20Bridge extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001fca38038062001fca833981016040819052620000349162000216565b6000805460ff19168155620000516401000000006200012f810204565b90506200006960008264010000000062000133810204565b6200009e7f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd58264010000000062000133810204565b620000d37f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a8264010000000062000133810204565b620001087f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c8264010000000062000133810204565b5060028054600160a060020a031916600160a060020a039290921691909117905562000248565b3390565b6200014882826401000000006200014c810204565b5050565b620001618282640100000000620001eb810204565b62000148576000828152600160208181526040808420600160a060020a0386168552909152909120805460ff19169091179055620001a76401000000006200012f810204565b600160a060020a031681600160a060020a0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b6000602082840312156200022957600080fd5b8151600160a060020a03811681146200024157600080fd5b9392505050565b611d7280620002586000396000f3fe608060405234801561001057600080fd5b5060043610610175576000357c0100000000000000000000000000000000000000000000000000000000900480638456cb59116100e0578063d547741f11610099578063d547741f1461036f578063da72c1e814610382578063e63ab1e914610395578063e787282a146103bc578063e96dab56146103cf578063ef0047cf146103e257600080fd5b80638456cb59146102ff57806384aea6331461030757806391d148541461031a578063a217fddf1461032d578063c22b869914610335578063c3be3f361461034857600080fd5b806336568abe1161013257806336568abe146102785780633f4ba83a1461028b57806343a12224146102935780635c975abb146102a857806380ccbde4146102b35780638278ef6d146102ec57600080fd5b806301ffc9a71461017a57806306b47e9e146101a25780630dd4b783146101b7578063248a9ca3146101f85780632e44a5b51461022a5780632f2ff15d14610265575b600080fd5b61018d6101883660046117ab565b610402565b60405190151581526020015b60405180910390f35b6101b56101b0366004611806565b610495565b005b6101e06101c5366004611842565b600090815260056020526040902054600160a060020a031690565b604051600160a060020a039091168152602001610199565b61021c610206366004611842565b6000908152600160208190526040909120015490565b604051908152602001610199565b61018d61023836600461185b565b600160a060020a039081166000908152600460209081526040808320948352939052919091205416151590565b6101b561027336600461185b565b61051d565b6101b561028636600461185b565b610544565b6101b56105d3565b61021c600080516020611d1d83398151915281565b60005460ff1661018d565b6101e06102c136600461185b565b600160a060020a03908116600090815260046020908152604080832094835293905291909120541690565b6101b56102fa366004611887565b610626565b6101b5610916565b6101b56103153660046118c3565b610967565b61018d61032836600461185b565b610c16565b61021c600081565b6101b5610343366004611918565b610c41565b61021c7f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c81565b6101b561037d36600461185b565b610d22565b6101b5610390366004611887565b610d49565b61021c7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b6101b56103ca36600461185b565b610e44565b6101b56103dd366004611967565b610eb6565b61021c6103f036600461199e565b60036020526000908152604090205481565b60007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1982167f7965db0b00000000000000000000000000000000000000000000000000000000148061048f57507f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff198316145b92915050565b6104ad600080516020611d1d83398151915233610c16565b6104d55760405160e560020a62461bcd0281526004016104cc906119b9565b60405180910390fd5b600160a060020a03831660009081526003602052604090205461050d5760405160e560020a62461bcd0281526004016104cc90611a16565b610518838383610f2f565b505050565b6000828152600160208190526040909120015461053a8133610faf565b6105188383611016565b600160a060020a03811633146105c55760405160e560020a62461bcd02815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201527f20726f6c657320666f722073656c66000000000000000000000000000000000060648201526084016104cc565b6105cf8282611081565b5050565b6105fd7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610c16565b61061c5760405160e560020a62461bcd0281526004016104cc90611a73565b6106246110e8565b565b60005460ff161561064c5760405160e560020a62461bcd0281526004016104cc90611ad0565b600254600160a060020a031633600160a060020a0316146106fe5760405160e560020a62461bcd02815260206004820152605560248201527f4552433230204272696467653a2043616e206e6f742070726f6365737320747260448201527f616e73666572732066726f6d20636f6e747261637473206f746865722074686160648201527f6e207468652062726964676520636f6e74726163740000000000000000000000608482015260a4016104cc565b600080610709611187565b9092509050600160a060020a03811661078d5760405160e560020a62461bcd02815260206004820152602360248201527f455243203230204272696467653a2063616c6c657220636f6e7472616374206960448201527f732030000000000000000000000000000000000000000000000000000000000060648201526084016104cc565b600082815260056020526040902054600160a060020a03168061081b5760405160e560020a62461bcd02815260206004820152603e60248201527f4552433230204272696467653a204e6f2045524320323020427269646765207360448201527f7570706f7274656420666f7220736f7572636520626c6f636b636861696e000060648201526084016104cc565b80600160a060020a031682600160a060020a0316146108a55760405160e560020a62461bcd02815260206004820152602c60248201527f4552433230204272696467653a20496e636f727265637420736f75726365204560448201527f524320323020427269646765000000000000000000000000000000000000000060648201526084016104cc565b6108b08686866111af565b60408051848152600160a060020a038085166020830152808916928201929092529086166060820152608081018590527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a0015b60405180910390a1505050505050565b6109407f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610c16565b61095f5760405160e560020a62461bcd0281526004016104cc90611a73565b61062461133b565b60005460ff161561098d5760405160e560020a62461bcd0281526004016104cc90611ad0565b600084815260056020526040902054600160a060020a031680610a1b5760405160e560020a62461bcd02815260206004820152602660248201527f4552433230204272696467653a20426c6f636b636861696e206e6f742073757060448201527f706f72746564000000000000000000000000000000000000000000000000000060648201526084016104cc565b600160a060020a0380851660009081526004602090815260408083208984529091529020541680610ab75760405160e560020a62461bcd02815260206004820152603c60248201527f4552433230204272696467653a20546f6b656e206e6f74207472616e7366657260448201527f61626c6520746f2072657175657374656420626c6f636b636861696e0000000060648201526084016104cc565b610ac2853385611396565b60025460408051600160a060020a0384811660248301528781166044830152606480830188905283518084039091018152608490920183526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f8278ef6d0000000000000000000000000000000000000000000000000000000017905291517f92b2c33500000000000000000000000000000000000000000000000000000000815291909216916392b2c33591610b83918a91879190600401611b5f565b600060405180830381600087803b158015610b9d57600080fd5b505af1158015610bb1573d6000803e3d6000fd5b505060408051898152600160a060020a03808a16602083015280861692820192909252336060820152908716608082015260a081018690527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c0019050610906565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b610c59600080516020611d1d83398151915233610c16565b610c785760405160e560020a62461bcd0281526004016104cc906119b9565b600160a060020a03841660009081526003602052604090205415610d075760405160e560020a62461bcd02815260206004820152602660248201527f4552433230204272696467653a20746f6b656e20616c726561647920636f6e6660448201527f696775726564000000000000000000000000000000000000000000000000000060648201526084016104cc565b610d1184826114f5565b610d1c848484610f2f565b50505050565b60008281526001602081905260409091200154610d3f8133610faf565b6105188383611081565b610d737f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c33610c16565b610de85760405160e560020a62461bcd02815260206004820152602a60248201527f4552433230204272696467653a204d75737420686176652041444d494e54524160448201527f4e5346455220726f6c650000000000000000000000000000000000000000000060648201526084016104cc565b610df38383836111af565b60408051600160a060020a038086168252841660208201529081018290527f728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358906060015b60405180910390a1505050565b610e5c600080516020611d1d83398151915233610c16565b610e7b5760405160e560020a62461bcd0281526004016104cc906119b9565b600091825260056020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b610ece600080516020611d1d83398151915233610c16565b610eed5760405160e560020a62461bcd0281526004016104cc906119b9565b600160a060020a038216600090815260036020526040902054610f255760405160e560020a62461bcd0281526004016104cc90611a16565b6105cf82826114f5565b600160a060020a038381166000818152600460209081526040808320878452825291829020805473ffffffffffffffffffffffffffffffffffffffff19169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a83090606001610e37565b610fb98282610c16565b6105cf57610fd181600160a060020a0316601461155a565b610fdc83602061155a565b604051602001610fed929190611b90565b60408051601f198184030181529082905260e560020a62461bcd0282526104cc91600401611c11565b6110208282610c16565b6105cf576000828152600160208181526040808420600160a060020a0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b61108b8282610c16565b156105cf576000828152600160209081526040808320600160a060020a0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff1661113d5760405160e560020a62461bcd02815260206004820152601460248201527f5061757361626c653a206e6f742070617573656400000000000000000000000060448201526064016104cc565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b604051600160a060020a03909116815260200160405180910390a1565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b600160a060020a038316600090815260036020526040902054600214156112ba576040517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a0383811660048301526024820183905284169063a9059cbb90604401602060405180830381600087803b15801561123357600080fd5b505af1158015611247573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061126b9190611c24565b6105185760405160e560020a62461bcd02815260206004820152600f60248201527f7472616e73666572206661696c6564000000000000000000000000000000000060448201526064016104cc565b6040517f40c10f19000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152602482018390528416906340c10f19906044015b600060405180830381600087803b15801561131e57600080fd5b505af1158015611332573d6000803e3d6000fd5b50505050505050565b60005460ff16156113615760405160e560020a62461bcd0281526004016104cc90611ad0565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a25861116a3390565b600160a060020a038316600090815260036020526040902054600214156114a7576040517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152306024830152604482018390528416906323b872dd90606401602060405180830381600087803b15801561142057600080fd5b505af1158015611434573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906114589190611c24565b6105185760405160e560020a62461bcd02815260206004820152601360248201527f7472616e7366657246726f6d206661696c65640000000000000000000000000060448201526064016104cc565b6040517f79cc6790000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152602482018390528416906379cc679090604401611304565b600081611503576001611506565b60025b600160a060020a038416600081815260036020908152604091829020849055815192835282018390529192507f75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a9101610e37565b60606000611569836002611c70565b611574906002611c8f565b67ffffffffffffffff81111561158c5761158c611ca7565b6040519080825280601f01601f1916602001820160405280156115b6576020820181803683370190505b5090507f3000000000000000000000000000000000000000000000000000000000000000816000815181106115ed576115ed611cd6565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053507f78000000000000000000000000000000000000000000000000000000000000008160018151811061165057611650611cd6565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600061168c846002611c70565b611697906001611c8f565b90505b6001811115611752577f303132333435363738396162636465660000000000000000000000000000000085600f16601081106116d8576116d8611cd6565b1a7f01000000000000000000000000000000000000000000000000000000000000000282828151811061170d5761170d611cd6565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535060109094049361174b81611d05565b905061169a565b5083156117a45760405160e560020a62461bcd02815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e7460448201526064016104cc565b9392505050565b6000602082840312156117bd57600080fd5b81357bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19811681146117a457600080fd5b8035600160a060020a038116811461180157600080fd5b919050565b60008060006060848603121561181b57600080fd5b611824846117ea565b925060208401359150611839604085016117ea565b90509250925092565b60006020828403121561185457600080fd5b5035919050565b6000806040838503121561186e57600080fd5b8235915061187e602084016117ea565b90509250929050565b60008060006060848603121561189c57600080fd5b6118a5846117ea565b92506118b3602085016117ea565b9150604084013590509250925092565b600080600080608085870312156118d957600080fd5b843593506118e9602086016117ea565b92506118f7604086016117ea565b9396929550929360600135925050565b801515811461191557600080fd5b50565b6000806000806080858703121561192e57600080fd5b611937856117ea565b93506020850135925061194c604086016117ea565b9150606085013561195c81611907565b939692955090935050565b6000806040838503121561197a57600080fd5b611983836117ea565b9150602083013561199381611907565b809150509250929050565b6000602082840312156119b057600080fd5b6117a4826117ea565b60208082526024908201527f4552433230204272696467653a204d7573742068617665204d415050494e472060408201527f726f6c6500000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526022908201527f4552433230204272696467653a20746f6b656e206e6f7420636f6e666967757260408201527f6564000000000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526023908201527f4552433230204272696467653a204d757374206861766520504155534552207260408201527f6f6c650000000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526010908201527f5061757361626c653a2070617573656400000000000000000000000000000000604082015260600190565b60005b83811015611b22578181015183820152602001611b0a565b83811115610d1c5750506000910152565b60008151808452611b4b816020860160208601611b07565b601f01601f19169290920160200192915050565b838152600160a060020a0383166020820152606060408201526000611b876060830184611b33565b95945050505050565b7f416363657373436f6e74726f6c3a206163636f756e7420000000000000000000815260008351611bc8816017850160208801611b07565b7f206973206d697373696e6720726f6c65200000000000000000000000000000006017918401918201528351611c05816028840160208801611b07565b01602801949350505050565b6020815260006117a46020830184611b33565b600060208284031215611c3657600080fd5b81516117a481611907565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000816000190483118215151615611c8a57611c8a611c41565b500290565b60008219821115611ca257611ca2611c41565b500190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600081611d1457611d14611c41565b50600019019056fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a264697066735822122043b77e4bf82fcf4687d00938df63328131a967b70b2536cd008e0c7c84780c8c64736f6c63430008090033";

    public static final String FUNC_ADMINTRANSFER_ROLE = "ADMINTRANSFER_ROLE";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_MAPPING_ROLE = "MAPPING_ROLE";

    public static final String FUNC_PAUSER_ROLE = "PAUSER_ROLE";

    public static final String FUNC_ADDCONTRACTFIRSTMAPPING = "addContractFirstMapping";

    public static final String FUNC_ADMINTRANSFER = "adminTransfer";

    public static final String FUNC_CHANGEBLOCKCHAINMAPPING = "changeBlockchainMapping";

    public static final String FUNC_CHANGECONTRACTMAPPING = "changeContractMapping";

    public static final String FUNC_GETBCIDTOKENMAPING = "getBcIdTokenMaping";

    public static final String FUNC_GETREMOTEERC20BRIDGECONTRACT = "getRemoteErc20BridgeContract";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_ISBCIDTOKENALLOWED = "isBcIdTokenAllowed";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_RECEIVEFROMOTHERBLOCKCHAIN = "receiveFromOtherBlockchain";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SETTOKENCONFIG = "setTokenConfig";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TOKENCONTRACTCONFIGURATION = "tokenContractConfiguration";

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

    public static final Event TOKENCONTRACTCONFIG_EVENT = new Event("TokenContractConfig", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERTO_EVENT = new Event("TransferTo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNPAUSED_EVENT = new Event("Unpaused", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected SfcErc20Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SfcErc20Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SfcErc20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SfcErc20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AdminTransferEventResponse> getAdminTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINTRANSFER_EVENT, transactionReceipt);
        ArrayList<AdminTransferEventResponse> responses = new ArrayList<AdminTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminTransferEventResponse typedResponse = new AdminTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._erc20Contract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse._erc20Contract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
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
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
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

    public List<TokenContractConfigEventResponse> getTokenContractConfigEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENCONTRACTCONFIG_EVENT, transactionReceipt);
        ArrayList<TokenContractConfigEventResponse> responses = new ArrayList<TokenContractConfigEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenContractConfigEventResponse typedResponse = new TokenContractConfigEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._thisBcTokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._config = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenContractConfigEventResponse> tokenContractConfigEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TokenContractConfigEventResponse>() {
            @Override
            public TokenContractConfigEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENCONTRACTCONFIG_EVENT, log);
                TokenContractConfigEventResponse typedResponse = new TokenContractConfigEventResponse();
                typedResponse.log = log;
                typedResponse._thisBcTokenContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._config = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenContractConfigEventResponse> tokenContractConfigEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENCONTRACTCONFIG_EVENT));
        return tokenContractConfigEventFlowable(filter);
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
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
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
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> addContractFirstMapping(String _thisBcTokenContract, BigInteger _otherBcId, String _otherTokenContract, Boolean _thisBcMassC) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCONTRACTFIRSTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _otherTokenContract), 
                new org.web3j.abi.datatypes.Bool(_thisBcMassC)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> adminTransfer(String _erc20Contract, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADMINTRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _erc20Contract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeBlockchainMapping(BigInteger _otherBcId, String _otherErc20Bridge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEBLOCKCHAINMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _otherErc20Bridge)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeContractMapping(String _thisBcTokenContract, BigInteger _otherBcId, String _otherTokenContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGECONTRACTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _otherTokenContract)), 
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

    public RemoteFunctionCall<String> getRemoteErc20BridgeContract(BigInteger _bcId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREMOTEERC20BRIDGECONTRACT, 
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

    public RemoteFunctionCall<TransactionReceipt> receiveFromOtherBlockchain(String _destTokenContract, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEFROMOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _destTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
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

    public RemoteFunctionCall<TransactionReceipt> setTokenConfig(String _thisBcTokenContract, Boolean _thisBcMassC) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTOKENCONFIG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.Bool(_thisBcMassC)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> tokenContractConfiguration(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENCONTRACTCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferToOtherBlockchain(BigInteger _destBcId, String _srcTokenContract, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERTOOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_destBcId), 
                new org.web3j.abi.datatypes.Address(160, _srcTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
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
    public static SfcErc20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SfcErc20Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SfcErc20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SfcErc20Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SfcErc20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SfcErc20Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SfcErc20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SfcErc20Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SfcErc20Bridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc20Bridge.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SfcErc20Bridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc20Bridge.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SfcErc20Bridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc20Bridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SfcErc20Bridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _sfcCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sfcCbcContract)));
        return deployRemoteCall(SfcErc20Bridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AdminTransferEventResponse extends BaseEventResponse {
        public String _erc20Contract;

        public String _recipient;

        public BigInteger _amount;
    }

    public static class PausedEventResponse extends BaseEventResponse {
        public String account;
    }

    public static class ReceivedFromEventResponse extends BaseEventResponse {
        public BigInteger _srcBcId;

        public String _srcTokenContract;

        public String _destTokenContract;

        public String _recipient;

        public BigInteger _amount;
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

    public static class TokenContractConfigEventResponse extends BaseEventResponse {
        public String _thisBcTokenContract;

        public BigInteger _config;
    }

    public static class TransferToEventResponse extends BaseEventResponse {
        public BigInteger _destBcId;

        public String _srcTokenContract;

        public String _destTokenContract;

        public String _sender;

        public String _recipient;

        public BigInteger _amount;
    }

    public static class UnpausedEventResponse extends BaseEventResponse {
        public String account;
    }
}
