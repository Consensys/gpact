/*
 * Copyright 2021 ConsenSys Software Inc
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
package net.consensys.gpact.applications.sfc.erc721bridge;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import net.consensys.gpact.soliditywrappers.applications.sfc.erc721bridge.SfcErc721Bridge;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class SfcErc721BridgeTest extends AbstractWeb3Test {
  private static final String SENDER_DENYLISTED_REVERT_MSG = "ERC 721 Bridge: Sender is denylisted";
  private static final String RECIPIENT_DENYLISTED_REVERT_MSG =
      "ERC 721 Bridge: Recipient is denylisted";

  private String fixDeniedAddress;
  private Credentials fixDeniedCred;
  private SfcErc721Bridge fixBridgeWithAdminCred;

  @BeforeEach
  public void setup() throws Exception {
    setupWeb3();
    fixDeniedCred = createNewIdentity();
    fixDeniedAddress = fixDeniedCred.getAddress();
    fixBridgeWithAdminCred =
        SfcErc721Bridge.deploy(
                this.web3j, this.tm, this.freeGasProvider, DummyAddressGenerator.gen())
            .send();
  }

  @Test
  public void addToDenylistShouldAddAddressToList() throws Exception {
    // sanity check, ensure that the address is not already in the denylist.
    assertEquals(fixBridgeWithAdminCred.isDenylisted(fixDeniedAddress).send(), false);

    TransactionReceipt receipt = addToDenylist(fixDeniedAddress);
    List<SfcErc721Bridge.AddressDenylistedEventResponse> addressAddedEvents =
        fixBridgeWithAdminCred.getAddressDenylistedEvents(receipt);
    assertEquals(1, addressAddedEvents.size());
    assertEquals(fixDeniedAddress, addressAddedEvents.get(0)._address);
  }

  @Test
  public void removeFromDenylistShouldRemoveAddressFromList() throws Exception {
    addToDenylist(fixDeniedAddress);

    TransactionReceipt receipt = removeFromDenylist(fixDeniedAddress);
    List<SfcErc721Bridge.AddressRemovedFromDenylistEventResponse> addressRemovedEvents =
        fixBridgeWithAdminCred.getAddressRemovedFromDenylistEvents(receipt);
    assertEquals(1, addressRemovedEvents.size());
    assertEquals(fixDeniedAddress, addressRemovedEvents.get(0)._address);
  }

  @Test
  public void transferTokenShouldFailForDenylistedSender() throws Exception {
    addToDenylist(fixDeniedAddress);

    RemoteFunctionCall<TransactionReceipt> receipt =
        loadBridgeWithCredential(fixDeniedCred)
            .transferToOtherBlockchain(
                BigInteger.ZERO,
                DummyAddressGenerator.gen(),
                DummyAddressGenerator.gen(),
                BigInteger.ZERO);
    TransactionException te = assertThrows(TransactionException.class, receipt::send);
    assertEquals(SENDER_DENYLISTED_REVERT_MSG, getRevertReason(te));
  }

  @Test
  public void transferTokenShouldFailForDenylistedRecipient() throws Exception {
    addToDenylist(fixDeniedAddress);

    RemoteFunctionCall<TransactionReceipt> receipt =
        fixBridgeWithAdminCred.transferToOtherBlockchain(
            BigInteger.ZERO, DummyAddressGenerator.gen(), fixDeniedAddress, BigInteger.ZERO);
    TransactionException te = assertThrows(TransactionException.class, receipt::send);
    assertEquals(RECIPIENT_DENYLISTED_REVERT_MSG, getRevertReason(te));
  }

  @Test
  public void transferTokenShouldSucceedIfSenderRemovedFromDenylist() throws Exception {
    addToDenylist(fixDeniedAddress);
    removeFromDenylist(fixDeniedAddress);

    RemoteFunctionCall<TransactionReceipt> receipt =
        loadBridgeWithCredential(fixDeniedCred)
            .transferToOtherBlockchain(
                BigInteger.ZERO,
                DummyAddressGenerator.gen(),
                DummyAddressGenerator.gen(),
                BigInteger.ZERO);
    TransactionException te = assertThrows(TransactionException.class, receipt::send);
    assertNotEquals(SENDER_DENYLISTED_REVERT_MSG, getRevertReason(te));
  }

  @Test
  public void receiveTokenShouldFailForDenylistedRecipient() throws Exception {
    addToDenylist(fixDeniedAddress);

    RemoteFunctionCall<TransactionReceipt> receipt =
        fixBridgeWithAdminCred.receiveFromOtherBlockchain(
            DummyAddressGenerator.gen(),
            fixDeniedAddress,
            BigInteger.ZERO,
            Strings.EMPTY.getBytes());
    TransactionException te = assertThrows(TransactionException.class, receipt::send);
    assertEquals(RECIPIENT_DENYLISTED_REVERT_MSG, getRevertReason(te));
  }

  @Test
  public void receiveTokenShouldSucceedIfRecipientRemovedFromDenylist() throws Exception {
    addToDenylist(fixDeniedAddress);
    removeFromDenylist(fixDeniedAddress);

    RemoteFunctionCall<TransactionReceipt> receipt =
        fixBridgeWithAdminCred.receiveFromOtherBlockchain(
            DummyAddressGenerator.gen(),
            fixDeniedAddress,
            BigInteger.ZERO,
            Strings.EMPTY.getBytes());
    TransactionException te = assertThrows(TransactionException.class, receipt::send);
    assertNotEquals(RECIPIENT_DENYLISTED_REVERT_MSG, getRevertReason(te));
  }

  private SfcErc721Bridge loadBridgeWithCredential(Credentials credentials) {
    return SfcErc721Bridge.load(
        fixBridgeWithAdminCred.getContractAddress(), this.web3j, credentials, this.freeGasProvider);
  }

  private TransactionReceipt addToDenylist(String address) throws Exception {
    TransactionReceipt receipt = fixBridgeWithAdminCred.addToDenylist(address).send();
    assertEquals(true, fixBridgeWithAdminCred.isDenylisted(address).send());
    return receipt;
  }

  private TransactionReceipt removeFromDenylist(String address) throws Exception {
    TransactionReceipt receipt = fixBridgeWithAdminCred.removeFromDenylist(address).send();
    assertEquals(false, fixBridgeWithAdminCred.isDenylisted(address).send());
    return receipt;
  }

  private String getRevertReason(TransactionException te) {
    return RevertReason.decodeRevertReason(
        te.getTransactionReceipt().map(TransactionReceipt::getRevertReason).orElse(Strings.EMPTY));
  }
}
