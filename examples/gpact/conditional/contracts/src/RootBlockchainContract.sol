/*
 * Copyright 2020 ConsenSys Software Inc
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

import "./OtherBlockchainContractInterface.sol";
import "../../../../../contracts/contracts/src/application/lockablestorage/LockableStorage.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainFunctionCallInterface.sol";
import "../../../../../contracts/contracts/src/functioncall/interface/CrosschainFunctionCallReturnInterface.sol";

contract RootBlockchainContract is LockableStorage {
    uint256 private otherBlockchainId;
    OtherBlockchainContractInterface private otherContract;

    uint256 private constant KEY_UINT256_VAL1 = 0;
    uint256 private constant KEY_UINT256_VAL2 = 1;

    constructor(
        address _crossBlockchainControl,
        uint256 _otherBlockchainId,
        address _otherContract
    ) LockableStorage(_crossBlockchainControl) {
        otherBlockchainId = _otherBlockchainId;
        otherContract = OtherBlockchainContractInterface(_otherContract);
    }

    function someComplexBusinessLogic(uint256 _val) external {
        // Use the value on the other blockchain as a threshold
        uint256 valueFromOtherBlockchain = CrosschainFunctionCallReturnInterface(
                address(cbc)
            ).crossBlockchainCallReturnsUint256(
                    otherBlockchainId,
                    address(otherContract),
                    abi.encodeWithSelector(otherContract.getVal.selector)
                );
        setVal2(valueFromOtherBlockchain);

        if (_val > valueFromOtherBlockchain) {
            CrosschainFunctionCallInterface(address(cbc)).crossBlockchainCall(
                otherBlockchainId,
                address(otherContract),
                abi.encodeWithSelector(
                    otherContract.setValues.selector,
                    _val,
                    valueFromOtherBlockchain
                )
            );
            setVal1(valueFromOtherBlockchain);
        } else {
            uint256 valueToSet = _val + 13;
            CrosschainFunctionCallInterface(address(cbc)).crossBlockchainCall(
                otherBlockchainId,
                address(otherContract),
                abi.encodeWithSelector(
                    otherContract.setVal.selector,
                    valueToSet
                )
            );
            setVal1(_val);
        }
    }

    function setVal1(uint256 _val) public {
        setUint256(KEY_UINT256_VAL1, _val);
    }

    function setVal2(uint256 _val) public {
        setUint256(KEY_UINT256_VAL2, _val);
    }

    function getVal1() external view returns (uint256) {
        return getUint256(KEY_UINT256_VAL1);
    }

    function getVal2() external view returns (uint256) {
        return getUint256(KEY_UINT256_VAL2);
    }
}
