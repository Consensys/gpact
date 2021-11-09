/*
 * Copyright 2021 ConsenSys AG.
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

abstract contract HiddenParameters {
    /**
     * Add three parameters to the end of an existing function call.
     *
     * @param _functionCall Function selector and an arbitrary list of parameters.
     * @param _param1 A parameter to be appended to the function call.
     * @param _param2 A parameter to be appended to the function call.
     * @param _param3 A parameter to be appended to the function call.
     */
    function encodeThreeHiddenParams(
        bytes memory _functionCall,
        uint256 _param1,
        uint256 _param2,
        uint256 _param3
    ) internal pure returns (bytes memory) {
        return
            bytes.concat(
                _functionCall,
                abi.encodePacked(_param1, _param2, _param3)
            );
    }

    /**
     * Extract three values from the end of the call data. The parameters are expected to have been
     * added to the end of the function call using encodeThreeHiddenParams.
     *
     * @return _param1 Hidden parameter that was passed in by appending values to call data.
     * @return _param2 Hidden parameter that was passed in by appending values to call data.
     * @return _param3 Hidden parameter that was passed in by appending values to call data.
     *
     */
    function extractThreeHiddenParams()
        internal
        pure
        returns (
            uint256 _param1,
            uint256 _param2,
            uint256 _param3
        )
    {
        bytes calldata allParams = msg.data;
        uint256 len = allParams.length;

        assembly {
            calldatacopy(0x0, sub(len, 96), 32)
            _param1 := mload(0)
            calldatacopy(0x0, sub(len, 64), 32)
            _param2 := mload(0)
            calldatacopy(0x0, sub(len, 32), 32)
            _param3 := mload(0)
        }
    }

    /**
     * Add two parameters to the end of an existing function call.
     *
     * @param _functionCall Function selector and an arbitrary list of parameters.
     * @param _param1 A parameter to be appended to the function call.
     * @param _param2 A parameter to be appended to the function call.
     */
    function encodeTwoHiddenParams(
        bytes memory _functionCall,
        uint256 _param1,
        uint256 _param2
    ) internal pure returns (bytes memory) {
        return bytes.concat(_functionCall, abi.encodePacked(_param1, _param2));
    }

    /**
     * Extract two values from the end of the call data. The parameters are expected to have been
     * added to the end of the function call using encodeTwoHiddenParams.
     *
     * @return _param1 Hidden parameter that was passed in by appending values to call data.
     * @return _param2 Hidden parameter that was passed in by appending values to call data.
     */
    function extractTwoHiddenParams()
        internal
        pure
        returns (uint256 _param1, uint256 _param2)
    {
        bytes calldata allParams = msg.data;
        uint256 len = allParams.length;

        assembly {
            calldatacopy(0x0, sub(len, 64), 32)
            _param1 := mload(0)
            calldatacopy(0x0, sub(len, 32), 32)
            _param2 := mload(0)
        }
    }
}
