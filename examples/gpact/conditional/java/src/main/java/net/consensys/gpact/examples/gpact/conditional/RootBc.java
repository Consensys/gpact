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
package net.consensys.gpact.examples.gpact.conditional;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.soliditywrappers.examples.gpact.conditional.RootBlockchainContract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class RootBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(RootBc.class);

  RootBlockchainContract rootBlockchainContract;

  public RootBc(final Credentials credentials, final BlockchainConfig bcConfig) throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContracts(
      String cbcContractAddress, BlockchainId otherBlockchainId, String otherContractAddress)
      throws Exception {
    LOG.info("Deploy Root Blockchain Contracts");
    this.rootBlockchainContract =
        RootBlockchainContract.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                cbcContractAddress,
                otherBlockchainId.asBigInt(),
                otherContractAddress)
            .send();
    LOG.info(" Root Blockchain Contract: {}", this.rootBlockchainContract.getContractAddress());
  }

  public String getRlpFunctionSignature_SomeComplexBusinessLogic(BigInteger val) {
    return this.rootBlockchainContract.getABI_someComplexBusinessLogic(val);
  }

  public void setVal1(BigInteger val) throws Exception {
    this.rootBlockchainContract.setVal1(val).send();
  }

  public void setVal2(BigInteger val) throws Exception {
    this.rootBlockchainContract.setVal2(val).send();
  }

  public BigInteger getVal1() throws Exception {
    return this.rootBlockchainContract.getVal1().send();
  }

  public BigInteger getVal2() throws Exception {
    return this.rootBlockchainContract.getVal2().send();
  }

  public void showValues() throws Exception {
    LOG.info("Root Blockchain: Val1: {}", this.rootBlockchainContract.getVal1().send());
    LOG.info("Root Blockchain: Val2: {}", this.rootBlockchainContract.getVal2().send());
  }
}
