package net.consensys.gpact.examples.tokenbridge;

import net.consensys.gpact.appcontracts.erc20.soliditywrappers.CrosschainERC20;
import net.consensys.gpact.cbc.CbcManager;
import net.consensys.gpact.cbc.calltree.CallExecutionTree;
import net.consensys.gpact.cbc.engine.*;
import net.consensys.gpact.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Erc20User {
    private static final Logger LOG = LogManager.getLogger(Erc20User.class);

    // protected static final int RETRY = 20;

    private final String name;

    protected Credentials creds;

    private final BlockchainId bcIdA;
    private final BlockchainId bcIdB;

    private final String addressOfCrosschainERCA;
    private final String addressOfCrosschainERCB;

    private CbcManager cbcManager;
    private CrossBlockchainConsensusType consensusMethodology;

    protected Erc20User(
            String name,
            BlockchainId bcIdA, String erc20AddressA,
            BlockchainId bcIdB, String erc20AddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();
        this.addressOfCrosschainERCA = erc20AddressA;
        this.addressOfCrosschainERCB = erc20AddressB;

        this.bcIdA = bcIdA;
        this.bcIdB = bcIdB;
    }

    public void createCbcManager(
            CrossBlockchainConsensusType consensusMethodology,
            BlockchainInfo bcInfoA, List<String> infrastructureContractAddressOnBcA,
            BlockchainInfo bcInfoB, List<String> infrastructureContractAddressOnBcB,
            AnIdentity globalSigner) throws Exception {
        this.consensusMethodology = consensusMethodology;
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
        BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
        BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
        String destinationContractAddress = fromAToB ? this.addressOfCrosschainERCB : this.addressOfCrosschainERCA;
        String sourceContractAddress = fromAToB ? this.addressOfCrosschainERCA : this.addressOfCrosschainERCB;

        // Build the call execution tree.
        CrosschainERC20 dummy = CrosschainERC20.load(null, null, this.creds, null);
        String rlpRoot = dummy.getRLP_crosschainTransfer(destinationBlockchainId.asBigInt(), this.creds.getAddress(), amount);
        String rlpSegment = dummy.getRLP_crosschainReceiver(this.creds.getAddress(), amount);

        CallExecutionTree seg = new CallExecutionTree(destinationBlockchainId, destinationContractAddress, rlpSegment);
        ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
        rootCalls1.add(seg);
        CallExecutionTree root = new CallExecutionTree(sourceBlockchainId, sourceContractAddress, rlpRoot, rootCalls1);
        byte[] encoded = root.encode();
        LOG.info(CallExecutionTree.dump(encoded));

        AbstractCbcExecutor executor;
        switch (this.consensusMethodology) {
            case TRANSACTION_RECEIPT_SIGNING:
                executor = new CbcExecutorTxReceiptRootTransfer(this.cbcManager);
                break;
            case EVENT_SIGNING:
                executor = new CbcExecutorSignedEvents(this.cbcManager);
                break;
            default:
                throw new RuntimeException("Not implemented yet");
        }
        ExecutionEngine executionEngine = new SerialExecutionEngine(executor);
        boolean success = executionEngine.execute(root, 300);

        LOG.info("Success: {}", success);

        if (!success) {
            throw new Exception("Crosschain Execution failed. See log for details");
        }
    }



}
