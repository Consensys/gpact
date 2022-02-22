package crypto

/*
 * Copyright 2022 ConsenSys Software Inc.
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

// Array of PRNGs that will be reseeded by the global quick reseed.
var prngPool []Random

// PRNGEntropyKick is called when an application event occurs that an attacker
// on a computer on the network can not observe. For example, when a message
// arrives, this function can be called. Attackers may know down to the milli
// or even micro second when a message is serviced. However, they are unlikely
// to know the timing down to the nearest nano second.
func PRNGEntropyKick() {
	for _, prng := range prngPool {
		prng.QuickReseedKick()
	}
}
