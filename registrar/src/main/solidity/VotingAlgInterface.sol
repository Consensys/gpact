/*
 * Copyright 2018 ConsenSys AG.
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
pragma solidity >=0.7.1;


/*
* Interface which all voting algorithms must implement.
*/
interface VotingAlgInterface {
    /**
     * Assess a vote.
     *
     * @param numParticipants Total number of participants.
     * @param votedFor     Array of participants who voted for the proposal.
     * @param votedAgainst Array of participants who voted against the proposal.
     * @return true if the result of the vote was true. That is, given the voting algorithm
     *              the result of the vote is for what was being voted on.
     *         false if the result of the vote was against the proposal
     */
    function assess(uint64 numParticipants, address[] memory votedFor, address[] memory votedAgainst) external pure returns (bool);
}