// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package application

import (
	"errors"
	"math/big"
	"strings"

	ethereum "github.com/ethereum/go-ethereum"
	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
	"github.com/ethereum/go-ethereum/event"
)

// Reference imports to suppress errors if they are not otherwise used.
var (
	_ = errors.New
	_ = big.NewInt
	_ = strings.NewReader
	_ = ethereum.NotFound
	_ = bind.Bind
	_ = common.Big1
	_ = types.BloomLookup
	_ = event.NewSubscription
)

// GpactERC20BridgeMetaData contains all meta data concerning the GpactERC20Bridge contract.
var GpactERC20BridgeMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_gpactCbcContract\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"_approved\",\"type\":\"bool\"}],\"name\":\"ApprovedRootBcId\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Paused\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_srcBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"ReceivedFrom\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"previousAdminRole\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"newAdminRole\",\"type\":\"bytes32\"}],\"name\":\"RoleAdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleGranted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleRevoked\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_othercTokenContract\",\"type\":\"address\"}],\"name\":\"TokenContractAddressMappingChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_config\",\"type\":\"uint256\"}],\"name\":\"TokenContractConfig\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_sender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"TransferTo\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Unpaused\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"DEFAULT_ADMIN_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"MAPPING_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PAUSER_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"}],\"name\":\"addApprovedRootBcId\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherTokenContract\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_thisBcMassC\",\"type\":\"bool\"}],\"name\":\"addContractFirstMapping\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherErc20Bridge\",\"type\":\"address\"}],\"name\":\"addRemoteERC20Bridge\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherTokenContract\",\"type\":\"address\"}],\"name\":\"changeContractMapping\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_tokenContract\",\"type\":\"address\"}],\"name\":\"getBcIdTokenMaping\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"}],\"name\":\"getRemoteErc20BridgeContract\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleAdmin\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"hasRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_tokenContract\",\"type\":\"address\"}],\"name\":\"isBcIdTokenAllowed\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"pause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"receiveFromOtherBlockchain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"}],\"name\":\"removeApprovedRootBcId\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"renounceRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"revokeRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_thisBcMassC\",\"type\":\"bool\"}],\"name\":\"setTokenConfig\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"interfaceId\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"tokenContractConfiguration\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_sender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"transferFromAccountToOtherBlockchain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"transferToOtherBlockchain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"unpause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x60806040523480156200001157600080fd5b5060405162001d9938038062001d99833981016040819052620000349162000163565b6000805460ff1916815533906200004c9082620000cb565b620000787f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd582620000cb565b620000a47f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a82620000cb565b50600280546001600160a01b0319166001600160a01b039290921691909117905562000195565b620000d78282620000db565b5050565b60008281526001602090815260408083206001600160a01b038516845290915290205460ff16620000d75760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b6000602082840312156200017657600080fd5b81516001600160a01b03811681146200018e57600080fd5b9392505050565b611bf480620001a56000396000f3fe608060405234801561001057600080fd5b50600436106101735760003560e01c80635efe4253116100de57806398e6460311610097578063d547741f11610071578063d547741f14610392578063e63ab1e9146103a5578063e96dab56146103cc578063ef0047cf146103df57600080fd5b806398e6460314610364578063a217fddf14610377578063c22b86991461037f57600080fd5b80635efe4253146102d757806380ccbde4146102ea5780638278ef6d146103235780638456cb591461033657806384aea6331461033e57806391d148541461035157600080fd5b80632e44a5b5116101305780632e44a5b51461024e5780632f2ff15d1461028957806336568abe1461029c5780633f4ba83a146102af57806343a12224146102b75780635c975abb146102cc57600080fd5b806301ffc9a714610178578063069b0084146101a057806306b47e9e146101b55780630dd4b783146101c8578063148d42e414610209578063248a9ca31461021c575b600080fd5b61018b610186366004611706565b6103ff565b60405190151581526020015b60405180910390f35b6101b36101ae366004611753565b610436565b005b6101b36101c33660046117a8565b610606565b6101f16101d63660046117e4565b6000908152600560205260409020546001600160a01b031690565b6040516001600160a01b039091168152602001610197565b6101b36102173660046117e4565b61067f565b61024061022a3660046117e4565b6000908152600160208190526040909120015490565b604051908152602001610197565b61018b61025c3660046117fd565b6001600160a01b039081166000908152600460209081526040808320948352939052919091205416151590565b6101b36102973660046117fd565b61070b565b6101b36102aa3660046117fd565b61079b565b6101b3610815565b610240600080516020611b9f83398151915281565b60005460ff1661018b565b6101b36102e53660046117e4565b610865565b6101f16102f83660046117fd565b6001600160a01b03908116600090815260046020908152604080832094835293905291909120541690565b6101b3610331366004611829565b6108e6565b6101b3610bff565b6101b361034c366004611865565b610c4d565b61018b61035f3660046117fd565b610e8a565b6101b36103723660046117fd565b610eb5565b610240600081565b6101b361038d3660046118ba565b610f17565b6101b36103a03660046117fd565b610fdb565b6102407f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b6101b36103da366004611909565b61105c565b6102406103ed366004611940565b60036020526000908152604090205481565b60006001600160e01b03198216637965db0b60e01b148061043057506301ffc9a760e01b6001600160e01b03198316145b92915050565b60005460ff16156104625760405162461bcd60e51b81526004016104599061195b565b60405180910390fd5b6000848152600560205260409020546001600160a01b0316806104975760405162461bcd60e51b815260040161045990611985565b6001600160a01b03808516600090815260046020908152604080832089845290915290205416806104da5760405162461bcd60e51b8152600401610459906119cb565b6104e6338689866110cf565b6002546040516001600160a01b03909116906392b2c3359088908590638278ef6d60e01b9061051d9087908b908b90602401611a28565b60408051601f198184030181529181526020820180516001600160e01b03166001600160e01b03199485161790525160e086901b9092168252610564939291600401611a4c565b600060405180830381600087803b15801561057e57600080fd5b505af1158015610592573d6000803e3d6000fd5b5050604080518981526001600160a01b03808a16602083015280861692820192909252818b166060820152908716608082015260a081018690527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c00190505b60405180910390a150505050505050565b61061e600080516020611b9f83398151915233610e8a565b61063a5760405162461bcd60e51b815260040161045990611ab8565b6001600160a01b03831660009081526003602052604090205461066f5760405162461bcd60e51b815260040161045990611afc565b61067a8383836111cd565b505050565b610697600080516020611b9f83398151915233610e8a565b6106b35760405162461bcd60e51b815260040161045990611ab8565b600081815260066020908152604091829020805460ff191660019081179091558251848152918201527fbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee77991015b60405180910390a150565b60008281526001602081905260409091200154610729905b33610e8a565b61078d5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526e0818591b5a5b881d1bc819dc985b9d608a1b6064820152608401610459565b6107978282611249565b5050565b6001600160a01b038116331461080b5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b6064820152608401610459565b61079782826112b4565b61083f7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610e8a565b61085b5760405162461bcd60e51b815260040161045990611b3e565b61086361131b565b565b61087d600080516020611b9f83398151915233610e8a565b6108995760405162461bcd60e51b815260040161045990611ab8565b6000818152600660209081526040808320805460ff191690558051848152918201929092527fbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee7799101610700565b60005460ff16156109095760405162461bcd60e51b81526004016104599061195b565b6002546001600160a01b0316336001600160a01b0316146109b05760405162461bcd60e51b815260206004820152605560248201527f4552433230204272696467653a2043616e206e6f742070726f6365737320747260448201527f616e73666572732066726f6d20636f6e747261637473206f74686572207468616064820152741b881d1a1948189c9a5919d94818dbdb9d1c9858dd605a1b608482015260a401610459565b60008060006109bd6113ae565b600083815260066020526040902054929550909350915060ff16610a375760405162461bcd60e51b815260206004820152602b60248201527f455243203230204272696467653a20526f6f7420626c6f636b636861696e206e60448201526a1bdd08185c1c1c9bdd995960aa1b6064820152608401610459565b6001600160a01b038116610a995760405162461bcd60e51b815260206004820152602360248201527f455243203230204272696467653a2063616c6c657220636f6e7472616374206960448201526207320360ec1b6064820152608401610459565b6000828152600560205260409020546001600160a01b031680610b245760405162461bcd60e51b815260206004820152603e60248201527f4552433230204272696467653a204e6f2045524320323020427269646765207360448201527f7570706f7274656420666f7220736f7572636520626c6f636b636861696e00006064820152608401610459565b806001600160a01b0316826001600160a01b031614610b9a5760405162461bcd60e51b815260206004820152602c60248201527f4552433230204272696467653a20496e636f727265637420736f75726365204560448201526b52432032302042726964676560a01b6064820152608401610459565b610ba58787876113e6565b604080518481526001600160a01b038085166020830152808a16928201929092529087166060820152608081018690527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a0016105f5565b610c297f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610e8a565b610c455760405162461bcd60e51b815260040161045990611b3e565b61086361152f565b60005460ff1615610c705760405162461bcd60e51b81526004016104599061195b565b6002546001600160a01b03163314610cf05760405162461bcd60e51b815260206004820152603c60248201527f43616e206f6e6c792062652063616c6c6564206469726563746c792066726f6d60448201527f2043726f7373636861696e20436f6e74726f6c20436f6e7472616374000000006064820152608401610459565b6000848152600560205260409020546001600160a01b031680610d255760405162461bcd60e51b815260040161045990611985565b6001600160a01b0380851660009081526004602090815260408083208984529091529020541680610d685760405162461bcd60e51b8152600401610459906119cb565b610d73853285611587565b6002546040516001600160a01b03909116906392b2c3359088908590638278ef6d60e01b90610daa9087908b908b90602401611a28565b60408051601f198184030181529181526020820180516001600160e01b03166001600160e01b03199485161790525160e086901b9092168252610df1939291600401611a4c565b600060405180830381600087803b158015610e0b57600080fd5b505af1158015610e1f573d6000803e3d6000fd5b5050604080518981526001600160a01b038981166020830152858116828401523260608301528816608082015260a0810187905290517f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b93509081900360c0019150a1505050505050565b60009182526001602090815260408084206001600160a01b0393909316845291905290205460ff1690565b610ecd600080516020611b9f83398151915233610e8a565b610ee95760405162461bcd60e51b815260040161045990611ab8565b60009182526005602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b610f2f600080516020611b9f83398151915233610e8a565b610f4b5760405162461bcd60e51b815260040161045990611ab8565b6001600160a01b03841660009081526003602052604090205415610fc05760405162461bcd60e51b815260206004820152602660248201527f4552433230204272696467653a20746f6b656e20616c726561647920636f6e666044820152651a59dd5c995960d21b6064820152608401610459565b610fca84826116a1565b610fd58484846111cd565b50505050565b60008281526001602081905260409091200154610ff790610723565b61080b5760405162461bcd60e51b815260206004820152603060248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526f2061646d696e20746f207265766f6b6560801b6064820152608401610459565b611074600080516020611b9f83398151915233610e8a565b6110905760405162461bcd60e51b815260040161045990611ab8565b6001600160a01b0382166000908152600360205260409020546110c55760405162461bcd60e51b815260040161045990611afc565b61079782826116a1565b6001600160a01b0383166000908152600360205260409020546002141561116557604051630bcc3c3960e31b81526001600160a01b038581166004830152838116602483015230604483015260648201839052841690635e61e1c890608401600060405180830381600087803b15801561114857600080fd5b505af115801561115c573d6000803e3d6000fd5b50505050610fd5565b604051632a71adb760e11b81526001600160a01b038416906354e35b6e9061119590879086908690600401611a28565b600060405180830381600087803b1580156111af57600080fd5b505af11580156111c3573d6000803e3d6000fd5b5050505050505050565b6001600160a01b03838116600081815260046020908152604080832087845282529182902080546001600160a01b0319169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830906060015b60405180910390a1505050565b6112538282610e8a565b6107975760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b6112be8282610e8a565b156107975760008281526001602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff166113645760405162461bcd60e51b815260206004820152601460248201527314185d5cd8589b194e881b9bdd081c185d5cd95960621b6044820152606401610459565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b6040516001600160a01b03909116815260200160405180910390a1565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b6001600160a01b038316600090815260036020526040902054600214156114c75760405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb90604401602060405180830381600087803b15801561145157600080fd5b505af1158015611465573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906114899190611b81565b61067a5760405162461bcd60e51b815260206004820152600f60248201526e1d1c985b9cd9995c8819985a5b1959608a1b6044820152606401610459565b6040516340c10f1960e01b81526001600160a01b038381166004830152602482018390528416906340c10f19906044015b600060405180830381600087803b15801561151257600080fd5b505af1158015611526573d6000803e3d6000fd5b50505050505050565b60005460ff16156115525760405162461bcd60e51b81526004016104599061195b565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a2586113913390565b6001600160a01b0383166000908152600360205260409020546002141561166c576040516323b872dd60e01b81526001600160a01b038416906323b872dd906115d890859030908690600401611a28565b602060405180830381600087803b1580156115f257600080fd5b505af1158015611606573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061162a9190611b81565b61067a5760405162461bcd60e51b81526020600482015260136024820152721d1c985b9cd9995c919c9bdb4819985a5b1959606a1b6044820152606401610459565b60405163079cc67960e41b81526001600160a01b038381166004830152602482018390528416906379cc6790906044016114f8565b6000816116af5760016116b2565b60025b6001600160a01b038416600081815260036020908152604091829020849055815192835282018390529192507f75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a910161123c565b60006020828403121561171857600080fd5b81356001600160e01b03198116811461173057600080fd5b9392505050565b80356001600160a01b038116811461174e57600080fd5b919050565b600080600080600060a0868803121561176b57600080fd5b61177486611737565b94506020860135935061178960408701611737565b925061179760608701611737565b949793965091946080013592915050565b6000806000606084860312156117bd57600080fd5b6117c684611737565b9250602084013591506117db60408501611737565b90509250925092565b6000602082840312156117f657600080fd5b5035919050565b6000806040838503121561181057600080fd5b8235915061182060208401611737565b90509250929050565b60008060006060848603121561183e57600080fd5b61184784611737565b925061185560208501611737565b9150604084013590509250925092565b6000806000806080858703121561187b57600080fd5b8435935061188b60208601611737565b925061189960408601611737565b9396929550929360600135925050565b80151581146118b757600080fd5b50565b600080600080608085870312156118d057600080fd5b6118d985611737565b9350602085013592506118ee60408601611737565b915060608501356118fe816118a9565b939692955090935050565b6000806040838503121561191c57600080fd5b61192583611737565b91506020830135611935816118a9565b809150509250929050565b60006020828403121561195257600080fd5b61173082611737565b60208082526010908201526f14185d5cd8589b194e881c185d5cd95960821b604082015260600190565b60208082526026908201527f4552433230204272696467653a20426c6f636b636861696e206e6f74207375706040820152651c1bdc9d195960d21b606082015260800190565b6020808252603c908201527f4552433230204272696467653a20546f6b656e206e6f74207472616e7366657260408201527f61626c6520746f2072657175657374656420626c6f636b636861696e00000000606082015260800190565b6001600160a01b039384168152919092166020820152604081019190915260600190565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b81811015611a8e57858101830151858201608001528201611a72565b81811115611aa0576000608083870101525b50601f01601f19169290920160800195945050505050565b60208082526024908201527f4552433230204272696467653a204d7573742068617665204d415050494e4720604082015263726f6c6560e01b606082015260800190565b60208082526022908201527f4552433230204272696467653a20746f6b656e206e6f7420636f6e6669677572604082015261195960f21b606082015260800190565b60208082526023908201527f4552433230204272696467653a204d75737420686176652050415553455220726040820152626f6c6560e81b606082015260800190565b600060208284031215611b9357600080fd5b8151611730816118a956fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a264697066735822122025ecf20784392ca236e211d69a70c12aeed075994ac69502cba374ac6954ba8364736f6c63430008090033",
}

// GpactERC20BridgeABI is the input ABI used to generate the binding from.
// Deprecated: Use GpactERC20BridgeMetaData.ABI instead.
var GpactERC20BridgeABI = GpactERC20BridgeMetaData.ABI

// GpactERC20BridgeBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use GpactERC20BridgeMetaData.Bin instead.
var GpactERC20BridgeBin = GpactERC20BridgeMetaData.Bin

// DeployGpactERC20Bridge deploys a new Ethereum contract, binding an instance of GpactERC20Bridge to it.
func DeployGpactERC20Bridge(auth *bind.TransactOpts, backend bind.ContractBackend, _gpactCbcContract common.Address) (common.Address, *types.Transaction, *GpactERC20Bridge, error) {
	parsed, err := GpactERC20BridgeMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(GpactERC20BridgeBin), backend, _gpactCbcContract)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &GpactERC20Bridge{GpactERC20BridgeCaller: GpactERC20BridgeCaller{contract: contract}, GpactERC20BridgeTransactor: GpactERC20BridgeTransactor{contract: contract}, GpactERC20BridgeFilterer: GpactERC20BridgeFilterer{contract: contract}}, nil
}

// GpactERC20Bridge is an auto generated Go binding around an Ethereum contract.
type GpactERC20Bridge struct {
	GpactERC20BridgeCaller     // Read-only binding to the contract
	GpactERC20BridgeTransactor // Write-only binding to the contract
	GpactERC20BridgeFilterer   // Log filterer for contract events
}

// GpactERC20BridgeCaller is an auto generated read-only Go binding around an Ethereum contract.
type GpactERC20BridgeCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// GpactERC20BridgeTransactor is an auto generated write-only Go binding around an Ethereum contract.
type GpactERC20BridgeTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// GpactERC20BridgeFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type GpactERC20BridgeFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// GpactERC20BridgeSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type GpactERC20BridgeSession struct {
	Contract     *GpactERC20Bridge // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// GpactERC20BridgeCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type GpactERC20BridgeCallerSession struct {
	Contract *GpactERC20BridgeCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts           // Call options to use throughout this session
}

// GpactERC20BridgeTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type GpactERC20BridgeTransactorSession struct {
	Contract     *GpactERC20BridgeTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts           // Transaction auth options to use throughout this session
}

// GpactERC20BridgeRaw is an auto generated low-level Go binding around an Ethereum contract.
type GpactERC20BridgeRaw struct {
	Contract *GpactERC20Bridge // Generic contract binding to access the raw methods on
}

// GpactERC20BridgeCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type GpactERC20BridgeCallerRaw struct {
	Contract *GpactERC20BridgeCaller // Generic read-only contract binding to access the raw methods on
}

// GpactERC20BridgeTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type GpactERC20BridgeTransactorRaw struct {
	Contract *GpactERC20BridgeTransactor // Generic write-only contract binding to access the raw methods on
}

// NewGpactERC20Bridge creates a new instance of GpactERC20Bridge, bound to a specific deployed contract.
func NewGpactERC20Bridge(address common.Address, backend bind.ContractBackend) (*GpactERC20Bridge, error) {
	contract, err := bindGpactERC20Bridge(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &GpactERC20Bridge{GpactERC20BridgeCaller: GpactERC20BridgeCaller{contract: contract}, GpactERC20BridgeTransactor: GpactERC20BridgeTransactor{contract: contract}, GpactERC20BridgeFilterer: GpactERC20BridgeFilterer{contract: contract}}, nil
}

// NewGpactERC20BridgeCaller creates a new read-only instance of GpactERC20Bridge, bound to a specific deployed contract.
func NewGpactERC20BridgeCaller(address common.Address, caller bind.ContractCaller) (*GpactERC20BridgeCaller, error) {
	contract, err := bindGpactERC20Bridge(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeCaller{contract: contract}, nil
}

// NewGpactERC20BridgeTransactor creates a new write-only instance of GpactERC20Bridge, bound to a specific deployed contract.
func NewGpactERC20BridgeTransactor(address common.Address, transactor bind.ContractTransactor) (*GpactERC20BridgeTransactor, error) {
	contract, err := bindGpactERC20Bridge(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeTransactor{contract: contract}, nil
}

// NewGpactERC20BridgeFilterer creates a new log filterer instance of GpactERC20Bridge, bound to a specific deployed contract.
func NewGpactERC20BridgeFilterer(address common.Address, filterer bind.ContractFilterer) (*GpactERC20BridgeFilterer, error) {
	contract, err := bindGpactERC20Bridge(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeFilterer{contract: contract}, nil
}

// bindGpactERC20Bridge binds a generic wrapper to an already deployed contract.
func bindGpactERC20Bridge(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(GpactERC20BridgeABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_GpactERC20Bridge *GpactERC20BridgeRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _GpactERC20Bridge.Contract.GpactERC20BridgeCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_GpactERC20Bridge *GpactERC20BridgeRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.GpactERC20BridgeTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_GpactERC20Bridge *GpactERC20BridgeRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.GpactERC20BridgeTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_GpactERC20Bridge *GpactERC20BridgeCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _GpactERC20Bridge.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_GpactERC20Bridge *GpactERC20BridgeTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_GpactERC20Bridge *GpactERC20BridgeTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.contract.Transact(opts, method, params...)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) DEFAULTADMINROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "DEFAULT_ADMIN_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.DEFAULTADMINROLE(&_GpactERC20Bridge.CallOpts)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.DEFAULTADMINROLE(&_GpactERC20Bridge.CallOpts)
}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) MAPPINGROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "MAPPING_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeSession) MAPPINGROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.MAPPINGROLE(&_GpactERC20Bridge.CallOpts)
}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) MAPPINGROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.MAPPINGROLE(&_GpactERC20Bridge.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) PAUSERROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "PAUSER_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeSession) PAUSERROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.PAUSERROLE(&_GpactERC20Bridge.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) PAUSERROLE() ([32]byte, error) {
	return _GpactERC20Bridge.Contract.PAUSERROLE(&_GpactERC20Bridge.CallOpts)
}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) GetBcIdTokenMaping(opts *bind.CallOpts, _bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "getBcIdTokenMaping", _bcId, _tokenContract)

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeSession) GetBcIdTokenMaping(_bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	return _GpactERC20Bridge.Contract.GetBcIdTokenMaping(&_GpactERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) GetBcIdTokenMaping(_bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	return _GpactERC20Bridge.Contract.GetBcIdTokenMaping(&_GpactERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) GetRemoteErc20BridgeContract(opts *bind.CallOpts, _bcId *big.Int) (common.Address, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "getRemoteErc20BridgeContract", _bcId)

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeSession) GetRemoteErc20BridgeContract(_bcId *big.Int) (common.Address, error) {
	return _GpactERC20Bridge.Contract.GetRemoteErc20BridgeContract(&_GpactERC20Bridge.CallOpts, _bcId)
}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) GetRemoteErc20BridgeContract(_bcId *big.Int) (common.Address, error) {
	return _GpactERC20Bridge.Contract.GetRemoteErc20BridgeContract(&_GpactERC20Bridge.CallOpts, _bcId)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) GetRoleAdmin(opts *bind.CallOpts, role [32]byte) ([32]byte, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "getRoleAdmin", role)

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _GpactERC20Bridge.Contract.GetRoleAdmin(&_GpactERC20Bridge.CallOpts, role)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _GpactERC20Bridge.Contract.GetRoleAdmin(&_GpactERC20Bridge.CallOpts, role)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) HasRole(opts *bind.CallOpts, role [32]byte, account common.Address) (bool, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "hasRole", role, account)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _GpactERC20Bridge.Contract.HasRole(&_GpactERC20Bridge.CallOpts, role, account)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _GpactERC20Bridge.Contract.HasRole(&_GpactERC20Bridge.CallOpts, role, account)
}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) IsBcIdTokenAllowed(opts *bind.CallOpts, _bcId *big.Int, _tokenContract common.Address) (bool, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "isBcIdTokenAllowed", _bcId, _tokenContract)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeSession) IsBcIdTokenAllowed(_bcId *big.Int, _tokenContract common.Address) (bool, error) {
	return _GpactERC20Bridge.Contract.IsBcIdTokenAllowed(&_GpactERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) IsBcIdTokenAllowed(_bcId *big.Int, _tokenContract common.Address) (bool, error) {
	return _GpactERC20Bridge.Contract.IsBcIdTokenAllowed(&_GpactERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) Paused(opts *bind.CallOpts) (bool, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "paused")

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeSession) Paused() (bool, error) {
	return _GpactERC20Bridge.Contract.Paused(&_GpactERC20Bridge.CallOpts)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) Paused() (bool, error) {
	return _GpactERC20Bridge.Contract.Paused(&_GpactERC20Bridge.CallOpts)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) SupportsInterface(opts *bind.CallOpts, interfaceId [4]byte) (bool, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "supportsInterface", interfaceId)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _GpactERC20Bridge.Contract.SupportsInterface(&_GpactERC20Bridge.CallOpts, interfaceId)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _GpactERC20Bridge.Contract.SupportsInterface(&_GpactERC20Bridge.CallOpts, interfaceId)
}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_GpactERC20Bridge *GpactERC20BridgeCaller) TokenContractConfiguration(opts *bind.CallOpts, arg0 common.Address) (*big.Int, error) {
	var out []interface{}
	err := _GpactERC20Bridge.contract.Call(opts, &out, "tokenContractConfiguration", arg0)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_GpactERC20Bridge *GpactERC20BridgeSession) TokenContractConfiguration(arg0 common.Address) (*big.Int, error) {
	return _GpactERC20Bridge.Contract.TokenContractConfiguration(&_GpactERC20Bridge.CallOpts, arg0)
}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_GpactERC20Bridge *GpactERC20BridgeCallerSession) TokenContractConfiguration(arg0 common.Address) (*big.Int, error) {
	return _GpactERC20Bridge.Contract.TokenContractConfiguration(&_GpactERC20Bridge.CallOpts, arg0)
}

// AddApprovedRootBcId is a paid mutator transaction binding the contract method 0x148d42e4.
//
// Solidity: function addApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) AddApprovedRootBcId(opts *bind.TransactOpts, _bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "addApprovedRootBcId", _bcId)
}

// AddApprovedRootBcId is a paid mutator transaction binding the contract method 0x148d42e4.
//
// Solidity: function addApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) AddApprovedRootBcId(_bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddApprovedRootBcId(&_GpactERC20Bridge.TransactOpts, _bcId)
}

// AddApprovedRootBcId is a paid mutator transaction binding the contract method 0x148d42e4.
//
// Solidity: function addApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) AddApprovedRootBcId(_bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddApprovedRootBcId(&_GpactERC20Bridge.TransactOpts, _bcId)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) AddContractFirstMapping(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "addContractFirstMapping", _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) AddContractFirstMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddContractFirstMapping(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) AddContractFirstMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddContractFirstMapping(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AddRemoteERC20Bridge is a paid mutator transaction binding the contract method 0x98e64603.
//
// Solidity: function addRemoteERC20Bridge(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) AddRemoteERC20Bridge(opts *bind.TransactOpts, _otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "addRemoteERC20Bridge", _otherBcId, _otherErc20Bridge)
}

// AddRemoteERC20Bridge is a paid mutator transaction binding the contract method 0x98e64603.
//
// Solidity: function addRemoteERC20Bridge(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) AddRemoteERC20Bridge(_otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddRemoteERC20Bridge(&_GpactERC20Bridge.TransactOpts, _otherBcId, _otherErc20Bridge)
}

// AddRemoteERC20Bridge is a paid mutator transaction binding the contract method 0x98e64603.
//
// Solidity: function addRemoteERC20Bridge(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) AddRemoteERC20Bridge(_otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.AddRemoteERC20Bridge(&_GpactERC20Bridge.TransactOpts, _otherBcId, _otherErc20Bridge)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) ChangeContractMapping(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "changeContractMapping", _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) ChangeContractMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.ChangeContractMapping(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) ChangeContractMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.ChangeContractMapping(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) GrantRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "grantRole", role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.GrantRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.GrantRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) Pause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "pause")
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) Pause() (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.Pause(&_GpactERC20Bridge.TransactOpts)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) Pause() (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.Pause(&_GpactERC20Bridge.TransactOpts)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) ReceiveFromOtherBlockchain(opts *bind.TransactOpts, _destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "receiveFromOtherBlockchain", _destTokenContract, _recipient, _amount)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) ReceiveFromOtherBlockchain(_destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.ReceiveFromOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _destTokenContract, _recipient, _amount)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) ReceiveFromOtherBlockchain(_destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.ReceiveFromOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _destTokenContract, _recipient, _amount)
}

// RemoveApprovedRootBcId is a paid mutator transaction binding the contract method 0x5efe4253.
//
// Solidity: function removeApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) RemoveApprovedRootBcId(opts *bind.TransactOpts, _bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "removeApprovedRootBcId", _bcId)
}

// RemoveApprovedRootBcId is a paid mutator transaction binding the contract method 0x5efe4253.
//
// Solidity: function removeApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) RemoveApprovedRootBcId(_bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RemoveApprovedRootBcId(&_GpactERC20Bridge.TransactOpts, _bcId)
}

// RemoveApprovedRootBcId is a paid mutator transaction binding the contract method 0x5efe4253.
//
// Solidity: function removeApprovedRootBcId(uint256 _bcId) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) RemoveApprovedRootBcId(_bcId *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RemoveApprovedRootBcId(&_GpactERC20Bridge.TransactOpts, _bcId)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) RenounceRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "renounceRole", role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RenounceRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RenounceRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) RevokeRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "revokeRole", role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RevokeRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.RevokeRole(&_GpactERC20Bridge.TransactOpts, role, account)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) SetTokenConfig(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "setTokenConfig", _thisBcTokenContract, _thisBcMassC)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) SetTokenConfig(_thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.SetTokenConfig(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _thisBcMassC)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) SetTokenConfig(_thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.SetTokenConfig(&_GpactERC20Bridge.TransactOpts, _thisBcTokenContract, _thisBcMassC)
}

// TransferFromAccountToOtherBlockchain is a paid mutator transaction binding the contract method 0x069b0084.
//
// Solidity: function transferFromAccountToOtherBlockchain(address _sender, uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) TransferFromAccountToOtherBlockchain(opts *bind.TransactOpts, _sender common.Address, _destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "transferFromAccountToOtherBlockchain", _sender, _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferFromAccountToOtherBlockchain is a paid mutator transaction binding the contract method 0x069b0084.
//
// Solidity: function transferFromAccountToOtherBlockchain(address _sender, uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) TransferFromAccountToOtherBlockchain(_sender common.Address, _destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.TransferFromAccountToOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _sender, _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferFromAccountToOtherBlockchain is a paid mutator transaction binding the contract method 0x069b0084.
//
// Solidity: function transferFromAccountToOtherBlockchain(address _sender, uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) TransferFromAccountToOtherBlockchain(_sender common.Address, _destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.TransferFromAccountToOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _sender, _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) TransferToOtherBlockchain(opts *bind.TransactOpts, _destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "transferToOtherBlockchain", _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) TransferToOtherBlockchain(_destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.TransferToOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) TransferToOtherBlockchain(_destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.TransferToOtherBlockchain(&_GpactERC20Bridge.TransactOpts, _destBcId, _srcTokenContract, _recipient, _amount)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactor) Unpause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _GpactERC20Bridge.contract.Transact(opts, "unpause")
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeSession) Unpause() (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.Unpause(&_GpactERC20Bridge.TransactOpts)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_GpactERC20Bridge *GpactERC20BridgeTransactorSession) Unpause() (*types.Transaction, error) {
	return _GpactERC20Bridge.Contract.Unpause(&_GpactERC20Bridge.TransactOpts)
}

// GpactERC20BridgeApprovedRootBcIdIterator is returned from FilterApprovedRootBcId and is used to iterate over the raw logs and unpacked data for ApprovedRootBcId events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeApprovedRootBcIdIterator struct {
	Event *GpactERC20BridgeApprovedRootBcId // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeApprovedRootBcIdIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeApprovedRootBcId)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeApprovedRootBcId)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeApprovedRootBcIdIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeApprovedRootBcIdIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeApprovedRootBcId represents a ApprovedRootBcId event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeApprovedRootBcId struct {
	BcId     *big.Int
	Approved bool
	Raw      types.Log // Blockchain specific contextual infos
}

// FilterApprovedRootBcId is a free log retrieval operation binding the contract event 0xbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee779.
//
// Solidity: event ApprovedRootBcId(uint256 _bcId, bool _approved)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterApprovedRootBcId(opts *bind.FilterOpts) (*GpactERC20BridgeApprovedRootBcIdIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "ApprovedRootBcId")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeApprovedRootBcIdIterator{contract: _GpactERC20Bridge.contract, event: "ApprovedRootBcId", logs: logs, sub: sub}, nil
}

// WatchApprovedRootBcId is a free log subscription operation binding the contract event 0xbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee779.
//
// Solidity: event ApprovedRootBcId(uint256 _bcId, bool _approved)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchApprovedRootBcId(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeApprovedRootBcId) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "ApprovedRootBcId")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeApprovedRootBcId)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "ApprovedRootBcId", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseApprovedRootBcId is a log parse operation binding the contract event 0xbf226311bb69e53731fdcbcc29e56e1103488e8988646a6870566d339e2ee779.
//
// Solidity: event ApprovedRootBcId(uint256 _bcId, bool _approved)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseApprovedRootBcId(log types.Log) (*GpactERC20BridgeApprovedRootBcId, error) {
	event := new(GpactERC20BridgeApprovedRootBcId)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "ApprovedRootBcId", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgePausedIterator is returned from FilterPaused and is used to iterate over the raw logs and unpacked data for Paused events raised by the GpactERC20Bridge contract.
type GpactERC20BridgePausedIterator struct {
	Event *GpactERC20BridgePaused // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgePausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgePaused)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgePaused)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgePausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgePausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgePaused represents a Paused event raised by the GpactERC20Bridge contract.
type GpactERC20BridgePaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterPaused is a free log retrieval operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterPaused(opts *bind.FilterOpts) (*GpactERC20BridgePausedIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgePausedIterator{contract: _GpactERC20Bridge.contract, event: "Paused", logs: logs, sub: sub}, nil
}

// WatchPaused is a free log subscription operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchPaused(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgePaused) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgePaused)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "Paused", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParsePaused is a log parse operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParsePaused(log types.Log) (*GpactERC20BridgePaused, error) {
	event := new(GpactERC20BridgePaused)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "Paused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeReceivedFromIterator is returned from FilterReceivedFrom and is used to iterate over the raw logs and unpacked data for ReceivedFrom events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeReceivedFromIterator struct {
	Event *GpactERC20BridgeReceivedFrom // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeReceivedFromIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeReceivedFrom)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeReceivedFrom)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeReceivedFromIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeReceivedFromIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeReceivedFrom represents a ReceivedFrom event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeReceivedFrom struct {
	SrcBcId           *big.Int
	SrcTokenContract  common.Address
	DestTokenContract common.Address
	Recipient         common.Address
	Amount            *big.Int
	Raw               types.Log // Blockchain specific contextual infos
}

// FilterReceivedFrom is a free log retrieval operation binding the contract event 0x3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece1.
//
// Solidity: event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterReceivedFrom(opts *bind.FilterOpts) (*GpactERC20BridgeReceivedFromIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "ReceivedFrom")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeReceivedFromIterator{contract: _GpactERC20Bridge.contract, event: "ReceivedFrom", logs: logs, sub: sub}, nil
}

// WatchReceivedFrom is a free log subscription operation binding the contract event 0x3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece1.
//
// Solidity: event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchReceivedFrom(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeReceivedFrom) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "ReceivedFrom")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeReceivedFrom)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "ReceivedFrom", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseReceivedFrom is a log parse operation binding the contract event 0x3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece1.
//
// Solidity: event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseReceivedFrom(log types.Log) (*GpactERC20BridgeReceivedFrom, error) {
	event := new(GpactERC20BridgeReceivedFrom)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "ReceivedFrom", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeRoleAdminChangedIterator is returned from FilterRoleAdminChanged and is used to iterate over the raw logs and unpacked data for RoleAdminChanged events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleAdminChangedIterator struct {
	Event *GpactERC20BridgeRoleAdminChanged // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeRoleAdminChangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeRoleAdminChanged)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeRoleAdminChanged)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeRoleAdminChangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeRoleAdminChangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeRoleAdminChanged represents a RoleAdminChanged event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleAdminChanged struct {
	Role              [32]byte
	PreviousAdminRole [32]byte
	NewAdminRole      [32]byte
	Raw               types.Log // Blockchain specific contextual infos
}

// FilterRoleAdminChanged is a free log retrieval operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterRoleAdminChanged(opts *bind.FilterOpts, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (*GpactERC20BridgeRoleAdminChangedIterator, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var previousAdminRoleRule []interface{}
	for _, previousAdminRoleItem := range previousAdminRole {
		previousAdminRoleRule = append(previousAdminRoleRule, previousAdminRoleItem)
	}
	var newAdminRoleRule []interface{}
	for _, newAdminRoleItem := range newAdminRole {
		newAdminRoleRule = append(newAdminRoleRule, newAdminRoleItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeRoleAdminChangedIterator{contract: _GpactERC20Bridge.contract, event: "RoleAdminChanged", logs: logs, sub: sub}, nil
}

// WatchRoleAdminChanged is a free log subscription operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchRoleAdminChanged(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeRoleAdminChanged, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (event.Subscription, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var previousAdminRoleRule []interface{}
	for _, previousAdminRoleItem := range previousAdminRole {
		previousAdminRoleRule = append(previousAdminRoleRule, previousAdminRoleItem)
	}
	var newAdminRoleRule []interface{}
	for _, newAdminRoleItem := range newAdminRole {
		newAdminRoleRule = append(newAdminRoleRule, newAdminRoleItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeRoleAdminChanged)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseRoleAdminChanged is a log parse operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseRoleAdminChanged(log types.Log) (*GpactERC20BridgeRoleAdminChanged, error) {
	event := new(GpactERC20BridgeRoleAdminChanged)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeRoleGrantedIterator is returned from FilterRoleGranted and is used to iterate over the raw logs and unpacked data for RoleGranted events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleGrantedIterator struct {
	Event *GpactERC20BridgeRoleGranted // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeRoleGrantedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeRoleGranted)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeRoleGranted)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeRoleGrantedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeRoleGrantedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeRoleGranted represents a RoleGranted event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleGranted struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleGranted is a free log retrieval operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterRoleGranted(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*GpactERC20BridgeRoleGrantedIterator, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var accountRule []interface{}
	for _, accountItem := range account {
		accountRule = append(accountRule, accountItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeRoleGrantedIterator{contract: _GpactERC20Bridge.contract, event: "RoleGranted", logs: logs, sub: sub}, nil
}

// WatchRoleGranted is a free log subscription operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchRoleGranted(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeRoleGranted, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var accountRule []interface{}
	for _, accountItem := range account {
		accountRule = append(accountRule, accountItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeRoleGranted)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleGranted", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseRoleGranted is a log parse operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseRoleGranted(log types.Log) (*GpactERC20BridgeRoleGranted, error) {
	event := new(GpactERC20BridgeRoleGranted)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleGranted", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeRoleRevokedIterator is returned from FilterRoleRevoked and is used to iterate over the raw logs and unpacked data for RoleRevoked events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleRevokedIterator struct {
	Event *GpactERC20BridgeRoleRevoked // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeRoleRevokedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeRoleRevoked)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeRoleRevoked)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeRoleRevokedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeRoleRevokedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeRoleRevoked represents a RoleRevoked event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeRoleRevoked struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleRevoked is a free log retrieval operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterRoleRevoked(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*GpactERC20BridgeRoleRevokedIterator, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var accountRule []interface{}
	for _, accountItem := range account {
		accountRule = append(accountRule, accountItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeRoleRevokedIterator{contract: _GpactERC20Bridge.contract, event: "RoleRevoked", logs: logs, sub: sub}, nil
}

// WatchRoleRevoked is a free log subscription operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchRoleRevoked(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeRoleRevoked, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

	var roleRule []interface{}
	for _, roleItem := range role {
		roleRule = append(roleRule, roleItem)
	}
	var accountRule []interface{}
	for _, accountItem := range account {
		accountRule = append(accountRule, accountItem)
	}
	var senderRule []interface{}
	for _, senderItem := range sender {
		senderRule = append(senderRule, senderItem)
	}

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeRoleRevoked)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseRoleRevoked is a log parse operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseRoleRevoked(log types.Log) (*GpactERC20BridgeRoleRevoked, error) {
	event := new(GpactERC20BridgeRoleRevoked)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeTokenContractAddressMappingChangedIterator is returned from FilterTokenContractAddressMappingChanged and is used to iterate over the raw logs and unpacked data for TokenContractAddressMappingChanged events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTokenContractAddressMappingChangedIterator struct {
	Event *GpactERC20BridgeTokenContractAddressMappingChanged // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeTokenContractAddressMappingChangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeTokenContractAddressMappingChanged)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeTokenContractAddressMappingChanged)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeTokenContractAddressMappingChangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeTokenContractAddressMappingChangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeTokenContractAddressMappingChanged represents a TokenContractAddressMappingChanged event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTokenContractAddressMappingChanged struct {
	ThisBcTokenContract common.Address
	OtherBcId           *big.Int
	OthercTokenContract common.Address
	Raw                 types.Log // Blockchain specific contextual infos
}

// FilterTokenContractAddressMappingChanged is a free log retrieval operation binding the contract event 0x3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830.
//
// Solidity: event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterTokenContractAddressMappingChanged(opts *bind.FilterOpts) (*GpactERC20BridgeTokenContractAddressMappingChangedIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "TokenContractAddressMappingChanged")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeTokenContractAddressMappingChangedIterator{contract: _GpactERC20Bridge.contract, event: "TokenContractAddressMappingChanged", logs: logs, sub: sub}, nil
}

// WatchTokenContractAddressMappingChanged is a free log subscription operation binding the contract event 0x3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830.
//
// Solidity: event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchTokenContractAddressMappingChanged(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeTokenContractAddressMappingChanged) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "TokenContractAddressMappingChanged")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeTokenContractAddressMappingChanged)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "TokenContractAddressMappingChanged", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTokenContractAddressMappingChanged is a log parse operation binding the contract event 0x3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830.
//
// Solidity: event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseTokenContractAddressMappingChanged(log types.Log) (*GpactERC20BridgeTokenContractAddressMappingChanged, error) {
	event := new(GpactERC20BridgeTokenContractAddressMappingChanged)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "TokenContractAddressMappingChanged", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeTokenContractConfigIterator is returned from FilterTokenContractConfig and is used to iterate over the raw logs and unpacked data for TokenContractConfig events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTokenContractConfigIterator struct {
	Event *GpactERC20BridgeTokenContractConfig // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeTokenContractConfigIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeTokenContractConfig)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeTokenContractConfig)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeTokenContractConfigIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeTokenContractConfigIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeTokenContractConfig represents a TokenContractConfig event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTokenContractConfig struct {
	ThisBcTokenContract common.Address
	Config              *big.Int
	Raw                 types.Log // Blockchain specific contextual infos
}

// FilterTokenContractConfig is a free log retrieval operation binding the contract event 0x75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a.
//
// Solidity: event TokenContractConfig(address _thisBcTokenContract, uint256 _config)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterTokenContractConfig(opts *bind.FilterOpts) (*GpactERC20BridgeTokenContractConfigIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "TokenContractConfig")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeTokenContractConfigIterator{contract: _GpactERC20Bridge.contract, event: "TokenContractConfig", logs: logs, sub: sub}, nil
}

// WatchTokenContractConfig is a free log subscription operation binding the contract event 0x75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a.
//
// Solidity: event TokenContractConfig(address _thisBcTokenContract, uint256 _config)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchTokenContractConfig(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeTokenContractConfig) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "TokenContractConfig")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeTokenContractConfig)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "TokenContractConfig", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTokenContractConfig is a log parse operation binding the contract event 0x75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a.
//
// Solidity: event TokenContractConfig(address _thisBcTokenContract, uint256 _config)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseTokenContractConfig(log types.Log) (*GpactERC20BridgeTokenContractConfig, error) {
	event := new(GpactERC20BridgeTokenContractConfig)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "TokenContractConfig", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeTransferToIterator is returned from FilterTransferTo and is used to iterate over the raw logs and unpacked data for TransferTo events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTransferToIterator struct {
	Event *GpactERC20BridgeTransferTo // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeTransferToIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeTransferTo)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeTransferTo)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeTransferToIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeTransferToIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeTransferTo represents a TransferTo event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeTransferTo struct {
	DestBcId          *big.Int
	SrcTokenContract  common.Address
	DestTokenContract common.Address
	Sender            common.Address
	Recipient         common.Address
	Amount            *big.Int
	Raw               types.Log // Blockchain specific contextual infos
}

// FilterTransferTo is a free log retrieval operation binding the contract event 0x752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b.
//
// Solidity: event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterTransferTo(opts *bind.FilterOpts) (*GpactERC20BridgeTransferToIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "TransferTo")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeTransferToIterator{contract: _GpactERC20Bridge.contract, event: "TransferTo", logs: logs, sub: sub}, nil
}

// WatchTransferTo is a free log subscription operation binding the contract event 0x752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b.
//
// Solidity: event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchTransferTo(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeTransferTo) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "TransferTo")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeTransferTo)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "TransferTo", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseTransferTo is a log parse operation binding the contract event 0x752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b.
//
// Solidity: event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _amount)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseTransferTo(log types.Log) (*GpactERC20BridgeTransferTo, error) {
	event := new(GpactERC20BridgeTransferTo)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "TransferTo", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// GpactERC20BridgeUnpausedIterator is returned from FilterUnpaused and is used to iterate over the raw logs and unpacked data for Unpaused events raised by the GpactERC20Bridge contract.
type GpactERC20BridgeUnpausedIterator struct {
	Event *GpactERC20BridgeUnpaused // Event containing the contract specifics and raw log

	contract *bind.BoundContract // Generic contract to use for unpacking event data
	event    string              // Event name to use for unpacking event data

	logs chan types.Log        // Log channel receiving the found contract events
	sub  ethereum.Subscription // Subscription for errors, completion and termination
	done bool                  // Whether the subscription completed delivering logs
	fail error                 // Occurred error to stop iteration
}

// Next advances the iterator to the subsequent event, returning whether there
// are any more events found. In case of a retrieval or parsing error, false is
// returned and Error() can be queried for the exact failure.
func (it *GpactERC20BridgeUnpausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(GpactERC20BridgeUnpaused)
			if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
				it.fail = err
				return false
			}
			it.Event.Raw = log
			return true

		default:
			return false
		}
	}
	// Iterator still in progress, wait for either a data or an error event
	select {
	case log := <-it.logs:
		it.Event = new(GpactERC20BridgeUnpaused)
		if err := it.contract.UnpackLog(it.Event, it.event, log); err != nil {
			it.fail = err
			return false
		}
		it.Event.Raw = log
		return true

	case err := <-it.sub.Err():
		it.done = true
		it.fail = err
		return it.Next()
	}
}

// Error returns any retrieval or parsing error occurred during filtering.
func (it *GpactERC20BridgeUnpausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *GpactERC20BridgeUnpausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// GpactERC20BridgeUnpaused represents a Unpaused event raised by the GpactERC20Bridge contract.
type GpactERC20BridgeUnpaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterUnpaused is a free log retrieval operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) FilterUnpaused(opts *bind.FilterOpts) (*GpactERC20BridgeUnpausedIterator, error) {

	logs, sub, err := _GpactERC20Bridge.contract.FilterLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return &GpactERC20BridgeUnpausedIterator{contract: _GpactERC20Bridge.contract, event: "Unpaused", logs: logs, sub: sub}, nil
}

// WatchUnpaused is a free log subscription operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) WatchUnpaused(opts *bind.WatchOpts, sink chan<- *GpactERC20BridgeUnpaused) (event.Subscription, error) {

	logs, sub, err := _GpactERC20Bridge.contract.WatchLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(GpactERC20BridgeUnpaused)
				if err := _GpactERC20Bridge.contract.UnpackLog(event, "Unpaused", log); err != nil {
					return err
				}
				event.Raw = log

				select {
				case sink <- event:
				case err := <-sub.Err():
					return err
				case <-quit:
					return nil
				}
			case err := <-sub.Err():
				return err
			case <-quit:
				return nil
			}
		}
	}), nil
}

// ParseUnpaused is a log parse operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_GpactERC20Bridge *GpactERC20BridgeFilterer) ParseUnpaused(log types.Log) (*GpactERC20BridgeUnpaused, error) {
	event := new(GpactERC20BridgeUnpaused)
	if err := _GpactERC20Bridge.contract.UnpackLog(event, "Unpaused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
