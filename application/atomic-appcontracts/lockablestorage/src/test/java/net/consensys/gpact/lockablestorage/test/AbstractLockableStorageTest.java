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
package net.consensys.gpact.lockablestorage.test;

import net.consensys.gpact.lockablestorage.soliditywrappers.MockCbcForLockableStorageTest;
import net.consensys.gpact.lockablestorage.soliditywrappers.TestLockableStorageWrapper;
import net.consensys.gpact.common.test.AbstractWeb3Test;


import static org.junit.Assert.assertEquals;

/**
 * Check operation, assuming the calls are single blockchain (that is not part of a
 * cross-blockchain) call.
 */
public class AbstractLockableStorageTest extends AbstractWeb3Test {
  static byte[] crosschainRootTxId;
  static byte[] zero;

  static {
    crosschainRootTxId = new byte[32];
    crosschainRootTxId[0] = 10;
    zero = new byte[32];
  }

  TestLockableStorageWrapper lockableStorageContract;
  MockCbcForLockableStorageTest mockCrossBlockchainControlContract;

  protected void deployContracts() throws Exception {
    this.mockCrossBlockchainControlContract = MockCbcForLockableStorageTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.lockableStorageContract = TestLockableStorageWrapper.deploy(this.web3j, this.tm, this.freeGasProvider,
            this.mockCrossBlockchainControlContract.getContractAddress()).send();
  }
}

