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

contract CallPath {
    /**
     * Determine call path of parent. NOTE: Do not call if this current call path represents the root.
     *
     * @param _callPath Currently executing call path
     * @return Call path of parent.
     */
    function determineParentCallPath(uint256[] memory _callPath)
        internal
        pure
        returns (uint256[] memory)
    {
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
        } else {
            callPathOfParent = new uint256[](callPathLen - 1);
            for (uint256 i = 0; i < callPathLen - 2; i++) {
                callPathOfParent[i] = _callPath[i];
            }
            callPathOfParent[callPathLen - 2] = 0;
        }
        return callPathOfParent;
    }
}
