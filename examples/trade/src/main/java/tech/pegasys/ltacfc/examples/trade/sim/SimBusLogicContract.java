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
package tech.pegasys.ltacfc.examples.trade.sim;

import tech.pegasys.ltacfc.examples.trade.Bc2BusLogic;

import java.math.BigInteger;

public class SimBusLogicContract {
  private String stockShipment_seller;
  private String stockShipment_buyer;
  private BigInteger stockShipment_quantity;
  private Bc2BusLogic bc2BusLogic;

  SimPriceOracleContract priceOracleContract;
  SimBalancesContract balancesContract;
  SimStockContract stockContract;

  public SimBusLogicContract(Bc2BusLogic contract, SimPriceOracleContract oracleContract, SimBalancesContract balancesContract, SimStockContract stockContract) {
    this.bc2BusLogic = contract;
    this.priceOracleContract = oracleContract;
    this.balancesContract = balancesContract;
    this.stockContract = stockContract;
  }

  public void stockShipment(String seller, String buyer, BigInteger quantity) throws Exception {
    this.stockShipment_seller = seller;
    this.stockShipment_buyer = buyer;
    this.stockShipment_quantity = quantity;


    BigInteger currentPrice = this.priceOracleContract.getPrice();
    BigInteger cost = currentPrice.multiply(quantity);

    // To address pays for goods.
    this.balancesContract.transfer(buyer, seller, cost);

    // Goods are shipped from From to To.
    this.stockContract.transfer(seller, buyer, quantity);
  }

  public String getRlpFunctionSignature_stockShipment() {
    return this.bc2BusLogic.getRlpFunctionSignature_StockShipment(this.stockShipment_seller, this.stockShipment_buyer, this.stockShipment_quantity);
  }
}
