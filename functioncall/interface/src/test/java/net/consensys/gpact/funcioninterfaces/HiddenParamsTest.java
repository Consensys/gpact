/*
 * Copyright 2021 ConsenSys Software.
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
package net.consensys.gpact.funcioninterfaces;

import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.funcioninterfaces.soliditywrappers.HiddenParamDestTest;
import net.consensys.gpact.funcioninterfaces.soliditywrappers.HiddenParamSourceTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.List;

public class HiddenParamsTest extends AbstractWeb3Test {
  private static final Logger LOG = LogManager.getLogger(HiddenParamsTest.class);

  // Have the polling interval equal to the block time.
  private static final int POLLING_INTERVAL = 2000;
  // Retry requests to Ethereum Clients up to five times.
  private static final int RETRY = 5;

  HiddenParamDestTest destContract;
  HiddenParamSourceTest sourceContract;




  public static final BigInteger EXPECTED1 = BigInteger.valueOf(41);
  public static final BigInteger EXPECTED2 = BigInteger.valueOf(47);
  public static final BigInteger EXPECTED3 = BigInteger.valueOf(53);

  @Before
  public void setup() throws Exception {
    setupWeb3();
    LOG.info("Deploying contracts");
    this.destContract = HiddenParamDestTest.deploy(this.web3j, this.tm, this.freeGasProvider, EXPECTED1, EXPECTED2, EXPECTED3).send();
    String destContractAddress = this.destContract.getContractAddress();
    LOG.info(" destContract deployed to address: {}", destContractAddress);

    this.sourceContract = HiddenParamSourceTest.deploy(this.web3j, this.tm, this.freeGasProvider,
            destContractAddress, EXPECTED1, EXPECTED2, EXPECTED3).send();
    LOG.info(" sourceContract deployed to address: {}", this.sourceContract.getContractAddress());
  }

  @Test
  public void twoParams() throws Exception {
    LOG.info("callFuncNoParams Hidden Params");
    TransactionReceipt receipt1;
    try {
      receipt1 = this.sourceContract.twoParamCallFuncNoParams().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncNoParams Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    List<HiddenParamSourceTest.DumpEventResponse> dumpEventResponses = this.sourceContract.getDumpEvents(receipt1);
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
        LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncOneParam Hidden Params");
    try {
      receipt1 = this.sourceContract.twoParamCallFuncOneParam().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncOneParam Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncTwoParams Hidden Params");
    try {
      receipt1 = this.sourceContract.twoParamCallFuncTwoParams().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncTwoParams Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }
  }

  @Test
  public void threeParams() throws Exception {
    LOG.info("callFuncNoParams Hidden Params");
    TransactionReceipt receipt1;
    try {
      receipt1 = this.sourceContract.callFuncNoParams().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncNoParams Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    List<HiddenParamSourceTest.DumpEventResponse> dumpEventResponses = this.sourceContract.getDumpEvents(receipt1);
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncOneParam Hidden Params");
    try {
      receipt1 = this.sourceContract.callFuncOneParam().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncOneParam Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncTwoParams Hidden Params");
    try {
      receipt1 = this.sourceContract.callFuncTwoParams().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
      throw new Exception("callFuncTwoParams Reverted: " + RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }
  }


  // This is just to create comparison gas costs.
  @Ignore
  @Test
  public void explicitParams() throws Exception {
    LOG.info("callFuncNoParams Explicit Params");
    TransactionReceipt receipt1;
    try {
      receipt1 = this.sourceContract.callFuncNoParamsExplicit().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    List<HiddenParamSourceTest.DumpEventResponse> dumpEventResponses = this.sourceContract.getDumpEvents(receipt1);
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncOneParam Explicit Params");
    try {
      receipt1 = this.sourceContract.callFuncOneParamExplicit().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }

    LOG.info("callFuncTwoParams Explicit Params");
    try {
      receipt1 = this.sourceContract.callFuncTwoParamsExplicit().send();
    } catch (TransactionException ex) {
      receipt1 = ex.getTransactionReceipt().orElseThrow(Exception::new);
    }
    LOG.info(" StatusOK: {}", receipt1.isStatusOK());
    LOG.info(" Gas used: {}", receipt1.getGasUsed());
    if (!receipt1.isStatusOK()) {
      LOG.info("  Revert Reason: {}", RevertReason.decodeRevertReason(receipt1.getRevertReason()));
    }
    for (HiddenParamSourceTest.DumpEventResponse event: dumpEventResponses) {
      LOG.info("   DumpEvent: {}", new BigInteger(1, event._b).toString(16));
    }
  }

}
