/*
 * Copyright 2022 ConsenSys Software Inc
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

import net.consensys.gpact.common.*;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.ERC20PresetFixedSupply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;


import java.io.IOException;
import java.math.BigInteger;

/**
 * Controls ERC 20 contract for a blockchain.
 */
public class ERC20Manager extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(ERC20Manager.class);

  private ERC20PresetFixedSupply erc20;

  public ERC20Manager(
      Credentials credentials,
      BlockchainId bcId,
      String uri,
      DynamicGasProvider.Strategy gasPriceStrategy,
      int blockPeriod)
      throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
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


  public void erc20Faucet(String account, BigInteger amount) throws Exception {
    this.erc20.transfer(account, amount).send();
  }

  public void erc20Approve(String spender, BigInteger amount) throws Exception {
    TransactionReceipt txR = this.erc20.approve(spender, amount).send();
    StatsHolder.logGas("ERC20 Approve", txR.getGasUsed());
  }

  public String getErc20ContractAddress() {
    return this.erc20.getContractAddress();
  }

  public BigInteger balanceOf(String account) throws Exception {
    return this.erc20.balanceOf(account).send();
  }


  public ERC20Manager forUser(Credentials user) throws IOException{
    ERC20Manager bc = new ERC20Manager(user, this.blockchainId, this.uri, this.gasPriceStrategy, this.pollingInterval);
    bc.loadErc20Contract(this.erc20.getContractAddress());
    return bc;
  }


}
