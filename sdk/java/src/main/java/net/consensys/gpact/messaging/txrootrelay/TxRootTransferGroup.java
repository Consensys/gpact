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
package net.consensys.gpact.messaging.txrootrelay;

import java.util.*;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.BlockchainInfo;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/** Manages a set of AttestorSigners for a set of blockchains. */
public class TxRootTransferGroup {
  static final Logger LOG = LogManager.getLogger(TxRootTransferGroup.class);

  Map<BlockchainId, TxRootTransfer> blockchains = new HashMap<>();

  public void addBlockchain(
      TxRootRelayerGroup relayerGroup, Credentials creds, BlockchainInfo bcInfo) throws Exception {
    if (this.blockchains.containsKey(bcInfo.bcId)) {
      return;
      // throw new Exception("Blockchain already in TxRoot Transfer Group: " + bcInfo.bcId);
    }

    this.blockchains.put(
        bcInfo.bcId,
        new TxRootTransfer(
            relayerGroup, creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period));
  }

  public MessagingVerificationInterface getVerifier(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId);
  }
}
