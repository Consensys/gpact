package tech.pegasys.ltacfc.cbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensusType;
import tech.pegasys.ltacfc.common.PropertiesLoader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CbcManager {
  static final Logger LOG = LogManager.getLogger(CbcManager.class);

  CrossBlockchainConsensusType consensusMethodology;
  Map<BigInteger, BcHolder> blockchains = new HashMap<>();

  public CbcManager(CrossBlockchainConsensusType consensusMethodology) {
    this.consensusMethodology = consensusMethodology;
  }

  public void addBlockchainAndDeployContracts(Credentials creds, PropertiesLoader.BlockchainInfo bcInfo) throws Exception {
    BigInteger blockchainId = new BigInteger(bcInfo.bcId, 16);
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }
    LOG.info("Deploying Cross-Blockchain Control contracts for blockchain id 0x{}",blockchainId.toString(16));

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

  public void registerSignerOnAllBlockchains(AnIdentity signer) throws Exception {
    for (BigInteger bcId1: this.blockchains.keySet()) {
      registerSigner(signer, bcId1);
    }
  }

  public void registerSigner(AnIdentity signer, BigInteger bcId1) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    BcHolder holder = this.blockchains.get(bcId1);
    holder.signers.add(signer);

    // Add the signer (their address / public key) to each blockchain
    for (BigInteger bcId2: this.blockchains.keySet()) {
      BcHolder holder2 = this.blockchains.get(bcId2);
      holder2.cbc.registerSigner(signer, bcId1);
    }
  }


  public AbstractCbc getCbcContract(BigInteger bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
    }
    return this.blockchains.get(bcId).cbc;
  }

  public CrossBlockchainControlTxReceiptRootTransfer getCbcContractTxRootTransfer(BigInteger bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
    }
    return this.blockchains.get(bcId).cbcTxRootTransfer;
  }

  public CrossBlockchainControlSignedEvents getCbcContractSignedEvents(BigInteger bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
    }
    return this.blockchains.get(bcId).cbcSignedEvents;
  }

  public String getCbcAddress(BigInteger bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
    }
    return this.blockchains.get(bcId).cbcContractAddress;
  }

  public AnIdentity[] getSigners(BigInteger bcId) {
    check(bcId);
    List<AnIdentity> signers = this.blockchains.get(bcId).signers;
    return signers.toArray(new AnIdentity[]{});
  }

  public Set<BigInteger> getAllBlockchainIds() {
    return this.blockchains.keySet();
  }

  private void check(BigInteger bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
    }
  }

  public void setupCrosschainTrust() throws Exception {
    for (BigInteger bcId1: this.blockchains.keySet()) {
      BcHolder holder1 = this.blockchains.get(bcId1);

      for (BigInteger bcId2: this.blockchains.keySet()) {
        BcHolder holder2 = this.blockchains.get(bcId2);
        holder1.cbc.addBlockchain(bcId2, holder2.cbcContractAddress);
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
