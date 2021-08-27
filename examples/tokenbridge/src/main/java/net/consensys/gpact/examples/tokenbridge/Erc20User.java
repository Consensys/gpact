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
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

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

    private final BigInteger bcIdA;
    private final BigInteger bcIdB;

    private final String addressOfCrosschainERCA;
    private final String addressOfCrosschainERCB;

    private CbcManager cbcManager;

    protected Erc20User(
            String name,
            String bcIdStrA, String erc20AddressA,
            String bcIdStrB, String erc20AddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();
        this.addressOfCrosschainERCA = erc20AddressA;
        this.addressOfCrosschainERCB = erc20AddressB;

        this.bcIdA = new BigInteger(bcIdStrA, 16);
        this.bcIdB = new BigInteger(bcIdStrB, 16);
    }

    public void createCbcManager(
            CrossBlockchainConsensusType consensusMethodology,
            PropertiesLoader.BlockchainInfo bcInfoA, List<String> infrastructureContractAddressOnBcA,
            PropertiesLoader.BlockchainInfo bcInfoB, List<String> infrastructureContractAddressOnBcB,
            AnIdentity globalSigner) throws Exception {
        this.cbcManager = new CbcManager(consensusMethodology);
        this.cbcManager.addBlockchain(this.creds, bcInfoA, infrastructureContractAddressOnBcA);
        this.cbcManager.addBlockchain(this.creds, bcInfoB, infrastructureContractAddressOnBcB);
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
        String destinationContractAddress = fromAToB ? this.addressOfCrosschainERCB : this.addressOfCrosschainERCA;
        String sourceContractAddress = fromAToB ? this.addressOfCrosschainERCA : this.addressOfCrosschainERCB;

        // Build the call execution tree.
        CrosschainERC20 dummy = CrosschainERC20.load(null, null, this.creds, null);
        String rlpRoot = dummy.getRLP_crosschainTransfer(destinationBlockchainId, this.creds.getAddress(), amount);
        String rlpSegment = dummy.getRLP_crosschainReceiver(this.creds.getAddress(), amount);

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
