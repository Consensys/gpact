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

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.common.*;
import net.consensys.gpact.messaging.common.SignatureBlob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/** TODO this simulates a relayer */
public class TxRootRelayer extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(TxRootRelayer.class);

  private TxReceiptsRootStorage txReceiptsRootStorage;

  List<AnIdentity> signers = new ArrayList<>();

  public TxRootRelayer(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void loadContract(String address) {
    this.txReceiptsRootStorage =
        TxReceiptsRootStorage.load(address, this.web3j, this.tm, this.gasProvider);
  }

  // TODO this method won't be needed / should be removed once the Attestor service has been
  // created.
  // Perhaps at that point, addSigner should be specifying the URL of an attestor.
  public void addSigner(AnIdentity signer) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    signers.add(signer);
  }

  public SignatureBlob sign(byte[] transactionReceiptRoot) {
    int numSigners = this.signers.size();
    // Sign the txReceiptRoot
    String[] theSigners = new String[numSigners];
    byte[][] sigR = new byte[numSigners][];
    byte[][] sigS = new byte[numSigners][];
    byte[] sigV = new byte[numSigners];
    for (int i = 0; i < numSigners; i++) {
      AnIdentity signer = this.signers.get(i);
      Sign.SignatureData signatureData = signer.sign(transactionReceiptRoot);
      theSigners[i] = signer.getAddress();
      sigR[i] = signatureData.getR();
      sigS[i] = signatureData.getS();
      sigV[i] = signatureData.getV()[0];
    }
    return new SignatureBlob(theSigners, sigR, sigS, sigV);
  }

  public CompletableFuture<TransactionReceipt> addTransactionReceiptRootToBlockchainAsyncPart1(
      SignatureBlob signatures, BlockchainId sourceBlockchainId, byte[] transactionReceiptRoot)
      throws Exception {
    // Add the transaction receipt root for the blockchain
    LOG.debug(
        "txReceiptsRootStorageContract.addTxReceiptRoot: publishing to BC ID {}, from BC ID: {}",
        this.blockchainId,
        sourceBlockchainId);
    return this.txReceiptsRootStorage
        .addTxReceiptRoot(
            sourceBlockchainId.asBigInt(), signatures.encode(), transactionReceiptRoot)
        .sendAsync();
  }

  public void addTransactionReceiptRootToBlockchainAsyncPart2(TransactionReceipt txR)
      throws Exception {
    if (!txR.isStatusOK()) {
      String revertReason = txR.getRevertReason();
      LOG.error(
          "Transaction to add transaction receipt root failed: Revert Reason: {}",
          RevertReason.decodeRevertReason(revertReason));
      throw new Exception(
          "Transaction to add transaction receipt root failed: Revert Reason: "
              + RevertReason.decodeRevertReason(revertReason));
    }
    StatsHolder.logGas("AddTxReceiptRoot Transaction", txR.getGasUsed());
  }
}
