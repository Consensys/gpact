package tech.pegasys.ltacfc.lockablestorage.test;

import org.junit.Test;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.exceptions.ContractCallException;

import java.math.BigInteger;

public class LockableStorageLockingBytesTest extends AbstractLockableStorageTest {


  // By default, locking is off. As such, setting a uint256 will not encounter any locks.
  @Test
  public void singleBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key = BigInteger.ZERO;
    byte[] val = new byte[]{0x15};

    this.storageWrapper.test_setBytes(key, val).send();
  }

  // By default, locking is off. As such, setting a uint256 as part of a cross-blockchain
  // call will not encounter any locks.
  @Test
  public void crossBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key = BigInteger.ZERO;
    byte[] val = new byte[]{0x15};

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();
    // The mock CrossBlockchainControlContract should now indicate a cross-blockchain call.
    assert(!this.mockCrossBlockchainControlContract.isSingleBlockchainCall().send());

    this.storageWrapper.test_setBytes(key, val).send();

    // The contract should now be locked.
    assert(this.lockableStorageContract.locked().send());

    // Even when the value is in provisional storage, should be able to return the value.
    assert(this.storageWrapper.test_getBytes(key).send()[0] == val[0]);
  }

  // An exception is thrown if a locked contract is to be written to by a single blockchain call.
  @Test
  public void singleBlockchainLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key = BigInteger.ZERO;
    byte[] val = new byte[]{0x15};

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    this.storageWrapper.test_setBytes(key, val).send();

    // Zero Root Blockchain Id is deemed to indicate an no active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ZERO).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.test_setBytes(key, val).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }
  }

  // An exception is thrown if a locked contract is to be written to by a single blockchain call.
  @Test
  public void crossBlockchainDifferentCall() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key = BigInteger.ZERO;
    byte[] val1 = new byte[]{0x15};
    byte[] val2 = new byte[]{0x19};

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    this.storageWrapper.test_setBytes(key, val1).send();

    // Simulate another cross-blockchain call by using a different Root Blockchain Id.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.TWO).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.test_setBytes(key, val1).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.test_getBytes(key).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // thrown as expected
    }

    // Same root blockchain, different transaction.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ONE).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.test_setBytes(key, val1).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.test_getBytes(key).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // Revert thrown as expected
    }

    // Same root blockchain and same transaction.
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ZERO).send();

    // Writing and reading should now work.
    this.storageWrapper.test_setBytes(key, val2).send();
    assert(this.storageWrapper.test_getBytes(key).send()[0] == val2[0]);
  }

  // Check that finalising with a commit = true works.
  @Test
  public void commit() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key0 = BigInteger.ZERO;
    BigInteger key1 = BigInteger.ONE;
    byte[] val0 = new byte[]{0x15};
    byte[] val1 = new byte[]{0x19};

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    // Set some values.
    this.storageWrapper.test_setBytes(key0, val0).send();
    this.storageWrapper.test_setBytes(key1, val1).send();

    // Commit changes.
    this.lockableStorageContract.finalise(true).send();

    assert(!this.lockableStorageContract.locked().send());

    assert(this.storageWrapper.test_getBytes(key0).send()[0] == val0[0]);
    assert(this.storageWrapper.test_getBytes(key1).send()[0] == val1[0]);
  }


  // Check that finalising with a commit = false works.
  @Test
  public void ignore() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger key0 = BigInteger.ZERO;
    BigInteger key1 = BigInteger.ONE;
    byte[] val0 = new byte[]{0x15};
    byte[] val1 = new byte[]{0x19};

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    // Set some values.
    this.storageWrapper.test_setBytes(key0, val0).send();
    this.storageWrapper.test_setBytes(key1, val1).send();

    // Commit changes.
    this.lockableStorageContract.finalise(false).send();

    assert(!this.lockableStorageContract.locked().send());

    assert(this.storageWrapper.test_getBytes(key0).send().length == 0);
    assert(this.storageWrapper.test_getBytes(key1).send().length == 0);
  }

}