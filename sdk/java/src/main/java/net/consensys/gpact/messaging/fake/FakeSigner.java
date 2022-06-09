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
package net.consensys.gpact.messaging.fake;

import java.util.List;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Manages the interaction between the application library and attestors for a certain blockchain.
 * The class asks an attestor to give the threshold signed event data. The attestor should have
 * cooperated with other attestors already to threshold sign the event data.
 */
public class FakeSigner implements MessagingVerificationInterface {
  static final Logger LOG = LogManager.getLogger(FakeSigner.class);

  BlockchainId bcId;

  FakeRelayer fakeRelayer;

  public FakeSigner(BlockchainId blockchainId, FakeRelayer fakeRelayer) {
    this.bcId = blockchainId;
    this.fakeRelayer = fakeRelayer;
  }

  @Override
  public SignedEvent getSignedEvent(
      List<BlockchainId> notUsedTargetBlockchainIds,
      TransactionReceipt notUsedTxReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature) {

    byte[] encodedSignatures =
        this.fakeRelayer.fetchedSignedEvent(
            this.bcId, contractAddress, eventFunctionSignature, eventData);
    return new SignedEvent(
        this.bcId, contractAddress, eventFunctionSignature, eventData, encodedSignatures);
  }
}
