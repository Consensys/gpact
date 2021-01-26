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

import "../../../../rlp/src/main/solidity/RLP.sol";
import "../../main/solidity/Receipts.sol";

/**
 * Transaction receipt processing.
 */
contract TestReceipts is Receipts {
    bytes32 constant private startEventFunctionSignature = keccak256("StartEvent(uint256)");


    function retrieveStartLog(address _contractAddress, bytes memory _receiptRlp)
      external pure returns (bytes memory){
        (, bytes memory data) = extractEvent(_contractAddress, startEventFunctionSignature, _receiptRlp);
        return data;
    }


    function retrieveALog(address _contractAddress, bytes32 _eventFunctionSignature, bytes memory _receiptRlp)
       external pure returns (bytes memory){
        (, bytes memory data) = extractEvent(_contractAddress, _eventFunctionSignature, _receiptRlp);
        return data;
    }


    function triggerStartEvent(uint256 _val) external {
        emit StartEvent(_val);
    }


    function numLogsFound(bytes memory _receiptRlp) external pure returns (uint){
        RLP.RLPItem[] memory receipt = RLP.toList(RLP.toRLPItem(_receiptRlp));
        RLP.RLPItem[] memory logs = RLP.toList(receipt[3]);
        return logs.length;
    }

    function emittingContractFirstLog(bytes memory _receiptRlp) external pure returns (address){
        RLP.RLPItem[] memory receipt = RLP.toList(RLP.toRLPItem(_receiptRlp));
        RLP.RLPItem[] memory logs = RLP.toList(receipt[3]);
        require(logs.length == 1);
        RLP.RLPItem[] memory log = RLP.toList(logs[0]);
        address contractAddressEmitter = BytesUtil.bytesToAddress(RLP.toData(log[0]));
        return contractAddressEmitter;
    }

    function getEventSignatureFirstLog(bytes memory _receiptRlp) external pure returns (address){
        RLP.RLPItem[] memory receipt = RLP.toList(RLP.toRLPItem(_receiptRlp));
        RLP.RLPItem[] memory logs = RLP.toList(receipt[3]);
        require(logs.length == 1);
        RLP.RLPItem[] memory log = RLP.toList(logs[0]);
        address contractAddressEmitter = BytesUtil.bytesToAddress(RLP.toData(log[0]));
        return contractAddressEmitter;
    }

    event StartEvent(uint256 _val);

}