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
package net.consensys.gpact.examples.conditional;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.examples.conditional.soliditywrappers.OtherBlockchainContract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class OtherBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(OtherBc.class);

  OtherBlockchainContract otherBlockchainContract;

  public OtherBc(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(String cbcContractAddress) throws Exception {
    LOG.info("Deploy Other Blockchain Contracts");
    this.otherBlockchainContract =
        OtherBlockchainContract.deploy(this.web3j, this.tm, this.gasProvider, cbcContractAddress)
            .send();
    LOG.info(" Other Blockchain Contract: {}", this.otherBlockchainContract.getContractAddress());
  }

  public void setVal(BigInteger val) throws Exception {
    this.otherBlockchainContract.setVal(val).send();
  }

  public String getRlpFunctionSignature_GetVal() {
    return this.otherBlockchainContract.getABI_getVal();
  }

  public String getRlpFunctionSignature_SetVal(BigInteger val) {
    return this.otherBlockchainContract.getABI_setVal(val);
  }

  public String getRlpFunctionSignature_SetValues(BigInteger val1, BigInteger val2) {
    return this.otherBlockchainContract.getABI_setValues(val1, val2);
  }

  public boolean storageIsLocked() throws Exception {
    return this.otherBlockchainContract.isLocked(BigInteger.ZERO).send();
  }

  public void showValues() throws Exception {
    LOG.info("Other Blockchain: KEY_FOR_VAL: {}", this.otherBlockchainContract.getVal().send());
  }
}
