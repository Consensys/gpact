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

import net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager.TXSTATE_IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HappyPathTest extends Abstract20ActsTest {
    static final Logger LOG = LogManager.getLogger(HappyPathTest.class);


    // Running multiple times will reveal any gas difference due to rerunning.
    static int NUM_TIMES_EXECUTE = 2;


    @Test
    public void happyPathDirectSignMultiBlockchain() throws Exception {
        StatsHolder.log("20ACTS: Happy Path");
        LOG.info("Started");

        deployAndSetup();
        setupBalances();

        for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
            LOG.info("Execution: {}  *****************", numExecutions);
            StatsHolder.log("Execution: " + numExecutions + " **************************");

            //lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(), lp1ChainBErc20.getErc20ContractAddress());

            int amountI = 10 * (numExecutions + 1);
            int lpFeeI = (numExecutions + 2);
            int inFeeI = (numExecutions + 1);
            int totalI = amountI + lpFeeI + inFeeI;

            BigInteger amount = BigInteger.valueOf(amountI);
            BigInteger lpFee = BigInteger.valueOf(lpFeeI);
            BigInteger inFee = BigInteger.valueOf(inFeeI);
            BigInteger total = BigInteger.valueOf(totalI);
            LOG.info("Amount: {}, LP Fee: {}, Inf Fee: {}, Total: {}", amount, lpFee, inFee, total);

            user1ChainAErc20.erc20Approve(user1ChainA20Acts.getCbcContractAddress(), total);


            BigInteger crosschainTxId = BigInteger.probablePrime(256, new Random());
            BigInteger sourceBcId = chainA.bcId.asBigInt();
            String sourceErc20Address = user1ChainAErc20.getErc20ContractAddress();
            BigInteger targetBcId = chainB.bcId.asBigInt();
            String targetErc20Address = user1ChainBErc20.getErc20ContractAddress();
            String sender = user1.getAddress();
            String recipient = receipient1.getAddress();
            String liquidityProvider = lp1.getAddress();
            BigInteger biddingPeriodEnd = BigInteger.ZERO;
            BigInteger timeoutInSeconds = BigInteger.valueOf((System.currentTimeMillis() / 1000) + 300);

            TwentyActs.TxInfo txInfo = new TwentyActs.TxInfo(
                    crosschainTxId, sourceBcId, sourceErc20Address, targetBcId, targetErc20Address,
                    sender, recipient, liquidityProvider, amount, lpFee, inFee, biddingPeriodEnd, timeoutInSeconds
            );

            // Prepare on Target
            Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnTargetEventResponse> prepareOnTargetResponse = lp1ChainB20Acts.prepareOnTarget(txInfo);
            if (!prepareOnTargetResponse.getFirst().isStatusOK()) {
                throw new Exception("Prepare On Target Failed");
            }
            //lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(), lp1ChainBErc20.getErc20ContractAddress());
            checkHoldings(lp1ChainB20Acts, lp1Account, erc20B, lp1DepB, amountI, 0);


            // Prepare on Source
            List<BlockchainId> justBcA = new ArrayList<>();
            justBcA.add(chainA.bcId);
            MessagingVerificationInterface messaging = managerGroup.getMessageVerification(chainB.bcId);
            SignedEvent signedSegEvent =
                    messaging.getSignedEvent(
                            justBcA,
                            prepareOnTargetResponse.getFirst(),
                            prepareOnTargetResponse.getSecond(),
                            lp1ChainB20Acts.getCbcContractAddress(),
                            TwentyActsManager.PREPARE_ON_TARGET_EVENT_SIGNATURE);

            byte[] signatureOrProof = signedSegEvent.getEncodedSignatures();
            Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnSourceEventResponse> prepareOnSourceResponse =
                    lp1ChainA20Acts.prepareOnSource(txInfo, prepareOnTargetResponse.getSecond(), signatureOrProof);
            if (!prepareOnSourceResponse.getFirst().isStatusOK()) {
                throw new Exception("Prepare On Source Failed");
            }
            checkHoldings(lp1ChainA20Acts, user1Account, erc20A, totalI, totalI, 0);
            checkHoldings(lp1ChainA20Acts, lp1Account, erc20A, lp1DepA, 0, 0);


            // Finalize on Target
            List<BlockchainId> justBcB = new ArrayList<>();
            justBcB.add(chainB.bcId);
            messaging = managerGroup.getMessageVerification(chainA.bcId);
            signedSegEvent =
                    messaging.getSignedEvent(
                            justBcB,
                            prepareOnSourceResponse.getFirst(),
                            prepareOnSourceResponse.getSecond(),
                            lp1ChainA20Acts.getCbcContractAddress(),
                            TwentyActsManager.PREPARE_ON_SOURCE_EVENT_SIGNATURE);

            signatureOrProof = signedSegEvent.getEncodedSignatures();
            Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnTargetEventResponse> finalizeOnTargetResponse =
                lp1ChainB20Acts.finalizeOnTarget(txInfo, prepareOnSourceResponse.getSecond(), signatureOrProof);
            if (!finalizeOnTargetResponse.getFirst().isStatusOK()) {
                throw new Exception("Finalize On Target Failed");
            }
            //lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(), lp1ChainBErc20.getErc20ContractAddress());
            lp1DepB -= amountI;
            checkHoldings(lp1ChainB20Acts, lp1Account, erc20B, lp1DepB, 0, 0);

            // Finalize on Source
            // Check transaction state.
            byte[] digest = prepareOnSourceResponse.getThird()._txInfoDigest;
            BigInteger txState = lp1ChainA20Acts.getTransactionState(digest);
            assertEquals(txState.intValue(), TXSTATE_IN_PROGRESS);

            messaging = managerGroup.getMessageVerification(chainB.bcId);
            signedSegEvent =
                    messaging.getSignedEvent(
                            justBcA,
                            finalizeOnTargetResponse.getFirst(),
                            finalizeOnTargetResponse.getSecond(),
                            lp1ChainB20Acts.getCbcContractAddress(),
                            TwentyActsManager.FINALIZE_ON_TARGET_EVENT_SIGNATURE);

            signatureOrProof = signedSegEvent.getEncodedSignatures();
            Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnSourceEventResponse> finalizeOnSourceResponse =
                    lp1ChainA20Acts.finalizeOnSource(txInfo, finalizeOnTargetResponse.getSecond(), signatureOrProof);
            if (!finalizeOnSourceResponse.getFirst().isStatusOK()) {
                throw new Exception("Finalize On Target Failed");
            }
            //lp1ChainA20Acts.showLiquidityProviderHoldings(lp1.getAddress(), lp1ChainBErc20.getErc20ContractAddress());
            lp1DepA += amountI + lpFeeI;
            infDepA += inFeeI;
            checkHoldings(lp1ChainA20Acts, user1Account, erc20A, 0, 0, 0);
            checkHoldings(lp1ChainA20Acts, lp1Account, erc20A, lp1DepA, 0, 0);
            checkHoldings(lp1ChainA20Acts, infAccount, erc20A, infDepA, 0, 0);

            user1BalA -= totalI;
            recipient1BalB += amountI;
            checkBalance(user1ChainAErc20, user1Account, user1BalA);
            checkBalance(user1ChainBErc20, recipient1Account, recipient1BalB);
        }

        depChainAErc20.shutdown();
        depChainBErc20.shutdown();

        StatsHolder.log("End");
        StatsHolder.print();
    }
}
