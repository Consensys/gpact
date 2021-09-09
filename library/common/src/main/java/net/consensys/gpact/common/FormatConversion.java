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

import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt256;

import java.math.BigInteger;


public class FormatConversion {
  public static final int BYTES_IN_ADDRESS = 20;

  public static byte[] addressStringToBytes(String address) {
    Bytes eventDataBytes = Bytes.fromHexString(address);
    byte[] addressBytes = eventDataBytes.toArray();

    if (addressBytes.length > BYTES_IN_ADDRESS) {
      throw new RuntimeException("Unexpected address length: " + addressBytes.length + " for address: " + address);
    }
    if (addressBytes.length < BYTES_IN_ADDRESS) {
      byte[] b = new byte[BYTES_IN_ADDRESS];
      System.arraycopy(addressBytes, 0, b, BYTES_IN_ADDRESS - addressBytes.length, addressBytes.length);
      addressBytes = b;
    }
    return addressBytes;
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


  public static String byteArrayToString(byte[] bytes) {
    return Bytes.wrap(bytes).toHexString();
  }

}
