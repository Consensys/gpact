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
package tech.pegasys.ltacfc.cbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.common.RevertReason;
import tech.pegasys.ltacfc.common.StatsHolder;
import tech.pegasys.ltacfc.common.Tuple;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.registrar.SigAlgorithmTypes;
import tech.pegasys.ltacfc.soliditywrappers.CbcSignedEvent;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CrossBlockchainControlSignedEvents extends AbstractCbc {
  private static final Logger LOG = LogManager.getLogger(CrossBlockchainControlSignedEvents.class);

  // TODO put this into a map for the current transaction id, so many transactions can be handled in parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;

  private CbcSignedEvent crossBlockchainControlContract;

  public CrossBlockchainControlSignedEvents(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts() throws Exception {
    super.deployContracts();
    this.crossBlockchainControlContract =
        CbcSignedEvent.deploy(this.web3j, this.tm, this.gasProvider, this.blockchainId, this.registrarContract.getContractAddress()).send();
    LOG.info(" Cross Blockchain Contract Contract: {}", this.crossBlockchainControlContract.getContractAddress());
  }



  public byte[] start(BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    LOG.info("Start Transaction on blockchain 0x{}", this.blockchainId.toString(16));
    StatsHolder.log("Start call now");
    TransactionReceipt txR = this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<CbcSignedEvent.StartEventResponse> startEvents = this.crossBlockchainControlContract.getStartEvents(txR);
    CbcSignedEvent.StartEventResponse startEvent = startEvents.get(0);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    // LOG.info("Start Event: {}", new BigInteger(getEventData(txR, AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return getEventData(txR, AbstractCbc.START_EVENT_SIGNATURE_BYTES);
  }




  public Tuple<byte[], Boolean, TransactionReceipt> segment(SignedEvent startEvent, List<SignedEvent> segEvents, List<BigInteger> callPath) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(startEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(startEvent.getEncodedSignatures());
    for (SignedEvent segEvent: segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    LOG.info("Call Path Len: {}", callPath.size());
    for (int i = 0; i < callPath.size(); i++) {
      LOG.info("Call Path[{}]: {}", i, callPath.get(i));
    }


    //RlpDumper.dump(RLP.input(Bytes.wrap(encodedSignatures.get(0))));
    TransactionReceipt txR;
    try {
      LOG.info("Segment Transaction on blockchain 0x{}", this.blockchainId.toString(16));
      txR = this.crossBlockchainControlContract.segment(encodedEvents, encodedSignatures, callPath).send();
      StatsHolder.logGas("Segment Transaction", txR.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    showSegmentEvents(convertSegment(this.crossBlockchainControlContract.getSegmentEvents(txR)));
    showBadCallEvents(convertBadCall(this.crossBlockchainControlContract.getBadCallEvents(txR)));
    showNotEnoughCallsEvents(convertNotEnoughCalls(this.crossBlockchainControlContract.getNotEnoughCallsEvents(txR)));
    showCallFailureEvents(convertCallFailure(this.crossBlockchainControlContract.getCallFailureEvents(txR)));
    showCallResultEvents(convertCallResult(this.crossBlockchainControlContract.getCallResultEvents(txR)));
    showDumpEvents(convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));

    List<CbcSignedEvent.SegmentEventResponse> segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    CbcSignedEvent.SegmentEventResponse segmentEventResponse = segmentEventResponses.get(0);

    return new Tuple<byte[], Boolean, TransactionReceipt>(
        getEventData(txR, AbstractCbc.SEGMENT_EVENT_SIGNATURE_BYTES),
        segmentEventResponse._lockedContracts.isEmpty(),
        txR);
  }


  public Tuple<byte[], TransactionReceipt, Boolean> root(SignedEvent startEvent, List<SignedEvent> segEvents) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(startEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(startEvent.getEncodedSignatures());
    for (SignedEvent segEvent: segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    long now = System.currentTimeMillis() / 1000;
    LOG.info(" Current time on this computer: {}; Transaction time-out: {}", now, this.crossBlockchainTransactionTimeout);
    if (this.crossBlockchainTransactionTimeout < now) {
      LOG.warn(" Cross-Blockchain transaction will fail as transaction has timed-out");
    }
    else if (this.crossBlockchainTransactionTimeout < (now - 10)) {
      LOG.warn(" Cross-Blockchain transaction might fail as transaction time-out is soon");
    }

    TransactionReceipt txR;
    try {
      LOG.info("Root Transaction on blockchain 0x{}", this.blockchainId.toString(16));
      txR = this.crossBlockchainControlContract.root(encodedEvents, encodedSignatures).send();
      StatsHolder.logGas("Root Transaction", txR.getGasUsed());
      if (!txR.isStatusOK()) {
        throw new Exception("Root transaction failed");
      }
    }
    catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    showRootEvents(convertRoot(this.crossBlockchainControlContract.getRootEvents(txR)));
    showBadCallEvents(convertBadCall(this.crossBlockchainControlContract.getBadCallEvents(txR)));
    showNotEnoughCallsEvents(convertNotEnoughCalls(this.crossBlockchainControlContract.getNotEnoughCallsEvents(txR)));
    showCallFailureEvents(convertCallFailure(this.crossBlockchainControlContract.getCallFailureEvents(txR)));
    showCallResultEvents(convertCallResult(this.crossBlockchainControlContract.getCallResultEvents(txR)));
    showDumpEvents(this.convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));

    List<CbcSignedEvent.RootEventResponse> rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    CbcSignedEvent.RootEventResponse rootEventResponse = rootEventResponses.get(0);
    this.rootEventSuccess = rootEventResponse._success;

    return new Tuple<byte[], TransactionReceipt, Boolean>(
        getEventData(txR, ROOT_EVENT_SIGNAUTRE_BYTES),
        txR, false);
  }

  public void signallingOldSerial(SignedEvent rootEvent, List<SignedEvent> segEvents) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(rootEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(rootEvent.getEncodedSignatures());
    for (SignedEvent segEvent: segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }


    LOG.info("Signalling Transaction on blockchain 0x{}", this.blockchainId.toString(16));
    TransactionReceipt txR = this.crossBlockchainControlContract.signalling(encodedEvents, encodedSignatures).send();
    StatsHolder.logGas("Signalling Transaction", txR.getGasUsed());
    if (!txR.isStatusOK()) {
      throw new Exception("Signalling transaction failed");
    }

    List<CbcSignedEvent.SignallingEventResponse> sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    CbcSignedEvent.SignallingEventResponse sigEventResponse = sigEventResponses.get(0);
    LOG.info("Signalling Event:");
    LOG.info(" _rootBlockchainId: {}", sigEventResponse._rootBcId.toString(16));
    LOG.info(" _crossBlockchainTransactionId: {}", sigEventResponse._crossBlockchainTransactionId.toString(16));

    showDumpEvents(this.convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));
  }

  public CompletableFuture<TransactionReceipt> signallingAsyncPart1(SignedEvent rootEvent, List<SignedEvent> segEvents) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(rootEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(rootEvent.getEncodedSignatures());
    for (SignedEvent segEvent : segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    LOG.info("Signalling Transaction on blockchain 0x{}", this.blockchainId.toString(16));
    return this.crossBlockchainControlContract.signalling(encodedEvents, encodedSignatures).sendAsync();
  }

  public void signallingAsyncPart2(TransactionReceipt txR) throws Exception {
    StatsHolder.logGas("Signalling Transaction", txR.getGasUsed());
    if (!txR.isStatusOK()) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(txR.getRevertReason()));
      throw new Exception("Signalling transaction failed");
    }

    List<CbcSignedEvent.SignallingEventResponse> sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    CbcSignedEvent.SignallingEventResponse sigEventResponse = sigEventResponses.get(0);
    LOG.info("Signalling Event:");
    LOG.info(" _rootBlockchainId: {}", sigEventResponse._rootBcId.toString(16));
    LOG.info(" _crossBlockchainTransactionId: {}", sigEventResponse._crossBlockchainTransactionId.toString(16));

    showDumpEvents(this.convertDump(this.crossBlockchainControlContract.getDumpEvents(txR)));
  }



  public String getCbcContractAddress() {
    return this.crossBlockchainControlContract.getContractAddress();
  }

  public boolean getRootEventSuccess() {
    return this.rootEventSuccess;
  }

  public byte[] getEventData(TransactionReceipt txR, Bytes eventSignatureToFind) throws Exception {
    List<Log> logs = txR.getLogs();
    String cbcAddress = getCbcContractAddress();
    for (Log log: logs) {
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


  private List<BadCallEventResponse> convertBadCall(List<CbcSignedEvent.BadCallEventResponse> callEventResponses) {
    List<BadCallEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.BadCallEventResponse e : callEventResponses) {
      BadCallEventResponse event = new BadCallEventResponse(e._expectedBlockchainId, e._actualBlockchainId,
          e._expectedContract, e._actualContract, e._expectedFunctionCall, e._actualFunctionCall);
      result.add(event);
    }
    return result;
  }

  private List<CallFailureEventResponse> convertCallFailure(List<CbcSignedEvent.CallFailureEventResponse> callFailureEventResponses) {
    List<CallFailureEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.CallFailureEventResponse e : callFailureEventResponses) {
      CallFailureEventResponse event = new CallFailureEventResponse(e._revertReason);
      result.add(event);
    }
    return result;
  }

  private List<CallResultEventResponse> convertCallResult(List<CbcSignedEvent.CallResultEventResponse> callResultEventResponses) {
    List<CallResultEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.CallResultEventResponse e : callResultEventResponses) {
      CallResultEventResponse event = new CallResultEventResponse(e._blockchainId, e._contract, e._functionCall, e._result);
      result.add(event);
    }
    return result;
  }

  private List<DumpEventResponse> convertDump(List<CbcSignedEvent.DumpEventResponse> dumpEventResponses) {
    List<DumpEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.DumpEventResponse e : dumpEventResponses) {
      DumpEventResponse event = new DumpEventResponse(e._val1, e._val2, e._val3, e._val4);
      result.add(event);
    }
    return result;
  }

  private List<NotEnoughCallsEventResponse> convertNotEnoughCalls(List<CbcSignedEvent.NotEnoughCallsEventResponse> notEnoughCallsEventResponses) {
    List<NotEnoughCallsEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.NotEnoughCallsEventResponse e : notEnoughCallsEventResponses) {
      NotEnoughCallsEventResponse event = new NotEnoughCallsEventResponse(e._expectedNumberOfCalls, e._actualNumberOfCalls);
      result.add(event);
    }
    return result;
  }

  private List<RootEventResponse> convertRoot(List<CbcSignedEvent.RootEventResponse> rootEventResponses) {
    List<RootEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.RootEventResponse e : rootEventResponses) {
      RootEventResponse event = new RootEventResponse(e._crossBlockchainTransactionId, e._success);
      result.add(event);
    }
    return result;
  }

  private List<SegmentEventResponse> convertSegment(List<CbcSignedEvent.SegmentEventResponse> segmentEventResponses) {
    List<SegmentEventResponse> result = new ArrayList<>();
    for (CbcSignedEvent.SegmentEventResponse e : segmentEventResponses) {
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
