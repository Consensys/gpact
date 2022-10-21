/*
 * Copyright 2019 ConsenSys Software Inc
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
package net.consensys.gpact.messaging.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.crypto.Hash;
import net.consensys.gpact.messaging.fake.FakeRelayer;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;
import org.web3j.tx.exceptions.ContractCallException;

public class RegistrarVerifySignatureTest extends AbstractRegistrarTest {
  final byte[] plainText = new byte[] {0x00};

  @Test
  public void verifyOneSignature() throws Exception {
    final String fixedPrivateKey1 = "1";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));

    checkVerify(signers, signers, 1);
  }

  @Test
  public void verifyTwoSignatures() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));

    checkVerify(signers, signers, 1);
  }

  // Put S instead of R. This will give an invalid signature
  @Test
  public void failInvalidSignature() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey1));
    signers.add(new AnIdentity(fixedPrivateKey2));

    List<AnIdentity> signersToRegister = signers;
    List<AnIdentity> signersToSign = signers;
    int threshold = 1;

    setupWeb3();
    deployRegistrarContract();

    BigInteger bcIdB = BigInteger.TEN;
    BlockchainId bcId = new BlockchainId(bcIdB);
    List<String> signerAddresses = new ArrayList<>();
    for (AnIdentity signer : signersToRegister) {
      signerAddresses.add(signer.getAddress());
    }

    this.registrarContract
        .addSignersSetThreshold(bcIdB, signerAddresses, BigInteger.valueOf(threshold))
        .send();

    FakeRelayer signatureCreator = new FakeRelayer(signersToSign);

    String contractAddress = "6D1e0220914f4fb73aF954694564e77024de3693";
    byte[] eventFunctionSignature =
        Hash.keccak256(Bytes.wrap("FuncEvent(uint256)".getBytes())).toArray();
    // The actual event data will depend on the event emitted.
    byte[] rawEventData = new byte[1];
    byte[] signature =
        signatureCreator.fetchedSignedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);
    byte[] encodedEventData =
        FakeRelayer.abiEncodePackedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);

    // Change the signature
    signature[140] = signature[140] == 0 ? (byte) 1 : 0;

    try {
      // Ignore return value. Exception will be thrown if an error
      this.registrarContract.verify(bcId.asBigInt(), signature, encodedEventData).send();
      throw new Exception("Did not fail");
    } catch (ContractCallException callException) {
      // Ignore expected exception
    }
  }

  // newSigner1 is a valid signer, but newSigner2 is not a valid signer
  @Test
  public void failUnregisteredSigner() throws Exception {
    final String fixedPrivateKey1 = "1";
    final String fixedPrivateKey2 = "2";
    List<AnIdentity> registeredSigners = new ArrayList<>();
    registeredSigners.add(new AnIdentity(fixedPrivateKey1));

    List<AnIdentity> signers = new ArrayList<>();
    signers.add(new AnIdentity(fixedPrivateKey2));

    try {
      checkVerify(registeredSigners, signers, 1);
      throw new Exception("No exception thrown");
    } catch (ContractCallException callException) {
      // Expected exception
    }
  }

  private void checkVerify(
      List<AnIdentity> signersToRegister, List<AnIdentity> signersToSign, int threshold)
      throws Exception {
    setupWeb3();
    deployRegistrarContract();

    BigInteger bcIdB = BigInteger.TEN;
    BlockchainId bcId = new BlockchainId(bcIdB);
    List<String> signerAddresses = new ArrayList<>();
    for (AnIdentity signer : signersToRegister) {
      signerAddresses.add(signer.getAddress());
    }

    this.registrarContract
        .addSignersSetThreshold(bcIdB, signerAddresses, BigInteger.valueOf(threshold))
        .send();

    FakeRelayer signatureCreator = new FakeRelayer(signersToSign);

    String contractAddress = "6D1e0220914f4fb73aF954694564e77024de3693";
    byte[] eventFunctionSignature =
        Hash.keccak256(Bytes.wrap("FuncEvent(uint256)".getBytes())).toArray();
    // The actual event data will depend on the event emitted.
    byte[] rawEventData = new byte[1];
    byte[] signature =
        signatureCreator.fetchedSignedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);
    byte[] encodedEventData =
        FakeRelayer.abiEncodePackedEvent(
            bcId, contractAddress, eventFunctionSignature, rawEventData);

    // Ignore return value. Exception will be thrown if an error
    this.registrarContract.verify(bcId.asBigInt(), signature, encodedEventData).send();
  }
}
