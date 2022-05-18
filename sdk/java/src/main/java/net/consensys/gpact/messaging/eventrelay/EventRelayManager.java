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
package net.consensys.gpact.messaging.eventrelay;

import java.io.IOException;
import java.util.ArrayList;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.messaging.common.RegistrarManager;
import net.consensys.gpact.soliditywrappers.messaging.eventrelay.EventRelayVerifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/** Manages the registrar and crosschain verifier contracts on a blockchain. */
public class EventRelayManager extends RegistrarManager {
  static final Logger LOG = LogManager.getLogger(EventRelayManager.class);

  private EventRelayVerifier verifier;

  private String functionCallContract;

  public EventRelayManager(
      Credentials credentials,
      BlockchainId bcId,
      String rpcUri,
      String wsUri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, rpcUri, wsUri, gasPriceStrategy, blockPeriod);
  }

  public void setFunctionCallContract(final String functionCallContract) {
    this.functionCallContract = functionCallContract;
  }

  @Override
  public void deployContracts() throws Exception {
    super.deployContracts();
    this.verifier =
        EventRelayVerifier.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                this.registrarContract.getContractAddress(),
                this.functionCallContract)
            .send();
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
        EventRelayVerifier.load(addresses.get(1), this.web3j, this.tm, this.gasProvider);
  }

  @Override
  public String getVerifierAddress() {
    return this.verifier.getContractAddress();
  }
}
