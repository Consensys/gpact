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
package net.consensys.gpact.helpers;

import net.consensys.gpact.common.StatsHolder;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GpactExampleBase {
  static final Logger LOG = LogManager.getLogger(GpactExampleBase.class);

  protected static GpactExampleSystemManager getExampleSystemManager(String[] args) {
    if (args.length != 2) {
      LOG.info("Usage: [GPACT version] [properties file name]");
      throw new RuntimeException("Incorrect number of parameters");
    }
    GpactCrossControlManagerGroup.GpactVersion gpactVersion =
        GpactCrossControlManagerGroup.GpactVersion.valueOf(args[0]);
    switch (gpactVersion) {
      case V1:
        StatsHolder.log("GPACTv1");
        return new GpactV1ExampleSystemManager(args[1]);
      case V2:
        StatsHolder.log("GPACTv2");
        return new GpactV2ExampleSystemManager(args[1]);
      default:
        throw new RuntimeException("Unknown GPACT version");
    }
  }
}
