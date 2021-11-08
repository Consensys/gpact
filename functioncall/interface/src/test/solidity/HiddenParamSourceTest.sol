/*
 * Copyright 2021 ConsenSys Software.
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

import "./HiddenParamDestTest.sol";

contract HiddenParamSourceTest is HiddenParameters {
    HiddenParamDestTest dest;

    uint256 private e1;
    uint256 private e2;
    uint256 private e3;

    event Dump(bytes _b);

    constructor(
        address _dest,
        uint256 _expected1,
        uint256 _expected2,
        uint256 _expected3
    ) {
        dest = HiddenParamDestTest(_dest);
        e1 = _expected1;
        e2 = _expected2;
        e3 = _expected3;
    }

    // These functions call with explicit parameters
    function callFuncNoParamsExplicit() external {
        dest.funcNoParamsExplicit(e1, e2, e3);
    }

    function callFuncOneParamExplicit() external {
        dest.funcOneParamExplicit(17, e1, e2, e3);
    }

    function callFuncTwoParamsExplicit() external {
        dest.funcTwoParamsExplicit(17, 23, e1, e2, e3);
    }

    // These functions call with hidden parameters
    function callFuncNoParams() external {
        doCallThreeParams(abi.encodeWithSelector(dest.funcNoParams.selector));
    }

    function callFuncOneParam() external {
        doCallThreeParams(
            abi.encodeWithSelector(dest.funcOneParam.selector, 17)
        );
    }

    function callFuncTwoParams() external {
        doCallThreeParams(
            abi.encodeWithSelector(dest.funcTwoParams.selector, 17, 23)
        );
    }

    function doCallThreeParams(bytes memory functionCall) private {
        bytes memory functionCallWithAuth = encodeThreeHiddenParams(
            functionCall,
            e1,
            e2,
            e3
        );

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = address(dest).call(
            functionCallWithAuth
        );

        if (!isSuccess) {
            revert(getRevertMsg(returnValueEncoded));
        }
    }

    function twoParamCallFuncNoParams() external {
        doCallTwoParams(
            abi.encodeWithSelector(dest.twoParamFuncNoParams.selector)
        );
    }

    function twoParamCallFuncOneParam() external {
        doCallTwoParams(
            abi.encodeWithSelector(dest.twoParamFuncOneParam.selector, 17)
        );
    }

    function twoParamCallFuncTwoParams() external {
        doCallTwoParams(
            abi.encodeWithSelector(dest.twoParamFuncTwoParams.selector, 17, 23)
        );
    }

    function doCallTwoParams(bytes memory functionCall) private {
        bytes memory functionCallWithAuth = encodeTwoHiddenParams(
            functionCall,
            e1,
            e2
        );

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = address(dest).call(
            functionCallWithAuth
        );

        if (!isSuccess) {
            revert(getRevertMsg(returnValueEncoded));
        }
    }

    function getRevertMsg(bytes memory _returnData)
        internal
        pure
        returns (string memory)
    {
        // If the _res length is less than 68, then the transaction failed silently (without a revert message)
        if (_returnData.length < 68) return "Transaction reverted silently";

        assembly {
            // Slice the sighash.
            _returnData := add(_returnData, 0x04)
        }
        return abi.decode(_returnData, (string)); // All that remains is the revert string
    }
}
