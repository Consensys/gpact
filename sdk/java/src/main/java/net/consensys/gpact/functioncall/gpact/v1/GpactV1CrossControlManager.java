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
package net.consensys.gpact.functioncall.gpact.v1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.crypto.Hash;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.gpact.AbstractGpactCrossControlManager;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

/**
 * Deploy and load the Crosschain Control Contract plus call functions in the contract.
 *
 * <p>NOTE: Beyond state required to call the contract, this class should not hold any state.
 */
public class GpactV1CrossControlManager extends AbstractGpactCrossControlManager {
  private static final Logger LOG = LogManager.getLogger(GpactV1CrossControlManager.class);

  private static final byte[] START_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("Start(uint256,address,uint256,bytes)".getBytes())).toArray();
  private static final Bytes START_EVENT_SIGNATURE_BYTES = Bytes.wrap(START_EVENT_SIGNATURE);

  protected net.consensys.gpact.functioncall.gpact.GpactCrosschainControl
      crossBlockchainControlContract;

  // TODO the executor should hold this and pass it in to the root call.
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;

  // TODO: refactor this such that it is returned by the root call. The executor should hold this
  // value.
  private boolean rootEventSuccess;

  public GpactV1CrossControlManager(final Credentials credentials, final BlockchainConfig bcConfig)
      throws IOException {
    super(credentials, bcConfig);
  }

  public void deployContract() throws Exception {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.deploy(
                this.web3j, this.tm, this.gasProvider, this.blockchainId.asBigInt())
            .send();
    LOG.debug(
        " Cross Blockchain Contract Contract: {}",
        this.crossBlockchainControlContract.getContractAddress());
  }

  public void loadContract(String cbcAddress) {
    this.crossBlockchainControlContract =
        net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.load(
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
          "CrosschainControl addRemoteCrosschainControl failed. My Bc: {}, Bc: {}, Cbc Address: {}, Revert Reason: {}",
          this.blockchainId.toDecimalString(),
          bcId.toDecimalString(),
          cbcContractAddress,
          revertReason);
      throw new Exception("CrosschainControl addVerify failed");
    }

    txr =
        this.crossBlockchainControlContract
            .addVerifier(bcId.asBigInt(), verifierContractAddress)
            .send();
    if (!txr.isStatusOK()) {
      String revertReason = RevertReason.decodeRevertReason(txr.getRevertReason());
      LOG.warn(
          "CrosschainControl addVerify failed. My Bc: {}, Bc: {}, Verifier Address: {}, Revert Reason: {}",
          this.blockchainId.toDecimalString(),
          bcId.toDecimalString(),
          verifierContractAddress,
          revertReason);
      throw new Exception("CrosschainControl addVerify failed");
    }
  }

  public Tuple<TransactionReceipt, byte[], Boolean> start(
      final CallExecutionTree callExecutionTree,
      final BigInteger transactionId,
      final BigInteger timeout)
      throws Exception {
    LOG.debug("Start Transaction on blockchain {}", this.blockchainId);
    StatsHolder.log("Start call now");
    TransactionReceipt txR =
        this.crossBlockchainControlContract
            .start(transactionId, timeout, callExecutionTree.encode(CallExecutionTree.ENCODING_V1))
            .send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.StartEventResponse>
        startEvents = this.crossBlockchainControlContract.getStartEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.StartEventResponse startEvent =
        startEvents.get(0);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    // LOG.debug("Start Event: {}", new BigInteger(getEventData(txR,
    // AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return new Tuple<>(
        txR, getEventData(txR, GpactV1CrossControlManager.START_EVENT_SIGNATURE_BYTES), false);
  }

  public Tuple<TransactionReceipt, byte[], Boolean> segment(
      final CallExecutionTree notUsedCallExecutionTree,
      final SignedEvent startEvent,
      final SignedEvent[] segEvents,
      final List<BigInteger> callPath)
      throws Exception {
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

    List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SegmentEventResponse>
        segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SegmentEventResponse
        segmentEventResponse = segmentEventResponses.get(0);

    return new Tuple<>(
        txR,
        getEventData(txR, GpactV1CrossControlManager.SEGMENT_EVENT_SIGNATURE_BYTES),
        segmentEventResponse._lockedContracts.isEmpty());
  }

  public Tuple<TransactionReceipt, byte[], Boolean> root(
      final CallExecutionTree notUsedCallExecutionTree,
      final SignedEvent startEvent,
      final SignedEvent[] segEvents)
      throws Exception {
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

    if (this.crossBlockchainTransactionTimeout < (now - 10)) {
      LOG.warn(" Cross-Blockchain transaction might fail as transaction time-out is soon");
    } else if (this.crossBlockchainTransactionTimeout < now) {
      LOG.warn(" Cross-Blockchain transaction will fail as transaction has timed-out");
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

    List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.RootEventResponse>
        rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.RootEventResponse
        rootEventResponse = rootEventResponses.get(0);
    this.rootEventSuccess = rootEventResponse._success;

    return new Tuple<>(txR, getEventData(txR, ROOT_EVENT_SIGNAUTRE_BYTES), false);
  }

  public CompletableFuture<TransactionReceipt> signallingAsyncPart1(
      SignedEvent rootEvent, List<SignedEvent> segEvents) {
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

    List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SignallingEventResponse>
        sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SignallingEventResponse
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
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.BadCallEventResponse>
          callEventResponses) {
    List<BadCallEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.BadCallEventResponse e :
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
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.CallFailureEventResponse>
          callFailureEventResponses) {
    List<CallFailureEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.CallFailureEventResponse e :
        callFailureEventResponses) {
      CallFailureEventResponse event = new CallFailureEventResponse(e._revertReason);
      result.add(event);
    }
    return result;
  }

  private List<CallResultEventResponse> convertCallResult(
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.CallResultEventResponse>
          callResultEventResponses) {
    List<CallResultEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.CallResultEventResponse e :
        callResultEventResponses) {
      CallResultEventResponse event =
          new CallResultEventResponse(e._blockchainId, e._contract, e._functionCall, e._result);
      result.add(event);
    }
    return result;
  }

  private List<DumpEventResponse> convertDump(
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.DumpEventResponse>
          dumpEventResponses) {
    List<DumpEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.DumpEventResponse e :
        dumpEventResponses) {
      DumpEventResponse event = new DumpEventResponse(e._val1, e._val2, e._val3, e._val4);
      result.add(event);
    }
    return result;
  }

  private List<NotEnoughCallsEventResponse> convertNotEnoughCalls(
      List<
              net.consensys.gpact.functioncall.gpact.GpactCrosschainControl
                  .NotEnoughCallsEventResponse>
          notEnoughCallsEventResponses) {
    List<NotEnoughCallsEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.NotEnoughCallsEventResponse
        e : notEnoughCallsEventResponses) {
      NotEnoughCallsEventResponse event =
          new NotEnoughCallsEventResponse(e._expectedNumberOfCalls, e._actualNumberOfCalls);
      result.add(event);
    }
    return result;
  }

  private List<RootEventResponse> convertRoot(
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.RootEventResponse>
          rootEventResponses) {
    List<RootEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.RootEventResponse e :
        rootEventResponses) {
      RootEventResponse event = new RootEventResponse(e._crossBlockchainTransactionId, e._success);
      result.add(event);
    }
    return result;
  }

  private List<SegmentEventResponse> convertSegment(
      List<net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SegmentEventResponse>
          segmentEventResponses) {
    List<SegmentEventResponse> result = new ArrayList<>();
    for (net.consensys.gpact.functioncall.gpact.GpactCrosschainControl.SegmentEventResponse e :
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

  public byte[] getStartEventSignature() {
    return START_EVENT_SIGNATURE;
  }
}
