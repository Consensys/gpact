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
 *
 * SPDX-License-Identifier: Apache-2.0
 */
pragma solidity >=0.8;

import "../interface/CrosschainVerifier.sol";
import "../common/MessagingRegistrar.sol";
import "../common/SignatureEncoding.sol";

contract EventAttestationVerifier is CrosschainVerifier, SignatureEncoding {
    MessagingRegistrar registrar;

    constructor(address _registrar) {
        registrar = MessagingRegistrar(_registrar);
    }

    function decodeAndVerifyEvent(
        uint256 _blockchainId,
        bytes32, /* _eventSig */
        bytes calldata _encodedEvent,
        bytes calldata _signatures
    ) external view override {
        registrar.verifyAndCheckThreshold(
            _blockchainId,
            _signatures,
            _encodedEvent
        );
    }
}
