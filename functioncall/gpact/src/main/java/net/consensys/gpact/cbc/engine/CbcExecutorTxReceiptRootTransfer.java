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

import net.consensys.gpact.cbc.*;
import net.consensys.gpact.common.AnIdentity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import net.consensys.gpact.common.CrossBlockchainConsensusType;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.Tuple;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CbcExecutorTxReceiptRootTransfer extends AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(CbcExecutorTxReceiptRootTransfer.class);

  public CbcExecutorTxReceiptRootTransfer(CbcManager cbcManager) throws Exception {
    super(CrossBlockchainConsensusType.TRANSACTION_RECEIPT_SIGNING, cbcManager);
  }


  protected void startCall() throws Exception {
    CrossBlockchainControlTxReceiptRootTransfer rootCbcContract = this.cbcManager.getCbcContractTxRootTransfer(this.rootBcId);

    Tuple<TransactionReceipt, byte[], Boolean> result = rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, this.callGraph);
    TransactionReceipt startTxReceipt = result.getFirst();
    byte[] eventData = result.getSecond();
    TxReceiptRootTransferEventProof proof = rootCbcContract.getEventProof(startTxReceipt, eventData, AbstractCbc.START_EVENT_SIGNATURE);
    this.signedStartEvent = proof.toSignedEvent();
    publishReceiptRootToAll(this.rootBcId, proof.getTransactionReceiptRoot());
  }

  protected void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception {
    if (callPath.size() == 0) {
      throw new Exception("Invalid call path length for segment: " + callPath.size());
    }

    BigInteger mapKey = callPathToMapKey(callPath);

    CrossBlockchainControlTxReceiptRootTransfer segmentCbcContract = this.cbcManager.getCbcContractTxRootTransfer(blockchainId);

    List<SignedEvent> proofs = this.signedSegmentEvents.computeIfAbsent(mapKey, k -> new ArrayList<>());
    Tuple<TransactionReceipt, byte[], Boolean> result = segmentCbcContract.segment(
            this.signedStartEvent, proofs, callPath);
    TransactionReceipt segTxReceipt = result.getFirst();
    byte[] eventData = result.getSecond();
    boolean noContractsLocked = result.getThird();
    TxReceiptRootTransferEventProof segProof = segmentCbcContract.getEventProof(segTxReceipt, eventData, AbstractCbc.SEGMENT_EVENT_SIGNATURE);
    this.transactionReceipts.put(mapKey, segTxReceipt);

    // Add the proof for the call that has just occurred to the map so it can be accessed when the next
    BigInteger parentMapKey = determineMapKeyOfCaller(callPath);
    proofs = this.signedSegmentEvents.computeIfAbsent(parentMapKey, k -> new ArrayList<>());
    proofs.add(segProof.toSignedEvent());

    // Add the proof if there were locked contracts as a result of the segment.
    if (!noContractsLocked) {
      List<SignedEvent> proofsOfSegmnentsWithLockedContracts =
          this.signedSegmentEventsWithLockedContracts.computeIfAbsent(blockchainId, k -> new ArrayList<>());
      proofsOfSegmnentsWithLockedContracts.add(segProof.toSignedEvent());
    }

    // Segments proofs need to be available on the blockchain they executed on (for the
    // Signalling call), and on the blockchain that the contract that called this contract
    // resides on (for the Root or Segment call).
    Set<BigInteger> blockchainsToPublishTo = new HashSet<>();
    blockchainsToPublishTo.add(blockchainId);
    blockchainsToPublishTo.add(callerBlockchainId);
    publishReceiptRoot(blockchainId, segProof.getTransactionReceiptRoot(), blockchainsToPublishTo);
  }


  protected void root() throws Exception {
    CrossBlockchainControlTxReceiptRootTransfer rootCbcContract = this.cbcManager.getCbcContractTxRootTransfer(this.rootBcId);
    List<SignedEvent> proofs = this.signedSegmentEvents.get(ROOT_CALL_MAP_KEY);
    Tuple<TransactionReceipt, byte[], Boolean> result = rootCbcContract.root(this.signedStartEvent, proofs);
    TransactionReceipt rootTxReceipt = result.getFirst();
    byte[] eventData = result.getSecond();
    TxReceiptRootTransferEventProof rootProof = rootCbcContract.getEventProof(rootTxReceipt, eventData, AbstractCbc.ROOT_EVENT_SIGNATURE);
    this.signedRootEvent = rootProof.toSignedEvent();
    this.transactionReceipts.put(ROOT_CALL_MAP_KEY, rootTxReceipt);

    this.success = rootCbcContract.getRootEventSuccess();
    publishReceiptRootToAll(this.rootBcId, rootProof.getTransactionReceiptRoot());
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
      List<SignedEvent> segProofsLockedContractsCurrentBlockchain =
          this.signedSegmentEventsWithLockedContracts.get(blockchainId);
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(blockchainId);
      transactionReceiptCompletableFutures[i++] = cbcContract.signallingAsyncPart1(this.signedRootEvent, segProofsLockedContractsCurrentBlockchain);
    }
    CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(transactionReceiptCompletableFutures);
    combinedFuture.get();

    i = 0;
    for (BigInteger blockchainId: this.signedSegmentEventsWithLockedContracts.keySet()) {
      TransactionReceipt receipt = (TransactionReceipt) transactionReceiptCompletableFutures[i++].get();
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(blockchainId);
      cbcContract.signallingAsyncPart2(receipt);
    }
  }


  private void publishReceiptRootToAll(BigInteger publishingFrom,  byte[] transactionReceiptRoot) throws Exception {
    Set<BigInteger> blockchainIdsToPublishTo = this.cbcManager.getAllBlockchainIds();
    publishReceiptRoot(publishingFrom, transactionReceiptRoot, blockchainIdsToPublishTo);
  }

  // Add tx receipt root so event will be trusted.
  private void publishReceiptRoot(BigInteger publishingFrom,  byte[] transactionReceiptRoot, Set<BigInteger> blockchainsToPublishTo) throws Exception {
    int numToShareWith =  blockchainsToPublishTo.size();
    if (numToShareWith == 0) {
      throw new RuntimeException("Unexpectedly, zero blockchains to publish to");
    }

    CompletableFuture<?>[] transactionReceiptCompletableFutures = new CompletableFuture<?>[numToShareWith];
    int i = 0;
    for (BigInteger bcId: blockchainsToPublishTo) {
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(bcId);
      AnIdentity[] signers = this.cbcManager.getSigners(publishingFrom);
      transactionReceiptCompletableFutures[i++] = cbcContract.addTransactionReceiptRootToBlockchainAsyncPart1(signers, publishingFrom, transactionReceiptRoot);
    }
    CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(transactionReceiptCompletableFutures);
    try {
      combinedFuture.get();
    }
    catch (ExecutionException ex) {
      Throwable th = ex.getCause();
      if (th instanceof TransactionException) {
        TransactionException txEx = (TransactionException) th;
        LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(txEx.getTransactionReceipt().get().getRevertReason()));
      }
      throw ex;
    }


    i = 0;
    for (BigInteger bcId: blockchainsToPublishTo) {
      TransactionReceipt receipt = (TransactionReceipt) transactionReceiptCompletableFutures[i++].get();
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(bcId);
      cbcContract.addTransactionReceiptRootToBlockchainAsyncPart2(receipt);
    }
  }



  private void publishReceiptRootSync(BigInteger publishingFrom,  byte[] transactionReceiptRoot, Set<BigInteger> blockchainsToPublishTo) throws Exception {
    for (BigInteger bcId: blockchainsToPublishTo) {
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(bcId);
      AnIdentity[] signers = this.cbcManager.getSigners(bcId);
      cbcContract.addTransactionReceiptRootToBlockchain(signers, publishingFrom, transactionReceiptRoot);
    }
  }

}
