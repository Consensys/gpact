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
package net.consensys.gpact.functioncall;

import java.util.HashMap;
import java.util.Map;
import net.consensys.gpact.functioncall.gpact.GpactCrossControlManagerGroup;
import net.consensys.gpact.functioncall.sfc.SimpleCrossControlManagerGroup;

public class CrosschainFunctionCallFactory {
  public static final String GPACT = "GPACT";
  public static final String SFC = "SFC";

  public static final String SERIAL = "SERIAL";
  public static final String PARALLEL = "PARALLEL";

  private static final Map<String, Class> impls;

  static {
    impls = new HashMap<>();
    registerImpl(GPACT, GpactCrossControlManagerGroup.class);
    registerImpl(SFC, SimpleCrossControlManagerGroup.class);
  }

  /**
   * Register a crosschain function call protocol implementation. This method can be used to update
   * the implementation of a protocol by passing in a name that is associated with an existing
   * implementation.
   *
   * @param name Name to associate with an implementation.
   * @param implClass Protocol implementation class that implements CrossControlManagerGroup.
   */
  public static void registerImpl(final String name, final Class implClass) {
    if (implClass == null) {
      throw new IllegalArgumentException("Attempted to register a null implementation");
    }
    impls.put(name, implClass);
  }

  /**
   * Create an instance of a crosschain function call implementation.
   *
   * @param implementationName Name of the implementation that must have been previously registered.
   * @return Implementation of CrosschainFunctionCall
   * @throws Exception Typically thrown if the implementation has not been registered yet.
   */
  public static CrossControlManagerGroup getInstance(final String implementationName)
      throws Exception {
    final Class<CrossControlManagerGroup> clazz = impls.get(implementationName);
    return clazz.getDeclaredConstructor().newInstance();
  }
}
