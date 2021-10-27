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

import net.consensys.gpact.appcontracts.nonatomic.erc721bridge.soliditywrappers.SfcErc721Bridge;
import net.consensys.gpact.common.RevertReason;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

public class SfcErc721BridgeTest extends AbstractWeb3Test {
    private static final String SENDER_BLACKLISTED_REVERT_MSG = "ERC 721 Bridge: Sender is blacklisted";
    private static final String RECIPIENT_BLACKLISTED_REVERT_MSG = "ERC 721 Bridge: Recipient is blacklisted";

    private String fixBlacklistAddress;
    private Credentials fixBlacklistedCred;
    private SfcErc721Bridge fixBridgeWithAdminCred;

    @Before
    public void setup() throws Exception {
        setupWeb3();
        fixBlacklistedCred = createNewIdentity();
        fixBlacklistAddress = fixBlacklistedCred.getAddress();
        fixBridgeWithAdminCred = SfcErc721Bridge.deploy(this.web3j, this.tm, this.freeGasProvider, DummyAddressGenerator.gen()).send();
    }

    @Test
    public void addToBlacklistShouldAddAddressToList() throws Exception {
        // sanity check, ensure that the address is not already blacklisted.
        assertEquals(fixBridgeWithAdminCred.isBlacklisted(fixBlacklistAddress).send(), false);

        TransactionReceipt receipt = addToBlacklist(fixBlacklistAddress);
        List<SfcErc721Bridge.AddressBlacklistedEventResponse> addressAddedEvents = fixBridgeWithAdminCred.getAddressBlacklistedEvents(receipt);
        assertEquals(1, addressAddedEvents.size());
        assertEquals(fixBlacklistAddress, addressAddedEvents.get(0)._address);
    }

    @Test
    public void removeFromBlacklistShouldRemoveAddressFromList() throws Exception {
        addToBlacklist(fixBlacklistAddress);

        TransactionReceipt receipt = removeFromBlacklist(fixBlacklistAddress);
        List<SfcErc721Bridge.AddressRemovedFromBlacklistEventResponse> addressRemovedEvents = fixBridgeWithAdminCred.getAddressRemovedFromBlacklistEvents(receipt);
        assertEquals(1, addressRemovedEvents.size());
        assertEquals(fixBlacklistAddress, addressRemovedEvents.get(0)._address);
    }

    @Test
    public void transferTokenShouldFailForBlacklistedSender() throws Exception {
        addToBlacklist(fixBlacklistAddress);

        RemoteFunctionCall<TransactionReceipt> receipt = loadBridgeWithCredential(fixBlacklistedCred).transferToOtherBlockchain(BigInteger.ZERO,
                DummyAddressGenerator.gen(), DummyAddressGenerator.gen(), BigInteger.ZERO);
        TransactionException te = assertThrows(TransactionException.class, receipt::send);
        assertEquals(SENDER_BLACKLISTED_REVERT_MSG, getRevertReason(te));
    }

    @Test
    public void transferTokenShouldFailForBlacklistedRecipient() throws Exception {
        addToBlacklist(fixBlacklistAddress);

        RemoteFunctionCall<TransactionReceipt> receipt = fixBridgeWithAdminCred.transferToOtherBlockchain(BigInteger.ZERO, DummyAddressGenerator.gen(),
                fixBlacklistAddress, BigInteger.ZERO);
        TransactionException te = assertThrows(TransactionException.class, receipt::send);
        assertEquals(RECIPIENT_BLACKLISTED_REVERT_MSG, getRevertReason(te));
    }

    @Test
    public void transferTokenShouldSucceedIfSenderRemovedFromBlacklist() throws Exception {
        addToBlacklist(fixBlacklistAddress);
        removeFromBlacklist(fixBlacklistAddress);

        RemoteFunctionCall<TransactionReceipt> receipt = loadBridgeWithCredential(fixBlacklistedCred).transferToOtherBlockchain(BigInteger.ZERO,
                DummyAddressGenerator.gen(), DummyAddressGenerator.gen(), BigInteger.ZERO);
        TransactionException te = assertThrows(TransactionException.class, receipt::send);
        assertNotEquals(SENDER_BLACKLISTED_REVERT_MSG, getRevertReason(te));
    }

    @Test
    public void receiveTokenShouldFailForBlacklistedRecipient() throws Exception {
        addToBlacklist(fixBlacklistAddress);

        RemoteFunctionCall<TransactionReceipt> receipt = fixBridgeWithAdminCred.receiveFromOtherBlockchain(DummyAddressGenerator.gen(), fixBlacklistAddress,
                BigInteger.ZERO);
        TransactionException te = assertThrows(TransactionException.class, receipt::send);
        assertEquals(RECIPIENT_BLACKLISTED_REVERT_MSG, getRevertReason(te));
    }

    @Test
    public void receiveTokenShouldSucceedIfRecipientRemovedFromBlacklist() throws Exception {
        addToBlacklist(fixBlacklistAddress);
        removeFromBlacklist(fixBlacklistAddress);

        RemoteFunctionCall<TransactionReceipt> receipt = fixBridgeWithAdminCred.receiveFromOtherBlockchain(DummyAddressGenerator.gen(), fixBlacklistAddress,
                BigInteger.ZERO);
        TransactionException te = assertThrows(TransactionException.class, receipt::send);
        assertNotEquals(RECIPIENT_BLACKLISTED_REVERT_MSG, getRevertReason(te));
    }

    private SfcErc721Bridge loadBridgeWithCredential(Credentials credentials) {
        return SfcErc721Bridge.load(fixBridgeWithAdminCred.getContractAddress(), this.web3j, credentials, this.freeGasProvider);
    }

    private TransactionReceipt addToBlacklist(String address) throws Exception {
        TransactionReceipt receipt = fixBridgeWithAdminCred.addToBlacklist(address).send();
        assertEquals(true, fixBridgeWithAdminCred.isBlacklisted(address).send());
        return receipt;
    }

    private TransactionReceipt removeFromBlacklist(String address) throws Exception {
        TransactionReceipt receipt = fixBridgeWithAdminCred.removeFromBlacklist(address).send();
        assertEquals(false, fixBridgeWithAdminCred.isBlacklisted(address).send());
        return receipt;
    }

    private String getRevertReason(TransactionException te) {
        return RevertReason.decodeRevertReason(te.getTransactionReceipt().map(TransactionReceipt::getRevertReason).orElse(Strings.EMPTY));
    }
}
