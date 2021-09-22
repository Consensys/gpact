package net.consensys.gpact.cbc;

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.BlockchainInfo;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CrossControlManagerGroup {
  static final Logger LOG = LogManager.getLogger(CrossControlManagerGroup.class);

  Map<BlockchainId, BcHolder> blockchains = new HashMap<>();


  public void addBlockchainAndDeployContracts(Credentials creds, BlockchainInfo bcInfo, MessagingVerificationInterface messageVerification) throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }
    LOG.debug("Deploying Cross-Blockchain Control contract for blockchain id {}",blockchainId);

    BcHolder holder = new BcHolder();
    holder.cbc = new CrossControlManager(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
    holder.cbc.deployContracts();
    holder.cbcContractAddress = holder.cbc.getCbcContractAddress();
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }

  public void addBlockchainAndLoadContracts(Credentials creds, BlockchainInfo bcInfo, List<String> addresses, MessagingVerificationInterface messageVerification) throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    BcHolder holder = new BcHolder();
    holder.cbc = new CrossControlManager(
                creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);

    holder.cbc.loadContracts(addresses);
    holder.cbcContractAddress = addresses.get(0);
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }



  public CrossControlManager getCbcContract(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbc;
  }

  public MessagingVerificationInterface getMessageVerification(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).ver;
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

  public ArrayList<BlockchainId> getAllBlockchainIds() {
    ArrayList<BlockchainId> bcIds = new ArrayList<>();
    bcIds.addAll(this.blockchains.keySet());
    return bcIds;
  }



  private static class BcHolder {
    CrossControlManager cbc;
    String cbcContractAddress;
    MessagingVerificationInterface ver;
  }

}
