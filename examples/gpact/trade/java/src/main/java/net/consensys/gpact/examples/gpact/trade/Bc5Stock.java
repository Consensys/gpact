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
package net.consensys.gpact.examples.gpact.trade;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class Bc5Stock extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc5Stock.class);

  Stock stockContract;

  public Bc5Stock(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContracts(String cbcContractAddress) throws Exception {
    this.stockContract =
        Stock.deploy(this.web3j, this.tm, this.gasProvider, cbcContractAddress).send();
    LOG.info(
        "Stock contract deployed to {} on blockchain 0x{}",
        this.stockContract.getContractAddress(),
        this.blockchainId);
  }

  public void setStock(String account, BigInteger newAmount) throws Exception {
    this.stockContract.setStock(account, newAmount).send();
  }

  public BigInteger getStock(String account) throws Exception {
    return this.stockContract.getStock(account).send();
  }

  public String getRlpFunctionSignature_Delivery(String from, String to, BigInteger amount) {
    return this.stockContract.getABI_delivery(from, to, amount);
  }

  public boolean storageIsLocked() throws Exception {
    return this.stockContract.isLocked(BigInteger.ZERO).send();
  }
}
