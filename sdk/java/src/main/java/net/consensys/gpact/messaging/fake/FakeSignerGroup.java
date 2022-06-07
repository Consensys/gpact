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
package net.consensys.gpact.messaging.fake;

import java.util.HashMap;
import java.util.Map;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Manages a set of FakeSigners for a set of blockchains. */
public class FakeSignerGroup {
  static final Logger LOG = LogManager.getLogger(FakeSignerGroup.class);

  private Map<BlockchainId, FakeSigner> blockchains = new HashMap<>();

  public void addBlockchain(BlockchainId blockchainId, FakeRelayer fakeRelayer) throws Exception {
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    this.blockchains.put(blockchainId, new FakeSigner(blockchainId, fakeRelayer));
  }

  public FakeRelayer configureRelayer(AnIdentity signingCredentials) {
    return new FakeRelayer(signingCredentials);
  }

  public MessagingVerificationInterface getVerifier(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId);
  }
}
