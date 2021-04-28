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
    public static final String BINARY = "60a060405234801561001057600080fd5b5060405161122938038061122983398101604081905261002f9161005b565b3360601b608052600180546001600160a01b0319166001600160a01b0392909216919091179055610089565b60006020828403121561006c578081fd5b81516001600160a01b0381168114610082578182fd5b9392505050565b60805160601c6111826100a76000396000610a8501526111826000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c806357bc2ef31161006657806357bc2ef3146101255780635bf9755e146101455780639c235a6814610158578063cf3090121461016b578063eda1824d1461018f5761009e565b8063011827fd146100a357806301b6a880146100b8578063160f4749146100e8578063441abbac146100fb5780635384d8bd1461011c575b600080fd5b6100b66100b1366004610f56565b610198565b005b6000546100cb906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100b66100f6366004610eee565b6103ab565b61010e610109366004610f26565b610600565b6040519081526020016100df565b61010e60025481565b610138610133366004610f26565b610708565b6040516100df9190610fee565b6100b6610153366004610fcd565b61089c565b6100b6610166366004610ec0565b610a83565b60015461017f90600160a01b900460ff1681565b60405190151581526020016100df565b61010e60035481565b6000546001600160a01b031633146101cb5760405162461bcd60e51b81526004016101c290611096565b60405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561021957600080fd5b505afa15801561022d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102519190610f0a565b1561029f57600154600160a01b900460ff16156102805760405162461bcd60e51b81526004016101c290611041565b6000838152600860205260409020610299908383610d53565b506103a6565b600154600160a01b900460ff1615610339576102b9610af0565b60008381526009602052604090206102d2908383610d53565b506000838152600b602052604090205460ff1661033457600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff191690911790555b6103a6565b610341610be2565b600083815260096020526040902061035a908383610d53565b50600a805460018181019092557fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a8018490556000848152600b60205260409020805460ff191690911790555b505050565b600154600160a01b900460ff166103fa5760405162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f2066696e616c69736560681b60448201526064016101c2565b80156104ea5760005b6006548110156104665760006006828154811061043057634e487b7160e01b600052603260045260246000fd5b6000918252602080832090910154825260058152604080832054600490925290912055508061045e81611117565b915050610403565b5060005b600a548110156104e8576000600a828154811061049757634e487b7160e01b600052603260045260246000fd5b600091825260208083209091015480835260098252604080842060089093529092208154929350916104c8906110dc565b6104d3929190610dd7565b505080806104e090611117565b91505061046a565b505b60005b6006548110156105585760006006828154811061051a57634e487b7160e01b600052603260045260246000fd5b6000918252602080832090910154825260058152604080832083905560079091529020805460ff19169055508061055081611117565b9150506104ed565b5060005b600a548110156105d7576000600a828154811061058957634e487b7160e01b600052603260045260246000fd5b60009182526020808320909101548083526009909152604082209092506105af91610e52565b6000908152600b60205260409020805460ff19169055806105cf81611117565b91505061055c565b506105e460066000610e91565b6105f0600a6000610e91565b506001805460ff60a01b19169055565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561064557600080fd5b505afa158015610659573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061067d9190610f0a565b156106c157600154600160a01b900460ff16156106ac5760405162461bcd60e51b81526004016101c290611041565b50600081815260046020526040902054610703565b600154600160a01b900460ff16156106ac576106db610af0565b60008281526007602052604090205460ff16156106ac57506000818152600560205260409020545b919050565b60015460408051635a61dbab60e11b815290516060926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561074d57600080fd5b505afa158015610761573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107859190610f0a565b1561085257600154600160a01b900460ff16156107b45760405162461bcd60e51b81526004016101c290611041565b600082815260086020526040902080546107cd906110dc565b80601f01602080910402602001604051908101604052809291908181526020018280546107f9906110dc565b80156108465780601f1061081b57610100808354040283529160200191610846565b820191906000526020600020905b81548152906001019060200180831161082957829003601f168201915b50505050509050610703565b600154600160a01b900460ff16156107b45761086c610af0565b6000828152600b602052604090205460ff16156107b457600082815260096020526040902080546107cd906110dc565b6000546001600160a01b031633146108c65760405162461bcd60e51b81526004016101c290611096565b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561091457600080fd5b505afa158015610928573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061094c9190610f0a565b1561099157600154600160a01b900460ff161561097b5760405162461bcd60e51b81526004016101c290611041565b6000828152600460205260409020819055610a7f565b600154600160a01b900460ff1615610a1e576109ab610af0565b6000828152600560209081526040808320849055600790915290205460ff16610a19576006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f018390556000838152600760205260409020805460ff191690911790555b610a7f565b610a26610be2565b60008281526005602090815260408083208490556006805460018082019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f018690556007909252909120805460ff191690911790555b5050565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03163314610ab857600080fd5b6000546001600160a01b031615610ace57600080fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600160009054906101000a90046001600160a01b03166001600160a01b0316638e9efe826040518163ffffffff1660e01b815260040160206040518083038186803b158015610b3e57600080fd5b505afa158015610b52573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b769190610f0a565b610be05760405162461bcd60e51b815260206004820152603560248201527f436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626044820152743637b1b5b1b430b4b7103a3930b739b0b1ba34b7b760591b60648201526084016101c2565b565b6001805460ff60a01b1916600160a01b17908190556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610c3957600080fd5b505afa158015610c4d573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c719190610f3e565b6002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610cb957600080fd5b505afa158015610ccd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610cf19190610f3e565b600355600154604051631ce7083f60e11b81523060048201526001600160a01b03909116906339ce107e90602401600060405180830381600087803b158015610d3957600080fd5b505af1158015610d4d573d6000803e3d6000fd5b50505050565b828054610d5f906110dc565b90600052602060002090601f016020900481019282610d815760008555610dc7565b82601f10610d9a5782800160ff19823516178555610dc7565b82800160010185558215610dc7579182015b82811115610dc7578235825591602001919060010190610dac565b50610dd3929150610eab565b5090565b828054610de3906110dc565b90600052602060002090601f016020900481019282610e055760008555610dc7565b82601f10610e165780548555610dc7565b82800160010185558215610dc757600052602060002091601f016020900482015b82811115610dc7578254825591600101919060010190610e37565b508054610e5e906110dc565b6000825580601f10610e705750610e8e565b601f016020900490600052602060002090810190610e8e9190610eab565b50565b5080546000825590600052602060002090810190610e8e91905b5b80821115610dd35760008155600101610eac565b600060208284031215610ed1578081fd5b81356001600160a01b0381168114610ee7578182fd5b9392505050565b600060208284031215610eff578081fd5b8135610ee78161113e565b600060208284031215610f1b578081fd5b8151610ee78161113e565b600060208284031215610f37578081fd5b5035919050565b600060208284031215610f4f578081fd5b5051919050565b600080600060408486031215610f6a578182fd5b83359250602084013567ffffffffffffffff80821115610f88578384fd5b818601915086601f830112610f9b578384fd5b813581811115610fa9578485fd5b876020828501011115610fba578485fd5b6020830194508093505050509250925092565b60008060408385031215610fdf578182fd5b50508035926020909101359150565b6000602080835283518082850152825b8181101561101a57858101830151858201604001528201610ffe565b8181111561102b5783604083870101525b50601f01601f1916929092016040019392505050565b60208082526035908201527f417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c604082015274081dda195b8818dbdb9d1c9858dd081b1bd8dad959605a1b606082015260800190565b60208082526026908201527f4f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6040820152651b9d1c9858dd60d21b606082015260800190565b6002810460018216806110f057607f821691505b6020821081141561111157634e487b7160e01b600052602260045260246000fd5b50919050565b600060001982141561113757634e487b7160e01b81526011600452602481fd5b5060010190565b8015158114610e8e57600080fdfea26469706673582212207e218b8d67f08400f7ab25c62d6913103ea2620d62b908655acf00ad4b68cf8164736f6c63430008020033";

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
