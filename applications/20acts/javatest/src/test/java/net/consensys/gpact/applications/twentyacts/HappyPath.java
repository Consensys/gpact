package net.consensys.gpact.applications.twentyacts;

import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.helpers.TwentyActsExampleSystemManager;
import net.consensys.gpact.messaging.MessagingManagerGroupInterface;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
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

        TwentyActsExampleSystemManager exampleManager = new TwentyActsExampleSystemManager(args[0]);
        exampleManager.standardExampleConfig(2);
        MessagingManagerGroupInterface messagingManagerGroup = exampleManager.getMessagingManagerGroup();


        BlockchainConfig chainA = exampleManager.getRootBcInfo();
        BlockchainConfig chainB = exampleManager.getBc2Info();


        Credentials deployerCreds = CredentialsCreator.createCredentials();
        Credentials infCreds = CredentialsCreator.createCredentials();
        Credentials chainAErc20Owner = CredentialsCreator.createCredentials();
        Credentials chainBErc20Owner = CredentialsCreator.createCredentials();
        Credentials user1 = CredentialsCreator.createCredentials();
        Credentials receipient1 = CredentialsCreator.createCredentials();
        Credentials lp1 = CredentialsCreator.createCredentials();



        BigInteger withdrawalWaitTime = BigInteger.valueOf(100);

        // Set-up Chain A
        BlockchainContracts depChainA =
                new BlockchainContracts(deployerCreds, chainA.bcId, chainA.uri, chainA.gasPriceStrategy, chainA.period);
        depChainA.deploy20ActsContract(withdrawalWaitTime, infCreds.getAddress());

        String chainAName = "DAI";
        String chainASymbol = "DAI";
        BigInteger chainAInitialSupply = BigInteger.valueOf(1000);
        depChainA.deployErc20Contract(chainAName, chainASymbol, chainAInitialSupply, chainAErc20Owner.getAddress());
        // The verifier on Chain A has been set-up to trust signed events from Chain B.
        depChainA.addVerifier(chainB.bcId, messagingManagerGroup.getVerifierAddress(chainA.bcId));

        // Set-up Chain B
        BlockchainContracts depChainB =
                new BlockchainContracts(deployerCreds, chainB.bcId, chainB.uri, chainB.gasPriceStrategy, chainB.period);
        depChainB.deploy20ActsContract(withdrawalWaitTime, infCreds.getAddress());
        // The verifier on Chain B has been set-up to trust signed events from Chain A.
        depChainB.addVerifier(chainA.bcId, messagingManagerGroup.getVerifierAddress(chainB.bcId));

        String chainBName = "DAI";
        String chainBSymbol = "DAI";
        BigInteger chainBInitialSupply = BigInteger.valueOf(1000);
        depChainB.deployErc20Contract(chainBName, chainBSymbol, chainBInitialSupply, chainBErc20Owner.getAddress());


        // Connect the chain A and chain B contracts together so there is a bridge.
        depChainA.addRemote20Acts(chainB.bcId, depChainB.get20ActsContractAddress());
        depChainA.addRemoteErc20(chainB.bcId, depChainB.getErc20ContractAddress());

        depChainB.addRemote20Acts(chainA.bcId, depChainA.get20ActsContractAddress());
        depChainB.addRemoteErc20(chainA.bcId, depChainA.getErc20ContractAddress());

        // Give some coins to the user on Chain A and the liquidity provider on Chain B.
        BlockchainContracts erc20OwnerChainA = depChainA.forUser(chainAErc20Owner);
        erc20OwnerChainA.erc20Faucet(user1.getAddress(), BigInteger.valueOf(100));

        BlockchainContracts erc20OwnerChainB = depChainB.forUser(chainBErc20Owner);
        erc20OwnerChainB.erc20Faucet(lp1.getAddress(), BigInteger.valueOf(500));

        // Liquidity provider deposits funds into 20ACTS contract.
        BlockchainContracts lp1ChainB = depChainB.forUser(lp1);
        lp1ChainB.erc20Approve(lp1ChainB.get20ActsContractAddress(), BigInteger.valueOf(500));
        lp1ChainB.lpDeposit(BigInteger.valueOf(500));


        BlockchainContracts user1ChainA = depChainA.forUser(user1);
        BlockchainContracts user1ChainB = depChainB.forUser(user1);

        for (int numExecutions = 0; numExecutions < NUM_TIMES_EXECUTE; numExecutions++) {
            LOG.info("Execution: {}  *****************", numExecutions);
            StatsHolder.log("Execution: " + numExecutions + " **************************");

            lp1ChainB.showLiquidityProviderHoldings(lp1.getAddress());

            BigInteger amount = BigInteger.valueOf((long) 10 * (numExecutions + 1));
            BigInteger lpFee = BigInteger.valueOf((numExecutions + 2));
            BigInteger inFee = BigInteger.valueOf((numExecutions + 1));
            BigInteger total = amount.add(lpFee).add(inFee);
            LOG.info("Amount: {}, LP Fee: {}, Inf Fee: {}, Total: {}", amount, lpFee, inFee, total);

            user1ChainA.erc20Approve(user1ChainA.get20ActsContractAddress(), total);


            BigInteger crosschainTxId = BigInteger.probablePrime(256, new Random());
            BigInteger sourceBcId = chainA.bcId.asBigInt();
            String sourceErc20Address = user1ChainA.getErc20ContractAddress();
            BigInteger targetBcId = chainB.bcId.asBigInt();
            String targetErc20Address = user1ChainB.getErc20ContractAddress();
            String sender = user1.getAddress();
            String recipient = receipient1.getAddress();
            String liquidityProvider = lp1.getAddress();
            BigInteger biddingPeriodEnd = BigInteger.ZERO;
            BigInteger timeoutInSeconds = BigInteger.valueOf((System.currentTimeMillis() / 1000) + 300);

            TwentyActs.TxInfo txInfo = new TwentyActs.TxInfo(
                    crosschainTxId, sourceBcId, sourceErc20Address, targetBcId, targetErc20Address,
                    sender, recipient, liquidityProvider, amount, lpFee, inFee, biddingPeriodEnd, timeoutInSeconds
            );

            TransactionReceipt txR = lp1ChainB.prepareOnTarget(txInfo);



        }

        depChainA.shutdown();
        depChainB.shutdown();

        StatsHolder.log("End");
        StatsHolder.print();
    }

}
