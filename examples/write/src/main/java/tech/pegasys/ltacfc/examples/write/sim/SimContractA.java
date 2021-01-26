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
package tech.pegasys.ltacfc.examples.write.sim;

import tech.pegasys.ltacfc.examples.write.Bc1ContractA;

import java.math.BigInteger;

public class SimContractA {
  private Bc1ContractA bc1ContractA;
  private SimContractB simContractB;

  BigInteger val;

  public SimContractA(Bc1ContractA contract, SimContractB simB) {
    this.bc1ContractA = contract;
    this.simContractB = simB;
  }

  public void doCrosschainWrite(BigInteger val) {
    this.val = val;
    this.simContractB.set(val);

  }

  public String getRlpFunctionSignature_DoCrosschainWrite() {
    return this.bc1ContractA.getRlpFunctionSignature_DoCrosschainWrite(this.val);
  }

}
