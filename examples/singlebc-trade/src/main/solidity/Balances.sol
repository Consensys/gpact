/*
 * Copyright 2020 ConsenSys AG.
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

contract Balances {
    mapping(address => uint256) balances;

    function setBalance(address _account, uint256 _newBalance) external {
        balances[_account] = _newBalance;
    }

    function transfer(address _from, address _to, uint256 _amount) external {
        uint256 fromBalance = getBalance(_from);
        uint256 toBalance = getBalance(_to);
        require(fromBalance >= _amount, "Value transfer: insufficient balance");

        balances[_from] = fromBalance - _amount;
        balances[_to] = toBalance + _amount;
    }


    function getBalance(address _account) public view returns (uint256) {
        return balances[_account];
    }
}
