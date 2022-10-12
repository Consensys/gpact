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

// ERC20MinterPauserMetaData contains all meta data concerning the ERC20MinterPauser contract.
var ERC20MinterPauserMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"symbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Paused\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"previousAdminRole\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"newAdminRole\",\"type\":\"bytes32\"}],\"name\":\"RoleAdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleGranted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleRevoked\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"Unpaused\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"DEFAULT_ADMIN_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"MINTER_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PAUSER_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"burn\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"burnFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleAdmin\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"getRoleMember\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleMemberCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"hasRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"mint\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"pause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"renounceRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"revokeRole\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"interfaceId\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"unpause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x60806040523480156200001157600080fd5b5060405162001c5c38038062001c5c8339810160408190526200003491620003b5565b8151829082906200004d90600590602085019062000242565b5080516200006390600690602084019062000242565b50506007805460ff19169055506200007d600033620000dd565b620000a97f9f2df0fed2c77648de5860a4cc508cd0818c85b8b8a1ab4ceeef8d981c8956a633620000dd565b620000d57f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a33620000dd565b50506200045c565b620000f482826200012060201b620009ef1760201c565b60008281526001602090815260409091206200011b918390620009f962000130821b17901c565b505050565b6200012c828262000150565b5050565b600062000147836001600160a01b038416620001f0565b90505b92915050565b6000828152602081815260408083206001600160a01b038516845290915290205460ff166200012c576000828152602081815260408083206001600160a01b03851684529091529020805460ff19166001179055620001ac3390565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b600081815260018301602052604081205462000239575081546001818101845560008481526020808220909301849055845484825282860190935260409020919091556200014a565b5060006200014a565b82805462000250906200041f565b90600052602060002090601f016020900481019282620002745760008555620002bf565b82601f106200028f57805160ff1916838001178555620002bf565b82800160010185558215620002bf579182015b82811115620002bf578251825591602001919060010190620002a2565b50620002cd929150620002d1565b5090565b5b80821115620002cd5760008155600101620002d2565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126200031057600080fd5b81516001600160401b03808211156200032d576200032d620002e8565b604051601f8301601f19908116603f01168101908282118183101715620003585762000358620002e8565b816040528381526020925086838588010111156200037557600080fd5b600091505b838210156200039957858201830151818301840152908201906200037a565b83821115620003ab5760008385830101525b9695505050505050565b60008060408385031215620003c957600080fd5b82516001600160401b0380821115620003e157600080fd5b620003ef86838701620002fe565b935060208501519150808211156200040657600080fd5b506200041585828601620002fe565b9150509250929050565b600181811c908216806200043457607f821691505b602082108114156200045657634e487b7160e01b600052602260045260246000fd5b50919050565b6117f0806200046c6000396000f3fe608060405234801561001057600080fd5b50600436106101c45760003560e01c806370a08231116100f9578063a457c2d711610097578063d539139311610071578063d5391393146103af578063d547741f146103d6578063dd62ed3e146103e9578063e63ab1e91461042257600080fd5b8063a457c2d714610376578063a9059cbb14610389578063ca15c8731461039c57600080fd5b80639010d07c116100d35780639010d07c1461032857806391d148541461035357806395d89b4114610366578063a217fddf1461036e57600080fd5b806370a08231146102e457806379cc67901461030d5780638456cb591461032057600080fd5b8063313ce567116101665780633f4ba83a116101405780633f4ba83a146102ab57806340c10f19146102b357806342966c68146102c65780635c975abb146102d957600080fd5b8063313ce5671461027657806336568abe14610285578063395093511461029857600080fd5b806318160ddd116101a257806318160ddd1461021957806323b872dd1461022b578063248a9ca31461023e5780632f2ff15d1461026157600080fd5b806301ffc9a7146101c957806306fdde03146101f1578063095ea7b314610206575b600080fd5b6101dc6101d7366004611561565b610449565b60405190151581526020015b60405180910390f35b6101f9610474565b6040516101e8919061158b565b6101dc6102143660046115fc565b610506565b6004545b6040519081526020016101e8565b6101dc610239366004611626565b61051c565b61021d61024c366004611662565b60009081526020819052604090206001015490565b61027461026f36600461167b565b6105d2565b005b604051601281526020016101e8565b61027461029336600461167b565b6105f9565b6101dc6102a63660046115fc565b61061b565b610274610652565b6102746102c13660046115fc565b6106f8565b6102746102d4366004611662565b61079b565b60075460ff166101dc565b61021d6102f23660046116a7565b6001600160a01b031660009081526002602052604090205490565b61027461031b3660046115fc565b6107a8565b61027461082b565b61033b6103363660046116c2565b6108cf565b6040516001600160a01b0390911681526020016101e8565b6101dc61036136600461167b565b6108ee565b6101f9610917565b61021d600081565b6101dc6103843660046115fc565b610926565b6101dc6103973660046115fc565b6109c1565b61021d6103aa366004611662565b6109ce565b61021d7f9f2df0fed2c77648de5860a4cc508cd0818c85b8b8a1ab4ceeef8d981c8956a681565b6102746103e436600461167b565b6109e5565b61021d6103f73660046116e4565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b61021d7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a81565b60006001600160e01b03198216635a05180f60e01b148061046e575061046e82610a0e565b92915050565b6060600580546104839061170e565b80601f01602080910402602001604051908101604052809291908181526020018280546104af9061170e565b80156104fc5780601f106104d1576101008083540402835291602001916104fc565b820191906000526020600020905b8154815290600101906020018083116104df57829003601f168201915b5050505050905090565b6000610513338484610a43565b50600192915050565b6000610529848484610b68565b6001600160a01b0384166000908152600360209081526040808320338452909152902054828110156105b35760405162461bcd60e51b815260206004820152602860248201527f45524332303a207472616e7366657220616d6f756e74206578636565647320616044820152676c6c6f77616e636560c01b60648201526084015b60405180910390fd5b6105c785336105c2868561175f565b610a43565b506001949350505050565b6105dc8282610d4b565b60008281526001602052604090206105f490826109f9565b505050565b6106038282610dcc565b60008281526001602052604090206105f49082610e46565b3360008181526003602090815260408083206001600160a01b038716845290915281205490916105139185906105c2908690611776565b61067c7f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a336108ee565b6106ee5760405162461bcd60e51b815260206004820152603960248201527f45524332305072657365744d696e7465725061757365723a206d75737420686160448201527f76652070617573657220726f6c6520746f20756e70617573650000000000000060648201526084016105aa565b6106f6610e5b565b565b6107227f9f2df0fed2c77648de5860a4cc508cd0818c85b8b8a1ab4ceeef8d981c8956a6336108ee565b61078d5760405162461bcd60e51b815260206004820152603660248201527f45524332305072657365744d696e7465725061757365723a206d7573742068616044820152751d99481b5a5b9d195c881c9bdb19481d1bc81b5a5b9d60521b60648201526084016105aa565b6107978282610eee565b5050565b6107a53382610fd9565b50565b60006107b483336103f7565b9050818110156108125760405162461bcd60e51b8152602060048201526024808201527f45524332303a206275726e20616d6f756e74206578636565647320616c6c6f77604482015263616e636560e01b60648201526084016105aa565b61082183336105c2858561175f565b6105f48383610fd9565b6108557f65d7a28e3265b37a6474929f336521b332c1681b933f6cb9f3376673440d862a336108ee565b6108c75760405162461bcd60e51b815260206004820152603760248201527f45524332305072657365744d696e7465725061757365723a206d75737420686160448201527f76652070617573657220726f6c6520746f20706175736500000000000000000060648201526084016105aa565b6106f6611134565b60008281526001602052604081206108e790836111af565b9392505050565b6000918252602082815260408084206001600160a01b0393909316845291905290205460ff1690565b6060600680546104839061170e565b3360009081526003602090815260408083206001600160a01b0386168452909152812054828110156109a85760405162461bcd60e51b815260206004820152602560248201527f45524332303a2064656372656173656420616c6c6f77616e63652062656c6f77604482015264207a65726f60d81b60648201526084016105aa565b6109b733856105c2868561175f565b5060019392505050565b6000610513338484610b68565b600081815260016020526040812061046e906111bb565b61060382826111c5565b6107978282611245565b60006108e7836001600160a01b0384166112c9565b60006001600160e01b03198216637965db0b60e01b148061046e57506301ffc9a760e01b6001600160e01b031983161461046e565b6001600160a01b038316610aa55760405162461bcd60e51b8152602060048201526024808201527f45524332303a20617070726f76652066726f6d20746865207a65726f206164646044820152637265737360e01b60648201526084016105aa565b6001600160a01b038216610b065760405162461bcd60e51b815260206004820152602260248201527f45524332303a20617070726f766520746f20746865207a65726f206164647265604482015261737360f01b60648201526084016105aa565b6001600160a01b0383811660008181526003602090815260408083209487168084529482529182902085905590518481527f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591015b60405180910390a3505050565b6001600160a01b038316610bcc5760405162461bcd60e51b815260206004820152602560248201527f45524332303a207472616e736665722066726f6d20746865207a65726f206164604482015264647265737360d81b60648201526084016105aa565b6001600160a01b038216610c2e5760405162461bcd60e51b815260206004820152602360248201527f45524332303a207472616e7366657220746f20746865207a65726f206164647260448201526265737360e81b60648201526084016105aa565b610c39838383611318565b6001600160a01b03831660009081526002602052604090205481811015610cb15760405162461bcd60e51b815260206004820152602660248201527f45524332303a207472616e7366657220616d6f756e7420657863656564732062604482015265616c616e636560d01b60648201526084016105aa565b610cbb828261175f565b6001600160a01b038086166000908152600260205260408082209390935590851681529081208054849290610cf1908490611776565b92505081905550826001600160a01b0316846001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef84604051610d3d91815260200190565b60405180910390a350505050565b600082815260208190526040902060010154610d68905b336108ee565b6109ef5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526e0818591b5a5b881d1bc819dc985b9d608a1b60648201526084016105aa565b6001600160a01b0381163314610e3c5760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084016105aa565b6107978282611323565b60006108e7836001600160a01b038416611388565b60075460ff16610ea45760405162461bcd60e51b815260206004820152601460248201527314185d5cd8589b194e881b9bdd081c185d5cd95960621b60448201526064016105aa565b6007805460ff191690557f5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa335b6040516001600160a01b03909116815260200160405180910390a1565b6001600160a01b038216610f445760405162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f20616464726573730060448201526064016105aa565b610f5060008383611318565b8060046000828254610f629190611776565b90915550506001600160a01b03821660009081526002602052604081208054839290610f8f908490611776565b90915550506040518181526001600160a01b038316906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9060200160405180910390a35050565b6001600160a01b0382166110395760405162461bcd60e51b815260206004820152602160248201527f45524332303a206275726e2066726f6d20746865207a65726f206164647265736044820152607360f81b60648201526084016105aa565b61104582600083611318565b6001600160a01b038216600090815260026020526040902054818110156110b95760405162461bcd60e51b815260206004820152602260248201527f45524332303a206275726e20616d6f756e7420657863656564732062616c616e604482015261636560f01b60648201526084016105aa565b6110c3828261175f565b6001600160a01b038416600090815260026020526040812091909155600480548492906110f190849061175f565b90915550506040518281526000906001600160a01b038516907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef90602001610b5b565b60075460ff161561117a5760405162461bcd60e51b815260206004820152601060248201526f14185d5cd8589b194e881c185d5cd95960821b60448201526064016105aa565b6007805460ff191660011790557f62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258610ed13390565b60006108e78383611475565b600061046e825490565b6000828152602081905260409020600101546111e090610d62565b610e3c5760405162461bcd60e51b815260206004820152603060248201527f416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e60448201526f2061646d696e20746f207265766f6b6560801b60648201526084016105aa565b61124f82826108ee565b610797576000828152602081815260408083206001600160a01b03851684529091529020805460ff191660011790556112853390565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b60008181526001830160205260408120546113105750815460018181018455600084815260208082209093018490558454848252828601909352604090209190915561046e565b50600061046e565b6105f48383836114fb565b61132d82826108ee565b15610797576000828152602081815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b6000818152600183016020526040812054801561146b5760006113ac60018361175f565b85549091506000906113c09060019061175f565b905060008660000182815481106113d9576113d961178e565b90600052602060002001549050808760000184815481106113fc576113fc61178e565b60009182526020808320909101929092558281526001890190915260409020849055865487908061142f5761142f6117a4565b6001900381819060005260206000200160009055905586600101600087815260200190815260200160002060009055600194505050505061046e565b600091505061046e565b815460009082106114d35760405162461bcd60e51b815260206004820152602260248201527f456e756d657261626c655365743a20696e646578206f7574206f6620626f756e604482015261647360f01b60648201526084016105aa565b8260000182815481106114e8576114e861178e565b9060005260206000200154905092915050565b60075460ff16156105f45760405162461bcd60e51b815260206004820152602a60248201527f45524332305061757361626c653a20746f6b656e207472616e736665722077686044820152691a5b19481c185d5cd95960b21b60648201526084016105aa565b60006020828403121561157357600080fd5b81356001600160e01b0319811681146108e757600080fd5b600060208083528351808285015260005b818110156115b85785810183015185820160400152820161159c565b818111156115ca576000604083870101525b50601f01601f1916929092016040019392505050565b80356001600160a01b03811681146115f757600080fd5b919050565b6000806040838503121561160f57600080fd5b611618836115e0565b946020939093013593505050565b60008060006060848603121561163b57600080fd5b611644846115e0565b9250611652602085016115e0565b9150604084013590509250925092565b60006020828403121561167457600080fd5b5035919050565b6000806040838503121561168e57600080fd5b8235915061169e602084016115e0565b90509250929050565b6000602082840312156116b957600080fd5b6108e7826115e0565b600080604083850312156116d557600080fd5b50508035926020909101359150565b600080604083850312156116f757600080fd5b611700836115e0565b915061169e602084016115e0565b600181811c9082168061172257607f821691505b6020821081141561174357634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052601160045260246000fd5b60008282101561177157611771611749565b500390565b6000821982111561178957611789611749565b500190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052603160045260246000fdfea26469706673582212203693de9e578224d2d91b9f214f36bff5bbbaf4170534ce864bbef2c6d5165ce864736f6c63430008090033",
}

// ERC20MinterPauserABI is the input ABI used to generate the binding from.
// Deprecated: Use ERC20MinterPauserMetaData.ABI instead.
var ERC20MinterPauserABI = ERC20MinterPauserMetaData.ABI

// ERC20MinterPauserBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use ERC20MinterPauserMetaData.Bin instead.
var ERC20MinterPauserBin = ERC20MinterPauserMetaData.Bin

// DeployERC20MinterPauser deploys a new Ethereum contract, binding an instance of ERC20MinterPauser to it.
func DeployERC20MinterPauser(auth *bind.TransactOpts, backend bind.ContractBackend, name string, symbol string) (common.Address, *types.Transaction, *ERC20MinterPauser, error) {
	parsed, err := ERC20MinterPauserMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(ERC20MinterPauserBin), backend, name, symbol)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &ERC20MinterPauser{ERC20MinterPauserCaller: ERC20MinterPauserCaller{contract: contract}, ERC20MinterPauserTransactor: ERC20MinterPauserTransactor{contract: contract}, ERC20MinterPauserFilterer: ERC20MinterPauserFilterer{contract: contract}}, nil
}

// ERC20MinterPauser is an auto generated Go binding around an Ethereum contract.
type ERC20MinterPauser struct {
	ERC20MinterPauserCaller     // Read-only binding to the contract
	ERC20MinterPauserTransactor // Write-only binding to the contract
	ERC20MinterPauserFilterer   // Log filterer for contract events
}

// ERC20MinterPauserCaller is an auto generated read-only Go binding around an Ethereum contract.
type ERC20MinterPauserCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20MinterPauserTransactor is an auto generated write-only Go binding around an Ethereum contract.
type ERC20MinterPauserTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20MinterPauserFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type ERC20MinterPauserFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20MinterPauserSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type ERC20MinterPauserSession struct {
	Contract     *ERC20MinterPauser // Generic contract binding to set the session for
	CallOpts     bind.CallOpts      // Call options to use throughout this session
	TransactOpts bind.TransactOpts  // Transaction auth options to use throughout this session
}

// ERC20MinterPauserCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type ERC20MinterPauserCallerSession struct {
	Contract *ERC20MinterPauserCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts            // Call options to use throughout this session
}

// ERC20MinterPauserTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type ERC20MinterPauserTransactorSession struct {
	Contract     *ERC20MinterPauserTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts            // Transaction auth options to use throughout this session
}

// ERC20MinterPauserRaw is an auto generated low-level Go binding around an Ethereum contract.
type ERC20MinterPauserRaw struct {
	Contract *ERC20MinterPauser // Generic contract binding to access the raw methods on
}

// ERC20MinterPauserCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type ERC20MinterPauserCallerRaw struct {
	Contract *ERC20MinterPauserCaller // Generic read-only contract binding to access the raw methods on
}

// ERC20MinterPauserTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type ERC20MinterPauserTransactorRaw struct {
	Contract *ERC20MinterPauserTransactor // Generic write-only contract binding to access the raw methods on
}

// NewERC20MinterPauser creates a new instance of ERC20MinterPauser, bound to a specific deployed contract.
func NewERC20MinterPauser(address common.Address, backend bind.ContractBackend) (*ERC20MinterPauser, error) {
	contract, err := bindERC20MinterPauser(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauser{ERC20MinterPauserCaller: ERC20MinterPauserCaller{contract: contract}, ERC20MinterPauserTransactor: ERC20MinterPauserTransactor{contract: contract}, ERC20MinterPauserFilterer: ERC20MinterPauserFilterer{contract: contract}}, nil
}

// NewERC20MinterPauserCaller creates a new read-only instance of ERC20MinterPauser, bound to a specific deployed contract.
func NewERC20MinterPauserCaller(address common.Address, caller bind.ContractCaller) (*ERC20MinterPauserCaller, error) {
	contract, err := bindERC20MinterPauser(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserCaller{contract: contract}, nil
}

// NewERC20MinterPauserTransactor creates a new write-only instance of ERC20MinterPauser, bound to a specific deployed contract.
func NewERC20MinterPauserTransactor(address common.Address, transactor bind.ContractTransactor) (*ERC20MinterPauserTransactor, error) {
	contract, err := bindERC20MinterPauser(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserTransactor{contract: contract}, nil
}

// NewERC20MinterPauserFilterer creates a new log filterer instance of ERC20MinterPauser, bound to a specific deployed contract.
func NewERC20MinterPauserFilterer(address common.Address, filterer bind.ContractFilterer) (*ERC20MinterPauserFilterer, error) {
	contract, err := bindERC20MinterPauser(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserFilterer{contract: contract}, nil
}

// bindERC20MinterPauser binds a generic wrapper to an already deployed contract.
func bindERC20MinterPauser(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(ERC20MinterPauserABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_ERC20MinterPauser *ERC20MinterPauserRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _ERC20MinterPauser.Contract.ERC20MinterPauserCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_ERC20MinterPauser *ERC20MinterPauserRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.ERC20MinterPauserTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_ERC20MinterPauser *ERC20MinterPauserRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.ERC20MinterPauserTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_ERC20MinterPauser *ERC20MinterPauserCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _ERC20MinterPauser.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_ERC20MinterPauser *ERC20MinterPauserTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_ERC20MinterPauser *ERC20MinterPauserTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.contract.Transact(opts, method, params...)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) DEFAULTADMINROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "DEFAULT_ADMIN_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.DEFAULTADMINROLE(&_ERC20MinterPauser.CallOpts)
}

// DEFAULTADMINROLE is a free data retrieval call binding the contract method 0xa217fddf.
//
// Solidity: function DEFAULT_ADMIN_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) DEFAULTADMINROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.DEFAULTADMINROLE(&_ERC20MinterPauser.CallOpts)
}

// MINTERROLE is a free data retrieval call binding the contract method 0xd5391393.
//
// Solidity: function MINTER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) MINTERROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "MINTER_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// MINTERROLE is a free data retrieval call binding the contract method 0xd5391393.
//
// Solidity: function MINTER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserSession) MINTERROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.MINTERROLE(&_ERC20MinterPauser.CallOpts)
}

// MINTERROLE is a free data retrieval call binding the contract method 0xd5391393.
//
// Solidity: function MINTER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) MINTERROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.MINTERROLE(&_ERC20MinterPauser.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) PAUSERROLE(opts *bind.CallOpts) ([32]byte, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "PAUSER_ROLE")

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserSession) PAUSERROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.PAUSERROLE(&_ERC20MinterPauser.CallOpts)
}

// PAUSERROLE is a free data retrieval call binding the contract method 0xe63ab1e9.
//
// Solidity: function PAUSER_ROLE() view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) PAUSERROLE() ([32]byte, error) {
	return _ERC20MinterPauser.Contract.PAUSERROLE(&_ERC20MinterPauser.CallOpts)
}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) Allowance(opts *bind.CallOpts, owner common.Address, spender common.Address) (*big.Int, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "allowance", owner, spender)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Allowance(owner common.Address, spender common.Address) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.Allowance(&_ERC20MinterPauser.CallOpts, owner, spender)
}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) Allowance(owner common.Address, spender common.Address) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.Allowance(&_ERC20MinterPauser.CallOpts, owner, spender)
}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) BalanceOf(opts *bind.CallOpts, account common.Address) (*big.Int, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "balanceOf", account)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserSession) BalanceOf(account common.Address) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.BalanceOf(&_ERC20MinterPauser.CallOpts, account)
}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) BalanceOf(account common.Address) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.BalanceOf(&_ERC20MinterPauser.CallOpts, account)
}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) Decimals(opts *bind.CallOpts) (uint8, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "decimals")

	if err != nil {
		return *new(uint8), err
	}

	out0 := *abi.ConvertType(out[0], new(uint8)).(*uint8)

	return out0, err

}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Decimals() (uint8, error) {
	return _ERC20MinterPauser.Contract.Decimals(&_ERC20MinterPauser.CallOpts)
}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) Decimals() (uint8, error) {
	return _ERC20MinterPauser.Contract.Decimals(&_ERC20MinterPauser.CallOpts)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) GetRoleAdmin(opts *bind.CallOpts, role [32]byte) ([32]byte, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "getRoleAdmin", role)

	if err != nil {
		return *new([32]byte), err
	}

	out0 := *abi.ConvertType(out[0], new([32]byte)).(*[32]byte)

	return out0, err

}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _ERC20MinterPauser.Contract.GetRoleAdmin(&_ERC20MinterPauser.CallOpts, role)
}

// GetRoleAdmin is a free data retrieval call binding the contract method 0x248a9ca3.
//
// Solidity: function getRoleAdmin(bytes32 role) view returns(bytes32)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) GetRoleAdmin(role [32]byte) ([32]byte, error) {
	return _ERC20MinterPauser.Contract.GetRoleAdmin(&_ERC20MinterPauser.CallOpts, role)
}

// GetRoleMember is a free data retrieval call binding the contract method 0x9010d07c.
//
// Solidity: function getRoleMember(bytes32 role, uint256 index) view returns(address)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) GetRoleMember(opts *bind.CallOpts, role [32]byte, index *big.Int) (common.Address, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "getRoleMember", role, index)

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// GetRoleMember is a free data retrieval call binding the contract method 0x9010d07c.
//
// Solidity: function getRoleMember(bytes32 role, uint256 index) view returns(address)
func (_ERC20MinterPauser *ERC20MinterPauserSession) GetRoleMember(role [32]byte, index *big.Int) (common.Address, error) {
	return _ERC20MinterPauser.Contract.GetRoleMember(&_ERC20MinterPauser.CallOpts, role, index)
}

// GetRoleMember is a free data retrieval call binding the contract method 0x9010d07c.
//
// Solidity: function getRoleMember(bytes32 role, uint256 index) view returns(address)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) GetRoleMember(role [32]byte, index *big.Int) (common.Address, error) {
	return _ERC20MinterPauser.Contract.GetRoleMember(&_ERC20MinterPauser.CallOpts, role, index)
}

// GetRoleMemberCount is a free data retrieval call binding the contract method 0xca15c873.
//
// Solidity: function getRoleMemberCount(bytes32 role) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) GetRoleMemberCount(opts *bind.CallOpts, role [32]byte) (*big.Int, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "getRoleMemberCount", role)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// GetRoleMemberCount is a free data retrieval call binding the contract method 0xca15c873.
//
// Solidity: function getRoleMemberCount(bytes32 role) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserSession) GetRoleMemberCount(role [32]byte) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.GetRoleMemberCount(&_ERC20MinterPauser.CallOpts, role)
}

// GetRoleMemberCount is a free data retrieval call binding the contract method 0xca15c873.
//
// Solidity: function getRoleMemberCount(bytes32 role) view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) GetRoleMemberCount(role [32]byte) (*big.Int, error) {
	return _ERC20MinterPauser.Contract.GetRoleMemberCount(&_ERC20MinterPauser.CallOpts, role)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) HasRole(opts *bind.CallOpts, role [32]byte, account common.Address) (bool, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "hasRole", role, account)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _ERC20MinterPauser.Contract.HasRole(&_ERC20MinterPauser.CallOpts, role, account)
}

// HasRole is a free data retrieval call binding the contract method 0x91d14854.
//
// Solidity: function hasRole(bytes32 role, address account) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) HasRole(role [32]byte, account common.Address) (bool, error) {
	return _ERC20MinterPauser.Contract.HasRole(&_ERC20MinterPauser.CallOpts, role, account)
}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) Name(opts *bind.CallOpts) (string, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "name")

	if err != nil {
		return *new(string), err
	}

	out0 := *abi.ConvertType(out[0], new(string)).(*string)

	return out0, err

}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Name() (string, error) {
	return _ERC20MinterPauser.Contract.Name(&_ERC20MinterPauser.CallOpts)
}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) Name() (string, error) {
	return _ERC20MinterPauser.Contract.Name(&_ERC20MinterPauser.CallOpts)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) Paused(opts *bind.CallOpts) (bool, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "paused")

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Paused() (bool, error) {
	return _ERC20MinterPauser.Contract.Paused(&_ERC20MinterPauser.CallOpts)
}

// Paused is a free data retrieval call binding the contract method 0x5c975abb.
//
// Solidity: function paused() view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) Paused() (bool, error) {
	return _ERC20MinterPauser.Contract.Paused(&_ERC20MinterPauser.CallOpts)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) SupportsInterface(opts *bind.CallOpts, interfaceId [4]byte) (bool, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "supportsInterface", interfaceId)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _ERC20MinterPauser.Contract.SupportsInterface(&_ERC20MinterPauser.CallOpts, interfaceId)
}

// SupportsInterface is a free data retrieval call binding the contract method 0x01ffc9a7.
//
// Solidity: function supportsInterface(bytes4 interfaceId) view returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) SupportsInterface(interfaceId [4]byte) (bool, error) {
	return _ERC20MinterPauser.Contract.SupportsInterface(&_ERC20MinterPauser.CallOpts, interfaceId)
}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) Symbol(opts *bind.CallOpts) (string, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "symbol")

	if err != nil {
		return *new(string), err
	}

	out0 := *abi.ConvertType(out[0], new(string)).(*string)

	return out0, err

}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Symbol() (string, error) {
	return _ERC20MinterPauser.Contract.Symbol(&_ERC20MinterPauser.CallOpts)
}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) Symbol() (string, error) {
	return _ERC20MinterPauser.Contract.Symbol(&_ERC20MinterPauser.CallOpts)
}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCaller) TotalSupply(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _ERC20MinterPauser.contract.Call(opts, &out, "totalSupply")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserSession) TotalSupply() (*big.Int, error) {
	return _ERC20MinterPauser.Contract.TotalSupply(&_ERC20MinterPauser.CallOpts)
}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20MinterPauser *ERC20MinterPauserCallerSession) TotalSupply() (*big.Int, error) {
	return _ERC20MinterPauser.Contract.TotalSupply(&_ERC20MinterPauser.CallOpts)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Approve(opts *bind.TransactOpts, spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "approve", spender, amount)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Approve(spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Approve(&_ERC20MinterPauser.TransactOpts, spender, amount)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Approve(spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Approve(&_ERC20MinterPauser.TransactOpts, spender, amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Burn(opts *bind.TransactOpts, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "burn", amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) Burn(amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Burn(&_ERC20MinterPauser.TransactOpts, amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Burn(amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Burn(&_ERC20MinterPauser.TransactOpts, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) BurnFrom(opts *bind.TransactOpts, account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "burnFrom", account, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) BurnFrom(account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.BurnFrom(&_ERC20MinterPauser.TransactOpts, account, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) BurnFrom(account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.BurnFrom(&_ERC20MinterPauser.TransactOpts, account, amount)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) DecreaseAllowance(opts *bind.TransactOpts, spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "decreaseAllowance", spender, subtractedValue)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) DecreaseAllowance(spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.DecreaseAllowance(&_ERC20MinterPauser.TransactOpts, spender, subtractedValue)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) DecreaseAllowance(spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.DecreaseAllowance(&_ERC20MinterPauser.TransactOpts, spender, subtractedValue)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) GrantRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "grantRole", role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.GrantRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// GrantRole is a paid mutator transaction binding the contract method 0x2f2ff15d.
//
// Solidity: function grantRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) GrantRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.GrantRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) IncreaseAllowance(opts *bind.TransactOpts, spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "increaseAllowance", spender, addedValue)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) IncreaseAllowance(spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.IncreaseAllowance(&_ERC20MinterPauser.TransactOpts, spender, addedValue)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) IncreaseAllowance(spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.IncreaseAllowance(&_ERC20MinterPauser.TransactOpts, spender, addedValue)
}

// Mint is a paid mutator transaction binding the contract method 0x40c10f19.
//
// Solidity: function mint(address to, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Mint(opts *bind.TransactOpts, to common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "mint", to, amount)
}

// Mint is a paid mutator transaction binding the contract method 0x40c10f19.
//
// Solidity: function mint(address to, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) Mint(to common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Mint(&_ERC20MinterPauser.TransactOpts, to, amount)
}

// Mint is a paid mutator transaction binding the contract method 0x40c10f19.
//
// Solidity: function mint(address to, uint256 amount) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Mint(to common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Mint(&_ERC20MinterPauser.TransactOpts, to, amount)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Pause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "pause")
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) Pause() (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Pause(&_ERC20MinterPauser.TransactOpts)
}

// Pause is a paid mutator transaction binding the contract method 0x8456cb59.
//
// Solidity: function pause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Pause() (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Pause(&_ERC20MinterPauser.TransactOpts)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) RenounceRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "renounceRole", role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.RenounceRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// RenounceRole is a paid mutator transaction binding the contract method 0x36568abe.
//
// Solidity: function renounceRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) RenounceRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.RenounceRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) RevokeRole(opts *bind.TransactOpts, role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "revokeRole", role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.RevokeRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// RevokeRole is a paid mutator transaction binding the contract method 0xd547741f.
//
// Solidity: function revokeRole(bytes32 role, address account) returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) RevokeRole(role [32]byte, account common.Address) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.RevokeRole(&_ERC20MinterPauser.TransactOpts, role, account)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Transfer(opts *bind.TransactOpts, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "transfer", recipient, amount)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) Transfer(recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Transfer(&_ERC20MinterPauser.TransactOpts, recipient, amount)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Transfer(recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Transfer(&_ERC20MinterPauser.TransactOpts, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) TransferFrom(opts *bind.TransactOpts, sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "transferFrom", sender, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserSession) TransferFrom(sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.TransferFrom(&_ERC20MinterPauser.TransactOpts, sender, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) TransferFrom(sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.TransferFrom(&_ERC20MinterPauser.TransactOpts, sender, recipient, amount)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactor) Unpause(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20MinterPauser.contract.Transact(opts, "unpause")
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserSession) Unpause() (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Unpause(&_ERC20MinterPauser.TransactOpts)
}

// Unpause is a paid mutator transaction binding the contract method 0x3f4ba83a.
//
// Solidity: function unpause() returns()
func (_ERC20MinterPauser *ERC20MinterPauserTransactorSession) Unpause() (*types.Transaction, error) {
	return _ERC20MinterPauser.Contract.Unpause(&_ERC20MinterPauser.TransactOpts)
}

// ERC20MinterPauserApprovalIterator is returned from FilterApproval and is used to iterate over the raw logs and unpacked data for Approval events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserApprovalIterator struct {
	Event *ERC20MinterPauserApproval // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserApprovalIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserApproval)
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
		it.Event = new(ERC20MinterPauserApproval)
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
func (it *ERC20MinterPauserApprovalIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserApprovalIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserApproval represents a Approval event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserApproval struct {
	Owner   common.Address
	Spender common.Address
	Value   *big.Int
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterApproval is a free log retrieval operation binding the contract event 0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925.
//
// Solidity: event Approval(address indexed owner, address indexed spender, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterApproval(opts *bind.FilterOpts, owner []common.Address, spender []common.Address) (*ERC20MinterPauserApprovalIterator, error) {

	var ownerRule []interface{}
	for _, ownerItem := range owner {
		ownerRule = append(ownerRule, ownerItem)
	}
	var spenderRule []interface{}
	for _, spenderItem := range spender {
		spenderRule = append(spenderRule, spenderItem)
	}

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "Approval", ownerRule, spenderRule)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserApprovalIterator{contract: _ERC20MinterPauser.contract, event: "Approval", logs: logs, sub: sub}, nil
}

// WatchApproval is a free log subscription operation binding the contract event 0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925.
//
// Solidity: event Approval(address indexed owner, address indexed spender, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchApproval(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserApproval, owner []common.Address, spender []common.Address) (event.Subscription, error) {

	var ownerRule []interface{}
	for _, ownerItem := range owner {
		ownerRule = append(ownerRule, ownerItem)
	}
	var spenderRule []interface{}
	for _, spenderItem := range spender {
		spenderRule = append(spenderRule, spenderItem)
	}

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "Approval", ownerRule, spenderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserApproval)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "Approval", log); err != nil {
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

// ParseApproval is a log parse operation binding the contract event 0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925.
//
// Solidity: event Approval(address indexed owner, address indexed spender, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseApproval(log types.Log) (*ERC20MinterPauserApproval, error) {
	event := new(ERC20MinterPauserApproval)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "Approval", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserPausedIterator is returned from FilterPaused and is used to iterate over the raw logs and unpacked data for Paused events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserPausedIterator struct {
	Event *ERC20MinterPauserPaused // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserPausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserPaused)
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
		it.Event = new(ERC20MinterPauserPaused)
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
func (it *ERC20MinterPauserPausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserPausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserPaused represents a Paused event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserPaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterPaused is a free log retrieval operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterPaused(opts *bind.FilterOpts) (*ERC20MinterPauserPausedIterator, error) {

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserPausedIterator{contract: _ERC20MinterPauser.contract, event: "Paused", logs: logs, sub: sub}, nil
}

// WatchPaused is a free log subscription operation binding the contract event 0x62e78cea01bee320cd4e420270b5ea74000d11b0c9f74754ebdbfc544b05a258.
//
// Solidity: event Paused(address account)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchPaused(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserPaused) (event.Subscription, error) {

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "Paused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserPaused)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "Paused", log); err != nil {
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
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParsePaused(log types.Log) (*ERC20MinterPauserPaused, error) {
	event := new(ERC20MinterPauserPaused)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "Paused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserRoleAdminChangedIterator is returned from FilterRoleAdminChanged and is used to iterate over the raw logs and unpacked data for RoleAdminChanged events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleAdminChangedIterator struct {
	Event *ERC20MinterPauserRoleAdminChanged // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserRoleAdminChangedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserRoleAdminChanged)
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
		it.Event = new(ERC20MinterPauserRoleAdminChanged)
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
func (it *ERC20MinterPauserRoleAdminChangedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserRoleAdminChangedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserRoleAdminChanged represents a RoleAdminChanged event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleAdminChanged struct {
	Role              [32]byte
	PreviousAdminRole [32]byte
	NewAdminRole      [32]byte
	Raw               types.Log // Blockchain specific contextual infos
}

// FilterRoleAdminChanged is a free log retrieval operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterRoleAdminChanged(opts *bind.FilterOpts, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (*ERC20MinterPauserRoleAdminChangedIterator, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserRoleAdminChangedIterator{contract: _ERC20MinterPauser.contract, event: "RoleAdminChanged", logs: logs, sub: sub}, nil
}

// WatchRoleAdminChanged is a free log subscription operation binding the contract event 0xbd79b86ffe0ab8e8776151514217cd7cacd52c909f66475c3af44e129f0b00ff.
//
// Solidity: event RoleAdminChanged(bytes32 indexed role, bytes32 indexed previousAdminRole, bytes32 indexed newAdminRole)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchRoleAdminChanged(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserRoleAdminChanged, role [][32]byte, previousAdminRole [][32]byte, newAdminRole [][32]byte) (event.Subscription, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "RoleAdminChanged", roleRule, previousAdminRoleRule, newAdminRoleRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserRoleAdminChanged)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
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
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseRoleAdminChanged(log types.Log) (*ERC20MinterPauserRoleAdminChanged, error) {
	event := new(ERC20MinterPauserRoleAdminChanged)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleAdminChanged", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserRoleGrantedIterator is returned from FilterRoleGranted and is used to iterate over the raw logs and unpacked data for RoleGranted events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleGrantedIterator struct {
	Event *ERC20MinterPauserRoleGranted // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserRoleGrantedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserRoleGranted)
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
		it.Event = new(ERC20MinterPauserRoleGranted)
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
func (it *ERC20MinterPauserRoleGrantedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserRoleGrantedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserRoleGranted represents a RoleGranted event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleGranted struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleGranted is a free log retrieval operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterRoleGranted(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*ERC20MinterPauserRoleGrantedIterator, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserRoleGrantedIterator{contract: _ERC20MinterPauser.contract, event: "RoleGranted", logs: logs, sub: sub}, nil
}

// WatchRoleGranted is a free log subscription operation binding the contract event 0x2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d.
//
// Solidity: event RoleGranted(bytes32 indexed role, address indexed account, address indexed sender)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchRoleGranted(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserRoleGranted, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "RoleGranted", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserRoleGranted)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleGranted", log); err != nil {
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
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseRoleGranted(log types.Log) (*ERC20MinterPauserRoleGranted, error) {
	event := new(ERC20MinterPauserRoleGranted)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleGranted", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserRoleRevokedIterator is returned from FilterRoleRevoked and is used to iterate over the raw logs and unpacked data for RoleRevoked events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleRevokedIterator struct {
	Event *ERC20MinterPauserRoleRevoked // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserRoleRevokedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserRoleRevoked)
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
		it.Event = new(ERC20MinterPauserRoleRevoked)
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
func (it *ERC20MinterPauserRoleRevokedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserRoleRevokedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserRoleRevoked represents a RoleRevoked event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserRoleRevoked struct {
	Role    [32]byte
	Account common.Address
	Sender  common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterRoleRevoked is a free log retrieval operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterRoleRevoked(opts *bind.FilterOpts, role [][32]byte, account []common.Address, sender []common.Address) (*ERC20MinterPauserRoleRevokedIterator, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserRoleRevokedIterator{contract: _ERC20MinterPauser.contract, event: "RoleRevoked", logs: logs, sub: sub}, nil
}

// WatchRoleRevoked is a free log subscription operation binding the contract event 0xf6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b.
//
// Solidity: event RoleRevoked(bytes32 indexed role, address indexed account, address indexed sender)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchRoleRevoked(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserRoleRevoked, role [][32]byte, account []common.Address, sender []common.Address) (event.Subscription, error) {

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

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "RoleRevoked", roleRule, accountRule, senderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserRoleRevoked)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
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
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseRoleRevoked(log types.Log) (*ERC20MinterPauserRoleRevoked, error) {
	event := new(ERC20MinterPauserRoleRevoked)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "RoleRevoked", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserTransferIterator is returned from FilterTransfer and is used to iterate over the raw logs and unpacked data for Transfer events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserTransferIterator struct {
	Event *ERC20MinterPauserTransfer // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserTransferIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserTransfer)
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
		it.Event = new(ERC20MinterPauserTransfer)
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
func (it *ERC20MinterPauserTransferIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserTransferIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserTransfer represents a Transfer event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserTransfer struct {
	From  common.Address
	To    common.Address
	Value *big.Int
	Raw   types.Log // Blockchain specific contextual infos
}

// FilterTransfer is a free log retrieval operation binding the contract event 0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef.
//
// Solidity: event Transfer(address indexed from, address indexed to, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterTransfer(opts *bind.FilterOpts, from []common.Address, to []common.Address) (*ERC20MinterPauserTransferIterator, error) {

	var fromRule []interface{}
	for _, fromItem := range from {
		fromRule = append(fromRule, fromItem)
	}
	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "Transfer", fromRule, toRule)
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserTransferIterator{contract: _ERC20MinterPauser.contract, event: "Transfer", logs: logs, sub: sub}, nil
}

// WatchTransfer is a free log subscription operation binding the contract event 0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef.
//
// Solidity: event Transfer(address indexed from, address indexed to, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchTransfer(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserTransfer, from []common.Address, to []common.Address) (event.Subscription, error) {

	var fromRule []interface{}
	for _, fromItem := range from {
		fromRule = append(fromRule, fromItem)
	}
	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "Transfer", fromRule, toRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserTransfer)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "Transfer", log); err != nil {
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

// ParseTransfer is a log parse operation binding the contract event 0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef.
//
// Solidity: event Transfer(address indexed from, address indexed to, uint256 value)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseTransfer(log types.Log) (*ERC20MinterPauserTransfer, error) {
	event := new(ERC20MinterPauserTransfer)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "Transfer", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20MinterPauserUnpausedIterator is returned from FilterUnpaused and is used to iterate over the raw logs and unpacked data for Unpaused events raised by the ERC20MinterPauser contract.
type ERC20MinterPauserUnpausedIterator struct {
	Event *ERC20MinterPauserUnpaused // Event containing the contract specifics and raw log

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
func (it *ERC20MinterPauserUnpausedIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20MinterPauserUnpaused)
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
		it.Event = new(ERC20MinterPauserUnpaused)
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
func (it *ERC20MinterPauserUnpausedIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20MinterPauserUnpausedIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20MinterPauserUnpaused represents a Unpaused event raised by the ERC20MinterPauser contract.
type ERC20MinterPauserUnpaused struct {
	Account common.Address
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterUnpaused is a free log retrieval operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) FilterUnpaused(opts *bind.FilterOpts) (*ERC20MinterPauserUnpausedIterator, error) {

	logs, sub, err := _ERC20MinterPauser.contract.FilterLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return &ERC20MinterPauserUnpausedIterator{contract: _ERC20MinterPauser.contract, event: "Unpaused", logs: logs, sub: sub}, nil
}

// WatchUnpaused is a free log subscription operation binding the contract event 0x5db9ee0a495bf2e6ff9c91a7834c1ba4fdd244a5e8aa4e537bd38aeae4b073aa.
//
// Solidity: event Unpaused(address account)
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) WatchUnpaused(opts *bind.WatchOpts, sink chan<- *ERC20MinterPauserUnpaused) (event.Subscription, error) {

	logs, sub, err := _ERC20MinterPauser.contract.WatchLogs(opts, "Unpaused")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20MinterPauserUnpaused)
				if err := _ERC20MinterPauser.contract.UnpackLog(event, "Unpaused", log); err != nil {
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
func (_ERC20MinterPauser *ERC20MinterPauserFilterer) ParseUnpaused(log types.Log) (*ERC20MinterPauserUnpaused, error) {
	event := new(ERC20MinterPauserUnpaused)
	if err := _ERC20MinterPauser.contract.UnpackLog(event, "Unpaused", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
