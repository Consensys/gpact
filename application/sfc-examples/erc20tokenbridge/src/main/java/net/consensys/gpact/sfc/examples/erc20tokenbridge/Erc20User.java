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
package net.consensys.gpact.sfc.examples.erc20tokenbridge;

import net.consensys.gpact.common.*;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers.SfcErc20Bridge;
import net.consensys.gpact.openzeppelin.soliditywrappers.ERC20PresetFixedSupply;
import net.consensys.gpact.sfccbc.SimpleCrossControlManagerGroup;
import net.consensys.gpact.sfccbc.SimpleCrosschainExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * An owner of ERC 20 tokens transferring tokens from one blockchain to another.
 */
public class Erc20User {
    private static final Logger LOG = LogManager.getLogger(Erc20User.class);

    // protected static final int RETRY = 20;

    private final String name;

    protected Credentials creds;

    private final BlockchainId bcIdA;
    private final BlockchainId bcIdB;

    private final String addressOfERC20OnBlockchainA;
    private final String addressOfERC20OnBlockchainB;

    private final String addressOfBridgeOnBlockchainA;
    private final String addressOfBridgeOnBlockchainB;

    private BlockchainInfo bcInfoA;
    private BlockchainInfo bcInfoB;

    private SimpleCrossControlManagerGroup crossControlManagerGroup;

    protected Erc20User(
            String name,
            BlockchainId bcIdA, String erc20AddressA, String erc20BridgeAddressA,
            BlockchainId bcIdB, String erc20AddressB, String erc20BridgeAddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();
        this.addressOfERC20OnBlockchainA = erc20AddressA;
        this.addressOfERC20OnBlockchainB = erc20AddressB;
        this.addressOfBridgeOnBlockchainA = erc20BridgeAddressA;
        this.addressOfBridgeOnBlockchainB = erc20BridgeAddressB;

        this.bcIdA = bcIdA;
        this.bcIdB = bcIdB;
    }


    public void createCbcManager(
            BlockchainInfo bcInfoA, List<String> infrastructureContractAddressOnBcA, MessagingVerificationInterface msgVerA,
            BlockchainInfo bcInfoB, List<String> infrastructureContractAddressOnBcB,  MessagingVerificationInterface msgVerB) throws Exception {
        this.crossControlManagerGroup = new SimpleCrossControlManagerGroup();
        this.crossControlManagerGroup.addBlockchainAndLoadContracts(this.creds, bcInfoA, infrastructureContractAddressOnBcA, msgVerA);
        this.crossControlManagerGroup.addBlockchainAndLoadContracts(this.creds, bcInfoB, infrastructureContractAddressOnBcB, msgVerB);

        this.bcInfoA = bcInfoA;
        this.bcInfoB = bcInfoB;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.creds.getAddress();
    }


    public void transfer(boolean fromAToB, int amountInt) throws Exception {
        transfer(fromAToB, this.creds.getAddress(), amountInt);
    }

    public void transfer(boolean fromAToB, String recipient, int amountInt) throws Exception {
        LOG.info(" Transfer: {}: {}: {} tokens ", this.name, fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA", amountInt);

        BigInteger amount = BigInteger.valueOf(amountInt);
        BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
        BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
        String sourceERC20ContractAddress = fromAToB ? this.addressOfERC20OnBlockchainA : this.addressOfERC20OnBlockchainB;
        String sourceBridgeContractAddress = fromAToB ? this.addressOfBridgeOnBlockchainA : this.addressOfBridgeOnBlockchainB;

        BlockchainInfo bcInfo = fromAToB ? this.bcInfoA : this.bcInfoB;

        final int RETRY = 20;
        Web3j web3j = Web3j.build(new HttpService(bcInfo.uri), bcInfo.period, new ScheduledThreadPoolExecutor(5));
        TransactionReceiptProcessor txrProcessor = new PollingTransactionReceiptProcessor(web3j, bcInfo.period, RETRY);
        FastTxManager tm = TxManagerCache.getOrCreate(web3j, this.creds, sourceBlockchainId.asLong(), txrProcessor);
        DynamicGasProvider gasProvider = new DynamicGasProvider(web3j, bcInfo.uri, bcInfo.gasPriceStrategy);

        // Step 1: Approve of the bridge contract using some of the user's tokens.
        LOG.info("Approve");
        ERC20PresetFixedSupply erc20 = ERC20PresetFixedSupply.load(sourceERC20ContractAddress, web3j, tm, gasProvider);
        TransactionReceipt txR;
        try {
            txR = erc20.approve(sourceBridgeContractAddress, amount).send();
        } catch (TransactionException ex) {
            // Crosschain Control Contract reverted
            String revertReason = RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
            LOG.error(" Revert Reason: {}", revertReason);
            throw ex;
        }
        StatsHolder.logGas("Approve", txR.getGasUsed());

        // Step 2: Do the crosschain transaction.
        SfcErc20Bridge sfcErc20Bridge = SfcErc20Bridge.load(sourceBridgeContractAddress, web3j, tm, gasProvider);
        LOG.info(" Call: BcId: {}, ERC20 Bridge: {}", sourceBlockchainId, sourceBridgeContractAddress);
        RemoteFunctionCall<TransactionReceipt> functionCall = sfcErc20Bridge.transferToOtherBlockchain(
                destinationBlockchainId.asBigInt(), sourceERC20ContractAddress, recipient, amount);

        SimpleCrosschainExecutor executor = new SimpleCrosschainExecutor(crossControlManagerGroup);
        Tuple<TransactionReceipt[], String, Boolean> results = executor.execute(sourceBlockchainId, functionCall);
        boolean success =  results.getThird();
        LOG.info("Success: {}", success);
        if (!success) {
            LOG.error("Crosschain Execution failed. See log for details");
            String errorMsg = results.getSecond();
            if (errorMsg != null) {
                LOG.error("Error information: {}", errorMsg);
            }
            for (TransactionReceipt txr: results.getFirst()) {
                LOG.error("Transaction Receipt: {}", txr.toString());
            }
            throw new Exception("Crosschain Execution failed. See log for details");
        }
    }

}
