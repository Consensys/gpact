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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public abstract class AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractBlockchain.class);

  // Retry requests to Ethereum Clients up to five times.
  protected static final int RETRY = 20;

  protected Credentials credentials;


  protected BlockchainId blockchainId;
  protected String uri;
  // Polling interval should be equal to the block time.
  protected int pollingInterval;
  public DynamicGasProvider gasProvider;

  public Web3j web3j;
  protected FastTxManager tm;


  protected AbstractBlockchain(Credentials credentials, BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod) throws IOException {
    this.blockchainId = bcId;
    this.uri = uri;
    this.pollingInterval = blockPeriod;
    this.credentials = credentials;
    this.web3j = Web3j.build(new HttpService(this.uri), this.pollingInterval, new ScheduledThreadPoolExecutor(5));

    TransactionReceiptProcessor txrProcessor = new PollingTransactionReceiptProcessor(this.web3j, this.pollingInterval, RETRY);
    this.tm = TxManagerCache.getOrCreate(this.web3j, this.credentials, this.blockchainId.asLong(), txrProcessor);
    this.gasProvider = new DynamicGasProvider(this.web3j, uri, gasPriceStrategy);
  }

  public void shutdown() {
    this.web3j.shutdown();
  }


  public BlockchainId getBlockchainId() {
    return this.blockchainId;
  }

  public String getUri() {
    return this.uri;
  }

}
