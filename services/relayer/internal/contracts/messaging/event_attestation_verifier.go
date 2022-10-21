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
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b506040516102c43803806102c483398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610231806100936000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80634c1ce90214610030575b600080fd5b61004361003e366004610116565b610057565b604051901515815260200160405180910390f35b6000805460405163012411a560e61b81526001600160a01b0390911690634904694090610090908a90879087908b908b906004016101c2565b60006040518083038186803b1580156100a857600080fd5b505afa1580156100bc573d6000803e3d6000fd5b5060019a9950505050505050505050565b60008083601f8401126100df57600080fd5b50813567ffffffffffffffff8111156100f757600080fd5b60208301915083602082850101111561010f57600080fd5b9250929050565b6000806000806000806080878903121561012f57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561015557600080fd5b6101618a838b016100cd565b9096509450606089013591508082111561017a57600080fd5b5061018789828a016100cd565b979a9699509497509295939492505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b8581526060602082015260006101dc606083018688610199565b82810360408401526101ef818587610199565b9897505050505050505056fea2646970667358221220ffa8f76da7936ea414701c89f685c796ab703ff60e1ad48bc699aebde98c0d9f64736f6c63430008090033",
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
