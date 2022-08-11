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
pragma solidity >=0.8;

import "../common/CbcDecVer.sol";
import "./CallExecutionTreeV2.sol";
import "../interface/LockableStorageInterface.sol";
import "../interface/CrosschainLockingInterface.sol";
import "../interface/CrosschainFunctionCallReturnInterface.sol";
import "../interface/AtomicHiddenAuthParameters.sol";
import "../../common/ResponseProcessUtil.sol";
import "../common/CallPath.sol";

contract GpactV2CrosschainControl is
    CrosschainFunctionCallReturnInterface,
    CbcDecVer,
    CallExecutionTreeV2,
    CallPath,
    CrosschainLockingInterface,
    AtomicHiddenAuthParameters,
    ResponseProcessUtil
{
    // 	0x77dab611
    bytes32 internal constant START_EVENT_SIGNATURE =
        keccak256("Start(uint256,address,uint256,bytes32)");
    event Start(
        uint256 _crossBlockchainTransactionId,
        address _caller,
        uint256 _timeout,
        bytes32 _callTreeHash
    );
    bytes32 internal constant SEGMENT_EVENT_SIGNATURE =
        keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");
    event Segment(
        uint256 _crossBlockchainTransactionId,
        bytes32 _hashOfCallGraph,
        uint256[] _callPath,
        address[] _lockedContracts,
        bool _success,
        bytes _returnValue
    );
    bytes32 internal constant ROOT_EVENT_SIGNATURE =
        keccak256("Root(uint256,bool)");
    event Root(uint256 _crossBlockchainTransactionId, bool _success);
    event Signalling(uint256 _rootBcId, uint256 _crossBlockchainTransactionId);

    event BadCall(
        bytes32 _expectedFunctionHash,
        uint256 _actualBlockchainId,
        address _actualContract,
        bytes _actualFunctionCall
    );
    event CallResult(
        uint256 _blockchainId,
        address _contract,
        bytes _functionCall,
        bytes _result
    );
    event NotEnoughCalls(
        uint256 _expectedNumberOfCalls,
        uint256 _actualNumberOfCalls
    );
    event CallFailure(string _revertReason);

    event Dump(uint256 _val1, bytes32 _val2, address _val3, bytes _val4);

    struct EventInfo {
        // Blockchain ids that events were emitted on.
        uint256 blockchainId;
        // Crosschain control contracts that event was emitted from.
        address cbcAddress;
        // Event function signature of emitted event.
        bytes32 eventFunctionSignature;
        // Event data for emitted event.
        bytes eventData;
        // Encoded signatures or proofs proving that the blockchain id, cbc address, and event data can be trusted.
        bytes signatures;
    }

    // The entry point function that called this blockchain
    struct Caller {
        uint256 blockchainId;
        address addr;
        bytes32 funcCallHash;
    }

    struct Called {
        address addr;
        bytes funcCallData;
    }

    uint256 public myBlockchainId;

    // For Root Blockchain:
    // Mapping of cross-blockchain transaction id to transaction information.
    // 0: Never used.
    // 1: The transaction has completed and was successful.
    // 2: The transaction has completed and was not successful.
    // Otherwise: time-out for the transaction: as seconds since unix epoch.
    uint256 private constant NOT_USED = 0;
    uint256 private constant SUCCESS = 1;
    uint256 private constant FAILURE = 2;
    mapping(uint256 => uint256) public rootTransactionInformation;

    // Used to prevent replay attacks in transaction segments.
    // Mapping of keccak256(abi.encodePacked(root blockchain id, transaction id, callpath)
    mapping(bytes32 => bool) public segmentTransactionExecuted;

    // Storage variables that are stored for the life of a transaction. They need to
    // be available in storage as code calls back into this contract.
    // -----------------------------------------------------------------------------
    // Combination of root blockchain id and crosschain transaction id of the
    // currently executing crosschain call. Used for locking. Provisional
    // values are stored against this id.
    bytes32 public activeCallCrosschainRootTxId;

    // Crosschain calls that the business logic code will make.
    bytes32[] private activeCallTargetFunctionCallHashes;

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

    constructor(uint256 _myBlockchainId) {
        myBlockchainId = _myBlockchainId;
    }

    /**
     * Commit to executing a call execution tree / start a crosschain transaction.
     *
     * @param _crossBlockchainTransactionId Id of the
     */
    function start(
        uint256 _crossBlockchainTransactionId,
        uint256 _timeout,
        bytes32 _callTreeHash
    ) external {
        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        require(
            rootTransactionInformation[_crossBlockchainTransactionId] ==
                NOT_USED,
            "Transaction already registered"
        );
        uint256 transactionTimeoutSeconds = _timeout + block.timestamp;
        rootTransactionInformation[
            _crossBlockchainTransactionId
        ] = transactionTimeoutSeconds;

        emit Start(
            _crossBlockchainTransactionId,
            msg.sender,
            transactionTimeoutSeconds,
            _callTreeHash
        );
    }

    /**
     * Execute a segment of the call execution tree.
     *
     * @param _events Array of events. Array offset 0 must be the start event. Other events must be segment events.
     * @param _callPath    The part of the call tree to be executed.
     * @param _callExecutionTree The call tree to be executed. The message digest of this must match the call tree hash emitted in the start event.
     * @param _target The function to be called.
     * @param _caller The entry point function that called this segment.
     */
    function segment(
        EventInfo[] calldata _events,
        uint256[] calldata _callPath,
        bytes calldata _callExecutionTree,
        Called calldata _target,
        Caller calldata _caller
    ) external {
        require(_callPath.length > 0, "Call path length too short");
        uint256[] memory callPathMem = _callPath;

        decodeAndVerifyEvents(_events, true);

        uint256 rootBcId = _events[0].blockchainId;

        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure segment is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Segment must be called from an EOA");

        // Event 0 will be the start event
        // Recall the start event is:
        // Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes32 _callGraphHash);
        uint256 crosschainTransactionId;
        //uint256 timeout;
        bytes32 callExecutionTreeHash;
        {
            address startCaller;
            (
                crosschainTransactionId,
                startCaller,
                ,
                callExecutionTreeHash
            ) = abi.decode(
                _events[0].eventData,
                (uint256, address, uint256, bytes32)
            );
            require(startCaller == tx.origin, "EOA does not match start event");
        }

        // Check that the call execution tree matches the hash form the start event.
        {
            // Scope to limit number of local variables compiler has to deal with.
            bytes32 calcCallExecutionTreeHash = keccak256(_callExecutionTree);
            require(
                calcCallExecutionTreeHash == callExecutionTreeHash,
                "Call execution tree doesn't match start event"
            );
        }

        // Stop replay of transaction segments.
        {
            // Scope to limit number of local variables compiler has to deal with.
            bytes32 mapKey = keccak256(
                abi.encodePacked(rootBcId, crosschainTransactionId, callPathMem)
            );
            require(
                segmentTransactionExecuted[mapKey] == false,
                "Segment transaction already executed"
            );
            segmentTransactionExecuted[mapKey] = true;
        }

        // No need to store call graph or call path if this is a leaf segment / function
        uint256 numSegments = _events.length - 1;
        if (numSegments > 0) {
            if (
                verifySegmentEvents(
                    _events,
                    _callPath,
                    callExecutionTreeHash,
                    crosschainTransactionId,
                    false
                )
            ) {
                return;
            }

            determineFuncsToBeCalled(
                _callExecutionTree,
                _callPath,
                numSegments
            );
        }

        // Set-up root blockchain / crosschain transaction value used for locking.
        activeCallCrosschainRootTxId = calcRootTxId(
            rootBcId,
            crosschainTransactionId
        );

        // Add application authentication information to the end of the call data.
        // For root transaction have parentBcId and parentContract == 0
        uint256 parentBcId;
        address parentContract;
        {
            uint256[] memory parentCallPath = determineParentCallPath(
                _callPath
            );
            bytes32 expectedParentFunctionCallHash = extractTargetHashFromCallGraph(
                    _callExecutionTree,
                    parentCallPath
                );
            bytes32 actualParentFunctionCallHash = keccak256(
                abi.encodePacked(
                    _caller.blockchainId,
                    _caller.addr,
                    _caller.funcCallHash
                )
            );

            require(
                actualParentFunctionCallHash == expectedParentFunctionCallHash,
                "Parent call parameters incorrect"
            );
            parentBcId = _caller.blockchainId;
            parentContract = _caller.addr;
        }

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = makeCall(
            _callExecutionTree,
            callPathMem,
            rootBcId,
            _target,
            parentBcId,
            parentContract
        );

        // TODO emit segments understanding of root blockhain id
        emit Segment(
            crosschainTransactionId,
            callExecutionTreeHash,
            callPathMem,
            activeCallLockedContracts,
            isSuccess,
            returnValueEncoded
        );

        // Only delete locations used.
        if (numSegments > 0) {
            cleanupAfterCallSegment();
        } else {
            cleanupAfterCallLeafSegment();
        }
    }

    /**
     * Execute the root call of the call execution tree.
     *
     * @param _events Array of events. Array offset 0 must be the start event. Other events must be segment events.
     * @param _callExecutionTree The call tree to be executed. The message digest of this must match the call tree hash emitted in the start event.
     * @param _target The function to be called.
     */
    function root(
        EventInfo[] calldata _events,
        bytes calldata _callExecutionTree,
        Called calldata _target
    ) external {
        decodeAndVerifyEvents(_events, true);

        uint256 rootBcId = _events[0].blockchainId;

        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure root is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Root must be called from an EOA");

        //Check that the blockchain Id that can be used with the transaction receipt for verification matches this
        // blockchain. That is, check that this is the root blockchain.
        require(myBlockchainId == rootBcId, "This is not the root blockchain");

        // Ensure this is the crosschain control contract that generated the start event.
        require(
            address(this) == _events[0].cbcAddress,
            "Root blockchain CBC contract was not this one"
        );

        // Event 0 will be the start event
        // Recall the start event is:
        // Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes32 _callGraphHash);
        uint256 crosschainTransactionId;
        //uint256 timeout;
        bytes32 callExecutionTreeHash;
        address startCaller;
        (crosschainTransactionId, startCaller, , callExecutionTreeHash) = abi
            .decode(_events[0].eventData, (uint256, address, uint256, bytes32));
        require(startCaller == tx.origin, "EOA does not match start event");

        // Check that Cross-blockchain Transaction Id as shown in the Start Event is still active.
        {
            // Scope to limit number of local variables compiler has to deal with.
            uint256 timeoutForCall = rootTransactionInformation[
                crosschainTransactionId
            ];
            require(timeoutForCall != NOT_USED, "Call not active");
            require(timeoutForCall != SUCCESS, "Call ended (success)");
            require(timeoutForCall != FAILURE, "Call ended (failure)");

            //Check if the cross-blockchain transaction has timed-out.
            if (block.timestamp > timeoutForCall) {
                failRootTransaction(crosschainTransactionId);
                cleanupAfterCallRoot();
                return;
            }
        }

        // Check that the tx.origin matches the account that submitted the Start transaction,
        // by checking the Start Event. Exit if it doesn’t.
        // This means that only the initiator of the cross-blockchain transaction can call this
        // function call prior to the time-out.
        require(startCaller == tx.origin, "EOA does not match start event");

        // Check that the call execution tree matches the hash form the start event.
        {
            // Scope to limit number of local variables compiler has to deal with.
            bytes32 calcCallExecutionTreeHash = keccak256(_callExecutionTree);
            require(
                calcCallExecutionTreeHash == callExecutionTreeHash,
                "Call execution tree doesn't match start event"
            );
        }

        // The element will be the default, 0.
        uint256[] memory callPathForRoot = new uint256[](1);

        if (
            verifySegmentEvents(
                _events,
                callPathForRoot,
                callExecutionTreeHash,
                crosschainTransactionId,
                true
            )
        ) {
            return;
        }
        uint256 numSegmentsCalled = _events.length - 1;
        determineFuncsToBeCalled(
            _callExecutionTree,
            callPathForRoot,
            numSegmentsCalled
        );

        // Set-up root blockchain / crosschain transaction value used for locking.
        // Store a copy in memory so that storage isn't read from in the code further below.
        bytes32 crosschainRootTxId = calcRootTxId(
            rootBcId,
            crosschainTransactionId
        );
        activeCallCrosschainRootTxId = crosschainRootTxId;

        bool isSuccess;
        (isSuccess, ) = makeCall(
            _callExecutionTree,
            callPathForRoot,
            rootBcId,
            _target,
            0,
            address(0)
        );

        // Unlock contracts locked by the root transaction.
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            LockableStorageInterface lockableStorageContract = LockableStorageInterface(
                    activeCallLockedContracts[i]
                );
            lockableStorageContract.finalise(isSuccess, crosschainRootTxId);
        }
        rootTransactionInformation[crosschainTransactionId] = isSuccess
            ? SUCCESS
            : FAILURE;
        emit Root(crosschainTransactionId, isSuccess);
        cleanupAfterCallRoot();
    }

    /**
     * Signalling call: Commit or ignore updates + unlock any locked contracts.
     *
     * @param _events Array of events. Array offset 0 must be the root event. Other events must be segment events.
     */
    function signalling(
        // TODO historically, Web3J didn't support arrays of structs in the Java code generator.
        EventInfo[] calldata _events
    ) external {
        decodeAndVerifyEvents(_events, false);

        // Extract information from the root event.
        // Recall: Root(uint256 _crossBlockchainTransactionId, bool _success);
        uint256 rootEventCrossBlockchainTxId;
        bool success;
        (rootEventCrossBlockchainTxId, success) = abi.decode(
            _events[0].eventData,
            (uint256, bool)
        );

        // Set-up root blockchain / crosschain transaction value for unlocking.
        bytes32 crosschainRootTxId = calcRootTxId(
            _events[0].blockchainId,
            rootEventCrossBlockchainTxId
        );

        // Check that all of the Segment Events are for the same transaction id, and are for this blockchain.
        for (uint256 i = 1; i < _events.length; i++) {
            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 segmentEventCrossBlockchainTxId;
            address[] memory lockedContracts;
            (segmentEventCrossBlockchainTxId, , , lockedContracts, , ) = abi
                .decode(
                    _events[i].eventData,
                    (uint256, bytes32, uint256[], address[], bool, bytes)
                );

            // Check that the cross blockchain transaction id is the same for the root and the segment event.
            require(
                rootEventCrossBlockchainTxId == segmentEventCrossBlockchainTxId
            );
            // TODO check the Root Blockchain id indicated in the segment matches the one from the root transaction.

            // TODO Check that the cross chain transaction id for the root blockchain id is in use on this blockchain but has not been added to the completed list.

            // For each address indicated in the Segment Event as being locked, Commit or Ignore updates
            // according to what the Root Event says.
            for (uint256 j = 0; j < lockedContracts.length; j++) {
                LockableStorageInterface lockedContract = LockableStorageInterface(
                        lockedContracts[j]
                    );
                lockedContract.finalise(success, crosschainRootTxId);
            }
        }

        emit Signalling(_events[0].blockchainId, rootEventCrossBlockchainTxId);
    }

    function crossBlockchainCall(
        uint256 _blockchainId,
        address _contract,
        bytes calldata _functionCallData
    ) external override {
        bool failed;
        bytes memory returnValue;
        (failed, returnValue) = commonCallProcessing(
            _blockchainId,
            _contract,
            _functionCallData
        );

        if (!failed) {
            if (!(compare(returnValue, bytes("")))) {
                emit CallFailure(
                    "Cross Blockchain Call with unexpected return values"
                );
                activeCallFailed = true;
            }
        }
    }

    function crossBlockchainCallReturnsUint256(
        uint256 _blockchainId,
        address _contract,
        bytes calldata _functionCallData
    ) external override returns (uint256) {
        bool failed;
        bytes memory returnValue;
        (failed, returnValue) = commonCallProcessing(
            _blockchainId,
            _contract,
            _functionCallData
        );
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
    function addToListOfLockedContracts(address _contractToLock)
        external
        override
    {
        // Don't add the same contract twice. So, check the contract isn't in
        // the array first.
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            if (activeCallLockedContracts[i] == _contractToLock) {
                return;
            }
        }
        activeCallLockedContracts.push(_contractToLock);
    }

    function getActiveCallCrosschainRootTxId()
        public
        view
        override
        returns (bytes32)
    {
        return activeCallCrosschainRootTxId;
    }

    function isSingleBlockchainCall() public view override returns (bool) {
        return activeCallCrosschainRootTxId == 0;
    }

    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************
    // **************************** PRIVATE BELOW HERE ***************************

    function makeCall(
        bytes calldata _callTree,
        uint256[] memory _callPath,
        uint256 _rootBcId,
        Called calldata _target,
        uint256 _parentBcId,
        address _parentAddress
    ) private returns (bool, bytes memory) {
        // Check that the the hash of the function call indicated by the call path matches.
        bytes32 expectedFunctionCallHash = extractTargetHashFromCallGraph(
            _callTree,
            _callPath
        );
        bytes32 functionHash = keccak256(_target.funcCallData);
        bytes32 actualFunctionCallHash = keccak256(
            abi.encodePacked(myBlockchainId, _target.addr, functionHash)
        );
        require(
            expectedFunctionCallHash == actualFunctionCallHash,
            "Target function call hash does not match expected"
        );

        bytes memory functionCallWithAuth = encodeAtomicAuthParams(
            _target.funcCallData,
            _rootBcId,
            _parentBcId,
            _parentAddress
        );

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = _target.addr.call(
            functionCallWithAuth
        );

        if (!isSuccess) {
            emit CallFailure(getRevertMsg(returnValueEncoded));
        }

        // Check that all cross-blockchain calls were used.
        // Do this even if the call has already failed: it will provide a bit
        // more debug information.
        if (activeCallReturnValues.length != activeCallReturnValuesIndex) {
            emit NotEnoughCalls(
                activeCallReturnValues.length,
                activeCallReturnValuesIndex
            );
            isSuccess = false;
        }

        // Fail if one of the called segments failed.
        isSuccess = activeCallFailed ? false : isSuccess;
        return (isSuccess, returnValueEncoded);
    }

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
        delete activeCallTargetFunctionCallHashes;
        delete activeCallCrosschainRootTxId;
        delete activeCallLockedContracts;
        delete activeCallReturnValues;
        delete activeCallReturnValuesIndex;

        // Indicates a failure happened in a call out to another segment.
        if (activeCallFailed) {
            // Save gas by only writing to the location is it is not the default value.
            delete activeCallFailed;
        }
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

        // Not used by leaf calls:
        //        delete activeCallTargetBcIds;
        //        delete activeCallTargetContracts;
        //        delete activeCallTargetFunctionCalls;

        //        delete activeCallReturnValues;
        //        delete activeCallReturnValuesIndex;
    }

    function cleanupAfterCallRoot() private {
        // Used for calling segments
        delete activeCallTargetFunctionCallHashes;
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
    }

    function commonCallProcessing(
        uint256 _blockchainId,
        address _contract,
        bytes calldata _functionCallData
    ) private returns (bool, bytes memory) {
        uint256 returnValuesIndex = activeCallReturnValuesIndex;
        // Fail if we have run out of return results.
        if (returnValuesIndex >= activeCallReturnValues.length) {
            activeCallFailed = true;
            return (true, bytes(""));
        }

        bytes32 calledFuncCallHash = keccak256(_functionCallData);
        bytes32 calledFunctionCallHash = keccak256(
            abi.encodePacked(_blockchainId, _contract, calledFuncCallHash)
        );

        bytes32 targetFunctionCallHash = activeCallTargetFunctionCallHashes[
            returnValuesIndex
        ];

        // Fail if what was called doesn't match what was expected to be called.
        if (calledFunctionCallHash != targetFunctionCallHash) {
            activeCallFailed = true;
            activeCallReturnValuesIndex = returnValuesIndex + 1;
            emit BadCall(
                targetFunctionCallHash,
                _blockchainId,
                _contract,
                _functionCallData
            );
            return (true, bytes(""));
        }
        bytes memory retVal = activeCallReturnValues[returnValuesIndex++];
        activeCallReturnValuesIndex = returnValuesIndex;
        // TODO CallResult events are not needed and cost gas.
        emit CallResult(_blockchainId, _contract, _functionCallData, retVal);
        return (false, retVal);
    }

    /**
     * Verify the event information in the Segment Events. This will be called by
     * the segment or root processing.  The segment events represent functions
     * that have been called by the function being executed in the segment or
     * root processing.
     *
     * Check that the Segment Events correspond to function calls that the
     * call execute tree in the Start Event indicates should have been called
     * by the function that is currently executing.
     *
     * For details of the call path format see: https://github.com/ConsenSys/gpact/blob/main/docs/callpath.md
     *
     * @param _segmentEvents   Encoded segment event information for each function
     *           called from the function being executed. The array elements must
     *           be in the order that the functions are called by the code.
     *           Note that offset 0 is the start event, and all other offsets
     *           are segment events.
     * @param _callPath        The call path of of the function being executed.
     * @param _hashOfCallTree The message digest of the call execution tree in the
     *           Start Event.
     * @param _crosschainTxId  The crosschain transaction id of the function being
     *           executed.
     * @return True if all of the segment event information can be verified.
     */
    function verifySegmentEvents(
        EventInfo[] calldata _segmentEvents,
        uint256[] memory _callPath,
        bytes32 _hashOfCallTree,
        uint256 _crosschainTxId,
        bool _asRoot
    ) private returns (bool) {
        // The caller must be a segment that is calling other segments or the
        // root call (which also is calling segments). The call path for the
        // caller in both cases will have 0 as the offset in the call path
        // array for the final element in the array.
        require(_callPath[_callPath.length - 1] == 0, "Invalid caller");

        // Start at offset 1 as offset 0 is the start event.
        for (uint256 i = 1; i < _segmentEvents.length; i++) {
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 crossBlockchainTxId;
            bytes32 hashOfCallTreeFromSegment;
            uint256[] memory segCallPath;
            address[] memory lockedContracts;
            bool success;
            bytes memory returnValue;
            (
                crossBlockchainTxId,
                hashOfCallTreeFromSegment,
                segCallPath,
                lockedContracts,
                success,
                returnValue
            ) = abi.decode(
                _segmentEvents[i].eventData,
                (uint256, bytes32, uint256[], address[], bool, bytes)
            );

            require(
                crossBlockchainTxId == _crosschainTxId,
                "Transaction id from segment and root do not match"
            );
            require(
                _hashOfCallTree == hashOfCallTreeFromSegment,
                "Call tree from segment and root do not match"
            );

            // The segment will be the same as the call path length of the caller
            // if it doesn't call any other segments. It will be one longer if
            // it calls one or more segments. In this case, the last entry in
            // the array will be 0.
            require(
                segCallPath.length == _callPath.length ||
                    segCallPath.length == _callPath.length + 1,
                "Bad call path length for segment"
            );
            if (segCallPath.length == _callPath.length + 1) {
                require(
                    segCallPath[segCallPath.length - 1] == 0,
                    "Final call path element not zero"
                );
            }

            // Check that the call path for the segment matches that of the
            // caller.
            for (uint256 j = 0; j < _callPath.length - 1; j++) {
                require(
                    segCallPath[j] == _callPath[j],
                    "Segment call path does not match caller"
                );
            }

            // Check that the final element in the call path matches the offset
            // for the segment. That is, for example, if this is the second
            // function to be called by the function currently being executed,
            // then the final element of the call path should be 2 and this should
            // be the second element in the _segmentEvents array.
            require(
                segCallPath[_callPath.length - 1] == i,
                "Segment events array out of order"
            );

            // Fail the root/segment transaction as one of the segments failed.
            if (!success) {
                if (_asRoot) {
                    failRootTransaction(_crosschainTxId);
                } else {
                    emit Segment(
                        _crosschainTxId,
                        _hashOfCallTree,
                        _callPath,
                        new address[](0),
                        false,
                        new bytes(0)
                    );
                }
                cleanupAfterCallSegment();
                return true;
            }

            // Store the extracted return results from segment events.
            activeCallReturnValues.push(returnValue);
        }
        return false;
    }

    /**
     * Determine the functions that are going to be called from this segment or root function, and
     * set-up the activeCallTargetBcIds, activeCallTargetContracts, and activeCallTargetFunctionCalls
     * with the appropriate values, in preparation for the business logic function executing a
     * crosschain coll.
     *
     * @param _callTree Call tree to be executed.
     * @param _callPath Call path to be executed as part of this root or segment function.
     * @param _numCalls The number of calls that the current segment / root is expected to call.
     */
    function determineFuncsToBeCalled(
        bytes calldata _callTree,
        uint256[] memory _callPath,
        uint256 _numCalls
    ) private {
        uint256 callPathLen = _callPath.length;
        uint256[] memory dynamicCallPath = new uint256[](callPathLen);
        for (uint256 i = 0; i < callPathLen - 1; i++) {
            dynamicCallPath[i] = _callPath[i];
        }

        for (uint256 i = 1; i <= _numCalls; i++) {
            dynamicCallPath[callPathLen - 1] = i;

            bytes32 functionCallHash = extractTargetHashFromCallGraph(
                _callTree,
                dynamicCallPath
            );
            activeCallTargetFunctionCallHashes.push(functionCallHash);
        }
    }

    /**
     * Calculate the combined Root Blockchain and Crosschain Transaction Id. This
     * is used for calls from lockable storage.
     */
    function calcRootTxId(uint256 _rootBcId, uint256 _crossTxId)
        private
        pure
        returns (bytes32)
    {
        return keccak256(abi.encodePacked(_rootBcId, _crossTxId));
    }

    function decodeAndVerifyEvents(
        EventInfo[] calldata _eventInfo,
        bool _expectStart
    ) private view {
        // The minimum number of events is 1: start and no segment, used to end a timed-out
        // crosschain transactions.
        uint256 numEvents = _eventInfo.length;
        require(numEvents > 0, "Must be at least one event");

        for (uint256 i = 0; i < numEvents; i++) {
            if (i == 0) {
                bytes32 eventSig = _expectStart
                    ? START_EVENT_SIGNATURE
                    : ROOT_EVENT_SIGNATURE;
                require(
                    eventSig == _eventInfo[i].eventFunctionSignature,
                    "Unexpected first event function signature"
                );
            } else {
                require(
                    SEGMENT_EVENT_SIGNATURE ==
                        _eventInfo[i].eventFunctionSignature,
                    "Event function signature not for a segment"
                );
            }

            decodeAndVerifyEvent(
                _eventInfo[i].blockchainId,
                _eventInfo[i].cbcAddress,
                _eventInfo[i].eventFunctionSignature,
                _eventInfo[i].eventData,
                _eventInfo[i].signatures
            );
        }
    }
}
