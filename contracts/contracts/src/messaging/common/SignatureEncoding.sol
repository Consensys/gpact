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
pragma solidity ^0.8;

/**
 * Signature encoding is defined in the EEA Crosschain Messaging specification:
 * https://entethalliance.github.io/crosschain-interoperability/draft_crosschain_techspec_messaging.html
 */
contract SignatureEncoding {
    uint16 constant ECDSA_SIGNATURE = 1;

    struct Signature {
        address by;
        bytes32 sigR;
        bytes32 sigS;
        uint8 sigV;
        bytes meta;
    }

    struct Signatures {
        uint256 typ;
        Signature[] signatures;
    }

    /**
     * Decode a signature blob.
     *
     * @param _signatures Encoded signatures.
     * @return Signture object.
     */
    function decodeSignature(bytes calldata _signatures)
        internal
        pure
        returns (Signatures memory)
    {
        (
            ,
            /* Skip offset of dynamic type */
            uint16 sigType
        ) = abi.decode(_signatures, (uint256, uint16));
        require(sigType == ECDSA_SIGNATURE, "Signature unknown type");

        Signatures memory sigs = abi.decode(_signatures, (Signatures));
        return sigs;
    }
}
