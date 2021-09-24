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
import "../../../../../common/common/src/main/solidity/BytesUtil.sol";




contract SimpleCrosschainControl is CrosschainFunctionCallInterface, CbcDecVer, BytesUtil {
    // 	0x77dab611
    bytes32 constant internal CROSS_CALL_EVENT_SIGNATURE = keccak256("CrossCall(uint256,uint256,address,uint256,address,bytes)");

    // How old events can be before they are not accepted.
    // Also used as a time after which crosschain transaction ids can be purged from the
    // replayProvention map, thus reducing the cost of the crosschain transaction.
    uint256 public timeHorizon;

    // Used to prevent replay attacks in transaction.
    // Mapping of txId to transaction expiry time.
    mapping (bytes32=> uint256) public replayPrevention;

    uint256 public myBlockchainId;

    uint256 private activeCallParentBlockchainId;
    address private activeCallParentContract;

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
    event CrossCall(bytes32 _txId, uint256 _timestamp, address _caller,
        uint256 _destBcId, address _destContract, bytes _destFunctionCall);

    event CallFailure(string _revertReason);



    constructor(uint256 _myBlockchainId) {
        myBlockchainId = _myBlockchainId;
    }

    function crossBlockchainCall(uint256 _destBcId, address _destContract, bytes calldata _destData) override external {
        // Note that this limits the number of calls to the same contract
        // from this blockchain with the same function call data to one per
        // block.
        bytes32 txId = keccak256(abi.encodePacked(block.timestamp, myBlockchainId, _destBcId, _destContract, _destData));
        emit CrossCall(txId, block.timestamp, msg.sender, _destBcId, _destContract, _destData);
    }


    /**
     * Call the crossCallHandler, but first specify a set of old transaction
     * identifiers. Releasing storage will tend to reduce the gas cost of the
     * overall transaction (15,000 gas is returned for each storage location
     * that has a non-zero value that is set to zero).
     */
    function crossCallHandlerSaveGas(uint256 _sourceBcId, address _cbcAddress,
        bytes calldata _eventData, bytes calldata _signature, bytes32[] calldata _oldTxIds) external {

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


    function crossCallHandler(uint256 _sourceBcId, address _cbcAddress,
        bytes calldata _eventData, bytes calldata _signature) public {
        decodeAndVerifyEvent(_sourceBcId, _cbcAddress, CROSS_CALL_EVENT_SIGNATURE, _eventData, _signature);

        // Decode _eventData
        // Recall that the cross call event is:
        // CrossCall(uint256 _txId, uint256 _timestamp, address _caller,
        //           uint256 _destBcId, address _destContract, bytes _destFunctionCall)
        bytes32 txId = BytesUtil.bytesToBytes32CallData(_eventData, 0);
        require(replayPrevention[txId] == 0, "Transaction already exists");

        uint256 timestamp = BytesUtil.bytesToUint256(_eventData, 0x20);
        require(timestamp < block.timestamp, "Event timestamp is in the future");
        require(timestamp + timeHorizon > block.timestamp, "Event is too old");
        replayPrevention[txId] = timestamp;

        address caller = BytesUtil.bytesToAddress1(_eventData, 0x40);

        uint256 destBcId = BytesUtil.bytesToUint256(_eventData, 0x60);
        require(destBcId == myBlockchainId);

        address destContract = BytesUtil.bytesToAddress1(_eventData, 0x80);
        uint256 lenOfFunctionCall = BytesUtil.bytesToUint256(_eventData, 0xA0);
        bytes memory functionCall = BytesUtil.slice(_eventData, 0xC0, lenOfFunctionCall);

        // Set-up active call state.
        activeCallParentBlockchainId = _sourceBcId;
        activeCallParentContract = caller;

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = destContract.call(functionCall);

        if (!isSuccess) {
            emit CallFailure(getRevertMsg(returnValueEncoded));
        }

        // Clear active call state.
        activeCallParentBlockchainId = 0;
        activeCallParentContract = address(0);
    }



    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() public override view returns (bool) {
        return 0 == activeCallParentBlockchainId;
    }

    function whoCalledMe() external view override returns (uint256 rootBlockchainId, uint256 parentBlockchainId, address parentContract) {
        uint256 parentBcId = activeCallParentBlockchainId;
        return (parentBcId, parentBcId, activeCallParentContract);
    }

    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************

    function getRevertMsg(bytes memory _returnData) internal pure returns (string memory) {
        // If the _res length is less than 68, then the transaction failed silently (without a revert message)
        if (_returnData.length < 68) return 'Transaction reverted silently';

        assembly {
        // Slice the sighash.
            _returnData := add(_returnData, 0x04)
        }
        return abi.decode(_returnData, (string)); // All that remains is the revert string
    }
}