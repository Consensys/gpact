/*
 * Copyright 2021 ConsenSys Software Inc.
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
package net.consensys.gpact.examples.tokenbridge;

import net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers.CrosschainERC20PresetFixedSupply;
import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;


public class SourceAndDestinationBlockchain extends AbstractBlockchain {
    private static final Logger LOG = LogManager.getLogger(SourceAndDestinationBlockchain.class);

    // Total number of tokens issued for booking.
    public final BigInteger tokenSupply;
    CrosschainERC20PresetFixedSupply erc20;
    public final String entity;


    public SourceAndDestinationBlockchain(final String entity, BigInteger tokenSupply,
                                          Credentials credentials, BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod) throws IOException {
        super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
        this.entity = entity;
        this.tokenSupply = tokenSupply;
    }

    public void deployContract(String cbcAddress) throws Exception {
        String name = this.entity;
        String symbol= this.entity;
        String owner = this.credentials.getAddress();
        this.erc20 = CrosschainERC20PresetFixedSupply.deploy(this.web3j, this.tm, this.gasProvider, name, symbol, cbcAddress, this.tokenSupply, owner).send();
        LOG.info(" Deploy {} Crosschain ERC20: {}. Token Supply: {}", this.entity, this.erc20.getContractAddress(), this.tokenSupply);
    }


    public String getErc20ContractAddress() {
        return this.erc20.getContractAddress();
    }

    public void giveTokens(final Erc20User user, final int number) throws Exception {
        this.erc20.transfer(user.getAddress(), BigInteger.valueOf(number)).send();
    }

    public void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress) throws Exception {
//        LOG.info(" Setting Remote ERC20: Local BcId: {}, Remote BcId: {}, Remote ERC20: {}",
//                this.blockchainId, remoteBcId, remoteERC20ContractAddress);
        this.erc20.setRemoteERC20(remoteBcId.asBigInt(), remoteERC20ContractAddress).send();
    }

    public void showErc20Balances(Erc20User[] users) throws Exception {
        LOG.info(" {} ERC 20 Balances", this.entity);
        for (Erc20User user: users) {
            BigInteger bal = this.erc20.balanceOf(user.getAddress()).send();
            LOG.info(" Account {}:{} balance: {}", user.getName(), user.getAddress(), bal);
        }
    }
    public void showErc20Allowance(String owner, String spender) throws Exception {
        BigInteger allowance = this.erc20.allowance(owner, spender).send();
        LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender, allowance);

    }
}
