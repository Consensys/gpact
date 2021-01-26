/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.test.registrar;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.common.RevertReason;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.common.AnIdentity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;

public class RegistrarVerifySignatureTest extends AbstractRegistrarTest {
  final byte[] plainText = new byte[]{0x00};

  @Test
  public void verifyOneSignature() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData = newSigner.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // This will revert if the signature does not verify
    this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
  }

  @Test
  public void verifyTwoSignatures() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());
    receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner2.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData1 = newSigner1.sign(this.plainText);
    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner1.getAddress());
    signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData1.getR());
    sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData1.getS());
    sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData1.getV()[0]));
    sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    // This will revert if the signature does not verify
    boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
    assert(verified);
  }


  // Put S instead of R. This will give an invalid signature
  @Test
  public void failInvalidSignature() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData = newSigner.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    // Put S instead of R. This will give an invalid signature
    sigR.add(signatureData.getS());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // This will revert if the signature does not verify
    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }

  // newSigner1 is a valid signer, but newSigner2 is not a valid signer
  @Test
  public void failUnregisteredSigner() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    // This will revert as signer2 is has not been registered for the blockchain
    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }

  @Test
  public void failSignersArrayWrongLength() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());
    receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner2.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData1 = newSigner1.sign(this.plainText);
    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner1.getAddress());
    //signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData1.getR());
    sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData1.getS());
    sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData1.getV()[0]));
    sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }

  @Test
  public void failSigRArrayWrongLength() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());
    receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner2.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData1 = newSigner1.sign(this.plainText);
    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner1.getAddress());
    signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData1.getR());
    //sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData1.getS());
    sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData1.getV()[0]));
    sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }

  @Test
  public void failSigSArrayWrongLength() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());
    receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner2.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData1 = newSigner1.sign(this.plainText);
    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner1.getAddress());
    signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData1.getR());
    sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData1.getS());
    //sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData1.getV()[0]));
    sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }

  @Test
  public void failSigVArrayWrongLength() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner1 = new AnIdentity();
    AnIdentity newSigner2 = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner1.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());
    receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner2.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData1 = newSigner1.sign(this.plainText);
    Sign.SignatureData signatureData2 = newSigner2.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner1.getAddress());
    signers.add(newSigner2.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData1.getR());
    sigR.add(signatureData2.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData1.getS());
    sigS.add(signatureData2.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData1.getV()[0]));
    //sigV.add(BigInteger.valueOf(signatureData2.getV()[0]));

    try {
      boolean verified = this.registrarContract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
      throw new Exception("Unexpectedly, no error while verifying. Verified: " + verified);
    } catch (TransactionException ex) {
      System.err.println("Revert reason: " + RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      // ignore
    } catch (org.web3j.tx.exceptions.ContractCallException ex1) {
      // System.err.println("Exception: " + ex1.getMessage());
    }
  }



  // TODO not enough signers
  // TODO one valid and one invalid signature

}
