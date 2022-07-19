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
package net.consensys.gpact.functioncall.gpact.v2;

import static net.consensys.gpact.common.crypto.Hash.keccak256;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.functioncall.gpact.AbstractGpactCrossControlManager;
import net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class GpactV2CrossControlManager extends AbstractGpactCrossControlManager {
  private static final Logger LOG = LogManager.getLogger(GpactV2CrossControlManager.class);

  public static byte[] START_EVENT_SIGNATURE =
      keccak256(Bytes.wrap("Start(uint256,address,uint256,byte32)".getBytes())).toArray();
  public static Bytes START_EVENT_SIGNATURE_BYTES = Bytes.wrap(START_EVENT_SIGNATURE);
  public static byte[] SEGMENT_EVENT_SIGNATURE =
      keccak256(Bytes.wrap("Segment(uint256,bytes32,uint256[],address[],bool,bytes)".getBytes()))
          .toArray();
  public static Bytes SEGMENT_EVENT_SIGNATURE_BYTES = Bytes.wrap(SEGMENT_EVENT_SIGNATURE);
  public static byte[] ROOT_EVENT_SIGNATURE =
      keccak256(Bytes.wrap("Root(uint256,bool)".getBytes())).toArray();
  public static Bytes ROOT_EVENT_SIGNAUTRE_BYTES = Bytes.wrap(ROOT_EVENT_SIGNATURE);

  protected net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl
      crossBlockchainControlContract;

  CallExecutionTree callExecutionTree;
  byte[] callExecutionTreeV2Encoded;

  // TODO put this into a map for the current transaction id, so many transactions can be handled in
  // parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;

  public GpactV2CrossControlManager(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContract() throws Exception {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.deploy(
                this.web3j, this.tm, this.gasProvider, this.blockchainId.asBigInt())
            .send();
    LOG.debug(
        " Cross Blockchain Contract Contract: {}",
        this.crossBlockchainControlContract.getContractAddress());
  }

  public void loadContract(String cbcAddress) {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.load(
            cbcAddress, this.web3j, this.tm, this.gasProvider);
  }

  public void addRemoteBlockchain(
      BlockchainId bcId, String cbcContractAddress, String verifierContractAddress)
      throws Exception {
    TransactionReceipt txr =
        this.crossBlockchainControlContract
            .addRemoteCrosschainControl(bcId.asBigInt(), cbcContractAddress)
            .send();
    if (!txr.isStatusOK()) {
      String revertReason = RevertReason.decodeRevertReason(txr.getRevertReason());
      LOG.warn(
          "GPACTv2 CrosschainControl addRemoteCrosschainControl failed. My Bc: {}, Bc: {}, Cbc Address: {}, Revert Reason: {}",
          this.blockchainId.toDecimalString(),
          bcId.toDecimalString(),
          cbcContractAddress,
          revertReason);
      throw new Exception("GPACTv2 CrosschainControl addVerify failed");
    }

    txr =
        this.crossBlockchainControlContract
            .addVerifier(bcId.asBigInt(), verifierContractAddress)
            .send();
    if (!txr.isStatusOK()) {
      String revertReason = RevertReason.decodeRevertReason(txr.getRevertReason());
      LOG.warn(
          "GPACTv2 CrosschainControl addVerify failed. My Bc: {}, Bc: {}, Verifier Address: {}, Revert Reason: {}",
          this.blockchainId.toDecimalString(),
          bcId.toDecimalString(),
          verifierContractAddress,
          revertReason);
      throw new Exception("GPACTv2 CrosschainControl addVerify failed");
    }
  }

  public Tuple<TransactionReceipt, byte[], Boolean> start(
      final BigInteger transactionId,
      final BigInteger timeout,
      final CallExecutionTree callExecutionTree)
      throws Exception {
    LOG.debug("Start Transaction on blockchain {}", this.blockchainId);

    this.callExecutionTreeV2Encoded = callExecutionTree.encode(CallExecutionTree.ENCODING_V2);
    byte[] callTreeHash = keccak256(Bytes.wrap(callExecutionTreeV2Encoded)).toArray();
    this.callExecutionTree = callExecutionTree;

    StatsHolder.log("Start call now");
    TransactionReceipt txR =
        this.crossBlockchainControlContract.start(transactionId, timeout, callTreeHash).send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.StartEventResponse>
        startEvents = this.crossBlockchainControlContract.getStartEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.StartEventResponse startEvent =
        startEvents.get(0);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    // LOG.debug("Start Event: {}", new BigInteger(getEventData(txR,
    // AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return new Tuple<>(
        txR, getEventData(txR, GpactV2CrossControlManager.START_EVENT_SIGNATURE_BYTES), false);
  }

  public Tuple<TransactionReceipt, byte[], Boolean> segment(
      final SignedEvent startEvent, final SignedEvent[] segEvents, final List<BigInteger> callPath)
      throws Exception {
    List<GpactV2CrosschainControl.EventInfo> events = new ArrayList<>();
    events.add(
        new GpactV2CrosschainControl.EventInfo(
            startEvent.getBcId().asBigInt(),
            startEvent.getCbcContract(),
            startEvent.getEventFunctionSignature(),
            startEvent.getEventData(),
            startEvent.getEncodedSignatures()));
    for (SignedEvent segEvent : segEvents) {
      events.add(
          new GpactV2CrosschainControl.EventInfo(
              segEvent.getBcId().asBigInt(),
              segEvent.getCbcContract(),
              segEvent.getEventFunctionSignature(),
              segEvent.getEventData(),
              segEvent.getEncodedSignatures()));
    }

    LOG.debug("Call Path Len: {}", callPath.size());
    for (int i = 0; i < callPath.size(); i++) {
      LOG.debug("Call Path[{}]: {}", i, callPath.get(i));
    }

    CallExecutionTree functionCall = this.callExecutionTree.fetchFunctionCall(callPath);
    String targetContract = functionCall.getContractAddress();
    byte[] targetFunctionCallData = functionCall.getFunctionCallDataAsBytes();

    // RlpDumper.dump(RLP.input(Bytes.wrap(encodedSignatures.get(0))));
    TransactionReceipt txR;
    try {
      LOG.debug("Segment Transaction on blockchain {}", this.blockchainId);
      txR =
          this.crossBlockchainControlContract
              .segment(
                  events,
                  callPath,
                  this.callExecutionTreeV2Encoded,
                  targetContract,
                  targetFunctionCallData)
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

    List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SegmentEventResponse>
        segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SegmentEventResponse
        segmentEventResponse = segmentEventResponses.get(0);

    return new Tuple<>(
        txR,
        getEventData(txR, GpactV2CrossControlManager.SEGMENT_EVENT_SIGNATURE_BYTES),
        segmentEventResponse._lockedContracts.isEmpty());
  }

  public Tuple<TransactionReceipt, byte[], Boolean> root(
      SignedEvent startEvent, SignedEvent[] segEvents) throws Exception {
    List<GpactV2CrosschainControl.EventInfo> events = new ArrayList<>();
    events.add(
        new GpactV2CrosschainControl.EventInfo(
            startEvent.getBcId().asBigInt(),
            startEvent.getCbcContract(),
            startEvent.getEventFunctionSignature(),
            startEvent.getEventData(),
            startEvent.getEncodedSignatures()));
    for (SignedEvent segEvent : segEvents) {
      events.add(
          new GpactV2CrosschainControl.EventInfo(
              segEvent.getBcId().asBigInt(),
              segEvent.getCbcContract(),
              segEvent.getEventFunctionSignature(),
              segEvent.getEventData(),
              segEvent.getEncodedSignatures()));
    }

    long now = System.currentTimeMillis() / 1000;
    LOG.debug(
        " Current time on this computer: {}; Transaction time-out: {}",
        now,
        this.crossBlockchainTransactionTimeout);

    if (this.crossBlockchainTransactionTimeout < (now - 10)) {
      LOG.warn(" Cross-Blockchain transaction might fail as transaction time-out is soon");
    } else if (this.crossBlockchainTransactionTimeout < now) {
      LOG.warn(" Cross-Blockchain transaction will fail as transaction has timed-out");
    }

    CallExecutionTree functionCall =
        this.callExecutionTree.fetchFunctionCall(CrosschainCallResult.ROOT_CALL);
    String targetContract = functionCall.getContractAddress();
    byte[] targetFunctionCallData = functionCall.getFunctionCallDataAsBytes();

    TransactionReceipt txR;
    try {
      LOG.debug("Root Transaction on blockchain {}", this.blockchainId);
      txR =
          this.crossBlockchainControlContract
              .root(events, this.callExecutionTreeV2Encoded, targetContract, targetFunctionCallData)
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

    List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.RootEventResponse>
        rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.RootEventResponse
        rootEventResponse = rootEventResponses.get(0);
    this.rootEventSuccess = rootEventResponse._success;

    return new Tuple<>(
        txR, getEventData(txR, ROOT_EVENT_SIGNAUTRE_BYTES), false);
  }

  public CompletableFuture<TransactionReceipt> signallingAsyncPart1(
      SignedEvent rootEvent, List<SignedEvent> segEvents) {
    List<GpactV2CrosschainControl.EventInfo> events = new ArrayList<>();
    events.add(
        new GpactV2CrosschainControl.EventInfo(
            rootEvent.getBcId().asBigInt(),
            rootEvent.getCbcContract(),
            rootEvent.getEventFunctionSignature(),
            rootEvent.getEventData(),
            rootEvent.getEncodedSignatures()));
    for (SignedEvent segEvent : segEvents) {
      events.add(
          new GpactV2CrosschainControl.EventInfo(
              segEvent.getBcId().asBigInt(),
              segEvent.getCbcContract(),
              segEvent.getEventFunctionSignature(),
              segEvent.getEventData(),
              segEvent.getEncodedSignatures()));
    }

    LOG.debug("Signalling Transaction on blockchain {}", this.blockchainId);
    return this.crossBlockchainControlContract.signalling(events).sendAsync();
  }

  public void signallingAsyncPart2(TransactionReceipt txR) throws Exception {
    StatsHolder.logGas("Signalling Transaction", txR.getGasUsed());
    if (!txR.isStatusOK()) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(txR.getRevertReason()));
      throw new Exception("Signalling transaction failed");
    }

    List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SignallingEventResponse>
        sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SignallingEventResponse
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

  private List<BadCallEventResponse> convertBadCall(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.BadCallEventResponse>
          callEventResponses) {
    List<BadCallEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.BadCallEventResponse e :
        callEventResponses) {
      BadCallEventResponse event =
          new BadCallEventResponse(
              e._expectedFunctionHash,
              e._actualBlockchainId,
              e._actualContract,
              e._actualFunctionCall);
      result.add(event);
    }
    return result;
  }

  private List<CallFailureEventResponse> convertCallFailure(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.CallFailureEventResponse>
          callFailureEventResponses) {
    List<CallFailureEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.CallFailureEventResponse
        e : callFailureEventResponses) {
      CallFailureEventResponse event = new CallFailureEventResponse(e._revertReason);
      result.add(event);
    }
    return result;
  }

  private List<CallResultEventResponse> convertCallResult(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.CallResultEventResponse>
          callResultEventResponses) {
    List<CallResultEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.CallResultEventResponse e :
        callResultEventResponses) {
      CallResultEventResponse event =
          new CallResultEventResponse(e._blockchainId, e._contract, e._functionCall, e._result);
      result.add(event);
    }
    return result;
  }

  private List<DumpEventResponse> convertDump(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.DumpEventResponse>
          dumpEventResponses) {
    List<DumpEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.DumpEventResponse e :
        dumpEventResponses) {
      DumpEventResponse event = new DumpEventResponse(e._val1, e._val2, e._val3, e._val4);
      result.add(event);
    }
    return result;
  }

  private List<NotEnoughCallsEventResponse> convertNotEnoughCalls(
      List<
              net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl
                  .NotEnoughCallsEventResponse>
          notEnoughCallsEventResponses) {
    List<NotEnoughCallsEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.NotEnoughCallsEventResponse
        e : notEnoughCallsEventResponses) {
      NotEnoughCallsEventResponse event =
          new NotEnoughCallsEventResponse(e._expectedNumberOfCalls, e._actualNumberOfCalls);
      result.add(event);
    }
    return result;
  }

  private List<RootEventResponse> convertRoot(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.RootEventResponse>
          rootEventResponses) {
    List<RootEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.RootEventResponse e :
        rootEventResponses) {
      RootEventResponse event = new RootEventResponse(e._crossBlockchainTransactionId, e._success);
      result.add(event);
    }
    return result;
  }

  private List<SegmentEventResponse> convertSegment(
      List<net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SegmentEventResponse>
          segmentEventResponses) {
    List<SegmentEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactV2CrosschainControl.SegmentEventResponse e :
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
