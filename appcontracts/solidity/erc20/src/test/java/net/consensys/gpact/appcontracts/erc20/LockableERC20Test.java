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

import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.StatsHolder;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;

/*
 * Test for LockableERC20.
 */
public class LockableERC20Test extends AbstractERC20Test {
  /**
   * Check operation of the contract prior to any data being added to the contract.
   * That is, check the default configuration immediately after deployment.
   */
  @Test
  public void checkDeployment() throws Exception {
    setupWeb3();
    deployContracts();

    assert(this.lockableERC20.totalSupply().send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.totalSupplyProvisional().send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.totalSupplyMin().send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.totalSupplyMax().send().compareTo(INITIAL_SUPPLY_BIG) == 0);

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(INITIAL_SUPPLY_BIG) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOfMin(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);

    assert(this.lockableERC20.accountPallelizationFactor().send().compareTo(BigInteger.valueOf(DEFAULT_ACCOUNT_PARALLELIZATION_FACTOR)) == 0);
    assert(this.lockableERC20.erc20PallelizationFactor().send().compareTo(BigInteger.valueOf(DEFAULT_ERC20_PARALLELIZATION_FACTOR)) == 0);
  }

  @Test
  public void oneTransferNoLocking() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount = BigInteger.valueOf(7);
    BigInteger remainder = INITIAL_SUPPLY_BIG.subtract(amount);

    TransactionReceipt txr = this.lockableERC20.transfer(this.otherAccount, amount).send();

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(amount) == 0);
    assert(this.lockableERC20.balanceOfMin(this.otherAccount).send().compareTo(amount) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(amount) == 0);
  }

  @Test
  public void oneTransferLocking() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount = BigInteger.valueOf(7);
    BigInteger remainder = INITIAL_SUPPLY_BIG.subtract(amount);

    BigInteger rootBlockchainId = BigInteger.ONE;
    BigInteger crosschainTransactionId = BigInteger.TEN;

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(rootBlockchainId).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(crosschainTransactionId).send();

    try {
      this.lockableERC20.transfer(this.otherAccount, amount).send();
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(amount) == 0);

    // Make the contract think a lockable transaction is no longer in progress.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ZERO).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ZERO).send();

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, rootBlockchainId, crosschainTransactionId).send();

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(amount) == 0);
    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(amount) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(amount) == 0);
  }


  @Test
  public void multipleParallelTransfersLocking() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount1 = BigInteger.valueOf(7);
    BigInteger amount2 = BigInteger.valueOf(11);
    BigInteger amount3 = BigInteger.valueOf(13);
    BigInteger amount4 = BigInteger.valueOf(17);
    BigInteger amount5 = BigInteger.valueOf(19);
    BigInteger amount6 = BigInteger.valueOf(23);
    BigInteger total = amount1.add(amount2).add(amount3).add(amount4).add(amount5);
    BigInteger remainder = INITIAL_SUPPLY_BIG.subtract(total);

    BigInteger rootBlockchainId = BigInteger.ONE;
    BigInteger crosschainTransactionId = BigInteger.TEN;

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(rootBlockchainId).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(crosschainTransactionId).send();

    this.lockableERC20.transfer(this.otherAccount, amount1).send();
    this.lockableERC20.transfer(this.otherAccount, amount2).send();
    this.lockableERC20.transfer(this.otherAccount, amount3).send();
    this.lockableERC20.transfer(this.otherAccount, amount4).send();
    this.lockableERC20.transfer(this.otherAccount, amount5).send();

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(INITIAL_SUPPLY_BIG) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(total) == 0);

    try {
      this.lockableERC20.transfer(this.otherAccount, amount6).send();
      throw new Error("Unexpectedly no revert when account parallelization factor exceeded");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }

    // Make sure the provisional values have not changed
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(total) == 0);

    // Make the contract think a lockable transaction is no longer in progress.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ZERO).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ZERO).send();

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, rootBlockchainId, crosschainTransactionId).send();

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(total) == 0);
    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(total) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(total) == 0);
  }



}

