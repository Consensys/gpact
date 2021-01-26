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
    uint256 balancesBcId;
    Balances balancesContract;
    uint256 priceBcId;
    PriceOracle priceOracleContract;
    uint256 stockBcId;
    Stock stockContract;
    CbcLockableStorageInterface crossBlockchainControl;

    event StockShipment(address _seller, address _buyer, uint256 _quantity, uint256 _price);

    constructor (address _cbc, uint256 _balancesBcId, address _balances, uint256 _oracleBcId, address _oracle, uint256 _stockBcId, address _stock) {
        crossBlockchainControl = CbcLockableStorageInterface(_cbc);
        balancesBcId = _balancesBcId;
        balancesContract = Balances(_balances);
        priceBcId = _oracleBcId;
        priceOracleContract = PriceOracle(_oracle);
        stockBcId = _stockBcId;
        stockContract = Stock(_stock);
    }

    function stockShipment(address _seller, address _buyer, uint256 _quantity) public {
        uint256 currentPrice = crossBlockchainControl.crossBlockchainCallReturnsUint256(
            priceBcId, address(priceOracleContract), abi.encodeWithSelector(priceOracleContract.getPrice.selector));

        uint256 cost = currentPrice * _quantity;

        // To address pays for goods.
        crossBlockchainControl.crossBlockchainCall(balancesBcId, address(balancesContract),
              abi.encodeWithSelector(balancesContract.transfer.selector, _buyer, _seller, cost));

        // Goods are shipped from From to To.
        crossBlockchainControl.crossBlockchainCall(stockBcId, address(stockContract),
            abi.encodeWithSelector(stockContract.delivery.selector, _seller, _buyer, _quantity));

        emit StockShipment(_seller, _buyer, _quantity, currentPrice);
    }
}
