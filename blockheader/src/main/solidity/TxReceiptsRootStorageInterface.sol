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
pragma experimental ABIEncoderV2;

import "../../../../common/src/main/solidity/ERC165.sol";

/**
 * Transaction receipt root data store.
 */
interface TxReceiptsRootStorageInterface is ERC165 {

    /**
     * Add a transaction receipt root to the transaction receipt root data store.
     *
     * Reverts in the following situations:
     * * If the number of signers and signatures do not match
     * * If any of the signers are not registered for the blockchain id
     * * The any signature in the array can not be verified using the corresponding signer address in the _signer array
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _signers Array of addresses that correspond to the public keys of the signers.
     * @param _sigR Array of R components of the signatures to be verified.
     * @param _sigS Array of S components of the signatures to be verified.
     * @param _sigV Array of V components of the signatures to be verified.
     * @param _txReceiptsRoot The transaction receipt root to add to the data store.
     */
    function addTxReceiptRoot(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes32 _txReceiptsRoot) external;


    /**
     * Verify that a transaction receipt is part of the Merkle Patricia Trie that hashes to a transaction
     * receipt root that belongs to a blockchain. Implementations check that the transaction receipt root
     * _txReceiptsRoot belongs to the blockchain specified by _blockchainId, and then verify that the
     * transaction receipt given by _txReceipt is proven to be part of the Merkle Partricia Trie given
     * _proof, _proofOffsets, and _txReceiptsRoot.
     *
     * Reverts in the following situations:
     * * Transaction receipt has not been added for the blockchain.
     * * The proof does not prove that the transaction receipt is part of the Merkle Patricia Trie.
     *
     * Ethereum transaction receipts are arranged as a Merkle Patricia Trie, with the leaf nodes of the trie being
     * the transaction receipts. The message digest of the nodes are included in the nodes next closer to the root
     * of the trie.
     *
     * If there is just one transaction in a block, then there is only one leaf node for the one transaction
     * receipt. The message digest is the transaction receipt root. In this case _proof and _proofOffsets are
     * empty arrays.
     *
     * If there are between two and sixteen transactions, there will be between two and sixteen leaf nodes to match
     * the transactions, plus a branch node. The RLP encoded branch node is the only element of _proof. The
     * message digest of this value must match the transaction receipt root. The message digest of _txReceipt must be
     * present in the RLP encoded branch node (that is the element of _proof) at the offset specified by _proofOffsets.
     *
     * If there are between seventeen and two hundred and fifty-six transactions, then there will be two layers of branch
     * nodes in the Merkle Patricia Trie, in addition to the leaf nodes. In this case, _proof and _proofOffsets will
     * have two elements, matching the two layers. The offsets in _proofOffsets allow the message digests to be
     * found in the next level up proof.
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _cbcContract Address of contract that emitted the event.
     * @param _txReceiptsRoot The transaction receipt root to reference.
     * @param _txReceipt The value that is being proven to be part of the Merkle Patricia Trie of transaction receipts.
     * @param _proofOffsets The offset of the message digest of the previous level node in the Merkle Patricia Trie
     *   within the RLP encoded data for the current node.
     * @param _proof The RLP encoding of branch nodes in the transaction receipt Merkle Patricia Trie.
     *
     */
    function verify(uint256 _blockchainId, address _cbcContract, bytes32 _txReceiptsRoot, bytes calldata _txReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof) external view returns (bool) ;


    /**
     * Check that a transaction receipt root has been added to the data store.
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _txReceiptsRoot The transaction receipt root to reference.
     * @return true if the transaction receipt has been stored in the data store for the blockchain id.
     */
    function containsTxReceiptRoot(uint256 _blockchainId, bytes32 _txReceiptsRoot) external view returns (bool);
}