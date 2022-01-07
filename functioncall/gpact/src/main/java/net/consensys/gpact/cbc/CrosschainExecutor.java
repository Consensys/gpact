/*
 * Copyright 2020 ConsenSys Software Inc
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
package net.consensys.gpact.cbc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

/**
 * Holds the state for a crosschain call. A separate instance of this class is needed for each
 * crosschain call.
 */
public class CrosschainExecutor {
  static final Logger LOG = LogManager.getLogger(CrosschainExecutor.class);

  // The maximum number of calls that can be done from any one function. The value
  // has been set to an aritrarily largish number. If people write complicated
  // functions that have a 1000 calls, or write functions that have loops and
  // do many cross-blockchain function calls, then this number might need to be made larger.
  private static final BigInteger MAX_CALLS_FROM_ONE_FUNCTION = BigInteger.valueOf(1000);

  private static final BigInteger ROOT_CALL_MAP_KEY = calculateRootCallMapKey();

  private static BigInteger calculateRootCallMapKey() {
    List<BigInteger> rootCallPath = new ArrayList<BigInteger>();
    rootCallPath.add(BigInteger.ZERO);
    return callPathToMapKey(rootCallPath);
  }

  private SignedEvent signedStartEvent;
  private SignedEvent signedRootEvent;
  // Key for this map is the call path of the caller.
  private final Map<BigInteger, SignedEvent[]> signedSegmentEvents = new ConcurrentHashMap<>();

  // Key for this map is the call path of the caller.
  private final Map<BigInteger, TransactionReceipt> transactionReceipts = new HashMap<>();

  // Key for this map is the blockchain id that the segment occurred on.
  private final Map<BlockchainId, List<SignedEvent>> signedSegmentEventsWithLockedContracts =
      new ConcurrentHashMap<>();

  CrossControlManagerGroup crossControlManagerGroup;

  protected byte[] callGraph;

  protected BigInteger timeout;
  protected BigInteger crossBlockchainTransactionId;
  protected BlockchainId rootBcId;

  boolean success = false;

  public CrosschainExecutor(CrossControlManagerGroup crossControlManagerGroup) {
    this.crossControlManagerGroup = crossControlManagerGroup;
  }

  public void init(
      byte[] callGraph, BigInteger timeout, BigInteger transactionId, BlockchainId rootBcId) {
    this.callGraph = callGraph;
    this.timeout = timeout;
    this.crossBlockchainTransactionId = transactionId;
    this.rootBcId = rootBcId;
  }

  public void startCall() throws Exception {
    CrossControlManager rootCbcContract =
        this.crossControlManagerGroup.getCbcContract(this.rootBcId);
    MessagingVerificationInterface messaging =
        this.crossControlManagerGroup.getMessageVerification(this.rootBcId);

    Tuple<TransactionReceipt, byte[], Boolean> result =
        rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, this.callGraph);
    TransactionReceipt txr = result.getFirst();
    byte[] startEventData = result.getSecond();

    this.signedStartEvent =
        messaging.getSignedEvent(
            this.crossControlManagerGroup.getAllBlockchainIds(),
            txr,
            startEventData,
            rootCbcContract.getCbcContractAddress(),
            CrossControlManager.START_EVENT_SIGNATURE);
  }

  public void segment(
      BlockchainId blockchainId,
      BlockchainId callerBlockchainId,
      List<BigInteger> callPath,
      final int parentNumCalledFunctions)
      throws Exception {
    if (callPath.size() == 0) {
      throw new Exception("Invalid call path length for segment: " + 0);
    }

    BigInteger mapKey = callPathToMapKey(callPath);
    int callPathIndex = (callPath.get(callPath.size() - 1)).intValue();

    CrossControlManager segmentCbcContract =
        this.crossControlManagerGroup.getCbcContract(blockchainId);
    MessagingVerificationInterface messaging =
        this.crossControlManagerGroup.getMessageVerification(blockchainId);

    SignedEvent signedEvents[] =
        this.signedSegmentEvents.computeIfAbsent(mapKey, k -> new SignedEvent[] {});
    Tuple<TransactionReceipt, byte[], Boolean> result =
        segmentCbcContract.segment(this.signedStartEvent, signedEvents, callPath);
    TransactionReceipt txr = result.getFirst();
    byte[] segEventData = result.getSecond();
    boolean noLockedContracts = result.getThird();

    SignedEvent signedSegEvent =
        messaging.getSignedEvent(
            this.crossControlManagerGroup.getAllBlockchainIds(),
            txr,
            segEventData,
            segmentCbcContract.getCbcContractAddress(),
            CrossControlManager.SEGMENT_EVENT_SIGNATURE);
    this.transactionReceipts.put(mapKey, txr);

    // Store the segment event for the call that has just occurred to the map so it can be accessed
    // when needed.
    BigInteger parentMapKey = determineMapKeyOfCaller(callPath);
    signedEvents =
        this.signedSegmentEvents.computeIfAbsent(
            parentMapKey, k -> new SignedEvent[parentNumCalledFunctions]);
    // Make sure the events are in the same order as they are called by the calling function.
    signedEvents[callPathIndex - 1] = signedSegEvent;

    // Add the proof to the list of segments that have contracts that need to be unlocked.
    if (!noLockedContracts) {
      List<SignedEvent> signedEvents1 =
          this.signedSegmentEventsWithLockedContracts.computeIfAbsent(
              blockchainId, k -> new ArrayList<>());
      signedEvents1.add(signedSegEvent);
    }
  }

  public void root() throws Exception {
    CrossControlManager rootCbcContract =
        this.crossControlManagerGroup.getCbcContract(this.rootBcId);
    MessagingVerificationInterface messaging =
        this.crossControlManagerGroup.getMessageVerification(this.rootBcId);
    SignedEvent[] signedSegEvents = this.signedSegmentEvents.get(ROOT_CALL_MAP_KEY);
    Tuple<TransactionReceipt, byte[], Boolean> result =
        rootCbcContract.root(this.signedStartEvent, signedSegEvents);
    TransactionReceipt txr = result.getFirst();
    byte[] rootEventData = result.getSecond();
    this.signedRootEvent =
        messaging.getSignedEvent(
            this.crossControlManagerGroup.getAllBlockchainIds(),
            txr,
            rootEventData,
            rootCbcContract.getCbcContractAddress(),
            CrossControlManager.ROOT_EVENT_SIGNATURE);
    this.transactionReceipts.put(ROOT_CALL_MAP_KEY, txr);
    this.success = rootCbcContract.getRootEventSuccess();
  }

  /**
   * This method executes all of the signalling transactions in parallel. They are for separate
   * blockchains, so this is definitely safe.
   *
   * @throws Exception
   */
  public void doSignallingCalls() throws Exception {
    int numSignalsToSend = this.signedSegmentEventsWithLockedContracts.size();
    if (numSignalsToSend == 0) {
      return;
    }

    CompletableFuture<?>[] transactionReceiptCompletableFutures =
        new CompletableFuture<?>[numSignalsToSend];
    int i = 0;
    for (BlockchainId blockchainId : this.signedSegmentEventsWithLockedContracts.keySet()) {
      List<SignedEvent> signedSegEventsLockedContractsCurrentBlockchain =
          this.signedSegmentEventsWithLockedContracts.get(blockchainId);
      CrossControlManager cbcContract = this.crossControlManagerGroup.getCbcContract(blockchainId);
      transactionReceiptCompletableFutures[i++] =
          cbcContract.signallingAsyncPart1(
              this.signedRootEvent, signedSegEventsLockedContractsCurrentBlockchain);
    }
    CompletableFuture<Void> combinedFuture =
        CompletableFuture.allOf(transactionReceiptCompletableFutures);
    try {
      combinedFuture.get();
    } catch (ExecutionException ex) {
      Throwable cause = ex.getCause();
      if (cause instanceof TransactionException) {
        TransactionException transactionException = (TransactionException) cause;
        LOG.error(
            " Revert Reason: {}",
            RevertReason.decodeRevertReason(
                transactionException.getTransactionReceipt().get().getRevertReason()));
      }
      throw ex;
    }

    i = 0;
    for (BlockchainId blockchainId : this.signedSegmentEventsWithLockedContracts.keySet()) {
      TransactionReceipt receipt =
          (TransactionReceipt) transactionReceiptCompletableFutures[i++].get();
      CrossControlManager cbcContract = this.crossControlManagerGroup.getCbcContract(blockchainId);
      cbcContract.signallingAsyncPart2(receipt);
    }
  }

  public boolean getRootEventSuccess() {
    return this.success;
  }

  /**
   * Return the transaction receipt for part of the call path. This allows applications to see what
   * events have been emitted across the call execution tree.
   *
   * @param callPath Part of the call execution tree to get the transaction receipt for.
   * @return the transaction receipt that was returned with callPath part of the call execution tree
   *     was executed.
   */
  public TransactionReceipt getTransationReceipt(List<BigInteger> callPath) {
    return this.transactionReceipts.get(callPathToMapKey(callPath));
  }

  /**
   * Determine a key that can be used for a map that uniquely identifies the call path's caller.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  private BigInteger determineMapKeyOfCaller(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      return BigInteger.ZERO;
    } else {
      List<BigInteger> parentCallPath = new ArrayList<>(callPath);

      BigInteger bottomOfCallPath = callPath.get(callPath.size() - 1);
      if (bottomOfCallPath.compareTo(BigInteger.ZERO) == 0) {
        parentCallPath.remove(parentCallPath.size() - 1);
      }
      parentCallPath.set(parentCallPath.size() - 1, BigInteger.ZERO);

      return callPathToMapKey(parentCallPath);
    }
  }

  /**
   * Determine a key that can be used for a map that uniquely identifies the call path. A message
   * digest of the call path could be used, but a simpler multiplication method will work just as
   * well.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  private static BigInteger callPathToMapKey(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      throw new RuntimeException("Invalid call path length: " + 0);
    } else {
      BigInteger key = BigInteger.ONE;
      for (BigInteger call : callPath) {
        if (call.compareTo(MAX_CALLS_FROM_ONE_FUNCTION) >= 0) {
          throw new RuntimeException(
              "Maximum calls from one function is: " + MAX_CALLS_FROM_ONE_FUNCTION);
        }

        key = key.multiply(MAX_CALLS_FROM_ONE_FUNCTION);
        key = key.add(call.add(BigInteger.ONE));
      }
      return key;
    }
  }
}
