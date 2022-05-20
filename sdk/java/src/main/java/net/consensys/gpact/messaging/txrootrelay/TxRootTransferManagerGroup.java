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
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.BaseMessagingManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/** Manage multiple blockchains, each holding a set of registrar and verification contracts */
public class TxRootTransferManagerGroup extends BaseMessagingManagerGroup {
  static final Logger LOG = LogManager.getLogger(TxRootTransferManagerGroup.class);

  @Override
  public void addBlockchainAndDeployContracts(
      final Credentials creds, final BlockchainConfig bcConfig) throws Exception {
    BlockchainId blockchainId = bcConfig.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
      // throw new Exception("Blockchain already added: " + blockchainId);
    }
    LOG.debug("Deploying Cross-Blockchain Control contracts for blockchain id {}", blockchainId);

    TxRootTransferManager manager = new TxRootTransferManager(creds, bcConfig);
    manager.deployContracts();

    this.blockchains.put(blockchainId, manager);
  }

  @Override
  public void addBlockchainAndLoadContracts(
      Credentials creds, BlockchainConfig bcConfig, ArrayList<String> addresses) throws Exception {
    BlockchainId blockchainId = bcConfig.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
      // throw new Exception("Blockchain already added: " + blockchainId);
    }

    TxRootTransferManager manager = new TxRootTransferManager(creds, bcConfig);
    manager.loadContracts(addresses);

    this.blockchains.put(blockchainId, manager);
  }

  public String getTxRootContractAddress(BlockchainId bcId) throws Exception {
    if (!this.blockchains.containsKey(bcId)) {
      throw new Exception("Unknown blockchain: " + bcId);
    }
    return ((TxRootTransferManager) this.blockchains.get(bcId)).getTxRootContractAddress();
  }
}
