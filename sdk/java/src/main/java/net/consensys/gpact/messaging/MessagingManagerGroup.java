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

import java.util.ArrayList;
import java.util.Set;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import org.web3j.crypto.Credentials;

/** Manage multiple blockchains, each holding a set of registrar and verification contracts */
public interface MessagingManagerGroup {

  /**
   * Add a blockchain to the group managed by this class and deploy contracts to the blockchain.
   *
   * @param creds Credentials to use for transactions on the blockchain.
   * @param bcInfo Blockchain configuration information.
   * @throws Exception If there is an issue; typically due to issues deploying the contracts.
   */
  void addBlockchainAndDeployContracts(Credentials creds, BlockchainConfig bcInfo) throws Exception;

  default void addBlockchainAndDeployContracts(
      Credentials creds, BlockchainConfig bcInfo, String additionalContractAddress)
      throws Exception {
    throw new RuntimeException("Not implemented");
  }

  /**
   * Add a blockchain to be managed by this class. Load contracts that have previously been
   * deployed.
   *
   * @param creds Credentials to use for transactions on the blockchain.
   * @param bcInfo Blockchain configuration information.
   * @param addresses Addresses of contracts.
   * @throws Exception If there is an issue; typically due to issues deploying the contracts.
   */
  void addBlockchainAndLoadContracts(
      Credentials creds, BlockchainConfig bcInfo, ArrayList<String> addresses) throws Exception;

  void registerSignerOnAllBlockchains(String signersAddress) throws Exception;

  void registerSigner(String signersAddress, BlockchainId bcId1) throws Exception;

  void registerFirstSignerOnAllBlockchains(String signersAddress) throws Exception;

  void registerFirstSigner(String signersAddress, BlockchainId bcId1) throws Exception;

  String getWsUri(final BlockchainId bcId) throws Exception;

  String getVerifierAddress(final BlockchainId bcId) throws Exception;

  Set<BlockchainId> getSupportedBlockchains();
}
