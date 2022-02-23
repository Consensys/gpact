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
	ABI: "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_erc20\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_cbc\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_blockchainId\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_travelAgencyContract\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spendingAccount\",\"type\":\"address\"}],\"name\":\"addApprovedTravelAgency\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_roomRate\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_numberOfRooms\",\"type\":\"uint256\"}],\"name\":\"addRooms\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_uniqueId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_maxAmountToPay\",\"type\":\"uint256\"}],\"name\":\"bookRoom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_roomNumber\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_roomRate\",\"type\":\"uint256\"}],\"name\":\"changeRoomRate\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bool\",\"name\":\"_commit\",\"type\":\"bool\"},{\"internalType\":\"bytes32\",\"name\":\"_crossRootTxId\",\"type\":\"bytes32\"}],\"name\":\"finalise\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_uniqueId\",\"type\":\"uint256\"}],\"name\":\"getBookingInformation\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"amountPaid\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"roomId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"date\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"}],\"name\":\"getBookings\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"bookings\",\"type\":\"address[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_date\",\"type\":\"uint256\"}],\"name\":\"getNumberRoomsAvailable\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"numRoomsAvailable\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_key\",\"type\":\"uint256\"}],\"name\":\"isLocked\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]",
	Bin: "0x608060405234801561001057600080fd5b50604051610e4c380380610e4c83398101604081905261002f91610089565b600080546001600160a01b039283166001600160a01b03199182161790915560078054821633179055600880549390921692169190911790556100bc565b80516001600160a01b038116811461008457600080fd5b919050565b6000806040838503121561009c57600080fd5b6100a58361006d565b91506100b36020840161006d565b90509250929050565b610d81806100cb6000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80637ebf2c60116100665780637ebf2c601461010657806399eb5d4c14610119578063d131aa7e1461012c578063f40109951461014d578063f6aacfb11461016d57600080fd5b80631358646e14610098578063184ad3c6146100ad5780631b474cf7146100c05780633ca1a5b4146100f3575b600080fd5b6100ab6100a6366004610b3c565b61019f565b005b6100ab6100bb366004610b84565b610451565b6100d36100ce366004610bc0565b6104af565b604080519384526020840192909252908201526060015b60405180910390f35b6100ab610101366004610bd9565b6104f5565b6100ab610114366004610bd9565b61051c565b6100ab610127366004610c09565b610575565b61013f61013a366004610bc0565b610625565b6040519081526020016100ea565b61016061015b366004610bc0565b610680565b6040516100ea9190610c35565b61018f61017b366004610bc0565b600090815260026020526040902054151590565b60405190151581526020016100ea565b6000546001600160a01b031633146101fe5760405162461bcd60e51b815260206004820152601760248201527f4d7573742062652063726f7373636861696e2063616c6c00000000000000000060448201526064015b60405180910390fd5b600080610209610779565b60008281526006602052604090205491945092506001600160a01b0380841691161490506102895760405162461bcd60e51b815260206004820152602760248201527f53656e646572206973206e6f7420616e20617070726f7665642074726176656c604482015266206167656e637960c81b60648201526084016101f5565b846102cd5760405162461bcd60e51b8152602060048201526014602482015273446174652063616e206e6f74206265207a65726f60601b60448201526064016101f5565b60005b60045481101561040c5760006102e76000836107b1565b90506102f5600183896107fc565b1580156103025750848111155b8015610322575060006103176001848a610850565b6001600160a01b0316145b156103f957610334600183893261088d565b610340600287846108c8565b61034c600387896108c8565b6008546001600160a01b03848116600090815260056020526040908190205460075491516323b872dd60e01b815290831660048201529082166024820152604481018490529116906323b872dd90606401602060405180830381600087803b1580156103b757600080fd5b505af11580156103cb573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103ef9190610c82565b5050505050505050565b508061040481610cbc565b9150506102d0565b5060405162461bcd60e51b81526020600482015260126024820152714e6f20726f6f6d7320617661696c61626c6560701b60448201526064016101f5565b5050505050565b6007546001600160a01b0316331461046857600080fd5b600092835260066020908152604080852080546001600160a01b03199081166001600160a01b03968716908117909255908652600590925290932080549093169116179055565b60008060006104bf6003856107b1565b9050806104d35760009250600091506104ee565b6104de6002856107b1565b91506104eb6000836107b1565b92505b9193909250565b6007546001600160a01b0316331461050c57600080fd5b610518600083836108c8565b5050565b6007546001600160a01b0316331461053357600080fd5b60005b81811015610570576004805461055e91600091908261055483610cbc565b91905055856108c8565b8061056881610cbc565b915050610536565b505050565b60005b60008281526003602052604090205481101561060d5760008281526003602052604081208054839081106105ae576105ae610cd7565b9060005260206000200154905083156105ec576000818152600260205260409020546105dc90600190610ced565b6000828152600160205260409020555b6000908152600260205260408120558061060581610cbc565b915050610578565b50600081815260036020526040812061051891610b02565b6000805b60045481101561067a5761063f600182856107fc565b158015610655575061065360018285610850565b155b15610668578161066481610cbc565b9250505b8061067281610cbc565b915050610629565b50919050565b6060600061068d83610625565b90508060045461069d9190610ced565b67ffffffffffffffff8111156106b5576106b5610d04565b6040519080825280602002602001820160405280156106de578160200160208202803683370190505b5091506000805b600454811015610771576106fb600182876107fc565b61075f57600061070d60018388610850565b90506001600160a01b0381161561075d5780858461072a81610cbc565b95508151811061073c5761073c610cd7565b60200260200101906001600160a01b031690816001600160a01b0316815250505b505b8061076981610cbc565b9150506106e5565b505050919050565b60008080803680602060531982018437600051955060206034820360003760005194506014808203600c376000519350505050909192565b60008083836040516020016107d0929190918252602082015260400190565b6040516020818303038152906040528051906020012090506107f48160001c610900565b949350505050565b604080516020810185905290810183905260608101829052600090819060800160408051601f1981840301815291815281516020928301206000818152600290935291205490915015155b95945050505050565b6040805160208082018690528183018590526060808301859052835180840390910181526080909201909252805191012060009061084781610900565b6040805160208082018790528183018690526060808301869052835180840390910181526080909201909252805191012061044a818361096f565b604080516020808201869052818301859052825180830384018152606090920190925280519101206108fa818361096f565b50505050565b6000818152600260205260408120541561095c5760405162461bcd60e51b815260206004820152601f60248201527f52656164207768696c6520636f6e7472616374206974656d206c6f636b65640060448201526064016101f5565b5060009081526001602052604090205490565b600082815260026020526040902054156109c25760405162461bcd60e51b815260206004820152601460248201527310dbdb9d1c9858dd081a5d195b481b1bd8dad95960621b60448201526064016101f5565b60008060009054906101000a90046001600160a01b03166001600160a01b0316637bf37a096040518163ffffffff1660e01b815260040160206040518083038186803b158015610a1157600080fd5b505afa158015610a25573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a499190610d1a565b905080610a63575060009182526001602052604090912055565b600054604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610aa857600080fd5b505af1158015610abc573d6000803e3d6000fd5b505050600082815260036020908152604082208054600181810183559184529190922001859055610aee915083610d33565b600084815260026020526040902055505050565b5080546000825590600052602060002090810190610b209190610b23565b50565b5b80821115610b385760008155600101610b24565b5090565b600080600060608486031215610b5157600080fd5b505081359360208301359350604090920135919050565b80356001600160a01b0381168114610b7f57600080fd5b919050565b600080600060608486031215610b9957600080fd5b83359250610ba960208501610b68565b9150610bb760408501610b68565b90509250925092565b600060208284031215610bd257600080fd5b5035919050565b60008060408385031215610bec57600080fd5b50508035926020909101359150565b8015158114610b2057600080fd5b60008060408385031215610c1c57600080fd5b8235610c2781610bfb565b946020939093013593505050565b6020808252825182820181905260009190848201906040850190845b81811015610c765783516001600160a01b031683529284019291840191600101610c51565b50909695505050505050565b600060208284031215610c9457600080fd5b8151610c9f81610bfb565b9392505050565b634e487b7160e01b600052601160045260246000fd5b6000600019821415610cd057610cd0610ca6565b5060010190565b634e487b7160e01b600052603260045260246000fd5b600082821015610cff57610cff610ca6565b500390565b634e487b7160e01b600052604160045260246000fd5b600060208284031215610d2c57600080fd5b5051919050565b60008219821115610d4657610d46610ca6565b50019056fea26469706673582212209bb4857ed5a65920b90a5739867cd79151ecb398a521d8c6e90f1c7be2cd2a3564736f6c63430008090033",
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

// ChangeRoomRate is a paid mutator transaction binding the contract method 0x3ca1a5b4.
//
// Solidity: function changeRoomRate(uint256 _roomNumber, uint256 _roomRate) returns()
func (_Hotel *HotelTransactor) ChangeRoomRate(opts *bind.TransactOpts, _roomNumber *big.Int, _roomRate *big.Int) (*types.Transaction, error) {
	return _Hotel.contract.Transact(opts, "changeRoomRate", _roomNumber, _roomRate)
}

// ChangeRoomRate is a paid mutator transaction binding the contract method 0x3ca1a5b4.
//
// Solidity: function changeRoomRate(uint256 _roomNumber, uint256 _roomRate) returns()
func (_Hotel *HotelSession) ChangeRoomRate(_roomNumber *big.Int, _roomRate *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.ChangeRoomRate(&_Hotel.TransactOpts, _roomNumber, _roomRate)
}

// ChangeRoomRate is a paid mutator transaction binding the contract method 0x3ca1a5b4.
//
// Solidity: function changeRoomRate(uint256 _roomNumber, uint256 _roomRate) returns()
func (_Hotel *HotelTransactorSession) ChangeRoomRate(_roomNumber *big.Int, _roomRate *big.Int) (*types.Transaction, error) {
	return _Hotel.Contract.ChangeRoomRate(&_Hotel.TransactOpts, _roomNumber, _roomRate)
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
