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

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.Collections;
import java.util.List;


/**
 * Decode revert reasons.
 *
 * See https://docs.soliditylang.org/en/v0.8.0/control-structures.html and look for Panic for a list
 * of Panic error codes.
 */
public class RevertReason {


  public static String decodeRevertReason(String revertReasonEncoded) {
    String errorMethodId = "0x08c379a0"; // Numeric.toHexString(Hash.sha3("Error(string)".getBytes())).substring(0, 10)
    String panicMethodId = "0x4e487b71"; // Numeric.toHexString(Hash.sha3("Panic(uint256)".getBytes())).substring(0, 10)
    List<TypeReference<Type>> errorRevertReasonTypes = Collections.singletonList(TypeReference.create((Class<Type>) AbiTypes.getType("string")));
    List<TypeReference<Type>> panicRevertReasonTypes = Collections.singletonList(TypeReference.create((Class<Type>) AbiTypes.getType("uint256")));

    if (revertReasonEncoded == null) {
      return "Revert Reason is null";
    }

    if (revertReasonEncoded.startsWith(errorMethodId)) {
      String encodedRevertReason = revertReasonEncoded.substring(errorMethodId.length());
      List<Type> decoded = FunctionReturnDecoder.decode(encodedRevertReason, errorRevertReasonTypes);
      Utf8String decodedRevertReason = (Utf8String) decoded.get(0);
      return decodedRevertReason.getValue();
    }
    else if (revertReasonEncoded.startsWith(panicMethodId)) {
      String encodedRevertReason = revertReasonEncoded.substring(panicMethodId.length());
      List<Type> decoded = FunctionReturnDecoder.decode(encodedRevertReason, panicRevertReasonTypes);
      Uint256 decodedRevertReason = (Uint256) decoded.get(0);
      return "Panic: 0x" + decodedRevertReason.getValue().toString(16);
    }

    return revertReasonEncoded;
  }
}
