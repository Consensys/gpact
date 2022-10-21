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
pragma solidity >=0.8;

import "./CrosschainVerifier.sol";
import "../common/SignatureEncoding.sol";

abstract contract CrosschainVerifierDefaultImpl is
    SignatureEncoding,
    CrosschainVerifier
{
    function getSignerList(
        uint256 /* _blockchainId */
    ) external pure returns (address[] memory) {
        return new address[](0);
    }

    function supportedSigningAlgorithm(
        uint256 /* _blockchainId */
    ) external pure returns (uint256) {
        return SignatureEncoding.ECDSA_SIGNATURE;
    }
}
