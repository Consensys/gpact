package net.consensys.gpact.common;

import net.consensys.gpact.attestorsign.AttestorSignerGroup;
import net.consensys.gpact.attestorsign.AttestorSignerManagerGroup;
import net.consensys.gpact.cbc.CrossControlManager;
import net.consensys.gpact.cbc.CrossControlManagerGroup;
import net.consensys.gpact.cbc.CrosschainExecutor;
import net.consensys.gpact.cbc.engine.ExecutionEngine;
import net.consensys.gpact.cbc.engine.ParallelExecutionEngine;
import net.consensys.gpact.cbc.engine.SerialExecutionEngine;
import net.consensys.gpact.messaging.MessagingManagerGroupInterface;
import net.consensys.gpact.txroot.TxRootRelayerGroup;
import net.consensys.gpact.txroot.TxRootTransferGroup;
import net.consensys.gpact.txroot.TxRootTransferManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.List;

public class ExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(ExampleSystemManager.class);

  private String propsFileName;
  private int numBlockchains;

  private BlockchainInfo root;
  private BlockchainInfo bc2;
  private BlockchainInfo bc3;

  private ExecutionEngineType executionEngineType;
  private CrossControlManagerGroup crossControlManagerGroup;

  public ExampleSystemManager(String propertiesFileName) {
    this.propsFileName = propertiesFileName;
  }

  public void standardExampleConfig(int numberOfBlockchains) throws Exception {
    this.numBlockchains = numberOfBlockchains;
    // Less that two blockchains doesn't make sense for crosschain.
    // The test infrasturcture only supports three blockchains at present.
    if (!(numberOfBlockchains == 2 || numberOfBlockchains == 3)) {
      throw new IllegalArgumentException("Number of blockchains must be two or three. Requested: " + numberOfBlockchains);
    }

    PropertiesLoader propsLoader = new PropertiesLoader(this.propsFileName);
    Credentials creds = CredentialsCreator.createCredentials();
    this.root = propsLoader.getBlockchainInfo("ROOT");
    this.bc2 = propsLoader.getBlockchainInfo("BC2");
    this.bc3 = propsLoader.getBlockchainInfo("BC3");
    CrossBlockchainConsensusType consensusMethodology = propsLoader.getConsensusMethodology();
    StatsHolder.log(consensusMethodology.name());
    this.executionEngineType = propsLoader.getExecutionEnngine();
    StatsHolder.log(executionEngineType.name());

    // To keep the example simple, just have one signer for all blockchains.
    AnIdentity globalSigner = new AnIdentity();

    MessagingManagerGroupInterface messagingManagerGroup = null;
    this.crossControlManagerGroup = new CrossControlManagerGroup();

    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    switch (consensusMethodology) {
      case EVENT_SIGNING:
        AttestorSignerGroup attestorSignerGroup = new AttestorSignerGroup();
        messagingManagerGroup = new AttestorSignerManagerGroup();

        addBcAttestorSign(messagingManagerGroup, crossControlManagerGroup,
                attestorSignerGroup, creds, this.root);
        addBcAttestorSign(messagingManagerGroup, crossControlManagerGroup,
                attestorSignerGroup, creds, this.bc2);
        if (this.numBlockchains == 3) {
          addBcAttestorSign(messagingManagerGroup, crossControlManagerGroup,
                  attestorSignerGroup, creds, this.bc3);
        }
        attestorSignerGroup.addSignerOnAllBlockchains(globalSigner);
        break;
      case TRANSACTION_RECEIPT_SIGNING:
        TxRootRelayerGroup relayerGroup = new TxRootRelayerGroup();
        TxRootTransferGroup txRootTransferGroup = new TxRootTransferGroup();
        messagingManagerGroup = new TxRootTransferManagerGroup();

        addBcTxRootSign(messagingManagerGroup, crossControlManagerGroup,
                relayerGroup, txRootTransferGroup, creds, this.root);
        addBcTxRootSign(messagingManagerGroup, crossControlManagerGroup,
                relayerGroup, txRootTransferGroup, creds, this.bc2);
        if (this.numBlockchains == 3) {
          addBcTxRootSign(messagingManagerGroup, crossControlManagerGroup,
                  relayerGroup, txRootTransferGroup, creds, this.bc3);
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

  public ExecutionEngine getExecutionEngine(CrosschainExecutor executor) {
    switch (this.executionEngineType) {
      case SERIAL:
        return new SerialExecutionEngine(executor);
      case PARALLEL:
        return new ParallelExecutionEngine(executor);
      default:
        throw new RuntimeException("Not implemented yet");
    }
  }

  public ExecutionEngineType getExecutionEngineType() {
    return executionEngineType;
  }

  public BlockchainInfo getRootBcInfo() {
    return this.root;
  }
  public BlockchainInfo getBc2Info() {
    return this.root;
  }
  public BlockchainInfo getBc3Info() {
    return this.root;
  }

  public CrossControlManagerGroup getCrossControlManagerGroup() {
    return this.crossControlManagerGroup;
  }

  private void setupCrosschainTrust(CrossControlManagerGroup crossControlManagerGroup, MessagingManagerGroupInterface messagingManagerGroup) throws Exception {
    ArrayList<BlockchainId> bcs = crossControlManagerGroup.getAllBlockchainIds();

    for (BlockchainId bcId1: bcs) {
      CrossControlManager crossManager1 = crossControlManagerGroup.getCbcContract(bcId1);
      String cbcContractAddress = crossManager1.getCbcContractAddress();
      String verifierContractAddress = messagingManagerGroup.getVerifierAddress(bcId1);

      for (BlockchainId bcId2: bcs) {
        CrossControlManager crossManager2 = crossControlManagerGroup.getCbcContract(bcId2);
        crossManager2.addBlockchain(bcId1, cbcContractAddress, verifierContractAddress);
      }
    }
  }


  private void addBcAttestorSign(
          MessagingManagerGroupInterface messagingManagerGroup,
          CrossControlManagerGroup crossControlManagerGroup,
          AttestorSignerGroup attestorSignerGroup,
          Credentials creds,
          BlockchainInfo bc) throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    attestorSignerGroup.addBlockchain(bc.bcId);
    crossControlManagerGroup.addBlockchainAndDeployContracts(creds, bc, attestorSignerGroup.getVerifier(bc.bcId));
  }

  private void addBcTxRootSign(
          MessagingManagerGroupInterface messagingManagerGroup,
          CrossControlManagerGroup crossControlManagerGroup,
          TxRootRelayerGroup relayerGroup,
          TxRootTransferGroup txRootTransferGroup,
          Credentials creds,
          BlockchainInfo bc) throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    txRootTransferGroup.addBlockchain(relayerGroup, creds, bc);
    relayerGroup.loadContractForBlockchain(creds, bc,
            ((TxRootTransferManagerGroup)messagingManagerGroup).getTxRootContractAddress(bc.bcId));
    crossControlManagerGroup.addBlockchainAndDeployContracts(creds, bc, txRootTransferGroup.getVerifier(bc.bcId));
  }

}
