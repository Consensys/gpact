/*
 * Copyright 2020 ConsenSys AG.
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
package tech.pegasys.ltacfc.cbc;

import org.apache.tuweni.units.bigints.UInt256;
import org.web3j.crypto.Sign;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.common.AnIdentity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static tech.pegasys.ltacfc.common.FormatConversion.addressStringToBytes;

/**
 * Information to allow a transaction receipt to be verified within a Solidity contract.
 */
public class SignedEvent {
  private byte[] encodedEventInformation;
  private byte[] encodedSignatures;

  public SignedEvent(AnIdentity[] signers, BigInteger blockchainId, String cbcContract, byte[] eventSignature, byte[] eventData) {
    this.encodedEventInformation = RlpEncoder.encode(encodeEvent(blockchainId, cbcContract, eventSignature, eventData));

    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer: signers) {
      Sign.SignatureData signatureData = signer.sign(this.encodedEventInformation);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(BigInteger.valueOf(signatureData.getV()[0]));
    }

    this.encodedSignatures = RlpEncoder.encode(encodeSignatures(theSigners, sigR, sigS, sigV));
  }

  public byte[] getEncodedEventInformation() {
    return encodedEventInformation;
  }

  public byte[] getEncodedSignatures() {
    return encodedSignatures;
  }

  private static RlpList encodeEvent(BigInteger blockchainId, String cbcContractAddress, byte[] eventSignature, byte[] eventData) {
    UInt256 blockchainIdUint256 = UInt256.valueOf(blockchainId);
    byte[] blockchainIdBytes = blockchainIdUint256.toBytes().toArray();

    return new RlpList(
        RlpString.create(blockchainIdBytes),
        RlpString.create(addressStringToBytes(cbcContractAddress)),
        RlpString.create(eventSignature),
        RlpString.create(eventData)
    );
  }


  private static RlpList encodeSignatures(List<String> theSigners, List<byte[]> sigR, List<byte[]> sigS, List<BigInteger> sigV) {
    List<RlpType> theSignersRlp = new ArrayList<>();
    List<RlpType> sigRRlp = new ArrayList<>();
    List<RlpType> sigSRlp = new ArrayList<>();
    List<RlpType> sigVRlp = new ArrayList<>();

    for (int i = 0; i < theSigners.size(); i++) {
      theSignersRlp.add(RlpString.create(addressStringToBytes(theSigners.get(i))));
      sigRRlp.add(RlpString.create(sigR.get(i)));
      sigSRlp.add(RlpString.create(sigS.get(i)));
      sigVRlp.add(RlpString.create(sigV.get(i)));
    }

    return new RlpList(
        new RlpList(theSignersRlp),
        new RlpList(sigRRlp),
        new RlpList(sigSRlp),
        new RlpList(sigVRlp));
  }
}
