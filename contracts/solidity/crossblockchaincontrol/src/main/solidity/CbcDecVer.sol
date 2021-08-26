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

abstract contract CbcDecVer {
    bytes32 constant internal START_EVENT_SIGNATURE = keccak256("Start(uint256,address,uint256,bytes)");
    bytes32 constant internal SEGMENT_EVENT_SIGNATURE = keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");
    bytes32 constant internal ROOT_EVENT_SIGNATURE = keccak256("Root(uint256,bool)");

    mapping (uint256 => CrosschainVerifier) private verifiers;

    // TODO this must be only owner
    function addVerifier(uint256 _blockchainId, address verifier) external {
        verifiers[_blockchainId] = CrosschainVerifier(verifier);
    }


    function decodeAndVerifyEvents(uint256[] calldata _blockchainIds, bytes[] calldata _signedEventInfo, bytes[] calldata _signatures, bool _expectStart)
        internal view returns(uint256 _rootBlockchainId, address _rootCbcContract, bytes memory _startOrRootEvent, bytes[] memory _segmentEvents) {
        // The minimum number of events is 1: start and no segment, used to end a timed-out
        // crosschain transactions.
        uint256 numEvents = _signedEventInfo.length;
        require(numEvents > 0, "Must be at least one event");
        require(numEvents == _blockchainIds.length, "Number of blockchain Ids and events must match");
        require(numEvents == _signatures.length || _signatures.length == 0, "Number of events and signatures match, or number of signatures must be zero");


        _segmentEvents = new bytes[](numEvents - 1);
        for (uint256 i = 0; i < numEvents; i++) {
            uint256 bcId = _blockchainIds[i];
            bytes memory signedEventInfo = _signedEventInfo[i];
            bytes memory signature;
            if (_signatures.length != 0) {
                signature = _signatures[i];
            }

            CrosschainVerifier verifier = verifiers[bcId];
            require(address(verifier) != address(0), "No registered verifier for blockchain");

            bytes32 eventSig;
            if (i == 0) {
                eventSig = _expectStart ? START_EVENT_SIGNATURE : ROOT_EVENT_SIGNATURE;
            }
            else {
                eventSig = SEGMENT_EVENT_SIGNATURE;
            }

            CrosschainVerifier.EventInfo memory eventInfo = verifier.decodeAndVerifyEvent(
                bcId, eventSig, signedEventInfo, signature);
            if (i == 0) {
                _rootCbcContract = eventInfo.cbcContract;
                _startOrRootEvent = eventInfo.eventData;
            }
            else {
                _segmentEvents[i-1] = eventInfo.eventData;
            }
        }
        _rootBlockchainId = _blockchainIds[0];
    }
}