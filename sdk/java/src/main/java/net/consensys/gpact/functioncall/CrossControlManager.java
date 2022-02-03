package net.consensys.gpact.functioncall;

import net.consensys.gpact.common.BlockchainId;

/** Manages a Crosschain Control Contract on a single blockchain. */
public interface CrossControlManager {

  /**
   * Allow events from a remote blockchain to be used with the crosschain control contract on the
   * blockchain managed by this class.
   *
   * @param bcId Remote blockchain identifier.
   * @param cbcContractAddress Address of crosschain control contract on remote blockchain.
   * @param verifierContractAddress Address of message verification contract on this blockchain that
   *     can verify signed events / proofs from the remote blockchain.
   * @throws Exception If an issue occurs while adding the information to the crosschain control
   *     contract.
   */
  void addRemoteBlockchain(
      BlockchainId bcId, String cbcContractAddress, String verifierContractAddress)
      throws Exception;

  /** @return Address of the Crosschain Control contract. */
  String getCbcContractAddress();
}
