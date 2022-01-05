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

// SfcERC20BridgeMetaData contains all meta data concerning the SfcERC20Bridge contract.
var SfcERC20BridgeMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_sfcCbcContract\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_erc20Contract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"AdminTransfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Paused\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_srcBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"ReceivedFrom\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"previousAdminRole\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"newAdminRole\",\"type\":\"bytes32\"}],\"name\":\"RoleAdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleGranted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleRevoked\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_othercTokenContract\",\"type\":\"address\"}],\"name\":\"TokenContractAddressMappingChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_config\",\"type\":\"uint256\"}],\"name\":\"TokenContractConfig\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_sender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"TransferTo\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Unpaused\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"ADMINTRANSFER_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"DEFAULT_ADMIN_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"MAPPING_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PAUSER_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherTokenContract\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_thisBcMassC\",\"type\":\"bool\"}],\"name\":\"addContractFirstMapping\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_erc20Contract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"adminTransfer\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherErc20Bridge\",\"type\":\"address\"}],\"name\":\"changeBlockchainMapping\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_otherBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_otherTokenContract\",\"type\":\"address\"}],\"name\":\"changeContractMapping\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_tokenContract\",\"type\":\"address\"}],\"name\":\"getBcIdTokenMaping\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"}],\"name\":\"getRemoteErc20BridgeContract\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleAdmin\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"hasRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_tokenContract\",\"type\":\"address\"}],\"name\":\"isBcIdTokenAllowed\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"pause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_destTokenContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"receiveFromOtherBlockchain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"renounceRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"revokeRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_thisBcTokenContract\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_thisBcMassC\",\"type\":\"bool\"}],\"name\":\"setTokenConfig\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"interfaceId\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"tokenContractConfiguration\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_srcTokenContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_amount\",\"type\":\"uint256\"}],\"name\":\"transferToOtherBlockchain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"unpause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x60806040523480156200001157600080fd5b506040516200192f3803806200192f83398101604081905262000034916200018f565b6000805460ff1916815533906200004c9082620000f7565b620000787f0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd582620000f7565b620000a47f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a82620000f7565b620000d07f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c82620000f7565b50600280546001600160a01b0319166001600160a01b0392909216919091179055620001c1565b62000103828262000107565b5050565b60008281526001602090815260408083206001600160a01b038516845290915290205460ff16620001035760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b600060208284031215620001a257600080fd5b81516001600160a01b0381168114620001ba57600080fd5b9392505050565b61175e80620001d16000396000f3fe608060405234801561001057600080fd5b50600436106101585760003560e01c80638456cb59116100c3578063d547741f1161007c578063d547741f14610352578063da72c1e814610365578063e63ab1e914610378578063e787282a1461039f578063e96dab56146103b2578063ef0047cf146103c557600080fd5b80638456cb59146102e257806384aea633146102ea57806391d14854146102fd578063a217fddf14610310578063c22b869914610318578063c3be3f361461032b57600080fd5b806336568abe1161011557806336568abe1461025b5780633f4ba83a1461026e57806343a12224146102765780635c975abb1461028b57806380ccbde4146102965780638278ef6d146102cf57600080fd5b806301ffc9a71461015d57806306b47e9e146101855780630dd4b7831461019a578063248a9ca3146101db5780632e44a5b51461020d5780632f2ff15d14610248575b600080fd5b61017061016b36600461138c565b6103e5565b60405190151581526020015b60405180910390f35b6101986101933660046113d9565b61041c565b005b6101c36101a8366004611415565b6000908152600560205260409020546001600160a01b031690565b6040516001600160a01b03909116815260200161017c565b6101ff6101e9366004611415565b6000908152600160208190526040909120015490565b60405190815260200161017c565b61017061021b36600461142e565b6001600160a01b039081166000908152600460209081526040808320948352939052919091205416151590565b61019861025636600461142e565b61049e565b61019861026936600461142e565b61052e565b6101986105a8565b6101ff60008051602061170983398151915281565b60005460ff16610170565b6101c36102a436600461142e565b6001600160a01b03908116600090815260046020908152604080832094835293905291909120541690565b6101986102dd36600461145a565b6105f8565b6101986108a6565b6101986102f8366004611496565b6108f4565b61017061030b36600461142e565b610b3c565b6101ff600081565b6101986103263660046114eb565b610b67565b6101ff7f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c81565b61019861036036600461142e565b610c2b565b61019861037336600461145a565b610cac565b6101ff7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b6101986103ad36600461142e565b610d91565b6101986103c036600461153a565b610df3565b6101ff6103d3366004611571565b60036020526000908152604090205481565b60006001600160e01b03198216637965db0b60e01b148061041657506301ffc9a760e01b6001600160e01b03198316145b92915050565b61043460008051602061170983398151915233610b3c565b6104595760405162461bcd60e51b81526004016104509061158c565b60405180910390fd5b6001600160a01b03831660009081526003602052604090205461048e5760405162461bcd60e51b8152600401610450906115d0565b610499838383610e66565b505050565b600082815260016020819052604090912001546104bc905b33610b3c565b6105205760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526e0818591b5a5b881d1bc819dc985b9d608a1b6064820152608401610450565b61052a8282610ed9565b5050565b6001600160a01b038116331461059e5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b6064820152608401610450565b61052a8282610f44565b6105d27f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610b3c565b6105ee5760405162461bcd60e51b815260040161045090611612565b6105f6610fab565b565b60005460ff161561061b5760405162461bcd60e51b815260040161045090611655565b6002546001600160a01b0316336001600160a01b0316146106c25760405162461bcd60e51b815260206004820152605560248201527f4552433230204272696467653a2043616e206e6f742070726f6365737320747260448201527f616e73666572732066726f6d20636f6e747261637473206f74686572207468616064820152741b881d1a1948189c9a5919d94818dbdb9d1c9858dd605a1b608482015260a401610450565b6000806106cd61103e565b90925090506001600160a01b0381166107345760405162461bcd60e51b815260206004820152602360248201527f455243203230204272696467653a2063616c6c657220636f6e7472616374206960448201526207320360ec1b6064820152608401610450565b6000828152600560205260409020546001600160a01b0316806107bf5760405162461bcd60e51b815260206004820152603e60248201527f4552433230204272696467653a204e6f2045524320323020427269646765207360448201527f7570706f7274656420666f7220736f7572636520626c6f636b636861696e00006064820152608401610450565b806001600160a01b0316826001600160a01b0316146108355760405162461bcd60e51b815260206004820152602c60248201527f4552433230204272696467653a20496e636f727265637420736f75726365204560448201526b52432032302042726964676560a01b6064820152608401610450565b610840868686611066565b604080518481526001600160a01b038085166020830152808916928201929092529086166060820152608081018590527f3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece19060a0015b60405180910390a1505050505050565b6108d07f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33610b3c565b6108ec5760405162461bcd60e51b815260040161045090611612565b6105f66111af565b60005460ff16156109175760405162461bcd60e51b815260040161045090611655565b6000848152600560205260409020546001600160a01b03168061098b5760405162461bcd60e51b815260206004820152602660248201527f4552433230204272696467653a20426c6f636b636861696e206e6f74207375706044820152651c1bdc9d195960d21b6064820152608401610450565b6001600160a01b0380851660009081526004602090815260408083208984529091529020541680610a245760405162461bcd60e51b815260206004820152603c60248201527f4552433230204272696467653a20546f6b656e206e6f74207472616e7366657260448201527f61626c6520746f2072657175657374656420626c6f636b636861696e000000006064820152608401610450565b610a2f853385611207565b600254604080516001600160a01b0384811660248301528781166044830152606480830188905283518084039091018152608490920183526020820180516001600160e01b0316638278ef6d60e01b17905291516392b2c33560e01b815291909216916392b2c33591610aa9918a9187919060040161167f565b600060405180830381600087803b158015610ac357600080fd5b505af1158015610ad7573d6000803e3d6000fd5b5050604080518981526001600160a01b03808a16602083015280861692820192909252336060820152908716608082015260a081018690527f752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b925060c0019050610896565b60009182526001602090815260408084206001600160a01b0393909316845291905290205460ff1690565b610b7f60008051602061170983398151915233610b3c565b610b9b5760405162461bcd60e51b81526004016104509061158c565b6001600160a01b03841660009081526003602052604090205415610c105760405162461bcd60e51b815260206004820152602660248201527f4552433230204272696467653a20746f6b656e20616c726561647920636f6e666044820152651a59dd5c995960d21b6064820152608401610450565b610c1a8482611327565b610c25848484610e66565b50505050565b60008281526001602081905260409091200154610c47906104b6565b61059e5760405162461bcd60e51b815260206004820152603060248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526f2061646d696e20746f207265766f6b6560801b6064820152608401610450565b610cd67f5d0c1c948618f08b3c6c416314a18855862126dc2035e0bc9f6ed23f8394ad0c33610b3c565b610d355760405162461bcd60e51b815260206004820152602a60248201527f4552433230204272696467653a204d75737420686176652041444d494e5452416044820152694e5346455220726f6c6560b01b6064820152608401610450565b610d40838383611066565b604080516001600160a01b038086168252841660208201529081018290527f728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358906060015b60405180910390a1505050565b610da960008051602061170983398151915233610b3c565b610dc55760405162461bcd60e51b81526004016104509061158c565b60009182526005602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b610e0b60008051602061170983398151915233610b3c565b610e275760405162461bcd60e51b81526004016104509061158c565b6001600160a01b038216600090815260036020526040902054610e5c5760405162461bcd60e51b8152600401610450906115d0565b61052a8282611327565b6001600160a01b03838116600081815260046020908152604080832087845282529182902080546001600160a01b0319169486169485179055815192835282018590528101919091527f3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a83090606001610d84565b610ee38282610b3c565b61052a5760008281526001602081815260408084206001600160a01b0386168086529252808420805460ff19169093179092559051339285917f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d9190a45050565b610f4e8282610b3c565b1561052a5760008281526001602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b60005460ff16610ff45760405162461bcd60e51b815260206004820152601460248201527314185d5cd8589b194e881b9bdd081c185d5cd95960621b6044820152606401610450565b6000805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b6040516001600160a01b03909116815260200160405180910390a1565b60008080368060206033198201843760005194506014808203600c3760005193505050509091565b6001600160a01b038316600090815260036020526040902054600214156111475760405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb90604401602060405180830381600087803b1580156110d157600080fd5b505af11580156110e5573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061110991906116eb565b6104995760405162461bcd60e51b815260206004820152600f60248201526e1d1c985b9cd9995c8819985a5b1959608a1b6044820152606401610450565b6040516340c10f1960e01b81526001600160a01b038381166004830152602482018390528416906340c10f19906044015b600060405180830381600087803b15801561119257600080fd5b505af11580156111a6573d6000803e3d6000fd5b50505050505050565b60005460ff16156111d25760405162461bcd60e51b815260040161045090611655565b6000805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a2586110213390565b6001600160a01b038316600090815260036020526040902054600214156112f2576040516323b872dd60e01b81526001600160a01b038381166004830152306024830152604482018390528416906323b872dd90606401602060405180830381600087803b15801561127857600080fd5b505af115801561128c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906112b091906116eb565b6104995760405162461bcd60e51b81526020600482015260136024820152721d1c985b9cd9995c919c9bdb4819985a5b1959606a1b6044820152606401610450565b60405163079cc67960e41b81526001600160a01b038381166004830152602482018390528416906379cc679090604401611178565b600081611335576001611338565b60025b6001600160a01b038416600081815260036020908152604091829020849055815192835282018390529192507f75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a9101610d84565b60006020828403121561139e57600080fd5b81356001600160e01b0319811681146113b657600080fd5b9392505050565b80356001600160a01b03811681146113d457600080fd5b919050565b6000806000606084860312156113ee57600080fd5b6113f7846113bd565b92506020840135915061140c604085016113bd565b90509250925092565b60006020828403121561142757600080fd5b5035919050565b6000806040838503121561144157600080fd5b82359150611451602084016113bd565b90509250929050565b60008060006060848603121561146f57600080fd5b611478846113bd565b9250611486602085016113bd565b9150604084013590509250925092565b600080600080608085870312156114ac57600080fd5b843593506114bc602086016113bd565b92506114ca604086016113bd565b9396929550929360600135925050565b80151581146114e857600080fd5b50565b6000806000806080858703121561150157600080fd5b61150a856113bd565b93506020850135925061151f604086016113bd565b9150606085013561152f816114da565b939692955090935050565b6000806040838503121561154d57600080fd5b611556836113bd565b91506020830135611566816114da565b809150509250929050565b60006020828403121561158357600080fd5b6113b6826113bd565b60208082526024908201527f4552433230204272696467653a204d7573742068617665204d415050494e4720604082015263726f6c6560e01b606082015260800190565b60208082526022908201527f4552433230204272696467653a20746f6b656e206e6f7420636f6e6669677572604082015261195960f21b606082015260800190565b60208082526023908201527f4552433230204272696467653a204d75737420686176652050415553455220726040820152626f6c6560e81b606082015260800190565b60208082526010908201526f14185d5cd8589b194e881c185d5cd95960821b604082015260600190565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156116c1578581018301518582016080015282016116a5565b818111156116d3576000608083870101525b50601f01601f19169290920160800195945050505050565b6000602082840312156116fd57600080fd5b81516113b6816114da56fe0121836bd496e565c22481bc40c30c41db1fecf07c7376491a377f8056cfccd5a264697066735822122091d126d31d653c64429bb5361bf0364bd1b5c9a0fa4c35b561030b8e39ff87c764736f6c63430008090033",
}

// SfcERC20BridgeABI is the input ABI used to generate the binding from.
// Deprecated: Use SfcERC20BridgeMetaData.ABI instead.
var SfcERC20BridgeABI = SfcERC20BridgeMetaData.ABI

// SfcERC20BridgeBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use SfcERC20BridgeMetaData.Bin instead.
var SfcERC20BridgeBin = SfcERC20BridgeMetaData.Bin

// DeploySfcERC20Bridge deploys a new Ethereum contract, binding an instance of SfcERC20Bridge to it.
func DeploySfcERC20Bridge(auth *bind.TransactOpts, backend bind.ContractBackend, _sfcCbcContract common.Address) (common.Address, *types.Transaction, *SfcERC20Bridge, error) {
	parsed, err := SfcERC20BridgeMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(SfcERC20BridgeBin), backend, _sfcCbcContract)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &SfcERC20Bridge{SfcERC20BridgeCaller: SfcERC20BridgeCaller{contract: contract}, SfcERC20BridgeTransactor: SfcERC20BridgeTransactor{contract: contract}, SfcERC20BridgeFilterer: SfcERC20BridgeFilterer{contract: contract}}, nil
}

// SfcERC20Bridge is an auto generated Go binding around an Ethereum contract.
type SfcERC20Bridge struct {
	SfcERC20BridgeCaller     // Read-only binding to the contract
	SfcERC20BridgeTransactor // Write-only binding to the contract
	SfcERC20BridgeFilterer   // Log filterer for contract events
}

// SfcERC20BridgeCaller is an auto generated read-only Go binding around an Ethereum contract.
type SfcERC20BridgeCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcERC20BridgeTransactor is an auto generated write-only Go binding around an Ethereum contract.
type SfcERC20BridgeTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcERC20BridgeFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type SfcERC20BridgeFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcERC20BridgeSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type SfcERC20BridgeSession struct {
	Contract     *SfcERC20Bridge   // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// SfcERC20BridgeCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type SfcERC20BridgeCallerSession struct {
	Contract *SfcERC20BridgeCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts         // Call options to use throughout this session
}

// SfcERC20BridgeTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type SfcERC20BridgeTransactorSession struct {
	Contract     *SfcERC20BridgeTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts         // Transaction auth options to use throughout this session
}

// SfcERC20BridgeRaw is an auto generated low-level Go binding around an Ethereum contract.
type SfcERC20BridgeRaw struct {
	Contract *SfcERC20Bridge // Generic contract binding to access the raw methods on
}

// SfcERC20BridgeCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type SfcERC20BridgeCallerRaw struct {
	Contract *SfcERC20BridgeCaller // Generic read-only contract binding to access the raw methods on
}

// SfcERC20BridgeTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type SfcERC20BridgeTransactorRaw struct {
	Contract *SfcERC20BridgeTransactor // Generic write-only contract binding to access the raw methods on
}

// NewSfcERC20Bridge creates a new instance of SfcERC20Bridge, bound to a specific deployed contract.
func NewSfcERC20Bridge(address common.Address, backend bind.ContractBackend) (*SfcERC20Bridge, error) {
	contract, err := bindSfcERC20Bridge(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &SfcERC20Bridge{SfcERC20BridgeCaller: SfcERC20BridgeCaller{contract: contract}, SfcERC20BridgeTransactor: SfcERC20BridgeTransactor{contract: contract}, SfcERC20BridgeFilterer: SfcERC20BridgeFilterer{contract: contract}}, nil
}

// NewSfcERC20BridgeCaller creates a new read-only instance of SfcERC20Bridge, bound to a specific deployed contract.
func NewSfcERC20BridgeCaller(address common.Address, caller bind.ContractCaller) (*SfcERC20BridgeCaller, error) {
	contract, err := bindSfcERC20Bridge(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeCaller{contract: contract}, nil
}

// NewSfcERC20BridgeTransactor creates a new write-only instance of SfcERC20Bridge, bound to a specific deployed contract.
func NewSfcERC20BridgeTransactor(address common.Address, transactor bind.ContractTransactor) (*SfcERC20BridgeTransactor, error) {
	contract, err := bindSfcERC20Bridge(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeTransactor{contract: contract}, nil
}

// NewSfcERC20BridgeFilterer creates a new log filterer instance of SfcERC20Bridge, bound to a specific deployed contract.
func NewSfcERC20BridgeFilterer(address common.Address, filterer bind.ContractFilterer) (*SfcERC20BridgeFilterer, error) {
	contract, err := bindSfcERC20Bridge(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeFilterer{contract: contract}, nil
}

// bindSfcERC20Bridge binds a generic wrapper to an already deployed contract.
func bindSfcERC20Bridge(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(SfcERC20BridgeABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_SfcERC20Bridge *SfcERC20BridgeRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _SfcERC20Bridge.Contract.SfcERC20BridgeCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_SfcERC20Bridge *SfcERC20BridgeRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.SfcERC20BridgeTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_SfcERC20Bridge *SfcERC20BridgeRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.SfcERC20BridgeTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_SfcERC20Bridge *SfcERC20BridgeCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _SfcERC20Bridge.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_SfcERC20Bridge *SfcERC20BridgeTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_SfcERC20Bridge *SfcERC20BridgeTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.contract.Transact(opts, method, params...)
}

// ADMINTRANSFERROLE is a free data retrieval call binding the contract method 0xc3be3f36.
//
// Solidity: function ADMINTRANSFER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) ADMINTRANSFERROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "ADMINTRANSFER_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// ADMINTRANSFERROLE is a free data retrieval call binding the contract method 0xc3be3f36.
//
// Solidity: function ADMINTRANSFER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeSession) ADMINTRANSFERROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.ADMINTRANSFERROLE(&_SfcERC20Bridge.CallOpts)
}

// ADMINTRANSFERROLE is a free data retrieval call binding the contract method 0xc3be3f36.
//
// Solidity: function ADMINTRANSFER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) ADMINTRANSFERROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.ADMINTRANSFERROLE(&_SfcERC20Bridge.CallOpts)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) DEFAULTADMINROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "DEFAULT_ADMIN_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.DEFAULTADMINROLE(&_SfcERC20Bridge.CallOpts)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.DEFAULTADMINROLE(&_SfcERC20Bridge.CallOpts)
}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) MAPPINGROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "MAPPING_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeSession) MAPPINGROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.MAPPINGROLE(&_SfcERC20Bridge.CallOpts)
}

// MAPPINGROLE is a free data retrieval call binding the contract method 0x43a12224.
//
// Solidity: function MAPPING_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) MAPPINGROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.MAPPINGROLE(&_SfcERC20Bridge.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) PAUSERROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "PAUSER_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeSession) PAUSERROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.PAUSERROLE(&_SfcERC20Bridge.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) PAUSERROLE() ([32]byte, error) {
	return _SfcERC20Bridge.Contract.PAUSERROLE(&_SfcERC20Bridge.CallOpts)
}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) GetBcIdTokenMaping(opts *bind.CallOpts, _bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "getBcIdTokenMaping", _bcId, _tokenContract)

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeSession) GetBcIdTokenMaping(_bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	return _SfcERC20Bridge.Contract.GetBcIdTokenMaping(&_SfcERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// GetBcIdTokenMaping is a free data retrieval call binding the contract method 0x80ccbde4.
//
// Solidity: function getBcIdTokenMaping(uint256 _bcId, address _tokenContract) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) GetBcIdTokenMaping(_bcId *big.Int, _tokenContract common.Address) (common.Address, error) {
	return _SfcERC20Bridge.Contract.GetBcIdTokenMaping(&_SfcERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) GetRemoteErc20BridgeContract(opts *bind.CallOpts, _bcId *big.Int) (common.Address, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "getRemoteErc20BridgeContract", _bcId)

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeSession) GetRemoteErc20BridgeContract(_bcId *big.Int) (common.Address, error) {
	return _SfcERC20Bridge.Contract.GetRemoteErc20BridgeContract(&_SfcERC20Bridge.CallOpts, _bcId)
}

// GetRemoteErc20BridgeContract is a free data retrieval call binding the contract method 0x0dd4b783.
//
// Solidity: function getRemoteErc20BridgeContract(uint256 _bcId) view returns(address)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) GetRemoteErc20BridgeContract(_bcId *big.Int) (common.Address, error) {
	return _SfcERC20Bridge.Contract.GetRemoteErc20BridgeContract(&_SfcERC20Bridge.CallOpts, _bcId)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) GetRoleAdmin(opts *bind.CallOpts, role [32]byte) ([32]byte, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "getRoleAdmin", role)

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _SfcERC20Bridge.Contract.GetRoleAdmin(&_SfcERC20Bridge.CallOpts, role)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _SfcERC20Bridge.Contract.GetRoleAdmin(&_SfcERC20Bridge.CallOpts, role)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) HasRole(opts *bind.CallOpts, role [32]byte, account common.Address) (bool, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "hasRole", role, account)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _SfcERC20Bridge.Contract.HasRole(&_SfcERC20Bridge.CallOpts, role, account)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _SfcERC20Bridge.Contract.HasRole(&_SfcERC20Bridge.CallOpts, role, account)
}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) IsBcIdTokenAllowed(opts *bind.CallOpts, _bcId *big.Int, _tokenContract common.Address) (bool, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "isBcIdTokenAllowed", _bcId, _tokenContract)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeSession) IsBcIdTokenAllowed(_bcId *big.Int, _tokenContract common.Address) (bool, error) {
	return _SfcERC20Bridge.Contract.IsBcIdTokenAllowed(&_SfcERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// IsBcIdTokenAllowed is a free data retrieval call binding the contract method 0x2e44a5b5.
//
// Solidity: function isBcIdTokenAllowed(uint256 _bcId, address _tokenContract) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) IsBcIdTokenAllowed(_bcId *big.Int, _tokenContract common.Address) (bool, error) {
	return _SfcERC20Bridge.Contract.IsBcIdTokenAllowed(&_SfcERC20Bridge.CallOpts, _bcId, _tokenContract)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) Paused(opts *bind.CallOpts) (bool, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "paused")

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeSession) Paused() (bool, error) {
	return _SfcERC20Bridge.Contract.Paused(&_SfcERC20Bridge.CallOpts)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) Paused() (bool, error) {
	return _SfcERC20Bridge.Contract.Paused(&_SfcERC20Bridge.CallOpts)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) SupportsInterface(opts *bind.CallOpts, interfaceId [4]byte) (bool, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "supportsInterface", interfaceId)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _SfcERC20Bridge.Contract.SupportsInterface(&_SfcERC20Bridge.CallOpts, interfaceId)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _SfcERC20Bridge.Contract.SupportsInterface(&_SfcERC20Bridge.CallOpts, interfaceId)
}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_SfcERC20Bridge *SfcERC20BridgeCaller) TokenContractConfiguration(opts *bind.CallOpts, arg0 common.Address) (*big.Int, error) {
	var out []interface{}
	err := _SfcERC20Bridge.contract.Call(opts, &out, "tokenContractConfiguration", arg0)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_SfcERC20Bridge *SfcERC20BridgeSession) TokenContractConfiguration(arg0 common.Address) (*big.Int, error) {
	return _SfcERC20Bridge.Contract.TokenContractConfiguration(&_SfcERC20Bridge.CallOpts, arg0)
}

// TokenContractConfiguration is a free data retrieval call binding the contract method 0xef0047cf.
//
// Solidity: function tokenContractConfiguration(address ) view returns(uint256)
func (_SfcERC20Bridge *SfcERC20BridgeCallerSession) TokenContractConfiguration(arg0 common.Address) (*big.Int, error) {
	return _SfcERC20Bridge.Contract.TokenContractConfiguration(&_SfcERC20Bridge.CallOpts, arg0)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) AddContractFirstMapping(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "addContractFirstMapping", _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) AddContractFirstMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.AddContractFirstMapping(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AddContractFirstMapping is a paid mutator transaction binding the contract method 0xc22b8699.
//
// Solidity: function addContractFirstMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) AddContractFirstMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.AddContractFirstMapping(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract, _thisBcMassC)
}

// AdminTransfer is a paid mutator transaction binding the contract method 0xda72c1e8.
//
// Solidity: function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) AdminTransfer(opts *bind.TransactOpts, _erc20Contract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "adminTransfer", _erc20Contract, _recipient, _amount)
}

// AdminTransfer is a paid mutator transaction binding the contract method 0xda72c1e8.
//
// Solidity: function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) AdminTransfer(_erc20Contract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.AdminTransfer(&_SfcERC20Bridge.TransactOpts, _erc20Contract, _recipient, _amount)
}

// AdminTransfer is a paid mutator transaction binding the contract method 0xda72c1e8.
//
// Solidity: function adminTransfer(address _erc20Contract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) AdminTransfer(_erc20Contract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.AdminTransfer(&_SfcERC20Bridge.TransactOpts, _erc20Contract, _recipient, _amount)
}

// ChangeBlockchainMapping is a paid mutator transaction binding the contract method 0xe787282a.
//
// Solidity: function changeBlockchainMapping(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) ChangeBlockchainMapping(opts *bind.TransactOpts, _otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "changeBlockchainMapping", _otherBcId, _otherErc20Bridge)
}

// ChangeBlockchainMapping is a paid mutator transaction binding the contract method 0xe787282a.
//
// Solidity: function changeBlockchainMapping(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) ChangeBlockchainMapping(_otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ChangeBlockchainMapping(&_SfcERC20Bridge.TransactOpts, _otherBcId, _otherErc20Bridge)
}

// ChangeBlockchainMapping is a paid mutator transaction binding the contract method 0xe787282a.
//
// Solidity: function changeBlockchainMapping(uint256 _otherBcId, address _otherErc20Bridge) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) ChangeBlockchainMapping(_otherBcId *big.Int, _otherErc20Bridge common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ChangeBlockchainMapping(&_SfcERC20Bridge.TransactOpts, _otherBcId, _otherErc20Bridge)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) ChangeContractMapping(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "changeContractMapping", _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) ChangeContractMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ChangeContractMapping(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// ChangeContractMapping is a paid mutator transaction binding the contract method 0x06b47e9e.
//
// Solidity: function changeContractMapping(address _thisBcTokenContract, uint256 _otherBcId, address _otherTokenContract) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) ChangeContractMapping(_thisBcTokenContract common.Address, _otherBcId *big.Int, _otherTokenContract common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ChangeContractMapping(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _otherBcId, _otherTokenContract)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) GrantRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "grantRole", role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.GrantRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.GrantRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) Pause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "pause")
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) Pause() (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.Pause(&_SfcERC20Bridge.TransactOpts)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) Pause() (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.Pause(&_SfcERC20Bridge.TransactOpts)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) ReceiveFromOtherBlockchain(opts *bind.TransactOpts, _destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "receiveFromOtherBlockchain", _destTokenContract, _recipient, _amount)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) ReceiveFromOtherBlockchain(_destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ReceiveFromOtherBlockchain(&_SfcERC20Bridge.TransactOpts, _destTokenContract, _recipient, _amount)
}

// ReceiveFromOtherBlockchain is a paid mutator transaction binding the contract method 0x8278ef6d.
//
// Solidity: function receiveFromOtherBlockchain(address _destTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) ReceiveFromOtherBlockchain(_destTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.ReceiveFromOtherBlockchain(&_SfcERC20Bridge.TransactOpts, _destTokenContract, _recipient, _amount)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) RenounceRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "renounceRole", role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.RenounceRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.RenounceRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) RevokeRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "revokeRole", role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.RevokeRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.RevokeRole(&_SfcERC20Bridge.TransactOpts, role, account)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) SetTokenConfig(opts *bind.TransactOpts, _thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "setTokenConfig", _thisBcTokenContract, _thisBcMassC)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) SetTokenConfig(_thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.SetTokenConfig(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _thisBcMassC)
}

// SetTokenConfig is a paid mutator transaction binding the contract method 0xe96dab56.
//
// Solidity: function setTokenConfig(address _thisBcTokenContract, bool _thisBcMassC) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) SetTokenConfig(_thisBcTokenContract common.Address, _thisBcMassC bool) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.SetTokenConfig(&_SfcERC20Bridge.TransactOpts, _thisBcTokenContract, _thisBcMassC)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) TransferToOtherBlockchain(opts *bind.TransactOpts, _destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "transferToOtherBlockchain", _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) TransferToOtherBlockchain(_destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.TransferToOtherBlockchain(&_SfcERC20Bridge.TransactOpts, _destBcId, _srcTokenContract, _recipient, _amount)
}

// TransferToOtherBlockchain is a paid mutator transaction binding the contract method 0x84aea633.
//
// Solidity: function transferToOtherBlockchain(uint256 _destBcId, address _srcTokenContract, address _recipient, uint256 _amount) returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) TransferToOtherBlockchain(_destBcId *big.Int, _srcTokenContract common.Address, _recipient common.Address, _amount *big.Int) (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.TransferToOtherBlockchain(&_SfcERC20Bridge.TransactOpts, _destBcId, _srcTokenContract, _recipient, _amount)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactor) Unpause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SfcERC20Bridge.contract.Transact(opts, "unpause")
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeSession) Unpause() (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.Unpause(&_SfcERC20Bridge.TransactOpts)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_SfcERC20Bridge *SfcERC20BridgeTransactorSession) Unpause() (*types.Transaction, error) {
	return _SfcERC20Bridge.Contract.Unpause(&_SfcERC20Bridge.TransactOpts)
}

// SfcERC20BridgeAdminTransferIterator is returned from FilterAdminTransfer and is used to iterate over the raw logs and unpacked data for AdminTransfer events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeAdminTransferIterator struct {
	Event *SfcERC20BridgeAdminTransfer // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeAdminTransferIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeAdminTransfer)
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
		it.Event = new(SfcERC20BridgeAdminTransfer)
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
func (it *SfcERC20BridgeAdminTransferIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeAdminTransferIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeAdminTransfer represents a AdminTransfer event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeAdminTransfer struct {
	Erc20Contract common.Address
	Recipient     common.Address
	Amount        *big.Int
	Raw           types.Log // Blockchain specific contextual infos
}

// FilterAdminTransfer is a free log retrieval operation binding the contract event 0x728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358.
//
// Solidity: event AdminTransfer(address _erc20Contract, address _recipient, uint256 _amount)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterAdminTransfer(opts *bind.FilterOpts) (*SfcERC20BridgeAdminTransferIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "AdminTransfer")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeAdminTransferIterator{contract: _SfcERC20Bridge.contract, event: "AdminTransfer", logs: logs, sub: sub}, nil
}

// WatchAdminTransfer is a free log subscription operation binding the contract event 0x728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358.
//
// Solidity: event AdminTransfer(address _erc20Contract, address _recipient, uint256 _amount)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchAdminTransfer(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeAdminTransfer) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "AdminTransfer")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeAdminTransfer)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "AdminTransfer", log); err != nil {
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

// ParseAdminTransfer is a log parse operation binding the contract event 0x728fe8c3e9dd087cac70e8ff44565c920a2bb77c726ed3191394fefb4aabc358.
//
// Solidity: event AdminTransfer(address _erc20Contract, address _recipient, uint256 _amount)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseAdminTransfer(log types.Log) (*SfcERC20BridgeAdminTransfer, error) {
	event := new(SfcERC20BridgeAdminTransfer)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "AdminTransfer", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgePausedIterator is returned from FilterPaused and is used to iterate over the raw logs and unpacked data for Paused events raised by the SfcERC20Bridge contract.
type SfcERC20BridgePausedIterator struct {
	Event *SfcERC20BridgePaused // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgePausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgePaused)
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
		it.Event = new(SfcERC20BridgePaused)
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
func (it *SfcERC20BridgePausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgePausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgePaused represents a Paused event raised by the SfcERC20Bridge contract.
type SfcERC20BridgePaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterPaused is a free log retrieval operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterPaused(opts *bind.FilterOpts) (*SfcERC20BridgePausedIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgePausedIterator{contract: _SfcERC20Bridge.contract, event: "Paused", logs: logs, sub: sub}, nil
}

// WatchPaused is a free log subscription operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchPaused(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgePaused) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgePaused)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "Paused", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParsePaused(log types.Log) (*SfcERC20BridgePaused, error) {
	event := new(SfcERC20BridgePaused)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "Paused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeReceivedFromIterator is returned from FilterReceivedFrom and is used to iterate over the raw logs and unpacked data for ReceivedFrom events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeReceivedFromIterator struct {
	Event *SfcERC20BridgeReceivedFrom // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeReceivedFromIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeReceivedFrom)
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
		it.Event = new(SfcERC20BridgeReceivedFrom)
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
func (it *SfcERC20BridgeReceivedFromIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeReceivedFromIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeReceivedFrom represents a ReceivedFrom event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeReceivedFrom struct {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterReceivedFrom(opts *bind.FilterOpts) (*SfcERC20BridgeReceivedFromIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "ReceivedFrom")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeReceivedFromIterator{contract: _SfcERC20Bridge.contract, event: "ReceivedFrom", logs: logs, sub: sub}, nil
}

// WatchReceivedFrom is a free log subscription operation binding the contract event 0x3008be2f38f2c92605e65a39c193c4b67868ea0dd742f5b9d6f228c9e258ece1.
//
// Solidity: event ReceivedFrom(uint256 _srcBcId, address _srcTokenContract, address _destTokenContract, address _recipient, uint256 _amount)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchReceivedFrom(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeReceivedFrom) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "ReceivedFrom")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeReceivedFrom)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "ReceivedFrom", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseReceivedFrom(log types.Log) (*SfcERC20BridgeReceivedFrom, error) {
	event := new(SfcERC20BridgeReceivedFrom)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "ReceivedFrom", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeRoleAdminChangedIterator is returned from FilterRoleAdminChanged and is used to iterate over the raw logs and unpacked data for RoleAdminChanged events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleAdminChangedIterator struct {
	Event *SfcERC20BridgeRoleAdminChanged // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeRoleAdminChangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeRoleAdminChanged)
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
		it.Event = new(SfcERC20BridgeRoleAdminChanged)
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
func (it *SfcERC20BridgeRoleAdminChangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeRoleAdminChangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeRoleAdminChanged represents a RoleAdminChanged event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleAdminChanged struct {
	Role              [32]byte
	PreviousAdminRole [32]byte
	NewAdminRole      [32]byte
	Raw               types.Log // Blockchain specific contextual infos
}

// FilterRoleAdminChanged is a free log retrieval operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterRoleAdminChanged(opts *bind.FilterOpts, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (*SfcERC20BridgeRoleAdminChangedIterator, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeRoleAdminChangedIterator{contract: _SfcERC20Bridge.contract, event: "RoleAdminChanged", logs: logs, sub: sub}, nil
}

// WatchRoleAdminChanged is a free log subscription operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchRoleAdminChanged(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeRoleAdminChanged, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (event.Subscription, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeRoleAdminChanged)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseRoleAdminChanged(log types.Log) (*SfcERC20BridgeRoleAdminChanged, error) {
	event := new(SfcERC20BridgeRoleAdminChanged)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeRoleGrantedIterator is returned from FilterRoleGranted and is used to iterate over the raw logs and unpacked data for RoleGranted events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleGrantedIterator struct {
	Event *SfcERC20BridgeRoleGranted // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeRoleGrantedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeRoleGranted)
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
		it.Event = new(SfcERC20BridgeRoleGranted)
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
func (it *SfcERC20BridgeRoleGrantedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeRoleGrantedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeRoleGranted represents a RoleGranted event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleGranted struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleGranted is a free log retrieval operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterRoleGranted(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*SfcERC20BridgeRoleGrantedIterator, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeRoleGrantedIterator{contract: _SfcERC20Bridge.contract, event: "RoleGranted", logs: logs, sub: sub}, nil
}

// WatchRoleGranted is a free log subscription operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchRoleGranted(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeRoleGranted, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeRoleGranted)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleGranted", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseRoleGranted(log types.Log) (*SfcERC20BridgeRoleGranted, error) {
	event := new(SfcERC20BridgeRoleGranted)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleGranted", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeRoleRevokedIterator is returned from FilterRoleRevoked and is used to iterate over the raw logs and unpacked data for RoleRevoked events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleRevokedIterator struct {
	Event *SfcERC20BridgeRoleRevoked // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeRoleRevokedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeRoleRevoked)
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
		it.Event = new(SfcERC20BridgeRoleRevoked)
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
func (it *SfcERC20BridgeRoleRevokedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeRoleRevokedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeRoleRevoked represents a RoleRevoked event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeRoleRevoked struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleRevoked is a free log retrieval operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterRoleRevoked(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*SfcERC20BridgeRoleRevokedIterator, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeRoleRevokedIterator{contract: _SfcERC20Bridge.contract, event: "RoleRevoked", logs: logs, sub: sub}, nil
}

// WatchRoleRevoked is a free log subscription operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchRoleRevoked(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeRoleRevoked, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

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

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeRoleRevoked)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseRoleRevoked(log types.Log) (*SfcERC20BridgeRoleRevoked, error) {
	event := new(SfcERC20BridgeRoleRevoked)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeTokenContractAddressMappingChangedIterator is returned from FilterTokenContractAddressMappingChanged and is used to iterate over the raw logs and unpacked data for TokenContractAddressMappingChanged events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTokenContractAddressMappingChangedIterator struct {
	Event *SfcERC20BridgeTokenContractAddressMappingChanged // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeTokenContractAddressMappingChangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeTokenContractAddressMappingChanged)
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
		it.Event = new(SfcERC20BridgeTokenContractAddressMappingChanged)
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
func (it *SfcERC20BridgeTokenContractAddressMappingChangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeTokenContractAddressMappingChangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeTokenContractAddressMappingChanged represents a TokenContractAddressMappingChanged event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTokenContractAddressMappingChanged struct {
	ThisBcTokenContract common.Address
	OtherBcId           *big.Int
	OthercTokenContract common.Address
	Raw                 types.Log // Blockchain specific contextual infos
}

// FilterTokenContractAddressMappingChanged is a free log retrieval operation binding the contract event 0x3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830.
//
// Solidity: event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterTokenContractAddressMappingChanged(opts *bind.FilterOpts) (*SfcERC20BridgeTokenContractAddressMappingChangedIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "TokenContractAddressMappingChanged")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeTokenContractAddressMappingChangedIterator{contract: _SfcERC20Bridge.contract, event: "TokenContractAddressMappingChanged", logs: logs, sub: sub}, nil
}

// WatchTokenContractAddressMappingChanged is a free log subscription operation binding the contract event 0x3887b7740ba2f669b578cfaf7b54c097711335413cf96f681d36dbf97b98a830.
//
// Solidity: event TokenContractAddressMappingChanged(address _thisBcTokenContract, uint256 _otherBcId, address _othercTokenContract)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchTokenContractAddressMappingChanged(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeTokenContractAddressMappingChanged) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "TokenContractAddressMappingChanged")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeTokenContractAddressMappingChanged)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "TokenContractAddressMappingChanged", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseTokenContractAddressMappingChanged(log types.Log) (*SfcERC20BridgeTokenContractAddressMappingChanged, error) {
	event := new(SfcERC20BridgeTokenContractAddressMappingChanged)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "TokenContractAddressMappingChanged", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeTokenContractConfigIterator is returned from FilterTokenContractConfig and is used to iterate over the raw logs and unpacked data for TokenContractConfig events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTokenContractConfigIterator struct {
	Event *SfcERC20BridgeTokenContractConfig // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeTokenContractConfigIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeTokenContractConfig)
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
		it.Event = new(SfcERC20BridgeTokenContractConfig)
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
func (it *SfcERC20BridgeTokenContractConfigIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeTokenContractConfigIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeTokenContractConfig represents a TokenContractConfig event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTokenContractConfig struct {
	ThisBcTokenContract common.Address
	Config              *big.Int
	Raw                 types.Log // Blockchain specific contextual infos
}

// FilterTokenContractConfig is a free log retrieval operation binding the contract event 0x75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a.
//
// Solidity: event TokenContractConfig(address _thisBcTokenContract, uint256 _config)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterTokenContractConfig(opts *bind.FilterOpts) (*SfcERC20BridgeTokenContractConfigIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "TokenContractConfig")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeTokenContractConfigIterator{contract: _SfcERC20Bridge.contract, event: "TokenContractConfig", logs: logs, sub: sub}, nil
}

// WatchTokenContractConfig is a free log subscription operation binding the contract event 0x75592476a227f5631d3cd8cc24fec7d4c3aec6a178f64ec2b8b91693672f613a.
//
// Solidity: event TokenContractConfig(address _thisBcTokenContract, uint256 _config)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchTokenContractConfig(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeTokenContractConfig) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "TokenContractConfig")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeTokenContractConfig)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "TokenContractConfig", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseTokenContractConfig(log types.Log) (*SfcERC20BridgeTokenContractConfig, error) {
	event := new(SfcERC20BridgeTokenContractConfig)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "TokenContractConfig", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeTransferToIterator is returned from FilterTransferTo and is used to iterate over the raw logs and unpacked data for TransferTo events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTransferToIterator struct {
	Event *SfcERC20BridgeTransferTo // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeTransferToIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeTransferTo)
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
		it.Event = new(SfcERC20BridgeTransferTo)
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
func (it *SfcERC20BridgeTransferToIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeTransferToIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeTransferTo represents a TransferTo event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeTransferTo struct {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterTransferTo(opts *bind.FilterOpts) (*SfcERC20BridgeTransferToIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "TransferTo")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeTransferToIterator{contract: _SfcERC20Bridge.contract, event: "TransferTo", logs: logs, sub: sub}, nil
}

// WatchTransferTo is a free log subscription operation binding the contract event 0x752a2bfed77c22e59eeaf9351f545a08f5ee0110b8d6de0e7bcaf32a5dfa661b.
//
// Solidity: event TransferTo(uint256 _destBcId, address _srcTokenContract, address _destTokenContract, address _sender, address _recipient, uint256 _amount)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchTransferTo(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeTransferTo) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "TransferTo")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeTransferTo)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "TransferTo", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseTransferTo(log types.Log) (*SfcERC20BridgeTransferTo, error) {
	event := new(SfcERC20BridgeTransferTo)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "TransferTo", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcERC20BridgeUnpausedIterator is returned from FilterUnpaused and is used to iterate over the raw logs and unpacked data for Unpaused events raised by the SfcERC20Bridge contract.
type SfcERC20BridgeUnpausedIterator struct {
	Event *SfcERC20BridgeUnpaused // Event containing the contract specifics and raw log

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
func (it *SfcERC20BridgeUnpausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcERC20BridgeUnpaused)
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
		it.Event = new(SfcERC20BridgeUnpaused)
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
func (it *SfcERC20BridgeUnpausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcERC20BridgeUnpausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcERC20BridgeUnpaused represents a Unpaused event raised by the SfcERC20Bridge contract.
type SfcERC20BridgeUnpaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterUnpaused is a free log retrieval operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) FilterUnpaused(opts *bind.FilterOpts) (*SfcERC20BridgeUnpausedIterator, error) {

	logs, sub, err := _SfcERC20Bridge.contract.FilterLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return &SfcERC20BridgeUnpausedIterator{contract: _SfcERC20Bridge.contract, event: "Unpaused", logs: logs, sub: sub}, nil
}

// WatchUnpaused is a free log subscription operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) WatchUnpaused(opts *bind.WatchOpts, sink chan<- *SfcERC20BridgeUnpaused) (event.Subscription, error) {

	logs, sub, err := _SfcERC20Bridge.contract.WatchLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcERC20BridgeUnpaused)
				if err := _SfcERC20Bridge.contract.UnpackLog(event, "Unpaused", log); err != nil {
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
func (_SfcERC20Bridge *SfcERC20BridgeFilterer) ParseUnpaused(log types.Log) (*SfcERC20BridgeUnpaused, error) {
	event := new(SfcERC20BridgeUnpaused)
	if err := _SfcERC20Bridge.contract.UnpackLog(event, "Unpaused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
