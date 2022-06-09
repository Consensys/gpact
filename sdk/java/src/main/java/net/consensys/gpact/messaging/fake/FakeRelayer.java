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
package net.consensys.gpact.messaging.fake;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.consensys.gpact.common.AnIdentity;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.messaging.SignedEvent;
import org.web3j.crypto.Sign;

/**
 * FakeRelayer emulates the relayer configured for event attestation. The relayer has a single
 * signer that signs events locally with an in-memory private key.
 */
public class FakeRelayer {

  AnIdentity signer;

  public FakeRelayer(final AnIdentity signer) {
    this.signer = signer;
  }

  public String fetchedSignedEvent(
      BlockchainId bcId, String contractAddress, byte[] eventFunctionSignature, byte[] eventData) {
    List<AnIdentity> signers = new ArrayList<>();
    signers.add(this.signer);
    SignedEvent signedEvent =
        getSignedEvent(signers, bcId, eventData, contractAddress, eventFunctionSignature);
    return FormatConversion.byteArrayToString(signedEvent.getEncodedSignatures());
  }

  public static SignedEvent getSignedEvent(
      List<AnIdentity> signers,
      BlockchainId bcId,
      byte[] eventData,
      String contractAddress,
      byte[] eventFunctionSignature) {

    byte[] encodedEventInformation =
        abiEncodePackedEvent(bcId, contractAddress, eventFunctionSignature, eventData);

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<Byte> sigV = new ArrayList<>();
    //    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer : signers) {
      Sign.SignatureData signatureData = signer.sign(encodedEventInformation);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(signatureData.getV()[0]);
    }

    byte[] encodedSignatures = abiEncodePackedSignatures(theSigners, sigR, sigS, sigV);
    return new SignedEvent(
        bcId, contractAddress, eventFunctionSignature, eventData, encodedSignatures);
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

  private static byte[] abiEncodePackedSignatures(
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
