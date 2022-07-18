/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact.functioncall.gpact.v2;

import java.io.IOException;
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManager;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import org.web3j.crypto.Credentials;

public class GpactV2CrossControlManagerGroup extends GpactCrossControlManagerGroup {

  public GpactV2CrossControlManagerGroup() {
    super(GpactVersion.V2);
  }

  protected GpactCrossControlManager newGpactCrossControlManager(
      final Credentials credentials, final BlockchainConfig bcConfig) throws IOException {
    return new GpactV2CrossControlManager(credentials, bcConfig);
  }
}
