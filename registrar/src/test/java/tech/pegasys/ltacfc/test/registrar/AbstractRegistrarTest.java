/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.test.registrar;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.registrar.SigAlgorithmTypes;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;

import java.math.BigInteger;

public abstract class AbstractRegistrarTest extends AbstractWeb3Test {
  protected Registrar registrarContract;

  protected void deployRegistrarContract() throws Exception {
    this.registrarContract = Registrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
  }

  protected Registrar deployRegistrarContract(TransactionManager tm1) throws Exception {
    return Registrar.deploy(this.web3j, tm1, this.freeGasProvider).send();
  }

  protected Registrar loadContract(TransactionManager tm1) throws Exception {
    return Registrar.load(this.registrarContract.getContractAddress(), this.web3j, tm1, this.freeGasProvider);
  }

  protected void addBlockchain(BigInteger blockchainId) throws Exception {
    BigInteger sigAlgorithm = SigAlgorithmTypes.ALG_ECDSA_KECCAK256_SECP256K1.asBigInt();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_BLOCKCHAIN.asBigInt(), blockchainId, BigInteger.ZERO, sigAlgorithm).send();
    assert(receipt.isStatusOK());
  }
}
