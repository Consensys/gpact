/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.messaging.eventrelay;

import java.util.List;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Provides dummy information back to the function call layer. For event relay, the relayers sign
 * the event and then forward it to the target chain.
 */
public class EventRelay implements MessagingVerificationInterface {

  BlockchainId bcId;

  public EventRelay(BlockchainId blockchainId) {
    this.bcId = blockchainId;
  }

  @Override
  public SignedEvent getSignedEvent(
      List<BlockchainId> targetBlockchainIds,
      TransactionReceipt txReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature)
      throws Exception {
    return null;
  }
}
