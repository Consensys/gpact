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

import "../../../../interface/src/main/solidity/CrosschainVerifier.sol";
import "../../../../../common/common/src/main/solidity/BytesUtil.sol";
import "../../../../attestor-sign/src/main/solidity/AttestorSignRegistrar.sol";
import "../../../../../functioncall/sfc/src/main/solidity/SimpleCrosschainControl.sol";

contract SignedEventStore is CrosschainVerifier, BytesUtil {
    AttestorSignRegistrar registrar;
    SimpleCrosschainControl functionCall;

    // 	0x77dab611
    bytes32 internal constant CROSS_CALL_EVENT_SIGNATURE =
        keccak256("CrossCall(bytes32,uint256,address,uint256,address,bytes)");

    struct SignedEvents {
        // The event to be actioned.
        bytes encodedEvent;
        // Replay protection is the responsibility of the function call layer.
        // This variable ensures the signers who submit the event are recorded, even
        // if it is after the event is actioned.
        bool actioned;
        // List of which signers voted.
        address[] whoVoted;
        mapping(address => bool) whoVotedMap;
    }
    mapping(bytes32 => SignedEvents) private signedEvents;

    constructor(address _registrar, address _functionCall) {
        registrar = AttestorSignRegistrar(_registrar);
        functionCall = SimpleCrosschainControl(_functionCall);
    }

    uint256 constant LEN_OF_LEN = 4;
    uint256 constant LEN_OF_SIG = 20 + 32 + 32 + 1;

    /**
     * For this implementation, the signatures have already been checked in the
     * relayEvent function below.
     */
    function decodeAndVerifyEvent(
        uint256 _blockchainId,
        bytes32, /* _eventSig */
        bytes calldata _encodedEvent,
        bytes calldata /* _signature */
    ) external view override {
        bytes32 signedEventDigest = keccak256(_encodedEvent);
        require(
            signedEvents[signedEventDigest].actioned == false,
            "Already actioned"
        );
        uint256 threshold = registrar.getSigningThreshold(_blockchainId);
        require(
            signedEvents[signedEventDigest].whoVoted.length >= threshold,
            "Not enough signers"
        );
    }

    /**
     * Relay event from one or more relayers.
     *
     * Note that replay protection and time outs are handled by the function call layer.
     */
    function relayEvent(
        uint256 _sourceBlockchainId,
        address _sourceCbcAddress,
        bytes calldata _eventData,
        bytes calldata _signature
    ) external {
        // Step 1: Check the signatures of signers.
        address[] memory signers;
        bytes32[] memory sigRs;
        bytes32[] memory sigSs;
        uint8[] memory sigVs;

        uint32 len = BytesUtil.bytesToUint32(_signature, 0);
        {
            require(
                _signature.length == LEN_OF_LEN + len * LEN_OF_SIG,
                "Signature incorrect length"
            );

            signers = new address[](len);
            sigRs = new bytes32[](len);
            sigSs = new bytes32[](len);
            sigVs = new uint8[](len);

            uint256 offset = LEN_OF_LEN;
            for (uint256 i = 0; i < len; i++) {
                signers[i] = BytesUtil.bytesToAddress2(_signature, offset);
                offset += 20;
                sigRs[i] = BytesUtil.bytesToBytes32(_signature, offset);
                offset += 32;
                sigSs[i] = BytesUtil.bytesToBytes32(_signature, offset);
                offset += 32;
                sigVs[i] = BytesUtil.bytesToUint8(_signature, offset);
                offset += 1;
            }
        }

        bytes memory encodedEvent = abi.encodePacked(
            _sourceBlockchainId,
            _sourceCbcAddress,
            CROSS_CALL_EVENT_SIGNATURE,
            _eventData
        );

        registrar.verify(
            _sourceBlockchainId,
            signers,
            sigRs,
            sigSs,
            sigVs,
            encodedEvent
        );

        // Step 2: Record who signed the event.
        bytes32 eventDigest = keccak256(encodedEvent);

        for (uint256 i = 0; i < len; i++) {
            if (signedEvents[eventDigest].whoVotedMap[signers[i]]) {
                // Ignore relayers inadvertently signing a second time.
                continue;
            }
            signedEvents[eventDigest].whoVotedMap[signers[i]] = true;
            signedEvents[eventDigest].whoVoted.push(signers[i]);
        }

        // Action the event if enough relayers have signed.
        if (signedEvents[eventDigest].actioned) {
            // There is no more to do. The signers have been recorded. This could
            // be important for determining who to pay.
            return;
        }
        uint256 threshold = registrar.getSigningThreshold(_sourceBlockchainId);
        if (signedEvents[eventDigest].whoVoted.length >= threshold) {
            // NOTE: In the call below, the _signature parameter isn't used. Could be cheaper to pass bytes("")
            functionCall.crossCallHandler(
                _sourceBlockchainId,
                _sourceCbcAddress,
                _eventData,
                _signature
            );
            signedEvents[eventDigest].actioned = true;
        }
    }
}
