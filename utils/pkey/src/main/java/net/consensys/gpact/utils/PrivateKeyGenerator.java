/*
 * Copyright 2021 ConsenSys Software.
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
package net.consensys.gpact.utils;

import net.consensys.gpact.common.crypto.KeyPairGen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivateKeyGenerator {
  static final Logger LOG = LogManager.getLogger(PrivateKeyGenerator.class);

  public static void main(String[] args) throws Exception {
    LOG.info("Example: Generate Key Pair");
    LOG.info("Started");

    KeyPairGen gen = new KeyPairGen();
    String privateKey = gen.generateKeyPairGetPrivateKey();
    LOG.info("Private Key: " + privateKey);

    LOG.info("Done");
  }
}
