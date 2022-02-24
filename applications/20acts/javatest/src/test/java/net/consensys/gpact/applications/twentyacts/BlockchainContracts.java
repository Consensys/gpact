/*
 * Copyright 2019 ConsenSys Software Inc
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
package net.consensys.gpact.applications.twentyacts;

import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.ERC20PresetFixedSupply;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;


import java.io.IOException;
import java.math.BigInteger;

/**
 * Controls ERC 20 and 20ACTS contract for a blockchain.
 */
public class BlockchainContracts extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(BlockchainContracts.class);

  private TwentyActs twentyActs;
  private ERC20PresetFixedSupply erc20;

  public BlockchainContracts(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deploy20ActsContract(BigInteger withdrawWaitPeriod, String infrastructureAccount)
      throws Exception {
    this.twentyActs = TwentyActs.deploy(
                this.web3j,
                this.tm,
                this.gasProvider,
                this.blockchainId.asBigInt(),
                withdrawWaitPeriod,
                infrastructureAccount)
            .send();
    LOG.info(
        "20ACTS deployed to {} on blockchain {}",
        this.twentyActs.getContractAddress(),
        this.blockchainId);
  }

  private void load20ActsContract(String address) {
    this.twentyActs = TwentyActs.load(
                address,
                    this.web3j,
                    this.tm,
                    this.gasProvider);
  }


  public void deployErc20Contract(String name, String symbol, BigInteger initialSupply, String owner)
          throws Exception {
    this.erc20 = ERC20PresetFixedSupply.deploy(
                    this.web3j,
                    this.tm,
                    this.gasProvider,
                    name,
                    symbol,
                    initialSupply,
                    owner)
            .send();
    LOG.info(
            "ERC20 deployed to {} on blockchain {}",
            this.erc20.getContractAddress(),
            this.blockchainId);
  }

  private void loadErc20Contract(String address) {
    this.erc20 = ERC20PresetFixedSupply.load(address,
                    this.web3j,
                    this.tm,
                    this.gasProvider);
  }

  public void addRemote20Acts(BlockchainId remoteBcId, String remote20ActsAddress) throws Exception {
    this.twentyActs.addRemoteCrosschainControl(remoteBcId.asBigInt(), remote20ActsAddress).send();
  }

  public void addVerifier(BlockchainId remoteBcId, String verifierAddress) throws Exception {
    this.twentyActs.addVerifier(remoteBcId.asBigInt(), verifierAddress).send();
  }

  public void addRemoteErc20(BlockchainId otherBc, String otherBcErc20) throws Exception {
    this.twentyActs.setErc20Mapping(this.erc20.getContractAddress(), otherBc.asBigInt(), otherBcErc20).send();
  }

  public void faucet(String account, BigInteger amount) throws Exception {
    this.erc20.transfer(account, amount).send();
  }


  public String get20ActsContractAddress() {
    return this.twentyActs.getContractAddress();
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }


  public BlockchainContracts forUser(Credentials user) throws IOException{
    BlockchainContracts bc = new BlockchainContracts(user, this.blockchainId, this.uri, this.gasPriceStrategy, this.pollingInterval);
    bc.load20ActsContract(this.twentyActs.getContractAddress());
    bc.loadErc20Contract(this.erc20.getContractAddress());
    return bc;
  }


}
