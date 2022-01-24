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
package net.consensys.gpact.functioncall.sfc;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.besucrypto.Hash;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.soliditywrappers.functioncall.sfc.SimpleCrosschainControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class SimpleCrossControlManager extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(SimpleCrossControlManager.class);

  public static byte[] CROSSCALL_EVENT_SIGNATURE =
      Hash.keccak256(
              Bytes.wrap("CrossCall(bytes32,uint256,address,uint256,address,bytes)".getBytes()))
          .toArray();
  public static Bytes CROSSCALL_EVENT_SIGNATURE_BYTES = Bytes.wrap(CROSSCALL_EVENT_SIGNATURE);

  protected net.consensys.gpact.soliditywrappers.functioncall.sfc.SimpleCrosschainControl
      crossBlockchainControlContract;

  public SimpleCrossControlManager(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  protected void deployContracts() throws Exception {
    final BigInteger HARD_CODED_EVENT_HORIZON = BigInteger.valueOf(3600); // 1 hour == 3600 seconds
    this.crossBlockchainControlContract =
        net.consensys.gpact.soliditywrappers.functioncall.sfc.SimpleCrosschainControl.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                this.blockchainId.asBigInt(),
                HARD_CODED_EVENT_HORIZON)
            .send();
    LOG.debug(
        " Simple Cross Blockchain Contract Contract: {}",
        this.crossBlockchainControlContract.getContractAddress());
  }

  public List<String> getContractAddresses() {
    List<String> addresses = new ArrayList<>();
    addresses.add(this.crossBlockchainControlContract.getContractAddress());
    return addresses;
  }

  public void loadContracts(List<String> addresses) {
    this.crossBlockchainControlContract =
        net.consensys.gpact.soliditywrappers.functioncall.sfc.SimpleCrosschainControl.load(
            addresses.get(0), this.web3j, this.tm, this.gasProvider);
  }

  public void addBlockchain(
      BlockchainId bcId, String cbcContractAddress, String verifierContractAddress)
      throws Exception {
    TransactionReceipt txr =
        this.crossBlockchainControlContract
            .addRemoteCrosschainControl(bcId.asBigInt(), cbcContractAddress)
            .send();
    assert (txr.isStatusOK());

    txr =
        this.crossBlockchainControlContract
            .addVerifier(bcId.asBigInt(), verifierContractAddress)
            .send();
    assert (txr.isStatusOK());
  }

  public Tuple<TransactionReceipt, byte[], SimpleCrosschainControl.CrossCallEventResponse>
      sourceBcCall(RemoteCall<TransactionReceipt> functionCall) throws Exception {

    LOG.debug("Transaction on source blockchain {}", this.blockchainId);
    StatsHolder.log("Source Blockchain call now");
    TransactionReceipt txR;
    try {
      txR = functionCall.send();
    } catch (TransactionException ex) {
      // Crosschain Control Contract reverted
      String revertReason =
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
      LOG.error(" Revert Reason1: {}", revertReason);
      return new Tuple<TransactionReceipt, byte[], SimpleCrosschainControl.CrossCallEventResponse>(
          ex.getTransactionReceipt().get(), null, null);
    }

    StatsHolder.logGas("Source Bc Call", txR.getGasUsed());
    List<SimpleCrosschainControl.CrossCallEventResponse> callEvents =
        this.crossBlockchainControlContract.getCrossCallEvents(txR);
    SimpleCrosschainControl.CrossCallEventResponse crossCallEvent = callEvents.get(0);
    dumpCrossCallEvent(crossCallEvent);
    return new Tuple<TransactionReceipt, byte[], SimpleCrosschainControl.CrossCallEventResponse>(
        txR, getEventData(txR, CROSSCALL_EVENT_SIGNATURE_BYTES), crossCallEvent);
  }

  public Tuple<TransactionReceipt, String, Boolean> destinationBcCall(SignedEvent crossCallEvent)
      throws Exception {
    TransactionReceipt txR;
    try {
      LOG.debug("Transaction on Destination blockchain {}", this.blockchainId);
      txR =
          this.crossBlockchainControlContract
              .crossCallHandler(
                  crossCallEvent.getBcId().asBigInt(), crossCallEvent.getCbcContract(),
                  crossCallEvent.getEventData(), crossCallEvent.getEncodedSignatures())
              .send();
      StatsHolder.logGas("Destination Bc Call", txR.getGasUsed());
    } catch (TransactionException ex) {
      // Crosschain Control Contract reverted
      String revertReason =
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
      LOG.error(" Revert Reason2: {}", revertReason);
      return new Tuple<TransactionReceipt, String, Boolean>(
          ex.getTransactionReceipt().get(), revertReason, false);
    }

    if (!txR.isStatusOK()) {
      // Transaction failed
      return new Tuple<TransactionReceipt, String, Boolean>(txR, null, false);
    }

    List<SimpleCrosschainControl.CallFailureEventResponse> failureEventResponses =
        this.crossBlockchainControlContract.getCallFailureEvents(txR);
    if (!failureEventResponses.isEmpty()) {
      // Application contract reverted
      // There will only be one failure event.
      LOG.warn(" Revert Reason3: {}", failureEventResponses.get(0)._revertReason);
      return new Tuple<TransactionReceipt, String, Boolean>(
          txR, failureEventResponses.get(0)._revertReason, false);
    }
    // Everything worked!
    return new Tuple<TransactionReceipt, String, Boolean>(txR, null, true);
  }

  public String getCbcContractAddress() {
    return this.crossBlockchainControlContract.getContractAddress();
  }

  // TODO this is common code between gpact and this.
  // TODO put common code somewhere
  public byte[] getEventData(TransactionReceipt txR, Bytes eventSignatureToFind) throws Exception {
    List<Log> logs = txR.getLogs();
    String cbcAddress = getCbcContractAddress();
    for (Log log : logs) {
      String eventEmittedByAddress = log.getAddress();
      if (!cbcAddress.equalsIgnoreCase(eventEmittedByAddress)) {
        continue;
      }
      List<String> eventTopics = log.getTopics();
      if (eventTopics.size() != 1) {
        continue;
      }
      String eventSignatureStr = eventTopics.get(0);
      Bytes eventSignatureBytes = Bytes.fromHexString(eventSignatureStr);
      if (eventSignatureBytes.compareTo(eventSignatureToFind) != 0) {
        continue;
      }
      String eventDataStr = log.getData();
      Bytes eventDataBytes = Bytes.fromHexString(eventDataStr);
      return eventDataBytes.toArray();
    }
    throw new Exception("Event not found in transaction receipt");
  }

  private void dumpCrossCallEvent(SimpleCrosschainControl.CrossCallEventResponse event) {
    LOG.debug(" Cross Call Event:");
    LOG.debug("   Tx Id: 0x{}", new BigInteger(1, event._txId).toString(16));
    LOG.debug("   Timestamp: {}", event._timestamp);
    LOG.debug("   Caller: {}", event._caller);
    LOG.debug("   Dest BdId: 0x{}", event._destBcId.toString(16));
    LOG.debug("   Dest Contract: {}", event._destContract);
    LOG.debug("   Dest Function Call: {}", new BigInteger(1, event._destFunctionCall).toString(16));
  }
}
