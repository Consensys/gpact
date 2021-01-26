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

/**
 * Interface to be used for Lockable Storage contracts to set and gain access to the state machine
 * for the executing cross-blockchain transaction.
 *
 */
interface CbcLockableStorageInterface {

    function crossBlockchainCall(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external;

    function crossBlockchainCallReturnsUint256(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external returns (uint256);

    // Called by a provisional storage contract indicating the contract needs to be locked.
    function addToListOfLockedContracts(address _contractToLock) external;

    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() external view returns (bool);

    function getActiveCallRootBlockchainId() external view returns (uint256);

    function getActiveCallCrossBlockchainTransactionId() external view returns (uint256);
}