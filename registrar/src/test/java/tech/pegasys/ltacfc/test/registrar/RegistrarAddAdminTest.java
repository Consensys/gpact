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

public class RegistrarAddAdminTest extends AbstractRegistrarTest {

  @Test
  public void contractDeployerIsAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    // The address that deployed the contract should be an admin
    assert(this.registrarContract.isAdmin(this.credentials.getAddress()).send());
    // There should be only one admin.
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.ONE) == 0);
  }


  @Test
  public void addAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    Credentials credentials2 = createNewIdentity();
    String cred2Address = credentials2.getAddress();
    BigInteger cred2AddressBig = new BigInteger(cred2Address.substring(2), 16);

    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), cred2AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
    assert(receipt.isStatusOK());

    // The newly added address should be an admin.
    assert(this.registrarContract.isAdmin(cred2Address).send());
    // There should now be two admins
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.TWO) == 0);
  }

  // Do not allow a non-admin to add an admin.
  @Test
  public void failAddAdminByNonAdmin() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    Credentials credentials2 = createNewIdentity();
    TransactionManager tm2 = createTransactionManager(credentials2);
    Registrar regContract2 = deployRegistrarContract(tm2);

    String cred2Address = credentials2.getAddress();
    BigInteger cred2AddressBig = new BigInteger(cred2Address.substring(2), 16);

    try {
      TransactionReceipt receipt = regContract2.proposeVote(
          RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), cred2AddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    // The add an admin should have failed.
    assertFalse(this.registrarContract.isAdmin(cred2Address).send());
    // There should be only one admin.
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.ONE) == 0);
  }

  // Do not allow an admin to be added twice.
  @Test
  public void failAddSameAdminTwice() throws Exception {
    setupWeb3();
    deployRegistrarContract();

    BigInteger credAddressBig = new BigInteger(this.credentials.getAddress().substring(2), 16);


    try {
      TransactionReceipt receipt = this.registrarContract.proposeVote(
          RegistrarVoteTypes.VOTE_ADD_ADMIN.asBigInt(), credAddressBig, BigInteger.ZERO, BigInteger.ZERO).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    // There should be only one admin.
    assert(this.registrarContract.getNumAdmins().send().compareTo(BigInteger.ONE) == 0);
  }

}
