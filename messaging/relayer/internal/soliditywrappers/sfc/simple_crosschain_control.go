// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package sfc

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

// SfcMetaData contains all meta data concerning the Sfc contract.
var SfcMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_myBlockchainId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_timeHorizon\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"string\",\"name\":\"_revertReason\",\"type\":\"string\"}],\"name\":\"CallFailure\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"_txId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_caller\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"_destContract\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes\",\"name\":\"_destFunctionCall\",\"type\":\"bytes\"}],\"name\":\"CrossCall\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_cbc\",\"type\":\"address\"}],\"name\":\"addRemoteCrosschainControl\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_verifier\",\"type\":\"address\"}],\"name\":\"addVerifier\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_destBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_destContract\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_destData\",\"type\":\"bytes\"}],\"name\":\"crossBlockchainCall\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sourceBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_cbcAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_eventData\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signature\",\"type\":\"bytes\"}],\"name\":\"crossCallHandler\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sourceBcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_cbcAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_eventData\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_signature\",\"type\":\"bytes\"},{\"internalType\":\"bytes32[]\",\"name\":\"_oldTxIds\",\"type\":\"bytes32[]\"}],\"name\":\"crossCallHandlerSaveGas\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"myBlockchainId\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"replayPrevention\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"timeHorizon\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b506040516113c03803806113c083398101604081905261002f9161007e565b600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3506005919091556003556100a2565b6000806040838503121561009157600080fd5b505080516020909101519092909150565b61130f806100b16000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063715018a611610071578063715018a61461011b5780638da5cb5b1461012357806392b2c3351461013e578063b283209614610151578063f2fde38b14610164578063f51a72d81461017757600080fd5b80630a3ef1f2146100ae5780631101b8f0146100e157806319836dc7146100ea57806340884052146100ff578063439160df14610112575b600080fd5b6100ce6100bc366004610b2c565b60046020526000908152604090205481565b6040519081526020015b60405180910390f35b6100ce60035481565b6100fd6100f8366004610b5d565b61018a565b005b6100fd61010d366004610bd6565b6101eb565b6100ce60055481565b6100fd61041f565b6000546040516001600160a01b0390911681526020016100d8565b6100fd61014c366004610c62565b610493565b6100fd61015f366004610b5d565b610514565b6100fd610172366004610cbe565b610607565b6100fd610185366004610cdb565b6106f1565b6000546001600160a01b031633146101bd5760405162461bcd60e51b81526004016101b490610dc1565b60405180910390fd5b60009182526002602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b61021a86867f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278787878761078a565b600080808080606061022e898b018b610e65565b600086815260046020526040902054959b50939950919750955093509150156102995760405162461bcd60e51b815260206004820152601a60248201527f5472616e73616374696f6e20616c72656164792065786973747300000000000060448201526064016101b4565b4285106102e85760405162461bcd60e51b815260206004820181905260248201527f4576656e742074696d657374616d7020697320696e207468652066757475726560448201526064016101b4565b42600354866102f79190610f3c565b116103375760405162461bcd60e51b815260206004820152601060248201526f115d995b9d081a5cc81d1bdbc81bdb1960821b60448201526064016101b4565b6000868152600460205260409020859055600554831461035657600080fd5b6000610363828e8761090e565b905060006060846001600160a01b0316836040516103819190610f84565b6000604051808303816000865af19150503d80600081146103be576040519150601f19603f3d011682016040523d82523d6000602084013e6103c3565b606091505b5090925090508161040e577f38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a66103f882610975565b6040516104059190610fcc565b60405180910390a15b505050505050505050505050505050565b6000546001600160a01b031633146104495760405162461bcd60e51b81526004016101b490610dc1565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b600042600554868686866040516020016104b296959493929190610fdf565b6040516020818303038152906040528051906020012090507f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27814233888888886040516105059796959493929190611049565b60405180910390a15050505050565b6000546001600160a01b0316331461053e5760405162461bcd60e51b81526004016101b490610dc1565b816105835760405162461bcd60e51b8152602060048201526015602482015274125b9d985b1a5908189b1bd8dad8da185a5b881a59605a1b60448201526064016101b4565b6001600160a01b0381166105d95760405162461bcd60e51b815260206004820152601860248201527f496e76616c69642076657269666965722061646472657373000000000000000060448201526064016101b4565b60009182526001602052604090912080546001600160a01b0319166001600160a01b03909216919091179055565b6000546001600160a01b031633146106315760405162461bcd60e51b81526004016101b490610dc1565b6001600160a01b0381166106965760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016101b4565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60005b8181101561077157600083838381811061071057610710611097565b90506020020135905060006004600083815260200190815260200160002054905042600354826107409190610f3c565b111561075c57801561075c576000828152600460205260408120555b50508080610769906110ad565b9150506106f4565b506107808888888888886101eb565b5050505050505050565b6000878152600160205260409020546001600160a01b0316806107fd5760405162461bcd60e51b815260206004820152602560248201527f4e6f207265676973746572656420766572696669657220666f7220626c6f636b60448201526431b430b4b760d91b60648201526084016101b4565b6000888152600260205260409020546001600160a01b038881169116146108745760405162461bcd60e51b815260206004820152602560248201527f44617461206e6f7420656d697474656420627920617070726f76656420636f6e6044820152641d1c9858dd60da1b60648201526084016101b4565b6000888888888860405160200161088f9594939291906110c8565b60408051601f198184030181529082905263260e748160e11b825291506001600160a01b03831690634c1ce902906108d3908c908b9086908a908a90600401611102565b60006040518083038186803b1580156108eb57600080fd5b505afa1580156108ff573d6000803e3d6000fd5b50505050505050505050505050565b606083838360405160200161093f92919091825260601b6bffffffffffffffffffffffff1916602082015260340190565b60408051601f198184030181529082905261095d9291602001611140565b60405160208183030381529060405290509392505050565b60606024825110156109b15761098b8251610a26565b60405160200161099b919061116f565b6040516020818303038152906040529050919050565b81516004909201916044118015610a0b576000838060200190518101906109d891906111c5565b90506109e381610a26565b6040516020016109f391906111de565b60405160208183030381529060405292505050919050565b82806020019051810190610a1f919061120d565b9392505050565b606081610a4a5750506040805180820190915260018152600360fc1b602082015290565b8160005b8115610a745780610a5e816110ad565b9150610a6d9050600a8361129a565b9150610a4e565b60008167ffffffffffffffff811115610a8f57610a8f610df6565b6040519080825280601f01601f191660200182016040528015610ab9576020820181803683370190505b5090505b8415610b2457610ace6001836112ae565b9150610adb600a866112c5565b610ae6906030610f3c565b60f81b818381518110610afb57610afb611097565b60200101906001600160f81b031916908160001a905350610b1d600a8661129a565b9450610abd565b949350505050565b600060208284031215610b3e57600080fd5b5035919050565b6001600160a01b0381168114610b5a57600080fd5b50565b60008060408385031215610b7057600080fd5b823591506020830135610b8281610b45565b809150509250929050565b60008083601f840112610b9f57600080fd5b50813567ffffffffffffffff811115610bb757600080fd5b602083019150836020828501011115610bcf57600080fd5b9250929050565b60008060008060008060808789031215610bef57600080fd5b863595506020870135610c0181610b45565b9450604087013567ffffffffffffffff80821115610c1e57600080fd5b610c2a8a838b01610b8d565b90965094506060890135915080821115610c4357600080fd5b50610c5089828a01610b8d565b979a9699509497509295939492505050565b60008060008060608587031215610c7857600080fd5b843593506020850135610c8a81610b45565b9250604085013567ffffffffffffffff811115610ca657600080fd5b610cb287828801610b8d565b95989497509550505050565b600060208284031215610cd057600080fd5b8135610a1f81610b45565b60008060008060008060008060a0898b031215610cf757600080fd5b883597506020890135610d0981610b45565b9650604089013567ffffffffffffffff80821115610d2657600080fd5b610d328c838d01610b8d565b909850965060608b0135915080821115610d4b57600080fd5b610d578c838d01610b8d565b909650945060808b0135915080821115610d7057600080fd5b818b0191508b601f830112610d8457600080fd5b813581811115610d9357600080fd5b8c60208260051b8501011115610da857600080fd5b6020830194508093505050509295985092959890939650565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610e3557610e35610df6565b604052919050565b600067ffffffffffffffff821115610e5757610e57610df6565b50601f01601f191660200190565b60008060008060008060c08789031215610e7e57600080fd5b86359550602087013594506040870135610e9781610b45565b9350606087013592506080870135610eae81610b45565b915060a087013567ffffffffffffffff811115610eca57600080fd5b8701601f81018913610edb57600080fd5b8035610eee610ee982610e3d565b610e0c565b8181528a6020838501011115610f0357600080fd5b816020840160208301376000602083830101528093505050509295509295509295565b634e487b7160e01b600052601160045260246000fd5b60008219821115610f4f57610f4f610f26565b500190565b60005b83811015610f6f578181015183820152602001610f57565b83811115610f7e576000848401525b50505050565b60008251610f96818460208701610f54565b9190910192915050565b60008151808452610fb8816020860160208601610f54565b601f01601f19169290920160200192915050565b602081526000610a1f6020830184610fa0565b8681528560208201528460408201526bffffffffffffffffffffffff198460601b166060820152818360748301376000910160740190815295945050505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b878152602081018790526001600160a01b038681166040830152606082018690528416608082015260c060a0820181905260009061108a9083018486611020565b9998505050505050505050565b634e487b7160e01b600052603260045260246000fd5b60006000198214156110c1576110c1610f26565b5060010190565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b8581528460208201526080604082015260006111216080830186610fa0565b8281036060840152611134818587611020565b98975050505050505050565b60008351611152818460208801610f54565b835190830190611166818360208801610f54565b01949350505050565b7f52657665727420666f7220756e6b6e6f776e206572726f722e204572726f722081526703632b733ba341d160c51b6020820152600082516111b8816028850160208701610f54565b9190910160280192915050565b6000602082840312156111d757600080fd5b5051919050565b6602830b734b19d160cd1b815260008251611200816007850160208701610f54565b9190910160070192915050565b60006020828403121561121f57600080fd5b815167ffffffffffffffff81111561123657600080fd5b8201601f8101841361124757600080fd5b8051611255610ee982610e3d565b81815285602083850101111561126a57600080fd5b61127b826020830160208601610f54565b95945050505050565b634e487b7160e01b600052601260045260246000fd5b6000826112a9576112a9611284565b500490565b6000828210156112c0576112c0610f26565b500390565b6000826112d4576112d4611284565b50069056fea2646970667358221220abb829896f4fb38e8171fec15d432d8cd81ade6932ccac37f675e221a49d27ae64736f6c63430008090033",
}

// SfcABI is the input ABI used to generate the binding from.
// Deprecated: Use SfcMetaData.ABI instead.
var SfcABI = SfcMetaData.ABI

// SfcBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use SfcMetaData.Bin instead.
var SfcBin = SfcMetaData.Bin

// DeploySfc deploys a new Ethereum contract, binding an instance of Sfc to it.
func DeploySfc(auth *bind.TransactOpts, backend bind.ContractBackend, _myBlockchainId *big.Int, _timeHorizon *big.Int) (common.Address, *types.Transaction, *Sfc, error) {
	parsed, err := SfcMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(SfcBin), backend, _myBlockchainId, _timeHorizon)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &Sfc{SfcCaller: SfcCaller{contract: contract}, SfcTransactor: SfcTransactor{contract: contract}, SfcFilterer: SfcFilterer{contract: contract}}, nil
}

// Sfc is an auto generated Go binding around an Ethereum contract.
type Sfc struct {
	SfcCaller     // Read-only binding to the contract
	SfcTransactor // Write-only binding to the contract
	SfcFilterer   // Log filterer for contract events
}

// SfcCaller is an auto generated read-only Go binding around an Ethereum contract.
type SfcCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcTransactor is an auto generated write-only Go binding around an Ethereum contract.
type SfcTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type SfcFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// SfcSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type SfcSession struct {
	Contract     *Sfc              // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// SfcCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type SfcCallerSession struct {
	Contract *SfcCaller    // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts // Call options to use throughout this session
}

// SfcTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type SfcTransactorSession struct {
	Contract     *SfcTransactor    // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// SfcRaw is an auto generated low-level Go binding around an Ethereum contract.
type SfcRaw struct {
	Contract *Sfc // Generic contract binding to access the raw methods on
}

// SfcCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type SfcCallerRaw struct {
	Contract *SfcCaller // Generic read-only contract binding to access the raw methods on
}

// SfcTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type SfcTransactorRaw struct {
	Contract *SfcTransactor // Generic write-only contract binding to access the raw methods on
}

// NewSfc creates a new instance of Sfc, bound to a specific deployed contract.
func NewSfc(address common.Address, backend bind.ContractBackend) (*Sfc, error) {
	contract, err := bindSfc(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &Sfc{SfcCaller: SfcCaller{contract: contract}, SfcTransactor: SfcTransactor{contract: contract}, SfcFilterer: SfcFilterer{contract: contract}}, nil
}

// NewSfcCaller creates a new read-only instance of Sfc, bound to a specific deployed contract.
func NewSfcCaller(address common.Address, caller bind.ContractCaller) (*SfcCaller, error) {
	contract, err := bindSfc(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &SfcCaller{contract: contract}, nil
}

// NewSfcTransactor creates a new write-only instance of Sfc, bound to a specific deployed contract.
func NewSfcTransactor(address common.Address, transactor bind.ContractTransactor) (*SfcTransactor, error) {
	contract, err := bindSfc(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &SfcTransactor{contract: contract}, nil
}

// NewSfcFilterer creates a new log filterer instance of Sfc, bound to a specific deployed contract.
func NewSfcFilterer(address common.Address, filterer bind.ContractFilterer) (*SfcFilterer, error) {
	contract, err := bindSfc(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &SfcFilterer{contract: contract}, nil
}

// bindSfc binds a generic wrapper to an already deployed contract.
func bindSfc(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(SfcABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Sfc *SfcRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Sfc.Contract.SfcCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Sfc *SfcRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Sfc.Contract.SfcTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Sfc *SfcRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Sfc.Contract.SfcTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Sfc *SfcCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Sfc.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Sfc *SfcTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Sfc.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Sfc *SfcTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Sfc.Contract.contract.Transact(opts, method, params...)
}

// MyBlockchainId is a free data retrieval call binding the contract method 0x439160df.
//
// Solidity: function myBlockchainId() view returns(uint256)
func (_Sfc *SfcCaller) MyBlockchainId(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _Sfc.contract.Call(opts, &out, "myBlockchainId")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// MyBlockchainId is a free data retrieval call binding the contract method 0x439160df.
//
// Solidity: function myBlockchainId() view returns(uint256)
func (_Sfc *SfcSession) MyBlockchainId() (*big.Int, error) {
	return _Sfc.Contract.MyBlockchainId(&_Sfc.CallOpts)
}

// MyBlockchainId is a free data retrieval call binding the contract method 0x439160df.
//
// Solidity: function myBlockchainId() view returns(uint256)
func (_Sfc *SfcCallerSession) MyBlockchainId() (*big.Int, error) {
	return _Sfc.Contract.MyBlockchainId(&_Sfc.CallOpts)
}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Sfc *SfcCaller) Owner(opts *bind.CallOpts) (common.Address, error) {
	var out []interface{}
	err := _Sfc.contract.Call(opts, &out, "owner")

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Sfc *SfcSession) Owner() (common.Address, error) {
	return _Sfc.Contract.Owner(&_Sfc.CallOpts)
}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Sfc *SfcCallerSession) Owner() (common.Address, error) {
	return _Sfc.Contract.Owner(&_Sfc.CallOpts)
}

// ReplayPrevention is a free data retrieval call binding the contract method 0x0a3ef1f2.
//
// Solidity: function replayPrevention(bytes32 ) view returns(uint256)
func (_Sfc *SfcCaller) ReplayPrevention(opts *bind.CallOpts, arg0 [32]byte) (*big.Int, error) {
	var out []interface{}
	err := _Sfc.contract.Call(opts, &out, "replayPrevention", arg0)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// ReplayPrevention is a free data retrieval call binding the contract method 0x0a3ef1f2.
//
// Solidity: function replayPrevention(bytes32 ) view returns(uint256)
func (_Sfc *SfcSession) ReplayPrevention(arg0 [32]byte) (*big.Int, error) {
	return _Sfc.Contract.ReplayPrevention(&_Sfc.CallOpts, arg0)
}

// ReplayPrevention is a free data retrieval call binding the contract method 0x0a3ef1f2.
//
// Solidity: function replayPrevention(bytes32 ) view returns(uint256)
func (_Sfc *SfcCallerSession) ReplayPrevention(arg0 [32]byte) (*big.Int, error) {
	return _Sfc.Contract.ReplayPrevention(&_Sfc.CallOpts, arg0)
}

// TimeHorizon is a free data retrieval call binding the contract method 0x1101b8f0.
//
// Solidity: function timeHorizon() view returns(uint256)
func (_Sfc *SfcCaller) TimeHorizon(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _Sfc.contract.Call(opts, &out, "timeHorizon")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// TimeHorizon is a free data retrieval call binding the contract method 0x1101b8f0.
//
// Solidity: function timeHorizon() view returns(uint256)
func (_Sfc *SfcSession) TimeHorizon() (*big.Int, error) {
	return _Sfc.Contract.TimeHorizon(&_Sfc.CallOpts)
}

// TimeHorizon is a free data retrieval call binding the contract method 0x1101b8f0.
//
// Solidity: function timeHorizon() view returns(uint256)
func (_Sfc *SfcCallerSession) TimeHorizon() (*big.Int, error) {
	return _Sfc.Contract.TimeHorizon(&_Sfc.CallOpts)
}

// AddRemoteCrosschainControl is a paid mutator transaction binding the contract method 0x19836dc7.
//
// Solidity: function addRemoteCrosschainControl(uint256 _blockchainId, address _cbc) returns()
func (_Sfc *SfcTransactor) AddRemoteCrosschainControl(opts *bind.TransactOpts, _blockchainId *big.Int, _cbc common.Address) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "addRemoteCrosschainControl", _blockchainId, _cbc)
}

// AddRemoteCrosschainControl is a paid mutator transaction binding the contract method 0x19836dc7.
//
// Solidity: function addRemoteCrosschainControl(uint256 _blockchainId, address _cbc) returns()
func (_Sfc *SfcSession) AddRemoteCrosschainControl(_blockchainId *big.Int, _cbc common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.AddRemoteCrosschainControl(&_Sfc.TransactOpts, _blockchainId, _cbc)
}

// AddRemoteCrosschainControl is a paid mutator transaction binding the contract method 0x19836dc7.
//
// Solidity: function addRemoteCrosschainControl(uint256 _blockchainId, address _cbc) returns()
func (_Sfc *SfcTransactorSession) AddRemoteCrosschainControl(_blockchainId *big.Int, _cbc common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.AddRemoteCrosschainControl(&_Sfc.TransactOpts, _blockchainId, _cbc)
}

// AddVerifier is a paid mutator transaction binding the contract method 0xb2832096.
//
// Solidity: function addVerifier(uint256 _blockchainId, address _verifier) returns()
func (_Sfc *SfcTransactor) AddVerifier(opts *bind.TransactOpts, _blockchainId *big.Int, _verifier common.Address) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "addVerifier", _blockchainId, _verifier)
}

// AddVerifier is a paid mutator transaction binding the contract method 0xb2832096.
//
// Solidity: function addVerifier(uint256 _blockchainId, address _verifier) returns()
func (_Sfc *SfcSession) AddVerifier(_blockchainId *big.Int, _verifier common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.AddVerifier(&_Sfc.TransactOpts, _blockchainId, _verifier)
}

// AddVerifier is a paid mutator transaction binding the contract method 0xb2832096.
//
// Solidity: function addVerifier(uint256 _blockchainId, address _verifier) returns()
func (_Sfc *SfcTransactorSession) AddVerifier(_blockchainId *big.Int, _verifier common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.AddVerifier(&_Sfc.TransactOpts, _blockchainId, _verifier)
}

// CrossBlockchainCall is a paid mutator transaction binding the contract method 0x92b2c335.
//
// Solidity: function crossBlockchainCall(uint256 _destBcId, address _destContract, bytes _destData) returns()
func (_Sfc *SfcTransactor) CrossBlockchainCall(opts *bind.TransactOpts, _destBcId *big.Int, _destContract common.Address, _destData []byte) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "crossBlockchainCall", _destBcId, _destContract, _destData)
}

// CrossBlockchainCall is a paid mutator transaction binding the contract method 0x92b2c335.
//
// Solidity: function crossBlockchainCall(uint256 _destBcId, address _destContract, bytes _destData) returns()
func (_Sfc *SfcSession) CrossBlockchainCall(_destBcId *big.Int, _destContract common.Address, _destData []byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossBlockchainCall(&_Sfc.TransactOpts, _destBcId, _destContract, _destData)
}

// CrossBlockchainCall is a paid mutator transaction binding the contract method 0x92b2c335.
//
// Solidity: function crossBlockchainCall(uint256 _destBcId, address _destContract, bytes _destData) returns()
func (_Sfc *SfcTransactorSession) CrossBlockchainCall(_destBcId *big.Int, _destContract common.Address, _destData []byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossBlockchainCall(&_Sfc.TransactOpts, _destBcId, _destContract, _destData)
}

// CrossCallHandler is a paid mutator transaction binding the contract method 0x40884052.
//
// Solidity: function crossCallHandler(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature) returns()
func (_Sfc *SfcTransactor) CrossCallHandler(opts *bind.TransactOpts, _sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "crossCallHandler", _sourceBcId, _cbcAddress, _eventData, _signature)
}

// CrossCallHandler is a paid mutator transaction binding the contract method 0x40884052.
//
// Solidity: function crossCallHandler(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature) returns()
func (_Sfc *SfcSession) CrossCallHandler(_sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossCallHandler(&_Sfc.TransactOpts, _sourceBcId, _cbcAddress, _eventData, _signature)
}

// CrossCallHandler is a paid mutator transaction binding the contract method 0x40884052.
//
// Solidity: function crossCallHandler(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature) returns()
func (_Sfc *SfcTransactorSession) CrossCallHandler(_sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossCallHandler(&_Sfc.TransactOpts, _sourceBcId, _cbcAddress, _eventData, _signature)
}

// CrossCallHandlerSaveGas is a paid mutator transaction binding the contract method 0xf51a72d8.
//
// Solidity: function crossCallHandlerSaveGas(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature, bytes32[] _oldTxIds) returns()
func (_Sfc *SfcTransactor) CrossCallHandlerSaveGas(opts *bind.TransactOpts, _sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte, _oldTxIds [][32]byte) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "crossCallHandlerSaveGas", _sourceBcId, _cbcAddress, _eventData, _signature, _oldTxIds)
}

// CrossCallHandlerSaveGas is a paid mutator transaction binding the contract method 0xf51a72d8.
//
// Solidity: function crossCallHandlerSaveGas(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature, bytes32[] _oldTxIds) returns()
func (_Sfc *SfcSession) CrossCallHandlerSaveGas(_sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte, _oldTxIds [][32]byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossCallHandlerSaveGas(&_Sfc.TransactOpts, _sourceBcId, _cbcAddress, _eventData, _signature, _oldTxIds)
}

// CrossCallHandlerSaveGas is a paid mutator transaction binding the contract method 0xf51a72d8.
//
// Solidity: function crossCallHandlerSaveGas(uint256 _sourceBcId, address _cbcAddress, bytes _eventData, bytes _signature, bytes32[] _oldTxIds) returns()
func (_Sfc *SfcTransactorSession) CrossCallHandlerSaveGas(_sourceBcId *big.Int, _cbcAddress common.Address, _eventData []byte, _signature []byte, _oldTxIds [][32]byte) (*types.Transaction, error) {
	return _Sfc.Contract.CrossCallHandlerSaveGas(&_Sfc.TransactOpts, _sourceBcId, _cbcAddress, _eventData, _signature, _oldTxIds)
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Sfc *SfcTransactor) RenounceOwnership(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "renounceOwnership")
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Sfc *SfcSession) RenounceOwnership() (*types.Transaction, error) {
	return _Sfc.Contract.RenounceOwnership(&_Sfc.TransactOpts)
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Sfc *SfcTransactorSession) RenounceOwnership() (*types.Transaction, error) {
	return _Sfc.Contract.RenounceOwnership(&_Sfc.TransactOpts)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Sfc *SfcTransactor) TransferOwnership(opts *bind.TransactOpts, newOwner common.Address) (*types.Transaction, error) {
	return _Sfc.contract.Transact(opts, "transferOwnership", newOwner)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Sfc *SfcSession) TransferOwnership(newOwner common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.TransferOwnership(&_Sfc.TransactOpts, newOwner)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Sfc *SfcTransactorSession) TransferOwnership(newOwner common.Address) (*types.Transaction, error) {
	return _Sfc.Contract.TransferOwnership(&_Sfc.TransactOpts, newOwner)
}

// SfcCallFailureIterator is returned from FilterCallFailure and is used to iterate over the raw logs and unpacked data for CallFailure events raised by the Sfc contract.
type SfcCallFailureIterator struct {
	Event *SfcCallFailure // Event containing the contract specifics and raw log

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
func (it *SfcCallFailureIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcCallFailure)
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
		it.Event = new(SfcCallFailure)
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
func (it *SfcCallFailureIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcCallFailureIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcCallFailure represents a CallFailure event raised by the Sfc contract.
type SfcCallFailure struct {
	RevertReason string
	Raw          types.Log // Blockchain specific contextual infos
}

// FilterCallFailure is a free log retrieval operation binding the contract event 0x38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a6.
//
// Solidity: event CallFailure(string _revertReason)
func (_Sfc *SfcFilterer) FilterCallFailure(opts *bind.FilterOpts) (*SfcCallFailureIterator, error) {

	logs, sub, err := _Sfc.contract.FilterLogs(opts, "CallFailure")
	if err != nil {
		return nil, err
	}
	return &SfcCallFailureIterator{contract: _Sfc.contract, event: "CallFailure", logs: logs, sub: sub}, nil
}

// WatchCallFailure is a free log subscription operation binding the contract event 0x38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a6.
//
// Solidity: event CallFailure(string _revertReason)
func (_Sfc *SfcFilterer) WatchCallFailure(opts *bind.WatchOpts, sink chan<- *SfcCallFailure) (event.Subscription, error) {

	logs, sub, err := _Sfc.contract.WatchLogs(opts, "CallFailure")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcCallFailure)
				if err := _Sfc.contract.UnpackLog(event, "CallFailure", log); err != nil {
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

// ParseCallFailure is a log parse operation binding the contract event 0x38e7ccc4b02b2da681f96e62aef89b5c6d4115f501f8d42430bb2f5f2fa981a6.
//
// Solidity: event CallFailure(string _revertReason)
func (_Sfc *SfcFilterer) ParseCallFailure(log types.Log) (*SfcCallFailure, error) {
	event := new(SfcCallFailure)
	if err := _Sfc.contract.UnpackLog(event, "CallFailure", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcCrossCallIterator is returned from FilterCrossCall and is used to iterate over the raw logs and unpacked data for CrossCall events raised by the Sfc contract.
type SfcCrossCallIterator struct {
	Event *SfcCrossCall // Event containing the contract specifics and raw log

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
func (it *SfcCrossCallIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcCrossCall)
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
		it.Event = new(SfcCrossCall)
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
func (it *SfcCrossCallIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcCrossCallIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcCrossCall represents a CrossCall event raised by the Sfc contract.
type SfcCrossCall struct {
	TxId             [32]byte
	Timestamp        *big.Int
	Caller           common.Address
	DestBcId         *big.Int
	DestContract     common.Address
	DestFunctionCall []byte
	Raw              types.Log // Blockchain specific contextual infos
}

// FilterCrossCall is a free log retrieval operation binding the contract event 0x59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27.
//
// Solidity: event CrossCall(bytes32 _txId, uint256 _timestamp, address _caller, uint256 _destBcId, address _destContract, bytes _destFunctionCall)
func (_Sfc *SfcFilterer) FilterCrossCall(opts *bind.FilterOpts) (*SfcCrossCallIterator, error) {

	logs, sub, err := _Sfc.contract.FilterLogs(opts, "CrossCall")
	if err != nil {
		return nil, err
	}
	return &SfcCrossCallIterator{contract: _Sfc.contract, event: "CrossCall", logs: logs, sub: sub}, nil
}

// WatchCrossCall is a free log subscription operation binding the contract event 0x59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27.
//
// Solidity: event CrossCall(bytes32 _txId, uint256 _timestamp, address _caller, uint256 _destBcId, address _destContract, bytes _destFunctionCall)
func (_Sfc *SfcFilterer) WatchCrossCall(opts *bind.WatchOpts, sink chan<- *SfcCrossCall) (event.Subscription, error) {

	logs, sub, err := _Sfc.contract.WatchLogs(opts, "CrossCall")
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcCrossCall)
				if err := _Sfc.contract.UnpackLog(event, "CrossCall", log); err != nil {
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

// ParseCrossCall is a log parse operation binding the contract event 0x59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad27.
//
// Solidity: event CrossCall(bytes32 _txId, uint256 _timestamp, address _caller, uint256 _destBcId, address _destContract, bytes _destFunctionCall)
func (_Sfc *SfcFilterer) ParseCrossCall(log types.Log) (*SfcCrossCall, error) {
	event := new(SfcCrossCall)
	if err := _Sfc.contract.UnpackLog(event, "CrossCall", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}

// SfcOwnershipTransferredIterator is returned from FilterOwnershipTransferred and is used to iterate over the raw logs and unpacked data for OwnershipTransferred events raised by the Sfc contract.
type SfcOwnershipTransferredIterator struct {
	Event *SfcOwnershipTransferred // Event containing the contract specifics and raw log

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
func (it *SfcOwnershipTransferredIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(SfcOwnershipTransferred)
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
		it.Event = new(SfcOwnershipTransferred)
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
func (it *SfcOwnershipTransferredIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *SfcOwnershipTransferredIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// SfcOwnershipTransferred represents a OwnershipTransferred event raised by the Sfc contract.
type SfcOwnershipTransferred struct {
	PreviousOwner common.Address
	NewOwner      common.Address
	Raw           types.Log // Blockchain specific contextual infos
}

// FilterOwnershipTransferred is a free log retrieval operation binding the contract event 0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0.
//
// Solidity: event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)
func (_Sfc *SfcFilterer) FilterOwnershipTransferred(opts *bind.FilterOpts, previousOwner []common.Address, newOwner []common.Address) (*SfcOwnershipTransferredIterator, error) {

	var previousOwnerRule []interface{}
	for _, previousOwnerItem := range previousOwner {
		previousOwnerRule = append(previousOwnerRule, previousOwnerItem)
	}
	var newOwnerRule []interface{}
	for _, newOwnerItem := range newOwner {
		newOwnerRule = append(newOwnerRule, newOwnerItem)
	}

	logs, sub, err := _Sfc.contract.FilterLogs(opts, "OwnershipTransferred", previousOwnerRule, newOwnerRule)
	if err != nil {
		return nil, err
	}
	return &SfcOwnershipTransferredIterator{contract: _Sfc.contract, event: "OwnershipTransferred", logs: logs, sub: sub}, nil
}

// WatchOwnershipTransferred is a free log subscription operation binding the contract event 0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0.
//
// Solidity: event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)
func (_Sfc *SfcFilterer) WatchOwnershipTransferred(opts *bind.WatchOpts, sink chan<- *SfcOwnershipTransferred, previousOwner []common.Address, newOwner []common.Address) (event.Subscription, error) {

	var previousOwnerRule []interface{}
	for _, previousOwnerItem := range previousOwner {
		previousOwnerRule = append(previousOwnerRule, previousOwnerItem)
	}
	var newOwnerRule []interface{}
	for _, newOwnerItem := range newOwner {
		newOwnerRule = append(newOwnerRule, newOwnerItem)
	}

	logs, sub, err := _Sfc.contract.WatchLogs(opts, "OwnershipTransferred", previousOwnerRule, newOwnerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(SfcOwnershipTransferred)
				if err := _Sfc.contract.UnpackLog(event, "OwnershipTransferred", log); err != nil {
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

// ParseOwnershipTransferred is a log parse operation binding the contract event 0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0.
//
// Solidity: event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)
func (_Sfc *SfcFilterer) ParseOwnershipTransferred(log types.Log) (*SfcOwnershipTransferred, error) {
	event := new(SfcOwnershipTransferred)
	if err := _Sfc.contract.UnpackLog(event, "OwnershipTransferred", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
