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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.BlockchainId;

public interface MessagingManagerInterface {

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
   * @param addresses Addresses of the contracts, in the same order as returned by
   *     getContractAddresses.
   */
  void loadContracts(ArrayList<String> addresses);

  void registerSigner(BlockchainId bcId, String signer) throws Exception;

  void registerSigner(BlockchainId bcId, String signer, BigInteger newThreshold) throws Exception;

  void registerSigners(BlockchainId bcId, List<String> signers, BigInteger newThreshold)
      throws Exception;

  void removeSigner(BlockchainId bcId, String signer) throws Exception;

  void removeSigner(BlockchainId bcId, String signer, BigInteger newThreshold) throws Exception;
}
