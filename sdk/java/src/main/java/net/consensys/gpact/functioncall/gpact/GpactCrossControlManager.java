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
package net.consensys.gpact.functioncall.gpact;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.consensys.gpact.common.*;
import net.consensys.gpact.functioncall.CallExecutionTree;
import net.consensys.gpact.functioncall.CrossControlManager;
import net.consensys.gpact.messaging.SignedEvent;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface GpactCrossControlManager extends CrossControlManager {

  void deployContract() throws Exception;

  void loadContract(String cbcAddress);

  void addRemoteBlockchain(
      BlockchainId bcId, String cbcContractAddress, String verifierContractAddress)
      throws Exception;

  Tuple<TransactionReceipt, byte[], Boolean> start(
      final CallExecutionTree callExecutionTree,
      final BigInteger transactionId,
      final BigInteger timeout)
      throws Exception;

  Tuple<TransactionReceipt, byte[], Boolean> segment(
      final CallExecutionTree callExecutionTree,
      final SignedEvent startEvent,
      final SignedEvent[] segEvents,
      final List<BigInteger> callPath)
      throws Exception;

  Tuple<TransactionReceipt, byte[], Boolean> root(
      final CallExecutionTree callExecutionTree,
      final SignedEvent startEvent,
      final SignedEvent[] segEvents)
      throws Exception;

  CompletableFuture<TransactionReceipt> signallingAsyncPart1(
      SignedEvent rootEvent, List<SignedEvent> segEvents) throws Exception;

  void signallingAsyncPart2(TransactionReceipt txR) throws Exception;

  String getCbcContractAddress();

  boolean getRootEventSuccess();

  byte[] getStartEventSignature();

  byte[] getSegmentEventSignature();

  byte[] getRootEventSignature();

  class BadCallEventResponse extends BaseEventResponse {
    public GpactCrossControlManagerGroup.GpactVersion ver;
    public byte[] _expectedFunctionCallHash;
    public BigInteger _expectedBlockchainId;
    public BigInteger _actualBlockchainId;
    public String _expectedContract;
    public String _actualContract;
    public byte[] _expectedFunctionCall;
    public byte[] _actualFunctionCall;

    public BadCallEventResponse(
        BigInteger _expectedBlockchainId,
        BigInteger _actualBlockchainId,
        String _expectedContract,
        String _actualContract,
        byte[] _expectedFunctionCall,
        byte[] _actualFunctionCall) {
      this.ver = GpactCrossControlManagerGroup.GpactVersion.V1;
      this._expectedBlockchainId = _expectedBlockchainId;
      this._actualBlockchainId = _actualBlockchainId;
      this._expectedContract = _expectedContract;
      this._actualContract = _actualContract;
      this._expectedFunctionCall = _expectedFunctionCall;
      this._actualFunctionCall = _actualFunctionCall;
    }

    public BadCallEventResponse(
        byte[] expectedFunctionCallHash,
        BigInteger _actualBlockchainId,
        String _actualContract,
        byte[] _actualFunctionCall) {
      this.ver = GpactCrossControlManagerGroup.GpactVersion.V2;
      this._expectedFunctionCallHash = expectedFunctionCallHash;
      this._actualBlockchainId = _actualBlockchainId;
      this._actualContract = _actualContract;
      this._actualFunctionCall = _actualFunctionCall;
    }
  }

  class CallFailureEventResponse extends BaseEventResponse {
    public String _revertReason;

    public CallFailureEventResponse(String _revertReason) {
      this._revertReason = _revertReason;
    }
  }

  class CallResultEventResponse extends BaseEventResponse {
    public BigInteger blockchainId;
    public String contract;
    public byte[] functionCall;
    public byte[] result;

    public CallResultEventResponse(
        BigInteger blockchainId, String contract, byte[] functionCall, byte[] result) {
      this.blockchainId = blockchainId;
      this.contract = contract;
      this.functionCall = functionCall;
      this.result = result;
    }
  }

  class DumpEventResponse extends BaseEventResponse {
    public BigInteger _val1;
    public byte[] _val2;
    public String _val3;
    public byte[] _val4;

    public DumpEventResponse(BigInteger _val1, byte[] _val2, String _val3, byte[] _val4) {
      this._val1 = _val1;
      this._val2 = _val2;
      this._val3 = _val3;
      this._val4 = _val4;
    }
  }

  class NotEnoughCallsEventResponse extends BaseEventResponse {
    public BigInteger _expectedNumberOfCalls;
    public BigInteger _actualNumberOfCalls;

    public NotEnoughCallsEventResponse(
        BigInteger _expectedNumberOfCalls, BigInteger _actualNumberOfCalls) {
      this._expectedNumberOfCalls = _expectedNumberOfCalls;
      this._actualNumberOfCalls = _actualNumberOfCalls;
    }
  }

  class RootEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public Boolean _success;

    public RootEventResponse(BigInteger _crossBlockchainTransactionId, Boolean _success) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._success = _success;
    }
  }

  class SegmentEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public byte[] _hashOfCallGraph;
    public List<BigInteger> _callPath;
    public List<String> _lockedContracts;
    public Boolean _success;
    public byte[] _returnValue;

    public SegmentEventResponse(
        BigInteger _crossBlockchainTransactionId,
        byte[] _hashOfCallGraph,
        List<BigInteger> _callPath,
        List<String> _lockedContracts,
        Boolean _success,
        byte[] _returnValue) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._hashOfCallGraph = _hashOfCallGraph;
      this._callPath = _callPath;
      this._lockedContracts = _lockedContracts;
      this._success = _success;
      this._returnValue = _returnValue;
    }
  }

  class SignallingEventResponse extends BaseEventResponse {
    public BigInteger _rootBcId;
    public BigInteger _crossBlockchainTransactionId;

    public SignallingEventResponse(BigInteger _rootBcId, BigInteger _crossBlockchainTransactionId) {
      this._rootBcId = _rootBcId;
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
    }
  }

  class StartEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public String _caller;
    public BigInteger _timeout;
    public byte[] _callGraph;

    public StartEventResponse(
        BigInteger _crossBlockchainTransactionId,
        String _caller,
        BigInteger _timeout,
        byte[] _callGraph) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._caller = _caller;
      this._timeout = _timeout;
      this._callGraph = _callGraph;
    }
  }
}
