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
package net.consensys.gpact.sfccbc;

import net.consensys.gpact.common.*;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.sfccbc.soliditywrappers.SimpleCrosschainControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.crypto.Hash;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public class SimpleCrossControlManager extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(SimpleCrossControlManager.class);

  public static byte[] CROSSCALL_EVENT_SIGNATURE =
          Hash.keccak256(Bytes.wrap("CrossCall(bytes32,uint256,address,uint256,address,bytes)".getBytes())).toArray();
  public static Bytes CROSSCALL_EVENT_SIGNATURE_BYTES = Bytes.wrap(CROSSCALL_EVENT_SIGNATURE);


  protected net.consensys.gpact.sfccbc.soliditywrappers.SimpleCrosschainControl crossBlockchainControlContract;

  // TODO put this into a map for the current transaction id, so many transactions can be handled in parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;


  protected SimpleCrossControlManager(Credentials credentials, BlockchainId bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
      super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  protected void deployContracts() throws Exception {
    this.crossBlockchainControlContract =
            net.consensys.gpact.sfccbc.soliditywrappers.SimpleCrosschainControl.deploy(this.web3j, this.tm, this.gasProvider,
                    this.blockchainId.asBigInt()).send();
    LOG.debug(" Simple Cross Blockchain Contract Contract: {}", this.crossBlockchainControlContract.getContractAddress());
  }

  public List<String> getContractAddresses() {
    List<String> addresses = new ArrayList<>();
    addresses.add(this.crossBlockchainControlContract.getContractAddress());
    return addresses;
  }


  public void loadContracts(List<String> addresses) {
    this.crossBlockchainControlContract =
            net.consensys.gpact.sfccbc.soliditywrappers.SimpleCrosschainControl.load(addresses.get(0), this.web3j, this.tm, this.gasProvider);
  }


  public void addBlockchain(BlockchainId bcId, String cbcContractAddress, String verifierContractAddress) throws Exception {
    TransactionReceipt txr = this.crossBlockchainControlContract.addRemoteCrosschainControl(bcId.asBigInt(), cbcContractAddress).send();
    assert(txr.isStatusOK());

    txr = this.crossBlockchainControlContract.addVerifier(bcId.asBigInt(), verifierContractAddress).send();
    assert(txr.isStatusOK());
  }


  public Tuple<TransactionReceipt, byte[], Boolean> start(String srcBcContract, byte[] functionCall) throws Exception {
    LOG.debug("Start Transaction on blockchain {}", this.blockchainId);
    StatsHolder.log("Start call now");
    TransactionReceipt txR = this.crossBlockchainControlContract.start(srcBcContract, functionCall).send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<SimpleCrosschainControl.CrossCallEventResponse> callEvents = this.crossBlockchainControlContract.getCrossCallEvents(txR);
    SimpleCrosschainControl.CrossCallEventResponse startEvent = callEvents.get(0);
    // LOG.debug("Start Event: {}", new BigInteger(getEventData(txR, AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return new Tuple<TransactionReceipt, byte[], Boolean>(
            txR,
            getEventData(txR, CROSSCALL_EVENT_SIGNATURE_BYTES),
            false);
  }

  public Tuple<TransactionReceipt, byte[], Boolean> crossCall(SignedEvent crossCallEvent) throws Exception {
    //RlpDumper.dump(RLP.input(Bytes.wrap(encodedSignatures.get(0))));
    TransactionReceipt txR;
    try {
      LOG.debug("CrossCall Transaction on blockchain {}", this.blockchainId);
      txR = this.crossBlockchainControlContract.crossCallHandler(
              crossCallEvent.getBcId().asBigInt(), crossCallEvent.getCbcContract(),
              crossCallEvent.getEventData(), crossCallEvent.getEncodedSignatures()).send();
      StatsHolder.logGas("CrossCall Transaction", txR.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    if (!txR.isStatusOK()) {
      throw new Exception("CrossCall transaction failed");
    }

    // TODO showCallFailureEvents(convertCallFailure(this.crossBlockchainControlContract.getCallFailureEvents(txR)));

    return new Tuple<TransactionReceipt, byte[], Boolean>(
            txR,
            null,
            false);
  }

  public String getCbcContractAddress() {
    return this.crossBlockchainControlContract.getContractAddress();
  }

  public boolean getRootEventSuccess() {
    return this.rootEventSuccess;
  }



  // TODO this is common code between gpact and this.
  // TODO put common code somewhere
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


}
