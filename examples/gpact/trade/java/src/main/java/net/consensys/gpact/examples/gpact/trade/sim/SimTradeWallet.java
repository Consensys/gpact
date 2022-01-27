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
package net.consensys.gpact.examples.gpact.trade.sim;

import java.math.BigInteger;
import net.consensys.gpact.examples.gpact.trade.Bc1TradeWallet;

// Note: only need to simulate enough to know what the parameters for function calls should be.
public class SimTradeWallet {
  private String executeTrade_seller;
  private BigInteger executeTrade_quantity;
  private Bc1TradeWallet rootContract;

  SimBusLogicContract simBusLogicContract;

  public SimTradeWallet(Bc1TradeWallet contract, SimBusLogicContract businessLogicContract) {
    this.rootContract = contract;
    this.simBusLogicContract = businessLogicContract;
  }

  public void executeTrade(String msgSender, String seller, BigInteger quantity) throws Exception {
    this.executeTrade_seller = seller;
    this.executeTrade_quantity = quantity;

    this.simBusLogicContract.stockShipment(seller, msgSender, quantity);
  }

  public String getRlpFunctionSignature_executeTrade() {
    return this.rootContract.getRlpFunctionSignature_ExecuteTrade(
        this.executeTrade_seller, this.executeTrade_quantity);
  }
}
