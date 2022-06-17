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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.CrosschainProtocolStackException;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.common.attestorrelayer.AttestorRelayer;

/** Manages a set of EventRelay objects for a set of blockchains. */
public class EventRelayGroup {
  private Map<BlockchainId, EventRelay> blockchains = new HashMap<>();

  public void addBlockchain(BlockchainId blockchainId) throws Exception {
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    this.blockchains.put(blockchainId, new EventRelay(blockchainId));
  }

  public void configureRelayer(
      AnIdentity signingCredentials,
      String relayerUri,
      List<AttestorRelayer.Source> sources,
      String dispatcherUri,
      List<AttestorRelayer.Dest> targets)
      throws CrosschainProtocolStackException {
    AttestorRelayer relayer = new AttestorRelayer(relayerUri, signingCredentials.getPrivateKey());
    for (AttestorRelayer.Source source : sources) {
      relayer.configureSigningKey(
          source.getBlockchainId().toDecimalString(), source.getCrosschainControlAddr());
      relayer.startNewObservation(source);
    }

    for (AttestorRelayer.Dest dest : targets) {
      relayer.setDispatcher(dispatcherUri, dest);
    }
  }

  public MessagingVerificationInterface getVerifier(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId);
  }
}
