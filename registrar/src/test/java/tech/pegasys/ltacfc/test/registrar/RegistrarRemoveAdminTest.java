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
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;

import java.math.BigInteger;

import static junit.framework.TestCase.assertFalse;

public class RegistrarRemoveAdminTest extends AbstractRegistrarTest {

  @Test
  public void removeAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    Credentials credentials2 = createNewIdentity();
    TransactionManager tm2 = createTransactionManager(credentials2);
    Registrar regContract2 = loadContract(tm2);

    String cred2Address = credentials2.getAddress();
    BigInteger cred2AddressBig = new BigInteger(cred2Address.substring(2), 16);
    BigInteger cred1AddressBig = new BigInteger(this.credentials.getAddress().substring(2), 16);


    // Add an admin
    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), cred2AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    // Remove the original admin
    receipt = regContract2.proposeVote(
        RegistrarVoteTypes.VOTE_REMOVE_ADMIN.asBigInt(), cred1AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    // Check that the new admin is an admin and that the old admin is no longer an admin
    assertFalse(this.registrarContract.isAdmin(this.credentials.getAddress()).send());
    assert(this.registrarContract.isAdmin(cred2Address).send());
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.ONE) == 0);
  }


  @Test
  public void failCantRemoveSelf() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    // Add an admin
    Credentials credentials2 = createNewIdentity();
    String cred2Address = credentials2.getAddress();
    BigInteger cred2AddressBig = new BigInteger(cred2Address.substring(2), 16);

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), cred2AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    // Remove the original admin using the original admin's credentials.
    try {
      BigInteger cred1AddressBig = new BigInteger(this.credentials.getAddress().substring(2), 16);
      receipt = this.registrarContract.proposeVote(
          RegistrarVoteTypes.VOTE_REMOVE_ADMIN.asBigInt(), cred1AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    // Check that both are still admins
    assert(this.registrarContract.isAdmin(this.credentials.getAddress()).send());
    assert(this.registrarContract.isAdmin(cred2Address).send());
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.TWO) == 0);
  }


  // Do not allow a non-admin to remove an admin.
  @Test
  public void failRemoveByNonAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    Credentials credentials2 = createNewIdentity();
    TransactionManager tm2 = createTransactionManager(credentials2);
    Registrar regContract2 = loadContract(tm2);

    Credentials credentials3 = createNewIdentity();

    String cred3Address = credentials3.getAddress();
    BigInteger cred3AddressBig = new BigInteger(cred3Address.substring(2), 16);
    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), cred3AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    // Check only added admins are admins
    assert(this.registrarContract.isAdmin(this.credentials.getAddress()).send());
    assertFalse(this.registrarContract.isAdmin(credentials2.getAddress()).send());
    assert(this.registrarContract.isAdmin(cred3Address).send());

    // Remove the original admin using the original admin's credentials.
    try {
      receipt = regContract2.proposeVote(
          RegistrarVoteTypes.VOTE_REMOVE_ADMIN.asBigInt(), cred3AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    // Check only added admins are admins
    assert(this.registrarContract.isAdmin(this.credentials.getAddress()).send());
    assertFalse(this.registrarContract.isAdmin(credentials2.getAddress()).send());
    assert(this.registrarContract.isAdmin(cred3Address).send());
  }

  // Fail if the address to be removed is not an admin
  @Test
  public void failRemoveNonAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    Credentials credentials3 = createNewIdentity();
    String cred3Address = credentials3.getAddress();
    BigInteger cred3AddressBig = new BigInteger(cred3Address.substring(2), 16);

    try {
      TransactionReceipt receipt = this.registrarContract.proposeVote(
          RegistrarVoteTypes.VOTE_REMOVE_ADMIN.asBigInt(), cred3AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }
  }

}
