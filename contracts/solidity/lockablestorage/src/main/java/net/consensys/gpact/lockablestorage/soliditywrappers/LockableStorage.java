package net.consensys.gpact.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.5-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class LockableStorage extends Contract {
    public static final String BINARY = "60a060405234801561001057600080fd5b5060405161120238038061120283398101604081905261002f9161005b565b3360601b608052600180546001600160a01b0319166001600160a01b039290921691909117905561008b565b60006020828403121561006d57600080fd5b81516001600160a01b038116811461008457600080fd5b9392505050565b60805160601c6111596100a96000396000610a3e01526111596000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c806357bc2ef31161006657806357bc2ef3146101255780635bf9755e146101455780639c235a6814610158578063cf3090121461016b578063eda1824d1461018f57600080fd5b8063011827fd146100a357806301b6a880146100b8578063160f4749146100e8578063441abbac146100fb5780635384d8bd1461011c575b600080fd5b6100b66100b1366004610f0d565b610198565b005b6000546100cb906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100b66100f6366004610ea1565b6103aa565b61010e610109366004610edb565b6105c7565b6040519081526020016100df565b61010e60025481565b610138610133366004610edb565b6106ca565b6040516100df9190610fab565b6100b6610153366004610f89565b61085e565b6100b6610166366004610e71565b610a3c565b60015461017f90600160a01b900460ff1681565b60405190151581526020016100df565b61010e60035481565b6000546001600160a01b031633146101cb5760405162461bcd60e51b81526004016101c290611055565b60405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561021957600080fd5b505afa15801561022d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102519190610ebe565b1561029f57600154600160a01b900460ff16156102805760405162461bcd60e51b81526004016101c290611000565b6000838152600860205260409020610299908383610d06565b50505050565b600154600160a01b900460ff1615610339576102b9610aa9565b60008381526009602052604090206102d2908383610d06565b506000838152600b602052604090205460ff1661033457600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff191690911790555b505050565b610341610b9b565b600083815260096020526040902061035a908383610d06565b50600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff19169091179055505050565b600154600160a01b900460ff166103f95760405162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f2066696e616c69736560681b60448201526064016101c2565b80156104cd5760005b60065481101561045757600060068281548110610421576104216110ff565b6000918252602080832090910154825260058152604080832054600490925290912055508061044f816110d6565b915050610402565b5060005b600a548110156104cb576000600a828154811061047a5761047a6110ff565b600091825260208083209091015480835260098252604080842060089093529092208154929350916104ab9061109b565b6104b6929190610d8a565b505080806104c3906110d6565b91505061045b565b505b60005b60065481101561052d576000600682815481106104ef576104ef6110ff565b6000918252602080832090910154825260058152604080832083905560079091529020805460ff191690555080610525816110d6565b9150506104d0565b5060005b600a5481101561059e576000600a8281548110610550576105506110ff565b600091825260208083209091015480835260099091526040822090925061057691610e05565b6000908152600b60205260409020805460ff1916905580610596816110d6565b915050610531565b506105ab60066000610e42565b6105b7600a6000610e42565b506001805460ff60a01b19169055565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561060c57600080fd5b505afa158015610620573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106449190610ebe565b1561068657600154600160a01b900460ff16156106735760405162461bcd60e51b81526004016101c290611000565b5060009081526004602052604090205490565b600154600160a01b900460ff1615610673576106a0610aa9565b60008281526007602052604090205460ff1615610673575060009081526005602052604090205490565b60015460408051635a61dbab60e11b815290516060926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561070f57600080fd5b505afa158015610723573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107479190610ebe565b1561081457600154600160a01b900460ff16156107765760405162461bcd60e51b81526004016101c290611000565b6000828152600860205260409020805461078f9061109b565b80601f01602080910402602001604051908101604052809291908181526020018280546107bb9061109b565b80156108085780601f106107dd57610100808354040283529160200191610808565b820191906000526020600020905b8154815290600101906020018083116107eb57829003601f168201915b50505050509050919050565b600154600160a01b900460ff16156107765761082e610aa9565b6000828152600b602052604090205460ff1615610776576000828152600960205260409020805461078f9061109b565b6000546001600160a01b031633146108885760405162461bcd60e51b81526004016101c290611055565b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156108d657600080fd5b505afa1580156108ea573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061090e9190610ebe565b1561094f57600154600160a01b900460ff161561093d5760405162461bcd60e51b81526004016101c290611000565b60009182526004602052604090912055565b600154600160a01b900460ff16156109db57610969610aa9565b6000828152600560209081526040808320849055600790915290205460ff166109d7576006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f018390556000838152600760205260409020805460ff191690911790555b5050565b6109e3610b9b565b6000828152600560209081526040808320939093556006805460018082019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01949094556007905220805460ff19169091179055565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03163314610a7157600080fd5b6000546001600160a01b031615610a8757600080fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600160009054906101000a90046001600160a01b03166001600160a01b0316638e9efe826040518163ffffffff1660e01b815260040160206040518083038186803b158015610af757600080fd5b505afa158015610b0b573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b2f9190610ebe565b610b995760405162461bcd60e51b815260206004820152603560248201527f436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626044820152743637b1b5b1b430b4b7103a3930b739b0b1ba34b7b760591b60648201526084016101c2565b565b6001805460ff60a01b198116600160a01b179091556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610bf257600080fd5b505afa158015610c06573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c2a9190610ef4565b6002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610c7257600080fd5b505afa158015610c86573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610caa9190610ef4565b600355600154604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610cf257600080fd5b505af1158015610299573d6000803e3d6000fd5b828054610d129061109b565b90600052602060002090601f016020900481019282610d345760008555610d7a565b82601f10610d4d5782800160ff19823516178555610d7a565b82800160010185558215610d7a579182015b82811115610d7a578235825591602001919060010190610d5f565b50610d86929150610e5c565b5090565b828054610d969061109b565b90600052602060002090601f016020900481019282610db85760008555610d7a565b82601f10610dc95780548555610d7a565b82800160010185558215610d7a57600052602060002091601f016020900482015b82811115610d7a578254825591600101919060010190610dea565b508054610e119061109b565b6000825580601f10610e21575050565b601f016020900490600052602060002090810190610e3f9190610e5c565b50565b5080546000825590600052602060002090810190610e3f91905b5b80821115610d865760008155600101610e5d565b600060208284031215610e8357600080fd5b81356001600160a01b0381168114610e9a57600080fd5b9392505050565b600060208284031215610eb357600080fd5b8135610e9a81611115565b600060208284031215610ed057600080fd5b8151610e9a81611115565b600060208284031215610eed57600080fd5b5035919050565b600060208284031215610f0657600080fd5b5051919050565b600080600060408486031215610f2257600080fd5b83359250602084013567ffffffffffffffff80821115610f4157600080fd5b818601915086601f830112610f5557600080fd5b813581811115610f6457600080fd5b876020828501011115610f7657600080fd5b6020830194508093505050509250925092565b60008060408385031215610f9c57600080fd5b50508035926020909101359150565b600060208083528351808285015260005b81811015610fd857858101830151858201604001528201610fbc565b81811115610fea576000604083870101525b50601f01601f1916929092016040019392505050565b60208082526035908201527f417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c604082015274081dda195b8818dbdb9d1c9858dd081b1bd8dad959605a1b606082015260800190565b60208082526026908201527f4f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6040820152651b9d1c9858dd60d21b606082015260800190565b600181811c908216806110af57607f821691505b602082108114156110d057634e487b7160e01b600052602260045260246000fd5b50919050565b60006000198214156110f857634e487b7160e01b600052601160045260246000fd5b5060010190565b634e487b7160e01b600052603260045260246000fd5b8015158114610e3f57600080fdfea264697066735822122079e78ac4fe01c4d5952bfab18250c38e1be7c745a54a72e3f0071c49835c87de64736f6c63430008050033";

    public static final String FUNC_BUSINESSLOGICCONTRACT = "businessLogicContract";

    public static final String FUNC_FINALISE = "finalise";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINID = "lockedByRootBlockchainId";

    public static final String FUNC_LOCKEDBYTRANSACTIONID = "lockedByTransactionId";

    public static final String FUNC_SETBUSINESSLOGICCONTRACT = "setBusinessLogicContract";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETUINT256 = "setUint256";

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> businessLogicContract() {
        final Function function = new Function(FUNC_BUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> finalise(Boolean _commit) {
        final Function function = new Function(
                FUNC_FINALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_commit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> getBytes(BigInteger _key) {
        final Function function = new Function(FUNC_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getUint256(BigInteger _key) {
        final Function function = new Function(FUNC_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> locked() {
        final Function function = new Function(FUNC_LOCKED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> lockedByRootBlockchainId() {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> lockedByTransactionId() {
        final Function function = new Function(FUNC_LOCKEDBYTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setBusinessLogicContract(String _businessLogicContract) {
        final Function function = new Function(
                FUNC_SETBUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _businessLogicContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
