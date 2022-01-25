/*
 * Copyright ConsenSys Software Inc
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
package net.consensys.gpact.common.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** All code should access Keccak 256 via this class. */
public abstract class Hash {
  static {
    //    Security.addProvider(new BesuProvider());
    Security.addProvider(new BouncyCastleProvider());
  }

  private Hash() {}

  public static final String KECCAK256_ALG = "KECCAK-256";

  /**
   * Helper method to generate a digest using the provided algorithm.
   *
   * @param input The input bytes to produce the digest for.
   * @param alg The name of the digest algorithm to use.
   * @return A digest.
   */
  @SuppressWarnings("DoNotInvokeMessageDigestDirectly")
  private static byte[] digestUsingAlgorithm(final Bytes input, final String alg) {
    try {
      final MessageDigest digest = MessageDigest.getInstance(alg);
      input.update(digest);
      return digest.digest();
    } catch (final NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Digest using keccak-256.
   *
   * @param input The input bytes to produce the digest for.
   * @return A digest.
   */
  public static Bytes32 keccak256(final Bytes input) {
    return Bytes32.wrap(digestUsingAlgorithm(input, KECCAK256_ALG));
  }
}
