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

import "../../../../../crossblockchaincontrol/src/main/solidity/CbcLockableStorageInterface.sol";


/**
 * Lockable storage contract.
 *
 * Write Algorithm: Used to write a value to the contract.
 *
 *  If caller is not Business Logic Contract {
 *    throw an error
 *  }
 *  Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
 *  If not (normal single blockchain call) {
 *    If locked {throw an error}
 *    Else (not locked) {Write to normal storage}
 *  }
 *  Else (this is a cross-blockchain call) {
 *    If locked {
 *      Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
 *      locked the contract?
 *      If no {throw an error}
 *      If yes {Write to provisional storage}
 *    }
 *    Else (not locked) {
 *      Lock the contract.
 *      Indicate in the Cross-Blockchain Control Contract that this call is locking the
 *       Lockable Storage contract
 *      Write to provisional storage
 *    }
 * }
 *
 * Read Algorithm: Used to read a value from the contract.
 *
 * If caller is not Business Logic Contract {
 *   throw an error
 * }
 * Check Cross-Blockchain Control Contract: is there an active cross-blockchain call?
 * If not (normal single blockchain call) {
 *   If locked {throw an error}
 *   Else (not locked) {Read from normal storage}
 * Else (this is a cross-blockchain call) { If locked {
 * Check Cross-Blockchain Control Contract: has this cross-blockchain call previously locked the contract?
 * If no {throw an error}
 * If yes {Allow the read. If the value isn’t available in provisional storage, return
 *  the value in normal storage.} }
 * Else (not locked) {Read from normal storage}
 */
contract LockableStorage {
    address immutable deployer;

    // TODO be able to upgrade the business logic contract
    // Address of the contract that this contract is supplying storage for.
    address public businessLogicContract;
    // The Cross-Blockchain Control Contract used for cross-blockchain transaction locking.
    CbcLockableStorageInterface private crossBlockchainControl;

    // True when this contract is locked.
    bool public locked;
    // Which root blockchain / transaction id locked this contract.
    uint256 public lockedByRootBlockchainId;
    uint256 public lockedByTransactionId;

    mapping(uint256=>uint256) private dataStoreUint256;
    mapping(uint256=>uint256) private provisionalUpdatesUint256;
    // List of keys for values that have been updated.
    uint256[] private provisionalUpdatesListUint256;
    // Indicates that a value for this key has already been updated.
    mapping(uint256=>bool) private provisionalUpdateExistsUint256;

    mapping(uint256=>bytes) private dataStoreBytes;
    mapping(uint256=>bytes) private provisionalUpdatesBytes;
    // List of keys for values that have been updated.
    uint256[] private provisionalUpdatesListBytes;
    // Indicates that a value for this key has already been updated.
    mapping(uint256=>bool) private provisionalUpdateExistsBytes;

    /**
     * Revert if the caller is not Business Logic Contract.
     */
    modifier onlyBusinessLogicContract() {
        if (msg.sender != businessLogicContract) {
            revert("Only call from business logic contract");
        }
        _;
    }

    constructor (address _crossBlockchainControl) {
        deployer = msg.sender;
        crossBlockchainControl = CbcLockableStorageInterface(_crossBlockchainControl);
    }

    /**
     * Set the business logic contract that this lockable storage contract relates to.
     * Can only be called once by the original deployer of this contract.
     *
     * @param _businessLogicContract The contract to link this contract to.
     */
    function setBusinessLogicContract(address _businessLogicContract) external {
        require(deployer == msg.sender);
        require(businessLogicContract == address(0));
        businessLogicContract = _businessLogicContract;
    }


    /**
     * Set a uint256 value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The value to store.
     */
    function setUint256(uint256 _key, uint256 _val) external onlyBusinessLogicContract {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Write to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            if (locked) {
                revert("Attempted single blockchain call when contract locked");
            }
            dataStoreUint256[_key] = _val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Write to provisional storage}
            if (locked) {
                checkLocking();
                provisionalUpdatesUint256[_key] = _val;
                if (!provisionalUpdateExistsUint256[_key]) {
                    provisionalUpdatesListUint256.push(_key);
                    provisionalUpdateExistsUint256[_key] = true;
                }
            }
            //   Else (not locked) {
            //     Lock the contract.
            //     Indicate in the Cross-Blockchain Control Contract that this call is locking the
            //      Lockable Storage contract
            //     Write to provisional storage
            else {
                lockContract();
                provisionalUpdatesUint256[_key] = _val;
                provisionalUpdatesListUint256.push(_key);
                provisionalUpdateExistsUint256[_key] = true;
            }
        }
    }



    /**
     * Set a bytes value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The value to store.
     */
    function setBytes(uint256 _key, bytes calldata _val) external onlyBusinessLogicContract {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Write to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            if (locked) {
                revert("Attempted single blockchain call when contract locked");
            }
            dataStoreBytes[_key] = _val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Write to provisional storage}
            if (locked) {
                checkLocking();
                provisionalUpdatesBytes[_key] = _val;
                if (!provisionalUpdateExistsBytes[_key]) {
                    provisionalUpdatesListBytes.push(_key);
                    provisionalUpdateExistsBytes[_key] = true;
                }
            }
            //   Else (not locked) {
            //     Lock the contract.
            //     Indicate in the Cross-Blockchain Control Contract that this call is locking the
            //      Lockable Storage contract
            //     Write to provisional storage
            else {
                lockContract();
                provisionalUpdatesBytes[_key] = _val;
                provisionalUpdatesListBytes.push(_key);
                provisionalUpdateExistsBytes[_key] = true;
            }
        }
    }


    /**
     * Called by the cross-blockchain control contract when the call has been completed.
     *
     * @param _commit True if the provisional updates should be committed. False indicates the
     *         provisional updates should be discarded.
     */
    function finalise(bool _commit) external {
        if (!locked) {
            revert("Nothing to finalise");
        }

        if (_commit) {
            // Copy all provisional updates to data stores.
            for (uint256 i = 0; i < provisionalUpdatesListUint256.length; i++) {
                uint256 key = provisionalUpdatesListUint256[i];
                dataStoreUint256[key] = provisionalUpdatesUint256[key];
            }
            for (uint256 i = 0; i < provisionalUpdatesListBytes.length; i++) {
                uint256 key = provisionalUpdatesListBytes[i];
                dataStoreBytes[key] = provisionalUpdatesBytes[key];
            }
        }

        // Delete all provisional updates.
        for (uint256 i = 0; i < provisionalUpdatesListUint256.length; i++) {
            uint256 key = provisionalUpdatesListUint256[i];
            delete provisionalUpdatesUint256[key];
            delete provisionalUpdateExistsUint256[key];
        }
        for (uint256 i = 0; i < provisionalUpdatesListBytes.length; i++) {
            uint256 key = provisionalUpdatesListBytes[i];
            delete provisionalUpdatesBytes[key];
            delete provisionalUpdateExistsBytes[key];
        }
        delete provisionalUpdatesListUint256;
        delete provisionalUpdatesListBytes;

        locked = false;
    }

    function getUint256(uint256 _key) external view returns(uint256) {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Read from to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            if (locked) {
                revert("Attempted single blockchain call when contract locked");
            }
            return dataStoreUint256[_key];
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Allow the read. If the value isn’t available in provisional storage, return
            //       the value in normal storage.} }
            if (locked) {
                checkLocking();
                if (provisionalUpdateExistsUint256[_key]) {
                    return provisionalUpdatesUint256[_key];
                }
                else {
                    return dataStoreUint256[_key];
                }
            }
            // Else (not locked) {Read from normal storage}
            else {
                return dataStoreUint256[_key];
            }
        }
    }


    function getBytes(uint256 _key) external view returns(bytes memory) {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Read from to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            if (locked) {
                revert("Attempted single blockchain call when contract locked");
            }
            return dataStoreBytes[_key];
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Allow the read. If the value isn’t available in provisional storage, return
            //       the value in normal storage.} }
            if (locked) {
                checkLocking();
                if (provisionalUpdateExistsBytes[_key]) {
                    return provisionalUpdatesBytes[_key];
                }
                else {
                    return dataStoreBytes[_key];
                }
            }
            // Else (not locked) {Read from normal storage}
            else {
                return dataStoreBytes[_key];
            }
        }
    }

    /**
     * Do the type-independent things that need to be done to lock a contract.
     *
     * Lock the contract.
     * Indicate in the Cross-Blockchain Control Contract that this call is locking the
     *   Lockable Storage contract
     */
    function lockContract() private {
        locked = true;
        lockedByRootBlockchainId = crossBlockchainControl.getActiveCallRootBlockchainId();
        lockedByTransactionId = crossBlockchainControl.getActiveCallCrossBlockchainTransactionId();
        crossBlockchainControl.addToListOfLockedContracts(address(this));
    }

    /**
     * Check whether a locked contract is being written to by the same cross-blockchain call.
     *
     * Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
     *   locked the contract?
     * If no {throw an error}
     *
     */
    function checkLocking() private view {
        if (lockedByRootBlockchainId != crossBlockchainControl.getActiveCallRootBlockchainId()) {
            revert("Contract locked by other root blockchain");
        }
        if (lockedByTransactionId != crossBlockchainControl.getActiveCallCrossBlockchainTransactionId()) {
            revert("Contract locked by other cross-blockchain transaction");
        }
    }

}
