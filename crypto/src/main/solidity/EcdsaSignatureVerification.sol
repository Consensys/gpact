/*
 * Copyright 2020 ConsenSys AG.
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

/**
 * Signature verification for ECDSA / KECCAK256 using secp256k1 curve.
 */
contract EcdsaSignatureVerification {
    /**
     * Verify a signature.
     *
     * @param _signer Address that corresponds to the public key of the signer.
     * @param _message Message to be verified.
     * @param _signature Signature to be verified.
     *
     */
    function verify(address _signer, bytes calldata _message, bytes calldata _signature) internal pure returns (bool) {
        // Check the signature length
        if (_signature.length != 65) {
            return false;
        }

        bytes memory sig = _signature;
        bytes32 r;
        bytes32 s;
        uint8 v;

        // Split the signature into components r, s and v variables with inline assembly.
        assembly {
            r := mload(add(sig, 0x20))
            s := mload(add(sig, 0x40))
            v := byte(0, mload(add(sig, 0x60)))
        }

        return verifySigComponents(_signer, _message, r, s, v);
    }

    /**
     * Verify a signature.
     *
     * @param _signer Address that corresponds to the public key of the signer.
     * @param _message Message to be verified.
     * @param _sigR Component of the signature to be verified.
     * @param _sigS Component of the signature to be verified.
     * @param _sigV Component of the signature to be verified.
     *
     */
    function verifySigComponents(address _signer, bytes calldata _message, bytes32 _sigR, bytes32 _sigS, uint8 _sigV) internal pure returns (bool) {
        bytes32 digest = keccak256(_message);

        if (_sigV != 27 && _sigV != 28) {
            return false;
        } else {
            // The signature is verified if the address recovered from the signature matches
            // the signer address (which maps to the public key).
            return _signer == ecrecover(digest, _sigV, _sigR, _sigS);
        }
    }

}