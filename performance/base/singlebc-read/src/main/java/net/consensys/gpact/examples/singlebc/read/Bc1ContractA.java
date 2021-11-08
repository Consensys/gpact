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
package net.consensys.gpact.examples.singlebc.read;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.examples.singlebc.read.soliditywrappers.ContractA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Bc1ContractA extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(Bc1ContractA.class);

  ContractA contractA;

  public Bc1ContractA(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(String busLogicContractAddress) throws Exception {
    this.contractA =
        ContractA.deploy(this.web3j, this.tm, this.gasProvider, busLogicContractAddress).send();
    LOG.info(
        "ContractA deployed to {} on blockchain {}",
        this.contractA.getContractAddress(),
        this.blockchainId);
  }

  public TransactionReceipt doRead() throws Exception {
    TransactionReceipt txR = this.contractA.doRead().send();
    StatsHolder.logGas("doRead", txR.getGasUsed());
    return txR;
  }

  public void showEvents(TransactionReceipt txR) {
    LOG.info("ContractA: Value Read Events");
    List<ContractA.ValueReadEventResponse> events = this.contractA.getValueReadEvents(txR);
    for (ContractA.ValueReadEventResponse e : events) {
      LOG.info(" Value: {}", e._val);
    }
  }

  public void showValueRead() throws Exception {
    LOG.info("ContractA: Value: {}", this.contractA.getVal().send());
  }

  public void separatedGet() throws Exception {
    TransactionReceipt txR = this.contractA.separatedDoRead(BigInteger.ONE).send();
    StatsHolder.logGas("Separated Get", txR.getGasUsed());
  }
}
