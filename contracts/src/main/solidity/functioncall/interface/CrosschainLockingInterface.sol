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

/**
 * Crosschain locking interface defines the API to be used by lockable storage
 * contracts to indicate to the function call layer that a contract has provisional
 * updates due to the current crosschain function call, and to determine information
 *
 *
 */
interface CrosschainLockingInterface {
    /**
     * Called by a lockable storage contract to indicate that it contains one
     * or more storage locations that are being locked by the current function
     * call.
     *
     * @param _contractToLock The address of the contract contained the locked
     *        storage locations.
     */
    function addToListOfLockedContracts(address _contractToLock) external;

    /**
     * Get the combined Root Blockchain / Crosschain Transaction id for the
     * current crosschain transaction.
     *
     * @return Crosschain Root Blockchain / Transaction Id. The value is zero
     *         if there is no active crosschain call (and this is a single
     *         blockchain function call).
     */
    function getActiveCallCrosschainRootTxId() external view returns (bytes32);

    function isSingleBlockchainCall() external view returns (bool);
}
