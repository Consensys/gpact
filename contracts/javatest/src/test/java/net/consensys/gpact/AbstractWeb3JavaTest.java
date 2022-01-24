/*
 * Copyright 2020 ConsenSys Software Inc
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
package net.consensys.gpact;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.TxManagerCache;
import net.consensys.gpact.common.crypto.KeyPairGen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

public abstract class AbstractWeb3JavaTest {
  private static final Logger LOG = LogManager.getLogger(AbstractWeb3JavaTest.class);

  protected static final BigInteger BLOCKCHAIN_ID = BigInteger.valueOf(31);
  private static final String IP_PORT = "127.0.0.1:8310";
  private static final String URI = "http://" + IP_PORT + "/";

  // Have the polling interval half of the block time to have quicker test time.
  protected static final int POLLING_INTERVAL = 1000;
  // Retry requests to Ethereum Clients many times. The servers may be slow to respond during
  // parallel testing.
  protected static final int RETRY = 100;

  protected Web3j web3j;
  protected TransactionManager tm;
  protected Credentials credentials;
  // A gas provider which indicates no gas is charged for transactions.
  protected ContractGasProvider freeGasProvider =
      new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);

  public void setupWeb3() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
    //    System.out.println("Priv2: " + privateKey);
    this.credentials = Credentials.create(privateKey);

    this.web3j =
        Web3j.build(new HttpService(URI), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(this.web3j, POLLING_INTERVAL, RETRY);
    this.tm =
        TxManagerCache.getOrCreate(
            this.web3j, this.credentials, BLOCKCHAIN_ID.longValue(), txrProcessor);
  }

  public Credentials createNewIdentity() {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
    return Credentials.create(privateKey);
  }

  public TransactionManager createTransactionManager(Credentials creds) {
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(this.web3j, POLLING_INTERVAL, RETRY);
    return TxManagerCache.getOrCreate(this.web3j, creds, BLOCKCHAIN_ID.longValue(), txrProcessor);
  }

  public void processTransactionException(TransactionException ex) {
    Optional<TransactionReceipt> otxr = ex.getTransactionReceipt();
    if (otxr.isPresent()) {
      String revertStr = otxr.get().getRevertReason();
      System.out.println(
          "Exception thrown as expected: Revert Reason: "
              + RevertReason.decodeRevertReason(revertStr));
    } else {
      System.out.println(
          "Exception thrown as expected, but not transaction receipt: " + ex.getMessage());
    }
  }
}
