/*
 * Copyright 2020 ConsenSys AG.
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StatsHolder {
  private static final Logger LOG = LogManager.getLogger(StatsHolder.class);

  private static StatsHolder instance;

  List<Stats> stats = new ArrayList<>();

  private static StatsHolder getInstance() {
    if (instance == null) {
      instance = new StatsHolder();
    }
    return instance;
  }

  public static void log(String event) {
    getInstance().stats.add(new Stats(event));
  }

  public static void logGas(String event, BigInteger gas) {
    getInstance().stats.add(new Stats(event, gas));
  }


  public static void print() {
    getInstance().printAll();
  }


  public void printAll() {
    LOG.info("Stats");
    if (this.stats.isEmpty()) {
      LOG.info(" NONE");
    }
    for (Stats s: this.stats) {
      if (s.gas == null) {
        LOG.info("{} {} {}", s.timestamp, s.nanoTime, s.event);
      }
      else {
        LOG.info("{} {} {}: gas: {}", s.timestamp, s.nanoTime, s.event, s.gas);
      }
    }
  }


  static class Stats {
    String event;
    BigInteger gas;
    long timestamp = System.currentTimeMillis();
    long nanoTime = System.nanoTime();

    Stats(String event) {
      this.event = event;
    }

    Stats(String event, BigInteger gas) {
      this.event = event;
      this.gas = gas;
    }

  }
}
