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
 * <p>Generated with web3j version 4.8.8.
 */
@SuppressWarnings("rawtypes")
public class SfcErc721Bridge extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b506040516200253f3803806200253f83398101604081905262000034916200024b565b6000805460ff191681556200005164010000000062000164810204565b90506200006960008264010000000062000168810204565b6200009e7f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd58264010000000062000168810204565b620000d37f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a8264010000000062000168810204565b620001087f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c8264010000000062000168810204565b6200013d7fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa08264010000000062000168810204565b5060028054600160a060020a031916600160a060020a03929092169190911790556200027d565b3390565b6200017d828264010000000062000181810204565b5050565b62000196828264010000000062000220810204565b6200017d576000828152600160208181526040808420600160a060020a0386168552909152909120805460ff19169091179055620001dc64010000000062000164810204565b600160a060020a031681600160a060020a0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b6000602082840312156200025e57600080fd5b8151600160a060020a03811681146200027657600080fd5b9392505050565b6122b2806200028d6000396000f3fe608060405234801561001057600080fd5b50600436106101ec576000357c0100000000000000000000000000000000000000000000000000000000900480638456cb5911610121578063d547741f116100bf578063e787282a1161008e578063e787282a14610493578063e877a526146104a6578063e96dab56146104b9578063ef0047cf146104cc57600080fd5b8063d547741f1461041d578063d6ca5d7814610430578063da72c1e814610459578063e63ab1e91461046c57600080fd5b8063a217fddf116100fb578063a217fddf146103c8578063c22b8699146103d0578063c3be3f36146103e3578063cd594b211461040a57600080fd5b80638456cb591461039a57806384aea633146103a257806391d14854146103b557600080fd5b80632f2ff15d1161018e57806343a122241161016857806343a122241461031657806348656bc21461032b5780635c975abb1461033e57806380ccbde41461034957600080fd5b80632f2ff15d146102e857806336568abe146102fb5780633f4ba83a1461030e57600080fd5b8063228b547e116101ca578063228b547e14610241578063248a9ca3146102545780632e44a5b5146102865780632e77ece7146102c157600080fd5b806301ffc9a7146101f157806302c52db01461021957806306b47e9e1461022e575b600080fd5b6102046101ff366004611a4b565b6104ec565b60405190151581526020015b60405180910390f35b61022c610227366004611a91565b610555565b005b61022c61023c366004611aac565b6105ff565b61022c61024f366004611a91565b61067e565b610278610262366004611ae8565b6000908152600160208190526040909120015490565b604051908152602001610210565b610204610294366004611b01565b600160a060020a039081166000908152600460209081526040808320948352939052919091205416151590565b6102787fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa081565b61022c6102f6366004611b01565b61071b565b61022c610309366004611b01565b610742565b61022c6107d1565b61027860008051602061225d83398151915281565b61022c610339366004611be9565b610824565b60005460ff16610204565b610382610357366004611b01565b600160a060020a03908116600090815260046020908152604080832094835293905291909120541690565b604051600160a060020a039091168152602001610210565b61022c610bc1565b61022c6103b0366004611c5b565b610c12565b6102046103c3366004611b01565b610c5a565b610278600081565b61022c6103de366004611caf565b610c85565b6102787f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c81565b61022c610418366004611cfc565b610d60565b61022c61042b366004611b01565b611098565b61038261043e366004611ae8565b600090815260056020526040902054600160a060020a031690565b61022c610467366004611d64565b6110bf565b6102787f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b61022c6104a1366004611b01565b6111ba565b6102046104b4366004611a91565b61122c565b61022c6104c7366004611da0565b61124a565b6102786104da366004611a91565b60036020526000908152604090205481565b6000600160e060020a031982167f7965db0b00000000000000000000000000000000000000000000000000000000148061054f57507f01ffc9a700000000000000000000000000000000000000000000000000000000600160e060020a03198316145b92915050565b61057f7fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa033610c5a565b6105a75760405160e560020a62461bcd02815260040161059e90611dca565b60405180910390fd5b600160a060020a038116600081815260066020908152604091829020805460ff1916905590519182527f6a39f0c1fa5dda09b1d9128adee11b814bc854ad8d4912fb5e9a6b3ec6d6c33491015b60405180910390a150565b61061760008051602061225d83398151915233610c5a565b6106365760405160e560020a62461bcd02815260040161059e90611e27565b600160a060020a03831660009081526003602052604090205461066e5760405160e560020a62461bcd02815260040161059e90611e84565b6106798383836112c3565b505050565b6106a87fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa033610c5a565b6106c75760405160e560020a62461bcd02815260040161059e90611dca565b600160a060020a038116600081815260066020908152604091829020805460ff1916600117905590519182527f283a1646ffc8ac5a87ea9fcac2a84a1007e1d87f9c64bb19a7120ec657add4ac91016105f4565b600082815260016020819052604090912001546107388133611343565b61067983836113aa565b600160a060020a03811633146107c35760405160e560020a62461bcd02815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201527f20726f6c657320666f722073656c660000000000000000000000000000000000606482015260840161059e565b6107cd8282611415565b5050565b6107fb7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610c5a565b61081a5760405160e560020a62461bcd02815260040161059e90611ee1565b61082261147c565b565b60005460ff161561084a5760405160e560020a62461bcd02815260040161059e90611f3e565b6108533361122c565b156108c85760405160e560020a62461bcd028152602060048201526024808201527f45524320373231204272696467653a2053656e6465722069732064656e796c6960448201527f7374656400000000000000000000000000000000000000000000000000000000606482015260840161059e565b6108d18361122c565b156108f15760405160e560020a62461bcd02815260040161059e90611f75565b6108fa3261122c565b1561091a5760405160e560020a62461bcd02815260040161059e90611fd2565b600085815260056020526040902054600160a060020a0316806109a85760405160e560020a62461bcd02815260206004820152602860248201527f45524320373231204272696467653a20426c6f636b636861696e206e6f74207360448201527f7570706f72746564000000000000000000000000000000000000000000000000606482015260840161059e565b600160a060020a0380861660009081526004602090815260408083208a84529091529020541680610a445760405160e560020a62461bcd02815260206004820152603e60248201527f45524320373231204272696467653a20546f6b656e206e6f74207472616e736660448201527f657261626c6520746f2072657175657374656420626c6f636b636861696e0000606482015260840161059e565b610a4f86338661151b565b600254604051600160a060020a03909116906392b2c33590899085907fcd594b210000000000000000000000000000000000000000000000000000000090610aa19087908c908c908c90602401612087565b60408051601f198184030181529181526020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff16600160e060020a03199094169390931790925290517c010000000000000000000000000000000000000000000000000000000063ffffffff8616028152610b21939291906004016120c3565b600060405180830381600087803b158015610b3b57600080fd5b505af1158015610b4f573d6000803e3d6000fd5b5050604080518a8152600160a060020a03808b16602083015280861692820192909252336060820152908816608082015260a081018790527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c00190505b60405180910390a150505050505050565b610beb7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610c5a565b610c0a5760405160e560020a62461bcd02815260040161059e90611ee1565b610822611609565b60005460ff1615610c385760405160e560020a62461bcd02815260040161059e90611f3e565b610c548484848460405180602001604052806000815250610824565b50505050565b6000918252600160209081526040808420600160a060020a0393909316845291905290205460ff1690565b610c9d60008051602061225d83398151915233610c5a565b610cbc5760405160e560020a62461bcd02815260040161059e90611e27565b600160a060020a03841660009081526003602052604090205415610d4b5760405160e560020a62461bcd02815260206004820152602760248201527f455243373231204272696467653a20746f6b656e20616c726561647920636f6e60448201527f6669677572656400000000000000000000000000000000000000000000000000606482015260840161059e565b610d558482611664565b610c548484846112c3565b60005460ff1615610d865760405160e560020a62461bcd02815260040161059e90611f3e565b610d8f3261122c565b15610daf5760405160e560020a62461bcd02815260040161059e90611fd2565b610db88361122c565b15610dd85760405160e560020a62461bcd02815260040161059e90611f75565b600254600160a060020a031633600160a060020a031614610e8a5760405160e560020a62461bcd02815260206004820152605760248201527f45524320373231204272696467653a2043616e206e6f742070726f636573732060448201527f7472616e73666572732066726f6d20636f6e747261637473206f74686572207460648201527f68616e207468652062726964676520636f6e7472616374000000000000000000608482015260a40161059e565b600080610e95611693565b9092509050600160a060020a038116610f185760405160e560020a62461bcd028152602060048201526024808201527f45524320373231204272696467653a2063616c6c657220636f6e74726163742060448201527f6973203000000000000000000000000000000000000000000000000000000000606482015260840161059e565b600082815260056020526040902054600160a060020a031680610fa8576040805160e560020a62461bcd0281526020600482015260248101919091527f45524320373231204272696467653a204e6f204552433732312042726964676560448201527f20737570706f7274656420666f7220736f7572636520626c6f636b636861696e606482015260840161059e565b80600160a060020a031682600160a060020a0316146110325760405160e560020a62461bcd02815260206004820152602e60248201527f45524320373231204272696467653a20496e636f727265637420736f7572636560448201527f2045524337323120427269646765000000000000000000000000000000000000606482015260840161059e565b61103e878787876116bb565b60408051848152600160a060020a038085166020830152808a16928201929092529087166060820152608081018690527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a001610bb0565b600082815260016020819052604090912001546110b58133611343565b6106798383611415565b6110e97f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c33610c5a565b61115e5760405160e560020a62461bcd02815260206004820152602b60248201527f455243373231204272696467653a204d75737420686176652041444d494e545260448201527f414e5346455220726f6c65000000000000000000000000000000000000000000606482015260840161059e565b6111698383836117df565b60408051600160a060020a038086168252841660208201529081018290527f728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358906060015b60405180910390a1505050565b6111d260008051602061225d83398151915233610c5a565b6111f15760405160e560020a62461bcd02815260040161059e90611e27565b600091825260056020526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b600160a060020a031660009081526006602052604090205460ff1690565b61126260008051602061225d83398151915233610c5a565b6112815760405160e560020a62461bcd02815260040161059e90611e27565b600160a060020a0382166000908152600360205260409020546112b95760405160e560020a62461bcd02815260040161059e90611e84565b6107cd8282611664565b600160a060020a038381166000818152600460209081526040808320878452825291829020805473ffffffffffffffffffffffffffffffffffffffff19169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830906060016111ad565b61134d8282610c5a565b6107cd5761136581600160a060020a031660146117fa565b6113708360206117fa565b6040516020016113819291906120f4565b60408051601f198184030181529082905260e560020a62461bcd02825261059e91600401612175565b6113b48282610c5a565b6107cd576000828152600160208181526040808420600160a060020a0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b61141f8282610c5a565b156107cd576000828152600160209081526040808320600160a060020a0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff166114d15760405160e560020a62461bcd02815260206004820152601460248201527f5061757361626c653a206e6f7420706175736564000000000000000000000000604482015260640161059e565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b604051600160a060020a03909116815260200160405180910390a1565b600160a060020a038316600090815260036020526040902054600114156115c3576040517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a038381166004830152306024830152604482018390528416906323b872dd906064015b600060405180830381600087803b1580156115a657600080fd5b505af11580156115ba573d6000803e3d6000fd5b50505050505050565b6040517f42966c6800000000000000000000000000000000000000000000000000000000815260048101829052600160a060020a038416906342966c689060240161158c565b60005460ff161561162f5760405160e560020a62461bcd02815260040161059e90611f3e565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a2586114fe3390565b80611670576002611673565b60015b600160a060020a0390921660009081526003602052604090209190915550565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b600160a060020a0384166000908152600360205260409020546001141561175e576040517fb88d4fde000000000000000000000000000000000000000000000000000000008152600160a060020a0385169063b88d4fde90611727903090879087908790600401612087565b600060405180830381600087803b15801561174157600080fd5b505af1158015611755573d6000803e3d6000fd5b50505050610c54565b6040517f94d008ef000000000000000000000000000000000000000000000000000000008152600160a060020a038516906394d008ef906117a790869086908690600401612188565b600060405180830381600087803b1580156117c157600080fd5b505af11580156117d5573d6000803e3d6000fd5b5050505050505050565b610679838383604051806020016040528060008152506116bb565b606060006118098360026121df565b6118149060026121fe565b67ffffffffffffffff81111561182c5761182c611b2d565b6040519080825280601f01601f191660200182016040528015611856576020820181803683370190505b5090507f30000000000000000000000000000000000000000000000000000000000000008160008151811061188d5761188d612216565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053507f7800000000000000000000000000000000000000000000000000000000000000816001815181106118f0576118f0612216565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600061192c8460026121df565b6119379060016121fe565b90505b60018111156119f2577f303132333435363738396162636465660000000000000000000000000000000085600f166010811061197857611978612216565b1a7f0100000000000000000000000000000000000000000000000000000000000000028282815181106119ad576119ad612216565b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053506010909404936119eb81612245565b905061193a565b508315611a445760405160e560020a62461bcd02815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e74604482015260640161059e565b9392505050565b600060208284031215611a5d57600080fd5b8135600160e060020a031981168114611a4457600080fd5b8035600160a060020a0381168114611a8c57600080fd5b919050565b600060208284031215611aa357600080fd5b611a4482611a75565b600080600060608486031215611ac157600080fd5b611aca84611a75565b925060208401359150611adf60408501611a75565b90509250925092565b600060208284031215611afa57600080fd5b5035919050565b60008060408385031215611b1457600080fd5b82359150611b2460208401611a75565b90509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600082601f830112611b6d57600080fd5b813567ffffffffffffffff80821115611b8857611b88611b2d565b604051601f8301601f19908116603f01168101908282118183101715611bb057611bb0611b2d565b81604052838152866020858801011115611bc957600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600080600060a08688031215611c0157600080fd5b85359450611c1160208701611a75565b9350611c1f60408701611a75565b925060608601359150608086013567ffffffffffffffff811115611c4257600080fd5b611c4e88828901611b5c565b9150509295509295909350565b60008060008060808587031215611c7157600080fd5b84359350611c8160208601611a75565b9250611c8f60408601611a75565b9396929550929360600135925050565b80358015158114611a8c57600080fd5b60008060008060808587031215611cc557600080fd5b611cce85611a75565b935060208501359250611ce360408601611a75565b9150611cf160608601611c9f565b905092959194509250565b60008060008060808587031215611d1257600080fd5b611d1b85611a75565b9350611d2960208601611a75565b925060408501359150606085013567ffffffffffffffff811115611d4c57600080fd5b611d5887828801611b5c565b91505092959194509250565b600080600060608486031215611d7957600080fd5b611d8284611a75565b9250611d9060208501611a75565b9150604084013590509250925092565b60008060408385031215611db357600080fd5b611dbc83611a75565b9150611b2460208401611c9f565b60208082526026908201527f455243373231204272696467653a204d75737420686176652044454e594c495360408201527f5420726f6c650000000000000000000000000000000000000000000000000000606082015260800190565b60208082526025908201527f455243373231204272696467653a204d7573742068617665204d415050494e4760408201527f20726f6c65000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526023908201527f455243373231204272696467653a20746f6b656e206e6f7420636f6e6669677560408201527f7265640000000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526024908201527f455243373231204272696467653a204d7573742068617665205041555345522060408201527f726f6c6500000000000000000000000000000000000000000000000000000000606082015260800190565b60208082526010908201527f5061757361626c653a2070617573656400000000000000000000000000000000604082015260600190565b60208082526027908201527f45524320373231204272696467653a20526563697069656e742069732064656e60408201527f796c697374656400000000000000000000000000000000000000000000000000606082015260800190565b60208082526034908201527f45524320373231204272696467653a205472616e73616374696f6e206f72696760408201527f696e61746f722069732064656e796c6973746564000000000000000000000000606082015260800190565b60005b8381101561204a578181015183820152602001612032565b83811115610c545750506000910152565b6000815180845261207381602086016020860161202f565b601f01601f19169290920160200192915050565b6000600160a060020a038087168352808616602084015250836040830152608060608301526120b9608083018461205b565b9695505050505050565b838152600160a060020a03831660208201526060604082015260006120eb606083018461205b565b95945050505050565b7f416363657373436f6e74726f6c3a206163636f756e742000000000000000000081526000835161212c81601785016020880161202f565b7f206973206d697373696e6720726f6c6520000000000000000000000000000000601791840191820152835161216981602884016020880161202f565b01602801949350505050565b602081526000611a44602083018461205b565b600160a060020a03841681528260208201526060604082015260006120eb606083018461205b565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60008160001904831182151516156121f9576121f96121b0565b500290565b60008219821115612211576122116121b0565b500190565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b600081612254576122546121b0565b50600019019056fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a2646970667358221220ddef1f6798288186e8eb476ba5750571d186c5148c4fe3a2a877963f12511ff064736f6c63430008090033";

    public static final String FUNC_ADMINTRANSFER_ROLE = "ADMINTRANSFER_ROLE";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_DENYLIST_ROLE = "DENYLIST_ROLE";

    public static final String FUNC_MAPPING_ROLE = "MAPPING_ROLE";

    public static final String FUNC_PAUSER_ROLE = "PAUSER_ROLE";

    public static final String FUNC_ADDCONTRACTFIRSTMAPPING = "addContractFirstMapping";

    public static final String FUNC_ADDTODENYLIST = "addToDenylist";

    public static final String FUNC_ADMINTRANSFER = "adminTransfer";

    public static final String FUNC_CHANGEBLOCKCHAINMAPPING = "changeBlockchainMapping";

    public static final String FUNC_CHANGECONTRACTMAPPING = "changeContractMapping";

    public static final String FUNC_GETBCIDTOKENMAPING = "getBcIdTokenMaping";

    public static final String FUNC_GETREMOTEERC721BRIDGECONTRACT = "getRemoteErc721BridgeContract";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_ISBCIDTOKENALLOWED = "isBcIdTokenAllowed";

    public static final String FUNC_ISDENYLISTED = "isDenylisted";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_RECEIVEFROMOTHERBLOCKCHAIN = "receiveFromOtherBlockchain";

    public static final String FUNC_REMOVEFROMDENYLIST = "removeFromDenylist";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SETTOKENCONFIG = "setTokenConfig";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TOKENCONTRACTCONFIGURATION = "tokenContractConfiguration";

    public static final String FUNC_transferToOtherBlockchain = "transferToOtherBlockchain";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final Event ADDRESSDENYLISTED_EVENT = new Event("AddressDenylisted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event ADDRESSREMOVEDFROMDENYLIST_EVENT = new Event("AddressRemovedFromDenylist", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

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

    public List<AddressDenylistedEventResponse> getAddressDenylistedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRESSDENYLISTED_EVENT, transactionReceipt);
        ArrayList<AddressDenylistedEventResponse> responses = new ArrayList<AddressDenylistedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressDenylistedEventResponse typedResponse = new AddressDenylistedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddressDenylistedEventResponse> addressDenylistedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AddressDenylistedEventResponse>() {
            @Override
            public AddressDenylistedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRESSDENYLISTED_EVENT, log);
                AddressDenylistedEventResponse typedResponse = new AddressDenylistedEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddressDenylistedEventResponse> addressDenylistedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSDENYLISTED_EVENT));
        return addressDenylistedEventFlowable(filter);
    }

    public List<AddressRemovedFromDenylistEventResponse> getAddressRemovedFromDenylistEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRESSREMOVEDFROMDENYLIST_EVENT, transactionReceipt);
        ArrayList<AddressRemovedFromDenylistEventResponse> responses = new ArrayList<AddressRemovedFromDenylistEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressRemovedFromDenylistEventResponse typedResponse = new AddressRemovedFromDenylistEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddressRemovedFromDenylistEventResponse> addressRemovedFromDenylistEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AddressRemovedFromDenylistEventResponse>() {
            @Override
            public AddressRemovedFromDenylistEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRESSREMOVEDFROMDENYLIST_EVENT, log);
                AddressRemovedFromDenylistEventResponse typedResponse = new AddressRemovedFromDenylistEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddressRemovedFromDenylistEventResponse> addressRemovedFromDenylistEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSREMOVEDFROMDENYLIST_EVENT));
        return addressRemovedFromDenylistEventFlowable(filter);
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

    public RemoteFunctionCall<byte[]> DENYLIST_ROLE() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DENYLIST_ROLE, 
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

    public RemoteFunctionCall<TransactionReceipt> addContractFirstMapping(String _thisBcTokenContract, BigInteger _otherBcId, String _otherTokenContract, Boolean _thisIsTheHomeBlockchain) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCONTRACTFIRSTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBcId), 
                new org.web3j.abi.datatypes.Address(160, _otherTokenContract), 
                new org.web3j.abi.datatypes.Bool(_thisIsTheHomeBlockchain)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToDenylist(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTODENYLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<Boolean> isDenylisted(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISDENYLISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
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

    public RemoteFunctionCall<TransactionReceipt> receiveFromOtherBlockchain(String _destTokenContract, String _recipient, BigInteger _tokenId, byte[] _data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEFROMOTHERBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _destTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeFromDenylist(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEFROMDENYLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
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

    public RemoteFunctionCall<TransactionReceipt> setTokenConfig(String _thisBcTokenContract, Boolean _thisIsTheHomeBlockchain) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTOKENCONFIG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _thisBcTokenContract), 
                new org.web3j.abi.datatypes.Bool(_thisIsTheHomeBlockchain)), 
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

    public RemoteFunctionCall<TransactionReceipt> transferToOtherBlockchain(BigInteger _destBcId, String _srcTokenContract, String _recipient, BigInteger _tokenId, byte[] _data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_transferToOtherBlockchain, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_destBcId), 
                new org.web3j.abi.datatypes.Address(160, _srcTokenContract), 
                new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferToOtherBlockchain(BigInteger _destBcId, String _srcTokenContract, String _recipient, BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_transferToOtherBlockchain, 
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

    public static class AddressDenylistedEventResponse extends BaseEventResponse {
        public String _address;
    }

    public static class AddressRemovedFromDenylistEventResponse extends BaseEventResponse {
        public String _address;
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
