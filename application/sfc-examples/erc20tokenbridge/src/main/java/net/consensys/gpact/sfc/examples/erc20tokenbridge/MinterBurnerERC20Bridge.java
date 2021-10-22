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
package net.consensys.gpact.sfc.examples.erc20tokenbridge;

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.openzeppelin.soliditywrappers.ERC20PresetMinterPauser;
import net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers.SfcErc20MintingBurningBridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperledger.besu.crypto.Hash;
import org.web3j.crypto.Credentials;
import org.apache.tuweni.bytes.Bytes;

import java.io.IOException;
import java.math.BigInteger;


/**
 * Configures and operates an ERC 20 contract and the ERC 20 Bridge
 * contract on a certain blockchain.
 *
 */
public class MinterBurnerERC20Bridge extends AbstractERC20Bridge {
    private static final Logger LOG = LogManager.getLogger(MinterBurnerERC20Bridge.class);

    public static byte[] MINTER_ROLE = Hash.keccak256(Bytes.wrap("MINTER_ROLE".getBytes())).toArray();



    ERC20PresetMinterPauser erc20;

    SfcErc20MintingBurningBridge erc20Bridge;


    public MinterBurnerERC20Bridge(final String entity, BigInteger tokenSupply,
                                   Credentials credentials, BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod) throws IOException {
        super(entity, tokenSupply, credentials, bcId, uri, gasPriceStrategy, blockPeriod);
    }

    public void deployContracts(String cbcAddress) throws Exception {
        String name = this.entity;
        String symbol= this.entity;
        String owner = this.credentials.getAddress();
        this.erc20 = ERC20PresetMinterPauser.deploy(this.web3j, this.tm, this.gasProvider, name, symbol).send();
        LOG.info(" Deploy {} ERC20: {}.", this.entity, this.erc20.getContractAddress());

        this.erc20Bridge = SfcErc20MintingBurningBridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
        LOG.info(" Deploy ERC20 Minter Burner Bridge: {}", this.erc20Bridge.getContractAddress());
        this.erc20BridgeAddress = this.erc20Bridge.getContractAddress();

        LOG.info(" Configure ERC20 Bridge to be the minter for the ERC20 contract");
        this.erc20.grantRole(MINTER_ROLE, this.erc20BridgeAddress).send();
    }

    public String getErc20ContractAddress() {
        return this.erc20.getContractAddress();
    }

    public String getErc20BridgeContractAddress() {
        return this.erc20Bridge.getContractAddress();
    }

    public void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress) throws Exception {
        LOG.info(" Setting Remote ERC20: Local BcId: {}, Remote BcId: {}, Local ERC20: {}, Remote ERC20: {}",
                this.blockchainId, remoteBcId, this.erc20.getContractAddress(), remoteERC20ContractAddress);
        this.erc20Bridge.changeContractMapping(this.erc20.getContractAddress(), remoteBcId.asBigInt(), remoteERC20ContractAddress).send();
    }

    public void addBlockchain(BlockchainId remoteBcId, String remoteERC20BridgeContractAddress) throws Exception {
        LOG.info(" Setting Remote ERC20 Bridge: Remote BcId: {}, Remote ERC20Bridge: {}",
                remoteBcId, remoteERC20BridgeContractAddress);
        this.erc20Bridge.changeBlockchainMapping(remoteBcId.asBigInt(), remoteERC20BridgeContractAddress).send();
    }

    public void giveTokensToERC20Bridge(final int number) throws Exception {
        throw new Exception("ERC 20 Bridge doesn't need tokens. It can mint then whenever it needs");
    }

    public void giveTokens(final Erc20User user, final int number) throws Exception {
        throw new Exception("Only the bridge can mint tokens");
    }


    public void showErc20Balances(Erc20User[] users) throws Exception {
        LOG.info(" {} ERC20 Balances", this.entity);
        BigInteger totalSupply = this.erc20.totalSupply().send();
        LOG.info("  Total Supply: {}", totalSupply);
        BigInteger bal = this.erc20.balanceOf(this.erc20BridgeAddress).send();
        LOG.info("  ERC20 Bridge Account {}: balance: {}", this.erc20BridgeAddress, bal);
        for (Erc20User user: users) {
            bal = this.erc20.balanceOf(user.getAddress()).send();
            LOG.info("  Account {}:{} balance: {}", user.getName(), user.getAddress(), bal);
        }
    }
    public void showErc20Allowance(String owner, String spender) throws Exception {
        BigInteger allowance = this.erc20.allowance(owner, spender).send();
        LOG.info(" {}: Owner {}: Spender: {}: Allowance: {}", this.entity, owner, spender, allowance);
    }

}
