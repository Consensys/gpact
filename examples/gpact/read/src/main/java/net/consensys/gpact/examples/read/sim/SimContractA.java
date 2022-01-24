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
package net.consensys.gpact.examples.read.sim;

import net.consensys.gpact.examples.read.Bc1ContractA;

public class SimContractA {
  private Bc1ContractA bc1ContractA;

  public SimContractA(Bc1ContractA contract) {
    this.bc1ContractA = contract;
  }

  public String getRlpFunctionSignature_DoCrosschainRead() {
    return this.bc1ContractA.getRlpFunctionSignature_DoCrosschainRead();
  }
}
