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
package net.consensys.gpact.appcontracts.erc20;

import net.consensys.gpact.appcontracts.erc20.soliditywrappers.LockableERC20PresetFixedSupply;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.appcontracts.erc20.soliditywrappers.MockCbcForERC20Test;

import java.math.BigInteger;

/**
 * Check operation, assuming the calls are single blockchain (that is not part of a
 * cross-blockchain) call.
 */
public class AbstractERC20Test extends AbstractWeb3Test {
  public static final int DEFAULT_ACCOUNT_PARALLELIZATION_FACTOR = 5;
  public static final int DEFAULT_ERC20_PARALLELIZATION_FACTOR = 10;


  LockableERC20PresetFixedSupply lockableERC20;
  MockCbcForERC20Test mockCrossBlockchainControlContract;

  public static final int INITIAL_SUPPLY = 1000000;
  static final BigInteger INITIAL_SUPPLY_BIG = BigInteger.valueOf(INITIAL_SUPPLY);
  public String owner;
  public String otherAccount;


  protected void deployContracts() throws Exception {
    this.owner = this.credentials.getAddress();
    this.otherAccount = createNewIdentity().getAddress();

    this.mockCrossBlockchainControlContract = MockCbcForERC20Test.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.lockableERC20 = LockableERC20PresetFixedSupply.deploy(this.web3j, this.tm, this.freeGasProvider,
            "Wrapped ETH", "wETH",
            this.mockCrossBlockchainControlContract.getContractAddress(),
            INITIAL_SUPPLY_BIG, this.owner
            ).send();
  }
}

