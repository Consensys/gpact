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
package net.consensys.gpact.attestorsign;

import net.consensys.gpact.attestorsign.soliditywrappers.AttestorSignRegistrar;
import net.consensys.gpact.attestorsign.soliditywrappers.CrosschainVerifierSign;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.messaging.MessagingManagementInterface;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 *
 */
public class AttestorSigner implements MessagingVerificationInterface {
  static final Logger LOG = LogManager.getLogger(AttestorSigner.class);


  @Override
  public SignedEvent getSignedEvent(
          List<BigInteger> targetBlockchainIds,
          TransactionReceipt startTxReceipt,
          byte[] eventData,
          byte[] eventFunctionSignature) throws Exception {
    return null;
  }


}
