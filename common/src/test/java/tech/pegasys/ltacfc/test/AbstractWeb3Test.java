/*
 * Copyright 2020 ConsenSys AG.
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
package tech.pegasys.ltacfc.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class AbstractWeb3Test {
  private static final Logger LOG = LogManager.getLogger(AbstractWeb3Test.class);

  protected static final BigInteger BLOCKCHAIN_ID = BigInteger.valueOf(31);
  private static final String IP_PORT = "127.0.0.1:8310";
  private static final String URI = "http://" + IP_PORT + "/";


  // Have the polling interval equal to the block time.
  protected static final int POLLING_INTERVAL = 2000;
  // Retry requests to Ethereum Clients up to five times.
  protected static final int RETRY = 5;

  protected Web3j web3j;
  protected TransactionManager tm;
  protected Credentials credentials;
  // A gas provider which indicates no gas is charged for transactions.
  protected ContractGasProvider freeGasProvider =  new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);


  public void setupWeb3() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
//    System.out.println("Priv2: " + privateKey);
    this.credentials = Credentials.create(privateKey);

    this.web3j = Web3j.build(new HttpService(URI), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    this.tm = new RawTransactionManager(this.web3j, this.credentials, BLOCKCHAIN_ID.longValue(), RETRY, POLLING_INTERVAL);
  }


  public Credentials createNewIdentity() {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
    return Credentials.create(privateKey);
  }

  public TransactionManager createTransactionManager(Credentials creds) {
    return new RawTransactionManager(this.web3j, creds, BLOCKCHAIN_ID.longValue(), RETRY, POLLING_INTERVAL);
  }


}
