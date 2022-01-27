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

import "./NonAtomicHiddenAuthParameters.sol";
import "./AtomicHiddenAuthParameters.sol";

contract HiddenParamDestTest is
    NonAtomicHiddenAuthParameters,
    AtomicHiddenAuthParameters
{
    uint256 private expectedUint256_1;
    uint256 private expectedUint256_2;
    address private expectedAddress;

    event AllGood(bool happy);

    constructor(
        uint256 _expectedUint256_1,
        uint256 _expectedUint256_2,
        address _expectedAddress
    ) {
        expectedUint256_1 = _expectedUint256_1;
        expectedUint256_2 = _expectedUint256_2;
        expectedAddress = _expectedAddress;
    }

    function funcNoParams() external {
        (
            uint256 actualUint256_1,
            uint256 actualUint256_2,
            address actualAddress
        ) = decodeAtomicAuthParams();
        check3(actualUint256_1, actualUint256_2, actualAddress);
        emit AllGood(true);
    }

    function funcOneParam(uint256 _val) external {
        (
            uint256 actualUint256_1,
            uint256 actualUint256_2,
            address actualAddress
        ) = decodeAtomicAuthParams();
        check3(actualUint256_1, actualUint256_2, actualAddress);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function funcTwoParams(uint256 _val1, uint256 _val2) external {
        (
            uint256 actualUint256_1,
            uint256 actualUint256_2,
            address actualAddress
        ) = decodeAtomicAuthParams();
        check3(actualUint256_1, actualUint256_2, actualAddress);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    function twoParamFuncNoParams() external {
        (
            uint256 actualUint256,
            address actualAddress
        ) = decodeNonAtomicAuthParams();
        check2(actualUint256, actualAddress);
        emit AllGood(true);
    }

    function twoParamFuncOneParam(uint256 _val) external {
        (
            uint256 actualUint256,
            address actualAddress
        ) = decodeNonAtomicAuthParams();
        check2(actualUint256, actualAddress);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function twoParamFuncTwoParams(uint256 _val1, uint256 _val2) external {
        (
            uint256 actualUint256,
            address actualAddress
        ) = decodeNonAtomicAuthParams();
        check2(actualUint256, actualAddress);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    // Same functions, but called explicitly
    function funcNoParamsExplicit(
        uint256 _actualUint256_1,
        uint256 _actualUint256_2,
        address _actualAddress
    ) external {
        check3(_actualUint256_1, _actualUint256_2, _actualAddress);
        emit AllGood(true);
    }

    function funcOneParamExplicit(
        uint256 _val,
        uint256 _actualUint256_1,
        uint256 actualUint256_2,
        address actualAddress
    ) external {
        check3(_actualUint256_1, actualUint256_2, actualAddress);
        require(_val == 17, "Error: Val");
        emit AllGood(true);
    }

    function funcTwoParamsExplicit(
        uint256 _val1,
        uint256 _val2,
        uint256 _actualUint256_1,
        uint256 _actualUint256_2,
        address _actualAddress
    ) external {
        check3(_actualUint256_1, _actualUint256_2, _actualAddress);
        require(_val1 == 17, "Error: Val1");
        require(_val2 == 23, "Error: Val2");
        emit AllGood(true);
    }

    function check2(uint256 _actualUint256, address _actualAddress)
        private
        view
    {
        require(_actualUint256 == expectedUint256_1, "First param not correct");
        require(
            _actualAddress == expectedAddress,
            "Second param (the address) not correct"
        );
    }

    function check3(
        uint256 _actualUint256_1,
        uint256 _actualUint256_2,
        address _actualAddress
    ) private view {
        require(
            _actualUint256_1 == expectedUint256_1,
            "First param not correct"
        );
        require(
            _actualUint256_2 == expectedUint256_2,
            "Second param not correct"
        );
        require(
            _actualAddress == expectedAddress,
            "Third param (the address) not correct"
        );
    }
}
