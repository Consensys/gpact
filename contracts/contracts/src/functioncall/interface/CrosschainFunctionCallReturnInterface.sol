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

import "./CrosschainFunctionCallInterface.sol";

/**
 * Crosschain Function Call Interface allows applications to call functions on other blockchains.
 * The calls defined in this file return values.
 *
 */
interface CrosschainFunctionCallReturnInterface is
    CrosschainFunctionCallInterface
{
    /**
     * Call a function on another blockchain that returns one or more values. Function call implementations
     * may implement this function. Implementations that do not support this functionality should revert
     * with the message, "NOT SUPPORTED: crossBlockchainCallWithReturn".
     *
     * Return values are ABI encoded. For instance, if the function returns the values (uint8, bytes32, address),
     * then the calling code should process the bytes output of this function using the following code:
     *
     * (uint8 val1, bytes32 val2, address val3) = abi.decode(returnValue, (uint8, bytes32, address));
     *

     *
     * @param _bcId Blockchain identifier of blockchain to be called.
     * @param _contract The address of the contract to be called.
     * @param _functionCallData The function selector and parameter data encoded using ABI encoding rules.
     * @return bool: True if the function executed without reverting.
     * @return bytes: ABI encoded return result.
     */
    function crossBlockchainCallWithReturn(
        uint256 _bcId,
        address _contract,
        bytes calldata _functionCallData
    ) external returns (bool, bytes memory);

    /**
     * Call a function on another blockchain that returns a uint256 value. Function call implementations
     * may implement this function. Implementations that do not support this functionality should revert
     * with the message, "NOT SUPPORTED: crossBlockchainCallReturnsUint256".
     *
     * @param _bcId Blockchain identifier of blockchain to be called.
     * @param _contract The address of the contract to be called.
     * @param _functionCallData The function selector and parameter data encoded using ABI encoding rules.
     * @return UINT256 result of function call.
     */
    function crossBlockchainCallReturnsUint256(
        uint256 _bcId,
        address _contract,
        bytes calldata _functionCallData
    ) external returns (uint256);
}
