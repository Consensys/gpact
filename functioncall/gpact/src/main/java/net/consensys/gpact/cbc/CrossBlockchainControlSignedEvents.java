/*
 * Copyright 2019 ConsenSys Software Inc
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
package net.consensys.gpact.cbc;

import net.consensys.gpact.attestorsign.soliditywrappers.CrosschainVerifierSign;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class CrossBlockchainControlSignedEvents extends AbstractCbc {
  private static final Logger LOG = LogManager.getLogger(CrossBlockchainControlSignedEvents.class);


  private CrosschainVerifierSign verifier;

  public CrossBlockchainControlSignedEvents(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts() throws Exception {
    super.deployContracts();
    this.verifier =
            CrosschainVerifierSign.deploy(this.web3j, this.tm, this.gasProvider, this.registrarContract.getContractAddress()).send();
  }

  public List<String> getContractAddresses() {
    List<String> addresses = super.getContractAddresses();
    addresses.add(this.verifier.getContractAddress());
    return addresses;
  }

  public void loadContracts(List<String> addresses) {
    super.loadContracts(addresses);
    this.verifier = CrosschainVerifierSign.load(addresses.get(2), this.web3j, this.tm, this.gasProvider);
  }

  protected void addVerifier(BigInteger bcId) throws Exception {
    this.crossBlockchainControlContract.addVerifier(bcId, this.verifier.getContractAddress()).send();
  }


}
