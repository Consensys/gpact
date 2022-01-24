/*
 * Copyright 2020 ConsenSys Software Inc
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
import "./OtherBlockchainContractInterface.sol";
import "../../../../contracts/src/application/lockablestorage/LockableStorage.sol";

contract OtherBlockchainContract is
    OtherBlockchainContractInterface,
    LockableStorage
{
    uint256 private constant KEY_FOR_VAL = 1;

    constructor(address _crossBlockchainControl)
        LockableStorage(_crossBlockchainControl)
    {}

    function setVal(uint256 _val) public override {
        setUint256(KEY_FOR_VAL, _val);
    }

    function incrementVal() external override {
        uint256 aVal = getUint256(KEY_FOR_VAL);
        aVal++;
        setUint256(KEY_FOR_VAL, aVal);
    }

    function setValues(uint256 _val1, uint256 _val2) external override {
        setVal(_val1 + _val2);
    }

    function getVal() external view override returns (uint256) {
        return getUint256(KEY_FOR_VAL);
    }
}
