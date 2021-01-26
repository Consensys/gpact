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
pragma solidity >=0.7.1;

import "./VotingAlgInterface.sol";


/**
* The vote is assessed based on those participants who voted.
*/
contract VotingAlgMajorityWhoVoted is VotingAlgInterface {

    function assess(uint64 /* numParticipants */, address[] memory votedFor, address[] memory votedAgainst) external pure override(VotingAlgInterface) returns (bool) {
        return (votedFor.length > votedAgainst.length);
    }
}