package net.consensys.gpact.cbc;

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.BlockchainInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.CrossBlockchainConsensusType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CbcManager {
  static final Logger LOG = LogManager.getLogger(CbcManager.class);

  CrossBlockchainConsensusType consensusMethodology;
  Map<BlockchainId, BcHolder> blockchains = new HashMap<>();

  public CbcManager(CrossBlockchainConsensusType consensusMethodology) {
    this.consensusMethodology = consensusMethodology;
  }

  public void addBlockchainAndDeployContracts(Credentials creds, BlockchainInfo bcInfo) throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }
    LOG.debug("Deploying Cross-Blockchain Control contracts for blockchain id {}",blockchainId);

    BcHolder holder = new BcHolder();
    switch (this.consensusMethodology) {
      case TRANSACTION_RECEIPT_SIGNING:
        holder.cbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
        holder.cbc = holder.cbcTxRootTransfer;
        break;
      case EVENT_SIGNING:
        holder.cbcSignedEvents = new CrossBlockchainControlSignedEvents(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
        holder.cbc = holder.cbcSignedEvents;
        break;
      default:
        throw new RuntimeException("Not supported yet: " + this.consensusMethodology);
    }

    holder.cbc.deployContracts();
    holder.cbcContractAddress = holder.cbc.getCbcContractAddress();

    this.blockchains.put(blockchainId, holder);
  }

  public void addBlockchain(Credentials creds, BlockchainInfo bcInfo, List<String> addresses) throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    BcHolder holder = new BcHolder();
    switch (this.consensusMethodology) {
      case TRANSACTION_RECEIPT_SIGNING:
        holder.cbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(
                creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
        holder.cbc = holder.cbcTxRootTransfer;
        break;
      case EVENT_SIGNING:
        holder.cbcSignedEvents = new CrossBlockchainControlSignedEvents(
                creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
        holder.cbc = holder.cbcSignedEvents;
        break;
      default:
        throw new RuntimeException("Not supported yet: " + this.consensusMethodology);
    }

    holder.cbc.loadContracts(addresses);
    holder.cbcContractAddress = addresses.get(0);

    this.blockchains.put(blockchainId, holder);
  }


  public void registerSignerOnAllBlockchains(AnIdentity signer) throws Exception {
    for (BlockchainId bcId1: this.blockchains.keySet()) {
      registerSigner(signer, bcId1);
    }
  }

  // TODO: Replace the signers in the BcHolder when the Attestor code is written
  public void registerSigner(AnIdentity signer, BlockchainId bcId1) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    BcHolder holder = this.blockchains.get(bcId1);
    holder.signers.add(signer);

    // Add the signer (their address / public key) to each blockchain
    for (BlockchainId bcId2: this.blockchains.keySet()) {
      BcHolder holder2 = this.blockchains.get(bcId2);
      holder2.cbc.registerSigner(signer, bcId1);
    }
  }

  public void addSignerOnAllBlockchains(AnIdentity signer) throws Exception {
    for (BlockchainId bcId1: this.blockchains.keySet()) {
      addSigner(signer, bcId1);
    }
  }
  public void addSigner(AnIdentity signer, BlockchainId bcId1) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    BcHolder holder = this.blockchains.get(bcId1);
    holder.signers.add(signer);
  }



  public AbstractCbc getCbcContract(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbc;
  }

  public CrossBlockchainControlTxReceiptRootTransfer getCbcContractTxRootTransfer(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbcTxRootTransfer;
  }

  public CrossBlockchainControlSignedEvents getCbcContractSignedEvents(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbcSignedEvents;
  }

  public String getCbcAddress(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbcContractAddress;
  }

  public List<String> getInfrastructureAddresses(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbc.getContractAddresses();
  }


  public AnIdentity[] getSigners(BlockchainId bcId) {
    check(bcId);
    List<AnIdentity> signers = this.blockchains.get(bcId).signers;
    return signers.toArray(new AnIdentity[]{});
  }

  public Set<BlockchainId> getAllBlockchainIds() {
    return this.blockchains.keySet();
  }

  private void check(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
  }

  public void setupCrosschainTrust(AnIdentity initialSigner) throws Exception {
    for (BlockchainId bcId1: this.blockchains.keySet()) {
      BcHolder holder1 = this.blockchains.get(bcId1);

      for (BlockchainId bcId2: this.blockchains.keySet()) {
        BcHolder holder2 = this.blockchains.get(bcId2);
        holder2.signers.add(initialSigner);
        holder1.cbc.addBlockchain(bcId2, holder2.cbcContractAddress, initialSigner.getAddress());
      }
    }
  }

  private static class BcHolder {
    CrossBlockchainControlTxReceiptRootTransfer cbcTxRootTransfer;
    CrossBlockchainControlSignedEvents cbcSignedEvents;
    AbstractCbc cbc;
    String cbcContractAddress;
    List<AnIdentity> signers = new ArrayList<>();
  }

}
