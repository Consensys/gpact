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
pragma solidity >=0.7.1;
pragma experimental ABIEncoderV2;

import "./CbcDecVer.sol";
import "./CallPathCallExecutionTree.sol";
import "../../../../interface/src/main/solidity/LockableStorageInterface.sol";
import "../../../../interface/src/main/solidity/CrosschainLockingInterface.sol";
import "../../../../interface/src/main/solidity/CrosschainFunctionCallReturnInterface.sol";


contract CrosschainControl is CrosschainFunctionCallReturnInterface, CbcDecVer, CallPathCallExecutionTree,
    CrosschainLockingInterface {


    event Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes _callGraph);
    event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
        address[] _lockedContracts, bool _success, bytes _returnValue);
    event Root(uint256 _crossBlockchainTransactionId, bool _success);
    event Signalling(uint256 _rootBcId, uint256 _crossBlockchainTransactionId);

    event BadCall(
        uint256 _expectedBlockchainId, uint256 _actualBlockchainId,
        address _expectedContract, address _actualContract,
        bytes _expectedFunctionCall, bytes _actualFunctionCall);
    event CallResult(uint256 _blockchainId, address _contract, bytes _functionCall, bytes _result);
    event NotEnoughCalls(uint256 _expectedNumberOfCalls, uint256 _actualNumberOfCalls);
    event CallFailure(string _revertReason);

    event Dump(uint256 _val1, bytes32 _val2, address _val3, bytes _val4);

    uint256 public myBlockchainId;

    // For Root Blockchain:
    // Mapping of cross-blockchain transaction id to transaction information.
    // 0: Never used.
    // 1: The transaction has completed and was successful.
    // 2: The transaction has completed and was not successful.
    // Otherwise: time-out for the transaction: as seconds since unix epoch.
    uint256 constant private NOT_USED = 0;
    uint256 constant private SUCCESS = 1;
    uint256 constant private FAILURE = 2;
    mapping (uint256=> uint256) public rootTransactionInformation;

    // Used to prevent replay attacks in transaction segments.
    // Mapping of keccak256(abi.encodePacked(root blockchain id, transaction id, callpath)
    mapping (bytes32=> bool) public segmentTransactionExecuted;


    // Storage variables that are stored for the life of a transaction. They need to
    // be available in storage as code calls back into this contract.
    // -----------------------------------------------------------------------------
    // The root blockchain for the currently executing cross-blockchain call. Used to detect if there is a
    // cross-blockchain call occurring and to determine the root blockchain id for the call locking a
    // contract.
    uint256 public activeCallRootBlockchainId;

    // Combination of root blockchain id and crosschain transaction id of the
    // currently executing crosschain call. Used for locking. Provisional
    // values are stored against this id.
    bytes32 public activeCallCrosschainRootTxId;

    // The call graph currently being executed. This is needed to determine whether any
    // cross-blockchain calls from the current execution are valid.
    bytes public activeCallGraph;

    // The location within the call graph of the currently executing call segment.
    uint256[] private activeCallsCallPath;

    // The values to be returned to function calls in the currently executing call segment.
    bytes[] private activeCallReturnValues;

    // The index into the activeCallReturnValues. That is, which return value should be returned
    // to the next function call from the currently executing call segment.
    uint256 private activeCallReturnValuesIndex;

    // The set of contracts locked by the current call segment.
    // List of locked contracts.
    address[] private activeCallLockedContracts;

    // Indicates the current call segment has failed.
    bool private activeCallFailed;

    // Contract that called the target function.
    uint256 private activeCallParentBlockchainId;
    address private activeCallParentContract;

    constructor(uint256 _myBlockchainId) {
        myBlockchainId = _myBlockchainId;
    }


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external {
        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        require(rootTransactionInformation[_crossBlockchainTransactionId] == NOT_USED, "Transaction already registered");
        uint256 transactionTimeoutSeconds = _timeout + block.timestamp;
        rootTransactionInformation[_crossBlockchainTransactionId] = transactionTimeoutSeconds;

        emit Start(_crossBlockchainTransactionId, msg.sender, transactionTimeoutSeconds, _callGraph);
    }


    function segment(uint256[] calldata _blockchainIds, address[] calldata _cbcAddresses,
            bytes32[] calldata _eventFunctionSignatures, bytes[] calldata _eventData, bytes[] calldata _signatures, uint256[] calldata _callPath) external {

        decodeAndVerifyEvents(_blockchainIds, _cbcAddresses, _eventFunctionSignatures, _eventData, _signatures, true);

        uint256 rootBcId = _blockchainIds[0];

        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Segment must be called from an EOA");

        // Event 0 will be the start event
        // Recall the start event is:
        // Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes _callGraph);
        uint256 crosschainTransactionId;
        address startCaller;
        //uint256 timeout;
        bytes memory callGraph;
        (crosschainTransactionId, startCaller, , callGraph) =
            abi.decode(_eventData[0], (uint256, address, uint256, bytes));
        require(startCaller == tx.origin, "EOA does not match start event");

        // Stop replay of transaction segments.
        {
            bytes32 mapKey = keccak256(abi.encodePacked(rootBcId, crosschainTransactionId, _callPath));
            require(segmentTransactionExecuted[mapKey] == false, "Segment transaction already executed");
            segmentTransactionExecuted[mapKey] == true;
        }
        bytes32 hashOfCallGraph = keccak256(callGraph);

        activeCallRootBlockchainId = rootBcId;

        // No need to store call graph or call path if this is a leaf segment / function
        if (_eventData.length > 1) {
            activeCallGraph = callGraph;
            activeCallsCallPath = _callPath;
            if (verifySegmentEvents(_eventData, _callPath, hashOfCallGraph, crosschainTransactionId)) {
                return;
            }
        }


        // Set-up root blockchain / crosschain transaction value used for locking.
        activeCallCrosschainRootTxId = calcRootTxId(rootBcId, crosschainTransactionId);
        // Set-up values to be returned if whoCalledMe is called
        prepareForWhoCalledMe(callGraph, _callPath);

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = makeCall(callGraph, _callPath);

        // TODO emit segments understanding of root blockhain id
        emit Segment(crosschainTransactionId, hashOfCallGraph, _callPath, activeCallLockedContracts, isSuccess, returnValueEncoded);

        // Only delete locations used.
        if (_eventData.length > 1) {
            cleanupAfterCallSegment();
        }
        else {
            cleanupAfterCallLeafSegment();
        }
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


        // Event 0 will be the start event
        // Recall the start event is:
        // Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes _callGraph);
        uint256 crosschainTransactionId;
        address startCaller;
        uint256 timeout;
        bytes memory callGraph;
        (crosschainTransactionId, startCaller, timeout, callGraph) =
        abi.decode(_eventData[0], (uint256, address, uint256, bytes));

        // Check that Cross-blockchain Transaction Id as shown in the Start Event is still active.
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
        require(startCaller == tx.origin, "EOA does not match start event");

        // Determine the hash of the call graph described in the start event. This is needed to check the segment
        // event information.
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


    /**
     * Signalling call: Commit or ignore updates + unlock any locked contracts.
     **/
    function signalling(uint256[] calldata _blockchainIds, address[] calldata _cbcAddresses,
        bytes32[] calldata _eventFunctionSignatures, bytes[] calldata _eventData, bytes[] calldata _signatures) external {

        decodeAndVerifyEvents(_blockchainIds, _cbcAddresses, _eventFunctionSignatures, _eventData, _signatures, false);

        // Extract information from the root event.
        // Recall: Root(uint256 _crossBlockchainTransactionId, bool _success);
        uint256 rootEventCrossBlockchainTxId;
        bool success;
        (rootEventCrossBlockchainTxId, success) = abi.decode(_eventData[0], (uint256, bool));

        // Set-up root blockchain / crosschain transaction value for unlocking.
        bytes32 crosschainRootTxId = calcRootTxId(_blockchainIds[0], rootEventCrossBlockchainTxId);

        // Check that all of the Segment Events are for the same transaction id, and are for this blockchain.
        for (uint256 i = 1; i < _eventData.length; i++) {
            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 segmentEventCrossBlockchainTxId = BytesUtil.bytesToUint256(_eventData[i], 0);
            // Check that the cross blockchain transaction id is the same for the root and the segment event.
            require(rootEventCrossBlockchainTxId == segmentEventCrossBlockchainTxId);
            // TODO check the Root Blockchain id indicated in the segment matches the one from the root transaction.


            // TODO Check that the cross chain transaction id for the root blockchain id is in use on this blockchain but has not been added to the completed list.

            // For each address indicated in the Segment Event as being locked, Commit or Ignore updates
            // according to what the Root Event says.
            uint256 locationOfLockedContracts = BytesUtil.bytesToUint256(_eventData[i], 0x60);
//            emit Dump(locationOfLockedContracts, bytes32(0), address(0), _segmentEvents[i]);
            uint256 numElementsOfArray = BytesUtil.bytesToUint256(_eventData[i], locationOfLockedContracts);
            //emit Dump(numElementsOfArray, bytes32(0), address(0), _segmentEvents[i]);
            for (uint256 j = 0; j < numElementsOfArray; j++) {
                address lockedContractAddr = BytesUtil.bytesToAddress1(_eventData[i], locationOfLockedContracts + 0x20 + 0x20 * j);
                //emit Dump(i * 100 + j, bytes32(0), lockedContractAddr, bytes(""));
                LockableStorageInterface lockedContract = LockableStorageInterface(lockedContractAddr);
                lockedContract.finalise(success, crosschainRootTxId);
            }
        }

        emit Signalling(_blockchainIds[0], rootEventCrossBlockchainTxId);
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
        bool failed;
        bytes memory returnValue;
        (failed, returnValue) = commonCallProcessing(_blockchainId, _contract, _functionCallData);
        if (failed) {
            return uint256(0);
        }
        return BytesUtil.bytesToUint256(returnValue, 0);
    }

    /**
     * Items in contracts are locked, but not contracts themselves. However, we only
     * need to note which contracts are being locked for the purposes of Segment
     * transactions / events.
     */
    function addToListOfLockedContracts(address _contractToLock) external override {
        // Don't add the same contract twice. So, check the contract isn't in
        // the array first.
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            if (activeCallLockedContracts[i] == _contractToLock) {
                return;
            }
        }
        activeCallLockedContracts.push(_contractToLock);
    }


    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() public override view returns (bool) {
        return bytes32(0) == activeCallCrosschainRootTxId;
    }

    function getActiveCallCrosschainRootTxId() public override view returns (bytes32) {
        return activeCallCrosschainRootTxId;
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


//    function extractTargetFromCallGraph(bytes memory _callGraph, uint256[] memory _callPath) private pure
//        returns (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) {
//
//        RLP.RLPItem[] memory functions = RLP.toList(RLP.toRLPItem(_callGraph));
//
//        for (uint i=0; i < _callPath.length - 1; i++) {
//            functions = RLP.toList(functions[_callPath[i]]);
//        }
//        RLP.RLPItem[] memory func = RLP.toList(functions[_callPath[_callPath.length - 1]]);
//        if (RLP.isList(func[0])) {
//            func = RLP.toList(func[0]);
//        }
//        targetBlockchainId = RLP.toUint(func[0]);
//        targetContract = RLP.toAddress(func[1]);
//        functionCall = RLP.toData(func[2]);
//    }

    function prepareForWhoCalledMe(bytes memory _callGraph, uint256[] memory _callPath) private {
        uint256[] memory parentCallPath = determineParentCallPath(_callPath);
        (activeCallParentBlockchainId, activeCallParentContract, /* bytes memory parentFunctionCall */ ) =
            extractTargetFromCallGraph(_callGraph, parentCallPath, false);
    }


//    function determineParentCallPath(uint256[] memory _callPath) private pure returns (uint256[] memory) {
//        uint256[] memory callPathOfParent;
//        uint256 callPathLen = _callPath.length;
//
//        // Don't call from root function
//        assert(!(callPathLen == 1 && _callPath[0] == 0));
//
//        if (_callPath[callPathLen - 1] != 0) {
//            callPathOfParent = new uint256[](callPathLen);
//            for (uint256 i = 0; i < callPathLen - 1; i++) {
//                callPathOfParent[i] = _callPath[i];
//            }
//            callPathOfParent[callPathLen - 1] = 0;
//        }
//        else {
//            callPathOfParent = new uint256[](callPathLen - 1);
//            for (uint256 i = 0; i < callPathLen - 2; i++) {
//                callPathOfParent[i] = _callPath[i];
//            }
//            callPathOfParent[callPathLen - 2] = 0;
//        }
//        return callPathOfParent;
//    }


    function failRootTransaction(uint256 _crosschainTxId) private {
        rootTransactionInformation[_crosschainTxId] = FAILURE;
        emit Root(_crosschainTxId, false);
    }


    /**
     * Clean-up temporary storage after a Segment or Root call.
     * The three similar functions below only delete storage locations
     * that could have been set given the scenario.
     */
    function cleanupAfterCallSegment() private {
        delete activeCallGraph;
        delete activeCallsCallPath;
        delete activeCallCrosschainRootTxId;
        delete activeCallLockedContracts;
        delete activeCallReturnValues;
        delete activeCallReturnValuesIndex;

        // Indicates a failure happened in a call out to another segment.
        if (activeCallFailed) {
            // Save gas by only writing to the location is it is not the default value.
            delete activeCallFailed;
        }

        // Used in whoCalledMe
        delete activeCallParentBlockchainId;
        delete activeCallParentContract;
        delete activeCallRootBlockchainId;
    }
    function cleanupAfterCallLeafSegment() private {
        // Indicates a failure happened in a call out to another segment. This
        // should never happen for a leaf segment. However, it would be set
        // if a segment that was supposed to be a "leaf segment" called out
        // to another blockchain.
        if (activeCallFailed) {
            // Save gas by only writing to the location is it is not the default value.
            delete activeCallFailed;
        }

        // Used for contract locking
        delete activeCallCrosschainRootTxId;
        delete activeCallLockedContracts;

        // Used in whoCalledMe
        delete activeCallRootBlockchainId;
        delete activeCallParentBlockchainId;
        delete activeCallParentContract;

        // Not used by leaf calls:
        //        delete activeCallGraph;
        //        delete activeCallsCallPath;
        //        delete activeCallReturnValues;
        //        delete activeCallReturnValuesIndex;
    }

    function cleanupAfterCallRoot() private {
        // Used in whoCalledMe
        delete activeCallRootBlockchainId;

        // Used for calling segments
        delete activeCallGraph;
        delete activeCallsCallPath;
        delete activeCallReturnValues;
        delete activeCallReturnValuesIndex;

        // Indicates a failure happened in a call out to another segment.
        if (activeCallFailed) {
            // Save gas by only writing to the location is it is not the default value.
            delete activeCallFailed;
        }

        // Used for contract locking
        delete activeCallCrosschainRootTxId;
        delete activeCallLockedContracts;

        // Not used by root calls:
        //        delete activeCallParentBlockchainId;
        //        delete activeCallParentContract;
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

    function verifySegmentEvents(bytes[] memory _segmentEvents, uint256[] memory callPath, bytes32 hashOfCallGraph, uint256 _crosschainTxId) private returns(bool){
        //Verify the event information in the Segment Events.
        // Check that the Segment Events correspond to function calls that the Start Event indicates that the
        //  root function call should have been called.
        // Exit if the information doesn’t match.
        //If any of the Segment Events indicate an error, Goto Ignore.
        for (uint256 i = 1; i < _segmentEvents.length; i++) {
            bytes memory segmentEvent = _segmentEvents[i];

            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 crossBlockchainTxId = BytesUtil.bytesToUint256(segmentEvent, 0);
            bytes32 hashOfCallGraphFromSegment = BytesUtil.bytesToBytes32(segmentEvent, 0x20);
            uint256 locationOfCallPath = BytesUtil.bytesToUint256(segmentEvent, 0x40);
            // Not needed: uint256 locationOfLockedContracts = BytesUtil.bytesToUint256(segmentEvent, 0x60);
            uint256 success = BytesUtil.bytesToUint256(segmentEvent, 0x80);
            uint256 locationOfReturnValue = BytesUtil.bytesToUint256(segmentEvent, 0xA0);
            uint256 lenOfReturnValue = BytesUtil.bytesToUint256(segmentEvent, locationOfReturnValue);
            bytes memory returnValue = BytesUtil.slice(segmentEvent, locationOfReturnValue + 0x20, lenOfReturnValue);
            uint256 lenOfCallPath = BytesUtil.bytesToUint256(segmentEvent, locationOfCallPath);

            require(crossBlockchainTxId == _crosschainTxId, "Transaction id from segment and root do not match");
            require(hashOfCallGraph == hashOfCallGraphFromSegment, "Call graph from segment and root do not match");

            // Segments with offset zero of Root calls are the only ones that can call segments.
            require(callPath[callPath.length - 1] == 0);
            require(lenOfCallPath == callPath.length || lenOfCallPath == callPath.length+1, "Bad call path length for segment");
            // TODO
//            if (lenOfCallPath == callPath.length+1) {
//                uint256 callPathFinalValue = BytesUtil.bytesToUint256(segmentEvent, locationOfCallPath + 0x20 * (lenOfCallPath-1));
//                require(callPathFinalValue == 0, "Call path optional second element not zero");
//            }

            // Fail the root transaction is one of the segments failed.
            if (success == 0) {
                failRootTransaction(_crosschainTxId);
                cleanupAfterCallSegment();
                return true;
            }

            // Store the extracted return results from segment events.
            activeCallReturnValues.push(returnValue);
        }
        return false;
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