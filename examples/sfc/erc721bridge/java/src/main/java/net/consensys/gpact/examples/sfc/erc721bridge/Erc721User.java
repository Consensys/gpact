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
package net.consensys.gpact.examples.sfc.erc721bridge;

import java.math.BigInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.CrosschainCallResult;
import net.consensys.gpact.helpers.CredentialsCreator;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.soliditywrappers.examples.sfc.erc721bridge.ERC721PresetMinterPauserAutoId;
import net.consensys.gpact.soliditywrappers.examples.sfc.erc721bridge.SfcErc721Bridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

/** An owner of ERC 20 tokens transferring tokens from one blockchain to another. */
public class Erc721User {
  private static final Logger LOG = LogManager.getLogger(Erc721User.class);

  private final String name;

  protected Credentials creds;

  private final BlockchainId bcIdA;
  private final BlockchainId bcIdB;

  private final String addressOfERC721OnBlockchainA;
  private final String addressOfERC721OnBlockchainB;

  private final String addressOfBridgeOnBlockchainA;
  private final String addressOfBridgeOnBlockchainB;

  private BlockchainConfig bcInfoA;
  private BlockchainConfig bcInfoB;

  private CrossControlManagerGroup crossControlManagerGroup;

  protected Erc721User(
      String name,
      BlockchainId bcIdA,
      String erc721AddressA,
      String erc721BridgeAddressA,
      BlockchainId bcIdB,
      String erc721AddressB,
      String erc721BridgeAddressB)
      throws Exception {

    this.name = name;
    this.creds = CredentialsCreator.createCredentials();
    this.addressOfERC721OnBlockchainA = erc721AddressA;
    this.addressOfERC721OnBlockchainB = erc721AddressB;
    this.addressOfBridgeOnBlockchainA = erc721BridgeAddressA;
    this.addressOfBridgeOnBlockchainB = erc721BridgeAddressB;

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

  public void transfer(boolean fromAToB, BigInteger tokenId) throws Exception {
    transfer(fromAToB, this.creds.getAddress(), tokenId);
  }

  public void transfer(boolean fromAToB, String recipient, BigInteger tokenId) throws Exception {
    LOG.info(
        " Transfer: {}: {}: {} tokens ",
        this.name,
        fromAToB ? "ChainA -> ChainB" : "ChainB -> ChainA",
        tokenId);

    BlockchainId destinationBlockchainId = fromAToB ? this.bcIdB : this.bcIdA;
    BlockchainId sourceBlockchainId = fromAToB ? this.bcIdA : this.bcIdB;
    String sourceERC721ContractAddress =
        fromAToB ? this.addressOfERC721OnBlockchainA : this.addressOfERC721OnBlockchainB;
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
    // NOTE: Both ERC 721 implementations have the required functions.
    // Hence, either can be used.
    ERC721PresetMinterPauserAutoId erc721 =
        ERC721PresetMinterPauserAutoId.load(sourceERC721ContractAddress, web3j, tm, gasProvider);
    TransactionReceipt txR;
    try {
      txR = erc721.approve(sourceBridgeContractAddress, tokenId).send();
    } catch (TransactionException ex) {
      // Crosschain Control Contract reverted
      String revertReason =
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
      LOG.error(" Revert Reason: {}", revertReason);
      throw ex;
    }
    StatsHolder.logGas("Approve", txR.getGasUsed());

    // Step 2: Do the crosschain transaction.
    SfcErc721Bridge sfcErc721Bridge =
        SfcErc721Bridge.load(sourceBridgeContractAddress, web3j, tm, gasProvider);
    LOG.info(
        " Call: BcId: {}, ERC 721 Bridge: {}", sourceBlockchainId, sourceBridgeContractAddress);

    String abiFuncCall =
        sfcErc721Bridge.getABI_transferToOtherBlockchain(
            destinationBlockchainId.asBigInt(),
            sourceERC721ContractAddress,
            recipient,
            tokenId,
            Strings.EMPTY.getBytes());

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
