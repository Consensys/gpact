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
package net.consensys.gpact.examples.gpact.erc20bridge;

import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.helpers.AbstractExampleTest;
import org.junit.jupiter.api.Test;

public class Erc20BridgeTest extends AbstractExampleTest {

  @Test
  public void directSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(true, true, false);
    ERC20TokenBridgeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void transferSignSerialMultiBlockchain() throws Exception {
    String tempPropsFile = createPropertiesFile(false, true, false);
    ERC20TokenBridgeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V1.toString(), tempPropsFile});
  }

  @Test
  public void fakeMessagingSerialMultiBlockchainGpactV2() throws Exception {
    String tempPropsFile = createPropertiesFile(MessagingType.FAKE, true, false);
    ERC20TokenBridgeExample.main(
        new String[] {GpactCrossControlManagerGroup.GpactVersion.V2.toString(), tempPropsFile});
  }
}
