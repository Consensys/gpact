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
 */
package tech.pegasys.ltacfc.utils.crypto;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;


import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.DrbgParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.Enumeration;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

/**
 * Generate one or more key pairs for use in the sample code.
 */
public class KeyPairGen {
  private final KeyPairGenerator keyPairGenerator;

  public KeyPairGen() {
    // TODO don't create a PRNG each call
    try {
      final SecureRandom rand = SecureRandom.getInstance("DRBG",
          DrbgParameters.instantiation(256, RESEED_ONLY, getPersonalizationString()));
      this.keyPairGenerator = KeyPairGenerator.getInstance("EC");
      final ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
      this.keyPairGenerator.initialize(ecGenParameterSpec, rand);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }

  }


  public KeyPair generateKeyPair() {
    return this.keyPairGenerator.generateKeyPair();
  }

  public String generateKeyPairGetPrivateKey() {
    KeyPair rawKeyPair = this.keyPairGenerator.generateKeyPair();
    final ECPrivateKey privateKey =  (ECPrivateKey) rawKeyPair.getPrivate();
    final BigInteger privateKeyValue = privateKey.getS();
    return privateKeyValue.toString(16);
  }


  // Use a personalisation string to help ensure the entropy going into the PRNG is unique.
  private static byte[] getPersonalizationString() throws SocketException, BufferOverflowException {
    final byte[] networkMacs = networkHardwareAddresses();
    final Runtime runtime = Runtime.getRuntime();
    final byte[] threadId = Longs.toByteArray(Thread.currentThread().getId());
    final byte[] availProcessors = Ints.toByteArray(runtime.availableProcessors());
    final byte[] freeMem = Longs.toByteArray(runtime.freeMemory());
    final byte[] runtimeMem = Longs.toByteArray(runtime.maxMemory());
    return Bytes.concat(threadId, availProcessors, freeMem, runtimeMem, networkMacs);
  }

  private static byte[] networkHardwareAddresses() throws SocketException, BufferOverflowException {
    final byte[] networkAddresses = new byte[256];
    final ByteBuffer buffer = ByteBuffer.wrap(networkAddresses);

    final Enumeration<NetworkInterface> networkInterfaces =
        NetworkInterface.getNetworkInterfaces();
    if (networkInterfaces != null) {
      while (networkInterfaces.hasMoreElements()) {
        final NetworkInterface networkInterface = networkInterfaces.nextElement();
        final byte[] hardwareAddress = networkInterface.getHardwareAddress();
        if (hardwareAddress != null) {
          buffer.put(hardwareAddress);
        }
      }
    }
    return Arrays.copyOf(networkAddresses, buffer.position());
  }
}
