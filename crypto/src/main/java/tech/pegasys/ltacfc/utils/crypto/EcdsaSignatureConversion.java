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
 */
package tech.pegasys.ltacfc.utils.crypto;

import org.web3j.crypto.Sign;

/**
 * Convert from Web3J signature class to an array of bytes.
 */
public abstract class EcdsaSignatureConversion {
  private  EcdsaSignatureConversion() {

  }

  public static byte[] convert(final Sign.SignatureData signatureData) {
    final int lengthR = 32;
    final int lengthS = 32;
    final int lengthV = 1;
    final int signatureLength = lengthR + lengthS + lengthV;
    byte[] signature = new byte[signatureLength];
    System.arraycopy(signatureData.getR(), 0, signature, 0, lengthR);
    System.arraycopy(signatureData.getS(), 0, signature, lengthR, lengthS);
    System.arraycopy(signatureData.getV(), 0, signature, lengthR+lengthS, lengthV);
    return signature;
  }

}
