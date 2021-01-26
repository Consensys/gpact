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

contract Stock {
    mapping (address => uint256) private stock;

    function setStock(address _account, uint256 _newBalance) external {
        stock[_account] = _newBalance;
    }

    function delivery(address _from, address _to, uint256 _amount) external {
        uint256 fromBalance = getStock(_from);
        uint256 toBalance = getStock(_to);
        require(fromBalance >= _amount, "Stock transfer: insufficient balance");

        stock[_from] = fromBalance - _amount;
        stock[_to] = toBalance + _amount;
    }


    function getStock(address _account) public view returns (uint256) {
        return stock[_account];
    }

}
