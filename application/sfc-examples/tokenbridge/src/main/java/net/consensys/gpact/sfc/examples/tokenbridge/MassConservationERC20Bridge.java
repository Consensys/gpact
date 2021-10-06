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

import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.DynamicGasProvider;
import net.consensys.gpact.sfc.examples.tokenbridge.soliditywrappers.SfcErc20MassConservationBridge;
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
public class MassConservationERC20Bridge extends AbstractERC20Bridge {
    private static final Logger LOG = LogManager.getLogger(MassConservationERC20Bridge.class);

    SfcErc20MassConservationBridge erc20Bridge;


    public MassConservationERC20Bridge(final String entity, BigInteger tokenSupply,
                                       Credentials credentials, BlockchainId bcId, String uri, DynamicGasProvider.Strategy gasPriceStrategy, int blockPeriod) throws IOException {
        super(entity, tokenSupply, credentials, bcId, uri, gasPriceStrategy, blockPeriod);
    }

    public void deployContract(String cbcAddress) throws Exception {
        super.deployContract();
        this.erc20Bridge = SfcErc20MassConservationBridge.deploy(this.web3j, this.tm, this.gasProvider, cbcAddress).send();
        LOG.info(" Deploy ERC20 Bridge: {}", this.erc20Bridge.getContractAddress());
    }

    public String getErc20BridgeContractAddress() {
        return this.erc20Bridge.getContractAddress();
    }

    public void addRemoteERC20(BlockchainId remoteBcId, String remoteERC20ContractAddress) throws Exception {
//        LOG.info(" Setting Remote ERC20: Local BcId: {}, Remote BcId: {}, Remote ERC20: {}",
//                this.blockchainId, remoteBcId, remoteERC20ContractAddress);
        this.erc20Bridge.changeContractMapping(this.erc20.getContractAddress(), remoteBcId.asBigInt(), remoteERC20ContractAddress).send();
    }
}
