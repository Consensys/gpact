package net.consensys.gpact.helpers;

import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.messaging.MessagingManagerGroupInterface;
import net.consensys.gpact.messaging.eventattest.AttestorSignerGroup;
import net.consensys.gpact.messaging.eventattest.AttestorSignerManagerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootRelayerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;


public class TwentyActsExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(TwentyActsExampleSystemManager.class);

  private final String propsFileName;

  private BlockchainConfig root;
  private BlockchainConfig bc2;
  private BlockchainConfig bc3;

  private MessagingManagerGroupInterface messagingManagerGroup = null;

  public TwentyActsExampleSystemManager(String propertiesFileName) {
    this.propsFileName = propertiesFileName;
  }

  public void standardExampleConfig(int numberOfBlockchains) throws Exception {
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



    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    switch (consensusMethodology) {
      case EVENT_SIGNING:
        AttestorSignerGroup attestorSignerGroup = new AttestorSignerGroup();
        messagingManagerGroup = new AttestorSignerManagerGroup();

        addBcAttestorSign(
            messagingManagerGroup, attestorSignerGroup, creds, this.root);
        addBcAttestorSign(
            messagingManagerGroup, attestorSignerGroup, creds, this.bc2);
        if (numberOfBlockchains == 3) {
          addBcAttestorSign(
              messagingManagerGroup,
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
            relayerGroup,
            txRootTransferGroup,
            creds,
            this.root);
        addBcTxRootSign(
            messagingManagerGroup,
            relayerGroup,
            txRootTransferGroup,
            creds,
            this.bc2);
        if (numberOfBlockchains == 3) {
          addBcTxRootSign(
              messagingManagerGroup,
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

  public MessagingManagerGroupInterface getMessagingManagerGroup() {
    return this.messagingManagerGroup;
  }



  private void addBcAttestorSign(
      MessagingManagerGroupInterface messagingManagerGroup,
      AttestorSignerGroup attestorSignerGroup,
      Credentials creds,
      BlockchainConfig bc)
      throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    attestorSignerGroup.addBlockchain(bc.bcId);
  }

  private void addBcTxRootSign(
      MessagingManagerGroupInterface messagingManagerGroup,
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
  }
}
