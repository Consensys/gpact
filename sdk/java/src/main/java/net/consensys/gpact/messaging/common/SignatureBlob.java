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
package net.consensys.gpact.messaging.common;

import static net.consensys.gpact.common.FormatConversion.*;

import java.nio.ByteBuffer;
import net.consensys.gpact.common.FormatConversion;

/*
 * Process the structure.
 *
 * struct Signature {
 *   address by;
 *   bytes32 sigR;
 *   bytes32 sigS;
 *   uint8 sigV;
 *   bytes meta;
 * }
 *
 * struct Signatures {
 *   uint16 typ;
 *   uint16 numberOfSignatures;
 *   Signature[] signatures;
 * }
 */
public class SignatureBlob {
  public static final int SIG_TYPE_ECDSA = 1;
  public int sigType;
  public String[] theSigners;
  public byte[][] sigRs;
  public byte[][] sigSs;
  public byte[] sigVs;
  public String[] metaData;

  public SignatureBlob(String[] theSigners, byte[][] sigRs, byte[][] sigSs, byte[] sigVs) {
    if (theSigners.length != sigRs.length) {
      throw new RuntimeException("Signature arrays mismatched lengths1");
    }
    if (theSigners.length != sigSs.length) {
      throw new RuntimeException("Signature arrays mismatched lengths2");
    }
    if (theSigners.length != sigVs.length) {
      throw new RuntimeException("Signature arrays mismatched lengths3");
    }

    this.sigType = SIG_TYPE_ECDSA;
    this.theSigners = theSigners;
    this.sigRs = sigRs;
    this.sigSs = sigSs;
    this.sigVs = sigVs;

    this.metaData = new String[theSigners.length];
    for (int i = 0; i < theSigners.length; i++) {
      this.metaData[i] = "";
    }
  }

  public static SignatureBlob decode(byte[] encoded) {
    return null;
  }

  /*
   * Get the ABI encoded version of the signature blob.
   *
   * Encoding is:
   * Offset of struct 0x20 as a uint256
   * Type uint256
   * Offset of dynamic type (array of structs): 0x60 as a uint256
   * Array length
   * Array of Offsets of Signature structs relative to array length
   *  First element is 0x20.
   *
   * Note: As this is likely to be called only once per blob, this encoded byte array is not cached.
   */
  public byte[] encode() {
    int len = this.theSigners.length;

    ByteBuffer bb = ByteBuffer.allocate(10000);
    // Offset of dynamic type: Signatures
    bb.put(longToUint256ByteArray(0x20));
    bb.put(longToUint256ByteArray(this.sigType));
    // Offset of dynamic type: array of Signatures
    bb.put(longToUint256ByteArray(0x40));

    // Array of signatures
    // The length is stored for a second time, as the encoded length of the array.
    bb.put(longToUint256ByteArray(len));
    byte[][] sigBlobs = new byte[len][];
    for (int i = 0; i < len; i++) {
      sigBlobs[i] =
          abiEncodeSigStruct(this.theSigners[i], this.sigRs[i], this.sigSs[i], this.sigVs[i], "");
    }
    int ofs = 0x20 * len;
    for (int i = 0; i < len; i++) {
      bb.put(longToUint256ByteArray(ofs));
      ofs += sigBlobs[i].length;
    }
    for (int i = 0; i < len; i++) {
      bb.put(sigBlobs[i]);
    }
    bb.flip();
    byte[] output = new byte[bb.limit()];
    bb.get(output);
    return output;
  }

  /*
   * Encode:
   * By   address encoded as 32 bytes
   * SigR
   * SigS
   * SigV uint8 encoded as 32 bytes
   * Offset of bytes (meta): 0xa0
   * Length of bytes (meta) in bytes
   * Meta  bytes are encoded from first top of bytes32
   */
  private byte[] abiEncodeSigStruct(
      String signer, byte[] sigR, byte[] sigS, byte sigV, String metaData) {
    ByteBuffer bb = ByteBuffer.allocate(10000);
    bb.put(FormatConversion.addressStringToPaddedBytes(signer));
    bb.put(FormatConversion.tobytes32(sigR));
    bb.put(FormatConversion.tobytes32(sigS));
    bb.put(longToUint256ByteArray(sigV));
    bb.put(longToUint256ByteArray(0xA0));

    bb.put(FormatConversion.abiEncodedBytes(metaData));

    bb.flip();
    byte[] output = new byte[bb.limit()];
    bb.get(output);
    return output;
  }

  @Override
  public String toString() {

    return null;
  }
}
