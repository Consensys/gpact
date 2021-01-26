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
package tech.pegasys.ltacfc.cbc.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelExecutionEngine extends AbstractExecutionEngine {
  static final Logger LOG = LogManager.getLogger(ParallelExecutionEngine.class);

  public ParallelExecutionEngine(AbstractCbcExecutor executor) {
    super(executor);
  }

  protected void executeCalls(List<RlpType> calls, List<BigInteger> callPath, BigInteger theCallerBlockchainId) throws Exception {
    int numCalls = calls.size();
    Executor executor = Executors.newFixedThreadPool(numCalls);
    CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
    BigInteger callOffset = BigInteger.ONE;
    for (int i = 1; i < numCalls; i++) {
      RlpList segCall = (RlpList) calls.get(i);
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(callOffset);
      completionService.submit(new Callable<String>() {
        public String call() throws Exception {
          callSegments(segCall, nextCallPath, theCallerBlockchainId);
          return "Not used";
        }
      });
      callOffset = callOffset.add(BigInteger.ONE);
    }
    int received = 0;
    while(received < numCalls - 1) {
      Future<String> resultFuture = completionService.take(); //blocks if none available
      try {
        String result = resultFuture.get();
        LOG.info("Finished {} of {} parallel calls", received + 1, numCalls - 1);
        received++;
      }
      catch(Exception e) {
        LOG.error("Error for call: {}: {}", received, e.getMessage());
        throw e;
      }
    }
    LOG.info("Done");
  }

}
