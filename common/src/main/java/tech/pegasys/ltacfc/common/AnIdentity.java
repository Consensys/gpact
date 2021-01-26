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
package tech.pegasys.ltacfc.common;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;

public class AnIdentity {
  private ECKeyPair keyPair;
  private String address;

  public AnIdentity() {
    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    this.keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    this.address = Keys.getAddress(keyPair.getPublicKey().toString(16));
  }

  public Sign.SignatureData sign(byte[] plainText) {
    return Sign.signMessage(plainText, this.keyPair);
  }

  public ECKeyPair getKeyPair() {
    return keyPair;
  }

  public String getAddress() {
    return address;
  }

  public BigInteger getAddressAsBigInt() {
    return new BigInteger(this.address, 16);
//    return new BigInteger(this.address.substring(2), 16);
  }
}
