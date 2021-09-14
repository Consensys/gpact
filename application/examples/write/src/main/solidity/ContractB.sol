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
import "../../../../../appcontracts/lockablestorage/src/main/solidity/LockableStorage.sol";



contract ContractB is LockableStorage {
    uint256 constant private KEY_VAL = 0;


    event ValueWritten(uint256 _val);

    constructor(address _crossBlockchainControl) LockableStorage(_crossBlockchainControl) {
    }

    function set(uint256 _val) external {
        setUint256(KEY_VAL, _val);
        emit ValueWritten(_val);
    }

    function getVal() external view returns(uint256) {
        return getUint256(KEY_VAL);
    }
}
