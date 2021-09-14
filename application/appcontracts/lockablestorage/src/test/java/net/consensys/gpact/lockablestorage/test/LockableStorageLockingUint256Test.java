package net.consensys.gpact.lockablestorage.test;

import org.junit.Test;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.exceptions.ContractCallException;

import java.math.BigInteger;

public class LockableStorageLockingUint256Test extends AbstractLockableStorageTest {


  // By default, locking is off. As such, setting a uint256 will not encounter any locks.
  @Test
  public void singleBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    this.lockableStorageContract.test_setUint256(theUint, BigInteger.ONE).send();
  }

  // By default, locking is off. As such, setting a uint256 as part of a cross-blockchain
  // call will not encounter any locks.
  @Test
  public void crossBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;
    BigInteger val = BigInteger.TEN;

    // Check starting conditions
    assert(this.mockCrossBlockchainControlContract.isSingleBlockchainCall().send());
    assert(!this.lockableStorageContract.isLocked(theUint).send());
    assert(this.lockableStorageContract.test_getUint256Provisional(theUint).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableStorageContract.test_getUint256Committed(theUint).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableStorageContract.test_getUint256(theUint).send().compareTo(BigInteger.ZERO) == 0);

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();
    // The mock CrossBlockchainControlContract should now indicate a cross-blockchain call.
    assert(!this.mockCrossBlockchainControlContract.isSingleBlockchainCall().send());

    this.lockableStorageContract.test_setUint256(theUint, val).send();

    // The contract item should now be locked.
    assert(this.lockableStorageContract.isLocked(theUint).send());

    // Once locked, values can no longer be read via the get method. See get committed and get provisional.
    // This will fail as the contract is locked.
    try {
      this.lockableStorageContract.test_getUint256(theUint).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // Do nothing
    }

    assert(this.lockableStorageContract.test_getUint256Provisional(theUint).send().compareTo(val) == 0);
    assert(this.lockableStorageContract.test_getUint256Committed(theUint).send().compareTo(BigInteger.ZERO) == 0);
  }

  // An exception is thrown if a locked contract item is to be written to by a single blockchain call.
  @Test
  public void singleBlockchainLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    this.lockableStorageContract.test_setUint256(theUint, BigInteger.ONE).send();

    // Zero Root Blockchain Id is deemed to indicate an no active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(zero).send();

    // This will fail as the contract is locked.
    try {
      this.lockableStorageContract.test_setUint256(theUint, BigInteger.ONE).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      processTransactionException(ex);
    }
  }

  // An exception is thrown if a locked contract is to be written to by a single blockchain call.
  @Test
  public void crossBlockchainDifferentCall() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    assert(!this.lockableStorageContract.isLocked(theUint).send());
    // Ensure the lockable storage contract perceives it is in the midst of a cross-blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();
    // Set a variable. This will lock the contract.
    this.lockableStorageContract.test_setUint256(theUint, BigInteger.ONE).send();
    assert(this.lockableStorageContract.isLocked(theUint).send());

    // Simulate the end of this cross-blockchain call by clearing the list of locked contracts.
    this.mockCrossBlockchainControlContract.clearListOfLockedContracts().send();

    // Attempt to set a value in the lockable storage contract. This should fail because the contract
    // is locked, and the cross-blockchain control contract will indicate that the contract was not
    // locked by this cross-blockchain segment.
    try {
      this.lockableStorageContract.test_setUint256(theUint, BigInteger.ONE).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      processTransactionException(ex);
    }

    // This will fail as the contract is locked.
    try {
      this.lockableStorageContract.test_getUint256(theUint).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // thrown as expected
    }
  }

  // Check that finalising with a commit = true works.
  @Test
  public void commit() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;
    BigInteger theMap = BigInteger.ONE;
    BigInteger theMapKey = BigInteger.TWO;

    BigInteger val1 = BigInteger.TEN;
    BigInteger val2 = BigInteger.valueOf(13);

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    // Set some values.
    this.lockableStorageContract.test_setUint256(theUint, val1).send();
    this.lockableStorageContract.test_setMapValue(theMap, theMapKey, val2).send();

    // Commit changes.
    this.lockableStorageContract.finalise(true, crosschainRootTxId).send();

    assert(!this.lockableStorageContract.isLocked(theUint).send());

    assert(this.lockableStorageContract.test_getUint256(theUint).send().compareTo(val1) == 0);
    assert(this.lockableStorageContract.test_getMapValue(theMap, theMapKey).send().compareTo(val2) == 0);
  }


  // Check that finalising with a commit = false works.
  @Test
  public void ignore() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;
    BigInteger theMap = BigInteger.ONE;
    BigInteger theMapKey = BigInteger.TWO;

    BigInteger val1 = BigInteger.TEN;
    BigInteger val2 = BigInteger.valueOf(13);

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setCrosschainRootTxId(crosschainRootTxId).send();

    // Set some values.
    this.lockableStorageContract.test_setUint256(theUint, val1).send();
    this.lockableStorageContract.test_setMapValue(theMap, theMapKey, val2).send();

    // Commit changes.
    this.lockableStorageContract.finalise(false, crosschainRootTxId).send();

    assert(!this.lockableStorageContract.isLocked(theUint).send());

    assert(this.lockableStorageContract.test_getUint256(theUint).send().compareTo(BigInteger.ZERO) == 0);
    assert(this.lockableStorageContract.test_getMapValue(theMap, theMapKey).send().compareTo(BigInteger.ZERO) == 0);
  }

}