/*
 * Copyright ConsenSys AG.
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
package tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.ethereum.rlp.RLP;

import static org.hyperledger.besu.crypto.Hash.keccak256;

/** An Merkle Patricial Trie. */
public interface MerklePatriciaTrie<K, V> {

  Bytes EMPTY_TRIE_NODE = RLP.NULL;
  Bytes32 EMPTY_TRIE_NODE_HASH = keccak256(EMPTY_TRIE_NODE);

  /**
   * Returns an {@code Optional} of value mapped to the hash if it exists; otherwise empty.
   *
   * @param key The key for the value.
   * @return an {@code Optional} of value mapped to the hash if it exists; otherwise empty
   */
  Optional<V> get(K key);

  /**
   * Returns value and ordered proof-related nodes mapped to the hash if it exists; otherwise empty.
   *
   * @param key The key for the value.
   * @return value and ordered proof-related nodes
   */
  Proof<V> getValueWithProof(K key);

  /**
   * Returns a MultiMerkleProof along with the values
   *
   * @param keys The list of keys for which the multi Merkle proof is desired.
   * @return a multi Merkle proof along with the data
   */
   default MultiMerkleProof<V> getValuesWithMultiMerkleProof(List<Bytes> keys) {
     return new MultiMerkleProof<>(NullNode.instance());
   }

   default Proof<Bytes> getValueWithSimpleProof(final K key) {
     throw new RuntimeException("not implemented yet");
   }


    /**
     * Updates the value mapped to the specified key, creating the mapping if one does not already
     * exist.
     *
     * @param key The key that corresponds to the value to be updated.
     * @param value The value to associate the key with.
     */
  void put(K key, V value);

  /**
   * Deletes the value mapped to the specified key, if such a value exists (Optional operation).
   *
   * @param key The key of the value to be deleted.
   */
  void remove(K key);

  /**
   * Returns the KECCAK256 hash of the root node of the trie.
   *
   * @return The KECCAK256 hash of the root node of the trie.
   */
  Bytes32 getRootHash();

  /**
   * Commits any pending changes to the underlying storage.
   *
   * @param nodeUpdater used to store the node values
   */
  void commit(NodeUpdater nodeUpdater);

  /**
   * Retrieve up to {@code limit} storage entries beginning from the first entry with hash equal to
   * or greater than {@code startKeyHash}.
   *
   * @param startKeyHash the first key hash to return.
   * @param limit the maximum number of entries to return.
   * @return the requested storage entries as a map of key hash to value.
   */
  Map<Bytes32, V> entriesFrom(Bytes32 startKeyHash, int limit);

  void visitAll(Consumer<Node<V>> visitor);
}
