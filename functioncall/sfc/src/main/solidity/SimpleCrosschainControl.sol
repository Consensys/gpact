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

import "../../../../interface/src/main/solidity/CrosschainFunctionCallInterface.sol";
import "../../../../../messaging/interface/src/main/solidity/CrosschainVerifier.sol";
import "../../../../gpact/src/main/solidity/CbcDecVer.sol";
import "../../../../interface/src/main/solidity/HiddenParameters.sol";
import "../../../../../common/common/src/main/solidity/ResponseProcessUtil.sol";

contract SimpleCrosschainControl is
    CrosschainFunctionCallInterface,
    CbcDecVer,
    HiddenParameters,
    ResponseProcessUtil
{
    // 	0x77dab611
    bytes32 internal constant CROSS_CALL_EVENT_SIGNATURE =
        keccak256("CrossCall(bytes32,uint256,address,uint256,address,bytes)");

    // How old events can be before they are not accepted.
    // Also used as a time after which crosschain transaction ids can be purged from the
    // replayProvention map, thus reducing the cost of the crosschain transaction.
    uint256 public timeHorizon;

    // Used to prevent replay attacks in transaction.
    // Mapping of txId to transaction expiry time.
    mapping(bytes32 => uint256) public replayPrevention;

    uint256 public myBlockchainId;

    /**
     * Crosschain Transaction event.
     *
     * @param _txId Crosschain Transaction id.
     * @param _timestamp The time when the event was generated.
     * @param _caller Contract or EOA that submitted the crosschain call on the source blockchain.
     * @param _destBcId Destination blockchain Id.
     * @param _destContract Contract to be called on the destination blockchain.
     * @param _destFunctionCall The function selector and parameters in ABI packed format.
     */
    event CrossCall(
        bytes32 _txId,
        uint256 _timestamp,
        address _caller,
        uint256 _destBcId,
        address _destContract,
        bytes _destFunctionCall
    );

    event CallFailure(string _revertReason);

    /**
     * @param _myBlockchainId Blockchain identifier of this blockchain.
     * @param _timeHorizon How old crosschain events can be before they are
     *     deemed to be invalid. Measured in seconds.
     */
    constructor(uint256 _myBlockchainId, uint256 _timeHorizon) {
        myBlockchainId = _myBlockchainId;
        timeHorizon = _timeHorizon;
    }

    function crossBlockchainCall(
        uint256 _destBcId,
        address _destContract,
        bytes calldata _destData
    ) external override {
        // Note that this limits the number of calls to the same contract
        // from this blockchain with the same function call data to one per
        // block.
        bytes32 txId = keccak256(
            abi.encodePacked(
                block.timestamp,
                myBlockchainId,
                _destBcId,
                _destContract,
                _destData
            )
        );
        emit CrossCall(
            txId,
            block.timestamp,
            msg.sender,
            _destBcId,
            _destContract,
            _destData
        );
    }

    /**
     * Call the crossCallHandler, but first specify a set of old transaction
     * identifiers. Releasing storage will tend to reduce the gas cost of the
     * overall transaction (15,000 gas is returned for each storage location
     * that has a non-zero value that is set to zero).
     */
    function crossCallHandlerSaveGas(
        uint256 _sourceBcId,
        address _cbcAddress,
        bytes calldata _eventData,
        bytes calldata _signature,
        bytes32[] calldata _oldTxIds
    ) external {
        // Go through the array of old crosschain transaction ids. If they
        for (uint256 i = 0; i < _oldTxIds.length; i++) {
            bytes32 oldTxId = _oldTxIds[i];
            uint256 timestamp = replayPrevention[oldTxId];
            if (timestamp + timeHorizon > block.timestamp) {
                if (timestamp != 0) {
                    replayPrevention[oldTxId] = 0;
                }
            }
        }
        crossCallHandler(_sourceBcId, _cbcAddress, _eventData, _signature);
    }

    function crossCallHandler(
        uint256 _sourceBcId,
        address _cbcAddress,
        bytes calldata _eventData,
        bytes calldata _signature
    ) public {
        decodeAndVerifyEvent(
            _sourceBcId,
            _cbcAddress,
            CROSS_CALL_EVENT_SIGNATURE,
            _eventData,
            _signature
        );

        // Decode _eventData
        // Recall that the cross call event is:
        // CrossCall(bytes32 _txId, uint256 _timestamp, address _caller,
        //           uint256 _destBcId, address _destContract, bytes _destFunctionCall)
        bytes32 txId;
        uint256 timestamp;
        address caller;
        uint256 destBcId;
        address destContract;
        bytes memory functionCall;
        (txId, timestamp, caller, destBcId, destContract, functionCall) = abi
            .decode(
                _eventData,
                (bytes32, uint256, address, uint256, address, bytes)
            );

        require(replayPrevention[txId] == 0, "Transaction already exists");

        require(
            timestamp < block.timestamp,
            "Event timestamp is in the future"
        );
        require(timestamp + timeHorizon > block.timestamp, "Event is too old");
        replayPrevention[txId] = timestamp;

        require(destBcId == myBlockchainId);

        // Add authentication information to the function call.
        bytes memory functionCallWithAuth = encodeTwoHiddenParams(
            functionCall,
            _sourceBcId,
            uint256(uint160(caller))
        );

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = destContract.call(
            functionCallWithAuth
        );

        if (!isSuccess) {
            emit CallFailure(getRevertMsg(returnValueEncoded));
        }
    }
}
