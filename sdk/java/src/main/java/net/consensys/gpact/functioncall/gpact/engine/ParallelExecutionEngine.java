/*
 * Copyright 2020 ConsenSys Software Inc
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
package net.consensys.gpact.functioncall.gpact.engine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.consensys.gpact.common.BlockchainId;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.gpact.GpactCrosschainExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParallelExecutionEngine extends AbstractExecutionEngine {
  static final Logger LOG = LogManager.getLogger(ParallelExecutionEngine.class);

  public ParallelExecutionEngine(GpactCrosschainExecutor executor) {
    super(executor);
  }

  protected void executeCalls(
      List<CallExecutionTree> calls, List<BigInteger> callPath, BlockchainId theCallerBlockchainId)
      throws Exception {
    final int numCalls = calls.size();
    Executor executor = Executors.newFixedThreadPool(numCalls);
    CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
    BigInteger callOffset = BigInteger.ONE;
    for (CallExecutionTree segCall : calls) {
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(callOffset);
      completionService.submit(
          new Callable<String>() {
            public String call() throws Exception {
              callSegmentsAndRoot(segCall, nextCallPath, theCallerBlockchainId, numCalls);
              return "Not used";
            }
          });
      callOffset = callOffset.add(BigInteger.ONE);
    }
    int received = 0;
    while (received < numCalls) {
      Future<String> resultFuture = completionService.take(); // blocks if none available
      try {
        String result = resultFuture.get();
        LOG.info("Finished {} of {} parallel calls", received + 1, numCalls);
        received++;
      } catch (Exception e) {
        LOG.error("Error for call: {}: {}", received, e.getMessage());
        throw e;
      }
    }
    LOG.info("Done");
  }
}
