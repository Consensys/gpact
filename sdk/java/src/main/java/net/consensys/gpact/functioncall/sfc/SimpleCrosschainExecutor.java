/*
 * Copyright 2020 ConsenSys Software Inc
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

import static net.consensys.gpact.functioncall.common.CallPath.calculateFirstCallMapKey;
import static net.consensys.gpact.functioncall.common.CallPath.calculateRootCallMapKey;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.functioncall.common.CrosschainCallResultImpl;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.soliditywrappers.functioncall.sfc.SimpleCrosschainControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Holds the state for a crosschain call. A separate instance of this class is needed for each
 * crosschain call.
 */
class SimpleCrosschainExecutor {
  static final Logger LOG = LogManager.getLogger(SimpleCrosschainExecutor.class);

  CrossControlManagerGroup crossControlManagerGroup;

  SimpleCrosschainExecutor(CrossControlManagerGroup crossControlManagerGroup) {
    this.crossControlManagerGroup = crossControlManagerGroup;
  }

  CrosschainCallResult execute(CallExecutionTree rootCall) throws Exception {
    Map<BigInteger, TransactionReceipt> transactionReceipts = new HashMap<>();

    CrossControlManager cbcManager =
        this.crossControlManagerGroup.getCbcManager(rootCall.getBlockchainId());
    MessagingVerificationInterface messaging =
        this.crossControlManagerGroup.getMessageVerification(rootCall.getBlockchainId());

    Tuple<TransactionReceipt, byte[], SimpleCrosschainControl.CrossCallEventResponse> result =
        ((SimpleCrossControlManager) cbcManager).sourceBcCall(rootCall);
    TransactionReceipt txr1 = result.getFirst();
    transactionReceipts.put(calculateRootCallMapKey(), txr1);
    byte[] crossCallEventData = result.getSecond();
    SimpleCrosschainControl.CrossCallEventResponse crossCallEvent = result.getThird();

    if (!txr1.isStatusOK()) {
      return new CrosschainCallResultImpl(
          rootCall, false, transactionReceipts, "Source blockchain transaction failed");
    }

    SignedEvent signedCrossCallEvent =
        messaging.getSignedEvent(
            this.crossControlManagerGroup.getAllBlockchainIds(),
            txr1,
            crossCallEventData,
            cbcManager.getCbcContractAddress(),
            SimpleCrossControlManager.CROSSCALL_EVENT_SIGNATURE);

    if (signedCrossCallEvent == null) {
      // The messaging protocol will complete the transaction for the user.
      // There is no more to do.
      return new CrosschainCallResultImpl(rootCall, true, transactionReceipts, null);
    }

    BlockchainId destBcId = new BlockchainId(crossCallEvent._destBcId);

    cbcManager = this.crossControlManagerGroup.getCbcManager(destBcId);
    Tuple<TransactionReceipt, String, Boolean> result2 =
        ((SimpleCrossControlManager) cbcManager).destinationBcCall(signedCrossCallEvent);

    transactionReceipts.put(calculateFirstCallMapKey(), result2.getFirst());

    String errorMsg = result2.getSecond();
    if (errorMsg != null) {
      errorMsg = "Destination blockchain transaction failed: " + errorMsg;
    }
    return new CrosschainCallResultImpl(
        rootCall, result2.getThird(), transactionReceipts, errorMsg);
  }
}
