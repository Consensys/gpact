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
package net.consensys.gpact.examples.tokenbridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.consensys.gpact.common.StatsHolder;


public class TokenBridge {
  static final Logger LOG = LogManager.getLogger(TokenBridge.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Example: Token Bridge");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
