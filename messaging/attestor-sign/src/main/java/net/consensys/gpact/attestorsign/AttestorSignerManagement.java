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

import net.consensys.gpact.attestorsign.soliditywrappers.AttestorSignRegistrar;
import net.consensys.gpact.attestorsign.soliditywrappers.CrosschainVerifierSign;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingManagementInterface;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the registrar and crosschain verifier contracts on a blockchain.
 */
public class AttestorSignerManagement extends AbstractBlockchain implements MessagingManagementInterface {
  static final Logger LOG = LogManager.getLogger(AttestorSignerManagement.class);

  AttestorSignRegistrar registrarContract;
  private CrosschainVerifierSign verifier;


  public AttestorSignerManagement(Credentials credentials, BlockchainId bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  @Override
  public void deployContracts() throws Exception {
    this.registrarContract = AttestorSignRegistrar.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.debug(" Registrar Contract: {}", this.registrarContract.getContractAddress());
    this.verifier =
            CrosschainVerifierSign.deploy(this.web3j, this.tm, this.gasProvider, this.registrarContract.getContractAddress()).send();
    LOG.debug(" Verified Contract: {}", this.verifier.getContractAddress());
  }

  @Override
  public ArrayList<String> getContractAddresses() {
    ArrayList<String> addresses = new ArrayList<>();
    addresses.add(this.registrarContract.getContractAddress());
    addresses.add(this.verifier.getContractAddress());
    return addresses;
  }


  @Override
  public void loadContracts(ArrayList<String> addresses) {
    this.registrarContract =
            AttestorSignRegistrar.load(addresses.get(0), this.web3j, this.tm, this.gasProvider);
    this.verifier =
            CrosschainVerifierSign.load(addresses.get(1), this.web3j, this.tm, this.gasProvider);
  }


  @Override
  public void addBlockchain(BigInteger bcId, String initialSigner) throws Exception {
    List<String> signers = new ArrayList<>();
    signers.add(initialSigner);
    TransactionReceipt txr = this.registrarContract.addBlockchain(bcId, BigInteger.ONE, signers).send();
    assert(txr.isStatusOK());
  }


  @Override
  public void registerSigner(AnIdentity signer, BigInteger bcId) throws Exception {
    LOG.debug("Registering signer 0x{} as signer for blockchain 0x{} in registration contract on blockchain {}",
            signer.getAddress(), bcId.toString(16), this.blockchainId);
    TransactionReceipt txr = this.registrarContract.addSigner(bcId, signer.getAddress()).send();
    assert(txr.isStatusOK());
  }

  @Override
  public String getVerifierAddress() {
    return this.verifier.getContractAddress();
  }
}
