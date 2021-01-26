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
import "./LockableStorage.sol";

contract LockableStorageWrapper  {
    LockableStorage public storageContract;

    constructor (address _storageContract) {
        storageContract = LockableStorage(_storageContract);
    }


    function setUint256(uint256 _key, uint256 _val) internal {
        try storageContract.setUint256(_key, _val) {
            // No more business logic to do.
        } catch Error(string memory reason) {
            revert(reason);
        } catch (bytes memory lowLevelData) {
            revert(string(lowLevelData));
        }
    }

    function setBool(uint256 _key, bool _flag) internal {
        storageContract.setUint256(_key, (_flag ? 1 : 0));
    }

    function setAddress(uint256 _key, address _address) internal {
        storageContract.setUint256(_key, uint256(_address));
    }

    function setBytes(uint256 _key, bytes calldata _val) internal {
        storageContract.setBytes(_key, _val);
    }


    function setArrayValue(uint256 _key, uint256 _index, uint256 _val) internal {
        // Location of the key is the length.
        uint256 len = storageContract.getUint256(_key);
        require(_index < len, "Index out of bounds");
        // Keccak256(_key) is the location of the array elements.
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + _index, _val);
    }

    function pushArrayValue(uint256 _key, uint256 _val) internal {
        uint256 len = storageContract.getUint256(_key);
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + len, _val);
        storageContract.setUint256(_key, len+1);
    }

    function popArrayValue(uint256 _key) internal {
        uint256 len = storageContract.getUint256(_key);
        require(len > 0, "Pop called onzero length array");
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + len, 0);
        storageContract.setUint256(_key, len-1);
    }

    function setMapValue(uint256 _key, uint256 _mapKey, uint256 _val) internal {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        storageContract.setUint256(uint256(index), _val);
    }


    function getUint256(uint256 _key) internal view returns(uint256) {
        return storageContract.getUint256(_key);
    }

    function getBool(uint256 _key) internal view returns(bool) {
        return storageContract.getUint256(_key) == 1 ? true : false;
    }

    function getAddress(uint256 _key) internal view returns(address) {
        return address(storageContract.getUint256(_key));
    }

    function getBytes(uint256 _key) internal view returns(bytes memory) {
        return storageContract.getBytes(_key);
    }

    function getArrayLength(uint256 _key) internal view returns(uint256) {
        return storageContract.getUint256(_key);
    }

    function getArrayValue(uint256 _key, uint256 _index) internal view returns(uint256) {
        uint256 len = storageContract.getUint256(_key);
        require(len > _index, "Index out of bounds");
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        return storageContract.getUint256(uint256(startOfArrayLocation) + _index);
    }

    function getMapValue(uint256 _key, uint256 _mapKey) internal view returns(uint256) {
        bytes32 index = keccak256(abi.encodePacked(_key, _mapKey));
        return storageContract.getUint256(uint256(index));
    }

}
