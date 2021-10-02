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

import "./ContractB.sol";
import "../../../../../appcontracts/lockablestorage/src/main/solidity/LockableStorage.sol";
import "../../../../../../functioncall/interface/src/main/solidity/CrosschainFunctionCallReturnInterface.sol";

contract ContractA is LockableStorage {
    uint256 otherBcId;
    ContractB contractB;

    uint256 constant private KEY_VAL = 0;

    event ValueRead(uint256 _val);

    constructor (address _cbc, uint256 _otherBcId, address _contractBAddress)
        LockableStorage(_cbc) {

        otherBcId = _otherBcId;
        contractB = ContractB(_contractBAddress);
    }

    function doCrosschainRead() external {
        uint256 val = CrosschainFunctionCallReturnInterface(address(cbc)).crossBlockchainCallReturnsUint256(
            otherBcId, address(contractB), abi.encodeWithSelector(contractB.get.selector));
        setUint256(KEY_VAL, val);
        emit ValueRead(val);
    }

    function getVal() external view returns(uint256) {
        return getUint256(KEY_VAL);
    }
}
