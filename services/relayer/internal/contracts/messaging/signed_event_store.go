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
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_functionCall\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sourceBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_sourceCbcAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_eventData\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"}],\"name\":\"relayEvent\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50604051610b72380380610b7283398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610ab4806100be6000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80631cd8253a1461003b5780634c1ce90214610050575b600080fd5b61004e610049366004610581565b610077565b005b61006361005e36600461060b565b61038a565b604051901515815260200160405180910390f35b600086867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad2787876040516020016100b295949392919061064a565b60408051601f19818403018152908290526000546326a2ec3160e21b83529092506001600160a01b031690639a8bb0c4906100f7908a908790879087906004016106ad565b60206040518083038186803b15801561010f57600080fd5b505afa158015610123573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101479190610718565b5080516020820120600061015b8585610492565b905060005b8160200151518110156102385760008260200151828151811061018557610185610741565b6020908102919091018101515160008681526002835260408082206001600160a01b0384168352600301909352919091205490915060ff16156101c85750610226565b60008481526002602081815260408084206001600160a01b03909516808552600386018352908420805460ff191660019081179091558383529490920180549485018155835290912090910180546001600160a01b03191690911790555b8061023081610757565b915050610160565b5060008281526002602052604090206001015460ff161561025b57505050610382565b6000805460405163a64ce19960e01b8152600481018c90526001600160a01b039091169063a64ce1999060240160206040518083038186803b1580156102a057600080fd5b505afa1580156102b4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102d89190610780565b60008481526002602081905260409091200154909150811161037d57600083815260026020526040908190206001908101805460ff191682179055549051632044202960e11b81526001600160a01b039091169063408840529061034a908d908d908d908d908d908d90600401610799565b600060405180830381600087803b15801561036457600080fd5b505af1158015610378573d6000803e3d6000fd5b505050505b505050505b505050505050565b600080858560405161039d9291906107e4565b6040519081900381206000805463a64ce19960e01b8452600484018c9052919350916001600160a01b039091169063a64ce1999060240160206040518083038186803b1580156103ec57600080fd5b505afa158015610400573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104249190610780565b600083815260026020819052604090912001549091508111156104835760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b60448201526064015b60405180910390fd5b50600198975050505050505050565b60408051808201909152600081526060602082015260006104b5838501856107f4565b91505061ffff81166001146105055760405162461bcd60e51b81526020600482015260166024820152755369676e617475726520756e6b6e6f776e207479706560501b604482015260640161047a565b6000610513848601866108be565b95945050505050565b80356001600160a01b038116811461053357600080fd5b919050565b60008083601f84011261054a57600080fd5b50813567ffffffffffffffff81111561056257600080fd5b60208301915083602082850101111561057a57600080fd5b9250929050565b6000806000806000806080878903121561059a57600080fd5b863595506105aa6020880161051c565b9450604087013567ffffffffffffffff808211156105c757600080fd5b6105d38a838b01610538565b909650945060608901359150808211156105ec57600080fd5b506105f989828a01610538565b979a9699509497509295939492505050565b6000806000806000806080878903121561062457600080fd5b8635955060208701359450604087013567ffffffffffffffff808211156105c757600080fd5b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b848152600060206060818401526106c8606084018688610684565b8381036040850152845180825260005b818110156106f35786810184015183820185015283016106d8565b818111156107045760008483850101525b50601f01601f191601019695505050505050565b60006020828403121561072a57600080fd5b8151801515811461073a57600080fd5b9392505050565b634e487b7160e01b600052603260045260246000fd5b600060001982141561077957634e487b7160e01b600052601160045260246000fd5b5060010190565b60006020828403121561079257600080fd5b5051919050565b8681526001600160a01b03861660208201526080604082018190526000906107c49083018688610684565b82810360608401526107d7818587610684565b9998505050505050505050565b8183823760009101908152919050565b6000806040838503121561080757600080fd5b82359150602083013561ffff8116811461082057600080fd5b809150509250929050565b634e487b7160e01b600052604160045260246000fd5b6040805190810167ffffffffffffffff811182821017156108645761086461082b565b60405290565b60405160a0810167ffffffffffffffff811182821017156108645761086461082b565b604051601f8201601f1916810167ffffffffffffffff811182821017156108b6576108b661082b565b604052919050565b600060208083850312156108d157600080fd5b823567ffffffffffffffff808211156108e957600080fd5b90840190604082870312156108fd57600080fd5b610905610841565b82358152838301358281111561091a57600080fd5b80840193505086601f84011261092f57600080fd5b8235828111156109415761094161082b565b8060051b61095086820161088d565b918252848101860191868101908a84111561096a57600080fd5b87870192505b83831015610a6a5782358681111561098757600080fd5b8701601f1960a0828e038201121561099e57600080fd5b6109a661086a565b6109b18b840161051c565b815260408301358b82015260608301356040820152608083013560ff811681146109da57600080fd5b606082015260a0830135898111156109f157600080fd5b8084019350508d603f840112610a0657600080fd5b8a83013589811115610a1a57610a1a61082b565b610a2a8c84601f8401160161088d565b92508083528e6040828601011115610a4157600080fd5b80604085018d85013760009083018c015260808101919091528352509187019190870190610970565b96840196909652509097965050505050505056fea26469706673582212206436105d47514b1e9e4fc491dea89abff1df8a7bb893f477e48b4b9d2125f84d64736f6c63430008090033",
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
