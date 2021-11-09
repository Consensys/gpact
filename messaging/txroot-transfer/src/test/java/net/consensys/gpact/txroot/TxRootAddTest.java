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
package net.consensys.gpact.txroot;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.attestorsign.soliditywrappers.AttestorSignRegistrar;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.FastTxManager;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.TxManagerCache;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.trie.MerklePatriciaTrie;
import net.consensys.gpact.trie.Proof;
import net.consensys.gpact.trie.SimpleMerklePatriciaTrie;
import net.consensys.gpact.txroot.soliditywrappers.TestEvents;
import net.consensys.gpact.txroot.soliditywrappers.TxReceiptsRootStorage;
import net.consensys.gpact.utils.crypto.KeyPairGen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

public class TxRootAddTest extends AbstractWeb3Test {
  static final Logger LOG = LogManager.getLogger(TxRootAddTest.class);

  final byte[] txReceiptRoot = new byte[32];

  TxReceiptsRootStorage txReceiptRootStorageContract;

  protected AttestorSignRegistrar registrarContract;

  protected void deployRegistrarContract() throws Exception {
    this.registrarContract =
        AttestorSignRegistrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }

  protected AttestorSignRegistrar deployRegistrarContract(TransactionManager tm1) throws Exception {
    return AttestorSignRegistrar.deploy(this.web3j, tm1, this.freeGasProvider).send();
  }

  protected AttestorSignRegistrar loadContract(TransactionManager tm1) throws Exception {
    return AttestorSignRegistrar.load(
        this.registrarContract.getContractAddress(), this.web3j, tm1, this.freeGasProvider);
  }

  protected void addBlockchain(BigInteger blockchainId, String initialSigner) throws Exception {
    List<String> signers = new ArrayList<>();
    signers.add(initialSigner);
    addBlockchain(blockchainId, signers);
  }

  protected void addBlockchain(BigInteger blockchainId, List<String> signers) throws Exception {
    TransactionReceipt receipt =
        this.registrarContract.addSignersSetThreshold(blockchainId, signers, BigInteger.ONE).send();
    assert (receipt.isStatusOK());
  }

  protected void deployTxReceiptRootStorageContract() throws Exception {
    this.txReceiptRootStorageContract =
        TxReceiptsRootStorage.deploy(
                this.web3j,
                this.tm,
                this.freeGasProvider,
                this.registrarContract.getContractAddress())
            .send();
  }

  @Test
  public void addTxReceipt() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    AnIdentity newSigner = new AnIdentity();
    addBlockchain(blockchainId, newSigner.getAddress());

    deployTxReceiptRootStorageContract();

    // Sign the txReceiptRoot
    Sign.SignatureData signatureData = newSigner.sign(this.txReceiptRoot);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // Check that the receipt root is has been registered.
    boolean containsReceiptRoot =
        this.txReceiptRootStorageContract
            .containsTxReceiptRoot(blockchainId, this.txReceiptRoot)
            .send();
    assertFalse(containsReceiptRoot);

    // This will revert if the signature does not verify
    try {
      TransactionReceipt receipt =
          this.txReceiptRootStorageContract
              .addTxReceiptRoot(blockchainId, signers, sigR, sigS, sigV, this.txReceiptRoot)
              .send();
      assert (receipt.isStatusOK());
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    // Check that the receipt root is has been registered.
    containsReceiptRoot =
        this.txReceiptRootStorageContract
            .containsTxReceiptRoot(blockchainId, this.txReceiptRoot)
            .send();
    assert (containsReceiptRoot);
  }

  @Test
  public void proveTxReceiptOneTxPerBlock() throws Exception {
    // This will result in just a leaf node in the trie.
    proveTxReceipt(1, true, true);
  }

  @Test
  public void proveTxReceiptTwoTxPerBlock() throws Exception {
    // This will result in just a branch node and two leaf nodes in the trie.
    proveTxReceipt(2, true, true);
  }

  @Test
  public void proveTxReceipt17TxPerBlock() throws Exception {
    // This will result in just a branch node, another branch node and seventeen leaf nodes in the
    // trie.
    proveTxReceipt(17, true, true);
  }

  @Test
  public void failTxReceiptProofBadBlockchain() throws Exception {
    // This will result in just a leaf node in the trie.
    try {
      proveTxReceipt(1, false, true);
    } catch (ContractCallException ex) {
      // Ignore
    }
  }

  @Test
  public void failTxReceiptProofBadCbcContract() throws Exception {
    try {
      proveTxReceipt(1, true, false);
    } catch (ContractCallException ex) {
      // Ignore
    }
  }

  private void proveTxReceipt(
      int numTransactionsPerBlock, boolean correctBlockchain, boolean correctCbcContract)
      throws Exception {
    setupWeb3();
    deployRegistrarContract();
    // In this test, the transactions being proven are actually on the same blockchain. However,
    // for the sake of the test, assume they are on a different blockchain, and assume that
    // blockchain
    // has blockchain id 10.
    BigInteger sourceBlockchainId = BigInteger.TEN;
    AnIdentity newSigner = new AnIdentity();
    addBlockchain(sourceBlockchainId, newSigner.getAddress());

    deployTxReceiptRootStorageContract();

    // Deploy the contract that will emit the events.
    TestEvents testContract = TestEvents.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    String testContractAddress = testContract.getContractAddress();

    // Have a separate EOA (and hence credentials) for each signer. In this way, the nonce
    // management can be done by web3j automatically.
    Credentials[] creds = new Credentials[numTransactionsPerBlock];
    FastTxManager[] tms = new FastTxManager[numTransactionsPerBlock];
    TestEvents[] testContracts = new TestEvents[numTransactionsPerBlock];
    final TransactionReceipt[] receipts = new TransactionReceipt[numTransactionsPerBlock];
    while (true) {
      for (int i = 0; i < numTransactionsPerBlock; i++) {
        String privateKey0 = new KeyPairGen().generateKeyPairGetPrivateKey();
        creds[i] = Credentials.create(privateKey0);
  
        TransactionReceiptProcessor txrProcessor =
            new PollingTransactionReceiptProcessor(this.web3j, POLLING_INTERVAL, RETRY);
        tms[i] =
            TxManagerCache.getOrCreate(this.web3j, creds[i], BLOCKCHAIN_ID.longValue(), txrProcessor);
        testContracts[i] =
            TestEvents.load(testContractAddress, this.web3j, tms[i], this.freeGasProvider);
      }
  
      BigInteger id = BigInteger.TWO;
  
      //    CompletableFuture<TransactionReceipt>[] transactionReceiptCompletableFutures =
      //        (CompletableFuture<TransactionReceipt>[]) new Object[numTransactionsPerBlock];
      CompletableFuture<?>[] transactionReceiptCompletableFutures =
          new CompletableFuture<?>[numTransactionsPerBlock];
      for (int i = 0; i < numTransactionsPerBlock; i++) {
        transactionReceiptCompletableFutures[i] = testContracts[i].start(id).sendAsync();
        id = id.add(BigInteger.ONE);
      }
      CompletableFuture<Void> combinedFuture =
          CompletableFuture.allOf(transactionReceiptCompletableFutures);
      combinedFuture.get();
  
      Set<String> headers = new HashSet<String>();
      for (int i = 0; i < numTransactionsPerBlock; i++) {
        receipts[i] = (TransactionReceipt) transactionReceiptCompletableFutures[i].get();
        // Show the receipts root that has been included in the block.
        assert (receipts[i] != null && receipts[i].getBlockHash() != null);
        headers.add(receipts[i].getBlockHash());
      }
      if (headers.size() == 1) {
        break;
      }
    }
    EthBlock block = web3j.ethGetBlockByHash(receipts[0].getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    Bytes32 receiptsRootBytes32 = Bytes32.fromHexString(receiptsRoot);
    byte[] receiptsRootByte = receiptsRootBytes32.toArray();

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    Sign.SignatureData signatureData = newSigner.sign(receiptsRootByte);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // This will revert if the signature does not verify
    TransactionReceipt receipt5 =
        this.txReceiptRootStorageContract
            .addTxReceiptRoot(sourceBlockchainId, signers, sigR, sigS, sigV, receiptsRootByte)
            .send();
    assert (receipt5.isStatusOK());

    // Calculate receipt root based on logs for all receipts of all transactions in the block.
    String blockHash = receipts[0].getBlockHash();
    EthGetBlockTransactionCountByHash transactionCountByHash =
        web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    List<org.hyperledger.besu.ethereum.core.TransactionReceipt> besuReceipts = new ArrayList<>();

    BigInteger transactionIndex = BigInteger.ZERO;
    do {
      EthTransaction ethTransaction =
          this.web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
      Optional<Transaction> transaction = ethTransaction.getTransaction();
      assert (transaction.isPresent());
      String txHash = transaction.get().getHash();
      EthGetTransactionReceipt ethGetTransactionReceipt =
          this.web3j.ethGetTransactionReceipt(txHash).send();
      Optional<TransactionReceipt> mayBeReceipt = ethGetTransactionReceipt.getTransactionReceipt();
      assert (mayBeReceipt.isPresent());
      TransactionReceipt receipt = mayBeReceipt.get();

      // Convert to Besu objects
      List<org.hyperledger.besu.ethereum.core.Log> besuLogs = new ArrayList<>();

      String stateRootFromReceipt = receipt.getRoot();
      Hash root = (stateRootFromReceipt == null) ? null : Hash.fromHexString(receipt.getRoot());
      String statusFromReceipt = receipt.getStatus();
      int status =
          statusFromReceipt == null ? -1 : Integer.parseInt(statusFromReceipt.substring(2), 16);
      for (Log web3jLog : receipt.getLogs()) {
        org.hyperledger.besu.ethereum.core.Address addr =
            org.hyperledger.besu.ethereum.core.Address.fromHexString(web3jLog.getAddress());
        Bytes data = Bytes.fromHexString(web3jLog.getData());
        List<String> topics = web3jLog.getTopics();
        List<LogTopic> logTopics = new ArrayList<>();
        for (String topic : topics) {
          LogTopic logTopic = LogTopic.create(Bytes.fromHexString(topic));
          logTopics.add(logTopic);
        }
        besuLogs.add(new org.hyperledger.besu.ethereum.core.Log(addr, data, logTopics));
      }
      String revertReasonFromReceipt = receipt.getRevertReason();
      Bytes revertReason =
          revertReasonFromReceipt == null ? null : Bytes.fromHexString(receipt.getRevertReason());
      org.hyperledger.besu.ethereum.core.TransactionReceipt txReceipt =
          root == null
              ? new org.hyperledger.besu.ethereum.core.TransactionReceipt(
                  status,
                  receipt.getCumulativeGasUsed().longValue(),
                  besuLogs,
                  java.util.Optional.ofNullable(revertReason))
              : new org.hyperledger.besu.ethereum.core.TransactionReceipt(
                  root,
                  receipt.getCumulativeGasUsed().longValue(),
                  besuLogs,
                  java.util.Optional.ofNullable(revertReason));
      besuReceipts.add(txReceipt);

      // Increment for the next time through the loop.
      transactionIndex = transactionIndex.add(BigInteger.ONE);
    } while (transactionIndex.compareTo(txCount) != 0);

    final MerklePatriciaTrie<Bytes, Bytes> trie = trie();
    for (int i = 0; i < besuReceipts.size(); ++i) {
      Bytes rlpEncoding = RLP.encode(besuReceipts.get(i)::writeTo);
      trie.put(indexKey(i), rlpEncoding);
    }
    Bytes32 besuCalculatedReceiptsRoot = trie.getRootHash();
    String besuCalculatedReceiptsRootStr = besuCalculatedReceiptsRoot.toHexString();
    assertEquals(besuCalculatedReceiptsRootStr, receiptsRoot);

    // Check that each transaction receipt can be proven.
    for (int i = 0; i < numTransactionsPerBlock; i++) {
      Bytes aKey = indexKey(i);

      Proof<Bytes> simpleProof = trie.getValueWithSimpleProof(aKey);
      Bytes transactionReceipt = simpleProof.getValue().get();
      Bytes rlpOfNode = transactionReceipt;
      // Node references can be hashes or the node itself, if the node is less than 32 bytes.
      // Leaf nodes in Ethereum, leaves of Merkle Patricia Tries could be less than 32 bytes,
      // but no other nodes. For transaction receipts, it isn't possible even the leaf nodes
      // to be 32 bytes.
      Bytes32 nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(transactionReceipt);

      List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
      List<BigInteger> proofOffsets = new ArrayList<>();
      List<byte[]> proofs = new ArrayList<>();
      for (int j = proofList1.size() - 1; j >= 0; j--) {
        rlpOfNode = proofList1.get(j);
        proofOffsets.add(BigInteger.valueOf(findOffset(rlpOfNode, nodeHash)));
        proofs.add(rlpOfNode.toArray());
        nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);
      }
      assertEquals(
          besuCalculatedReceiptsRoot.toHexString(),
          org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode).toHexString());

      BigInteger bcId = sourceBlockchainId;
      if (!correctBlockchain) {
        bcId = sourceBlockchainId.add(BigInteger.ONE);
      }

      String cbcContractAddress = "0";
      if (!correctCbcContract) {
        cbcContractAddress = "1";
      }

      this.txReceiptRootStorageContract
          .verify(
              bcId,
              cbcContractAddress,
              besuCalculatedReceiptsRoot.toArray(),
              transactionReceipt.toArray(),
              proofOffsets,
              proofs)
          .send();
    }

    //    System.exit(0);
  }

  private static int findOffset(Bytes rlpOfNode, Bytes nodeRef) {
    int sizeNodeRef = nodeRef.size();
    for (int i = 0; i < rlpOfNode.size() - sizeNodeRef; i++) {
      boolean found = true;
      for (int j = 0; j < sizeNodeRef; j++) {
        if (rlpOfNode.get(i + j) != nodeRef.get(j)) {
          found = false;
          break;
        }
      }
      if (found) {
        return i;
      }
    }
    return -1;
  }

  private static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  private static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }
}
