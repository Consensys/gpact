// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package messaging

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

// EventAttestationVerifierMetaData contains all meta data concerning the EventAttestationVerifier contract.
var EventAttestationVerifierMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[],\"name\":\"ECDSA_SIGNATURE\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"getSignerList\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"\",\"type\":\"address[]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"supportedSigningAlgorithm\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b506040516103a43803806103a483398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610311806100936000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80634c1ce902146100515780637d1f180114610079578063d068a7c21461009b578063d453f0ba146100a3575b600080fd5b61006461005f366004610190565b6100d1565b60405190151581526020015b60405180910390f35b61008d610087366004610213565b50600190565b604051908152602001610070565b61008d600181565b6100c46100b1366004610213565b5060408051600081526020810190915290565b604051610070919061022c565b6000805460405163012411a560e61b81526001600160a01b039091169063490469409061010a908a90879087908b908b906004016102a2565b60006040518083038186803b15801561012257600080fd5b505afa158015610136573d6000803e3d6000fd5b5060019a9950505050505050505050565b60008083601f84011261015957600080fd5b50813567ffffffffffffffff81111561017157600080fd5b60208301915083602082850101111561018957600080fd5b9250929050565b600080600080600080608087890312156101a957600080fd5b8635955060208701359450604087013567ffffffffffffffff808211156101cf57600080fd5b6101db8a838b01610147565b909650945060608901359150808211156101f457600080fd5b5061020189828a01610147565b979a9699509497509295939492505050565b60006020828403121561022557600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b8181101561026d5783516001600160a01b031683529284019291840191600101610248565b50909695505050505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b8581526060602082015260006102bc606083018688610279565b82810360408401526102cf818587610279565b9897505050505050505056fea26469706673582212200c770279a97765f75bb39111e28189021e0b07c57d280e24cd8eb61a150480a364736f6c63430008090033",
}

// EventAttestationVerifierABI is the input ABI used to generate the binding from.
// Deprecated: Use EventAttestationVerifierMetaData.ABI instead.
var EventAttestationVerifierABI = EventAttestationVerifierMetaData.ABI

// EventAttestationVerifierBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use EventAttestationVerifierMetaData.Bin instead.
var EventAttestationVerifierBin = EventAttestationVerifierMetaData.Bin

// DeployEventAttestationVerifier deploys a new Ethereum contract, binding an instance of EventAttestationVerifier to it.
func DeployEventAttestationVerifier(auth *bind.TransactOpts, backend bind.ContractBackend, _registrar common.Address) (common.Address, *types.Transaction, *EventAttestationVerifier, error) {
	parsed, err := EventAttestationVerifierMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(EventAttestationVerifierBin), backend, _registrar)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &EventAttestationVerifier{EventAttestationVerifierCaller: EventAttestationVerifierCaller{contract: contract}, EventAttestationVerifierTransactor: EventAttestationVerifierTransactor{contract: contract}, EventAttestationVerifierFilterer: EventAttestationVerifierFilterer{contract: contract}}, nil
}

// EventAttestationVerifier is an auto generated Go binding around an Ethereum contract.
type EventAttestationVerifier struct {
	EventAttestationVerifierCaller     // Read-only binding to the contract
	EventAttestationVerifierTransactor // Write-only binding to the contract
	EventAttestationVerifierFilterer   // Log filterer for contract events
}

// EventAttestationVerifierCaller is an auto generated read-only Go binding around an Ethereum contract.
type EventAttestationVerifierCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// EventAttestationVerifierTransactor is an auto generated write-only Go binding around an Ethereum contract.
type EventAttestationVerifierTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// EventAttestationVerifierFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type EventAttestationVerifierFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// EventAttestationVerifierSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type EventAttestationVerifierSession struct {
	Contract     *EventAttestationVerifier // Generic contract binding to set the session for
	CallOpts     bind.CallOpts             // Call options to use throughout this session
	TransactOpts bind.TransactOpts         // Transaction auth options to use throughout this session
}

// EventAttestationVerifierCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type EventAttestationVerifierCallerSession struct {
	Contract *EventAttestationVerifierCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts                   // Call options to use throughout this session
}

// EventAttestationVerifierTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type EventAttestationVerifierTransactorSession struct {
	Contract     *EventAttestationVerifierTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts                   // Transaction auth options to use throughout this session
}

// EventAttestationVerifierRaw is an auto generated low-level Go binding around an Ethereum contract.
type EventAttestationVerifierRaw struct {
	Contract *EventAttestationVerifier // Generic contract binding to access the raw methods on
}

// EventAttestationVerifierCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type EventAttestationVerifierCallerRaw struct {
	Contract *EventAttestationVerifierCaller // Generic read-only contract binding to access the raw methods on
}

// EventAttestationVerifierTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type EventAttestationVerifierTransactorRaw struct {
	Contract *EventAttestationVerifierTransactor // Generic write-only contract binding to access the raw methods on
}

// NewEventAttestationVerifier creates a new instance of EventAttestationVerifier, bound to a specific deployed contract.
func NewEventAttestationVerifier(address common.Address, backend bind.ContractBackend) (*EventAttestationVerifier, error) {
	contract, err := bindEventAttestationVerifier(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &EventAttestationVerifier{EventAttestationVerifierCaller: EventAttestationVerifierCaller{contract: contract}, EventAttestationVerifierTransactor: EventAttestationVerifierTransactor{contract: contract}, EventAttestationVerifierFilterer: EventAttestationVerifierFilterer{contract: contract}}, nil
}

// NewEventAttestationVerifierCaller creates a new read-only instance of EventAttestationVerifier, bound to a specific deployed contract.
func NewEventAttestationVerifierCaller(address common.Address, caller bind.ContractCaller) (*EventAttestationVerifierCaller, error) {
	contract, err := bindEventAttestationVerifier(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &EventAttestationVerifierCaller{contract: contract}, nil
}

// NewEventAttestationVerifierTransactor creates a new write-only instance of EventAttestationVerifier, bound to a specific deployed contract.
func NewEventAttestationVerifierTransactor(address common.Address, transactor bind.ContractTransactor) (*EventAttestationVerifierTransactor, error) {
	contract, err := bindEventAttestationVerifier(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &EventAttestationVerifierTransactor{contract: contract}, nil
}

// NewEventAttestationVerifierFilterer creates a new log filterer instance of EventAttestationVerifier, bound to a specific deployed contract.
func NewEventAttestationVerifierFilterer(address common.Address, filterer bind.ContractFilterer) (*EventAttestationVerifierFilterer, error) {
	contract, err := bindEventAttestationVerifier(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &EventAttestationVerifierFilterer{contract: contract}, nil
}

// bindEventAttestationVerifier binds a generic wrapper to an already deployed contract.
func bindEventAttestationVerifier(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(EventAttestationVerifierABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_EventAttestationVerifier *EventAttestationVerifierRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _EventAttestationVerifier.Contract.EventAttestationVerifierCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_EventAttestationVerifier *EventAttestationVerifierRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _EventAttestationVerifier.Contract.EventAttestationVerifierTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_EventAttestationVerifier *EventAttestationVerifierRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _EventAttestationVerifier.Contract.EventAttestationVerifierTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_EventAttestationVerifier *EventAttestationVerifierCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _EventAttestationVerifier.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_EventAttestationVerifier *EventAttestationVerifierTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _EventAttestationVerifier.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_EventAttestationVerifier *EventAttestationVerifierTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _EventAttestationVerifier.Contract.contract.Transact(opts, method, params...)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierCaller) ECDSASIGNATURE(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _EventAttestationVerifier.contract.Call(opts, &out, "ECDSA_SIGNATURE")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierSession) ECDSASIGNATURE() (*big.Int, error) {
	return _EventAttestationVerifier.Contract.ECDSASIGNATURE(&_EventAttestationVerifier.CallOpts)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierCallerSession) ECDSASIGNATURE() (*big.Int, error) {
	return _EventAttestationVerifier.Contract.ECDSASIGNATURE(&_EventAttestationVerifier.CallOpts)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signatures) view returns(bool)
func (_EventAttestationVerifier *EventAttestationVerifierCaller) DecodeAndVerifyEvent(opts *bind.CallOpts, _blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signatures []byte) (bool, error) {
	var out []interface{}
	err := _EventAttestationVerifier.contract.Call(opts, &out, "decodeAndVerifyEvent", _blockchainId, arg1, _encodedEvent, _signatures)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signatures) view returns(bool)
func (_EventAttestationVerifier *EventAttestationVerifierSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signatures []byte) (bool, error) {
	return _EventAttestationVerifier.Contract.DecodeAndVerifyEvent(&_EventAttestationVerifier.CallOpts, _blockchainId, arg1, _encodedEvent, _signatures)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signatures) view returns(bool)
func (_EventAttestationVerifier *EventAttestationVerifierCallerSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signatures []byte) (bool, error) {
	return _EventAttestationVerifier.Contract.DecodeAndVerifyEvent(&_EventAttestationVerifier.CallOpts, _blockchainId, arg1, _encodedEvent, _signatures)
}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_EventAttestationVerifier *EventAttestationVerifierCaller) GetSignerList(opts *bind.CallOpts, arg0 *big.Int) ([]common.Address, error) {
	var out []interface{}
	err := _EventAttestationVerifier.contract.Call(opts, &out, "getSignerList", arg0)

	if err != nil {
		return *new([]common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new([]common.Address)).(*[]common.Address)

	return out0, err

}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_EventAttestationVerifier *EventAttestationVerifierSession) GetSignerList(arg0 *big.Int) ([]common.Address, error) {
	return _EventAttestationVerifier.Contract.GetSignerList(&_EventAttestationVerifier.CallOpts, arg0)
}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_EventAttestationVerifier *EventAttestationVerifierCallerSession) GetSignerList(arg0 *big.Int) ([]common.Address, error) {
	return _EventAttestationVerifier.Contract.GetSignerList(&_EventAttestationVerifier.CallOpts, arg0)
}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierCaller) SupportedSigningAlgorithm(opts *bind.CallOpts, arg0 *big.Int) (*big.Int, error) {
	var out []interface{}
	err := _EventAttestationVerifier.contract.Call(opts, &out, "supportedSigningAlgorithm", arg0)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierSession) SupportedSigningAlgorithm(arg0 *big.Int) (*big.Int, error) {
	return _EventAttestationVerifier.Contract.SupportedSigningAlgorithm(&_EventAttestationVerifier.CallOpts, arg0)
}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_EventAttestationVerifier *EventAttestationVerifierCallerSession) SupportedSigningAlgorithm(arg0 *big.Int) (*big.Int, error) {
	return _EventAttestationVerifier.Contract.SupportedSigningAlgorithm(&_EventAttestationVerifier.CallOpts, arg0)
}
