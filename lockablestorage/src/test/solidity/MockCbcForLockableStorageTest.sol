/*
 * Copyright 2019 ConsenSys AG.
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

import "../../../../crossblockchaincontrol/src/main/solidity/CbcLockableStorageInterface.sol";


contract MockCbcForLockableStorageTest is CbcLockableStorageInterface {
    uint256 private rootBlockchainId;
    uint256 private transactionId;

    function setRootBlockchainId(uint256 _rootBcId) external {
        rootBlockchainId = _rootBcId;
    }

    function setCrossBlockchainTransactionId(uint256 _txId) external {
        transactionId = _txId;
    }


    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() external override view returns (bool) {
        return rootBlockchainId == 0;
    }

    function getActiveCallRootBlockchainId() external override view returns (uint256) {
        return rootBlockchainId;
    }

    function getActiveCallCrossBlockchainTransactionId() external override view returns (uint256) {
        return transactionId;
    }



    // ****** Functions below here not used in this test ****

    function crossBlockchainCall(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override {

    }


    function crossBlockchainCallReturnsUint256(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override pure returns (uint256) {
        return uint256(0);
    }

    // Called by a provisional storage contract indicating the contract needs to be locked.
    function addToListOfLockedContracts(address /* _contractToLock */ ) external override {

    }
}