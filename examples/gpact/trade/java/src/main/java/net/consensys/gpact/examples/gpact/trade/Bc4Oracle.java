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
import net.consensys.gpact.soliditywrappers.examples.gpact.trade.PriceOracle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class Bc4Oracle extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc4Oracle.class);

  PriceOracle priceOracleContract;

  public Bc4Oracle(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContracts() throws Exception {
    this.priceOracleContract = PriceOracle.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.info(
        "Price Oracle contract deployed to {}, on blockchain {}",
        this.priceOracleContract.getContractAddress(),
        this.blockchainId);
  }

  public void setPrice(BigInteger newPrice) throws Exception {
    this.priceOracleContract.setPrice(newPrice).send();
  }

  public BigInteger getPrice() throws Exception {
    return this.priceOracleContract.getPrice().send();
  }

  public String getRlpFunctionSignature_GetPrice() {
    return this.priceOracleContract.getABI_getPrice();
  }

  public void showValues() throws Exception {
    LOG.info("Price Oracle: Price: {}", this.priceOracleContract.getPrice().send());
  }
}
