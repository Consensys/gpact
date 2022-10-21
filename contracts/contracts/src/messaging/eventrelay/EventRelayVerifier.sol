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

import "../interface/CrosschainVerifierDefaultImpl.sol";
import "../common/MessagingRegistrar.sol";
import "../../functioncall/sfc/SimpleCrosschainControl.sol";
import "../common/SignatureEncoding.sol";

contract EventRelayVerifier is CrosschainVerifierDefaultImpl {
    MessagingRegistrar registrar;
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
        registrar = MessagingRegistrar(_registrar);
        functionCall = SimpleCrosschainControl(_functionCall);
    }

    /**
     * For this implementation, the signatures have already been checked in the
     * relayEvent function below.
     */
    function decodeAndVerifyEvent(
        uint256 _blockchainId,
        bytes32, /* _eventSig */
        bytes calldata _encodedEvent,
        bytes calldata /* _signature */
    ) external view override returns (bool) {
        bytes32 eventDigest = keccak256(_encodedEvent);
        uint256 threshold = registrar.getSigningThreshold(_blockchainId);
        require(
            signedEvents[eventDigest].whoVoted.length >= threshold,
            "Not enough signers"
        );

        // A return value is needed so that Web3J generates a wrapper for this function.
        return true;
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
        bytes calldata _signatures
    ) external {
        // Step 1: Check the signatures of signers.
        bytes memory encodedEvent = abi.encodePacked(
            _sourceBlockchainId,
            _sourceCbcAddress,
            CROSS_CALL_EVENT_SIGNATURE,
            _eventData
        );
        registrar.verify(_sourceBlockchainId, _signatures, encodedEvent);

        // Step 2: Record who signed the event.
        bytes32 eventDigest = keccak256(encodedEvent);
        Signatures memory sigs = decodeSignature(_signatures);

        for (uint256 i = 0; i < sigs.signatures.length; i++) {
            address signer = sigs.signatures[i].by;
            if (signedEvents[eventDigest].whoVotedMap[signer]) {
                // Ignore relayers inadvertently signing a second time.
                continue;
            }
            signedEvents[eventDigest].whoVotedMap[signer] = true;
            signedEvents[eventDigest].whoVoted.push(signer);
        }

        // Action the event if enough relayers have signed.
        if (signedEvents[eventDigest].actioned) {
            // There is no more to do. The signers have been recorded. This could
            // be important for determining who to pay.
            return;
        }
        uint256 threshold = registrar.getSigningThreshold(_sourceBlockchainId);
        if (signedEvents[eventDigest].whoVoted.length >= threshold) {
            signedEvents[eventDigest].actioned = true;
            // TODO: In the call below, the _signature parameter isn't used. Could be cheaper to pass bytes("")
            functionCall.crossCallHandler(
                _sourceBlockchainId,
                _sourceCbcAddress,
                _eventData,
                _signatures
            );
        }
    }
}
