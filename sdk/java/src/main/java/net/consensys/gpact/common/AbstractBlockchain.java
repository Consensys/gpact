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
package net.consensys.gpact.common;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

public abstract class AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractBlockchain.class);

  // Retry requests to Ethereum Clients up to 100 times.
  protected static final int RETRY = 100;

  protected Credentials credentials;
  protected BlockchainConfig blockchainConfig;

  protected BlockchainId blockchainId;
  protected String rpcUri;
  protected String wsUri;
  // Polling interval should be equal to the block time.
  protected int pollingIntervalMs;
  public DynamicGasProvider gasProvider;
  protected DynamicGasProvider.Strategy gasPriceStrategy;

  public Web3j web3j;
  protected FastTxManager tm;

  protected AbstractBlockchain(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    this.credentials = credentials;
    this.blockchainConfig = bcConfig;
    this.blockchainId = bcConfig.bcId;
    this.rpcUri = bcConfig.blockchainNodeRpcUri;
    this.wsUri = bcConfig.blockchainNodeWsUri;
    this.pollingIntervalMs = bcConfig.period;
    this.gasPriceStrategy = bcConfig.gasPriceStrategy;

    this.web3j =
        Web3j.build(
            new HttpService(this.rpcUri),
            this.pollingIntervalMs,
            new ScheduledThreadPoolExecutor(5));
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(this.web3j, this.pollingIntervalMs, RETRY);
    this.tm =
        TxManagerCache.getOrCreate(
            this.web3j, this.credentials, this.blockchainId.asLong(), txrProcessor);
    this.gasProvider = new DynamicGasProvider(this.web3j, rpcUri, gasPriceStrategy);
  }

  public void shutdown() {
    this.web3j.shutdown();
  }

  public BlockchainId getBlockchainId() {
    return this.blockchainId;
  }

  public String getRpcUri() {
    return this.rpcUri;
  }

  public String getWsUri() {
    return this.wsUri;
  }

  public void pauseWhileTransactionMined() {
    try {
      Thread.sleep((long) this.pollingIntervalMs);
    } catch (InterruptedException e) {
      // Do nothing
    }
  }
}
