/*
 * Copyright 2020 ConsenSys Software Inc
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

import "./TxReceiptsRootStorageInterface.sol";
import "../common/MessagingRegistrar.sol";
import "../../common/ERC165MappingImplementation.sol";
import "../../common/BytesUtil.sol";

contract TxReceiptsRootStorage is
    TxReceiptsRootStorageInterface,
    ERC165MappingImplementation,
    BytesUtil
{
    MessagingRegistrar registrar;

    // Mapping (blockchain Id => mapping(transaction receipt root) => bool)
    // The bool is true if the transaction receipt root exists for the blockchain
    mapping(uint256 => mapping(bytes32 => bool)) private txReceiptsRoots;

    constructor(address _registrar) {
        registrar = MessagingRegistrar(_registrar);

        supportedInterfaces[
            type(TxReceiptsRootStorageInterface).interfaceId
        ] = true;
    }

    function addTxReceiptRoot(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes32 _txReceiptsRoot
    ) external override(TxReceiptsRootStorageInterface) {
        bytes memory txReceiptsRootBytes = abi.encodePacked(_txReceiptsRoot);
        registrar.verifyAndCheckThreshold(
            _blockchainId,
            _signers,
            _sigR,
            _sigS,
            _sigV,
            txReceiptsRootBytes
        );
        txReceiptsRoots[_blockchainId][_txReceiptsRoot] = true;
    }

    function verify(
        uint256 _blockchainId,
        address, // _cbcContract
        bytes32 _txReceiptsRoot,
        bytes calldata _txReceipt,
        uint256[] calldata _proofOffsets,
        bytes[] calldata _proof
    ) external view override(TxReceiptsRootStorageInterface) returns (bool) {
        require(
            txReceiptsRoots[_blockchainId][_txReceiptsRoot],
            "Transaction receipt root does not exist for blockchain id"
        );
        require(
            _proof.length == _proofOffsets.length,
            "Length of proofs and proofsOffsets does not match"
        );
        // Check the CBC contract is done in the CrosschainControl.
        //registrar.verifyContract(_blockchainId, _cbcContract);

        bytes32 hash = keccak256(_txReceipt);
        for (uint256 i = 0; i < _proof.length; i++) {
            bytes32 candidateHash = BytesUtil.bytesToBytes32(
                _proof[i],
                _proofOffsets[i]
            );
            require(
                candidateHash == hash,
                "Candidate Hash did not match calculated hash"
            );
            hash = keccak256(_proof[i]);
        }
        require(
            _txReceiptsRoot == hash,
            "Root Hash did not match calculated hash"
        );
        return true;
    }

    function containsTxReceiptRoot(
        uint256 _blockchainId,
        bytes32 _txReceiptsRoot
    ) external view override(TxReceiptsRootStorageInterface) returns (bool) {
        return (txReceiptsRoots[_blockchainId][_txReceiptsRoot]);
    }
}
