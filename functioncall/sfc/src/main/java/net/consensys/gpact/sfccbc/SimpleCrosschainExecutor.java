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
package net.consensys.gpact.sfccbc;

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.sfccbc.soliditywrappers.SimpleCrosschainControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Holds the state for a crosschain call. A separate instance of this
 * class is needed for each crosschain call.
 */
public class SimpleCrosschainExecutor {
  static final Logger LOG = LogManager.getLogger(SimpleCrosschainExecutor.class);

  SimpleCrossControlManagerGroup crossControlManagerGroup;

  public SimpleCrosschainExecutor(SimpleCrossControlManagerGroup crossControlManagerGroup) {
    this.crossControlManagerGroup = crossControlManagerGroup;
  }

  public Tuple<TransactionReceipt[], String, Boolean> execute(BlockchainId sourceBcId, RemoteCall<TransactionReceipt> functionCall) throws Exception {
    SimpleCrossControlManager cbcContract = this.crossControlManagerGroup.getCbcContract(sourceBcId);
    MessagingVerificationInterface messaging = this.crossControlManagerGroup.getMessageVerification(sourceBcId);

    Tuple<TransactionReceipt, byte[], SimpleCrosschainControl.CrossCallEventResponse> result =
            cbcContract.sourceBcCall(functionCall);
    TransactionReceipt txr1 = result.getFirst();
    byte[] crossCallEventData = result.getSecond();
    SimpleCrosschainControl.CrossCallEventResponse crossCallEvent = result.getThird();

    if (!txr1.isStatusOK()) {
      return new Tuple<TransactionReceipt[], String, Boolean>(new TransactionReceipt[] {txr1}, "Source blockchain transaction failed", false);
    }

    SignedEvent signedCrossCallEvent = messaging.getSignedEvent(
            this.crossControlManagerGroup.getAllBlockchainIds(),
            txr1,
            crossCallEventData,
            cbcContract.getCbcContractAddress(),
            SimpleCrossControlManager.CROSSCALL_EVENT_SIGNATURE);

    BlockchainId destBcId = new BlockchainId(crossCallEvent._destBcId);

    cbcContract = this.crossControlManagerGroup.getCbcContract(destBcId);
    Tuple<TransactionReceipt, String, Boolean> result2 = cbcContract.destinationBcCall(signedCrossCallEvent);
    return new Tuple<TransactionReceipt[], String, Boolean>(
            new TransactionReceipt[]{txr1, result2.getFirst()},
            result2.getSecond(),
            result2.getThird());
  }


}
