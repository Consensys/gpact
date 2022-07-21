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
 */
package net.consensys.gpact.common.crypto;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** Generate one or more key pairs. */
public class KeyPairGen {
  private final KeyPairGenerator keyPairGenerator;

  static {
    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
      Security.addProvider(new BouncyCastleProvider());
    }
  }

  public KeyPairGen() {
    SecureRandom rand = RandomNumbers.getPrivateRand();
    try {
      this.keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
      final ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
      this.keyPairGenerator.initialize(ecGenParameterSpec, rand);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public KeyPair generateKeyPair() {
    RandomNumbers.quickReseedPrivateRand();
    return this.keyPairGenerator.generateKeyPair();
  }

  public String generateKeyPairGetPrivateKey() {
    RandomNumbers.quickReseedPrivateRand();
    KeyPair rawKeyPair = this.keyPairGenerator.generateKeyPair();
    final ECPrivateKey privateKey = (ECPrivateKey) rawKeyPair.getPrivate();
    final BigInteger privateKeyValue = privateKey.getS();
    return privateKeyValue.toString(16);
  }
}
