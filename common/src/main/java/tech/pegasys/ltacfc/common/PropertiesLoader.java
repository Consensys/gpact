/*
 * Copyright 2019 ConsenSys AG.
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
package tech.pegasys.ltacfc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesLoader {
  static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);

  public class BlockchainInfo {
    public String bcId;
    public String uri;
    public String gasPriceStrategy;
    public String period;

    public BlockchainInfo(String bcId, String uri, String gasPriceStrategy, String period) {
      this.bcId = bcId;
      this.uri = uri;
      this.gasPriceStrategy = gasPriceStrategy;
      this.period = period;
    }
  }

  public Properties properties = new Properties();


  public PropertiesLoader(String fileName) throws IOException {
    Path propertiesFile;
    if (fileName.startsWith("/")) {
      propertiesFile = Paths.get(fileName);
    }
    else {
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

  public Credentials getCredentials() {
    return Credentials.create(this.properties.getProperty("PRIVATE_KEY"));
  }
  public Credentials getCredentials(String keyName) {
    return Credentials.create(this.properties.getProperty(keyName));
  }

  public BlockchainInfo getBlockchainInfo(String tag) {
    String bcIdStr = this.properties.getProperty(tag + "_BC_ID");
    LOG.info(" {}_BC_ID: 0x{}", tag, bcIdStr);
    String uriStr = this.properties.getProperty(tag + "_URI");
    LOG.info(" {}_URI: {}", tag, uriStr);
    String gasPriceStrategyStr = this.properties.getProperty(tag + "_GAS");
    LOG.info(" {}_GAS: {}", tag, gasPriceStrategyStr);
    String blockPeriodStr = this.properties.getProperty(tag + "_PERIOD");
    LOG.info(" {}_PERIOD: {}", tag, blockPeriodStr);
    return new BlockchainInfo(bcIdStr, uriStr, gasPriceStrategyStr, blockPeriodStr);
  }

  public CrossBlockchainConsensusType getConsensusMethodology() {
    String consensus = this.properties.getProperty("CONSENSUS_METHODOLOGY");
    LOG.info(" CONSENSUS_METHODOLOGY: {}", consensus);
    return CrossBlockchainConsensusType.valueOf(consensus);
  }

  public ExecutionEngineType getExecutionEnngine() {
    String engineType = this.properties.getProperty("EXECUTION_ENGINE");
    LOG.info(" EXECUTION_ENGINE: {}", engineType);
    return ExecutionEngineType.valueOf(engineType);
  }
}
