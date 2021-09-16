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
package net.consensys.gpact.messaging;


import net.consensys.gpact.common.AnIdentity;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public interface MessagingManagementInterface {

  void deployContracts() throws Exception;

  String getVerifierAddress();

  /**
   * Get the addresses of all contracts related to this messaging technique.
   *
   * @return addresses of all contracts.
   */
  ArrayList<String> getContractAddresses();

  /**
   * Load all contracts related to this messaging technique.
   *
   * @param addresses Addresses of the contracts, in the same order as
   *                  returned by getContractAddresses.
   */
  void loadContracts(ArrayList<String> addresses);


  void addBlockchain(BigInteger bcId, String cbcContractAddress, String initialSigner) throws Exception;

  void registerSigner(AnIdentity signer, BigInteger bcId) throws Exception;
}
