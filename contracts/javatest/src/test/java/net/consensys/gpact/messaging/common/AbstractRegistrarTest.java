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
package net.consensys.gpact.messaging.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;

public abstract class AbstractRegistrarTest extends AbstractWeb3Test {
  protected MessagingRegistrar registrarContract;

  protected void deployRegistrarContract() throws Exception {
    this.registrarContract =
        MessagingRegistrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }

  protected MessagingRegistrar deployRegistrarContract(TransactionManager tm1) throws Exception {
    return MessagingRegistrar.deploy(this.web3j, tm1, this.freeGasProvider).send();
  }

  protected MessagingRegistrar loadContract(TransactionManager tm1) throws Exception {
    return MessagingRegistrar.load(
        this.registrarContract.getContractAddress(), this.web3j, tm1, this.freeGasProvider);
  }

  protected void addBlockchain(BigInteger blockchainId, String initialSigner) throws Exception {
    List<String> signers = new ArrayList<>();
    signers.add(initialSigner);
    addBlockchain(blockchainId, signers);
  }

  protected void addBlockchain(BigInteger blockchainId, List<String> signers) throws Exception {
    TransactionReceipt receipt =
        this.registrarContract.addSignersSetThreshold(blockchainId, signers, BigInteger.ONE).send();
    assert (receipt.isStatusOK());
  }
}
