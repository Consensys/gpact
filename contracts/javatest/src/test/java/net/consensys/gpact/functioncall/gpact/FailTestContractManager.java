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
package net.consensys.gpact.functioncall.gpact;

import java.io.IOException;
import java.math.BigInteger;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.functioncall.FailureTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

/**
 * Manage the FailureTest contract. This is used as part of tests to check that failures in certain
 * parts of the call tree are handled correctly by the GPACT protocol.
 */
public class FailTestContractManager extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(FailTestContractManager.class);

  String entity;

  FailureTest failureTestContract;

  public FailTestContractManager(
      final String entity, Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
    this.entity = entity;
  }

  public void deployContracts(
      String cbcContractAddress, BlockchainId otherBlockchainId, String otherContractAddress)
      throws Exception {
    this.failureTestContract =
        FailureTest.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                cbcContractAddress,
                otherBlockchainId.asBigInt(),
                otherContractAddress)
            .send();
    LOG.info(
        " Deployed {} contracts: {}", this.entity, this.failureTestContract.getContractAddress());
  }

  public String contractAddress() {
    return this.failureTestContract.getContractAddress();
  }

  public String getAbi(
      BigInteger totalCallDepth, BigInteger currentCallDepth, BigInteger failCallDepth) {
    return this.failureTestContract.getABI_callRemote(
        totalCallDepth, currentCallDepth, failCallDepth);
  }
}
