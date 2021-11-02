package net.consensys.gpact.examples.tokenbridge;

import net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers.GpactERC20Bridge;
import net.consensys.gpact.appcontracts.atomic.erc20.soliditywrappers.LockableERC20PresetFixedSupply;
import net.consensys.gpact.cbc.CrossControlManagerGroup;
import net.consensys.gpact.cbc.CrosschainExecutor;
import net.consensys.gpact.cbc.calltree.CallExecutionTree;
import net.consensys.gpact.cbc.engine.*;
import net.consensys.gpact.common.*;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Erc20User {
    private static final Logger LOG = LogManager.getLogger(Erc20User.class);

    // protected static final int RETRY = 20;

    private final String name;

    protected Credentials creds;

    private final BlockchainId bcIdA;
    private final BlockchainId bcIdB;

    private final String addressOfERC20BridgeBcA;
    private final String addressOfERC20BridgeBcB;
    private final String addressOfERC20BcA;
    private final String addressOfERC20BcB;

    private CrossControlManagerGroup crossControlManagerGroup;

    private BlockchainInfo bcInfoA;
    private BlockchainInfo bcInfoB;

    protected Erc20User(
            String name,
            BlockchainId bcIdA, String erc20BridgeAddressA, String erc20AddressA,
            BlockchainId bcIdB, String erc20BridgeAddressB, String erc20AddressB) throws Exception {

        this.name = name;
        this.creds = CredentialsCreator.createCredentials();
        this.addressOfERC20BridgeBcA = erc20BridgeAddressA;
        this.addressOfERC20BridgeBcB = erc20BridgeAddressB;
        this.addressOfERC20BcA = erc20AddressA;
        this.addressOfERC20BcB = erc20AddressB;

        this.bcIdA = bcIdA;
        this.bcIdB = bcIdB;
    }

    public void createCbcManager(
            BlockchainInfo bcInfoA, List<String> infrastructureContractAddressOnBcA, MessagingVerificationInterface msgVerA,
            BlockchainInfo bcInfoB, List<String> infrastructureContractAddressOnBcB,  MessagingVerificationInterface msgVerB) throws Exception {
        this.crossControlManagerGroup = new CrossControlManagerGroup();
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
        LOG.info(" Transfer: {}: {}: {} tokens ", this.name, fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA", amountInt);

        BigInteger amount = BigInteger.valueOf(amountInt);
        BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
        BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
        String destinationBridgeContractAddress = fromAToB ? this.addressOfERC20BridgeBcB : this.addressOfERC20BridgeBcA;
        String sourceBridgeContractAddress = fromAToB ? this.addressOfERC20BridgeBcA : this.addressOfERC20BridgeBcB;
        String destinationERC20ContractAddress = fromAToB ? this.addressOfERC20BcB : this.addressOfERC20BcA;
        String sourceERC20ContractAddress = fromAToB ? this.addressOfERC20BcA : this.addressOfERC20BcB;

        BlockchainInfo bcInfo = fromAToB ? this.bcInfoA : this.bcInfoB;

        final int RETRY = 20;
        Web3j web3j = Web3j.build(new HttpService(bcInfo.uri), bcInfo.period, new ScheduledThreadPoolExecutor(5));
        TransactionReceiptProcessor txrProcessor = new PollingTransactionReceiptProcessor(web3j, bcInfo.period, RETRY);
        FastTxManager tm = TxManagerCache.getOrCreate(web3j, this.creds, sourceBlockchainId.asLong(), txrProcessor);
        DynamicGasProvider gasProvider = new DynamicGasProvider(web3j, bcInfo.uri, bcInfo.gasPriceStrategy);


        // Step 1: Approve of the bridge contract using some of the user's tokens.
        LOG.info("Approve");
        LockableERC20PresetFixedSupply erc20 = LockableERC20PresetFixedSupply.load(sourceERC20ContractAddress, web3j, tm, gasProvider);
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


        // Build the call execution tree.
        GpactERC20Bridge dummy = GpactERC20Bridge.load(null, null, this.creds, null);
        String rlpRoot = dummy.getRLP_transferToOtherBlockchain(destinationBlockchainId.asBigInt(), sourceERC20ContractAddress, this.creds.getAddress(), amount);
        String rlpSegment = dummy.getRLP_receiveFromOtherBlockchain(destinationERC20ContractAddress, this.creds.getAddress(), amount);

        CallExecutionTree seg = new CallExecutionTree(destinationBlockchainId, destinationBridgeContractAddress, rlpSegment);
        ArrayList<CallExecutionTree> rootCalls1 = new ArrayList<>();
        rootCalls1.add(seg);
        CallExecutionTree root = new CallExecutionTree(sourceBlockchainId, sourceBridgeContractAddress, rlpRoot, rootCalls1);
        byte[] encoded = root.encode();
        LOG.info(CallExecutionTree.dump(encoded));

        CrosschainExecutor executor = new CrosschainExecutor(this.crossControlManagerGroup);
        // Note: There is no point using a parallel execution engine: there is nothing to execute in parallel!
        ExecutionEngine executionEngine = new SerialExecutionEngine(executor);
        boolean success = executionEngine.execute(root, 300);

        LOG.info("Success: {}", success);

        if (!success) {
            throw new Exception("Crosschain Execution failed. See log for details");
        }
    }



}
