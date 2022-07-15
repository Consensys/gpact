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
package net.consensys.gpact.functioncall.gpact;

import static net.consensys.gpact.functioncall.common.CallPath.calculateRootCallMapKey;
import static net.consensys.gpact.functioncall.common.CallPath.callPathToMapKey;

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
import net.consensys.gpact.functioncall.gpact.v1.GpactV1CrossControlManager;
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
public class GpactCrosschainExecutor {
  static final Logger LOG = LogManager.getLogger(GpactCrosschainExecutor.class);

  private static final BigInteger ROOT_CALL_MAP_KEY = calculateRootCallMapKey();

  private SignedEvent signedStartEvent;
  private SignedEvent signedRootEvent;
  // Key for this map is the call path of the caller.
  private final Map<BigInteger, SignedEvent[]> signedSegmentEvents = new ConcurrentHashMap<>();

  // Key for this map is the call path of the caller.
  private final Map<BigInteger, TransactionReceipt> transactionReceipts = new HashMap<>();

  // Key for this map is the blockchain id that the segment occurred on.
  private final Map<BlockchainId, List<SignedEvent>> signedSegmentEventsWithLockedContracts =
      new ConcurrentHashMap<>();

  GpactCrossControlManagerGroup crossControlManagerGroup;

  protected byte[] callGraph;

  protected BigInteger timeout;
  protected BigInteger crossBlockchainTransactionId;
  protected BlockchainId rootBcId;

  boolean success = false;

  public GpactCrosschainExecutor(GpactCrossControlManagerGroup crossControlManagerGroup) {
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
    GpactV1CrossControlManager rootCbcContract =
        (GpactV1CrossControlManager) this.crossControlManagerGroup.getCbcManager(this.rootBcId);
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
            GpactV1CrossControlManager.START_EVENT_SIGNATURE);
    if (this.signedStartEvent == null) {
      throw new RuntimeException(
          "Messaging layer not configured to return proofs to function call layer");
    }
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
    if (callPathIndex == 0) {
      callPathIndex = (callPath.get(callPath.size() - 2)).intValue();
    }

    GpactV1CrossControlManager segmentCbcContract =
        (GpactV1CrossControlManager) this.crossControlManagerGroup.getCbcManager(blockchainId);
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
            GpactV1CrossControlManager.SEGMENT_EVENT_SIGNATURE);
    this.transactionReceipts.put(mapKey, txr);
    if (signedSegEvent == null) {
      throw new RuntimeException(
          "Messaging layer not configured to return proofs to function call layer");
    }

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
    GpactV1CrossControlManager rootCbcContract =
        (GpactV1CrossControlManager) this.crossControlManagerGroup.getCbcManager(this.rootBcId);
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
            GpactV1CrossControlManager.ROOT_EVENT_SIGNATURE);
    if (this.signedRootEvent == null) {
      throw new RuntimeException(
          "Messaging layer not configured to return proofs to function call layer");
    }
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
      GpactV1CrossControlManager cbcContract =
          (GpactV1CrossControlManager) this.crossControlManagerGroup.getCbcManager(blockchainId);
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
      GpactV1CrossControlManager cbcContract =
          (GpactV1CrossControlManager) this.crossControlManagerGroup.getCbcManager(blockchainId);
      cbcContract.signallingAsyncPart2(receipt);
    }
  }

  public boolean getRootEventSuccess() {
    return this.success;
  }

  /**
   * Return all of the transaction receipts.
   *
   * @return the transaction receipts
   */
  public Map<BigInteger, TransactionReceipt> getTransationReceipts() {
    return this.transactionReceipts;
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
}
