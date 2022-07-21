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

import "../../messaging/interface/CrosschainVerifier.sol";
import "../../openzeppelin/access/Ownable.sol";

abstract contract CbcDecVer is Ownable {
    // Address of verifier contract to be used for a certain blockchain id.
    mapping(uint256 => CrosschainVerifier) private verifiers;

    // Address of Crosschain Control Contract on another blockchain.
    mapping(uint256 => address) internal remoteCrosschainControlContracts;

    function addVerifier(uint256 _blockchainId, address _verifier)
        external
        onlyOwner
    {
        require(_blockchainId != 0, "Invalid blockchain id");
        require(_verifier != address(0), "Invalid verifier address");
        verifiers[_blockchainId] = CrosschainVerifier(_verifier);
    }

    // TODO this must be only owner
    function addRemoteCrosschainControl(uint256 _blockchainId, address _cbc)
        external
        onlyOwner
    {
        remoteCrosschainControlContracts[_blockchainId] = _cbc;
    }

    /**
     * Decode signatures or proofs and use them to verify an event.
     *
     * @param _blockchainId The blockchain that the event was emitted on.
     * @param _cbcAddress The Crosschain Control Contract that emitted the event.
     * @param _eventFunctionSignature The function selector of the event that emitted the event.
     * @param _eventData The emitted event data.
     * @param _signature The signature of proof across the ABI encoded combination of:
     *            _blockchainId, _cbcAddress, _eventFunctionSignature, and _signature.
     */
    function decodeAndVerifyEvent(
        uint256 _blockchainId,
        address _cbcAddress,
        bytes32 _eventFunctionSignature,
        bytes calldata _eventData,
        bytes calldata _signature
    ) internal view {
        // This indirectly checks that _blockchainId is an authorised source blockchain
        // by checking that there is a verifier for the blockchain.
        CrosschainVerifier verifier = verifiers[_blockchainId];
        require(
            address(verifier) != address(0),
            "No registered verifier for blockchain"
        );

        require(
            _cbcAddress == remoteCrosschainControlContracts[_blockchainId],
            "Data not emitted by approved contract"
        );

        bytes memory encodedEvent = abi.encodePacked(
            _blockchainId,
            _cbcAddress,
            _eventFunctionSignature,
            _eventData
        );
        verifier.decodeAndVerifyEvent(
            _blockchainId,
            _eventFunctionSignature,
            encodedEvent,
            _signature
        );
    }
}
