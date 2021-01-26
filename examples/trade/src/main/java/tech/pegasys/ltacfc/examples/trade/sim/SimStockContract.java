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

import tech.pegasys.ltacfc.examples.trade.Bc5Stock;

import java.math.BigInteger;

public class SimStockContract {
  private String transfer_from;
  private String transfer_to;
  private BigInteger transfer_quantity;
  private Bc5Stock bc5Stock;

  public SimStockContract(Bc5Stock contract) {
    this.bc5Stock = contract;
  }


  public void transfer(String from, String to, BigInteger quantity) throws Exception {
    this.transfer_from = from;
    this.transfer_to = to;
    this.transfer_quantity = quantity;

    // Do the following check to see if the execution is likely to fail.
    BigInteger fromBalance = getStock(from);
    if (fromBalance.longValue() < quantity.longValue()) {
      throw new Exception("Stock transfer: insufficient balance. From stock: " + fromBalance + ", Transfer quantity: " + quantity);
    }
  }

  public BigInteger getStock(String account) throws Exception {
    return this.bc5Stock.getStock(account);
  }

  public String getRlpFunctionSignature_delivery() {
    return this.bc5Stock.getRlpFunctionSignature_Delivery(this.transfer_from, this.transfer_to, this.transfer_quantity);
  }

}
