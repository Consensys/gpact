/*
 * Copyright 2019 ConsenSys Software Inc
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
import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "../../../../receipts/src/main/solidity/Receipts.sol";

contract CrosschainVerifierTxRoot is CrosschainVerifier,  Receipts {
    TxReceiptsRootStorageInterface private txReceiptRootStorage;
    Registrar private registrar;

    struct EventProof {
        uint256 blockchainId;
        address cbcContract;
        bytes32 txReceiptRoot;
        bytes encodedTxReceipt;
        uint256[] proofOffsets;
        bytes[] proof;
    }

    constructor(address _registrar, address _txReceiptRootStorage) {
        registrar = Registrar(_registrar);
        txReceiptRootStorage = TxReceiptsRootStorageInterface(_txReceiptRootStorage);
    }


    function decodeAndVerifyEvent(uint256 _expectedBlockchainId, bytes32 _eventSig, bytes calldata _encodedEvent, bytes calldata _proof)
        external view override {

        address cbcContract = registrar.getApprovedContract(_expectedBlockchainId);

        EventProof memory eventProof = toEventProof(_proof);
        txReceiptRootStorage.verify(
            eventProof.blockchainId,
            eventProof.cbcContract,
            eventProof.txReceiptRoot,
            eventProof.encodedTxReceipt,
            eventProof.proofOffsets,
            eventProof.proof);
        require(_expectedBlockchainId == eventProof.blockchainId, "Event not emitted by expected blockchain");

        // Extract from the encoded transaction receipt the Patricia Merkle Trie key and the receipt.
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(eventProof.encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);

        bytes memory eventData;
        // If the CBC Contract is incorrect, extractEvent will fail.
        (, eventData) = extractEvent(eventProof.cbcContract, _eventSig, receiptBytes);

        bytes memory calculatedEncoded = abi.encodePacked(_expectedBlockchainId, eventProof.cbcContract, _eventSig, eventData);
        require(compare(calculatedEncoded, _encodedEvent), "Expected event does not match event in proof");
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
}