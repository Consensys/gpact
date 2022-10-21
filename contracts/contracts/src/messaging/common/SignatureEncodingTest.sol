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

import "./SignatureEncoding.sol";

/**
 * Signature encoding is defined in the EEA Crosschain Messaging specification:
 * https://entethalliance.github.io/crosschain-interoperability/draft_crosschain_techspec_messaging.html
 */
contract SignatureEncodingTest is SignatureEncoding {
    event ShowSig(bytes encoded);

    function testEmitSignatureBlob() external {
        Signature memory sig1 = Signature(
            address(0x21),
            bytes32(uint256(0x22)),
            bytes32(uint256(0x23)),
            uint8(0x24),
            bytes("abc")
        );
        Signature[] memory s = new Signature[](1);
        s[0] = sig1;
        Signatures memory sigs = Signatures(0x25, s);
        bytes memory encoded = abi.encode(sigs);
        emit ShowSig(encoded);
    }

    event SigType(uint256 typ);
    event SigInfo1(uint256 typ, uint256 len);
    event SigInfo2(
        address by,
        bytes32 sigR,
        bytes32 sigS,
        uint8 sigV,
        bytes meta
    );

    function testDecodeSignatureType(bytes calldata _signatures) external {
        (, uint256 sigType) = abi.decode(_signatures, (uint256, uint256));
        emit SigType(sigType);
    }

    function testDecodeSignature(bytes calldata _signatures) external {
        Signatures memory sigs = decodeSignature(_signatures);

        emit SigInfo1(sigs.typ, sigs.signatures.length);

        for (uint256 i = 0; i < sigs.signatures.length; i++) {
            Signature memory sig = sigs.signatures[i];
            emit SigInfo2(sig.by, sig.sigR, sig.sigS, sig.sigV, sig.meta);
        }
    }
}
