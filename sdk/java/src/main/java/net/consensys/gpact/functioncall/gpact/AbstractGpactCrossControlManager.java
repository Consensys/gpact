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
package net.consensys.gpact.functioncall.gpact;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public abstract class AbstractGpactCrossControlManager extends AbstractBlockchain
    implements GpactCrossControlManager {
  private static final Logger LOG = LogManager.getLogger(AbstractGpactCrossControlManager.class);

  protected AbstractGpactCrossControlManager(
      final Credentials credentials, final BlockchainConfig bcConfig) throws IOException {
    super(credentials, bcConfig);
  }

  protected void showBadCallEvents(List<BadCallEventResponse> badCallEventRespons) {
    LOG.debug("Bad Call Events");
    if (badCallEventRespons.isEmpty()) {
      LOG.debug(" None");
    }
    for (BadCallEventResponse badCallEventResponse : badCallEventRespons) {
      boolean isV1 = badCallEventResponse.ver == GpactCrossControlManagerGroup.GpactVersion.V1;

      LOG.debug(" Event:");
      if (isV1) {
        LOG.debug(
            "   Expected Blockchain Id: 0x{}",
            badCallEventResponse._expectedBlockchainId.toString(16));
      } else {
        LOG.debug(
            "   Expected Function Call Hash: {}",
            new BigInteger(1, badCallEventResponse._expectedFunctionCallHash).toString(16));
      }
      LOG.debug(
          "   Actual Blockchain Id: 0x{}", badCallEventResponse._actualBlockchainId.toString(16));
      if (isV1) {
        LOG.debug("   Expected Contract: {}", badCallEventResponse._expectedContract);
      }
      LOG.debug("   Actual Contract: {}", badCallEventResponse._actualContract);
      if (isV1) {
        LOG.debug(
            "   Expected Function Call: {}",
            new BigInteger(1, badCallEventResponse._expectedFunctionCall).toString(16));
      }
      LOG.debug(
          "   Actual Function Call: {}",
          new BigInteger(1, badCallEventResponse._actualFunctionCall).toString(16));
    }
  }

  protected void showCallFailureEvents(List<CallFailureEventResponse> callFailureEventResponses) {
    LOG.debug("Call Failure Events");
    if (callFailureEventResponses.isEmpty()) {
      LOG.debug(" None");
    }
    for (CallFailureEventResponse callFailureEventResponse : callFailureEventResponses) {
      LOG.debug(" Revert Reason: {}", callFailureEventResponse._revertReason);
    }
  }

  protected void showCallResultEvents(List<CallResultEventResponse> callResultEventResponses) {
    LOG.debug("Call Result Events");
    if (callResultEventResponses.isEmpty()) {
      LOG.debug(" None");
    }
    for (CallResultEventResponse callResultEventResponse : callResultEventResponses) {
      LOG.debug(" Event:");
      LOG.debug("   Blockchain Id: 0x{}", callResultEventResponse.blockchainId.toString(16));
      LOG.debug("   Contract: {}", callResultEventResponse.contract);
      LOG.debug(
          "   Function Call: {}",
          new BigInteger(1, callResultEventResponse.functionCall).toString(16));
      LOG.debug("   Result: 0x{}", new BigInteger(1, callResultEventResponse.result).toString(16));
    }
  }

  protected void showDumpEvents(List<DumpEventResponse> dumpEventResponses) {
    LOG.debug("Dump Events");
    if (dumpEventResponses.isEmpty()) {
      LOG.debug(" None");
    }
    for (DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.debug(" Event:");
      LOG.debug("  Uint256: {}", dumpEventResponse._val1.toString(16));
      LOG.debug("  Bytes32: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.debug("  Address: {}", dumpEventResponse._val3);
      LOG.debug("  Bytes: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }
  }

  protected void showNotEnoughCallsEvents(
      List<NotEnoughCallsEventResponse> notEnoughCallsEventResponses) {
    LOG.debug("Not Enough Call Events");
    if (notEnoughCallsEventResponses.isEmpty()) {
      LOG.debug(" None");
    }
    for (NotEnoughCallsEventResponse notEnoughCallsEventResponse : notEnoughCallsEventResponses) {
      LOG.debug("  Event:");
      LOG.debug("   Actual Number of Calls: {}", notEnoughCallsEventResponse._actualNumberOfCalls);
      LOG.debug(
          "   Expected Number of Calls: {}", notEnoughCallsEventResponse._expectedNumberOfCalls);
    }
  }

  protected void showRootEvents(List<RootEventResponse> rootEventResponses) {
    if (rootEventResponses.size() != 1) {
      LOG.error("Unexpected number of root events emitted: {}", rootEventResponses.size());
    }
    LOG.debug("Root Event:");
    for (RootEventResponse rootEventResponse : rootEventResponses) {
      LOG.debug(
          " _crossBlockchainTransactionId: 0x{}",
          rootEventResponse._crossBlockchainTransactionId.toString(16));
      LOG.debug(" _success: {}", rootEventResponse._success);
    }
    if (rootEventResponses.size() != 1) {
      throw new RuntimeException("Undexpected number of root events: " + rootEventResponses.size());
    }
  }

  protected void showSegmentEvents(List<SegmentEventResponse> segmentEventResponses) {
    if (segmentEventResponses.size() != 1) {
      LOG.error("Unexpected number of segment events emitted: {}", segmentEventResponses.size());
    }
    LOG.debug("Segment Event:");
    for (SegmentEventResponse segmentEventResponse : segmentEventResponses) {
      LOG.debug(
          " Cross-Blockchain Transaction Id: 0x{}",
          segmentEventResponse._crossBlockchainTransactionId.toString(16));
      StringBuilder calls = new StringBuilder();
      for (BigInteger partOfCallPath : segmentEventResponse._callPath) {
        calls.append("[");
        calls.append(partOfCallPath);
        calls.append("] ");
      }
      LOG.debug(" Call Path: {}", calls);
      LOG.debug(
          " Hash Of Call Graph: {}",
          new BigInteger(1, segmentEventResponse._hashOfCallGraph).toString(16));
      LOG.debug(" Success: {}", segmentEventResponse._success);
      LOG.debug(
          " Return Value: {}", new BigInteger(1, segmentEventResponse._returnValue).toString(16));
      StringBuilder lockedContracts = new StringBuilder();
      for (String lockedContract : segmentEventResponse._lockedContracts) {
        calls.append("[");
        calls.append(lockedContract);
        calls.append("] ");
      }
      LOG.debug(" Locked Contracts: [{}]", lockedContracts);
    }
    if (segmentEventResponses.size() != 1) {
      throw new RuntimeException(
          "Unexpected number of segment events: " + segmentEventResponses.size());
    }
  }

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
}
