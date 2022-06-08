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
import net.consensys.gpact.common.BlockchainConfig;
import net.consensys.gpact.messaging.common.attestorrelayer.AttestorRelayer;
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

  private String getProperty(String prop) {
    String p = this.properties.getProperty(prop);
    if (p == null) {
      LOG.error("Property {} was not defined", prop);
      throw new IllegalArgumentException("Property " + prop + " was not defined");
    }
    return this.properties.getProperty(prop);
  }

  public BlockchainConfig getBlockchainInfo(String tag) {
    String bcIdStr = getProperty(tag + "_BC_ID");
    LOG.info(" {}_BC_ID: 0x{}", tag, bcIdStr);
    String rpcUriStr = getProperty(tag + "_BC_RPC_URI");
    LOG.info(" {}_BC_RPC_URI: {}", tag, rpcUriStr);
    String wsUriStr = getProperty(tag + "_BC_WS_URI");
    LOG.info(" {}_BC_WS_URI: {}", tag, wsUriStr);
    String gasPriceStrategyStr = getProperty(tag + "_GAS");
    LOG.info(" {}_GAS: {}", tag, gasPriceStrategyStr);
    String blockPeriodStr = getProperty(tag + "_PERIOD");
    LOG.info(" {}_PERIOD: {}", tag, blockPeriodStr);
    String observerUriStr = getProperty(tag + "_OBSERVER_URI");
    LOG.info(" {}_OBSERVER_URI: {}", tag, observerUriStr);
    String dispatcherUriStr = getProperty(tag + "_DISPATCHER_URI");
    LOG.info(" {}_DISPATCHER_URI: {}", tag, dispatcherUriStr);
    String msgStoreUriFromDispatcherStr = getProperty(tag + "_MSG_STORE_FROM_DISPATCHER_URI");
    LOG.info(" {}_MSG_STORE_FROM_DISPATCHER_URI: {}", tag, msgStoreUriFromDispatcherStr);
    String msgStoreUriFromUserStr = getProperty(tag + "_MSG_STORE_FROM_USER_URI");
    LOG.info(" {}_MSG_STORE_FROM_USER_URI: {}", tag, msgStoreUriFromUserStr);
    return new BlockchainConfig(
        bcIdStr,
        rpcUriStr,
        wsUriStr,
        gasPriceStrategyStr,
        blockPeriodStr,
        observerUriStr,
        dispatcherUriStr,
        msgStoreUriFromDispatcherStr,
        msgStoreUriFromUserStr);
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

  public String getRelayerUri() {
    String relayerUri = getProperty("RELAYER_URI");
    LOG.info(" RELAYER_URI: {}", relayerUri);
    return relayerUri;
  }

  public AttestorRelayer.WatcherType getWatcherType(){
    String watcherType = getProperty("WATCHER_TYPE");
    LOG.info(" WATCHER_TYPE: {}", watcherType);
    return AttestorRelayer.WatcherType.valueOf(watcherType.toUpperCase());
  }
}
