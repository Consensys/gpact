package net.consensys.gpact.functioncall;

import java.util.ArrayList;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.web3j.crypto.Credentials;

/**
 * Crosschain Manager Group implementations provide the SDK with an ability to manage Crosschain
 * Control Contracts across multiple blockchains.
 */
public interface CrossControlManagerGroup {

  /**
   * This method should be only used in testing situations. Importantly, it deploys a new Crosschain
   * Control Contract.
   *
   * <p>Configure a blockchain for use by the function call layer.
   *
   * @param creds Credentials to be used for signing transactions on the specified blockchain.
   * @param bcInfo Information about the blocchain: Blockchain id, URL, gas pricing strategy.
   * @param messageVerification Implementation used to obtain signed events or proofs of events so
   *     that the events can be used on remote blockchains.
   * @throws Exception If an issue is encountered deploying the crosschain control contract.
   */
  void addBlockchainAndDeployContracts(
      Credentials creds,
      BlockchainConfig bcInfo,
      MessagingVerificationInterface messageVerification)
      throws Exception;

  /**
   * Configure a blockchain for use by the function call layer.
   *
   * @param creds Credentials to be used for signing transactions on the specified blockchain.
   * @param bcInfo Information about the blockchain: Blockchain id, URL, gas pricing strategy.
   * @param cbcAddress Address of crosschain control contract.
   * @param messageVerification Implementation used to obtain signed events or proofs of events so
   *     that the events can be used on remote blockchains.
   * @throws Exception If an issue is encountered loading the crosschain control contract.
   */
  void addBlockchainAndLoadCbcContract(
      Credentials creds,
      BlockchainConfig bcInfo,
      String cbcAddress,
      MessagingVerificationInterface messageVerification)
      throws Exception;

  /**
   * Execute a crosschain function call.
   *
   * @param executionEngine The name of the execution engine to use. The valid options are
   *     currently: CrosschainProtocols.SERIAL or CrosschainProtocols.PARALLEL.
   * @param callTree The root of the call execution tree to execute.
   * @param timeout The timeout of the crosschain call in seconds.
   * @return Indicates if the overall call succeeds.
   * @throws CrosschainFunctionCallException if the execution engine is not supported or some other
   *     error occurs.
   */
  CrosschainCallResult executeCrosschainCall(
      String executionEngine, CallExecutionTree callTree, long timeout)
      throws CrosschainFunctionCallException;

  /**
   * Get the crosschain control manager for a particular blockchain.
   *
   * @param bcId Identifier of blockchain.
   * @return Crosschain control manager for the specified blockchain.
   * @throws CrosschainFunctionCallException if no information is available for the blockchain.
   */
  CrossControlManager getCbcManager(BlockchainId bcId) throws CrosschainFunctionCallException;

  /**
   * Get the event signer / proof generator component for a blockchain.
   *
   * @param bcId Blockchain identifier to fetch the event signer for.
   * @return Message verification / signing / proof generating component for this blockchain.
   * @throws CrosschainFunctionCallException if no information is available for the blockchain.
   */
  MessagingVerificationInterface getMessageVerification(BlockchainId bcId)
      throws CrosschainFunctionCallException;

  /**
   * Get the address of the crosschain control contract for a blockchain.
   *
   * @param bcId Blockchain identifier to fetch the address for.
   * @throws CrosschainFunctionCallException if no information is available for the blockchain.
   */
  String getCbcAddress(BlockchainId bcId) throws CrosschainFunctionCallException;

  /**
   * Get a list of all blockchains supported by this instance.
   *
   * @return Ordered list of all blockchain identifiers.
   */
  ArrayList<BlockchainId> getAllBlockchainIds();
}
