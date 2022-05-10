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
package net.consensys.gpact.messaging.eventattest;

import java.util.*;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.CrosschainProtocolStackException;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.common.attestorrelayer.AttestorRelayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Manages a set of AttestorSigners for a set of blockchains. */
public class AttestorSignerGroup {
  static final Logger LOG = LogManager.getLogger(AttestorSignerGroup.class);

  private Map<BlockchainId, AttestorSigner> blockchains = new HashMap<>();

  public void addBlockchain(BlockchainId blockchainId, String msgStoreUrlFromUser)
      throws Exception {
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    this.blockchains.put(blockchainId, new AttestorSigner(blockchainId, msgStoreUrlFromUser));
  }

  public void configureRelayer(
      AnIdentity signingCredentials,
      String relayerUri,
      List<AttestorRelayer.Source> sources,
      String dispatcherUri,
      String msgStoreUriFromDispatcher,
      String msgStoreUriFromUser)
      throws CrosschainProtocolStackException {
    AttestorRelayer relayer = new AttestorRelayer(relayerUri, signingCredentials.getPrivateKey());
    for (AttestorRelayer.Source source : sources) {
      relayer.addNewSource(source);
    }
    relayer.addMessageStore(dispatcherUri, msgStoreUriFromDispatcher, msgStoreUriFromUser);
  }

  public MessagingVerificationInterface getVerifier(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId);
  }
}
