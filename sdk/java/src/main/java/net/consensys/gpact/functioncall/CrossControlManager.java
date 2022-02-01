package net.consensys.gpact.functioncall;

import net.consensys.gpact.common.BlockchainId;

public interface CrossControlManager {

  /**
   * All events from a remote blockchain to used with the crosschain control contract.
   *
   * @param bcId Remote blockchain identifier.
   * @param cbcContractAddress Address of crosschain control contract on remove blockchain.
   * @param verifierContractAddress Address of message verification contract on this blockchain that
   *     can verify signed events / proofs from the remote blockchain.
   * @throws Exception If an issue occurs while adding the information to the crosschain control
   *     contract.
   */
  void addBlockchain(BlockchainId bcId, String cbcContractAddress, String verifierContractAddress)
      throws Exception;

  /** @return Address of the Crosschain Control contract. */
  String getCbcContractAddress();
}
