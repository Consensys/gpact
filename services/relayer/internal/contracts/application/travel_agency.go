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

// TravelAgencyMetaData contains all meta data concerning the TravelAgency contract.
var TravelAgencyMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_hotelBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_hotelContract\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_trainBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_trainContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_cbc\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_bookingId\",\"type\":\"uint256\"}],\"name\":\"bookHotelAndTrain\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bookingId\",\"type\":\"uint256\"}],\"name\":\"bookingConfirmed\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bool\",\"name\":\"_commit\",\"type\":\"bool\"},{\"internalType\":\"bytes32\",\"name\":\"_crossRootTxId\",\"type\":\"bytes32\"}],\"name\":\"finalise\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"hotelBlockchainId\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"hotelContract\",\"outputs\":[{\"internalType\":\"contractHotel\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_key\",\"type\":\"uint256\"}],\"name\":\"isLocked\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"trainBlockchainId\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"trainContract\",\"outputs\":[{\"internalType\":\"contractTrain\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b506040516109cb3803806109cb83398101604081905261002f916100a2565b600080546001600160a01b03199081166001600160a01b03938416179091556004805433908316179055600595909555600680548616948216949094179093556007919091556008805490931691161790556100f9565b80516001600160a01b038116811461009d57600080fd5b919050565b600080600080600060a086880312156100ba57600080fd5b855194506100ca60208701610086565b9350604086015192506100df60608701610086565b91506100ed60808701610086565b90509295509295909350565b6108c3806101086000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80638146b5c01161005b5780638146b5c01461010057806399eb5d4c146101155780639d0599ac14610128578063f6aacfb11461013b57600080fd5b8063036b0edf1461008d578063168fd46f146100b557806341b1637c146100cc5780634d1177da146100f7575b600080fd5b6100a061009b366004610726565b61015d565b60405190151581526020015b60405180910390f35b6100be60075481565b6040519081526020016100ac565b6008546100df906001600160a01b031681565b6040516001600160a01b0390911681526020016100ac565b6100be60055481565b61011361010e36600461073f565b610174565b005b610113610123366004610761565b6103b7565b6006546100df906001600160a01b031681565b6100a0610149366004610726565b600090815260026020526040902054151590565b60008061016b600084610467565b15159392505050565b6000546001600160a01b031633146101f05760405162461bcd60e51b815260206004820152603460248201527f53686f756c64206f6e6c792062652063616c6c65642062792043726f73736368604482015273185a5b8810dbdb9d1c9bdb0810dbdb9d1c9858dd60621b60648201526084015b60405180910390fd5b6004546001600160a01b0316321461024a5760405162461bcd60e51b815260206004820152601a60248201527f4f6e6c79206f776e65722063616e20646f20626f6f6b696e677300000000000060448201526064016101e7565b600054600554600654604080516024810187905260448101869052606480820181905282518083039091018152608490910182526020810180516001600160e01b03166309ac323760e11b17905290516392b2c33560e01b81526001600160a01b03948516946392b2c335946102c7949093911691600401610792565b600060405180830381600087803b1580156102e157600080fd5b505af11580156102f5573d6000803e3d6000fd5b5050600054600754600854604080516024810189905260448101889052606480820181905282518083039091018152608490910182526020810180516001600160e01b0316632465d6fd60e01b17905290516392b2c33560e01b81526001600160a01b0394851696506392b2c33595506103759490921691600401610792565b600060405180830381600087803b15801561038f57600080fd5b505af11580156103a3573d6000803e3d6000fd5b505050506103b3600082846104b2565b5050565b60005b60008281526003602052604090205481101561044f5760008281526003602052604081208054839081106103f0576103f06107fe565b90600052602060002001549050831561042e5760008181526002602052604090205461041e9060019061082a565b6000828152600160205260409020555b6000908152600260205260408120558061044781610841565b9150506103ba565b5060008181526003602052604081206103b3916106ec565b6000808383604051602001610486929190918252602082015260400190565b6040516020818303038152906040528051906020012090506104aa8160001c6104ea565b949350505050565b604080516020808201869052818301859052825180830384018152606090920190925280519101206104e48183610559565b50505050565b600081815260026020526040812054156105465760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101e7565b5060009081526001602052604090205490565b600082815260026020526040902054156105ac5760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016101e7565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b1580156105fb57600080fd5b505afa15801561060f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610633919061085c565b90508061064d575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b15801561069257600080fd5b505af11580156106a6573d6000803e3d6000fd5b5050506000828152600360209081526040822080546001818101835591845291909220018590556106d8915083610875565b600084815260026020526040902055505050565b508054600082559060005260206000209081019061070a919061070d565b50565b5b80821115610722576000815560010161070e565b5090565b60006020828403121561073857600080fd5b5035919050565b6000806040838503121561075257600080fd5b50508035926020909101359150565b6000806040838503121561077457600080fd5b8235801515811461078457600080fd5b946020939093013593505050565b8381526000602060018060a01b0385168184015260606040840152835180606085015260005b818110156107d4578581018301518582016080015282016107b8565b818111156107e6576000608083870101525b50601f01601f19169290920160800195945050505050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b60008282101561083c5761083c610814565b500390565b600060001982141561085557610855610814565b5060010190565b60006020828403121561086e57600080fd5b5051919050565b6000821982111561088857610888610814565b50019056fea2646970667358221220c138eb1d2279cb8e4890ac352d6458a653158d051628473b2781ba8f67bb771264736f6c63430008090033",
}

// TravelAgencyABI is the input ABI used to generate the binding from.
// Deprecated: Use TravelAgencyMetaData.ABI instead.
var TravelAgencyABI = TravelAgencyMetaData.ABI

// TravelAgencyBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use TravelAgencyMetaData.Bin instead.
var TravelAgencyBin = TravelAgencyMetaData.Bin

// DeployTravelAgency deploys a new Ethereum contract, binding an instance of TravelAgency to it.
func DeployTravelAgency(auth *bind.TransactOpts, backend bind.ContractBackend, _hotelBlockchainId *big.Int, _hotelContract common.Address, _trainBlockchainId *big.Int, _trainContract common.Address, _cbc common.Address) (common.Address, *types.Transaction, *TravelAgency, error) {
	parsed, err := TravelAgencyMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(TravelAgencyBin), backend, _hotelBlockchainId, _hotelContract, _trainBlockchainId, _trainContract, _cbc)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &TravelAgency{TravelAgencyCaller: TravelAgencyCaller{contract: contract}, TravelAgencyTransactor: TravelAgencyTransactor{contract: contract}, TravelAgencyFilterer: TravelAgencyFilterer{contract: contract}}, nil
}

// TravelAgency is an auto generated Go binding around an Ethereum contract.
type TravelAgency struct {
	TravelAgencyCaller     // Read-only binding to the contract
	TravelAgencyTransactor // Write-only binding to the contract
	TravelAgencyFilterer   // Log filterer for contract events
}

// TravelAgencyCaller is an auto generated read-only Go binding around an Ethereum contract.
type TravelAgencyCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// TravelAgencyTransactor is an auto generated write-only Go binding around an Ethereum contract.
type TravelAgencyTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// TravelAgencyFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type TravelAgencyFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// TravelAgencySession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type TravelAgencySession struct {
	Contract     *TravelAgency     // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// TravelAgencyCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type TravelAgencyCallerSession struct {
	Contract *TravelAgencyCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts       // Call options to use throughout this session
}

// TravelAgencyTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type TravelAgencyTransactorSession struct {
	Contract     *TravelAgencyTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts       // Transaction auth options to use throughout this session
}

// TravelAgencyRaw is an auto generated low-level Go binding around an Ethereum contract.
type TravelAgencyRaw struct {
	Contract *TravelAgency // Generic contract binding to access the raw methods on
}

// TravelAgencyCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type TravelAgencyCallerRaw struct {
	Contract *TravelAgencyCaller // Generic read-only contract binding to access the raw methods on
}

// TravelAgencyTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type TravelAgencyTransactorRaw struct {
	Contract *TravelAgencyTransactor // Generic write-only contract binding to access the raw methods on
}

// NewTravelAgency creates a new instance of TravelAgency, bound to a specific deployed contract.
func NewTravelAgency(address common.Address, backend bind.ContractBackend) (*TravelAgency, error) {
	contract, err := bindTravelAgency(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &TravelAgency{TravelAgencyCaller: TravelAgencyCaller{contract: contract}, TravelAgencyTransactor: TravelAgencyTransactor{contract: contract}, TravelAgencyFilterer: TravelAgencyFilterer{contract: contract}}, nil
}

// NewTravelAgencyCaller creates a new read-only instance of TravelAgency, bound to a specific deployed contract.
func NewTravelAgencyCaller(address common.Address, caller bind.ContractCaller) (*TravelAgencyCaller, error) {
	contract, err := bindTravelAgency(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &TravelAgencyCaller{contract: contract}, nil
}

// NewTravelAgencyTransactor creates a new write-only instance of TravelAgency, bound to a specific deployed contract.
func NewTravelAgencyTransactor(address common.Address, transactor bind.ContractTransactor) (*TravelAgencyTransactor, error) {
	contract, err := bindTravelAgency(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &TravelAgencyTransactor{contract: contract}, nil
}

// NewTravelAgencyFilterer creates a new log filterer instance of TravelAgency, bound to a specific deployed contract.
func NewTravelAgencyFilterer(address common.Address, filterer bind.ContractFilterer) (*TravelAgencyFilterer, error) {
	contract, err := bindTravelAgency(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &TravelAgencyFilterer{contract: contract}, nil
}

// bindTravelAgency binds a generic wrapper to an already deployed contract.
func bindTravelAgency(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(TravelAgencyABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_TravelAgency *TravelAgencyRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _TravelAgency.Contract.TravelAgencyCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_TravelAgency *TravelAgencyRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _TravelAgency.Contract.TravelAgencyTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_TravelAgency *TravelAgencyRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _TravelAgency.Contract.TravelAgencyTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_TravelAgency *TravelAgencyCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _TravelAgency.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_TravelAgency *TravelAgencyTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _TravelAgency.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_TravelAgency *TravelAgencyTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _TravelAgency.Contract.contract.Transact(opts, method, params...)
}

// BookingConfirmed is a free data retrieval call binding the contract method 0x036b0edf.
//
// Solidity: function bookingConfirmed(uint256 _bookingId) view returns(bool)
func (_TravelAgency *TravelAgencyCaller) BookingConfirmed(opts *bind.CallOpts, _bookingId *big.Int) (bool, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "bookingConfirmed", _bookingId)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// BookingConfirmed is a free data retrieval call binding the contract method 0x036b0edf.
//
// Solidity: function bookingConfirmed(uint256 _bookingId) view returns(bool)
func (_TravelAgency *TravelAgencySession) BookingConfirmed(_bookingId *big.Int) (bool, error) {
	return _TravelAgency.Contract.BookingConfirmed(&_TravelAgency.CallOpts, _bookingId)
}

// BookingConfirmed is a free data retrieval call binding the contract method 0x036b0edf.
//
// Solidity: function bookingConfirmed(uint256 _bookingId) view returns(bool)
func (_TravelAgency *TravelAgencyCallerSession) BookingConfirmed(_bookingId *big.Int) (bool, error) {
	return _TravelAgency.Contract.BookingConfirmed(&_TravelAgency.CallOpts, _bookingId)
}

// HotelBlockchainId is a free data retrieval call binding the contract method 0x4d1177da.
//
// Solidity: function hotelBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencyCaller) HotelBlockchainId(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "hotelBlockchainId")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// HotelBlockchainId is a free data retrieval call binding the contract method 0x4d1177da.
//
// Solidity: function hotelBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencySession) HotelBlockchainId() (*big.Int, error) {
	return _TravelAgency.Contract.HotelBlockchainId(&_TravelAgency.CallOpts)
}

// HotelBlockchainId is a free data retrieval call binding the contract method 0x4d1177da.
//
// Solidity: function hotelBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencyCallerSession) HotelBlockchainId() (*big.Int, error) {
	return _TravelAgency.Contract.HotelBlockchainId(&_TravelAgency.CallOpts)
}

// HotelContract is a free data retrieval call binding the contract method 0x9d0599ac.
//
// Solidity: function hotelContract() view returns(address)
func (_TravelAgency *TravelAgencyCaller) HotelContract(opts *bind.CallOpts) (common.Address, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "hotelContract")

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// HotelContract is a free data retrieval call binding the contract method 0x9d0599ac.
//
// Solidity: function hotelContract() view returns(address)
func (_TravelAgency *TravelAgencySession) HotelContract() (common.Address, error) {
	return _TravelAgency.Contract.HotelContract(&_TravelAgency.CallOpts)
}

// HotelContract is a free data retrieval call binding the contract method 0x9d0599ac.
//
// Solidity: function hotelContract() view returns(address)
func (_TravelAgency *TravelAgencyCallerSession) HotelContract() (common.Address, error) {
	return _TravelAgency.Contract.HotelContract(&_TravelAgency.CallOpts)
}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_TravelAgency *TravelAgencyCaller) IsLocked(opts *bind.CallOpts, _key *big.Int) (bool, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "isLocked", _key)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_TravelAgency *TravelAgencySession) IsLocked(_key *big.Int) (bool, error) {
	return _TravelAgency.Contract.IsLocked(&_TravelAgency.CallOpts, _key)
}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_TravelAgency *TravelAgencyCallerSession) IsLocked(_key *big.Int) (bool, error) {
	return _TravelAgency.Contract.IsLocked(&_TravelAgency.CallOpts, _key)
}

// TrainBlockchainId is a free data retrieval call binding the contract method 0x168fd46f.
//
// Solidity: function trainBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencyCaller) TrainBlockchainId(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "trainBlockchainId")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TrainBlockchainId is a free data retrieval call binding the contract method 0x168fd46f.
//
// Solidity: function trainBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencySession) TrainBlockchainId() (*big.Int, error) {
	return _TravelAgency.Contract.TrainBlockchainId(&_TravelAgency.CallOpts)
}

// TrainBlockchainId is a free data retrieval call binding the contract method 0x168fd46f.
//
// Solidity: function trainBlockchainId() view returns(uint256)
func (_TravelAgency *TravelAgencyCallerSession) TrainBlockchainId() (*big.Int, error) {
	return _TravelAgency.Contract.TrainBlockchainId(&_TravelAgency.CallOpts)
}

// TrainContract is a free data retrieval call binding the contract method 0x41b1637c.
//
// Solidity: function trainContract() view returns(address)
func (_TravelAgency *TravelAgencyCaller) TrainContract(opts *bind.CallOpts) (common.Address, error) {
	var out []interface{}
	err := _TravelAgency.contract.Call(opts, &out, "trainContract")

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// TrainContract is a free data retrieval call binding the contract method 0x41b1637c.
//
// Solidity: function trainContract() view returns(address)
func (_TravelAgency *TravelAgencySession) TrainContract() (common.Address, error) {
	return _TravelAgency.Contract.TrainContract(&_TravelAgency.CallOpts)
}

// TrainContract is a free data retrieval call binding the contract method 0x41b1637c.
//
// Solidity: function trainContract() view returns(address)
func (_TravelAgency *TravelAgencyCallerSession) TrainContract() (common.Address, error) {
	return _TravelAgency.Contract.TrainContract(&_TravelAgency.CallOpts)
}

// BookHotelAndTrain is a paid mutator transaction binding the contract method 0x8146b5c0.
//
// Solidity: function bookHotelAndTrain(uint256 _date, uint256 _bookingId) returns()
func (_TravelAgency *TravelAgencyTransactor) BookHotelAndTrain(opts *bind.TransactOpts, _date *big.Int, _bookingId *big.Int) (*types.Transaction, error) {
	return _TravelAgency.contract.Transact(opts, "bookHotelAndTrain", _date, _bookingId)
}

// BookHotelAndTrain is a paid mutator transaction binding the contract method 0x8146b5c0.
//
// Solidity: function bookHotelAndTrain(uint256 _date, uint256 _bookingId) returns()
func (_TravelAgency *TravelAgencySession) BookHotelAndTrain(_date *big.Int, _bookingId *big.Int) (*types.Transaction, error) {
	return _TravelAgency.Contract.BookHotelAndTrain(&_TravelAgency.TransactOpts, _date, _bookingId)
}

// BookHotelAndTrain is a paid mutator transaction binding the contract method 0x8146b5c0.
//
// Solidity: function bookHotelAndTrain(uint256 _date, uint256 _bookingId) returns()
func (_TravelAgency *TravelAgencyTransactorSession) BookHotelAndTrain(_date *big.Int, _bookingId *big.Int) (*types.Transaction, error) {
	return _TravelAgency.Contract.BookHotelAndTrain(&_TravelAgency.TransactOpts, _date, _bookingId)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_TravelAgency *TravelAgencyTransactor) Finalise(opts *bind.TransactOpts, _commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _TravelAgency.contract.Transact(opts, "finalise", _commit, _crossRootTxId)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_TravelAgency *TravelAgencySession) Finalise(_commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _TravelAgency.Contract.Finalise(&_TravelAgency.TransactOpts, _commit, _crossRootTxId)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_TravelAgency *TravelAgencyTransactorSession) Finalise(_commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _TravelAgency.Contract.Finalise(&_TravelAgency.TransactOpts, _commit, _crossRootTxId)
}
