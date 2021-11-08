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
package net.consensys.gpact.examples.singlebc.write;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.examples.singlebc.write.soliditywrappers.ContractB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Bc2ContractB extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc2ContractB.class);

  ContractB contractB;

  public Bc2ContractB(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts() throws Exception {
    this.contractB = ContractB.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.info(
        "ContractB deployed to {} on blockchain {}",
        this.contractB.getContractAddress(),
        this.blockchainId);
  }

  public void showEvents(TransactionReceipt txR) {
    LOG.info("ContractA: Value Read Events");
    List<ContractB.ValueWrittenEventResponse> events = this.contractB.getValueWrittenEvents(txR);
    for (ContractB.ValueWrittenEventResponse e : events) {
      LOG.info(" Value: {}", e._val);
    }
  }

  public void showValueWritten() throws Exception {
    LOG.info("ContractB: Value: {}", this.contractB.getVal().send());
  }

  public TransactionReceipt separatedDoWrite(BigInteger val) throws Exception {
    TransactionReceipt txR = this.contractB.set(val).send();
    StatsHolder.logGas("doSeparatedWrite", txR.getGasUsed());
    return txR;
  }
}
