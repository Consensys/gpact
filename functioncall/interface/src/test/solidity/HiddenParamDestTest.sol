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

import "../../main/solidity/HiddenParameters.sol";

contract HiddenParamDestTest is HiddenParameters {
    uint256 private e1;
    uint256 private e2;
    uint256 private e3;

    event AllGood(bool happy);

    constructor(
        uint256 _expected1,
        uint256 _expected2,
        uint256 _expected3
    ) {
        e1 = _expected1;
        e2 = _expected2;
        e3 = _expected3;
    }

    function funcNoParams() external {
        (uint256 a1, uint256 a2, uint256 a3) = extractThreeHiddenParams();
        check3(a1, a2, a3);
        emit AllGood(true);
    }

    function funcOneParam(uint256 _val) external {
        (uint256 a1, uint256 a2, uint256 a3) = extractThreeHiddenParams();
        check3(a1, a2, a3);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function funcTwoParams(uint256 _val1, uint256 _val2) external {
        (uint256 a1, uint256 a2, uint256 a3) = extractThreeHiddenParams();
        check3(a1, a2, a3);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    function twoParamFuncNoParams() external {
        (uint256 a1, uint256 a2) = extractTwoHiddenParams();
        check2(a1, a2);
        emit AllGood(true);
    }

    function twoParamFuncOneParam(uint256 _val) external {
        (uint256 a1, uint256 a2) = extractTwoHiddenParams();
        check2(a1, a2);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function twoParamFuncTwoParams(uint256 _val1, uint256 _val2) external {
        (uint256 a1, uint256 a2) = extractTwoHiddenParams();
        check2(a1, a2);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    // Same functions, but called explicitly
    function funcNoParamsExplicit(
        uint256 a1,
        uint256 a2,
        uint256 a3
    ) external {
        check3(a1, a2, a3);
        emit AllGood(true);
    }

    function funcOneParamExplicit(
        uint256 _val,
        uint256 a1,
        uint256 a2,
        uint256 a3
    ) external {
        check3(a1, a2, a3);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function funcTwoParamsExplicit(
        uint256 _val1,
        uint256 _val2,
        uint256 a1,
        uint256 a2,
        uint256 a3
    ) external {
        check3(a1, a2, a3);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    function check2(uint256 _a1, uint256 _a2) private view {
        require(_a1 == e1, "First param not correct");
        require(_a2 == e2, "Second param not correct");
    }

    function check3(
        uint256 _a1,
        uint256 _a2,
        uint256 _a3
    ) private view {
        require(_a1 == e1, "First param not correct");
        require(_a2 == e2, "Second param not correct");
        require(_a3 == e3, "Third param not correct");
    }
}
