package net.consensys.gpact.helpers;

import java.util.ArrayList;
import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.messaging.MessagingManagerGroupInterface;
import net.consensys.gpact.messaging.eventattest.AttestorSignerGroup;
import net.consensys.gpact.messaging.eventattest.AttestorSignerManagerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootRelayerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class SfcExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(SfcExampleSystemManager.class);

  private final String propsFileName;

  private BlockchainConfig root;
  private BlockchainConfig bc2;
  private BlockchainConfig bc3;

  private CrossControlManagerGroup crossControlManagerGroup;

  public SfcExampleSystemManager(String propertiesFileName) {
    this.propsFileName = propertiesFileName;
  }

  public void sfcStandardExampleConfig(int numberOfBlockchains) throws Exception {
    // Less that two blockchains doesn't make sense for crosschain.
    // The test infrasturcture only supports three blockchains at present.
    if (!(numberOfBlockchains == 2 || numberOfBlockchains == 3)) {
      throw new IllegalArgumentException(
          "Number of blockchains must be two or three. Requested: " + numberOfBlockchains);
    }

    PropertiesLoader propsLoader = new PropertiesLoader(this.propsFileName);
    Credentials creds = CredentialsCreator.createCredentials();
    this.root = propsLoader.getBlockchainInfo("ROOT");
    this.bc2 = propsLoader.getBlockchainInfo("BC2");
    this.bc3 = propsLoader.getBlockchainInfo("BC3");
    CrossBlockchainConsensusType consensusMethodology = propsLoader.getConsensusMethodology();
    StatsHolder.log(consensusMethodology.name());

    // To keep the example simple, just have one signer for all blockchains.
    AnIdentity globalSigner = new AnIdentity();

    MessagingManagerGroupInterface messagingManagerGroup = null;
    this.crossControlManagerGroup =
        CrosschainProtocols.getFunctionCallInstance(CrosschainProtocols.SFC);

    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    switch (consensusMethodology) {
      case EVENT_SIGNING:
        AttestorSignerGroup attestorSignerGroup = new AttestorSignerGroup();
        messagingManagerGroup = new AttestorSignerManagerGroup();

        addBcAttestorSign(
            messagingManagerGroup, crossControlManagerGroup, attestorSignerGroup, creds, this.root);
        addBcAttestorSign(
            messagingManagerGroup, crossControlManagerGroup, attestorSignerGroup, creds, this.bc2);
        if (numberOfBlockchains == 3) {
          addBcAttestorSign(
              messagingManagerGroup,
              crossControlManagerGroup,
              attestorSignerGroup,
              creds,
              this.bc3);
        }
        attestorSignerGroup.addSignerOnAllBlockchains(globalSigner);
        break;
      case TRANSACTION_RECEIPT_SIGNING:
        TxRootRelayerGroup relayerGroup = new TxRootRelayerGroup();
        TxRootTransferGroup txRootTransferGroup = new TxRootTransferGroup();
        messagingManagerGroup = new TxRootTransferManagerGroup();

        addBcTxRootSign(
            messagingManagerGroup,
            crossControlManagerGroup,
            relayerGroup,
            txRootTransferGroup,
            creds,
            this.root);
        addBcTxRootSign(
            messagingManagerGroup,
            crossControlManagerGroup,
            relayerGroup,
            txRootTransferGroup,
            creds,
            this.bc2);
        if (numberOfBlockchains == 3) {
          addBcTxRootSign(
              messagingManagerGroup,
              crossControlManagerGroup,
              relayerGroup,
              txRootTransferGroup,
              creds,
              this.bc3);
        }
        relayerGroup.addSignerOnAllBlockchains(globalSigner);
        break;
      default:
        throw new Exception("Unknown messaging: " + consensusMethodology);
    }

    messagingManagerGroup.registerFirstSignerOnAllBlockchains(globalSigner.getAddress());
    // Have each Crosschain Control contract trust the Crosschain Control
    // contracts on the other blockchains.
    setupCrosschainTrust(crossControlManagerGroup, messagingManagerGroup);
  }

  public String getExecutionEngine() {
    // Serial execution engine is the only type used with SFC.
    return CrosschainProtocols.SERIAL;
  }

  public BlockchainConfig getRootBcInfo() {
    return this.root;
  }

  public BlockchainConfig getBc2Info() {
    return this.bc2;
  }

  public BlockchainConfig getBc3Info() {
    return this.bc3;
  }

  public CrossControlManagerGroup getCrossControlManagerGroup() {
    return this.crossControlManagerGroup;
  }

  private void setupCrosschainTrust(
      CrossControlManagerGroup crossControlManagerGroup,
      MessagingManagerGroupInterface messagingManagerGroup)
      throws Exception {
    ArrayList<BlockchainId> bcs = crossControlManagerGroup.getAllBlockchainIds();

    for (BlockchainId bcId1 : bcs) {
      CrossControlManager crossManager1 = crossControlManagerGroup.getCbcManager(bcId1);
      String cbcContractAddress = crossManager1.getCbcContractAddress();
      String verifierContractAddress = messagingManagerGroup.getVerifierAddress(bcId1);

      for (BlockchainId bcId2 : bcs) {
        CrossControlManager crossManager2 = crossControlManagerGroup.getCbcManager(bcId2);
        crossManager2.addRemoteBlockchain(bcId1, cbcContractAddress, verifierContractAddress);
      }
    }
  }

  private void addBcAttestorSign(
      MessagingManagerGroupInterface messagingManagerGroup,
      CrossControlManagerGroup crossControlManagerGroup,
      AttestorSignerGroup attestorSignerGroup,
      Credentials creds,
      BlockchainConfig bc)
      throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    attestorSignerGroup.addBlockchain(bc.bcId);
    crossControlManagerGroup.addBlockchainAndDeployContracts(
        creds, bc, attestorSignerGroup.getVerifier(bc.bcId));
  }

  private void addBcTxRootSign(
      MessagingManagerGroupInterface messagingManagerGroup,
      CrossControlManagerGroup crossControlManagerGroup,
      TxRootRelayerGroup relayerGroup,
      TxRootTransferGroup txRootTransferGroup,
      Credentials creds,
      BlockchainConfig bc)
      throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    txRootTransferGroup.addBlockchain(relayerGroup, creds, bc);
    relayerGroup.loadContractForBlockchain(
        creds,
        bc,
        ((TxRootTransferManagerGroup) messagingManagerGroup).getTxRootContractAddress(bc.bcId));
    crossControlManagerGroup.addBlockchainAndDeployContracts(
        creds, bc, txRootTransferGroup.getVerifier(bc.bcId));
  }
}
