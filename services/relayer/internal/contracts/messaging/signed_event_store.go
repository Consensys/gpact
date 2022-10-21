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

// SignedEventStoreMetaData contains all meta data concerning the SignedEventStore contract.
var SignedEventStoreMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_functionCall\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[],\"name\":\"ECDSA_SIGNATURE\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"getSignerList\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"\",\"type\":\"address[]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sourceBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_sourceCbcAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_eventData\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"}],\"name\":\"relayEvent\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"supportedSigningAlgorithm\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50604051610c39380380610c3983398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610b7b806100be6000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80631cd8253a1461005c5780634c1ce902146100715780637d1f180114610099578063d068a7c2146100bb578063d453f0ba146100c3575b600080fd5b61006f61006a3660046105f7565b6100f1565b005b61008461007f366004610681565b610404565b60405190151581526020015b60405180910390f35b6100ad6100a73660046106c0565b50600190565b604051908152602001610090565b6100ad600181565b6100e46100d13660046106c0565b5060408051600081526020810190915290565b60405161009091906106d9565b600086867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27878760405160200161012c959493929190610726565b60408051601f19818403018152908290526000546326a2ec3160e21b83529092506001600160a01b031690639a8bb0c490610171908a90879087908790600401610789565b60206040518083038186803b15801561018957600080fd5b505afa15801561019d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101c191906107f4565b508051602082012060006101d5858561050c565b905060005b8160200151518110156102b2576000826020015182815181106101ff576101ff61081d565b6020908102919091018101515160008681526002835260408082206001600160a01b0384168352600301909352919091205490915060ff161561024257506102a0565b60008481526002602081815260408084206001600160a01b03909516808552600386018352908420805460ff191660019081179091558383529490920180549485018155835290912090910180546001600160a01b03191690911790555b806102aa81610833565b9150506101da565b5060008281526002602052604090206001015460ff16156102d5575050506103fc565b6000805460405163a64ce19960e01b8152600481018c90526001600160a01b039091169063a64ce1999060240160206040518083038186803b15801561031a57600080fd5b505afa15801561032e573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610352919061085c565b6000848152600260208190526040909120015490915081116103f757600083815260026020526040908190206001908101805460ff191682179055549051632044202960e11b81526001600160a01b03909116906340884052906103c4908d908d908d908d908d908d90600401610875565b600060405180830381600087803b1580156103de57600080fd5b505af11580156103f2573d6000803e3d6000fd5b505050505b505050505b505050505050565b60008085856040516104179291906108c0565b6040519081900381206000805463a64ce19960e01b8452600484018c9052919350916001600160a01b039091169063a64ce1999060240160206040518083038186803b15801561046657600080fd5b505afa15801561047a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061049e919061085c565b600083815260026020819052604090912001549091508111156104fd5760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b60448201526064015b60405180910390fd5b50600198975050505050505050565b604080518082019091526000815260606020820152600061052f838501856108e6565b9150506001811461057b5760405162461bcd60e51b81526020600482015260166024820152755369676e617475726520756e6b6e6f776e207479706560501b60448201526064016104f4565b600061058984860186610985565b95945050505050565b80356001600160a01b03811681146105a957600080fd5b919050565b60008083601f8401126105c057600080fd5b50813567ffffffffffffffff8111156105d857600080fd5b6020830191508360208285010111156105f057600080fd5b9250929050565b6000806000806000806080878903121561061057600080fd5b8635955061062060208801610592565b9450604087013567ffffffffffffffff8082111561063d57600080fd5b6106498a838b016105ae565b9096509450606089013591508082111561066257600080fd5b5061066f89828a016105ae565b979a9699509497509295939492505050565b6000806000806000806080878903121561069a57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561063d57600080fd5b6000602082840312156106d257600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b8181101561071a5783516001600160a01b0316835292840192918401916001016106f5565b50909695505050505050565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b848152600060206060818401526107a4606084018688610760565b8381036040850152845180825260005b818110156107cf5786810184015183820185015283016107b4565b818111156107e05760008483850101525b50601f01601f191601019695505050505050565b60006020828403121561080657600080fd5b8151801515811461081657600080fd5b9392505050565b634e487b7160e01b600052603260045260246000fd5b600060001982141561085557634e487b7160e01b600052601160045260246000fd5b5060010190565b60006020828403121561086e57600080fd5b5051919050565b8681526001600160a01b03861660208201526080604082018190526000906108a09083018688610760565b82810360608401526108b3818587610760565b9998505050505050505050565b8183823760009101908152919050565b634e487b7160e01b600052604160045260246000fd5b600080604083850312156108f957600080fd5b50508035926020909101359150565b6040805190810167ffffffffffffffff8111828210171561092b5761092b6108d0565b60405290565b60405160a0810167ffffffffffffffff8111828210171561092b5761092b6108d0565b604051601f8201601f1916810167ffffffffffffffff8111828210171561097d5761097d6108d0565b604052919050565b6000602080838503121561099857600080fd5b823567ffffffffffffffff808211156109b057600080fd5b90840190604082870312156109c457600080fd5b6109cc610908565b8235815283830135828111156109e157600080fd5b80840193505086601f8401126109f657600080fd5b823582811115610a0857610a086108d0565b8060051b610a17868201610954565b918252848101860191868101908a841115610a3157600080fd5b87870192505b83831015610b3157823586811115610a4e57600080fd5b8701601f1960a0828e0382011215610a6557600080fd5b610a6d610931565b610a788b8401610592565b815260408301358b82015260608301356040820152608083013560ff81168114610aa157600080fd5b606082015260a083013589811115610ab857600080fd5b8084019350508d603f840112610acd57600080fd5b8a83013589811115610ae157610ae16108d0565b610af18c84601f84011601610954565b92508083528e6040828601011115610b0857600080fd5b80604085018d85013760009083018c015260808101919091528352509187019190870190610a37565b96840196909652509097965050505050505056fea264697066735822122033075b32f04bf9d84724a99924aeae0dc9448474261be342cf3b179e02c37e8664736f6c63430008090033",
}

// SignedEventStoreABI is the input ABI used to generate the binding from.
// Deprecated: Use SignedEventStoreMetaData.ABI instead.
var SignedEventStoreABI = SignedEventStoreMetaData.ABI

// SignedEventStoreBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use SignedEventStoreMetaData.Bin instead.
var SignedEventStoreBin = SignedEventStoreMetaData.Bin

// DeploySignedEventStore deploys a new Ethereum contract, binding an instance of SignedEventStore to it.
func DeploySignedEventStore(auth *bind.TransactOpts, backend bind.ContractBackend, _registrar common.Address, _functionCall common.Address) (common.Address, *types.Transaction, *SignedEventStore, error) {
	parsed, err := SignedEventStoreMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(SignedEventStoreBin), backend, _registrar, _functionCall)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &SignedEventStore{SignedEventStoreCaller: SignedEventStoreCaller{contract: contract}, SignedEventStoreTransactor: SignedEventStoreTransactor{contract: contract}, SignedEventStoreFilterer: SignedEventStoreFilterer{contract: contract}}, nil
}

// SignedEventStore is an auto generated Go binding around an Ethereum contract.
type SignedEventStore struct {
	SignedEventStoreCaller     // Read-only binding to the contract
	SignedEventStoreTransactor // Write-only binding to the contract
	SignedEventStoreFilterer   // Log filterer for contract events
}

// SignedEventStoreCaller is an auto generated read-only Go binding around an Ethereum contract.
type SignedEventStoreCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SignedEventStoreTransactor is an auto generated write-only Go binding around an Ethereum contract.
type SignedEventStoreTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SignedEventStoreFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type SignedEventStoreFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SignedEventStoreSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type SignedEventStoreSession struct {
	Contract     *SignedEventStore // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// SignedEventStoreCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type SignedEventStoreCallerSession struct {
	Contract *SignedEventStoreCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts           // Call options to use throughout this session
}

// SignedEventStoreTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type SignedEventStoreTransactorSession struct {
	Contract     *SignedEventStoreTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts           // Transaction auth options to use throughout this session
}

// SignedEventStoreRaw is an auto generated low-level Go binding around an Ethereum contract.
type SignedEventStoreRaw struct {
	Contract *SignedEventStore // Generic contract binding to access the raw methods on
}

// SignedEventStoreCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type SignedEventStoreCallerRaw struct {
	Contract *SignedEventStoreCaller // Generic read-only contract binding to access the raw methods on
}

// SignedEventStoreTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type SignedEventStoreTransactorRaw struct {
	Contract *SignedEventStoreTransactor // Generic write-only contract binding to access the raw methods on
}

// NewSignedEventStore creates a new instance of SignedEventStore, bound to a specific deployed contract.
func NewSignedEventStore(address common.Address, backend bind.ContractBackend) (*SignedEventStore, error) {
	contract, err := bindSignedEventStore(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &SignedEventStore{SignedEventStoreCaller: SignedEventStoreCaller{contract: contract}, SignedEventStoreTransactor: SignedEventStoreTransactor{contract: contract}, SignedEventStoreFilterer: SignedEventStoreFilterer{contract: contract}}, nil
}

// NewSignedEventStoreCaller creates a new read-only instance of SignedEventStore, bound to a specific deployed contract.
func NewSignedEventStoreCaller(address common.Address, caller bind.ContractCaller) (*SignedEventStoreCaller, error) {
	contract, err := bindSignedEventStore(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &SignedEventStoreCaller{contract: contract}, nil
}

// NewSignedEventStoreTransactor creates a new write-only instance of SignedEventStore, bound to a specific deployed contract.
func NewSignedEventStoreTransactor(address common.Address, transactor bind.ContractTransactor) (*SignedEventStoreTransactor, error) {
	contract, err := bindSignedEventStore(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &SignedEventStoreTransactor{contract: contract}, nil
}

// NewSignedEventStoreFilterer creates a new log filterer instance of SignedEventStore, bound to a specific deployed contract.
func NewSignedEventStoreFilterer(address common.Address, filterer bind.ContractFilterer) (*SignedEventStoreFilterer, error) {
	contract, err := bindSignedEventStore(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &SignedEventStoreFilterer{contract: contract}, nil
}

// bindSignedEventStore binds a generic wrapper to an already deployed contract.
func bindSignedEventStore(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(SignedEventStoreABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_SignedEventStore *SignedEventStoreRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _SignedEventStore.Contract.SignedEventStoreCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_SignedEventStore *SignedEventStoreRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SignedEventStore.Contract.SignedEventStoreTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_SignedEventStore *SignedEventStoreRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _SignedEventStore.Contract.SignedEventStoreTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_SignedEventStore *SignedEventStoreCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _SignedEventStore.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_SignedEventStore *SignedEventStoreTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _SignedEventStore.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_SignedEventStore *SignedEventStoreTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _SignedEventStore.Contract.contract.Transact(opts, method, params...)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_SignedEventStore *SignedEventStoreCaller) ECDSASIGNATURE(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _SignedEventStore.contract.Call(opts, &out, "ECDSA_SIGNATURE")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_SignedEventStore *SignedEventStoreSession) ECDSASIGNATURE() (*big.Int, error) {
	return _SignedEventStore.Contract.ECDSASIGNATURE(&_SignedEventStore.CallOpts)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_SignedEventStore *SignedEventStoreCallerSession) ECDSASIGNATURE() (*big.Int, error) {
	return _SignedEventStore.Contract.ECDSASIGNATURE(&_SignedEventStore.CallOpts)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns(bool)
func (_SignedEventStore *SignedEventStoreCaller) DecodeAndVerifyEvent(opts *bind.CallOpts, _blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) (bool, error) {
	var out []interface{}
	err := _SignedEventStore.contract.Call(opts, &out, "decodeAndVerifyEvent", _blockchainId, arg1, _encodedEvent, arg3)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns(bool)
func (_SignedEventStore *SignedEventStoreSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) (bool, error) {
	return _SignedEventStore.Contract.DecodeAndVerifyEvent(&_SignedEventStore.CallOpts, _blockchainId, arg1, _encodedEvent, arg3)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns(bool)
func (_SignedEventStore *SignedEventStoreCallerSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) (bool, error) {
	return _SignedEventStore.Contract.DecodeAndVerifyEvent(&_SignedEventStore.CallOpts, _blockchainId, arg1, _encodedEvent, arg3)
}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_SignedEventStore *SignedEventStoreCaller) GetSignerList(opts *bind.CallOpts, arg0 *big.Int) ([]common.Address, error) {
	var out []interface{}
	err := _SignedEventStore.contract.Call(opts, &out, "getSignerList", arg0)

	if err != nil {
		return *new([]common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new([]common.Address)).(*[]common.Address)

	return out0, err

}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_SignedEventStore *SignedEventStoreSession) GetSignerList(arg0 *big.Int) ([]common.Address, error) {
	return _SignedEventStore.Contract.GetSignerList(&_SignedEventStore.CallOpts, arg0)
}

// GetSignerList is a free data retrieval call binding the contract method 0xd453f0ba.
//
// Solidity: function getSignerList(uint256 ) pure returns(address[])
func (_SignedEventStore *SignedEventStoreCallerSession) GetSignerList(arg0 *big.Int) ([]common.Address, error) {
	return _SignedEventStore.Contract.GetSignerList(&_SignedEventStore.CallOpts, arg0)
}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_SignedEventStore *SignedEventStoreCaller) SupportedSigningAlgorithm(opts *bind.CallOpts, arg0 *big.Int) (*big.Int, error) {
	var out []interface{}
	err := _SignedEventStore.contract.Call(opts, &out, "supportedSigningAlgorithm", arg0)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_SignedEventStore *SignedEventStoreSession) SupportedSigningAlgorithm(arg0 *big.Int) (*big.Int, error) {
	return _SignedEventStore.Contract.SupportedSigningAlgorithm(&_SignedEventStore.CallOpts, arg0)
}

// SupportedSigningAlgorithm is a free data retrieval call binding the contract method 0x7d1f1801.
//
// Solidity: function supportedSigningAlgorithm(uint256 ) pure returns(uint256)
func (_SignedEventStore *SignedEventStoreCallerSession) SupportedSigningAlgorithm(arg0 *big.Int) (*big.Int, error) {
	return _SignedEventStore.Contract.SupportedSigningAlgorithm(&_SignedEventStore.CallOpts, arg0)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signatures) returns()
func (_SignedEventStore *SignedEventStoreTransactor) RelayEvent(opts *bind.TransactOpts, _sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signatures []byte) (*types.Transaction, error) {
	return _SignedEventStore.contract.Transact(opts, "relayEvent", _sourceBlockchainId, _sourceCbcAddress, _eventData, _signatures)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signatures) returns()
func (_SignedEventStore *SignedEventStoreSession) RelayEvent(_sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signatures []byte) (*types.Transaction, error) {
	return _SignedEventStore.Contract.RelayEvent(&_SignedEventStore.TransactOpts, _sourceBlockchainId, _sourceCbcAddress, _eventData, _signatures)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signatures) returns()
func (_SignedEventStore *SignedEventStoreTransactorSession) RelayEvent(_sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signatures []byte) (*types.Transaction, error) {
	return _SignedEventStore.Contract.RelayEvent(&_SignedEventStore.TransactOpts, _sourceBlockchainId, _sourceCbcAddress, _eventData, _signatures)
}
