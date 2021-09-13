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
package net.consensys.gpact.cbc;

import net.consensys.gpact.cbc.soliditywrappers.CrosschainVerifierTxRoot;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.StatsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
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
import net.consensys.gpact.cbc.soliditywrappers.CrosschainControl;
import net.consensys.gpact.txroot.soliditywrappers.TxReceiptsRootStorage;
import net.consensys.gpact.trie.MerklePatriciaTrie;
import net.consensys.gpact.trie.Proof;
import net.consensys.gpact.trie.SimpleMerklePatriciaTrie;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CrossBlockchainControlTxReceiptRootTransfer extends AbstractCbc {
  private static final Logger LOG = LogManager.getLogger(CrossBlockchainControlTxReceiptRootTransfer.class);

  // TODO put this into a map for the current transaction id, so many transactions can be handled in parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;

  private TxReceiptsRootStorage txReceiptsRootStorageContract;
  private CrosschainVerifierTxRoot verifier;

  public CrossBlockchainControlTxReceiptRootTransfer(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts() throws Exception {
    super.deployContracts();
    this.txReceiptsRootStorageContract =
        TxReceiptsRootStorage.deploy(this.web3j, this.tm, this.gasProvider,
            this.registrarContract.getContractAddress()).send();
    this.verifier =
            CrosschainVerifierTxRoot.deploy(this.web3j, this.tm, this.gasProvider, this.registrarContract.getContractAddress(), this.txReceiptsRootStorageContract.getContractAddress()).send();

    LOG.debug(" TxReceiptRoot Contract: {}", this.txReceiptsRootStorageContract.getContractAddress());
  }

  public List<String> getContractAddresses() {
    List<String> addresses = super.getContractAddresses();
    addresses.add(this.txReceiptsRootStorageContract.getContractAddress());
    addresses.add(this.verifier.getContractAddress());
    return addresses;
  }

  public void loadContracts(List<String> addresses) {
    super.loadContracts(addresses);
    this.txReceiptsRootStorageContract = TxReceiptsRootStorage.load(addresses.get(2), this.web3j, this.tm, this.gasProvider);
    this.verifier = CrosschainVerifierTxRoot.load(addresses.get(3), this.web3j, this.tm, this.gasProvider);
  }



  protected void addVerifier(BigInteger bcId) throws Exception {
    this.crossBlockchainControlContract.addVerifier(bcId, this.verifier.getContractAddress()).send();
  }


  public byte[] getTransactionReceiptRoot(TransactionReceipt transactionReceipt) throws Exception {
    EthBlock block = this.web3j.ethGetBlockByHash(transactionReceipt.getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    Bytes32 receiptsRootBytes32 = Bytes32.fromHexString(receiptsRoot);
    return receiptsRootBytes32.toArray();
  }


  public void addTransactionReceiptRootToBlockchain(
          AnIdentity[] signers, BigInteger sourceBlockchainId, byte[] transactionReceiptRoot) throws Exception {
    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer: signers) {
      Sign.SignatureData signatureData = signer.sign(transactionReceiptRoot);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(BigInteger.valueOf(signatureData.getV()[0]));
    }

    // This will revert if the signature does not verify
    try {
      TransactionReceipt txR = this.txReceiptsRootStorageContract.addTxReceiptRoot(sourceBlockchainId, theSigners, sigR, sigS, sigV, transactionReceiptRoot).send();
      StatsHolder.logGas("AddTxReceiptRoot Transaction", txR.getGasUsed());
      if (!txR.isStatusOK()) {
        throw new Exception("Transaction to add transaction receipt root failed");
      }
    } catch (TransactionException txe) {
      String revertReason = txe.getTransactionReceipt().get().getRevertReason();
      LOG.error("Revert Reason: {}", RevertReason.decodeRevertReason(revertReason));
      throw txe;
    }
  }

  public CompletableFuture<TransactionReceipt> addTransactionReceiptRootToBlockchainAsyncPart1(
      AnIdentity[] signers, BigInteger sourceBlockchainId, byte[] transactionReceiptRoot) throws Exception {
    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer: signers) {
      Sign.SignatureData signatureData = signer.sign(transactionReceiptRoot);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(BigInteger.valueOf(signatureData.getV()[0]));
    }

    LOG.debug("txReceiptsRootStorageContract.addTxReceiptRoot: publishing to BC ID {}, from BC ID: {}", this.blockchainId, sourceBlockchainId);
    return this.txReceiptsRootStorageContract.addTxReceiptRoot(sourceBlockchainId, theSigners, sigR, sigS, sigV, transactionReceiptRoot).sendAsync();
  }


  public void addTransactionReceiptRootToBlockchainAsyncPart2(TransactionReceipt txR) throws Exception {
    if (!txR.isStatusOK()) {
      String revertReason = txR.getRevertReason();
      LOG.error("Transaction to add transaction receipt root failed: Revert Reason: {}", RevertReason.decodeRevertReason(revertReason));
      throw new Exception("Transaction to add transaction receipt root failed: Revert Reason: " + RevertReason.decodeRevertReason(revertReason));
    }
    StatsHolder.logGas("AddTxReceiptRoot Transaction", txR.getGasUsed());
  }



  public TxReceiptRootTransferEventProof getProofForTxReceipt(
          BigInteger blockchainId, String cbcContractAddress, TransactionReceipt aReceipt,
          byte[] eventData,
          byte[] eventFunctionSignature) throws Exception {
    // Calculate receipt root based on logs for all receipts of all transactions in the block.
    String blockHash = aReceipt.getBlockHash();
    EthGetBlockTransactionCountByHash transactionCountByHash = this.web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    List<org.hyperledger.besu.ethereum.core.TransactionReceipt> besuReceipts = new ArrayList<>();

    BigInteger transactionIndex = BigInteger.ZERO;
    do {
      EthTransaction ethTransaction = this.web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
      Optional<Transaction> transaction = ethTransaction.getTransaction();
      assert(transaction.isPresent());
      String txHash = transaction.get().getHash();
      EthGetTransactionReceipt ethGetTransactionReceipt = this.web3j.ethGetTransactionReceipt(txHash).send();
      Optional<TransactionReceipt> mayBeReceipt = ethGetTransactionReceipt.getTransactionReceipt();
      assert(mayBeReceipt.isPresent());
      TransactionReceipt receipt = mayBeReceipt.get();

      // Convert to Besu objects
      List<org.hyperledger.besu.ethereum.core.Log> besuLogs = new ArrayList<>();

      String stateRootFromReceipt = receipt.getRoot();
      Hash root = (stateRootFromReceipt == null) ? null : Hash.fromHexString(receipt.getRoot());
      String statusFromReceipt = receipt.getStatus();
      int status = statusFromReceipt == null ? -1 : Integer.parseInt(statusFromReceipt.substring(2), 16);
      for (Log web3jLog: receipt.getLogs()) {
        org.hyperledger.besu.ethereum.core.Address addr = org.hyperledger.besu.ethereum.core.Address.fromHexString(web3jLog.getAddress());
        Bytes data = Bytes.fromHexString(web3jLog.getData());
        List<String> topics = web3jLog.getTopics();
        List<LogTopic> logTopics = new ArrayList<>();
        for (String topic: topics) {
          LogTopic logTopic = LogTopic.create(Bytes.fromHexString(topic));
          logTopics.add(logTopic);
        }
        besuLogs.add(new org.hyperledger.besu.ethereum.core.Log(addr, data, logTopics));
      }
      String revertReasonFromReceipt = receipt.getRevertReason();
      Bytes revertReason = revertReasonFromReceipt == null ? null : Bytes.fromHexString(receipt.getRevertReason());
      org.hyperledger.besu.ethereum.core.TransactionReceipt txReceipt =
          root == null ?
              new org.hyperledger.besu.ethereum.core.TransactionReceipt(status, receipt.getCumulativeGasUsed().longValue(),
                  besuLogs, Optional.ofNullable(revertReason))
              :
              new org.hyperledger.besu.ethereum.core.TransactionReceipt(root, receipt.getCumulativeGasUsed().longValue(),
                  besuLogs, Optional.ofNullable(revertReason));
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
    if (!besuCalculatedReceiptsRootStr.equalsIgnoreCase( receiptsRoot)) {
      LOG.error("Calculated transaction receipt root {} does not match actual receipt root {}", besuCalculatedReceiptsRootStr, receiptsRoot);
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
//    LOG.debug("Block Hash Calculated***: {} should be: {}", blockHash1.toHexString(), blockHash);


    // TODO end remove

    BigInteger txIndex = aReceipt.getTransactionIndex();
    Bytes aKey = indexKey((int)txIndex.longValue());

    Proof<Bytes> simpleProof = trie.getValueWithSimpleProof(aKey);
    Bytes encodedTransactionReceipt = simpleProof.getValue().get();
    Bytes rlpOfNode = encodedTransactionReceipt;
    // Node references can be hashes or the node itself, if the node is less than 32 bytes.
    // Leaf nodes in Ethereum, leaves of Merkle Patricia Tries could be less than 32 bytes,
    // but no other nodes. For transaction receipts, it isn't possible even the leaf nodes
    // to be 32 bytes.
    Bytes32 nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);

    List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
    List<BigInteger> proofOffsets = new ArrayList<>();
    List<byte[]> proofs = new ArrayList<>();
    for (int j = proofList1.size()-1; j >=0; j--) {
      rlpOfNode = proofList1.get(j);
      proofOffsets.add(BigInteger.valueOf(findOffset(rlpOfNode, nodeHash)));
      proofs.add(rlpOfNode.toArray());
      nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);
    }
    if (!besuCalculatedReceiptsRoot.toHexString().equalsIgnoreCase(nodeHash.toHexString())) {
      throw new Error("Transaction receipt root calculated using proof did not match actual receipt root");
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







  protected static int findOffset(Bytes rlpOfNode, Bytes nodeRef) {
    int sizeNodeRef = nodeRef.size();
    for (int i = 0; i < rlpOfNode.size() - sizeNodeRef; i++) {
      boolean found = true;
      for (int j = 0; j < sizeNodeRef; j++) {
        if (rlpOfNode.get(i+j) != nodeRef.get(j)) {
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


  protected static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  protected static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }

  public TxReceiptRootTransferEventProof getEventProof(TransactionReceipt startTxReceipt, byte[] eventData,
                                                       byte[] eventFunctionSignature) throws Exception {
    return getProofForTxReceipt(this.blockchainId,
            this.crossBlockchainControlContract.getContractAddress(),
            startTxReceipt,
            eventData,
            eventFunctionSignature);
  }


  private List<BadCallEventResponse> convertBadCall(List<CrosschainControl.BadCallEventResponse> callEventResponses) {
    List<BadCallEventResponse> result = new ArrayList<>();
    for (CrosschainControl.BadCallEventResponse e : callEventResponses) {
      BadCallEventResponse event = new BadCallEventResponse(e._expectedBlockchainId, e._actualBlockchainId,
          e._expectedContract, e._actualContract, e._expectedFunctionCall, e._actualFunctionCall);
      result.add(event);
    }
    return result;
  }

  private List<CallFailureEventResponse> convertCallFailure(List<CrosschainControl.CallFailureEventResponse> callFailureEventResponses) {
    List<CallFailureEventResponse> result = new ArrayList<>();
    for (CrosschainControl.CallFailureEventResponse e : callFailureEventResponses) {
      CallFailureEventResponse event = new CallFailureEventResponse(e._revertReason);
      result.add(event);
    }
    return result;
  }

  private List<CallResultEventResponse> convertCallResult(List<CrosschainControl.CallResultEventResponse> callResultEventResponses) {
    List<CallResultEventResponse> result = new ArrayList<>();
    for (CrosschainControl.CallResultEventResponse e : callResultEventResponses) {
      CallResultEventResponse event = new CallResultEventResponse(e._blockchainId, e._contract, e._functionCall, e._result);
      result.add(event);
    }
    return result;
  }

  private List<DumpEventResponse> convertDump(List<CrosschainControl.DumpEventResponse> dumpEventResponses) {
    List<DumpEventResponse> result = new ArrayList<>();
    for (CrosschainControl.DumpEventResponse e : dumpEventResponses) {
      DumpEventResponse event = new DumpEventResponse(e._val1, e._val2, e._val3, e._val4);
      result.add(event);
    }
    return result;
  }

  private List<NotEnoughCallsEventResponse> convertNotEnoughCalls(List<CrosschainControl.NotEnoughCallsEventResponse> notEnoughCallsEventResponses) {
    List<NotEnoughCallsEventResponse> result = new ArrayList<>();
    for (CrosschainControl.NotEnoughCallsEventResponse e : notEnoughCallsEventResponses) {
      NotEnoughCallsEventResponse event = new NotEnoughCallsEventResponse(e._expectedNumberOfCalls, e._actualNumberOfCalls);
      result.add(event);
    }
    return result;
  }

  private List<RootEventResponse> convertRoot(List<CrosschainControl.RootEventResponse> rootEventResponses) {
    List<RootEventResponse> result = new ArrayList<>();
    for (CrosschainControl.RootEventResponse e : rootEventResponses) {
      RootEventResponse event = new RootEventResponse(e._crossBlockchainTransactionId, e._success);
      result.add(event);
    }
    return result;
  }

  private List<SegmentEventResponse> convertSegment(List<CrosschainControl.SegmentEventResponse> segmentEventResponses) {
    List<SegmentEventResponse> result = new ArrayList<>();
    for (CrosschainControl.SegmentEventResponse e : segmentEventResponses) {
      // TODO The code below is a hack to handle the fact that currently Web3J returns a Uint256 object, but the type is BigInteger.
      // TODO this code will break when Web3J fixes their bug.
      List<BigInteger> callPathFixed = new ArrayList<>();
      for (Object partOfCallPath: e._callPath) {
        Uint256 hack = (Uint256) partOfCallPath;
        callPathFixed.add(hack.getValue());
      }
      // TODO The code below is a hack to handle the fact that currently Web3J returns an Address object, but the type is BigInteger.
      // TODO this code will break when Web3J fixes their bug.
      List<String> lockedContractsFixed = new ArrayList<>();
      for (Object lockedContract: e._lockedContracts) {
        Address hack = (Address) lockedContract;
        lockedContractsFixed.add(hack.getValue());
      }
      SegmentEventResponse event = new SegmentEventResponse(e._crossBlockchainTransactionId, e._hashOfCallGraph,
          callPathFixed, lockedContractsFixed, e._success, e._returnValue);
      result.add(event);
    }
    return result;
  }


}
