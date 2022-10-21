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

// RegistrarMetaData contains all meta data concerning the Registrar contract.
var RegistrarMetaData = &bind.MetaData{
	ABI: "[{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"ECDSA_SIGNATURE\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_signer\",\"type\":\"address\"}],\"name\":\"addSigner\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_signer\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_newSigningThreshold\",\"type\":\"uint256\"}],\"name\":\"addSignerSetThreshold\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address[]\",\"name\":\"_signers\",\"type\":\"address[]\"},{\"internalType\":\"uint256\",\"name\":\"_newSigningThreshold\",\"type\":\"uint256\"}],\"name\":\"addSignersSetThreshold\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"}],\"name\":\"getSigningThreshold\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_mightBeSigner\",\"type\":\"address\"}],\"name\":\"isSigner\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"}],\"name\":\"numSigners\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_signer\",\"type\":\"address\"}],\"name\":\"removeSigner\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_signer\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_newSigningThreshold\",\"type\":\"uint256\"}],\"name\":\"removeSignerSetThreshold\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_bcId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_newSigningThreshold\",\"type\":\"uint256\"}],\"name\":\"setThreshold\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_plainText\",\"type\":\"bytes\"}],\"name\":\"verify\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"_signatures\",\"type\":\"bytes\"},{\"internalType\":\"bytes\",\"name\":\"_plainText\",\"type\":\"bytes\"}],\"name\":\"verifyAndCheckThreshold\",\"outputs\":[],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50600080546001600160a01b031916339081178255604051909182917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350611389806100616000396000f3fe608060405234801561001057600080fd5b50600436106100f45760003560e01c80638da5cb5b11610097578063d068a7c211610066578063d068a7c214610223578063d4c0d34d1461022b578063f2fde38b1461023e578063f5e232ea1461025157600080fd5b80638da5cb5b146101b45780639a8bb0c4146101cf578063a64ce199146101e2578063b9c362091461021057600080fd5b806348bcbd2d116100d357806348bcbd2d146101345780634904694014610186578063715018a6146101995780638d7678fd146101a157600080fd5b8062ab2414146100f957806315a098251461010e5780633156d37c14610121575b600080fd5b61010c610107366004610e3f565b610275565b005b61010c61011c366004610e74565b6102f6565b61010c61012f366004610e74565b6103c9565b610171610142366004610e74565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff1692915050565b60405190151581526020015b60405180910390f35b61010c610194366004610ee9565b6104b4565b61010c61053a565b61010c6101af366004610f63565b6105ae565b6000546040516001600160a01b03909116815260200161017d565b6101716101dd366004610ee9565b61066c565b6102026101f0366004610fe8565b60009081526001602052604090205490565b60405190815260200161017d565b61010c61021e366004611001565b61069d565b610202600181565b61010c610239366004610e3f565b6106ec565b61010c61024c366004611023565b61073d565b61020261025f366004610fe8565b6000908152600160208190526040909120015490565b6000546001600160a01b031633146102a85760405162461bcd60e51b815260040161029f90611045565b60405180910390fd5b6102b28383610827565b600083815260016020819052604082208101546102ce91611090565b60008581526001602081905260409091200181905590506102f08482846108c9565b50505050565b6000546001600160a01b031633146103205760405162461bcd60e51b815260040161029f90611045565b6000828152600160205260409020546103995760405162461bcd60e51b815260206004820152603560248201527f43616e206e6f7420616464207369676e657220666f7220626c6f636b636861696044820152741b881dda5d1a081e995c9bc81d1a1c995cda1bdb19605a1b606482015260840161029f565b6103a38282610827565b60008281526001602081905260408220018054916103c0836110a8565b91905055505050565b6000546001600160a01b031633146103f35760405162461bcd60e51b815260040161029f90611045565b6103fd828261098a565b6000828152600160208190526040822081015461041a91906110c3565b6000848152600160205260409020549091508110156104995760405162461bcd60e51b815260206004820152603560248201527f50726f706f736564206e6577206e756d626572206f66207369676e65727320696044820152741cc81b195cdcc81d1a185b881d1a1c995cda1bdb19605a1b606482015260840161029f565b60009283526001602081905260409093209092019190915550565b60006104c08585610a28565b6000878152600160209081526040909120549082015151919250111561051d5760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b604482015260640161029f565b61052681610aae565b61053286828585610bac565b505050505050565b6000546001600160a01b031633146105645760405162461bcd60e51b815260040161029f90611045565b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000546001600160a01b031633146105d85760405162461bcd60e51b815260040161029f90611045565b60005b8281101561062557610613858585848181106105f9576105f96110da565b905060200201602081019061060e9190611023565b610827565b8061061d816110a8565b9150506105db565b50600084815260016020819052604082200154610643908490611090565b60008681526001602081905260409091200181905590506106658582846108c9565b5050505050565b6000806106798686610a28565b905061068481610aae565b61069087828686610bac565b5060019695505050505050565b6000546001600160a01b031633146106c75760405162461bcd60e51b815260040161029f90611045565b6106e8826001600085815260200190815260200160002060010154836108c9565b5050565b6000546001600160a01b031633146107165760405162461bcd60e51b815260040161029f90611045565b610720838361098a565b600083815260016020819052604082208101546102ce91906110c3565b6000546001600160a01b031633146107675760405162461bcd60e51b815260040161029f90611045565b6001600160a01b0381166107cc5760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b606482015260840161029f565b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff16156108955760405162461bcd60e51b81526020600482015260156024820152745369676e657220616c72656164792065786973747360581b604482015260640161029f565b60009182526001602081815260408085206001600160a01b039094168552600290930190529120805460ff19169091179055565b808210156109275760405162461bcd60e51b815260206004820152602560248201527f4e756d626572206f66207369676e657273206c657373207468616e20746872656044820152641cda1bdb1960da1b606482015260840161029f565b806109745760405162461bcd60e51b815260206004820152601960248201527f5468726573686f6c642063616e206e6f74206265207a65726f00000000000000604482015260640161029f565b6000928352600160205260409092209190915550565b60008281526001602090815260408083206001600160a01b038516845260020190915290205460ff166109f75760405162461bcd60e51b815260206004820152601560248201527414da59db995c88191bd95cc81b9bdd08195e1a5cdd605a1b604482015260640161029f565b60009182526001602090815260408084206001600160a01b039093168452600290920190529020805460ff19169055565b6040805180820190915260008152606060208201526000610a4b83850185611001565b91505060018114610a975760405162461bcd60e51b81526020600482015260166024820152755369676e617475726520756e6b6e6f776e207479706560501b604482015260640161029f565b6000610aa584860186611183565b95945050505050565b60005b6001826020015151610ac391906110c3565b8110156106e857600082602001518281518110610ae257610ae26110da565b60200260200101516000015190506000826001610aff9190611090565b90505b836020015151811015610b975783602001518181518110610b2557610b256110da565b6020026020010151600001516001600160a01b0316826001600160a01b03161415610b855760405162461bcd60e51b815260206004820152601060248201526f223ab83634b1b0ba329039b4b3b732b960811b604482015260640161029f565b80610b8f816110a8565b915050610b02565b50508080610ba4906110a8565b915050610ab1565b60005b8360200151518110156106655760016000868152602001908152602001600020600201600085602001518381518110610bea57610bea6110da565b602090810291909101810151516001600160a01b031682528101919091526040016000205460ff16610c705760405162461bcd60e51b815260206004820152602960248201527f5369676e6572206e6f74207265676973746572656420666f72207468697320626044820152683637b1b5b1b430b4b760b91b606482015260840161029f565b610d0284602001518281518110610c8957610c896110da565b602002602001015160000151848487602001518581518110610cad57610cad6110da565b60200260200101516020015188602001518681518110610ccf57610ccf6110da565b60200260200101516040015189602001518781518110610cf157610cf16110da565b602002602001015160600151610d60565b610d4e5760405162461bcd60e51b815260206004820152601860248201527f5369676e617475726520646964206e6f74207665726966790000000000000000604482015260640161029f565b80610d58816110a8565b915050610baf565b6000808686604051610d73929190611343565b604051809103902090508260ff16601b14158015610d9557508260ff16601c14155b15610da4576000915050610e19565b60408051600081526020810180835283905260ff851691810191909152606081018690526080810185905260019060a0016020604051602081039080840390855afa158015610df7573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b9695505050505050565b80356001600160a01b0381168114610e3a57600080fd5b919050565b600080600060608486031215610e5457600080fd5b83359250610e6460208501610e23565b9150604084013590509250925092565b60008060408385031215610e8757600080fd5b82359150610e9760208401610e23565b90509250929050565b60008083601f840112610eb257600080fd5b50813567ffffffffffffffff811115610eca57600080fd5b602083019150836020828501011115610ee257600080fd5b9250929050565b600080600080600060608688031215610f0157600080fd5b85359450602086013567ffffffffffffffff80821115610f2057600080fd5b610f2c89838a01610ea0565b90965094506040880135915080821115610f4557600080fd5b50610f5288828901610ea0565b969995985093965092949392505050565b60008060008060608587031215610f7957600080fd5b84359350602085013567ffffffffffffffff80821115610f9857600080fd5b818701915087601f830112610fac57600080fd5b813581811115610fbb57600080fd5b8860208260051b8501011115610fd057600080fd5b95986020929092019750949560400135945092505050565b600060208284031215610ffa57600080fd5b5035919050565b6000806040838503121561101457600080fd5b50508035926020909101359150565b60006020828403121561103557600080fd5b61103e82610e23565b9392505050565b6020808252818101527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604082015260600190565b634e487b7160e01b600052601160045260246000fd5b600082198211156110a3576110a361107a565b500190565b60006000198214156110bc576110bc61107a565b5060010190565b6000828210156110d5576110d561107a565b500390565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fd5b6040805190810167ffffffffffffffff81118282101715611129576111296110f0565b60405290565b60405160a0810167ffffffffffffffff81118282101715611129576111296110f0565b604051601f8201601f1916810167ffffffffffffffff8111828210171561117b5761117b6110f0565b604052919050565b6000602080838503121561119657600080fd5b823567ffffffffffffffff808211156111ae57600080fd5b90840190604082870312156111c257600080fd5b6111ca611106565b8235815283830135828111156111df57600080fd5b80840193505086601f8401126111f457600080fd5b823582811115611206576112066110f0565b8060051b611215868201611152565b918252848101860191868101908a84111561122f57600080fd5b87870192505b8383101561132f5782358681111561124c57600080fd5b8701601f1960a0828e038201121561126357600080fd5b61126b61112f565b6112768b8401610e23565b815260408301358b82015260608301356040820152608083013560ff8116811461129f57600080fd5b606082015260a0830135898111156112b657600080fd5b8084019350508d603f8401126112cb57600080fd5b8a830135898111156112df576112df6110f0565b6112ef8c84601f84011601611152565b92508083528e604082860101111561130657600080fd5b80604085018d85013760009083018c015260808101919091528352509187019190870190611235565b968401969096525090979650505050505050565b818382376000910190815291905056fea26469706673582212200b96eeac09fe503460d491b04d8580c5c58f7c0681d00cc3e0a3029039090bf264736f6c63430008090033",
}

// RegistrarABI is the input ABI used to generate the binding from.
// Deprecated: Use RegistrarMetaData.ABI instead.
var RegistrarABI = RegistrarMetaData.ABI

// RegistrarBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use RegistrarMetaData.Bin instead.
var RegistrarBin = RegistrarMetaData.Bin

// DeployRegistrar deploys a new Ethereum contract, binding an instance of Registrar to it.
func DeployRegistrar(auth *bind.TransactOpts, backend bind.ContractBackend) (common.Address, *types.Transaction, *Registrar, error) {
	parsed, err := RegistrarMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(RegistrarBin), backend)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &Registrar{RegistrarCaller: RegistrarCaller{contract: contract}, RegistrarTransactor: RegistrarTransactor{contract: contract}, RegistrarFilterer: RegistrarFilterer{contract: contract}}, nil
}

// Registrar is an auto generated Go binding around an Ethereum contract.
type Registrar struct {
	RegistrarCaller     // Read-only binding to the contract
	RegistrarTransactor // Write-only binding to the contract
	RegistrarFilterer   // Log filterer for contract events
}

// RegistrarCaller is an auto generated read-only Go binding around an Ethereum contract.
type RegistrarCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// RegistrarTransactor is an auto generated write-only Go binding around an Ethereum contract.
type RegistrarTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// RegistrarFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type RegistrarFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// RegistrarSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type RegistrarSession struct {
	Contract     *Registrar        // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// RegistrarCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type RegistrarCallerSession struct {
	Contract *RegistrarCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts    // Call options to use throughout this session
}

// RegistrarTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type RegistrarTransactorSession struct {
	Contract     *RegistrarTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts    // Transaction auth options to use throughout this session
}

// RegistrarRaw is an auto generated low-level Go binding around an Ethereum contract.
type RegistrarRaw struct {
	Contract *Registrar // Generic contract binding to access the raw methods on
}

// RegistrarCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type RegistrarCallerRaw struct {
	Contract *RegistrarCaller // Generic read-only contract binding to access the raw methods on
}

// RegistrarTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type RegistrarTransactorRaw struct {
	Contract *RegistrarTransactor // Generic write-only contract binding to access the raw methods on
}

// NewRegistrar creates a new instance of Registrar, bound to a specific deployed contract.
func NewRegistrar(address common.Address, backend bind.ContractBackend) (*Registrar, error) {
	contract, err := bindRegistrar(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &Registrar{RegistrarCaller: RegistrarCaller{contract: contract}, RegistrarTransactor: RegistrarTransactor{contract: contract}, RegistrarFilterer: RegistrarFilterer{contract: contract}}, nil
}

// NewRegistrarCaller creates a new read-only instance of Registrar, bound to a specific deployed contract.
func NewRegistrarCaller(address common.Address, caller bind.ContractCaller) (*RegistrarCaller, error) {
	contract, err := bindRegistrar(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &RegistrarCaller{contract: contract}, nil
}

// NewRegistrarTransactor creates a new write-only instance of Registrar, bound to a specific deployed contract.
func NewRegistrarTransactor(address common.Address, transactor bind.ContractTransactor) (*RegistrarTransactor, error) {
	contract, err := bindRegistrar(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &RegistrarTransactor{contract: contract}, nil
}

// NewRegistrarFilterer creates a new log filterer instance of Registrar, bound to a specific deployed contract.
func NewRegistrarFilterer(address common.Address, filterer bind.ContractFilterer) (*RegistrarFilterer, error) {
	contract, err := bindRegistrar(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &RegistrarFilterer{contract: contract}, nil
}

// bindRegistrar binds a generic wrapper to an already deployed contract.
func bindRegistrar(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(RegistrarABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Registrar *RegistrarRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Registrar.Contract.RegistrarCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Registrar *RegistrarRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Registrar.Contract.RegistrarTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Registrar *RegistrarRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Registrar.Contract.RegistrarTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Registrar *RegistrarCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Registrar.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Registrar *RegistrarTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Registrar.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Registrar *RegistrarTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Registrar.Contract.contract.Transact(opts, method, params...)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_Registrar *RegistrarCaller) ECDSASIGNATURE(opts *bind.CallOpts) (*big.Int, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "ECDSA_SIGNATURE")

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_Registrar *RegistrarSession) ECDSASIGNATURE() (*big.Int, error) {
	return _Registrar.Contract.ECDSASIGNATURE(&_Registrar.CallOpts)
}

// ECDSASIGNATURE is a free data retrieval call binding the contract method 0xd068a7c2.
//
// Solidity: function ECDSA_SIGNATURE() view returns(uint256)
func (_Registrar *RegistrarCallerSession) ECDSASIGNATURE() (*big.Int, error) {
	return _Registrar.Contract.ECDSASIGNATURE(&_Registrar.CallOpts)
}

// GetSigningThreshold is a free data retrieval call binding the contract method 0xa64ce199.
//
// Solidity: function getSigningThreshold(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarCaller) GetSigningThreshold(opts *bind.CallOpts, _blockchainId *big.Int) (*big.Int, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "getSigningThreshold", _blockchainId)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// GetSigningThreshold is a free data retrieval call binding the contract method 0xa64ce199.
//
// Solidity: function getSigningThreshold(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarSession) GetSigningThreshold(_blockchainId *big.Int) (*big.Int, error) {
	return _Registrar.Contract.GetSigningThreshold(&_Registrar.CallOpts, _blockchainId)
}

// GetSigningThreshold is a free data retrieval call binding the contract method 0xa64ce199.
//
// Solidity: function getSigningThreshold(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarCallerSession) GetSigningThreshold(_blockchainId *big.Int) (*big.Int, error) {
	return _Registrar.Contract.GetSigningThreshold(&_Registrar.CallOpts, _blockchainId)
}

// IsSigner is a free data retrieval call binding the contract method 0x48bcbd2d.
//
// Solidity: function isSigner(uint256 _blockchainId, address _mightBeSigner) view returns(bool)
func (_Registrar *RegistrarCaller) IsSigner(opts *bind.CallOpts, _blockchainId *big.Int, _mightBeSigner common.Address) (bool, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "isSigner", _blockchainId, _mightBeSigner)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// IsSigner is a free data retrieval call binding the contract method 0x48bcbd2d.
//
// Solidity: function isSigner(uint256 _blockchainId, address _mightBeSigner) view returns(bool)
func (_Registrar *RegistrarSession) IsSigner(_blockchainId *big.Int, _mightBeSigner common.Address) (bool, error) {
	return _Registrar.Contract.IsSigner(&_Registrar.CallOpts, _blockchainId, _mightBeSigner)
}

// IsSigner is a free data retrieval call binding the contract method 0x48bcbd2d.
//
// Solidity: function isSigner(uint256 _blockchainId, address _mightBeSigner) view returns(bool)
func (_Registrar *RegistrarCallerSession) IsSigner(_blockchainId *big.Int, _mightBeSigner common.Address) (bool, error) {
	return _Registrar.Contract.IsSigner(&_Registrar.CallOpts, _blockchainId, _mightBeSigner)
}

// NumSigners is a free data retrieval call binding the contract method 0xf5e232ea.
//
// Solidity: function numSigners(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarCaller) NumSigners(opts *bind.CallOpts, _blockchainId *big.Int) (*big.Int, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "numSigners", _blockchainId)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// NumSigners is a free data retrieval call binding the contract method 0xf5e232ea.
//
// Solidity: function numSigners(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarSession) NumSigners(_blockchainId *big.Int) (*big.Int, error) {
	return _Registrar.Contract.NumSigners(&_Registrar.CallOpts, _blockchainId)
}

// NumSigners is a free data retrieval call binding the contract method 0xf5e232ea.
//
// Solidity: function numSigners(uint256 _blockchainId) view returns(uint256)
func (_Registrar *RegistrarCallerSession) NumSigners(_blockchainId *big.Int) (*big.Int, error) {
	return _Registrar.Contract.NumSigners(&_Registrar.CallOpts, _blockchainId)
}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Registrar *RegistrarCaller) Owner(opts *bind.CallOpts) (common.Address, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "owner")

	if err != nil {
		return *new(common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new(common.Address)).(*common.Address)

	return out0, err

}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Registrar *RegistrarSession) Owner() (common.Address, error) {
	return _Registrar.Contract.Owner(&_Registrar.CallOpts)
}

// Owner is a free data retrieval call binding the contract method 0x8da5cb5b.
//
// Solidity: function owner() view returns(address)
func (_Registrar *RegistrarCallerSession) Owner() (common.Address, error) {
	return _Registrar.Contract.Owner(&_Registrar.CallOpts)
}

// Verify is a free data retrieval call binding the contract method 0x9a8bb0c4.
//
// Solidity: function verify(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns(bool)
func (_Registrar *RegistrarCaller) Verify(opts *bind.CallOpts, _blockchainId *big.Int, _signatures []byte, _plainText []byte) (bool, error) {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "verify", _blockchainId, _signatures, _plainText)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// Verify is a free data retrieval call binding the contract method 0x9a8bb0c4.
//
// Solidity: function verify(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns(bool)
func (_Registrar *RegistrarSession) Verify(_blockchainId *big.Int, _signatures []byte, _plainText []byte) (bool, error) {
	return _Registrar.Contract.Verify(&_Registrar.CallOpts, _blockchainId, _signatures, _plainText)
}

// Verify is a free data retrieval call binding the contract method 0x9a8bb0c4.
//
// Solidity: function verify(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns(bool)
func (_Registrar *RegistrarCallerSession) Verify(_blockchainId *big.Int, _signatures []byte, _plainText []byte) (bool, error) {
	return _Registrar.Contract.Verify(&_Registrar.CallOpts, _blockchainId, _signatures, _plainText)
}

// VerifyAndCheckThreshold is a free data retrieval call binding the contract method 0x49046940.
//
// Solidity: function verifyAndCheckThreshold(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns()
func (_Registrar *RegistrarCaller) VerifyAndCheckThreshold(opts *bind.CallOpts, _blockchainId *big.Int, _signatures []byte, _plainText []byte) error {
	var out []interface{}
	err := _Registrar.contract.Call(opts, &out, "verifyAndCheckThreshold", _blockchainId, _signatures, _plainText)

	if err != nil {
		return err
	}

	return err

}

// VerifyAndCheckThreshold is a free data retrieval call binding the contract method 0x49046940.
//
// Solidity: function verifyAndCheckThreshold(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns()
func (_Registrar *RegistrarSession) VerifyAndCheckThreshold(_blockchainId *big.Int, _signatures []byte, _plainText []byte) error {
	return _Registrar.Contract.VerifyAndCheckThreshold(&_Registrar.CallOpts, _blockchainId, _signatures, _plainText)
}

// VerifyAndCheckThreshold is a free data retrieval call binding the contract method 0x49046940.
//
// Solidity: function verifyAndCheckThreshold(uint256 _blockchainId, bytes _signatures, bytes _plainText) view returns()
func (_Registrar *RegistrarCallerSession) VerifyAndCheckThreshold(_blockchainId *big.Int, _signatures []byte, _plainText []byte) error {
	return _Registrar.Contract.VerifyAndCheckThreshold(&_Registrar.CallOpts, _blockchainId, _signatures, _plainText)
}

// AddSigner is a paid mutator transaction binding the contract method 0x15a09825.
//
// Solidity: function addSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarTransactor) AddSigner(opts *bind.TransactOpts, _bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "addSigner", _bcId, _signer)
}

// AddSigner is a paid mutator transaction binding the contract method 0x15a09825.
//
// Solidity: function addSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarSession) AddSigner(_bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.AddSigner(&_Registrar.TransactOpts, _bcId, _signer)
}

// AddSigner is a paid mutator transaction binding the contract method 0x15a09825.
//
// Solidity: function addSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarTransactorSession) AddSigner(_bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.AddSigner(&_Registrar.TransactOpts, _bcId, _signer)
}

// AddSignerSetThreshold is a paid mutator transaction binding the contract method 0x00ab2414.
//
// Solidity: function addSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactor) AddSignerSetThreshold(opts *bind.TransactOpts, _bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "addSignerSetThreshold", _bcId, _signer, _newSigningThreshold)
}

// AddSignerSetThreshold is a paid mutator transaction binding the contract method 0x00ab2414.
//
// Solidity: function addSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarSession) AddSignerSetThreshold(_bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.AddSignerSetThreshold(&_Registrar.TransactOpts, _bcId, _signer, _newSigningThreshold)
}

// AddSignerSetThreshold is a paid mutator transaction binding the contract method 0x00ab2414.
//
// Solidity: function addSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactorSession) AddSignerSetThreshold(_bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.AddSignerSetThreshold(&_Registrar.TransactOpts, _bcId, _signer, _newSigningThreshold)
}

// AddSignersSetThreshold is a paid mutator transaction binding the contract method 0x8d7678fd.
//
// Solidity: function addSignersSetThreshold(uint256 _bcId, address[] _signers, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactor) AddSignersSetThreshold(opts *bind.TransactOpts, _bcId *big.Int, _signers []common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "addSignersSetThreshold", _bcId, _signers, _newSigningThreshold)
}

// AddSignersSetThreshold is a paid mutator transaction binding the contract method 0x8d7678fd.
//
// Solidity: function addSignersSetThreshold(uint256 _bcId, address[] _signers, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarSession) AddSignersSetThreshold(_bcId *big.Int, _signers []common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.AddSignersSetThreshold(&_Registrar.TransactOpts, _bcId, _signers, _newSigningThreshold)
}

// AddSignersSetThreshold is a paid mutator transaction binding the contract method 0x8d7678fd.
//
// Solidity: function addSignersSetThreshold(uint256 _bcId, address[] _signers, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactorSession) AddSignersSetThreshold(_bcId *big.Int, _signers []common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.AddSignersSetThreshold(&_Registrar.TransactOpts, _bcId, _signers, _newSigningThreshold)
}

// RemoveSigner is a paid mutator transaction binding the contract method 0x3156d37c.
//
// Solidity: function removeSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarTransactor) RemoveSigner(opts *bind.TransactOpts, _bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "removeSigner", _bcId, _signer)
}

// RemoveSigner is a paid mutator transaction binding the contract method 0x3156d37c.
//
// Solidity: function removeSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarSession) RemoveSigner(_bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.RemoveSigner(&_Registrar.TransactOpts, _bcId, _signer)
}

// RemoveSigner is a paid mutator transaction binding the contract method 0x3156d37c.
//
// Solidity: function removeSigner(uint256 _bcId, address _signer) returns()
func (_Registrar *RegistrarTransactorSession) RemoveSigner(_bcId *big.Int, _signer common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.RemoveSigner(&_Registrar.TransactOpts, _bcId, _signer)
}

// RemoveSignerSetThreshold is a paid mutator transaction binding the contract method 0xd4c0d34d.
//
// Solidity: function removeSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactor) RemoveSignerSetThreshold(opts *bind.TransactOpts, _bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "removeSignerSetThreshold", _bcId, _signer, _newSigningThreshold)
}

// RemoveSignerSetThreshold is a paid mutator transaction binding the contract method 0xd4c0d34d.
//
// Solidity: function removeSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarSession) RemoveSignerSetThreshold(_bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.RemoveSignerSetThreshold(&_Registrar.TransactOpts, _bcId, _signer, _newSigningThreshold)
}

// RemoveSignerSetThreshold is a paid mutator transaction binding the contract method 0xd4c0d34d.
//
// Solidity: function removeSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactorSession) RemoveSignerSetThreshold(_bcId *big.Int, _signer common.Address, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.RemoveSignerSetThreshold(&_Registrar.TransactOpts, _bcId, _signer, _newSigningThreshold)
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Registrar *RegistrarTransactor) RenounceOwnership(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "renounceOwnership")
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Registrar *RegistrarSession) RenounceOwnership() (*types.Transaction, error) {
	return _Registrar.Contract.RenounceOwnership(&_Registrar.TransactOpts)
}

// RenounceOwnership is a paid mutator transaction binding the contract method 0x715018a6.
//
// Solidity: function renounceOwnership() returns()
func (_Registrar *RegistrarTransactorSession) RenounceOwnership() (*types.Transaction, error) {
	return _Registrar.Contract.RenounceOwnership(&_Registrar.TransactOpts)
}

// SetThreshold is a paid mutator transaction binding the contract method 0xb9c36209.
//
// Solidity: function setThreshold(uint256 _bcId, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactor) SetThreshold(opts *bind.TransactOpts, _bcId *big.Int, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "setThreshold", _bcId, _newSigningThreshold)
}

// SetThreshold is a paid mutator transaction binding the contract method 0xb9c36209.
//
// Solidity: function setThreshold(uint256 _bcId, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarSession) SetThreshold(_bcId *big.Int, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.SetThreshold(&_Registrar.TransactOpts, _bcId, _newSigningThreshold)
}

// SetThreshold is a paid mutator transaction binding the contract method 0xb9c36209.
//
// Solidity: function setThreshold(uint256 _bcId, uint256 _newSigningThreshold) returns()
func (_Registrar *RegistrarTransactorSession) SetThreshold(_bcId *big.Int, _newSigningThreshold *big.Int) (*types.Transaction, error) {
	return _Registrar.Contract.SetThreshold(&_Registrar.TransactOpts, _bcId, _newSigningThreshold)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Registrar *RegistrarTransactor) TransferOwnership(opts *bind.TransactOpts, newOwner common.Address) (*types.Transaction, error) {
	return _Registrar.contract.Transact(opts, "transferOwnership", newOwner)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Registrar *RegistrarSession) TransferOwnership(newOwner common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.TransferOwnership(&_Registrar.TransactOpts, newOwner)
}

// TransferOwnership is a paid mutator transaction binding the contract method 0xf2fde38b.
//
// Solidity: function transferOwnership(address newOwner) returns()
func (_Registrar *RegistrarTransactorSession) TransferOwnership(newOwner common.Address) (*types.Transaction, error) {
	return _Registrar.Contract.TransferOwnership(&_Registrar.TransactOpts, newOwner)
}

// RegistrarOwnershipTransferredIterator is returned from FilterOwnershipTransferred and is used to iterate over the raw logs and unpacked data for OwnershipTransferred events raised by the Registrar contract.
type RegistrarOwnershipTransferredIterator struct {
	Event *RegistrarOwnershipTransferred // Event containing the contract specifics and raw log

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
func (it *RegistrarOwnershipTransferredIterator) Next() bool {
	// If the iterator failed, stop iterating
	if it.fail != nil {
		return false
	}
	// If the iterator completed, deliver directly whatever's available
	if it.done {
		select {
		case log := <-it.logs:
			it.Event = new(RegistrarOwnershipTransferred)
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
		it.Event = new(RegistrarOwnershipTransferred)
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
func (it *RegistrarOwnershipTransferredIterator) Error() error {
	return it.fail
}

// Close terminates the iteration process, releasing any pending underlying
// resources.
func (it *RegistrarOwnershipTransferredIterator) Close() error {
	it.sub.Unsubscribe()
	return nil
}

// RegistrarOwnershipTransferred represents a OwnershipTransferred event raised by the Registrar contract.
type RegistrarOwnershipTransferred struct {
	PreviousOwner common.Address
	NewOwner      common.Address
	Raw           types.Log // Blockchain specific contextual infos
}

// FilterOwnershipTransferred is a free log retrieval operation binding the contract event 0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0.
//
// Solidity: event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)
func (_Registrar *RegistrarFilterer) FilterOwnershipTransferred(opts *bind.FilterOpts, previousOwner []common.Address, newOwner []common.Address) (*RegistrarOwnershipTransferredIterator, error) {

	var previousOwnerRule []interface{}
	for _, previousOwnerItem := range previousOwner {
		previousOwnerRule = append(previousOwnerRule, previousOwnerItem)
	}
	var newOwnerRule []interface{}
	for _, newOwnerItem := range newOwner {
		newOwnerRule = append(newOwnerRule, newOwnerItem)
	}

	logs, sub, err := _Registrar.contract.FilterLogs(opts, "OwnershipTransferred", previousOwnerRule, newOwnerRule)
	if err != nil {
		return nil, err
	}
	return &RegistrarOwnershipTransferredIterator{contract: _Registrar.contract, event: "OwnershipTransferred", logs: logs, sub: sub}, nil
}

// WatchOwnershipTransferred is a free log subscription operation binding the contract event 0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0.
//
// Solidity: event OwnershipTransferred(address indexed previousOwner, address indexed newOwner)
func (_Registrar *RegistrarFilterer) WatchOwnershipTransferred(opts *bind.WatchOpts, sink chan<- *RegistrarOwnershipTransferred, previousOwner []common.Address, newOwner []common.Address) (event.Subscription, error) {

	var previousOwnerRule []interface{}
	for _, previousOwnerItem := range previousOwner {
		previousOwnerRule = append(previousOwnerRule, previousOwnerItem)
	}
	var newOwnerRule []interface{}
	for _, newOwnerItem := range newOwner {
		newOwnerRule = append(newOwnerRule, newOwnerItem)
	}

	logs, sub, err := _Registrar.contract.WatchLogs(opts, "OwnershipTransferred", previousOwnerRule, newOwnerRule)
	if err != nil {
		return nil, err
	}
	return event.NewSubscription(func(quit <-chan struct{}) error {
		defer sub.Unsubscribe()
		for {
			select {
			case log := <-logs:
				// New log arrived, parse the event and forward to the user
				event := new(RegistrarOwnershipTransferred)
				if err := _Registrar.contract.UnpackLog(event, "OwnershipTransferred", log); err != nil {
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
func (_Registrar *RegistrarFilterer) ParseOwnershipTransferred(log types.Log) (*RegistrarOwnershipTransferred, error) {
	event := new(RegistrarOwnershipTransferred)
	if err := _Registrar.contract.UnpackLog(event, "OwnershipTransferred", log); err != nil {
		return nil, err
	}
	event.Raw = log
	return event, nil
}
