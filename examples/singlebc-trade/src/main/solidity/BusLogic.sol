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

import "./Balances.sol";
import "./PriceOracle.sol";
import "./Stock.sol";

contract BusLogic {
    Balances balancesContract;
    PriceOracle priceOracleContract;
    Stock stockContract;

    event StockShipment(address _seller, address _buyer, uint256 _quantity, uint256 _price);

    constructor (address _balances, address _oracle, address _stock) {
        balancesContract = Balances(_balances);
        priceOracleContract = PriceOracle(_oracle);
        stockContract = Stock(_stock);
    }

    function stockShipment(address _seller, address _buyer, uint256 _quantity) public {
        uint256 currentPrice = priceOracleContract.getPrice();

        uint256 cost = currentPrice * _quantity;

        // To address pays for goods.
        balancesContract.transfer(_buyer, _seller, cost);

        // Goods are shipped from From to To.
        stockContract.delivery(_seller, _buyer, _quantity);

        emit StockShipment(_seller, _buyer, _quantity, currentPrice);
    }
}
