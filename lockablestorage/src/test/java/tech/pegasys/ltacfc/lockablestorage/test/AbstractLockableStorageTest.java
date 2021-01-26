/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.lockablestorage.test;

import org.junit.Test;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.MockCbcForLockableStorageTest;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.TestLockableStorageWrapper;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;


import static org.junit.Assert.assertEquals;

/**
 * Check operation, assuming the calls are single blockchain (that is not part of a
 * cross-blockchain) call.
 */
public class AbstractLockableStorageTest extends AbstractWeb3Test {
  TestLockableStorageWrapper storageWrapper;
  LockableStorage lockableStorageContract;
  MockCbcForLockableStorageTest mockCrossBlockchainControlContract;




  protected void deployContracts() throws Exception {
    this.mockCrossBlockchainControlContract = MockCbcForLockableStorageTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.mockCrossBlockchainControlContract.getContractAddress()).send();
    this.storageWrapper = TestLockableStorageWrapper.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.storageWrapper.getContractAddress()).send();
  }


  @Test
  public void checkDeployment() throws Exception {
    setupWeb3();
    deployContracts();

    assert(!this.lockableStorageContract.locked().send());
    assertEquals(this.lockableStorageContract.businessLogicContract().send(), this.storageWrapper.getContractAddress());
  }

}

