/*
 * Copyright 2022 ConsenSys Software Inc
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
package net.consensys.gpact;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.consensys.gpact.functioncall.CrossControlManagerGroup;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.functioncall.sfc.SimpleCrossControlManagerGroup;
import net.consensys.gpact.messaging.MessagingManagerGroup;
import net.consensys.gpact.messaging.eventattest.AttestorSignerManagerGroup;
import net.consensys.gpact.messaging.eventrelay.EventRelayManagerGroup;
import net.consensys.gpact.messaging.txrootrelay.TxRootTransferManagerGroup;

/** Entry point class for the Crosschian Protocol Stack SDK. */
public class CrosschainProtocols {
  public static final String GPACT = "GPACT";
  public static final String SFC = "SFC";

  public static final String EVENTRELAY = "EVENTRELAY";
  public static final String ATTESTOR = "ATTESTOR";
  public static final String TXROOT = "TXROOT";

  // Execution engine tpe
  public static final String SERIAL = "SERIAL";
  public static final String PARALLEL = "PARALLEL";

  private static final Map<String, Class<? extends CrossControlManagerGroup>> functionCallImpls;

  private static final Map<String, Class<? extends MessagingManagerGroup>> messagingImpls;

  static {
    functionCallImpls = new HashMap<>();
    registerFunctionCallImpl(GPACT, GpactCrossControlManagerGroup.class);
    registerFunctionCallImpl(SFC, SimpleCrossControlManagerGroup.class);
    messagingImpls = new HashMap<>();
    registerMessagingImpl(EVENTRELAY, EventRelayManagerGroup.class);
    registerMessagingImpl(ATTESTOR, AttestorSignerManagerGroup.class);
    registerMessagingImpl(TXROOT, TxRootTransferManagerGroup.class);
  }

  /**
   * Register a crosschain function call protocol implementation. This method can be used to update
   * the implementation of a protocol by passing in a name that is associated with an existing
   * implementation.
   *
   * @param name Name to associate with an implementation.
   * @param implClass Protocol implementation class that implements CrossControlManagerGroup.
   */
  public static void registerFunctionCallImpl(
      final String name, final Class<? extends CrossControlManagerGroup> implClass) {
    if (implClass == null) {
      throw new IllegalArgumentException("Attempted to register a null implementation");
    }
    if (name == null) {
      throw new IllegalArgumentException(
          "Attempted to register an implementation with a null name");
    }
    if (name.length() == 0) {
      throw new IllegalArgumentException(
          "Attempted to register an implementation with a zero length name");
    }
    functionCallImpls.put(name, implClass);
  }

  /**
   * Register a crosschain messaging protocol implementation. This method can be used to update the
   * implementation of a protocol by passing in a name that is associated with an existing
   * implementation.
   *
   * @param name Name to associate with an implementation.
   * @param implClass Protocol implementation class that implements MessagingManagerGroup.
   */
  public static void registerMessagingImpl(
      final String name, final Class<? extends MessagingManagerGroup> implClass) {
    if (implClass == null) {
      throw new IllegalArgumentException("Attempted to register a null implementation");
    }
    if (name == null) {
      throw new IllegalArgumentException(
          "Attempted to register an implementation with a null name");
    }
    if (name.length() == 0) {
      throw new IllegalArgumentException(
          "Attempted to register an implementation with a zero length name");
    }
    messagingImpls.put(name, implClass);
  }

  /**
   * Create an instance of a crosschain function call implementation.
   *
   * @param implementationName Name of the implementation that must have been previously registered.
   * @return Implementation of CrosschainFunctionCall
   * @throws Exception Typically thrown if the implementation has not been registered yet.
   */
  public static Optional<CrossControlManagerGroup> getFunctionCallInstance(
      final String implementationName) throws Exception {
    final Class<? extends CrossControlManagerGroup> clazz =
        functionCallImpls.get(implementationName);
    if (clazz == null) {
      return Optional.empty();
    }
    return Optional.of(clazz.getDeclaredConstructor().newInstance());
  }

  /**
   * Create an instance of a crosschain function call implementation.
   *
   * @param implementationName Name of the implementation that must have been previously registered.
   * @return Implementation of CrosschainFunctionCall
   * @throws Exception Typically thrown if the implementation has not been registered yet.
   */
  public static Optional<MessagingManagerGroup> getMessagingInstance(
      final String implementationName) throws Exception {
    final Class<? extends MessagingManagerGroup> clazz = messagingImpls.get(implementationName);
    if (clazz == null) {
      return Optional.empty();
    }
    return Optional.of(clazz.getDeclaredConstructor().newInstance());
  }
}
