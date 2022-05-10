/*
 * Copyright 2020 ConsenSys Software Inc
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
package net.consensys.gpact.common;

import net.consensys.gpact.common.crypto.KeyPairGen;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

/** Holds a private key. Used either to configure relayers or for testing. */
public class AnIdentity {
  private ECKeyPair keyPair;
  private String address;

  public static AnIdentity createNewRandomIdentity() {
    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    return new AnIdentity(privateKey);
  }

  public AnIdentity(String privateKey) {
    this.keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    this.address = Keys.getAddress(keyPair.getPublicKey().toString(16));
  }

  /**
   * Used to create test signatures.
   *
   * @param plainText To be signed data.
   * @return Signature
   */
  public Sign.SignatureData sign(byte[] plainText) {
    return Sign.signMessage(plainText, this.keyPair);
  }

  public String getAddress() {
    return address;
  }

  public byte[] getPrivateKey() {
    // TODO this limits private key length to 256 bits.
    return FormatConversion.bigIntegerToUint256ByteArray(this.keyPair.getPrivateKey());
  }
}
