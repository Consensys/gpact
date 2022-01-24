package net.consensys.gpact.soliditywrappers.messaging.eventrelay;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class EventRelayVerifier extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610f27380380610f2783398101604081905261002f9161007c565b600080546001600160a01b039384166001600160a01b031991821617909155600180549290931691161790556100af565b80516001600160a01b038116811461007757600080fd5b919050565b6000806040838503121561008f57600080fd5b61009883610060565b91506100a660208401610060565b90509250929050565b610e69806100be6000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80631cd8253a1461003b5780634c1ce90214610050575b600080fd5b61004e610049366004610a71565b610063565b005b61004e61005e366004610b09565b6107f2565b60608060608060006100aa87878080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525092506108ef915050565b90506100bd605563ffffffff8316610b5e565b6100c8906004610b7d565b861461011b5760405162461bcd60e51b815260206004820152601a60248201527f5369676e617475726520696e636f7272656374206c656e67746800000000000060448201526064015b60405180910390fd5b8063ffffffff1667ffffffffffffffff81111561013a5761013a610b95565b604051908082528060200260200182016040528015610163578160200160208202803683370190505b5094508063ffffffff1667ffffffffffffffff81111561018557610185610b95565b6040519080825280602002602001820160405280156101ae578160200160208202803683370190505b5093508063ffffffff1667ffffffffffffffff8111156101d0576101d0610b95565b6040519080825280602002602001820160405280156101f9578160200160208202803683370190505b5092508063ffffffff1667ffffffffffffffff81111561021b5761021b610b95565b604051908082528060200260200182016040528015610244578160200160208202803683370190505b509150600460005b8263ffffffff168110156104325761029b89898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610955915050565b8782815181106102ad576102ad610bab565b6001600160a01b03909216602092830291909101909101526102d0601483610b7d565b915061031389898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061095d915050565b86828151811061032557610325610bab565b60200260200101818152505060208261033e9190610b7d565b915061038189898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061095d915050565b85828151811061039357610393610bab565b6020026020010181815250506020826103ac9190610b7d565b91506103ef89898080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508692506109c2915050565b84828151811061040157610401610bab565b60ff9092166020928302919091019091015261041e600183610b7d565b91508061042a81610bc1565b91505061024c565b505060008b8b7f59f736dc5e15c4b12526487502645403b0a4316d82eba7e9ecdc2a050c10ad278c8c60405160200161046f959493929190610bdc565b60408051601f198184030181529082905260005463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b906104b8908f908a908a908a908a908990600401610cd1565b60206040518083038186803b1580156104d057600080fd5b505afa1580156104e4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105089190610d7a565b508051602082012060005b8363ffffffff1681101561065d5760026000838152602001908152602001600020600301600089838151811061054b5761054b610bab565b6020908102919091018101516001600160a01b031682528101919091526040016000205460ff161561057c5761064b565b60016002600084815260200190815260200160002060030160008a84815181106105a8576105a8610bab565b60200260200101516001600160a01b03166001600160a01b0316815260200190815260200160002060006101000a81548160ff0219169083151502179055506002600083815260200190815260200160002060020188828151811061060f5761060f610bab565b60209081029190910181015182546001810184556000938452919092200180546001600160a01b0319166001600160a01b039092169190911790555b8061065581610bc1565b915050610513565b5060008181526002602052604090206001015460ff161561068457505050505050506107ea565b60008060009054906101000a90046001600160a01b03166001600160a01b031663a64ce1998f6040518263ffffffff1660e01b81526004016106c891815260200190565b60206040518083038186803b1580156106e057600080fd5b505afa1580156106f4573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107189190610da3565b6000838152600260208190526040909120015490915081116107e15760016002600084815260200190815260200160002060010160006101000a81548160ff021916908315150217905550600160009054906101000a90046001600160a01b03166001600160a01b031663408840528f8f8f8f8f8f6040518763ffffffff1660e01b81526004016107ae96959493929190610de5565b600060405180830381600087803b1580156107c857600080fd5b505af11580156107dc573d6000803e3d6000fd5b505050505b50505050505050505b505050505050565b60008484604051610804929190610e23565b6040519081900381206000805463a64ce19960e01b8452600484018b9052919350916001600160a01b039091169063a64ce1999060240160206040518083038186803b15801561085357600080fd5b505afa158015610867573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061088b9190610da3565b600083815260026020819052604090912001549091508111156108e55760405162461bcd60e51b81526020600482015260126024820152714e6f7420656e6f756768207369676e65727360701b6044820152606401610112565b5050505050505050565b60006108fc826004610b7d565b8351101561094c5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e743332290000006044820152606401610112565b50016004015190565b016014015190565b60008060005b60208110156109ba57610977816008610b5e565b856109828387610b7d565b8151811061099257610992610bab565b01602001516001600160f81b031916901c9190911790806109b281610bc1565b915050610963565b509392505050565b60006109cf826001610b7d565b83511015610a1f5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e743829000000006044820152606401610112565b50016001015190565b60008083601f840112610a3a57600080fd5b50813567ffffffffffffffff811115610a5257600080fd5b602083019150836020828501011115610a6a57600080fd5b9250929050565b60008060008060008060808789031215610a8a57600080fd5b8635955060208701356001600160a01b0381168114610aa857600080fd5b9450604087013567ffffffffffffffff80821115610ac557600080fd5b610ad18a838b01610a28565b90965094506060890135915080821115610aea57600080fd5b50610af789828a01610a28565b979a9699509497509295939492505050565b60008060008060008060808789031215610b2257600080fd5b8635955060208701359450604087013567ffffffffffffffff80821115610ac557600080fd5b634e487b7160e01b600052601160045260246000fd5b6000816000190483118215151615610b7857610b78610b48565b500290565b60008219821115610b9057610b90610b48565b500190565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b6000600019821415610bd557610bd5610b48565b5060010190565b8581526bffffffffffffffffffffffff198560601b1660208201528360348201528183605483013760009101605401908152949350505050565b600081518084526020808501945080840160005b83811015610c4657815187529582019590820190600101610c2a565b509495945050505050565b600081518084526020808501945080840160005b83811015610c4657815160ff1687529582019590820190600101610c65565b6000815180845260005b81811015610caa57602081850181015186830182015201610c8e565b81811115610cbc576000602083870101525b50601f01601f19169290920160200192915050565b600060c08201888352602060c08185015281895180845260e086019150828b01935060005b81811015610d1b5784516001600160a01b031683529383019391830191600101610cf6565b50508481036040860152610d2f818a610c16565b925050508281036060840152610d458187610c16565b90508281036080840152610d598186610c51565b905082810360a0840152610d6d8185610c84565b9998505050505050505050565b600060208284031215610d8c57600080fd5b81518015158114610d9c57600080fd5b9392505050565b600060208284031215610db557600080fd5b5051919050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b8681526001600160a01b0386166020820152608060408201819052600090610e109083018688610dbc565b8281036060840152610d6d818587610dbc565b818382376000910190815291905056fea2646970667358221220f5cd3f1268add49fc0b2f62014a3f53330867263817afc78690bc338d8dcb3e164736f6c63430008090033";

    public static final String FUNC_DECODEANDVERIFYEVENT = "decodeAndVerifyEvent";

    public static final String FUNC_RELAYEVENT = "relayEvent";

    @Deprecated
    protected EventRelayVerifier(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EventRelayVerifier(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EventRelayVerifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EventRelayVerifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> relayEvent(BigInteger _sourceBlockchainId, String _sourceCbcAddress, byte[] _eventData, byte[] _signature) {
        final Function function = new Function(
                FUNC_RELAYEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_sourceBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _sourceCbcAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_eventData), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EventRelayVerifier load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventRelayVerifier(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EventRelayVerifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EventRelayVerifier(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EventRelayVerifier load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EventRelayVerifier(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EventRelayVerifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EventRelayVerifier(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EventRelayVerifier> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _registrar, String _functionCall) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _functionCall)));
        return deployRemoteCall(EventRelayVerifier.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<EventRelayVerifier> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _registrar, String _functionCall) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _functionCall)));
        return deployRemoteCall(EventRelayVerifier.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EventRelayVerifier> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _registrar, String _functionCall) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _functionCall)));
        return deployRemoteCall(EventRelayVerifier.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EventRelayVerifier> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _registrar, String _functionCall) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar), 
                new org.web3j.abi.datatypes.Address(160, _functionCall)));
        return deployRemoteCall(EventRelayVerifier.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
