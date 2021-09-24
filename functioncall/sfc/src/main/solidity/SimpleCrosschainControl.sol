/*
 * Copyright 2021 ConsenSys Software Inc
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
pragma solidity >=0.8;

import "../../../../interface/src/main/solidity/CrosschainFunctionCallInterface.sol";


contract CrosschainControl is CrosschainFunctionCallInterface {


    event CrossCall(uint256 _sourceBcId, uint256 _txId, address _caller,
        uint256 _destBcId, address _destContract, bytes _destData);

    uint256 public myBlockchainId;


    constructor(uint256 _myBlockchainId) {
        myBlockchainId = _myBlockchainId;
    }


    function root(uint256[] calldata _blockchainIds, address[] calldata _cbcAddresses,
        bytes32[] calldata _eventFunctionSignatures, bytes[] calldata _eventData, bytes[] calldata _signatures) external {

        decodeAndVerifyEvents(_blockchainIds, _cbcAddresses, _eventFunctionSignatures, _eventData, _signatures, true);

        uint256 rootBcId = _blockchainIds[0];

        //Check that tx.origin == msg.sender. This call must be issued by an EOA. This is required so
        // that we can check that the tx.origin for this call matches what is in the start event.
        require(tx.origin == msg.sender, "Transaction must be instigated by an EOA");

        //Check that the blockchain Id that can be used with the transaction receipt for verification matches this
        // blockchain. That is, check that this is the root blockchain.
        require(myBlockchainId == rootBcId, "This is not the root blockchain");
        activeCallRootBlockchainId = rootBcId;

        // Ensure this is the crosschain control contract that generated the start event.
        require(address(this) == _cbcAddresses[0], "Root blockchain CBC contract was not this one");

        // Check that Cross-blockchain Transaction Id as shown in the Start Event is still active.
        uint256 crosschainTransactionId = BytesUtil.bytesToUint256(_eventData[0], 0);
        uint256 timeoutForCall = rootTransactionInformation[crosschainTransactionId];
        require(timeoutForCall != NOT_USED, "Call not active");
        require(timeoutForCall != SUCCESS, "Call ended (success)");
        require(timeoutForCall != FAILURE, "Call ended (failure)");

        //Check if the cross-blockchain transaction has timed-out.
        if (block.timestamp > timeoutForCall) {
            failRootTransaction(crosschainTransactionId);
            cleanupAfterCallRoot();
            return;
        }

        // Check that the tx.origin matches the account that submitted the Start transaction,
        // by checking the Start Event. Exit if it doesn’t.
        // This means that only the initiator of the cross-blockchain transaction can call this
        // function call prior to the time-out.
        address startCaller = BytesUtil.bytesToAddress1(_eventData[0], 0x20);
        require(startCaller == tx.origin, "EOA does not match start event");

        // Determine the hash of the call graph described in the start event. This is needed to check the segment
        // event information.
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(_eventData[0], 0x80);
        bytes memory callGraph = BytesUtil.slice(_eventData[0], 0xA0, lenOfActiveCallGraph);
        activeCallGraph = callGraph;
        bytes32 hashOfCallGraph = keccak256(callGraph);

        // The element will be the default, 0.
        uint256[] memory callPathForStart = new uint256[](1);
        activeCallsCallPath = callPathForStart;

        if (verifySegmentEvents(_eventData, callPathForStart, hashOfCallGraph, crosschainTransactionId)) {
            return;
        }

        // Set-up root blockchain / crosschain transaction value used for locking.
        // Store a copy in memory so that storage isn't read from in the code further below.
        bytes32 crosschainRootTxId = calcRootTxId(rootBcId, crosschainTransactionId);
        activeCallCrosschainRootTxId = crosschainRootTxId;

        bool isSuccess;
        (isSuccess, ) = makeCall(callGraph, callPathForStart);

        // Unlock contracts locked by the root transaction.
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            LockableStorageInterface lockableStorageContract = LockableStorageInterface(activeCallLockedContracts[i]);
            lockableStorageContract.finalise(isSuccess, crosschainRootTxId);
        }
        rootTransactionInformation[crosschainTransactionId] = isSuccess ? SUCCESS : FAILURE;
        emit Root(crosschainTransactionId, isSuccess);
        cleanupAfterCallRoot();
    }


    function crossBlockchainCall(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) external override {
        bool failed;
        bytes memory returnValue;
        (failed, returnValue) = commonCallProcessing(_blockchainId, _contract, _functionCallData);

        if (!failed) {
            if (!(compare(returnValue, bytes("")))) {
                emit CallFailure("Cross Blockchain Call with unexpected return values");
                activeCallFailed = true;
            }
        }
    }


    function crossBlockchainCallReturnsUint256(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) external override returns (uint256){
        revert("Not implemented");
    }

    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() public override view returns (bool) {
        return bytes32(0) == activeCallCrosschainRootTxId;
    }

    function whoCalledMe() external view override returns (uint256 rootBlockchainId, uint256 parentBlockchainId, address parentContract) {
        return (activeCallRootBlockchainId, activeCallParentBlockchainId, activeCallParentContract);
    }

    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************

    function makeCall(bytes memory _callGraph, uint256[] memory _callPath) private returns(bool, bytes memory) {
        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(_callGraph, _callPath, true);
        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = targetContract.call(functionCall);

        if (!isSuccess) {
            emit CallFailure(getRevertMsg(returnValueEncoded));
        }

        // Check that all cross-blockchain calls were used.
        // Do this even if the call has already failed: it will provide a bit
        // more debug information.
        if (activeCallReturnValues.length != activeCallReturnValuesIndex) {
            emit NotEnoughCalls(activeCallReturnValues.length, activeCallReturnValuesIndex);
            isSuccess = false;
        }

        // Fail if one of the called segments failed.
        isSuccess = activeCallFailed ? false : isSuccess;
        return (isSuccess, returnValueEncoded);
    }



    function prepareForWhoCalledMe(bytes memory _callGraph, uint256[] memory _callPath) private {
        uint256[] memory parentCallPath = determineParentCallPath(_callPath);
        (activeCallParentBlockchainId, activeCallParentContract, /* bytes memory parentFunctionCall */ ) =
            extractTargetFromCallGraph(_callGraph, parentCallPath, false);
    }




    function commonCallProcessing(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) private returns(bool, bytes memory) {
        // Fail if we have run out of return results.
        if (activeCallReturnValuesIndex >= activeCallReturnValues.length) {
            activeCallFailed = true;
            activeCallReturnValuesIndex++;
            return (true, bytes(""));
        }

        // Check that this function call should occur.
        // First create the call path to the next function that should execute.
        require(activeCallsCallPath.length != 0, "Active Calls call path length is zero");
        uint256[] memory callPath = new uint256[](activeCallsCallPath.length);
        for (uint i = 0; i < activeCallsCallPath.length - 1; i++) {
            callPath[i] = activeCallsCallPath[i];
        }
        callPath[callPath.length - 1] = activeCallReturnValuesIndex + 1;
        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(activeCallGraph, callPath, true);

        // Fail if what was called doesn't match what was expected to be called.
        if (_blockchainId != targetBlockchainId ||
            _contract != targetContract ||
            !compare(_functionCallData, functionCall)) {

            activeCallFailed = true;
            activeCallReturnValuesIndex++;
            emit BadCall(targetBlockchainId, _blockchainId, targetContract, _contract, functionCall, _functionCallData);
            return (true, bytes(""));
        }
        bytes memory retVal = activeCallReturnValues[activeCallReturnValuesIndex++];
        emit CallResult(_blockchainId, _contract, _functionCallData, retVal);
        return (false, retVal);
    }


    /**
     * Calculate the combined Root Blockchain and Crosschain Transaction Id. This
     * is used for calls from lockable storage.
     */
    function calcRootTxId(uint256 _rootBcId, uint256 _crossTxId) private pure returns (bytes32) {
        return keccak256(abi.encodePacked(_rootBcId, _crossTxId));
    }


    function getRevertMsg(bytes memory _returnData) internal pure returns (string memory) {
        // If the _res length is less than 68, then the transaction failed silently (without a revert message)
        if (_returnData.length < 68) return 'Transaction reverted silently';

        assembly {
        // Slice the sighash.
            _returnData := add(_returnData, 0x04)
        }
        return abi.decode(_returnData, (string)); // All that remains is the revert string
    }
}