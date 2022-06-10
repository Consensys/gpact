/*
 * Copyright 2019 ConsenSys Software Inc
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
package net.consensys.gpact.messaging.eventrelay;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import net.consensys.gpact.common.*;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.functioncall.sfc.SimpleCrossControlManager;
import net.consensys.gpact.functioncall.sfc.SimpleCrosschainControl;
import net.consensys.gpact.messaging.SignedEvent;
import net.consensys.gpact.messaging.common.MessagingRegistrar;
import net.consensys.gpact.messaging.fake.FakeRelayer;
import net.consensys.gpact.messaging.fake.FakeSigner;
import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

public class EventRelayTest extends AbstractWeb3Test {
  private MessagingRegistrar registrarContract;
  private EventRelayVerifier eventStoreContract;
  private SimpleCrosschainControl sfc;

  private EventRelayAppTest appTest;

  private void deployContracts(BlockchainId sourceBcId, String sourceCbc, BlockchainId myBcId)
      throws Exception {
    BigInteger timeHorizon = BigInteger.valueOf(1000);
    this.registrarContract =
        MessagingRegistrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.sfc =
        SimpleCrosschainControl.deploy(
                this.web3j, this.tm, this.freeGasProvider, myBcId.asBigInt(), timeHorizon)
            .send();
    this.eventStoreContract =
        EventRelayVerifier.deploy(
                this.web3j,
                this.tm,
                this.freeGasProvider,
                this.registrarContract.getContractAddress(),
                this.sfc.getContractAddress())
            .send();
    this.appTest = EventRelayAppTest.deploy(this.web3j, this.tm, this.freeGasProvider).send();

    this.sfc
        .addVerifier(sourceBcId.asBigInt(), this.eventStoreContract.getContractAddress())
        .send();
    this.sfc.addRemoteCrosschainControl(sourceBcId.asBigInt(), sourceCbc).send();
  }

  private void addBlockchain(BlockchainId sourceBcId, String onlySigner) throws Exception {
    this.registrarContract
        .addSignerSetThreshold(sourceBcId.asBigInt(), onlySigner, BigInteger.ONE)
        .send();
  }

  private byte[] createEventData(BlockchainId destBcId1, BigInteger val) {
    byte[] txId = new byte[32]; // Crosschain transaction id. Should be randomly generated.
    byte[] timestamp =
        FormatConversion.longToUint256ByteArray(
            System.currentTimeMillis() / 1000); // Time when event was emitted.
    byte[] caller =
        addressStringToBytes(
            "0xF3ce7435e19A4E902e2aF5bfE48a3004DBef0000"); // Address of contract that instigated
    // the crosschain call.
    byte[] destBcId = destBcId1.asBytes();
    byte[] destContract = addressStringToBytes(this.appTest.getContractAddress());
    String functionCallStr = this.appTest.getABI_aFunc(val);
    Bytes functionCallBytes = Bytes.fromHexString(functionCallStr);
    byte[] functionCall = functionCallBytes.toArray();
    byte[] functionCallLength = FormatConversion.longToUint256ByteArray(functionCall.length);
    byte[] functionCallOffset = FormatConversion.longToUint256ByteArray(6 * 32);

    // (bytes32, uint256, address, uint256, address, bytes)
    ByteBuffer bb = ByteBuffer.allocate(7 * 32 + functionCall.length);
    bb.put(txId);
    bb.put(timestamp);
    bb.put(new byte[12]); // zero fill so that the address is 32 byte aligned.
    bb.put(caller);
    bb.put(destBcId);
    bb.put(new byte[12]); // zero fill so that the address is 32 byte aligned.
    bb.put(destContract);
    bb.put(functionCallOffset);
    bb.put(functionCallLength);
    bb.put(functionCall);

    return bb.array();
  }

  @Test
  public void executeFunctionCall() throws Exception {
    BlockchainId destBcId =
        new BlockchainId(
            BLOCKCHAIN_ID); // Must match the blockchain the contracts have been deployed on.
    BlockchainId sourceBcId = new BlockchainId(BigInteger.valueOf(32)); // Any other blockchain id.
    String sourceBcCbcAddress =
        "0xF3ce7435e19A4E902e2aF5bfE48a3004DBef0001"; // For the purposes of this test, a random
    // address.

    AnIdentity newSigner = AnIdentity.createNewRandomIdentity();
    FakeRelayer fakeRelayer = new FakeRelayer(newSigner);
    FakeSigner fakeSigner = new FakeSigner(sourceBcId, fakeRelayer);

    setupWeb3();
    deployContracts(sourceBcId, sourceBcCbcAddress, destBcId);
    addBlockchain(sourceBcId, newSigner.getAddress());

    BigInteger val = BigInteger.valueOf(17);

    byte[] eventData = createEventData(destBcId, val);

    SignedEvent signedEvent =
        fakeSigner.getSignedEvent(
            null,
            null,
            eventData,
            sourceBcCbcAddress,
            SimpleCrossControlManager.CROSSCALL_EVENT_SIGNATURE);

    TransactionReceipt txR;
    try {
      txR =
          this.eventStoreContract
              .relayEvent(
                  sourceBcId.asBigInt(),
                  sourceBcCbcAddress,
                  signedEvent.getEventData(),
                  signedEvent.getEncodedSignatures())
              .send();
    } catch (TransactionException ex) {
      // Crosschain Control Contract reverted
      String revertReason =
          RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason());
      throw new Exception("Revert Reason: " + revertReason);
    }
    assertTrue(txR.isStatusOK());

    // Finally.... check that the value really was set.
    BigInteger actualVal = this.appTest.val().send();
    assertEquals(val.longValue(), actualVal.longValue());
  }
}
