package net.consensys.gpact.applications.twentyacts;

import net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManager;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.applications.twentyacts.helpers.TwentyActsExampleSystemManager;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HappyPath {
    static final Logger LOG = LogManager.getLogger(HappyPath.class);


    // Running multiple times will reveal any gas difference due to rerunning.
    static int NUM_TIMES_EXECUTE = 2;

    public static void main(String[] args) throws Exception {
        StatsHolder.log("20ACTS: Happy Path");
        LOG.info("Started");

        if (args.length != 1) {
            LOG.info("Usage: [properties file name]");
            return;
        }

        Credentials deployerCreds = CredentialsCreator.createCredentials();
        Credentials infCreds = CredentialsCreator.createCredentials();
        Credentials chainAErc20Owner = CredentialsCreator.createCredentials();
        Credentials chainBErc20Owner = CredentialsCreator.createCredentials();
        Credentials user1 = CredentialsCreator.createCredentials();
        Credentials receipient1 = CredentialsCreator.createCredentials();
        Credentials lp1 = CredentialsCreator.createCredentials();


        TwentyActsExampleSystemManager exampleManager = new TwentyActsExampleSystemManager(args[0]);
        // Deploy twenty acts contrcts and verifier contracts on blockchain.
        exampleManager.standardExampleConfig(2, deployerCreds);
        CrossControlManagerGroup managerGroup = exampleManager.getCrossControlManagerGroup();

        BlockchainConfig chainA = exampleManager.getRootBcInfo();
        BlockchainConfig chainB = exampleManager.getBc2Info();

        BigInteger withdrawalWaitTime = BigInteger.valueOf(100);

        // Set-up Chain A
        ERC20Manager depChainAErc20 =
                new ERC20Manager(deployerCreds, chainA.bcId, chainA.uri, chainA.gasPriceStrategy, chainA.period);
        TwentyActsManager depChainA20Acts = (TwentyActsManager) managerGroup.getCbcManager(chainA.bcId);
        depChainA20Acts.setWithdrawalTime(withdrawalWaitTime);
        depChainA20Acts.setInfBenficiary(infCreds.getAddress());

        String chainAName = "DAI";
        String chainASymbol = "DAI";
        BigInteger chainAInitialSupply = BigInteger.valueOf(1000);
        depChainAErc20.deployErc20Contract(chainAName, chainASymbol, chainAInitialSupply, chainAErc20Owner.getAddress());

        // Set-up Chain B
        ERC20Manager depChainBErc20 =
                new ERC20Manager(deployerCreds, chainB.bcId, chainB.uri, chainB.gasPriceStrategy, chainB.period);
        TwentyActsManager depChainB20Acts = (TwentyActsManager) managerGroup.getCbcManager(chainB.bcId);
        depChainB20Acts.setWithdrawalTime(withdrawalWaitTime);
        depChainB20Acts.setInfBenficiary(infCreds.getAddress());

        String chainBName = "DAI";
        String chainBSymbol = "DAI";
        BigInteger chainBInitialSupply = BigInteger.valueOf(1000);
        depChainBErc20.deployErc20Contract(chainBName, chainBSymbol, chainBInitialSupply, chainBErc20Owner.getAddress());


        // Connect the chain A and chain B contracts together so there is a bridge.
        depChainA20Acts.addRemoteErc20(depChainAErc20.getErc20ContractAddress(), chainB.bcId, depChainBErc20.getErc20ContractAddress());

        depChainB20Acts.addRemoteErc20(depChainBErc20.getErc20ContractAddress(), chainA.bcId, depChainAErc20.getErc20ContractAddress());

        // Give some coins to the user on Chain A and the liquidity provider on Chain B.
        ERC20Manager erc20OwnerChainA = depChainAErc20.forUser(chainAErc20Owner);
        erc20OwnerChainA.erc20Faucet(user1.getAddress(), BigInteger.valueOf(100));

        ERC20Manager erc20OwnerChainB = depChainBErc20.forUser(chainBErc20Owner);
        erc20OwnerChainB.erc20Faucet(lp1.getAddress(), BigInteger.valueOf(500));

        // Liquidity provider deposits funds into 20ACTS contract.
        ERC20Manager lp1ChainBErc20 = depChainBErc20.forUser(lp1);
        TwentyActsManager lp1ChainB20Acts = depChainB20Acts.forUser(lp1);
        TwentyActsManager lp1ChainA20Acts = depChainA20Acts.forUser(lp1);
        lp1ChainBErc20.erc20Approve(lp1ChainB20Acts.getCbcContractAddress(), BigInteger.valueOf(500));
        lp1ChainB20Acts.lpDeposit(lp1ChainBErc20.getErc20ContractAddress(), BigInteger.valueOf(500));


        ERC20Manager user1ChainAErc20 = depChainAErc20.forUser(user1);
        TwentyActsManager user1ChainA20Acts = depChainA20Acts.forUser(user1);
        ERC20Manager user1ChainBErc20 = depChainBErc20.forUser(user1);
        TwentyActsManager user1ChainB20Acts = depChainB20Acts.forUser(user1);



        for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
            LOG.info("Execution: {}  *****************", numExecutions);
            StatsHolder.log("Execution: " + numExecutions + " **************************");

            lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(), lp1ChainBErc20.getErc20ContractAddress());

            BigInteger amount = BigInteger.valueOf((long) 10 * (numExecutions + 1));
            BigInteger lpFee = BigInteger.valueOf((numExecutions + 2));
            BigInteger inFee = BigInteger.valueOf((numExecutions + 1));
            BigInteger total = amount.add(lpFee).add(inFee);
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
            lp1ChainB20Acts.finalizeOnTarget(txInfo, prepareOnTargetResponse.getSecond(), signatureOrProof);

        }

        depChainAErc20.shutdown();
        depChainBErc20.shutdown();

        StatsHolder.log("End");
        StatsHolder.print();
    }

}
