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
package net.consensys.gpact.helpers;

import net.consensys.gpact.CrosschainProtocols;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GpactV1ExampleSystemManager extends GpactExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(GpactV1ExampleSystemManager.class);

  public GpactV1ExampleSystemManager(String propertiesFileName) {
    super(propertiesFileName);
  }

  protected CrossControlManagerGroup getFunctionCallInstance() throws Exception {
    return CrosschainProtocols.getFunctionCallInstance(CrosschainProtocols.GPACT_V1).get();
  }

  protected String getFunctionCallImplName() throws Exception {
    return CrosschainProtocols.GPACT_V1;
  }
}
