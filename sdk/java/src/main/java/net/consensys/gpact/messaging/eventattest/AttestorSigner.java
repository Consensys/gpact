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
package net.consensys.gpact.messaging.eventattest;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.messaging.MessagingVerificationInterface;
import net.consensys.gpact.messaging.SignedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Manages the interaction between the application library and attestors for a certain blockchain.
 * The class asks an attestor to give the threshold signed event data. The attestor should have
 * cooperated with other attestors already to threshold sign the event data.
 *
 * <p>TODO the Attestor service has not been implemented yet. As such, this class simulates calling
 * out to the Attestor service to get the threshold signed event data.
 */
public class AttestorSigner implements MessagingVerificationInterface {
  static final Logger LOG = LogManager.getLogger(AttestorSigner.class);

  List<AnIdentity> signers = new ArrayList<>();

  BlockchainId bcId;

  public AttestorSigner(BlockchainId blockchainId) {
    this.bcId = blockchainId;
  }

  // TODO this method won't be needed / should be removed once the Attestor service has been
  // created.
  // Perhaps at that point, addSigner should be specifying the URL of an attestor.
  public void addSigner(AnIdentity signer) throws Exception {
    // Add the signer (their private key) to app for the blockchain
    signers.add(signer);
  }

  @Override
  public SignedEvent getSignedEvent(
      List<BlockchainId> targetBlockchainIds,
      TransactionReceipt startTxReceipt,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature)
      throws Exception {

    byte[] encodedEventInformation =
        abiEncodePackedEvent(this.bcId, contractAddress, eventFunctionSignature, eventData);

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<Byte> sigV = new ArrayList<>();
    //    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer : this.signers) {
      Sign.SignatureData signatureData = signer.sign(encodedEventInformation);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(signatureData.getV()[0]);
    }

    byte[] encodedSignatures = abiEncodePackedSignatures(theSigners, sigR, sigS, sigV);
    return new SignedEvent(
        this.bcId, contractAddress, eventFunctionSignature, eventData, encodedSignatures);
  }

  private static byte[] abiEncodePackedEvent(
      BlockchainId blockchainId, String contractAddress, byte[] eventSignature, byte[] eventData) {
    byte[] blockchainIdBytes = blockchainId.asBytes();

    byte[] address = addressStringToBytes(contractAddress);

    byte[] abiEncodePacked =
        new byte
            [blockchainIdBytes.length + address.length + eventSignature.length + eventData.length];
    System.arraycopy(blockchainIdBytes, 0, abiEncodePacked, 0, blockchainIdBytes.length);
    System.arraycopy(address, 0, abiEncodePacked, blockchainIdBytes.length, address.length);
    System.arraycopy(
        eventSignature,
        0,
        abiEncodePacked,
        blockchainIdBytes.length + address.length,
        eventSignature.length);
    System.arraycopy(
        eventData,
        0,
        abiEncodePacked,
        blockchainIdBytes.length + address.length + eventSignature.length,
        eventData.length);

    return abiEncodePacked;
  }

  private byte[] abiEncodePackedSignatures(
      List<String> theSigners, List<byte[]> sigR, List<byte[]> sigS, List<Byte> sigV) {
    final int LEN_OF_LEN = 4;
    final int LEN_OF_SIG = 20 + 32 + 32 + 1;

    int len = theSigners.size();

    ByteBuffer bb = ByteBuffer.allocate(LEN_OF_LEN + LEN_OF_SIG * len);
    bb.putInt(len);

    for (int i = 0; i < len; i++) {
      bb.put(addressStringToBytes(theSigners.get(i)));
      bb.put(sigR.get(i));
      bb.put(sigS.get(i));
      bb.put(sigV.get(i));
    }
    return bb.array();
  }
}
