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
package net.consensys.gpact.nonatomic.appcontracts.erc20bridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import net.consensys.gpact.common.test.AbstractWeb3Test;
import net.consensys.gpact.common.test.DummyAddressGenerator;
import net.consensys.gpact.common.test.DummyBlockchainIdGenerator;
import net.consensys.gpact.nonatomic.appcontracts.erc20bridge.soliditywrappers.SfcErc20Bridge;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class SfcErc20BridgeTestConfig extends AbstractWeb3Test {

  @Test
  public void addOneErc20() throws Exception {
    setupWeb3();

    String dummySfcCbc = DummyAddressGenerator.gen();
    SfcErc20Bridge bridge =
        SfcErc20Bridge.deploy(this.web3j, this.tm, this.freeGasProvider, dummySfcCbc).send();

    String thisBcTokenContract = DummyAddressGenerator.gen();
    BigInteger otherBcId = DummyBlockchainIdGenerator.gen();
    String otherTokenContract = DummyAddressGenerator.gen();
    Boolean thisBcMassC = true;
    BigInteger MASSC = BigInteger.TWO;

    TransactionReceipt txr =
        bridge
            .addContractFirstMapping(
                thisBcTokenContract, otherBcId, otherTokenContract, thisBcMassC)
            .send();
    List<SfcErc20Bridge.TokenContractConfigEventResponse> configEvents =
        bridge.getTokenContractConfigEvents(txr);
    assertEquals(configEvents.size(), 1);

    List<SfcErc20Bridge.TokenContractAddressMappingChangedEventResponse> mappingChangedEvents =
        bridge.getTokenContractAddressMappingChangedEvents(txr);
    assertEquals(mappingChangedEvents.size(), 1);

    SfcErc20Bridge.TokenContractConfigEventResponse configEvent = configEvents.get(0);
    assertEquals(configEvent._thisBcTokenContract, thisBcTokenContract);
    assertEquals(configEvent._config.compareTo(MASSC), 0);

    SfcErc20Bridge.TokenContractAddressMappingChangedEventResponse mappingChangedEvent =
        mappingChangedEvents.get(0);
    assertEquals(mappingChangedEvent._thisBcTokenContract, thisBcTokenContract);
    assertEquals(mappingChangedEvent._otherBcId.compareTo(otherBcId), 0);
    assertEquals(mappingChangedEvent._othercTokenContract, otherTokenContract);
  }

  @Test
  public void addSecondRemoteErc20() throws Exception {
    setupWeb3();

    String dummySfcCbc = DummyAddressGenerator.gen();
    SfcErc20Bridge bridge =
        SfcErc20Bridge.deploy(this.web3j, this.tm, this.freeGasProvider, dummySfcCbc).send();

    String thisBcTokenContract = DummyAddressGenerator.gen();
    BigInteger otherBcId = DummyBlockchainIdGenerator.gen();
    String otherTokenContract = DummyAddressGenerator.gen();
    Boolean thisBcMassC = true;
    BigInteger MASSC = BigInteger.TWO;

    bridge
        .addContractFirstMapping(thisBcTokenContract, otherBcId, otherTokenContract, thisBcMassC)
        .send();

    BigInteger otherBcId2 = DummyBlockchainIdGenerator.gen();
    String otherTokenContract2 = DummyAddressGenerator.gen();
    TransactionReceipt txr =
        bridge.changeContractMapping(thisBcTokenContract, otherBcId2, otherTokenContract2).send();

    List<SfcErc20Bridge.TokenContractAddressMappingChangedEventResponse> mappingChangedEvents =
        bridge.getTokenContractAddressMappingChangedEvents(txr);
    assertEquals(mappingChangedEvents.size(), 1);

    SfcErc20Bridge.TokenContractAddressMappingChangedEventResponse mappingChangedEvent =
        mappingChangedEvents.get(0);
    assertEquals(mappingChangedEvent._thisBcTokenContract, thisBcTokenContract);
    assertEquals(mappingChangedEvent._otherBcId.compareTo(otherBcId2), 0);
    assertEquals(mappingChangedEvent._othercTokenContract, otherTokenContract2);
  }

  @Test
  public void changeErc20Config() throws Exception {
    setupWeb3();

    String dummySfcCbc = DummyAddressGenerator.gen();
    SfcErc20Bridge bridge =
        SfcErc20Bridge.deploy(this.web3j, this.tm, this.freeGasProvider, dummySfcCbc).send();

    String thisBcTokenContract = DummyAddressGenerator.gen();
    BigInteger otherBcId = DummyBlockchainIdGenerator.gen();
    String otherTokenContract = DummyAddressGenerator.gen();
    Boolean thisBcMassC = true;

    bridge
        .addContractFirstMapping(thisBcTokenContract, otherBcId, otherTokenContract, thisBcMassC)
        .send();

    thisBcMassC = false;
    BigInteger MINTING = BigInteger.ONE;
    TransactionReceipt txr = bridge.setTokenConfig(thisBcTokenContract, thisBcMassC).send();

    List<SfcErc20Bridge.TokenContractConfigEventResponse> configEvents =
        bridge.getTokenContractConfigEvents(txr);
    assertEquals(configEvents.size(), 1);

    SfcErc20Bridge.TokenContractConfigEventResponse configEvent = configEvents.get(0);
    assertEquals(configEvent._thisBcTokenContract, thisBcTokenContract);
    assertEquals(configEvent._config.compareTo(MINTING), 0);
  }
}
