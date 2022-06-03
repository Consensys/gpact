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

import "./CallPathCallExecutionTree.sol";

contract FailureTest {
    uint256 otherBlockchainId;
    FailureTest otherContract;
    address cbc;

    constructor(
        address _crossBlockchainControl,
        uint256 _otherBlockchainId,
        address _otherContract
    ) {
        cbc = _crossBlockchainControl;
        otherBlockchainId = _otherBlockchainId;
        otherContract = FailureTest(_otherContract);
    }

    function callRemote(uint256 _totalCallDepth, uint256 _whenToFail) external {
        callRemote1(_totalCallDepth, 0, _whenToFail) external {
    }

    function callRemote1(uint256 _totalCallDepth, uint256 _currentCallDepth, uint256 _whenToFail) external {
        if (_currentCallDepth == _whenToFail) {
            revert("Time to Fail!");
        }

        CrosschainFunctionCallInterface(address(cbc)).crossBlockchainCall(
            otherBlockchainId,
            address(otherContract),
            abi.encodeWithSelector(
                otherContract.callRemote1.selector,
                _totalCallDepth,
                _currentCallDepth + 1,
                _whenToFail
            )
        );
    }
}
