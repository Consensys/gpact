package net.consensys.gpact.examples.tokenbridge;

import net.consensys.gpact.appcontracts.erc20.soliditywrappers.CrosschainERC20;
import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.cbc.engine.*;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static net.consensys.gpact.cbc.CallGraphHelper.createLeafFunctionCall;
import static net.consensys.gpact.cbc.CallGraphHelper.createRootFunctionCall;

public class Erc20User {
    private static final Logger LOG = LogManager.getLogger(Erc20User.class);

    protected static final int RETRY = 20;

    private String name;

    protected Credentials creds;

    private BigInteger bcIdA;
    private Web3j web3jA;
    private CrosschainERC20 crosschainERC20A;

    private BigInteger bcIdB;
    private Web3j web3jB;
    private CrosschainERC20 crosschainERC20B;

    private CbcManager cbcManager;

    protected Erc20User(
            String name,
            String bcIdStrA, String uriA, String gasPriceStrategyA, String blockPeriodA, String erc20AddressA,
            String bcIdStrB, String uriB, String gasPriceStrategyB, String blockPeriodB, String erc20AddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();

        this.bcIdA = new BigInteger(bcIdStrA, 16);
        int pollingIntervalA = Integer.parseInt(blockPeriodA);
        this.web3jA = Web3j.build(new HttpService(uriA), pollingIntervalA, new ScheduledThreadPoolExecutor(5));
        TransactionManager tmA = new RawTransactionManager(this.web3jA, this.creds, this.bcIdA.longValue(), RETRY, pollingIntervalA);
        ContractGasProvider gasProviderA = new DynamicGasProvider(this.web3jA, uriA, gasPriceStrategyA);
        this.crosschainERC20A = CrosschainERC20.load(erc20AddressA, this.web3jA, tmA, gasProviderA);

        this.bcIdB = new BigInteger(bcIdStrB, 16);
        int pollingIntervalB = Integer.parseInt(blockPeriodB);
        this.web3jB = Web3j.build(new HttpService(uriB), pollingIntervalB, new ScheduledThreadPoolExecutor(5));
        TransactionManager tmB = new RawTransactionManager(this.web3jB, this.creds, this.bcIdB.longValue(), RETRY, pollingIntervalB);
        ContractGasProvider gasProviderB = new DynamicGasProvider(this.web3jB, uriB, gasPriceStrategyB);
        this.crosschainERC20B = CrosschainERC20.load(erc20AddressB, this.web3jB, tmB, gasProviderB);
    }

    public void shutdown() {
        this.web3jA.shutdown();
        this.web3jB.shutdown();
    }

    public void createCbcManager(
            CrossBlockchainConsensusType consensusMethodology,
            PropertiesLoader.BlockchainInfo bcInfoA, String cbcContractAddressOnBcA,
            PropertiesLoader.BlockchainInfo bcInfoB, String cbcContractAddressOnBcB,
            AnIdentity globalSigner) throws Exception {
        this.cbcManager = new CbcManager(consensusMethodology);
        this.cbcManager.addBlockchain(this.creds, bcInfoA, cbcContractAddressOnBcA);
        this.cbcManager.addBlockchain(this.creds, bcInfoB, cbcContractAddressOnBcB);
        this.cbcManager.addSignerOnAllBlockchains(globalSigner);
    }


    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.creds.getAddress();
    }



    public void transfer(boolean fromAToB, int amountInt) throws Exception {
        LOG.info(" Transfer: {}: {}: {} tokens ", this.name, fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA", amountInt);

        BigInteger amount = BigInteger.valueOf(amountInt);
        BigInteger destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
        BigInteger sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
        String destinationContractAddress = fromAToB ? this.crosschainERC20B.getContractAddress() : this.crosschainERC20A.getContractAddress();
        String sourceContractAddress = fromAToB ? this.crosschainERC20A.getContractAddress() : this.crosschainERC20B.getContractAddress();

        // Build the call execution tree.
        String rlpRoot = this.crosschainERC20A.getRLP_crosschainTransfer(destinationBlockchainId, this.creds.getAddress(), amount);
        String rlpSegment = this.crosschainERC20A.getRLP_crosschainReceiver(this.creds.getAddress(), amount);

        RlpList segment = createLeafFunctionCall(destinationBlockchainId, destinationContractAddress, rlpSegment);
        List<RlpType> rootCalls = new ArrayList<>();
        rootCalls.add(segment);
        RlpList callTree = createRootFunctionCall(sourceBlockchainId, sourceContractAddress, rlpRoot, rootCalls);

        AbstractCbcExecutor executor = new CbcExecutorSignedEvents(this.cbcManager);

        ExecutionEngine executionEngine = new SerialExecutionEngine(executor);
        boolean success = executionEngine.execute(callTree, 300);

        LOG.info("Success: {}", success);

        if (!success) {
            throw new Exception("Crosschain Execution failed. See log for details");
        }
    }



}
