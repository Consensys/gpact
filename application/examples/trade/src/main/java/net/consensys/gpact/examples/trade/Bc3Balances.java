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
package net.consensys.gpact.examples.trade;

import net.consensys.gpact.examples.trade.soliditywrappers.Balances;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import net.consensys.gpact.cbc.AbstractBlockchain;

import java.io.IOException;
import java.math.BigInteger;

public class Bc3Balances extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc3Balances.class);

  Balances balancesContract;

  public Bc3Balances(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts(String cbcContractAddress) throws Exception {
    this.balancesContract =
        Balances.deploy(this.web3j, this.tm, this.gasProvider, cbcContractAddress).send();
    LOG.info("Balances contract deployed to {} on blockchain 0x{}",
        this.balancesContract.getContractAddress(), this.blockchainId.toString(16));
  }

  public void setBalance(String account, BigInteger newBalance) throws Exception {
    this.balancesContract.setBalance(account, newBalance).send();
  }

  public BigInteger getBalance(String account) throws Exception {
    return this.balancesContract.getBalance(account).send();
  }

  public String getRlpFunctionSignature_Transfer(String from, String to, BigInteger amount) {
    return this.balancesContract.getRLP_transfer(from, to, amount);
  }

  public boolean storageIsLocked() throws Exception {
    return  this.balancesContract.isLocked(BigInteger.ZERO).send();
  }
}
