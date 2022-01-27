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
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Manages a set of AttestorSigners for a set of blockchains. */
public class AttestorSignerGroup {
  static final Logger LOG = LogManager.getLogger(AttestorSignerGroup.class);

  private Map<BlockchainId, AttestorSigner> blockchains = new HashMap<>();

  public void addBlockchain(BlockchainId blockchainId) throws Exception {
    if (this.blockchains.containsKey(blockchainId)) {
      return;
      // throw new Exception("Blockchain already in Attestor Signer Group: " + blockchainId);
    }

    this.blockchains.put(blockchainId, new AttestorSigner(blockchainId));
  }

  // TODO when an attestor signer service is implemented, this will change to
  // setting up URLs where attestors can be contacted
  public void addSignerOnAllBlockchains(AnIdentity signer) throws Exception {
    for (BlockchainId bcId1 : this.blockchains.keySet()) {
      addSigner(signer, bcId1);
    }
  }

  // TODO when an attestor signer service is implemented, this will change to
  // setting up URLs where attestors can be contacted
  public void addSigner(AnIdentity signer, BlockchainId bcId1) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    AttestorSigner holder = this.blockchains.get(bcId1);
    holder.signers.add(signer);
  }

  public MessagingVerificationInterface getVerifier(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId);
  }
}
