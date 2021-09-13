package net.consensys.gpact.txroot.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
public class CrosschainVerifierTxRoot extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516112d53803806112d583398101604081905261002f9161007c565b600180546001600160a01b039384166001600160a01b031991821617909155600080549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b611217806100be6000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80634c1ce90214610030575b600080fd5b61004361003e366004610f4c565b610045565b005b6001546040516303e04ced60e11b8152600481018890526000916001600160a01b0316906307c099da9060240160206040518083038186803b15801561008a57600080fd5b505afa15801561009e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100c29190610efa565b9050600061010584848080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152506103aa92505050565b600054815160208301516040808501516060860151608087015160a0880151935163917ede9960e01b81529798506001600160a01b039096169663917ede99966101559695949091600401611041565b60206040518083038186803b15801561016d57600080fd5b505afa158015610181573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101a59190610f2a565b508051881461020c5760405162461bcd60e51b815260206004820152602860248201527f4576656e74206e6f7420656d697474656420627920657870656374656420626c60448201526737b1b5b1b430b4b760c11b60648201526084015b60405180910390fd5b80602001516001600160a01b0316826001600160a01b0316146102805760405162461bcd60e51b815260206004820152602660248201527f4576656e74206e6f7420656d697474656420627920657870656374656420636f6044820152651b9d1c9858dd60d21b6064820152608401610203565b6000610297610292836060015161067a565b6106c9565b905060006102be826001815181106102b1576102b16111b5565b60200260200101516107aa565b905060606102d184602001518b84610827565b602080870151604051929450600093506102f1928f928f91879101610ffb565b60408051601f198184030181526020601f8d018190048102840181019092528b8352925061033b9183918d908d908190840183828082843760009201919091525061099292505050565b61039c5760405162461bcd60e51b815260206004820152602c60248201527f4578706563746564206576656e7420646f6573206e6f74206d6174636820657660448201526b32b73a1034b710383937b7b360a11b6064820152608401610203565b505050505050505050505050565b6040805160c08101825260008082526020820181905291810182905260608082018190526080820181905260a0820152906103e76102928461067a565b9050600061040b610404836000815181106102b1576102b16111b5565b60006109bf565b9050600061042f610428846001815181106102b1576102b16111b5565b6014015190565b9050600061045361044c856002815181106102b1576102b16111b5565b6000610a25565b9050600061046d856003815181106102b1576102b16111b5565b9050600061049486600481518110610487576104876111b5565b60200260200101516106c9565b905060006104ae87600581518110610487576104876111b5565b9050805182511461051c5760405162461bcd60e51b815260206004820152603260248201527f4c656e677468206f662070726f6f664f66667365747320646f6573206e6f742060448201527136b0ba31b4103632b733ba3410383937b7b360711b6064820152608401610203565b6000825167ffffffffffffffff811115610538576105386111cb565b604051908082528060200260200182016040528015610561578160200160208202803683370190505b5090506000825167ffffffffffffffff811115610580576105806111cb565b6040519080825280602002602001820160405280156105b357816020015b606081526020019060019003908161059e5790505b50905060005b835181101561063d576105da6104048683815181106102b1576102b16111b5565b8382815181106105ec576105ec6111b5565b60200260200101818152505061060d8482815181106102b1576102b16111b5565b82828151811061061f5761061f6111b5565b6020026020010181905250808061063590611184565b9150506105b9565b506040805160c0810182529889526001600160a01b0390971660208901529587019490945250506060840152608083015260a08201529392505050565b60408051808201909152600080825260208201528151806106b05750506040805180820190915260008082526020820152919050565b6040805180820190915260209384018152928301525090565b60606106d482610a8a565b6106dd57600080fd5b60006106e883610ab1565b90508067ffffffffffffffff811115610703576107036111cb565b60405190808252806020026020018201604052801561074857816020015b60408051808201909152600080825260208201528152602001906001900390816107215790505b509150600061075684610b43565b905060005b61076482610b9e565b156107a25761077282610bc2565b848281518110610784576107846111b5565b6020026020010181905250808061079a90611184565b91505061075b565b505050919050565b60606107b582610c1c565b6107be57600080fd5b6000806107ca84610c42565b90925090508067ffffffffffffffff8111156107e8576107e86111cb565b6040519080825280601f01601f191660200182016040528015610812576020820181803683370190505b509250610820828483610ce6565b5050919050565b60608060006108386102928561067a565b9050600061085282600381518110610487576104876111b5565b905060005b8151811015610933576000610877838381518110610487576104876111b5565b90506000610894610428836000815181106102b1576102b16111b5565b90506108ac82600181518110610487576104876111b5565b96506108c4826002815181106102b1576102b16111b5565b955060006108eb886000815181106108de576108de6111b5565b6020026020010151610d24565b9050898114801561090d57508a6001600160a01b0316826001600160a01b0316145b1561091d5750505050505061098a565b505050808061092b90611184565b915050610857565b5060405162461bcd60e51b815260206004820152602560248201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560448201526418d95a5c1d60da1b6064820152608401610203565b935093915050565b600081518351146109a5575060006109b9565b818051906020012083805190602001201490505b92915050565b60006109cc826020611106565b83511015610a1c5760405162461bcd60e51b815260206004820152601e60248201527f736c6963696e67206f7574206f662072616e6765202875696e743235362900006044820152606401610203565b50016020015190565b60008060005b6020811015610a8257610a3f81600861111e565b85610a4a8387611106565b81518110610a5a57610a5a6111b5565b01602001516001600160f81b031916901c919091179080610a7a81611184565b915050610a2b565b509392505050565b6000816020015160001415610aa157506000919050565b50515160c060009190911a101590565b6000610abc82610a8a565b610ac857506000919050565b81518051600090811a9190610adc85610d2f565b610ae69083611106565b905060006001866020015184610afc9190611106565b610b06919061113d565b905060005b818311610b3957610b1b83610dbd565b610b259084611106565b925080610b3181611184565b915050610b0b565b9695505050505050565b6040805160808101825260009181018281526060820183905281526020810191909152610b6f82610a8a565b610b7857600080fd5b6000610b8383610d2f565b8351610b8f9190611106565b92825250602081019190915290565b80516020810151815160009291610bb491611106565b836020015110915050919050565b6040805180820190915260008082526020820152610bdf82610b9e565b1561002b5760208201516000610bf482610dbd565b828452602084018190529050610c0a8183611106565b602085015250610c179050565b919050565b6000816020015160001415610c3357506000919050565b50515160c060009190911a1090565b600080610c4e83610c1c565b610c5757600080fd5b8251805160001a906080821015610c7357946001945092505050565b60b8821015610ca15760018560200151610c8d919061113d565b9250610c9a816001611106565b9350610cdf565b602085015160b6198301908190610cba9060019061113d565b610cc4919061113d565b9350610cd08183611106565b610cdb906001611106565b9450505b5050915091565b6020601f820104836020840160005b83811015610d1157602081028381015190830152600101610cf5565b5050505060008251602001830152505050565b60006109b982610e5b565b6000816020015160001415610d4657506000919050565b8151805160001a906080821015610d61575060009392505050565b60b8821080610d7c575060c08210158015610d7c575060f882105b15610d8b575060019392505050565b60c0821015610db257610d9f60b78361113d565b610daa906001611106565b949350505050565b610d9f60f78361113d565b8051600090811a6080811015610dd65760019150610e55565b60b8811015610dfc57610dea60808261113d565b610df5906001611106565b9150610e55565b60c0811015610e2557600183015160b76020839003016101000a9004810160b519019150610e55565b60f8811015610e3957610dea60c08261113d565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b6000610e6682610c1c565b610e6f57600080fd5b600080610e7b84610c42565b90925090506020811115610e8e57600080fd5b80610e9d575060009392505050565b806020036101000a82510492505050919050565b60008083601f840112610ec357600080fd5b50813567ffffffffffffffff811115610edb57600080fd5b602083019150836020828501011115610ef357600080fd5b9250929050565b600060208284031215610f0c57600080fd5b81516001600160a01b0381168114610f2357600080fd5b9392505050565b600060208284031215610f3c57600080fd5b81518015158114610f2357600080fd5b60008060008060008060808789031215610f6557600080fd5b8635955060208701359450604087013567ffffffffffffffff80821115610f8b57600080fd5b610f978a838b01610eb1565b90965094506060890135915080821115610fb057600080fd5b50610fbd89828a01610eb1565b979a9699509497509295939492505050565b60008151808452610fe7816020860160208601611154565b601f01601f19169290920160200192915050565b8481526bffffffffffffffffffffffff198460601b16602082015282603482015260008251611031816054850160208701611154565b9190910160540195945050505050565b8681526000602060018060a01b0388168184015286604084015260c0606084015261106f60c0840187610fcf565b838103608085015285518082528287019183019060005b818110156110a257835183529284019291840191600101611086565b505084810360a086015285518082528382019250600581901b8201840184880160005b838110156110f357601f198584030186526110e1838351610fcf565b958701959250908601906001016110c5565b50909d9c50505050505050505050505050565b600082198211156111195761111961119f565b500190565b60008160001904831182151516156111385761113861119f565b500290565b60008282101561114f5761114f61119f565b500390565b60005b8381101561116f578181015183820152602001611157565b8381111561117e576000848401525b50505050565b60006000198214156111985761119861119f565b5060010190565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052604160045260246000fdfea2646970667358221220dc82fa506d69b6c149ba579b2c71d1dfd80d563c6b638248c07341f4ed2a358f64736f6c63430008050033";

    public static final String FUNC_DECODEANDVERIFYEVENT = "decodeAndVerifyEvent";

    @Deprecated
    protected CrosschainVerifierTxRoot(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CrosschainVerifierTxRoot(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CrosschainVerifierTxRoot(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CrosschainVerifierTxRoot(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static CrosschainVerifierTxRoot load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrosschainVerifierTxRoot(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CrosschainVerifierTxRoot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrosschainVerifierTxRoot(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CrosschainVerifierTxRoot load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CrosschainVerifierTxRoot(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CrosschainVerifierTxRoot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CrosschainVerifierTxRoot(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CrosschainVerifierTxRoot> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _registrar, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrosschainVerifierTxRoot.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CrosschainVerifierTxRoot> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _registrar, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrosschainVerifierTxRoot.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CrosschainVerifierTxRoot> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _registrar, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrosschainVerifierTxRoot.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CrosschainVerifierTxRoot> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _registrar, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrosschainVerifierTxRoot.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
