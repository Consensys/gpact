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

import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.messaging.fake.FakeRelayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Facilitates testing the Event Signer contract. This functionality is completed by the relayer.
 */
public class EventSigner {
  static final Logger LOG = LogManager.getLogger(EventSigner.class);

  List<AnIdentity> signers = new ArrayList<>();

  BlockchainId sourceBcId;

  public EventSigner(BlockchainId sourceBcId) {
    this.sourceBcId = sourceBcId;
  }

  public void addSigner(AnIdentity signer) throws Exception {
    signers.add(signer);
  }

  public SignedEvent getSignedEvent(
      byte[] eventData, String contractAddress, byte[] eventFunctionSignature) throws Exception {
    return FakeRelayer.getSignedEvent(
        this.signers, this.sourceBcId, eventData, contractAddress, eventFunctionSignature);
  }
}
