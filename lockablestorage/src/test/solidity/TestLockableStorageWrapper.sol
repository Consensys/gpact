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

import "../../main/solidity/LockableStorageWrapper.sol";

contract TestLockableStorageWrapper is LockableStorageWrapper {

    constructor (address _storageContract) LockableStorageWrapper(_storageContract){
    }


    function test_setUint256(uint256 _key, uint256 _val) external {
        setUint256(_key, _val);
    }

    function test_setBool(uint256 _key, bool _flag) external {
        setBool(_key, _flag);
    }

    function test_setAddress(uint256 _key, address _address) external {
        setAddress(_key, _address);
    }

    function test_setBytes(uint256 _key, bytes calldata _val) external {
        setBytes(_key, _val);
    }


    function test_setArrayValue(uint256 _key, uint256 _index, uint256 _val) external {
        setArrayValue(_key, _index, _val);
    }

    function test_pushArrayValue(uint256 _key, uint256 _val) external {
        pushArrayValue(_key, _val);
    }

    function test_popArrayValue(uint256 _key) external {
        popArrayValue(_key);
    }

    function test_setMapValue(uint256 _key, uint256 _mapKey, uint256 _val) external {
        setMapValue(_key, _mapKey, _val);
    }


    function test_getUint256(uint256 _key) external view returns(uint256) {
        return getUint256(_key);
    }

    function test_getBool(uint256 _key) external view returns(bool) {
        return getBool(_key);
    }

    function test_getAddress(uint256 _key) external view returns(address) {
        return getAddress(_key);
    }

    function test_getBytes(uint256 _key) external view returns(bytes memory) {
        return getBytes(_key);
    }

    function test_getArrayLength(uint256 _key) external view returns(uint256) {
        return getArrayLength(_key);
    }

    function test_getArrayValue(uint256 _key, uint256 _index) external view returns(uint256) {
        return getArrayValue(_key, _index);
    }

    function test_getMapValue(uint256 _key, uint256 _mapKey) external view returns(uint256) {
        return getMapValue(_key, _mapKey);
    }
}
