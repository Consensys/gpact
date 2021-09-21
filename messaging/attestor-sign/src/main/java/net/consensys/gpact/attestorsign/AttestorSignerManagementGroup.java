/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.attestorsign;

import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.*;


/**
 * Manage multiple blockchains, each holding a set of registrar and verification contracts
 */
public class AttestorSignerManagementGroup {
  static final Logger LOG = LogManager.getLogger(AttestorSignerManagementGroup.class);

  Map<BigInteger, AttestorSignerManagement> blockchains = new HashMap<>();


  public void addBlockchain(Credentials creds, BlockchainInfo bcInfo) throws Exception {
//    BigInteger blockchainId = new BigInteger(bcInfo.bcId, 16);
//    if (this.blockchains.containsKey(blockchainId)) {
//      throw new Exception("Blockchain already added: ")
//      return;
//    }
//    LOG.debug("Deploying Cross-Blockchain Control contracts for blockchain id 0x{}",blockchainId.toString(16));
//
//    BcHolder holder = new BcHolder();
//    switch (this.consensusMethodology) {
//      case TRANSACTION_RECEIPT_SIGNING:
//        holder.cbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(
//            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
//        holder.cbc = holder.cbcTxRootTransfer;
//        break;
//      case EVENT_SIGNING:
//        holder.cbcSignedEvents = new CrossBlockchainControlSignedEvents(
//            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
//        holder.cbc = holder.cbcSignedEvents;
//        break;
//      default:
//        throw new RuntimeException("Not supported yet: " + this.consensusMethodology);
//    }
//
//    holder.cbc.deployContracts();
//    holder.cbcContractAddress = holder.cbc.getCbcContractAddress();
//
//    this.blockchains.put(blockchainId, holder);
  }

//  public void addBlockchain(Credentials creds, BlockchainInfo bcInfo, List<String> addresses) throws Exception {
//    BigInteger blockchainId = new BigInteger(bcInfo.bcId, 16);
//    if (this.blockchains.containsKey(blockchainId)) {
//      return;
//    }
//
//    BcHolder holder = new BcHolder();
//    switch (this.consensusMethodology) {
//      case TRANSACTION_RECEIPT_SIGNING:
//        holder.cbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(
//                creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
//        holder.cbc = holder.cbcTxRootTransfer;
//        break;
//      case EVENT_SIGNING:
//        holder.cbcSignedEvents = new CrossBlockchainControlSignedEvents(
//                creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
//        holder.cbc = holder.cbcSignedEvents;
//        break;
//      default:
//        throw new RuntimeException("Not supported yet: " + this.consensusMethodology);
//    }
//
//    holder.cbc.loadContracts(addresses);
//    holder.cbcContractAddress = addresses.get(0);
//
//    this.blockchains.put(blockchainId, holder);
//  }
//
//
//  public void registerSignerOnAllBlockchains(AnIdentity signer) throws Exception {
//    for (BigInteger bcId1: this.blockchains.keySet()) {
//      registerSigner(signer, bcId1);
//    }
//  }
//
//  // TODO: Replace the signers in the BcHolder when the Attestor code is written
//  public void registerSigner(AnIdentity signer, BigInteger bcId1) throws Exception {
//    // Add the signer (their private key) to app for the blockchain
//    BcHolder holder = this.blockchains.get(bcId1);
//    holder.signers.add(signer);
//
//    // Add the signer (their address / public key) to each blockchain
//    for (BigInteger bcId2: this.blockchains.keySet()) {
//      BcHolder holder2 = this.blockchains.get(bcId2);
//      holder2.cbc.registerSigner(signer, bcId1);
//    }
//  }
//
//  public void addSignerOnAllBlockchains(AnIdentity signer) throws Exception {
//    for (BigInteger bcId1: this.blockchains.keySet()) {
//      addSigner(signer, bcId1);
//    }
//  }
//  public void addSigner(AnIdentity signer, BigInteger bcId1) throws Exception {
//    // Add the signer (their private key) to app for the blockchain
//    BcHolder holder = this.blockchains.get(bcId1);
//    holder.signers.add(signer);
//  }
//
//
//
//  public AbstractCbc getCbcContract(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//    return this.blockchains.get(bcId).cbc;
//  }
//
//  public CrossBlockchainControlTxReceiptRootTransfer getCbcContractTxRootTransfer(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//    return this.blockchains.get(bcId).cbcTxRootTransfer;
//  }
//
//  public CrossBlockchainControlSignedEvents getCbcContractSignedEvents(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//    return this.blockchains.get(bcId).cbcSignedEvents;
//  }
//
//  public String getCbcAddress(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//    return this.blockchains.get(bcId).cbcContractAddress;
//  }
//
//  public List<String> getInfrastructureAddresses(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//    return this.blockchains.get(bcId).cbc.getContractAddresses();
//  }
//
//
//  public AnIdentity[] getSigners(BigInteger bcId) {
//    check(bcId);
//    List<AnIdentity> signers = this.blockchains.get(bcId).signers;
//    return signers.toArray(new AnIdentity[]{});
//  }
//
//  public Set<BigInteger> getAllBlockchainIds() {
//    return this.blockchains.keySet();
//  }
//
//  private void check(BigInteger bcId) {
//    if (!this.blockchains.containsKey(bcId)) {
//      throw new RuntimeException("Unknown blockchain: 0x" + bcId.toString(16));
//    }
//  }
//
//  public void setupCrosschainTrust(AnIdentity initialSigner) throws Exception {
//    for (BigInteger bcId1: this.blockchains.keySet()) {
//      BcHolder holder1 = this.blockchains.get(bcId1);
//
//      for (BigInteger bcId2: this.blockchains.keySet()) {
//        BcHolder holder2 = this.blockchains.get(bcId2);
//        holder2.signers.add(initialSigner);
//        holder1.cbc.addBlockchain(bcId2, holder2.cbcContractAddress, initialSigner.getAddress());
//      }
//    }
//  }
//
//  private static class BcHolder {
//    CrossBlockchainControlTxReceiptRootTransfer cbcTxRootTransfer;
//    CrossBlockchainControlSignedEvents cbcSignedEvents;
//    AbstractCbc cbc;
//    String cbcContractAddress;
//    List<AnIdentity> signers = new ArrayList<>();
//  }

}
