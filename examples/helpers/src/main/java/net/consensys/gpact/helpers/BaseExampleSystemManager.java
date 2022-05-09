/*
 * Copyright 2022 ConsenSys Software Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package net.consensys.gpact.helpers;

import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.messaging.MessagingManagerGroup;
import net.consensys.gpact.messaging.common.attestorrelayer.AttestorRelayer;
import net.consensys.gpact.messaging.eventattest.AttestorSignerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootRelayerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public abstract class BaseExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(BaseExampleSystemManager.class);

  private final String propsFileName;

  protected BlockchainConfig root;
  protected BlockchainConfig bc2;
  protected BlockchainConfig bc3;

  private CrossControlManagerGroup crossControlManagerGroup;
  protected MessagingManagerGroup messagingManagerGroup;

  public BaseExampleSystemManager(String propertiesFileName) {
    this.propsFileName = propertiesFileName;
  }

  public void standardExampleConfig(int numberOfBlockchains) throws Exception {
    // Less than two blockchains doesn't make sense for crosschain.
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
    String relayerUri = propsLoader.getRelayerUri();

    loadFunctionPayerProperties(propsLoader);

    // To keep the example simple, just have one signer for all blockchains.
    AnIdentity globalSigner = AnIdentity.createNewRandomIdentity();

    this.crossControlManagerGroup = getFunctionCallInstance();

    // Set-up GPACT contracts: Deploy Crosschain Control and Registrar contracts on
    // each blockchain.
    switch (consensusMethodology) {
      case EVENT_SIGNING:
        AttestorSignerGroup attestorSignerGroup = new AttestorSignerGroup();
        this.messagingManagerGroup =
            CrosschainProtocols.getMessagingInstance(CrosschainProtocols.ATTESTOR).get();

        List<AttestorRelayer.Source> sources = new ArrayList<>();

        addBcAttestorSign(
            messagingManagerGroup,
            crossControlManagerGroup,
            attestorSignerGroup,
            creds,
            this.root,
            sources);

        addBcAttestorSign(
            messagingManagerGroup,
            crossControlManagerGroup,
            attestorSignerGroup,
            creds,
            this.bc2,
            sources);
        if (numberOfBlockchains == 3) {
          addBcAttestorSign(
              messagingManagerGroup,
              crossControlManagerGroup,
              attestorSignerGroup,
              creds,
              this.bc3,
              sources);
        }
        attestorSignerGroup.configureRelayer(
            globalSigner,
            relayerUri,
            sources,
            this.root.dispatcherUri,
            this.root.msgStoreUrlFromDispatcher,
            this.root.msgStoreUrlFromUser);
        break;
      case TRANSACTION_RECEIPT_SIGNING:
        TxRootRelayerGroup relayerGroup = new TxRootRelayerGroup();
        TxRootTransferGroup txRootTransferGroup = new TxRootTransferGroup();
        this.messagingManagerGroup =
            CrosschainProtocols.getMessagingInstance(CrosschainProtocols.TXROOT).get();

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

  protected abstract void loadFunctionPayerProperties(PropertiesLoader propsLoader);

  protected abstract CrossControlManagerGroup getFunctionCallInstance() throws Exception;

  protected abstract String getFunctionCallImplName() throws Exception;

  private void setupCrosschainTrust(
      CrossControlManagerGroup crossControlManagerGroup,
      MessagingManagerGroup messagingManagerGroup)
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
      MessagingManagerGroup messagingManagerGroup,
      CrossControlManagerGroup crossControlManagerGroup,
      AttestorSignerGroup attestorSignerGroup,
      Credentials creds,
      BlockchainConfig bc,
      List<AttestorRelayer.Source> sources)
      throws Exception {
    messagingManagerGroup.addBlockchainAndDeployContracts(creds, bc);
    attestorSignerGroup.addBlockchain(bc.bcId, bc.msgStoreUrlFromUser);
    crossControlManagerGroup.addBlockchainAndDeployContracts(
        creds, bc, attestorSignerGroup.getVerifier(bc.bcId));
    String crosschainControlAddr = crossControlManagerGroup.getCbcAddress(bc.bcId);
    sources.add(
        new AttestorRelayer.Source(
            bc.bcId,
            crosschainControlAddr,
            getFunctionCallImplName(),
            bc.observerUri,
            bc.blockchainNodeWsUri));
  }

  private void addBcTxRootSign(
      MessagingManagerGroup messagingManagerGroup,
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
}
