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

import "./RegistrarInterface.sol";
import "./VotingAlgInterface.sol";
import "../../../../crypto/src/main/solidity/EcdsaSignatureVerification.sol";
import "../../../../common/src/main/solidity/ERC165MappingImplementation.sol";

contract Registrar is RegistrarInterface, EcdsaSignatureVerification, ERC165MappingImplementation {
    // Indications that a vote is underway.
    // VOTE_NONE indicates no vote is underway. Also matches the deleted value for integers.
    enum VoteType {
        VOTE_NONE,                            // 0: MUST be the first value so it is the zero / deleted value.
        VOTE_ADD_ADMIN,                       // 1
        VOTE_REMOVE_ADMIN,                    // 2
        VOTE_CHANGE_VOTING,                   // 3
        VOTE_ADD_BLOCKCHAIN,                  // 4
        VOTE_SET_SIGNING_THRESHOLD,           // 5
        VOTE_ADD_SIGNER,                      // 6
        VOTE_REMOVE_SIGNER,                   // 7
        VOTE_SET_FINALITY                     // 8
    }

    // Signature algorithm and curve.
    enum SigAlgorithm {
        ALG_NONE,                             // 0: MUST be the first value so it is the zero / deleted value.
        ALG_ECDSA_KECCAK256_SECP256K1         // 1
    }

    struct Votes {
        // The type of vote being voted on.
        VoteType voteType;
        // Additional information about what is being voted on.
        uint256 additionalInfo1;
        uint256 additionalInfo2;
        // The block number when voting will cease.
        uint endOfVotingBlockNumber;

        // Map of who has voted on the proposal.
        mapping(address=>bool) hasVoted;
        // The participants who voted for the proposal.
        address[] votedFor;
        // The participants who voted against the proposal.
        address[] votedAgainst;
    }

    struct BlockchainRecord {
        SigAlgorithm sigAlgorithm;
        uint64 signingThreshold;
        // An indication of the finality of the blockchain, in blocks.
        // Conforming relayers should not add transaction receipt roots to blocks that
        // are not final.
        uint64 finality;

        address approvedContract;

        // Number of active signers
        uint64 numSigners;
        // Address of accounts who sign and the offset in the array+1.
        mapping(address => uint256) signersMap;
        address[] signersArray;
    }

    mapping(uint256=>BlockchainRecord) private blockchains;

    // The algorithm for assessing the votes.
    address private votingAlgorithmContract;
    // Voting period in blocks. This is the period in which participants can vote. Must be greater than 0.
    uint64 private votingPeriod;

    // Number of active administrators.
    uint64 private numAdmins;
    // Address of accounts who administer this contract and the offset in the array+1.
    mapping(address => uint256) private adminsMap;
    address[] private adminsArray;

    // Votes for adding and removing participants, for changing voting algorithm and voting period.
    mapping(uint256=>Votes) private votes;


    /**
     * Function modifier to ensure only admins can call the function.
     *
     * @dev Throws if the message sender isn't an admin.
     */
    modifier onlyAdmin() {
        require(adminsMap[msg.sender] != 0);
        _;
    }



    constructor() {
        // Have msg.sender deploying this contract as an admin
        adminsArray.push(msg.sender);
        // The value is the offset into the array + 1.
        adminsMap[msg.sender] = 1;
        numAdmins = 1;

        supportedInterfaces[type(RegistrarInterface).interfaceId] = true;
    }




    function proposeVote(uint16 _action, uint256 _voteTarget, uint256 _additionalInfo1, uint256 _additionalInfo2) external override(RegistrarInterface) onlyAdmin() {
        // This will throw an error if the action is not a valid VoteType.
        VoteType action = VoteType(_action);

        // Can't start a vote if a vote is already underway.
        require(votes[_voteTarget].voteType == VoteType.VOTE_NONE);

        address targetAddr = address(_voteTarget);
        address additionalAddress = address(_additionalInfo1);

        if (action == VoteType.VOTE_ADD_ADMIN) {
            // If the action is to add an admin, then they shouldn't be an admin already.
            require(!isAdmin(targetAddr));
        }
        else if (action == VoteType.VOTE_REMOVE_ADMIN) {
            // If the action is to remove an admin, then they should be an admin already.
            require(isAdmin(targetAddr));
            // Don't allow admins to propose removing themselves. This means the case of removing
            // the only admin is avoided.
            require(targetAddr != msg.sender);
        }
        //else if (action == VoteType.VOTE_CHANGE_VOTING) {
            // Nothing to check
        //}
        else if (action == VoteType.VOTE_ADD_BLOCKCHAIN) {
            // Ensure the blockchain does not yet exist
            require(blockchains[_voteTarget].signingThreshold == 0);
            // Ensure the signature algorithm is the only one supported.
            SigAlgorithm sigAlg = SigAlgorithm(_additionalInfo2);
            require(sigAlg == SigAlgorithm.ALG_ECDSA_KECCAK256_SECP256K1);
        }
        else if (action == VoteType.VOTE_SET_SIGNING_THRESHOLD) {
            // Ensure the blockchain exists
            require(blockchains[_voteTarget].signingThreshold != 0);
        }
        else if (action == VoteType.VOTE_ADD_SIGNER) {
            // Ensure the blockchain exists
            require(blockchains[_voteTarget].signingThreshold != 0);
            // Can only add a signer if they aren't a signer yet
            require(blockchains[_voteTarget].signersMap[additionalAddress] == 0);
        }
        else if (action == VoteType.VOTE_REMOVE_SIGNER) {
            // Can only remove a signer if they are a signer
            require(blockchains[_voteTarget].signersMap[additionalAddress] != 0);
        }
        // Nothing to do for set finality

        // Set-up the vote.
        votes[_voteTarget].voteType = action;
        votes[_voteTarget].endOfVotingBlockNumber = block.number + votingPeriod;
        votes[_voteTarget].additionalInfo1 = _additionalInfo1;
        votes[_voteTarget].additionalInfo2 = _additionalInfo2;

        if (votingAlgorithmContract == address(0)) {
            // If there is no voting algorithm then all proposals are acted upon immediately.
            actionVotesNoChecks(_voteTarget, true);
        }
        else {
            // The proposer is deemed to be voting for the proposal.
            voteNoChecks(_action, _voteTarget, true);
        }
    }

    function vote(uint16 _action, uint256 _voteTarget, bool _voteFor) external override(RegistrarInterface) onlyAdmin() {
        // This will throw an error if the action is not a valid VoteType.
        VoteType action = VoteType(_action);

        // The type of vote must match what is currently being voted on.
        // Note that this will catch the case when someone is voting when there is no active vote.
        require(votes[_voteTarget].voteType == action);
        // Ensure the account has not voted yet.
        require(votes[_voteTarget].hasVoted[msg.sender] == false);

        // Check voting period has not expired.
        require(votes[_voteTarget].endOfVotingBlockNumber >= block.number);

        voteNoChecks(_action, _voteTarget, _voteFor);
    }

    function actionVotes(uint256 _voteTarget) external override(RegistrarInterface) onlyAdmin() {
        // If no vote is underway, then there is nothing to action.
        VoteType action = votes[_voteTarget].voteType;
        require(action != VoteType.VOTE_NONE);
        // Can only action vote after voting period has ended.
        require(votes[_voteTarget].endOfVotingBlockNumber < block.number);

        VotingAlgInterface voteAlg = VotingAlgInterface(votingAlgorithmContract);
        bool result = voteAlg.assess(
            numAdmins,
            votes[_voteTarget].votedFor,
            votes[_voteTarget].votedAgainst);
        emit VoteResult(uint16(action), _voteTarget, result);

        actionVotesNoChecks(_voteTarget, result);
    }


    function verify(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes calldata _plainText) external view override(RegistrarInterface) returns (bool){

        uint256 signersLength = _signers.length;
        require(signersLength == _sigR.length, "sigR length mismatch");
        require(signersLength == _sigS.length, "sigS length mismatch");
        require(signersLength == _sigV.length, "sigV length mismatch");

        require(signersLength >= blockchains[_blockchainId].signingThreshold, "Not enough signers");

        for (uint256 i=0; i<signersLength; i++) {
            // Check that signer is a signer for this blockchain
            require(blockchains[_blockchainId].signersMap[_signers[i]] != 0, "Signer not signer for this blockchain");
            // Verify the signature
            require(verifySigComponents(_signers[i], _plainText, _sigR[i], _sigS[i], _sigV[i]), "Signature did not verify");
        }
        return true;
    }

    function verifyContract(
        uint256 _blockchainId,
        address _emittingContract) external view override(RegistrarInterface) returns (bool){
        require(_emittingContract == blockchains[_blockchainId].approvedContract, "Data not emitted by approved contract");
        return true;
    }



    function adminArraySize() external view override(RegistrarInterface) returns (uint256) {
        return adminsArray.length;
    }

    function getAdmin(uint256 _index) external view override(RegistrarInterface) returns (address)  {
        return adminsArray[_index];
    }

    function isAdmin(address _mightBeAdmin) public view override(RegistrarInterface) returns (bool)  {
        return adminsMap[_mightBeAdmin] != 0;
    }

    function getNumAdmins() external view override(RegistrarInterface) returns (uint64) {
        return numAdmins;
    }

    function getSigAlgorithm(uint256 _blockchainId) external view override(RegistrarInterface) returns (uint256) {
        return uint256(blockchains[_blockchainId].sigAlgorithm);
    }

    function getSigningThreshold(uint256 _blockchainId) external view override(RegistrarInterface) returns (uint64) {
        return blockchains[_blockchainId].signingThreshold;
    }

    function numSigners(uint256 _blockchainId) external view override(RegistrarInterface) returns (uint64) {
        return blockchains[_blockchainId].numSigners;
    }

    function isSigner(uint256 _blockchainId, address _mightBeSigner) external view override(RegistrarInterface) returns (bool) {
        return blockchains[_blockchainId].signersMap[_mightBeSigner] != 0;
    }

    function getChainFinality(uint256 _blockchainId) external view override(RegistrarInterface) returns (uint64) {
        return blockchains[_blockchainId].finality;
    }

    function getApprovedContract(uint256 _blockchainId) external view override(RegistrarInterface) returns (address) {
        return blockchains[_blockchainId].approvedContract;
    }

    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************

    /**
     * This function is used to indicate that an admin has voted. It has been created so that
     * calls to proposeVote do not have to incur all of the value checking in the vote call.
     *
     */
    function voteNoChecks(uint16 _action, uint256 _voteTarget, bool _voteFor) private {
        // Indicate msg.sender has voted.
        emit Voted(msg.sender, _action, _voteTarget, _voteFor);
        votes[_voteTarget].hasVoted[msg.sender] = true;

        if (_voteFor) {
            votes[_voteTarget].votedFor.push(msg.sender);
        } else {
            votes[_voteTarget].votedAgainst.push(msg.sender);
        }
    }


    function actionVotesNoChecks(uint256 _voteTarget, bool _result) private {
        if (_result) {
            // The vote has been decided in the affirmative.
            VoteType action = votes[_voteTarget].voteType;
            uint256 additionalInfo1 = votes[_voteTarget].additionalInfo1;
            uint256 additionalInfo2 = votes[_voteTarget].additionalInfo2;
            address addr1 = address(_voteTarget);
            address addr2 = address(additionalInfo1);
            if (action == VoteType.VOTE_ADD_ADMIN) {
                adminsArray.push(addr1);
                adminsMap[addr1] = adminsArray.length;
                numAdmins++;
            }
            else if (action == VoteType.VOTE_REMOVE_ADMIN) {
                uint256 arrayOfsPlusOne = adminsMap[addr1];
                delete adminsArray[arrayOfsPlusOne-1];
                delete adminsMap[addr1];
                numAdmins--;
            }
            else if (action == VoteType.VOTE_CHANGE_VOTING) {
                votingAlgorithmContract = addr1;
                votingPeriod = uint64(additionalInfo1);
            }
            else if (action == VoteType.VOTE_ADD_BLOCKCHAIN) {
                blockchains[_voteTarget].approvedContract = addr2;
                blockchains[_voteTarget].sigAlgorithm = SigAlgorithm(additionalInfo2);
                blockchains[_voteTarget].signingThreshold = 1;
            }
            else if (action == VoteType.VOTE_SET_SIGNING_THRESHOLD) {
                blockchains[_voteTarget].signingThreshold = uint64(additionalInfo1);
            }
            else if (action == VoteType.VOTE_ADD_SIGNER) {
                blockchains[_voteTarget].signersArray.push(addr2);
                blockchains[_voteTarget].signersMap[addr2] = blockchains[_voteTarget].signersArray.length;
                blockchains[_voteTarget].numSigners++;
            }
            else if (action == VoteType.VOTE_REMOVE_SIGNER) {
                uint256 arrayOfsPlusOne = blockchains[_voteTarget].signersMap[addr2];
                delete blockchains[_voteTarget].signersArray[arrayOfsPlusOne-1];
                delete blockchains[_voteTarget].signersMap[addr2];
                blockchains[_voteTarget].numSigners--;
            }
            else if (action == VoteType.VOTE_SET_FINALITY) {
                blockchains[_voteTarget].finality = uint64(additionalInfo1);
            }
        }


        // The vote is over. Now delete the voting arrays and indicate there is no vote underway.
        // Remove all values from the map: Maps can't be deleted in Solidity.
        // NOTE: The code below has used values directly, rather than a local variable due to running
        // out of local variables.
        for (uint i = 0; i < adminsArray.length; i++) {
            if (adminsArray[i] != address(0)) {
                delete votes[_voteTarget].hasVoted[adminsArray[i]];
                delete votes[_voteTarget].votedFor;
                delete votes[_voteTarget].votedAgainst;
            }
        }
        // This will recursively delete everything in the structure, except for the map, which was
        // deleted in the for loop above.
        delete votes[_voteTarget];
    }
}