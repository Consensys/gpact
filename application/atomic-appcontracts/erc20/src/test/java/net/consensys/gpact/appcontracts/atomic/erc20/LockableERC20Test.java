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
package net.consensys.gpact.appcontracts.atomic.erc20;

import net.consensys.gpact.common.RevertReason;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;

/*
 * Test for LockableERC20.
 */
public class LockableERC20Test extends AbstractERC20Test {
  static byte[] crosschainRootTxId;
  static byte[] zero;

  static {
    crosschainRootTxId = new byte[32];
    crosschainRootTxId[0] = 10;
    zero = new byte[32];
  }


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
    assert(this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);

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

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

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
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(zero).send();

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, crosschainRootTxId).send();

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


    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

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
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(zero).send();

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, crosschainRootTxId).send();

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(remainder) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(remainder) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(total) == 0);
    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(total) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(total) == 0);
  }

  @Test
  public void approveLockingAndNoLocking() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount1 = BigInteger.valueOf(7);
    BigInteger amount2 = BigInteger.valueOf(11);

    this.lockableERC20.approve(this.otherAccount, amount1).send();
    assert (this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(amount1) == 0);
    this.lockableERC20.approve(this.otherAccount, amount2).send();
    assert (this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(amount2) == 0);

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    try {
      this.lockableERC20.approve(this.otherAccount, BigInteger.ZERO).send();
      throw new Error("Unexpectedly no revert when approve called while locked");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }
  }


  @Test
  public void incDecAllowanceNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount1 = BigInteger.valueOf(7);
    BigInteger amount2 = BigInteger.valueOf(11);
    BigInteger amount3 = BigInteger.valueOf(13);

    this.lockableERC20.increaseAllowance(this.otherAccount, amount2).send();
    assert (this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(amount2) == 0);
    this.lockableERC20.decreaseAllowance(this.otherAccount, amount1).send();
    assert (this.lockableERC20.allowance(this.owner, this.otherAccount).send()
            .compareTo(amount2.subtract(amount1)) == 0);
    try {
      this.lockableERC20.decreaseAllowance(this.otherAccount, amount3).send();
      throw new Error("Unexpectedly no revert when decreased allowance below zero");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }
    assert (this.lockableERC20.allowance(this.owner, this.otherAccount).send()
            .compareTo(amount2.subtract(amount1)) == 0);
  }

  @Test
  public void incDecAllowanceLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger amount1 = BigInteger.valueOf(7);
    BigInteger amount2 = BigInteger.valueOf(11);
    BigInteger amount3 = BigInteger.valueOf(13);
    BigInteger amount4 = BigInteger.valueOf(17);
    BigInteger amount5 = BigInteger.valueOf(19);
    BigInteger amount6 = BigInteger.valueOf(23);
    BigInteger amount7 = BigInteger.valueOf(29);
    BigInteger amount8 = BigInteger.valueOf(31);
    BigInteger amount9 = BigInteger.valueOf(37);
    BigInteger amount10 = BigInteger.valueOf(41);
    BigInteger amount11 = BigInteger.valueOf(43);

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    // Should be able to do up to the parallelization factor for increases during a crosschain
    // transaction.
    this.lockableERC20.increaseAllowance(this.otherAccount, amount6).send();
    this.lockableERC20.increaseAllowance(this.otherAccount, amount7).send();
    this.lockableERC20.increaseAllowance(this.otherAccount, amount8).send();
    this.lockableERC20.increaseAllowance(this.otherAccount, amount9).send();
    this.lockableERC20.increaseAllowance(this.otherAccount, amount10).send();
    try {
      this.lockableERC20.increaseAllowance(this.otherAccount, amount11).send();
      throw new Error("Unexpectedly no revert when increaseAllowance called in excess of parallelization factor");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }
    BigInteger totalAdd = amount6.add(amount7).add(amount8).add(amount9).add(amount10);

    assert(this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableERC20.allowanceProvisional(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);
    assert(this.lockableERC20.allowanceMax(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);
    assert(this.lockableERC20.allowanceMin(this.owner, this.otherAccount).send().compareTo(BigInteger.ZERO) == 0);

    // Any decrease will fail at the moment as the minimum allowance is zero.
    try {
      this.lockableERC20.decreaseAllowance(this.otherAccount, amount11).send();
      throw new Error("Unexpectedly no revert when decreaseAllowance called that could make allowance negative");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }

    // Make the contract think a lockable transaction is no longer in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(zero).send();

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, crosschainRootTxId).send();

    assert(this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);
    assert(this.lockableERC20.allowanceProvisional(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);
    assert(this.lockableERC20.allowanceMax(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);
    assert(this.lockableERC20.allowanceMin(this.owner, this.otherAccount).send().compareTo(totalAdd) == 0);

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    this.lockableERC20.decreaseAllowance(this.otherAccount, amount1).send();
    this.lockableERC20.decreaseAllowance(this.otherAccount, amount2).send();
    this.lockableERC20.decreaseAllowance(this.otherAccount, amount3).send();
    this.lockableERC20.decreaseAllowance(this.otherAccount, amount4).send();
    this.lockableERC20.decreaseAllowance(this.otherAccount, amount5).send();

    // Any decrease will fail at the moment as more than parallelization factor number of simultaneous decreases.
    try {
      this.lockableERC20.decreaseAllowance(this.otherAccount, amount1).send();
      throw new Error("Unexpectedly no revert when decreaseAllowance called that could make allowance negative");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }

    this.lockableERC20.increaseAllowance(this.otherAccount, amount11).send();

    BigInteger start = totalAdd;
    totalAdd = amount11;
    BigInteger totalDecrease = amount1.add(amount2).add(amount3).add(amount4).add(amount5);

    assert(this.lockableERC20.allowance(this.owner, this.otherAccount).send().compareTo(start) == 0);
    assert(this.lockableERC20.allowanceProvisional(this.owner, this.otherAccount).send().compareTo(start.add(totalAdd).subtract(totalDecrease)) == 0);
    assert(this.lockableERC20.allowanceMax(this.owner, this.otherAccount).send().compareTo(start.add(totalAdd)) == 0);
    assert(this.lockableERC20.allowanceMin(this.owner, this.otherAccount).send().compareTo(start.subtract(totalDecrease)) == 0);
  }

  @Test
  public void transferFromNotLocked() throws Exception {
    setupWeb3();
    deployContracts();
    loadOtherCredentialsContract();

    BigInteger amount1 = BigInteger.valueOf(100);
    BigInteger amount2 = BigInteger.valueOf(11);
    BigInteger amount3 = BigInteger.valueOf(7);

    this.lockableERC20.transfer(otherAccount, amount1).send();
    this.otherLockableERC20.approve(this.owner, amount2).send();
    assert(this.lockableERC20.allowance(this.otherAccount, this.owner).send().compareTo(amount2) == 0);
    try {
      this.lockableERC20.transferFrom(otherAccount, this.owner, amount1).send();
      throw new Error("Unexpectedly no revert when transferFrom greater than allowance");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }
    try {
    this.lockableERC20.transferFrom(this.otherAccount, this.owner, amount3).send();
    } catch (TransactionException ex) {
      System.err.println(" Revert ReasonX: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    BigInteger balOther = amount1.subtract(amount3);
    BigInteger balOwner = INITIAL_SUPPLY_BIG.subtract(balOther);

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(balOwner) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(balOwner) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(balOwner) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(balOther) == 0);
    assert(this.lockableERC20.balanceOfMin(this.otherAccount).send().compareTo(balOther) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(balOther) == 0);
  }


  @Test
  public void transferFromLocked() throws Exception {
    setupWeb3();
    deployContracts();
    loadOtherCredentialsContract();

    BigInteger amount1 = BigInteger.valueOf(100);
    BigInteger amount2 = BigInteger.valueOf(11);
    BigInteger amount3 = BigInteger.valueOf(7);

    this.lockableERC20.transfer(otherAccount, amount1).send();

    // Make the contract think a lockable transaction is in progress.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    this.otherLockableERC20.increaseAllowance(this.owner, amount2).send();
    try {
      this.lockableERC20.transferFrom(otherAccount, this.owner, amount3).send();
      throw new Error("Unexpectedly no revert when transferFrom greater than min allowance");
    } catch (TransactionException ex) {
      System.err.println(" Revert Reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
    }

    // Unlock the contracts and apply the updates.
    this.lockableERC20.finalise(true, crosschainRootTxId).send();
    // The contract will still detect a crosschain transaction because the cbc is still indicating this.

    this.lockableERC20.transferFrom(otherAccount, this.owner, amount3).send();

    BigInteger balOther = amount1.subtract(amount3);
    BigInteger balOwner = INITIAL_SUPPLY_BIG.subtract(balOther);

    assert(this.lockableERC20.balanceOf(this.owner).send().compareTo(INITIAL_SUPPLY_BIG.subtract(amount1)) == 0);
    assert(this.lockableERC20.balanceOfMin(this.owner).send().compareTo(INITIAL_SUPPLY_BIG.subtract(amount1)) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.owner).send().compareTo(balOwner) == 0);

    assert(this.lockableERC20.balanceOf(this.otherAccount).send().compareTo(amount1) == 0);
    assert(this.lockableERC20.balanceOfMin(this.otherAccount).send().compareTo(balOther) == 0);
    assert(this.lockableERC20.balanceOfProvisional(this.otherAccount).send().compareTo(balOther) == 0);
  }



}

