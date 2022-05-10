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
package net.consensys.gpact.examples.sfc.erc20bridge;

import java.math.BigInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.soliditywrappers.examples.sfc.erc20bridge.ERC20PresetFixedSupply;
import net.consensys.gpact.soliditywrappers.examples.sfc.erc20bridge.SfcErc20Bridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

/** An owner of ERC 20 tokens transferring tokens from one blockchain to another. */
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

  private BlockchainConfig bcInfoA;
  private BlockchainConfig bcInfoB;

  private CrossControlManagerGroup crossControlManagerGroup;

  protected Erc20User(
      String name,
      BlockchainId bcIdA,
      String erc20AddressA,
      String erc20BridgeAddressA,
      BlockchainId bcIdB,
      String erc20AddressB,
      String erc20BridgeAddressB)
      throws Exception {

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
      BlockchainConfig bcInfoA,
      String cbcContractAddressOnBcA,
      MessagingVerificationInterface msgVerA,
      BlockchainConfig bcInfoB,
      String cbcContractAddressOnBcB,
      MessagingVerificationInterface msgVerB)
      throws Exception {
    this.crossControlManagerGroup =
        CrosschainProtocols.getFunctionCallInstance(CrosschainProtocols.SFC).get();
    this.crossControlManagerGroup.addBlockchainAndLoadCbcContract(
        this.creds, bcInfoA, cbcContractAddressOnBcA, msgVerA);
    this.crossControlManagerGroup.addBlockchainAndLoadCbcContract(
        this.creds, bcInfoB, cbcContractAddressOnBcB, msgVerB);

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
    LOG.info(
        " Transfer: {}: {}: {} tokens ",
        this.name,
        fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA",
        amountInt);

    BigInteger amount = BigInteger.valueOf(amountInt);
    BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
    BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
    String sourceERC20ContractAddress =
        fromAToB ? this.addressOfERC20OnBlockchainA : this.addressOfERC20OnBlockchainB;
    String sourceBridgeContractAddress =
        fromAToB ? this.addressOfBridgeOnBlockchainA : this.addressOfBridgeOnBlockchainB;

    BlockchainConfig bcInfo = fromAToB ? this.bcInfoA : this.bcInfoB;

    final int RETRY = 20;
    Web3j web3j =
        Web3j.build(
            new HttpService(bcInfo.blockchainNodeRpcUri),
            bcInfo.period,
            new ScheduledThreadPoolExecutor(5));
    TransactionReceiptProcessor txrProcessor =
        new PollingTransactionReceiptProcessor(web3j, bcInfo.period, RETRY);
    FastTxManager tm =
        TxManagerCache.getOrCreate(web3j, this.creds, sourceBlockchainId.asLong(), txrProcessor);
    DynamicGasProvider gasProvider =
        new DynamicGasProvider(web3j, bcInfo.blockchainNodeRpcUri, bcInfo.gasPriceStrategy);

    // Step 1: Approve of the bridge contract using some of the user's tokens.
    LOG.info("Approve");
    ERC20PresetFixedSupply erc20 =
        ERC20PresetFixedSupply.load(sourceERC20ContractAddress, web3j, tm, gasProvider);
    TransactionReceipt txR;
    try {
      txR = erc20.approve(sourceBridgeContractAddress, amount).send();
    } catch (TransactionException ex) {
      // Crosschain Control Contract reverted
      String revertReason =
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
      LOG.error(" Revert Reason: {}", revertReason);
      throw ex;
    }
    StatsHolder.logGas("Approve", txR.getGasUsed());

    // Step 2: Do the crosschain transaction.
    SfcErc20Bridge sfcErc20Bridge =
        SfcErc20Bridge.load(sourceBridgeContractAddress, web3j, tm, gasProvider);
    LOG.info(" Call: BcId: {}, ERC20 Bridge: {}", sourceBlockchainId, sourceBridgeContractAddress);

    String abiFuncCall =
        sfcErc20Bridge.getABI_transferToOtherBlockchain(
            destinationBlockchainId.asBigInt(), sourceERC20ContractAddress, recipient, amount);

    CallExecutionTree rootCall =
        new CallExecutionTree(sourceBlockchainId, sourceBridgeContractAddress, abiFuncCall);

    CrosschainCallResult result =
        crossControlManagerGroup.executeCrosschainCall(CrosschainProtocols.SERIAL, rootCall, 300);

    LOG.info("Success: {}", result.isSuccessful());
    if (!result.isSuccessful()) {
      LOG.error("Crosschain Execution failed. See log for details");
      String errorMsg = result.getAdditionalErrorInfo();
      if (errorMsg != null) {
        LOG.error("Error information: {}", errorMsg);
      }
      TransactionReceipt rootTxr = result.getTransactionReceipt(CrosschainCallResult.ROOT_CALL);
      LOG.error("Root Call Transaction Receipt: {}", rootTxr.toString());
      TransactionReceipt firstCallTxr =
          result.getTransactionReceipt(CrosschainCallResult.FIRST_CALL);
      LOG.error("Called function Transaction Receipt: {}", firstCallTxr.toString());
      throw new Exception("Crosschain Execution failed. See log for details");
    }
  }
}
