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

import "./CrosschainVerifier.sol";
import "../../../../registrar/src/main/solidity/Registrar.sol";


abstract contract CbcDecVer {
    // 	0x77dab611
    bytes32 constant internal START_EVENT_SIGNATURE = keccak256("Start(uint256,address,uint256,bytes)");
    // 0xb01557f1
    bytes32 constant internal SEGMENT_EVENT_SIGNATURE = keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");
    // 0xe6763dd9
    bytes32 constant internal ROOT_EVENT_SIGNATURE = keccak256("Root(uint256,bool)");

    mapping (uint256 => CrosschainVerifier) private verifiers;

    Registrar private reg;

    constructor(address _registrar) {
        reg = Registrar(_registrar);
    }

    // TODO this must be only owner
    function addVerifier(uint256 _blockchainId, address verifier) external {
        verifiers[_blockchainId] = CrosschainVerifier(verifier);
    }

    function decodeAndVerifyEvents(
        uint256[] calldata _blockchainIds,
        address[] calldata _cbcAddresses,
        bytes32[] calldata _eventFunctionSignatures,
        bytes[] calldata _eventData,
        bytes[] calldata _signatures,
        bool _expectStart) internal view {

        // The minimum number of events is 1: start and no segment, used to end a timed-out
        // crosschain transactions.
        uint256 numEvents = _blockchainIds.length;
        require(numEvents > 0, "Must be at least one event");
        require(numEvents == _cbcAddresses.length, "Number of blockchain Ids and cbcAddresses must match");
        require(numEvents == _eventFunctionSignatures.length, "Number of blockchain Ids and event function signatures must match");
        require(numEvents == _eventData.length, "Number of blockchain Ids and event data must match");
        require(numEvents == _signatures.length, "Number of events and signatures match");

        for (uint256 i = 0; i < numEvents; i++) {
            uint256 bcId = _blockchainIds[i];

            CrosschainVerifier verifier = verifiers[bcId];
            require(address(verifier) != address(0), "No registered verifier for blockchain");

            reg.verifyContract(bcId, _cbcAddresses[i]);

            if (i == 0) {
                bytes32 eventSig = _expectStart ? START_EVENT_SIGNATURE : ROOT_EVENT_SIGNATURE;
                require(eventSig == _eventFunctionSignatures[i], "Unexpected first event function signature");
            }
            else {
                require(SEGMENT_EVENT_SIGNATURE == _eventFunctionSignatures[i], "Event function signature not for a segment");
            }

            bytes memory encodedEvent = abi.encodePacked(bcId, _cbcAddresses[i], _eventFunctionSignatures[i], _eventData[i]);
            verifier.decodeAndVerifyEvent(
                bcId, _eventFunctionSignatures[i], encodedEvent, _signatures[i]);
        }
    }
}