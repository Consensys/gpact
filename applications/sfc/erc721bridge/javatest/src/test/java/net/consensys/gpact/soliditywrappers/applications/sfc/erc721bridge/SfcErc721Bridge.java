package net.consensys.gpact.soliditywrappers.applications.sfc.erc721bridge;

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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class SfcErc721Bridge extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001ec238038062001ec28339810160408190526200003491620001bb565b6000805460ff1916815533906200004c908262000123565b620000787f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd58262000123565b620000a47f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a8262000123565b620000d07f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c8262000123565b620000fc7fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa08262000123565b50600280546001600160a01b0319166001600160a01b0392909216919091179055620001ed565b6200012f828262000133565b5050565b60008281526001602090815260408083206001600160a01b038516845290915290205460ff166200012f5760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b600060208284031215620001ce57600080fd5b81516001600160a01b0381168114620001e657600080fd5b9392505050565b611cc580620001fd6000396000f3fe608060405234801561001057600080fd5b50600436106101cf5760003560e01c80638456cb5911610104578063d547741f116100a2578063e787282a11610071578063e787282a14610476578063e877a52614610489578063e96dab561461049c578063ef0047cf146104af57600080fd5b8063d547741f14610400578063d6ca5d7814610413578063da72c1e81461043c578063e63ab1e91461044f57600080fd5b8063a217fddf116100de578063a217fddf146103ab578063c22b8699146103b3578063c3be3f36146103c6578063cd594b21146103ed57600080fd5b80638456cb591461037d57806384aea6331461038557806391d148541461039857600080fd5b80632f2ff15d1161017157806343a122241161014b57806343a12224146102f957806348656bc21461030e5780635c975abb1461032157806380ccbde41461032c57600080fd5b80632f2ff15d146102cb57806336568abe146102de5780633f4ba83a146102f157600080fd5b8063228b547e116101ad578063228b547e14610224578063248a9ca3146102375780632e44a5b5146102695780632e77ece7146102a457600080fd5b806301ffc9a7146101d457806302c52db0146101fc57806306b47e9e14610211575b600080fd5b6101e76101e2366004611647565b6104cf565b60405190151581526020015b60405180910390f35b61020f61020a366004611694565b610506565b005b61020f61021f3660046116af565b6105ad565b61020f610232366004611694565b610626565b61025b6102453660046116eb565b6000908152600160208190526040909120015490565b6040519081526020016101f3565b6101e7610277366004611704565b6001600160a01b039081166000908152600460209081526040808320948352939052919091205416151590565b61025b7fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa081565b61020f6102d9366004611704565b6106c0565b61020f6102ec366004611704565b610750565b61020f6107ca565b61025b600080516020611c7083398151915281565b61020f61031c3660046117d3565b61081a565b60005460ff166101e7565b61036561033a366004611704565b6001600160a01b03908116600090815260046020908152604080832094835293905291909120541690565b6040516001600160a01b0390911681526020016101f3565b61020f610b25565b61020f610393366004611845565b610b73565b6101e76103a6366004611704565b610bb8565b61025b600081565b61020f6103c1366004611899565b610be3565b61025b7f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c81565b61020f6103fb3660046118e6565b610ca2565b61020f61040e366004611704565b610f9d565b6103656104213660046116eb565b6000908152600560205260409020546001600160a01b031690565b61020f61044a36600461194e565b61101e565b61025b7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b61020f610484366004611704565b611104565b6101e7610497366004611694565b611166565b61020f6104aa36600461198a565b611184565b61025b6104bd366004611694565b60036020526000908152604090205481565b60006001600160e01b03198216637965db0b60e01b148061050057506301ffc9a760e01b6001600160e01b03198316145b92915050565b6105307fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa033610bb8565b6105555760405162461bcd60e51b815260040161054c906119b4565b60405180910390fd5b6001600160a01b038116600081815260066020908152604091829020805460ff1916905590519182527f6a39f0c1fa5dda09b1d9128adee11b814bc854ad8d4912fb5e9a6b3ec6d6c33491015b60405180910390a150565b6105c5600080516020611c7083398151915233610bb8565b6105e15760405162461bcd60e51b815260040161054c906119fa565b6001600160a01b0383166000908152600360205260409020546106165760405162461bcd60e51b815260040161054c90611a3f565b6106218383836111f7565b505050565b6106507fba0157a524a9edce82088d879a0b2b52012f2f5deaf67385b1c6bf8c1e4f2fa033610bb8565b61066c5760405162461bcd60e51b815260040161054c906119b4565b6001600160a01b038116600081815260066020908152604091829020805460ff1916600117905590519182527f283a1646ffc8ac5a87ea9fcac2a84a1007e1d87f9c64bb19a7120ec657add4ac91016105a2565b600082815260016020819052604090912001546106de905b33610bb8565b6107425760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526e0818591b5a5b881d1bc819dc985b9d608a1b606482015260840161054c565b61074c828261126a565b5050565b6001600160a01b03811633146107c05760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b606482015260840161054c565b61074c82826112d5565b6107f47f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610bb8565b6108105760405162461bcd60e51b815260040161054c90611a82565b61081861133c565b565b60005460ff161561083d5760405162461bcd60e51b815260040161054c90611ac6565b61084633611166565b1561089f5760405162461bcd60e51b8152602060048201526024808201527f45524320373231204272696467653a2053656e6465722069732064656e796c696044820152631cdd195960e21b606482015260840161054c565b6108a883611166565b156108c55760405162461bcd60e51b815260040161054c90611af0565b6108ce32611166565b156108eb5760405162461bcd60e51b815260040161054c90611b37565b6000858152600560205260409020546001600160a01b0316806109615760405162461bcd60e51b815260206004820152602860248201527f45524320373231204272696467653a20426c6f636b636861696e206e6f7420736044820152671d5c1c1bdc9d195960c21b606482015260840161054c565b6001600160a01b0380861660009081526004602090815260408083208a845290915290205416806109fa5760405162461bcd60e51b815260206004820152603e60248201527f45524320373231204272696467653a20546f6b656e206e6f74207472616e736660448201527f657261626c6520746f2072657175657374656420626c6f636b636861696e0000606482015260840161054c565b610a058633866113cf565b6002546040516001600160a01b03909116906392b2c335908990859063cd594b2160e01b90610a3e9087908c908c908c90602401611bd8565b60408051601f198184030181529181526020820180516001600160e01b03166001600160e01b03199485161790525160e086901b9092168252610a85939291600401611c15565b600060405180830381600087803b158015610a9f57600080fd5b505af1158015610ab3573d6000803e3d6000fd5b5050604080518a81526001600160a01b03808b16602083015280861692820192909252336060820152908816608082015260a081018790527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c00190505b60405180910390a150505050505050565b610b4f7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610bb8565b610b6b5760405162461bcd60e51b815260040161054c90611a82565b61081861148b565b60005460ff1615610b965760405162461bcd60e51b815260040161054c90611ac6565b610bb2848484846040518060200160405280600081525061081a565b50505050565b60009182526001602090815260408084206001600160a01b0393909316845291905290205460ff1690565b610bfb600080516020611c7083398151915233610bb8565b610c175760405162461bcd60e51b815260040161054c906119fa565b6001600160a01b03841660009081526003602052604090205415610c8d5760405162461bcd60e51b815260206004820152602760248201527f455243373231204272696467653a20746f6b656e20616c726561647920636f6e604482015266199a59dd5c995960ca1b606482015260840161054c565b610c9784826114e3565b610bb28484846111f7565b60005460ff1615610cc55760405162461bcd60e51b815260040161054c90611ac6565b610cce32611166565b15610ceb5760405162461bcd60e51b815260040161054c90611b37565b610cf483611166565b15610d115760405162461bcd60e51b815260040161054c90611af0565b6002546001600160a01b0316336001600160a01b031614610dc05760405162461bcd60e51b815260206004820152605760248201527f45524320373231204272696467653a2043616e206e6f742070726f636573732060448201527f7472616e73666572732066726f6d20636f6e747261637473206f74686572207460648201527f68616e207468652062726964676520636f6e7472616374000000000000000000608482015260a40161054c565b600080610dcb611512565b90925090506001600160a01b038116610e325760405162461bcd60e51b8152602060048201526024808201527f45524320373231204272696467653a2063616c6c657220636f6e7472616374206044820152630697320360e41b606482015260840161054c565b6000828152600560205260409020546001600160a01b031680610ebf576040805162461bcd60e51b81526020600482015260248101919091527f45524320373231204272696467653a204e6f204552433732312042726964676560448201527f20737570706f7274656420666f7220736f7572636520626c6f636b636861696e606482015260840161054c565b806001600160a01b0316826001600160a01b031614610f375760405162461bcd60e51b815260206004820152602e60248201527f45524320373231204272696467653a20496e636f727265637420736f7572636560448201526d204552433732312042726964676560901b606482015260840161054c565b610f438787878761153a565b604080518481526001600160a01b038085166020830152808a16928201929092529087166060820152608081018690527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a001610b14565b60008281526001602081905260409091200154610fb9906106d8565b6107c05760405162461bcd60e51b815260206004820152603060248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526f2061646d696e20746f207265766f6b6560801b606482015260840161054c565b6110487f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c33610bb8565b6110a85760405162461bcd60e51b815260206004820152602b60248201527f455243373231204272696467653a204d75737420686176652041444d494e545260448201526a414e5346455220726f6c6560a81b606482015260840161054c565b6110b383838361162c565b604080516001600160a01b038086168252841660208201529081018290527f728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358906060015b60405180910390a1505050565b61111c600080516020611c7083398151915233610bb8565b6111385760405162461bcd60e51b815260040161054c906119fa565b60009182526005602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6001600160a01b031660009081526006602052604090205460ff1690565b61119c600080516020611c7083398151915233610bb8565b6111b85760405162461bcd60e51b815260040161054c906119fa565b6001600160a01b0382166000908152600360205260409020546111ed5760405162461bcd60e51b815260040161054c90611a3f565b61074c82826114e3565b6001600160a01b03838116600081815260046020908152604080832087845282529182902080546001600160a01b0319169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830906060016110f7565b6112748282610bb8565b61074c5760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b6112df8282610bb8565b1561074c5760008281526001602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff166113855760405162461bcd60e51b815260206004820152601460248201527314185d5cd8589b194e881b9bdd081c185d5cd95960621b604482015260640161054c565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b6040516001600160a01b03909116815260200160405180910390a1565b6001600160a01b0383166000908152600360205260409020546001141561145e576040516323b872dd60e01b81526001600160a01b038381166004830152306024830152604482018390528416906323b872dd906064015b600060405180830381600087803b15801561144157600080fd5b505af1158015611455573d6000803e3d6000fd5b50505050505050565b604051630852cd8d60e31b8152600481018290526001600160a01b038416906342966c6890602401611427565b60005460ff16156114ae5760405162461bcd60e51b815260040161054c90611ac6565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a2586113b23390565b806114ef5760026114f2565b60015b6001600160a01b0390921660009081526003602052604090209190915550565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b6001600160a01b038416600090815260036020526040902054600114156115c457604051635c46a7ef60e11b81526001600160a01b0385169063b88d4fde9061158d903090879087908790600401611bd8565b600060405180830381600087803b1580156115a757600080fd5b505af11580156115bb573d6000803e3d6000fd5b50505050610bb2565b6040516394d008ef60e01b81526001600160a01b038516906394d008ef906115f490869086908690600401611c48565b600060405180830381600087803b15801561160e57600080fd5b505af1158015611622573d6000803e3d6000fd5b5050505050505050565b6106218383836040518060200160405280600081525061153a565b60006020828403121561165957600080fd5b81356001600160e01b03198116811461167157600080fd5b9392505050565b80356001600160a01b038116811461168f57600080fd5b919050565b6000602082840312156116a657600080fd5b61167182611678565b6000806000606084860312156116c457600080fd5b6116cd84611678565b9250602084013591506116e260408501611678565b90509250925092565b6000602082840312156116fd57600080fd5b5035919050565b6000806040838503121561171757600080fd5b8235915061172760208401611678565b90509250929050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261175757600080fd5b813567ffffffffffffffff8082111561177257611772611730565b604051601f8301601f19908116603f0116810190828211818310171561179a5761179a611730565b816040528381528660208588010111156117b357600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600080600060a086880312156117eb57600080fd5b853594506117fb60208701611678565b935061180960408701611678565b925060608601359150608086013567ffffffffffffffff81111561182c57600080fd5b61183888828901611746565b9150509295509295909350565b6000806000806080858703121561185b57600080fd5b8435935061186b60208601611678565b925061187960408601611678565b9396929550929360600135925050565b8035801515811461168f57600080fd5b600080600080608085870312156118af57600080fd5b6118b885611678565b9350602085013592506118cd60408601611678565b91506118db60608601611889565b905092959194509250565b600080600080608085870312156118fc57600080fd5b61190585611678565b935061191360208601611678565b925060408501359150606085013567ffffffffffffffff81111561193657600080fd5b61194287828801611746565b91505092959194509250565b60008060006060848603121561196357600080fd5b61196c84611678565b925061197a60208501611678565b9150604084013590509250925092565b6000806040838503121561199d57600080fd5b6119a683611678565b915061172760208401611889565b60208082526026908201527f455243373231204272696467653a204d75737420686176652044454e594c49536040820152655420726f6c6560d01b606082015260800190565b60208082526025908201527f455243373231204272696467653a204d7573742068617665204d415050494e4760408201526420726f6c6560d81b606082015260800190565b60208082526023908201527f455243373231204272696467653a20746f6b656e206e6f7420636f6e666967756040820152621c995960ea1b606082015260800190565b60208082526024908201527f455243373231204272696467653a204d75737420686176652050415553455220604082015263726f6c6560e01b606082015260800190565b60208082526010908201526f14185d5cd8589b194e881c185d5cd95960821b604082015260600190565b60208082526027908201527f45524320373231204272696467653a20526563697069656e742069732064656e6040820152661e5b1a5cdd195960ca1b606082015260800190565b60208082526034908201527f45524320373231204272696467653a205472616e73616374696f6e206f7269676040820152731a5b985d1bdc881a5cc819195b9e5b1a5cdd195960621b606082015260800190565b6000815180845260005b81811015611bb157602081850181015186830182015201611b95565b81811115611bc3576000602083870101525b50601f01601f19169290920160200192915050565b6001600160a01b0385811682528416602082015260408101839052608060608201819052600090611c0b90830184611b8b565b9695505050505050565b8381526001600160a01b0383166020820152606060408201819052600090611c3f90830184611b8b565b95945050505050565b60018060a01b0384168152826020820152606060408201526000611c3f6060830184611b8b56fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a26469706673582212206bede7af09dd32472c0f3b8e12f662b1dc7e645531cae261707c7c82739f20e964736f6c634300080a0033";

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
