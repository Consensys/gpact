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
package net.consensys.gpact.sfc.examples.tokenbridge;

import net.consensys.gpact.common.AbstractBlockchain;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;


/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge
 * contract on a certain blockchain.
 *
 */
public abstract class AbstractERC20Bridge extends AbstractBlockchain {
    private static final Logger LOG = LogManager.getLogger(AbstractERC20Bridge.class);

    // Total number of tokens issued for booking.
    public final BigInteger tokenSupply;
    public final String entity;

    protected String erc20BridgeAddress;


    public AbstractERC20Bridge(final String entity, BigInteger tokenSupply,
                               Credentials credentials, BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod) throws IOException {
        super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
        this.entity = entity;
        this.tokenSupply = tokenSupply;
    }

    public abstract void deployContracts(String cbcAddress) throws Exception;



    public abstract void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress) throws Exception;

    public abstract void addBlockchain(BlockchainId remoteBcId, String remoteERC20BridgeContractAddress) throws Exception;


    public abstract String getErc20ContractAddress();

    public abstract String getErc20BridgeContractAddress();

    public abstract void giveTokensToERC20Bridge(final int number) throws Exception;

    public abstract void giveTokens(final Erc20User user, final int number) throws Exception;

    public abstract void showErc20Balances(Erc20User[] users) throws Exception;

    public abstract void showErc20Allowance(String owner, String spender) throws Exception;
}
