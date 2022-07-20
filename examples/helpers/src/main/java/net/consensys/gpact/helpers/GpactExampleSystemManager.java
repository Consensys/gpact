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
import net.consensys.gpact.common.StatsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GpactExampleSystemManager extends BaseExampleSystemManager {
  static final Logger LOG = LogManager.getLogger(GpactExampleSystemManager.class);

  private ExecutionEngineType executionEngineType;

  public GpactExampleSystemManager(String propertiesFileName) {
    super(propertiesFileName);
  }

  protected void loadFunctionLayerProperties(PropertiesLoader propsLoader) {
    this.executionEngineType = propsLoader.getExecutionEnngine();
    StatsHolder.log(executionEngineType.name());
  }

  public String getExecutionEngine() {
    switch (this.executionEngineType) {
      case SERIAL:
        return CrosschainProtocols.SERIAL;
      case PARALLEL:
        return CrosschainProtocols.PARALLEL;
      default:
        throw new RuntimeException("Not implemented yet");
    }
  }

  public ExecutionEngineType getExecutionEngineType() {
    return executionEngineType;
  }
}
