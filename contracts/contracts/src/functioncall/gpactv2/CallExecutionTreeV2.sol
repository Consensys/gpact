/*
 * Copyright 2022 ConsenSys Software Inc
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

import "../../common/BytesUtil.sol";

/**
 * Call execution tree processing for GPACTv2.
 *
 * See ../../../../../docs/call-tree-encoding.md for details of the call tree encoding formats.
 */
contract CallExecutionTreeV2 is BytesUtil {
    // Call execution tree type related values.
    // Offset of the type field in the call tree.
    uint256 private constant OFFSET_OF_TYPE = 0;
    // Call tree with root and called functions.
    uint256 private constant ENCODING_V2_SINGLE_LAYER = 1;
    // Call tree with root, called functions, and function called by the called
    // functions, to an arbitrary call depth.
    uint256 private constant ENCODING_V2_MULTI_LAYER = 2;

    // Size of the field indicating the number of called functions.
    uint256 private constant NUM_FUNCS_CALLED_SIZE = 1;
    // Size of the offset field.
    uint256 private constant OFFSET_SIZE = 4;

    // For Simple format, offset of the number of functions called.
    uint256 private constant OFFSET_NUM_FUNCS_CALLED = 1;
    // For Simple format, offset of the start of the functions called array.
    uint256 private constant OFFSET_FUNCS_CALLED_ARRAY = 2;
    // Size of each hash in teh functions called array.
    uint256 private constant HASH_SIZE = 32;

    /**
     * Based on a call path, extract the function call hash of a function to be executed.
     *
     * @param _callExecutionTree Data blob representing a call tree.
     * @param _callPath Path of the function hash to be extracted.
     * @return functionCallHash Function call hash of the function represented by the _callPath.
     */
    function extractTargetHashFromCallGraph(
        bytes calldata _callExecutionTree,
        uint256[] memory _callPath
    ) internal pure returns (bytes32 functionCallHash) {
        uint8 callTreeType = BytesUtil.bytesToUint8(
            _callExecutionTree,
            OFFSET_OF_TYPE
        );
        if (callTreeType == ENCODING_V2_SINGLE_LAYER) {
            return
                extractTargetFromCallGraphSingleLayer(
                    _callExecutionTree,
                    _callPath
                );
        }
        if (callTreeType == ENCODING_V2_MULTI_LAYER) {
            return
                extractTargetFromCallGraphMultiLayer(
                    _callExecutionTree,
                    _callPath
                );
        }
        revert("Unknown call tree type");
    }

    /**
     * Extract the function call hash assuming a call tree with root and called functions only,
     * in the Simple Encoding format.
     *
     * @param _callExecutionTree Data blob representing a call tree.
     * @param _callPath Path of the function hash to be extracted.
     * @return functionCallHash Function call hash of the function represented by the _callPath.
     */
    function extractTargetFromCallGraphSingleLayer(
        bytes calldata _callExecutionTree,
        uint256[] memory _callPath
    ) internal pure returns (bytes32 functionCallHash) {
        require(
            _callPath.length == 1,
            "Call Path Error: Multiple layers in a single layer call tree"
        );
        uint256 calledFunc = _callPath[0];

        uint8 numFuncsCalled = BytesUtil.bytesToUint8(
            _callExecutionTree,
            OFFSET_NUM_FUNCS_CALLED
        );
        require(
            calledFunc <= numFuncsCalled,
            "Call Path Error: calling beyond end of call tree"
        );
        uint256 index = OFFSET_FUNCS_CALLED_ARRAY + calledFunc * HASH_SIZE;
        functionCallHash = BytesUtil.bytesToBytes32(_callExecutionTree, index);
    }

    /**
     * Extract the function call hash assuming a call tree with root and called functions only,
     * in the Arbitrary Call Tree Encoding format.
     *
     * @param _callExecutionTree Data blob representing a call tree.
     * @param _callPath Path of the function hash to be extracted.
     * @return functionCallHash Function call hash of the function represented by the _callPath.
     */
    function extractTargetFromCallGraphMultiLayer(
        bytes calldata _callExecutionTree,
        uint256[] memory _callPath
    ) internal pure returns (bytes32 functionCallHash) {
        uint256 index = 1;

        // Go down the call path to the target function
        for (uint256 i = 0; i < _callPath.length; i++) {
            uint256 offset = 0;
            uint8 numFuncsCalled = BytesUtil.bytesToUint8(
                _callExecutionTree,
                index
            );
            if (numFuncsCalled == 0) {
                require(
                    i == _callPath.length - 1,
                    "Reached leaf function but there is more call path."
                );
            } else {
                // Jump to the location of the offset of the function
                uint256 functionCalled = _callPath[i];
                offset = BytesUtil.bytesToUint32(
                    _callExecutionTree,
                    index + functionCalled * OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE
                );
            }
            // Jump to the function
            index = index + offset;
        }

        // Jump over the leaf function indicator / numFuncsCalled = 0 indicator
        if (_callPath[_callPath.length - 1] != 0) {
            uint8 numFuncsCalled = BytesUtil.bytesToUint8(
                _callExecutionTree,
                index
            );
            if (numFuncsCalled != 0) {
                // The call path should have included an extra 0 at the end,
                // for instance [2] rather than [2][0].
                // This can happen for multi-level call trees, where the call path is only a part of the
                // call path. In determineFuncsToBeCalled, it will pass in, for instance
                // [1] rather than [1][0], when fetching the functions that the root function calls.

                // Jump to the location of the offset of the function
                // functionCalled would be 0
                uint256 offset = BytesUtil.bytesToUint32(
                    _callExecutionTree,
                    index + NUM_FUNCS_CALLED_SIZE
                );
                // Jump to the function
                index = index + offset;
            } else {
                index += NUM_FUNCS_CALLED_SIZE;
            }
        }

        functionCallHash = BytesUtil.bytesToBytes32(_callExecutionTree, index);
    }
}
