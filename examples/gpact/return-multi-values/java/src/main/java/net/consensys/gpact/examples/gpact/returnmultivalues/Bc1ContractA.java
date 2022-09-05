/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.examples.gpact.returnmultivalues;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Bc1ContractA extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc1ContractA.class);

  ContractA contractA;

  public Bc1ContractA(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContracts(
      String cbcContractAddress, BigInteger busLogicBlockchainId, String busLogicContractAddress)
      throws Exception {
    this.contractA =
        ContractA.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                cbcContractAddress,
                busLogicBlockchainId,
                busLogicContractAddress)
            .send();
    LOG.info(
        "ContractA deployed to {} on blockchain {}",
        this.contractA.getContractAddress(),
        this.blockchainId);
  }

  public String getContractAddress() {
    return this.contractA.getContractAddress();
  }

  public String getRlpFunctionSignature_DoCrosschainRead(BigInteger val) {
    return this.contractA.getABI_doCrosschainRead(val);
  }

  public void checkEvents(TransactionReceipt txR, BigInteger expectedVal) throws Exception {
    LOG.info("ContractA: Value Events");
    List<ContractA.FailedEventResponse> failedEvents = this.contractA.getFailedEvents(txR);
    if (!failedEvents.isEmpty()) {
      LOG.error("Fail Event emitted");
      throw new Exception("Fail Event emitted");
    }

    List<ContractA.ReturnValsEventResponse> returnValsEvents =
        this.contractA.getReturnValsEvents(txR);
    if (returnValsEvents.size() != 1) {
      LOG.error("Unexpected number of return values events: {}", returnValsEvents.size());
      throw new Exception("Unexpected number of return values events");
    }
    for (ContractA.ReturnValsEventResponse e : returnValsEvents) {
      if (e.val1.compareTo(expectedVal) != 0) {
        LOG.error("Unexpected value returned (Val1): {}", e.val1);
      }
      if (e.val3.equalsIgnoreCase(getContractAddress())) {
        LOG.error("Unexpected value returned (Val3): {}", e.val3);
      }

      LOG.info(" Val1: {}", e.val1);
      LOG.info(" Val2: {}", new BigInteger(1, e.val2).toString(16));
      LOG.info(" Val3: {}", e.val3);
    }
  }
}
