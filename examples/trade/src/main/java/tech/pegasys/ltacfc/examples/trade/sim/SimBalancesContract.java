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

import tech.pegasys.ltacfc.examples.trade.Bc3Balances;

import java.math.BigInteger;

public class SimBalancesContract {
  private String transfer_from;
  private String transfer_to;
  private BigInteger transfer_amount;
  private Bc3Balances bc3Balances;

  public SimBalancesContract(Bc3Balances contract) {
    this.bc3Balances = contract;
  }


  public void transfer(String from, String to, BigInteger amount) throws Exception {
    this.transfer_from = from;
    this.transfer_to = to;
    this.transfer_amount = amount;

    // Check that the execution isn't going to fail due to insufficient balance.
    BigInteger fromBalance = getBalance(from);
    if (fromBalance.longValue() < amount.longValue()) {
      throw new Exception("Value transfer: insufficient balance. From balance: " + fromBalance + " Transfer Amount: " + amount);
    }
  }

  public BigInteger getBalance(String account) throws Exception {
    return this.bc3Balances.getBalance(account);
  }

  public String getRlpFunctionSignature_transfer() {
    return this.bc3Balances.getRlpFunctionSignature_Transfer(this.transfer_from, this.transfer_to, this.transfer_amount);
  }

}
