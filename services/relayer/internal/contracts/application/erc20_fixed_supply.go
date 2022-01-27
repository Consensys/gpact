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

// ERC20FixedSupplyMetaData contains all meta data concerning the ERC20FixedSupply contract.
var ERC20FixedSupplyMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"symbol\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"initialSupply\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"burn\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"burnFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x60806040523480156200001157600080fd5b5060405162000f2238038062000f228339810160408190526200003491620002dd565b8351849084906200004d9060039060208501906200016a565b508051620000639060049060208401906200016a565b5050506200007881836200008260201b60201c565b50505050620003d6565b6001600160a01b038216620000dd5760405162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015260640160405180910390fd5b8060026000828254620000f1919062000372565b90915550506001600160a01b038216600090815260208190526040812080548392906200012090849062000372565b90915550506040518181526001600160a01b038316906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9060200160405180910390a35050565b828054620001789062000399565b90600052602060002090601f0160209004810192826200019c5760008555620001e7565b82601f10620001b757805160ff1916838001178555620001e7565b82800160010185558215620001e7579182015b82811115620001e7578251825591602001919060010190620001ca565b50620001f5929150620001f9565b5090565b5b80821115620001f55760008155600101620001fa565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126200023857600080fd5b81516001600160401b038082111562000255576200025562000210565b604051601f8301601f19908116603f0116810190828211818310171562000280576200028062000210565b816040528381526020925086838588010111156200029d57600080fd5b600091505b83821015620002c15785820183015181830184015290820190620002a2565b83821115620002d35760008385830101525b9695505050505050565b60008060008060808587031215620002f457600080fd5b84516001600160401b03808211156200030c57600080fd5b6200031a8883890162000226565b955060208701519150808211156200033157600080fd5b50620003408782880162000226565b60408701516060880151919550935090506001600160a01b03811681146200036757600080fd5b939692955090935050565b600082198211156200039457634e487b7160e01b600052601160045260246000fd5b500190565b600181811c90821680620003ae57607f821691505b60208210811415620003d057634e487b7160e01b600052602260045260246000fd5b50919050565b610b3c80620003e66000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c806342966c681161008c57806395d89b411161006657806395d89b41146101ad578063a457c2d7146101b5578063a9059cbb146101c8578063dd62ed3e146101db57600080fd5b806342966c681461015c57806370a082311461017157806379cc67901461019a57600080fd5b806306fdde03146100d4578063095ea7b3146100f257806318160ddd1461011557806323b872dd14610127578063313ce5671461013a5780633950935114610149575b600080fd5b6100dc610214565b6040516100e99190610941565b60405180910390f35b6101056101003660046109b2565b6102a6565b60405190151581526020016100e9565b6002545b6040519081526020016100e9565b6101056101353660046109dc565b6102bc565b604051601281526020016100e9565b6101056101573660046109b2565b610372565b61016f61016a366004610a18565b6103a9565b005b61011961017f366004610a31565b6001600160a01b031660009081526020819052604090205490565b61016f6101a83660046109b2565b6103b6565b6100dc61043e565b6101056101c33660046109b2565b61044d565b6101056101d63660046109b2565b6104e8565b6101196101e9366004610a53565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b60606003805461022390610a86565b80601f016020809104026020016040519081016040528092919081815260200182805461024f90610a86565b801561029c5780601f106102715761010080835404028352916020019161029c565b820191906000526020600020905b81548152906001019060200180831161027f57829003601f168201915b5050505050905090565b60006102b33384846104f5565b50600192915050565b60006102c984848461061a565b6001600160a01b0384166000908152600160209081526040808320338452909152902054828110156103535760405162461bcd60e51b815260206004820152602860248201527f45524332303a207472616e7366657220616d6f756e74206578636565647320616044820152676c6c6f77616e636560c01b60648201526084015b60405180910390fd5b61036785336103628685610ad7565b6104f5565b506001949350505050565b3360008181526001602090815260408083206001600160a01b038716845290915281205490916102b3918590610362908690610aee565b6103b333826107f2565b50565b60006103c283336101e9565b9050818110156104205760405162461bcd60e51b8152602060048201526024808201527f45524332303a206275726e20616d6f756e74206578636565647320616c6c6f77604482015263616e636560e01b606482015260840161034a565b61042f83336103628585610ad7565b61043983836107f2565b505050565b60606004805461022390610a86565b3360009081526001602090815260408083206001600160a01b0386168452909152812054828110156104cf5760405162461bcd60e51b815260206004820152602560248201527f45524332303a2064656372656173656420616c6c6f77616e63652062656c6f77604482015264207a65726f60d81b606482015260840161034a565b6104de33856103628685610ad7565b5060019392505050565b60006102b333848461061a565b6001600160a01b0383166105575760405162461bcd60e51b8152602060048201526024808201527f45524332303a20617070726f76652066726f6d20746865207a65726f206164646044820152637265737360e01b606482015260840161034a565b6001600160a01b0382166105b85760405162461bcd60e51b815260206004820152602260248201527f45524332303a20617070726f766520746f20746865207a65726f206164647265604482015261737360f01b606482015260840161034a565b6001600160a01b0383811660008181526001602090815260408083209487168084529482529182902085905590518481527f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591015b60405180910390a3505050565b6001600160a01b03831661067e5760405162461bcd60e51b815260206004820152602560248201527f45524332303a207472616e736665722066726f6d20746865207a65726f206164604482015264647265737360d81b606482015260840161034a565b6001600160a01b0382166106e05760405162461bcd60e51b815260206004820152602360248201527f45524332303a207472616e7366657220746f20746865207a65726f206164647260448201526265737360e81b606482015260840161034a565b6001600160a01b038316600090815260208190526040902054818110156107585760405162461bcd60e51b815260206004820152602660248201527f45524332303a207472616e7366657220616d6f756e7420657863656564732062604482015265616c616e636560d01b606482015260840161034a565b6107628282610ad7565b6001600160a01b038086166000908152602081905260408082209390935590851681529081208054849290610798908490610aee565b92505081905550826001600160a01b0316846001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040516107e491815260200190565b60405180910390a350505050565b6001600160a01b0382166108525760405162461bcd60e51b815260206004820152602160248201527f45524332303a206275726e2066726f6d20746865207a65726f206164647265736044820152607360f81b606482015260840161034a565b6001600160a01b038216600090815260208190526040902054818110156108c65760405162461bcd60e51b815260206004820152602260248201527f45524332303a206275726e20616d6f756e7420657863656564732062616c616e604482015261636560f01b606482015260840161034a565b6108d08282610ad7565b6001600160a01b038416600090815260208190526040812091909155600280548492906108fe908490610ad7565b90915550506040518281526000906001600160a01b038516907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9060200161060d565b600060208083528351808285015260005b8181101561096e57858101830151858201604001528201610952565b81811115610980576000604083870101525b50601f01601f1916929092016040019392505050565b80356001600160a01b03811681146109ad57600080fd5b919050565b600080604083850312156109c557600080fd5b6109ce83610996565b946020939093013593505050565b6000806000606084860312156109f157600080fd5b6109fa84610996565b9250610a0860208501610996565b9150604084013590509250925092565b600060208284031215610a2a57600080fd5b5035919050565b600060208284031215610a4357600080fd5b610a4c82610996565b9392505050565b60008060408385031215610a6657600080fd5b610a6f83610996565b9150610a7d60208401610996565b90509250929050565b600181811c90821680610a9a57607f821691505b60208210811415610abb57634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052601160045260246000fd5b600082821015610ae957610ae9610ac1565b500390565b60008219821115610b0157610b01610ac1565b50019056fea26469706673582212202e241dbb092171169068fcb551f0acb126fdaf9452653b7c960b8774a650b2ea64736f6c63430008090033",
}

// ERC20FixedSupplyABI is the input ABI used to generate the binding from.
// Deprecated: Use ERC20FixedSupplyMetaData.ABI instead.
var ERC20FixedSupplyABI = ERC20FixedSupplyMetaData.ABI

// ERC20FixedSupplyBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use ERC20FixedSupplyMetaData.Bin instead.
var ERC20FixedSupplyBin = ERC20FixedSupplyMetaData.Bin

// DeployERC20FixedSupply deploys a new Ethereum contract, binding an instance of ERC20FixedSupply to it.
func DeployERC20FixedSupply(auth *bind.TransactOpts, backend bind.ContractBackend, name string, symbol string, initialSupply *big.Int, owner common.Address) (common.Address, *types.Transaction, *ERC20FixedSupply, error) {
	parsed, err := ERC20FixedSupplyMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(ERC20FixedSupplyBin), backend, name, symbol, initialSupply, owner)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &ERC20FixedSupply{ERC20FixedSupplyCaller: ERC20FixedSupplyCaller{contract: contract}, ERC20FixedSupplyTransactor: ERC20FixedSupplyTransactor{contract: contract}, ERC20FixedSupplyFilterer: ERC20FixedSupplyFilterer{contract: contract}}, nil
}

// ERC20FixedSupply is an auto generated Go binding around an Ethereum contract.
type ERC20FixedSupply struct {
	ERC20FixedSupplyCaller     // Read-only binding to the contract
	ERC20FixedSupplyTransactor // Write-only binding to the contract
	ERC20FixedSupplyFilterer   // Log filterer for contract events
}

// ERC20FixedSupplyCaller is an auto generated read-only Go binding around an Ethereum contract.
type ERC20FixedSupplyCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20FixedSupplyTransactor is an auto generated write-only Go binding around an Ethereum contract.
type ERC20FixedSupplyTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20FixedSupplyFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type ERC20FixedSupplyFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// ERC20FixedSupplySession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type ERC20FixedSupplySession struct {
	Contract     *ERC20FixedSupply // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// ERC20FixedSupplyCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type ERC20FixedSupplyCallerSession struct {
	Contract *ERC20FixedSupplyCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts           // Call options to use throughout this session
}

// ERC20FixedSupplyTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type ERC20FixedSupplyTransactorSession struct {
	Contract     *ERC20FixedSupplyTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts           // Transaction auth options to use throughout this session
}

// ERC20FixedSupplyRaw is an auto generated low-level Go binding around an Ethereum contract.
type ERC20FixedSupplyRaw struct {
	Contract *ERC20FixedSupply // Generic contract binding to access the raw methods on
}

// ERC20FixedSupplyCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type ERC20FixedSupplyCallerRaw struct {
	Contract *ERC20FixedSupplyCaller // Generic read-only contract binding to access the raw methods on
}

// ERC20FixedSupplyTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type ERC20FixedSupplyTransactorRaw struct {
	Contract *ERC20FixedSupplyTransactor // Generic write-only contract binding to access the raw methods on
}

// NewERC20FixedSupply creates a new instance of ERC20FixedSupply, bound to a specific deployed contract.
func NewERC20FixedSupply(address common.Address, backend bind.ContractBackend) (*ERC20FixedSupply, error) {
	contract, err := bindERC20FixedSupply(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupply{ERC20FixedSupplyCaller: ERC20FixedSupplyCaller{contract: contract}, ERC20FixedSupplyTransactor: ERC20FixedSupplyTransactor{contract: contract}, ERC20FixedSupplyFilterer: ERC20FixedSupplyFilterer{contract: contract}}, nil
}

// NewERC20FixedSupplyCaller creates a new read-only instance of ERC20FixedSupply, bound to a specific deployed contract.
func NewERC20FixedSupplyCaller(address common.Address, caller bind.ContractCaller) (*ERC20FixedSupplyCaller, error) {
	contract, err := bindERC20FixedSupply(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupplyCaller{contract: contract}, nil
}

// NewERC20FixedSupplyTransactor creates a new write-only instance of ERC20FixedSupply, bound to a specific deployed contract.
func NewERC20FixedSupplyTransactor(address common.Address, transactor bind.ContractTransactor) (*ERC20FixedSupplyTransactor, error) {
	contract, err := bindERC20FixedSupply(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupplyTransactor{contract: contract}, nil
}

// NewERC20FixedSupplyFilterer creates a new log filterer instance of ERC20FixedSupply, bound to a specific deployed contract.
func NewERC20FixedSupplyFilterer(address common.Address, filterer bind.ContractFilterer) (*ERC20FixedSupplyFilterer, error) {
	contract, err := bindERC20FixedSupply(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupplyFilterer{contract: contract}, nil
}

// bindERC20FixedSupply binds a generic wrapper to an already deployed contract.
func bindERC20FixedSupply(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(ERC20FixedSupplyABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_ERC20FixedSupply *ERC20FixedSupplyRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _ERC20FixedSupply.Contract.ERC20FixedSupplyCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_ERC20FixedSupply *ERC20FixedSupplyRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.ERC20FixedSupplyTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_ERC20FixedSupply *ERC20FixedSupplyRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.ERC20FixedSupplyTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_ERC20FixedSupply *ERC20FixedSupplyCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _ERC20FixedSupply.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.contract.Transact(opts, method, params...)
}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) Allowance(opts *bind.CallOpts, owner common.Address, spender common.Address) (*big.Int, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "allowance", owner, spender)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Allowance(owner common.Address, spender common.Address) (*big.Int, error) {
	return _ERC20FixedSupply.Contract.Allowance(&_ERC20FixedSupply.CallOpts, owner, spender)
}

// Allowance is a free data retrieval call binding the contract method 0xdd62ed3e.
//
// Solidity: function allowance(address owner, address spender) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) Allowance(owner common.Address, spender common.Address) (*big.Int, error) {
	return _ERC20FixedSupply.Contract.Allowance(&_ERC20FixedSupply.CallOpts, owner, spender)
}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) BalanceOf(opts *bind.CallOpts, account common.Address) (*big.Int, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "balanceOf", account)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplySession) BalanceOf(account common.Address) (*big.Int, error) {
	return _ERC20FixedSupply.Contract.BalanceOf(&_ERC20FixedSupply.CallOpts, account)
}

// BalanceOf is a free data retrieval call binding the contract method 0x70a08231.
//
// Solidity: function balanceOf(address account) view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) BalanceOf(account common.Address) (*big.Int, error) {
	return _ERC20FixedSupply.Contract.BalanceOf(&_ERC20FixedSupply.CallOpts, account)
}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) Decimals(opts *bind.CallOpts) (uint8, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "decimals")

	if err != nil {
		return *new(uint8), err
	}

	out0 := *abi.ConvertType(out[0], new(uint8)).(*uint8)

	return out0, err

}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Decimals() (uint8, error) {
	return _ERC20FixedSupply.Contract.Decimals(&_ERC20FixedSupply.CallOpts)
}

// Decimals is a free data retrieval call binding the contract method 0x313ce567.
//
// Solidity: function decimals() view returns(uint8)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) Decimals() (uint8, error) {
	return _ERC20FixedSupply.Contract.Decimals(&_ERC20FixedSupply.CallOpts)
}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) Name(opts *bind.CallOpts) (string, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "name")

	if err != nil {
		return *new(string), err
	}

	out0 := *abi.ConvertType(out[0], new(string)).(*string)

	return out0, err

}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Name() (string, error) {
	return _ERC20FixedSupply.Contract.Name(&_ERC20FixedSupply.CallOpts)
}

// Name is a free data retrieval call binding the contract method 0x06fdde03.
//
// Solidity: function name() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) Name() (string, error) {
	return _ERC20FixedSupply.Contract.Name(&_ERC20FixedSupply.CallOpts)
}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) Symbol(opts *bind.CallOpts) (string, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "symbol")

	if err != nil {
		return *new(string), err
	}

	out0 := *abi.ConvertType(out[0], new(string)).(*string)

	return out0, err

}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Symbol() (string, error) {
	return _ERC20FixedSupply.Contract.Symbol(&_ERC20FixedSupply.CallOpts)
}

// Symbol is a free data retrieval call binding the contract method 0x95d89b41.
//
// Solidity: function symbol() view returns(string)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) Symbol() (string, error) {
	return _ERC20FixedSupply.Contract.Symbol(&_ERC20FixedSupply.CallOpts)
}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCaller) TotalSupply(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _ERC20FixedSupply.contract.Call(opts, &out, "totalSupply")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplySession) TotalSupply() (*big.Int, error) {
	return _ERC20FixedSupply.Contract.TotalSupply(&_ERC20FixedSupply.CallOpts)
}

// TotalSupply is a free data retrieval call binding the contract method 0x18160ddd.
//
// Solidity: function totalSupply() view returns(uint256)
func (_ERC20FixedSupply *ERC20FixedSupplyCallerSession) TotalSupply() (*big.Int, error) {
	return _ERC20FixedSupply.Contract.TotalSupply(&_ERC20FixedSupply.CallOpts)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) Approve(opts *bind.TransactOpts, spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "approve", spender, amount)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Approve(spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Approve(&_ERC20FixedSupply.TransactOpts, spender, amount)
}

// Approve is a paid mutator transaction binding the contract method 0x095ea7b3.
//
// Solidity: function approve(address spender, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) Approve(spender common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Approve(&_ERC20FixedSupply.TransactOpts, spender, amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) Burn(opts *bind.TransactOpts, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "burn", amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplySession) Burn(amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Burn(&_ERC20FixedSupply.TransactOpts, amount)
}

// Burn is a paid mutator transaction binding the contract method 0x42966c68.
//
// Solidity: function burn(uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) Burn(amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Burn(&_ERC20FixedSupply.TransactOpts, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) BurnFrom(opts *bind.TransactOpts, account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "burnFrom", account, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplySession) BurnFrom(account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.BurnFrom(&_ERC20FixedSupply.TransactOpts, account, amount)
}

// BurnFrom is a paid mutator transaction binding the contract method 0x79cc6790.
//
// Solidity: function burnFrom(address account, uint256 amount) returns()
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) BurnFrom(account common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.BurnFrom(&_ERC20FixedSupply.TransactOpts, account, amount)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) DecreaseAllowance(opts *bind.TransactOpts, spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "decreaseAllowance", spender, subtractedValue)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplySession) DecreaseAllowance(spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.DecreaseAllowance(&_ERC20FixedSupply.TransactOpts, spender, subtractedValue)
}

// DecreaseAllowance is a paid mutator transaction binding the contract method 0xa457c2d7.
//
// Solidity: function decreaseAllowance(address spender, uint256 subtractedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) DecreaseAllowance(spender common.Address, subtractedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.DecreaseAllowance(&_ERC20FixedSupply.TransactOpts, spender, subtractedValue)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) IncreaseAllowance(opts *bind.TransactOpts, spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "increaseAllowance", spender, addedValue)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplySession) IncreaseAllowance(spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.IncreaseAllowance(&_ERC20FixedSupply.TransactOpts, spender, addedValue)
}

// IncreaseAllowance is a paid mutator transaction binding the contract method 0x39509351.
//
// Solidity: function increaseAllowance(address spender, uint256 addedValue) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) IncreaseAllowance(spender common.Address, addedValue *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.IncreaseAllowance(&_ERC20FixedSupply.TransactOpts, spender, addedValue)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) Transfer(opts *bind.TransactOpts, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "transfer", recipient, amount)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplySession) Transfer(recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Transfer(&_ERC20FixedSupply.TransactOpts, recipient, amount)
}

// Transfer is a paid mutator transaction binding the contract method 0xa9059cbb.
//
// Solidity: function transfer(address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) Transfer(recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.Transfer(&_ERC20FixedSupply.TransactOpts, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactor) TransferFrom(opts *bind.TransactOpts, sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.contract.Transact(opts, "transferFrom", sender, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplySession) TransferFrom(sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.TransferFrom(&_ERC20FixedSupply.TransactOpts, sender, recipient, amount)
}

// TransferFrom is a paid mutator transaction binding the contract method 0x23b872dd.
//
// Solidity: function transferFrom(address sender, address recipient, uint256 amount) returns(bool)
func (_ERC20FixedSupply *ERC20FixedSupplyTransactorSession) TransferFrom(sender common.Address, recipient common.Address, amount *big.Int) (*types.Transaction, error) {
	return _ERC20FixedSupply.Contract.TransferFrom(&_ERC20FixedSupply.TransactOpts, sender, recipient, amount)
}

// ERC20FixedSupplyApprovalIterator is returned from FilterApproval and is used to iterate over the raw logs and unpacked data for Approval events raised by the ERC20FixedSupply contract.
type ERC20FixedSupplyApprovalIterator struct {
	Event *ERC20FixedSupplyApproval // Event containing the contract specifics and raw log

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
func (it *ERC20FixedSupplyApprovalIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20FixedSupplyApproval)
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
		it.Event = new(ERC20FixedSupplyApproval)
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
func (it *ERC20FixedSupplyApprovalIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20FixedSupplyApprovalIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20FixedSupplyApproval represents a Approval event raised by the ERC20FixedSupply contract.
type ERC20FixedSupplyApproval struct {
	Owner   common.Address
	Spender common.Address
	Value   *big.Int
	Raw     types.Log // Blockchain specific contextual infos
}

// FilterApproval is a free log retrieval operation binding the contract event 0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925.
//
// Solidity: event Approval(address indexed owner, address indexed spender, uint256 value)
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) FilterApproval(opts *bind.FilterOpts, owner []common.Address, spender []common.Address) (*ERC20FixedSupplyApprovalIterator, error) {

	var ownerRule []interface{}
	for _, ownerItem := range owner {
		ownerRule = append(ownerRule, ownerItem)
	}
	var spenderRule []interface{}
	for _, spenderItem := range spender {
		spenderRule = append(spenderRule, spenderItem)
	}

	logs, sub, err := _ERC20FixedSupply.contract.FilterLogs(opts, "Approval", ownerRule, spenderRule)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupplyApprovalIterator{contract: _ERC20FixedSupply.contract, event: "Approval", logs: logs, sub: sub}, nil
}

// WatchApproval is a free log subscription operation binding the contract event 0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925.
//
// Solidity: event Approval(address indexed owner, address indexed spender, uint256 value)
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) WatchApproval(opts *bind.WatchOpts, sink chan<- *ERC20FixedSupplyApproval, owner []common.Address, spender []common.Address) (event.Subscription, error) {

	var ownerRule []interface{}
	for _, ownerItem := range owner {
		ownerRule = append(ownerRule, ownerItem)
	}
	var spenderRule []interface{}
	for _, spenderItem := range spender {
		spenderRule = append(spenderRule, spenderItem)
	}

	logs, sub, err := _ERC20FixedSupply.contract.WatchLogs(opts, "Approval", ownerRule, spenderRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20FixedSupplyApproval)
				if err := _ERC20FixedSupply.contract.UnpackLog(event, "Approval", log); err != nil {
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
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) ParseApproval(log types.Log) (*ERC20FixedSupplyApproval, error) {
	event := new(ERC20FixedSupplyApproval)
	if err := _ERC20FixedSupply.contract.UnpackLog(event, "Approval", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// ERC20FixedSupplyTransferIterator is returned from FilterTransfer and is used to iterate over the raw logs and unpacked data for Transfer events raised by the ERC20FixedSupply contract.
type ERC20FixedSupplyTransferIterator struct {
	Event *ERC20FixedSupplyTransfer // Event containing the contract specifics and raw log

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
func (it *ERC20FixedSupplyTransferIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(ERC20FixedSupplyTransfer)
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
		it.Event = new(ERC20FixedSupplyTransfer)
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
func (it *ERC20FixedSupplyTransferIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *ERC20FixedSupplyTransferIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// ERC20FixedSupplyTransfer represents a Transfer event raised by the ERC20FixedSupply contract.
type ERC20FixedSupplyTransfer struct {
	From  common.Address
	To    common.Address
	Value *big.Int
	Raw   types.Log // Blockchain specific contextual infos
}

// FilterTransfer is a free log retrieval operation binding the contract event 0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef.
//
// Solidity: event Transfer(address indexed from, address indexed to, uint256 value)
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) FilterTransfer(opts *bind.FilterOpts, from []common.Address, to []common.Address) (*ERC20FixedSupplyTransferIterator, error) {

	var fromRule []interface{}
	for _, fromItem := range from {
		fromRule = append(fromRule, fromItem)
	}
	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}

	logs, sub, err := _ERC20FixedSupply.contract.FilterLogs(opts, "Transfer", fromRule, toRule)
	if err != nil {
		return nil, err
	}
	return &ERC20FixedSupplyTransferIterator{contract: _ERC20FixedSupply.contract, event: "Transfer", logs: logs, sub: sub}, nil
}

// WatchTransfer is a free log subscription operation binding the contract event 0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef.
//
// Solidity: event Transfer(address indexed from, address indexed to, uint256 value)
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) WatchTransfer(opts *bind.WatchOpts, sink chan<- *ERC20FixedSupplyTransfer, from []common.Address, to []common.Address) (event.Subscription, error) {

	var fromRule []interface{}
	for _, fromItem := range from {
		fromRule = append(fromRule, fromItem)
	}
	var toRule []interface{}
	for _, toItem := range to {
		toRule = append(toRule, toItem)
	}

	logs, sub, err := _ERC20FixedSupply.contract.WatchLogs(opts, "Transfer", fromRule, toRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(ERC20FixedSupplyTransfer)
				if err := _ERC20FixedSupply.contract.UnpackLog(event, "Transfer", log); err != nil {
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
func (_ERC20FixedSupply *ERC20FixedSupplyFilterer) ParseTransfer(log types.Log) (*ERC20FixedSupplyTransfer, error) {
	event := new(ERC20FixedSupplyTransfer)
	if err := _ERC20FixedSupply.contract.UnpackLog(event, "Transfer", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
