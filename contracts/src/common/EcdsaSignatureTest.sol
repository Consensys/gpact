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
pragma solidity >=0.7.1;

import "./EcdsaSignatureVerification.sol";

contract EcdsaSignatureTest is EcdsaSignatureVerification {
    function verify2(
        address _signer,
        bytes calldata _message,
        bytes calldata _signature
    ) external pure returns (bool) {
        return verify(_signer, _message, _signature);
    }

    function verifySigComponents2(
        address _signer,
        bytes calldata _message,
        bytes32 _sigR,
        bytes32 _sigS,
        uint8 _sigV
    ) external pure returns (bool) {
        return verifySigComponents(_signer, _message, _sigR, _sigS, _sigV);
    }
}
