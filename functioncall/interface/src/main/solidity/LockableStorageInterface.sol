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
 * Lockable storage interface defines the API between the function call layer and
 * the application layer to allow the function call layer to indicate to the application
 * layer that locked values should be committed or discarded.
 *
 */
interface LockableStorageInterface {
    /**
     * Called by the crosschain control contract when the call has been completed.
     *
     * @param _commit True if the provisional updates should be committed. False indicates the
     *         provisional updates should be discarded.
     * @param _crossRootTxId a value that indicates the transaction that has been completed.
     *        The value is the keccak256 message digest of the Root Blockchain Id and the
     *        Crosschain Transaction Id.
     */
    function finalise(bool _commit, bytes32 _crossRootTxId) external;
}
