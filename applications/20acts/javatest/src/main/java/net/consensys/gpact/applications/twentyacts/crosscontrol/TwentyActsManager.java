/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.applications.twentyacts.crosscontrol;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.crypto.Hash;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class TwentyActsManager extends AbstractBlockchain implements CrossControlManager {
  private static final Logger LOG = LogManager.getLogger(TwentyActsManager.class);

  public static byte[] PREPARE_ON_TARGET_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("PrepareOnTarget(bytes32)".getBytes())).toArray();
  public static Bytes PREPARE_ON_TARGET_EVENT_SIGNATURE_BYTES =
      Bytes.wrap(PREPARE_ON_TARGET_EVENT_SIGNATURE);

  public static byte[] PREPARE_ON_SOURCE_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("PrepareOnSource(bytes32,bool,uint256,string)".getBytes()))
          .toArray();
  public static Bytes PREPARE_ON_SOURCE_EVENT_SIGNATURE_BYTES =
      Bytes.wrap(PREPARE_ON_SOURCE_EVENT_SIGNATURE);

  public static byte[] FINALIZE_ON_TARGET_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("FinalizeOnTarget(bytes32)".getBytes())).toArray();
  public static Bytes FINALIZE_ON_TARGET_EVENT_SIGNATURE_BYTES =
      Bytes.wrap(FINALIZE_ON_TARGET_EVENT_SIGNATURE);

  public static byte[] FINALIZE_ON_SOURCE_EVENT_SIGNATURE =
      Hash.keccak256(Bytes.wrap("FinalizeOnSource(bytes32)".getBytes())).toArray();
  public static Bytes FINALIZE_ON_SOURCE_EVENT_SIGNATURE_BYTES =
      Bytes.wrap(FINALIZE_ON_SOURCE_EVENT_SIGNATURE);

  public static final int TXSTATE_NOT_USED = 0;
  public static final int TXSTATE_IN_PROGRESS = 1;
  public static final int TXSTATE_COMPLETED_FAIL = 2;
  public static final int TXSTATE_COMPLETED_SUCCESS = 3;

  private TwentyActs twentyActs;

  public TwentyActsManager(Credentials credentials, BlockchainConfig bcConfig) throws IOException {
    super(credentials, bcConfig);
  }

  public void deploy20ActsContract(BigInteger withdrawWaitPeriod, String infrastructureAccount)
      throws Exception {
    this.twentyActs =
        TwentyActs.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                this.blockchainId.asBigInt(),
                withdrawWaitPeriod,
                infrastructureAccount)
            .send();
    LOG.info(
        "20ACTS deployed to {} on blockchain {}",
        this.twentyActs.getContractAddress(),
        this.blockchainId);
  }

  protected void deployCbcContract() throws Exception {
    // When deploying without parameters, set the wait time to 1000 years
    // and the infrastructure account to the deployer.
    BigInteger withdrawWaitPeriodInSeconds = BigInteger.valueOf((long) 3600 * 24 * 365 * 1000);
    String infrastructure = this.credentials.getAddress();
    deploy20ActsContract(withdrawWaitPeriodInSeconds, infrastructure);
  }

  public void loadCbcContract(String address) {
    this.twentyActs = TwentyActs.load(address, this.web3j, this.tm, this.gasProvider);
  }

  public void addRemoteBlockchain(
      BlockchainId remoteBcId, String remote20ActsAddress, String verifierAddress)
      throws Exception {
    TransactionReceipt txr =
        this.twentyActs
            .addRemoteCrosschainControl(remoteBcId.asBigInt(), remote20ActsAddress)
            .send();
    assert (txr.isStatusOK());

    txr = this.twentyActs.addVerifier(remoteBcId.asBigInt(), verifierAddress).send();
    assert (txr.isStatusOK());
  }

  public void addRemoteErc20(String thisBcErc20, BlockchainId otherBc, String otherBcErc20)
      throws Exception {
    this.twentyActs.setErc20Mapping(thisBcErc20, otherBc.asBigInt(), otherBcErc20).send();
  }

  public void setWithdrawalTime(BigInteger withdrawalWaitTime) throws Exception {
    this.twentyActs.setWithdrawalWaitPeriod(withdrawalWaitTime).send();
  }

  public void setInfBenficiary(String infAddress) throws Exception {
    this.twentyActs.setInfrastructureAccount(infAddress).send();
  }

  public void lpDeposit(String erc20, BigInteger amount) throws Exception {
    TransactionReceipt txR;
    try {
      txR = this.twentyActs.deposit(erc20, amount).send();
      StatsHolder.logGas("Deposit", txR.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(
          " Revert Reason: {}",
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    if (!txR.isStatusOK()) {
      throw new Exception("Deposit transaction failed");
    }
  }

  public Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnTargetEventResponse> prepareOnTarget(
      TwentyActs.TxInfo txInfo) throws Exception {

    LOG.debug("Prepare On Target {}", this.blockchainId);

    TransactionReceipt txR;
    try {
      txR = this.twentyActs.prepareOnTarget(txInfo).send();
      StatsHolder.logGas("PrepareOnTarget", txR.getGasUsed());
    } catch (TransactionException ex) {
      txR = ex.getTransactionReceipt().get();
      String revertReason = RevertReason.decodeRevertReason(txR.getRevertReason());
      LOG.error(" Revert Reason1: {}", revertReason);
      return new Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnTargetEventResponse>(
          txR, null, null);
    }
    TwentyActs.PrepareOnTargetEventResponse prepareOnTargetEvent = null;
    if (txR.isStatusOK()) {
      List<TwentyActs.PrepareOnTargetEventResponse> prepareOnTargetEvents =
          this.twentyActs.getPrepareOnTargetEvents(txR);
      prepareOnTargetEvent = prepareOnTargetEvents.get(0);
      dumpPrepareOnTargetEvent(prepareOnTargetEvent);
    }
    return new Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnTargetEventResponse>(
        txR, getEventData(txR, PREPARE_ON_TARGET_EVENT_SIGNATURE_BYTES), prepareOnTargetEvent);
  }

  public Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnSourceEventResponse> prepareOnSource(
      TwentyActs.TxInfo txInfo, byte[] prepareOnTargetEvent, byte[] signatureOrProof)
      throws Exception {

    LOG.debug("Prepare On Source {}", this.blockchainId);

    TransactionReceipt txR;
    try {
      txR =
          this.twentyActs
              .prepareOnSource(txInfo, prepareOnTargetEvent, signatureOrProof, new byte[] {})
              .send();
      StatsHolder.logGas("PrepareOnSource", txR.getGasUsed());
    } catch (TransactionException ex) {
      txR = ex.getTransactionReceipt().get();
      String revertReason = RevertReason.decodeRevertReason(txR.getRevertReason());
      LOG.error(" Revert Reason1: {}", revertReason);
      return new Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnSourceEventResponse>(
          txR, null, null);
    }
    TwentyActs.PrepareOnSourceEventResponse prepareOnSourceEvent = null;
    if (txR.isStatusOK()) {
      List<TwentyActs.PrepareOnSourceEventResponse> prepareOnSourceEvents =
          this.twentyActs.getPrepareOnSourceEvents(txR);
      prepareOnSourceEvent = prepareOnSourceEvents.get(0);
      dumpPrepareOnSourceEvent(prepareOnSourceEvent);
    }
    return new Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnSourceEventResponse>(
        txR, getEventData(txR, PREPARE_ON_SOURCE_EVENT_SIGNATURE_BYTES), prepareOnSourceEvent);
  }

  public Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnTargetEventResponse>
      finalizeOnTarget(
          TwentyActs.TxInfo txInfo, byte[] prepareOnSourceEvent, byte[] signatureOrProof)
          throws Exception {

    LOG.debug("Finalize on Target {}", this.blockchainId);

    TransactionReceipt txR;
    try {
      txR = this.twentyActs.finalizeOnTarget(txInfo, prepareOnSourceEvent, signatureOrProof).send();
      StatsHolder.logGas("FinalizeOnTarget", txR.getGasUsed());
    } catch (TransactionException ex) {
      txR = ex.getTransactionReceipt().get();
      String revertReason = RevertReason.decodeRevertReason(txR.getRevertReason());
      LOG.error(" Revert Reason1: {}", revertReason);
      return new Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnTargetEventResponse>(
          txR, null, null);
    }

    TwentyActs.FinalizeOnTargetEventResponse finalizeOnTargetEvent = null;
    if (txR.isStatusOK()) {
      List<TwentyActs.FinalizeOnTargetEventResponse> finalizeOnTargetEvents =
          this.twentyActs.getFinalizeOnTargetEvents(txR);
      finalizeOnTargetEvent = finalizeOnTargetEvents.get(0);
      dumpFinalizeOnTargetEvent(finalizeOnTargetEvent);
    }
    return new Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnTargetEventResponse>(
        txR, getEventData(txR, FINALIZE_ON_TARGET_EVENT_SIGNATURE_BYTES), finalizeOnTargetEvent);
  }

  public Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnSourceEventResponse>
      finalizeOnSource(
          TwentyActs.TxInfo txInfo, byte[] finalizeOnTargetEvent, byte[] signatureOrProof)
          throws Exception {

    LOG.debug("Finalize on Source {}", this.blockchainId);

    TransactionReceipt txR;
    try {
      txR =
          this.twentyActs.finalizeOnSource(txInfo, finalizeOnTargetEvent, signatureOrProof).send();
      StatsHolder.logGas("FinalizeOnSource", txR.getGasUsed());
    } catch (TransactionException ex) {
      txR = ex.getTransactionReceipt().get();
      String revertReason = RevertReason.decodeRevertReason(txR.getRevertReason());
      LOG.error(" Revert Reason1: {}", revertReason);
      return new Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnSourceEventResponse>(
          txR, null, null);
    }

    TwentyActs.FinalizeOnSourceEventResponse finalizeOnSourceEvent = null;
    if (txR.isStatusOK()) {
      List<TwentyActs.FinalizeOnSourceEventResponse> finalizeOnSourceEvents =
          this.twentyActs.getFinalizeOnSourceEvents(txR);
      finalizeOnSourceEvent = finalizeOnSourceEvents.get(0);
      dumpFinalizeOnSourceEvent(finalizeOnSourceEvent);
    }
    return new Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnSourceEventResponse>(
        txR, getEventData(txR, FINALIZE_ON_SOURCE_EVENT_SIGNATURE_BYTES), finalizeOnSourceEvent);
  }

  public TwentyActsManager forUser(Credentials user) throws IOException {
    TwentyActsManager bc = new TwentyActsManager(user, this.blockchainConfig);
    bc.loadCbcContract(this.twentyActs.getContractAddress());
    return bc;
  }

  public String getCbcContractAddress() {
    return this.twentyActs.getContractAddress();
  }

  public BigInteger getTransactionState(byte[] txInfoHash) throws Exception {
    return this.twentyActs.txState(txInfoHash).send();
  }

  public Tuple<BigInteger, BigInteger, BigInteger> getHoldings(String account, String erc20)
      throws Exception {
    BigInteger deposits = this.twentyActs.deposits(account, erc20).send();
    BigInteger allocation = this.twentyActs.allocated(account, erc20).send();
    BigInteger withdrawals = this.twentyActs.withdrawals(account, erc20).send();

    return new Tuple<BigInteger, BigInteger, BigInteger>(deposits, allocation, withdrawals);
  }

  public void showLiquidityProviderHoldings(String liquidityProvider, String erc20)
      throws Exception {
    LOG.info(
        "Liquidity Provider {}: Deposits:    {} ",
        liquidityProvider,
        this.twentyActs.deposits(liquidityProvider, erc20).send());
    LOG.info(
        "Liquidity Provider {}: Allocation:  {} ",
        liquidityProvider,
        this.twentyActs.allocated(liquidityProvider, erc20).send());
    LOG.info(
        "Liquidity Provider {}: Withdrawals: {} ",
        liquidityProvider,
        this.twentyActs.withdrawals(liquidityProvider, erc20).send());
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

  private void dumpPrepareOnTargetEvent(TwentyActs.PrepareOnTargetEventResponse event) {
    LOG.debug(" Prepare On Target Event:");
    LOG.debug("   Tx Info Digest: 0x{}", new BigInteger(1, event._txInfoDigest).toString(16));
  }

  private void dumpPrepareOnSourceEvent(TwentyActs.PrepareOnSourceEventResponse event) {
    LOG.debug(" Prepare On Source Event:");
    LOG.debug("   Tx Info Digest: 0x{}", new BigInteger(1, event._txInfoDigest).toString(16));
    LOG.debug("   Success: {}", event._success);
    LOG.debug("   Failure Reason: 0x{}", event._failureReason.toString(16));
    LOG.debug("   Error Msg: {}", event._msg);
  }

  private void dumpFinalizeOnTargetEvent(TwentyActs.FinalizeOnTargetEventResponse event) {
    LOG.debug(" Finalize On Target Event:");
    LOG.debug("   Tx Info Digest: 0x{}", new BigInteger(1, event._txInfoDigest).toString(16));
  }

  private void dumpFinalizeOnSourceEvent(TwentyActs.FinalizeOnSourceEventResponse event) {
    LOG.debug(" Finalize On Source Event:");
    LOG.debug("   Tx Info Digest: 0x{}", new BigInteger(1, event._txInfoDigest).toString(16));
  }
}
