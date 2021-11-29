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
pragma solidity >=0.7.1;

import "../../../../../../functioncall/interface/src/main/solidity/LockableStorageInterface.sol";
import "../../../../../../functioncall/interface/src/main/solidity/CrosschainLockingInterface.sol";

/**
 * Lockable storage contract:
 *  - lockable per item.
 *  - designed to be integrated directly into another contract.
 *  - uint256 based storage only.
 *  - Can not store max uint256 value - 1.
 *
 */
abstract contract LockableStorage is LockableStorageInterface {
    // A value added to all values stored in provisional storage so that
    // lock detection can happen without using an extra memory location.
    uint256 private constant NOT_USED_OFFSET = 0x1;
    // The value that provisional storage will be if it is not being used.
    uint256 private constant NOT_LOCKED_INDICATOR = 0;

    // The Cross-Blockchain Control Contract used for cross-blockchain transaction locking.
    CrosschainLockingInterface internal cbc;

    // Data storage keys to values stored.
    mapping(uint256 => uint256) private dataStore;
    // Provisional updates for data storage keys to values stored.
    // Values are offset by the NOT_USED_OFFSET value so that locked storage can be
    // detected without using an extra memory location.
    mapping(uint256 => uint256) private provisionalUpdates;

    // Map of list of keys for values that have been updated / are locked.
    // Map key is keccak256(root bc id, transaction id)
    mapping(bytes32 => uint256[]) private provisionalUpdatesLists;

    constructor(address _crossBlockchainControl) {
        cbc = CrosschainLockingInterface(_crossBlockchainControl);
    }

    /**
     * Set a uint256 value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The value to store.
     */
    function setUint256(uint256 _key, uint256 _val) internal {
        //   If item locked {throw an error}
        if (isLocked(_key)) {
            revert("Contract item locked");
        }

        bytes32 crossRootTxId = cbc.getActiveCallCrosschainRootTxId();
        if (crossRootTxId == bytes32(0)) {
            // Single blockchain call
            dataStore[_key] = _val;
        } else {
            cbc.addToListOfLockedContracts(address(this));
            provisionalUpdatesLists[crossRootTxId].push(_key);
            // Note, the following addition is unchecked. If the value being stored
            // is 2**256-1 then this will overflow and revert.
            provisionalUpdates[_key] = _val + NOT_USED_OFFSET;
        }
    }

    function setBool(uint256 _key, bool _flag) internal {
        setUint256(_key, (_flag ? 1 : 0));
    }

    function setAddress(uint256 _key, address _address) internal {
        setUint256(_key, uint256(uint160(_address)));
    }

    function setArrayValue(
        uint256 _key,
        uint256 _index,
        uint256 _val
    ) internal {
        // Location of the key is the length.
        uint256 len = getUint256(_key);
        require(_index < len, "Index out of bounds");
        // Keccak256(_key) is the location of the array elements.
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        setUint256(uint256(startOfArrayLocation) + _index, _val);
    }

    function pushArrayValue(uint256 _key, uint256 _val) internal {
        uint256 len = getUint256(_key);
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        setUint256(uint256(startOfArrayLocation) + len, _val);
        setUint256(_key, len + 1);
    }

    function popArrayValue(uint256 _key) internal {
        uint256 len = getUint256(_key);
        require(len > 0, "Pop called onzero length array");
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        setUint256(uint256(startOfArrayLocation) + len, 0);
        setUint256(_key, len - 1);
    }

    function setMapValue(
        uint256 _key,
        uint256 _mapKey,
        uint256 _val
    ) internal {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        setUint256(uint256(index), _val);
    }

    function setDoubleMapValue(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _val
    ) internal {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey1, _mapKey2));
        setUint256(uint256(index), _val);
    }

    function setTripleMapValue(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _mapKey3,
        uint256 _val
    ) internal {
        bytes32 index = keccak256(
            abi.encodePacked(_key, _mapKey1, _mapKey2, _mapKey3)
        );
        setUint256(uint256(index), _val);
    }

    /**
     * Called by the cross-blockchain control contract when the call has been completed.
     *
     * @param _commit True if the provisional updates should be committed. False indicates the
     *         provisional updates should be discarded.
     */
    function finalise(bool _commit, bytes32 _crossRootTxId)
        external
        override(LockableStorageInterface)
    {
        for (
            uint256 i = 0;
            i < provisionalUpdatesLists[_crossRootTxId].length;
            i++
        ) {
            uint256 key = provisionalUpdatesLists[_crossRootTxId][i];
            if (_commit) {
                // Copy provisional updates to data stores.
                dataStore[key] = provisionalUpdates[key] - NOT_USED_OFFSET;
            }
            // Delete provisional updates.
            delete provisionalUpdates[key];
        }
        // Delete the list of updates.
        delete provisionalUpdatesLists[_crossRootTxId];
    }

    /************************************************************************/
    /************************************************************************/
    /*****                        VIEW BELOW HERE                       *****/
    /************************************************************************/
    /************************************************************************/

    function getUint256(uint256 _key) internal view returns (uint256) {
        if (isLocked(_key)) {
            revert("Read while contract item locked");
        }
        return dataStore[_key];
    }

    function getUint256Committed(uint256 _key) internal view returns (uint256) {
        return dataStore[_key];
    }

    function getUint256Provisional(uint256 _key)
        internal
        view
        returns (uint256)
    {
        if (isLocked(_key)) {
            return provisionalUpdates[_key] - NOT_USED_OFFSET;
        }
        return dataStore[_key];
    }

    function getBool(uint256 _key) internal view returns (bool) {
        return getUint256(_key) == 1 ? true : false;
    }

    function getAddress(uint256 _key) internal view returns (address) {
        return address(uint160(getUint256(_key)));
    }

    function getArrayLength(uint256 _key) internal view returns (uint256) {
        return getUint256(_key);
    }

    function getArrayValue(uint256 _key, uint256 _index)
        internal
        view
        returns (uint256)
    {
        uint256 len = getUint256(_key);
        require(len > _index, "Index out of bounds");
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        return getUint256(uint256(startOfArrayLocation) + _index);
    }

    function getMapValue(uint256 _key, uint256 _mapKey)
        internal
        view
        returns (uint256)
    {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        return getUint256(uint256(index));
    }

    function getMapValueCommitted(uint256 _key, uint256 _mapKey)
        internal
        view
        returns (uint256)
    {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        return getUint256Committed(uint256(index));
    }

    function getMapValueProvisional(uint256 _key, uint256 _mapKey)
        internal
        view
        returns (uint256)
    {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        return getUint256Provisional(uint256(index));
    }

    function isMapValueLocked(uint256 _key, uint256 _mapKey)
        internal
        view
        returns (bool)
    {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        return isLocked(uint256(index));
    }

    function getDoubleMapValue(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2
    ) internal view returns (uint256) {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey1, _mapKey2));
        return getUint256(uint256(index));
    }

    function getDoubleMapValueCommitted(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2
    ) internal view returns (uint256) {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey1, _mapKey2));
        return getUint256Committed(uint256(index));
    }

    function getDoubleMapValueProvisional(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2
    ) internal view returns (uint256) {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey1, _mapKey2));
        return getUint256Provisional(uint256(index));
    }

    function isDoubleMapValueLocked(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2
    ) internal view returns (bool) {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey1, _mapKey2));
        return isLocked(uint256(index));
    }

    function getTripleMapValue(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _mapKey3
    ) internal view returns (uint256) {
        bytes32 index = keccak256(
            abi.encodePacked(_key, _mapKey1, _mapKey2, _mapKey3)
        );
        return getUint256(uint256(index));
    }

    function getTripleMapValueCommitted(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _mapKey3
    ) internal view returns (uint256) {
        bytes32 index = keccak256(
            abi.encodePacked(_key, _mapKey1, _mapKey2, _mapKey3)
        );
        return getUint256Committed(uint256(index));
    }

    function getTripleMapValueProvisional(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _mapKey3
    ) internal view returns (uint256) {
        bytes32 index = keccak256(
            abi.encodePacked(_key, _mapKey1, _mapKey2, _mapKey3)
        );
        return getUint256Provisional(uint256(index));
    }

    function isTripleMapValueLocked(
        uint256 _key,
        uint256 _mapKey1,
        uint256 _mapKey2,
        uint256 _mapKey3
    ) internal view returns (bool) {
        bytes32 index = keccak256(
            abi.encodePacked(_key, _mapKey1, _mapKey2, _mapKey3)
        );
        return isLocked(uint256(index));
    }

    function isLocked(uint256 _key) public view returns (bool) {
        return provisionalUpdates[_key] != NOT_LOCKED_INDICATOR;
    }
}
