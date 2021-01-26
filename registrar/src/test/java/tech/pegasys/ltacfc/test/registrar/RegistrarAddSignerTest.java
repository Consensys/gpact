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

import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.common.AnIdentity;

import java.math.BigInteger;

import static junit.framework.TestCase.assertFalse;

public class RegistrarAddSignerTest extends AbstractRegistrarTest {

  @Test
  public void addSigner() throws Exception {
    setupWeb3();
    deployRegistrarContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    assert(this.registrarContract.isSigner(blockchainId, newSigner.getAddress()).send());
    assert(this.registrarContract.numSigners(blockchainId).send().compareTo(BigInteger.ONE) == 0);
  }

  // Do not allow a signer to be added twice.
  @Test
  public void failAddSameBlockchainTwice() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    AnIdentity newSigner = new AnIdentity();

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt(), BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    try {
      receipt = this.registrarContract.proposeVote(
          RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt(), BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    // There should still be just one signer
    assert(this.registrarContract.numSigners(blockchainId).send().compareTo(BigInteger.ONE) == 0);
  }

}
