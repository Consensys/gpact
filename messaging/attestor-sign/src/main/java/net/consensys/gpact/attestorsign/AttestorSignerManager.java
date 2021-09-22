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

import net.consensys.gpact.attestorsign.soliditywrappers.CrosschainVerifierSign;
import net.consensys.gpact.common.BlockchainId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages the registrar and crosschain verifier contracts on a blockchain.
 */
public class AttestorSignerManager extends RegistrarManager {
  static final Logger LOG = LogManager.getLogger(AttestorSignerManager.class);

  private CrosschainVerifierSign verifier;


  public AttestorSignerManager(Credentials credentials, BlockchainId bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  @Override
  public void deployContracts() throws Exception {
    super.deployContracts();
    this.verifier =
            CrosschainVerifierSign.deploy(this.web3j, this.tm, this.gasProvider, this.registrarContract.getContractAddress()).send();
    LOG.debug(" Verifier Contract: {}", this.verifier.getContractAddress());
  }

  @Override
  public ArrayList<String> getContractAddresses() {
    ArrayList<String> addresses = super.getContractAddresses();
    addresses.add(this.verifier.getContractAddress());
    return addresses;
  }


  @Override
  public void loadContracts(ArrayList<String> addresses) {
    super.loadContracts(addresses);
    this.verifier =
            CrosschainVerifierSign.load(addresses.get(1), this.web3j, this.tm, this.gasProvider);
  }

  @Override
  public String getVerifierAddress() {
    return this.verifier.getContractAddress();
  }
}
