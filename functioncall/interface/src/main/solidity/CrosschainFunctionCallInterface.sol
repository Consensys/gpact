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
 * Crosschain Function Call Interface allows applications to call functions on other blockchains
 * and to get information about the currently executing function call.
 *
 */
interface CrosschainFunctionCallInterface {

    /**
     * Call a function on another blockchain. All function call implementations must implement
     * this function.
     *
     * @param _bcId Blockchain identifier of blockchain to be called.
     * @param _contract The address of the contract to be called.
     * @param _functionCallData The function selector and parameter data encoded using ABI encoding rules.
     */
    function crossBlockchainCall(uint256 _bcId, address _contract, bytes calldata _functionCallData) external;


    /**
     * Indicate if the executing code is being called as part of a crosschain call or
     * not. This could be used to limit access to non-crosschain compatible parts
     * of a contract.
     *
     * @return false if the executing code is part of a crosschain call.
     */
    function isSingleBlockchainCall() external view returns (bool);

    /**
     * Provide information that can be used as part of a crosschain authentication
     * system. That is, application code may have a set of Root Blockchains,
     * Parent Blockchains, and callign contracts that they are prepared to work with.
     *
     * @return rootBcId The blockchain identifier for the root blockchain. This is
     *   the blockchain that had the entry point function for the entire call execution
     *   tree.
     * @return parentBcId The blockchain identifier for the blockchain that contains
     *   the contract that contains the function that called this blockchain.
     * @return parentContract The address of the contract that contained the entry point
     *   function on the parent blockchain. Note that this contract may have in turn
     *   called functions in other contracts prior to executing the crosschain call
     *   segment that resulted in the currently executing code executing.
     */
    function whoCalledMe() external view returns (uint256 rootBcId, uint256 parentBcId, address parentContract);
}