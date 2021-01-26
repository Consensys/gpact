/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.receipts;

import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.receipts.soliditywrappers.TestReceipts;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hyperledger.besu.crypto.Hash.keccak256;

public class ReceiptDecoding extends AbstractWeb3Test {
  @Test
  public void numLogsFound() throws Exception {
    setupWeb3();

    TestReceipts testReceiptsContract = TestReceipts.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    TransactionReceipt receipt = testReceiptsContract.triggerStartEvent(BigInteger.TEN).send();
    byte[] rlpOfReceipt = web3JReceiptToBytes(receipt);
    BigInteger numLogsFound = testReceiptsContract.numLogsFound(rlpOfReceipt).send();
    assertEquals(numLogsFound, BigInteger.ONE);
  }

  @Test
  public void eventEmitterContractAddress() throws Exception {
    setupWeb3();

    TestReceipts testReceiptsContract = TestReceipts.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    TransactionReceipt receipt = testReceiptsContract.triggerStartEvent(BigInteger.TEN).send();
    byte[] rlpOfReceipt = web3JReceiptToBytes(receipt);
    String emitterAddress = testReceiptsContract.emittingContractFirstLog(rlpOfReceipt).send();
    String contractAddress = testReceiptsContract.getContractAddress();
    assertEquals(emitterAddress, contractAddress);
  }




  @Test
  public void checkEventData() throws Exception {
    int val = 11;
    BigInteger valBigInt = BigInteger.valueOf(val);

    setupWeb3();

    TestReceipts testReceiptsContract = TestReceipts.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    TransactionReceipt receipt = testReceiptsContract.triggerStartEvent(valBigInt).send();
    byte[] rlpOfReceipt = web3JReceiptToBytes(receipt);
    String contractAddress = testReceiptsContract.getContractAddress();
    byte[] eventData = testReceiptsContract.retrieveStartLog(contractAddress, rlpOfReceipt).send();
    assertEquals(eventData.length, 32);
    BigInteger eventDataBigInt = new BigInteger(1, eventData);
    assertEquals(eventDataBigInt, valBigInt);
  }


  @Test
  public void checkEventDataNamedEvent() throws Exception {
    int val = 11;
    BigInteger valBigInt = BigInteger.valueOf(val);

    setupWeb3();

    TestReceipts testReceiptsContract = TestReceipts.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    TransactionReceipt receipt = testReceiptsContract.triggerStartEvent(valBigInt).send();
    byte[] rlpOfReceipt = web3JReceiptToBytes(receipt);
    String contractAddress = testReceiptsContract.getContractAddress();
    String eventFuncSigStr = "StartEvent(uint256)";
    byte[] eventFuncSigBytes = eventFuncSigStr.getBytes();
    byte[] eventFuncSigHash = keccak256(Bytes.wrap(eventFuncSigBytes)).toArray();
    byte[] eventData = testReceiptsContract.retrieveALog(contractAddress, eventFuncSigHash, rlpOfReceipt).send();
    assertEquals(eventData.length, 32);
    BigInteger eventDataBigInt = new BigInteger(1, eventData);
    assertEquals(eventDataBigInt, valBigInt);
  }


  private static byte[] web3JReceiptToBytes(TransactionReceipt receipt) {
    org.hyperledger.besu.ethereum.core.TransactionReceipt txR = web3JReceiptToBesuReceipt(receipt);
    return RLP.encode(txR::writeTo).toArray();
  }

  private static org.hyperledger.besu.ethereum.core.TransactionReceipt web3JReceiptToBesuReceipt(TransactionReceipt receipt) {
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
                besuLogs, java.util.Optional.ofNullable(revertReason))
            :
            new org.hyperledger.besu.ethereum.core.TransactionReceipt(root, receipt.getCumulativeGasUsed().longValue(),
                besuLogs, java.util.Optional.ofNullable(revertReason));
    return txReceipt;
  }

}

