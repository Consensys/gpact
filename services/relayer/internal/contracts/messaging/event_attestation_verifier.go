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
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signature\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b5060405161098c38038061098c83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6108f9806100936000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80634c1ce90214610030575b600080fd5b61004361003e366004610641565b610045565b005b606080606080600061008c87878080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525092506104bf915050565b905061009f605563ffffffff83166106da565b6100aa9060046106f9565b86146100fd5760405162461bcd60e51b815260206004820152601a60248201527f5369676e617475726520696e636f7272656374206c656e67746800000000000060448201526064015b60405180910390fd5b8063ffffffff1667ffffffffffffffff81111561011c5761011c610711565b604051908082528060200260200182016040528015610145578160200160208202803683370190505b5094508063ffffffff1667ffffffffffffffff81111561016757610167610711565b604051908082528060200260200182016040528015610190578160200160208202803683370190505b5093508063ffffffff1667ffffffffffffffff8111156101b2576101b2610711565b6040519080825280602002602001820160405280156101db578160200160208202803683370190505b5092508063ffffffff1667ffffffffffffffff8111156101fd576101fd610711565b604051908082528060200260200182016040528015610226578160200160208202803683370190505b509150600460005b8263ffffffff168110156104145761027d89898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610525915050565b87828151811061028f5761028f610727565b6001600160a01b03909216602092830291909101909101526102b26014836106f9565b91506102f589898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061052d915050565b86828151811061030757610307610727565b60200260200101818152505060208261032091906106f9565b915061036389898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061052d915050565b85828151811061037557610375610727565b60200260200101818152505060208261038e91906106f9565b91506103d189898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610592915050565b8482815181106103e3576103e3610727565b60ff909216602092830291909101909101526104006001836106f9565b91508061040c8161073d565b91505061022e565b50505060008054906101000a90046001600160a01b03166001600160a01b031663ad107bb48b868686868e8e6040518863ffffffff1660e01b815260040161046297969594939291906107ef565b60206040518083038186803b15801561047a57600080fd5b505afa15801561048e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104b2919061089a565b5050505050505050505050565b60006104cc8260046106f9565b8351101561051c5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e7433322900000060448201526064016100f4565b50016004015190565b016014015190565b60008060005b602081101561058a576105478160086106da565b8561055283876106f9565b8151811061056257610562610727565b01602001516001600160f81b031916901c9190911790806105828161073d565b915050610533565b509392505050565b600061059f8260016106f9565b835110156105ef5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e7438290000000060448201526064016100f4565b50016001015190565b60008083601f84011261060a57600080fd5b50813567ffffffffffffffff81111561062257600080fd5b60208301915083602082850101111561063a57600080fd5b9250929050565b6000806000806000806080878903121561065a57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561068057600080fd5b61068c8a838b016105f8565b909650945060608901359150808211156106a557600080fd5b506106b289828a016105f8565b979a9699509497509295939492505050565b634e487b7160e01b600052601160045260246000fd5b60008160001904831182151516156106f4576106f46106c4565b500290565b6000821982111561070c5761070c6106c4565b500190565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b6000600019821415610751576107516106c4565b5060010190565b600081518084526020808501945080840160005b838110156107885781518752958201959082019060010161076c565b509495945050505050565b600081518084526020808501945080840160005b8381101561078857815160ff16875295820195908201906001016107a7565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b600060c08201898352602060c081850152818a5180845260e086019150828c01935060005b818110156108395784516001600160a01b031683529383019391830191600101610814565b5050848103604086015261084d818b610758565b9250505082810360608401526108638188610758565b905082810360808401526108778187610793565b905082810360a084015261088c8185876107c6565b9a9950505050505050505050565b6000602082840312156108ac57600080fd5b815180151581146108bc57600080fd5b939250505056fea2646970667358221220150b6e1058812e3160fe3da7856839e5442f303ae53ec4637780cfbedf8432c064736f6c63430008090033",
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
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signature) view returns()
func (_EventAttestationVerifier *EventAttestationVerifierCaller) DecodeAndVerifyEvent(opts *bind.CallOpts, _blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signature []byte) error {
	var out []interface{}
	err := _EventAttestationVerifier.contract.Call(opts, &out, "decodeAndVerifyEvent", _blockchainId, arg1, _encodedEvent, _signature)

	if err != nil {
		return err
	}

	return err

}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signature) view returns()
func (_EventAttestationVerifier *EventAttestationVerifierSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signature []byte) error {
	return _EventAttestationVerifier.Contract.DecodeAndVerifyEvent(&_EventAttestationVerifier.CallOpts, _blockchainId, arg1, _encodedEvent, _signature)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes _signature) view returns()
func (_EventAttestationVerifier *EventAttestationVerifierCallerSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, _signature []byte) error {
	return _EventAttestationVerifier.Contract.DecodeAndVerifyEvent(&_EventAttestationVerifier.CallOpts, _blockchainId, arg1, _encodedEvent, _signature)
}
