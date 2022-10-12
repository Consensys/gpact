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

// HotelMetaData contains all meta data concerning the Hotel contract.
var HotelMetaData = &bind.MetaData{
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_erc20\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_cbc\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_travelAgencyContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spendingAccount\",\"type\":\"address\"}],\"name\":\"addApprovedTravelAgency\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_roomRate\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_numberOfRooms\",\"type\":\"uint256\"}],\"name\":\"addRooms\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_uniqueId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_maxAmountToPay\",\"type\":\"uint256\"}],\"name\":\"bookRoom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bool\",\"name\":\"_commit\",\"type\":\"bool\"},{\"internalType\":\"bytes32\",\"name\":\"_crossRootTxId\",\"type\":\"bytes32\"}],\"name\":\"finalise\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_uniqueId\",\"type\":\"uint256\"}],\"name\":\"getBookingInformation\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"amountPaid\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"roomId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"date\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"}],\"name\":\"getBookings\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"bookings\",\"type\":\"address[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"}],\"name\":\"getNumberRoomsAvailable\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"numRoomsAvailable\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_key\",\"type\":\"uint256\"}],\"name\":\"isLocked\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50604051610e7f380380610e7f83398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560078054821633179055600880549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610db4806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806399eb5d4c1161005b57806399eb5d4c146100fb578063d131aa7e1461010e578063f40109951461012f578063f6aacfb11461014f57600080fd5b80631358646e1461008d578063184ad3c6146100a25780631b474cf7146100b55780637ebf2c60146100e8575b600080fd5b6100a061009b366004610b6f565b610181565b005b6100a06100b0366004610bb7565b6104a0565b6100c86100c3366004610bf3565b6104fe565b604080519384526020840192909252908201526060015b60405180910390f35b6100a06100f6366004610c0c565b610544565b6100a0610109366004610c3c565b61059d565b61012161011c366004610bf3565b610651565b6040519081526020016100df565b61014261013d366004610bf3565b6106ac565b6040516100df9190610c68565b61017161015d366004610bf3565b600090815260026020526040902054151590565b60405190151581526020016100df565b6000546001600160a01b031633146101e05760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b60006101ed6002846107a5565b905080156102535760405162461bcd60e51b815260206004820152602d60248201527f486f74656c3a204578697374696e6720626f6f6b696e6720666f7220626f6f6b60448201526c696e67207265666572656e636560981b60648201526084016101d7565b60008061025e6107f0565b60008281526006602052604090205491945092506001600160a01b0380841691161490506102de5760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b60648201526084016101d7565b856103225760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b60448201526064016101d7565b60005b60045481101561046257600061033c6000836107a5565b905061034a6001838a610828565b1580156103575750858111155b80156103775750600061036c6001848b61087c565b6001600160a01b0316145b1561044f576103896001838a326108b9565b610395600288846108fb565b6103a16003888a6108fb565b6008546001600160a01b03848116600090815260056020526040908190205460075491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b15801561040c57600080fd5b505af1158015610420573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104449190610cb5565b505050505050505050565b508061045a81610cef565b915050610325565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b60448201526064016101d7565b6007546001600160a01b031633146104b757600080fd5b600092835260066020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600590925290932080549093169116179055565b600080600061050e6003856107a5565b90508061052257600092506000915061053d565b61052d6002856107a5565b915061053a6000836107a5565b92505b9193909250565b6007546001600160a01b0316331461055b57600080fd5b60005b81811015610598576004805461058691600091908261057c83610cef565b91905055856108fb565b8061059081610cef565b91505061055e565b505050565b60005b6000828152600360205260409020548110156106355760008281526003602052604081208054839081106105d6576105d6610d0a565b9060005260206000200154905083156106145760008181526002602052604090205461060490600190610d20565b6000828152600160205260409020555b6000908152600260205260408120558061062d81610cef565b9150506105a0565b50600081815260036020526040812061064d91610b35565b5050565b6000805b6004548110156106a65761066b60018285610828565b158015610681575061067f6001828561087c565b155b15610694578161069081610cef565b9250505b8061069e81610cef565b915050610655565b50919050565b606060006106b983610651565b9050806004546106c99190610d20565b67ffffffffffffffff8111156106e1576106e1610d37565b60405190808252806020026020018201604052801561070a578160200160208202803683370190505b5091506000805b60045481101561079d5761072760018287610828565b61078b5760006107396001838861087c565b90506001600160a01b038116156107895780858461075681610cef565b95508151811061076857610768610d0a565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b8061079581610cef565b915050610711565b505050919050565b60008083836040516020016107c4929190918252602082015260400190565b6040516020818303038152906040528051906020012090506107e88160001c610933565b949350505050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b604080516020810185905290810183905260608101829052600090819060800160408051601f1981840301815291815281516020928301206000818152600290935291205490915015155b95945050505050565b6040805160208082018690528183018590526060808301859052835180840390910181526080909201909252805191012060009061087381610933565b604080516020808201879052818301869052606080830186905283518084039091018152608090920190925280519101206108f481836109a2565b5050505050565b6040805160208082018690528183018590528251808303840181526060909201909252805191012061092d81836109a2565b50505050565b6000818152600260205260408120541561098f5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101d7565b5060009081526001602052604090205490565b600082815260026020526040902054156109f55760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016101d7565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b158015610a4457600080fd5b505afa158015610a58573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a7c9190610d4d565b905080610a96575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610adb57600080fd5b505af1158015610aef573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610b21915083610d66565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610b539190610b56565b50565b5b80821115610b6b5760008155600101610b57565b5090565b600080600060608486031215610b8457600080fd5b505081359360208301359350604090920135919050565b80356001600160a01b0381168114610bb257600080fd5b919050565b600080600060608486031215610bcc57600080fd5b83359250610bdc60208501610b9b565b9150610bea60408501610b9b565b90509250925092565b600060208284031215610c0557600080fd5b5035919050565b60008060408385031215610c1f57600080fd5b50508035926020909101359150565b8015158114610b5357600080fd5b60008060408385031215610c4f57600080fd5b8235610c5a81610c2e565b946020939093013593505050565b6020808252825182820181905260009190848201906040850190845b81811015610ca95783516001600160a01b031683529284019291840191600101610c84565b50909695505050505050565b600060208284031215610cc757600080fd5b8151610cd281610c2e565b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610d0357610d03610cd9565b5060010190565b634e487b7160e01b600052603260045260246000fd5b600082821015610d3257610d32610cd9565b500390565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610d5f57600080fd5b5051919050565b60008219821115610d7957610d79610cd9565b50019056fea2646970667358221220a34ff56c6236a27b6428dbdd7d7499ad2c4fcf3f7331ff115d1c67f19248f3c664736f6c63430008090033",
}

// HotelABI is the input ABI used to generate the binding from.
// Deprecated: Use HotelMetaData.ABI instead.
var HotelABI = HotelMetaData.ABI

// HotelBin is the compiled bytecode used for deploying new contracts.
// Deprecated: Use HotelMetaData.Bin instead.
var HotelBin = HotelMetaData.Bin

// DeployHotel deploys a new Ethereum contract, binding an instance of Hotel to it.
func DeployHotel(auth *bind.TransactOpts, backend bind.ContractBackend, _erc20 common.Address, _cbc common.Address) (common.Address, *types.Transaction, *Hotel, error) {
	parsed, err := HotelMetaData.GetAbi()
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	if parsed == nil {
		return common.Address{}, nil, nil, errors.New("GetABI returned nil")
	}

	address, tx, contract, err := bind.DeployContract(auth, *parsed, common.FromHex(HotelBin), backend, _erc20, _cbc)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &Hotel{HotelCaller: HotelCaller{contract: contract}, HotelTransactor: HotelTransactor{contract: contract}, HotelFilterer: HotelFilterer{contract: contract}}, nil
}

// Hotel is an auto generated Go binding around an Ethereum contract.
type Hotel struct {
	HotelCaller     // Read-only binding to the contract
	HotelTransactor // Write-only binding to the contract
	HotelFilterer   // Log filterer for contract events
}

// HotelCaller is an auto generated read-only Go binding around an Ethereum contract.
type HotelCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// HotelTransactor is an auto generated write-only Go binding around an Ethereum contract.
type HotelTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// HotelFilterer is an auto generated log filtering Go binding around an Ethereum contract events.
type HotelFilterer struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// HotelSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type HotelSession struct {
	Contract     *Hotel            // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// HotelCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type HotelCallerSession struct {
	Contract *HotelCaller  // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts // Call options to use throughout this session
}

// HotelTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type HotelTransactorSession struct {
	Contract     *HotelTransactor  // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// HotelRaw is an auto generated low-level Go binding around an Ethereum contract.
type HotelRaw struct {
	Contract *Hotel // Generic contract binding to access the raw methods on
}

// HotelCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type HotelCallerRaw struct {
	Contract *HotelCaller // Generic read-only contract binding to access the raw methods on
}

// HotelTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type HotelTransactorRaw struct {
	Contract *HotelTransactor // Generic write-only contract binding to access the raw methods on
}

// NewHotel creates a new instance of Hotel, bound to a specific deployed contract.
func NewHotel(address common.Address, backend bind.ContractBackend) (*Hotel, error) {
	contract, err := bindHotel(address, backend, backend, backend)
	if err != nil {
		return nil, err
	}
	return &Hotel{HotelCaller: HotelCaller{contract: contract}, HotelTransactor: HotelTransactor{contract: contract}, HotelFilterer: HotelFilterer{contract: contract}}, nil
}

// NewHotelCaller creates a new read-only instance of Hotel, bound to a specific deployed contract.
func NewHotelCaller(address common.Address, caller bind.ContractCaller) (*HotelCaller, error) {
	contract, err := bindHotel(address, caller, nil, nil)
	if err != nil {
		return nil, err
	}
	return &HotelCaller{contract: contract}, nil
}

// NewHotelTransactor creates a new write-only instance of Hotel, bound to a specific deployed contract.
func NewHotelTransactor(address common.Address, transactor bind.ContractTransactor) (*HotelTransactor, error) {
	contract, err := bindHotel(address, nil, transactor, nil)
	if err != nil {
		return nil, err
	}
	return &HotelTransactor{contract: contract}, nil
}

// NewHotelFilterer creates a new log filterer instance of Hotel, bound to a specific deployed contract.
func NewHotelFilterer(address common.Address, filterer bind.ContractFilterer) (*HotelFilterer, error) {
	contract, err := bindHotel(address, nil, nil, filterer)
	if err != nil {
		return nil, err
	}
	return &HotelFilterer{contract: contract}, nil
}

// bindHotel binds a generic wrapper to an already deployed contract.
func bindHotel(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor, filterer bind.ContractFilterer) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(HotelABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, filterer), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Hotel *HotelRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Hotel.Contract.HotelCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Hotel *HotelRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Hotel.Contract.HotelTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Hotel *HotelRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Hotel.Contract.HotelTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_Hotel *HotelCallerRaw) Call(opts *bind.CallOpts, result *[]interface{}, method string, params ...interface{}) error {
	return _Hotel.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_Hotel *HotelTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _Hotel.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_Hotel *HotelTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _Hotel.Contract.contract.Transact(opts, method, params...)
}

// GetBookingInformation is a free data retrieval call binding the contract method 0x1b474cf7.
//
// Solidity: function getBookingInformation(uint256 _uniqueId) view returns(uint256 amountPaid, uint256 roomId, uint256 date)
func (_Hotel *HotelCaller) GetBookingInformation(opts *bind.CallOpts, _uniqueId *big.Int) (struct {
	AmountPaid *big.Int
	RoomId     *big.Int
	Date       *big.Int
}, error) {
	var out []interface{}
	err := _Hotel.contract.Call(opts, &out, "getBookingInformation", _uniqueId)

	outstruct := new(struct {
		AmountPaid *big.Int
		RoomId     *big.Int
		Date       *big.Int
	})
	if err != nil {
		return *outstruct, err
	}

	outstruct.AmountPaid = *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)
	outstruct.RoomId = *abi.ConvertType(out[1], new(*big.Int)).(**big.Int)
	outstruct.Date = *abi.ConvertType(out[2], new(*big.Int)).(**big.Int)

	return *outstruct, err

}

// GetBookingInformation is a free data retrieval call binding the contract method 0x1b474cf7.
//
// Solidity: function getBookingInformation(uint256 _uniqueId) view returns(uint256 amountPaid, uint256 roomId, uint256 date)
func (_Hotel *HotelSession) GetBookingInformation(_uniqueId *big.Int) (struct {
	AmountPaid *big.Int
	RoomId     *big.Int
	Date       *big.Int
}, error) {
	return _Hotel.Contract.GetBookingInformation(&_Hotel.CallOpts, _uniqueId)
}

// GetBookingInformation is a free data retrieval call binding the contract method 0x1b474cf7.
//
// Solidity: function getBookingInformation(uint256 _uniqueId) view returns(uint256 amountPaid, uint256 roomId, uint256 date)
func (_Hotel *HotelCallerSession) GetBookingInformation(_uniqueId *big.Int) (struct {
	AmountPaid *big.Int
	RoomId     *big.Int
	Date       *big.Int
}, error) {
	return _Hotel.Contract.GetBookingInformation(&_Hotel.CallOpts, _uniqueId)
}

// GetBookings is a free data retrieval call binding the contract method 0xf4010995.
//
// Solidity: function getBookings(uint256 _date) view returns(address[] bookings)
func (_Hotel *HotelCaller) GetBookings(opts *bind.CallOpts, _date *big.Int) ([]common.Address, error) {
	var out []interface{}
	err := _Hotel.contract.Call(opts, &out, "getBookings", _date)

	if err != nil {
		return *new([]common.Address), err
	}

	out0 := *abi.ConvertType(out[0], new([]common.Address)).(*[]common.Address)

	return out0, err

}

// GetBookings is a free data retrieval call binding the contract method 0xf4010995.
//
// Solidity: function getBookings(uint256 _date) view returns(address[] bookings)
func (_Hotel *HotelSession) GetBookings(_date *big.Int) ([]common.Address, error) {
	return _Hotel.Contract.GetBookings(&_Hotel.CallOpts, _date)
}

// GetBookings is a free data retrieval call binding the contract method 0xf4010995.
//
// Solidity: function getBookings(uint256 _date) view returns(address[] bookings)
func (_Hotel *HotelCallerSession) GetBookings(_date *big.Int) ([]common.Address, error) {
	return _Hotel.Contract.GetBookings(&_Hotel.CallOpts, _date)
}

// GetNumberRoomsAvailable is a free data retrieval call binding the contract method 0xd131aa7e.
//
// Solidity: function getNumberRoomsAvailable(uint256 _date) view returns(uint256 numRoomsAvailable)
func (_Hotel *HotelCaller) GetNumberRoomsAvailable(opts *bind.CallOpts, _date *big.Int) (*big.Int, error) {
	var out []interface{}
	err := _Hotel.contract.Call(opts, &out, "getNumberRoomsAvailable", _date)

	if err != nil {
		return *new(*big.Int), err
	}

	out0 := *abi.ConvertType(out[0], new(*big.Int)).(**big.Int)

	return out0, err

}

// GetNumberRoomsAvailable is a free data retrieval call binding the contract method 0xd131aa7e.
//
// Solidity: function getNumberRoomsAvailable(uint256 _date) view returns(uint256 numRoomsAvailable)
func (_Hotel *HotelSession) GetNumberRoomsAvailable(_date *big.Int) (*big.Int, error) {
	return _Hotel.Contract.GetNumberRoomsAvailable(&_Hotel.CallOpts, _date)
}

// GetNumberRoomsAvailable is a free data retrieval call binding the contract method 0xd131aa7e.
//
// Solidity: function getNumberRoomsAvailable(uint256 _date) view returns(uint256 numRoomsAvailable)
func (_Hotel *HotelCallerSession) GetNumberRoomsAvailable(_date *big.Int) (*big.Int, error) {
	return _Hotel.Contract.GetNumberRoomsAvailable(&_Hotel.CallOpts, _date)
}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_Hotel *HotelCaller) IsLocked(opts *bind.CallOpts, _key *big.Int) (bool, error) {
	var out []interface{}
	err := _Hotel.contract.Call(opts, &out, "isLocked", _key)

	if err != nil {
		return *new(bool), err
	}

	out0 := *abi.ConvertType(out[0], new(bool)).(*bool)

	return out0, err

}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_Hotel *HotelSession) IsLocked(_key *big.Int) (bool, error) {
	return _Hotel.Contract.IsLocked(&_Hotel.CallOpts, _key)
}

// IsLocked is a free data retrieval call binding the contract method 0xf6aacfb1.
//
// Solidity: function isLocked(uint256 _key) view returns(bool)
func (_Hotel *HotelCallerSession) IsLocked(_key *big.Int) (bool, error) {
	return _Hotel.Contract.IsLocked(&_Hotel.CallOpts, _key)
}

// AddApprovedTravelAgency is a paid mutator transaction binding the contract method 0x184ad3c6.
//
// Solidity: function addApprovedTravelAgency(uint256 _blockchainId, address _travelAgencyContract, address spendingAccount) returns()
func (_Hotel *HotelTransactor) AddApprovedTravelAgency(opts *bind.TransactOpts, _blockchainId *big.Int, _travelAgencyContract common.Address, spendingAccount common.Address) (*types.Transaction, error) {
	return _Hotel.contract.Transact(opts, "addApprovedTravelAgency", _blockchainId, _travelAgencyContract, spendingAccount)
}

// AddApprovedTravelAgency is a paid mutator transaction binding the contract method 0x184ad3c6.
//
// Solidity: function addApprovedTravelAgency(uint256 _blockchainId, address _travelAgencyContract, address spendingAccount) returns()
func (_Hotel *HotelSession) AddApprovedTravelAgency(_blockchainId *big.Int, _travelAgencyContract common.Address, spendingAccount common.Address) (*types.Transaction, error) {
	return _Hotel.Contract.AddApprovedTravelAgency(&_Hotel.TransactOpts, _blockchainId, _travelAgencyContract, spendingAccount)
}

// AddApprovedTravelAgency is a paid mutator transaction binding the contract method 0x184ad3c6.
//
// Solidity: function addApprovedTravelAgency(uint256 _blockchainId, address _travelAgencyContract, address spendingAccount) returns()
func (_Hotel *HotelTransactorSession) AddApprovedTravelAgency(_blockchainId *big.Int, _travelAgencyContract common.Address, spendingAccount common.Address) (*types.Transaction, error) {
	return _Hotel.Contract.AddApprovedTravelAgency(&_Hotel.TransactOpts, _blockchainId, _travelAgencyContract, spendingAccount)
}

// AddRooms is a paid mutator transaction binding the contract method 0x7ebf2c60.
//
// Solidity: function addRooms(uint256 _roomRate, uint256 _numberOfRooms) returns()
func (_Hotel *HotelTransactor) AddRooms(opts *bind.TransactOpts, _roomRate *big.Int, _numberOfRooms *big.Int) (*types.Transaction, error) {
	return _Hotel.contract.Transact(opts, "addRooms", _roomRate, _numberOfRooms)
}

// AddRooms is a paid mutator transaction binding the contract method 0x7ebf2c60.
//
// Solidity: function addRooms(uint256 _roomRate, uint256 _numberOfRooms) returns()
func (_Hotel *HotelSession) AddRooms(_roomRate *big.Int, _numberOfRooms *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.AddRooms(&_Hotel.TransactOpts, _roomRate, _numberOfRooms)
}

// AddRooms is a paid mutator transaction binding the contract method 0x7ebf2c60.
//
// Solidity: function addRooms(uint256 _roomRate, uint256 _numberOfRooms) returns()
func (_Hotel *HotelTransactorSession) AddRooms(_roomRate *big.Int, _numberOfRooms *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.AddRooms(&_Hotel.TransactOpts, _roomRate, _numberOfRooms)
}

// BookRoom is a paid mutator transaction binding the contract method 0x1358646e.
//
// Solidity: function bookRoom(uint256 _date, uint256 _uniqueId, uint256 _maxAmountToPay) returns()
func (_Hotel *HotelTransactor) BookRoom(opts *bind.TransactOpts, _date *big.Int, _uniqueId *big.Int, _maxAmountToPay *big.Int) (*types.Transaction, error) {
	return _Hotel.contract.Transact(opts, "bookRoom", _date, _uniqueId, _maxAmountToPay)
}

// BookRoom is a paid mutator transaction binding the contract method 0x1358646e.
//
// Solidity: function bookRoom(uint256 _date, uint256 _uniqueId, uint256 _maxAmountToPay) returns()
func (_Hotel *HotelSession) BookRoom(_date *big.Int, _uniqueId *big.Int, _maxAmountToPay *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.BookRoom(&_Hotel.TransactOpts, _date, _uniqueId, _maxAmountToPay)
}

// BookRoom is a paid mutator transaction binding the contract method 0x1358646e.
//
// Solidity: function bookRoom(uint256 _date, uint256 _uniqueId, uint256 _maxAmountToPay) returns()
func (_Hotel *HotelTransactorSession) BookRoom(_date *big.Int, _uniqueId *big.Int, _maxAmountToPay *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.BookRoom(&_Hotel.TransactOpts, _date, _uniqueId, _maxAmountToPay)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_Hotel *HotelTransactor) Finalise(opts *bind.TransactOpts, _commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _Hotel.contract.Transact(opts, "finalise", _commit, _crossRootTxId)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_Hotel *HotelSession) Finalise(_commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _Hotel.Contract.Finalise(&_Hotel.TransactOpts, _commit, _crossRootTxId)
}

// Finalise is a paid mutator transaction binding the contract method 0x99eb5d4c.
//
// Solidity: function finalise(bool _commit, bytes32 _crossRootTxId) returns()
func (_Hotel *HotelTransactorSession) Finalise(_commit bool, _crossRootTxId [32]byte) (*types.Transaction, error) {
	return _Hotel.Contract.Finalise(&_Hotel.TransactOpts, _commit, _crossRootTxId)
}
