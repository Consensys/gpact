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

import static net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager.TXSTATE_IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.consensys.gpact.applications.twentyacts.crosscontrol.TwentyActsManager;
import net.consensys.gpact.applications.twentyacts.helpers.TxInfoDigest;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.common.Tuple;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.soliditywrappers.applications.twentyacts.TwentyActs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.rlp.*;
import org.web3j.utils.Numeric;

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

      // lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(),
      // lp1ChainBErc20.getErc20ContractAddress());

      int amountI = 10 * (numExecutions + 1);
      int lpFeeI = (numExecutions + 2);
      int inFeeI = (numExecutions + 1);
      int totalI = amountI + lpFeeI + inFeeI;

      BigInteger amount = BigInteger.valueOf(amountI);
      BigInteger lpFee = BigInteger.valueOf(lpFeeI);
      BigInteger inFee = BigInteger.valueOf(inFeeI);
      BigInteger total = BigInteger.valueOf(totalI);
      LOG.info("Amount: {}, LP Fee: {}, Inf Fee: {}, Total: {}", amount, lpFee, inFee, total);

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

      TwentyActs.TxInfo txInfo =
          new TwentyActs.TxInfo(
              crosschainTxId,
              sourceBcId,
              sourceErc20Address,
              targetBcId,
              targetErc20Address,
              sender,
              recipient,
              liquidityProvider,
              amount,
              lpFee,
              inFee,
              biddingPeriodEnd,
              timeoutInSeconds);
      byte[] txInfoDigest = TxInfoDigest.txInfoDigest(txInfo);

      // user1ChainAErc20.erc20Approve(user1ChainA20Acts.getCbcContractAddress(), total);
      // Create the ERC 20 Approve with the user account (credentials and nonce).
      String signedErc20Approve =
          this.user1ChainAErc20.createSpecialErc20Approve(
              user1ChainA20Acts.getCbcContractAddress(), total, txInfoDigest);
      // The ERC 20 Approve is communicated to the LP.
      // Submit the signed transaction
      this.lp1ChainAErc20.sendRawTransaction(signedErc20Approve);
      verifyTransactionSignature(this.user1, signedErc20Approve, chainA.bcId.asBigInt());

      // Prepare on Target
      Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnTargetEventResponse>
          prepareOnTargetResponse = lp1ChainB20Acts.prepareOnTarget(txInfo);
      if (!prepareOnTargetResponse.getFirst().isStatusOK()) {
        throw new Exception("Prepare On Target Failed");
      }
      // lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(),
      // lp1ChainBErc20.getErc20ContractAddress());
      checkHoldings(lp1ChainB20Acts, lp1Account, erc20B, lp1DepB, amountI, 0);

      // TODO ***** remove encoded from event!
      // TODO is the problem the addresses should be encoded into 32 bytes, and not 20
      assertTrue(Arrays.equals(prepareOnTargetResponse.getThird()._txInfoDigest, txInfoDigest));

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
      Tuple<TransactionReceipt, byte[], TwentyActs.PrepareOnSourceEventResponse>
          prepareOnSourceResponse =
              lp1ChainA20Acts.prepareOnSource(
                  txInfo, prepareOnTargetResponse.getSecond(), signatureOrProof);
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
      Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnTargetEventResponse>
          finalizeOnTargetResponse =
              lp1ChainB20Acts.finalizeOnTarget(
                  txInfo, prepareOnSourceResponse.getSecond(), signatureOrProof);
      if (!finalizeOnTargetResponse.getFirst().isStatusOK()) {
        throw new Exception("Finalize On Target Failed");
      }
      // lp1ChainB20Acts.showLiquidityProviderHoldings(lp1.getAddress(),
      // lp1ChainBErc20.getErc20ContractAddress());
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
      Tuple<TransactionReceipt, byte[], TwentyActs.FinalizeOnSourceEventResponse>
          finalizeOnSourceResponse =
              lp1ChainA20Acts.finalizeOnSource(
                  txInfo, finalizeOnTargetResponse.getSecond(), signatureOrProof);
      if (!finalizeOnSourceResponse.getFirst().isStatusOK()) {
        throw new Exception("Finalize On Target Failed");
      }
      // lp1ChainA20Acts.showLiquidityProviderHoldings(lp1.getAddress(),
      // lp1ChainBErc20.getErc20ContractAddress());
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

  // The code below analyses how to verify a transaction in code. This is in preparation for
  // implementing this in Solidity
  public void verifyTransactionSignature(Credentials credentials, String tx, BigInteger chainId)
      throws Exception {
    LOG.info("Tx: {}", tx);
    Bytes txBytes = Bytes.of(FormatConversion.hexStringToByteArray(tx));
    LOG.info("Tx: {}", txBytes.toHexString());

    SignedRawTransaction signedRawTx = (SignedRawTransaction) TransactionDecoder.decode(tx);
    LOG.info("R: {}", Bytes.wrap(signedRawTx.getSignatureData().getR()).toHexString());
    LOG.info("S: {}", Bytes.wrap(signedRawTx.getSignatureData().getS()).toHexString());
    LOG.info("V: {}", Bytes.wrap(signedRawTx.getSignatureData().getV()).toHexString());
    RawTransaction rawTx =
        RawTransaction.createTransaction(
            signedRawTx.getNonce(),
            signedRawTx.getGasPrice(),
            signedRawTx.getGasLimit(),
            signedRawTx.getTo(),
            signedRawTx.getValue(),
            signedRawTx.getData());
    byte[] unsignedRawTx = TransactionEncoder.encode(rawTx);
    Bytes unsignedTxBytes = Bytes.of(unsignedRawTx);
    LOG.info("Tx[ {}", unsignedTxBytes);

    LOG.info("Len Signed Tx: {}, RawTx: {}", txBytes.size(), unsignedRawTx.length);
    LOG.info(
        "Ofs[1]: Signed Tx: {}, RawTx: {}", txBytes.get(1) & 0xff, unsignedTxBytes.get(1) & 0xff);

    final int SIG_LEN = 67;
    byte v = txBytes.get(txBytes.size() - SIG_LEN);
    final int DISTANCE_R_LEN_FROM_END = 66;
    final int DISTANCE_S_LEN_FROM_END = 33;
    final int RLP_ENCODED_LEN = 0xA0;
    final int WORD_SIZE = 32;

    int lenOfR = txBytes.get(txBytes.size() - DISTANCE_R_LEN_FROM_END) & 0xff;
    Bytes r = txBytes.slice(txBytes.size() - DISTANCE_R_LEN_FROM_END + 1, WORD_SIZE);
    int lenOfS = txBytes.get(txBytes.size() - DISTANCE_R_LEN_FROM_END) & 0xff;
    Bytes s = txBytes.slice(txBytes.size() - DISTANCE_S_LEN_FROM_END + 1, WORD_SIZE);

    LOG.info("V: {}", v & 0xff);
    LOG.info("Len of R: {}", lenOfR);
    LOG.info("Len of S: {}", lenOfS);
    LOG.info("R: {}", r.toHexString());
    LOG.info("S: {}", s.toHexString());

    byte[] signTx = FormatConversion.hexStringToByteArray(tx);

    byte transactionType = signTx[0];
    final int EIP1559_TYPE = 2;
    if (transactionType == EIP1559_TYPE) {
      throw new Exception("EIP1559 transactions not supported yet");
    }

    final RlpList rlpList = RlpDecoder.decode(signTx);
    final RlpList values = (RlpList) rlpList.getValues().get(0);
    final BigInteger nonce = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
    final BigInteger gasPrice = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
    final BigInteger gasLimit = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
    final String to = ((RlpString) values.getValues().get(3)).asString();
    final BigInteger value = ((RlpString) values.getValues().get(4)).asPositiveBigInteger();
    final String data = ((RlpString) values.getValues().get(5)).asString();
    final byte[] v1 = ((RlpString) values.getValues().get(6)).getBytes();
    final byte[] r1 =
        Numeric.toBytesPadded(
            Numeric.toBigInt(((RlpString) values.getValues().get(7)).getBytes()), 32);
    final byte[] s1 =
        Numeric.toBytesPadded(
            Numeric.toBigInt(((RlpString) values.getValues().get(8)).getBytes()), 32);

    final int CHAIN_ID_INC = 35;
    final int LOWER_REAL_V = 27;

    // Remove EIP 155 manipulation of v
    BigInteger v2 = new BigInteger(1, v1);
    BigInteger v2a = v2.subtract(BigInteger.valueOf(CHAIN_ID_INC));
    BigInteger doubleChainId = chainId.multiply(BigInteger.TWO);
    BigInteger v3 = v2a.subtract(doubleChainId);
    BigInteger v4 = v3.add(BigInteger.valueOf(LOWER_REAL_V));
    byte[] v5 = v4.toByteArray();
    assertEquals(v5.length, 1);

    final Sign.SignatureData signatureData = new Sign.SignatureData(v5, r1, s1);

    List<RlpType> result = new ArrayList<>();
    result.add(RlpString.create(nonce));
    result.add(RlpString.create(gasPrice));
    result.add(RlpString.create(gasLimit));
    result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
    result.add(RlpString.create(value));

    // value field will already be hex encoded, so we need to convert into binary first
    result.add(RlpString.create(FormatConversion.hexStringToByteArray(data)));
    result.add(RlpString.create(chainId.toByteArray()));
    result.add(RlpString.create(new byte[] {}));
    result.add(RlpString.create(new byte[] {}));

    // result.add(RlpString.create(chainId));

    RlpList rlpList1 = new RlpList(result);
    byte[] tbs = RlpEncoder.encode(rlpList1);
    LOG.info("Bytes tbs: {}", Bytes.wrap(tbs).toHexString());
    byte[] messageHash = Hash.sha3(tbs);

    BigInteger pubKey = Sign.signedMessageHashToKey(messageHash, signatureData);
    LOG.info("Recovered pub key: {}", pubKey.toString(16));
    LOG.info("Pub Key: {}", credentials.getEcKeyPair().getPublicKey().toString(16));
  }
}
