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
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_registrar\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_functionCall\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"_encodedEvent\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"name\":\"decodeAndVerifyEvent\",\"outputs\":[],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sourceBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_sourceCbcAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_eventData\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signature\",\"type\":\"bytes\"}],\"name\":\"relayEvent\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50604051610f27380380610f2783398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e69806100be6000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80631cd8253a1461003b5780634c1ce90214610050575b600080fd5b61004e610049366004610a71565b610063565b005b61004e61005e366004610b09565b6107f2565b60608060608060006100aa87878080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525092506108ef915050565b90506100bd605563ffffffff8316610b5e565b6100c8906004610b7d565b861461011b5760405162461bcd60e51b815260206004820152601a60248201527f5369676e617475726520696e636f7272656374206c656e67746800000000000060448201526064015b60405180910390fd5b8063ffffffff1667ffffffffffffffff81111561013a5761013a610b95565b604051908082528060200260200182016040528015610163578160200160208202803683370190505b5094508063ffffffff1667ffffffffffffffff81111561018557610185610b95565b6040519080825280602002602001820160405280156101ae578160200160208202803683370190505b5093508063ffffffff1667ffffffffffffffff8111156101d0576101d0610b95565b6040519080825280602002602001820160405280156101f9578160200160208202803683370190505b5092508063ffffffff1667ffffffffffffffff81111561021b5761021b610b95565b604051908082528060200260200182016040528015610244578160200160208202803683370190505b509150600460005b8263ffffffff168110156104325761029b89898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610955915050565b8782815181106102ad576102ad610bab565b6001600160a01b03909216602092830291909101909101526102d0601483610b7d565b915061031389898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061095d915050565b86828151811061032557610325610bab565b60200260200101818152505060208261033e9190610b7d565b915061038189898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061095d915050565b85828151811061039357610393610bab565b6020026020010181815250506020826103ac9190610b7d565b91506103ef89898080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508692506109c2915050565b84828151811061040157610401610bab565b60ff9092166020928302919091019091015261041e600183610b7d565b91508061042a81610bc1565b91505061024c565b505060008b8b7f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278c8c60405160200161046f959493929190610bdc565b60408051601f198184030181529082905260005463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b906104b8908f908a908a908a908a908990600401610cd1565b60206040518083038186803b1580156104d057600080fd5b505afa1580156104e4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105089190610d7a565b508051602082012060005b8363ffffffff1681101561065d5760026000838152602001908152602001600020600301600089838151811061054b5761054b610bab565b6020908102919091018101516001600160a01b031682528101919091526040016000205460ff161561057c5761064b565b60016002600084815260200190815260200160002060030160008a84815181106105a8576105a8610bab565b60200260200101516001600160a01b03166001600160a01b0316815260200190815260200160002060006101000a81548160ff0219169083151502179055506002600083815260200190815260200160002060020188828151811061060f5761060f610bab565b60209081029190910181015182546001810184556000938452919092200180546001600160a01b0319166001600160a01b039092169190911790555b8061065581610bc1565b915050610513565b5060008181526002602052604090206001015460ff161561068457505050505050506107ea565b60008060009054906101000a90046001600160a01b03166001600160a01b031663a64ce1998f6040518263ffffffff1660e01b81526004016106c891815260200190565b60206040518083038186803b1580156106e057600080fd5b505afa1580156106f4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107189190610da3565b6000838152600260208190526040909120015490915081116107e15760016002600084815260200190815260200160002060010160006101000a81548160ff021916908315150217905550600160009054906101000a90046001600160a01b03166001600160a01b031663408840528f8f8f8f8f8f6040518763ffffffff1660e01b81526004016107ae96959493929190610de5565b600060405180830381600087803b1580156107c857600080fd5b505af11580156107dc573d6000803e3d6000fd5b505050505b50505050505050505b505050505050565b60008484604051610804929190610e23565b6040519081900381206000805463a64ce19960e01b8452600484018b9052919350916001600160a01b039091169063a64ce1999060240160206040518083038186803b15801561085357600080fd5b505afa158015610867573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061088b9190610da3565b600083815260026020819052604090912001549091508111156108e55760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b6044820152606401610112565b5050505050505050565b60006108fc826004610b7d565b8351101561094c5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743332290000006044820152606401610112565b50016004015190565b016014015190565b60008060005b60208110156109ba57610977816008610b5e565b856109828387610b7d565b8151811061099257610992610bab565b01602001516001600160f81b031916901c9190911790806109b281610bc1565b915050610963565b509392505050565b60006109cf826001610b7d565b83511015610a1f5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e743829000000006044820152606401610112565b50016001015190565b60008083601f840112610a3a57600080fd5b50813567ffffffffffffffff811115610a5257600080fd5b602083019150836020828501011115610a6a57600080fd5b9250929050565b60008060008060008060808789031215610a8a57600080fd5b8635955060208701356001600160a01b0381168114610aa857600080fd5b9450604087013567ffffffffffffffff80821115610ac557600080fd5b610ad18a838b01610a28565b90965094506060890135915080821115610aea57600080fd5b50610af789828a01610a28565b979a9699509497509295939492505050565b60008060008060008060808789031215610b2257600080fd5b8635955060208701359450604087013567ffffffffffffffff80821115610ac557600080fd5b634e487b7160e01b600052601160045260246000fd5b6000816000190483118215151615610b7857610b78610b48565b500290565b60008219821115610b9057610b90610b48565b500190565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b6000600019821415610bd557610bd5610b48565b5060010190565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b600081518084526020808501945080840160005b83811015610c4657815187529582019590820190600101610c2a565b509495945050505050565b600081518084526020808501945080840160005b83811015610c4657815160ff1687529582019590820190600101610c65565b6000815180845260005b81811015610caa57602081850181015186830182015201610c8e565b81811115610cbc576000602083870101525b50601f01601f19169290920160200192915050565b600060c08201888352602060c08185015281895180845260e086019150828b01935060005b81811015610d1b5784516001600160a01b031683529383019391830191600101610cf6565b50508481036040860152610d2f818a610c16565b925050508281036060840152610d458187610c16565b90508281036080840152610d598186610c51565b905082810360a0840152610d6d8185610c84565b9998505050505050505050565b600060208284031215610d8c57600080fd5b81518015158114610d9c57600080fd5b9392505050565b600060208284031215610db557600080fd5b5051919050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b8681526001600160a01b0386166020820152608060408201819052600090610e109083018688610dbc565b8281036060840152610d6d818587610dbc565b818382376000910190815291905056fea26469706673582212206afd14ab6073b566a114c2d289435844d38c7fa1ea4da61c0af91220a3f10d3c64736f6c63430008090033",
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
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns()
func (_SignedEventStore *SignedEventStoreCaller) DecodeAndVerifyEvent(opts *bind.CallOpts, _blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) error {
	var out []interface{}
	err := _SignedEventStore.contract.Call(opts, &out, "decodeAndVerifyEvent", _blockchainId, arg1, _encodedEvent, arg3)

	if err != nil {
		return err
	}

	return err

}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns()
func (_SignedEventStore *SignedEventStoreSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) error {
	return _SignedEventStore.Contract.DecodeAndVerifyEvent(&_SignedEventStore.CallOpts, _blockchainId, arg1, _encodedEvent, arg3)
}

// DecodeAndVerifyEvent is a free data retrieval call binding the contract method 0x4c1ce902.
//
// Solidity: function decodeAndVerifyEvent(uint256 _blockchainId, bytes32 , bytes _encodedEvent, bytes ) view returns()
func (_SignedEventStore *SignedEventStoreCallerSession) DecodeAndVerifyEvent(_blockchainId *big.Int, arg1 [32]byte, _encodedEvent []byte, arg3 []byte) error {
	return _SignedEventStore.Contract.DecodeAndVerifyEvent(&_SignedEventStore.CallOpts, _blockchainId, arg1, _encodedEvent, arg3)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signature) returns()
func (_SignedEventStore *SignedEventStoreTransactor) RelayEvent(opts *bind.TransactOpts, _sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _SignedEventStore.contract.Transact(opts, "relayEvent", _sourceBlockchainId, _sourceCbcAddress, _eventData, _signature)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signature) returns()
func (_SignedEventStore *SignedEventStoreSession) RelayEvent(_sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _SignedEventStore.Contract.RelayEvent(&_SignedEventStore.TransactOpts, _sourceBlockchainId, _sourceCbcAddress, _eventData, _signature)
}

// RelayEvent is a paid mutator transaction binding the contract method 0x1cd8253a.
//
// Solidity: function relayEvent(uint256 _sourceBlockchainId, address _sourceCbcAddress, bytes _eventData, bytes _signature) returns()
func (_SignedEventStore *SignedEventStoreTransactorSession) RelayEvent(_sourceBlockchainId *big.Int, _sourceCbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _SignedEventStore.Contract.RelayEvent(&_SignedEventStore.TransactOpts, _sourceBlockchainId, _sourceCbcAddress, _eventData, _signature)
}
