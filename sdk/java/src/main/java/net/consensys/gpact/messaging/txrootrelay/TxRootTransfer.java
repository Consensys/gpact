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
import java.math.BigInteger;
import java.util.*;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.messaging.txrootrelay.besuethereum.core.Hash;
import net.consensys.gpact.messaging.txrootrelay.besuethereum.core.LogTopic;
import net.consensys.gpact.messaging.txrootrelay.besuethereum.rlp.RLP;
import net.consensys.gpact.messaging.txrootrelay.trie.MerklePatriciaTrie;
import net.consensys.gpact.messaging.txrootrelay.trie.Proof;
import net.consensys.gpact.messaging.txrootrelay.trie.SimpleMerklePatriciaTrie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.*;

/**
 * Manages the interaction between the application library and transaction receipt root transfer
 * code for a certain blockchain.
 *
 * <p>TODO The class should check that a threshold signed transaction receipt root have been
 * transferred to the target blockchain, and then create the Merkle Proof. TODO the Relayer service
 * has not been implemented yet. As such, this class simulates the Relayer service tranferring the
 * transaction receipt root to blockchains.
 */
public class TxRootTransfer extends AbstractBlockchain implements MessagingVerificationInterface {
  static final Logger LOG = LogManager.getLogger(TxRootTransfer.class);

  TxRootRelayerGroup relayerGroup;

  public TxRootTransfer(
      TxRootRelayerGroup relayerGroup,
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
    this.relayerGroup = relayerGroup;
  }

  @Override
  public SignedEvent getSignedEvent(
      List<BlockchainId> targetBlockchainIds,
      TransactionReceipt startTxReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature)
      throws Exception {

    TxReceiptRootTransferEventProof proof =
        getEventProof(startTxReceipt, eventData, contractAddress, eventFunctionSignature);
    relayerGroup.publishReceiptRoot(
        this.blockchainId, proof.getTransactionReceiptRoot(), targetBlockchainIds);
    return proof.toSignedEvent();
  }

  public TxReceiptRootTransferEventProof getEventProof(
      TransactionReceipt startTxReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature)
      throws Exception {
    return getProofForTxReceipt(
        this.blockchainId, contractAddress, startTxReceipt, eventData, eventFunctionSignature);
  }

  public TxReceiptRootTransferEventProof getProofForTxReceipt(
      BlockchainId blockchainId,
      String cbcContractAddress,
      TransactionReceipt aReceipt,
      byte[] eventData,
      byte[] eventFunctionSignature)
      throws Exception {
    // Calculate receipt root based on logs for all receipts of all transactions in the block.
    String blockHash = aReceipt.getBlockHash();
    EthGetBlockTransactionCountByHash transactionCountByHash =
        this.web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    List<net.consensys.gpact.messaging.txrootrelay.besuethereum.core.TransactionReceipt>
        besuReceipts = new ArrayList<>();
    //    List<org.hyperledger.besu.ethereum.core.TransactionReceipt> besuReceipts = new
    // ArrayList<>();

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
      List<net.consensys.gpact.messaging.txrootrelay.besuethereum.core.Log> besuLogs =
          new ArrayList<>();

      String stateRootFromReceipt = receipt.getRoot();
      Hash root = (stateRootFromReceipt == null) ? null : Hash.fromHexString(receipt.getRoot());
      String statusFromReceipt = receipt.getStatus();
      int status =
          statusFromReceipt == null ? -1 : Integer.parseInt(statusFromReceipt.substring(2), 16);
      for (Log web3jLog : receipt.getLogs()) {
        net.consensys.gpact.messaging.txrootrelay.besuethereum.core.Address addr =
            net.consensys.gpact.messaging.txrootrelay.besuethereum.core.Address.fromHexString(
                web3jLog.getAddress());
        Bytes data = Bytes.fromHexString(web3jLog.getData());
        List<String> topics = web3jLog.getTopics();
        List<LogTopic> logTopics = new ArrayList<>();
        for (String topic : topics) {
          LogTopic logTopic = LogTopic.create(Bytes.fromHexString(topic));
          logTopics.add(logTopic);
        }
        besuLogs.add(
            new net.consensys.gpact.messaging.txrootrelay.besuethereum.core.Log(
                addr, data, logTopics));
      }
      String revertReasonFromReceipt = receipt.getRevertReason();
      Bytes revertReason =
          revertReasonFromReceipt == null ? null : Bytes.fromHexString(receipt.getRevertReason());
      net.consensys.gpact.messaging.txrootrelay.besuethereum.core.TransactionReceipt txReceipt =
          root == null
              ? new net.consensys.gpact.messaging.txrootrelay.besuethereum.core.TransactionReceipt(
                  status,
                  receipt.getCumulativeGasUsed().longValue(),
                  besuLogs,
                  Optional.ofNullable(revertReason))
              : new net.consensys.gpact.messaging.txrootrelay.besuethereum.core.TransactionReceipt(
                  root,
                  receipt.getCumulativeGasUsed().longValue(),
                  besuLogs,
                  Optional.ofNullable(revertReason));
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

    // TODO remove this check code that isn't needed
    EthBlock block = this.web3j.ethGetBlockByHash(aReceipt.getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    if (!besuCalculatedReceiptsRootStr.equalsIgnoreCase(receiptsRoot)) {
      LOG.error(
          "Calculated transaction receipt root {} does not match actual receipt root {}",
          besuCalculatedReceiptsRootStr,
          receiptsRoot);
      throw new Error("Calculated transaction receipt root does not match actual receipt root");
    }

    //    // TODO remove
    //    Bytes32 parentHash = Bytes32.fromHexString(b1.getParentHash());
    //    Bytes32 ommersHash = Bytes32.fromHexString(b1.getSha3Uncles());
    //    Bytes coinbase = Bytes.fromHexString(b1.getMiner());
    //    Bytes32 stateRoot = Bytes32.fromHexString(b1.getStateRoot());
    //    Bytes32 transactionsRoot = Bytes32.fromHexString(b1.getTransactionsRoot());
    //    Bytes32 receiptsRoot1 = Bytes32.fromHexString(b1.getReceiptsRoot());
    //    Bytes logsBloom = Bytes.fromHexString(b1.getLogsBloom());
    //    BigInteger difficulty = b1.getDifficulty();
    //    BigInteger number = b1.getNumber();
    //    BigInteger gasLimit = b1.getGasLimit();
    //    BigInteger gasUsed = b1.getGasUsed();
    //    BigInteger timestamp = b1.getTimestamp();
    //    Bytes extraData = Bytes.fromHexString(b1.getExtraData());
    //    Bytes32 mixHash = Bytes32.fromHexString(b1.getMixHash());
    //    BigInteger nonce = b1.getNonce();
    //
    //    Bytes blockHash1 = Hash.hash(
    //        RLP.encode(
    //            out -> {
    //              out.startList();
    //              out.writeBytes(parentHash);
    //              out.writeBytes(ommersHash);
    //              out.writeBytes(coinbase);
    //              out.writeBytes(stateRoot);
    //              out.writeBytes(transactionsRoot);
    //              out.writeBytes(receiptsRoot1);
    //              out.writeBytes(logsBloom);
    //              out.writeBytes(UInt256.valueOf(difficulty).toMinimalBytes());
    //              out.writeLongScalar(number.longValue());
    //              out.writeLongScalar(gasLimit.longValue());
    //              out.writeLongScalar(gasUsed.longValue());
    //              out.writeLongScalar(timestamp.longValue());
    //              out.writeBytes(extraData);
    //              out.writeBytes(mixHash);
    //              out.writeLong(nonce.longValue());
    ////    if (ExperimentalEIPs.eip1559Enabled && baseFee != null) {
    ////      out.writeLongScalar(baseFee);
    ////    }
    //              out.endList();
    //            }));
    //    LOG.debug("Block Hash Calculated***: {} should be: {}", blockHash1.toHexString(),
    // blockHash);

    // TODO end remove

    BigInteger txIndex = aReceipt.getTransactionIndex();
    Bytes aKey = indexKey((int) txIndex.longValue());

    Proof<Bytes> simpleProof = trie.getValueWithSimpleProof(aKey);
    Bytes encodedTransactionReceipt = simpleProof.getValue().get();
    Bytes rlpOfNode = encodedTransactionReceipt;
    // Node references can be hashes or the node itself, if the node is less than 32 bytes.
    // Leaf nodes in Ethereum, leaves of Merkle Patricia Tries could be less than 32 bytes,
    // but no other nodes. For transaction receipts, it isn't possible even the leaf nodes
    // to be 32 bytes.
    Bytes32 nodeHash = net.consensys.gpact.common.crypto.Hash.keccak256(rlpOfNode);

    List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
    List<BigInteger> proofOffsets = new ArrayList<>();
    List<byte[]> proofs = new ArrayList<>();
    for (int j = proofList1.size() - 1; j >= 0; j--) {
      rlpOfNode = proofList1.get(j);
      proofOffsets.add(BigInteger.valueOf(findOffset(rlpOfNode, nodeHash)));
      proofs.add(rlpOfNode.toArray());
      nodeHash = net.consensys.gpact.common.crypto.Hash.keccak256(rlpOfNode);
    }
    if (!besuCalculatedReceiptsRoot.toHexString().equalsIgnoreCase(nodeHash.toHexString())) {
      throw new Error(
          "Transaction receipt root calculated using proof did not match actual receipt root");
    }

    return new TxReceiptRootTransferEventProof(
        blockchainId,
        cbcContractAddress,
        getTransactionReceiptRoot(aReceipt),
        encodedTransactionReceipt.toArray(),
        proofOffsets,
        proofs,
        eventFunctionSignature,
        eventData);
  }

  static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }

  static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  static int findOffset(Bytes rlpOfNode, Bytes nodeRef) {
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

  public byte[] getTransactionReceiptRoot(TransactionReceipt transactionReceipt) throws Exception {
    EthBlock block = this.web3j.ethGetBlockByHash(transactionReceipt.getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    Bytes32 receiptsRootBytes32 = Bytes32.fromHexString(receiptsRoot);
    return receiptsRootBytes32.toArray();
  }
}
