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

import "../../../../../common/common/src/main/solidity/BytesUtil.sol";


contract CallPathCallExecutionTree is BytesUtil {

    uint256 constant private NUM_FUNCS_CALLED_SIZE = 1;
    uint256 constant private OFFSET_SIZE = 4;
    uint256 constant private BLOCKCHAIN_ID_SIZE = 32;
    uint256 constant private ADDRESS_SIZE = 20;
    uint256 constant private DATA_LEN_SIZE_SIZE = 2;

    function extractTargetFromCallGraph(bytes memory _callGraph, uint256[] memory _callPath, bool getFunction) internal pure
        returns (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) {

        uint256 index = 0;

        // Go down the call path to the target function
        for (uint256 i = 0; i < _callPath.length; i++) {
            uint256 offset = 0;
            uint8 numFuncsCalled = BytesUtil.bytesToUint8(_callGraph, index);
            if (numFuncsCalled == 0) {
                require(i == _callPath.length - 1, "Reached leaf function but there is more call path.");
            } else {
                // Jump to the location of the offset of the function
                uint256 functionCalled = _callPath[i];
                offset = BytesUtil.bytesToUint32(_callGraph,
                    index + functionCalled * OFFSET_SIZE + NUM_FUNCS_CALLED_SIZE);
            }
            // Jump to the function
            index = index + offset;
        }

        // Jump over the leaf function indicator / numFuncsCalled = 0 indicator
        if (_callPath[_callPath.length-1] != 0) {
            uint8 numFuncsCalled = BytesUtil.bytesToUint8(_callGraph, index);
            require(numFuncsCalled == 0, "Expected leaf function.");
            index += NUM_FUNCS_CALLED_SIZE;
        }

        targetBlockchainId = BytesUtil.bytesToUint256(_callGraph, index);
        index += BLOCKCHAIN_ID_SIZE;
        targetContract = BytesUtil.bytesToAddress2(_callGraph, index);
        if (getFunction) {
            index += ADDRESS_SIZE;
            uint16 functionDataLen = BytesUtil.bytesToUint16(_callGraph, index);
            index += DATA_LEN_SIZE_SIZE;
            functionCall = BytesUtil.sliceAsm(_callGraph, index, functionDataLen);
        }
        else {
            functionCall = "";
        }
    }

    /**
     * Determine call path of parent. NOTE: Do not call if this current call path represents the root.
     *
     * @param _callPath Currently executing call path
     * @return Call path of parent.
     */
    function determineParentCallPath(uint256[] memory _callPath) internal pure returns (uint256[] memory) {
        uint256[] memory callPathOfParent;
        uint256 callPathLen = _callPath.length;

        // Don't call from root function
        //assert(!(callPathLen == 1 && _callPath[0] == 0));

        if (_callPath[callPathLen - 1] != 0) {
            callPathOfParent = new uint256[](callPathLen);
            for (uint256 i = 0; i < callPathLen - 1; i++) {
                callPathOfParent[i] = _callPath[i];
            }
            callPathOfParent[callPathLen - 1] = 0;
        }
        else {
            callPathOfParent = new uint256[](callPathLen - 1);
            for (uint256 i = 0; i < callPathLen - 2; i++) {
                callPathOfParent[i] = _callPath[i];
            }
            callPathOfParent[callPathLen - 2] = 0;
        }
        return callPathOfParent;
    }
}