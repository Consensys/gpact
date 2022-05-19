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
package net.consensys.gpact.examples.sfc.write;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.soliditywrappers.examples.sfc.write.ContractA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

public class Bc1ContractA extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc1ContractA.class);

  private ContractA contractA;

  public Bc1ContractA(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContracts(
      String cbcContractAddress, BlockchainId busLogicBlockchainId, String busLogicContractAddress)
      throws Exception {
    this.contractA =
        ContractA.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                cbcContractAddress,
                busLogicBlockchainId.asBigInt(),
                busLogicContractAddress)
            .send();
    LOG.info(
        "ContractA deployed to {} on blockchain {}",
        this.contractA.getContractAddress(),
        this.blockchainId);
  }

  public String getABI_doCrosschainWrite(BigInteger val) {
    return this.contractA.getABI_doCrosschainWrite(val);
  }

  public String getContractAddress() {
    return this.contractA.getContractAddress();
  }
}
