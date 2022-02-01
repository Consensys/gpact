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
package net.consensys.gpact.functioncall.gpact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.BlockchainInfo;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.functioncall.CrosschainFunctionCallException;
import net.consensys.gpact.functioncall.calltree.CallExecutionTree;
import net.consensys.gpact.functioncall.gpact.engine.ExecutionEngine;
import net.consensys.gpact.functioncall.gpact.engine.ParallelExecutionEngine;
import net.consensys.gpact.functioncall.gpact.engine.SerialExecutionEngine;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class GpactCrossControlManagerGroup implements CrossControlManagerGroup {
  private static final Logger LOG = LogManager.getLogger(GpactCrossControlManagerGroup.class);

  private final Map<BlockchainId, BcHolder> blockchains = new HashMap<>();

  @Override
  public void addBlockchainAndDeployContracts(
      Credentials creds, BlockchainInfo bcInfo, MessagingVerificationInterface messageVerification)
      throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }
    LOG.debug("Deploying Cross-Blockchain Control contract for blockchain id {}", blockchainId);

    BcHolder holder = new BcHolder();
    holder.cbc =
        new GpactCrossControlManager(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
    holder.cbc.deployContract();
    holder.cbcContractAddress = holder.cbc.getCbcContractAddress();
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }

  @Override
  public void addBlockchainAndLoadContracts(
      Credentials creds,
      BlockchainInfo bcInfo,
      String cbcAddress,
      MessagingVerificationInterface messageVerification)
      throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
    }

    BcHolder holder = new BcHolder();
    holder.cbc =
        new GpactCrossControlManager(
            creds, bcInfo.bcId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);

    holder.cbc.loadContract(cbcAddress);
    holder.cbcContractAddress = cbcAddress;
    holder.ver = messageVerification;

    this.blockchains.put(blockchainId, holder);
  }

  @Override
  public CrosschainCallResult executeCrosschainCall(
      String executionEngine, CallExecutionTree callTree, long timeout)
      throws CrosschainFunctionCallException {
    GpactCrosschainExecutor executor = new GpactCrosschainExecutor(this);

    ExecutionEngine engine = null;
    if (executionEngine.equalsIgnoreCase("SERIAL")) {
      engine = new SerialExecutionEngine(executor);
    } else if (executionEngine.equalsIgnoreCase("PARALLEL")) {
      engine = new ParallelExecutionEngine(executor);
    } else {
      throw new CrosschainFunctionCallException("Unknown execution engine: " + executionEngine);
    }

    try {
      return engine.execute(callTree, timeout);
    } catch (Exception ex) {
      throw new CrosschainFunctionCallException("Exception while executing crosschain call", ex);
    }
  }

  @Override
  public CrossControlManager getCbcManager(BlockchainId bcId)
      throws CrosschainFunctionCallException {
    if (!this.blockchains.containsKey(bcId)) {
      throw new CrosschainFunctionCallException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).cbc;
  }

  @Override
  public MessagingVerificationInterface getMessageVerification(BlockchainId bcId)
      throws CrosschainFunctionCallException {
    if (!this.blockchains.containsKey(bcId)) {
      throw new CrosschainFunctionCallException("Unknown blockchain: " + bcId);
    }
    return this.blockchains.get(bcId).ver;
  }

  @Override
  public String getCbcAddress(BlockchainId bcId) throws CrosschainFunctionCallException {
    if (!this.blockchains.containsKey(bcId)) {
      throw new CrosschainFunctionCallException("Unknown blockchain: " + bcId);
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
    GpactCrossControlManager cbc;
    String cbcContractAddress;
    MessagingVerificationInterface ver;
  }
}
