/*
 * Copyright 2019 ConsenSys AG.
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
pragma experimental ABIEncoderV2;

import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "./CrossBlockchainControl.sol";


contract CbcTxRootTransfer is CrossBlockchainControl {
    TxReceiptsRootStorageInterface public txReceiptRootStorage;

    struct EventProof {
        uint256 blockchainId;
        address cbcContract;
        bytes32 txReceiptRoot;
        bytes encodedTxReceipt;
        uint256[] proofOffsets;
        bytes[] proof;
    }

    constructor(uint256 _myBlockchainId, address _txReceiptRootStorage) CrossBlockchainControl (_myBlockchainId){
        txReceiptRootStorage = TxReceiptsRootStorageInterface(_txReceiptRootStorage);
    }


    /**
     * Segment call, when transaction root transfer consensus is used.
     *
     * The parameter should be an array of RLP encoded Event Proof, with the array offset 0 being the start event,
     * and the other array elements being for segment events. Note that the segment events must be
     * in order of the functions called.
     *
    **/
    function segment(bytes[] calldata _eventProofsEncoded, uint256[] calldata _callPath) external {
        uint256 startEventBlockchainId;
        address startEventCbcAddress;
        bytes memory startEventData;
        bytes[] memory segmentEvents;
        (startEventBlockchainId, startEventCbcAddress, startEventData, segmentEvents) = unpackEventProofs(_eventProofsEncoded);
        segmentProcessing(startEventBlockchainId, startEventData, segmentEvents, _callPath);
    }


    /**
     * Root call, when transaction root transfer consensus is used.
     *
     * The parameter should be an array of RLP encoded Event Proof, with the array offset 0 being the start event,
     * and the other array elements being for segment events. Note that the segment events must be
     * in order of the functions called.
     *
    **/
    function root(bytes[] calldata _eventProofsEncoded) external {
        uint256 startEventBlockchainId;
        address startEventCbcAddress;
        bytes memory startEventData;
        bytes[] memory segmentEvents;
        (startEventBlockchainId, startEventCbcAddress, startEventData, segmentEvents) = unpackEventProofs(_eventProofsEncoded);
        rootProcessing(startEventBlockchainId, startEventCbcAddress, startEventData, segmentEvents);
    }


    /**
 * Signalling call: Commit or ignore updates + unlock any locked contracts.
 *
 * The parameter should be an array of Info[], with the array offset 0 being the root event,
 * and the other array elements being for segment events that have locked contracts.
 *
 * User rootPrep to set-up the parameters
 **/
    function signalling(bytes[] calldata _eventProofsEncoded) external {
        uint256 rootBlockchainId;
        bytes memory rootEventData;
        bytes[] memory segmentEvents = new bytes[](_eventProofsEncoded.length - 1);

        //Verify the start event and all segment events.
        for (uint256 i = 0; i < _eventProofsEncoded.length; i++) {
            EventProof memory eventProof = toEventProof(_eventProofsEncoded[i]);
            txReceiptRootStorage.verify(
                eventProof.blockchainId,
                eventProof.cbcContract,
                eventProof.txReceiptRoot,
                eventProof.encodedTxReceipt,
                eventProof.proofOffsets,
                eventProof.proof);

            if (i == 0) {
                rootBlockchainId = eventProof.blockchainId;

                // Extract the root event from the RLP encoded receipt trie data.
                bytes memory encodedRootTxReceiptLocal = eventProof.encodedTxReceipt;
                rootEventData = extractRootEventData(eventProof.cbcContract, encodedRootTxReceiptLocal);
            }
            else {
                //Check that the blockchain Id for the segment was matches this contract's blockchain id.
                require(myBlockchainId == eventProof.blockchainId, "Not the correct blockchain id");

                // Ensure this is the cross-blockchain contract contract that generated the segment event.
                require(address(this) == eventProof.cbcContract, "Segment blockchain CBC contract was not this one");

                // Extract segment events
                segmentEvents[i-1] = extractSegmentEventData(eventProof.cbcContract, eventProof.encodedTxReceipt);
            }
        }
        signallingProcessing(rootBlockchainId, rootEventData, segmentEvents);
    }


    function unpackEventProofs(bytes[] calldata _eventProofsEncoded) private view
        returns(uint256 startEventBlockchainId, address startEventCbcAddress, bytes memory startEventData, bytes[] memory segmentEvents) {
        segmentEvents = new bytes[](_eventProofsEncoded.length - 1);

        //Verify the start event and all segment events.
        for (uint256 i = 0; i < _eventProofsEncoded.length; i++) {
            EventProof memory eventProof = toEventProof(_eventProofsEncoded[i]);
            txReceiptRootStorage.verify(
                eventProof.blockchainId,
                eventProof.cbcContract,
                eventProof.txReceiptRoot,
                eventProof.encodedTxReceipt,
                eventProof.proofOffsets,
                eventProof.proof);

            if (i == 0) {
                startEventBlockchainId = eventProof.blockchainId;
                startEventCbcAddress = eventProof.cbcContract;

                // Extract the start event from the RLP encoded receipt trie data.
                bytes memory encodedStartTxReceiptLocal = eventProof.encodedTxReceipt;
                startEventData = extractStartEventData(startEventCbcAddress, encodedStartTxReceiptLocal);
            }
            else {
                // Extract segment events
                segmentEvents[i-1] = extractSegmentEventData(eventProof.cbcContract, eventProof.encodedTxReceipt);
            }
        }
        return (startEventBlockchainId, startEventCbcAddress, startEventData, segmentEvents);
    }



    function toEventProof(bytes memory _encodedEventProof) private pure returns(EventProof memory){
        RLP.RLPItem[] memory encodedEventProof = RLP.toList(RLP.toRLPItem(_encodedEventProof));
        uint256 blockchainId = BytesUtil.bytesToUint256(RLP.toData(encodedEventProof[0]), 0);
        address cbcContract = BytesUtil.bytesToAddress(RLP.toData(encodedEventProof[1]));
        bytes32 txReceiptRoot = BytesUtil.bytesToBytes32(RLP.toData(encodedEventProof[2]), 0);
        bytes memory encodedTxReceipt = RLP.toData(encodedEventProof[3]);
        RLP.RLPItem[] memory proofOffsetsRlp = RLP.toList(encodedEventProof[4]);
        RLP.RLPItem[] memory proofRlp = RLP.toList(encodedEventProof[5]);
        require(proofOffsetsRlp.length == proofRlp.length, "Length of proofOffsets does not match length proof");

        uint256[] memory proofOffsets = new uint256[](proofOffsetsRlp.length);
        bytes[] memory proof = new bytes[](proofRlp.length);
        for (uint256 i = 0; i < proofRlp.length; i++) {
            proofOffsets[i] = BytesUtil.bytesToUint256(RLP.toData(proofOffsetsRlp[i]), 0);
            proof[i] = RLP.toData(proofRlp[i]);
        }

        return EventProof(blockchainId, cbcContract, txReceiptRoot, encodedTxReceipt, proofOffsets, proof);
    }

    function extractStartEventData(address _rootCBCContract, bytes memory _encodedStartTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory startEventData;
        (, startEventData) = extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        return startEventData;
    }

    function extractSegmentEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory segmentEventData;
        (, segmentEventData) = extractEvent(_cBCContract, SEGMENT_EVENT_SIGNATURE, receiptBytes);
        return segmentEventData;
    }

    function extractRootEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory rootEventData;
        (, rootEventData) = extractEvent(_cBCContract, ROOT_EVENT_SIGNATURE, receiptBytes);
        return rootEventData;
    }

}