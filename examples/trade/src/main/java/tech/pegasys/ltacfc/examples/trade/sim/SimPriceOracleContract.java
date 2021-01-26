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

import tech.pegasys.ltacfc.examples.trade.Bc4Oracle;

import java.math.BigInteger;

public class SimPriceOracleContract {
  private Bc4Oracle bc4Oracle;

  public SimPriceOracleContract(Bc4Oracle contract) {
    this.bc4Oracle = contract;
  }

  public BigInteger getPrice() throws Exception {
    // Read the current value from the blockchain.
    return this.bc4Oracle.getPrice();
  }

  public String getRlpFunctionSignature_getPrice() {
    return this.bc4Oracle.getRlpFunctionSignature_GetPrice();
  }
}
