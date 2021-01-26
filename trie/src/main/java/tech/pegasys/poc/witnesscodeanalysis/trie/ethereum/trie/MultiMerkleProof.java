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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.logging.log4j.LogManager.getLogger;
import static tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.CompactEncoding.bytesToPath;

import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;

/**
 * An in-memory MultiMerkleProof. This is a sub-structure of MerklePatriciaTrie with
 * additional MerkleProofHashNode's. Given a sequence of key value pairs, and a root hash,
 * the instance of this class can be used as an evidence that the key value paris indeed
 * belong to the trie pointed by the root hash.
 *
 * @param <V> The type of values stored by this trie.
 */
public class MultiMerkleProof<V>  {
  private static final Logger LOG = getLogger();
  private final PathNodeVisitor<V> getVisitor = new GetVisitor<>();
  private final MultiproofStatsVisitor<V> statsVisitor = new MultiproofStatsVisitor<>();

  private Node<V> root;

  public MultiMerkleProof(final Node<V> root) {
    this.root = root;
  }

  public Optional<V> get(final Bytes key) {
    checkNotNull(key);
    return root.accept(getVisitor, bytesToPath(key)).getValue();
  }


  public Bytes32 computeRootHash() {
    return root.getHash();
  }

  public void printStats(){
    if(statsVisitor.getNumHashes() == 0) {
      root.accept(statsVisitor);
    }
    LOG.info("#Hashes = {}, #Extension nodes = {}, #Branch nodes = {}",
      statsVisitor.getNumHashes(), statsVisitor.getNumExtensions(), statsVisitor.getNumBranches());
  }

  public Bytes getRlp() {
    return root.getRlp();
  }


  @Override
  public String toString() {
    return root.print();
  }
}
