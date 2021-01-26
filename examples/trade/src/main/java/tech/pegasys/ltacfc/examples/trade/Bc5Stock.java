/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.examples.trade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.cbc.AbstractBlockchain;
import tech.pegasys.ltacfc.examples.trade.soliditywrappers.Stock;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;

import java.io.IOException;
import java.math.BigInteger;

public class Bc5Stock extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc5Stock.class);

  Stock stockContract;
  private LockableStorage lockableStorageContract;

  public Bc5Stock(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts(String cbcContractAddress) throws Exception {
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.gasProvider,
        cbcContractAddress).send();
    this.stockContract =
        Stock.deploy(this.web3j, this.tm, this.gasProvider,
          this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.stockContract.getContractAddress()).send();
    LOG.info("Stock contract deployed to {} on blockchain 0x{}",
        this.stockContract.getContractAddress(), this.blockchainId.toString(16));
    LOG.info("Lockable Storage contract for Stock deployed to {} on blockchain 0x{}",
        this.lockableStorageContract.getContractAddress(), this.blockchainId.toString(16));
  }

  public void setStock(String account, BigInteger newAmount) throws Exception {
    this.stockContract.setStock(account, newAmount).send();
  }

  public BigInteger getStock(String account) throws Exception {
    return this.stockContract.getStock(account).send();
  }

  public String getRlpFunctionSignature_Delivery(String from, String to, BigInteger amount) {
    return this.stockContract.getRLP_delivery(from, to, amount);
  }

  public boolean storageIsLocked() throws Exception {
    return  this.lockableStorageContract.locked().send();
  }
}
