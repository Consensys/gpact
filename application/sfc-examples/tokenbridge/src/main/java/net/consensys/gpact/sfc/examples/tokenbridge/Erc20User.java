package net.consensys.gpact.sfc.examples.tokenbridge;

import net.consensys.gpact.common.*;
import net.consensys.gpact.sfccbc.SimpleCrossControlManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

    private SimpleCrossControlManagerGroup crossControlManagerGroup;

    protected Erc20User(
            String name,
            BlockchainId bcIdA, String erc20AddressA,
            BlockchainId bcIdB, String erc20AddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();
        this.addressOfERC20OnBlockchainA = erc20AddressA;
        this.addressOfERC20OnBlockchainB = erc20AddressB;

        this.bcIdA = bcIdA;
        this.bcIdB = bcIdB;
    }

    // TODO
//
//    public void createCbcManager(
//            BlockchainInfo bcInfoA, List<String> infrastructureContractAddressOnBcA, MessagingVerificationInterface msgVerA,
//            BlockchainInfo bcInfoB, List<String> infrastructureContractAddressOnBcB,  MessagingVerificationInterface msgVerB) throws Exception {
//        this.crossControlManagerGroup = new CrossControlManagerGroup();
//        this.crossControlManagerGroup.addBlockchainAndLoadContracts(this.creds, bcInfoA, infrastructureContractAddressOnBcA, msgVerA);
//        this.crossControlManagerGroup.addBlockchainAndLoadContracts(this.creds, bcInfoB, infrastructureContractAddressOnBcB, msgVerB);
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public String getAddress() {
//        return this.creds.getAddress();
//    }
//
//
//
//    public void transfer(boolean fromAToB, int amountInt) throws Exception {
//        LOG.info(" Transfer: {}: {}: {} tokens ", this.name, fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA", amountInt);
//
//        BigInteger amount = BigInteger.valueOf(amountInt);
//        BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
//        BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
//        String destinationContractAddress = fromAToB ? this.addressOfERC20OnBlockchainB : this.addressOfERC20OnBlockchainA;
//        String sourceContractAddress = fromAToB ? this.addressOfERC20OnBlockchainA : this.addressOfERC20OnBlockchainB;
//
//        // Build the call execution tree.
//        CrosschainERC20 dummy = CrosschainERC20.load(null, null, this.creds, null);
//        String rlpRoot = dummy.getRLP_crosschainTransfer(destinationBlockchainId.asBigInt(), this.creds.getAddress(), amount);
//        String rlpSegment = dummy.getRLP_crosschainReceiver(this.creds.getAddress(), amount);
//
//        CallExecutionTree seg = new CallExecutionTree(destinationBlockchainId, destinationContractAddress, rlpSegment);
//        ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
//        rootCalls1.add(seg);
//        CallExecutionTree root = new CallExecutionTree(sourceBlockchainId, sourceContractAddress, rlpRoot, rootCalls1);
//        byte[] encoded = root.encode();
//        LOG.info(CallExecutionTree.dump(encoded));
//
//        CrosschainExecutor executor = new CrosschainExecutor(this.crossControlManagerGroup);
//        // Note: There is no point using a parallel execution engine: there is nothing to execute in parallel!
//        ExecutionEngine executionEngine = new SerialExecutionEngine(executor);
//        boolean success = executionEngine.execute(root, 300);
//
//        LOG.info("Success: {}", success);
//
//        if (!success) {
//            throw new Exception("Crosschain Execution failed. See log for details");
//        }
//    }
//
//

}
