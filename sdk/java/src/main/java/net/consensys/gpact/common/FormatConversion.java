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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt256;

public class FormatConversion {
  public static final int BYTES_IN_ADDRESS = 20;
  public static final int BYTES_IN_WORD = 32;

  public static byte[] addressStringToBytes(String address) {
    Bytes eventDataBytes = Bytes.fromHexString(address);
    byte[] addressBytes = eventDataBytes.toArray();

    if (addressBytes.length > BYTES_IN_ADDRESS) {
      throw new RuntimeException(
          "Unexpected address length: " + addressBytes.length + " for address: " + address);
    }
    if (addressBytes.length < BYTES_IN_ADDRESS) {
      byte[] b = new byte[BYTES_IN_ADDRESS];
      System.arraycopy(
          addressBytes, 0, b, BYTES_IN_ADDRESS - addressBytes.length, addressBytes.length);
      addressBytes = b;
    }
    return addressBytes;
  }

  public static byte[] addressStringToPaddedBytes(String address) {
    Bytes eventDataBytes = Bytes.fromHexString(address);
    byte[] addressBytes = eventDataBytes.toArray();

    if (addressBytes.length > BYTES_IN_ADDRESS) {
      throw new RuntimeException(
          "Unexpected address length: " + addressBytes.length + " for address: " + address);
    }

    byte[] b = new byte[BYTES_IN_WORD];
    System.arraycopy(addressBytes, 0, b, BYTES_IN_WORD - addressBytes.length, addressBytes.length);
    return b;
  }

  public static byte[] tobytes32(byte[] val) {
    if (val.length == BYTES_IN_WORD) {
      return val;
    }
    if (val.length > BYTES_IN_WORD) {
      throw new RuntimeException("Too big for bytes32");
    }
    byte[] b = new byte[BYTES_IN_WORD];
    System.arraycopy(val, 0, b, BYTES_IN_WORD - val.length, val.length);
    return b;
  }

  public static byte[] hexStringToByteArray(String hexString) {
    Bytes eventDataBytes = Bytes.fromHexString(hexString);
    return eventDataBytes.toArray();
  }

  public static byte[] bigIntegerToUint256ByteArray(BigInteger b) {
    UInt256 blockchainIdUint256 = UInt256.valueOf(b);
    return blockchainIdUint256.toBytes().toArray();
  }

  public static byte[] longToUint256ByteArray(long val) {
    UInt256 blockchainIdUint256 = UInt256.valueOf(val);
    return blockchainIdUint256.toBytes().toArray();
  }

  public static byte[] shortToUint256ByteArray(short val) {
    UInt256 blockchainIdUint256 = UInt256.valueOf(val);
    return blockchainIdUint256.toBytes().toArray();
  }

  public static String byteArrayToString(byte[] bytes) {
    return Bytes.wrap(bytes).toHexString();
  }

  public static String hexStringToDecString(String hexStr) {
    if (hexStr.startsWith("0x")) {
      hexStr = hexStr.substring(2);
    }
    BigInteger value = new BigInteger(hexStr, 16);
    return value.toString();
  }

  /**
   * ABI encode a string as a bytes.
   *
   * @param s
   * @return
   */
  public static byte[] abiEncodedBytes(String s) {
    ByteBuffer bb = ByteBuffer.allocate(1000);
    int len = s.length();
    // Prefix the length
    bb.put(FormatConversion.longToUint256ByteArray(len));
    int ofs;
    for (ofs = 0; ofs < len / 32; ofs++) {
      // Followed by 32 byte blocks of characters
      String substring = s.substring(ofs, ofs + 32);
      bb.put(substring.getBytes(StandardCharsets.UTF_8));
    }
    String substring = s.substring(ofs, len);
    // Followed by the final characters
    bb.put(substring.getBytes(StandardCharsets.UTF_8));
    // Then zero fill
    bb.put(new byte[32 - substring.length()]);

    bb.flip();
    byte[] output = new byte[bb.limit()];
    bb.get(output);
    return output;
  }
}
