package net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers;

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
public class GpactERC20Bridge extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162002441380380620024418339810160408190526200003491620001e1565b6000805460ff1916815562000051640100000000620000fa810204565b905062000069600082640100000000620000fe810204565b6200009e7f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd582640100000000620000fe810204565b620000d37f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a82640100000000620000fe810204565b5060028054600160a060020a031916600160a060020a039290921691909117905562000213565b3390565b62000113828264010000000062000117810204565b5050565b6200012c8282640100000000620001b6810204565b62000113576000828152600160208181526040808420600160a060020a0386168552909152909120805460ff1916909117905562000172640100000000620000fa810204565b600160a060020a031681600160a060020a0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b600060208284031215620001f457600080fd5b8151600160a060020a03811681146200020c57600080fd5b9392505050565b61221e80620002236000396000f3fe608060405234801561001057600080fd5b50600436106101775760003560e060020a900480635efe4253116100e257806398e646031161009b578063d547741f11610075578063d547741f14610396578063e63ab1e9146103a9578063e96dab56146103d0578063ef0047cf146103e357600080fd5b806398e6460314610368578063a217fddf1461037b578063c22b86991461038357600080fd5b80635efe4253146102db57806380ccbde4146102ee5780638278ef6d146103275780638456cb591461033a57806384aea6331461034257806391d148541461035557600080fd5b80632e44a5b5116101345780632e44a5b5146102525780632f2ff15d1461028d57806336568abe146102a05780633f4ba83a146102b357806343a12224146102bb5780635c975abb146102d057600080fd5b806301ffc9a71461017c578063069b0084146101a457806306b47e9e146101b95780630dd4b783146101cc578063148d42e41461020d578063248a9ca314610220575b600080fd5b61018f61018a366004611b39565b610403565b60405190151581526020015b60405180910390f35b6101b76101b2366004611b7f565b61046c565b005b6101b76101c7366004611bd4565b61067e565b6101f56101da366004611c10565b600090815260056020526040902054600160a060020a031690565b604051600160a060020a03909116815260200161019b565b6101b761021b366004611c10565b6106fd565b61024461022e366004611c10565b6000908152600160208190526040909120015490565b60405190815260200161019b565b61018f610260366004611c29565b600160a060020a039081166000908152600460209081526040808320948352939052919091205416151590565b6101b761029b366004611c29565b61078c565b6101b76102ae366004611c29565b6107b3565b6101b7610842565b6102446000805160206121c983398151915281565b60005460ff1661018f565b6101b76102e9366004611c10565b610895565b6101f56102fc366004611c29565b600160a060020a03908116600090815260046020908152604080832094835293905291909120541690565b6101b7610335366004611c55565b610919565b6101b7610c89565b6101b7610350366004611c91565b610cda565b61018f610363366004611c29565b610f5c565b6101b7610376366004611c29565b610f87565b610244600081565b6101b7610391366004611ce6565b610ff9565b6101b76103a4366004611c29565b6110da565b6102447f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b6101b76103de366004611d35565b611101565b6102446103f1366004611d6c565b60036020526000908152604090205481565b6000600160e060020a031982167f7965db0b00000000000000000000000000000000000000000000000000000000148061046657507f01ffc9a700000000000000000000000000000000000000000000000000000000600160e060020a03198316145b92915050565b60005460ff161561049b5760405160e560020a62461bcd02815260040161049290611d87565b60405180910390fd5b600084815260056020526040902054600160a060020a0316806104d35760405160e560020a62461bcd02815260040161049290611dbe565b600160a060020a03808516600090815260046020908152604080832089845290915290205416806105195760405160e560020a62461bcd02815260040161049290611e1b565b6105253386898661117a565b600254604051600160a060020a03909116906392b2c33590889085907f8278ef6d00000000000000000000000000000000000000000000000000000000906105759087908b908b90602401611e78565b60408051601f198184030181529181526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff16600160e060020a031990941693909317909252905160e060020a63ffffffff86160281526105dc93929190600401611ef4565b600060405180830381600087803b1580156105f657600080fd5b505af115801561060a573d6000803e3d6000fd5b505060408051898152600160a060020a03808a16602083015280861692820192909252818b166060820152908716608082015260a081018690527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c00190505b60405180910390a150505050505050565b6106966000805160206121c983398151915233610f5c565b6106b55760405160e560020a62461bcd02815260040161049290611f25565b600160a060020a0383166000908152600360205260409020546106ed5760405160e560020a62461bcd02815260040161049290611f82565b6106f88383836112aa565b505050565b6107156000805160206121c983398151915233610f5c565b6107345760405160e560020a62461bcd02815260040161049290611f25565b600081815260066020908152604091829020805460ff191660019081179091558251848152918201527fbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee77991015b60405180910390a150565b600082815260016020819052604090912001546107a98133611333565b6106f8838361139a565b600160a060020a03811633146108345760405160e560020a62461bcd02815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201527f20726f6c657320666f722073656c6600000000000000000000000000000000006064820152608401610492565b61083e8282611405565b5050565b61086c7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610f5c565b61088b5760405160e560020a62461bcd02815260040161049290611fdf565b61089361146c565b565b6108ad6000805160206121c983398151915233610f5c565b6108cc5760405160e560020a62461bcd02815260040161049290611f25565b6000818152600660209081526040808320805460ff191690558051848152918201929092527fbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee7799101610781565b60005460ff161561093f5760405160e560020a62461bcd02815260040161049290611d87565b600254600160a060020a031633600160a060020a0316146109f15760405160e560020a62461bcd02815260206004820152605560248201527f4552433230204272696467653a2043616e206e6f742070726f6365737320747260448201527f616e73666572732066726f6d20636f6e747261637473206f746865722074686160648201527f6e207468652062726964676520636f6e74726163740000000000000000000000608482015260a401610492565b60008060006109fe61150b565b600083815260066020526040902054929550909350915060ff16610a8d5760405160e560020a62461bcd02815260206004820152602b60248201527f455243203230204272696467653a20526f6f7420626c6f636b636861696e206e60448201527f6f7420617070726f7665640000000000000000000000000000000000000000006064820152608401610492565b600160a060020a038116610b0c5760405160e560020a62461bcd02815260206004820152602360248201527f455243203230204272696467653a2063616c6c657220636f6e7472616374206960448201527f73203000000000000000000000000000000000000000000000000000000000006064820152608401610492565b600082815260056020526040902054600160a060020a031680610b9a5760405160e560020a62461bcd02815260206004820152603e60248201527f4552433230204272696467653a204e6f2045524320323020427269646765207360448201527f7570706f7274656420666f7220736f7572636520626c6f636b636861696e00006064820152608401610492565b80600160a060020a031682600160a060020a031614610c245760405160e560020a62461bcd02815260206004820152602c60248201527f4552433230204272696467653a20496e636f727265637420736f75726365204560448201527f52432032302042726964676500000000000000000000000000000000000000006064820152608401610492565b610c2f878787611543565b60408051848152600160a060020a038085166020830152808a16928201929092529087166060820152608081018690527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a00161066d565b610cb37f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610f5c565b610cd25760405160e560020a62461bcd02815260040161049290611fdf565b6108936116cf565b60005460ff1615610d005760405160e560020a62461bcd02815260040161049290611d87565b600254600160a060020a03163314610d835760405160e560020a62461bcd02815260206004820152603c60248201527f43616e206f6e6c792062652063616c6c6564206469726563746c792066726f6d60448201527f2043726f7373636861696e20436f6e74726f6c20436f6e7472616374000000006064820152608401610492565b600084815260056020526040902054600160a060020a031680610dbb5760405160e560020a62461bcd02815260040161049290611dbe565b600160a060020a0380851660009081526004602090815260408083208984529091529020541680610e015760405160e560020a62461bcd02815260040161049290611e1b565b610e0c85328561172a565b600254604051600160a060020a03909116906392b2c33590889085907f8278ef6d0000000000000000000000000000000000000000000000000000000090610e5c9087908b908b90602401611e78565b60408051601f198184030181529181526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff16600160e060020a031990941693909317909252905160e060020a63ffffffff8616028152610ec393929190600401611ef4565b600060405180830381600087803b158015610edd57600080fd5b505af1158015610ef1573d6000803e3d6000fd5b505060408051898152600160a060020a038981166020830152858116828401523260608301528816608082015260a0810187905290517f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b93509081900360c0019150a1505050505050565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b610f9f6000805160206121c983398151915233610f5c565b610fbe5760405160e560020a62461bcd02815260040161049290611f25565b600091825260056020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b6110116000805160206121c983398151915233610f5c565b6110305760405160e560020a62461bcd02815260040161049290611f25565b600160a060020a038416600090815260036020526040902054156110bf5760405160e560020a62461bcd02815260206004820152602660248201527f4552433230204272696467653a20746f6b656e20616c726561647920636f6e6660448201527f69677572656400000000000000000000000000000000000000000000000000006064820152608401610492565b6110c98482611883565b6110d48484846112aa565b50505050565b600082815260016020819052604090912001546110f78133611333565b6106f88383611405565b6111196000805160206121c983398151915233610f5c565b6111385760405160e560020a62461bcd02815260040161049290611f25565b600160a060020a0382166000908152600360205260409020546111705760405160e560020a62461bcd02815260040161049290611f82565b61083e8282611883565b600160a060020a03831660009081526003602052604090205460021415611229576040517f5e61e1c8000000000000000000000000000000000000000000000000000000008152600160a060020a038581166004830152838116602483015230604483015260648201839052841690635e61e1c890608401600060405180830381600087803b15801561120c57600080fd5b505af1158015611220573d6000803e3d6000fd5b505050506110d4565b6040517f54e35b6e000000000000000000000000000000000000000000000000000000008152600160a060020a038416906354e35b6e9061127290879086908690600401611e78565b600060405180830381600087803b15801561128c57600080fd5b505af11580156112a0573d6000803e3d6000fd5b5050505050505050565b600160a060020a038381166000818152600460209081526040808320878452825291829020805473ffffffffffffffffffffffffffffffffffffffff19169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830906060015b60405180910390a1505050565b61133d8282610f5c565b61083e5761135581600160a060020a031660146118e8565b6113608360206118e8565b60405160200161137192919061203c565b60408051601f198184030181529082905260e560020a62461bcd028252610492916004016120bd565b6113a48282610f5c565b61083e576000828152600160208181526040808420600160a060020a0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b61140f8282610f5c565b1561083e576000828152600160209081526040808320600160a060020a0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff166114c15760405160e560020a62461bcd02815260206004820152601460248201527f5061757361626c653a206e6f74207061757365640000000000000000000000006044820152606401610492565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b604051600160a060020a03909116815260200160405180910390a1565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b600160a060020a0383166000908152600360205260409020546002141561164e576040517fa9059cbb000000000000000000000000000000000000000000000000000000008152600160a060020a0383811660048301526024820183905284169063a9059cbb90604401602060405180830381600087803b1580156115c757600080fd5b505af11580156115db573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906115ff91906120d0565b6106f85760405160e560020a62461bcd02815260206004820152600f60248201527f7472616e73666572206661696c656400000000000000000000000000000000006044820152606401610492565b6040517f40c10f19000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152602482018390528416906340c10f19906044015b600060405180830381600087803b1580156116b257600080fd5b505af11580156116c6573d6000803e3d6000fd5b50505050505050565b60005460ff16156116f55760405160e560020a62461bcd02815260040161049290611d87565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a2586114ee3390565b600160a060020a03831660009081526003602052604090205460021415611835576040517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a038416906323b872dd9061179490859030908690600401611e78565b602060405180830381600087803b1580156117ae57600080fd5b505af11580156117c2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906117e691906120d0565b6106f85760405160e560020a62461bcd02815260206004820152601360248201527f7472616e7366657246726f6d206661696c6564000000000000000000000000006044820152606401610492565b6040517f79cc6790000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152602482018390528416906379cc679090604401611698565b600081611891576001611894565b60025b600160a060020a038416600081815260036020908152604091829020849055815192835282018390529192507f75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a9101611326565b606060006118f783600261211c565b61190290600261213b565b67ffffffffffffffff81111561191a5761191a612153565b6040519080825280601f01601f191660200182016040528015611944576020820181803683370190505b5090507f30000000000000000000000000000000000000000000000000000000000000008160008151811061197b5761197b612182565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053507f7800000000000000000000000000000000000000000000000000000000000000816001815181106119de576119de612182565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053506000611a1a84600261211c565b611a2590600161213b565b90505b6001811115611ae0577f303132333435363738396162636465660000000000000000000000000000000085600f1660108110611a6657611a66612182565b1a7f010000000000000000000000000000000000000000000000000000000000000002828281518110611a9b57611a9b612182565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350601090940493611ad9816121b1565b9050611a28565b508315611b325760405160e560020a62461bcd02815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610492565b9392505050565b600060208284031215611b4b57600080fd5b8135600160e060020a031981168114611b3257600080fd5b8035600160a060020a0381168114611b7a57600080fd5b919050565b600080600080600060a08688031215611b9757600080fd5b611ba086611b63565b945060208601359350611bb560408701611b63565b9250611bc360608701611b63565b949793965091946080013592915050565b600080600060608486031215611be957600080fd5b611bf284611b63565b925060208401359150611c0760408501611b63565b90509250925092565b600060208284031215611c2257600080fd5b5035919050565b60008060408385031215611c3c57600080fd5b82359150611c4c60208401611b63565b90509250929050565b600080600060608486031215611c6a57600080fd5b611c7384611b63565b9250611c8160208501611b63565b9150604084013590509250925092565b60008060008060808587031215611ca757600080fd5b84359350611cb760208601611b63565b9250611cc560408601611b63565b9396929550929360600135925050565b8015158114611ce357600080fd5b50565b60008060008060808587031215611cfc57600080fd5b611d0585611b63565b935060208501359250611d1a60408601611b63565b91506060850135611d2a81611cd5565b939692955090935050565b60008060408385031215611d4857600080fd5b611d5183611b63565b91506020830135611d6181611cd5565b809150509250929050565b600060208284031215611d7e57600080fd5b611b3282611b63565b60208082526010908201527f5061757361626c653a2070617573656400000000000000000000000000000000604082015260600190565b60208082526026908201527f4552433230204272696467653a20426c6f636b636861696e206e6f742073757060408201527f706f727465640000000000000000000000000000000000000000000000000000606082015260800190565b6020808252603c908201527f4552433230204272696467653a20546f6b656e206e6f74207472616e7366657260408201527f61626c6520746f2072657175657374656420626c6f636b636861696e00000000606082015260800190565b600160a060020a039384168152919092166020820152604081019190915260600190565b60005b83811015611eb7578181015183820152602001611e9f565b838111156110d45750506000910152565b60008151808452611ee0816020860160208601611e9c565b601f01601f19169290920160200192915050565b838152600160a060020a0383166020820152606060408201526000611f1c6060830184611ec8565b95945050505050565b60208082526024908201527f4552433230204272696467653a204d7573742068617665204d415050494e472060408201527f726f6c6500000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526022908201527f4552433230204272696467653a20746f6b656e206e6f7420636f6e666967757260408201527f6564000000000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526023908201527f4552433230204272696467653a204d757374206861766520504155534552207260408201527f6f6c650000000000000000000000000000000000000000000000000000000000606082015260800190565b7f416363657373436f6e74726f6c3a206163636f756e7420000000000000000000815260008351612074816017850160208801611e9c565b7f206973206d697373696e6720726f6c652000000000000000000000000000000060179184019182015283516120b1816028840160208801611e9c565b01602801949350505050565b602081526000611b326020830184611ec8565b6000602082840312156120e257600080fd5b8151611b3281611cd5565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000816000190483118215151615612136576121366120ed565b500290565b6000821982111561214e5761214e6120ed565b500190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b6000816121c0576121c06120ed565b50600019019056fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a2646970667358221220efaa0353dac78697d8a453368c5ba77d9db16fffbfa5dbbcac56141f53aef06764736f6c63430008090033";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_MAPPING_ROLE = "MAPPING_ROLE";

    public static final String FUNC_PAUSER_ROLE = "PAUSER_ROLE";

    public static final String FUNC_ADDAPPROVEDROOTBCID = "addApprovedRootBcId";

    public static final String FUNC_ADDCONTRACTFIRSTMAPPING = "addContractFirstMapping";

    public static final String FUNC_ADDREMOTEERC20BRIDGE = "addRemoteERC20Bridge";

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

    public static final String FUNC_REMOVEAPPROVEDROOTBCID = "removeApprovedRootBcId";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SETTOKENCONFIG = "setTokenConfig";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TOKENCONTRACTCONFIGURATION = "tokenContractConfiguration";

    public static final String FUNC_TRANSFERFROMACCOUNTTOOTHERBLOCKCHAIN = "transferFromAccountToOtherBlockchain";

    public static final String FUNC_TRANSFERTOOTHERBLOCKCHAIN = "transferToOtherBlockchain";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final Event APPROVEDROOTBCID_EVENT = new Event("ApprovedRootBcId", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
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
    protected GpactERC20Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected GpactERC20Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected GpactERC20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected GpactERC20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovedRootBcIdEventResponse> getApprovedRootBcIdEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVEDROOTBCID_EVENT, transactionReceipt);
        ArrayList<ApprovedRootBcIdEventResponse> responses = new ArrayList<ApprovedRootBcIdEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovedRootBcIdEventResponse typedResponse = new ApprovedRootBcIdEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._approved = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovedRootBcIdEventResponse> approvedRootBcIdEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovedRootBcIdEventResponse>() {
            @Override
            public ApprovedRootBcIdEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVEDROOTBCID_EVENT, log);
                ApprovedRootBcIdEventResponse typedResponse = new ApprovedRootBcIdEventResponse();
                typedResponse.log = log;
                typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._approved = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovedRootBcIdEventResponse> approvedRootBcIdEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVEDROOTBCID_EVENT));
        return approvedRootBcIdEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> addApprovedRootBcId(BigInteger _bcId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDAPPROVEDROOTBCID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<TransactionReceipt> addRemoteERC20Bridge(BigInteger _otherBcId, String _otherErc20Bridge) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREMOTEERC20BRIDGE, 
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

    public RemoteFunctionCall<TransactionReceipt> removeApprovedRootBcId(BigInteger _bcId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEAPPROVEDROOTBCID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_bcId)), 
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

    public RemoteFunctionCall<TransactionReceipt> transferFromAccountToOtherBlockchain(String _sender, BigInteger _destBcId, String _srcTokenContract, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROMACCOUNTTOOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sender), 
                new org.web3j.abi.datatypes.generated.Uint256(_destBcId), 
                new org.web3j.abi.datatypes.Address(160, _srcTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
    public static GpactERC20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new GpactERC20Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static GpactERC20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new GpactERC20Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static GpactERC20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new GpactERC20Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static GpactERC20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new GpactERC20Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<GpactERC20Bridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _gpactCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _gpactCbcContract)));
        return deployRemoteCall(GpactERC20Bridge.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<GpactERC20Bridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _gpactCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _gpactCbcContract)));
        return deployRemoteCall(GpactERC20Bridge.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<GpactERC20Bridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _gpactCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _gpactCbcContract)));
        return deployRemoteCall(GpactERC20Bridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<GpactERC20Bridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _gpactCbcContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _gpactCbcContract)));
        return deployRemoteCall(GpactERC20Bridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ApprovedRootBcIdEventResponse extends BaseEventResponse {
        public BigInteger _bcId;

        public Boolean _approved;
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
