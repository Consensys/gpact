/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.applications.twentyacts.crosscontrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.functioncall.*;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/** Manage multiple TwentyActsCrosschainControlManagers, one for each blockchain. */
public class TwentyActsManagerGroup implements CrossControlManagerGroup {
  static final Logger LOG = LogManager.getLogger(TwentyActsManagerGroup.class);

  private Map<BlockchainId, BcHolder> blockchains = new HashMap<>();

  @Override
  public void addBlockchainAndDeployContracts(
      Credentials creds,
      BlockchainConfig bcInfo,
      MessagingVerificationInterface messageVerification)
      throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }
    LOG.debug("Deploying Cross-Blockchain Control contract for blockchain id {}", blockchainId);

    BcHolder holder = new BcHolder();
    holder.cbc =
        new TwentyActsManager(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
    holder.cbc.deployCbcContract();
    holder.cbcContractAddress = holder.cbc.getCbcContractAddress();
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }

  @Override
  public void addBlockchainAndLoadCbcContract(
      Credentials creds,
      BlockchainConfig bcInfo,
      String cbcAddress,
      MessagingVerificationInterface messageVerification)
      throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    BcHolder holder = new BcHolder();
    holder.cbc =
        new TwentyActsManager(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);

    holder.cbc.loadCbcContract(cbcAddress);
    holder.cbcContractAddress = cbcAddress;
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }

  @Override
  public CrosschainCallResult executeCrosschainCall(
      String executionEngine, CallExecutionTree callTree, long timeout)
      throws CrosschainFunctionCallException {
    if (!executionEngine.equalsIgnoreCase(CrosschainProtocols.SERIAL)) {
      throw new CrosschainFunctionCallException("Unsupported execution engine: " + executionEngine);
    }

    throw new Error("not supported");
  }

  @Override
  public CrossControlManager getCbcManager(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return (CrossControlManager) this.blockchains.get(bcId).cbc;
  }

  @Override
  public MessagingVerificationInterface getMessageVerification(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).ver;
  }

  @Override
  public String getCbcAddress(BlockchainId bcId) {
    if (!this.blockchains.containsKey(bcId)) {
      throw new RuntimeException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbcContractAddress;
  }

  @Override
  public ArrayList<BlockchainId> getAllBlockchainIds() {
    ArrayList<BlockchainId> bcIds = new ArrayList<>();
    bcIds.addAll(this.blockchains.keySet());
    return bcIds;
  }

  private static class BcHolder {
    TwentyActsManager cbc;
    String cbcContractAddress;
    MessagingVerificationInterface ver;
  }
}
