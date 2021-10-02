/*
 * Copyright 2019 ConsenSys Software Inc
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
pragma experimental ABIEncoderV2;

import "../../../../../../functioncall/interface/src/main/solidity/CrosschainLockingInterface.sol";


contract MockCbcForLockableStorageTest is CrosschainLockingInterface {
    bytes32 private rootTxId;
    address[] activeCallLockedContracts;

    function setCrosschainRootTxId(bytes32 _txId) external {
        rootTxId = _txId;
    }

    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() external override view returns (bool) {
        return rootTxId == bytes32(0);
    }

    function getActiveCallCrosschainRootTxId() external override view returns (bytes32) {
        return rootTxId;
    }



    // ****** Functions below here not used in this test ****

//    function crossBlockchainCall(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override {
//
//    }


//    function crossBlockchainCallReturnsUint256(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override pure returns (uint256) {
//        return uint256(0);
//    }



    function addToListOfLockedContracts(address _contractToLock) external override {
        // Don't add the same contract twice. So, check the contract isn't in
        // the array first.
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            if (activeCallLockedContracts[i] == _contractToLock) {
                return;
            }
        }
        activeCallLockedContracts.push(_contractToLock);
    }

    function clearListOfLockedContracts() external {
        delete activeCallLockedContracts;
    }
}