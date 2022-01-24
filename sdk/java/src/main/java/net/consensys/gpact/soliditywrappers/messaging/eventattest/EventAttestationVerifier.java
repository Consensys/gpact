package net.consensys.gpact.soliditywrappers.messaging.eventattest;

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
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.7-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class EventAttestationVerifier extends Contract {
  public static final String BINARY =
      "608060405234801561001057600080fd5b5060405161098c38038061098c83398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b6108f9806100936000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c80634c1ce90214610030575b600080fd5b61004361003e366004610641565b610045565b005b606080606080600061008c87878080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525092506104bf915050565b905061009f605563ffffffff83166106da565b6100aa9060046106f9565b86146100fd5760405162461bcd60e51b815260206004820152601a60248201527f5369676e617475726520696e636f7272656374206c656e67746800000000000060448201526064015b60405180910390fd5b8063ffffffff1667ffffffffffffffff81111561011c5761011c610711565b604051908082528060200260200182016040528015610145578160200160208202803683370190505b5094508063ffffffff1667ffffffffffffffff81111561016757610167610711565b604051908082528060200260200182016040528015610190578160200160208202803683370190505b5093508063ffffffff1667ffffffffffffffff8111156101b2576101b2610711565b6040519080825280602002602001820160405280156101db578160200160208202803683370190505b5092508063ffffffff1667ffffffffffffffff8111156101fd576101fd610711565b604051908082528060200260200182016040528015610226578160200160208202803683370190505b509150600460005b8263ffffffff168110156104145761027d89898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610525915050565b87828151811061028f5761028f610727565b6001600160a01b03909216602092830291909101909101526102b26014836106f9565b91506102f589898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061052d915050565b86828151811061030757610307610727565b60200260200101818152505060208261032091906106f9565b915061036389898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525086925061052d915050565b85828151811061037557610375610727565b60200260200101818152505060208261038e91906106f9565b91506103d189898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250869250610592915050565b8482815181106103e3576103e3610727565b60ff909216602092830291909101909101526104006001836106f9565b91508061040c8161073d565b91505061022e565b50505060008054906101000a90046001600160a01b03166001600160a01b031663ad107bb48b868686868e8e6040518863ffffffff1660e01b815260040161046297969594939291906107ef565b60206040518083038186803b15801561047a57600080fd5b505afa15801561048e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104b2919061089a565b5050505050505050505050565b60006104cc8260046106f9565b8351101561051c5760405162461bcd60e51b815260206004820152601d60248201527f736c6963696e67206f7574206f662072616e6765202875696e7433322900000060448201526064016100f4565b50016004015190565b016014015190565b60008060005b602081101561058a576105478160086106da565b8561055283876106f9565b8151811061056257610562610727565b01602001516001600160f81b031916901c9190911790806105828161073d565b915050610533565b509392505050565b600061059f8260016106f9565b835110156105ef5760405162461bcd60e51b815260206004820152601c60248201527f736c6963696e67206f7574206f662072616e6765202875696e7438290000000060448201526064016100f4565b50016001015190565b60008083601f84011261060a57600080fd5b50813567ffffffffffffffff81111561062257600080fd5b60208301915083602082850101111561063a57600080fd5b9250929050565b6000806000806000806080878903121561065a57600080fd5b8635955060208701359450604087013567ffffffffffffffff8082111561068057600080fd5b61068c8a838b016105f8565b909650945060608901359150808211156106a557600080fd5b506106b289828a016105f8565b979a9699509497509295939492505050565b634e487b7160e01b600052601160045260246000fd5b60008160001904831182151516156106f4576106f46106c4565b500290565b6000821982111561070c5761070c6106c4565b500190565b634e487b7160e01b600052604160045260246000fd5b634e487b7160e01b600052603260045260246000fd5b6000600019821415610751576107516106c4565b5060010190565b600081518084526020808501945080840160005b838110156107885781518752958201959082019060010161076c565b509495945050505050565b600081518084526020808501945080840160005b8381101561078857815160ff16875295820195908201906001016107a7565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b600060c08201898352602060c081850152818a5180845260e086019150828c01935060005b818110156108395784516001600160a01b031683529383019391830191600101610814565b5050848103604086015261084d818b610758565b9250505082810360608401526108638188610758565b905082810360808401526108778187610793565b905082810360a084015261088c8185876107c6565b9a9950505050505050505050565b6000602082840312156108ac57600080fd5b815180151581146108bc57600080fd5b939250505056fea2646970667358221220c3447d6777d015189454e826d40f2214cc356b155b8a16f7c5a0f63f5a0a292164736f6c63430008090033";

  public static final String FUNC_DECODEANDVERIFYEVENT = "decodeAndVerifyEvent";

  @Deprecated
  protected EventAttestationVerifier(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected EventAttestationVerifier(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected EventAttestationVerifier(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected EventAttestationVerifier(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  @Deprecated
  public static EventAttestationVerifier load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new EventAttestationVerifier(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static EventAttestationVerifier load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new EventAttestationVerifier(
        contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static EventAttestationVerifier load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new EventAttestationVerifier(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static EventAttestationVerifier load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new EventAttestationVerifier(
        contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<EventAttestationVerifier> deploy(
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider,
      String _registrar) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
    return deployRemoteCall(
        EventAttestationVerifier.class,
        web3j,
        credentials,
        contractGasProvider,
        BINARY,
        encodedConstructor);
  }

  public static RemoteCall<EventAttestationVerifier> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider,
      String _registrar) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
    return deployRemoteCall(
        EventAttestationVerifier.class,
        web3j,
        transactionManager,
        contractGasProvider,
        BINARY,
        encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<EventAttestationVerifier> deploy(
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit,
      String _registrar) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
    return deployRemoteCall(
        EventAttestationVerifier.class,
        web3j,
        credentials,
        gasPrice,
        gasLimit,
        BINARY,
        encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<EventAttestationVerifier> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit,
      String _registrar) {
    String encodedConstructor =
        FunctionEncoder.encodeConstructor(
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
    return deployRemoteCall(
        EventAttestationVerifier.class,
        web3j,
        transactionManager,
        gasPrice,
        gasLimit,
        BINARY,
        encodedConstructor);
  }
}
