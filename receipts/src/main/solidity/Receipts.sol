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
import "../../../../common/src/main/solidity/BytesUtil.sol";

/**
 * Transaction receipt processing.
 */
contract Receipts is RLP, BytesUtil {
    /**
     * Find an event emitted by a certain contract. Extract topics and data from transaction receipt log.
     *
     * @param _contractAddress Address of contract that emitted event.
     * @param _eventFunctionSignature Keccak 256 of event function signature.
     * @param _receiptRlp RLP encoded receipt that should contain the log event.
     * @return topics The topics associated with the event.
     * @return data The RLP encoded data associated with the event.
     */
    function extractEvent(address _contractAddress, bytes32 _eventFunctionSignature, bytes memory _receiptRlp)
       internal pure returns (RLP.RLPItem[] memory topics, bytes memory data) {
        // Decode the receipt into an array of RLP items.
        //  receipt[0]: state root or status
        //  receipt[1]: cumulative gas used
        //  receipt[2]: Bloom Filter
        //  receipt[3]: event logs
        //  receipt[4]: Revert Reason
        RLP.RLPItem[] memory receipt = RLP.toList(RLP.toRLPItem(_receiptRlp));
        RLP.RLPItem[] memory logs = RLP.toList(receipt[3]);

        // The receipt may contain multiple event logs if the code called by the transaction emitted
        // multiple events.
        for (uint256 i = 0; i < logs.length; i++) {
            RLP.RLPItem[] memory log = RLP.toList(logs[i]);
            // The component's of a log is:
            //  log[0]: Address of contract that emitted the event.
            //  log[1]: Topics. These are the event function signature and indexed parameters.
            //  log[2]: Data. The RLP encoded parameters.
            address contractAddressEmitter = BytesUtil.bytesToAddress(RLP.toData(log[0]));
            topics = RLP.toList(log[1]);
            data = RLP.toData(log[2]);

            // With the exception of anonymous topics, the first topic is the Keccak 256 of the
            // event's function signature.
            bytes32 eventSignature = RLP.toBytes32(topics[0]);
            if ((eventSignature == _eventFunctionSignature) && (contractAddressEmitter == _contractAddress)){
                return (topics, data);
            }
        }
        revert("No event found in transaction receipt");
    }


}