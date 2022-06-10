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
package net.consensys.gpact.messaging.fake;

import net.consensys.gpact.messaging.eventattest.AttestorSignerManagerGroup;

/**
 * Manage contracts on multiple blockchains, each holding a set of registrar and verification
 * contracts.
 *
 * <p>The fake signer creates a real signature. This real signature can be verified using the event
 * attestation (attestor signer) contract. As such, this contract just inherits from the
 * AttestorSignerManagerGroup.
 */
public class FakeSignerManagerGroup extends AttestorSignerManagerGroup {}
