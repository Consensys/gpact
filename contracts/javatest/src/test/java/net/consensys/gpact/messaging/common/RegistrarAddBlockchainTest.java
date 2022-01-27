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
import org.junit.jupiter.api.Test;
import org.web3j.protocol.exceptions.TransactionException;

public class RegistrarAddBlockchainTest extends AbstractRegistrarTest {

  @Test
  public void addBlockchain() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId, this.credentials.getAddress());
    assert (this.registrarContract
            .getSigningThreshold(blockchainId)
            .send()
            .compareTo(BigInteger.ONE)
        == 0);
  }

  // Do not allow an admin to be added twice.
  @Test
  public void failAddSameBlockchainTwice() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId, this.credentials.getAddress());

    try {
      addBlockchain(blockchainId, this.credentials.getAddress());
      throw new Exception("Unexpectly, no exception thrown");
    } catch (TransactionException ex) {
      // Do nothing.
    }
  }
}
