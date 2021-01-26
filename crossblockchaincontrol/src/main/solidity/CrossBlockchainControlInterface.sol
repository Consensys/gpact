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
 * Interface to be used for Externally Owned Accounts to execute segments of cross-blockchain
 * transactions.
 *
 */
interface CrossBlockchainControlInterface {


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external;

    function segment(
        uint256 _rootBlockchainId, address _rootCBCContract,
        bytes32 _startEventTxReceiptRoot, bytes calldata _startTxReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof, uint256[] calldata _callPath) external;


    /**
     * This function is needed to load up event proof information for transaction receipt root transfer method.
     * The reason this is needed is bbecause struct parameters containing arrays of bytes, or even arrays of
     * arrays does yet not work (at of 0.7.4). To get around this, information is loaded across several
     * calls into temporary storage.
     */
    function callPrep(uint256 _blockchainId, address _cbcContract, bytes32 _txReceiptRoot,
        bytes calldata _txReceipt, uint256[] calldata _proofOffsets, bytes[] calldata _proof) external;

    function rootTxRootTransfer() external;

    function signalling() external;

    // Accessor functions for public variables.
    function myBlockchainId() external view returns(uint256);
    function transactionInformation(uint256) external view returns(uint256);
}