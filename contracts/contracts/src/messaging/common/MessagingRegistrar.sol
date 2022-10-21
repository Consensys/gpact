/*
 * Copyright 2021 ConsenSys Software Inc
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

import "../../common/EcdsaSignatureVerification.sol";
import "../../openzeppelin/access/Ownable.sol";
import "./SignatureEncoding.sol";

contract MessagingRegistrar is
    EcdsaSignatureVerification,
    SignatureEncoding,
    Ownable
{
    struct BlockchainRecord {
        uint256 signingThreshold;
        uint256 numSigners;
        mapping(address => bool) signers;
    }
    mapping(uint256 => BlockchainRecord) private blockchains;

    function addSigner(uint256 _bcId, address _signer) external onlyOwner {
        require(
            blockchains[_bcId].signingThreshold != 0,
            "Can not add signer for blockchain with zero threshold"
        );
        internalAddSigner(_bcId, _signer);
        blockchains[_bcId].numSigners++;
    }

    function addSignerSetThreshold(
        uint256 _bcId,
        address _signer,
        uint256 _newSigningThreshold
    ) external onlyOwner {
        internalAddSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners + 1;
        blockchains[_bcId].numSigners = newNumSigners;
        internalSetThreshold(_bcId, newNumSigners, _newSigningThreshold);
    }

    function addSignersSetThreshold(
        uint256 _bcId,
        address[] calldata _signers,
        uint256 _newSigningThreshold
    ) external onlyOwner {
        for (uint256 i = 0; i < _signers.length; i++) {
            internalAddSigner(_bcId, _signers[i]);
        }
        uint256 newNumSigners = blockchains[_bcId].numSigners + _signers.length;
        blockchains[_bcId].numSigners = newNumSigners;
        internalSetThreshold(_bcId, newNumSigners, _newSigningThreshold);
    }

    function removeSigner(uint256 _bcId, address _signer) external onlyOwner {
        internalRemoveSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners - 1;
        require(
            newNumSigners >= blockchains[_bcId].signingThreshold,
            "Proposed new number of signers is less than threshold"
        );
        blockchains[_bcId].numSigners = newNumSigners;
    }

    function removeSignerSetThreshold(
        uint256 _bcId,
        address _signer,
        uint256 _newSigningThreshold
    ) external onlyOwner {
        internalRemoveSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners - 1;
        blockchains[_bcId].numSigners = newNumSigners;
        internalSetThreshold(_bcId, newNumSigners, _newSigningThreshold);
    }

    function setThreshold(uint256 _bcId, uint256 _newSigningThreshold)
        external
        onlyOwner
    {
        internalSetThreshold(
            _bcId,
            blockchains[_bcId].numSigners,
            _newSigningThreshold
        );
    }

    // Verify signatures and check the threshold
    function verifyAndCheckThreshold(
        uint256 _blockchainId,
        bytes calldata _signatures,
        bytes calldata _plainText
    ) external view {
        Signatures memory sigs = decodeSignature(_signatures);

        // Check signing threshold
        require(
            sigs.signatures.length >=
                blockchains[_blockchainId].signingThreshold,
            "Not enough signers"
        );

        checkUniqueSigners(sigs);
        verifyInternal(_blockchainId, sigs, _plainText);
    }

    // Version of verify called by Event Relay
    // Verify a signature
    function verify(
        uint256 _blockchainId,
        bytes calldata _signatures,
        bytes calldata _plainText
    ) public view returns (bool) {
        Signatures memory sigs = decodeSignature(_signatures);

        checkUniqueSigners(sigs);
        verifyInternal(_blockchainId, sigs, _plainText);
        return true;
    }

    function getSigningThreshold(uint256 _blockchainId)
        external
        view
        returns (uint256)
    {
        return blockchains[_blockchainId].signingThreshold;
    }

    function numSigners(uint256 _blockchainId) external view returns (uint256) {
        return blockchains[_blockchainId].numSigners;
    }

    function isSigner(uint256 _blockchainId, address _mightBeSigner)
        external
        view
        returns (bool)
    {
        return blockchains[_blockchainId].signers[_mightBeSigner];
    }

    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/

    function internalAddSigner(uint256 _bcId, address _signer) private {
        require(
            blockchains[_bcId].signers[_signer] == false,
            "Signer already exists"
        );
        blockchains[_bcId].signers[_signer] = true;
    }

    function internalRemoveSigner(uint256 _bcId, address _signer) private {
        require(blockchains[_bcId].signers[_signer], "Signer does not exist");
        blockchains[_bcId].signers[_signer] = false;
    }

    function internalSetThreshold(
        uint256 _bcId,
        uint256 _currentNumSigners,
        uint256 _newThreshold
    ) private {
        require(
            _currentNumSigners >= _newThreshold,
            "Number of signers less than threshold"
        );
        require(_newThreshold != 0, "Threshold can not be zero");
        blockchains[_bcId].signingThreshold = _newThreshold;
    }

    /**
     * Revert if there is a duplicate signer in the list.
     */
    function checkUniqueSigners(Signatures memory _sigs) private pure {
        for (uint256 i = 0; i < _sigs.signatures.length - 1; i++) {
            address signer = _sigs.signatures[i].by;

            for (uint256 j = i + 1; j < _sigs.signatures.length; j++) {
                require(signer != _sigs.signatures[j].by, "Duplicate signer");
            }
        }
    }

    function verifyInternal(
        uint256 _blockchainId,
        Signatures memory _sigs,
        bytes calldata _plainText
    ) private view {
        for (uint256 i = 0; i < _sigs.signatures.length; i++) {
            // Check that signer is a signer for this blockchain
            require(
                blockchains[_blockchainId].signers[_sigs.signatures[i].by],
                "Signer not registered for this blockchain"
            );
            // Verify the signature
            require(
                verifySigComponents(
                    _sigs.signatures[i].by,
                    _plainText,
                    _sigs.signatures[i].sigR,
                    _sigs.signatures[i].sigS,
                    _sigs.signatures[i].sigV
                ),
                "Signature did not verify"
            );
        }
    }
}
