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

import "./BusLogic.sol";

contract TradeWallet {
    BusLogic busLogicContract;

    uint256[] private trades;

    event Trade(bytes32 _tradeId);

    constructor (address _busLogicContract) {
        busLogicContract = BusLogic(_busLogicContract);
    }

    function executeTrade(address _seller, uint256 _quantity) public {
        busLogicContract.stockShipment(_seller, tx.origin, _quantity);

        bytes32 tradeId = keccak256(abi.encodePacked(_seller, tx.origin, _quantity));

        trades.push() = uint256(tradeId);

        emit Trade(tradeId);
    }

    function getNumTrades() external view returns (uint256) {
        return trades.length;
    }

    function getTrade(uint256 _index) external view returns (uint256) {
        return trades[_index];
    }
}
