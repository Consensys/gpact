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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import net.consensys.gpact.common.BlockchainInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesLoader {
  static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);

  public Properties properties = new Properties();

  public PropertiesLoader(String fileName) throws IOException {
    Path propertiesFile;
    if (fileName.startsWith("/")) {
      propertiesFile = Paths.get(fileName);
    } else {
      propertiesFile = Paths.get(System.getProperty("user.dir"), fileName);
    }
    FileInputStream fis = new FileInputStream(propertiesFile.toFile());
    this.properties.load(fis);
    LOG.info("Loaded properties from file {}", propertiesFile.toString());
  }

  public String getProperty(String prop) {
    String p = this.properties.getProperty(prop);
    if (p == null) {
      LOG.error("Property {} was not defined", prop);
      throw new IllegalArgumentException("Property " + prop + " was not defined");
    }
    return this.properties.getProperty(prop);
  }

  // Static credentials don't work for testing as multiple tests are run in parallel.
  // The nonce values end up being wrong and the test fail.
  //  public Credentials getCredentials() {
  //    return Credentials.create(getProperty("PRIVATE_KEY"));
  //  }
  //  public Credentials getCredentials(String keyName) {
  //    return Credentials.create(this.properties.getProperty(keyName));
  //  }

  public BlockchainInfo getBlockchainInfo(String tag) {
    String bcIdStr = getProperty(tag + "_BC_ID");
    LOG.info(" {}_BC_ID: 0x{}", tag, bcIdStr);
    String uriStr = getProperty(tag + "_URI");
    LOG.info(" {}_URI: {}", tag, uriStr);
    String gasPriceStrategyStr = getProperty(tag + "_GAS");
    LOG.info(" {}_GAS: {}", tag, gasPriceStrategyStr);
    String blockPeriodStr = getProperty(tag + "_PERIOD");
    LOG.info(" {}_PERIOD: {}", tag, blockPeriodStr);
    return new BlockchainInfo(bcIdStr, uriStr, gasPriceStrategyStr, blockPeriodStr);
  }

  public CrossBlockchainConsensusType getConsensusMethodology() {
    String consensus = getProperty("CONSENSUS_METHODOLOGY");
    LOG.info(" CONSENSUS_METHODOLOGY: {}", consensus);
    return CrossBlockchainConsensusType.valueOf(consensus);
  }

  public ExecutionEngineType getExecutionEnngine() {
    String engineType = getProperty("EXECUTION_ENGINE");
    LOG.info(" EXECUTION_ENGINE: {}", engineType);
    return ExecutionEngineType.valueOf(engineType);
  }
}
