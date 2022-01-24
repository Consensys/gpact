/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.messaging.txrootrelay;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.BlockchainInfo;
import net.consensys.gpact.common.RevertReason;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

/** TODO this simulates all of the relayers for all blockchains */
public class TxRootRelayerGroup {
  static final Logger LOG = LogManager.getLogger(TxRootRelayerGroup.class);

  Map<BlockchainId, TxRootRelayer> blockchains = new HashMap<>();

  public void loadContractForBlockchain(
      Credentials creds, BlockchainInfo bcInfo, String txRootContract) throws Exception {
    BlockchainId blockchainId = bcInfo.bcId;
    if (this.blockchains.containsKey(blockchainId)) {
      return;
      // throw new Exception("Blockchain already added: " + blockchainId);
    }

    TxRootRelayer manager =
        new TxRootRelayer(creds, blockchainId, bcInfo.uri, bcInfo.gasPriceStrategy, bcInfo.period);
    manager.loadContract(txRootContract);

    this.blockchains.put(blockchainId, manager);
  }

  // Add tx receipt root so event will be trusted.
  public void publishReceiptRoot(
      BlockchainId publishingFrom,
      byte[] transactionReceiptRoot,
      List<BlockchainId> blockchainsToPublishTo)
      throws Exception {
    int numToShareWith = blockchainsToPublishTo.size();
    if (numToShareWith == 0) {
      throw new RuntimeException("Unexpectedly, zero blockchains to publish to");
    }

    CompletableFuture<?>[] transactionReceiptCompletableFutures =
        new CompletableFuture<?>[numToShareWith];
    int i = 0;
    for (BlockchainId bcId : blockchainsToPublishTo) {
      // TODO handle blockchain id not registered
      TxRootRelayer relayer = this.blockchains.get(bcId);
      Signatures signed = this.blockchains.get(publishingFrom).sign(transactionReceiptRoot);
      transactionReceiptCompletableFutures[i++] =
          relayer.addTransactionReceiptRootToBlockchainAsyncPart1(
              signed, publishingFrom, transactionReceiptRoot);
    }
    CompletableFuture<Void> combinedFuture =
        CompletableFuture.allOf(transactionReceiptCompletableFutures);
    try {
      combinedFuture.get();
    } catch (ExecutionException ex) {
      Throwable th = ex.getCause();
      if (th instanceof TransactionException) {
        TransactionException txEx = (TransactionException) th;
        LOG.error(
            " Revert Reason: {}",
            RevertReason.decodeRevertReason(txEx.getTransactionReceipt().get().getRevertReason()));
      }
      throw ex;
    }

    i = 0;
    for (BlockchainId bcId : blockchainsToPublishTo) {
      TransactionReceipt receipt =
          (TransactionReceipt) transactionReceiptCompletableFutures[i++].get();
      TxRootRelayer relayer = this.blockchains.get(bcId);
      relayer.addTransactionReceiptRootToBlockchainAsyncPart2(receipt);
    }
  }

  // TODO when an attestor signer service is implemented, this will change to
  // setting up URLs where attestors can be contacted
  public void addSignerOnAllBlockchains(AnIdentity signer) throws Exception {
    for (BlockchainId bcId1 : this.blockchains.keySet()) {
      addSigner(signer, bcId1);
    }
  }

  // TODO when an attestor signer service is implemented, this will change to
  // setting up URLs where attestors can be contacted
  public void addSigner(AnIdentity signer, BlockchainId bcId1) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    TxRootRelayer holder = this.blockchains.get(bcId1);
    holder.addSigner(signer);
  }
}
