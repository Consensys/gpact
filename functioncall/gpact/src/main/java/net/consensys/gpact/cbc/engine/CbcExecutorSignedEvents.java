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
package net.consensys.gpact.cbc.engine;

import net.consensys.gpact.cbc.CrossBlockchainControlSignedEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import net.consensys.gpact.cbc.AbstractCbc;
import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.common.CrossBlockchainConsensusType;
import net.consensys.gpact.common.Tuple;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CbcExecutorSignedEvents extends AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(CbcExecutorSignedEvents.class);


  public CbcExecutorSignedEvents(CbcManager cbcManager) throws Exception {
    super(CrossBlockchainConsensusType.EVENT_SIGNING, cbcManager);
  }


  protected void startCall() throws Exception {
    CrossBlockchainControlSignedEvents rootCbcContract = this.cbcManager.getCbcContractSignedEvents(this.rootBcId);

    Tuple<TransactionReceipt, byte[], Boolean> result = rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, this.callGraph);
    byte[] startEventData = result.getSecond();
    this.signedStartEvent = new SignedEvent(this.cbcManager.getSigners(this.rootBcId),
          rootBcId, this.cbcManager.getCbcAddress(this.rootBcId), AbstractCbc.START_EVENT_SIGNATURE, startEventData);
  }

  protected void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception {
    if (callPath.size() == 0) {
      throw new Exception("Invalid call path length for segment: " + 0);
    }

    BigInteger mapKey = callPathToMapKey(callPath);

    CrossBlockchainControlSignedEvents segmentCbcContract = this.cbcManager.getCbcContractSignedEvents(blockchainId);

    List<SignedEvent> signedEvents = this.signedSegmentEvents.computeIfAbsent(mapKey, k -> new ArrayList<>());
    Tuple<TransactionReceipt, byte[], Boolean> result = segmentCbcContract.segment(this.signedStartEvent, signedEvents, callPath);
    TransactionReceipt transactionReceipt = result.getFirst();
    byte[] segEventData = result.getSecond();
    boolean noLockedContracts = result.getThird();
    SignedEvent signedSegEvent = new SignedEvent(this.cbcManager.getSigners(blockchainId),
            blockchainId, this.cbcManager.getCbcAddress(blockchainId), AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
    this.transactionReceipts.put(mapKey, transactionReceipt);

    // Add the proof for the call that has just occurred to the map so it can be accessed when the next
    BigInteger parentMapKey = determineMapKeyOfCaller(callPath);
    signedEvents = this.signedSegmentEvents.computeIfAbsent(parentMapKey, k -> new ArrayList<>());
    signedEvents.add(signedSegEvent);

    // Add the proof to the list of segments that have contracts that need to be unlocked.
    if (!noLockedContracts) {
      signedEvents =
          this.signedSegmentEventsWithLockedContracts.computeIfAbsent(blockchainId, k -> new ArrayList<>());
      signedEvents.add(signedSegEvent);
    }
  }


  protected void root() throws Exception {
    CrossBlockchainControlSignedEvents rootCbcContract = this.cbcManager.getCbcContractSignedEvents(this.rootBcId);
    List<SignedEvent> signedSegEvents = this.signedSegmentEvents.get(ROOT_CALL_MAP_KEY);
    Tuple<TransactionReceipt, byte[], Boolean> result = rootCbcContract.root(this.signedStartEvent, signedSegEvents);
    TransactionReceipt rootTxReceipt = result.getFirst();
    byte[] rootEventData = result.getSecond();
    this.signedRootEvent = new SignedEvent(this.cbcManager.getSigners(this.rootBcId),
          this.rootBcId, this.cbcManager.getCbcAddress(this.rootBcId), AbstractCbc.ROOT_EVENT_SIGNATURE, rootEventData);
    this.transactionReceipts.put(ROOT_CALL_MAP_KEY, rootTxReceipt);
    this.success = rootCbcContract.getRootEventSuccess();
  }

  /**
   * This method executes all of the signalling transactions in parallel. They are for separate
   * blockchains, so this is definitely safe.
   *
   * @throws Exception
   */
  protected void doSignallingCalls() throws Exception {
    int numSignalsToSend = this.signedSegmentEventsWithLockedContracts.size();
    if (numSignalsToSend == 0) {
      return;
    }

    CompletableFuture<?>[] transactionReceiptCompletableFutures = new CompletableFuture<?>[numSignalsToSend];
    int i = 0;
    for (BigInteger blockchainId: this.signedSegmentEventsWithLockedContracts.keySet()) {
      List<SignedEvent> signedSegEventsLockedContractsCurrentBlockchain =
          this.signedSegmentEventsWithLockedContracts.get(blockchainId);
      CrossBlockchainControlSignedEvents cbcContract = this.cbcManager.getCbcContractSignedEvents(blockchainId);
      transactionReceiptCompletableFutures[i++] = cbcContract.signallingAsyncPart1(this.signedRootEvent, signedSegEventsLockedContractsCurrentBlockchain);
    }
    CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(transactionReceiptCompletableFutures);
    combinedFuture.get();

    i = 0;
    for (BigInteger blockchainId: this.signedSegmentEventsWithLockedContracts.keySet()) {
      TransactionReceipt receipt = (TransactionReceipt) transactionReceiptCompletableFutures[i++].get();
      CrossBlockchainControlSignedEvents cbcContract = this.cbcManager.getCbcContractSignedEvents(blockchainId);
      cbcContract.signallingAsyncPart2(receipt);
    }
  }
}
