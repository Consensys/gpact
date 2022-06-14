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

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

import java.io.IOException;
import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.crypto.Hash;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class GpactCrossControlManager extends AbstractBlockchain implements CrossControlManager {
  private static final Logger LOG = LogManager.getLogger(GpactCrossControlManager.class);

  public static byte[] START_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("Start(uint256,address,uint256,bytes)".getBytes())).toArray();
  public static Bytes START_EVENT_SIGNATURE_BYTES = Bytes.wrap(START_EVENT_SIGNATURE);
  public static byte[] SEGMENT_EVENT_SIGNATURE =
      Hash.keccak256(
              Bytes.wrap("Segment(uint256,bytes32,uint256[],address[],bool,bytes)".getBytes()))
          .toArray();
  public static Bytes SEGMENT_EVENT_SIGNATURE_BYTES = Bytes.wrap(SEGMENT_EVENT_SIGNATURE);
  public static byte[] ROOT_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("Root(uint256,bool)".getBytes())).toArray();
  public static Bytes ROOT_EVENT_SIGNAUTRE_BYTES = Bytes.wrap(ROOT_EVENT_SIGNATURE);

  protected net.consensys.gpact.functioncall.gpact.CrosschainControl crossBlockchainControlContract;

  // TODO put this into a map for the current transaction id, so many transactions can be handled in
  // parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;

  protected GpactCrossControlManager(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  protected void deployContract() throws Exception {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.CrosschainControl.deploy(
                this.web3j, this.tm, this.gasProvider, this.blockchainId.asBigInt())
            .send();
    LOG.debug(
        " Cross Blockchain Contract Contract: {}",
        this.crossBlockchainControlContract.getContractAddress());
  }

  public void loadContract(String cbcAddress) {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.CrosschainControl.load(
            cbcAddress, this.web3j, this.tm, this.gasProvider);
  }

  public void addRemoteBlockchain(
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

  public static BigInteger generateRandomCrossBlockchainTransactionId()
      throws NoSuchAlgorithmException {
    // TODO put this into the crypto module and do a better job or this + reseeding.
    final SecureRandom rand =
        SecureRandom.getInstance(
            "DRBG", DrbgParameters.instantiation(256, RESEED_ONLY, new byte[] {0x01}));
    return new BigInteger(255, rand);
  }

  public Tuple<TransactionReceipt, byte[], Boolean> start(
      BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    LOG.debug("Start Transaction on blockchain {}", this.blockchainId);
    StatsHolder.log("Start call now");
    TransactionReceipt txR =
        this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<net.consensys.gpact.functioncall.gpact.CrosschainControl.StartEventResponse> startEvents =
        this.crossBlockchainControlContract.getStartEvents(txR);
    net.consensys.gpact.functioncall.gpact.CrosschainControl.StartEventResponse startEvent =
        startEvents.get(0);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    // LOG.debug("Start Event: {}", new BigInteger(getEventData(txR,
    // AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return new Tuple<TransactionReceipt, byte[], Boolean>(
        txR, getEventData(txR, GpactCrossControlManager.START_EVENT_SIGNATURE_BYTES), false);
  }

  public Tuple<TransactionReceipt, byte[], Boolean> segment(
      SignedEvent startEvent, SignedEvent segEvents[], List<BigInteger> callPath) throws Exception {
    List<BigInteger> bcIds = new ArrayList<>();
    List<String> cbcAddresses = new ArrayList<>();
    List<byte[]> eventFunctionSignatures = new ArrayList<>();
    List<byte[]> eventData = new ArrayList<>();
    List<byte[]> encodedSignatures = new ArrayList<>();

    bcIds.add(startEvent.getBcId().asBigInt());
    cbcAddresses.add(startEvent.getCbcContract());
    eventFunctionSignatures.add(startEvent.getEventFunctionSignature());
    eventData.add(startEvent.getEventData());
    encodedSignatures.add(startEvent.getEncodedSignatures());
    for (SignedEvent segEvent : segEvents) {
      bcIds.add(segEvent.getBcId().asBigInt());
      cbcAddresses.add(segEvent.getCbcContract());
      eventFunctionSignatures.add(segEvent.getEventFunctionSignature());
      eventData.add(segEvent.getEventData());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    LOG.debug("Call Path Len: {}", callPath.size());
    for (int i = 0; i < callPath.size(); i++) {
      LOG.debug("Call Path[{}]: {}", i, callPath.get(i));
    }

    // RlpDumper.dump(RLP.input(Bytes.wrap(encodedSignatures.get(0))));
    TransactionReceipt txR;
    try {
      LOG.debug("Segment Transaction on blockchain {}", this.blockchainId);
      txR =
          this.crossBlockchainControlContract
              .segment(
                  bcIds,
                  cbcAddresses,
                  eventFunctionSignatures,
                  eventData,
                  encodedSignatures,
                  callPath)
              .send();
      StatsHolder.logGas("Segment Transaction", txR.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    showSegmentEvents(convertSegment(this.crossBlockchainControlContract.getSegmentEvents(txR)));
    showBadCallEvents(convertBadCall(this.crossBlockchainControlContract.getBadCallEvents(txR)));
    showNotEnoughCallsEvents(
        convertNotEnoughCalls(this.crossBlockchainControlContract.getNotEnoughCallsEvents(txR)));
    showCallFailureEvents(
        convertCallFailure(this.crossBlockchainControlContract.getCallFailureEvents(txR)));
    showCallResultEvents(
        convertCallResult(this.crossBlockchainControlContract.getCallResultEvents(txR)));
    showDumpEvents(convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));

    List<net.consensys.gpact.functioncall.gpact.CrosschainControl.SegmentEventResponse>
        segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    net.consensys.gpact.functioncall.gpact.CrosschainControl.SegmentEventResponse
        segmentEventResponse = segmentEventResponses.get(0);

    return new Tuple<TransactionReceipt, byte[], Boolean>(
        txR,
        getEventData(txR, GpactCrossControlManager.SEGMENT_EVENT_SIGNATURE_BYTES),
        segmentEventResponse._lockedContracts.isEmpty());
  }

  public Tuple<TransactionReceipt, byte[], Boolean> root(
      SignedEvent startEvent, SignedEvent[] segEvents) throws Exception {
    List<BigInteger> bcIds = new ArrayList<>();
    List<String> cbcAddresses = new ArrayList<>();
    List<byte[]> eventFunctionSignatures = new ArrayList<>();
    List<byte[]> eventData = new ArrayList<>();
    List<byte[]> encodedSignatures = new ArrayList<>();

    bcIds.add(startEvent.getBcId().asBigInt());
    cbcAddresses.add(startEvent.getCbcContract());
    eventFunctionSignatures.add(startEvent.getEventFunctionSignature());
    eventData.add(startEvent.getEventData());
    encodedSignatures.add(startEvent.getEncodedSignatures());
    for (SignedEvent segEvent : segEvents) {
      bcIds.add(segEvent.getBcId().asBigInt());
      cbcAddresses.add(segEvent.getCbcContract());
      eventFunctionSignatures.add(segEvent.getEventFunctionSignature());
      eventData.add(segEvent.getEventData());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    long now = System.currentTimeMillis() / 1000;
    LOG.debug(
        " Current time on this computer: {}; Transaction time-out: {}",
        now,
        this.crossBlockchainTransactionTimeout);
    if (this.crossBlockchainTransactionTimeout < now) {
      LOG.warn(" Cross-Blockchain transaction will fail as transaction has timed-out");
    } else if (this.crossBlockchainTransactionTimeout < (now - 10)) {
      LOG.warn(" Cross-Blockchain transaction might fail as transaction time-out is soon");
    }

    TransactionReceipt txR;
    try {
      LOG.debug("Root Transaction on blockchain {}", this.blockchainId);
      txR =
          this.crossBlockchainControlContract
              .root(bcIds, cbcAddresses, eventFunctionSignatures, eventData, encodedSignatures)
              .send();
      StatsHolder.logGas("Root Transaction", txR.getGasUsed());
      if (!txR.isStatusOK()) {
        throw new Exception("Root transaction failed");
      }
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    showRootEvents(convertRoot(this.crossBlockchainControlContract.getRootEvents(txR)));
    showBadCallEvents(convertBadCall(this.crossBlockchainControlContract.getBadCallEvents(txR)));
    showNotEnoughCallsEvents(
        convertNotEnoughCalls(this.crossBlockchainControlContract.getNotEnoughCallsEvents(txR)));
    showCallFailureEvents(
        convertCallFailure(this.crossBlockchainControlContract.getCallFailureEvents(txR)));
    showCallResultEvents(
        convertCallResult(this.crossBlockchainControlContract.getCallResultEvents(txR)));
    showDumpEvents(this.convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));

    List<net.consensys.gpact.functioncall.gpact.CrosschainControl.RootEventResponse>
        rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    net.consensys.gpact.functioncall.gpact.CrosschainControl.RootEventResponse rootEventResponse =
        rootEventResponses.get(0);
    this.rootEventSuccess = rootEventResponse._success;

    return new Tuple<TransactionReceipt, byte[], Boolean>(
        txR, getEventData(txR, ROOT_EVENT_SIGNAUTRE_BYTES), false);
  }

  public CompletableFuture<TransactionReceipt> signallingAsyncPart1(
      SignedEvent rootEvent, List<SignedEvent> segEvents) throws Exception {
    List<BigInteger> bcIds = new ArrayList<>();
    List<String> cbcAddresses = new ArrayList<>();
    List<byte[]> eventFunctionSignatures = new ArrayList<>();
    List<byte[]> eventData = new ArrayList<>();
    List<byte[]> encodedSignatures = new ArrayList<>();

    bcIds.add(rootEvent.getBcId().asBigInt());
    cbcAddresses.add(rootEvent.getCbcContract());
    eventFunctionSignatures.add(rootEvent.getEventFunctionSignature());
    eventData.add(rootEvent.getEventData());
    encodedSignatures.add(rootEvent.getEncodedSignatures());
    for (SignedEvent segEvent : segEvents) {
      bcIds.add(segEvent.getBcId().asBigInt());
      cbcAddresses.add(segEvent.getCbcContract());
      eventFunctionSignatures.add(segEvent.getEventFunctionSignature());
      eventData.add(segEvent.getEventData());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    LOG.debug("Signalling Transaction on blockchain {}", this.blockchainId);
    return this.crossBlockchainControlContract
        .signalling(bcIds, cbcAddresses, eventFunctionSignatures, eventData, encodedSignatures)
        .sendAsync();
  }

  public void signallingAsyncPart2(TransactionReceipt txR) throws Exception {
    StatsHolder.logGas("Signalling Transaction", txR.getGasUsed());
    if (!txR.isStatusOK()) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(txR.getRevertReason()));
      throw new Exception("Signalling transaction failed");
    }

    List<net.consensys.gpact.functioncall.gpact.CrosschainControl.SignallingEventResponse>
        sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    net.consensys.gpact.functioncall.gpact.CrosschainControl.SignallingEventResponse
        sigEventResponse = sigEventResponses.get(0);
    LOG.debug("Signalling Event:");
    LOG.debug(" _rootBlockchainId: {}", sigEventResponse._rootBcId.toString(16));
    LOG.debug(
        " _crossBlockchainTransactionId: {}",
        sigEventResponse._crossBlockchainTransactionId.toString(16));

    // showDumpEvents(this.convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));
  }

  public String getCbcContractAddress() {
    return this.crossBlockchainControlContract.getContractAddress();
  }

  public boolean getRootEventSuccess() {
    return this.rootEventSuccess;
  }

  public static class BadCallEventResponse extends BaseEventResponse {
    public BigInteger _expectedBlockchainId;
    public BigInteger _actualBlockchainId;
    public String _expectedContract;
    public String _actualContract;
    public byte[] _expectedFunctionCall;
    public byte[] _actualFunctionCall;

    public BadCallEventResponse(
        BigInteger _expectedBlockchainId,
        BigInteger _actualBlockchainId,
        String _expectedContract,
        String _actualContract,
        byte[] _expectedFunctionCall,
        byte[] _actualFunctionCall) {
      this._expectedBlockchainId = _expectedBlockchainId;
      this._actualBlockchainId = _actualBlockchainId;
      this._expectedContract = _expectedContract;
      this._actualContract = _actualContract;
      this._expectedFunctionCall = _expectedFunctionCall;
      this._actualFunctionCall = _actualFunctionCall;
    }
  }

  public static class CallFailureEventResponse extends BaseEventResponse {
    public String _revertReason;

    public CallFailureEventResponse(String _revertReason) {
      this._revertReason = _revertReason;
    }
  }

  public static class CallResultEventResponse extends BaseEventResponse {
    public BigInteger blockchainId;
    public String contract;
    public byte[] functionCall;
    public byte[] result;

    public CallResultEventResponse(
        BigInteger blockchainId, String contract, byte[] functionCall, byte[] result) {
      this.blockchainId = blockchainId;
      this.contract = contract;
      this.functionCall = functionCall;
      this.result = result;
    }
  }

  public static class DumpEventResponse extends BaseEventResponse {
    public BigInteger _val1;
    public byte[] _val2;
    public String _val3;
    public byte[] _val4;

    public DumpEventResponse(BigInteger _val1, byte[] _val2, String _val3, byte[] _val4) {
      this._val1 = _val1;
      this._val2 = _val2;
      this._val3 = _val3;
      this._val4 = _val4;
    }
  }

  public static class NotEnoughCallsEventResponse extends BaseEventResponse {
    public BigInteger _expectedNumberOfCalls;
    public BigInteger _actualNumberOfCalls;

    public NotEnoughCallsEventResponse(
        BigInteger _expectedNumberOfCalls, BigInteger _actualNumberOfCalls) {
      this._expectedNumberOfCalls = _expectedNumberOfCalls;
      this._actualNumberOfCalls = _actualNumberOfCalls;
    }
  }

  public static class RootEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public Boolean _success;

    public RootEventResponse(BigInteger _crossBlockchainTransactionId, Boolean _success) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._success = _success;
    }
  }

  public static class SegmentEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public byte[] _hashOfCallGraph;
    public List<BigInteger> _callPath;
    public List<String> _lockedContracts;
    public Boolean _success;
    public byte[] _returnValue;

    public SegmentEventResponse(
        BigInteger _crossBlockchainTransactionId,
        byte[] _hashOfCallGraph,
        List<BigInteger> _callPath,
        List<String> _lockedContracts,
        Boolean _success,
        byte[] _returnValue) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._hashOfCallGraph = _hashOfCallGraph;
      this._callPath = _callPath;
      this._lockedContracts = _lockedContracts;
      this._success = _success;
      this._returnValue = _returnValue;
    }
  }

  public static class SignallingEventResponse extends BaseEventResponse {
    public BigInteger _rootBcId;
    public BigInteger _crossBlockchainTransactionId;

    public SignallingEventResponse(BigInteger _rootBcId, BigInteger _crossBlockchainTransactionId) {
      this._rootBcId = _rootBcId;
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
    }
  }

  public static class StartEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public String _caller;
    public BigInteger _timeout;
    public byte[] _callGraph;

    public StartEventResponse(
        BigInteger _crossBlockchainTransactionId,
        String _caller,
        BigInteger _timeout,
        byte[] _callGraph) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._caller = _caller;
      this._timeout = _timeout;
      this._callGraph = _callGraph;
    }
  }

  protected void showBadCallEvents(List<BadCallEventResponse> badCallEventRespons) {
    LOG.debug("Bad Call Events");
    if (badCallEventRespons.isEmpty()) {
      LOG.debug(" None");
    }
    for (BadCallEventResponse badCallEventResponse : badCallEventRespons) {
      LOG.debug(" Event:");
      LOG.debug(
          "   Expected Blockchain Id: 0x{}",
          badCallEventResponse._expectedBlockchainId.toString(16));
      LOG.debug(
          "   Actual Blockchain Id: 0x{}", badCallEventResponse._actualBlockchainId.toString(16));
      LOG.debug("   Expected Contract: {}", badCallEventResponse._expectedContract);
      LOG.debug("   Actual Contract: {}", badCallEventResponse._actualContract);
      LOG.debug(
          "   Expected Function Call: {}",
          new BigInteger(1, badCallEventResponse._expectedFunctionCall).toString(16));
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

  private List<BadCallEventResponse> convertBadCall(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.BadCallEventResponse>
          callEventResponses) {
    List<BadCallEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.BadCallEventResponse e :
        callEventResponses) {
      BadCallEventResponse event =
          new BadCallEventResponse(
              e._expectedBlockchainId,
              e._actualBlockchainId,
              e._expectedContract,
              e._actualContract,
              e._expectedFunctionCall,
              e._actualFunctionCall);
      result.add(event);
    }
    return result;
  }

  private List<CallFailureEventResponse> convertCallFailure(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.CallFailureEventResponse>
          callFailureEventResponses) {
    List<CallFailureEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.CallFailureEventResponse e :
        callFailureEventResponses) {
      CallFailureEventResponse event = new CallFailureEventResponse(e._revertReason);
      result.add(event);
    }
    return result;
  }

  private List<CallResultEventResponse> convertCallResult(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.CallResultEventResponse>
          callResultEventResponses) {
    List<CallResultEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.CallResultEventResponse e :
        callResultEventResponses) {
      CallResultEventResponse event =
          new CallResultEventResponse(e._blockchainId, e._contract, e._functionCall, e._result);
      result.add(event);
    }
    return result;
  }

  private List<DumpEventResponse> convertDump(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.DumpEventResponse>
          dumpEventResponses) {
    List<DumpEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.DumpEventResponse e :
        dumpEventResponses) {
      DumpEventResponse event = new DumpEventResponse(e._val1, e._val2, e._val3, e._val4);
      result.add(event);
    }
    return result;
  }

  private List<NotEnoughCallsEventResponse> convertNotEnoughCalls(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.NotEnoughCallsEventResponse>
          notEnoughCallsEventResponses) {
    List<NotEnoughCallsEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.NotEnoughCallsEventResponse e :
        notEnoughCallsEventResponses) {
      NotEnoughCallsEventResponse event =
          new NotEnoughCallsEventResponse(e._expectedNumberOfCalls, e._actualNumberOfCalls);
      result.add(event);
    }
    return result;
  }

  private List<RootEventResponse> convertRoot(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.RootEventResponse>
          rootEventResponses) {
    List<RootEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.RootEventResponse e :
        rootEventResponses) {
      RootEventResponse event = new RootEventResponse(e._crossBlockchainTransactionId, e._success);
      result.add(event);
    }
    return result;
  }

  private List<SegmentEventResponse> convertSegment(
      List<net.consensys.gpact.functioncall.gpact.CrosschainControl.SegmentEventResponse>
          segmentEventResponses) {
    List<SegmentEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.CrosschainControl.SegmentEventResponse e :
        segmentEventResponses) {
      // TODO The code below is a hack to handle the fact that currently Web3J returns a Uint256
      // object, but the type is BigInteger.
      // TODO this code will break when Web3J fixes their bug.
      List<BigInteger> callPathFixed = new ArrayList<>();
      for (Object partOfCallPath : e._callPath) {
        Uint256 hack = (Uint256) partOfCallPath;
        callPathFixed.add(hack.getValue());
      }
      // TODO The code below is a hack to handle the fact that currently Web3J returns an Address
      // object, but the type is BigInteger.
      // TODO this code will break when Web3J fixes their bug.
      List<String> lockedContractsFixed = new ArrayList<>();
      for (Object lockedContract : e._lockedContracts) {
        Address hack = (Address) lockedContract;
        lockedContractsFixed.add(hack.getValue());
      }
      SegmentEventResponse event =
          new SegmentEventResponse(
              e._crossBlockchainTransactionId,
              e._hashOfCallGraph,
              callPathFixed,
              lockedContractsFixed,
              e._success,
              e._returnValue);
      result.add(event);
    }
    return result;
  }
}
