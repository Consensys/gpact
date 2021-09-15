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

import "../../../../../common/common/src/main/solidity/EcdsaSignatureVerification.sol";
import "../../../../../common/openzeppelin/src/main/solidity/access/Ownable.sol";

contract AttestorSignRegistrar is EcdsaSignatureVerification, Ownable {
    struct BlockchainRecord {
        uint256 signingThreshold;
        uint256 numSigners;
        mapping(address => bool) signers;
    }
    mapping(uint256=>BlockchainRecord) private blockchains;


    function addBlockchain(uint256 _bcId, uint256 _signingThreshold, address[] calldata _signers) onlyOwner external {
        require(blockchains[_bcId].signingThreshold == 0, "Blockchain already exists");
        require(_signingThreshold != 0, "Signing threshold can not be zero");
        blockchains[_bcId].numSigners = _signers.length;

        for (uint256 i = 0; i < _signers.length; i++) {
            internalAddSigner(_bcId, _signers[i]);
        }

        internalSetThreshold(_bcId, _signers.length, _signingThreshold);
    }

    function addSigner(uint256 _bcId, address _signer) onlyOwner external {
        require(blockchains[_bcId].signingThreshold != 0, "Blockchain does not exist");
        internalAddSigner(_bcId, _signer);
        blockchains[_bcId].numSigners++;
    }

    function addSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) onlyOwner external {
        require(blockchains[_bcId].signingThreshold != 0, "Blockchain does not exist");
        internalAddSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners + 1;
        blockchains[_bcId].numSigners = newNumSigners;
        internalSetThreshold(_bcId, newNumSigners, _newSigningThreshold);
    }

    function removeSigner(uint256 _bcId, address _signer) onlyOwner external {
        require(blockchains[_bcId].signingThreshold != 0, "Blockchain does not exist");
        internalRemoveSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners - 1;
        require(newNumSigners >= blockchains[_bcId].signingThreshold, "Proposed new number of signers is less than threshold");
        blockchains[_bcId].numSigners = newNumSigners;
    }

    function removeSignerSetThreshold(uint256 _bcId, address _signer, uint256 _newSigningThreshold) onlyOwner external {
        require(blockchains[_bcId].signingThreshold != 0, "Blockchain does not exist");
        internalRemoveSigner(_bcId, _signer);
        uint256 newNumSigners = blockchains[_bcId].numSigners - 1;
        blockchains[_bcId].numSigners = newNumSigners;
        internalSetThreshold(_bcId, newNumSigners, _newSigningThreshold);
    }

    function setThreshold(uint256 _bcId, uint256 _newSigningThreshold) onlyOwner external {
        require(blockchains[_bcId].signingThreshold != 0, "Blockchain does not exist");
        internalSetThreshold(_bcId, blockchains[_bcId].numSigners, _newSigningThreshold);
    }

    function verify(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes calldata _plainText) external view returns (bool){

        uint256 signersLength = _signers.length;
        require(signersLength == _sigR.length, "sigR length mismatch");
        require(signersLength == _sigS.length, "sigS length mismatch");
        require(signersLength == _sigV.length, "sigV length mismatch");

        require(signersLength >= blockchains[_blockchainId].signingThreshold, "Not enough signers");

        for (uint256 i=0; i < signersLength; i++) {
            // Check that signer is a signer for this blockchain
            require(blockchains[_blockchainId].signers[_signers[i]], "Signer not signer for this blockchain");
            // Verify the signature
            require(verifySigComponents(_signers[i], _plainText, _sigR[i], _sigS[i], _sigV[i]), "Signature did not verify");
        }
        return true;
    }

    function getSigningThreshold(uint256 _blockchainId) external view returns (uint256) {
        return blockchains[_blockchainId].signingThreshold;
    }

    function numSigners(uint256 _blockchainId) external view returns (uint256) {
        return blockchains[_blockchainId].numSigners;
    }

    function isSigner(uint256 _blockchainId, address _mightBeSigner) external view returns (bool) {
        return blockchains[_blockchainId].signers[_mightBeSigner];
    }



    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************/

    function internalAddSigner(uint256 _bcId, address _signer) private {
        require(blockchains[_bcId].signers[_signer] == false, "Signer already exists");
        blockchains[_bcId].signers[_signer] = true;
    }

    function internalRemoveSigner(uint256 _bcId, address _signer) private {
        require(blockchains[_bcId].signers[_signer], "Signer does not exist");
        blockchains[_bcId].signers[_signer] = false;
    }

    function internalSetThreshold(uint256 _bcId, uint256 _currentNumSigners, uint256 _newThreshold) private {
        require(_currentNumSigners >= _newThreshold, "Number of signers less than threshold");
        blockchains[_bcId].signingThreshold = _newThreshold;
    }
}